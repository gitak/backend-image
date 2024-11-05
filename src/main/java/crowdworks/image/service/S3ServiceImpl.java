package crowdworks.image.service;

import crowdworks.image.common.ErrorCode;
import crowdworks.image.config.S3Properties;
import crowdworks.image.dto.request.ImageFile;
import crowdworks.image.dto.request.ImageUploadRequest;
import crowdworks.image.dto.request.PresignedUrlRequest;
import crowdworks.image.dto.response.PresignedUrlResponse;
import crowdworks.image.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3ServiceImpl {

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;
    private final S3Properties s3Properties;

    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB
    private static final List<String> ALLOWED_CONTENT_TYPES = Arrays.asList(
            "image/jpeg", "image/png", "image/gif", "image/webp"
    );

    public List<PresignedUrlResponse> generatePresignedUrls(ImageUploadRequest request) {
        validateRequest(request);

        return request.getFiles().stream()
                .map(imageFile -> generatePresignedUrl(imageFile, request.getDirectoryPath()))
                .collect(Collectors.toList());
    }

    private void validateRequest(ImageUploadRequest request) {
        if (request.getFiles() == null || request.getFiles().isEmpty()) {
            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE, "At least one file must be provided");
        }

        List<String> errors = new ArrayList<>();

        request.getFiles().forEach(imageFile -> {
            if (!imageFile.isValidSize()) {
                errors.add(String.format("File %s exceeds maximum size of 5MB",
                        imageFile.getFileName()));
            }
            if (!imageFile.isImageFile()) {
                errors.add(String.format("File %s is not a valid image file",
                        imageFile.getFileName()));
            }
            if (!ALLOWED_CONTENT_TYPES.contains(imageFile.getFile().getContentType())) {
                errors.add(String.format("File %s has unsupported content type: %s",
                        imageFile.getFileName(), imageFile.getFile().getContentType()));
            }
        });

        if (!errors.isEmpty()) {
            if (errors.stream().anyMatch(error -> error.contains("maximum size"))) {
                throw new BusinessException(ErrorCode.FILE_SIZE_EXCEED, String.join(", ", errors));
            }
            throw new BusinessException(ErrorCode.INVALID_FILE_TYPE, String.join(", ", errors));
        }
    }

    private PresignedUrlResponse generatePresignedUrl(ImageFile imageFile, String directoryPath) {
        String fileName = createUniqueFileName(imageFile.getFileName());
        String fullPath = createFullPath(directoryPath, fileName);

        try {
            PutObjectRequest objectRequest = PutObjectRequest.builder()
                    .bucket(s3Properties.getBucket())
                    .key(fullPath)
                    .contentType(imageFile.getFile().getContentType())
                    .contentLength(imageFile.getFile().getSize())
                    .build();

            PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(10))
                    .putObjectRequest(objectRequest)
                    .build();

            PresignedPutObjectRequest presignedRequest = s3Presigner.presignPutObject(presignRequest);

            return PresignedUrlResponse.builder()
                    .presignedUrl(presignedRequest.url().toString())
                    .fileName(fileName)
                    .fullPath(fullPath)
                    .originalFileName(imageFile.getFileName())
                    .fileSize(imageFile.getFile().getSize())
                    .contentType(imageFile.getFile().getContentType())
                    .build();
        } catch (S3Exception e) {
            log.error("S3 error occurred while generating presigned URL for file: {}", imageFile.getFileName(), e);
            throw new BusinessException(ErrorCode.S3_UPLOAD_ERROR,
                    "Failed to generate presigned URL for file: " + imageFile.getFileName(), e);
        } catch (Exception e) {
            log.error("Unexpected error occurred while generating presigned URL for file: {}", imageFile.getFileName(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR,
                    "Unexpected error while generating presigned URL for file: " + imageFile.getFileName(), e);
        }
    }

    private String createUniqueFileName(String originalFileName) {
        String uuid = UUID.randomUUID().toString();
        String extension = StringUtils.getFilenameExtension(originalFileName);
        return uuid + "." + extension;
    }

    private String createFullPath(String directoryPath, String fileName) {
        if (StringUtils.hasText(directoryPath)) {
            return directoryPath.endsWith("/") ?
                    directoryPath + fileName :
                    directoryPath + "/" + fileName;
        }
        return fileName;
    }

    // 파일 삭제 메소드
    public void deleteFile(String fullPath) {
        try {
            DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
                    .bucket(s3Properties.getBucket())
                    .key(fullPath)
                    .build();

            s3Client.deleteObject(deleteRequest);
            log.info("Successfully deleted file: {}", fullPath);
        } catch (Exception e) {
            log.error("Failed to delete file from S3: {}", fullPath, e);
            throw new RuntimeException("Failed to delete file from S3", e);
        }
    }
}


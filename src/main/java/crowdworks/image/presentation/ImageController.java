package crowdworks.image.presentation;

import crowdworks.image.common.ResultCode;
import crowdworks.image.dto.request.ImageFile;
import crowdworks.image.dto.request.ImageUploadRequest;
import crowdworks.image.dto.request.PresignedUrlRequest;
import crowdworks.image.dto.response.ApiResponse;
import crowdworks.image.dto.response.ImageResponse;
import crowdworks.image.dto.response.PresignedUrlResponse;
import crowdworks.image.service.ImageService;
import crowdworks.image.service.S3Service;
import crowdworks.image.service.S3ServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {
//    private final ImageService imageService;
    private final S3ServiceImpl s3Service;

    @PostMapping(value = "/presigned-urls", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<PresignedUrlResponse>> generatePresignedUrls(
            @RequestPart(value = "files") List<MultipartFile> files,
            @RequestParam(value = "directoryPath", required = false) String directoryPath) {

        List<ImageFile> imageFiles = files.stream()
                .map(file -> {
                    ImageFile imageFile = new ImageFile();
                    imageFile.setFileName(file.getOriginalFilename());
                    imageFile.setFile(file);
                    return imageFile;
                })
                .collect(Collectors.toList());

        ImageUploadRequest request = new ImageUploadRequest();
        request.setDirectoryPath(directoryPath);
        request.setFiles(imageFiles);

        List<PresignedUrlResponse> responses = s3Service.generatePresignedUrls(request);
        return ResponseEntity.ok(responses);
    }

}
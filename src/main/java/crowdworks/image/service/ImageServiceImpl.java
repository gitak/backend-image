package crowdworks.image.service;

import crowdworks.image.common.ErrorCode;
import crowdworks.image.domain.Image;
import crowdworks.image.dto.request.ImageUploadRequest;
import crowdworks.image.dto.response.ImageResponse;
import crowdworks.image.exception.BusinessException;
import crowdworks.image.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

//@Service
//@RequiredArgsConstructor
//@Transactional
//@Slf4j
//public class ImageServiceImpl implements ImageService {
//    private final ImageRepository imageRepository;
//    private final S3Service s3Service;
//
//    @Override
//    public ImageResponse saveImageInfo(ImageUploadRequest request) {
//        Image image = Image.builder()
//                .originalFileName(request.getOriginalFileName())
//                .storedFileName(request.getStoredFileName())
//                .s3Url(s3Service.getObjectUrl(request.getObjectKey()))
//                .contentType(request.getContentType())
//                .fileSize(request.getFileSize())
//                .uploadPath(request.getUploadPath())
//                .build();
//
//        Image savedImage = imageRepository.save(image);
//        log.info("Image information saved - Original: {}, Stored: {}, Path: {}",
//                request.getOriginalFileName(), request.getStoredFileName(), request.getUploadPath());
//
//        return ImageResponse.from(savedImage);
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<ImageResponse> getAllImages() {
//        return imageRepository.findAll().stream()
//                .map(ImageResponse::from)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public ImageResponse getImage(Long imageId) {
//        Image image = imageRepository.findById(imageId)
//                .orElseThrow(() -> new BusinessException(ErrorCode.IMAGE_NOT_FOUND));
//        return ImageResponse.from(image);
//    }
//
//    @Override
//    public void deleteImage(String fileName) {
//        Image image = imageRepository.findByStoredFileName(fileName)
//                .orElseThrow(() -> new BusinessException(ErrorCode.IMAGE_NOT_FOUND));
//
//        s3Service.deleteObject(image.getUploadPath() + "/" + fileName);
//        imageRepository.delete(image);
//
//        log.info("Image deleted - fileName: {}, path: {}", fileName, image.getUploadPath());
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<ImageResponse> getImagesByPath(String path) {
//        return imageRepository.findByUploadPath(path).stream()
//                .map(ImageResponse::from)
//                .collect(Collectors.toList());
//    }
//}

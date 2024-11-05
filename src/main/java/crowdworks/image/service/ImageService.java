package crowdworks.image.service;

import crowdworks.image.dto.request.ImageUploadRequest;
import crowdworks.image.dto.response.ImageResponse;

import java.util.List;

public interface ImageService {
    /**
     * PreSigned URL을 통해 업로드된 이미지의 정보를 저장
     */
    ImageResponse saveImageInfo(ImageUploadRequest request);

    /**
     * 모든 이미지 정보 조회
     */
    List<ImageResponse> getAllImages();

    /**
     * 특정 이미지 정보 조회
     */
    ImageResponse getImage(Long imageId);

    /**
     * 이미지 삭제
     */
    void deleteImage(String fileName);

    /**
     * 특정 경로의 이미지들 조회
     */
    List<ImageResponse> getImagesByPath(String path);
}

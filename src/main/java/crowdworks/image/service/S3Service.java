package crowdworks.image.service;

import crowdworks.image.dto.request.PresignedUrlRequest;
import crowdworks.image.dto.response.PresignedUrlResponse;

public interface S3Service {
    PresignedUrlResponse generatePresignedUrl(PresignedUrlRequest request);
    void deleteObject(String objectKey);
    String getObjectUrl(String objectKey);
}

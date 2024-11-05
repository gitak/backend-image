package crowdworks.image.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    private String originalFileName;
    private String storedFileName;
    private String s3Url;
    private String contentType;
    private long fileSize;
    private LocalDateTime uploadedAt;
    private String uploadPath;

    @Builder
    public Image(String originalFileName, String storedFileName, String s3Url,
                 String contentType, long fileSize, String uploadPath) {
        this.originalFileName = originalFileName;
        this.storedFileName = storedFileName;
        this.s3Url = s3Url;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.uploadPath = uploadPath;
        this.uploadedAt = LocalDateTime.now();
    }
}

package crowdworks.image.repository;

import crowdworks.image.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByStoredFileName(String fileName);

    List<Image> findByUploadPath(String uploadPath);
}

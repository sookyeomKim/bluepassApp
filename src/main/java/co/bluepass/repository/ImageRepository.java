package co.bluepass.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.bluepass.domain.Image;

/**
 * The interface Image repository.
 */
public interface ImageRepository extends JpaRepository<Image, Long> {
}

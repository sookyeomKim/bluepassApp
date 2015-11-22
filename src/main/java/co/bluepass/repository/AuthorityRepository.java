package co.bluepass.repository;

import co.bluepass.domain.Authority;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Authority repository.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {

    /**
     * Count by name int.
     *
     * @param requestType the request type
     * @return the int
     */
    int countByName(String requestType);
}

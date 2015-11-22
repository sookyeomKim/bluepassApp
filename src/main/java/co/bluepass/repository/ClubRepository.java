package co.bluepass.repository;

import co.bluepass.domain.Club;
import co.bluepass.domain.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * The interface Club repository.
 */
public interface ClubRepository extends JpaRepository<Club,Long> {

    /**
     * Find all for current user list.
     *
     * @return the list
     */
    @Query("select club from Club club where club.creator.email = ?#{principal.username}")
    List<Club> findAllForCurrentUser();

    /**
     * Find by creator page.
     *
     * @param user                the user
     * @param generatePageRequest the generate page request
     * @return the page
     */
    Page<Club> findByCreator(User user, Pageable generatePageRequest);

    /**
     * Find by name like page.
     *
     * @param name                the name
     * @param generatePageRequest the generate page request
     * @return the page
     */
    Page<Club> findByNameLike(String name, Pageable generatePageRequest);

}

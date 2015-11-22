package co.bluepass.repository;

import co.bluepass.domain.Action;
import co.bluepass.domain.Club;
import co.bluepass.domain.CommonCode;
import co.bluepass.domain.User;
import co.bluepass.domain.Zip;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * The interface Action repository.
 */
public interface ActionRepository extends JpaRepository<Action,Long> {

    /**
     * Find all for current user list.
     *
     * @return the list
     */
    @Query("select action from Action action where action.creator.email = ?#{principal.username}")
    List<Action> findAllForCurrentUser();

    /**
     * Find by club list.
     *
     * @param club the club
     * @return the list
     */
    List<Action> findByClub(Club club);

    /**
     * Find by creator page.
     *
     * @param user                the user
     * @param generatePageRequest the generate page request
     * @return the page
     */
    Page<Action> findByCreator(User user, Pageable generatePageRequest);

    /**
     * Find by category page.
     *
     * @param categoryCode        the category code
     * @param generatePageRequest the generate page request
     * @return the page
     */
    Page<Action> findByCategory(CommonCode categoryCode, Pageable generatePageRequest);

    /**
     * Find by category list.
     *
     * @param categoryCode the category code
     * @return the list
     */
    List<Action> findByCategory(CommonCode categoryCode);

    /**
     * Find by category and zip list.
     *
     * @param categoryCode the category code
     * @param zip          the zip
     * @return the list
     */
    List<Action> findByCategoryAndZip(CommonCode categoryCode, Zip zip);

    /**
     * Find by category and zip page.
     *
     * @param categoryCode        the category code
     * @param zip                 the zip
     * @param generatePageRequest the generate page request
     * @return the page
     */
    Page<Action> findByCategoryAndZip(CommonCode categoryCode, Zip zip, Pageable generatePageRequest);

    /**
     * Find by zip list.
     *
     * @param zip the zip
     * @return the list
     */
    List<Action> findByZip(Zip zip);

    /**
     * Find by zip page.
     *
     * @param zip                 the zip
     * @param generatePageRequest the generate page request
     * @return the page
     */
    Page<Action> findByZip(Zip zip, Pageable generatePageRequest);

    /**
     * Find all page.
     *
     * @param spec                the spec
     * @param generatePageRequest the generate page request
     * @return the page
     */
    Page<Action> findAll(Specification<Action> spec, Pageable generatePageRequest);

}

package co.bluepass.repository;

import co.bluepass.domain.Club;
import co.bluepass.domain.Feature;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * The interface Feature repository.
 */
public interface FeatureRepository extends JpaRepository<Feature,Long> {

    /**
     * Find by club list.
     *
     * @param club the club
     * @return the list
     */
    List<Feature> findByClub(Club club);

}

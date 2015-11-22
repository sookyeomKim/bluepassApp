package co.bluepass.repository;

import co.bluepass.domain.Action;
import co.bluepass.domain.Club;
import co.bluepass.domain.Instructor;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * The interface Instructor repository.
 */
public interface InstructorRepository extends JpaRepository<Instructor,Long> {

    /**
     * Find by club list.
     *
     * @param club the club
     * @return the list
     */
    List<Instructor> findByClub(Club club);

}

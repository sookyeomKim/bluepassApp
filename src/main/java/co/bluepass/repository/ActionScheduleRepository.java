package co.bluepass.repository;

import co.bluepass.domain.Action;
import co.bluepass.domain.ActionSchedule;
import co.bluepass.domain.Club;

import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * The interface Action schedule repository.
 */
public interface ActionScheduleRepository extends JpaRepository<ActionSchedule,Long> {

    /**
     * Find by action list.
     *
     * @param action the action
     * @return the list
     */
    List<ActionSchedule> findByAction(Action action);

    /**
     * Find by club list.
     *
     * @param club the club
     * @return the list
     */
    List<ActionSchedule> findByClub(Club club);

    /**
     * Find by club page.
     *
     * @param club     the club
     * @param pageable the pageable
     * @return the page
     */
    Page<ActionSchedule> findByClub(Club club, Pageable pageable);

    /**
     * Find distinct instructor by action list.
     *
     * @param action the action
     * @return the list
     */
    List<ActionSchedule> findDistinctInstructorByAction(Action action);

    /**
     * Find by enable and schedule type not and end date greater than list.
     *
     * @param enable          the enable
     * @param scheduleType    the schedule type
     * @param endDateStandard the end date standard
     * @return the list
     */
    List<ActionSchedule> findByEnableAndScheduleTypeNotAndEndDateGreaterThan(
			boolean enable, String scheduleType, DateTime endDateStandard);

}

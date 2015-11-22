package co.bluepass.repository;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.bluepass.domain.Action;
import co.bluepass.domain.ActionSchedule;
import co.bluepass.domain.ClassSchedule;
import co.bluepass.domain.CommonCode;

/**
 * The interface Class schedule repository.
 */
public interface ClassScheduleRepository extends JpaRepository<ClassSchedule,Long> {

    /**
     * Find all page.
     *
     * @param spec     the spec
     * @param pageable the pageable
     * @return the page
     */
    Page<ClassSchedule> findAll(Specification<ClassSchedule> spec, Pageable pageable);

	/*
	@Query(nativeQuery = true,
			value = "SELECT  * FROM class_schedule cs LEFT OUTER JOIN club c on (cs.club_id = c.id)"
					+ "WHERE 1 =1 "
					+ "AND ((?1 IS NULL AND cs.start_time >= NOW()) OR DATE_FORMAT(cs.start_time, '%y%m%d') >= ?1) "
					+ "AND ((?2 IS NULL AND cs.end_time <= DATE_ADD(NOW(), INTERVAL 7 DAY)) OR DATE_FORMAT(cs.end_time, '%y%m%d') <= ?2) "
					+ "AND ((?3 IS NULL AND cs.start_time IS NOT NULL) OR TIME_FORMAT(cs.start_time, '%H%i') <= ?3) "
					+ "AND ((?4 IS NULL AND cs.end_time IS NOT NULL) OR TIME_FORMAT(cs.end_time, '%H%i') >= ?4) "
					+ "AND ((?5 IS NULL AND cs.category_id IS NOT NULL) OR cs.category_id = ?5) "
					+ "AND ((?6 IS NULL AND cs.club_id IS NOT NULL) OR cs.club_id = ?6) "
					+ "AND ((?7 IS NULL AND cs.instructor_id IS NOT NULL) OR cs.instructor_id = ?7) "
					+ "AND ((?8 IS NULL AND cs.action_id IS NOT NULL) OR cs.action_id = ?8) "
					+ "AND (((?9 IS NULL AND c.address1 IS NOT NULL) OR c.address1 LIKE CONCAT('%',?9)) AND ((?10 IS NULL AND c.address2 IS NOT NULL) OR c.address2 LIKE CONCAT(?10,'%'))) "
					+ "ORDER BY cs.start_time"
			)
	List<ClassSchedule> find(String startDate, String endDate, String startTime, String endTime, Long categoryId, Long clubId, Long instructorId, Long actionId, String address1, String address2);


	@Query(nativeQuery = true,
			value = "SELECT  * FROM class_schedule cs "
					+ "WHERE 1 =1 "
					+ "AND ((?1 IS NULL AND cs.start_time >= NOW()) OR DATE_FORMAT(cs.start_time, '%y%m%d') >= ?1) "
					+ "AND ((?2 IS NULL AND cs.end_time <= DATE_ADD(NOW(), INTERVAL 7 DAY)) OR DATE_FORMAT(cs.end_time, '%y%m%d') <= ?2) "
					+ "AND ((?3 IS NULL AND cs.start_time IS NOT NULL) OR TIME_FORMAT(cs.start_time, '%H%i') <= ?3) "
					+ "AND ((?4 IS NULL AND cs.end_time IS NOT NULL) OR TIME_FORMAT(cs.end_time, '%H%i') >= ?4) "
					+ "AND ((?5 IS NULL AND cs.category_id IS NOT NULL) OR cs.category_id = ?5) "
					+ "AND ((?6 IS NULL AND cs.club_id IS NOT NULL) OR cs.club_id = ?6) "
					+ "AND ((?7 IS NULL AND cs.instructor_id IS NOT NULL) OR cs.instructor_id = ?7) "
					+ "AND ((?8 IS NULL AND cs.action_id IS NOT NULL) OR cs.action_id = ?8) "
					+ "ORDER BY cs.start_time"
					+ "LIMIT ?9, ?10"
			)
	List<ClassSchedule> find(String startDate, String endDate, String startTime, String endTime, Long categoryId, Long clubId, Long instructorId, Long actionId, Integer offset, Integer limit);
	*/

    /**
     * Find distinct action by start time greater than equal order by start time asc list.
     *
     * @param startTime the start time
     * @return the list
     */
    List<ClassSchedule> findDistinctActionByStartTimeGreaterThanEqualOrderByStartTimeAsc(DateTime startTime);

    /**
     * Find distinct action by start time greater than equal page.
     *
     * @param startTime the start time
     * @param pageable  the pageable
     * @return the page
     */
    Page<ClassSchedule> findDistinctActionByStartTimeGreaterThanEqual(DateTime startTime, Pageable pageable);

    /**
     * Find distinct action by category and start time greater than equal order by start time asc list.
     *
     * @param category  the category
     * @param startTime the start time
     * @return the list
     */
    List<ClassSchedule> findDistinctActionByCategoryAndStartTimeGreaterThanEqualOrderByStartTimeAsc(CommonCode category, DateTime startTime);

    /**
     * Find distinct action by category and start time greater than equal page.
     *
     * @param category  the category
     * @param startTime the start time
     * @param pageable  the pageable
     * @return the page
     */
    Page<ClassSchedule> findDistinctActionByCategoryAndStartTimeGreaterThanEqual(CommonCode category, DateTime startTime, Pageable pageable);

    /**
     * Find distinct instructor by action list.
     *
     * @param action the action
     * @return the list
     */
    List<ClassSchedule> findDistinctInstructorByAction(Action action);

    /**
     * Find by action schedule list.
     *
     * @param actionSchedule the action schedule
     * @return the list
     */
    List<ClassSchedule> findByActionSchedule(ActionSchedule actionSchedule);

    /**
     * Find by start time greater than and start time less than and finished list.
     *
     * @param now             the now
     * @param prepareStandard the prepare standard
     * @param b               the b
     * @return the list
     */
    List<ClassSchedule> findByStartTimeGreaterThanAndStartTimeLessThanAndFinished(DateTime now, DateTime prepareStandard, boolean b);

    /**
     * Find by finished not and start time less than list.
     *
     * @param finished      the finished
     * @param closeStandard the close standard
     * @return the list
     */
    List<ClassSchedule> findByFinishedNotAndStartTimeLessThan(boolean finished, DateTime closeStandard);

    /**
     * Find reach close time list.
     *
     * @return the list
     */
    @Query(nativeQuery = true,
			value = "SELECT cs.* FROM CLASS_SCHEDULE cs, CLUB c "
					+ "WHERE cs.club_id = c.id "
					+ "AND NOT cs.finished "
					+ "AND ( "
					+ "			(c.reservation_close = 0 AND cs.start_time <= DATE_ADD(NOW(), INTERVAL 1 HOUR))"
					+ "			OR"
					+ "			(c.reservation_close = 1 AND cs.start_time <= DATE_ADD(NOW(), INTERVAL 3 HOUR))"
					+ "			OR"
					+ "			(c.reservation_close = 2 AND DATE_FORMAT(cs.start_time,'%Y-%m-%d') = DATE_FORMAT(NOW(),'%Y-%m-%d'))"
					+ ")"
			)
	List<ClassSchedule> findReachCloseTime();

    /**
     * Find top 1 by action schedule and start time and end time class schedule.
     *
     * @param actionSchedule the action schedule
     * @param startTime      the start time
     * @param endTime        the end time
     * @return the class schedule
     */
    ClassSchedule findTop1ByActionScheduleAndStartTimeAndEndTime(ActionSchedule actionSchedule, DateTime startTime, DateTime endTime);

    /**
     * Count by action int.
     *
     * @param action the action
     * @return the int
     */
    int countByAction(Action action);

}

package co.bluepass.repository;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.bluepass.domain.Action;
import co.bluepass.domain.ClassSchedule;
import co.bluepass.domain.Club;
import co.bluepass.domain.Reservation;
import co.bluepass.domain.User;

/**
 * The interface Reservation repository.
 */
public interface ReservationRepository extends JpaRepository<Reservation,Long> {

    /**
     * Find all for current user list.
     *
     * @return the list
     */
    @Query("select reservation from Reservation reservation where reservation.user.email = ?#{principal.email}")
    List<Reservation> findAllForCurrentUser();

    /**
     * Find by club list.
     *
     * @param club the club
     * @return the list
     */
    List<Reservation> findByClub(Club club);

    /**
     * Find one by user and class schedule reservation.
     *
     * @param user          the user
     * @param classSchedule the class schedule
     * @return the reservation
     */
    Reservation findOneByUserAndClassSchedule(User user, ClassSchedule classSchedule);

    /**
     * Find one by class schedule reservation.
     *
     * @param classSchedule the class schedule
     * @return the reservation
     */
    Reservation findOneByClassSchedule(ClassSchedule classSchedule);

    /**
     * Count by class schedule int.
     *
     * @param classSchedule the class schedule
     * @return the int
     */
    int countByClassSchedule(ClassSchedule classSchedule);

    /**
     * Find all page.
     *
     * @param spec     the spec
     * @param pageable the pageable
     * @return the page
     */
    Page<Reservation> findAll(Specification<Reservation> spec, Pageable pageable);

    /**
     * Find by class schedule list.
     *
     * @param classSchedule the class schedule
     * @return the list
     */
    List<Reservation> findByClassSchedule(ClassSchedule classSchedule);

    /**
     * Find by club and used list.
     *
     * @param club the club
     * @param used the used
     * @return the list
     */
    List<Reservation> findByClubAndUsed(Club club, Boolean used);

    /**
     * Count integer.
     *
     * @param spec the spec
     * @return the integer
     */
    Integer count(Specification<Reservation> spec);

    /**
     * Count by class schedule and canceled int.
     *
     * @param schedule the schedule
     * @param canceled the canceled
     * @return the int
     */
    int countByClassScheduleAndCanceled(ClassSchedule schedule, boolean canceled);

    /**
     * Find by canceled and class schedule in list.
     *
     * @param canceled  the canceled
     * @param schedules the schedules
     * @return the list
     */
    List<Reservation> findByCanceledAndClassScheduleIn(boolean canceled, List<ClassSchedule> schedules);

    /**
     * Count by user and start time between int.
     *
     * @param user           the user
     * @param usersDateStart the users date start
     * @param usersDateEnd   the users date end
     * @return the int
     */
    int countByUserAndStartTimeBetween(User user, DateTime usersDateStart, DateTime usersDateEnd);

    /**
     * Find by canceled and used and class schedule in list.
     *
     * @param b         the b
     * @param c         the c
     * @param schedules the schedules
     * @return the list
     */
    List<Reservation> findByCanceledAndUsedAndClassScheduleIn(boolean b, boolean c, List<ClassSchedule> schedules);

    /**
     * Find one by user and class schedule and canceled reservation.
     *
     * @param user          the user
     * @param classSchedule the class schedule
     * @param b             the b
     * @return the reservation
     */
    Reservation findOneByUserAndClassScheduleAndCanceled(User user, ClassSchedule classSchedule, boolean b);

    /**
     * Find by class schedule and canceled or class schedule and canceled is null list.
     *
     * @param cs       the cs
     * @param canceled the canceled
     * @param cs2      the cs 2
     * @return the list
     */
    List<Reservation> findByClassScheduleAndCanceledOrClassScheduleAndCanceledIsNull(ClassSchedule cs, boolean canceled, ClassSchedule cs2);

    /**
     * Find by club and used and start time between list.
     *
     * @param club           the club
     * @param b              the b
     * @param monthStartDate the month start date
     * @param monthEndDate   the month end date
     * @return the list
     */
    List<Reservation> findByClubAndUsedAndStartTimeBetween(Club club, boolean b, DateTime monthStartDate, DateTime monthEndDate);

    /**
     * Count by user and start time between and canceled int.
     *
     * @param user           the user
     * @param usersDateStart the users date start
     * @param usersDateEnd   the users date end
     * @param canceled       the canceled
     * @return the int
     */
    int countByUserAndStartTimeBetweenAndCanceled(User user, DateTime usersDateStart, DateTime usersDateEnd, boolean canceled);

    /**
     * Count by user and action and used int.
     *
     * @param user   the user
     * @param action the action
     * @param used   the used
     * @return the int
     */
    int countByUserAndActionAndUsed(User user, Action action, boolean used);

    /**
     * Count by user and action and used and start time between integer.
     *
     * @param user           the user
     * @param action         the action
     * @param used           the used
     * @param monthStartDate the month start date
     * @param monthEndDate   the month end date
     * @return the integer
     */
    Integer countByUserAndActionAndUsedAndStartTimeBetween(User user, Action action, boolean used, DateTime monthStartDate, DateTime monthEndDate);

    /**
     * Find by action and used list.
     *
     * @param action the action
     * @param used   the used
     * @return the list
     */
    List<Reservation> findByActionAndUsed(Action action, boolean used);

    /**
     * Find by action and used and start time between list.
     *
     * @param action         the action
     * @param used           the used
     * @param monthStartDate the month start date
     * @param monthEndDate   the month end date
     * @return the list
     */
    List<Reservation> findByActionAndUsedAndStartTimeBetween(Action action, boolean used, DateTime monthStartDate, DateTime monthEndDate);

    /**
     * Find by canceled and used and start time greater than and class schedule in list.
     *
     * @param b         the b
     * @param c         the c
     * @param now       the now
     * @param schedules the schedules
     * @return the list
     */
    List<Reservation> findByCanceledAndUsedAndStartTimeGreaterThanAndClassScheduleIn(boolean b, boolean c, DateTime now, List<ClassSchedule> schedules);

    /**
     * Find by class schedule in list.
     *
     * @param schedules the schedules
     * @return the list
     */
    List<Reservation> findByClassScheduleIn(List<ClassSchedule> schedules);

}

package co.bluepass.repository;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;

import co.bluepass.domain.Club;
import co.bluepass.domain.ReservationHistory;

/**
 * The interface Reservation history repository.
 */
public interface ReservationHistoryRepository extends JpaRepository<ReservationHistory,Long> {

    /**
     * Find by action id and used list.
     *
     * @param actionId the action id
     * @param used     the used
     * @return the list
     */
    List<ReservationHistory> findByActionIdAndUsed(Long actionId, boolean used);

    /**
     * Find by action id and used and start time between list.
     *
     * @param id             the id
     * @param used           the used
     * @param monthStartDate the month start date
     * @param monthEndDate   the month end date
     * @return the list
     */
    List<ReservationHistory> findByActionIdAndUsedAndStartTimeBetween(
			Long id, boolean used, DateTime monthStartDate, DateTime monthEndDate);

    /**
     * Find by club id and used list.
     *
     * @param clubId the club id
     * @param used   the used
     * @return the list
     */
    List<ReservationHistory> findByClubIdAndUsed(Long clubId, boolean used);

    /**
     * Find by club id and used and start time between list.
     *
     * @param id             the id
     * @param used           the used
     * @param monthStartDate the month start date
     * @param monthEndDate   the month end date
     * @return the list
     */
    List<ReservationHistory> findByClubIdAndUsedAndStartTimeBetween(
			Long id, boolean used, DateTime monthStartDate, DateTime monthEndDate);

    /**
     * Find one by reservation id reservation history.
     *
     * @param id the id
     * @return the reservation history
     */
    ReservationHistory findOneByReservationId(Long id);

    /**
     * Find by user id list.
     *
     * @param userId the user id
     * @return the list
     */
    List<ReservationHistory> findByUserId(Long userId);

    /**
     * Find by user id and used and canceled list.
     *
     * @param id       the id
     * @param used     the used
     * @param canceled the canceled
     * @return the list
     */
    List<ReservationHistory> findByUserIdAndUsedAndCanceled(Long id, Boolean used, Boolean canceled);

    /**
     * Find by user id and used list.
     *
     * @param id   the id
     * @param used the used
     * @return the list
     */
    List<ReservationHistory> findByUserIdAndUsed(Long id, Boolean used);

    /**
     * Find by user id and canceled list.
     *
     * @param id       the id
     * @param canceled the canceled
     * @return the list
     */
    List<ReservationHistory> findByUserIdAndCanceled(Long id, Boolean canceled);

}

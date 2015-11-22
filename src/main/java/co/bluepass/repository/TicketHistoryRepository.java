package co.bluepass.repository;

import co.bluepass.domain.TicketHistory;
import co.bluepass.domain.User;

import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * The interface Ticket history repository.
 */
public interface TicketHistoryRepository extends JpaRepository<TicketHistory,Long> {

    /**
     * Find all for current user list.
     *
     * @return the list
     */
    @Query("select ticketHistory from TicketHistory ticketHistory where ticketHistory.user.email = ?#{principal.username}")
    List<TicketHistory> findAllForCurrentUser();

    /**
     * Find by activated date less than list.
     *
     * @param expiredStandard the expired standard
     * @return the list
     */
    List<TicketHistory> findByActivatedDateLessThan(DateTime expiredStandard);

    /**
     * Count by user and closed int.
     *
     * @param user   the user
     * @param closed the closed
     * @return the int
     */
    int countByUserAndClosed(User user, boolean closed);

    /**
     * Find top 1 by user and activated and closed ticket history.
     *
     * @param user      the user
     * @param activated the activated
     * @param closed    the closed
     * @return the ticket history
     */
    TicketHistory findTop1ByUserAndActivatedAndClosed(User user, boolean activated, boolean closed);

}

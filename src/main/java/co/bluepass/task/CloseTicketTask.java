package co.bluepass.task;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import co.bluepass.domain.Authority;
import co.bluepass.domain.TicketHistory;
import co.bluepass.domain.User;
import co.bluepass.repository.AuthorityRepository;
import co.bluepass.repository.TicketHistoryRepository;
import co.bluepass.repository.UserRepository;
import co.bluepass.security.AuthoritiesConstants;

/**
 * The type Close ticket task.
 */
@Component
public class CloseTicketTask {

	private final Logger log = LoggerFactory.getLogger(CloseTicketTask.class);

    @Inject
    private TicketHistoryRepository ticketHistoryRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private AuthorityRepository authorityRepository;

    /**
     * Check ticket date.
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void checkTicketDate() {
		log.info("checkTicketDate start");

		DateTime expiredStandard = DateTime.now().minusDays(30);
		List<TicketHistory> expiredTickets = ticketHistoryRepository.findByActivatedDateLessThan(expiredStandard);
		log.debug("expiredTickets size = {}", expiredTickets.size());

		if(expiredTickets == null || expiredTickets.isEmpty()) {
			return;
		}

		Set<User> ticketOwners = new HashSet<User>();

		for (TicketHistory ticketHistory : expiredTickets) {
			ticketHistory.setClosed(true);
			ticketHistory.setCloseDate(DateTime.now());
			ticketOwners.add(ticketHistory.getUser());
		}

		ticketHistoryRepository.save(expiredTickets);

		if(ticketOwners.isEmpty()) {
			return;
		}

		for (User user : ticketOwners) {
			if(ticketHistoryRepository.countByUserAndClosed(user, false) <= 0) {
				Authority authority = authorityRepository.findOne(AuthoritiesConstants.REGISTER);
				user.getAuthorities().remove(authority);
				userRepository.saveAndFlush(user);
			}
		}
    }
}

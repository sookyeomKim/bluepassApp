package co.bluepass.task;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import co.bluepass.domain.ClassSchedule;
import co.bluepass.domain.Reservation;
import co.bluepass.domain.ReservationHistory;
import co.bluepass.repository.ClassScheduleRepository;
import co.bluepass.repository.ReservationHistoryRepository;
import co.bluepass.repository.ReservationRepository;

/**
 * The type Temp tasks.
 */
@Component
public class TempTasks {

	private final Logger log = LoggerFactory.getLogger(TempTasks.class);

	@Inject
    private ReservationRepository reservationRepository;

	@Inject
    private ReservationHistoryRepository reservationHistoryRepository;

    /**
     * Update reservation history.
     */
    @Scheduled(fixedDelay = 1000 * 60 * 24 * 365)
    public void updateReservationHistory() {
		log.info("updateReservationHistory start");

		if(reservationHistoryRepository.count() <= 0 ){
			List<Reservation> reservations = reservationRepository.findAll();
			List<ReservationHistory> histories = new ArrayList<>();
			for (Reservation reservation : reservations) {
				histories.add(new ReservationHistory(reservation));
			}

			reservationHistoryRepository.save(histories);
		}
    }
}

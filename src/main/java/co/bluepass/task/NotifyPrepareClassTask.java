package co.bluepass.task;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import co.bluepass.domain.Authority;
import co.bluepass.domain.ClassSchedule;
import co.bluepass.domain.TicketHistory;
import co.bluepass.domain.User;
import co.bluepass.proxy.SmsService;
import co.bluepass.repository.AuthorityRepository;
import co.bluepass.repository.ClassScheduleRepository;
import co.bluepass.repository.ReservationRepository;
import co.bluepass.repository.TicketHistoryRepository;
import co.bluepass.repository.UserRepository;
import co.bluepass.security.AuthoritiesConstants;

/**
 * The type Notify prepare class task.
 */
@Component
public class NotifyPrepareClassTask {

	private final Logger log = LoggerFactory.getLogger(NotifyPrepareClassTask.class);

    @Inject
    private ClassScheduleRepository classScheduleRepository;

    @Inject
    private ReservationRepository reservationRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private AuthorityRepository authorityRepository;

    /**
     * Check near schedule.
     */
    @Scheduled(fixedDelay = 1000 * 60 * 10)
    public void checkNearSchedule() {
		log.info("checkNearSchedule start");
		//3시간 후 수업이 시작되는 클래스 검색
		DateTime prepareStandard = DateTime.now().plusHours(3).plusMinutes(1);
		List<ClassSchedule> schedules  =
				classScheduleRepository.findByStartTimeGreaterThanAndStartTimeLessThanAndFinished(
						DateTime.now(),
						prepareStandard,
						false
					);

		log.debug("near schedules size = {}", schedules.size());

		if(schedules == null || schedules.isEmpty()) {
			return;
		}

    	DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy/MM/dd HH:mm");
    	StringBuffer sb = null;

		for (ClassSchedule schedule : schedules) {
			//클럽의 noti type이 0인 경우 알림
			if(schedule.getClub() != null && "0".equals(schedule.getClub().getNotificationType())){
				int reservationAmount = reservationRepository.countByClassScheduleAndCanceled(schedule, false);
				//예약건수가 1건 이상이고 연락처가 있는 경우 알림
				if(reservationAmount > 0 && StringUtils.isNotEmpty(schedule.getClub().getManagerMobile())) {
					sb = new StringBuffer("[블루패스]");
					sb.append(schedule.getStartTime().toString(fmt)).append(" ")
					.append(schedule.getClub().getName()).append(" ")
					.append(schedule.getAction().getTitle()).append(" ")
					.append("클래스에 ").append(reservationAmount).append("명이 방문할 예정입니다.");
					SmsService.sendSms(sb.toString(), schedule.getClub().getManagerMobile(), schedule.getClub().getName());
				}
			}
		}
    }
}

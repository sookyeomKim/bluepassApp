package co.bluepass.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.DurationFieldType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.bluepass.domain.ActionSchedule;
import co.bluepass.domain.ClassSchedule;
import co.bluepass.repository.ClassScheduleRepository;

/**
 * The type Schedule service.
 */
@Service
@Transactional
public class ScheduleService {

	private final Logger log = LoggerFactory.getLogger(ScheduleService.class);

    @Inject
    private ClassScheduleRepository classScheduleRepository;

    /**
     * Create class schedules.
     *
     * @param saved the saved
     */
    public void createClassSchedules(ActionSchedule saved) {
		DateTime startDate = saved.getStartDate();
		DateTime endDate = saved.getEndDate();
		DateTime dayClassEndTime = saved.getStartDate();

		if(saved.getStartDate() == null) {
			dayClassEndTime = startDate = DateTime.now().withTime(0, 0, 0, 0);
		}

		//1970-01-01T12:59:00.000Z
		int startHour = Integer.parseInt(saved.getStartTime().substring(11, 13));
		int startMinutes = Integer.parseInt(saved.getStartTime().substring(14, 16));
		startDate = startDate.plusHours(startHour).plusMinutes(startMinutes);

		int endtHour = Integer.parseInt(saved.getEndTime().substring(11, 13));
		int endtMinutes = Integer.parseInt(saved.getEndTime().substring(14, 16));
		int days = 365;

		if("하루만".equals(saved.getScheduleType())) {
			endDate = saved.getStartDate().plusHours(endtHour).plusMinutes(endtMinutes);

			ClassSchedule schedule = new ClassSchedule(startDate, endDate, saved);
			classScheduleRepository.save(schedule);
		} else {
			if(saved.getEndDate() != null) {
				endDate = endDate.plusHours(endtHour).plusMinutes(endtMinutes);
				days = Days.daysBetween(startDate, endDate).getDays() + 1;
			}

			dayClassEndTime = dayClassEndTime.plusHours(endtHour).plusMinutes(endtMinutes);

			ClassSchedule schedule = null;
			List<ClassSchedule> schedules = new ArrayList<ClassSchedule>();

			for (int i=0; i < days; i++) {
				if(schedules.size() >= 10){
					break;
				}

				DateTime dayStart = startDate.withFieldAdded(DurationFieldType.days(), i);

				if(dayStart.isBefore(DateTime.now())){
					continue;
				}

				log.debug("date= {}, dayOfWeek = {}", dayStart, dayStart.getDayOfWeek());
				if(checkEqualsDayOfWeek(dayStart.getDayOfWeek(), saved.getDay())){
					DateTime dayEnd = dayClassEndTime.withFieldAdded(DurationFieldType.days(), i);
					schedule = classScheduleRepository.findTop1ByActionScheduleAndStartTimeAndEndTime(saved, dayStart, dayEnd );
					if(schedule != null){
						schedule.update(dayStart, dayEnd, saved);
					}else{
						schedule = new ClassSchedule(dayStart, dayEnd, saved);
					}
					schedules.add(schedule);

					try {
						classScheduleRepository.save(schedule);
					} catch(DataIntegrityViolationException dive) {
						log.error("dup key...");
                    }
				}

			}

			//classScheduleRepository.save(schedules);
		}
	}

    private boolean checkEqualsDayOfWeek(int dayOfWeek, String day) {
    	boolean result = false;
    	String[] days= {"월", "화", "수", "목", "금", "토", "일"};
    	String[] d = day.split(",");
    	for (String item : d) {
    		result = item.equals(days[dayOfWeek - 1]);
    		if(result){
    			break;
    		}
		}
    	return result;
	}

}

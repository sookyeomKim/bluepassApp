package co.bluepass.task;

import java.util.List;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import co.bluepass.domain.ActionSchedule;
import co.bluepass.domain.ClassSchedule;
import co.bluepass.repository.ActionScheduleRepository;
import co.bluepass.repository.ClassScheduleRepository;
import co.bluepass.service.ScheduleService;

/**
 * The type Create class task.
 */
@Component
public class CreateClassTask {

	private final Logger log = LoggerFactory.getLogger(CreateClassTask.class);

    @Inject
    private ActionScheduleRepository actionScheduleRepository;

    @Inject
    private ClassScheduleRepository classScheduleRepository;

    @Inject
    private ScheduleService scheduleService;

    /**
     * Create next class schedules.
     */
//@Scheduled(fixedDelay = 1000 * 60 * 30)
    @Scheduled(cron = "0 1 0 * * ?")
    public void createNextClassSchedules() {
		log.info("createNextClassSchedules start");

		DateTime endDateStandard = DateTime.now().plusDays(1);
		List<ActionSchedule> activeActionSchedules =
				actionScheduleRepository
				.findByEnableAndScheduleTypeNotAndEndDateGreaterThan(true, "하루만", endDateStandard);

		if(activeActionSchedules == null || activeActionSchedules.isEmpty()){
			return;
		}

		for (ActionSchedule actionSchedule : activeActionSchedules) {
			scheduleService.createClassSchedules(actionSchedule);
		}

		List<ClassSchedule> closeClasses = classScheduleRepository.findReachCloseTime();
		log.debug("closeClasses size = {}", closeClasses.size());

		if(closeClasses == null || closeClasses.isEmpty()) {
			return;
		}

		for (ClassSchedule classSchedule : closeClasses) {
			classSchedule.setFinished(true);
		}

		classScheduleRepository.save(closeClasses);
    }
}

package co.bluepass.task;

import java.util.List;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import co.bluepass.domain.ClassSchedule;
import co.bluepass.repository.ClassScheduleRepository;

/**
 * The type Close class task.
 */
@Component
public class CloseClassTask {

	private final Logger log = LoggerFactory.getLogger(CloseClassTask.class);

    @Inject
    private ClassScheduleRepository classScheduleRepository;

    /**
     * Check class start time.
     */
    @Scheduled(fixedDelay = 1000 * 60 * 30)
    public void checkClassStartTime() {
		log.info("checkClassStartTime start");

		//DateTime closeStandard = DateTime.now();
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

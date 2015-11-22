package co.bluepass.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import co.bluepass.task.CloseTicketTask;

/**
 * The type Scheduling task configuration.
 */
@Configuration
@EnableScheduling
public class SchedulingTaskConfiguration {

    private final Logger log = LoggerFactory.getLogger(SchedulingTaskConfiguration.class);

    /**
     * Close ticket close ticket task.
     *
     * @return the close ticket task
     */
    @Bean
    public CloseTicketTask closeTicket() {
    	return new CloseTicketTask();
    }

}

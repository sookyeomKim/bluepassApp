package co.bluepass.web.rest;

import co.bluepass.Application;
import co.bluepass.domain.ActionSchedule;
import co.bluepass.repository.ActionScheduleRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ActionScheduleResource REST controller.
 *
 * @see ActionScheduleResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ActionScheduleResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private static final String DEFAULT_DAY = "M";
    private static final String UPDATED_DAY = "T";

    private static final String DEFAULT_START_TIME = "23:30";
    private static final String UPDATED_START_TIME = "11:00";
    private static final String DEFAULT_START_TIME_STR = DEFAULT_START_TIME;

    private static final String DEFAULT_END_TIME = "23:50";
    private static final String UPDATED_END_TIME = "13:00";
    private static final String DEFAULT_END_TIME_STR = DEFAULT_END_TIME;

    private static final DateTime DEFAULT_START_DATE = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_START_DATE = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_START_DATE_STR = dateTimeFormatter.print(DEFAULT_START_DATE);

    private static final DateTime DEFAULT_END_DATE = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_END_DATE = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_END_DATE_STR = dateTimeFormatter.print(DEFAULT_END_DATE);
    private static final String DEFAULT_SCHEDULE_TYPE = "SAMPLE_TEXT";
    private static final String UPDATED_SCHEDULE_TYPE = "UPDATED_TEXT";

    @Inject
    private ActionScheduleRepository actionScheduleRepository;

    private MockMvc restActionScheduleMockMvc;

    private ActionSchedule actionSchedule;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ActionScheduleResource actionScheduleResource = new ActionScheduleResource();
        ReflectionTestUtils.setField(actionScheduleResource, "actionScheduleRepository", actionScheduleRepository);
        this.restActionScheduleMockMvc = MockMvcBuilders.standaloneSetup(actionScheduleResource).build();
    }

    @Before
    public void initTest() {
        actionSchedule = new ActionSchedule();
        actionSchedule.setDay(DEFAULT_DAY);
        actionSchedule.setStartTime(DEFAULT_START_TIME);
        actionSchedule.setEndTime(DEFAULT_END_TIME);
        actionSchedule.setStartDate(DEFAULT_START_DATE);
        actionSchedule.setEndDate(DEFAULT_END_DATE);
        actionSchedule.setScheduleType(DEFAULT_SCHEDULE_TYPE);
    }

    /*@Test
    @Transactional
    public void createActionSchedule() throws Exception {
        int databaseSizeBeforeCreate = actionScheduleRepository.findAll().size();

        // Create the ActionSchedule
        restActionScheduleMockMvc.perform(post("/api/actionSchedules")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(actionSchedule)))
                .andExpect(status().isCreated());

        // Validate the ActionSchedule in the database
        List<ActionSchedule> actionSchedules = actionScheduleRepository.findAll();
        assertThat(actionSchedules).hasSize(databaseSizeBeforeCreate + 1);
        ActionSchedule testActionSchedule = actionSchedules.get(actionSchedules.size() - 1);
        assertThat(testActionSchedule.getDay()).isEqualTo(DEFAULT_DAY);
        assertThat(testActionSchedule.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testActionSchedule.getEndTime()).isEqualTo(DEFAULT_END_TIME);
        assertThat(testActionSchedule.getStartDate().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_START_DATE);
        assertThat(testActionSchedule.getEndDate().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_END_DATE);
        assertThat(testActionSchedule.getScheduleType()).isEqualTo(DEFAULT_SCHEDULE_TYPE);
    }*/

    /*@Test
    @Transactional
    public void checkDayIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(actionScheduleRepository.findAll()).hasSize(0);
        // set the field null
        actionSchedule.setDay(null);

        // Create the ActionSchedule, which fails.
        restActionScheduleMockMvc.perform(post("/api/actionSchedules")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(actionSchedule)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<ActionSchedule> actionSchedules = actionScheduleRepository.findAll();
        assertThat(actionSchedules).hasSize(0);
    }*/

    /*@Test
    @Transactional
    public void checkStartTimeIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(actionScheduleRepository.findAll()).hasSize(0);
        // set the field null
        actionSchedule.setStartTime(null);

        // Create the ActionSchedule, which fails.
        restActionScheduleMockMvc.perform(post("/api/actionSchedules")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(actionSchedule)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<ActionSchedule> actionSchedules = actionScheduleRepository.findAll();
        assertThat(actionSchedules).hasSize(0);
    }*/

    /*@Test
    @Transactional
    public void checkEndTimeIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(actionScheduleRepository.findAll()).hasSize(0);
        // set the field null
        actionSchedule.setEndTime(null);

        // Create the ActionSchedule, which fails.
        restActionScheduleMockMvc.perform(post("/api/actionSchedules")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(actionSchedule)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<ActionSchedule> actionSchedules = actionScheduleRepository.findAll();
        assertThat(actionSchedules).hasSize(0);
    }*/

    /*@Test
    @Transactional
    public void checkScheduleTypeIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(actionScheduleRepository.findAll()).hasSize(0);
        // set the field null
        actionSchedule.setScheduleType(null);

        // Create the ActionSchedule, which fails.
        restActionScheduleMockMvc.perform(post("/api/actionSchedules")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(actionSchedule)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<ActionSchedule> actionSchedules = actionScheduleRepository.findAll();
        assertThat(actionSchedules).hasSize(0);
    }*/

    @Test
    @Transactional
    public void getAllActionSchedules() throws Exception {
        // Initialize the database
        actionScheduleRepository.saveAndFlush(actionSchedule);

        // Get all the actionSchedules
        restActionScheduleMockMvc.perform(get("/api/actionSchedules"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(actionSchedule.getId().intValue())))
                .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY.toString())))
                .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME_STR)))
                .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME_STR)))
                .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE_STR)))
                .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE_STR)))
                .andExpect(jsonPath("$.[*].scheduleType").value(hasItem(DEFAULT_SCHEDULE_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getActionSchedule() throws Exception {
        // Initialize the database
        actionScheduleRepository.saveAndFlush(actionSchedule);

        // Get the actionSchedule
        restActionScheduleMockMvc.perform(get("/api/actionSchedules/{id}", actionSchedule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(actionSchedule.getId().intValue()))
            .andExpect(jsonPath("$.day").value(DEFAULT_DAY.toString()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME_STR))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME_STR))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE_STR))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE_STR))
            .andExpect(jsonPath("$.scheduleType").value(DEFAULT_SCHEDULE_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingActionSchedule() throws Exception {
        // Get the actionSchedule
        restActionScheduleMockMvc.perform(get("/api/actionSchedules/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    /*@Test
    @Transactional
    public void updateActionSchedule() throws Exception {
        // Initialize the database
        actionScheduleRepository.saveAndFlush(actionSchedule);

		int databaseSizeBeforeUpdate = actionScheduleRepository.findAll().size();

        // Update the actionSchedule
        actionSchedule.setDay(UPDATED_DAY);
        actionSchedule.setStartTime(UPDATED_START_TIME);
        actionSchedule.setEndTime(UPDATED_END_TIME);
        actionSchedule.setStartDate(UPDATED_START_DATE);
        actionSchedule.setEndDate(UPDATED_END_DATE);
        actionSchedule.setScheduleType(UPDATED_SCHEDULE_TYPE);
        restActionScheduleMockMvc.perform(put("/api/actionSchedules")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(actionSchedule)))
                .andExpect(status().isOk());

        // Validate the ActionSchedule in the database
        List<ActionSchedule> actionSchedules = actionScheduleRepository.findAll();
        assertThat(actionSchedules).hasSize(databaseSizeBeforeUpdate);
        ActionSchedule testActionSchedule = actionSchedules.get(actionSchedules.size() - 1);
        assertThat(testActionSchedule.getDay()).isEqualTo(UPDATED_DAY);
        assertThat(testActionSchedule.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testActionSchedule.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testActionSchedule.getStartDate().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_START_DATE);
        assertThat(testActionSchedule.getEndDate().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_END_DATE);
        assertThat(testActionSchedule.getScheduleType()).isEqualTo(UPDATED_SCHEDULE_TYPE);
    }*/

    /*@Test
    @Transactional
    public void deleteActionSchedule() throws Exception {
        // Initialize the database
        actionScheduleRepository.saveAndFlush(actionSchedule);

		int databaseSizeBeforeDelete = actionScheduleRepository.findAll().size();

        // Get the actionSchedule
        restActionScheduleMockMvc.perform(delete("/api/actionSchedules/{id}", actionSchedule.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ActionSchedule> actionSchedules = actionScheduleRepository.findAll();
        assertThat(actionSchedules).hasSize(databaseSizeBeforeDelete - 1);
    }*/
}

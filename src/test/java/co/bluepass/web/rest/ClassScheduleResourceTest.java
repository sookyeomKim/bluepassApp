package co.bluepass.web.rest;

import co.bluepass.Application;
import co.bluepass.domain.ClassSchedule;
import co.bluepass.repository.ClassScheduleRepository;

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
 * Test class for the ClassScheduleResource REST controller.
 *
 * @see ClassScheduleResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ClassScheduleResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");


    private static final DateTime DEFAULT_START_TIME = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_START_TIME = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_START_TIME_STR = dateTimeFormatter.print(DEFAULT_START_TIME);

    private static final DateTime DEFAULT_END_TIME = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_END_TIME = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_END_TIME_STR = dateTimeFormatter.print(DEFAULT_END_TIME);

    private static final Boolean DEFAULT_ENABLE = false;
    private static final Boolean UPDATED_ENABLE = true;

    private static final Boolean DEFAULT_FINISHED = false;
    private static final Boolean UPDATED_FINISHED = true;
    private static final String DEFAULT_ETC = "SAMPLE_TEXT";
    private static final String UPDATED_ETC = "UPDATED_TEXT";

    @Inject
    private ClassScheduleRepository classScheduleRepository;

    private MockMvc restClassScheduleMockMvc;

    private ClassSchedule classSchedule;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ClassScheduleResource classScheduleResource = new ClassScheduleResource();
        ReflectionTestUtils.setField(classScheduleResource, "classScheduleRepository", classScheduleRepository);
        this.restClassScheduleMockMvc = MockMvcBuilders.standaloneSetup(classScheduleResource).build();
    }

    @Before
    public void initTest() {
        classSchedule = new ClassSchedule();
        classSchedule.setStartTime(DEFAULT_START_TIME);
        classSchedule.setEndTime(DEFAULT_END_TIME);
        classSchedule.setEnable(DEFAULT_ENABLE);
        classSchedule.setFinished(DEFAULT_FINISHED);
        classSchedule.setEtc(DEFAULT_ETC);
    }

    /*@Test
    @Transactional
    public void createClassSchedule() throws Exception {
        int databaseSizeBeforeCreate = classScheduleRepository.findAll().size();

        // Create the ClassSchedule
        restClassScheduleMockMvc.perform(post("/api/classSchedules")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(classSchedule)))
                .andExpect(status().isCreated());

        // Validate the ClassSchedule in the database
        List<ClassSchedule> classSchedules = classScheduleRepository.findAll();
        assertThat(classSchedules).hasSize(databaseSizeBeforeCreate + 1);
        ClassSchedule testClassSchedule = classSchedules.get(classSchedules.size() - 1);
        assertThat(testClassSchedule.getStartTime().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_START_TIME);
        assertThat(testClassSchedule.getEndTime().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_END_TIME);
        assertThat(testClassSchedule.getEnable()).isEqualTo(DEFAULT_ENABLE);
        assertThat(testClassSchedule.getFinished()).isEqualTo(DEFAULT_FINISHED);
        assertThat(testClassSchedule.getEtc()).isEqualTo(DEFAULT_ETC);
    }*/

    /*@Test
    @Transactional
    public void checkStartTimeIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(classScheduleRepository.findAll()).hasSize(0);
        // set the field null
        classSchedule.setStartTime(null);

        // Create the ClassSchedule, which fails.
        restClassScheduleMockMvc.perform(post("/api/classSchedules")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(classSchedule)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<ClassSchedule> classSchedules = classScheduleRepository.findAll();
        assertThat(classSchedules).hasSize(0);
    }*/

    @Test
    @Transactional
    public void getAllClassSchedules() throws Exception {
        // Initialize the database
        classScheduleRepository.saveAndFlush(classSchedule);

        // Get all the classSchedules
        restClassScheduleMockMvc.perform(get("/api/classSchedules"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(classSchedule.getId().intValue())))
                .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME_STR)))
                .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME_STR)))
                .andExpect(jsonPath("$.[*].enable").value(hasItem(DEFAULT_ENABLE.booleanValue())))
                .andExpect(jsonPath("$.[*].finished").value(hasItem(DEFAULT_FINISHED.booleanValue())))
                .andExpect(jsonPath("$.[*].etc").value(hasItem(DEFAULT_ETC.toString())));
    }

    @Test
    @Transactional
    public void getClassSchedule() throws Exception {
        // Initialize the database
        classScheduleRepository.saveAndFlush(classSchedule);

        // Get the classSchedule
        restClassScheduleMockMvc.perform(get("/api/classSchedules/{id}", classSchedule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(classSchedule.getId().intValue()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME_STR))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME_STR))
            .andExpect(jsonPath("$.enable").value(DEFAULT_ENABLE.booleanValue()))
            .andExpect(jsonPath("$.finished").value(DEFAULT_FINISHED.booleanValue()))
            .andExpect(jsonPath("$.etc").value(DEFAULT_ETC.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingClassSchedule() throws Exception {
        // Get the classSchedule
        restClassScheduleMockMvc.perform(get("/api/classSchedules/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

   /* @Test
    @Transactional
    public void updateClassSchedule() throws Exception {
        // Initialize the database
        classScheduleRepository.saveAndFlush(classSchedule);

		int databaseSizeBeforeUpdate = classScheduleRepository.findAll().size();

        // Update the classSchedule
        classSchedule.setStartTime(UPDATED_START_TIME);
        classSchedule.setEndTime(UPDATED_END_TIME);
        classSchedule.setEnable(UPDATED_ENABLE);
        classSchedule.setFinished(UPDATED_FINISHED);
        classSchedule.setEtc(UPDATED_ETC);
        restClassScheduleMockMvc.perform(put("/api/classSchedules")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(classSchedule)))
                .andExpect(status().isOk());

        // Validate the ClassSchedule in the database
        List<ClassSchedule> classSchedules = classScheduleRepository.findAll();
        assertThat(classSchedules).hasSize(databaseSizeBeforeUpdate);
        ClassSchedule testClassSchedule = classSchedules.get(classSchedules.size() - 1);
        assertThat(testClassSchedule.getStartTime().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_START_TIME);
        assertThat(testClassSchedule.getEndTime().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_END_TIME);
        assertThat(testClassSchedule.getEnable()).isEqualTo(UPDATED_ENABLE);
        assertThat(testClassSchedule.getFinished()).isEqualTo(UPDATED_FINISHED);
        assertThat(testClassSchedule.getEtc()).isEqualTo(UPDATED_ETC);
    }*/

    /*@Test
    @Transactional
    public void deleteClassSchedule() throws Exception {
        // Initialize the database
        classScheduleRepository.saveAndFlush(classSchedule);

		int databaseSizeBeforeDelete = classScheduleRepository.findAll().size();

        // Get the classSchedule
        restClassScheduleMockMvc.perform(delete("/api/classSchedules/{id}", classSchedule.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ClassSchedule> classSchedules = classScheduleRepository.findAll();
        assertThat(classSchedules).hasSize(databaseSizeBeforeDelete - 1);
    }*/
}

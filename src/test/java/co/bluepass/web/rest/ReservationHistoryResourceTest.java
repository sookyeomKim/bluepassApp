package co.bluepass.web.rest;

import co.bluepass.Application;
import co.bluepass.domain.ReservationHistory;
import co.bluepass.repository.ReservationHistoryRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
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
 * Test class for the ReservationHistoryResource REST controller.
 *
 * @see ReservationHistoryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ReservationHistoryResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");


    private static final Long DEFAULT_RESERVATION_ID = 1L;
    private static final Long UPDATED_RESERVATION_ID = 2L;
    private static final String DEFAULT_RESERVATION_METHOD = "SAMPLE_TEXT";
    private static final String UPDATED_RESERVATION_METHOD = "UPDATED_TEXT";

    private static final DateTime DEFAULT_RESERVATION_TIME = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_RESERVATION_TIME = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_RESERVATION_TIME_STR = dateTimeFormatter.print(DEFAULT_RESERVATION_TIME);

    private static final DateTime DEFAULT_START_TIME = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_START_TIME = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_START_TIME_STR = dateTimeFormatter.print(DEFAULT_START_TIME);

    private static final DateTime DEFAULT_END_TIME = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_END_TIME = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_END_TIME_STR = dateTimeFormatter.print(DEFAULT_END_TIME);

    private static final Boolean DEFAULT_USED = false;
    private static final Boolean UPDATED_USED = true;

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;
    private static final String DEFAULT_USER_EMAIL = "SAMPLE_TEXT";
    private static final String UPDATED_USER_EMAIL = "UPDATED_TEXT";
    private static final String DEFAULT_USER_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_USER_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_USER_PHONE_NUMBER = "SAMPLE_TEXT";
    private static final String UPDATED_USER_PHONE_NUMBER = "UPDATED_TEXT";
    private static final String DEFAULT_USER_GENDER = "SAMPLE_TEXT";
    private static final String UPDATED_USER_GENDER = "UPDATED_TEXT";
    private static final String DEFAULT_REGISTER_STATUS = "SAMPLE_TEXT";
    private static final String UPDATED_REGISTER_STATUS = "UPDATED_TEXT";

    private static final Long DEFAULT_ACTION_SCHEDULE_ID = 1L;
    private static final Long UPDATED_ACTION_SCHEDULE_ID = 2L;
    private static final String DEFAULT_ACTION_SCHEDULE_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_ACTION_SCHEDULE_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_DAY = "SAMPLE_TEXT";
    private static final String UPDATED_DAY = "UPDATED_TEXT";
    private static final String DEFAULT_SCHEDULE_TYPE = "SAMPLE_TEXT";
    private static final String UPDATED_SCHEDULE_TYPE = "UPDATED_TEXT";

    private static final Integer DEFAULT_ATTENDEE_LIMIT = 1;
    private static final Integer UPDATED_ATTENDEE_LIMIT = 2;

    private static final Long DEFAULT_CLASS_SCHEDULE_ID = 1L;
    private static final Long UPDATED_CLASS_SCHEDULE_ID = 2L;
    private static final String DEFAULT_CLASS_SCHEDULE_ETC = "SAMPLE_TEXT";
    private static final String UPDATED_CLASS_SCHEDULE_ETC = "UPDATED_TEXT";

    private static final Long DEFAULT_CLUB_ID = 1L;
    private static final Long UPDATED_CLUB_ID = 2L;
    private static final String DEFAULT_CLUB_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_CLUB_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_CLUB_ZIPCODE = "SAMPLE_TEXT";
    private static final String UPDATED_CLUB_ZIPCODE = "UPDATED_TEXT";
    private static final String DEFAULT_CLUB_ADDRESS1 = "SAMPLE_TEXT";
    private static final String UPDATED_CLUB_ADDRESS1 = "UPDATED_TEXT";
    private static final String DEFAULT_CLUB_ADDRESS2 = "SAMPLE_TEXT";
    private static final String UPDATED_CLUB_ADDRESS2 = "UPDATED_TEXT";
    private static final String DEFAULT_CLUB_PHONE_NUMBER = "SAMPLE_TEXT";
    private static final String UPDATED_CLUB_PHONE_NUMBER = "UPDATED_TEXT";

    private static final Boolean DEFAULT_ONLY_FEMAIL = false;
    private static final Boolean UPDATED_ONLY_FEMAIL = true;
    private static final String DEFAULT_CLUB_MANAGER_MOBILE = "SAMPLE_TEXT";
    private static final String UPDATED_CLUB_MANAGER_MOBILE = "UPDATED_TEXT";
    private static final String DEFAULT_NOTIFICATION_TYPE = "SAMPLE_TEXT";
    private static final String UPDATED_NOTIFICATION_TYPE = "UPDATED_TEXT";
    private static final String DEFAULT_RESERVATION_CLOSE = "SAMPLE_TEXT";
    private static final String UPDATED_RESERVATION_CLOSE = "UPDATED_TEXT";

    private static final Long DEFAULT_ACTION_ID = 1L;
    private static final Long UPDATED_ACTION_ID = 2L;
    private static final String DEFAULT_ACTION_TITLE = "SAMPLE_TEXT";
    private static final String UPDATED_ACTION_TITLE = "UPDATED_TEXT";
    private static final String DEFAULT_ACTION_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_ACTION_DESCRIPTION = "UPDATED_TEXT";
    private static final String DEFAULT_ACTION_USE_LIMIT_TYPE = "SAMPLE_TEXT";
    private static final String UPDATED_ACTION_USE_LIMIT_TYPE = "UPDATED_TEXT";

    private static final Integer DEFAULT_ACTION_USE_LIMIT_VALUE = 1;
    private static final Integer UPDATED_ACTION_USE_LIMIT_VALUE = 2;

    private static final Long DEFAULT_INSTRUCTOR_ID = 1L;
    private static final Long UPDATED_INSTRUCTOR_ID = 2L;
    private static final String DEFAULT_INSTRUCTOR_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_INSTRUCTOR_NAME = "UPDATED_TEXT";

    private static final Long DEFAULT_CATEGORY_ID = 1L;
    private static final Long UPDATED_CATEGORY_ID = 2L;
    private static final String DEFAULT_CATEGORY_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_CATEGORY_NAME = "UPDATED_TEXT";

    private static final Long DEFAULT_TICKET_ID = 1L;
    private static final Long UPDATED_TICKET_ID = 2L;
    private static final String DEFAULT_TICKET_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_TICKET_NAME = "UPDATED_TEXT";

    @Inject
    private ReservationHistoryRepository reservationHistoryRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restReservationHistoryMockMvc;

    private ReservationHistory reservationHistory;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReservationHistoryResource reservationHistoryResource = new ReservationHistoryResource();
        ReflectionTestUtils.setField(reservationHistoryResource, "reservationHistoryRepository", reservationHistoryRepository);
        this.restReservationHistoryMockMvc = MockMvcBuilders.standaloneSetup(reservationHistoryResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        reservationHistory = new ReservationHistory();
        reservationHistory.setReservationId(DEFAULT_RESERVATION_ID);
        reservationHistory.setReservationMethod(DEFAULT_RESERVATION_METHOD);
        reservationHistory.setReservationTime(DEFAULT_RESERVATION_TIME);
        reservationHistory.setStartTime(DEFAULT_START_TIME);
        reservationHistory.setEndTime(DEFAULT_END_TIME);
        reservationHistory.setUsed(DEFAULT_USED);
        reservationHistory.setUserId(DEFAULT_USER_ID);
        reservationHistory.setUserEmail(DEFAULT_USER_EMAIL);
        reservationHistory.setUserName(DEFAULT_USER_NAME);
        reservationHistory.setUserPhoneNumber(DEFAULT_USER_PHONE_NUMBER);
        reservationHistory.setUserGender(DEFAULT_USER_GENDER);
        reservationHistory.setRegisterStatus(DEFAULT_REGISTER_STATUS);
        reservationHistory.setActionScheduleId(DEFAULT_ACTION_SCHEDULE_ID);
        reservationHistory.setActionScheduleName(DEFAULT_ACTION_SCHEDULE_NAME);
        reservationHistory.setDay(DEFAULT_DAY);
        reservationHistory.setScheduleType(DEFAULT_SCHEDULE_TYPE);
        reservationHistory.setAttendeeLimit(DEFAULT_ATTENDEE_LIMIT);
        reservationHistory.setClassScheduleId(DEFAULT_CLASS_SCHEDULE_ID);
        reservationHistory.setClassScheduleEtc(DEFAULT_CLASS_SCHEDULE_ETC);
        reservationHistory.setClubId(DEFAULT_CLUB_ID);
        reservationHistory.setClubName(DEFAULT_CLUB_NAME);
        reservationHistory.setClubZipcode(DEFAULT_CLUB_ZIPCODE);
        reservationHistory.setClubAddress1(DEFAULT_CLUB_ADDRESS1);
        reservationHistory.setClubAddress2(DEFAULT_CLUB_ADDRESS2);
        reservationHistory.setClubPhoneNumber(DEFAULT_CLUB_PHONE_NUMBER);
        reservationHistory.setOnlyFemail(DEFAULT_ONLY_FEMAIL);
        reservationHistory.setClubManagerMobile(DEFAULT_CLUB_MANAGER_MOBILE);
        reservationHistory.setNotificationType(DEFAULT_NOTIFICATION_TYPE);
        reservationHistory.setReservationClose(DEFAULT_RESERVATION_CLOSE);
        reservationHistory.setActionId(DEFAULT_ACTION_ID);
        reservationHistory.setActionTitle(DEFAULT_ACTION_TITLE);
        reservationHistory.setActionDescription(DEFAULT_ACTION_DESCRIPTION);
        reservationHistory.setActionUseLimitType(DEFAULT_ACTION_USE_LIMIT_TYPE);
        reservationHistory.setActionUseLimitValue(DEFAULT_ACTION_USE_LIMIT_VALUE);
        reservationHistory.setInstructorId(DEFAULT_INSTRUCTOR_ID);
        reservationHistory.setInstructorName(DEFAULT_INSTRUCTOR_NAME);
        reservationHistory.setCategoryId(DEFAULT_CATEGORY_ID);
        reservationHistory.setCategoryName(DEFAULT_CATEGORY_NAME);
        reservationHistory.setTicketId(DEFAULT_TICKET_ID);
        reservationHistory.setTicketName(DEFAULT_TICKET_NAME);
    }

    @Test
    @Transactional
    public void createReservationHistory() throws Exception {
        int databaseSizeBeforeCreate = reservationHistoryRepository.findAll().size();

        // Create the ReservationHistory

        restReservationHistoryMockMvc.perform(post("/api/reservationHistorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(reservationHistory)))
                .andExpect(status().isCreated());

        // Validate the ReservationHistory in the database
        List<ReservationHistory> reservationHistorys = reservationHistoryRepository.findAll();
        assertThat(reservationHistorys).hasSize(databaseSizeBeforeCreate + 1);
        ReservationHistory testReservationHistory = reservationHistorys.get(reservationHistorys.size() - 1);
        assertThat(testReservationHistory.getReservationId()).isEqualTo(DEFAULT_RESERVATION_ID);
        assertThat(testReservationHistory.getReservationMethod()).isEqualTo(DEFAULT_RESERVATION_METHOD);
        assertThat(testReservationHistory.getReservationTime().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_RESERVATION_TIME);
        assertThat(testReservationHistory.getStartTime().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_START_TIME);
        assertThat(testReservationHistory.getEndTime().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_END_TIME);
        assertThat(testReservationHistory.getUsed()).isEqualTo(DEFAULT_USED);
        assertThat(testReservationHistory.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testReservationHistory.getUserEmail()).isEqualTo(DEFAULT_USER_EMAIL);
        assertThat(testReservationHistory.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testReservationHistory.getUserPhoneNumber()).isEqualTo(DEFAULT_USER_PHONE_NUMBER);
        assertThat(testReservationHistory.getUserGender()).isEqualTo(DEFAULT_USER_GENDER);
        assertThat(testReservationHistory.getRegisterStatus()).isEqualTo(DEFAULT_REGISTER_STATUS);
        assertThat(testReservationHistory.getActionScheduleId()).isEqualTo(DEFAULT_ACTION_SCHEDULE_ID);
        assertThat(testReservationHistory.getActionScheduleName()).isEqualTo(DEFAULT_ACTION_SCHEDULE_NAME);
        assertThat(testReservationHistory.getDay()).isEqualTo(DEFAULT_DAY);
        assertThat(testReservationHistory.getScheduleType()).isEqualTo(DEFAULT_SCHEDULE_TYPE);
        assertThat(testReservationHistory.getAttendeeLimit()).isEqualTo(DEFAULT_ATTENDEE_LIMIT);
        assertThat(testReservationHistory.getClassScheduleId()).isEqualTo(DEFAULT_CLASS_SCHEDULE_ID);
        assertThat(testReservationHistory.getClassScheduleEtc()).isEqualTo(DEFAULT_CLASS_SCHEDULE_ETC);
        assertThat(testReservationHistory.getClubId()).isEqualTo(DEFAULT_CLUB_ID);
        assertThat(testReservationHistory.getClubName()).isEqualTo(DEFAULT_CLUB_NAME);
        assertThat(testReservationHistory.getClubZipcode()).isEqualTo(DEFAULT_CLUB_ZIPCODE);
        assertThat(testReservationHistory.getClubAddress1()).isEqualTo(DEFAULT_CLUB_ADDRESS1);
        assertThat(testReservationHistory.getClubAddress2()).isEqualTo(DEFAULT_CLUB_ADDRESS2);
        assertThat(testReservationHistory.getClubPhoneNumber()).isEqualTo(DEFAULT_CLUB_PHONE_NUMBER);
        assertThat(testReservationHistory.getOnlyFemail()).isEqualTo(DEFAULT_ONLY_FEMAIL);
        assertThat(testReservationHistory.getClubManagerMobile()).isEqualTo(DEFAULT_CLUB_MANAGER_MOBILE);
        assertThat(testReservationHistory.getNotificationType()).isEqualTo(DEFAULT_NOTIFICATION_TYPE);
        assertThat(testReservationHistory.getReservationClose()).isEqualTo(DEFAULT_RESERVATION_CLOSE);
        assertThat(testReservationHistory.getActionId()).isEqualTo(DEFAULT_ACTION_ID);
        assertThat(testReservationHistory.getActionTitle()).isEqualTo(DEFAULT_ACTION_TITLE);
        assertThat(testReservationHistory.getActionDescription()).isEqualTo(DEFAULT_ACTION_DESCRIPTION);
        assertThat(testReservationHistory.getActionUseLimitType()).isEqualTo(DEFAULT_ACTION_USE_LIMIT_TYPE);
        assertThat(testReservationHistory.getActionUseLimitValue()).isEqualTo(DEFAULT_ACTION_USE_LIMIT_VALUE);
        assertThat(testReservationHistory.getInstructorId()).isEqualTo(DEFAULT_INSTRUCTOR_ID);
        assertThat(testReservationHistory.getInstructorName()).isEqualTo(DEFAULT_INSTRUCTOR_NAME);
        assertThat(testReservationHistory.getCategoryId()).isEqualTo(DEFAULT_CATEGORY_ID);
        assertThat(testReservationHistory.getCategoryName()).isEqualTo(DEFAULT_CATEGORY_NAME);
        assertThat(testReservationHistory.getTicketId()).isEqualTo(DEFAULT_TICKET_ID);
        assertThat(testReservationHistory.getTicketName()).isEqualTo(DEFAULT_TICKET_NAME);
    }

    @Test
    @Transactional
    public void getAllReservationHistorys() throws Exception {
        // Initialize the database
        reservationHistoryRepository.saveAndFlush(reservationHistory);

        // Get all the reservationHistorys
        restReservationHistoryMockMvc.perform(get("/api/reservationHistorys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(reservationHistory.getId().intValue())))
                .andExpect(jsonPath("$.[*].reservationId").value(hasItem(DEFAULT_RESERVATION_ID.intValue())))
                .andExpect(jsonPath("$.[*].reservationMethod").value(hasItem(DEFAULT_RESERVATION_METHOD.toString())))
                .andExpect(jsonPath("$.[*].reservationTime").value(hasItem(DEFAULT_RESERVATION_TIME_STR)))
                .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME_STR)))
                .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME_STR)))
                .andExpect(jsonPath("$.[*].used").value(hasItem(DEFAULT_USED.booleanValue())))
                .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
                .andExpect(jsonPath("$.[*].userEmail").value(hasItem(DEFAULT_USER_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME.toString())))
                .andExpect(jsonPath("$.[*].userPhoneNumber").value(hasItem(DEFAULT_USER_PHONE_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].userGender").value(hasItem(DEFAULT_USER_GENDER.toString())))
                .andExpect(jsonPath("$.[*].registerStatus").value(hasItem(DEFAULT_REGISTER_STATUS.toString())))
                .andExpect(jsonPath("$.[*].actionScheduleId").value(hasItem(DEFAULT_ACTION_SCHEDULE_ID.intValue())))
                .andExpect(jsonPath("$.[*].actionScheduleName").value(hasItem(DEFAULT_ACTION_SCHEDULE_NAME.toString())))
                .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY.toString())))
                .andExpect(jsonPath("$.[*].scheduleType").value(hasItem(DEFAULT_SCHEDULE_TYPE.toString())))
                .andExpect(jsonPath("$.[*].attendeeLimit").value(hasItem(DEFAULT_ATTENDEE_LIMIT)))
                .andExpect(jsonPath("$.[*].classScheduleId").value(hasItem(DEFAULT_CLASS_SCHEDULE_ID.intValue())))
                .andExpect(jsonPath("$.[*].classScheduleEtc").value(hasItem(DEFAULT_CLASS_SCHEDULE_ETC.toString())))
                .andExpect(jsonPath("$.[*].clubId").value(hasItem(DEFAULT_CLUB_ID.intValue())))
                .andExpect(jsonPath("$.[*].clubName").value(hasItem(DEFAULT_CLUB_NAME.toString())))
                .andExpect(jsonPath("$.[*].clubZipcode").value(hasItem(DEFAULT_CLUB_ZIPCODE.toString())))
                .andExpect(jsonPath("$.[*].clubAddress1").value(hasItem(DEFAULT_CLUB_ADDRESS1.toString())))
                .andExpect(jsonPath("$.[*].clubAddress2").value(hasItem(DEFAULT_CLUB_ADDRESS2.toString())))
                .andExpect(jsonPath("$.[*].clubPhoneNumber").value(hasItem(DEFAULT_CLUB_PHONE_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].onlyFemail").value(hasItem(DEFAULT_ONLY_FEMAIL.booleanValue())))
                .andExpect(jsonPath("$.[*].clubManagerMobile").value(hasItem(DEFAULT_CLUB_MANAGER_MOBILE.toString())))
                .andExpect(jsonPath("$.[*].notificationType").value(hasItem(DEFAULT_NOTIFICATION_TYPE.toString())))
                .andExpect(jsonPath("$.[*].reservationClose").value(hasItem(DEFAULT_RESERVATION_CLOSE.toString())))
                .andExpect(jsonPath("$.[*].actionId").value(hasItem(DEFAULT_ACTION_ID.intValue())))
                .andExpect(jsonPath("$.[*].actionTitle").value(hasItem(DEFAULT_ACTION_TITLE.toString())))
                .andExpect(jsonPath("$.[*].actionDescription").value(hasItem(DEFAULT_ACTION_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].actionUseLimitType").value(hasItem(DEFAULT_ACTION_USE_LIMIT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].actionUseLimitValue").value(hasItem(DEFAULT_ACTION_USE_LIMIT_VALUE)))
                .andExpect(jsonPath("$.[*].instructorId").value(hasItem(DEFAULT_INSTRUCTOR_ID.intValue())))
                .andExpect(jsonPath("$.[*].instructorName").value(hasItem(DEFAULT_INSTRUCTOR_NAME.toString())))
                .andExpect(jsonPath("$.[*].categoryId").value(hasItem(DEFAULT_CATEGORY_ID.intValue())))
                .andExpect(jsonPath("$.[*].categoryName").value(hasItem(DEFAULT_CATEGORY_NAME.toString())))
                .andExpect(jsonPath("$.[*].ticketId").value(hasItem(DEFAULT_TICKET_ID.intValue())))
                .andExpect(jsonPath("$.[*].ticketName").value(hasItem(DEFAULT_TICKET_NAME.toString())));
    }

    @Test
    @Transactional
    public void getReservationHistory() throws Exception {
        // Initialize the database
        reservationHistoryRepository.saveAndFlush(reservationHistory);

        // Get the reservationHistory
        restReservationHistoryMockMvc.perform(get("/api/reservationHistorys/{id}", reservationHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(reservationHistory.getId().intValue()))
            .andExpect(jsonPath("$.reservationId").value(DEFAULT_RESERVATION_ID.intValue()))
            .andExpect(jsonPath("$.reservationMethod").value(DEFAULT_RESERVATION_METHOD.toString()))
            .andExpect(jsonPath("$.reservationTime").value(DEFAULT_RESERVATION_TIME_STR))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME_STR))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME_STR))
            .andExpect(jsonPath("$.used").value(DEFAULT_USED.booleanValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.userEmail").value(DEFAULT_USER_EMAIL.toString()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME.toString()))
            .andExpect(jsonPath("$.userPhoneNumber").value(DEFAULT_USER_PHONE_NUMBER.toString()))
            .andExpect(jsonPath("$.userGender").value(DEFAULT_USER_GENDER.toString()))
            .andExpect(jsonPath("$.registerStatus").value(DEFAULT_REGISTER_STATUS.toString()))
            .andExpect(jsonPath("$.actionScheduleId").value(DEFAULT_ACTION_SCHEDULE_ID.intValue()))
            .andExpect(jsonPath("$.actionScheduleName").value(DEFAULT_ACTION_SCHEDULE_NAME.toString()))
            .andExpect(jsonPath("$.day").value(DEFAULT_DAY.toString()))
            .andExpect(jsonPath("$.scheduleType").value(DEFAULT_SCHEDULE_TYPE.toString()))
            .andExpect(jsonPath("$.attendeeLimit").value(DEFAULT_ATTENDEE_LIMIT))
            .andExpect(jsonPath("$.classScheduleId").value(DEFAULT_CLASS_SCHEDULE_ID.intValue()))
            .andExpect(jsonPath("$.classScheduleEtc").value(DEFAULT_CLASS_SCHEDULE_ETC.toString()))
            .andExpect(jsonPath("$.clubId").value(DEFAULT_CLUB_ID.intValue()))
            .andExpect(jsonPath("$.clubName").value(DEFAULT_CLUB_NAME.toString()))
            .andExpect(jsonPath("$.clubZipcode").value(DEFAULT_CLUB_ZIPCODE.toString()))
            .andExpect(jsonPath("$.clubAddress1").value(DEFAULT_CLUB_ADDRESS1.toString()))
            .andExpect(jsonPath("$.clubAddress2").value(DEFAULT_CLUB_ADDRESS2.toString()))
            .andExpect(jsonPath("$.clubPhoneNumber").value(DEFAULT_CLUB_PHONE_NUMBER.toString()))
            .andExpect(jsonPath("$.onlyFemail").value(DEFAULT_ONLY_FEMAIL.booleanValue()))
            .andExpect(jsonPath("$.clubManagerMobile").value(DEFAULT_CLUB_MANAGER_MOBILE.toString()))
            .andExpect(jsonPath("$.notificationType").value(DEFAULT_NOTIFICATION_TYPE.toString()))
            .andExpect(jsonPath("$.reservationClose").value(DEFAULT_RESERVATION_CLOSE.toString()))
            .andExpect(jsonPath("$.actionId").value(DEFAULT_ACTION_ID.intValue()))
            .andExpect(jsonPath("$.actionTitle").value(DEFAULT_ACTION_TITLE.toString()))
            .andExpect(jsonPath("$.actionDescription").value(DEFAULT_ACTION_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.actionUseLimitType").value(DEFAULT_ACTION_USE_LIMIT_TYPE.toString()))
            .andExpect(jsonPath("$.actionUseLimitValue").value(DEFAULT_ACTION_USE_LIMIT_VALUE))
            .andExpect(jsonPath("$.instructorId").value(DEFAULT_INSTRUCTOR_ID.intValue()))
            .andExpect(jsonPath("$.instructorName").value(DEFAULT_INSTRUCTOR_NAME.toString()))
            .andExpect(jsonPath("$.categoryId").value(DEFAULT_CATEGORY_ID.intValue()))
            .andExpect(jsonPath("$.categoryName").value(DEFAULT_CATEGORY_NAME.toString()))
            .andExpect(jsonPath("$.ticketId").value(DEFAULT_TICKET_ID.intValue()))
            .andExpect(jsonPath("$.ticketName").value(DEFAULT_TICKET_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingReservationHistory() throws Exception {
        // Get the reservationHistory
        restReservationHistoryMockMvc.perform(get("/api/reservationHistorys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReservationHistory() throws Exception {
        // Initialize the database
        reservationHistoryRepository.saveAndFlush(reservationHistory);

		int databaseSizeBeforeUpdate = reservationHistoryRepository.findAll().size();

        // Update the reservationHistory
        reservationHistory.setReservationId(UPDATED_RESERVATION_ID);
        reservationHistory.setReservationMethod(UPDATED_RESERVATION_METHOD);
        reservationHistory.setReservationTime(UPDATED_RESERVATION_TIME);
        reservationHistory.setStartTime(UPDATED_START_TIME);
        reservationHistory.setEndTime(UPDATED_END_TIME);
        reservationHistory.setUsed(UPDATED_USED);
        reservationHistory.setUserId(UPDATED_USER_ID);
        reservationHistory.setUserEmail(UPDATED_USER_EMAIL);
        reservationHistory.setUserName(UPDATED_USER_NAME);
        reservationHistory.setUserPhoneNumber(UPDATED_USER_PHONE_NUMBER);
        reservationHistory.setUserGender(UPDATED_USER_GENDER);
        reservationHistory.setRegisterStatus(UPDATED_REGISTER_STATUS);
        reservationHistory.setActionScheduleId(UPDATED_ACTION_SCHEDULE_ID);
        reservationHistory.setActionScheduleName(UPDATED_ACTION_SCHEDULE_NAME);
        reservationHistory.setDay(UPDATED_DAY);
        reservationHistory.setScheduleType(UPDATED_SCHEDULE_TYPE);
        reservationHistory.setAttendeeLimit(UPDATED_ATTENDEE_LIMIT);
        reservationHistory.setClassScheduleId(UPDATED_CLASS_SCHEDULE_ID);
        reservationHistory.setClassScheduleEtc(UPDATED_CLASS_SCHEDULE_ETC);
        reservationHistory.setClubId(UPDATED_CLUB_ID);
        reservationHistory.setClubName(UPDATED_CLUB_NAME);
        reservationHistory.setClubZipcode(UPDATED_CLUB_ZIPCODE);
        reservationHistory.setClubAddress1(UPDATED_CLUB_ADDRESS1);
        reservationHistory.setClubAddress2(UPDATED_CLUB_ADDRESS2);
        reservationHistory.setClubPhoneNumber(UPDATED_CLUB_PHONE_NUMBER);
        reservationHistory.setOnlyFemail(UPDATED_ONLY_FEMAIL);
        reservationHistory.setClubManagerMobile(UPDATED_CLUB_MANAGER_MOBILE);
        reservationHistory.setNotificationType(UPDATED_NOTIFICATION_TYPE);
        reservationHistory.setReservationClose(UPDATED_RESERVATION_CLOSE);
        reservationHistory.setActionId(UPDATED_ACTION_ID);
        reservationHistory.setActionTitle(UPDATED_ACTION_TITLE);
        reservationHistory.setActionDescription(UPDATED_ACTION_DESCRIPTION);
        reservationHistory.setActionUseLimitType(UPDATED_ACTION_USE_LIMIT_TYPE);
        reservationHistory.setActionUseLimitValue(UPDATED_ACTION_USE_LIMIT_VALUE);
        reservationHistory.setInstructorId(UPDATED_INSTRUCTOR_ID);
        reservationHistory.setInstructorName(UPDATED_INSTRUCTOR_NAME);
        reservationHistory.setCategoryId(UPDATED_CATEGORY_ID);
        reservationHistory.setCategoryName(UPDATED_CATEGORY_NAME);
        reservationHistory.setTicketId(UPDATED_TICKET_ID);
        reservationHistory.setTicketName(UPDATED_TICKET_NAME);
        

        restReservationHistoryMockMvc.perform(put("/api/reservationHistorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(reservationHistory)))
                .andExpect(status().isOk());

        // Validate the ReservationHistory in the database
        List<ReservationHistory> reservationHistorys = reservationHistoryRepository.findAll();
        assertThat(reservationHistorys).hasSize(databaseSizeBeforeUpdate);
        ReservationHistory testReservationHistory = reservationHistorys.get(reservationHistorys.size() - 1);
        assertThat(testReservationHistory.getReservationId()).isEqualTo(UPDATED_RESERVATION_ID);
        assertThat(testReservationHistory.getReservationMethod()).isEqualTo(UPDATED_RESERVATION_METHOD);
        assertThat(testReservationHistory.getReservationTime().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_RESERVATION_TIME);
        assertThat(testReservationHistory.getStartTime().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_START_TIME);
        assertThat(testReservationHistory.getEndTime().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_END_TIME);
        assertThat(testReservationHistory.getUsed()).isEqualTo(UPDATED_USED);
        assertThat(testReservationHistory.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testReservationHistory.getUserEmail()).isEqualTo(UPDATED_USER_EMAIL);
        assertThat(testReservationHistory.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testReservationHistory.getUserPhoneNumber()).isEqualTo(UPDATED_USER_PHONE_NUMBER);
        assertThat(testReservationHistory.getUserGender()).isEqualTo(UPDATED_USER_GENDER);
        assertThat(testReservationHistory.getRegisterStatus()).isEqualTo(UPDATED_REGISTER_STATUS);
        assertThat(testReservationHistory.getActionScheduleId()).isEqualTo(UPDATED_ACTION_SCHEDULE_ID);
        assertThat(testReservationHistory.getActionScheduleName()).isEqualTo(UPDATED_ACTION_SCHEDULE_NAME);
        assertThat(testReservationHistory.getDay()).isEqualTo(UPDATED_DAY);
        assertThat(testReservationHistory.getScheduleType()).isEqualTo(UPDATED_SCHEDULE_TYPE);
        assertThat(testReservationHistory.getAttendeeLimit()).isEqualTo(UPDATED_ATTENDEE_LIMIT);
        assertThat(testReservationHistory.getClassScheduleId()).isEqualTo(UPDATED_CLASS_SCHEDULE_ID);
        assertThat(testReservationHistory.getClassScheduleEtc()).isEqualTo(UPDATED_CLASS_SCHEDULE_ETC);
        assertThat(testReservationHistory.getClubId()).isEqualTo(UPDATED_CLUB_ID);
        assertThat(testReservationHistory.getClubName()).isEqualTo(UPDATED_CLUB_NAME);
        assertThat(testReservationHistory.getClubZipcode()).isEqualTo(UPDATED_CLUB_ZIPCODE);
        assertThat(testReservationHistory.getClubAddress1()).isEqualTo(UPDATED_CLUB_ADDRESS1);
        assertThat(testReservationHistory.getClubAddress2()).isEqualTo(UPDATED_CLUB_ADDRESS2);
        assertThat(testReservationHistory.getClubPhoneNumber()).isEqualTo(UPDATED_CLUB_PHONE_NUMBER);
        assertThat(testReservationHistory.getOnlyFemail()).isEqualTo(UPDATED_ONLY_FEMAIL);
        assertThat(testReservationHistory.getClubManagerMobile()).isEqualTo(UPDATED_CLUB_MANAGER_MOBILE);
        assertThat(testReservationHistory.getNotificationType()).isEqualTo(UPDATED_NOTIFICATION_TYPE);
        assertThat(testReservationHistory.getReservationClose()).isEqualTo(UPDATED_RESERVATION_CLOSE);
        assertThat(testReservationHistory.getActionId()).isEqualTo(UPDATED_ACTION_ID);
        assertThat(testReservationHistory.getActionTitle()).isEqualTo(UPDATED_ACTION_TITLE);
        assertThat(testReservationHistory.getActionDescription()).isEqualTo(UPDATED_ACTION_DESCRIPTION);
        assertThat(testReservationHistory.getActionUseLimitType()).isEqualTo(UPDATED_ACTION_USE_LIMIT_TYPE);
        assertThat(testReservationHistory.getActionUseLimitValue()).isEqualTo(UPDATED_ACTION_USE_LIMIT_VALUE);
        assertThat(testReservationHistory.getInstructorId()).isEqualTo(UPDATED_INSTRUCTOR_ID);
        assertThat(testReservationHistory.getInstructorName()).isEqualTo(UPDATED_INSTRUCTOR_NAME);
        assertThat(testReservationHistory.getCategoryId()).isEqualTo(UPDATED_CATEGORY_ID);
        assertThat(testReservationHistory.getCategoryName()).isEqualTo(UPDATED_CATEGORY_NAME);
        assertThat(testReservationHistory.getTicketId()).isEqualTo(UPDATED_TICKET_ID);
        assertThat(testReservationHistory.getTicketName()).isEqualTo(UPDATED_TICKET_NAME);
    }

    @Test
    @Transactional
    public void deleteReservationHistory() throws Exception {
        // Initialize the database
        reservationHistoryRepository.saveAndFlush(reservationHistory);

		int databaseSizeBeforeDelete = reservationHistoryRepository.findAll().size();

        // Get the reservationHistory
        restReservationHistoryMockMvc.perform(delete("/api/reservationHistorys/{id}", reservationHistory.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ReservationHistory> reservationHistorys = reservationHistoryRepository.findAll();
        assertThat(reservationHistorys).hasSize(databaseSizeBeforeDelete - 1);
    }
}

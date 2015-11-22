package co.bluepass.web.rest;

import co.bluepass.Application;
import co.bluepass.domain.Reservation;
import co.bluepass.repository.ReservationRepository;

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
 * Test class for the ReservationResource REST controller.
 *
 * @see ReservationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ReservationResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

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

    private static final Boolean DEFAULT_CANCELED = false;
    private static final Boolean UPDATED_CANCELED = true;

    private static final Boolean DEFAULT_USED = false;
    private static final Boolean UPDATED_USED = true;

    @Inject
    private ReservationRepository reservationRepository;

    private MockMvc restReservationMockMvc;

    private Reservation reservation;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReservationResource reservationResource = new ReservationResource();
        ReflectionTestUtils.setField(reservationResource, "reservationRepository", reservationRepository);
        this.restReservationMockMvc = MockMvcBuilders.standaloneSetup(reservationResource).build();
    }

    @Before
    public void initTest() {
        reservation = new Reservation();
        reservation.setReservationMethod(DEFAULT_RESERVATION_METHOD);
        reservation.setReservationTime(DEFAULT_RESERVATION_TIME);
        reservation.setStartTime(DEFAULT_START_TIME);
        reservation.setEndTime(DEFAULT_END_TIME);
        reservation.setCanceled(DEFAULT_CANCELED);
        reservation.setUsed(DEFAULT_USED);
    }

    /*@Test
    @Transactional
    public void createReservation() throws Exception {
        int databaseSizeBeforeCreate = reservationRepository.findAll().size();

        // Create the Reservation
        restReservationMockMvc.perform(post("/api/reservations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(reservation)))
                .andExpect(status().isCreated());

        // Validate the Reservation in the database
        List<Reservation> reservations = reservationRepository.findAll();
        assertThat(reservations).hasSize(databaseSizeBeforeCreate + 1);
        Reservation testReservation = reservations.get(reservations.size() - 1);
        assertThat(testReservation.getReservationMethod()).isEqualTo(DEFAULT_RESERVATION_METHOD);
        assertThat(testReservation.getReservationTime().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_RESERVATION_TIME);
        assertThat(testReservation.getStartTime().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_START_TIME);
        assertThat(testReservation.getEndTime().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_END_TIME);
        assertThat(testReservation.getCanceled()).isEqualTo(DEFAULT_CANCELED);
        assertThat(testReservation.getUsed()).isEqualTo(DEFAULT_USED);
    }*/

    @Test
    @Transactional
    public void getAllReservations() throws Exception {
        // Initialize the database
        reservationRepository.saveAndFlush(reservation);

        // Get all the reservations
        restReservationMockMvc.perform(get("/api/reservations"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(reservation.getId().intValue())))
                .andExpect(jsonPath("$.[*].reservationMethod").value(hasItem(DEFAULT_RESERVATION_METHOD.toString())))
                .andExpect(jsonPath("$.[*].reservationTime").value(hasItem(DEFAULT_RESERVATION_TIME_STR)))
                .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME_STR)))
                .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME_STR)))
                .andExpect(jsonPath("$.[*].canceled").value(hasItem(DEFAULT_CANCELED.booleanValue())))
                .andExpect(jsonPath("$.[*].used").value(hasItem(DEFAULT_USED.booleanValue())));
    }

    @Test
    @Transactional
    public void getReservation() throws Exception {
        // Initialize the database
        reservationRepository.saveAndFlush(reservation);

        // Get the reservation
        restReservationMockMvc.perform(get("/api/reservations/{id}", reservation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(reservation.getId().intValue()))
            .andExpect(jsonPath("$.reservationMethod").value(DEFAULT_RESERVATION_METHOD.toString()))
            .andExpect(jsonPath("$.reservationTime").value(DEFAULT_RESERVATION_TIME_STR))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME_STR))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME_STR))
            .andExpect(jsonPath("$.canceled").value(DEFAULT_CANCELED.booleanValue()))
            .andExpect(jsonPath("$.used").value(DEFAULT_USED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingReservation() throws Exception {
        // Get the reservation
        restReservationMockMvc.perform(get("/api/reservations/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    /*@Test
    @Transactional
    public void updateReservation() throws Exception {
        // Initialize the database
        reservationRepository.saveAndFlush(reservation);

		int databaseSizeBeforeUpdate = reservationRepository.findAll().size();

        // Update the reservation
        reservation.setReservationMethod(UPDATED_RESERVATION_METHOD);
        reservation.setReservationTime(UPDATED_RESERVATION_TIME);
        reservation.setStartTime(UPDATED_START_TIME);
        reservation.setEndTime(UPDATED_END_TIME);
        reservation.setCanceled(UPDATED_CANCELED);
        reservation.setUsed(UPDATED_USED);
        restReservationMockMvc.perform(put("/api/reservations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(reservation)))
                .andExpect(status().isOk());

        // Validate the Reservation in the database
        List<Reservation> reservations = reservationRepository.findAll();
        assertThat(reservations).hasSize(databaseSizeBeforeUpdate);
        Reservation testReservation = reservations.get(reservations.size() - 1);
        assertThat(testReservation.getReservationMethod()).isEqualTo(UPDATED_RESERVATION_METHOD);
        assertThat(testReservation.getReservationTime().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_RESERVATION_TIME);
        assertThat(testReservation.getStartTime().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_START_TIME);
        assertThat(testReservation.getEndTime().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_END_TIME);
        assertThat(testReservation.getCanceled()).isEqualTo(UPDATED_CANCELED);
        assertThat(testReservation.getUsed()).isEqualTo(UPDATED_USED);
    }*/

    /*@Test
    @Transactional
    public void deleteReservation() throws Exception {
        // Initialize the database
        reservationRepository.saveAndFlush(reservation);

		int databaseSizeBeforeDelete = reservationRepository.findAll().size();

        // Get the reservation
        restReservationMockMvc.perform(delete("/api/reservations/{id}", reservation.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Reservation> reservations = reservationRepository.findAll();
        assertThat(reservations).hasSize(databaseSizeBeforeDelete - 1);
    }*/
}

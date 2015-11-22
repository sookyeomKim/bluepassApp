package co.bluepass.web.rest;

import co.bluepass.Application;
import co.bluepass.domain.TicketHistory;
import co.bluepass.repository.TicketHistoryRepository;

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
 * Test class for the TicketHistoryResource REST controller.
 *
 * @see TicketHistoryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TicketHistoryResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");


    private static final Boolean DEFAULT_ACTIVATED = false;
    private static final Boolean UPDATED_ACTIVATED = true;

    private static final DateTime DEFAULT_REQUEST_DATE = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_REQUEST_DATE = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_REQUEST_DATE_STR = dateTimeFormatter.print(DEFAULT_REQUEST_DATE);

    private static final DateTime DEFAULT_ACTIVATED_DATE = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_ACTIVATED_DATE = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_ACTIVATED_DATE_STR = dateTimeFormatter.print(DEFAULT_ACTIVATED_DATE);

    private static final DateTime DEFAULT_CLOSE_DATE = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_CLOSE_DATE = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_CLOSE_DATE_STR = dateTimeFormatter.print(DEFAULT_CLOSE_DATE);

    @Inject
    private TicketHistoryRepository ticketHistoryRepository;

    private MockMvc restTicketHistoryMockMvc;

    private TicketHistory ticketHistory;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TicketHistoryResource ticketHistoryResource = new TicketHistoryResource();
        ReflectionTestUtils.setField(ticketHistoryResource, "ticketHistoryRepository", ticketHistoryRepository);
        this.restTicketHistoryMockMvc = MockMvcBuilders.standaloneSetup(ticketHistoryResource).build();
    }

    @Before
    public void initTest() {
        ticketHistory = new TicketHistory();
        ticketHistory.setActivated(DEFAULT_ACTIVATED);
        ticketHistory.setRequestDate(DEFAULT_REQUEST_DATE);
        ticketHistory.setActivatedDate(DEFAULT_ACTIVATED_DATE);
        ticketHistory.setCloseDate(DEFAULT_CLOSE_DATE);
    }

    @Test
    @Transactional
    public void createTicketHistory() throws Exception {
        int databaseSizeBeforeCreate = ticketHistoryRepository.findAll().size();

        // Create the TicketHistory
        restTicketHistoryMockMvc.perform(post("/api/ticketHistorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ticketHistory)))
                .andExpect(status().isCreated());

        // Validate the TicketHistory in the database
        List<TicketHistory> ticketHistorys = ticketHistoryRepository.findAll();
        assertThat(ticketHistorys).hasSize(databaseSizeBeforeCreate + 1);
        TicketHistory testTicketHistory = ticketHistorys.get(ticketHistorys.size() - 1);
        assertThat(testTicketHistory.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testTicketHistory.getRequestDate().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_REQUEST_DATE);
        assertThat(testTicketHistory.getActivatedDate().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_ACTIVATED_DATE);
        assertThat(testTicketHistory.getCloseDate().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_CLOSE_DATE);
    }

    @Test
    @Transactional
    public void checkRequestDateIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(ticketHistoryRepository.findAll()).hasSize(0);
        // set the field null
        ticketHistory.setRequestDate(null);

        // Create the TicketHistory, which fails.
        restTicketHistoryMockMvc.perform(post("/api/ticketHistorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ticketHistory)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<TicketHistory> ticketHistorys = ticketHistoryRepository.findAll();
        assertThat(ticketHistorys).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllTicketHistorys() throws Exception {
        // Initialize the database
        ticketHistoryRepository.saveAndFlush(ticketHistory);

        // Get all the ticketHistorys
        restTicketHistoryMockMvc.perform(get("/api/ticketHistorys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(ticketHistory.getId().intValue())))
                .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
                .andExpect(jsonPath("$.[*].requestDate").value(hasItem(DEFAULT_REQUEST_DATE_STR)))
                .andExpect(jsonPath("$.[*].activatedDate").value(hasItem(DEFAULT_ACTIVATED_DATE_STR)))
                .andExpect(jsonPath("$.[*].closeDate").value(hasItem(DEFAULT_CLOSE_DATE_STR)));
    }

    @Test
    @Transactional
    public void getTicketHistory() throws Exception {
        // Initialize the database
        ticketHistoryRepository.saveAndFlush(ticketHistory);

        // Get the ticketHistory
        restTicketHistoryMockMvc.perform(get("/api/ticketHistorys/{id}", ticketHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(ticketHistory.getId().intValue()))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.requestDate").value(DEFAULT_REQUEST_DATE_STR))
            .andExpect(jsonPath("$.activatedDate").value(DEFAULT_ACTIVATED_DATE_STR))
            .andExpect(jsonPath("$.closeDate").value(DEFAULT_CLOSE_DATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingTicketHistory() throws Exception {
        // Get the ticketHistory
        restTicketHistoryMockMvc.perform(get("/api/ticketHistorys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTicketHistory() throws Exception {
        // Initialize the database
        ticketHistoryRepository.saveAndFlush(ticketHistory);

		int databaseSizeBeforeUpdate = ticketHistoryRepository.findAll().size();

        // Update the ticketHistory
        ticketHistory.setActivated(UPDATED_ACTIVATED);
        ticketHistory.setRequestDate(UPDATED_REQUEST_DATE);
        ticketHistory.setActivatedDate(UPDATED_ACTIVATED_DATE);
        ticketHistory.setCloseDate(UPDATED_CLOSE_DATE);
        restTicketHistoryMockMvc.perform(put("/api/ticketHistorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ticketHistory)))
                .andExpect(status().isOk());

        // Validate the TicketHistory in the database
        List<TicketHistory> ticketHistorys = ticketHistoryRepository.findAll();
        assertThat(ticketHistorys).hasSize(databaseSizeBeforeUpdate);
        TicketHistory testTicketHistory = ticketHistorys.get(ticketHistorys.size() - 1);
        assertThat(testTicketHistory.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testTicketHistory.getRequestDate().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_REQUEST_DATE);
        assertThat(testTicketHistory.getActivatedDate().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_ACTIVATED_DATE);
        assertThat(testTicketHistory.getCloseDate().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_CLOSE_DATE);
    }

    @Test
    @Transactional
    public void deleteTicketHistory() throws Exception {
        // Initialize the database
        ticketHistoryRepository.saveAndFlush(ticketHistory);

		int databaseSizeBeforeDelete = ticketHistoryRepository.findAll().size();

        // Get the ticketHistory
        restTicketHistoryMockMvc.perform(delete("/api/ticketHistorys/{id}", ticketHistory.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<TicketHistory> ticketHistorys = ticketHistoryRepository.findAll();
        assertThat(ticketHistorys).hasSize(databaseSizeBeforeDelete - 1);
    }
}

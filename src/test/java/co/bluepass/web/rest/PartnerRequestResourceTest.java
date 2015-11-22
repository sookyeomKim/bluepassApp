package co.bluepass.web.rest;

import co.bluepass.Application;
import co.bluepass.domain.PartnerRequest;
import co.bluepass.repository.PartnerRequestRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PartnerRequestResource REST controller.
 *
 * @see PartnerRequestResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PartnerRequestResourceTest {

    private static final String DEFAULT_CLUB_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_CLUB_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_USER_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_USER_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_ADDRESS1 = "SAMPLE_TEXT";
    private static final String UPDATED_ADDRESS1 = "UPDATED_TEXT";
    private static final String DEFAULT_ADDRESS2 = "SAMPLE_TEXT";
    private static final String UPDATED_ADDRESS2 = "UPDATED_TEXT";
    private static final String DEFAULT_PHONE_NUMBER = "SAMPLE_TEXT";
    private static final String UPDATED_PHONE_NUMBER = "UPDATED_TEXT";
    private static final String DEFAULT_MESSAGE = "SAMPLE_TEXT";
    private static final String UPDATED_MESSAGE = "UPDATED_TEXT";

    @Inject
    private PartnerRequestRepository partnerRequestRepository;

    private MockMvc restPartnerRequestMockMvc;

    private PartnerRequest partnerRequest;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PartnerRequestResource partnerRequestResource = new PartnerRequestResource();
        ReflectionTestUtils.setField(partnerRequestResource, "partnerRequestRepository", partnerRequestRepository);
        this.restPartnerRequestMockMvc = MockMvcBuilders.standaloneSetup(partnerRequestResource).build();
    }

    @Before
    public void initTest() {
        partnerRequest = new PartnerRequest();
        partnerRequest.setClubName(DEFAULT_CLUB_NAME);
        partnerRequest.setUserName(DEFAULT_USER_NAME);
        partnerRequest.setAddress1(DEFAULT_ADDRESS1);
        partnerRequest.setPhoneNumber(DEFAULT_PHONE_NUMBER);
        partnerRequest.setMessage(DEFAULT_MESSAGE);
    }

    @Test
    @Transactional
    public void createPartnerRequest() throws Exception {
        int databaseSizeBeforeCreate = partnerRequestRepository.findAll().size();

        // Create the PartnerRequest
        restPartnerRequestMockMvc.perform(post("/api/partnerRequests")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(partnerRequest)))
                .andExpect(status().isCreated());

        // Validate the PartnerRequest in the database
        List<PartnerRequest> partnerRequests = partnerRequestRepository.findAll();
        assertThat(partnerRequests).hasSize(databaseSizeBeforeCreate + 1);
        PartnerRequest testPartnerRequest = partnerRequests.get(partnerRequests.size() - 1);
        assertThat(testPartnerRequest.getClubName()).isEqualTo(DEFAULT_CLUB_NAME);
        assertThat(testPartnerRequest.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testPartnerRequest.getAddress1()).isEqualTo(DEFAULT_ADDRESS1);
        assertThat(testPartnerRequest.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testPartnerRequest.getMessage()).isEqualTo(DEFAULT_MESSAGE);
    }

    @Test
    @Transactional
    public void checkClubNameIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(partnerRequestRepository.findAll()).hasSize(0);
        // set the field null
        partnerRequest.setClubName(null);

        // Create the PartnerRequest, which fails.
        restPartnerRequestMockMvc.perform(post("/api/partnerRequests")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(partnerRequest)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<PartnerRequest> partnerRequests = partnerRequestRepository.findAll();
        assertThat(partnerRequests).hasSize(0);
    }

    @Test
    @Transactional
    public void checkUserNameIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(partnerRequestRepository.findAll()).hasSize(0);
        // set the field null
        partnerRequest.setUserName(null);

        // Create the PartnerRequest, which fails.
        restPartnerRequestMockMvc.perform(post("/api/partnerRequests")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(partnerRequest)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<PartnerRequest> partnerRequests = partnerRequestRepository.findAll();
        assertThat(partnerRequests).hasSize(0);
    }

    @Test
    @Transactional
    public void checkAddressIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(partnerRequestRepository.findAll()).hasSize(0);
        // set the field null
        partnerRequest.setAddress1(null);

        // Create the PartnerRequest, which fails.
        restPartnerRequestMockMvc.perform(post("/api/partnerRequests")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(partnerRequest)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<PartnerRequest> partnerRequests = partnerRequestRepository.findAll();
        assertThat(partnerRequests).hasSize(0);
    }

    @Test
    @Transactional
    public void checkPhoneNumberIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(partnerRequestRepository.findAll()).hasSize(0);
        // set the field null
        partnerRequest.setPhoneNumber(null);

        // Create the PartnerRequest, which fails.
        restPartnerRequestMockMvc.perform(post("/api/partnerRequests")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(partnerRequest)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<PartnerRequest> partnerRequests = partnerRequestRepository.findAll();
        assertThat(partnerRequests).hasSize(0);
    }

    @Test
    @Transactional
    public void checkMessageIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(partnerRequestRepository.findAll()).hasSize(0);
        // set the field null
        partnerRequest.setMessage(null);

        // Create the PartnerRequest, which fails.
        restPartnerRequestMockMvc.perform(post("/api/partnerRequests")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(partnerRequest)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<PartnerRequest> partnerRequests = partnerRequestRepository.findAll();
        assertThat(partnerRequests).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllPartnerRequests() throws Exception {
        // Initialize the database
        partnerRequestRepository.saveAndFlush(partnerRequest);

        // Get all the partnerRequests
        restPartnerRequestMockMvc.perform(get("/api/partnerRequests"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(partnerRequest.getId().intValue())))
                .andExpect(jsonPath("$.[*].clubName").value(hasItem(DEFAULT_CLUB_NAME.toString())))
                .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME.toString())))
                .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS1.toString())))
                .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE.toString())));
    }

    @Test
    @Transactional
    public void getPartnerRequest() throws Exception {
        // Initialize the database
        partnerRequestRepository.saveAndFlush(partnerRequest);

        // Get the partnerRequest
        restPartnerRequestMockMvc.perform(get("/api/partnerRequests/{id}", partnerRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(partnerRequest.getId().intValue()))
            .andExpect(jsonPath("$.clubName").value(DEFAULT_CLUB_NAME.toString()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS1.toString()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.toString()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPartnerRequest() throws Exception {
        // Get the partnerRequest
        restPartnerRequestMockMvc.perform(get("/api/partnerRequests/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePartnerRequest() throws Exception {
        // Initialize the database
        partnerRequestRepository.saveAndFlush(partnerRequest);

		int databaseSizeBeforeUpdate = partnerRequestRepository.findAll().size();

        // Update the partnerRequest
        partnerRequest.setClubName(UPDATED_CLUB_NAME);
        partnerRequest.setUserName(UPDATED_USER_NAME);
        partnerRequest.setAddress1(UPDATED_ADDRESS1);
        partnerRequest.setPhoneNumber(UPDATED_PHONE_NUMBER);
        partnerRequest.setMessage(UPDATED_MESSAGE);
        restPartnerRequestMockMvc.perform(put("/api/partnerRequests")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(partnerRequest)))
                .andExpect(status().isOk());

        // Validate the PartnerRequest in the database
        List<PartnerRequest> partnerRequests = partnerRequestRepository.findAll();
        assertThat(partnerRequests).hasSize(databaseSizeBeforeUpdate);
        PartnerRequest testPartnerRequest = partnerRequests.get(partnerRequests.size() - 1);
        assertThat(testPartnerRequest.getClubName()).isEqualTo(UPDATED_CLUB_NAME);
        assertThat(testPartnerRequest.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testPartnerRequest.getAddress1()).isEqualTo(UPDATED_ADDRESS1);
        assertThat(testPartnerRequest.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testPartnerRequest.getMessage()).isEqualTo(UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void deletePartnerRequest() throws Exception {
        // Initialize the database
        partnerRequestRepository.saveAndFlush(partnerRequest);

		int databaseSizeBeforeDelete = partnerRequestRepository.findAll().size();

        // Get the partnerRequest
        restPartnerRequestMockMvc.perform(delete("/api/partnerRequests/{id}", partnerRequest.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PartnerRequest> partnerRequests = partnerRequestRepository.findAll();
        assertThat(partnerRequests).hasSize(databaseSizeBeforeDelete - 1);
    }
}

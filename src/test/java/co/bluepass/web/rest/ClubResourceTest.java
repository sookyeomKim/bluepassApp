package co.bluepass.web.rest;

import co.bluepass.Application;
import co.bluepass.domain.Club;
import co.bluepass.repository.ClubRepository;

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
 * Test class for the ClubResource REST controller.
 *
 * @see ClubResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ClubResourceTest {

    /*private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";
    private static final String DEFAULT_LICENSE_NUMBER = "1";
    private static final String UPDATED_LICENSE_NUMBER = "2";
    private static final String DEFAULT_HOMEPAGE = "SAMPLE_TEXT";
    private static final String UPDATED_HOMEPAGE = "UPDATED_TEXT";
    private static final String DEFAULT_ZIPCODE = "123456";
    private static final String UPDATED_ZIPCODE = "654321";
    private static final String DEFAULT_ADDRESS1 = "SAMPLE_TEXT";
    private static final String UPDATED_ADDRESS1 = "UPDATED_TEXT";
    private static final String DEFAULT_ADDRESS2 = "SAMPLE_TEXT";
    private static final String UPDATED_ADDRESS2 = "UPDATED_TEXT";

    private static final Boolean DEFAULT_ONLY_FEMALE = false;
    private static final Boolean UPDATED_ONLY_FEMALE = true;
    private static final String DEFAULT_FEATURES = "SAMPLE_TEXT";
    private static final String UPDATED_FEATURES = "UPDATED_TEXT";

    @Inject
    private ClubRepository clubRepository;

    private MockMvc restClubMockMvc;

    private Club club;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ClubResource clubResource = new ClubResource();
        ReflectionTestUtils.setField(clubResource, "clubRepository", clubRepository);
        this.restClubMockMvc = MockMvcBuilders.standaloneSetup(clubResource).build();
    }

    @Before
    public void initTest() {
        club = new Club();
        club.setName(DEFAULT_NAME);
        club.setDescription(DEFAULT_DESCRIPTION);
        club.setLicenseNumber(DEFAULT_LICENSE_NUMBER);
        club.setHomepage(DEFAULT_HOMEPAGE);
        club.setZipcode(DEFAULT_ZIPCODE);
        club.setAddress1(DEFAULT_ADDRESS1);
        club.setAddress2(DEFAULT_ADDRESS2);
        club.setOnlyFemale(DEFAULT_ONLY_FEMALE);
        club.setFeatures(DEFAULT_FEATURES);
    }

    @Test
    @Transactional
    public void createClub() throws Exception {
        int databaseSizeBeforeCreate = clubRepository.findAll().size();

        // Create the Club
        restClubMockMvc.perform(post("/api/clubs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(club)))
                .andExpect(status().isCreated());

        // Validate the Club in the database
        List<Club> clubs = clubRepository.findAll();
        assertThat(clubs).hasSize(databaseSizeBeforeCreate + 1);
        Club testClub = clubs.get(clubs.size() - 1);
        assertThat(testClub.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testClub.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testClub.getLicenseNumber()).isEqualTo(DEFAULT_LICENSE_NUMBER);
        assertThat(testClub.getHomepage()).isEqualTo(DEFAULT_HOMEPAGE);
        assertThat(testClub.getZipcode()).isEqualTo(DEFAULT_ZIPCODE);
        assertThat(testClub.getAddress1()).isEqualTo(DEFAULT_ADDRESS1);
        assertThat(testClub.getAddress2()).isEqualTo(DEFAULT_ADDRESS2);
        assertThat(testClub.getOnlyFemale()).isEqualTo(DEFAULT_ONLY_FEMALE);
        assertThat(testClub.getFeatures()).isEqualTo(DEFAULT_FEATURES);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(clubRepository.findAll()).hasSize(0);
        // set the field null
        club.setName(null);

        // Create the Club, which fails.
        restClubMockMvc.perform(post("/api/clubs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(club)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Club> clubs = clubRepository.findAll();
        assertThat(clubs).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllClubs() throws Exception {
        // Initialize the database
        clubRepository.saveAndFlush(club);

        // Get all the clubs
        restClubMockMvc.perform(get("/api/clubs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(club.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].licenseNumber").value(hasItem(DEFAULT_LICENSE_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].homepage").value(hasItem(DEFAULT_HOMEPAGE.toString())))
                .andExpect(jsonPath("$.[*].zipcode").value(hasItem(DEFAULT_ZIPCODE.toString())))
                .andExpect(jsonPath("$.[*].address1").value(hasItem(DEFAULT_ADDRESS1.toString())))
                .andExpect(jsonPath("$.[*].address2").value(hasItem(DEFAULT_ADDRESS2.toString())))
                .andExpect(jsonPath("$.[*].onlyFemale").value(hasItem(DEFAULT_ONLY_FEMALE.booleanValue())))
                .andExpect(jsonPath("$.[*].features").value(hasItem(DEFAULT_FEATURES.toString())));
    }

    @Test
    @Transactional
    public void getClub() throws Exception {
        // Initialize the database
        clubRepository.saveAndFlush(club);

        // Get the club
        restClubMockMvc.perform(get("/api/clubs/{id}", club.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(club.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.licenseNumber").value(DEFAULT_LICENSE_NUMBER.toString()))
            .andExpect(jsonPath("$.homepage").value(DEFAULT_HOMEPAGE.toString()))
            .andExpect(jsonPath("$.zipcode").value(DEFAULT_ZIPCODE.toString()))
            .andExpect(jsonPath("$.address1").value(DEFAULT_ADDRESS1.toString()))
            .andExpect(jsonPath("$.address2").value(DEFAULT_ADDRESS2.toString()))
            .andExpect(jsonPath("$.onlyFemale").value(DEFAULT_ONLY_FEMALE.booleanValue()))
            .andExpect(jsonPath("$.features").value(DEFAULT_FEATURES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingClub() throws Exception {
        // Get the club
        restClubMockMvc.perform(get("/api/clubs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClub() throws Exception {
        // Initialize the database
        clubRepository.saveAndFlush(club);

		int databaseSizeBeforeUpdate = clubRepository.findAll().size();

        // Update the club
        club.setName(UPDATED_NAME);
        club.setDescription(UPDATED_DESCRIPTION);
        club.setLicenseNumber(UPDATED_LICENSE_NUMBER);
        club.setHomepage(UPDATED_HOMEPAGE);
        club.setZipcode(UPDATED_ZIPCODE);
        club.setAddress1(UPDATED_ADDRESS1);
        club.setAddress2(UPDATED_ADDRESS2);
        club.setOnlyFemale(UPDATED_ONLY_FEMALE);
        club.setFeatures(UPDATED_FEATURES);
        restClubMockMvc.perform(put("/api/clubs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(club)))
                .andExpect(status().isOk());

        // Validate the Club in the database
        List<Club> clubs = clubRepository.findAll();
        assertThat(clubs).hasSize(databaseSizeBeforeUpdate);
        Club testClub = clubs.get(clubs.size() - 1);
        assertThat(testClub.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testClub.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testClub.getLicenseNumber()).isEqualTo(UPDATED_LICENSE_NUMBER);
        assertThat(testClub.getHomepage()).isEqualTo(UPDATED_HOMEPAGE);
        assertThat(testClub.getZipcode()).isEqualTo(UPDATED_ZIPCODE);
        assertThat(testClub.getAddress1()).isEqualTo(UPDATED_ADDRESS1);
        assertThat(testClub.getAddress2()).isEqualTo(UPDATED_ADDRESS2);
        assertThat(testClub.getOnlyFemale()).isEqualTo(UPDATED_ONLY_FEMALE);
        assertThat(testClub.getFeatures()).isEqualTo(UPDATED_FEATURES);
    }

    @Test
    @Transactional
    public void deleteClub() throws Exception {
        // Initialize the database
        clubRepository.saveAndFlush(club);

		int databaseSizeBeforeDelete = clubRepository.findAll().size();

        // Get the club
        restClubMockMvc.perform(delete("/api/clubs/{id}", club.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Club> clubs = clubRepository.findAll();
        assertThat(clubs).hasSize(databaseSizeBeforeDelete - 1);
    }*/
}

package co.bluepass.web.rest;

import co.bluepass.Application;
import co.bluepass.domain.Zip;
import co.bluepass.repository.ZipRepository;

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
 * Test class for the ZipResource REST controller.
 *
 * @see ZipResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ZipResourceTest {

    private static final String DEFAULT_ZIPCODE = "SAMPLE_TEXT";
    private static final String UPDATED_ZIPCODE = "UPDATED_TEXT";
    private static final String DEFAULT_SIDO = "SAMPLE_TEXT";
    private static final String UPDATED_SIDO = "UPDATED_TEXT";
    private static final String DEFAULT_GUGUN = "SAMPLE_TEXT";
    private static final String UPDATED_GUGUN = "UPDATED_TEXT";
    private static final String DEFAULT_DONG = "SAMPLE_TEXT";
    private static final String UPDATED_DONG = "UPDATED_TEXT";

    @Inject
    private ZipRepository zipRepository;

    private MockMvc restZipMockMvc;

    private Zip zip;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ZipResource zipResource = new ZipResource();
        ReflectionTestUtils.setField(zipResource, "zipRepository", zipRepository);
        this.restZipMockMvc = MockMvcBuilders.standaloneSetup(zipResource).build();
    }

    @Before
    public void initTest() {
        zip = new Zip();
        zip.setZipcode(DEFAULT_ZIPCODE);
        zip.setSido(DEFAULT_SIDO);
        zip.setGugun(DEFAULT_GUGUN);
        zip.setDong(DEFAULT_DONG);
    }

    @Test
    @Transactional
    public void createZip() throws Exception {
        int databaseSizeBeforeCreate = zipRepository.findAll().size();

        // Create the Zip
        restZipMockMvc.perform(post("/api/zips")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(zip)))
                .andExpect(status().isCreated());

        // Validate the Zip in the database
        List<Zip> zips = zipRepository.findAll();
        assertThat(zips).hasSize(databaseSizeBeforeCreate + 1);
        Zip testZip = zips.get(zips.size() - 1);
        assertThat(testZip.getZipcode()).isEqualTo(DEFAULT_ZIPCODE);
        assertThat(testZip.getSido()).isEqualTo(DEFAULT_SIDO);
        assertThat(testZip.getGugun()).isEqualTo(DEFAULT_GUGUN);
        assertThat(testZip.getDong()).isEqualTo(DEFAULT_DONG);
    }

    @Test
    @Transactional
    public void checkZipcodeIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(zipRepository.findAll()).hasSize(0);
        // set the field null
        zip.setZipcode(null);

        // Create the Zip, which fails.
        restZipMockMvc.perform(post("/api/zips")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(zip)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Zip> zips = zipRepository.findAll();
        assertThat(zips).hasSize(0);
    }

    @Test
    @Transactional
    public void checkSidoIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(zipRepository.findAll()).hasSize(0);
        // set the field null
        zip.setSido(null);

        // Create the Zip, which fails.
        restZipMockMvc.perform(post("/api/zips")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(zip)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Zip> zips = zipRepository.findAll();
        assertThat(zips).hasSize(0);
    }

    @Test
    @Transactional
    public void checkGugunIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(zipRepository.findAll()).hasSize(0);
        // set the field null
        zip.setGugun(null);

        // Create the Zip, which fails.
        restZipMockMvc.perform(post("/api/zips")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(zip)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Zip> zips = zipRepository.findAll();
        assertThat(zips).hasSize(0);
    }

    @Test
    @Transactional
    public void checkDongIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(zipRepository.findAll()).hasSize(0);
        // set the field null
        zip.setDong(null);

        // Create the Zip, which fails.
        restZipMockMvc.perform(post("/api/zips")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(zip)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Zip> zips = zipRepository.findAll();
        assertThat(zips).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllZips() throws Exception {
        // Initialize the database
        zipRepository.saveAndFlush(zip);

        // Get all the zips
        restZipMockMvc.perform(get("/api/zips"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(zip.getId().intValue())))
                .andExpect(jsonPath("$.[*].zipcode").value(hasItem(DEFAULT_ZIPCODE.toString())))
                .andExpect(jsonPath("$.[*].sido").value(hasItem(DEFAULT_SIDO.toString())))
                .andExpect(jsonPath("$.[*].gugun").value(hasItem(DEFAULT_GUGUN.toString())))
                .andExpect(jsonPath("$.[*].dong").value(hasItem(DEFAULT_DONG.toString())));
    }

    @Test
    @Transactional
    public void getZip() throws Exception {
        // Initialize the database
        zipRepository.saveAndFlush(zip);

        // Get the zip
        restZipMockMvc.perform(get("/api/zips/{id}", zip.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(zip.getId().intValue()))
            .andExpect(jsonPath("$.zipcode").value(DEFAULT_ZIPCODE.toString()))
            .andExpect(jsonPath("$.sido").value(DEFAULT_SIDO.toString()))
            .andExpect(jsonPath("$.gugun").value(DEFAULT_GUGUN.toString()))
            .andExpect(jsonPath("$.dong").value(DEFAULT_DONG.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingZip() throws Exception {
        // Get the zip
        restZipMockMvc.perform(get("/api/zips/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateZip() throws Exception {
        // Initialize the database
        zipRepository.saveAndFlush(zip);

		int databaseSizeBeforeUpdate = zipRepository.findAll().size();

        // Update the zip
        zip.setZipcode(UPDATED_ZIPCODE);
        zip.setSido(UPDATED_SIDO);
        zip.setGugun(UPDATED_GUGUN);
        zip.setDong(UPDATED_DONG);
        restZipMockMvc.perform(put("/api/zips")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(zip)))
                .andExpect(status().isOk());

        // Validate the Zip in the database
        List<Zip> zips = zipRepository.findAll();
        assertThat(zips).hasSize(databaseSizeBeforeUpdate);
        Zip testZip = zips.get(zips.size() - 1);
        assertThat(testZip.getZipcode()).isEqualTo(UPDATED_ZIPCODE);
        assertThat(testZip.getSido()).isEqualTo(UPDATED_SIDO);
        assertThat(testZip.getGugun()).isEqualTo(UPDATED_GUGUN);
        assertThat(testZip.getDong()).isEqualTo(UPDATED_DONG);
    }

    @Test
    @Transactional
    public void deleteZip() throws Exception {
        // Initialize the database
        zipRepository.saveAndFlush(zip);

		int databaseSizeBeforeDelete = zipRepository.findAll().size();

        // Get the zip
        restZipMockMvc.perform(delete("/api/zips/{id}", zip.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Zip> zips = zipRepository.findAll();
        assertThat(zips).hasSize(databaseSizeBeforeDelete - 1);
    }
}

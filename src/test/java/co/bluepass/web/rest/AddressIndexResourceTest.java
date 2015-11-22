package co.bluepass.web.rest;

import co.bluepass.Application;
import co.bluepass.domain.AddressIndex;
import co.bluepass.repository.AddressIndexRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the AddressIndexResource REST controller.
 *
 * @see AddressIndexResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AddressIndexResourceTest {

    private static final String DEFAULT_OLD_ADDRESS = "SAMPLE_TEXT";
    private static final String UPDATED_OLD_ADDRESS = "UPDATED_TEXT";

    @Inject
    private AddressIndexRepository addressIndexRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restAddressIndexMockMvc;

    private AddressIndex addressIndex;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AddressIndexResource addressIndexResource = new AddressIndexResource();
        ReflectionTestUtils.setField(addressIndexResource, "addressIndexRepository", addressIndexRepository);
        this.restAddressIndexMockMvc = MockMvcBuilders.standaloneSetup(addressIndexResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        addressIndex = new AddressIndex();
        addressIndex.setOldAddress(DEFAULT_OLD_ADDRESS);
    }

    /*@Test
    @Transactional
    public void createAddressIndex() throws Exception {
        int databaseSizeBeforeCreate = addressIndexRepository.findAll().size();

        // Create the AddressIndex

        restAddressIndexMockMvc.perform(post("/api/addressIndexs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(addressIndex)))
                .andExpect(status().isCreated());

        // Validate the AddressIndex in the database
        List<AddressIndex> addressIndexs = addressIndexRepository.findAll();
        assertThat(addressIndexs).hasSize(databaseSizeBeforeCreate + 1);
        AddressIndex testAddressIndex = addressIndexs.get(addressIndexs.size() - 1);
        assertThat(testAddressIndex.getOldAddress()).isEqualTo(DEFAULT_OLD_ADDRESS);
    }*/

    @Test
    @Transactional
    public void getAllAddressIndexs() throws Exception {
        // Initialize the database
        addressIndexRepository.saveAndFlush(addressIndex);

        // Get all the addressIndexs
        restAddressIndexMockMvc.perform(get("/api/addressIndexs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(addressIndex.getId().intValue())))
                .andExpect(jsonPath("$.[*].oldAddress").value(hasItem(DEFAULT_OLD_ADDRESS.toString())));
    }

    @Test
    @Transactional
    public void getAddressIndex() throws Exception {
        // Initialize the database
        addressIndexRepository.saveAndFlush(addressIndex);

        // Get the addressIndex
        restAddressIndexMockMvc.perform(get("/api/addressIndexs/{id}", addressIndex.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(addressIndex.getId().intValue()))
            .andExpect(jsonPath("$.oldAddress").value(DEFAULT_OLD_ADDRESS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAddressIndex() throws Exception {
        // Get the addressIndex
        restAddressIndexMockMvc.perform(get("/api/addressIndexs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    /*@Test
    @Transactional
    public void updateAddressIndex() throws Exception {
        // Initialize the database
        addressIndexRepository.saveAndFlush(addressIndex);

		int databaseSizeBeforeUpdate = addressIndexRepository.findAll().size();

        // Update the addressIndex
        addressIndex.setOldAddress(UPDATED_OLD_ADDRESS);
        

        restAddressIndexMockMvc.perform(put("/api/addressIndexs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(addressIndex)))
                .andExpect(status().isOk());

        // Validate the AddressIndex in the database
        List<AddressIndex> addressIndexs = addressIndexRepository.findAll();
        assertThat(addressIndexs).hasSize(databaseSizeBeforeUpdate);
        AddressIndex testAddressIndex = addressIndexs.get(addressIndexs.size() - 1);
        assertThat(testAddressIndex.getOldAddress()).isEqualTo(UPDATED_OLD_ADDRESS);
    }*/

    /*@Test
    @Transactional
    public void deleteAddressIndex() throws Exception {
        // Initialize the database
        addressIndexRepository.saveAndFlush(addressIndex);

		int databaseSizeBeforeDelete = addressIndexRepository.findAll().size();

        // Get the addressIndex
        restAddressIndexMockMvc.perform(delete("/api/addressIndexs/{id}", addressIndex.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<AddressIndex> addressIndexs = addressIndexRepository.findAll();
        assertThat(addressIndexs).hasSize(databaseSizeBeforeDelete - 1);
    }*/
}

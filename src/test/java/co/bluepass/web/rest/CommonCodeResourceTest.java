package co.bluepass.web.rest;

import co.bluepass.Application;
import co.bluepass.domain.CommonCode;
import co.bluepass.repository.CommonCodeRepository;

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
 * Test class for the CommonCodeResource REST controller.
 *
 * @see CommonCodeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CommonCodeResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_VALUE = "SAMPLE_TEXT";
    private static final String UPDATED_VALUE = "UPDATED_TEXT";
    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";
    private static final String DEFAULT_OPTION1 = "SAMPLE_TEXT";
    private static final String UPDATED_OPTION1 = "UPDATED_TEXT";
    private static final String DEFAULT_OPTION2 = "SAMPLE_TEXT";
    private static final String UPDATED_OPTION2 = "UPDATED_TEXT";
    private static final String DEFAULT_OPTION3 = "SAMPLE_TEXT";
    private static final String UPDATED_OPTION3 = "UPDATED_TEXT";

    @Inject
    private CommonCodeRepository commonCodeRepository;

    private MockMvc restCommonCodeMockMvc;

    private CommonCode commonCode;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CommonCodeResource commonCodeResource = new CommonCodeResource();
        ReflectionTestUtils.setField(commonCodeResource, "commonCodeRepository", commonCodeRepository);
        this.restCommonCodeMockMvc = MockMvcBuilders.standaloneSetup(commonCodeResource).build();
    }

    @Before
    public void initTest() {
        commonCode = new CommonCode();
        commonCode.setName(DEFAULT_NAME);
        commonCode.setValue(DEFAULT_VALUE);
        commonCode.setDescription(DEFAULT_DESCRIPTION);
        commonCode.setOption1(DEFAULT_OPTION1);
        commonCode.setOption2(DEFAULT_OPTION2);
        commonCode.setOption3(DEFAULT_OPTION3);
    }

    /*@Test
    @Transactional
    public void createCommonCode() throws Exception {
        int databaseSizeBeforeCreate = commonCodeRepository.findAll().size();

        // Create the CommonCode
        restCommonCodeMockMvc.perform(post("/api/commonCodes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(commonCode)))
                .andExpect(status().isCreated());

        // Validate the CommonCode in the database
        List<CommonCode> commonCodes = commonCodeRepository.findAll();
        assertThat(commonCodes).hasSize(databaseSizeBeforeCreate + 1);
        CommonCode testCommonCode = commonCodes.get(commonCodes.size() - 1);
        assertThat(testCommonCode.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCommonCode.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testCommonCode.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCommonCode.getOption1()).isEqualTo(DEFAULT_OPTION1);
        assertThat(testCommonCode.getOption2()).isEqualTo(DEFAULT_OPTION2);
        assertThat(testCommonCode.getOption3()).isEqualTo(DEFAULT_OPTION3);
    }*/

    /*@Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(commonCodeRepository.findAll()).hasSize(0);
        // set the field null
        commonCode.setName(null);

        // Create the CommonCode, which fails.
        restCommonCodeMockMvc.perform(post("/api/commonCodes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(commonCode)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<CommonCode> commonCodes = commonCodeRepository.findAll();
        assertThat(commonCodes).hasSize(0);
    }*/

    /*@Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(commonCodeRepository.findAll()).hasSize(0);
        // set the field null
        commonCode.setValue(null);

        // Create the CommonCode, which fails.
        restCommonCodeMockMvc.perform(post("/api/commonCodes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(commonCode)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<CommonCode> commonCodes = commonCodeRepository.findAll();
        assertThat(commonCodes).hasSize(0);
    }*/

    @Test
    @Transactional
    public void getAllCommonCodes() throws Exception {
        // Initialize the database
        commonCodeRepository.saveAndFlush(commonCode);

        // Get all the commonCodes
        restCommonCodeMockMvc.perform(get("/api/commonCodes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(commonCode.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].option1").value(hasItem(DEFAULT_OPTION1.toString())))
                .andExpect(jsonPath("$.[*].option2").value(hasItem(DEFAULT_OPTION2.toString())))
                .andExpect(jsonPath("$.[*].option3").value(hasItem(DEFAULT_OPTION3.toString())));
    }

    @Test
    @Transactional
    public void getCommonCode() throws Exception {
        // Initialize the database
        commonCodeRepository.saveAndFlush(commonCode);

        // Get the commonCode
        restCommonCodeMockMvc.perform(get("/api/commonCodes/{id}", commonCode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(commonCode.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.option1").value(DEFAULT_OPTION1.toString()))
            .andExpect(jsonPath("$.option2").value(DEFAULT_OPTION2.toString()))
            .andExpect(jsonPath("$.option3").value(DEFAULT_OPTION3.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCommonCode() throws Exception {
        // Get the commonCode
        restCommonCodeMockMvc.perform(get("/api/commonCodes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    /*@Test
    @Transactional
    public void updateCommonCode() throws Exception {
        // Initialize the database
        commonCodeRepository.saveAndFlush(commonCode);

		int databaseSizeBeforeUpdate = commonCodeRepository.findAll().size();

        // Update the commonCode
        commonCode.setName(UPDATED_NAME);
        commonCode.setValue(UPDATED_VALUE);
        commonCode.setDescription(UPDATED_DESCRIPTION);
        commonCode.setOption1(UPDATED_OPTION1);
        commonCode.setOption2(UPDATED_OPTION2);
        commonCode.setOption3(UPDATED_OPTION3);
        restCommonCodeMockMvc.perform(put("/api/commonCodes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(commonCode)))
                .andExpect(status().isOk());

        // Validate the CommonCode in the database
        List<CommonCode> commonCodes = commonCodeRepository.findAll();
        assertThat(commonCodes).hasSize(databaseSizeBeforeUpdate);
        CommonCode testCommonCode = commonCodes.get(commonCodes.size() - 1);
        assertThat(testCommonCode.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCommonCode.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCommonCode.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCommonCode.getOption1()).isEqualTo(UPDATED_OPTION1);
        assertThat(testCommonCode.getOption2()).isEqualTo(UPDATED_OPTION2);
        assertThat(testCommonCode.getOption3()).isEqualTo(UPDATED_OPTION3);
    }*/

    /*@Test
    @Transactional
    public void deleteCommonCode() throws Exception {
        // Initialize the database
        commonCodeRepository.saveAndFlush(commonCode);

		int databaseSizeBeforeDelete = commonCodeRepository.findAll().size();

        // Get the commonCode
        restCommonCodeMockMvc.perform(delete("/api/commonCodes/{id}", commonCode.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CommonCode> commonCodes = commonCodeRepository.findAll();
        assertThat(commonCodes).hasSize(databaseSizeBeforeDelete - 1);
    }*/
}

package co.bluepass.web.rest;

import co.bluepass.Application;
import co.bluepass.domain.Instructor;
import co.bluepass.repository.InstructorRepository;

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
 * Test class for the InstructorResource REST controller.
 *
 * @see InstructorResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InstructorResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";

    @Inject
    private InstructorRepository instructorRepository;

    private MockMvc restInstructorMockMvc;

    private Instructor instructor;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InstructorResource instructorResource = new InstructorResource();
        ReflectionTestUtils.setField(instructorResource, "instructorRepository", instructorRepository);
        this.restInstructorMockMvc = MockMvcBuilders.standaloneSetup(instructorResource).build();
    }

    @Before
    public void initTest() {
        instructor = new Instructor();
        instructor.setName(DEFAULT_NAME);
        instructor.setDescription(DEFAULT_DESCRIPTION);
    }

    /*@Test
    @Transactional
    public void createInstructor() throws Exception {
        int databaseSizeBeforeCreate = instructorRepository.findAll().size();

        // Create the Instructor
        restInstructorMockMvc.perform(post("/api/instructors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instructor)))
                .andExpect(status().isCreated());

        // Validate the Instructor in the database
        List<Instructor> instructors = instructorRepository.findAll();
        assertThat(instructors).hasSize(databaseSizeBeforeCreate + 1);
        Instructor testInstructor = instructors.get(instructors.size() - 1);
        assertThat(testInstructor.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testInstructor.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }*/

    /*@Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(instructorRepository.findAll()).hasSize(0);
        // set the field null
        instructor.setName(null);

        // Create the Instructor, which fails.
        restInstructorMockMvc.perform(post("/api/instructors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instructor)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Instructor> instructors = instructorRepository.findAll();
        assertThat(instructors).hasSize(0);
    }*/

    @Test
    @Transactional
    public void getAllInstructors() throws Exception {
        // Initialize the database
        instructorRepository.saveAndFlush(instructor);

        // Get all the instructors
        restInstructorMockMvc.perform(get("/api/instructors"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(instructor.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getInstructor() throws Exception {
        // Initialize the database
        instructorRepository.saveAndFlush(instructor);

        // Get the instructor
        restInstructorMockMvc.perform(get("/api/instructors/{id}", instructor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(instructor.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInstructor() throws Exception {
        // Get the instructor
        restInstructorMockMvc.perform(get("/api/instructors/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    /*@Test
    @Transactional
    public void updateInstructor() throws Exception {
        // Initialize the database
        instructorRepository.saveAndFlush(instructor);

		int databaseSizeBeforeUpdate = instructorRepository.findAll().size();

        // Update the instructor
        instructor.setName(UPDATED_NAME);
        instructor.setDescription(UPDATED_DESCRIPTION);
        restInstructorMockMvc.perform(put("/api/instructors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instructor)))
                .andExpect(status().isOk());

        // Validate the Instructor in the database
        List<Instructor> instructors = instructorRepository.findAll();
        assertThat(instructors).hasSize(databaseSizeBeforeUpdate);
        Instructor testInstructor = instructors.get(instructors.size() - 1);
        assertThat(testInstructor.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testInstructor.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }*/

    /*@Test
    @Transactional
    public void deleteInstructor() throws Exception {
        // Initialize the database
        instructorRepository.saveAndFlush(instructor);

		int databaseSizeBeforeDelete = instructorRepository.findAll().size();

        // Get the instructor
        restInstructorMockMvc.perform(delete("/api/instructors/{id}", instructor.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Instructor> instructors = instructorRepository.findAll();
        assertThat(instructors).hasSize(databaseSizeBeforeDelete - 1);
    }*/
}

package co.bluepass.web.rest;

import co.bluepass.Application;
import co.bluepass.domain.Action;
import co.bluepass.repository.ActionRepository;

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
 * Test class for the ActionResource REST controller.
 *
 * @see ActionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ActionResourceTest {

    /*private static final String DEFAULT_TITLE = "SAMPLE_TEXT";
    private static final String UPDATED_TITLE = "UPDATED_TEXT";
    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";
    private static final String DEFAULT_SHORT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_SHORT_DESCRIPTION = "UPDATED_TEXT";

    private static final Integer DEFAULT_ATTENDEE_LIMIT = 0;
    private static final Integer UPDATED_ATTENDEE_LIMIT = 1;
    private static final String DEFAULT_USE_LIMIT_TYPE = "SAMPLE_TEXT";
    private static final String UPDATED_USE_LIMIT_TYPE = "UPDATED_TEXT";

    private static final Integer DEFAULT_USE_LIMIT_VALUE = 0;
    private static final Integer UPDATED_USE_LIMIT_VALUE = 1;

    private static final Boolean DEFAULT_REPEATABLE = false;
    private static final Boolean UPDATED_REPEATABLE = true;

    @Inject
    private ActionRepository actionRepository;

    private MockMvc restActionMockMvc;

    private Action action;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ActionResource actionResource = new ActionResource();
        ReflectionTestUtils.setField(actionResource, "actionRepository", actionRepository);
        this.restActionMockMvc = MockMvcBuilders.standaloneSetup(actionResource).build();
    }

    @Before
    public void initTest() {
        action = new Action();
        action.setTitle(DEFAULT_TITLE);
        action.setDescription(DEFAULT_DESCRIPTION);
        action.setShortDescription(DEFAULT_SHORT_DESCRIPTION);
        action.setAttendeeLimit(DEFAULT_ATTENDEE_LIMIT);
        action.setUseLimitType(DEFAULT_USE_LIMIT_TYPE);
        action.setUseLimitValue(DEFAULT_USE_LIMIT_VALUE);
        action.setRepeatable(DEFAULT_REPEATABLE);
    }

    @Test
    @Transactional
    public void createAction() throws Exception {
        int databaseSizeBeforeCreate = actionRepository.findAll().size();

        // Create the Action
        restActionMockMvc.perform(post("/api/actions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(action)))
                .andExpect(status().isCreated());

        // Validate the Action in the database
        List<Action> actions = actionRepository.findAll();
        assertThat(actions).hasSize(databaseSizeBeforeCreate + 1);
        Action testAction = actions.get(actions.size() - 1);
        assertThat(testAction.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testAction.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAction.getShortDescription()).isEqualTo(DEFAULT_SHORT_DESCRIPTION);
        assertThat(testAction.getAttendeeLimit()).isEqualTo(DEFAULT_ATTENDEE_LIMIT);
        assertThat(testAction.getUseLimitType()).isEqualTo(DEFAULT_USE_LIMIT_TYPE);
        assertThat(testAction.getUseLimitValue()).isEqualTo(DEFAULT_USE_LIMIT_VALUE);
        assertThat(testAction.getRepeatable()).isEqualTo(DEFAULT_REPEATABLE);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(actionRepository.findAll()).hasSize(0);
        // set the field null
        action.setTitle(null);

        // Create the Action, which fails.
        restActionMockMvc.perform(post("/api/actions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(action)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Action> actions = actionRepository.findAll();
        assertThat(actions).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllActions() throws Exception {
        // Initialize the database
        actionRepository.saveAndFlush(action);

        // Get all the actions
        restActionMockMvc.perform(get("/api/actions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(action.getId().intValue())))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].shortDescription").value(hasItem(DEFAULT_SHORT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].attendeeLimit").value(hasItem(DEFAULT_ATTENDEE_LIMIT)))
                .andExpect(jsonPath("$.[*].useLimitType").value(hasItem(DEFAULT_USE_LIMIT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].useLimitValue").value(hasItem(DEFAULT_USE_LIMIT_VALUE)))
                .andExpect(jsonPath("$.[*].repeatable").value(hasItem(DEFAULT_REPEATABLE.booleanValue())));
    }

    @Test
    @Transactional
    public void getAction() throws Exception {
        // Initialize the database
        actionRepository.saveAndFlush(action);

        // Get the action
        restActionMockMvc.perform(get("/api/actions/{id}", action.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(action.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.shortDescription").value(DEFAULT_SHORT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.attendeeLimit").value(DEFAULT_ATTENDEE_LIMIT))
            .andExpect(jsonPath("$.useLimitType").value(DEFAULT_USE_LIMIT_TYPE.toString()))
            .andExpect(jsonPath("$.useLimitValue").value(DEFAULT_USE_LIMIT_VALUE))
            .andExpect(jsonPath("$.repeatable").value(DEFAULT_REPEATABLE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAction() throws Exception {
        // Get the action
        restActionMockMvc.perform(get("/api/actions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAction() throws Exception {
        // Initialize the database
        actionRepository.saveAndFlush(action);

		int databaseSizeBeforeUpdate = actionRepository.findAll().size();

        // Update the action
        action.setTitle(UPDATED_TITLE);
        action.setDescription(UPDATED_DESCRIPTION);
        action.setShortDescription(UPDATED_SHORT_DESCRIPTION);
        action.setAttendeeLimit(UPDATED_ATTENDEE_LIMIT);
        action.setUseLimitType(UPDATED_USE_LIMIT_TYPE);
        action.setUseLimitValue(UPDATED_USE_LIMIT_VALUE);
        action.setRepeatable(UPDATED_REPEATABLE);
        restActionMockMvc.perform(put("/api/actions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(action)))
                .andExpect(status().isOk());

        // Validate the Action in the database
        List<Action> actions = actionRepository.findAll();
        assertThat(actions).hasSize(databaseSizeBeforeUpdate);
        Action testAction = actions.get(actions.size() - 1);
        assertThat(testAction.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testAction.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAction.getShortDescription()).isEqualTo(UPDATED_SHORT_DESCRIPTION);
        assertThat(testAction.getAttendeeLimit()).isEqualTo(UPDATED_ATTENDEE_LIMIT);
        assertThat(testAction.getUseLimitType()).isEqualTo(UPDATED_USE_LIMIT_TYPE);
        assertThat(testAction.getUseLimitValue()).isEqualTo(UPDATED_USE_LIMIT_VALUE);
        assertThat(testAction.getRepeatable()).isEqualTo(UPDATED_REPEATABLE);
    }

    @Test
    @Transactional
    public void deleteAction() throws Exception {
        // Initialize the database
        actionRepository.saveAndFlush(action);

		int databaseSizeBeforeDelete = actionRepository.findAll().size();

        // Get the action
        restActionMockMvc.perform(delete("/api/actions/{id}", action.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Action> actions = actionRepository.findAll();
        assertThat(actions).hasSize(databaseSizeBeforeDelete - 1);
    }*/
}

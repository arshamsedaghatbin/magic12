package com.papra.magic.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.papra.magic.IntegrationTest;
import com.papra.magic.domain.PracticeSession;
import com.papra.magic.repository.PracticeSessionRepository;
import com.papra.magic.service.PracticeSessionService;
import com.papra.magic.service.dto.PracticeSessionDTO;
import com.papra.magic.service.mapper.PracticeSessionMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PracticeSessionResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PracticeSessionResourceIT {

    private static final String DEFAULT_TILTLE = "AAAAAAAAAA";
    private static final String UPDATED_TILTLE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/practice-sessions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PracticeSessionRepository practiceSessionRepository;

    @Mock
    private PracticeSessionRepository practiceSessionRepositoryMock;

    @Autowired
    private PracticeSessionMapper practiceSessionMapper;

    @Mock
    private PracticeSessionService practiceSessionServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPracticeSessionMockMvc;

    private PracticeSession practiceSession;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PracticeSession createEntity(EntityManager em) {
        PracticeSession practiceSession = new PracticeSession().tiltle(DEFAULT_TILTLE);
        return practiceSession;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PracticeSession createUpdatedEntity(EntityManager em) {
        PracticeSession practiceSession = new PracticeSession().tiltle(UPDATED_TILTLE);
        return practiceSession;
    }

    @BeforeEach
    public void initTest() {
        practiceSession = createEntity(em);
    }

    @Test
    @Transactional
    void createPracticeSession() throws Exception {
        int databaseSizeBeforeCreate = practiceSessionRepository.findAll().size();
        // Create the PracticeSession
        PracticeSessionDTO practiceSessionDTO = practiceSessionMapper.toDto(practiceSession);
        restPracticeSessionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(practiceSessionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PracticeSession in the database
        List<PracticeSession> practiceSessionList = practiceSessionRepository.findAll();
        assertThat(practiceSessionList).hasSize(databaseSizeBeforeCreate + 1);
        PracticeSession testPracticeSession = practiceSessionList.get(practiceSessionList.size() - 1);
        assertThat(testPracticeSession.getTiltle()).isEqualTo(DEFAULT_TILTLE);
    }

    @Test
    @Transactional
    void createPracticeSessionWithExistingId() throws Exception {
        // Create the PracticeSession with an existing ID
        practiceSession.setId(1L);
        PracticeSessionDTO practiceSessionDTO = practiceSessionMapper.toDto(practiceSession);

        int databaseSizeBeforeCreate = practiceSessionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPracticeSessionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(practiceSessionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PracticeSession in the database
        List<PracticeSession> practiceSessionList = practiceSessionRepository.findAll();
        assertThat(practiceSessionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPracticeSessions() throws Exception {
        // Initialize the database
        practiceSessionRepository.saveAndFlush(practiceSession);

        // Get all the practiceSessionList
        restPracticeSessionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(practiceSession.getId().intValue())))
            .andExpect(jsonPath("$.[*].tiltle").value(hasItem(DEFAULT_TILTLE)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPracticeSessionsWithEagerRelationshipsIsEnabled() throws Exception {
        when(practiceSessionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPracticeSessionMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(practiceSessionServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPracticeSessionsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(practiceSessionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPracticeSessionMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(practiceSessionServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getPracticeSession() throws Exception {
        // Initialize the database
        practiceSessionRepository.saveAndFlush(practiceSession);

        // Get the practiceSession
        restPracticeSessionMockMvc
            .perform(get(ENTITY_API_URL_ID, practiceSession.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(practiceSession.getId().intValue()))
            .andExpect(jsonPath("$.tiltle").value(DEFAULT_TILTLE));
    }

    @Test
    @Transactional
    void getNonExistingPracticeSession() throws Exception {
        // Get the practiceSession
        restPracticeSessionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPracticeSession() throws Exception {
        // Initialize the database
        practiceSessionRepository.saveAndFlush(practiceSession);

        int databaseSizeBeforeUpdate = practiceSessionRepository.findAll().size();

        // Update the practiceSession
        PracticeSession updatedPracticeSession = practiceSessionRepository.findById(practiceSession.getId()).get();
        // Disconnect from session so that the updates on updatedPracticeSession are not directly saved in db
        em.detach(updatedPracticeSession);
        updatedPracticeSession.tiltle(UPDATED_TILTLE);
        PracticeSessionDTO practiceSessionDTO = practiceSessionMapper.toDto(updatedPracticeSession);

        restPracticeSessionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, practiceSessionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(practiceSessionDTO))
            )
            .andExpect(status().isOk());

        // Validate the PracticeSession in the database
        List<PracticeSession> practiceSessionList = practiceSessionRepository.findAll();
        assertThat(practiceSessionList).hasSize(databaseSizeBeforeUpdate);
        PracticeSession testPracticeSession = practiceSessionList.get(practiceSessionList.size() - 1);
        assertThat(testPracticeSession.getTiltle()).isEqualTo(UPDATED_TILTLE);
    }

    @Test
    @Transactional
    void putNonExistingPracticeSession() throws Exception {
        int databaseSizeBeforeUpdate = practiceSessionRepository.findAll().size();
        practiceSession.setId(count.incrementAndGet());

        // Create the PracticeSession
        PracticeSessionDTO practiceSessionDTO = practiceSessionMapper.toDto(practiceSession);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPracticeSessionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, practiceSessionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(practiceSessionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PracticeSession in the database
        List<PracticeSession> practiceSessionList = practiceSessionRepository.findAll();
        assertThat(practiceSessionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPracticeSession() throws Exception {
        int databaseSizeBeforeUpdate = practiceSessionRepository.findAll().size();
        practiceSession.setId(count.incrementAndGet());

        // Create the PracticeSession
        PracticeSessionDTO practiceSessionDTO = practiceSessionMapper.toDto(practiceSession);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPracticeSessionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(practiceSessionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PracticeSession in the database
        List<PracticeSession> practiceSessionList = practiceSessionRepository.findAll();
        assertThat(practiceSessionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPracticeSession() throws Exception {
        int databaseSizeBeforeUpdate = practiceSessionRepository.findAll().size();
        practiceSession.setId(count.incrementAndGet());

        // Create the PracticeSession
        PracticeSessionDTO practiceSessionDTO = practiceSessionMapper.toDto(practiceSession);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPracticeSessionMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(practiceSessionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PracticeSession in the database
        List<PracticeSession> practiceSessionList = practiceSessionRepository.findAll();
        assertThat(practiceSessionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePracticeSessionWithPatch() throws Exception {
        // Initialize the database
        practiceSessionRepository.saveAndFlush(practiceSession);

        int databaseSizeBeforeUpdate = practiceSessionRepository.findAll().size();

        // Update the practiceSession using partial update
        PracticeSession partialUpdatedPracticeSession = new PracticeSession();
        partialUpdatedPracticeSession.setId(practiceSession.getId());

        restPracticeSessionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPracticeSession.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPracticeSession))
            )
            .andExpect(status().isOk());

        // Validate the PracticeSession in the database
        List<PracticeSession> practiceSessionList = practiceSessionRepository.findAll();
        assertThat(practiceSessionList).hasSize(databaseSizeBeforeUpdate);
        PracticeSession testPracticeSession = practiceSessionList.get(practiceSessionList.size() - 1);
        assertThat(testPracticeSession.getTiltle()).isEqualTo(DEFAULT_TILTLE);
    }

    @Test
    @Transactional
    void fullUpdatePracticeSessionWithPatch() throws Exception {
        // Initialize the database
        practiceSessionRepository.saveAndFlush(practiceSession);

        int databaseSizeBeforeUpdate = practiceSessionRepository.findAll().size();

        // Update the practiceSession using partial update
        PracticeSession partialUpdatedPracticeSession = new PracticeSession();
        partialUpdatedPracticeSession.setId(practiceSession.getId());

        partialUpdatedPracticeSession.tiltle(UPDATED_TILTLE);

        restPracticeSessionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPracticeSession.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPracticeSession))
            )
            .andExpect(status().isOk());

        // Validate the PracticeSession in the database
        List<PracticeSession> practiceSessionList = practiceSessionRepository.findAll();
        assertThat(practiceSessionList).hasSize(databaseSizeBeforeUpdate);
        PracticeSession testPracticeSession = practiceSessionList.get(practiceSessionList.size() - 1);
        assertThat(testPracticeSession.getTiltle()).isEqualTo(UPDATED_TILTLE);
    }

    @Test
    @Transactional
    void patchNonExistingPracticeSession() throws Exception {
        int databaseSizeBeforeUpdate = practiceSessionRepository.findAll().size();
        practiceSession.setId(count.incrementAndGet());

        // Create the PracticeSession
        PracticeSessionDTO practiceSessionDTO = practiceSessionMapper.toDto(practiceSession);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPracticeSessionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, practiceSessionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(practiceSessionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PracticeSession in the database
        List<PracticeSession> practiceSessionList = practiceSessionRepository.findAll();
        assertThat(practiceSessionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPracticeSession() throws Exception {
        int databaseSizeBeforeUpdate = practiceSessionRepository.findAll().size();
        practiceSession.setId(count.incrementAndGet());

        // Create the PracticeSession
        PracticeSessionDTO practiceSessionDTO = practiceSessionMapper.toDto(practiceSession);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPracticeSessionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(practiceSessionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PracticeSession in the database
        List<PracticeSession> practiceSessionList = practiceSessionRepository.findAll();
        assertThat(practiceSessionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPracticeSession() throws Exception {
        int databaseSizeBeforeUpdate = practiceSessionRepository.findAll().size();
        practiceSession.setId(count.incrementAndGet());

        // Create the PracticeSession
        PracticeSessionDTO practiceSessionDTO = practiceSessionMapper.toDto(practiceSession);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPracticeSessionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(practiceSessionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PracticeSession in the database
        List<PracticeSession> practiceSessionList = practiceSessionRepository.findAll();
        assertThat(practiceSessionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePracticeSession() throws Exception {
        // Initialize the database
        practiceSessionRepository.saveAndFlush(practiceSession);

        int databaseSizeBeforeDelete = practiceSessionRepository.findAll().size();

        // Delete the practiceSession
        restPracticeSessionMockMvc
            .perform(delete(ENTITY_API_URL_ID, practiceSession.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PracticeSession> practiceSessionList = practiceSessionRepository.findAll();
        assertThat(practiceSessionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

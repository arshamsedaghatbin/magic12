package com.papra.magic.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.papra.magic.IntegrationTest;
import com.papra.magic.domain.Practice;
import com.papra.magic.domain.enumeration.AccountType;
import com.papra.magic.repository.PracticeRepository;
import com.papra.magic.service.dto.PracticeDTO;
import com.papra.magic.service.mapper.PracticeMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link PracticeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PracticeResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_PHOTO_URL = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO_URL = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_VOICE_URL = "AAAAAAAAAA";
    private static final String UPDATED_VOICE_URL = "BBBBBBBBBB";

    private static final byte[] DEFAULT_VOICE_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_VOICE_FILE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_VOICE_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_VOICE_FILE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_MASTER_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_MASTER_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_MASTER_ADVICE = "AAAAAAAAAA";
    private static final String UPDATED_MASTER_ADVICE = "BBBBBBBBBB";

    private static final String DEFAULT_BRIEF_MASTER_ADVICE = "AAAAAAAAAA";
    private static final String UPDATED_BRIEF_MASTER_ADVICE = "BBBBBBBBBB";

    private static final AccountType DEFAULT_ACCOUNT_TYPE = AccountType.FREE;
    private static final AccountType UPDATED_ACCOUNT_TYPE = AccountType.GOLD;

    private static final String ENTITY_API_URL = "/api/practices";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PracticeRepository practiceRepository;

    @Autowired
    private PracticeMapper practiceMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPracticeMockMvc;

    private Practice practice;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Practice createEntity(EntityManager em) {
        Practice practice = new Practice()
            .title(DEFAULT_TITLE)
            .photoUrl(DEFAULT_PHOTO_URL)
            .photo(DEFAULT_PHOTO)
            .photoContentType(DEFAULT_PHOTO_CONTENT_TYPE)
            .voiceUrl(DEFAULT_VOICE_URL)
            .voiceFile(DEFAULT_VOICE_FILE)
            .voiceFileContentType(DEFAULT_VOICE_FILE_CONTENT_TYPE)
            .masterDescription(DEFAULT_MASTER_DESCRIPTION)
            .masterAdvice(DEFAULT_MASTER_ADVICE)
            .briefMasterAdvice(DEFAULT_BRIEF_MASTER_ADVICE)
            .accountType(DEFAULT_ACCOUNT_TYPE);
        return practice;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Practice createUpdatedEntity(EntityManager em) {
        Practice practice = new Practice()
            .title(UPDATED_TITLE)
            .photoUrl(UPDATED_PHOTO_URL)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .voiceUrl(UPDATED_VOICE_URL)
            .voiceFile(UPDATED_VOICE_FILE)
            .voiceFileContentType(UPDATED_VOICE_FILE_CONTENT_TYPE)
            .masterDescription(UPDATED_MASTER_DESCRIPTION)
            .masterAdvice(UPDATED_MASTER_ADVICE)
            .briefMasterAdvice(UPDATED_BRIEF_MASTER_ADVICE)
            .accountType(UPDATED_ACCOUNT_TYPE);
        return practice;
    }

    @BeforeEach
    public void initTest() {
        practice = createEntity(em);
    }

    @Test
    @Transactional
    void createPractice() throws Exception {
        int databaseSizeBeforeCreate = practiceRepository.findAll().size();
        // Create the Practice
        PracticeDTO practiceDTO = practiceMapper.toDto(practice);
        restPracticeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(practiceDTO)))
            .andExpect(status().isCreated());

        // Validate the Practice in the database
        List<Practice> practiceList = practiceRepository.findAll();
        assertThat(practiceList).hasSize(databaseSizeBeforeCreate + 1);
        Practice testPractice = practiceList.get(practiceList.size() - 1);
        assertThat(testPractice.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testPractice.getPhotoUrl()).isEqualTo(DEFAULT_PHOTO_URL);
        assertThat(testPractice.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testPractice.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
        assertThat(testPractice.getVoiceUrl()).isEqualTo(DEFAULT_VOICE_URL);
        assertThat(testPractice.getVoiceFile()).isEqualTo(DEFAULT_VOICE_FILE);
        assertThat(testPractice.getVoiceFileContentType()).isEqualTo(DEFAULT_VOICE_FILE_CONTENT_TYPE);
        assertThat(testPractice.getMasterDescription()).isEqualTo(DEFAULT_MASTER_DESCRIPTION);
        assertThat(testPractice.getMasterAdvice()).isEqualTo(DEFAULT_MASTER_ADVICE);
        assertThat(testPractice.getBriefMasterAdvice()).isEqualTo(DEFAULT_BRIEF_MASTER_ADVICE);
        assertThat(testPractice.getAccountType()).isEqualTo(DEFAULT_ACCOUNT_TYPE);
    }

    @Test
    @Transactional
    void createPracticeWithExistingId() throws Exception {
        // Create the Practice with an existing ID
        practice.setId(1L);
        PracticeDTO practiceDTO = practiceMapper.toDto(practice);

        int databaseSizeBeforeCreate = practiceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPracticeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(practiceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Practice in the database
        List<Practice> practiceList = practiceRepository.findAll();
        assertThat(practiceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPractices() throws Exception {
        // Initialize the database
        practiceRepository.saveAndFlush(practice);

        // Get all the practiceList
        restPracticeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(practice.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].photoUrl").value(hasItem(DEFAULT_PHOTO_URL)))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))))
            .andExpect(jsonPath("$.[*].voiceUrl").value(hasItem(DEFAULT_VOICE_URL)))
            .andExpect(jsonPath("$.[*].voiceFileContentType").value(hasItem(DEFAULT_VOICE_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].voiceFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_VOICE_FILE))))
            .andExpect(jsonPath("$.[*].masterDescription").value(hasItem(DEFAULT_MASTER_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].masterAdvice").value(hasItem(DEFAULT_MASTER_ADVICE)))
            .andExpect(jsonPath("$.[*].briefMasterAdvice").value(hasItem(DEFAULT_BRIEF_MASTER_ADVICE)))
            .andExpect(jsonPath("$.[*].accountType").value(hasItem(DEFAULT_ACCOUNT_TYPE.toString())));
    }

    @Test
    @Transactional
    void getPractice() throws Exception {
        // Initialize the database
        practiceRepository.saveAndFlush(practice);

        // Get the practice
        restPracticeMockMvc
            .perform(get(ENTITY_API_URL_ID, practice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(practice.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.photoUrl").value(DEFAULT_PHOTO_URL))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64Utils.encodeToString(DEFAULT_PHOTO)))
            .andExpect(jsonPath("$.voiceUrl").value(DEFAULT_VOICE_URL))
            .andExpect(jsonPath("$.voiceFileContentType").value(DEFAULT_VOICE_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.voiceFile").value(Base64Utils.encodeToString(DEFAULT_VOICE_FILE)))
            .andExpect(jsonPath("$.masterDescription").value(DEFAULT_MASTER_DESCRIPTION))
            .andExpect(jsonPath("$.masterAdvice").value(DEFAULT_MASTER_ADVICE))
            .andExpect(jsonPath("$.briefMasterAdvice").value(DEFAULT_BRIEF_MASTER_ADVICE))
            .andExpect(jsonPath("$.accountType").value(DEFAULT_ACCOUNT_TYPE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPractice() throws Exception {
        // Get the practice
        restPracticeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPractice() throws Exception {
        // Initialize the database
        practiceRepository.saveAndFlush(practice);

        int databaseSizeBeforeUpdate = practiceRepository.findAll().size();

        // Update the practice
        Practice updatedPractice = practiceRepository.findById(practice.getId()).get();
        // Disconnect from session so that the updates on updatedPractice are not directly saved in db
        em.detach(updatedPractice);
        updatedPractice
            .title(UPDATED_TITLE)
            .photoUrl(UPDATED_PHOTO_URL)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .voiceUrl(UPDATED_VOICE_URL)
            .voiceFile(UPDATED_VOICE_FILE)
            .voiceFileContentType(UPDATED_VOICE_FILE_CONTENT_TYPE)
            .masterDescription(UPDATED_MASTER_DESCRIPTION)
            .masterAdvice(UPDATED_MASTER_ADVICE)
            .briefMasterAdvice(UPDATED_BRIEF_MASTER_ADVICE)
            .accountType(UPDATED_ACCOUNT_TYPE);
        PracticeDTO practiceDTO = practiceMapper.toDto(updatedPractice);

        restPracticeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, practiceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(practiceDTO))
            )
            .andExpect(status().isOk());

        // Validate the Practice in the database
        List<Practice> practiceList = practiceRepository.findAll();
        assertThat(practiceList).hasSize(databaseSizeBeforeUpdate);
        Practice testPractice = practiceList.get(practiceList.size() - 1);
        assertThat(testPractice.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testPractice.getPhotoUrl()).isEqualTo(UPDATED_PHOTO_URL);
        assertThat(testPractice.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testPractice.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
        assertThat(testPractice.getVoiceUrl()).isEqualTo(UPDATED_VOICE_URL);
        assertThat(testPractice.getVoiceFile()).isEqualTo(UPDATED_VOICE_FILE);
        assertThat(testPractice.getVoiceFileContentType()).isEqualTo(UPDATED_VOICE_FILE_CONTENT_TYPE);
        assertThat(testPractice.getMasterDescription()).isEqualTo(UPDATED_MASTER_DESCRIPTION);
        assertThat(testPractice.getMasterAdvice()).isEqualTo(UPDATED_MASTER_ADVICE);
        assertThat(testPractice.getBriefMasterAdvice()).isEqualTo(UPDATED_BRIEF_MASTER_ADVICE);
        assertThat(testPractice.getAccountType()).isEqualTo(UPDATED_ACCOUNT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingPractice() throws Exception {
        int databaseSizeBeforeUpdate = practiceRepository.findAll().size();
        practice.setId(count.incrementAndGet());

        // Create the Practice
        PracticeDTO practiceDTO = practiceMapper.toDto(practice);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPracticeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, practiceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(practiceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Practice in the database
        List<Practice> practiceList = practiceRepository.findAll();
        assertThat(practiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPractice() throws Exception {
        int databaseSizeBeforeUpdate = practiceRepository.findAll().size();
        practice.setId(count.incrementAndGet());

        // Create the Practice
        PracticeDTO practiceDTO = practiceMapper.toDto(practice);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPracticeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(practiceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Practice in the database
        List<Practice> practiceList = practiceRepository.findAll();
        assertThat(practiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPractice() throws Exception {
        int databaseSizeBeforeUpdate = practiceRepository.findAll().size();
        practice.setId(count.incrementAndGet());

        // Create the Practice
        PracticeDTO practiceDTO = practiceMapper.toDto(practice);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPracticeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(practiceDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Practice in the database
        List<Practice> practiceList = practiceRepository.findAll();
        assertThat(practiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePracticeWithPatch() throws Exception {
        // Initialize the database
        practiceRepository.saveAndFlush(practice);

        int databaseSizeBeforeUpdate = practiceRepository.findAll().size();

        // Update the practice using partial update
        Practice partialUpdatedPractice = new Practice();
        partialUpdatedPractice.setId(practice.getId());

        partialUpdatedPractice
            .title(UPDATED_TITLE)
            .photoUrl(UPDATED_PHOTO_URL)
            .voiceUrl(UPDATED_VOICE_URL)
            .masterAdvice(UPDATED_MASTER_ADVICE)
            .briefMasterAdvice(UPDATED_BRIEF_MASTER_ADVICE)
            .accountType(UPDATED_ACCOUNT_TYPE);

        restPracticeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPractice.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPractice))
            )
            .andExpect(status().isOk());

        // Validate the Practice in the database
        List<Practice> practiceList = practiceRepository.findAll();
        assertThat(practiceList).hasSize(databaseSizeBeforeUpdate);
        Practice testPractice = practiceList.get(practiceList.size() - 1);
        assertThat(testPractice.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testPractice.getPhotoUrl()).isEqualTo(UPDATED_PHOTO_URL);
        assertThat(testPractice.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testPractice.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
        assertThat(testPractice.getVoiceUrl()).isEqualTo(UPDATED_VOICE_URL);
        assertThat(testPractice.getVoiceFile()).isEqualTo(DEFAULT_VOICE_FILE);
        assertThat(testPractice.getVoiceFileContentType()).isEqualTo(DEFAULT_VOICE_FILE_CONTENT_TYPE);
        assertThat(testPractice.getMasterDescription()).isEqualTo(DEFAULT_MASTER_DESCRIPTION);
        assertThat(testPractice.getMasterAdvice()).isEqualTo(UPDATED_MASTER_ADVICE);
        assertThat(testPractice.getBriefMasterAdvice()).isEqualTo(UPDATED_BRIEF_MASTER_ADVICE);
        assertThat(testPractice.getAccountType()).isEqualTo(UPDATED_ACCOUNT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdatePracticeWithPatch() throws Exception {
        // Initialize the database
        practiceRepository.saveAndFlush(practice);

        int databaseSizeBeforeUpdate = practiceRepository.findAll().size();

        // Update the practice using partial update
        Practice partialUpdatedPractice = new Practice();
        partialUpdatedPractice.setId(practice.getId());

        partialUpdatedPractice
            .title(UPDATED_TITLE)
            .photoUrl(UPDATED_PHOTO_URL)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .voiceUrl(UPDATED_VOICE_URL)
            .voiceFile(UPDATED_VOICE_FILE)
            .voiceFileContentType(UPDATED_VOICE_FILE_CONTENT_TYPE)
            .masterDescription(UPDATED_MASTER_DESCRIPTION)
            .masterAdvice(UPDATED_MASTER_ADVICE)
            .briefMasterAdvice(UPDATED_BRIEF_MASTER_ADVICE)
            .accountType(UPDATED_ACCOUNT_TYPE);

        restPracticeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPractice.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPractice))
            )
            .andExpect(status().isOk());

        // Validate the Practice in the database
        List<Practice> practiceList = practiceRepository.findAll();
        assertThat(practiceList).hasSize(databaseSizeBeforeUpdate);
        Practice testPractice = practiceList.get(practiceList.size() - 1);
        assertThat(testPractice.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testPractice.getPhotoUrl()).isEqualTo(UPDATED_PHOTO_URL);
        assertThat(testPractice.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testPractice.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
        assertThat(testPractice.getVoiceUrl()).isEqualTo(UPDATED_VOICE_URL);
        assertThat(testPractice.getVoiceFile()).isEqualTo(UPDATED_VOICE_FILE);
        assertThat(testPractice.getVoiceFileContentType()).isEqualTo(UPDATED_VOICE_FILE_CONTENT_TYPE);
        assertThat(testPractice.getMasterDescription()).isEqualTo(UPDATED_MASTER_DESCRIPTION);
        assertThat(testPractice.getMasterAdvice()).isEqualTo(UPDATED_MASTER_ADVICE);
        assertThat(testPractice.getBriefMasterAdvice()).isEqualTo(UPDATED_BRIEF_MASTER_ADVICE);
        assertThat(testPractice.getAccountType()).isEqualTo(UPDATED_ACCOUNT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingPractice() throws Exception {
        int databaseSizeBeforeUpdate = practiceRepository.findAll().size();
        practice.setId(count.incrementAndGet());

        // Create the Practice
        PracticeDTO practiceDTO = practiceMapper.toDto(practice);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPracticeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, practiceDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(practiceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Practice in the database
        List<Practice> practiceList = practiceRepository.findAll();
        assertThat(practiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPractice() throws Exception {
        int databaseSizeBeforeUpdate = practiceRepository.findAll().size();
        practice.setId(count.incrementAndGet());

        // Create the Practice
        PracticeDTO practiceDTO = practiceMapper.toDto(practice);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPracticeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(practiceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Practice in the database
        List<Practice> practiceList = practiceRepository.findAll();
        assertThat(practiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPractice() throws Exception {
        int databaseSizeBeforeUpdate = practiceRepository.findAll().size();
        practice.setId(count.incrementAndGet());

        // Create the Practice
        PracticeDTO practiceDTO = practiceMapper.toDto(practice);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPracticeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(practiceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Practice in the database
        List<Practice> practiceList = practiceRepository.findAll();
        assertThat(practiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePractice() throws Exception {
        // Initialize the database
        practiceRepository.saveAndFlush(practice);

        int databaseSizeBeforeDelete = practiceRepository.findAll().size();

        // Delete the practice
        restPracticeMockMvc
            .perform(delete(ENTITY_API_URL_ID, practice.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Practice> practiceList = practiceRepository.findAll();
        assertThat(practiceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

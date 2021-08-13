package com.papra.magic.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.papra.magic.IntegrationTest;
import com.papra.magic.domain.SubCategory;
import com.papra.magic.domain.enumeration.AccountType;
import com.papra.magic.repository.SubCategoryRepository;
import com.papra.magic.service.SubCategoryService;
import com.papra.magic.service.dto.SubCategoryDTO;
import com.papra.magic.service.mapper.SubCategoryMapper;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link SubCategoryResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class SubCategoryResourceIT {

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

    private static final AccountType DEFAULT_ACCOUNT_TYPE = AccountType.FREE;
    private static final AccountType UPDATED_ACCOUNT_TYPE = AccountType.GOLD;

    private static final String ENTITY_API_URL = "/api/sub-categories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Mock
    private SubCategoryRepository subCategoryRepositoryMock;

    @Autowired
    private SubCategoryMapper subCategoryMapper;

    @Mock
    private SubCategoryService subCategoryServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSubCategoryMockMvc;

    private SubCategory subCategory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubCategory createEntity(EntityManager em) {
        SubCategory subCategory = new SubCategory()
            .title(DEFAULT_TITLE)
            .photoUrl(DEFAULT_PHOTO_URL)
            .photo(DEFAULT_PHOTO)
            .photoContentType(DEFAULT_PHOTO_CONTENT_TYPE)
            .voiceUrl(DEFAULT_VOICE_URL)
            .voiceFile(DEFAULT_VOICE_FILE)
            .voiceFileContentType(DEFAULT_VOICE_FILE_CONTENT_TYPE)
            .masterDescription(DEFAULT_MASTER_DESCRIPTION)
            .masterAdvice(DEFAULT_MASTER_ADVICE)
            .accountType(DEFAULT_ACCOUNT_TYPE);
        return subCategory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubCategory createUpdatedEntity(EntityManager em) {
        SubCategory subCategory = new SubCategory()
            .title(UPDATED_TITLE)
            .photoUrl(UPDATED_PHOTO_URL)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .voiceUrl(UPDATED_VOICE_URL)
            .voiceFile(UPDATED_VOICE_FILE)
            .voiceFileContentType(UPDATED_VOICE_FILE_CONTENT_TYPE)
            .masterDescription(UPDATED_MASTER_DESCRIPTION)
            .masterAdvice(UPDATED_MASTER_ADVICE)
            .accountType(UPDATED_ACCOUNT_TYPE);
        return subCategory;
    }

    @BeforeEach
    public void initTest() {
        subCategory = createEntity(em);
    }

    @Test
    @Transactional
    void createSubCategory() throws Exception {
        int databaseSizeBeforeCreate = subCategoryRepository.findAll().size();
        // Create the SubCategory
        SubCategoryDTO subCategoryDTO = subCategoryMapper.toDto(subCategory);
        restSubCategoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subCategoryDTO))
            )
            .andExpect(status().isCreated());

        // Validate the SubCategory in the database
        List<SubCategory> subCategoryList = subCategoryRepository.findAll();
        assertThat(subCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        SubCategory testSubCategory = subCategoryList.get(subCategoryList.size() - 1);
        assertThat(testSubCategory.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testSubCategory.getPhotoUrl()).isEqualTo(DEFAULT_PHOTO_URL);
        assertThat(testSubCategory.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testSubCategory.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
        assertThat(testSubCategory.getVoiceUrl()).isEqualTo(DEFAULT_VOICE_URL);
        assertThat(testSubCategory.getVoiceFile()).isEqualTo(DEFAULT_VOICE_FILE);
        assertThat(testSubCategory.getVoiceFileContentType()).isEqualTo(DEFAULT_VOICE_FILE_CONTENT_TYPE);
        assertThat(testSubCategory.getMasterDescription()).isEqualTo(DEFAULT_MASTER_DESCRIPTION);
        assertThat(testSubCategory.getMasterAdvice()).isEqualTo(DEFAULT_MASTER_ADVICE);
        assertThat(testSubCategory.getAccountType()).isEqualTo(DEFAULT_ACCOUNT_TYPE);
    }

    @Test
    @Transactional
    void createSubCategoryWithExistingId() throws Exception {
        // Create the SubCategory with an existing ID
        subCategory.setId(1L);
        SubCategoryDTO subCategoryDTO = subCategoryMapper.toDto(subCategory);

        int databaseSizeBeforeCreate = subCategoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubCategoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubCategory in the database
        List<SubCategory> subCategoryList = subCategoryRepository.findAll();
        assertThat(subCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSubCategories() throws Exception {
        // Initialize the database
        subCategoryRepository.saveAndFlush(subCategory);

        // Get all the subCategoryList
        restSubCategoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].photoUrl").value(hasItem(DEFAULT_PHOTO_URL)))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))))
            .andExpect(jsonPath("$.[*].voiceUrl").value(hasItem(DEFAULT_VOICE_URL)))
            .andExpect(jsonPath("$.[*].voiceFileContentType").value(hasItem(DEFAULT_VOICE_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].voiceFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_VOICE_FILE))))
            .andExpect(jsonPath("$.[*].masterDescription").value(hasItem(DEFAULT_MASTER_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].masterAdvice").value(hasItem(DEFAULT_MASTER_ADVICE)))
            .andExpect(jsonPath("$.[*].accountType").value(hasItem(DEFAULT_ACCOUNT_TYPE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSubCategoriesWithEagerRelationshipsIsEnabled() throws Exception {
        when(subCategoryServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSubCategoryMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(subCategoryServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSubCategoriesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(subCategoryServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSubCategoryMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(subCategoryServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getSubCategory() throws Exception {
        // Initialize the database
        subCategoryRepository.saveAndFlush(subCategory);

        // Get the subCategory
        restSubCategoryMockMvc
            .perform(get(ENTITY_API_URL_ID, subCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(subCategory.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.photoUrl").value(DEFAULT_PHOTO_URL))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64Utils.encodeToString(DEFAULT_PHOTO)))
            .andExpect(jsonPath("$.voiceUrl").value(DEFAULT_VOICE_URL))
            .andExpect(jsonPath("$.voiceFileContentType").value(DEFAULT_VOICE_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.voiceFile").value(Base64Utils.encodeToString(DEFAULT_VOICE_FILE)))
            .andExpect(jsonPath("$.masterDescription").value(DEFAULT_MASTER_DESCRIPTION))
            .andExpect(jsonPath("$.masterAdvice").value(DEFAULT_MASTER_ADVICE))
            .andExpect(jsonPath("$.accountType").value(DEFAULT_ACCOUNT_TYPE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingSubCategory() throws Exception {
        // Get the subCategory
        restSubCategoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSubCategory() throws Exception {
        // Initialize the database
        subCategoryRepository.saveAndFlush(subCategory);

        int databaseSizeBeforeUpdate = subCategoryRepository.findAll().size();

        // Update the subCategory
        SubCategory updatedSubCategory = subCategoryRepository.findById(subCategory.getId()).get();
        // Disconnect from session so that the updates on updatedSubCategory are not directly saved in db
        em.detach(updatedSubCategory);
        updatedSubCategory
            .title(UPDATED_TITLE)
            .photoUrl(UPDATED_PHOTO_URL)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .voiceUrl(UPDATED_VOICE_URL)
            .voiceFile(UPDATED_VOICE_FILE)
            .voiceFileContentType(UPDATED_VOICE_FILE_CONTENT_TYPE)
            .masterDescription(UPDATED_MASTER_DESCRIPTION)
            .masterAdvice(UPDATED_MASTER_ADVICE)
            .accountType(UPDATED_ACCOUNT_TYPE);
        SubCategoryDTO subCategoryDTO = subCategoryMapper.toDto(updatedSubCategory);

        restSubCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, subCategoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subCategoryDTO))
            )
            .andExpect(status().isOk());

        // Validate the SubCategory in the database
        List<SubCategory> subCategoryList = subCategoryRepository.findAll();
        assertThat(subCategoryList).hasSize(databaseSizeBeforeUpdate);
        SubCategory testSubCategory = subCategoryList.get(subCategoryList.size() - 1);
        assertThat(testSubCategory.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testSubCategory.getPhotoUrl()).isEqualTo(UPDATED_PHOTO_URL);
        assertThat(testSubCategory.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testSubCategory.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
        assertThat(testSubCategory.getVoiceUrl()).isEqualTo(UPDATED_VOICE_URL);
        assertThat(testSubCategory.getVoiceFile()).isEqualTo(UPDATED_VOICE_FILE);
        assertThat(testSubCategory.getVoiceFileContentType()).isEqualTo(UPDATED_VOICE_FILE_CONTENT_TYPE);
        assertThat(testSubCategory.getMasterDescription()).isEqualTo(UPDATED_MASTER_DESCRIPTION);
        assertThat(testSubCategory.getMasterAdvice()).isEqualTo(UPDATED_MASTER_ADVICE);
        assertThat(testSubCategory.getAccountType()).isEqualTo(UPDATED_ACCOUNT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingSubCategory() throws Exception {
        int databaseSizeBeforeUpdate = subCategoryRepository.findAll().size();
        subCategory.setId(count.incrementAndGet());

        // Create the SubCategory
        SubCategoryDTO subCategoryDTO = subCategoryMapper.toDto(subCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, subCategoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubCategory in the database
        List<SubCategory> subCategoryList = subCategoryRepository.findAll();
        assertThat(subCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSubCategory() throws Exception {
        int databaseSizeBeforeUpdate = subCategoryRepository.findAll().size();
        subCategory.setId(count.incrementAndGet());

        // Create the SubCategory
        SubCategoryDTO subCategoryDTO = subCategoryMapper.toDto(subCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubCategory in the database
        List<SubCategory> subCategoryList = subCategoryRepository.findAll();
        assertThat(subCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSubCategory() throws Exception {
        int databaseSizeBeforeUpdate = subCategoryRepository.findAll().size();
        subCategory.setId(count.incrementAndGet());

        // Create the SubCategory
        SubCategoryDTO subCategoryDTO = subCategoryMapper.toDto(subCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubCategoryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subCategoryDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SubCategory in the database
        List<SubCategory> subCategoryList = subCategoryRepository.findAll();
        assertThat(subCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSubCategoryWithPatch() throws Exception {
        // Initialize the database
        subCategoryRepository.saveAndFlush(subCategory);

        int databaseSizeBeforeUpdate = subCategoryRepository.findAll().size();

        // Update the subCategory using partial update
        SubCategory partialUpdatedSubCategory = new SubCategory();
        partialUpdatedSubCategory.setId(subCategory.getId());

        partialUpdatedSubCategory
            .title(UPDATED_TITLE)
            .voiceUrl(UPDATED_VOICE_URL)
            .masterAdvice(UPDATED_MASTER_ADVICE)
            .accountType(UPDATED_ACCOUNT_TYPE);

        restSubCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSubCategory))
            )
            .andExpect(status().isOk());

        // Validate the SubCategory in the database
        List<SubCategory> subCategoryList = subCategoryRepository.findAll();
        assertThat(subCategoryList).hasSize(databaseSizeBeforeUpdate);
        SubCategory testSubCategory = subCategoryList.get(subCategoryList.size() - 1);
        assertThat(testSubCategory.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testSubCategory.getPhotoUrl()).isEqualTo(DEFAULT_PHOTO_URL);
        assertThat(testSubCategory.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testSubCategory.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
        assertThat(testSubCategory.getVoiceUrl()).isEqualTo(UPDATED_VOICE_URL);
        assertThat(testSubCategory.getVoiceFile()).isEqualTo(DEFAULT_VOICE_FILE);
        assertThat(testSubCategory.getVoiceFileContentType()).isEqualTo(DEFAULT_VOICE_FILE_CONTENT_TYPE);
        assertThat(testSubCategory.getMasterDescription()).isEqualTo(DEFAULT_MASTER_DESCRIPTION);
        assertThat(testSubCategory.getMasterAdvice()).isEqualTo(UPDATED_MASTER_ADVICE);
        assertThat(testSubCategory.getAccountType()).isEqualTo(UPDATED_ACCOUNT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateSubCategoryWithPatch() throws Exception {
        // Initialize the database
        subCategoryRepository.saveAndFlush(subCategory);

        int databaseSizeBeforeUpdate = subCategoryRepository.findAll().size();

        // Update the subCategory using partial update
        SubCategory partialUpdatedSubCategory = new SubCategory();
        partialUpdatedSubCategory.setId(subCategory.getId());

        partialUpdatedSubCategory
            .title(UPDATED_TITLE)
            .photoUrl(UPDATED_PHOTO_URL)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .voiceUrl(UPDATED_VOICE_URL)
            .voiceFile(UPDATED_VOICE_FILE)
            .voiceFileContentType(UPDATED_VOICE_FILE_CONTENT_TYPE)
            .masterDescription(UPDATED_MASTER_DESCRIPTION)
            .masterAdvice(UPDATED_MASTER_ADVICE)
            .accountType(UPDATED_ACCOUNT_TYPE);

        restSubCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSubCategory))
            )
            .andExpect(status().isOk());

        // Validate the SubCategory in the database
        List<SubCategory> subCategoryList = subCategoryRepository.findAll();
        assertThat(subCategoryList).hasSize(databaseSizeBeforeUpdate);
        SubCategory testSubCategory = subCategoryList.get(subCategoryList.size() - 1);
        assertThat(testSubCategory.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testSubCategory.getPhotoUrl()).isEqualTo(UPDATED_PHOTO_URL);
        assertThat(testSubCategory.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testSubCategory.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
        assertThat(testSubCategory.getVoiceUrl()).isEqualTo(UPDATED_VOICE_URL);
        assertThat(testSubCategory.getVoiceFile()).isEqualTo(UPDATED_VOICE_FILE);
        assertThat(testSubCategory.getVoiceFileContentType()).isEqualTo(UPDATED_VOICE_FILE_CONTENT_TYPE);
        assertThat(testSubCategory.getMasterDescription()).isEqualTo(UPDATED_MASTER_DESCRIPTION);
        assertThat(testSubCategory.getMasterAdvice()).isEqualTo(UPDATED_MASTER_ADVICE);
        assertThat(testSubCategory.getAccountType()).isEqualTo(UPDATED_ACCOUNT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingSubCategory() throws Exception {
        int databaseSizeBeforeUpdate = subCategoryRepository.findAll().size();
        subCategory.setId(count.incrementAndGet());

        // Create the SubCategory
        SubCategoryDTO subCategoryDTO = subCategoryMapper.toDto(subCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, subCategoryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(subCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubCategory in the database
        List<SubCategory> subCategoryList = subCategoryRepository.findAll();
        assertThat(subCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSubCategory() throws Exception {
        int databaseSizeBeforeUpdate = subCategoryRepository.findAll().size();
        subCategory.setId(count.incrementAndGet());

        // Create the SubCategory
        SubCategoryDTO subCategoryDTO = subCategoryMapper.toDto(subCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(subCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubCategory in the database
        List<SubCategory> subCategoryList = subCategoryRepository.findAll();
        assertThat(subCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSubCategory() throws Exception {
        int databaseSizeBeforeUpdate = subCategoryRepository.findAll().size();
        subCategory.setId(count.incrementAndGet());

        // Create the SubCategory
        SubCategoryDTO subCategoryDTO = subCategoryMapper.toDto(subCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(subCategoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SubCategory in the database
        List<SubCategory> subCategoryList = subCategoryRepository.findAll();
        assertThat(subCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSubCategory() throws Exception {
        // Initialize the database
        subCategoryRepository.saveAndFlush(subCategory);

        int databaseSizeBeforeDelete = subCategoryRepository.findAll().size();

        // Delete the subCategory
        restSubCategoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, subCategory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SubCategory> subCategoryList = subCategoryRepository.findAll();
        assertThat(subCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

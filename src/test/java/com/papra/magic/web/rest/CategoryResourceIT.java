package com.papra.magic.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.papra.magic.IntegrationTest;
import com.papra.magic.domain.Category;
import com.papra.magic.domain.enumeration.AccountType;
import com.papra.magic.repository.CategoryRepository;
import com.papra.magic.service.CategoryService;
import com.papra.magic.service.dto.CategoryDTO;
import com.papra.magic.service.mapper.CategoryMapper;
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
 * Integration tests for the {@link CategoryResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CategoryResourceIT {

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

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_ADVICE = "AAAAAAAAAA";
    private static final String UPDATED_ADVICE = "BBBBBBBBBB";

    private static final AccountType DEFAULT_ACCOUNT_TYPE = AccountType.FREE;
    private static final AccountType UPDATED_ACCOUNT_TYPE = AccountType.GOLD;

    private static final String ENTITY_API_URL = "/api/categories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryRepository categoryRepositoryMock;

    @Autowired
    private CategoryMapper categoryMapper;

    @Mock
    private CategoryService categoryServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCategoryMockMvc;

    private Category category;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Category createEntity(EntityManager em) {
        Category category = new Category()
            .title(DEFAULT_TITLE)
            .photoUrl(DEFAULT_PHOTO_URL)
            .photo(DEFAULT_PHOTO)
            .photoContentType(DEFAULT_PHOTO_CONTENT_TYPE)
            .voiceUrl(DEFAULT_VOICE_URL)
            .voiceFile(DEFAULT_VOICE_FILE)
            .voiceFileContentType(DEFAULT_VOICE_FILE_CONTENT_TYPE)
            .description(DEFAULT_DESCRIPTION)
            .advice(DEFAULT_ADVICE)
            .accountType(DEFAULT_ACCOUNT_TYPE);
        return category;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Category createUpdatedEntity(EntityManager em) {
        Category category = new Category()
            .title(UPDATED_TITLE)
            .photoUrl(UPDATED_PHOTO_URL)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .voiceUrl(UPDATED_VOICE_URL)
            .voiceFile(UPDATED_VOICE_FILE)
            .voiceFileContentType(UPDATED_VOICE_FILE_CONTENT_TYPE)
            .description(UPDATED_DESCRIPTION)
            .advice(UPDATED_ADVICE)
            .accountType(UPDATED_ACCOUNT_TYPE);
        return category;
    }

    @BeforeEach
    public void initTest() {
        category = createEntity(em);
    }

    @Test
    @Transactional
    void createCategory() throws Exception {
        int databaseSizeBeforeCreate = categoryRepository.findAll().size();
        // Create the Category
        CategoryDTO categoryDTO = categoryMapper.toDto(category);
        restCategoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categoryDTO)))
            .andExpect(status().isCreated());

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeCreate + 1);
        Category testCategory = categoryList.get(categoryList.size() - 1);
        assertThat(testCategory.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testCategory.getPhotoUrl()).isEqualTo(DEFAULT_PHOTO_URL);
        assertThat(testCategory.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testCategory.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
        assertThat(testCategory.getVoiceUrl()).isEqualTo(DEFAULT_VOICE_URL);
        assertThat(testCategory.getVoiceFile()).isEqualTo(DEFAULT_VOICE_FILE);
        assertThat(testCategory.getVoiceFileContentType()).isEqualTo(DEFAULT_VOICE_FILE_CONTENT_TYPE);
        assertThat(testCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCategory.getAdvice()).isEqualTo(DEFAULT_ADVICE);
        assertThat(testCategory.getAccountType()).isEqualTo(DEFAULT_ACCOUNT_TYPE);
    }

    @Test
    @Transactional
    void createCategoryWithExistingId() throws Exception {
        // Create the Category with an existing ID
        category.setId(1L);
        CategoryDTO categoryDTO = categoryMapper.toDto(category);

        int databaseSizeBeforeCreate = categoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCategories() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList
        restCategoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(category.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].photoUrl").value(hasItem(DEFAULT_PHOTO_URL)))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))))
            .andExpect(jsonPath("$.[*].voiceUrl").value(hasItem(DEFAULT_VOICE_URL)))
            .andExpect(jsonPath("$.[*].voiceFileContentType").value(hasItem(DEFAULT_VOICE_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].voiceFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_VOICE_FILE))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].advice").value(hasItem(DEFAULT_ADVICE)))
            .andExpect(jsonPath("$.[*].accountType").value(hasItem(DEFAULT_ACCOUNT_TYPE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCategoriesWithEagerRelationshipsIsEnabled() throws Exception {
        when(categoryServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCategoryMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(categoryServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCategoriesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(categoryServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCategoryMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(categoryServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getCategory() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get the category
        restCategoryMockMvc
            .perform(get(ENTITY_API_URL_ID, category.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(category.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.photoUrl").value(DEFAULT_PHOTO_URL))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64Utils.encodeToString(DEFAULT_PHOTO)))
            .andExpect(jsonPath("$.voiceUrl").value(DEFAULT_VOICE_URL))
            .andExpect(jsonPath("$.voiceFileContentType").value(DEFAULT_VOICE_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.voiceFile").value(Base64Utils.encodeToString(DEFAULT_VOICE_FILE)))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.advice").value(DEFAULT_ADVICE))
            .andExpect(jsonPath("$.accountType").value(DEFAULT_ACCOUNT_TYPE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCategory() throws Exception {
        // Get the category
        restCategoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCategory() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        int databaseSizeBeforeUpdate = categoryRepository.findAll().size();

        // Update the category
        Category updatedCategory = categoryRepository.findById(category.getId()).get();
        // Disconnect from session so that the updates on updatedCategory are not directly saved in db
        em.detach(updatedCategory);
        updatedCategory
            .title(UPDATED_TITLE)
            .photoUrl(UPDATED_PHOTO_URL)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .voiceUrl(UPDATED_VOICE_URL)
            .voiceFile(UPDATED_VOICE_FILE)
            .voiceFileContentType(UPDATED_VOICE_FILE_CONTENT_TYPE)
            .description(UPDATED_DESCRIPTION)
            .advice(UPDATED_ADVICE)
            .accountType(UPDATED_ACCOUNT_TYPE);
        CategoryDTO categoryDTO = categoryMapper.toDto(updatedCategory);

        restCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, categoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categoryDTO))
            )
            .andExpect(status().isOk());

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeUpdate);
        Category testCategory = categoryList.get(categoryList.size() - 1);
        assertThat(testCategory.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testCategory.getPhotoUrl()).isEqualTo(UPDATED_PHOTO_URL);
        assertThat(testCategory.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testCategory.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
        assertThat(testCategory.getVoiceUrl()).isEqualTo(UPDATED_VOICE_URL);
        assertThat(testCategory.getVoiceFile()).isEqualTo(UPDATED_VOICE_FILE);
        assertThat(testCategory.getVoiceFileContentType()).isEqualTo(UPDATED_VOICE_FILE_CONTENT_TYPE);
        assertThat(testCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCategory.getAdvice()).isEqualTo(UPDATED_ADVICE);
        assertThat(testCategory.getAccountType()).isEqualTo(UPDATED_ACCOUNT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingCategory() throws Exception {
        int databaseSizeBeforeUpdate = categoryRepository.findAll().size();
        category.setId(count.incrementAndGet());

        // Create the Category
        CategoryDTO categoryDTO = categoryMapper.toDto(category);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, categoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCategory() throws Exception {
        int databaseSizeBeforeUpdate = categoryRepository.findAll().size();
        category.setId(count.incrementAndGet());

        // Create the Category
        CategoryDTO categoryDTO = categoryMapper.toDto(category);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCategory() throws Exception {
        int databaseSizeBeforeUpdate = categoryRepository.findAll().size();
        category.setId(count.incrementAndGet());

        // Create the Category
        CategoryDTO categoryDTO = categoryMapper.toDto(category);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categoryDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCategoryWithPatch() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        int databaseSizeBeforeUpdate = categoryRepository.findAll().size();

        // Update the category using partial update
        Category partialUpdatedCategory = new Category();
        partialUpdatedCategory.setId(category.getId());

        partialUpdatedCategory
            .title(UPDATED_TITLE)
            .voiceUrl(UPDATED_VOICE_URL)
            .voiceFile(UPDATED_VOICE_FILE)
            .voiceFileContentType(UPDATED_VOICE_FILE_CONTENT_TYPE)
            .description(UPDATED_DESCRIPTION);

        restCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCategory))
            )
            .andExpect(status().isOk());

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeUpdate);
        Category testCategory = categoryList.get(categoryList.size() - 1);
        assertThat(testCategory.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testCategory.getPhotoUrl()).isEqualTo(DEFAULT_PHOTO_URL);
        assertThat(testCategory.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testCategory.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
        assertThat(testCategory.getVoiceUrl()).isEqualTo(UPDATED_VOICE_URL);
        assertThat(testCategory.getVoiceFile()).isEqualTo(UPDATED_VOICE_FILE);
        assertThat(testCategory.getVoiceFileContentType()).isEqualTo(UPDATED_VOICE_FILE_CONTENT_TYPE);
        assertThat(testCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCategory.getAdvice()).isEqualTo(DEFAULT_ADVICE);
        assertThat(testCategory.getAccountType()).isEqualTo(DEFAULT_ACCOUNT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateCategoryWithPatch() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        int databaseSizeBeforeUpdate = categoryRepository.findAll().size();

        // Update the category using partial update
        Category partialUpdatedCategory = new Category();
        partialUpdatedCategory.setId(category.getId());

        partialUpdatedCategory
            .title(UPDATED_TITLE)
            .photoUrl(UPDATED_PHOTO_URL)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .voiceUrl(UPDATED_VOICE_URL)
            .voiceFile(UPDATED_VOICE_FILE)
            .voiceFileContentType(UPDATED_VOICE_FILE_CONTENT_TYPE)
            .description(UPDATED_DESCRIPTION)
            .advice(UPDATED_ADVICE)
            .accountType(UPDATED_ACCOUNT_TYPE);

        restCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCategory))
            )
            .andExpect(status().isOk());

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeUpdate);
        Category testCategory = categoryList.get(categoryList.size() - 1);
        assertThat(testCategory.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testCategory.getPhotoUrl()).isEqualTo(UPDATED_PHOTO_URL);
        assertThat(testCategory.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testCategory.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
        assertThat(testCategory.getVoiceUrl()).isEqualTo(UPDATED_VOICE_URL);
        assertThat(testCategory.getVoiceFile()).isEqualTo(UPDATED_VOICE_FILE);
        assertThat(testCategory.getVoiceFileContentType()).isEqualTo(UPDATED_VOICE_FILE_CONTENT_TYPE);
        assertThat(testCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCategory.getAdvice()).isEqualTo(UPDATED_ADVICE);
        assertThat(testCategory.getAccountType()).isEqualTo(UPDATED_ACCOUNT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingCategory() throws Exception {
        int databaseSizeBeforeUpdate = categoryRepository.findAll().size();
        category.setId(count.incrementAndGet());

        // Create the Category
        CategoryDTO categoryDTO = categoryMapper.toDto(category);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, categoryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCategory() throws Exception {
        int databaseSizeBeforeUpdate = categoryRepository.findAll().size();
        category.setId(count.incrementAndGet());

        // Create the Category
        CategoryDTO categoryDTO = categoryMapper.toDto(category);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCategory() throws Exception {
        int databaseSizeBeforeUpdate = categoryRepository.findAll().size();
        category.setId(count.incrementAndGet());

        // Create the Category
        CategoryDTO categoryDTO = categoryMapper.toDto(category);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(categoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCategory() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        int databaseSizeBeforeDelete = categoryRepository.findAll().size();

        // Delete the category
        restCategoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, category.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

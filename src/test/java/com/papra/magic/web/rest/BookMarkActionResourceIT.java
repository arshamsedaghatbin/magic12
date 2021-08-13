package com.papra.magic.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.papra.magic.IntegrationTest;
import com.papra.magic.domain.BookMarkAction;
import com.papra.magic.repository.BookMarkActionRepository;
import com.papra.magic.service.dto.BookMarkActionDTO;
import com.papra.magic.service.mapper.BookMarkActionMapper;
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

/**
 * Integration tests for the {@link BookMarkActionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BookMarkActionResourceIT {

    private static final String DEFAULT_USER_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_USER_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/book-mark-actions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BookMarkActionRepository bookMarkActionRepository;

    @Autowired
    private BookMarkActionMapper bookMarkActionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBookMarkActionMockMvc;

    private BookMarkAction bookMarkAction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BookMarkAction createEntity(EntityManager em) {
        BookMarkAction bookMarkAction = new BookMarkAction().userDescription(DEFAULT_USER_DESCRIPTION);
        return bookMarkAction;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BookMarkAction createUpdatedEntity(EntityManager em) {
        BookMarkAction bookMarkAction = new BookMarkAction().userDescription(UPDATED_USER_DESCRIPTION);
        return bookMarkAction;
    }

    @BeforeEach
    public void initTest() {
        bookMarkAction = createEntity(em);
    }

    @Test
    @Transactional
    void createBookMarkAction() throws Exception {
        int databaseSizeBeforeCreate = bookMarkActionRepository.findAll().size();
        // Create the BookMarkAction
        BookMarkActionDTO bookMarkActionDTO = bookMarkActionMapper.toDto(bookMarkAction);
        restBookMarkActionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bookMarkActionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the BookMarkAction in the database
        List<BookMarkAction> bookMarkActionList = bookMarkActionRepository.findAll();
        assertThat(bookMarkActionList).hasSize(databaseSizeBeforeCreate + 1);
        BookMarkAction testBookMarkAction = bookMarkActionList.get(bookMarkActionList.size() - 1);
        assertThat(testBookMarkAction.getUserDescription()).isEqualTo(DEFAULT_USER_DESCRIPTION);
    }

    @Test
    @Transactional
    void createBookMarkActionWithExistingId() throws Exception {
        // Create the BookMarkAction with an existing ID
        bookMarkAction.setId(1L);
        BookMarkActionDTO bookMarkActionDTO = bookMarkActionMapper.toDto(bookMarkAction);

        int databaseSizeBeforeCreate = bookMarkActionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBookMarkActionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bookMarkActionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BookMarkAction in the database
        List<BookMarkAction> bookMarkActionList = bookMarkActionRepository.findAll();
        assertThat(bookMarkActionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBookMarkActions() throws Exception {
        // Initialize the database
        bookMarkActionRepository.saveAndFlush(bookMarkAction);

        // Get all the bookMarkActionList
        restBookMarkActionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bookMarkAction.getId().intValue())))
            .andExpect(jsonPath("$.[*].userDescription").value(hasItem(DEFAULT_USER_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getBookMarkAction() throws Exception {
        // Initialize the database
        bookMarkActionRepository.saveAndFlush(bookMarkAction);

        // Get the bookMarkAction
        restBookMarkActionMockMvc
            .perform(get(ENTITY_API_URL_ID, bookMarkAction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bookMarkAction.getId().intValue()))
            .andExpect(jsonPath("$.userDescription").value(DEFAULT_USER_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingBookMarkAction() throws Exception {
        // Get the bookMarkAction
        restBookMarkActionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBookMarkAction() throws Exception {
        // Initialize the database
        bookMarkActionRepository.saveAndFlush(bookMarkAction);

        int databaseSizeBeforeUpdate = bookMarkActionRepository.findAll().size();

        // Update the bookMarkAction
        BookMarkAction updatedBookMarkAction = bookMarkActionRepository.findById(bookMarkAction.getId()).get();
        // Disconnect from session so that the updates on updatedBookMarkAction are not directly saved in db
        em.detach(updatedBookMarkAction);
        updatedBookMarkAction.userDescription(UPDATED_USER_DESCRIPTION);
        BookMarkActionDTO bookMarkActionDTO = bookMarkActionMapper.toDto(updatedBookMarkAction);

        restBookMarkActionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bookMarkActionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bookMarkActionDTO))
            )
            .andExpect(status().isOk());

        // Validate the BookMarkAction in the database
        List<BookMarkAction> bookMarkActionList = bookMarkActionRepository.findAll();
        assertThat(bookMarkActionList).hasSize(databaseSizeBeforeUpdate);
        BookMarkAction testBookMarkAction = bookMarkActionList.get(bookMarkActionList.size() - 1);
        assertThat(testBookMarkAction.getUserDescription()).isEqualTo(UPDATED_USER_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingBookMarkAction() throws Exception {
        int databaseSizeBeforeUpdate = bookMarkActionRepository.findAll().size();
        bookMarkAction.setId(count.incrementAndGet());

        // Create the BookMarkAction
        BookMarkActionDTO bookMarkActionDTO = bookMarkActionMapper.toDto(bookMarkAction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBookMarkActionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bookMarkActionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bookMarkActionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BookMarkAction in the database
        List<BookMarkAction> bookMarkActionList = bookMarkActionRepository.findAll();
        assertThat(bookMarkActionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBookMarkAction() throws Exception {
        int databaseSizeBeforeUpdate = bookMarkActionRepository.findAll().size();
        bookMarkAction.setId(count.incrementAndGet());

        // Create the BookMarkAction
        BookMarkActionDTO bookMarkActionDTO = bookMarkActionMapper.toDto(bookMarkAction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookMarkActionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bookMarkActionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BookMarkAction in the database
        List<BookMarkAction> bookMarkActionList = bookMarkActionRepository.findAll();
        assertThat(bookMarkActionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBookMarkAction() throws Exception {
        int databaseSizeBeforeUpdate = bookMarkActionRepository.findAll().size();
        bookMarkAction.setId(count.incrementAndGet());

        // Create the BookMarkAction
        BookMarkActionDTO bookMarkActionDTO = bookMarkActionMapper.toDto(bookMarkAction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookMarkActionMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bookMarkActionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BookMarkAction in the database
        List<BookMarkAction> bookMarkActionList = bookMarkActionRepository.findAll();
        assertThat(bookMarkActionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBookMarkActionWithPatch() throws Exception {
        // Initialize the database
        bookMarkActionRepository.saveAndFlush(bookMarkAction);

        int databaseSizeBeforeUpdate = bookMarkActionRepository.findAll().size();

        // Update the bookMarkAction using partial update
        BookMarkAction partialUpdatedBookMarkAction = new BookMarkAction();
        partialUpdatedBookMarkAction.setId(bookMarkAction.getId());

        partialUpdatedBookMarkAction.userDescription(UPDATED_USER_DESCRIPTION);

        restBookMarkActionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBookMarkAction.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBookMarkAction))
            )
            .andExpect(status().isOk());

        // Validate the BookMarkAction in the database
        List<BookMarkAction> bookMarkActionList = bookMarkActionRepository.findAll();
        assertThat(bookMarkActionList).hasSize(databaseSizeBeforeUpdate);
        BookMarkAction testBookMarkAction = bookMarkActionList.get(bookMarkActionList.size() - 1);
        assertThat(testBookMarkAction.getUserDescription()).isEqualTo(UPDATED_USER_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateBookMarkActionWithPatch() throws Exception {
        // Initialize the database
        bookMarkActionRepository.saveAndFlush(bookMarkAction);

        int databaseSizeBeforeUpdate = bookMarkActionRepository.findAll().size();

        // Update the bookMarkAction using partial update
        BookMarkAction partialUpdatedBookMarkAction = new BookMarkAction();
        partialUpdatedBookMarkAction.setId(bookMarkAction.getId());

        partialUpdatedBookMarkAction.userDescription(UPDATED_USER_DESCRIPTION);

        restBookMarkActionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBookMarkAction.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBookMarkAction))
            )
            .andExpect(status().isOk());

        // Validate the BookMarkAction in the database
        List<BookMarkAction> bookMarkActionList = bookMarkActionRepository.findAll();
        assertThat(bookMarkActionList).hasSize(databaseSizeBeforeUpdate);
        BookMarkAction testBookMarkAction = bookMarkActionList.get(bookMarkActionList.size() - 1);
        assertThat(testBookMarkAction.getUserDescription()).isEqualTo(UPDATED_USER_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingBookMarkAction() throws Exception {
        int databaseSizeBeforeUpdate = bookMarkActionRepository.findAll().size();
        bookMarkAction.setId(count.incrementAndGet());

        // Create the BookMarkAction
        BookMarkActionDTO bookMarkActionDTO = bookMarkActionMapper.toDto(bookMarkAction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBookMarkActionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bookMarkActionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bookMarkActionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BookMarkAction in the database
        List<BookMarkAction> bookMarkActionList = bookMarkActionRepository.findAll();
        assertThat(bookMarkActionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBookMarkAction() throws Exception {
        int databaseSizeBeforeUpdate = bookMarkActionRepository.findAll().size();
        bookMarkAction.setId(count.incrementAndGet());

        // Create the BookMarkAction
        BookMarkActionDTO bookMarkActionDTO = bookMarkActionMapper.toDto(bookMarkAction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookMarkActionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bookMarkActionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BookMarkAction in the database
        List<BookMarkAction> bookMarkActionList = bookMarkActionRepository.findAll();
        assertThat(bookMarkActionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBookMarkAction() throws Exception {
        int databaseSizeBeforeUpdate = bookMarkActionRepository.findAll().size();
        bookMarkAction.setId(count.incrementAndGet());

        // Create the BookMarkAction
        BookMarkActionDTO bookMarkActionDTO = bookMarkActionMapper.toDto(bookMarkAction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookMarkActionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bookMarkActionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BookMarkAction in the database
        List<BookMarkAction> bookMarkActionList = bookMarkActionRepository.findAll();
        assertThat(bookMarkActionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBookMarkAction() throws Exception {
        // Initialize the database
        bookMarkActionRepository.saveAndFlush(bookMarkAction);

        int databaseSizeBeforeDelete = bookMarkActionRepository.findAll().size();

        // Delete the bookMarkAction
        restBookMarkActionMockMvc
            .perform(delete(ENTITY_API_URL_ID, bookMarkAction.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BookMarkAction> bookMarkActionList = bookMarkActionRepository.findAll();
        assertThat(bookMarkActionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

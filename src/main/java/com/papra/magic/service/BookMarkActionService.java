package com.papra.magic.service;

import com.papra.magic.service.dto.BookMarkActionDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.papra.magic.domain.BookMarkAction}.
 */
public interface BookMarkActionService {
    /**
     * Save a bookMarkAction.
     *
     * @param bookMarkActionDTO the entity to save.
     * @return the persisted entity.
     */
    BookMarkActionDTO save(BookMarkActionDTO bookMarkActionDTO);

    /**
     * Partially updates a bookMarkAction.
     *
     * @param bookMarkActionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BookMarkActionDTO> partialUpdate(BookMarkActionDTO bookMarkActionDTO);

    /**
     * Get all the bookMarkActions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BookMarkActionDTO> findAll(Pageable pageable);

    /**
     * Get the "id" bookMarkAction.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BookMarkActionDTO> findOne(Long id);

    /**
     * Delete the "id" bookMarkAction.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

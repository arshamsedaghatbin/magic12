package com.papra.magic.web.rest;

import com.papra.magic.repository.BookMarkActionRepository;
import com.papra.magic.service.BookMarkActionService;
import com.papra.magic.service.dto.BookMarkActionDTO;
import com.papra.magic.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.papra.magic.domain.BookMarkAction}.
 */
@RestController
@RequestMapping("/api")
public class BookMarkActionResource {

    private final Logger log = LoggerFactory.getLogger(BookMarkActionResource.class);

    private static final String ENTITY_NAME = "bookMarkAction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BookMarkActionService bookMarkActionService;

    private final BookMarkActionRepository bookMarkActionRepository;

    public BookMarkActionResource(BookMarkActionService bookMarkActionService, BookMarkActionRepository bookMarkActionRepository) {
        this.bookMarkActionService = bookMarkActionService;
        this.bookMarkActionRepository = bookMarkActionRepository;
    }

    /**
     * {@code POST  /book-mark-actions} : Create a new bookMarkAction.
     *
     * @param bookMarkActionDTO the bookMarkActionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bookMarkActionDTO, or with status {@code 400 (Bad Request)} if the bookMarkAction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/book-mark-actions")
    public ResponseEntity<BookMarkActionDTO> createBookMarkAction(@RequestBody BookMarkActionDTO bookMarkActionDTO)
        throws URISyntaxException {
        log.debug("REST request to save BookMarkAction : {}", bookMarkActionDTO);
        if (bookMarkActionDTO.getId() != null) {
            throw new BadRequestAlertException("A new bookMarkAction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BookMarkActionDTO result = bookMarkActionService.save(bookMarkActionDTO);
        return ResponseEntity
            .created(new URI("/api/book-mark-actions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /book-mark-actions/:id} : Updates an existing bookMarkAction.
     *
     * @param id the id of the bookMarkActionDTO to save.
     * @param bookMarkActionDTO the bookMarkActionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bookMarkActionDTO,
     * or with status {@code 400 (Bad Request)} if the bookMarkActionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bookMarkActionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/book-mark-actions/{id}")
    public ResponseEntity<BookMarkActionDTO> updateBookMarkAction(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BookMarkActionDTO bookMarkActionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BookMarkAction : {}, {}", id, bookMarkActionDTO);
        if (bookMarkActionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bookMarkActionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bookMarkActionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BookMarkActionDTO result = bookMarkActionService.save(bookMarkActionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bookMarkActionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /book-mark-actions/:id} : Partial updates given fields of an existing bookMarkAction, field will ignore if it is null
     *
     * @param id the id of the bookMarkActionDTO to save.
     * @param bookMarkActionDTO the bookMarkActionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bookMarkActionDTO,
     * or with status {@code 400 (Bad Request)} if the bookMarkActionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bookMarkActionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bookMarkActionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/book-mark-actions/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<BookMarkActionDTO> partialUpdateBookMarkAction(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BookMarkActionDTO bookMarkActionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BookMarkAction partially : {}, {}", id, bookMarkActionDTO);
        if (bookMarkActionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bookMarkActionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bookMarkActionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BookMarkActionDTO> result = bookMarkActionService.partialUpdate(bookMarkActionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bookMarkActionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /book-mark-actions} : get all the bookMarkActions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bookMarkActions in body.
     */
    @GetMapping("/book-mark-actions")
    public ResponseEntity<List<BookMarkActionDTO>> getAllBookMarkActions(Pageable pageable) {
        log.debug("REST request to get a page of BookMarkActions");
        Page<BookMarkActionDTO> page = bookMarkActionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /book-mark-actions/:id} : get the "id" bookMarkAction.
     *
     * @param id the id of the bookMarkActionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bookMarkActionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/book-mark-actions/{id}")
    public ResponseEntity<BookMarkActionDTO> getBookMarkAction(@PathVariable Long id) {
        log.debug("REST request to get BookMarkAction : {}", id);
        Optional<BookMarkActionDTO> bookMarkActionDTO = bookMarkActionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bookMarkActionDTO);
    }

    /**
     * {@code DELETE  /book-mark-actions/:id} : delete the "id" bookMarkAction.
     *
     * @param id the id of the bookMarkActionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/book-mark-actions/{id}")
    public ResponseEntity<Void> deleteBookMarkAction(@PathVariable Long id) {
        log.debug("REST request to delete BookMarkAction : {}", id);
        bookMarkActionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

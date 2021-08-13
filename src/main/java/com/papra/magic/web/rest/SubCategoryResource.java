package com.papra.magic.web.rest;

import com.papra.magic.repository.SubCategoryRepository;
import com.papra.magic.service.SubCategoryService;
import com.papra.magic.service.dto.SubCategoryDTO;
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
 * REST controller for managing {@link com.papra.magic.domain.SubCategory}.
 */
@RestController
@RequestMapping("/api")
public class SubCategoryResource {

    private final Logger log = LoggerFactory.getLogger(SubCategoryResource.class);

    private static final String ENTITY_NAME = "subCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubCategoryService subCategoryService;

    private final SubCategoryRepository subCategoryRepository;

    public SubCategoryResource(SubCategoryService subCategoryService, SubCategoryRepository subCategoryRepository) {
        this.subCategoryService = subCategoryService;
        this.subCategoryRepository = subCategoryRepository;
    }

    /**
     * {@code POST  /sub-categories} : Create a new subCategory.
     *
     * @param subCategoryDTO the subCategoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subCategoryDTO, or with status {@code 400 (Bad Request)} if the subCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sub-categories")
    public ResponseEntity<SubCategoryDTO> createSubCategory(@RequestBody SubCategoryDTO subCategoryDTO) throws URISyntaxException {
        log.debug("REST request to save SubCategory : {}", subCategoryDTO);
        if (subCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new subCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SubCategoryDTO result = subCategoryService.save(subCategoryDTO);
        return ResponseEntity
            .created(new URI("/api/sub-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sub-categories/:id} : Updates an existing subCategory.
     *
     * @param id the id of the subCategoryDTO to save.
     * @param subCategoryDTO the subCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the subCategoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sub-categories/{id}")
    public ResponseEntity<SubCategoryDTO> updateSubCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SubCategoryDTO subCategoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SubCategory : {}, {}", id, subCategoryDTO);
        if (subCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subCategoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SubCategoryDTO result = subCategoryService.save(subCategoryDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sub-categories/:id} : Partial updates given fields of an existing subCategory, field will ignore if it is null
     *
     * @param id the id of the subCategoryDTO to save.
     * @param subCategoryDTO the subCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the subCategoryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the subCategoryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the subCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sub-categories/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<SubCategoryDTO> partialUpdateSubCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SubCategoryDTO subCategoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SubCategory partially : {}, {}", id, subCategoryDTO);
        if (subCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subCategoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SubCategoryDTO> result = subCategoryService.partialUpdate(subCategoryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subCategoryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /sub-categories} : get all the subCategories.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subCategories in body.
     */
    @GetMapping("/sub-categories")
    public ResponseEntity<List<SubCategoryDTO>> getAllSubCategories(
        Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of SubCategories");
        Page<SubCategoryDTO> page;
        if (eagerload) {
            page = subCategoryService.findAllWithEagerRelationships(pageable);
        } else {
            page = subCategoryService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sub-categories/:id} : get the "id" subCategory.
     *
     * @param id the id of the subCategoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subCategoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sub-categories/{id}")
    public ResponseEntity<SubCategoryDTO> getSubCategory(@PathVariable Long id) {
        log.debug("REST request to get SubCategory : {}", id);
        Optional<SubCategoryDTO> subCategoryDTO = subCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(subCategoryDTO);
    }

    /**
     * {@code DELETE  /sub-categories/:id} : delete the "id" subCategory.
     *
     * @param id the id of the subCategoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sub-categories/{id}")
    public ResponseEntity<Void> deleteSubCategory(@PathVariable Long id) {
        log.debug("REST request to delete SubCategory : {}", id);
        subCategoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

package com.papra.magic.web.rest;

import com.papra.magic.repository.PracticeRepository;
import com.papra.magic.service.PracticeService;
import com.papra.magic.service.dto.PracticeDTO;
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
 * REST controller for managing {@link com.papra.magic.domain.Practice}.
 */
@RestController
@RequestMapping("/api")
public class PracticeResource {

    private final Logger log = LoggerFactory.getLogger(PracticeResource.class);

    private static final String ENTITY_NAME = "practice";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PracticeService practiceService;

    private final PracticeRepository practiceRepository;

    public PracticeResource(PracticeService practiceService, PracticeRepository practiceRepository) {
        this.practiceService = practiceService;
        this.practiceRepository = practiceRepository;
    }

    /**
     * {@code POST  /practices} : Create a new practice.
     *
     * @param practiceDTO the practiceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new practiceDTO, or with status {@code 400 (Bad Request)} if the practice has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/practices")
    public ResponseEntity<PracticeDTO> createPractice(@RequestBody PracticeDTO practiceDTO) throws URISyntaxException {
        log.debug("REST request to save Practice : {}", practiceDTO);
        if (practiceDTO.getId() != null) {
            throw new BadRequestAlertException("A new practice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PracticeDTO result = practiceService.save(practiceDTO);
        return ResponseEntity
            .created(new URI("/api/practices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /practices/:id} : Updates an existing practice.
     *
     * @param id the id of the practiceDTO to save.
     * @param practiceDTO the practiceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated practiceDTO,
     * or with status {@code 400 (Bad Request)} if the practiceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the practiceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/practices/{id}")
    public ResponseEntity<PracticeDTO> updatePractice(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PracticeDTO practiceDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Practice : {}, {}", id, practiceDTO);
        if (practiceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, practiceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!practiceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PracticeDTO result = practiceService.save(practiceDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, practiceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /practices/:id} : Partial updates given fields of an existing practice, field will ignore if it is null
     *
     * @param id the id of the practiceDTO to save.
     * @param practiceDTO the practiceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated practiceDTO,
     * or with status {@code 400 (Bad Request)} if the practiceDTO is not valid,
     * or with status {@code 404 (Not Found)} if the practiceDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the practiceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/practices/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<PracticeDTO> partialUpdatePractice(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PracticeDTO practiceDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Practice partially : {}, {}", id, practiceDTO);
        if (practiceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, practiceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!practiceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PracticeDTO> result = practiceService.partialUpdate(practiceDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, practiceDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /practices} : get all the practices.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of practices in body.
     */
    @GetMapping("/practices")
    public ResponseEntity<List<PracticeDTO>> getAllPractices(Pageable pageable) {
        log.debug("REST request to get a page of Practices");
        Page<PracticeDTO> page = practiceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /practices/:id} : get the "id" practice.
     *
     * @param id the id of the practiceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the practiceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/practices/{id}")
    public ResponseEntity<PracticeDTO> getPractice(@PathVariable Long id) {
        log.debug("REST request to get Practice : {}", id);
        Optional<PracticeDTO> practiceDTO = practiceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(practiceDTO);
    }

    /**
     * {@code DELETE  /practices/:id} : delete the "id" practice.
     *
     * @param id the id of the practiceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/practices/{id}")
    public ResponseEntity<Void> deletePractice(@PathVariable Long id) {
        log.debug("REST request to delete Practice : {}", id);
        practiceService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

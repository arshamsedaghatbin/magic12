package com.papra.magic.web.rest;

import com.papra.magic.repository.PracticeSessionRepository;
import com.papra.magic.service.PracticeSessionService;
import com.papra.magic.service.dto.PracticeSessionDTO;
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
 * REST controller for managing {@link com.papra.magic.domain.PracticeSession}.
 */
@RestController
@RequestMapping("/api")
public class PracticeSessionResource {

    private final Logger log = LoggerFactory.getLogger(PracticeSessionResource.class);

    private static final String ENTITY_NAME = "practiceSession";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PracticeSessionService practiceSessionService;

    private final PracticeSessionRepository practiceSessionRepository;

    public PracticeSessionResource(PracticeSessionService practiceSessionService, PracticeSessionRepository practiceSessionRepository) {
        this.practiceSessionService = practiceSessionService;
        this.practiceSessionRepository = practiceSessionRepository;
    }

    /**
     * {@code POST  /practice-sessions} : Create a new practiceSession.
     *
     * @param practiceSessionDTO the practiceSessionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new practiceSessionDTO, or with status {@code 400 (Bad Request)} if the practiceSession has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/practice-sessions")
    public ResponseEntity<PracticeSessionDTO> createPracticeSession(@RequestBody PracticeSessionDTO practiceSessionDTO)
        throws URISyntaxException {
        log.debug("REST request to save PracticeSession : {}", practiceSessionDTO);
        if (practiceSessionDTO.getId() != null) {
            throw new BadRequestAlertException("A new practiceSession cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PracticeSessionDTO result = practiceSessionService.save(practiceSessionDTO);
        return ResponseEntity
            .created(new URI("/api/practice-sessions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /practice-sessions/:id} : Updates an existing practiceSession.
     *
     * @param id the id of the practiceSessionDTO to save.
     * @param practiceSessionDTO the practiceSessionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated practiceSessionDTO,
     * or with status {@code 400 (Bad Request)} if the practiceSessionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the practiceSessionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/practice-sessions/{id}")
    public ResponseEntity<PracticeSessionDTO> updatePracticeSession(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PracticeSessionDTO practiceSessionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PracticeSession : {}, {}", id, practiceSessionDTO);
        if (practiceSessionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, practiceSessionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!practiceSessionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PracticeSessionDTO result = practiceSessionService.save(practiceSessionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, practiceSessionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /practice-sessions/:id} : Partial updates given fields of an existing practiceSession, field will ignore if it is null
     *
     * @param id the id of the practiceSessionDTO to save.
     * @param practiceSessionDTO the practiceSessionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated practiceSessionDTO,
     * or with status {@code 400 (Bad Request)} if the practiceSessionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the practiceSessionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the practiceSessionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/practice-sessions/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<PracticeSessionDTO> partialUpdatePracticeSession(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PracticeSessionDTO practiceSessionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PracticeSession partially : {}, {}", id, practiceSessionDTO);
        if (practiceSessionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, practiceSessionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!practiceSessionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PracticeSessionDTO> result = practiceSessionService.partialUpdate(practiceSessionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, practiceSessionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /practice-sessions} : get all the practiceSessions.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of practiceSessions in body.
     */
    @GetMapping("/practice-sessions")
    public ResponseEntity<List<PracticeSessionDTO>> getAllPracticeSessions(
        Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of PracticeSessions");
        Page<PracticeSessionDTO> page;
        if (eagerload) {
            page = practiceSessionService.findAllWithEagerRelationships(pageable);
        } else {
            page = practiceSessionService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /practice-sessions/:id} : get the "id" practiceSession.
     *
     * @param id the id of the practiceSessionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the practiceSessionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/practice-sessions/{id}")
    public ResponseEntity<PracticeSessionDTO> getPracticeSession(@PathVariable Long id) {
        log.debug("REST request to get PracticeSession : {}", id);
        Optional<PracticeSessionDTO> practiceSessionDTO = practiceSessionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(practiceSessionDTO);
    }

    /**
     * {@code DELETE  /practice-sessions/:id} : delete the "id" practiceSession.
     *
     * @param id the id of the practiceSessionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/practice-sessions/{id}")
    public ResponseEntity<Void> deletePracticeSession(@PathVariable Long id) {
        log.debug("REST request to delete PracticeSession : {}", id);
        practiceSessionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

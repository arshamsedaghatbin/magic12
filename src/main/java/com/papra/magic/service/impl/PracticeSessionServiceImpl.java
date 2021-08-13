package com.papra.magic.service.impl;

import com.papra.magic.domain.PracticeSession;
import com.papra.magic.repository.PracticeSessionRepository;
import com.papra.magic.service.PracticeSessionService;
import com.papra.magic.service.dto.PracticeSessionDTO;
import com.papra.magic.service.mapper.PracticeSessionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PracticeSession}.
 */
@Service
@Transactional
public class PracticeSessionServiceImpl implements PracticeSessionService {

    private final Logger log = LoggerFactory.getLogger(PracticeSessionServiceImpl.class);

    private final PracticeSessionRepository practiceSessionRepository;

    private final PracticeSessionMapper practiceSessionMapper;

    public PracticeSessionServiceImpl(PracticeSessionRepository practiceSessionRepository, PracticeSessionMapper practiceSessionMapper) {
        this.practiceSessionRepository = practiceSessionRepository;
        this.practiceSessionMapper = practiceSessionMapper;
    }

    @Override
    public PracticeSessionDTO save(PracticeSessionDTO practiceSessionDTO) {
        log.debug("Request to save PracticeSession : {}", practiceSessionDTO);
        PracticeSession practiceSession = practiceSessionMapper.toEntity(practiceSessionDTO);
        practiceSession = practiceSessionRepository.save(practiceSession);
        return practiceSessionMapper.toDto(practiceSession);
    }

    @Override
    public Optional<PracticeSessionDTO> partialUpdate(PracticeSessionDTO practiceSessionDTO) {
        log.debug("Request to partially update PracticeSession : {}", practiceSessionDTO);

        return practiceSessionRepository
            .findById(practiceSessionDTO.getId())
            .map(
                existingPracticeSession -> {
                    practiceSessionMapper.partialUpdate(existingPracticeSession, practiceSessionDTO);

                    return existingPracticeSession;
                }
            )
            .map(practiceSessionRepository::save)
            .map(practiceSessionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PracticeSessionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PracticeSessions");
        return practiceSessionRepository.findAll(pageable).map(practiceSessionMapper::toDto);
    }

    public Page<PracticeSessionDTO> findAllWithEagerRelationships(Pageable pageable) {
        return practiceSessionRepository.findAllWithEagerRelationships(pageable).map(practiceSessionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PracticeSessionDTO> findOne(Long id) {
        log.debug("Request to get PracticeSession : {}", id);
        return practiceSessionRepository.findOneWithEagerRelationships(id).map(practiceSessionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PracticeSession : {}", id);
        practiceSessionRepository.deleteById(id);
    }
}

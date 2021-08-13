package com.papra.magic.service.impl;

import com.papra.magic.domain.Practice;
import com.papra.magic.repository.PracticeRepository;
import com.papra.magic.service.PracticeService;
import com.papra.magic.service.dto.PracticeDTO;
import com.papra.magic.service.mapper.PracticeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Practice}.
 */
@Service
@Transactional
public class PracticeServiceImpl implements PracticeService {

    private final Logger log = LoggerFactory.getLogger(PracticeServiceImpl.class);

    private final PracticeRepository practiceRepository;

    private final PracticeMapper practiceMapper;

    public PracticeServiceImpl(PracticeRepository practiceRepository, PracticeMapper practiceMapper) {
        this.practiceRepository = practiceRepository;
        this.practiceMapper = practiceMapper;
    }

    @Override
    public PracticeDTO save(PracticeDTO practiceDTO) {
        log.debug("Request to save Practice : {}", practiceDTO);
        Practice practice = practiceMapper.toEntity(practiceDTO);
        practice = practiceRepository.save(practice);
        return practiceMapper.toDto(practice);
    }

    @Override
    public Optional<PracticeDTO> partialUpdate(PracticeDTO practiceDTO) {
        log.debug("Request to partially update Practice : {}", practiceDTO);

        return practiceRepository
            .findById(practiceDTO.getId())
            .map(
                existingPractice -> {
                    practiceMapper.partialUpdate(existingPractice, practiceDTO);

                    return existingPractice;
                }
            )
            .map(practiceRepository::save)
            .map(practiceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PracticeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Practices");
        return practiceRepository.findAll(pageable).map(practiceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PracticeDTO> findOne(Long id) {
        log.debug("Request to get Practice : {}", id);
        return practiceRepository.findById(id).map(practiceMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Practice : {}", id);
        practiceRepository.deleteById(id);
    }
}

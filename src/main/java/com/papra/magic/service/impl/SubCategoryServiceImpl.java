package com.papra.magic.service.impl;

import com.papra.magic.domain.SubCategory;
import com.papra.magic.repository.SubCategoryRepository;
import com.papra.magic.service.SubCategoryService;
import com.papra.magic.service.dto.SubCategoryDTO;
import com.papra.magic.service.mapper.SubCategoryMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SubCategory}.
 */
@Service
@Transactional
public class SubCategoryServiceImpl implements SubCategoryService {

    private final Logger log = LoggerFactory.getLogger(SubCategoryServiceImpl.class);

    private final SubCategoryRepository subCategoryRepository;

    private final SubCategoryMapper subCategoryMapper;

    public SubCategoryServiceImpl(SubCategoryRepository subCategoryRepository, SubCategoryMapper subCategoryMapper) {
        this.subCategoryRepository = subCategoryRepository;
        this.subCategoryMapper = subCategoryMapper;
    }

    @Override
    public SubCategoryDTO save(SubCategoryDTO subCategoryDTO) {
        log.debug("Request to save SubCategory : {}", subCategoryDTO);
        SubCategory subCategory = subCategoryMapper.toEntity(subCategoryDTO);
        subCategory = subCategoryRepository.save(subCategory);
        return subCategoryMapper.toDto(subCategory);
    }

    @Override
    public Optional<SubCategoryDTO> partialUpdate(SubCategoryDTO subCategoryDTO) {
        log.debug("Request to partially update SubCategory : {}", subCategoryDTO);

        return subCategoryRepository
            .findById(subCategoryDTO.getId())
            .map(
                existingSubCategory -> {
                    subCategoryMapper.partialUpdate(existingSubCategory, subCategoryDTO);

                    return existingSubCategory;
                }
            )
            .map(subCategoryRepository::save)
            .map(subCategoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SubCategoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SubCategories");
        return subCategoryRepository.findAll(pageable).map(subCategoryMapper::toDto);
    }

    public Page<SubCategoryDTO> findAllWithEagerRelationships(Pageable pageable) {
        return subCategoryRepository.findAllWithEagerRelationships(pageable).map(subCategoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SubCategoryDTO> findOne(Long id) {
        log.debug("Request to get SubCategory : {}", id);
        return subCategoryRepository.findOneWithEagerRelationships(id).map(subCategoryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SubCategory : {}", id);
        subCategoryRepository.deleteById(id);
    }
}

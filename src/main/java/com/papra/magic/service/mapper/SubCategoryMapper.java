package com.papra.magic.service.mapper;

import com.papra.magic.domain.*;
import com.papra.magic.service.dto.SubCategoryDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SubCategory} and its DTO {@link SubCategoryDTO}.
 */
@Mapper(componentModel = "spring", uses = { ActionMapper.class, CategoryMapper.class })
public interface SubCategoryMapper extends EntityMapper<SubCategoryDTO, SubCategory> {
    @Mapping(target = "actions", source = "actions", qualifiedByName = "idSet")
    @Mapping(target = "category", source = "category", qualifiedByName = "id")
    SubCategoryDTO toDto(SubCategory s);

    @Mapping(target = "removeAction", ignore = true)
    SubCategory toEntity(SubCategoryDTO subCategoryDTO);
}

package com.papra.magic.service.mapper;

import com.papra.magic.domain.*;
import com.papra.magic.service.dto.CategoryDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Category} and its DTO {@link CategoryDTO}.
 */
@Mapper(componentModel = "spring", uses = { ActionMapper.class })
public interface CategoryMapper extends EntityMapper<CategoryDTO, Category> {
    @Mapping(target = "actions", source = "actions", qualifiedByName = "idSet")
    CategoryDTO toDto(Category s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CategoryDTO toDtoId(Category category);

    @Mapping(target = "removeAction", ignore = true)
    Category toEntity(CategoryDTO categoryDTO);
}

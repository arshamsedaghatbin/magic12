package com.papra.magic.service.mapper;

import com.papra.magic.domain.*;
import com.papra.magic.service.dto.PracticeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Practice} and its DTO {@link PracticeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PracticeMapper extends EntityMapper<PracticeDTO, Practice> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PracticeDTO toDtoId(Practice practice);
}

package com.papra.magic.service.mapper;

import com.papra.magic.domain.*;
import com.papra.magic.service.dto.ActionDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Action} and its DTO {@link ActionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ActionMapper extends EntityMapper<ActionDTO, Action> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ActionDTO toDtoId(Action action);

    @Named("idSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    Set<ActionDTO> toDtoIdSet(Set<Action> action);
}

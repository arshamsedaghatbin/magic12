package com.papra.magic.service.mapper;

import com.papra.magic.domain.*;
import com.papra.magic.service.dto.PracticeSessionDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PracticeSession} and its DTO {@link PracticeSessionDTO}.
 */
@Mapper(componentModel = "spring", uses = { ActionMapper.class, PracticeMapper.class })
public interface PracticeSessionMapper extends EntityMapper<PracticeSessionDTO, PracticeSession> {
    @Mapping(target = "actions", source = "actions", qualifiedByName = "idSet")
    @Mapping(target = "practice", source = "practice", qualifiedByName = "id")
    PracticeSessionDTO toDto(PracticeSession s);

    @Mapping(target = "removeAction", ignore = true)
    PracticeSession toEntity(PracticeSessionDTO practiceSessionDTO);
}

package com.apptium.order.service.mapper;

import com.apptium.order.domain.*;
import com.apptium.order.service.dto.OrdNoteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrdNote} and its DTO {@link OrdNoteDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OrdNoteMapper extends EntityMapper<OrdNoteDTO, OrdNote> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrdNoteDTO toDtoId(OrdNote ordNote);
}

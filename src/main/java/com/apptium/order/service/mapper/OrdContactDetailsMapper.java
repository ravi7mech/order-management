package com.apptium.order.service.mapper;

import com.apptium.order.domain.*;
import com.apptium.order.service.dto.OrdContactDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrdContactDetails} and its DTO {@link OrdContactDetailsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OrdContactDetailsMapper extends EntityMapper<OrdContactDetailsDTO, OrdContactDetails> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrdContactDetailsDTO toDtoId(OrdContactDetails ordContactDetails);
}

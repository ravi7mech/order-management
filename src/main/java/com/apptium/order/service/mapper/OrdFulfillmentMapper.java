package com.apptium.order.service.mapper;

import com.apptium.order.domain.*;
import com.apptium.order.service.dto.OrdFulfillmentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrdFulfillment} and its DTO {@link OrdFulfillmentDTO}.
 */
@Mapper(componentModel = "spring", uses = { OrdProductOrderMapper.class })
public interface OrdFulfillmentMapper extends EntityMapper<OrdFulfillmentDTO, OrdFulfillment> {
    @Mapping(target = "ordProductOrder", source = "ordProductOrder", qualifiedByName = "id")
    OrdFulfillmentDTO toDto(OrdFulfillment s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrdFulfillmentDTO toDtoId(OrdFulfillment ordFulfillment);
}

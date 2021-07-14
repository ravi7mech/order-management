package com.apptium.order.service.mapper;

import com.apptium.order.domain.*;
import com.apptium.order.service.dto.OrdFulfillmentCharDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrdFulfillmentChar} and its DTO {@link OrdFulfillmentCharDTO}.
 */
@Mapper(componentModel = "spring", uses = { OrdFulfillmentMapper.class })
public interface OrdFulfillmentCharMapper extends EntityMapper<OrdFulfillmentCharDTO, OrdFulfillmentChar> {
    @Mapping(target = "ordFulfillment", source = "ordFulfillment", qualifiedByName = "id")
    OrdFulfillmentCharDTO toDto(OrdFulfillmentChar s);
}

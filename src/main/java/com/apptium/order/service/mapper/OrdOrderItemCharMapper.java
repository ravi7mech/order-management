package com.apptium.order.service.mapper;

import com.apptium.order.domain.*;
import com.apptium.order.service.dto.OrdOrderItemCharDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrdOrderItemChar} and its DTO {@link OrdOrderItemCharDTO}.
 */
@Mapper(componentModel = "spring", uses = { OrdOrderItemMapper.class })
public interface OrdOrderItemCharMapper extends EntityMapper<OrdOrderItemCharDTO, OrdOrderItemChar> {
    @Mapping(target = "ordOrderItem", source = "ordOrderItem", qualifiedByName = "id")
    OrdOrderItemCharDTO toDto(OrdOrderItemChar s);
}

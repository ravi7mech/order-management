package com.apptium.order.service.mapper;

import com.apptium.order.domain.*;
import com.apptium.order.service.dto.OrdOrderItemRelationshipDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrdOrderItemRelationship} and its DTO {@link OrdOrderItemRelationshipDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OrdOrderItemRelationshipMapper extends EntityMapper<OrdOrderItemRelationshipDTO, OrdOrderItemRelationship> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrdOrderItemRelationshipDTO toDtoId(OrdOrderItemRelationship ordOrderItemRelationship);
}

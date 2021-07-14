package com.apptium.order.service.mapper;

import com.apptium.order.domain.*;
import com.apptium.order.service.dto.OrdOrderItemDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrdOrderItem} and its DTO {@link OrdOrderItemDTO}.
 */
@Mapper(
    componentModel = "spring",
    uses = {
        OrdOrderPriceMapper.class,
        OrdOrderItemRelationshipMapper.class,
        OrdProductOfferingRefMapper.class,
        OrdProductMapper.class,
        OrdOrderItemProvisioningMapper.class,
        OrdProductOrderMapper.class,
    }
)
public interface OrdOrderItemMapper extends EntityMapper<OrdOrderItemDTO, OrdOrderItem> {
    @Mapping(target = "ordOrderPrice", source = "ordOrderPrice", qualifiedByName = "id")
    @Mapping(target = "ordOrderItemRelationship", source = "ordOrderItemRelationship", qualifiedByName = "id")
    @Mapping(target = "ordProductOfferingRef", source = "ordProductOfferingRef", qualifiedByName = "id")
    @Mapping(target = "ordProduct", source = "ordProduct", qualifiedByName = "id")
    @Mapping(target = "ordOrderItemProvisioning", source = "ordOrderItemProvisioning", qualifiedByName = "id")
    @Mapping(target = "ordProductOrder", source = "ordProductOrder", qualifiedByName = "id")
    OrdOrderItemDTO toDto(OrdOrderItem s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrdOrderItemDTO toDtoId(OrdOrderItem ordOrderItem);
}

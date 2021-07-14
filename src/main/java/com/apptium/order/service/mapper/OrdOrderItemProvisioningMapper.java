package com.apptium.order.service.mapper;

import com.apptium.order.domain.*;
import com.apptium.order.service.dto.OrdOrderItemProvisioningDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrdOrderItemProvisioning} and its DTO {@link OrdOrderItemProvisioningDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OrdOrderItemProvisioningMapper extends EntityMapper<OrdOrderItemProvisioningDTO, OrdOrderItemProvisioning> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrdOrderItemProvisioningDTO toDtoId(OrdOrderItemProvisioning ordOrderItemProvisioning);
}

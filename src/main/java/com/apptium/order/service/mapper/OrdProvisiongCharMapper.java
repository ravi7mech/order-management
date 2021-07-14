package com.apptium.order.service.mapper;

import com.apptium.order.domain.*;
import com.apptium.order.service.dto.OrdProvisiongCharDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrdProvisiongChar} and its DTO {@link OrdProvisiongCharDTO}.
 */
@Mapper(componentModel = "spring", uses = { OrdOrderItemProvisioningMapper.class })
public interface OrdProvisiongCharMapper extends EntityMapper<OrdProvisiongCharDTO, OrdProvisiongChar> {
    @Mapping(target = "ordOrderItemProvisioning", source = "ordOrderItemProvisioning", qualifiedByName = "id")
    OrdProvisiongCharDTO toDto(OrdProvisiongChar s);
}

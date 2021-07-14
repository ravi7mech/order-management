package com.apptium.order.service.mapper;

import com.apptium.order.domain.*;
import com.apptium.order.service.dto.OrdProductOfferingRefDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrdProductOfferingRef} and its DTO {@link OrdProductOfferingRefDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OrdProductOfferingRefMapper extends EntityMapper<OrdProductOfferingRefDTO, OrdProductOfferingRef> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrdProductOfferingRefDTO toDtoId(OrdProductOfferingRef ordProductOfferingRef);
}

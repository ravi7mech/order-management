package com.apptium.order.service.mapper;

import com.apptium.order.domain.*;
import com.apptium.order.service.dto.OrdBillingAccountRefDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrdBillingAccountRef} and its DTO {@link OrdBillingAccountRefDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OrdBillingAccountRefMapper extends EntityMapper<OrdBillingAccountRefDTO, OrdBillingAccountRef> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrdBillingAccountRefDTO toDtoId(OrdBillingAccountRef ordBillingAccountRef);
}

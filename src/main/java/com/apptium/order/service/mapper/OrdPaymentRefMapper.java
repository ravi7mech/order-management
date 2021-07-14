package com.apptium.order.service.mapper;

import com.apptium.order.domain.*;
import com.apptium.order.service.dto.OrdPaymentRefDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrdPaymentRef} and its DTO {@link OrdPaymentRefDTO}.
 */
@Mapper(componentModel = "spring", uses = { OrdProductOrderMapper.class })
public interface OrdPaymentRefMapper extends EntityMapper<OrdPaymentRefDTO, OrdPaymentRef> {
    @Mapping(target = "ordProductOrder", source = "ordProductOrder", qualifiedByName = "id")
    OrdPaymentRefDTO toDto(OrdPaymentRef s);
}

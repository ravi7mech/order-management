package com.apptium.order.service.mapper;

import com.apptium.order.domain.*;
import com.apptium.order.service.dto.OrdReasonDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrdReason} and its DTO {@link OrdReasonDTO}.
 */
@Mapper(componentModel = "spring", uses = { OrdProductOrderMapper.class })
public interface OrdReasonMapper extends EntityMapper<OrdReasonDTO, OrdReason> {
    @Mapping(target = "ordProductOrder", source = "ordProductOrder", qualifiedByName = "id")
    OrdReasonDTO toDto(OrdReason s);
}

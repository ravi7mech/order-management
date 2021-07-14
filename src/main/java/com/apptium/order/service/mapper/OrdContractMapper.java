package com.apptium.order.service.mapper;

import com.apptium.order.domain.*;
import com.apptium.order.service.dto.OrdContractDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrdContract} and its DTO {@link OrdContractDTO}.
 */
@Mapper(componentModel = "spring", uses = { OrdProductOrderMapper.class })
public interface OrdContractMapper extends EntityMapper<OrdContractDTO, OrdContract> {
    @Mapping(target = "ordProductOrder", source = "ordProductOrder", qualifiedByName = "id")
    OrdContractDTO toDto(OrdContract s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrdContractDTO toDtoId(OrdContract ordContract);
}

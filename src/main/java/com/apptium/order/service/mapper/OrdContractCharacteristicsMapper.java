package com.apptium.order.service.mapper;

import com.apptium.order.domain.*;
import com.apptium.order.service.dto.OrdContractCharacteristicsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrdContractCharacteristics} and its DTO {@link OrdContractCharacteristicsDTO}.
 */
@Mapper(componentModel = "spring", uses = { OrdContractMapper.class })
public interface OrdContractCharacteristicsMapper extends EntityMapper<OrdContractCharacteristicsDTO, OrdContractCharacteristics> {
    @Mapping(target = "ordContract", source = "ordContract", qualifiedByName = "id")
    OrdContractCharacteristicsDTO toDto(OrdContractCharacteristics s);
}

package com.apptium.order.service.mapper;

import com.apptium.order.domain.*;
import com.apptium.order.service.dto.OrdCharacteristicsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrdCharacteristics} and its DTO {@link OrdCharacteristicsDTO}.
 */
@Mapper(componentModel = "spring", uses = { OrdProductOrderMapper.class })
public interface OrdCharacteristicsMapper extends EntityMapper<OrdCharacteristicsDTO, OrdCharacteristics> {
    @Mapping(target = "ordProductOrder", source = "ordProductOrder", qualifiedByName = "id")
    OrdCharacteristicsDTO toDto(OrdCharacteristics s);
}

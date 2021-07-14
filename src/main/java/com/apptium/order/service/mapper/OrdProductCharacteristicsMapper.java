package com.apptium.order.service.mapper;

import com.apptium.order.domain.*;
import com.apptium.order.service.dto.OrdProductCharacteristicsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrdProductCharacteristics} and its DTO {@link OrdProductCharacteristicsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OrdProductCharacteristicsMapper extends EntityMapper<OrdProductCharacteristicsDTO, OrdProductCharacteristics> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrdProductCharacteristicsDTO toDtoId(OrdProductCharacteristics ordProductCharacteristics);
}

package com.apptium.order.service.mapper;

import com.apptium.order.domain.*;
import com.apptium.order.service.dto.OrdProductDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrdProduct} and its DTO {@link OrdProductDTO}.
 */
@Mapper(componentModel = "spring", uses = { OrdProductCharacteristicsMapper.class })
public interface OrdProductMapper extends EntityMapper<OrdProductDTO, OrdProduct> {
    @Mapping(target = "ordProductCharacteristics", source = "ordProductCharacteristics", qualifiedByName = "id")
    OrdProductDTO toDto(OrdProduct s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrdProductDTO toDtoId(OrdProduct ordProduct);
}

package com.apptium.order.service.mapper;

import com.apptium.order.domain.*;
import com.apptium.order.service.dto.OrdOrderPriceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrdOrderPrice} and its DTO {@link OrdOrderPriceDTO}.
 */
@Mapper(componentModel = "spring", uses = { OrdPriceAmountMapper.class })
public interface OrdOrderPriceMapper extends EntityMapper<OrdOrderPriceDTO, OrdOrderPrice> {
    @Mapping(target = "ordPriceAmount", source = "ordPriceAmount", qualifiedByName = "id")
    OrdOrderPriceDTO toDto(OrdOrderPrice s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrdOrderPriceDTO toDtoId(OrdOrderPrice ordOrderPrice);
}

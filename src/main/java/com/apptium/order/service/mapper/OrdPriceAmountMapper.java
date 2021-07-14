package com.apptium.order.service.mapper;

import com.apptium.order.domain.*;
import com.apptium.order.service.dto.OrdPriceAmountDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrdPriceAmount} and its DTO {@link OrdPriceAmountDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OrdPriceAmountMapper extends EntityMapper<OrdPriceAmountDTO, OrdPriceAmount> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrdPriceAmountDTO toDtoId(OrdPriceAmount ordPriceAmount);
}

package com.apptium.order.service.mapper;

import com.apptium.order.domain.*;
import com.apptium.order.service.dto.OrdPriceAlterationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrdPriceAlteration} and its DTO {@link OrdPriceAlterationDTO}.
 */
@Mapper(componentModel = "spring", uses = { OrdPriceAmountMapper.class, OrdOrderPriceMapper.class })
public interface OrdPriceAlterationMapper extends EntityMapper<OrdPriceAlterationDTO, OrdPriceAlteration> {
    @Mapping(target = "ordPriceAmount", source = "ordPriceAmount", qualifiedByName = "id")
    @Mapping(target = "ordOrderPrice", source = "ordOrderPrice", qualifiedByName = "id")
    OrdPriceAlterationDTO toDto(OrdPriceAlteration s);
}

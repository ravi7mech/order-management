package com.apptium.order.service.mapper;

import com.apptium.order.domain.*;
import com.apptium.order.service.dto.OrdProductOrderDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrdProductOrder} and its DTO {@link OrdProductOrderDTO}.
 */
@Mapper(
    componentModel = "spring",
    uses = {
        OrdContactDetailsMapper.class,
        OrdNoteMapper.class,
        OrdChannelMapper.class,
        OrdOrderPriceMapper.class,
        OrdBillingAccountRefMapper.class,
    }
)
public interface OrdProductOrderMapper extends EntityMapper<OrdProductOrderDTO, OrdProductOrder> {
    @Mapping(target = "ordContactDetails", source = "ordContactDetails", qualifiedByName = "id")
    @Mapping(target = "ordNote", source = "ordNote", qualifiedByName = "id")
    @Mapping(target = "ordChannel", source = "ordChannel", qualifiedByName = "id")
    @Mapping(target = "ordOrderPrice", source = "ordOrderPrice", qualifiedByName = "id")
    @Mapping(target = "ordBillingAccountRef", source = "ordBillingAccountRef", qualifiedByName = "id")
    OrdProductOrderDTO toDto(OrdProductOrder s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrdProductOrderDTO toDtoId(OrdProductOrder ordProductOrder);
}

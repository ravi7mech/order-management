package com.apptium.order.service.mapper;

import com.apptium.order.domain.*;
import com.apptium.order.service.dto.OrdPlaceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrdPlace} and its DTO {@link OrdPlaceDTO}.
 */
@Mapper(componentModel = "spring", uses = { OrdProductMapper.class })
public interface OrdPlaceMapper extends EntityMapper<OrdPlaceDTO, OrdPlace> {
    @Mapping(target = "ordProduct", source = "ordProduct", qualifiedByName = "id")
    OrdPlaceDTO toDto(OrdPlace s);
}

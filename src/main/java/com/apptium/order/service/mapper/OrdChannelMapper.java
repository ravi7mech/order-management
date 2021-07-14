package com.apptium.order.service.mapper;

import com.apptium.order.domain.*;
import com.apptium.order.service.dto.OrdChannelDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrdChannel} and its DTO {@link OrdChannelDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OrdChannelMapper extends EntityMapper<OrdChannelDTO, OrdChannel> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrdChannelDTO toDtoId(OrdChannel ordChannel);
}

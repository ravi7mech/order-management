package com.apptium.order.service.mapper;

import com.apptium.order.domain.*;
import com.apptium.order.service.dto.OrdAcquisitionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrdAcquisition} and its DTO {@link OrdAcquisitionDTO}.
 */
@Mapper(componentModel = "spring", uses = { OrdProductOrderMapper.class })
public interface OrdAcquisitionMapper extends EntityMapper<OrdAcquisitionDTO, OrdAcquisition> {
    @Mapping(target = "ordProductOrder", source = "ordProductOrder", qualifiedByName = "id")
    OrdAcquisitionDTO toDto(OrdAcquisition s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrdAcquisitionDTO toDtoId(OrdAcquisition ordAcquisition);
}

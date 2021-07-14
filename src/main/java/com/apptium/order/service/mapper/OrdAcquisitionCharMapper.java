package com.apptium.order.service.mapper;

import com.apptium.order.domain.*;
import com.apptium.order.service.dto.OrdAcquisitionCharDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrdAcquisitionChar} and its DTO {@link OrdAcquisitionCharDTO}.
 */
@Mapper(componentModel = "spring", uses = { OrdAcquisitionMapper.class })
public interface OrdAcquisitionCharMapper extends EntityMapper<OrdAcquisitionCharDTO, OrdAcquisitionChar> {
    @Mapping(target = "ordAcquisition", source = "ordAcquisition", qualifiedByName = "id")
    OrdAcquisitionCharDTO toDto(OrdAcquisitionChar s);
}

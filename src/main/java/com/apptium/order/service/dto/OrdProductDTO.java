package com.apptium.order.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.apptium.order.domain.OrdProduct} entity.
 */
public class OrdProductDTO implements Serializable {

    private Long id;

    private Long versionId;

    private Long variationId;

    private String lineOfService;

    private Long assetId;

    private Long serialNo;

    private String name;

    private OrdProductCharacteristicsDTO ordProductCharacteristics;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersionId() {
        return versionId;
    }

    public void setVersionId(Long versionId) {
        this.versionId = versionId;
    }

    public Long getVariationId() {
        return variationId;
    }

    public void setVariationId(Long variationId) {
        this.variationId = variationId;
    }

    public String getLineOfService() {
        return lineOfService;
    }

    public void setLineOfService(String lineOfService) {
        this.lineOfService = lineOfService;
    }

    public Long getAssetId() {
        return assetId;
    }

    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }

    public Long getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(Long serialNo) {
        this.serialNo = serialNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OrdProductCharacteristicsDTO getOrdProductCharacteristics() {
        return ordProductCharacteristics;
    }

    public void setOrdProductCharacteristics(OrdProductCharacteristicsDTO ordProductCharacteristics) {
        this.ordProductCharacteristics = ordProductCharacteristics;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrdProductDTO)) {
            return false;
        }

        OrdProductDTO ordProductDTO = (OrdProductDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ordProductDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdProductDTO{" +
            "id=" + getId() +
            ", versionId=" + getVersionId() +
            ", variationId=" + getVariationId() +
            ", lineOfService='" + getLineOfService() + "'" +
            ", assetId=" + getAssetId() +
            ", serialNo=" + getSerialNo() +
            ", name='" + getName() + "'" +
            ", ordProductCharacteristics=" + getOrdProductCharacteristics() +
            "}";
    }
}

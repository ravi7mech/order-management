package com.apptium.order.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.apptium.order.domain.OrdProvisiongChar} entity.
 */
public class OrdProvisiongCharDTO implements Serializable {

    private Long id;

    private String name;

    private String value;

    private String valueType;

    private String createdBy;

    private Instant createdDate;

    private OrdOrderItemProvisioningDTO ordOrderItemProvisioning;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public OrdOrderItemProvisioningDTO getOrdOrderItemProvisioning() {
        return ordOrderItemProvisioning;
    }

    public void setOrdOrderItemProvisioning(OrdOrderItemProvisioningDTO ordOrderItemProvisioning) {
        this.ordOrderItemProvisioning = ordOrderItemProvisioning;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrdProvisiongCharDTO)) {
            return false;
        }

        OrdProvisiongCharDTO ordProvisiongCharDTO = (OrdProvisiongCharDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ordProvisiongCharDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdProvisiongCharDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", value='" + getValue() + "'" +
            ", valueType='" + getValueType() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", ordOrderItemProvisioning=" + getOrdOrderItemProvisioning() +
            "}";
    }
}

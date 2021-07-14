package com.apptium.order.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.apptium.order.domain.OrdFulfillmentChar} entity.
 */
public class OrdFulfillmentCharDTO implements Serializable {

    private Long id;

    private String name;

    private String value;

    private String valueType;

    private String createdBy;

    private Instant createdDate;

    private OrdFulfillmentDTO ordFulfillment;

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

    public OrdFulfillmentDTO getOrdFulfillment() {
        return ordFulfillment;
    }

    public void setOrdFulfillment(OrdFulfillmentDTO ordFulfillment) {
        this.ordFulfillment = ordFulfillment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrdFulfillmentCharDTO)) {
            return false;
        }

        OrdFulfillmentCharDTO ordFulfillmentCharDTO = (OrdFulfillmentCharDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ordFulfillmentCharDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdFulfillmentCharDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", value='" + getValue() + "'" +
            ", valueType='" + getValueType() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", ordFulfillment=" + getOrdFulfillment() +
            "}";
    }
}

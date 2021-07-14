package com.apptium.order.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.apptium.order.domain.OrdProductCharacteristics} entity.
 */
public class OrdProductCharacteristicsDTO implements Serializable {

    private Long id;

    private String name;

    private String value;

    private String valueType;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrdProductCharacteristicsDTO)) {
            return false;
        }

        OrdProductCharacteristicsDTO ordProductCharacteristicsDTO = (OrdProductCharacteristicsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ordProductCharacteristicsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdProductCharacteristicsDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", value='" + getValue() + "'" +
            ", valueType='" + getValueType() + "'" +
            "}";
    }
}

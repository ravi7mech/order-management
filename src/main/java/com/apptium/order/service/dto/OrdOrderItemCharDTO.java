package com.apptium.order.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.apptium.order.domain.OrdOrderItemChar} entity.
 */
public class OrdOrderItemCharDTO implements Serializable {

    private Long id;

    private String name;

    private String value;

    private OrdOrderItemDTO ordOrderItem;

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

    public OrdOrderItemDTO getOrdOrderItem() {
        return ordOrderItem;
    }

    public void setOrdOrderItem(OrdOrderItemDTO ordOrderItem) {
        this.ordOrderItem = ordOrderItem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrdOrderItemCharDTO)) {
            return false;
        }

        OrdOrderItemCharDTO ordOrderItemCharDTO = (OrdOrderItemCharDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ordOrderItemCharDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdOrderItemCharDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", value='" + getValue() + "'" +
            ", ordOrderItem=" + getOrdOrderItem() +
            "}";
    }
}

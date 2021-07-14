package com.apptium.order.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.apptium.order.domain.OrdOrderItemRelationship} entity.
 */
public class OrdOrderItemRelationshipDTO implements Serializable {

    private Long id;

    private String type;

    private Long primaryOrderItemId;

    private Long secondaryOrderItemId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getPrimaryOrderItemId() {
        return primaryOrderItemId;
    }

    public void setPrimaryOrderItemId(Long primaryOrderItemId) {
        this.primaryOrderItemId = primaryOrderItemId;
    }

    public Long getSecondaryOrderItemId() {
        return secondaryOrderItemId;
    }

    public void setSecondaryOrderItemId(Long secondaryOrderItemId) {
        this.secondaryOrderItemId = secondaryOrderItemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrdOrderItemRelationshipDTO)) {
            return false;
        }

        OrdOrderItemRelationshipDTO ordOrderItemRelationshipDTO = (OrdOrderItemRelationshipDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ordOrderItemRelationshipDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdOrderItemRelationshipDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", primaryOrderItemId=" + getPrimaryOrderItemId() +
            ", secondaryOrderItemId=" + getSecondaryOrderItemId() +
            "}";
    }
}

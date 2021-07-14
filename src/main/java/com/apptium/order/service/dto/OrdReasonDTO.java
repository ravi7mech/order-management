package com.apptium.order.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.apptium.order.domain.OrdReason} entity.
 */
public class OrdReasonDTO implements Serializable {

    private Long id;

    private String reason;

    private String description;

    private OrdProductOrderDTO ordProductOrder;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OrdProductOrderDTO getOrdProductOrder() {
        return ordProductOrder;
    }

    public void setOrdProductOrder(OrdProductOrderDTO ordProductOrder) {
        this.ordProductOrder = ordProductOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrdReasonDTO)) {
            return false;
        }

        OrdReasonDTO ordReasonDTO = (OrdReasonDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ordReasonDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdReasonDTO{" +
            "id=" + getId() +
            ", reason='" + getReason() + "'" +
            ", description='" + getDescription() + "'" +
            ", ordProductOrder=" + getOrdProductOrder() +
            "}";
    }
}

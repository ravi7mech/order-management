package com.apptium.order.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.apptium.order.domain.OrdOrderItemProvisioning} entity.
 */
public class OrdOrderItemProvisioningDTO implements Serializable {

    private Long id;

    private Long provisioningId;

    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProvisioningId() {
        return provisioningId;
    }

    public void setProvisioningId(Long provisioningId) {
        this.provisioningId = provisioningId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrdOrderItemProvisioningDTO)) {
            return false;
        }

        OrdOrderItemProvisioningDTO ordOrderItemProvisioningDTO = (OrdOrderItemProvisioningDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ordOrderItemProvisioningDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdOrderItemProvisioningDTO{" +
            "id=" + getId() +
            ", provisioningId=" + getProvisioningId() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}

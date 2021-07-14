package com.apptium.order.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.apptium.order.domain.OrdProductOfferingRef} entity.
 */
public class OrdProductOfferingRefDTO implements Serializable {

    private Long id;

    private String href;

    private String name;

    private String productGuid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductGuid() {
        return productGuid;
    }

    public void setProductGuid(String productGuid) {
        this.productGuid = productGuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrdProductOfferingRefDTO)) {
            return false;
        }

        OrdProductOfferingRefDTO ordProductOfferingRefDTO = (OrdProductOfferingRefDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ordProductOfferingRefDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdProductOfferingRefDTO{" +
            "id=" + getId() +
            ", href='" + getHref() + "'" +
            ", name='" + getName() + "'" +
            ", productGuid='" + getProductGuid() + "'" +
            "}";
    }
}

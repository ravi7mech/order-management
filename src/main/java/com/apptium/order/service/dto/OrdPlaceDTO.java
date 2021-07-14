package com.apptium.order.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.apptium.order.domain.OrdPlace} entity.
 */
public class OrdPlaceDTO implements Serializable {

    private Long id;

    private String href;

    private String name;

    private String role;

    private OrdProductDTO ordProduct;

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public OrdProductDTO getOrdProduct() {
        return ordProduct;
    }

    public void setOrdProduct(OrdProductDTO ordProduct) {
        this.ordProduct = ordProduct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrdPlaceDTO)) {
            return false;
        }

        OrdPlaceDTO ordPlaceDTO = (OrdPlaceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ordPlaceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdPlaceDTO{" +
            "id=" + getId() +
            ", href='" + getHref() + "'" +
            ", name='" + getName() + "'" +
            ", role='" + getRole() + "'" +
            ", ordProduct=" + getOrdProduct() +
            "}";
    }
}

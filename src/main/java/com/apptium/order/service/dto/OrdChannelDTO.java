package com.apptium.order.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.apptium.order.domain.OrdChannel} entity.
 */
public class OrdChannelDTO implements Serializable {

    private Long id;

    private String href;

    private String name;

    private String role;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrdChannelDTO)) {
            return false;
        }

        OrdChannelDTO ordChannelDTO = (OrdChannelDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ordChannelDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdChannelDTO{" +
            "id=" + getId() +
            ", href='" + getHref() + "'" +
            ", name='" + getName() + "'" +
            ", role='" + getRole() + "'" +
            "}";
    }
}

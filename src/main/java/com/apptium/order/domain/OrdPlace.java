package com.apptium.order.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A OrdPlace.
 */
@Entity
@Table(name = "ord_place")
public class OrdPlace implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "href")
    private String href;

    @Column(name = "name")
    private String name;

    @Column(name = "role")
    private String role;

    @ManyToOne
    @JsonIgnoreProperties(value = { "ordProductCharacteristics", "ordPlaces", "ordOrderItem" }, allowSetters = true)
    private OrdProduct ordProduct;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrdPlace id(Long id) {
        this.id = id;
        return this;
    }

    public String getHref() {
        return this.href;
    }

    public OrdPlace href(String href) {
        this.href = href;
        return this;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getName() {
        return this.name;
    }

    public OrdPlace name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return this.role;
    }

    public OrdPlace role(String role) {
        this.role = role;
        return this;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public OrdProduct getOrdProduct() {
        return this.ordProduct;
    }

    public OrdPlace ordProduct(OrdProduct ordProduct) {
        this.setOrdProduct(ordProduct);
        return this;
    }

    public void setOrdProduct(OrdProduct ordProduct) {
        this.ordProduct = ordProduct;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrdPlace)) {
            return false;
        }
        return id != null && id.equals(((OrdPlace) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdPlace{" +
            "id=" + getId() +
            ", href='" + getHref() + "'" +
            ", name='" + getName() + "'" +
            ", role='" + getRole() + "'" +
            "}";
    }
}

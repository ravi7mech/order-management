package com.apptium.order.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A OrdProductCharacteristics.
 */
@Entity
@Table(name = "ord_product_characteristics")
public class OrdProductCharacteristics implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private String value;

    @Column(name = "value_type")
    private String valueType;

    @JsonIgnoreProperties(value = { "ordProductCharacteristics", "ordPlaces", "ordOrderItem" }, allowSetters = true)
    @OneToOne(mappedBy = "ordProductCharacteristics")
    private OrdProduct ordProduct;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrdProductCharacteristics id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public OrdProductCharacteristics name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return this.value;
    }

    public OrdProductCharacteristics value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValueType() {
        return this.valueType;
    }

    public OrdProductCharacteristics valueType(String valueType) {
        this.valueType = valueType;
        return this;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public OrdProduct getOrdProduct() {
        return this.ordProduct;
    }

    public OrdProductCharacteristics ordProduct(OrdProduct ordProduct) {
        this.setOrdProduct(ordProduct);
        return this;
    }

    public void setOrdProduct(OrdProduct ordProduct) {
        if (this.ordProduct != null) {
            this.ordProduct.setOrdProductCharacteristics(null);
        }
        if (ordProduct != null) {
            ordProduct.setOrdProductCharacteristics(this);
        }
        this.ordProduct = ordProduct;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrdProductCharacteristics)) {
            return false;
        }
        return id != null && id.equals(((OrdProductCharacteristics) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdProductCharacteristics{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", value='" + getValue() + "'" +
            ", valueType='" + getValueType() + "'" +
            "}";
    }
}

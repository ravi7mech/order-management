package com.apptium.order.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A OrdProductOfferingRef.
 */
@Entity
@Table(name = "ord_product_offering_ref")
public class OrdProductOfferingRef implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "href")
    private String href;

    @Column(name = "name")
    private String name;

    @Column(name = "product_guid")
    private String productGuid;

    @JsonIgnoreProperties(
        value = {
            "ordOrderPrice",
            "ordOrderItemRelationship",
            "ordProductOfferingRef",
            "ordProduct",
            "ordOrderItemProvisioning",
            "ordOrderItemChars",
            "ordProductOrder",
        },
        allowSetters = true
    )
    @OneToOne(mappedBy = "ordProductOfferingRef")
    private OrdOrderItem ordOrderItem;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrdProductOfferingRef id(Long id) {
        this.id = id;
        return this;
    }

    public String getHref() {
        return this.href;
    }

    public OrdProductOfferingRef href(String href) {
        this.href = href;
        return this;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getName() {
        return this.name;
    }

    public OrdProductOfferingRef name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductGuid() {
        return this.productGuid;
    }

    public OrdProductOfferingRef productGuid(String productGuid) {
        this.productGuid = productGuid;
        return this;
    }

    public void setProductGuid(String productGuid) {
        this.productGuid = productGuid;
    }

    public OrdOrderItem getOrdOrderItem() {
        return this.ordOrderItem;
    }

    public OrdProductOfferingRef ordOrderItem(OrdOrderItem ordOrderItem) {
        this.setOrdOrderItem(ordOrderItem);
        return this;
    }

    public void setOrdOrderItem(OrdOrderItem ordOrderItem) {
        if (this.ordOrderItem != null) {
            this.ordOrderItem.setOrdProductOfferingRef(null);
        }
        if (ordOrderItem != null) {
            ordOrderItem.setOrdProductOfferingRef(this);
        }
        this.ordOrderItem = ordOrderItem;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrdProductOfferingRef)) {
            return false;
        }
        return id != null && id.equals(((OrdProductOfferingRef) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdProductOfferingRef{" +
            "id=" + getId() +
            ", href='" + getHref() + "'" +
            ", name='" + getName() + "'" +
            ", productGuid='" + getProductGuid() + "'" +
            "}";
    }
}

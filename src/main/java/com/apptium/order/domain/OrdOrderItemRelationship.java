package com.apptium.order.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A OrdOrderItemRelationship.
 */
@Entity
@Table(name = "ord_order_item_relationship")
public class OrdOrderItemRelationship implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "primary_order_item_id")
    private Long primaryOrderItemId;

    @Column(name = "secondary_order_item_id")
    private Long secondaryOrderItemId;

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
    @OneToOne(mappedBy = "ordOrderItemRelationship")
    private OrdOrderItem ordOrderItem;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrdOrderItemRelationship id(Long id) {
        this.id = id;
        return this;
    }

    public String getType() {
        return this.type;
    }

    public OrdOrderItemRelationship type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getPrimaryOrderItemId() {
        return this.primaryOrderItemId;
    }

    public OrdOrderItemRelationship primaryOrderItemId(Long primaryOrderItemId) {
        this.primaryOrderItemId = primaryOrderItemId;
        return this;
    }

    public void setPrimaryOrderItemId(Long primaryOrderItemId) {
        this.primaryOrderItemId = primaryOrderItemId;
    }

    public Long getSecondaryOrderItemId() {
        return this.secondaryOrderItemId;
    }

    public OrdOrderItemRelationship secondaryOrderItemId(Long secondaryOrderItemId) {
        this.secondaryOrderItemId = secondaryOrderItemId;
        return this;
    }

    public void setSecondaryOrderItemId(Long secondaryOrderItemId) {
        this.secondaryOrderItemId = secondaryOrderItemId;
    }

    public OrdOrderItem getOrdOrderItem() {
        return this.ordOrderItem;
    }

    public OrdOrderItemRelationship ordOrderItem(OrdOrderItem ordOrderItem) {
        this.setOrdOrderItem(ordOrderItem);
        return this;
    }

    public void setOrdOrderItem(OrdOrderItem ordOrderItem) {
        if (this.ordOrderItem != null) {
            this.ordOrderItem.setOrdOrderItemRelationship(null);
        }
        if (ordOrderItem != null) {
            ordOrderItem.setOrdOrderItemRelationship(this);
        }
        this.ordOrderItem = ordOrderItem;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrdOrderItemRelationship)) {
            return false;
        }
        return id != null && id.equals(((OrdOrderItemRelationship) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdOrderItemRelationship{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", primaryOrderItemId=" + getPrimaryOrderItemId() +
            ", secondaryOrderItemId=" + getSecondaryOrderItemId() +
            "}";
    }
}

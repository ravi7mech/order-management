package com.apptium.order.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A OrdOrderItemProvisioning.
 */
@Entity
@Table(name = "ord_order_item_provisioning")
public class OrdOrderItemProvisioning implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "provisioning_id")
    private Long provisioningId;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "ordOrderItemProvisioning")
    @JsonIgnoreProperties(value = { "ordOrderItemProvisioning" }, allowSetters = true)
    private Set<OrdProvisiongChar> ordProvisiongChars = new HashSet<>();

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
    @OneToOne(mappedBy = "ordOrderItemProvisioning")
    private OrdOrderItem ordOrderItem;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrdOrderItemProvisioning id(Long id) {
        this.id = id;
        return this;
    }

    public Long getProvisioningId() {
        return this.provisioningId;
    }

    public OrdOrderItemProvisioning provisioningId(Long provisioningId) {
        this.provisioningId = provisioningId;
        return this;
    }

    public void setProvisioningId(Long provisioningId) {
        this.provisioningId = provisioningId;
    }

    public String getStatus() {
        return this.status;
    }

    public OrdOrderItemProvisioning status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<OrdProvisiongChar> getOrdProvisiongChars() {
        return this.ordProvisiongChars;
    }

    public OrdOrderItemProvisioning ordProvisiongChars(Set<OrdProvisiongChar> ordProvisiongChars) {
        this.setOrdProvisiongChars(ordProvisiongChars);
        return this;
    }

    public OrdOrderItemProvisioning addOrdProvisiongChar(OrdProvisiongChar ordProvisiongChar) {
        this.ordProvisiongChars.add(ordProvisiongChar);
        ordProvisiongChar.setOrdOrderItemProvisioning(this);
        return this;
    }

    public OrdOrderItemProvisioning removeOrdProvisiongChar(OrdProvisiongChar ordProvisiongChar) {
        this.ordProvisiongChars.remove(ordProvisiongChar);
        ordProvisiongChar.setOrdOrderItemProvisioning(null);
        return this;
    }

    public void setOrdProvisiongChars(Set<OrdProvisiongChar> ordProvisiongChars) {
        if (this.ordProvisiongChars != null) {
            this.ordProvisiongChars.forEach(i -> i.setOrdOrderItemProvisioning(null));
        }
        if (ordProvisiongChars != null) {
            ordProvisiongChars.forEach(i -> i.setOrdOrderItemProvisioning(this));
        }
        this.ordProvisiongChars = ordProvisiongChars;
    }

    public OrdOrderItem getOrdOrderItem() {
        return this.ordOrderItem;
    }

    public OrdOrderItemProvisioning ordOrderItem(OrdOrderItem ordOrderItem) {
        this.setOrdOrderItem(ordOrderItem);
        return this;
    }

    public void setOrdOrderItem(OrdOrderItem ordOrderItem) {
        if (this.ordOrderItem != null) {
            this.ordOrderItem.setOrdOrderItemProvisioning(null);
        }
        if (ordOrderItem != null) {
            ordOrderItem.setOrdOrderItemProvisioning(this);
        }
        this.ordOrderItem = ordOrderItem;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrdOrderItemProvisioning)) {
            return false;
        }
        return id != null && id.equals(((OrdOrderItemProvisioning) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdOrderItemProvisioning{" +
            "id=" + getId() +
            ", provisioningId=" + getProvisioningId() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}

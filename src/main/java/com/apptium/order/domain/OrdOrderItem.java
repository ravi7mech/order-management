package com.apptium.order.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A OrdOrderItem.
 */
@Entity
@Table(name = "ord_order_item")
public class OrdOrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "biller_id")
    private Long billerId;

    @Column(name = "fullfillment_id")
    private Long fullfillmentId;

    @Column(name = "acquisition_id")
    private Long acquisitionId;

    @Column(name = "action")
    private String action;

    @Column(name = "state")
    private String state;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "item_type")
    private String itemType;

    @Column(name = "item_description")
    private String itemDescription;

    @Column(name = "cart_item_id")
    private Long cartItemId;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @JsonIgnoreProperties(value = { "ordPriceAmount", "ordPriceAlterations", "ordProductOrder", "ordOrderItem" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private OrdOrderPrice ordOrderPrice;

    @JsonIgnoreProperties(value = { "ordOrderItem" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private OrdOrderItemRelationship ordOrderItemRelationship;

    @JsonIgnoreProperties(value = { "ordOrderItem" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private OrdProductOfferingRef ordProductOfferingRef;

    @JsonIgnoreProperties(value = { "ordProductCharacteristics", "ordPlaces", "ordOrderItem" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private OrdProduct ordProduct;

    @JsonIgnoreProperties(value = { "ordProvisiongChars", "ordOrderItem" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private OrdOrderItemProvisioning ordOrderItemProvisioning;

    @OneToMany(mappedBy = "ordOrderItem")
    @JsonIgnoreProperties(value = { "ordOrderItem" }, allowSetters = true)
    private Set<OrdOrderItemChar> ordOrderItemChars = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "ordContactDetails",
            "ordNote",
            "ordChannel",
            "ordOrderPrice",
            "ordBillingAccountRef",
            "ordCharacteristics",
            "ordOrderItems",
            "ordPaymentRefs",
            "ordReasons",
            "ordContracts",
            "ordFulfillments",
            "ordAcquisitions",
        },
        allowSetters = true
    )
    private OrdProductOrder ordProductOrder;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrdOrderItem id(Long id) {
        this.id = id;
        return this;
    }

    public Long getBillerId() {
        return this.billerId;
    }

    public OrdOrderItem billerId(Long billerId) {
        this.billerId = billerId;
        return this;
    }

    public void setBillerId(Long billerId) {
        this.billerId = billerId;
    }

    public Long getFullfillmentId() {
        return this.fullfillmentId;
    }

    public OrdOrderItem fullfillmentId(Long fullfillmentId) {
        this.fullfillmentId = fullfillmentId;
        return this;
    }

    public void setFullfillmentId(Long fullfillmentId) {
        this.fullfillmentId = fullfillmentId;
    }

    public Long getAcquisitionId() {
        return this.acquisitionId;
    }

    public OrdOrderItem acquisitionId(Long acquisitionId) {
        this.acquisitionId = acquisitionId;
        return this;
    }

    public void setAcquisitionId(Long acquisitionId) {
        this.acquisitionId = acquisitionId;
    }

    public String getAction() {
        return this.action;
    }

    public OrdOrderItem action(String action) {
        this.action = action;
        return this;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getState() {
        return this.state;
    }

    public OrdOrderItem state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getQuantity() {
        return this.quantity;
    }

    public OrdOrderItem quantity(Long quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getItemType() {
        return this.itemType;
    }

    public OrdOrderItem itemType(String itemType) {
        this.itemType = itemType;
        return this;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemDescription() {
        return this.itemDescription;
    }

    public OrdOrderItem itemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
        return this;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public Long getCartItemId() {
        return this.cartItemId;
    }

    public OrdOrderItem cartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
        return this;
    }

    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public OrdOrderItem createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public OrdOrderItem createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public OrdOrderItem updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedDate() {
        return this.updatedDate;
    }

    public OrdOrderItem updatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public OrdOrderPrice getOrdOrderPrice() {
        return this.ordOrderPrice;
    }

    public OrdOrderItem ordOrderPrice(OrdOrderPrice ordOrderPrice) {
        this.setOrdOrderPrice(ordOrderPrice);
        return this;
    }

    public void setOrdOrderPrice(OrdOrderPrice ordOrderPrice) {
        this.ordOrderPrice = ordOrderPrice;
    }

    public OrdOrderItemRelationship getOrdOrderItemRelationship() {
        return this.ordOrderItemRelationship;
    }

    public OrdOrderItem ordOrderItemRelationship(OrdOrderItemRelationship ordOrderItemRelationship) {
        this.setOrdOrderItemRelationship(ordOrderItemRelationship);
        return this;
    }

    public void setOrdOrderItemRelationship(OrdOrderItemRelationship ordOrderItemRelationship) {
        this.ordOrderItemRelationship = ordOrderItemRelationship;
    }

    public OrdProductOfferingRef getOrdProductOfferingRef() {
        return this.ordProductOfferingRef;
    }

    public OrdOrderItem ordProductOfferingRef(OrdProductOfferingRef ordProductOfferingRef) {
        this.setOrdProductOfferingRef(ordProductOfferingRef);
        return this;
    }

    public void setOrdProductOfferingRef(OrdProductOfferingRef ordProductOfferingRef) {
        this.ordProductOfferingRef = ordProductOfferingRef;
    }

    public OrdProduct getOrdProduct() {
        return this.ordProduct;
    }

    public OrdOrderItem ordProduct(OrdProduct ordProduct) {
        this.setOrdProduct(ordProduct);
        return this;
    }

    public void setOrdProduct(OrdProduct ordProduct) {
        this.ordProduct = ordProduct;
    }

    public OrdOrderItemProvisioning getOrdOrderItemProvisioning() {
        return this.ordOrderItemProvisioning;
    }

    public OrdOrderItem ordOrderItemProvisioning(OrdOrderItemProvisioning ordOrderItemProvisioning) {
        this.setOrdOrderItemProvisioning(ordOrderItemProvisioning);
        return this;
    }

    public void setOrdOrderItemProvisioning(OrdOrderItemProvisioning ordOrderItemProvisioning) {
        this.ordOrderItemProvisioning = ordOrderItemProvisioning;
    }

    public Set<OrdOrderItemChar> getOrdOrderItemChars() {
        return this.ordOrderItemChars;
    }

    public OrdOrderItem ordOrderItemChars(Set<OrdOrderItemChar> ordOrderItemChars) {
        this.setOrdOrderItemChars(ordOrderItemChars);
        return this;
    }

    public OrdOrderItem addOrdOrderItemChar(OrdOrderItemChar ordOrderItemChar) {
        this.ordOrderItemChars.add(ordOrderItemChar);
        ordOrderItemChar.setOrdOrderItem(this);
        return this;
    }

    public OrdOrderItem removeOrdOrderItemChar(OrdOrderItemChar ordOrderItemChar) {
        this.ordOrderItemChars.remove(ordOrderItemChar);
        ordOrderItemChar.setOrdOrderItem(null);
        return this;
    }

    public void setOrdOrderItemChars(Set<OrdOrderItemChar> ordOrderItemChars) {
        if (this.ordOrderItemChars != null) {
            this.ordOrderItemChars.forEach(i -> i.setOrdOrderItem(null));
        }
        if (ordOrderItemChars != null) {
            ordOrderItemChars.forEach(i -> i.setOrdOrderItem(this));
        }
        this.ordOrderItemChars = ordOrderItemChars;
    }

    public OrdProductOrder getOrdProductOrder() {
        return this.ordProductOrder;
    }

    public OrdOrderItem ordProductOrder(OrdProductOrder ordProductOrder) {
        this.setOrdProductOrder(ordProductOrder);
        return this;
    }

    public void setOrdProductOrder(OrdProductOrder ordProductOrder) {
        this.ordProductOrder = ordProductOrder;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrdOrderItem)) {
            return false;
        }
        return id != null && id.equals(((OrdOrderItem) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdOrderItem{" +
            "id=" + getId() +
            ", billerId=" + getBillerId() +
            ", fullfillmentId=" + getFullfillmentId() +
            ", acquisitionId=" + getAcquisitionId() +
            ", action='" + getAction() + "'" +
            ", state='" + getState() + "'" +
            ", quantity=" + getQuantity() +
            ", itemType='" + getItemType() + "'" +
            ", itemDescription='" + getItemDescription() + "'" +
            ", cartItemId=" + getCartItemId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            "}";
    }
}

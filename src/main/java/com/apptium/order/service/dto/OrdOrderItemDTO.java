package com.apptium.order.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.apptium.order.domain.OrdOrderItem} entity.
 */
public class OrdOrderItemDTO implements Serializable {

    private Long id;

    private Long billerId;

    private Long fullfillmentId;

    private Long acquisitionId;

    private String action;

    private String state;

    private Long quantity;

    private String itemType;

    private String itemDescription;

    private Long cartItemId;

    private String createdBy;

    private Instant createdDate;

    private String updatedBy;

    private Instant updatedDate;

    private OrdOrderPriceDTO ordOrderPrice;

    private OrdOrderItemRelationshipDTO ordOrderItemRelationship;

    private OrdProductOfferingRefDTO ordProductOfferingRef;

    private OrdProductDTO ordProduct;

    private OrdOrderItemProvisioningDTO ordOrderItemProvisioning;

    private OrdProductOrderDTO ordProductOrder;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBillerId() {
        return billerId;
    }

    public void setBillerId(Long billerId) {
        this.billerId = billerId;
    }

    public Long getFullfillmentId() {
        return fullfillmentId;
    }

    public void setFullfillmentId(Long fullfillmentId) {
        this.fullfillmentId = fullfillmentId;
    }

    public Long getAcquisitionId() {
        return acquisitionId;
    }

    public void setAcquisitionId(Long acquisitionId) {
        this.acquisitionId = acquisitionId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public Long getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public OrdOrderPriceDTO getOrdOrderPrice() {
        return ordOrderPrice;
    }

    public void setOrdOrderPrice(OrdOrderPriceDTO ordOrderPrice) {
        this.ordOrderPrice = ordOrderPrice;
    }

    public OrdOrderItemRelationshipDTO getOrdOrderItemRelationship() {
        return ordOrderItemRelationship;
    }

    public void setOrdOrderItemRelationship(OrdOrderItemRelationshipDTO ordOrderItemRelationship) {
        this.ordOrderItemRelationship = ordOrderItemRelationship;
    }

    public OrdProductOfferingRefDTO getOrdProductOfferingRef() {
        return ordProductOfferingRef;
    }

    public void setOrdProductOfferingRef(OrdProductOfferingRefDTO ordProductOfferingRef) {
        this.ordProductOfferingRef = ordProductOfferingRef;
    }

    public OrdProductDTO getOrdProduct() {
        return ordProduct;
    }

    public void setOrdProduct(OrdProductDTO ordProduct) {
        this.ordProduct = ordProduct;
    }

    public OrdOrderItemProvisioningDTO getOrdOrderItemProvisioning() {
        return ordOrderItemProvisioning;
    }

    public void setOrdOrderItemProvisioning(OrdOrderItemProvisioningDTO ordOrderItemProvisioning) {
        this.ordOrderItemProvisioning = ordOrderItemProvisioning;
    }

    public OrdProductOrderDTO getOrdProductOrder() {
        return ordProductOrder;
    }

    public void setOrdProductOrder(OrdProductOrderDTO ordProductOrder) {
        this.ordProductOrder = ordProductOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrdOrderItemDTO)) {
            return false;
        }

        OrdOrderItemDTO ordOrderItemDTO = (OrdOrderItemDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ordOrderItemDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdOrderItemDTO{" +
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
            ", ordOrderPrice=" + getOrdOrderPrice() +
            ", ordOrderItemRelationship=" + getOrdOrderItemRelationship() +
            ", ordProductOfferingRef=" + getOrdProductOfferingRef() +
            ", ordProduct=" + getOrdProduct() +
            ", ordOrderItemProvisioning=" + getOrdOrderItemProvisioning() +
            ", ordProductOrder=" + getOrdProductOrder() +
            "}";
    }
}

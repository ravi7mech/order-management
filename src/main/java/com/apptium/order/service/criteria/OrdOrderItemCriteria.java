package com.apptium.order.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.apptium.order.domain.OrdOrderItem} entity. This class is used
 * in {@link com.apptium.order.web.rest.OrdOrderItemResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ord-order-items?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OrdOrderItemCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter billerId;

    private LongFilter fullfillmentId;

    private LongFilter acquisitionId;

    private StringFilter action;

    private StringFilter state;

    private LongFilter quantity;

    private StringFilter itemType;

    private StringFilter itemDescription;

    private LongFilter cartItemId;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter updatedBy;

    private InstantFilter updatedDate;

    private LongFilter ordOrderPriceId;

    private LongFilter ordOrderItemRelationshipId;

    private LongFilter ordProductOfferingRefId;

    private LongFilter ordProductId;

    private LongFilter ordOrderItemProvisioningId;

    private LongFilter ordOrderItemCharId;

    private LongFilter ordProductOrderId;

    public OrdOrderItemCriteria() {}

    public OrdOrderItemCriteria(OrdOrderItemCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.billerId = other.billerId == null ? null : other.billerId.copy();
        this.fullfillmentId = other.fullfillmentId == null ? null : other.fullfillmentId.copy();
        this.acquisitionId = other.acquisitionId == null ? null : other.acquisitionId.copy();
        this.action = other.action == null ? null : other.action.copy();
        this.state = other.state == null ? null : other.state.copy();
        this.quantity = other.quantity == null ? null : other.quantity.copy();
        this.itemType = other.itemType == null ? null : other.itemType.copy();
        this.itemDescription = other.itemDescription == null ? null : other.itemDescription.copy();
        this.cartItemId = other.cartItemId == null ? null : other.cartItemId.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.updatedBy = other.updatedBy == null ? null : other.updatedBy.copy();
        this.updatedDate = other.updatedDate == null ? null : other.updatedDate.copy();
        this.ordOrderPriceId = other.ordOrderPriceId == null ? null : other.ordOrderPriceId.copy();
        this.ordOrderItemRelationshipId = other.ordOrderItemRelationshipId == null ? null : other.ordOrderItemRelationshipId.copy();
        this.ordProductOfferingRefId = other.ordProductOfferingRefId == null ? null : other.ordProductOfferingRefId.copy();
        this.ordProductId = other.ordProductId == null ? null : other.ordProductId.copy();
        this.ordOrderItemProvisioningId = other.ordOrderItemProvisioningId == null ? null : other.ordOrderItemProvisioningId.copy();
        this.ordOrderItemCharId = other.ordOrderItemCharId == null ? null : other.ordOrderItemCharId.copy();
        this.ordProductOrderId = other.ordProductOrderId == null ? null : other.ordProductOrderId.copy();
    }

    @Override
    public OrdOrderItemCriteria copy() {
        return new OrdOrderItemCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getBillerId() {
        return billerId;
    }

    public LongFilter billerId() {
        if (billerId == null) {
            billerId = new LongFilter();
        }
        return billerId;
    }

    public void setBillerId(LongFilter billerId) {
        this.billerId = billerId;
    }

    public LongFilter getFullfillmentId() {
        return fullfillmentId;
    }

    public LongFilter fullfillmentId() {
        if (fullfillmentId == null) {
            fullfillmentId = new LongFilter();
        }
        return fullfillmentId;
    }

    public void setFullfillmentId(LongFilter fullfillmentId) {
        this.fullfillmentId = fullfillmentId;
    }

    public LongFilter getAcquisitionId() {
        return acquisitionId;
    }

    public LongFilter acquisitionId() {
        if (acquisitionId == null) {
            acquisitionId = new LongFilter();
        }
        return acquisitionId;
    }

    public void setAcquisitionId(LongFilter acquisitionId) {
        this.acquisitionId = acquisitionId;
    }

    public StringFilter getAction() {
        return action;
    }

    public StringFilter action() {
        if (action == null) {
            action = new StringFilter();
        }
        return action;
    }

    public void setAction(StringFilter action) {
        this.action = action;
    }

    public StringFilter getState() {
        return state;
    }

    public StringFilter state() {
        if (state == null) {
            state = new StringFilter();
        }
        return state;
    }

    public void setState(StringFilter state) {
        this.state = state;
    }

    public LongFilter getQuantity() {
        return quantity;
    }

    public LongFilter quantity() {
        if (quantity == null) {
            quantity = new LongFilter();
        }
        return quantity;
    }

    public void setQuantity(LongFilter quantity) {
        this.quantity = quantity;
    }

    public StringFilter getItemType() {
        return itemType;
    }

    public StringFilter itemType() {
        if (itemType == null) {
            itemType = new StringFilter();
        }
        return itemType;
    }

    public void setItemType(StringFilter itemType) {
        this.itemType = itemType;
    }

    public StringFilter getItemDescription() {
        return itemDescription;
    }

    public StringFilter itemDescription() {
        if (itemDescription == null) {
            itemDescription = new StringFilter();
        }
        return itemDescription;
    }

    public void setItemDescription(StringFilter itemDescription) {
        this.itemDescription = itemDescription;
    }

    public LongFilter getCartItemId() {
        return cartItemId;
    }

    public LongFilter cartItemId() {
        if (cartItemId == null) {
            cartItemId = new LongFilter();
        }
        return cartItemId;
    }

    public void setCartItemId(LongFilter cartItemId) {
        this.cartItemId = cartItemId;
    }

    public StringFilter getCreatedBy() {
        return createdBy;
    }

    public StringFilter createdBy() {
        if (createdBy == null) {
            createdBy = new StringFilter();
        }
        return createdBy;
    }

    public void setCreatedBy(StringFilter createdBy) {
        this.createdBy = createdBy;
    }

    public InstantFilter getCreatedDate() {
        return createdDate;
    }

    public InstantFilter createdDate() {
        if (createdDate == null) {
            createdDate = new InstantFilter();
        }
        return createdDate;
    }

    public void setCreatedDate(InstantFilter createdDate) {
        this.createdDate = createdDate;
    }

    public StringFilter getUpdatedBy() {
        return updatedBy;
    }

    public StringFilter updatedBy() {
        if (updatedBy == null) {
            updatedBy = new StringFilter();
        }
        return updatedBy;
    }

    public void setUpdatedBy(StringFilter updatedBy) {
        this.updatedBy = updatedBy;
    }

    public InstantFilter getUpdatedDate() {
        return updatedDate;
    }

    public InstantFilter updatedDate() {
        if (updatedDate == null) {
            updatedDate = new InstantFilter();
        }
        return updatedDate;
    }

    public void setUpdatedDate(InstantFilter updatedDate) {
        this.updatedDate = updatedDate;
    }

    public LongFilter getOrdOrderPriceId() {
        return ordOrderPriceId;
    }

    public LongFilter ordOrderPriceId() {
        if (ordOrderPriceId == null) {
            ordOrderPriceId = new LongFilter();
        }
        return ordOrderPriceId;
    }

    public void setOrdOrderPriceId(LongFilter ordOrderPriceId) {
        this.ordOrderPriceId = ordOrderPriceId;
    }

    public LongFilter getOrdOrderItemRelationshipId() {
        return ordOrderItemRelationshipId;
    }

    public LongFilter ordOrderItemRelationshipId() {
        if (ordOrderItemRelationshipId == null) {
            ordOrderItemRelationshipId = new LongFilter();
        }
        return ordOrderItemRelationshipId;
    }

    public void setOrdOrderItemRelationshipId(LongFilter ordOrderItemRelationshipId) {
        this.ordOrderItemRelationshipId = ordOrderItemRelationshipId;
    }

    public LongFilter getOrdProductOfferingRefId() {
        return ordProductOfferingRefId;
    }

    public LongFilter ordProductOfferingRefId() {
        if (ordProductOfferingRefId == null) {
            ordProductOfferingRefId = new LongFilter();
        }
        return ordProductOfferingRefId;
    }

    public void setOrdProductOfferingRefId(LongFilter ordProductOfferingRefId) {
        this.ordProductOfferingRefId = ordProductOfferingRefId;
    }

    public LongFilter getOrdProductId() {
        return ordProductId;
    }

    public LongFilter ordProductId() {
        if (ordProductId == null) {
            ordProductId = new LongFilter();
        }
        return ordProductId;
    }

    public void setOrdProductId(LongFilter ordProductId) {
        this.ordProductId = ordProductId;
    }

    public LongFilter getOrdOrderItemProvisioningId() {
        return ordOrderItemProvisioningId;
    }

    public LongFilter ordOrderItemProvisioningId() {
        if (ordOrderItemProvisioningId == null) {
            ordOrderItemProvisioningId = new LongFilter();
        }
        return ordOrderItemProvisioningId;
    }

    public void setOrdOrderItemProvisioningId(LongFilter ordOrderItemProvisioningId) {
        this.ordOrderItemProvisioningId = ordOrderItemProvisioningId;
    }

    public LongFilter getOrdOrderItemCharId() {
        return ordOrderItemCharId;
    }

    public LongFilter ordOrderItemCharId() {
        if (ordOrderItemCharId == null) {
            ordOrderItemCharId = new LongFilter();
        }
        return ordOrderItemCharId;
    }

    public void setOrdOrderItemCharId(LongFilter ordOrderItemCharId) {
        this.ordOrderItemCharId = ordOrderItemCharId;
    }

    public LongFilter getOrdProductOrderId() {
        return ordProductOrderId;
    }

    public LongFilter ordProductOrderId() {
        if (ordProductOrderId == null) {
            ordProductOrderId = new LongFilter();
        }
        return ordProductOrderId;
    }

    public void setOrdProductOrderId(LongFilter ordProductOrderId) {
        this.ordProductOrderId = ordProductOrderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OrdOrderItemCriteria that = (OrdOrderItemCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(billerId, that.billerId) &&
            Objects.equals(fullfillmentId, that.fullfillmentId) &&
            Objects.equals(acquisitionId, that.acquisitionId) &&
            Objects.equals(action, that.action) &&
            Objects.equals(state, that.state) &&
            Objects.equals(quantity, that.quantity) &&
            Objects.equals(itemType, that.itemType) &&
            Objects.equals(itemDescription, that.itemDescription) &&
            Objects.equals(cartItemId, that.cartItemId) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updatedDate, that.updatedDate) &&
            Objects.equals(ordOrderPriceId, that.ordOrderPriceId) &&
            Objects.equals(ordOrderItemRelationshipId, that.ordOrderItemRelationshipId) &&
            Objects.equals(ordProductOfferingRefId, that.ordProductOfferingRefId) &&
            Objects.equals(ordProductId, that.ordProductId) &&
            Objects.equals(ordOrderItemProvisioningId, that.ordOrderItemProvisioningId) &&
            Objects.equals(ordOrderItemCharId, that.ordOrderItemCharId) &&
            Objects.equals(ordProductOrderId, that.ordProductOrderId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            billerId,
            fullfillmentId,
            acquisitionId,
            action,
            state,
            quantity,
            itemType,
            itemDescription,
            cartItemId,
            createdBy,
            createdDate,
            updatedBy,
            updatedDate,
            ordOrderPriceId,
            ordOrderItemRelationshipId,
            ordProductOfferingRefId,
            ordProductId,
            ordOrderItemProvisioningId,
            ordOrderItemCharId,
            ordProductOrderId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdOrderItemCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (billerId != null ? "billerId=" + billerId + ", " : "") +
            (fullfillmentId != null ? "fullfillmentId=" + fullfillmentId + ", " : "") +
            (acquisitionId != null ? "acquisitionId=" + acquisitionId + ", " : "") +
            (action != null ? "action=" + action + ", " : "") +
            (state != null ? "state=" + state + ", " : "") +
            (quantity != null ? "quantity=" + quantity + ", " : "") +
            (itemType != null ? "itemType=" + itemType + ", " : "") +
            (itemDescription != null ? "itemDescription=" + itemDescription + ", " : "") +
            (cartItemId != null ? "cartItemId=" + cartItemId + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
            (updatedBy != null ? "updatedBy=" + updatedBy + ", " : "") +
            (updatedDate != null ? "updatedDate=" + updatedDate + ", " : "") +
            (ordOrderPriceId != null ? "ordOrderPriceId=" + ordOrderPriceId + ", " : "") +
            (ordOrderItemRelationshipId != null ? "ordOrderItemRelationshipId=" + ordOrderItemRelationshipId + ", " : "") +
            (ordProductOfferingRefId != null ? "ordProductOfferingRefId=" + ordProductOfferingRefId + ", " : "") +
            (ordProductId != null ? "ordProductId=" + ordProductId + ", " : "") +
            (ordOrderItemProvisioningId != null ? "ordOrderItemProvisioningId=" + ordOrderItemProvisioningId + ", " : "") +
            (ordOrderItemCharId != null ? "ordOrderItemCharId=" + ordOrderItemCharId + ", " : "") +
            (ordProductOrderId != null ? "ordProductOrderId=" + ordProductOrderId + ", " : "") +
            "}";
    }
}

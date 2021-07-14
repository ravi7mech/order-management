package com.apptium.order.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.apptium.order.domain.OrdBillingAccountRef} entity. This class is used
 * in {@link com.apptium.order.web.rest.OrdBillingAccountRefResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ord-billing-account-refs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OrdBillingAccountRefCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter href;

    private LongFilter cartPriceId;

    private LongFilter billingAccountId;

    private StringFilter billingSystem;

    private StringFilter deliveryMethod;

    private StringFilter billingAddress;

    private StringFilter status;

    private LongFilter quoteId;

    private LongFilter salesOrderId;

    private LongFilter ordProductOrderId;

    public OrdBillingAccountRefCriteria() {}

    public OrdBillingAccountRefCriteria(OrdBillingAccountRefCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.href = other.href == null ? null : other.href.copy();
        this.cartPriceId = other.cartPriceId == null ? null : other.cartPriceId.copy();
        this.billingAccountId = other.billingAccountId == null ? null : other.billingAccountId.copy();
        this.billingSystem = other.billingSystem == null ? null : other.billingSystem.copy();
        this.deliveryMethod = other.deliveryMethod == null ? null : other.deliveryMethod.copy();
        this.billingAddress = other.billingAddress == null ? null : other.billingAddress.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.quoteId = other.quoteId == null ? null : other.quoteId.copy();
        this.salesOrderId = other.salesOrderId == null ? null : other.salesOrderId.copy();
        this.ordProductOrderId = other.ordProductOrderId == null ? null : other.ordProductOrderId.copy();
    }

    @Override
    public OrdBillingAccountRefCriteria copy() {
        return new OrdBillingAccountRefCriteria(this);
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

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getHref() {
        return href;
    }

    public StringFilter href() {
        if (href == null) {
            href = new StringFilter();
        }
        return href;
    }

    public void setHref(StringFilter href) {
        this.href = href;
    }

    public LongFilter getCartPriceId() {
        return cartPriceId;
    }

    public LongFilter cartPriceId() {
        if (cartPriceId == null) {
            cartPriceId = new LongFilter();
        }
        return cartPriceId;
    }

    public void setCartPriceId(LongFilter cartPriceId) {
        this.cartPriceId = cartPriceId;
    }

    public LongFilter getBillingAccountId() {
        return billingAccountId;
    }

    public LongFilter billingAccountId() {
        if (billingAccountId == null) {
            billingAccountId = new LongFilter();
        }
        return billingAccountId;
    }

    public void setBillingAccountId(LongFilter billingAccountId) {
        this.billingAccountId = billingAccountId;
    }

    public StringFilter getBillingSystem() {
        return billingSystem;
    }

    public StringFilter billingSystem() {
        if (billingSystem == null) {
            billingSystem = new StringFilter();
        }
        return billingSystem;
    }

    public void setBillingSystem(StringFilter billingSystem) {
        this.billingSystem = billingSystem;
    }

    public StringFilter getDeliveryMethod() {
        return deliveryMethod;
    }

    public StringFilter deliveryMethod() {
        if (deliveryMethod == null) {
            deliveryMethod = new StringFilter();
        }
        return deliveryMethod;
    }

    public void setDeliveryMethod(StringFilter deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public StringFilter getBillingAddress() {
        return billingAddress;
    }

    public StringFilter billingAddress() {
        if (billingAddress == null) {
            billingAddress = new StringFilter();
        }
        return billingAddress;
    }

    public void setBillingAddress(StringFilter billingAddress) {
        this.billingAddress = billingAddress;
    }

    public StringFilter getStatus() {
        return status;
    }

    public StringFilter status() {
        if (status == null) {
            status = new StringFilter();
        }
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
    }

    public LongFilter getQuoteId() {
        return quoteId;
    }

    public LongFilter quoteId() {
        if (quoteId == null) {
            quoteId = new LongFilter();
        }
        return quoteId;
    }

    public void setQuoteId(LongFilter quoteId) {
        this.quoteId = quoteId;
    }

    public LongFilter getSalesOrderId() {
        return salesOrderId;
    }

    public LongFilter salesOrderId() {
        if (salesOrderId == null) {
            salesOrderId = new LongFilter();
        }
        return salesOrderId;
    }

    public void setSalesOrderId(LongFilter salesOrderId) {
        this.salesOrderId = salesOrderId;
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
        final OrdBillingAccountRefCriteria that = (OrdBillingAccountRefCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(href, that.href) &&
            Objects.equals(cartPriceId, that.cartPriceId) &&
            Objects.equals(billingAccountId, that.billingAccountId) &&
            Objects.equals(billingSystem, that.billingSystem) &&
            Objects.equals(deliveryMethod, that.deliveryMethod) &&
            Objects.equals(billingAddress, that.billingAddress) &&
            Objects.equals(status, that.status) &&
            Objects.equals(quoteId, that.quoteId) &&
            Objects.equals(salesOrderId, that.salesOrderId) &&
            Objects.equals(ordProductOrderId, that.ordProductOrderId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            href,
            cartPriceId,
            billingAccountId,
            billingSystem,
            deliveryMethod,
            billingAddress,
            status,
            quoteId,
            salesOrderId,
            ordProductOrderId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdBillingAccountRefCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (href != null ? "href=" + href + ", " : "") +
            (cartPriceId != null ? "cartPriceId=" + cartPriceId + ", " : "") +
            (billingAccountId != null ? "billingAccountId=" + billingAccountId + ", " : "") +
            (billingSystem != null ? "billingSystem=" + billingSystem + ", " : "") +
            (deliveryMethod != null ? "deliveryMethod=" + deliveryMethod + ", " : "") +
            (billingAddress != null ? "billingAddress=" + billingAddress + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (quoteId != null ? "quoteId=" + quoteId + ", " : "") +
            (salesOrderId != null ? "salesOrderId=" + salesOrderId + ", " : "") +
            (ordProductOrderId != null ? "ordProductOrderId=" + ordProductOrderId + ", " : "") +
            "}";
    }
}

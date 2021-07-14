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
 * Criteria class for the {@link com.apptium.order.domain.OrdOrderPrice} entity. This class is used
 * in {@link com.apptium.order.web.rest.OrdOrderPriceResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ord-order-prices?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OrdOrderPriceCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private StringFilter priceType;

    private StringFilter unitOfMeasure;

    private StringFilter recurringChargePeriod;

    private LongFilter priceId;

    private LongFilter ordPriceAmountId;

    private LongFilter ordPriceAlterationId;

    private LongFilter ordProductOrderId;

    private LongFilter ordOrderItemId;

    public OrdOrderPriceCriteria() {}

    public OrdOrderPriceCriteria(OrdOrderPriceCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.priceType = other.priceType == null ? null : other.priceType.copy();
        this.unitOfMeasure = other.unitOfMeasure == null ? null : other.unitOfMeasure.copy();
        this.recurringChargePeriod = other.recurringChargePeriod == null ? null : other.recurringChargePeriod.copy();
        this.priceId = other.priceId == null ? null : other.priceId.copy();
        this.ordPriceAmountId = other.ordPriceAmountId == null ? null : other.ordPriceAmountId.copy();
        this.ordPriceAlterationId = other.ordPriceAlterationId == null ? null : other.ordPriceAlterationId.copy();
        this.ordProductOrderId = other.ordProductOrderId == null ? null : other.ordProductOrderId.copy();
        this.ordOrderItemId = other.ordOrderItemId == null ? null : other.ordOrderItemId.copy();
    }

    @Override
    public OrdOrderPriceCriteria copy() {
        return new OrdOrderPriceCriteria(this);
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

    public StringFilter getDescription() {
        return description;
    }

    public StringFilter description() {
        if (description == null) {
            description = new StringFilter();
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getPriceType() {
        return priceType;
    }

    public StringFilter priceType() {
        if (priceType == null) {
            priceType = new StringFilter();
        }
        return priceType;
    }

    public void setPriceType(StringFilter priceType) {
        this.priceType = priceType;
    }

    public StringFilter getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public StringFilter unitOfMeasure() {
        if (unitOfMeasure == null) {
            unitOfMeasure = new StringFilter();
        }
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(StringFilter unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public StringFilter getRecurringChargePeriod() {
        return recurringChargePeriod;
    }

    public StringFilter recurringChargePeriod() {
        if (recurringChargePeriod == null) {
            recurringChargePeriod = new StringFilter();
        }
        return recurringChargePeriod;
    }

    public void setRecurringChargePeriod(StringFilter recurringChargePeriod) {
        this.recurringChargePeriod = recurringChargePeriod;
    }

    public LongFilter getPriceId() {
        return priceId;
    }

    public LongFilter priceId() {
        if (priceId == null) {
            priceId = new LongFilter();
        }
        return priceId;
    }

    public void setPriceId(LongFilter priceId) {
        this.priceId = priceId;
    }

    public LongFilter getOrdPriceAmountId() {
        return ordPriceAmountId;
    }

    public LongFilter ordPriceAmountId() {
        if (ordPriceAmountId == null) {
            ordPriceAmountId = new LongFilter();
        }
        return ordPriceAmountId;
    }

    public void setOrdPriceAmountId(LongFilter ordPriceAmountId) {
        this.ordPriceAmountId = ordPriceAmountId;
    }

    public LongFilter getOrdPriceAlterationId() {
        return ordPriceAlterationId;
    }

    public LongFilter ordPriceAlterationId() {
        if (ordPriceAlterationId == null) {
            ordPriceAlterationId = new LongFilter();
        }
        return ordPriceAlterationId;
    }

    public void setOrdPriceAlterationId(LongFilter ordPriceAlterationId) {
        this.ordPriceAlterationId = ordPriceAlterationId;
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

    public LongFilter getOrdOrderItemId() {
        return ordOrderItemId;
    }

    public LongFilter ordOrderItemId() {
        if (ordOrderItemId == null) {
            ordOrderItemId = new LongFilter();
        }
        return ordOrderItemId;
    }

    public void setOrdOrderItemId(LongFilter ordOrderItemId) {
        this.ordOrderItemId = ordOrderItemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OrdOrderPriceCriteria that = (OrdOrderPriceCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(priceType, that.priceType) &&
            Objects.equals(unitOfMeasure, that.unitOfMeasure) &&
            Objects.equals(recurringChargePeriod, that.recurringChargePeriod) &&
            Objects.equals(priceId, that.priceId) &&
            Objects.equals(ordPriceAmountId, that.ordPriceAmountId) &&
            Objects.equals(ordPriceAlterationId, that.ordPriceAlterationId) &&
            Objects.equals(ordProductOrderId, that.ordProductOrderId) &&
            Objects.equals(ordOrderItemId, that.ordOrderItemId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            description,
            priceType,
            unitOfMeasure,
            recurringChargePeriod,
            priceId,
            ordPriceAmountId,
            ordPriceAlterationId,
            ordProductOrderId,
            ordOrderItemId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdOrderPriceCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (priceType != null ? "priceType=" + priceType + ", " : "") +
            (unitOfMeasure != null ? "unitOfMeasure=" + unitOfMeasure + ", " : "") +
            (recurringChargePeriod != null ? "recurringChargePeriod=" + recurringChargePeriod + ", " : "") +
            (priceId != null ? "priceId=" + priceId + ", " : "") +
            (ordPriceAmountId != null ? "ordPriceAmountId=" + ordPriceAmountId + ", " : "") +
            (ordPriceAlterationId != null ? "ordPriceAlterationId=" + ordPriceAlterationId + ", " : "") +
            (ordProductOrderId != null ? "ordProductOrderId=" + ordProductOrderId + ", " : "") +
            (ordOrderItemId != null ? "ordOrderItemId=" + ordOrderItemId + ", " : "") +
            "}";
    }
}

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
 * Criteria class for the {@link com.apptium.order.domain.OrdPriceAlteration} entity. This class is used
 * in {@link com.apptium.order.web.rest.OrdPriceAlterationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ord-price-alterations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OrdPriceAlterationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private StringFilter priceType;

    private StringFilter unitOfMeasure;

    private StringFilter recurringChargePeriod;

    private StringFilter applicationDuration;

    private StringFilter priority;

    private LongFilter ordPriceAmountId;

    private LongFilter ordOrderPriceId;

    public OrdPriceAlterationCriteria() {}

    public OrdPriceAlterationCriteria(OrdPriceAlterationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.priceType = other.priceType == null ? null : other.priceType.copy();
        this.unitOfMeasure = other.unitOfMeasure == null ? null : other.unitOfMeasure.copy();
        this.recurringChargePeriod = other.recurringChargePeriod == null ? null : other.recurringChargePeriod.copy();
        this.applicationDuration = other.applicationDuration == null ? null : other.applicationDuration.copy();
        this.priority = other.priority == null ? null : other.priority.copy();
        this.ordPriceAmountId = other.ordPriceAmountId == null ? null : other.ordPriceAmountId.copy();
        this.ordOrderPriceId = other.ordOrderPriceId == null ? null : other.ordOrderPriceId.copy();
    }

    @Override
    public OrdPriceAlterationCriteria copy() {
        return new OrdPriceAlterationCriteria(this);
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

    public StringFilter getApplicationDuration() {
        return applicationDuration;
    }

    public StringFilter applicationDuration() {
        if (applicationDuration == null) {
            applicationDuration = new StringFilter();
        }
        return applicationDuration;
    }

    public void setApplicationDuration(StringFilter applicationDuration) {
        this.applicationDuration = applicationDuration;
    }

    public StringFilter getPriority() {
        return priority;
    }

    public StringFilter priority() {
        if (priority == null) {
            priority = new StringFilter();
        }
        return priority;
    }

    public void setPriority(StringFilter priority) {
        this.priority = priority;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OrdPriceAlterationCriteria that = (OrdPriceAlterationCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(priceType, that.priceType) &&
            Objects.equals(unitOfMeasure, that.unitOfMeasure) &&
            Objects.equals(recurringChargePeriod, that.recurringChargePeriod) &&
            Objects.equals(applicationDuration, that.applicationDuration) &&
            Objects.equals(priority, that.priority) &&
            Objects.equals(ordPriceAmountId, that.ordPriceAmountId) &&
            Objects.equals(ordOrderPriceId, that.ordOrderPriceId)
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
            applicationDuration,
            priority,
            ordPriceAmountId,
            ordOrderPriceId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdPriceAlterationCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (priceType != null ? "priceType=" + priceType + ", " : "") +
            (unitOfMeasure != null ? "unitOfMeasure=" + unitOfMeasure + ", " : "") +
            (recurringChargePeriod != null ? "recurringChargePeriod=" + recurringChargePeriod + ", " : "") +
            (applicationDuration != null ? "applicationDuration=" + applicationDuration + ", " : "") +
            (priority != null ? "priority=" + priority + ", " : "") +
            (ordPriceAmountId != null ? "ordPriceAmountId=" + ordPriceAmountId + ", " : "") +
            (ordOrderPriceId != null ? "ordOrderPriceId=" + ordOrderPriceId + ", " : "") +
            "}";
    }
}

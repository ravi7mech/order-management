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
 * Criteria class for the {@link com.apptium.order.domain.OrdReason} entity. This class is used
 * in {@link com.apptium.order.web.rest.OrdReasonResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ord-reasons?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OrdReasonCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter reason;

    private StringFilter description;

    private LongFilter ordProductOrderId;

    public OrdReasonCriteria() {}

    public OrdReasonCriteria(OrdReasonCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.reason = other.reason == null ? null : other.reason.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.ordProductOrderId = other.ordProductOrderId == null ? null : other.ordProductOrderId.copy();
    }

    @Override
    public OrdReasonCriteria copy() {
        return new OrdReasonCriteria(this);
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

    public StringFilter getReason() {
        return reason;
    }

    public StringFilter reason() {
        if (reason == null) {
            reason = new StringFilter();
        }
        return reason;
    }

    public void setReason(StringFilter reason) {
        this.reason = reason;
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
        final OrdReasonCriteria that = (OrdReasonCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(reason, that.reason) &&
            Objects.equals(description, that.description) &&
            Objects.equals(ordProductOrderId, that.ordProductOrderId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, reason, description, ordProductOrderId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdReasonCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (reason != null ? "reason=" + reason + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (ordProductOrderId != null ? "ordProductOrderId=" + ordProductOrderId + ", " : "") +
            "}";
    }
}

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
 * Criteria class for the {@link com.apptium.order.domain.OrdOrderItemRelationship} entity. This class is used
 * in {@link com.apptium.order.web.rest.OrdOrderItemRelationshipResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ord-order-item-relationships?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OrdOrderItemRelationshipCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter type;

    private LongFilter primaryOrderItemId;

    private LongFilter secondaryOrderItemId;

    private LongFilter ordOrderItemId;

    public OrdOrderItemRelationshipCriteria() {}

    public OrdOrderItemRelationshipCriteria(OrdOrderItemRelationshipCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.primaryOrderItemId = other.primaryOrderItemId == null ? null : other.primaryOrderItemId.copy();
        this.secondaryOrderItemId = other.secondaryOrderItemId == null ? null : other.secondaryOrderItemId.copy();
        this.ordOrderItemId = other.ordOrderItemId == null ? null : other.ordOrderItemId.copy();
    }

    @Override
    public OrdOrderItemRelationshipCriteria copy() {
        return new OrdOrderItemRelationshipCriteria(this);
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

    public StringFilter getType() {
        return type;
    }

    public StringFilter type() {
        if (type == null) {
            type = new StringFilter();
        }
        return type;
    }

    public void setType(StringFilter type) {
        this.type = type;
    }

    public LongFilter getPrimaryOrderItemId() {
        return primaryOrderItemId;
    }

    public LongFilter primaryOrderItemId() {
        if (primaryOrderItemId == null) {
            primaryOrderItemId = new LongFilter();
        }
        return primaryOrderItemId;
    }

    public void setPrimaryOrderItemId(LongFilter primaryOrderItemId) {
        this.primaryOrderItemId = primaryOrderItemId;
    }

    public LongFilter getSecondaryOrderItemId() {
        return secondaryOrderItemId;
    }

    public LongFilter secondaryOrderItemId() {
        if (secondaryOrderItemId == null) {
            secondaryOrderItemId = new LongFilter();
        }
        return secondaryOrderItemId;
    }

    public void setSecondaryOrderItemId(LongFilter secondaryOrderItemId) {
        this.secondaryOrderItemId = secondaryOrderItemId;
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
        final OrdOrderItemRelationshipCriteria that = (OrdOrderItemRelationshipCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(type, that.type) &&
            Objects.equals(primaryOrderItemId, that.primaryOrderItemId) &&
            Objects.equals(secondaryOrderItemId, that.secondaryOrderItemId) &&
            Objects.equals(ordOrderItemId, that.ordOrderItemId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, primaryOrderItemId, secondaryOrderItemId, ordOrderItemId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdOrderItemRelationshipCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (primaryOrderItemId != null ? "primaryOrderItemId=" + primaryOrderItemId + ", " : "") +
            (secondaryOrderItemId != null ? "secondaryOrderItemId=" + secondaryOrderItemId + ", " : "") +
            (ordOrderItemId != null ? "ordOrderItemId=" + ordOrderItemId + ", " : "") +
            "}";
    }
}

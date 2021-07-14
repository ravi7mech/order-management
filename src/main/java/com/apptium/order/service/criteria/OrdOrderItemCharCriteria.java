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
 * Criteria class for the {@link com.apptium.order.domain.OrdOrderItemChar} entity. This class is used
 * in {@link com.apptium.order.web.rest.OrdOrderItemCharResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ord-order-item-chars?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OrdOrderItemCharCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter value;

    private LongFilter ordOrderItemId;

    public OrdOrderItemCharCriteria() {}

    public OrdOrderItemCharCriteria(OrdOrderItemCharCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.value = other.value == null ? null : other.value.copy();
        this.ordOrderItemId = other.ordOrderItemId == null ? null : other.ordOrderItemId.copy();
    }

    @Override
    public OrdOrderItemCharCriteria copy() {
        return new OrdOrderItemCharCriteria(this);
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

    public StringFilter getValue() {
        return value;
    }

    public StringFilter value() {
        if (value == null) {
            value = new StringFilter();
        }
        return value;
    }

    public void setValue(StringFilter value) {
        this.value = value;
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
        final OrdOrderItemCharCriteria that = (OrdOrderItemCharCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(value, that.value) &&
            Objects.equals(ordOrderItemId, that.ordOrderItemId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, value, ordOrderItemId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdOrderItemCharCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (value != null ? "value=" + value + ", " : "") +
            (ordOrderItemId != null ? "ordOrderItemId=" + ordOrderItemId + ", " : "") +
            "}";
    }
}

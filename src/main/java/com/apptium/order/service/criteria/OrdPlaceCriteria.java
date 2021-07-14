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
 * Criteria class for the {@link com.apptium.order.domain.OrdPlace} entity. This class is used
 * in {@link com.apptium.order.web.rest.OrdPlaceResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ord-places?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OrdPlaceCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter href;

    private StringFilter name;

    private StringFilter role;

    private LongFilter ordProductId;

    public OrdPlaceCriteria() {}

    public OrdPlaceCriteria(OrdPlaceCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.href = other.href == null ? null : other.href.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.role = other.role == null ? null : other.role.copy();
        this.ordProductId = other.ordProductId == null ? null : other.ordProductId.copy();
    }

    @Override
    public OrdPlaceCriteria copy() {
        return new OrdPlaceCriteria(this);
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

    public StringFilter getRole() {
        return role;
    }

    public StringFilter role() {
        if (role == null) {
            role = new StringFilter();
        }
        return role;
    }

    public void setRole(StringFilter role) {
        this.role = role;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OrdPlaceCriteria that = (OrdPlaceCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(href, that.href) &&
            Objects.equals(name, that.name) &&
            Objects.equals(role, that.role) &&
            Objects.equals(ordProductId, that.ordProductId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, href, name, role, ordProductId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdPlaceCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (href != null ? "href=" + href + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (role != null ? "role=" + role + ", " : "") +
            (ordProductId != null ? "ordProductId=" + ordProductId + ", " : "") +
            "}";
    }
}

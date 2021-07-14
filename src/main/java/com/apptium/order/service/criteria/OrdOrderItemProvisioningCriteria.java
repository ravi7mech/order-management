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
 * Criteria class for the {@link com.apptium.order.domain.OrdOrderItemProvisioning} entity. This class is used
 * in {@link com.apptium.order.web.rest.OrdOrderItemProvisioningResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ord-order-item-provisionings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OrdOrderItemProvisioningCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter provisioningId;

    private StringFilter status;

    private LongFilter ordProvisiongCharId;

    private LongFilter ordOrderItemId;

    public OrdOrderItemProvisioningCriteria() {}

    public OrdOrderItemProvisioningCriteria(OrdOrderItemProvisioningCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.provisioningId = other.provisioningId == null ? null : other.provisioningId.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.ordProvisiongCharId = other.ordProvisiongCharId == null ? null : other.ordProvisiongCharId.copy();
        this.ordOrderItemId = other.ordOrderItemId == null ? null : other.ordOrderItemId.copy();
    }

    @Override
    public OrdOrderItemProvisioningCriteria copy() {
        return new OrdOrderItemProvisioningCriteria(this);
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

    public LongFilter getProvisioningId() {
        return provisioningId;
    }

    public LongFilter provisioningId() {
        if (provisioningId == null) {
            provisioningId = new LongFilter();
        }
        return provisioningId;
    }

    public void setProvisioningId(LongFilter provisioningId) {
        this.provisioningId = provisioningId;
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

    public LongFilter getOrdProvisiongCharId() {
        return ordProvisiongCharId;
    }

    public LongFilter ordProvisiongCharId() {
        if (ordProvisiongCharId == null) {
            ordProvisiongCharId = new LongFilter();
        }
        return ordProvisiongCharId;
    }

    public void setOrdProvisiongCharId(LongFilter ordProvisiongCharId) {
        this.ordProvisiongCharId = ordProvisiongCharId;
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
        final OrdOrderItemProvisioningCriteria that = (OrdOrderItemProvisioningCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(provisioningId, that.provisioningId) &&
            Objects.equals(status, that.status) &&
            Objects.equals(ordProvisiongCharId, that.ordProvisiongCharId) &&
            Objects.equals(ordOrderItemId, that.ordOrderItemId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, provisioningId, status, ordProvisiongCharId, ordOrderItemId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdOrderItemProvisioningCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (provisioningId != null ? "provisioningId=" + provisioningId + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (ordProvisiongCharId != null ? "ordProvisiongCharId=" + ordProvisiongCharId + ", " : "") +
            (ordOrderItemId != null ? "ordOrderItemId=" + ordOrderItemId + ", " : "") +
            "}";
    }
}

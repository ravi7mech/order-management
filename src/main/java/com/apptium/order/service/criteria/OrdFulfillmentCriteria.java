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
 * Criteria class for the {@link com.apptium.order.domain.OrdFulfillment} entity. This class is used
 * in {@link com.apptium.order.web.rest.OrdFulfillmentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ord-fulfillments?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OrdFulfillmentCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter workorderId;

    private LongFilter appointmentId;

    private StringFilter orderFulfillmentType;

    private StringFilter alternateShippingAddress;

    private StringFilter orderCallAheadNumber;

    private StringFilter orderJobComments;

    private StringFilter status;

    private LongFilter ordFulfillmentCharId;

    private LongFilter ordProductOrderId;

    public OrdFulfillmentCriteria() {}

    public OrdFulfillmentCriteria(OrdFulfillmentCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.workorderId = other.workorderId == null ? null : other.workorderId.copy();
        this.appointmentId = other.appointmentId == null ? null : other.appointmentId.copy();
        this.orderFulfillmentType = other.orderFulfillmentType == null ? null : other.orderFulfillmentType.copy();
        this.alternateShippingAddress = other.alternateShippingAddress == null ? null : other.alternateShippingAddress.copy();
        this.orderCallAheadNumber = other.orderCallAheadNumber == null ? null : other.orderCallAheadNumber.copy();
        this.orderJobComments = other.orderJobComments == null ? null : other.orderJobComments.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.ordFulfillmentCharId = other.ordFulfillmentCharId == null ? null : other.ordFulfillmentCharId.copy();
        this.ordProductOrderId = other.ordProductOrderId == null ? null : other.ordProductOrderId.copy();
    }

    @Override
    public OrdFulfillmentCriteria copy() {
        return new OrdFulfillmentCriteria(this);
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

    public LongFilter getWorkorderId() {
        return workorderId;
    }

    public LongFilter workorderId() {
        if (workorderId == null) {
            workorderId = new LongFilter();
        }
        return workorderId;
    }

    public void setWorkorderId(LongFilter workorderId) {
        this.workorderId = workorderId;
    }

    public LongFilter getAppointmentId() {
        return appointmentId;
    }

    public LongFilter appointmentId() {
        if (appointmentId == null) {
            appointmentId = new LongFilter();
        }
        return appointmentId;
    }

    public void setAppointmentId(LongFilter appointmentId) {
        this.appointmentId = appointmentId;
    }

    public StringFilter getOrderFulfillmentType() {
        return orderFulfillmentType;
    }

    public StringFilter orderFulfillmentType() {
        if (orderFulfillmentType == null) {
            orderFulfillmentType = new StringFilter();
        }
        return orderFulfillmentType;
    }

    public void setOrderFulfillmentType(StringFilter orderFulfillmentType) {
        this.orderFulfillmentType = orderFulfillmentType;
    }

    public StringFilter getAlternateShippingAddress() {
        return alternateShippingAddress;
    }

    public StringFilter alternateShippingAddress() {
        if (alternateShippingAddress == null) {
            alternateShippingAddress = new StringFilter();
        }
        return alternateShippingAddress;
    }

    public void setAlternateShippingAddress(StringFilter alternateShippingAddress) {
        this.alternateShippingAddress = alternateShippingAddress;
    }

    public StringFilter getOrderCallAheadNumber() {
        return orderCallAheadNumber;
    }

    public StringFilter orderCallAheadNumber() {
        if (orderCallAheadNumber == null) {
            orderCallAheadNumber = new StringFilter();
        }
        return orderCallAheadNumber;
    }

    public void setOrderCallAheadNumber(StringFilter orderCallAheadNumber) {
        this.orderCallAheadNumber = orderCallAheadNumber;
    }

    public StringFilter getOrderJobComments() {
        return orderJobComments;
    }

    public StringFilter orderJobComments() {
        if (orderJobComments == null) {
            orderJobComments = new StringFilter();
        }
        return orderJobComments;
    }

    public void setOrderJobComments(StringFilter orderJobComments) {
        this.orderJobComments = orderJobComments;
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

    public LongFilter getOrdFulfillmentCharId() {
        return ordFulfillmentCharId;
    }

    public LongFilter ordFulfillmentCharId() {
        if (ordFulfillmentCharId == null) {
            ordFulfillmentCharId = new LongFilter();
        }
        return ordFulfillmentCharId;
    }

    public void setOrdFulfillmentCharId(LongFilter ordFulfillmentCharId) {
        this.ordFulfillmentCharId = ordFulfillmentCharId;
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
        final OrdFulfillmentCriteria that = (OrdFulfillmentCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(workorderId, that.workorderId) &&
            Objects.equals(appointmentId, that.appointmentId) &&
            Objects.equals(orderFulfillmentType, that.orderFulfillmentType) &&
            Objects.equals(alternateShippingAddress, that.alternateShippingAddress) &&
            Objects.equals(orderCallAheadNumber, that.orderCallAheadNumber) &&
            Objects.equals(orderJobComments, that.orderJobComments) &&
            Objects.equals(status, that.status) &&
            Objects.equals(ordFulfillmentCharId, that.ordFulfillmentCharId) &&
            Objects.equals(ordProductOrderId, that.ordProductOrderId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            workorderId,
            appointmentId,
            orderFulfillmentType,
            alternateShippingAddress,
            orderCallAheadNumber,
            orderJobComments,
            status,
            ordFulfillmentCharId,
            ordProductOrderId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdFulfillmentCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (workorderId != null ? "workorderId=" + workorderId + ", " : "") +
            (appointmentId != null ? "appointmentId=" + appointmentId + ", " : "") +
            (orderFulfillmentType != null ? "orderFulfillmentType=" + orderFulfillmentType + ", " : "") +
            (alternateShippingAddress != null ? "alternateShippingAddress=" + alternateShippingAddress + ", " : "") +
            (orderCallAheadNumber != null ? "orderCallAheadNumber=" + orderCallAheadNumber + ", " : "") +
            (orderJobComments != null ? "orderJobComments=" + orderJobComments + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (ordFulfillmentCharId != null ? "ordFulfillmentCharId=" + ordFulfillmentCharId + ", " : "") +
            (ordProductOrderId != null ? "ordProductOrderId=" + ordProductOrderId + ", " : "") +
            "}";
    }
}

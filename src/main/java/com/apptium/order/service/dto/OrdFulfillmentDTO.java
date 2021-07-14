package com.apptium.order.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.apptium.order.domain.OrdFulfillment} entity.
 */
public class OrdFulfillmentDTO implements Serializable {

    private Long id;

    private Long workorderId;

    private Long appointmentId;

    private String orderFulfillmentType;

    private String alternateShippingAddress;

    private String orderCallAheadNumber;

    private String orderJobComments;

    private String status;

    private OrdProductOrderDTO ordProductOrder;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWorkorderId() {
        return workorderId;
    }

    public void setWorkorderId(Long workorderId) {
        this.workorderId = workorderId;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getOrderFulfillmentType() {
        return orderFulfillmentType;
    }

    public void setOrderFulfillmentType(String orderFulfillmentType) {
        this.orderFulfillmentType = orderFulfillmentType;
    }

    public String getAlternateShippingAddress() {
        return alternateShippingAddress;
    }

    public void setAlternateShippingAddress(String alternateShippingAddress) {
        this.alternateShippingAddress = alternateShippingAddress;
    }

    public String getOrderCallAheadNumber() {
        return orderCallAheadNumber;
    }

    public void setOrderCallAheadNumber(String orderCallAheadNumber) {
        this.orderCallAheadNumber = orderCallAheadNumber;
    }

    public String getOrderJobComments() {
        return orderJobComments;
    }

    public void setOrderJobComments(String orderJobComments) {
        this.orderJobComments = orderJobComments;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public OrdProductOrderDTO getOrdProductOrder() {
        return ordProductOrder;
    }

    public void setOrdProductOrder(OrdProductOrderDTO ordProductOrder) {
        this.ordProductOrder = ordProductOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrdFulfillmentDTO)) {
            return false;
        }

        OrdFulfillmentDTO ordFulfillmentDTO = (OrdFulfillmentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ordFulfillmentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdFulfillmentDTO{" +
            "id=" + getId() +
            ", workorderId=" + getWorkorderId() +
            ", appointmentId=" + getAppointmentId() +
            ", orderFulfillmentType='" + getOrderFulfillmentType() + "'" +
            ", alternateShippingAddress='" + getAlternateShippingAddress() + "'" +
            ", orderCallAheadNumber='" + getOrderCallAheadNumber() + "'" +
            ", orderJobComments='" + getOrderJobComments() + "'" +
            ", status='" + getStatus() + "'" +
            ", ordProductOrder=" + getOrdProductOrder() +
            "}";
    }
}

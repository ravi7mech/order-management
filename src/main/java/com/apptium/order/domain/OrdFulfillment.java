package com.apptium.order.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A OrdFulfillment.
 */
@Entity
@Table(name = "ord_fulfillment")
public class OrdFulfillment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "workorder_id")
    private Long workorderId;

    @Column(name = "appointment_id")
    private Long appointmentId;

    @Column(name = "order_fulfillment_type")
    private String orderFulfillmentType;

    @Column(name = "alternate_shipping_address")
    private String alternateShippingAddress;

    @Column(name = "order_call_ahead_number")
    private String orderCallAheadNumber;

    @Column(name = "order_job_comments")
    private String orderJobComments;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "ordFulfillment")
    @JsonIgnoreProperties(value = { "ordFulfillment" }, allowSetters = true)
    private Set<OrdFulfillmentChar> ordFulfillmentChars = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "ordContactDetails",
            "ordNote",
            "ordChannel",
            "ordOrderPrice",
            "ordBillingAccountRef",
            "ordCharacteristics",
            "ordOrderItems",
            "ordPaymentRefs",
            "ordReasons",
            "ordContracts",
            "ordFulfillments",
            "ordAcquisitions",
        },
        allowSetters = true
    )
    private OrdProductOrder ordProductOrder;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrdFulfillment id(Long id) {
        this.id = id;
        return this;
    }

    public Long getWorkorderId() {
        return this.workorderId;
    }

    public OrdFulfillment workorderId(Long workorderId) {
        this.workorderId = workorderId;
        return this;
    }

    public void setWorkorderId(Long workorderId) {
        this.workorderId = workorderId;
    }

    public Long getAppointmentId() {
        return this.appointmentId;
    }

    public OrdFulfillment appointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
        return this;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getOrderFulfillmentType() {
        return this.orderFulfillmentType;
    }

    public OrdFulfillment orderFulfillmentType(String orderFulfillmentType) {
        this.orderFulfillmentType = orderFulfillmentType;
        return this;
    }

    public void setOrderFulfillmentType(String orderFulfillmentType) {
        this.orderFulfillmentType = orderFulfillmentType;
    }

    public String getAlternateShippingAddress() {
        return this.alternateShippingAddress;
    }

    public OrdFulfillment alternateShippingAddress(String alternateShippingAddress) {
        this.alternateShippingAddress = alternateShippingAddress;
        return this;
    }

    public void setAlternateShippingAddress(String alternateShippingAddress) {
        this.alternateShippingAddress = alternateShippingAddress;
    }

    public String getOrderCallAheadNumber() {
        return this.orderCallAheadNumber;
    }

    public OrdFulfillment orderCallAheadNumber(String orderCallAheadNumber) {
        this.orderCallAheadNumber = orderCallAheadNumber;
        return this;
    }

    public void setOrderCallAheadNumber(String orderCallAheadNumber) {
        this.orderCallAheadNumber = orderCallAheadNumber;
    }

    public String getOrderJobComments() {
        return this.orderJobComments;
    }

    public OrdFulfillment orderJobComments(String orderJobComments) {
        this.orderJobComments = orderJobComments;
        return this;
    }

    public void setOrderJobComments(String orderJobComments) {
        this.orderJobComments = orderJobComments;
    }

    public String getStatus() {
        return this.status;
    }

    public OrdFulfillment status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<OrdFulfillmentChar> getOrdFulfillmentChars() {
        return this.ordFulfillmentChars;
    }

    public OrdFulfillment ordFulfillmentChars(Set<OrdFulfillmentChar> ordFulfillmentChars) {
        this.setOrdFulfillmentChars(ordFulfillmentChars);
        return this;
    }

    public OrdFulfillment addOrdFulfillmentChar(OrdFulfillmentChar ordFulfillmentChar) {
        this.ordFulfillmentChars.add(ordFulfillmentChar);
        ordFulfillmentChar.setOrdFulfillment(this);
        return this;
    }

    public OrdFulfillment removeOrdFulfillmentChar(OrdFulfillmentChar ordFulfillmentChar) {
        this.ordFulfillmentChars.remove(ordFulfillmentChar);
        ordFulfillmentChar.setOrdFulfillment(null);
        return this;
    }

    public void setOrdFulfillmentChars(Set<OrdFulfillmentChar> ordFulfillmentChars) {
        if (this.ordFulfillmentChars != null) {
            this.ordFulfillmentChars.forEach(i -> i.setOrdFulfillment(null));
        }
        if (ordFulfillmentChars != null) {
            ordFulfillmentChars.forEach(i -> i.setOrdFulfillment(this));
        }
        this.ordFulfillmentChars = ordFulfillmentChars;
    }

    public OrdProductOrder getOrdProductOrder() {
        return this.ordProductOrder;
    }

    public OrdFulfillment ordProductOrder(OrdProductOrder ordProductOrder) {
        this.setOrdProductOrder(ordProductOrder);
        return this;
    }

    public void setOrdProductOrder(OrdProductOrder ordProductOrder) {
        this.ordProductOrder = ordProductOrder;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrdFulfillment)) {
            return false;
        }
        return id != null && id.equals(((OrdFulfillment) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdFulfillment{" +
            "id=" + getId() +
            ", workorderId=" + getWorkorderId() +
            ", appointmentId=" + getAppointmentId() +
            ", orderFulfillmentType='" + getOrderFulfillmentType() + "'" +
            ", alternateShippingAddress='" + getAlternateShippingAddress() + "'" +
            ", orderCallAheadNumber='" + getOrderCallAheadNumber() + "'" +
            ", orderJobComments='" + getOrderJobComments() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}

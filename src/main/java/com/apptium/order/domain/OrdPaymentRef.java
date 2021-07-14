package com.apptium.order.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 * A OrdPaymentRef.
 */
@Entity
@Table(name = "ord_payment_ref")
public class OrdPaymentRef implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "payment_id")
    private Long paymentId;

    @Column(name = "href")
    private String href;

    @Column(name = "name")
    private String name;

    @Column(name = "payment_amount", precision = 21, scale = 2)
    private BigDecimal paymentAmount;

    @Column(name = "action")
    private String action;

    @Column(name = "status")
    private String status;

    @Column(name = "enrol_recurring")
    private String enrolRecurring;

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

    public OrdPaymentRef id(Long id) {
        this.id = id;
        return this;
    }

    public Long getPaymentId() {
        return this.paymentId;
    }

    public OrdPaymentRef paymentId(Long paymentId) {
        this.paymentId = paymentId;
        return this;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public String getHref() {
        return this.href;
    }

    public OrdPaymentRef href(String href) {
        this.href = href;
        return this;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getName() {
        return this.name;
    }

    public OrdPaymentRef name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPaymentAmount() {
        return this.paymentAmount;
    }

    public OrdPaymentRef paymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
        return this;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getAction() {
        return this.action;
    }

    public OrdPaymentRef action(String action) {
        this.action = action;
        return this;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getStatus() {
        return this.status;
    }

    public OrdPaymentRef status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEnrolRecurring() {
        return this.enrolRecurring;
    }

    public OrdPaymentRef enrolRecurring(String enrolRecurring) {
        this.enrolRecurring = enrolRecurring;
        return this;
    }

    public void setEnrolRecurring(String enrolRecurring) {
        this.enrolRecurring = enrolRecurring;
    }

    public OrdProductOrder getOrdProductOrder() {
        return this.ordProductOrder;
    }

    public OrdPaymentRef ordProductOrder(OrdProductOrder ordProductOrder) {
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
        if (!(o instanceof OrdPaymentRef)) {
            return false;
        }
        return id != null && id.equals(((OrdPaymentRef) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdPaymentRef{" +
            "id=" + getId() +
            ", paymentId=" + getPaymentId() +
            ", href='" + getHref() + "'" +
            ", name='" + getName() + "'" +
            ", paymentAmount=" + getPaymentAmount() +
            ", action='" + getAction() + "'" +
            ", status='" + getStatus() + "'" +
            ", enrolRecurring='" + getEnrolRecurring() + "'" +
            "}";
    }
}

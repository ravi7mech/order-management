package com.apptium.order.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.apptium.order.domain.OrdPaymentRef} entity.
 */
public class OrdPaymentRefDTO implements Serializable {

    private Long id;

    private Long paymentId;

    private String href;

    private String name;

    private BigDecimal paymentAmount;

    private String action;

    private String status;

    private String enrolRecurring;

    private OrdProductOrderDTO ordProductOrder;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEnrolRecurring() {
        return enrolRecurring;
    }

    public void setEnrolRecurring(String enrolRecurring) {
        this.enrolRecurring = enrolRecurring;
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
        if (!(o instanceof OrdPaymentRefDTO)) {
            return false;
        }

        OrdPaymentRefDTO ordPaymentRefDTO = (OrdPaymentRefDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ordPaymentRefDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdPaymentRefDTO{" +
            "id=" + getId() +
            ", paymentId=" + getPaymentId() +
            ", href='" + getHref() + "'" +
            ", name='" + getName() + "'" +
            ", paymentAmount=" + getPaymentAmount() +
            ", action='" + getAction() + "'" +
            ", status='" + getStatus() + "'" +
            ", enrolRecurring='" + getEnrolRecurring() + "'" +
            ", ordProductOrder=" + getOrdProductOrder() +
            "}";
    }
}

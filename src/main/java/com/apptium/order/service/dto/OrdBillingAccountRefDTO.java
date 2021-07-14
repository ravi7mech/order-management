package com.apptium.order.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.apptium.order.domain.OrdBillingAccountRef} entity.
 */
public class OrdBillingAccountRefDTO implements Serializable {

    private Long id;

    private String name;

    private String href;

    private Long cartPriceId;

    private Long billingAccountId;

    private String billingSystem;

    private String deliveryMethod;

    private String billingAddress;

    private String status;

    private Long quoteId;

    private Long salesOrderId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Long getCartPriceId() {
        return cartPriceId;
    }

    public void setCartPriceId(Long cartPriceId) {
        this.cartPriceId = cartPriceId;
    }

    public Long getBillingAccountId() {
        return billingAccountId;
    }

    public void setBillingAccountId(Long billingAccountId) {
        this.billingAccountId = billingAccountId;
    }

    public String getBillingSystem() {
        return billingSystem;
    }

    public void setBillingSystem(String billingSystem) {
        this.billingSystem = billingSystem;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(Long quoteId) {
        this.quoteId = quoteId;
    }

    public Long getSalesOrderId() {
        return salesOrderId;
    }

    public void setSalesOrderId(Long salesOrderId) {
        this.salesOrderId = salesOrderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrdBillingAccountRefDTO)) {
            return false;
        }

        OrdBillingAccountRefDTO ordBillingAccountRefDTO = (OrdBillingAccountRefDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ordBillingAccountRefDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdBillingAccountRefDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", href='" + getHref() + "'" +
            ", cartPriceId=" + getCartPriceId() +
            ", billingAccountId=" + getBillingAccountId() +
            ", billingSystem='" + getBillingSystem() + "'" +
            ", deliveryMethod='" + getDeliveryMethod() + "'" +
            ", billingAddress='" + getBillingAddress() + "'" +
            ", status='" + getStatus() + "'" +
            ", quoteId=" + getQuoteId() +
            ", salesOrderId=" + getSalesOrderId() +
            "}";
    }
}

package com.apptium.order.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A OrdBillingAccountRef.
 */
@Entity
@Table(name = "ord_billing_account_ref")
public class OrdBillingAccountRef implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "href")
    private String href;

    @Column(name = "cart_price_id")
    private Long cartPriceId;

    @Column(name = "billing_account_id")
    private Long billingAccountId;

    @Column(name = "billing_system")
    private String billingSystem;

    @Column(name = "delivery_method")
    private String deliveryMethod;

    @Column(name = "billing_address")
    private String billingAddress;

    @Column(name = "status")
    private String status;

    @Column(name = "quote_id")
    private Long quoteId;

    @Column(name = "sales_order_id")
    private Long salesOrderId;

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
    @OneToOne(mappedBy = "ordBillingAccountRef")
    private OrdProductOrder ordProductOrder;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrdBillingAccountRef id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public OrdBillingAccountRef name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHref() {
        return this.href;
    }

    public OrdBillingAccountRef href(String href) {
        this.href = href;
        return this;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Long getCartPriceId() {
        return this.cartPriceId;
    }

    public OrdBillingAccountRef cartPriceId(Long cartPriceId) {
        this.cartPriceId = cartPriceId;
        return this;
    }

    public void setCartPriceId(Long cartPriceId) {
        this.cartPriceId = cartPriceId;
    }

    public Long getBillingAccountId() {
        return this.billingAccountId;
    }

    public OrdBillingAccountRef billingAccountId(Long billingAccountId) {
        this.billingAccountId = billingAccountId;
        return this;
    }

    public void setBillingAccountId(Long billingAccountId) {
        this.billingAccountId = billingAccountId;
    }

    public String getBillingSystem() {
        return this.billingSystem;
    }

    public OrdBillingAccountRef billingSystem(String billingSystem) {
        this.billingSystem = billingSystem;
        return this;
    }

    public void setBillingSystem(String billingSystem) {
        this.billingSystem = billingSystem;
    }

    public String getDeliveryMethod() {
        return this.deliveryMethod;
    }

    public OrdBillingAccountRef deliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
        return this;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public String getBillingAddress() {
        return this.billingAddress;
    }

    public OrdBillingAccountRef billingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
        return this;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getStatus() {
        return this.status;
    }

    public OrdBillingAccountRef status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getQuoteId() {
        return this.quoteId;
    }

    public OrdBillingAccountRef quoteId(Long quoteId) {
        this.quoteId = quoteId;
        return this;
    }

    public void setQuoteId(Long quoteId) {
        this.quoteId = quoteId;
    }

    public Long getSalesOrderId() {
        return this.salesOrderId;
    }

    public OrdBillingAccountRef salesOrderId(Long salesOrderId) {
        this.salesOrderId = salesOrderId;
        return this;
    }

    public void setSalesOrderId(Long salesOrderId) {
        this.salesOrderId = salesOrderId;
    }

    public OrdProductOrder getOrdProductOrder() {
        return this.ordProductOrder;
    }

    public OrdBillingAccountRef ordProductOrder(OrdProductOrder ordProductOrder) {
        this.setOrdProductOrder(ordProductOrder);
        return this;
    }

    public void setOrdProductOrder(OrdProductOrder ordProductOrder) {
        if (this.ordProductOrder != null) {
            this.ordProductOrder.setOrdBillingAccountRef(null);
        }
        if (ordProductOrder != null) {
            ordProductOrder.setOrdBillingAccountRef(this);
        }
        this.ordProductOrder = ordProductOrder;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrdBillingAccountRef)) {
            return false;
        }
        return id != null && id.equals(((OrdBillingAccountRef) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdBillingAccountRef{" +
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

package com.apptium.order.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 * A OrdPriceAmount.
 */
@Entity
@Table(name = "ord_price_amount")
public class OrdPriceAmount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "currency_code")
    private String currencyCode;

    @Column(name = "tax_included_amount", precision = 21, scale = 2)
    private BigDecimal taxIncludedAmount;

    @Column(name = "duty_free_amount", precision = 21, scale = 2)
    private BigDecimal dutyFreeAmount;

    @Column(name = "tax_rate", precision = 21, scale = 2)
    private BigDecimal taxRate;

    @Column(name = "percentage", precision = 21, scale = 2)
    private BigDecimal percentage;

    @Column(name = "total_recurring_price", precision = 21, scale = 2)
    private BigDecimal totalRecurringPrice;

    @Column(name = "total_one_time_price", precision = 21, scale = 2)
    private BigDecimal totalOneTimePrice;

    @JsonIgnoreProperties(value = { "ordPriceAmount", "ordPriceAlterations", "ordProductOrder", "ordOrderItem" }, allowSetters = true)
    @OneToOne(mappedBy = "ordPriceAmount")
    private OrdOrderPrice ordOrderPrice;

    @JsonIgnoreProperties(value = { "ordPriceAmount", "ordOrderPrice" }, allowSetters = true)
    @OneToOne(mappedBy = "ordPriceAmount")
    private OrdPriceAlteration ordPriceAlteration;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrdPriceAmount id(Long id) {
        this.id = id;
        return this;
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public OrdPriceAmount currencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
        return this;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public BigDecimal getTaxIncludedAmount() {
        return this.taxIncludedAmount;
    }

    public OrdPriceAmount taxIncludedAmount(BigDecimal taxIncludedAmount) {
        this.taxIncludedAmount = taxIncludedAmount;
        return this;
    }

    public void setTaxIncludedAmount(BigDecimal taxIncludedAmount) {
        this.taxIncludedAmount = taxIncludedAmount;
    }

    public BigDecimal getDutyFreeAmount() {
        return this.dutyFreeAmount;
    }

    public OrdPriceAmount dutyFreeAmount(BigDecimal dutyFreeAmount) {
        this.dutyFreeAmount = dutyFreeAmount;
        return this;
    }

    public void setDutyFreeAmount(BigDecimal dutyFreeAmount) {
        this.dutyFreeAmount = dutyFreeAmount;
    }

    public BigDecimal getTaxRate() {
        return this.taxRate;
    }

    public OrdPriceAmount taxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
        return this;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public BigDecimal getPercentage() {
        return this.percentage;
    }

    public OrdPriceAmount percentage(BigDecimal percentage) {
        this.percentage = percentage;
        return this;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    public BigDecimal getTotalRecurringPrice() {
        return this.totalRecurringPrice;
    }

    public OrdPriceAmount totalRecurringPrice(BigDecimal totalRecurringPrice) {
        this.totalRecurringPrice = totalRecurringPrice;
        return this;
    }

    public void setTotalRecurringPrice(BigDecimal totalRecurringPrice) {
        this.totalRecurringPrice = totalRecurringPrice;
    }

    public BigDecimal getTotalOneTimePrice() {
        return this.totalOneTimePrice;
    }

    public OrdPriceAmount totalOneTimePrice(BigDecimal totalOneTimePrice) {
        this.totalOneTimePrice = totalOneTimePrice;
        return this;
    }

    public void setTotalOneTimePrice(BigDecimal totalOneTimePrice) {
        this.totalOneTimePrice = totalOneTimePrice;
    }

    public OrdOrderPrice getOrdOrderPrice() {
        return this.ordOrderPrice;
    }

    public OrdPriceAmount ordOrderPrice(OrdOrderPrice ordOrderPrice) {
        this.setOrdOrderPrice(ordOrderPrice);
        return this;
    }

    public void setOrdOrderPrice(OrdOrderPrice ordOrderPrice) {
        if (this.ordOrderPrice != null) {
            this.ordOrderPrice.setOrdPriceAmount(null);
        }
        if (ordOrderPrice != null) {
            ordOrderPrice.setOrdPriceAmount(this);
        }
        this.ordOrderPrice = ordOrderPrice;
    }

    public OrdPriceAlteration getOrdPriceAlteration() {
        return this.ordPriceAlteration;
    }

    public OrdPriceAmount ordPriceAlteration(OrdPriceAlteration ordPriceAlteration) {
        this.setOrdPriceAlteration(ordPriceAlteration);
        return this;
    }

    public void setOrdPriceAlteration(OrdPriceAlteration ordPriceAlteration) {
        if (this.ordPriceAlteration != null) {
            this.ordPriceAlteration.setOrdPriceAmount(null);
        }
        if (ordPriceAlteration != null) {
            ordPriceAlteration.setOrdPriceAmount(this);
        }
        this.ordPriceAlteration = ordPriceAlteration;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrdPriceAmount)) {
            return false;
        }
        return id != null && id.equals(((OrdPriceAmount) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdPriceAmount{" +
            "id=" + getId() +
            ", currencyCode='" + getCurrencyCode() + "'" +
            ", taxIncludedAmount=" + getTaxIncludedAmount() +
            ", dutyFreeAmount=" + getDutyFreeAmount() +
            ", taxRate=" + getTaxRate() +
            ", percentage=" + getPercentage() +
            ", totalRecurringPrice=" + getTotalRecurringPrice() +
            ", totalOneTimePrice=" + getTotalOneTimePrice() +
            "}";
    }
}

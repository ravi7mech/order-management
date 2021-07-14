package com.apptium.order.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.apptium.order.domain.OrdPriceAmount} entity.
 */
public class OrdPriceAmountDTO implements Serializable {

    private Long id;

    private String currencyCode;

    private BigDecimal taxIncludedAmount;

    private BigDecimal dutyFreeAmount;

    private BigDecimal taxRate;

    private BigDecimal percentage;

    private BigDecimal totalRecurringPrice;

    private BigDecimal totalOneTimePrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public BigDecimal getTaxIncludedAmount() {
        return taxIncludedAmount;
    }

    public void setTaxIncludedAmount(BigDecimal taxIncludedAmount) {
        this.taxIncludedAmount = taxIncludedAmount;
    }

    public BigDecimal getDutyFreeAmount() {
        return dutyFreeAmount;
    }

    public void setDutyFreeAmount(BigDecimal dutyFreeAmount) {
        this.dutyFreeAmount = dutyFreeAmount;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    public BigDecimal getTotalRecurringPrice() {
        return totalRecurringPrice;
    }

    public void setTotalRecurringPrice(BigDecimal totalRecurringPrice) {
        this.totalRecurringPrice = totalRecurringPrice;
    }

    public BigDecimal getTotalOneTimePrice() {
        return totalOneTimePrice;
    }

    public void setTotalOneTimePrice(BigDecimal totalOneTimePrice) {
        this.totalOneTimePrice = totalOneTimePrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrdPriceAmountDTO)) {
            return false;
        }

        OrdPriceAmountDTO ordPriceAmountDTO = (OrdPriceAmountDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ordPriceAmountDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdPriceAmountDTO{" +
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

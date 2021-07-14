package com.apptium.order.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BigDecimalFilter;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.apptium.order.domain.OrdPriceAmount} entity. This class is used
 * in {@link com.apptium.order.web.rest.OrdPriceAmountResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ord-price-amounts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OrdPriceAmountCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter currencyCode;

    private BigDecimalFilter taxIncludedAmount;

    private BigDecimalFilter dutyFreeAmount;

    private BigDecimalFilter taxRate;

    private BigDecimalFilter percentage;

    private BigDecimalFilter totalRecurringPrice;

    private BigDecimalFilter totalOneTimePrice;

    private LongFilter ordOrderPriceId;

    private LongFilter ordPriceAlterationId;

    public OrdPriceAmountCriteria() {}

    public OrdPriceAmountCriteria(OrdPriceAmountCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.currencyCode = other.currencyCode == null ? null : other.currencyCode.copy();
        this.taxIncludedAmount = other.taxIncludedAmount == null ? null : other.taxIncludedAmount.copy();
        this.dutyFreeAmount = other.dutyFreeAmount == null ? null : other.dutyFreeAmount.copy();
        this.taxRate = other.taxRate == null ? null : other.taxRate.copy();
        this.percentage = other.percentage == null ? null : other.percentage.copy();
        this.totalRecurringPrice = other.totalRecurringPrice == null ? null : other.totalRecurringPrice.copy();
        this.totalOneTimePrice = other.totalOneTimePrice == null ? null : other.totalOneTimePrice.copy();
        this.ordOrderPriceId = other.ordOrderPriceId == null ? null : other.ordOrderPriceId.copy();
        this.ordPriceAlterationId = other.ordPriceAlterationId == null ? null : other.ordPriceAlterationId.copy();
    }

    @Override
    public OrdPriceAmountCriteria copy() {
        return new OrdPriceAmountCriteria(this);
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

    public StringFilter getCurrencyCode() {
        return currencyCode;
    }

    public StringFilter currencyCode() {
        if (currencyCode == null) {
            currencyCode = new StringFilter();
        }
        return currencyCode;
    }

    public void setCurrencyCode(StringFilter currencyCode) {
        this.currencyCode = currencyCode;
    }

    public BigDecimalFilter getTaxIncludedAmount() {
        return taxIncludedAmount;
    }

    public BigDecimalFilter taxIncludedAmount() {
        if (taxIncludedAmount == null) {
            taxIncludedAmount = new BigDecimalFilter();
        }
        return taxIncludedAmount;
    }

    public void setTaxIncludedAmount(BigDecimalFilter taxIncludedAmount) {
        this.taxIncludedAmount = taxIncludedAmount;
    }

    public BigDecimalFilter getDutyFreeAmount() {
        return dutyFreeAmount;
    }

    public BigDecimalFilter dutyFreeAmount() {
        if (dutyFreeAmount == null) {
            dutyFreeAmount = new BigDecimalFilter();
        }
        return dutyFreeAmount;
    }

    public void setDutyFreeAmount(BigDecimalFilter dutyFreeAmount) {
        this.dutyFreeAmount = dutyFreeAmount;
    }

    public BigDecimalFilter getTaxRate() {
        return taxRate;
    }

    public BigDecimalFilter taxRate() {
        if (taxRate == null) {
            taxRate = new BigDecimalFilter();
        }
        return taxRate;
    }

    public void setTaxRate(BigDecimalFilter taxRate) {
        this.taxRate = taxRate;
    }

    public BigDecimalFilter getPercentage() {
        return percentage;
    }

    public BigDecimalFilter percentage() {
        if (percentage == null) {
            percentage = new BigDecimalFilter();
        }
        return percentage;
    }

    public void setPercentage(BigDecimalFilter percentage) {
        this.percentage = percentage;
    }

    public BigDecimalFilter getTotalRecurringPrice() {
        return totalRecurringPrice;
    }

    public BigDecimalFilter totalRecurringPrice() {
        if (totalRecurringPrice == null) {
            totalRecurringPrice = new BigDecimalFilter();
        }
        return totalRecurringPrice;
    }

    public void setTotalRecurringPrice(BigDecimalFilter totalRecurringPrice) {
        this.totalRecurringPrice = totalRecurringPrice;
    }

    public BigDecimalFilter getTotalOneTimePrice() {
        return totalOneTimePrice;
    }

    public BigDecimalFilter totalOneTimePrice() {
        if (totalOneTimePrice == null) {
            totalOneTimePrice = new BigDecimalFilter();
        }
        return totalOneTimePrice;
    }

    public void setTotalOneTimePrice(BigDecimalFilter totalOneTimePrice) {
        this.totalOneTimePrice = totalOneTimePrice;
    }

    public LongFilter getOrdOrderPriceId() {
        return ordOrderPriceId;
    }

    public LongFilter ordOrderPriceId() {
        if (ordOrderPriceId == null) {
            ordOrderPriceId = new LongFilter();
        }
        return ordOrderPriceId;
    }

    public void setOrdOrderPriceId(LongFilter ordOrderPriceId) {
        this.ordOrderPriceId = ordOrderPriceId;
    }

    public LongFilter getOrdPriceAlterationId() {
        return ordPriceAlterationId;
    }

    public LongFilter ordPriceAlterationId() {
        if (ordPriceAlterationId == null) {
            ordPriceAlterationId = new LongFilter();
        }
        return ordPriceAlterationId;
    }

    public void setOrdPriceAlterationId(LongFilter ordPriceAlterationId) {
        this.ordPriceAlterationId = ordPriceAlterationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OrdPriceAmountCriteria that = (OrdPriceAmountCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(currencyCode, that.currencyCode) &&
            Objects.equals(taxIncludedAmount, that.taxIncludedAmount) &&
            Objects.equals(dutyFreeAmount, that.dutyFreeAmount) &&
            Objects.equals(taxRate, that.taxRate) &&
            Objects.equals(percentage, that.percentage) &&
            Objects.equals(totalRecurringPrice, that.totalRecurringPrice) &&
            Objects.equals(totalOneTimePrice, that.totalOneTimePrice) &&
            Objects.equals(ordOrderPriceId, that.ordOrderPriceId) &&
            Objects.equals(ordPriceAlterationId, that.ordPriceAlterationId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            currencyCode,
            taxIncludedAmount,
            dutyFreeAmount,
            taxRate,
            percentage,
            totalRecurringPrice,
            totalOneTimePrice,
            ordOrderPriceId,
            ordPriceAlterationId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdPriceAmountCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (currencyCode != null ? "currencyCode=" + currencyCode + ", " : "") +
            (taxIncludedAmount != null ? "taxIncludedAmount=" + taxIncludedAmount + ", " : "") +
            (dutyFreeAmount != null ? "dutyFreeAmount=" + dutyFreeAmount + ", " : "") +
            (taxRate != null ? "taxRate=" + taxRate + ", " : "") +
            (percentage != null ? "percentage=" + percentage + ", " : "") +
            (totalRecurringPrice != null ? "totalRecurringPrice=" + totalRecurringPrice + ", " : "") +
            (totalOneTimePrice != null ? "totalOneTimePrice=" + totalOneTimePrice + ", " : "") +
            (ordOrderPriceId != null ? "ordOrderPriceId=" + ordOrderPriceId + ", " : "") +
            (ordPriceAlterationId != null ? "ordPriceAlterationId=" + ordPriceAlterationId + ", " : "") +
            "}";
    }
}

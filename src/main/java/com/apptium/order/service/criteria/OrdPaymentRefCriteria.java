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
 * Criteria class for the {@link com.apptium.order.domain.OrdPaymentRef} entity. This class is used
 * in {@link com.apptium.order.web.rest.OrdPaymentRefResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ord-payment-refs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OrdPaymentRefCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter paymentId;

    private StringFilter href;

    private StringFilter name;

    private BigDecimalFilter paymentAmount;

    private StringFilter action;

    private StringFilter status;

    private StringFilter enrolRecurring;

    private LongFilter ordProductOrderId;

    public OrdPaymentRefCriteria() {}

    public OrdPaymentRefCriteria(OrdPaymentRefCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.paymentId = other.paymentId == null ? null : other.paymentId.copy();
        this.href = other.href == null ? null : other.href.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.paymentAmount = other.paymentAmount == null ? null : other.paymentAmount.copy();
        this.action = other.action == null ? null : other.action.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.enrolRecurring = other.enrolRecurring == null ? null : other.enrolRecurring.copy();
        this.ordProductOrderId = other.ordProductOrderId == null ? null : other.ordProductOrderId.copy();
    }

    @Override
    public OrdPaymentRefCriteria copy() {
        return new OrdPaymentRefCriteria(this);
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

    public LongFilter getPaymentId() {
        return paymentId;
    }

    public LongFilter paymentId() {
        if (paymentId == null) {
            paymentId = new LongFilter();
        }
        return paymentId;
    }

    public void setPaymentId(LongFilter paymentId) {
        this.paymentId = paymentId;
    }

    public StringFilter getHref() {
        return href;
    }

    public StringFilter href() {
        if (href == null) {
            href = new StringFilter();
        }
        return href;
    }

    public void setHref(StringFilter href) {
        this.href = href;
    }

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public BigDecimalFilter getPaymentAmount() {
        return paymentAmount;
    }

    public BigDecimalFilter paymentAmount() {
        if (paymentAmount == null) {
            paymentAmount = new BigDecimalFilter();
        }
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimalFilter paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public StringFilter getAction() {
        return action;
    }

    public StringFilter action() {
        if (action == null) {
            action = new StringFilter();
        }
        return action;
    }

    public void setAction(StringFilter action) {
        this.action = action;
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

    public StringFilter getEnrolRecurring() {
        return enrolRecurring;
    }

    public StringFilter enrolRecurring() {
        if (enrolRecurring == null) {
            enrolRecurring = new StringFilter();
        }
        return enrolRecurring;
    }

    public void setEnrolRecurring(StringFilter enrolRecurring) {
        this.enrolRecurring = enrolRecurring;
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
        final OrdPaymentRefCriteria that = (OrdPaymentRefCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(paymentId, that.paymentId) &&
            Objects.equals(href, that.href) &&
            Objects.equals(name, that.name) &&
            Objects.equals(paymentAmount, that.paymentAmount) &&
            Objects.equals(action, that.action) &&
            Objects.equals(status, that.status) &&
            Objects.equals(enrolRecurring, that.enrolRecurring) &&
            Objects.equals(ordProductOrderId, that.ordProductOrderId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, paymentId, href, name, paymentAmount, action, status, enrolRecurring, ordProductOrderId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdPaymentRefCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (paymentId != null ? "paymentId=" + paymentId + ", " : "") +
            (href != null ? "href=" + href + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (paymentAmount != null ? "paymentAmount=" + paymentAmount + ", " : "") +
            (action != null ? "action=" + action + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (enrolRecurring != null ? "enrolRecurring=" + enrolRecurring + ", " : "") +
            (ordProductOrderId != null ? "ordProductOrderId=" + ordProductOrderId + ", " : "") +
            "}";
    }
}

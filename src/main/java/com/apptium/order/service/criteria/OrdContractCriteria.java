package com.apptium.order.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.apptium.order.domain.OrdContract} entity. This class is used
 * in {@link com.apptium.order.web.rest.OrdContractResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ord-contracts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OrdContractCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter contractId;

    private LongFilter languageId;

    private StringFilter termTypeCode;

    private StringFilter action;

    private StringFilter status;

    private LongFilter ordContractCharacteristicsId;

    private LongFilter ordProductOrderId;

    public OrdContractCriteria() {}

    public OrdContractCriteria(OrdContractCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.contractId = other.contractId == null ? null : other.contractId.copy();
        this.languageId = other.languageId == null ? null : other.languageId.copy();
        this.termTypeCode = other.termTypeCode == null ? null : other.termTypeCode.copy();
        this.action = other.action == null ? null : other.action.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.ordContractCharacteristicsId = other.ordContractCharacteristicsId == null ? null : other.ordContractCharacteristicsId.copy();
        this.ordProductOrderId = other.ordProductOrderId == null ? null : other.ordProductOrderId.copy();
    }

    @Override
    public OrdContractCriteria copy() {
        return new OrdContractCriteria(this);
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

    public LongFilter getContractId() {
        return contractId;
    }

    public LongFilter contractId() {
        if (contractId == null) {
            contractId = new LongFilter();
        }
        return contractId;
    }

    public void setContractId(LongFilter contractId) {
        this.contractId = contractId;
    }

    public LongFilter getLanguageId() {
        return languageId;
    }

    public LongFilter languageId() {
        if (languageId == null) {
            languageId = new LongFilter();
        }
        return languageId;
    }

    public void setLanguageId(LongFilter languageId) {
        this.languageId = languageId;
    }

    public StringFilter getTermTypeCode() {
        return termTypeCode;
    }

    public StringFilter termTypeCode() {
        if (termTypeCode == null) {
            termTypeCode = new StringFilter();
        }
        return termTypeCode;
    }

    public void setTermTypeCode(StringFilter termTypeCode) {
        this.termTypeCode = termTypeCode;
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

    public LongFilter getOrdContractCharacteristicsId() {
        return ordContractCharacteristicsId;
    }

    public LongFilter ordContractCharacteristicsId() {
        if (ordContractCharacteristicsId == null) {
            ordContractCharacteristicsId = new LongFilter();
        }
        return ordContractCharacteristicsId;
    }

    public void setOrdContractCharacteristicsId(LongFilter ordContractCharacteristicsId) {
        this.ordContractCharacteristicsId = ordContractCharacteristicsId;
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
        final OrdContractCriteria that = (OrdContractCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(contractId, that.contractId) &&
            Objects.equals(languageId, that.languageId) &&
            Objects.equals(termTypeCode, that.termTypeCode) &&
            Objects.equals(action, that.action) &&
            Objects.equals(status, that.status) &&
            Objects.equals(ordContractCharacteristicsId, that.ordContractCharacteristicsId) &&
            Objects.equals(ordProductOrderId, that.ordProductOrderId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, contractId, languageId, termTypeCode, action, status, ordContractCharacteristicsId, ordProductOrderId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdContractCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (contractId != null ? "contractId=" + contractId + ", " : "") +
            (languageId != null ? "languageId=" + languageId + ", " : "") +
            (termTypeCode != null ? "termTypeCode=" + termTypeCode + ", " : "") +
            (action != null ? "action=" + action + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (ordContractCharacteristicsId != null ? "ordContractCharacteristicsId=" + ordContractCharacteristicsId + ", " : "") +
            (ordProductOrderId != null ? "ordProductOrderId=" + ordProductOrderId + ", " : "") +
            "}";
    }
}

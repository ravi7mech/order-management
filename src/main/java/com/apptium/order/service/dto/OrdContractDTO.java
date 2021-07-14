package com.apptium.order.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.apptium.order.domain.OrdContract} entity.
 */
public class OrdContractDTO implements Serializable {

    private Long id;

    private Long contractId;

    private Long languageId;

    private String termTypeCode;

    private String action;

    private String status;

    private OrdProductOrderDTO ordProductOrder;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public Long getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Long languageId) {
        this.languageId = languageId;
    }

    public String getTermTypeCode() {
        return termTypeCode;
    }

    public void setTermTypeCode(String termTypeCode) {
        this.termTypeCode = termTypeCode;
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
        if (!(o instanceof OrdContractDTO)) {
            return false;
        }

        OrdContractDTO ordContractDTO = (OrdContractDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ordContractDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdContractDTO{" +
            "id=" + getId() +
            ", contractId=" + getContractId() +
            ", languageId=" + getLanguageId() +
            ", termTypeCode='" + getTermTypeCode() + "'" +
            ", action='" + getAction() + "'" +
            ", status='" + getStatus() + "'" +
            ", ordProductOrder=" + getOrdProductOrder() +
            "}";
    }
}

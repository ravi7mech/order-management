package com.apptium.order.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A OrdContract.
 */
@Entity
@Table(name = "ord_contract")
public class OrdContract implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "contract_id")
    private Long contractId;

    @Column(name = "language_id")
    private Long languageId;

    @Column(name = "term_type_code")
    private String termTypeCode;

    @Column(name = "action")
    private String action;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "ordContract")
    @JsonIgnoreProperties(value = { "ordContract" }, allowSetters = true)
    private Set<OrdContractCharacteristics> ordContractCharacteristics = new HashSet<>();

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

    public OrdContract id(Long id) {
        this.id = id;
        return this;
    }

    public Long getContractId() {
        return this.contractId;
    }

    public OrdContract contractId(Long contractId) {
        this.contractId = contractId;
        return this;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public Long getLanguageId() {
        return this.languageId;
    }

    public OrdContract languageId(Long languageId) {
        this.languageId = languageId;
        return this;
    }

    public void setLanguageId(Long languageId) {
        this.languageId = languageId;
    }

    public String getTermTypeCode() {
        return this.termTypeCode;
    }

    public OrdContract termTypeCode(String termTypeCode) {
        this.termTypeCode = termTypeCode;
        return this;
    }

    public void setTermTypeCode(String termTypeCode) {
        this.termTypeCode = termTypeCode;
    }

    public String getAction() {
        return this.action;
    }

    public OrdContract action(String action) {
        this.action = action;
        return this;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getStatus() {
        return this.status;
    }

    public OrdContract status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<OrdContractCharacteristics> getOrdContractCharacteristics() {
        return this.ordContractCharacteristics;
    }

    public OrdContract ordContractCharacteristics(Set<OrdContractCharacteristics> ordContractCharacteristics) {
        this.setOrdContractCharacteristics(ordContractCharacteristics);
        return this;
    }

    public OrdContract addOrdContractCharacteristics(OrdContractCharacteristics ordContractCharacteristics) {
        this.ordContractCharacteristics.add(ordContractCharacteristics);
        ordContractCharacteristics.setOrdContract(this);
        return this;
    }

    public OrdContract removeOrdContractCharacteristics(OrdContractCharacteristics ordContractCharacteristics) {
        this.ordContractCharacteristics.remove(ordContractCharacteristics);
        ordContractCharacteristics.setOrdContract(null);
        return this;
    }

    public void setOrdContractCharacteristics(Set<OrdContractCharacteristics> ordContractCharacteristics) {
        if (this.ordContractCharacteristics != null) {
            this.ordContractCharacteristics.forEach(i -> i.setOrdContract(null));
        }
        if (ordContractCharacteristics != null) {
            ordContractCharacteristics.forEach(i -> i.setOrdContract(this));
        }
        this.ordContractCharacteristics = ordContractCharacteristics;
    }

    public OrdProductOrder getOrdProductOrder() {
        return this.ordProductOrder;
    }

    public OrdContract ordProductOrder(OrdProductOrder ordProductOrder) {
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
        if (!(o instanceof OrdContract)) {
            return false;
        }
        return id != null && id.equals(((OrdContract) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdContract{" +
            "id=" + getId() +
            ", contractId=" + getContractId() +
            ", languageId=" + getLanguageId() +
            ", termTypeCode='" + getTermTypeCode() + "'" +
            ", action='" + getAction() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}

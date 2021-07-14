package com.apptium.order.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A OrdAcquisition.
 */
@Entity
@Table(name = "ord_acquisition")
public class OrdAcquisition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "channel")
    private String channel;

    @Column(name = "affiliate")
    private String affiliate;

    @Column(name = "partner")
    private String partner;

    @Column(name = "acquisition_agent")
    private String acquisitionAgent;

    @Column(name = "action")
    private String action;

    @OneToMany(mappedBy = "ordAcquisition")
    @JsonIgnoreProperties(value = { "ordAcquisition" }, allowSetters = true)
    private Set<OrdAcquisitionChar> ordAcquisitionChars = new HashSet<>();

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

    public OrdAcquisition id(Long id) {
        this.id = id;
        return this;
    }

    public String getChannel() {
        return this.channel;
    }

    public OrdAcquisition channel(String channel) {
        this.channel = channel;
        return this;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getAffiliate() {
        return this.affiliate;
    }

    public OrdAcquisition affiliate(String affiliate) {
        this.affiliate = affiliate;
        return this;
    }

    public void setAffiliate(String affiliate) {
        this.affiliate = affiliate;
    }

    public String getPartner() {
        return this.partner;
    }

    public OrdAcquisition partner(String partner) {
        this.partner = partner;
        return this;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getAcquisitionAgent() {
        return this.acquisitionAgent;
    }

    public OrdAcquisition acquisitionAgent(String acquisitionAgent) {
        this.acquisitionAgent = acquisitionAgent;
        return this;
    }

    public void setAcquisitionAgent(String acquisitionAgent) {
        this.acquisitionAgent = acquisitionAgent;
    }

    public String getAction() {
        return this.action;
    }

    public OrdAcquisition action(String action) {
        this.action = action;
        return this;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Set<OrdAcquisitionChar> getOrdAcquisitionChars() {
        return this.ordAcquisitionChars;
    }

    public OrdAcquisition ordAcquisitionChars(Set<OrdAcquisitionChar> ordAcquisitionChars) {
        this.setOrdAcquisitionChars(ordAcquisitionChars);
        return this;
    }

    public OrdAcquisition addOrdAcquisitionChar(OrdAcquisitionChar ordAcquisitionChar) {
        this.ordAcquisitionChars.add(ordAcquisitionChar);
        ordAcquisitionChar.setOrdAcquisition(this);
        return this;
    }

    public OrdAcquisition removeOrdAcquisitionChar(OrdAcquisitionChar ordAcquisitionChar) {
        this.ordAcquisitionChars.remove(ordAcquisitionChar);
        ordAcquisitionChar.setOrdAcquisition(null);
        return this;
    }

    public void setOrdAcquisitionChars(Set<OrdAcquisitionChar> ordAcquisitionChars) {
        if (this.ordAcquisitionChars != null) {
            this.ordAcquisitionChars.forEach(i -> i.setOrdAcquisition(null));
        }
        if (ordAcquisitionChars != null) {
            ordAcquisitionChars.forEach(i -> i.setOrdAcquisition(this));
        }
        this.ordAcquisitionChars = ordAcquisitionChars;
    }

    public OrdProductOrder getOrdProductOrder() {
        return this.ordProductOrder;
    }

    public OrdAcquisition ordProductOrder(OrdProductOrder ordProductOrder) {
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
        if (!(o instanceof OrdAcquisition)) {
            return false;
        }
        return id != null && id.equals(((OrdAcquisition) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdAcquisition{" +
            "id=" + getId() +
            ", channel='" + getChannel() + "'" +
            ", affiliate='" + getAffiliate() + "'" +
            ", partner='" + getPartner() + "'" +
            ", acquisitionAgent='" + getAcquisitionAgent() + "'" +
            ", action='" + getAction() + "'" +
            "}";
    }
}

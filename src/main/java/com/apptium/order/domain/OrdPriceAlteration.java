package com.apptium.order.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A OrdPriceAlteration.
 */
@Entity
@Table(name = "ord_price_alteration")
public class OrdPriceAlteration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price_type")
    private String priceType;

    @Column(name = "unit_of_measure")
    private String unitOfMeasure;

    @Column(name = "recurring_charge_period")
    private String recurringChargePeriod;

    @Column(name = "application_duration")
    private String applicationDuration;

    @Column(name = "priority")
    private String priority;

    @JsonIgnoreProperties(value = { "ordOrderPrice", "ordPriceAlteration" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private OrdPriceAmount ordPriceAmount;

    @ManyToOne
    @JsonIgnoreProperties(value = { "ordPriceAmount", "ordPriceAlterations", "ordProductOrder", "ordOrderItem" }, allowSetters = true)
    private OrdOrderPrice ordOrderPrice;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrdPriceAlteration id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public OrdPriceAlteration name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public OrdPriceAlteration description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriceType() {
        return this.priceType;
    }

    public OrdPriceAlteration priceType(String priceType) {
        this.priceType = priceType;
        return this;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    public String getUnitOfMeasure() {
        return this.unitOfMeasure;
    }

    public OrdPriceAlteration unitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
        return this;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public String getRecurringChargePeriod() {
        return this.recurringChargePeriod;
    }

    public OrdPriceAlteration recurringChargePeriod(String recurringChargePeriod) {
        this.recurringChargePeriod = recurringChargePeriod;
        return this;
    }

    public void setRecurringChargePeriod(String recurringChargePeriod) {
        this.recurringChargePeriod = recurringChargePeriod;
    }

    public String getApplicationDuration() {
        return this.applicationDuration;
    }

    public OrdPriceAlteration applicationDuration(String applicationDuration) {
        this.applicationDuration = applicationDuration;
        return this;
    }

    public void setApplicationDuration(String applicationDuration) {
        this.applicationDuration = applicationDuration;
    }

    public String getPriority() {
        return this.priority;
    }

    public OrdPriceAlteration priority(String priority) {
        this.priority = priority;
        return this;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public OrdPriceAmount getOrdPriceAmount() {
        return this.ordPriceAmount;
    }

    public OrdPriceAlteration ordPriceAmount(OrdPriceAmount ordPriceAmount) {
        this.setOrdPriceAmount(ordPriceAmount);
        return this;
    }

    public void setOrdPriceAmount(OrdPriceAmount ordPriceAmount) {
        this.ordPriceAmount = ordPriceAmount;
    }

    public OrdOrderPrice getOrdOrderPrice() {
        return this.ordOrderPrice;
    }

    public OrdPriceAlteration ordOrderPrice(OrdOrderPrice ordOrderPrice) {
        this.setOrdOrderPrice(ordOrderPrice);
        return this;
    }

    public void setOrdOrderPrice(OrdOrderPrice ordOrderPrice) {
        this.ordOrderPrice = ordOrderPrice;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrdPriceAlteration)) {
            return false;
        }
        return id != null && id.equals(((OrdPriceAlteration) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdPriceAlteration{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", priceType='" + getPriceType() + "'" +
            ", unitOfMeasure='" + getUnitOfMeasure() + "'" +
            ", recurringChargePeriod='" + getRecurringChargePeriod() + "'" +
            ", applicationDuration='" + getApplicationDuration() + "'" +
            ", priority='" + getPriority() + "'" +
            "}";
    }
}

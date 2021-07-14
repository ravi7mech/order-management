package com.apptium.order.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A OrdOrderPrice.
 */
@Entity
@Table(name = "ord_order_price")
public class OrdOrderPrice implements Serializable {

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

    @Column(name = "price_id")
    private Long priceId;

    @JsonIgnoreProperties(value = { "ordOrderPrice", "ordPriceAlteration" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private OrdPriceAmount ordPriceAmount;

    @OneToMany(mappedBy = "ordOrderPrice")
    @JsonIgnoreProperties(value = { "ordPriceAmount", "ordOrderPrice" }, allowSetters = true)
    private Set<OrdPriceAlteration> ordPriceAlterations = new HashSet<>();

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
    @OneToOne(mappedBy = "ordOrderPrice")
    private OrdProductOrder ordProductOrder;

    @JsonIgnoreProperties(
        value = {
            "ordOrderPrice",
            "ordOrderItemRelationship",
            "ordProductOfferingRef",
            "ordProduct",
            "ordOrderItemProvisioning",
            "ordOrderItemChars",
            "ordProductOrder",
        },
        allowSetters = true
    )
    @OneToOne(mappedBy = "ordOrderPrice")
    private OrdOrderItem ordOrderItem;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrdOrderPrice id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public OrdOrderPrice name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public OrdOrderPrice description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriceType() {
        return this.priceType;
    }

    public OrdOrderPrice priceType(String priceType) {
        this.priceType = priceType;
        return this;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    public String getUnitOfMeasure() {
        return this.unitOfMeasure;
    }

    public OrdOrderPrice unitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
        return this;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public String getRecurringChargePeriod() {
        return this.recurringChargePeriod;
    }

    public OrdOrderPrice recurringChargePeriod(String recurringChargePeriod) {
        this.recurringChargePeriod = recurringChargePeriod;
        return this;
    }

    public void setRecurringChargePeriod(String recurringChargePeriod) {
        this.recurringChargePeriod = recurringChargePeriod;
    }

    public Long getPriceId() {
        return this.priceId;
    }

    public OrdOrderPrice priceId(Long priceId) {
        this.priceId = priceId;
        return this;
    }

    public void setPriceId(Long priceId) {
        this.priceId = priceId;
    }

    public OrdPriceAmount getOrdPriceAmount() {
        return this.ordPriceAmount;
    }

    public OrdOrderPrice ordPriceAmount(OrdPriceAmount ordPriceAmount) {
        this.setOrdPriceAmount(ordPriceAmount);
        return this;
    }

    public void setOrdPriceAmount(OrdPriceAmount ordPriceAmount) {
        this.ordPriceAmount = ordPriceAmount;
    }

    public Set<OrdPriceAlteration> getOrdPriceAlterations() {
        return this.ordPriceAlterations;
    }

    public OrdOrderPrice ordPriceAlterations(Set<OrdPriceAlteration> ordPriceAlterations) {
        this.setOrdPriceAlterations(ordPriceAlterations);
        return this;
    }

    public OrdOrderPrice addOrdPriceAlteration(OrdPriceAlteration ordPriceAlteration) {
        this.ordPriceAlterations.add(ordPriceAlteration);
        ordPriceAlteration.setOrdOrderPrice(this);
        return this;
    }

    public OrdOrderPrice removeOrdPriceAlteration(OrdPriceAlteration ordPriceAlteration) {
        this.ordPriceAlterations.remove(ordPriceAlteration);
        ordPriceAlteration.setOrdOrderPrice(null);
        return this;
    }

    public void setOrdPriceAlterations(Set<OrdPriceAlteration> ordPriceAlterations) {
        if (this.ordPriceAlterations != null) {
            this.ordPriceAlterations.forEach(i -> i.setOrdOrderPrice(null));
        }
        if (ordPriceAlterations != null) {
            ordPriceAlterations.forEach(i -> i.setOrdOrderPrice(this));
        }
        this.ordPriceAlterations = ordPriceAlterations;
    }

    public OrdProductOrder getOrdProductOrder() {
        return this.ordProductOrder;
    }

    public OrdOrderPrice ordProductOrder(OrdProductOrder ordProductOrder) {
        this.setOrdProductOrder(ordProductOrder);
        return this;
    }

    public void setOrdProductOrder(OrdProductOrder ordProductOrder) {
        if (this.ordProductOrder != null) {
            this.ordProductOrder.setOrdOrderPrice(null);
        }
        if (ordProductOrder != null) {
            ordProductOrder.setOrdOrderPrice(this);
        }
        this.ordProductOrder = ordProductOrder;
    }

    public OrdOrderItem getOrdOrderItem() {
        return this.ordOrderItem;
    }

    public OrdOrderPrice ordOrderItem(OrdOrderItem ordOrderItem) {
        this.setOrdOrderItem(ordOrderItem);
        return this;
    }

    public void setOrdOrderItem(OrdOrderItem ordOrderItem) {
        if (this.ordOrderItem != null) {
            this.ordOrderItem.setOrdOrderPrice(null);
        }
        if (ordOrderItem != null) {
            ordOrderItem.setOrdOrderPrice(this);
        }
        this.ordOrderItem = ordOrderItem;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrdOrderPrice)) {
            return false;
        }
        return id != null && id.equals(((OrdOrderPrice) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdOrderPrice{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", priceType='" + getPriceType() + "'" +
            ", unitOfMeasure='" + getUnitOfMeasure() + "'" +
            ", recurringChargePeriod='" + getRecurringChargePeriod() + "'" +
            ", priceId=" + getPriceId() +
            "}";
    }
}

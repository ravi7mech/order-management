package com.apptium.order.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.apptium.order.domain.OrdPriceAlteration} entity.
 */
public class OrdPriceAlterationDTO implements Serializable {

    private Long id;

    private String name;

    private String description;

    private String priceType;

    private String unitOfMeasure;

    private String recurringChargePeriod;

    private String applicationDuration;

    private String priority;

    private OrdPriceAmountDTO ordPriceAmount;

    private OrdOrderPriceDTO ordOrderPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public String getRecurringChargePeriod() {
        return recurringChargePeriod;
    }

    public void setRecurringChargePeriod(String recurringChargePeriod) {
        this.recurringChargePeriod = recurringChargePeriod;
    }

    public String getApplicationDuration() {
        return applicationDuration;
    }

    public void setApplicationDuration(String applicationDuration) {
        this.applicationDuration = applicationDuration;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public OrdPriceAmountDTO getOrdPriceAmount() {
        return ordPriceAmount;
    }

    public void setOrdPriceAmount(OrdPriceAmountDTO ordPriceAmount) {
        this.ordPriceAmount = ordPriceAmount;
    }

    public OrdOrderPriceDTO getOrdOrderPrice() {
        return ordOrderPrice;
    }

    public void setOrdOrderPrice(OrdOrderPriceDTO ordOrderPrice) {
        this.ordOrderPrice = ordOrderPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrdPriceAlterationDTO)) {
            return false;
        }

        OrdPriceAlterationDTO ordPriceAlterationDTO = (OrdPriceAlterationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ordPriceAlterationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdPriceAlterationDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", priceType='" + getPriceType() + "'" +
            ", unitOfMeasure='" + getUnitOfMeasure() + "'" +
            ", recurringChargePeriod='" + getRecurringChargePeriod() + "'" +
            ", applicationDuration='" + getApplicationDuration() + "'" +
            ", priority='" + getPriority() + "'" +
            ", ordPriceAmount=" + getOrdPriceAmount() +
            ", ordOrderPrice=" + getOrdOrderPrice() +
            "}";
    }
}

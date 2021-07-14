package com.apptium.order.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.apptium.order.domain.OrdOrderPrice} entity.
 */
public class OrdOrderPriceDTO implements Serializable {

    private Long id;

    private String name;

    private String description;

    private String priceType;

    private String unitOfMeasure;

    private String recurringChargePeriod;

    private Long priceId;

    private OrdPriceAmountDTO ordPriceAmount;

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

    public Long getPriceId() {
        return priceId;
    }

    public void setPriceId(Long priceId) {
        this.priceId = priceId;
    }

    public OrdPriceAmountDTO getOrdPriceAmount() {
        return ordPriceAmount;
    }

    public void setOrdPriceAmount(OrdPriceAmountDTO ordPriceAmount) {
        this.ordPriceAmount = ordPriceAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrdOrderPriceDTO)) {
            return false;
        }

        OrdOrderPriceDTO ordOrderPriceDTO = (OrdOrderPriceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ordOrderPriceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdOrderPriceDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", priceType='" + getPriceType() + "'" +
            ", unitOfMeasure='" + getUnitOfMeasure() + "'" +
            ", recurringChargePeriod='" + getRecurringChargePeriod() + "'" +
            ", priceId=" + getPriceId() +
            ", ordPriceAmount=" + getOrdPriceAmount() +
            "}";
    }
}

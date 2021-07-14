package com.apptium.order.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.apptium.order.domain.OrdAcquisition} entity.
 */
public class OrdAcquisitionDTO implements Serializable {

    private Long id;

    private String channel;

    private String affiliate;

    private String partner;

    private String acquisitionAgent;

    private String action;

    private OrdProductOrderDTO ordProductOrder;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getAffiliate() {
        return affiliate;
    }

    public void setAffiliate(String affiliate) {
        this.affiliate = affiliate;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getAcquisitionAgent() {
        return acquisitionAgent;
    }

    public void setAcquisitionAgent(String acquisitionAgent) {
        this.acquisitionAgent = acquisitionAgent;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
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
        if (!(o instanceof OrdAcquisitionDTO)) {
            return false;
        }

        OrdAcquisitionDTO ordAcquisitionDTO = (OrdAcquisitionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ordAcquisitionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdAcquisitionDTO{" +
            "id=" + getId() +
            ", channel='" + getChannel() + "'" +
            ", affiliate='" + getAffiliate() + "'" +
            ", partner='" + getPartner() + "'" +
            ", acquisitionAgent='" + getAcquisitionAgent() + "'" +
            ", action='" + getAction() + "'" +
            ", ordProductOrder=" + getOrdProductOrder() +
            "}";
    }
}

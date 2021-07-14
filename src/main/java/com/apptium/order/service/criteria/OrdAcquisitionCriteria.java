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
 * Criteria class for the {@link com.apptium.order.domain.OrdAcquisition} entity. This class is used
 * in {@link com.apptium.order.web.rest.OrdAcquisitionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ord-acquisitions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OrdAcquisitionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter channel;

    private StringFilter affiliate;

    private StringFilter partner;

    private StringFilter acquisitionAgent;

    private StringFilter action;

    private LongFilter ordAcquisitionCharId;

    private LongFilter ordProductOrderId;

    public OrdAcquisitionCriteria() {}

    public OrdAcquisitionCriteria(OrdAcquisitionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.channel = other.channel == null ? null : other.channel.copy();
        this.affiliate = other.affiliate == null ? null : other.affiliate.copy();
        this.partner = other.partner == null ? null : other.partner.copy();
        this.acquisitionAgent = other.acquisitionAgent == null ? null : other.acquisitionAgent.copy();
        this.action = other.action == null ? null : other.action.copy();
        this.ordAcquisitionCharId = other.ordAcquisitionCharId == null ? null : other.ordAcquisitionCharId.copy();
        this.ordProductOrderId = other.ordProductOrderId == null ? null : other.ordProductOrderId.copy();
    }

    @Override
    public OrdAcquisitionCriteria copy() {
        return new OrdAcquisitionCriteria(this);
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

    public StringFilter getChannel() {
        return channel;
    }

    public StringFilter channel() {
        if (channel == null) {
            channel = new StringFilter();
        }
        return channel;
    }

    public void setChannel(StringFilter channel) {
        this.channel = channel;
    }

    public StringFilter getAffiliate() {
        return affiliate;
    }

    public StringFilter affiliate() {
        if (affiliate == null) {
            affiliate = new StringFilter();
        }
        return affiliate;
    }

    public void setAffiliate(StringFilter affiliate) {
        this.affiliate = affiliate;
    }

    public StringFilter getPartner() {
        return partner;
    }

    public StringFilter partner() {
        if (partner == null) {
            partner = new StringFilter();
        }
        return partner;
    }

    public void setPartner(StringFilter partner) {
        this.partner = partner;
    }

    public StringFilter getAcquisitionAgent() {
        return acquisitionAgent;
    }

    public StringFilter acquisitionAgent() {
        if (acquisitionAgent == null) {
            acquisitionAgent = new StringFilter();
        }
        return acquisitionAgent;
    }

    public void setAcquisitionAgent(StringFilter acquisitionAgent) {
        this.acquisitionAgent = acquisitionAgent;
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

    public LongFilter getOrdAcquisitionCharId() {
        return ordAcquisitionCharId;
    }

    public LongFilter ordAcquisitionCharId() {
        if (ordAcquisitionCharId == null) {
            ordAcquisitionCharId = new LongFilter();
        }
        return ordAcquisitionCharId;
    }

    public void setOrdAcquisitionCharId(LongFilter ordAcquisitionCharId) {
        this.ordAcquisitionCharId = ordAcquisitionCharId;
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
        final OrdAcquisitionCriteria that = (OrdAcquisitionCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(channel, that.channel) &&
            Objects.equals(affiliate, that.affiliate) &&
            Objects.equals(partner, that.partner) &&
            Objects.equals(acquisitionAgent, that.acquisitionAgent) &&
            Objects.equals(action, that.action) &&
            Objects.equals(ordAcquisitionCharId, that.ordAcquisitionCharId) &&
            Objects.equals(ordProductOrderId, that.ordProductOrderId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, channel, affiliate, partner, acquisitionAgent, action, ordAcquisitionCharId, ordProductOrderId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdAcquisitionCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (channel != null ? "channel=" + channel + ", " : "") +
            (affiliate != null ? "affiliate=" + affiliate + ", " : "") +
            (partner != null ? "partner=" + partner + ", " : "") +
            (acquisitionAgent != null ? "acquisitionAgent=" + acquisitionAgent + ", " : "") +
            (action != null ? "action=" + action + ", " : "") +
            (ordAcquisitionCharId != null ? "ordAcquisitionCharId=" + ordAcquisitionCharId + ", " : "") +
            (ordProductOrderId != null ? "ordProductOrderId=" + ordProductOrderId + ", " : "") +
            "}";
    }
}

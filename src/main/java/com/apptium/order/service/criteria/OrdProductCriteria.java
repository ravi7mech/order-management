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
 * Criteria class for the {@link com.apptium.order.domain.OrdProduct} entity. This class is used
 * in {@link com.apptium.order.web.rest.OrdProductResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ord-products?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OrdProductCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter versionId;

    private LongFilter variationId;

    private StringFilter lineOfService;

    private LongFilter assetId;

    private LongFilter serialNo;

    private StringFilter name;

    private LongFilter ordProductCharacteristicsId;

    private LongFilter ordPlaceId;

    private LongFilter ordOrderItemId;

    public OrdProductCriteria() {}

    public OrdProductCriteria(OrdProductCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.versionId = other.versionId == null ? null : other.versionId.copy();
        this.variationId = other.variationId == null ? null : other.variationId.copy();
        this.lineOfService = other.lineOfService == null ? null : other.lineOfService.copy();
        this.assetId = other.assetId == null ? null : other.assetId.copy();
        this.serialNo = other.serialNo == null ? null : other.serialNo.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.ordProductCharacteristicsId = other.ordProductCharacteristicsId == null ? null : other.ordProductCharacteristicsId.copy();
        this.ordPlaceId = other.ordPlaceId == null ? null : other.ordPlaceId.copy();
        this.ordOrderItemId = other.ordOrderItemId == null ? null : other.ordOrderItemId.copy();
    }

    @Override
    public OrdProductCriteria copy() {
        return new OrdProductCriteria(this);
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

    public LongFilter getVersionId() {
        return versionId;
    }

    public LongFilter versionId() {
        if (versionId == null) {
            versionId = new LongFilter();
        }
        return versionId;
    }

    public void setVersionId(LongFilter versionId) {
        this.versionId = versionId;
    }

    public LongFilter getVariationId() {
        return variationId;
    }

    public LongFilter variationId() {
        if (variationId == null) {
            variationId = new LongFilter();
        }
        return variationId;
    }

    public void setVariationId(LongFilter variationId) {
        this.variationId = variationId;
    }

    public StringFilter getLineOfService() {
        return lineOfService;
    }

    public StringFilter lineOfService() {
        if (lineOfService == null) {
            lineOfService = new StringFilter();
        }
        return lineOfService;
    }

    public void setLineOfService(StringFilter lineOfService) {
        this.lineOfService = lineOfService;
    }

    public LongFilter getAssetId() {
        return assetId;
    }

    public LongFilter assetId() {
        if (assetId == null) {
            assetId = new LongFilter();
        }
        return assetId;
    }

    public void setAssetId(LongFilter assetId) {
        this.assetId = assetId;
    }

    public LongFilter getSerialNo() {
        return serialNo;
    }

    public LongFilter serialNo() {
        if (serialNo == null) {
            serialNo = new LongFilter();
        }
        return serialNo;
    }

    public void setSerialNo(LongFilter serialNo) {
        this.serialNo = serialNo;
    }

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public LongFilter getOrdProductCharacteristicsId() {
        return ordProductCharacteristicsId;
    }

    public LongFilter ordProductCharacteristicsId() {
        if (ordProductCharacteristicsId == null) {
            ordProductCharacteristicsId = new LongFilter();
        }
        return ordProductCharacteristicsId;
    }

    public void setOrdProductCharacteristicsId(LongFilter ordProductCharacteristicsId) {
        this.ordProductCharacteristicsId = ordProductCharacteristicsId;
    }

    public LongFilter getOrdPlaceId() {
        return ordPlaceId;
    }

    public LongFilter ordPlaceId() {
        if (ordPlaceId == null) {
            ordPlaceId = new LongFilter();
        }
        return ordPlaceId;
    }

    public void setOrdPlaceId(LongFilter ordPlaceId) {
        this.ordPlaceId = ordPlaceId;
    }

    public LongFilter getOrdOrderItemId() {
        return ordOrderItemId;
    }

    public LongFilter ordOrderItemId() {
        if (ordOrderItemId == null) {
            ordOrderItemId = new LongFilter();
        }
        return ordOrderItemId;
    }

    public void setOrdOrderItemId(LongFilter ordOrderItemId) {
        this.ordOrderItemId = ordOrderItemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OrdProductCriteria that = (OrdProductCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(versionId, that.versionId) &&
            Objects.equals(variationId, that.variationId) &&
            Objects.equals(lineOfService, that.lineOfService) &&
            Objects.equals(assetId, that.assetId) &&
            Objects.equals(serialNo, that.serialNo) &&
            Objects.equals(name, that.name) &&
            Objects.equals(ordProductCharacteristicsId, that.ordProductCharacteristicsId) &&
            Objects.equals(ordPlaceId, that.ordPlaceId) &&
            Objects.equals(ordOrderItemId, that.ordOrderItemId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            versionId,
            variationId,
            lineOfService,
            assetId,
            serialNo,
            name,
            ordProductCharacteristicsId,
            ordPlaceId,
            ordOrderItemId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdProductCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (versionId != null ? "versionId=" + versionId + ", " : "") +
            (variationId != null ? "variationId=" + variationId + ", " : "") +
            (lineOfService != null ? "lineOfService=" + lineOfService + ", " : "") +
            (assetId != null ? "assetId=" + assetId + ", " : "") +
            (serialNo != null ? "serialNo=" + serialNo + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (ordProductCharacteristicsId != null ? "ordProductCharacteristicsId=" + ordProductCharacteristicsId + ", " : "") +
            (ordPlaceId != null ? "ordPlaceId=" + ordPlaceId + ", " : "") +
            (ordOrderItemId != null ? "ordOrderItemId=" + ordOrderItemId + ", " : "") +
            "}";
    }
}

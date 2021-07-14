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
 * Criteria class for the {@link com.apptium.order.domain.OrdProductOfferingRef} entity. This class is used
 * in {@link com.apptium.order.web.rest.OrdProductOfferingRefResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ord-product-offering-refs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OrdProductOfferingRefCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter href;

    private StringFilter name;

    private StringFilter productGuid;

    private LongFilter ordOrderItemId;

    public OrdProductOfferingRefCriteria() {}

    public OrdProductOfferingRefCriteria(OrdProductOfferingRefCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.href = other.href == null ? null : other.href.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.productGuid = other.productGuid == null ? null : other.productGuid.copy();
        this.ordOrderItemId = other.ordOrderItemId == null ? null : other.ordOrderItemId.copy();
    }

    @Override
    public OrdProductOfferingRefCriteria copy() {
        return new OrdProductOfferingRefCriteria(this);
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

    public StringFilter getHref() {
        return href;
    }

    public StringFilter href() {
        if (href == null) {
            href = new StringFilter();
        }
        return href;
    }

    public void setHref(StringFilter href) {
        this.href = href;
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

    public StringFilter getProductGuid() {
        return productGuid;
    }

    public StringFilter productGuid() {
        if (productGuid == null) {
            productGuid = new StringFilter();
        }
        return productGuid;
    }

    public void setProductGuid(StringFilter productGuid) {
        this.productGuid = productGuid;
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
        final OrdProductOfferingRefCriteria that = (OrdProductOfferingRefCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(href, that.href) &&
            Objects.equals(name, that.name) &&
            Objects.equals(productGuid, that.productGuid) &&
            Objects.equals(ordOrderItemId, that.ordOrderItemId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, href, name, productGuid, ordOrderItemId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdProductOfferingRefCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (href != null ? "href=" + href + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (productGuid != null ? "productGuid=" + productGuid + ", " : "") +
            (ordOrderItemId != null ? "ordOrderItemId=" + ordOrderItemId + ", " : "") +
            "}";
    }
}

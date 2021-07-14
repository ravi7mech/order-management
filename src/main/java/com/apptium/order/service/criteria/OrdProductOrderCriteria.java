package com.apptium.order.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.apptium.order.domain.OrdProductOrder} entity. This class is used
 * in {@link com.apptium.order.web.rest.OrdProductOrderResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ord-product-orders?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OrdProductOrderCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter href;

    private StringFilter externalId;

    private StringFilter priority;

    private StringFilter description;

    private StringFilter category;

    private StringFilter status;

    private InstantFilter orderDate;

    private InstantFilter completionDate;

    private InstantFilter requestedStartDate;

    private InstantFilter requestedCompletionDate;

    private InstantFilter expectedCompletionDate;

    private StringFilter notificationContact;

    private LongFilter customerId;

    private IntegerFilter shoppingCartId;

    private StringFilter type;

    private LongFilter locationId;

    private LongFilter ordContactDetailsId;

    private LongFilter ordNoteId;

    private LongFilter ordChannelId;

    private LongFilter ordOrderPriceId;

    private LongFilter ordBillingAccountRefId;

    private LongFilter ordCharacteristicsId;

    private LongFilter ordOrderItemId;

    private LongFilter ordPaymentRefId;

    private LongFilter ordReasonId;

    private LongFilter ordContractId;

    private LongFilter ordFulfillmentId;

    private LongFilter ordAcquisitionId;

    public OrdProductOrderCriteria() {}

    public OrdProductOrderCriteria(OrdProductOrderCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.href = other.href == null ? null : other.href.copy();
        this.externalId = other.externalId == null ? null : other.externalId.copy();
        this.priority = other.priority == null ? null : other.priority.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.category = other.category == null ? null : other.category.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.orderDate = other.orderDate == null ? null : other.orderDate.copy();
        this.completionDate = other.completionDate == null ? null : other.completionDate.copy();
        this.requestedStartDate = other.requestedStartDate == null ? null : other.requestedStartDate.copy();
        this.requestedCompletionDate = other.requestedCompletionDate == null ? null : other.requestedCompletionDate.copy();
        this.expectedCompletionDate = other.expectedCompletionDate == null ? null : other.expectedCompletionDate.copy();
        this.notificationContact = other.notificationContact == null ? null : other.notificationContact.copy();
        this.customerId = other.customerId == null ? null : other.customerId.copy();
        this.shoppingCartId = other.shoppingCartId == null ? null : other.shoppingCartId.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.locationId = other.locationId == null ? null : other.locationId.copy();
        this.ordContactDetailsId = other.ordContactDetailsId == null ? null : other.ordContactDetailsId.copy();
        this.ordNoteId = other.ordNoteId == null ? null : other.ordNoteId.copy();
        this.ordChannelId = other.ordChannelId == null ? null : other.ordChannelId.copy();
        this.ordOrderPriceId = other.ordOrderPriceId == null ? null : other.ordOrderPriceId.copy();
        this.ordBillingAccountRefId = other.ordBillingAccountRefId == null ? null : other.ordBillingAccountRefId.copy();
        this.ordCharacteristicsId = other.ordCharacteristicsId == null ? null : other.ordCharacteristicsId.copy();
        this.ordOrderItemId = other.ordOrderItemId == null ? null : other.ordOrderItemId.copy();
        this.ordPaymentRefId = other.ordPaymentRefId == null ? null : other.ordPaymentRefId.copy();
        this.ordReasonId = other.ordReasonId == null ? null : other.ordReasonId.copy();
        this.ordContractId = other.ordContractId == null ? null : other.ordContractId.copy();
        this.ordFulfillmentId = other.ordFulfillmentId == null ? null : other.ordFulfillmentId.copy();
        this.ordAcquisitionId = other.ordAcquisitionId == null ? null : other.ordAcquisitionId.copy();
    }

    @Override
    public OrdProductOrderCriteria copy() {
        return new OrdProductOrderCriteria(this);
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

    public StringFilter getExternalId() {
        return externalId;
    }

    public StringFilter externalId() {
        if (externalId == null) {
            externalId = new StringFilter();
        }
        return externalId;
    }

    public void setExternalId(StringFilter externalId) {
        this.externalId = externalId;
    }

    public StringFilter getPriority() {
        return priority;
    }

    public StringFilter priority() {
        if (priority == null) {
            priority = new StringFilter();
        }
        return priority;
    }

    public void setPriority(StringFilter priority) {
        this.priority = priority;
    }

    public StringFilter getDescription() {
        return description;
    }

    public StringFilter description() {
        if (description == null) {
            description = new StringFilter();
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getCategory() {
        return category;
    }

    public StringFilter category() {
        if (category == null) {
            category = new StringFilter();
        }
        return category;
    }

    public void setCategory(StringFilter category) {
        this.category = category;
    }

    public StringFilter getStatus() {
        return status;
    }

    public StringFilter status() {
        if (status == null) {
            status = new StringFilter();
        }
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
    }

    public InstantFilter getOrderDate() {
        return orderDate;
    }

    public InstantFilter orderDate() {
        if (orderDate == null) {
            orderDate = new InstantFilter();
        }
        return orderDate;
    }

    public void setOrderDate(InstantFilter orderDate) {
        this.orderDate = orderDate;
    }

    public InstantFilter getCompletionDate() {
        return completionDate;
    }

    public InstantFilter completionDate() {
        if (completionDate == null) {
            completionDate = new InstantFilter();
        }
        return completionDate;
    }

    public void setCompletionDate(InstantFilter completionDate) {
        this.completionDate = completionDate;
    }

    public InstantFilter getRequestedStartDate() {
        return requestedStartDate;
    }

    public InstantFilter requestedStartDate() {
        if (requestedStartDate == null) {
            requestedStartDate = new InstantFilter();
        }
        return requestedStartDate;
    }

    public void setRequestedStartDate(InstantFilter requestedStartDate) {
        this.requestedStartDate = requestedStartDate;
    }

    public InstantFilter getRequestedCompletionDate() {
        return requestedCompletionDate;
    }

    public InstantFilter requestedCompletionDate() {
        if (requestedCompletionDate == null) {
            requestedCompletionDate = new InstantFilter();
        }
        return requestedCompletionDate;
    }

    public void setRequestedCompletionDate(InstantFilter requestedCompletionDate) {
        this.requestedCompletionDate = requestedCompletionDate;
    }

    public InstantFilter getExpectedCompletionDate() {
        return expectedCompletionDate;
    }

    public InstantFilter expectedCompletionDate() {
        if (expectedCompletionDate == null) {
            expectedCompletionDate = new InstantFilter();
        }
        return expectedCompletionDate;
    }

    public void setExpectedCompletionDate(InstantFilter expectedCompletionDate) {
        this.expectedCompletionDate = expectedCompletionDate;
    }

    public StringFilter getNotificationContact() {
        return notificationContact;
    }

    public StringFilter notificationContact() {
        if (notificationContact == null) {
            notificationContact = new StringFilter();
        }
        return notificationContact;
    }

    public void setNotificationContact(StringFilter notificationContact) {
        this.notificationContact = notificationContact;
    }

    public LongFilter getCustomerId() {
        return customerId;
    }

    public LongFilter customerId() {
        if (customerId == null) {
            customerId = new LongFilter();
        }
        return customerId;
    }

    public void setCustomerId(LongFilter customerId) {
        this.customerId = customerId;
    }

    public IntegerFilter getShoppingCartId() {
        return shoppingCartId;
    }

    public IntegerFilter shoppingCartId() {
        if (shoppingCartId == null) {
            shoppingCartId = new IntegerFilter();
        }
        return shoppingCartId;
    }

    public void setShoppingCartId(IntegerFilter shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    public StringFilter getType() {
        return type;
    }

    public StringFilter type() {
        if (type == null) {
            type = new StringFilter();
        }
        return type;
    }

    public void setType(StringFilter type) {
        this.type = type;
    }

    public LongFilter getLocationId() {
        return locationId;
    }

    public LongFilter locationId() {
        if (locationId == null) {
            locationId = new LongFilter();
        }
        return locationId;
    }

    public void setLocationId(LongFilter locationId) {
        this.locationId = locationId;
    }

    public LongFilter getOrdContactDetailsId() {
        return ordContactDetailsId;
    }

    public LongFilter ordContactDetailsId() {
        if (ordContactDetailsId == null) {
            ordContactDetailsId = new LongFilter();
        }
        return ordContactDetailsId;
    }

    public void setOrdContactDetailsId(LongFilter ordContactDetailsId) {
        this.ordContactDetailsId = ordContactDetailsId;
    }

    public LongFilter getOrdNoteId() {
        return ordNoteId;
    }

    public LongFilter ordNoteId() {
        if (ordNoteId == null) {
            ordNoteId = new LongFilter();
        }
        return ordNoteId;
    }

    public void setOrdNoteId(LongFilter ordNoteId) {
        this.ordNoteId = ordNoteId;
    }

    public LongFilter getOrdChannelId() {
        return ordChannelId;
    }

    public LongFilter ordChannelId() {
        if (ordChannelId == null) {
            ordChannelId = new LongFilter();
        }
        return ordChannelId;
    }

    public void setOrdChannelId(LongFilter ordChannelId) {
        this.ordChannelId = ordChannelId;
    }

    public LongFilter getOrdOrderPriceId() {
        return ordOrderPriceId;
    }

    public LongFilter ordOrderPriceId() {
        if (ordOrderPriceId == null) {
            ordOrderPriceId = new LongFilter();
        }
        return ordOrderPriceId;
    }

    public void setOrdOrderPriceId(LongFilter ordOrderPriceId) {
        this.ordOrderPriceId = ordOrderPriceId;
    }

    public LongFilter getOrdBillingAccountRefId() {
        return ordBillingAccountRefId;
    }

    public LongFilter ordBillingAccountRefId() {
        if (ordBillingAccountRefId == null) {
            ordBillingAccountRefId = new LongFilter();
        }
        return ordBillingAccountRefId;
    }

    public void setOrdBillingAccountRefId(LongFilter ordBillingAccountRefId) {
        this.ordBillingAccountRefId = ordBillingAccountRefId;
    }

    public LongFilter getOrdCharacteristicsId() {
        return ordCharacteristicsId;
    }

    public LongFilter ordCharacteristicsId() {
        if (ordCharacteristicsId == null) {
            ordCharacteristicsId = new LongFilter();
        }
        return ordCharacteristicsId;
    }

    public void setOrdCharacteristicsId(LongFilter ordCharacteristicsId) {
        this.ordCharacteristicsId = ordCharacteristicsId;
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

    public LongFilter getOrdPaymentRefId() {
        return ordPaymentRefId;
    }

    public LongFilter ordPaymentRefId() {
        if (ordPaymentRefId == null) {
            ordPaymentRefId = new LongFilter();
        }
        return ordPaymentRefId;
    }

    public void setOrdPaymentRefId(LongFilter ordPaymentRefId) {
        this.ordPaymentRefId = ordPaymentRefId;
    }

    public LongFilter getOrdReasonId() {
        return ordReasonId;
    }

    public LongFilter ordReasonId() {
        if (ordReasonId == null) {
            ordReasonId = new LongFilter();
        }
        return ordReasonId;
    }

    public void setOrdReasonId(LongFilter ordReasonId) {
        this.ordReasonId = ordReasonId;
    }

    public LongFilter getOrdContractId() {
        return ordContractId;
    }

    public LongFilter ordContractId() {
        if (ordContractId == null) {
            ordContractId = new LongFilter();
        }
        return ordContractId;
    }

    public void setOrdContractId(LongFilter ordContractId) {
        this.ordContractId = ordContractId;
    }

    public LongFilter getOrdFulfillmentId() {
        return ordFulfillmentId;
    }

    public LongFilter ordFulfillmentId() {
        if (ordFulfillmentId == null) {
            ordFulfillmentId = new LongFilter();
        }
        return ordFulfillmentId;
    }

    public void setOrdFulfillmentId(LongFilter ordFulfillmentId) {
        this.ordFulfillmentId = ordFulfillmentId;
    }

    public LongFilter getOrdAcquisitionId() {
        return ordAcquisitionId;
    }

    public LongFilter ordAcquisitionId() {
        if (ordAcquisitionId == null) {
            ordAcquisitionId = new LongFilter();
        }
        return ordAcquisitionId;
    }

    public void setOrdAcquisitionId(LongFilter ordAcquisitionId) {
        this.ordAcquisitionId = ordAcquisitionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OrdProductOrderCriteria that = (OrdProductOrderCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(href, that.href) &&
            Objects.equals(externalId, that.externalId) &&
            Objects.equals(priority, that.priority) &&
            Objects.equals(description, that.description) &&
            Objects.equals(category, that.category) &&
            Objects.equals(status, that.status) &&
            Objects.equals(orderDate, that.orderDate) &&
            Objects.equals(completionDate, that.completionDate) &&
            Objects.equals(requestedStartDate, that.requestedStartDate) &&
            Objects.equals(requestedCompletionDate, that.requestedCompletionDate) &&
            Objects.equals(expectedCompletionDate, that.expectedCompletionDate) &&
            Objects.equals(notificationContact, that.notificationContact) &&
            Objects.equals(customerId, that.customerId) &&
            Objects.equals(shoppingCartId, that.shoppingCartId) &&
            Objects.equals(type, that.type) &&
            Objects.equals(locationId, that.locationId) &&
            Objects.equals(ordContactDetailsId, that.ordContactDetailsId) &&
            Objects.equals(ordNoteId, that.ordNoteId) &&
            Objects.equals(ordChannelId, that.ordChannelId) &&
            Objects.equals(ordOrderPriceId, that.ordOrderPriceId) &&
            Objects.equals(ordBillingAccountRefId, that.ordBillingAccountRefId) &&
            Objects.equals(ordCharacteristicsId, that.ordCharacteristicsId) &&
            Objects.equals(ordOrderItemId, that.ordOrderItemId) &&
            Objects.equals(ordPaymentRefId, that.ordPaymentRefId) &&
            Objects.equals(ordReasonId, that.ordReasonId) &&
            Objects.equals(ordContractId, that.ordContractId) &&
            Objects.equals(ordFulfillmentId, that.ordFulfillmentId) &&
            Objects.equals(ordAcquisitionId, that.ordAcquisitionId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            href,
            externalId,
            priority,
            description,
            category,
            status,
            orderDate,
            completionDate,
            requestedStartDate,
            requestedCompletionDate,
            expectedCompletionDate,
            notificationContact,
            customerId,
            shoppingCartId,
            type,
            locationId,
            ordContactDetailsId,
            ordNoteId,
            ordChannelId,
            ordOrderPriceId,
            ordBillingAccountRefId,
            ordCharacteristicsId,
            ordOrderItemId,
            ordPaymentRefId,
            ordReasonId,
            ordContractId,
            ordFulfillmentId,
            ordAcquisitionId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdProductOrderCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (href != null ? "href=" + href + ", " : "") +
            (externalId != null ? "externalId=" + externalId + ", " : "") +
            (priority != null ? "priority=" + priority + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (category != null ? "category=" + category + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (orderDate != null ? "orderDate=" + orderDate + ", " : "") +
            (completionDate != null ? "completionDate=" + completionDate + ", " : "") +
            (requestedStartDate != null ? "requestedStartDate=" + requestedStartDate + ", " : "") +
            (requestedCompletionDate != null ? "requestedCompletionDate=" + requestedCompletionDate + ", " : "") +
            (expectedCompletionDate != null ? "expectedCompletionDate=" + expectedCompletionDate + ", " : "") +
            (notificationContact != null ? "notificationContact=" + notificationContact + ", " : "") +
            (customerId != null ? "customerId=" + customerId + ", " : "") +
            (shoppingCartId != null ? "shoppingCartId=" + shoppingCartId + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (locationId != null ? "locationId=" + locationId + ", " : "") +
            (ordContactDetailsId != null ? "ordContactDetailsId=" + ordContactDetailsId + ", " : "") +
            (ordNoteId != null ? "ordNoteId=" + ordNoteId + ", " : "") +
            (ordChannelId != null ? "ordChannelId=" + ordChannelId + ", " : "") +
            (ordOrderPriceId != null ? "ordOrderPriceId=" + ordOrderPriceId + ", " : "") +
            (ordBillingAccountRefId != null ? "ordBillingAccountRefId=" + ordBillingAccountRefId + ", " : "") +
            (ordCharacteristicsId != null ? "ordCharacteristicsId=" + ordCharacteristicsId + ", " : "") +
            (ordOrderItemId != null ? "ordOrderItemId=" + ordOrderItemId + ", " : "") +
            (ordPaymentRefId != null ? "ordPaymentRefId=" + ordPaymentRefId + ", " : "") +
            (ordReasonId != null ? "ordReasonId=" + ordReasonId + ", " : "") +
            (ordContractId != null ? "ordContractId=" + ordContractId + ", " : "") +
            (ordFulfillmentId != null ? "ordFulfillmentId=" + ordFulfillmentId + ", " : "") +
            (ordAcquisitionId != null ? "ordAcquisitionId=" + ordAcquisitionId + ", " : "") +
            "}";
    }
}

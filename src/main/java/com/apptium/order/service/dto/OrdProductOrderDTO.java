package com.apptium.order.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.apptium.order.domain.OrdProductOrder} entity.
 */
public class OrdProductOrderDTO implements Serializable {

    private Long id;

    private String href;

    private String externalId;

    private String priority;

    private String description;

    private String category;

    private String status;

    private Instant orderDate;

    private Instant completionDate;

    private Instant requestedStartDate;

    private Instant requestedCompletionDate;

    private Instant expectedCompletionDate;

    private String notificationContact;

    private Long customerId;

    private Integer shoppingCartId;

    private String type;

    private Long locationId;

    private OrdContactDetailsDTO ordContactDetails;

    private OrdNoteDTO ordNote;

    private OrdChannelDTO ordChannel;

    private OrdOrderPriceDTO ordOrderPrice;

    private OrdBillingAccountRefDTO ordBillingAccountRef;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Instant orderDate) {
        this.orderDate = orderDate;
    }

    public Instant getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Instant completionDate) {
        this.completionDate = completionDate;
    }

    public Instant getRequestedStartDate() {
        return requestedStartDate;
    }

    public void setRequestedStartDate(Instant requestedStartDate) {
        this.requestedStartDate = requestedStartDate;
    }

    public Instant getRequestedCompletionDate() {
        return requestedCompletionDate;
    }

    public void setRequestedCompletionDate(Instant requestedCompletionDate) {
        this.requestedCompletionDate = requestedCompletionDate;
    }

    public Instant getExpectedCompletionDate() {
        return expectedCompletionDate;
    }

    public void setExpectedCompletionDate(Instant expectedCompletionDate) {
        this.expectedCompletionDate = expectedCompletionDate;
    }

    public String getNotificationContact() {
        return notificationContact;
    }

    public void setNotificationContact(String notificationContact) {
        this.notificationContact = notificationContact;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Integer getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(Integer shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public OrdContactDetailsDTO getOrdContactDetails() {
        return ordContactDetails;
    }

    public void setOrdContactDetails(OrdContactDetailsDTO ordContactDetails) {
        this.ordContactDetails = ordContactDetails;
    }

    public OrdNoteDTO getOrdNote() {
        return ordNote;
    }

    public void setOrdNote(OrdNoteDTO ordNote) {
        this.ordNote = ordNote;
    }

    public OrdChannelDTO getOrdChannel() {
        return ordChannel;
    }

    public void setOrdChannel(OrdChannelDTO ordChannel) {
        this.ordChannel = ordChannel;
    }

    public OrdOrderPriceDTO getOrdOrderPrice() {
        return ordOrderPrice;
    }

    public void setOrdOrderPrice(OrdOrderPriceDTO ordOrderPrice) {
        this.ordOrderPrice = ordOrderPrice;
    }

    public OrdBillingAccountRefDTO getOrdBillingAccountRef() {
        return ordBillingAccountRef;
    }

    public void setOrdBillingAccountRef(OrdBillingAccountRefDTO ordBillingAccountRef) {
        this.ordBillingAccountRef = ordBillingAccountRef;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrdProductOrderDTO)) {
            return false;
        }

        OrdProductOrderDTO ordProductOrderDTO = (OrdProductOrderDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ordProductOrderDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdProductOrderDTO{" +
            "id=" + getId() +
            ", href='" + getHref() + "'" +
            ", externalId='" + getExternalId() + "'" +
            ", priority='" + getPriority() + "'" +
            ", description='" + getDescription() + "'" +
            ", category='" + getCategory() + "'" +
            ", status='" + getStatus() + "'" +
            ", orderDate='" + getOrderDate() + "'" +
            ", completionDate='" + getCompletionDate() + "'" +
            ", requestedStartDate='" + getRequestedStartDate() + "'" +
            ", requestedCompletionDate='" + getRequestedCompletionDate() + "'" +
            ", expectedCompletionDate='" + getExpectedCompletionDate() + "'" +
            ", notificationContact='" + getNotificationContact() + "'" +
            ", customerId=" + getCustomerId() +
            ", shoppingCartId=" + getShoppingCartId() +
            ", type='" + getType() + "'" +
            ", locationId=" + getLocationId() +
            ", ordContactDetails=" + getOrdContactDetails() +
            ", ordNote=" + getOrdNote() +
            ", ordChannel=" + getOrdChannel() +
            ", ordOrderPrice=" + getOrdOrderPrice() +
            ", ordBillingAccountRef=" + getOrdBillingAccountRef() +
            "}";
    }
}

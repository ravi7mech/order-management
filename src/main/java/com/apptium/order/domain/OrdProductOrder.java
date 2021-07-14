package com.apptium.order.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A OrdProductOrder.
 */
@Entity
@Table(name = "ord_product_order")
public class OrdProductOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "href")
    private String href;

    @Column(name = "external_id")
    private String externalId;

    @Column(name = "priority")
    private String priority;

    @Column(name = "description")
    private String description;

    @Column(name = "category")
    private String category;

    @Column(name = "status")
    private String status;

    @Column(name = "order_date")
    private Instant orderDate;

    @Column(name = "completion_date")
    private Instant completionDate;

    @Column(name = "requested_start_date")
    private Instant requestedStartDate;

    @Column(name = "requested_completion_date")
    private Instant requestedCompletionDate;

    @Column(name = "expected_completion_date")
    private Instant expectedCompletionDate;

    @Column(name = "notification_contact")
    private String notificationContact;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "shopping_cart_id")
    private Integer shoppingCartId;

    @Column(name = "type")
    private String type;

    @Column(name = "location_id")
    private Long locationId;

    @JsonIgnoreProperties(value = { "ordProductOrder" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private OrdContactDetails ordContactDetails;

    @JsonIgnoreProperties(value = { "ordProductOrder" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private OrdNote ordNote;

    @JsonIgnoreProperties(value = { "ordProductOrder" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private OrdChannel ordChannel;

    @JsonIgnoreProperties(value = { "ordPriceAmount", "ordPriceAlterations", "ordProductOrder", "ordOrderItem" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private OrdOrderPrice ordOrderPrice;

    @JsonIgnoreProperties(value = { "ordProductOrder" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private OrdBillingAccountRef ordBillingAccountRef;

    @OneToMany(mappedBy = "ordProductOrder")
    @JsonIgnoreProperties(value = { "ordProductOrder" }, allowSetters = true)
    private Set<OrdCharacteristics> ordCharacteristics = new HashSet<>();

    @OneToMany(mappedBy = "ordProductOrder")
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
    private Set<OrdOrderItem> ordOrderItems = new HashSet<>();

    @OneToMany(mappedBy = "ordProductOrder")
    @JsonIgnoreProperties(value = { "ordProductOrder" }, allowSetters = true)
    private Set<OrdPaymentRef> ordPaymentRefs = new HashSet<>();

    @OneToMany(mappedBy = "ordProductOrder")
    @JsonIgnoreProperties(value = { "ordProductOrder" }, allowSetters = true)
    private Set<OrdReason> ordReasons = new HashSet<>();

    @OneToMany(mappedBy = "ordProductOrder")
    @JsonIgnoreProperties(value = { "ordContractCharacteristics", "ordProductOrder" }, allowSetters = true)
    private Set<OrdContract> ordContracts = new HashSet<>();

    @OneToMany(mappedBy = "ordProductOrder")
    @JsonIgnoreProperties(value = { "ordFulfillmentChars", "ordProductOrder" }, allowSetters = true)
    private Set<OrdFulfillment> ordFulfillments = new HashSet<>();

    @OneToMany(mappedBy = "ordProductOrder")
    @JsonIgnoreProperties(value = { "ordAcquisitionChars", "ordProductOrder" }, allowSetters = true)
    private Set<OrdAcquisition> ordAcquisitions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrdProductOrder id(Long id) {
        this.id = id;
        return this;
    }

    public String getHref() {
        return this.href;
    }

    public OrdProductOrder href(String href) {
        this.href = href;
        return this;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getExternalId() {
        return this.externalId;
    }

    public OrdProductOrder externalId(String externalId) {
        this.externalId = externalId;
        return this;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getPriority() {
        return this.priority;
    }

    public OrdProductOrder priority(String priority) {
        this.priority = priority;
        return this;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return this.description;
    }

    public OrdProductOrder description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return this.category;
    }

    public OrdProductOrder category(String category) {
        this.category = category;
        return this;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return this.status;
    }

    public OrdProductOrder status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getOrderDate() {
        return this.orderDate;
    }

    public OrdProductOrder orderDate(Instant orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public void setOrderDate(Instant orderDate) {
        this.orderDate = orderDate;
    }

    public Instant getCompletionDate() {
        return this.completionDate;
    }

    public OrdProductOrder completionDate(Instant completionDate) {
        this.completionDate = completionDate;
        return this;
    }

    public void setCompletionDate(Instant completionDate) {
        this.completionDate = completionDate;
    }

    public Instant getRequestedStartDate() {
        return this.requestedStartDate;
    }

    public OrdProductOrder requestedStartDate(Instant requestedStartDate) {
        this.requestedStartDate = requestedStartDate;
        return this;
    }

    public void setRequestedStartDate(Instant requestedStartDate) {
        this.requestedStartDate = requestedStartDate;
    }

    public Instant getRequestedCompletionDate() {
        return this.requestedCompletionDate;
    }

    public OrdProductOrder requestedCompletionDate(Instant requestedCompletionDate) {
        this.requestedCompletionDate = requestedCompletionDate;
        return this;
    }

    public void setRequestedCompletionDate(Instant requestedCompletionDate) {
        this.requestedCompletionDate = requestedCompletionDate;
    }

    public Instant getExpectedCompletionDate() {
        return this.expectedCompletionDate;
    }

    public OrdProductOrder expectedCompletionDate(Instant expectedCompletionDate) {
        this.expectedCompletionDate = expectedCompletionDate;
        return this;
    }

    public void setExpectedCompletionDate(Instant expectedCompletionDate) {
        this.expectedCompletionDate = expectedCompletionDate;
    }

    public String getNotificationContact() {
        return this.notificationContact;
    }

    public OrdProductOrder notificationContact(String notificationContact) {
        this.notificationContact = notificationContact;
        return this;
    }

    public void setNotificationContact(String notificationContact) {
        this.notificationContact = notificationContact;
    }

    public Long getCustomerId() {
        return this.customerId;
    }

    public OrdProductOrder customerId(Long customerId) {
        this.customerId = customerId;
        return this;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Integer getShoppingCartId() {
        return this.shoppingCartId;
    }

    public OrdProductOrder shoppingCartId(Integer shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
        return this;
    }

    public void setShoppingCartId(Integer shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    public String getType() {
        return this.type;
    }

    public OrdProductOrder type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getLocationId() {
        return this.locationId;
    }

    public OrdProductOrder locationId(Long locationId) {
        this.locationId = locationId;
        return this;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public OrdContactDetails getOrdContactDetails() {
        return this.ordContactDetails;
    }

    public OrdProductOrder ordContactDetails(OrdContactDetails ordContactDetails) {
        this.setOrdContactDetails(ordContactDetails);
        return this;
    }

    public void setOrdContactDetails(OrdContactDetails ordContactDetails) {
        this.ordContactDetails = ordContactDetails;
    }

    public OrdNote getOrdNote() {
        return this.ordNote;
    }

    public OrdProductOrder ordNote(OrdNote ordNote) {
        this.setOrdNote(ordNote);
        return this;
    }

    public void setOrdNote(OrdNote ordNote) {
        this.ordNote = ordNote;
    }

    public OrdChannel getOrdChannel() {
        return this.ordChannel;
    }

    public OrdProductOrder ordChannel(OrdChannel ordChannel) {
        this.setOrdChannel(ordChannel);
        return this;
    }

    public void setOrdChannel(OrdChannel ordChannel) {
        this.ordChannel = ordChannel;
    }

    public OrdOrderPrice getOrdOrderPrice() {
        return this.ordOrderPrice;
    }

    public OrdProductOrder ordOrderPrice(OrdOrderPrice ordOrderPrice) {
        this.setOrdOrderPrice(ordOrderPrice);
        return this;
    }

    public void setOrdOrderPrice(OrdOrderPrice ordOrderPrice) {
        this.ordOrderPrice = ordOrderPrice;
    }

    public OrdBillingAccountRef getOrdBillingAccountRef() {
        return this.ordBillingAccountRef;
    }

    public OrdProductOrder ordBillingAccountRef(OrdBillingAccountRef ordBillingAccountRef) {
        this.setOrdBillingAccountRef(ordBillingAccountRef);
        return this;
    }

    public void setOrdBillingAccountRef(OrdBillingAccountRef ordBillingAccountRef) {
        this.ordBillingAccountRef = ordBillingAccountRef;
    }

    public Set<OrdCharacteristics> getOrdCharacteristics() {
        return this.ordCharacteristics;
    }

    public OrdProductOrder ordCharacteristics(Set<OrdCharacteristics> ordCharacteristics) {
        this.setOrdCharacteristics(ordCharacteristics);
        return this;
    }

    public OrdProductOrder addOrdCharacteristics(OrdCharacteristics ordCharacteristics) {
        this.ordCharacteristics.add(ordCharacteristics);
        ordCharacteristics.setOrdProductOrder(this);
        return this;
    }

    public OrdProductOrder removeOrdCharacteristics(OrdCharacteristics ordCharacteristics) {
        this.ordCharacteristics.remove(ordCharacteristics);
        ordCharacteristics.setOrdProductOrder(null);
        return this;
    }

    public void setOrdCharacteristics(Set<OrdCharacteristics> ordCharacteristics) {
        if (this.ordCharacteristics != null) {
            this.ordCharacteristics.forEach(i -> i.setOrdProductOrder(null));
        }
        if (ordCharacteristics != null) {
            ordCharacteristics.forEach(i -> i.setOrdProductOrder(this));
        }
        this.ordCharacteristics = ordCharacteristics;
    }

    public Set<OrdOrderItem> getOrdOrderItems() {
        return this.ordOrderItems;
    }

    public OrdProductOrder ordOrderItems(Set<OrdOrderItem> ordOrderItems) {
        this.setOrdOrderItems(ordOrderItems);
        return this;
    }

    public OrdProductOrder addOrdOrderItem(OrdOrderItem ordOrderItem) {
        this.ordOrderItems.add(ordOrderItem);
        ordOrderItem.setOrdProductOrder(this);
        return this;
    }

    public OrdProductOrder removeOrdOrderItem(OrdOrderItem ordOrderItem) {
        this.ordOrderItems.remove(ordOrderItem);
        ordOrderItem.setOrdProductOrder(null);
        return this;
    }

    public void setOrdOrderItems(Set<OrdOrderItem> ordOrderItems) {
        if (this.ordOrderItems != null) {
            this.ordOrderItems.forEach(i -> i.setOrdProductOrder(null));
        }
        if (ordOrderItems != null) {
            ordOrderItems.forEach(i -> i.setOrdProductOrder(this));
        }
        this.ordOrderItems = ordOrderItems;
    }

    public Set<OrdPaymentRef> getOrdPaymentRefs() {
        return this.ordPaymentRefs;
    }

    public OrdProductOrder ordPaymentRefs(Set<OrdPaymentRef> ordPaymentRefs) {
        this.setOrdPaymentRefs(ordPaymentRefs);
        return this;
    }

    public OrdProductOrder addOrdPaymentRef(OrdPaymentRef ordPaymentRef) {
        this.ordPaymentRefs.add(ordPaymentRef);
        ordPaymentRef.setOrdProductOrder(this);
        return this;
    }

    public OrdProductOrder removeOrdPaymentRef(OrdPaymentRef ordPaymentRef) {
        this.ordPaymentRefs.remove(ordPaymentRef);
        ordPaymentRef.setOrdProductOrder(null);
        return this;
    }

    public void setOrdPaymentRefs(Set<OrdPaymentRef> ordPaymentRefs) {
        if (this.ordPaymentRefs != null) {
            this.ordPaymentRefs.forEach(i -> i.setOrdProductOrder(null));
        }
        if (ordPaymentRefs != null) {
            ordPaymentRefs.forEach(i -> i.setOrdProductOrder(this));
        }
        this.ordPaymentRefs = ordPaymentRefs;
    }

    public Set<OrdReason> getOrdReasons() {
        return this.ordReasons;
    }

    public OrdProductOrder ordReasons(Set<OrdReason> ordReasons) {
        this.setOrdReasons(ordReasons);
        return this;
    }

    public OrdProductOrder addOrdReason(OrdReason ordReason) {
        this.ordReasons.add(ordReason);
        ordReason.setOrdProductOrder(this);
        return this;
    }

    public OrdProductOrder removeOrdReason(OrdReason ordReason) {
        this.ordReasons.remove(ordReason);
        ordReason.setOrdProductOrder(null);
        return this;
    }

    public void setOrdReasons(Set<OrdReason> ordReasons) {
        if (this.ordReasons != null) {
            this.ordReasons.forEach(i -> i.setOrdProductOrder(null));
        }
        if (ordReasons != null) {
            ordReasons.forEach(i -> i.setOrdProductOrder(this));
        }
        this.ordReasons = ordReasons;
    }

    public Set<OrdContract> getOrdContracts() {
        return this.ordContracts;
    }

    public OrdProductOrder ordContracts(Set<OrdContract> ordContracts) {
        this.setOrdContracts(ordContracts);
        return this;
    }

    public OrdProductOrder addOrdContract(OrdContract ordContract) {
        this.ordContracts.add(ordContract);
        ordContract.setOrdProductOrder(this);
        return this;
    }

    public OrdProductOrder removeOrdContract(OrdContract ordContract) {
        this.ordContracts.remove(ordContract);
        ordContract.setOrdProductOrder(null);
        return this;
    }

    public void setOrdContracts(Set<OrdContract> ordContracts) {
        if (this.ordContracts != null) {
            this.ordContracts.forEach(i -> i.setOrdProductOrder(null));
        }
        if (ordContracts != null) {
            ordContracts.forEach(i -> i.setOrdProductOrder(this));
        }
        this.ordContracts = ordContracts;
    }

    public Set<OrdFulfillment> getOrdFulfillments() {
        return this.ordFulfillments;
    }

    public OrdProductOrder ordFulfillments(Set<OrdFulfillment> ordFulfillments) {
        this.setOrdFulfillments(ordFulfillments);
        return this;
    }

    public OrdProductOrder addOrdFulfillment(OrdFulfillment ordFulfillment) {
        this.ordFulfillments.add(ordFulfillment);
        ordFulfillment.setOrdProductOrder(this);
        return this;
    }

    public OrdProductOrder removeOrdFulfillment(OrdFulfillment ordFulfillment) {
        this.ordFulfillments.remove(ordFulfillment);
        ordFulfillment.setOrdProductOrder(null);
        return this;
    }

    public void setOrdFulfillments(Set<OrdFulfillment> ordFulfillments) {
        if (this.ordFulfillments != null) {
            this.ordFulfillments.forEach(i -> i.setOrdProductOrder(null));
        }
        if (ordFulfillments != null) {
            ordFulfillments.forEach(i -> i.setOrdProductOrder(this));
        }
        this.ordFulfillments = ordFulfillments;
    }

    public Set<OrdAcquisition> getOrdAcquisitions() {
        return this.ordAcquisitions;
    }

    public OrdProductOrder ordAcquisitions(Set<OrdAcquisition> ordAcquisitions) {
        this.setOrdAcquisitions(ordAcquisitions);
        return this;
    }

    public OrdProductOrder addOrdAcquisition(OrdAcquisition ordAcquisition) {
        this.ordAcquisitions.add(ordAcquisition);
        ordAcquisition.setOrdProductOrder(this);
        return this;
    }

    public OrdProductOrder removeOrdAcquisition(OrdAcquisition ordAcquisition) {
        this.ordAcquisitions.remove(ordAcquisition);
        ordAcquisition.setOrdProductOrder(null);
        return this;
    }

    public void setOrdAcquisitions(Set<OrdAcquisition> ordAcquisitions) {
        if (this.ordAcquisitions != null) {
            this.ordAcquisitions.forEach(i -> i.setOrdProductOrder(null));
        }
        if (ordAcquisitions != null) {
            ordAcquisitions.forEach(i -> i.setOrdProductOrder(this));
        }
        this.ordAcquisitions = ordAcquisitions;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrdProductOrder)) {
            return false;
        }
        return id != null && id.equals(((OrdProductOrder) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdProductOrder{" +
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
            "}";
    }
}

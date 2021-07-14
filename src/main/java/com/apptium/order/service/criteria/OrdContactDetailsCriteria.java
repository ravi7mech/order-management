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
 * Criteria class for the {@link com.apptium.order.domain.OrdContactDetails} entity. This class is used
 * in {@link com.apptium.order.web.rest.OrdContactDetailsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ord-contact-details?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OrdContactDetailsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter contactName;

    private StringFilter contactPhoneNumber;

    private StringFilter contactEmailId;

    private StringFilter firstName;

    private StringFilter lastName;

    private LongFilter ordProductOrderId;

    public OrdContactDetailsCriteria() {}

    public OrdContactDetailsCriteria(OrdContactDetailsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.contactName = other.contactName == null ? null : other.contactName.copy();
        this.contactPhoneNumber = other.contactPhoneNumber == null ? null : other.contactPhoneNumber.copy();
        this.contactEmailId = other.contactEmailId == null ? null : other.contactEmailId.copy();
        this.firstName = other.firstName == null ? null : other.firstName.copy();
        this.lastName = other.lastName == null ? null : other.lastName.copy();
        this.ordProductOrderId = other.ordProductOrderId == null ? null : other.ordProductOrderId.copy();
    }

    @Override
    public OrdContactDetailsCriteria copy() {
        return new OrdContactDetailsCriteria(this);
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

    public StringFilter getContactName() {
        return contactName;
    }

    public StringFilter contactName() {
        if (contactName == null) {
            contactName = new StringFilter();
        }
        return contactName;
    }

    public void setContactName(StringFilter contactName) {
        this.contactName = contactName;
    }

    public StringFilter getContactPhoneNumber() {
        return contactPhoneNumber;
    }

    public StringFilter contactPhoneNumber() {
        if (contactPhoneNumber == null) {
            contactPhoneNumber = new StringFilter();
        }
        return contactPhoneNumber;
    }

    public void setContactPhoneNumber(StringFilter contactPhoneNumber) {
        this.contactPhoneNumber = contactPhoneNumber;
    }

    public StringFilter getContactEmailId() {
        return contactEmailId;
    }

    public StringFilter contactEmailId() {
        if (contactEmailId == null) {
            contactEmailId = new StringFilter();
        }
        return contactEmailId;
    }

    public void setContactEmailId(StringFilter contactEmailId) {
        this.contactEmailId = contactEmailId;
    }

    public StringFilter getFirstName() {
        return firstName;
    }

    public StringFilter firstName() {
        if (firstName == null) {
            firstName = new StringFilter();
        }
        return firstName;
    }

    public void setFirstName(StringFilter firstName) {
        this.firstName = firstName;
    }

    public StringFilter getLastName() {
        return lastName;
    }

    public StringFilter lastName() {
        if (lastName == null) {
            lastName = new StringFilter();
        }
        return lastName;
    }

    public void setLastName(StringFilter lastName) {
        this.lastName = lastName;
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
        final OrdContactDetailsCriteria that = (OrdContactDetailsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(contactName, that.contactName) &&
            Objects.equals(contactPhoneNumber, that.contactPhoneNumber) &&
            Objects.equals(contactEmailId, that.contactEmailId) &&
            Objects.equals(firstName, that.firstName) &&
            Objects.equals(lastName, that.lastName) &&
            Objects.equals(ordProductOrderId, that.ordProductOrderId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, contactName, contactPhoneNumber, contactEmailId, firstName, lastName, ordProductOrderId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdContactDetailsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (contactName != null ? "contactName=" + contactName + ", " : "") +
            (contactPhoneNumber != null ? "contactPhoneNumber=" + contactPhoneNumber + ", " : "") +
            (contactEmailId != null ? "contactEmailId=" + contactEmailId + ", " : "") +
            (firstName != null ? "firstName=" + firstName + ", " : "") +
            (lastName != null ? "lastName=" + lastName + ", " : "") +
            (ordProductOrderId != null ? "ordProductOrderId=" + ordProductOrderId + ", " : "") +
            "}";
    }
}

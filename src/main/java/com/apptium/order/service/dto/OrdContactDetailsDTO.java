package com.apptium.order.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.apptium.order.domain.OrdContactDetails} entity.
 */
public class OrdContactDetailsDTO implements Serializable {

    private Long id;

    private String contactName;

    private String contactPhoneNumber;

    private String contactEmailId;

    private String firstName;

    private String lastName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhoneNumber() {
        return contactPhoneNumber;
    }

    public void setContactPhoneNumber(String contactPhoneNumber) {
        this.contactPhoneNumber = contactPhoneNumber;
    }

    public String getContactEmailId() {
        return contactEmailId;
    }

    public void setContactEmailId(String contactEmailId) {
        this.contactEmailId = contactEmailId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrdContactDetailsDTO)) {
            return false;
        }

        OrdContactDetailsDTO ordContactDetailsDTO = (OrdContactDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ordContactDetailsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdContactDetailsDTO{" +
            "id=" + getId() +
            ", contactName='" + getContactName() + "'" +
            ", contactPhoneNumber='" + getContactPhoneNumber() + "'" +
            ", contactEmailId='" + getContactEmailId() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            "}";
    }
}

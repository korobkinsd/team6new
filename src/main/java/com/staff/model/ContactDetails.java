package com.staff.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
//import java.io.Serializable;
import java.util.Objects;


@Entity
@Table (name = "CONTACT_DETAILS")
public class ContactDetails {

    public enum ContactDetailType {EMAIL, MOBILEPHONE, HOMEPHONE, ADDRESS}

    @Column(name="CANDIDATE_ID", nullable = false)
    private Integer idCandidate;

    @NotNull(message="is required")
    @Size(min=3, max=1000, message="name must be between 3 and 1000 characters long")
    @Column(name="CONTACT_DETAILS", nullable = false, length = 1000)
    private String contactDetails;

    @NotNull(message="is required")
    @Enumerated(EnumType.STRING)
    @Column(name="CONTACT_TYPE", nullable = false)
    private ContactDetailType contactType;

    public final int getIdCandidate() {
        return idCandidate;
    }

    public final void setIdCandidate(final Integer idCandidate) {
        this.idCandidate = idCandidate;
    }

    public final String getContactDetail() {
        return contactDetails;
    }

    public final void setContactDetail(final String contactDetails) {
        this.contactDetails = contactDetails;
    }

    @Override
    public final String toString() {
        return "ContactDetails{" +
                "idCandidate=" + idCandidate +
                "contactType=" + contactType.toString() +
                ", contactDetails='" + contactDetails + '\'' +
                '}';
    }

    @Override
    public final boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ContactDetails that = (ContactDetails) o;
        return idCandidate == that.idCandidate &&
                Objects.equals(contactType, that.contactType) &&
                Objects.equals(contactDetails, that.contactDetails);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(idCandidate, contactType, contactDetails);
    }

}

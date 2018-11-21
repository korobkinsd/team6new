package com.staff.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
//import org.hibernate.annotations.Cascade;
import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table (name = "CONTACT_DETAILS", schema = "team6")
public class ContactDetails {

    public enum ContactType {
            EMAIL( "E-mail" )
        ,   MOBILEPHONE( "Mobile phone" )
        ,   ADDRESS("Address");

        private final String description;

        ContactType(final String description) {
            this.description = description;
        }

        public static ContactType getByString(String value) {
            ContactType[] types = ContactType.values();
            for(ContactType c : types) {
                if (value.equals(c.getDescription())) {
                    return c;
                }
            }
            throw new UnsupportedOperationException( "The contact type " + value + " is not supported!" );
        }

        public String getDescription() { return description; }
    }


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @NotNull(message="is required")
    @Size(min=3, max=1000, message="name must be between 3 and 1000 characters long")
    @Column(name="CONTACT_DETAILS", nullable = false, length = 1000)
    private String contactDetails;

    @NotNull(message="is required")
    @Column(name="CONTACT_TYPE", nullable = false)
    @Convert (converter = ContactTypeConverter.class)
    private ContactType contactType;


    @ManyToOne(fetch = FetchType.EAGER) //, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "CANDIDATE_ID", referencedColumnName = "ID", updatable = false, insertable = true)   //
    //@JsonManagedReference
    @JsonBackReference
    private Candidate candidate;

    public Candidate getCandidate() {
        return this.candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public String getContactType() {
        return contactType.getDescription();
    }

    public void setContactType(String contactType) {
        this.contactType = ContactType.getByString(contactType);
    }

    /*public final int getIdCandidate() {
        return idCandidate;
    }

    public final void setIdCandidate(final Integer idCandidate) {
        this.idCandidate = idCandidate;
    }*/

    public final String getContactDetails() {
        return contactDetails;
    }

    public final void setContactDetails(final String contactDetails) {
        this.contactDetails = contactDetails;
    }

    /*public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }*/

    @Override
    public final String toString() {
        return "ContactDetails{" +
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
        return  Objects.equals(id, that.id) &&
                Objects.equals(contactType, that.contactType) &&
                Objects.equals(contactDetails, that.contactDetails);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(contactType, contactDetails);
    }

    @Converter
    public static class ContactTypeConverter
            implements AttributeConverter<ContactType, String> {

        public String convertToDatabaseColumn( ContactType value ) {
            if ( value == null ) {
                return null;
            }
            return value.getDescription();
        }

        public ContactType convertToEntityAttribute( String value ) {
            if ( value == null ) {
                return null;
            }
            return ContactType.getByString( value );
        }
    }
}

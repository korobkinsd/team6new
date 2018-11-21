package com.staff.model;

//import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

@Entity
@Table(name = "CANDIDATE", schema = "team6")
public class Candidate {   // implements Serializable

    public enum CandidateState {
            ARCHIVE( "В архиве" )
        ,   ACTIVE( "Активен" );

        private final String description;

        CandidateState(final String description) {
            this.description = description;
        }

        public static CandidateState getByString(String state) {
            CandidateState[] states = CandidateState.values();
            for(CandidateState c : states) {
                if (state.equals(c.getDescription())) {
                    return c;
                }
            }
            throw new UnsupportedOperationException( "The state " + state + " is not supported!" );
        }

        public String getDescription() { return description; }
    }


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotNull(message="is required")
    @Size(min=3, max=255, message="name must be between 3 and 255 characters long")
    private String name;

    //@Column(length=255, nullable=true)  - not need to specify - uses by default!
    private String surname;

    @Min(0)
    private Double salary;

    @OneToMany( fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "candidate" )   //,CascadeType.PERSIST,CascadeType.MERGE
    //@JsonBackReference
    private List<ContactDetails> contactDetailsList;

    @Past
    @Temporal(value = TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birthday;


    @NotNull(message="is required")
    @Column(name="CANDIDATE_STATE")
    @Convert (converter = CandidateStateConverter.class)
    private CandidateState candidateState;

    //@Transient
    //private List<String> listCandidateState;


    @Transient
    private final Logger logger = LoggerFactory.getLogger(Candidate.class);

    private List<ContactDetails> getContactDetailsList() {
        return this.contactDetailsList;
    };

    public final void setContactDetailsList(List<ContactDetails> contactDetailsList) {
        this.contactDetailsList = contactDetailsList;
    }

    public final Long getId() {
        return id;
    }

    public final String getName() {
        return name;
    }

    public final String getSurname() {
        return surname;
    }

    public final Double getSalary() {
        return salary;
    }

    /*public final Date getBirthday() {
        return birthday;
    }*/

    public final CandidateState getCandidateState() {
        return candidateState;
    }

    public final void setId(Long id) {
        this.id = id;
    }

    public final void setName(String name) {
        this.name = name;
    }

    public final void setSurname(String surname) {
        this.surname = surname;
    }

    public final void setSalary(double salary) {
        this.salary = salary;
    }

    /*public final void setBirthday(Date birthday) {
        this.birthday = birthday;
    }*/

    public final void setBirthday(String birthday) {
        String[] validPatterns = {"dd.MM.yyyy","yyyy-MM-dd","dd/MM/yyyy","dd-MM-yyyy","dd/mm/yy"};
        SimpleDateFormat formatter = new SimpleDateFormat();
        for (String validPattern : validPatterns) {
            try {
                formatter.applyPattern(validPattern);
                formatter.setLenient(false);
                Date ret = new Date(formatter.parse(birthday).getTime());
                this.birthday = ret;
                return;
            } catch (ParseException e) {
                //ok, nothing to do, just takes next pattern
                logger.debug("Skip pattern " + validPattern);
            }
        }
        logger.debug("Pattern for [" + birthday + "] is not supported!");
    }

    public final void setCandidateState(String st) {
        this.candidateState = CandidateState.getByString(st);
    }

    public final void setCandidateState(CandidateState candidateState) {
        this.candidateState = candidateState;
    }

    @JsonIgnore
    public final String getBirthdayAsString() {
        if (!(this.birthday == null)) {
            Format formatter = new SimpleDateFormat("dd.MM.yyyy");
            return formatter.format(birthday);
        } else {
            return "";
        }
        //return String.format("%td.%tm.%tY",birthday,birthday,birthday);
    }

    // get a string like '01.03.2015', parse it and set birthday of Candidate
    /*public void setBirthday(String s) {

        if (s == null) {
            return;
        }
        try {
            int day = Integer.parseInt(s.substring(0, 2));
            int month = Integer.parseInt(s.substring(3, 5))-1;
            int year = Integer.parseInt(s.substring(6, 10));
            birthday.set(year, month, day);
        } catch (Exception e) {
            e.printStackTrace();
            birthday.set(1900, Calendar.JANUARY, 1);
        }
    }*/

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Candidate candidate = (Candidate) o;
        return  Objects.equals(id,candidate.id) &&
                //Double.compare(candidate.salary, salary) == 0 &&
                //Objects.equals(name, candidate.name) &&
                //Objects.equals(surname, candidate.surname) &&
                Objects.equals(birthday, candidate.birthday) &&
                Objects.equals(candidateState, candidate.candidateState);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(id, birthday, candidateState);
    }

    @Override
    public final String toString() {
        Collection<ContactDetails> contactDetails = this.getContactDetailsList();
        return "Candidate [" +
                "id=" + (this.id != null ? this.getId().toString() : "" )+
                ", name='" + (this.name != null ? this.getName() : "")+ '\'' +
                ", surname='" + (this.surname != null ?this.getSurname() : "") + '\'' +
                ", salary=" + (this.salary != null ? this.getSalary() : "") +
                ", birthday=" + this.getBirthdayAsString() +
                ", candidateState=" + (this.candidateState != null ? this.getCandidateState().getDescription() : "")+
                ", contactDetails: " + contactDetails.toString()+
                "]";
    }

    /*@Override
    public final void setForeignKey(final String foreignKey) {
        this.id = Integer.parseInt(foreignKey);
    }

    @Override
    public final String getForeignKey() {
        return this.id != null ? this.id.toString() : "-1";
    }*/

    @Converter
    public static class CandidateStateConverter
            implements AttributeConverter<CandidateState, String> {

        public String convertToDatabaseColumn( CandidateState value ) {
            if ( value == null ) {
                return null;
            }
            return value.getDescription();
        }

        public CandidateState convertToEntityAttribute( String value ) {
            if ( value == null ) {
                return null;
            }
            return CandidateState.getByString( value );
        }
    }


}

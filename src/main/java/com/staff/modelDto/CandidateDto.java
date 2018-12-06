package com.staff.modelDto;

import com.fasterxml.jackson.annotation.JsonFormat;
//import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
//import com.staff.model.Candidate;
import com.staff.model.ContactDetails;

//import java.text.ParseException;
//import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
//import java.util.TimeZone;

public class CandidateDto {

    //private static final SimpleDateFormat DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private Long id;

    private String name;

    private String surname;

    private Double salary;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birthday;

    //@JsonIgnore
    //@JsonProperty("candidateStateEnum")
    //private Candidate.CandidateState candidateState;

    @JsonProperty("candidateState")
    private String candidateStateAsString;

    private List<ContactDetails> contactDetailsList;

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

    public final Date getBirthday() {
        return this.birthday;
    }

    /*public Date getBirthdayTimezone(String timezone) throws ParseException {
        dateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
        return dateFormat.parse(this.birthday);
    }*/

    //public final Candidate.CandidateState getCandidateState() {
    //    return candidateState;
    //}

    public final String getCandidateStateAsString() {
        return candidateStateAsString;
    }

    public List<ContactDetails> getContactDetailsList() {
        return this.contactDetailsList;
    };

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

    public final void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /*public void setBirthdayTimezone(Date date, String timezone) {
        dateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
        this.birthday = dateFormat.format(date);
    }*/

    //public final void setCandidateState(Candidate.CandidateState value) {
    //    this.candidateState = value;
    //}

    public final void setCandidateStateAsString(String value) {
        this.candidateStateAsString = value;
    }

    public final void setContactDetailsList(List<ContactDetails> contactDetailsList) {
        this.contactDetailsList = contactDetailsList;
    }

}

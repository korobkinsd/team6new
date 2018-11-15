package com.staff.util.filtering;


import com.staff.model.Candidate;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/*
 * Filter for searching candidates
 */
public class CandidateFilter {
    private String name;

    private String surname;

    private Date birthdayFrom;

    private Date birthdayTo;

    private Double salaryFrom;

    private Double salaryTo;

    private List<Candidate.CandidateState> candidateStates;

    public final String getName() {
        return name;
    }

    public final void setName(String name) {
        this.name = name;
    }

    public final String getSurname() {
        return surname;
    }

    public final void setSurname(String surname) {
        this.surname = surname;
    }

    public final void setBirthdayFrom(String birthdayFrom) {
        String[] validPatterns = {"dd.MM.yyyy","dd/MM/yyyy","dd-MM-yyyy","dd/mm/yy","yyyy-MM-dd"};
        SimpleDateFormat formatter = new SimpleDateFormat();
        for (String validPattern : validPatterns) {
            try {
                formatter.applyPattern(validPattern);
                formatter.setLenient(false);
                Date ret = new Date(formatter.parse(birthdayFrom).getTime());
                this.birthdayFrom = ret;
            } catch (ParseException e) {
                //ok, just take next pattern
                //TODO add exception
            }
        }
        this.birthdayFrom = null;
    }

    public final Date getBirthdayFrom() {
        return birthdayFrom;
    }

    public final String getBirthdayFromAsString() {
        if (this.birthdayFrom != null) {
            Format formatter = new SimpleDateFormat("yyyy-MM-dd");
            return formatter.format(this.birthdayFrom);
        } else {
            return null;
        }
    }

    public final void setBirthdayTo(String birthdayTo) {
        String[] validPatterns = {"dd.MM.yyyy","dd/MM/yyyy","dd-MM-yyyy","dd/mm/yy","yyyy-MM-dd"};
        SimpleDateFormat formatter = new SimpleDateFormat();
        for (String validPattern : validPatterns) {
            try {
                formatter.applyPattern(validPattern);
                formatter.setLenient(false);
                Date ret = new Date(formatter.parse(birthdayTo).getTime());
                this.birthdayTo = ret;
            } catch (ParseException e) {
                //ok, just take next pattern
                //TODO add exception
            }
        }
        this.birthdayTo = null;
    }

    public final Date getBirthdayTo() {
        return birthdayTo;
    }

    public final String getBirthdayToAsString() {
        if (this.birthdayTo != null) {
            Format formatter = new SimpleDateFormat("yyyy-MM-dd");
            return formatter.format(this.birthdayTo);
        } else {
            return null;
        }
    }

    public final Double getSalaryFrom() {
        return salaryFrom;
    }

    public final void setSalaryFrom(Double salaryFrom) {
        this.salaryFrom = salaryFrom;
    }

    public final void setSalaryFrom(String salaryFrom) {
        this.salaryFrom = Double.parseDouble(salaryFrom);
    }

    public final Double getSalaryTo() {
        return salaryTo;
    }

    public final void setSalaryTo(Double salaryTo) {
        this.salaryTo = salaryTo;
    }

    public final void setSalaryTo(String salaryTo) {
        this.salaryTo = Double.parseDouble(salaryTo);
    }

    public final List<Candidate.CandidateState> getCandidateStates() {
        return candidateStates;
    }

    public final void setCandidateStates(List<Candidate.CandidateState> candidateStates) {
        this.candidateStates = candidateStates;
    }

    @Override
    public final String toString() {

        StringBuilder result = new StringBuilder();
        for (Candidate.CandidateState o : this.candidateStates) {
            result.append(o.toString()).append(";");
        }
        return "CandidateFilter{" +
                "name='" + getName() + '\'' +
                ", surname='" + getSurname() + '\'' +
                ", birthdayFrom=" + getBirthdayFromAsString() +
                ", birthdayTo=" + getBirthdayToAsString() +
                ", salaryFrom=" + getSalaryFrom().toString() +
                ", salaryTo=" + getSalaryTo().toString() +
                ", candidateStates=" + result.toString() +
                '}';
    }
}

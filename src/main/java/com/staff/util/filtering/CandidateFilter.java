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

    private String sortColumnName;

    private String order;

    private Integer page;

    private Integer pagesize;

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
        if (birthdayFrom!=null && !"".equals(birthdayFrom)) {
            String[] validPatterns = {"dd.MM.yyyy", "dd/MM/yyyy", "dd-MM-yyyy", "dd/mm/yy", "yyyy-MM-dd"};
            SimpleDateFormat formatter = new SimpleDateFormat();
            for (String validPattern : validPatterns) {
                try {
                    formatter.applyPattern(validPattern);
                    formatter.setLenient(false);
                    Date ret = new Date(formatter.parse(birthdayFrom).getTime());
                    this.birthdayFrom = ret;
                } catch (ParseException e) {
                    //ok, just take next pattern
                    return;//TODO add exception
                }
            }
        } else {
            this.birthdayFrom = null;
        }
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
        if (birthdayTo!=null && !"".equals(birthdayTo)) {
            String[] validPatterns = {"dd.MM.yyyy", "dd/MM/yyyy", "dd-MM-yyyy", "dd/mm/yy", "yyyy-MM-dd"};
            SimpleDateFormat formatter = new SimpleDateFormat();
            for (String validPattern : validPatterns) {
                try {
                    formatter.applyPattern(validPattern);
                    formatter.setLenient(false);
                    Date ret = new Date(formatter.parse(birthdayTo).getTime());
                    this.birthdayTo = ret;
                } catch (ParseException e) {
                    //ok, just take next pattern
                    return;//TODO add exception
                }
            }
        } else {
            this.birthdayTo = null;
        }
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
        if (salaryFrom!=null && !"".equals(salaryFrom)) {
            this.salaryFrom = Double.parseDouble(salaryFrom);
        }
    }

    public final Double getSalaryTo() {
        return salaryTo;
    }

    public final void setSalaryTo(Double salaryTo) {
        this.salaryTo = salaryTo;
    }

    public final void setSalaryTo(String salaryTo) {
        if (salaryTo!=null && !"".equals(salaryTo)) {
            this.salaryTo = Double.parseDouble(salaryTo);
        }
    }

    public final List<Candidate.CandidateState> getCandidateStates() {
        return candidateStates;
    }

    public final void setCandidateStates(List<Candidate.CandidateState> candidateStates) {
        this.candidateStates = candidateStates;
    }

    public String getSortColumnName() {
        return sortColumnName;
    }

    public void setSortColumnName(String sortColumnName) {
        this.sortColumnName = sortColumnName;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        if ( page > 0 ) {
            this.page = page;
        } else {
            this.page = 1;
        }
    }

    public Integer getPagesize() {
        return pagesize;
    }

    public void setPagesize(Integer pagesize) {
        if ( pagesize > 0 ) {
            this.pagesize = pagesize;
        } else {
            this.pagesize = 10;
        }
    }

    @Override
    public final String toString() {

        StringBuilder result = new StringBuilder();
        for (Candidate.CandidateState o : this.candidateStates) {
            result.append(o.toString()).append(";");
        }
        return "CandidateFilter{" +
                "name='" + ( this.name!=null ? getName() : "") + '\'' +
                ", surname='" + ( this.name!=null ? getSurname() : "") + '\'' +
                ", birthdayFrom=" + ( this.birthdayFrom != null ? getBirthdayFromAsString() : "") +
                ", birthdayTo=" + ( this.birthdayTo != null ? getBirthdayToAsString() : "") +
                ", salaryFrom=" + ( this.salaryFrom != null ? getSalaryFrom().toString() : "") +
                ", salaryTo=" + ( this.salaryTo != null ? getSalaryTo().toString() : "") +
                ", candidateStates=" + ( result!=null ? result.toString() : "") +
                '}';
    }
}


package com.staff.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "vacancy", schema = "team6")
public class Vacancy  {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @Column(name = "position")
    private String position;

    
  /*  @Column(name = "DEVELOPER_ID")
    private Long idDeveloper;*/

    
    @Column(name = "salary_From")
    private double salaryFrom;
    
    @Column(name = "salary_To")
    private double salaryTo;
    
    @Column(name = "experience_Years_Require")
    private double experienceYearsRequire;
    
    @Column(name = "vacancy_state")
    private String state;

    @ManyToOne(fetch = FetchType.EAGER )
    @JsonIgnore
    @JoinColumn(name = "DEVELOPER_ID")
    private User developer;

    @ManyToMany(fetch = FetchType.EAGER/*LAZY,
            cascade=CascadeType.ALL*/)
    @JsonIgnore
    @JoinTable(name = "vacancy_requirement",
            joinColumns = { @JoinColumn(name = "VACANCY_ID") },
            inverseJoinColumns = { @JoinColumn(name = "REQUIREMENT") })
    private List<Requirement> requirementList;

    @ManyToMany(fetch = FetchType.LAZY/*LAZY,
            cascade=CascadeType.ALL*/)
   // @OrderColumn
    @JsonIgnore
    @JoinTable(name = "vacancy_candidates",
            joinColumns = { @JoinColumn(name = "VACANCY_ID") },
            inverseJoinColumns = { @JoinColumn(name = "CANDIDATE_ID") })
    private List<Candidate> candidateList;

    public void setDeveloper(User developer) {
        this.developer = developer;
    }

    public List<Candidate> getCandidateList() {
        return candidateList;
    }

    public void setCandidateList(List<Candidate> candidateList) {
        this.candidateList = candidateList;
    }



     public List<Requirement> getRequirementList() {
        return requirementList;
    }

    public void setRequirementList(List<Requirement> requirementList) {
        this.requirementList = requirementList;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vacancy vacancy = (Vacancy) o;
        return Objects.equals(getId(), vacancy.getId()) &&
                Objects.equals(getPosition(), vacancy.getPosition()/* &&
                Objects.equals(getIdDeveloper(), vacancy.getIdDeveloper()*/);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPosition());
    }

    public User getDeveloper() {
        return this.developer;
    }


    public String getState() {
        return state;
    }

    public void setState(String status) {
        this.state = status;
    }

    public double getExperienceYearsRequire() {
        return experienceYearsRequire;
    }

    public void setExperienceYearsRequire(double experienceYearsRequire) {
        this.experienceYearsRequire = experienceYearsRequire;   }



    private boolean isNew() { return this.id == null;    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

  /*  public Long getIdDeveloper() {
        return idDeveloper;
    }

    public void setIdDeveloper(Long idDeveloper) {
        this.idDeveloper = idDeveloper;
    }*/

    public double getSalaryFrom() {
        return salaryFrom;
    }

    public void setSalaryFrom(double salaryFrom) {
        this.salaryFrom = salaryFrom;
    }

    public double getSalaryTo() {
        return salaryTo;
    }

    public void setSalaryTo(double salaryTo) {
        this.salaryTo = salaryTo;
    }


    @Override
    public String toString() {

        return "Vacancy [id=" + id + ", position=" + position + ", idDeveloper=" + ", salaryFrom=" + salaryFrom
                + ", salaryTo=" + salaryTo + ", experienceYearsRequire "+experienceYearsRequire+"]" + isNew();
    }





}



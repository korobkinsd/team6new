
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

    public void setUser(User developer) {
        this.developer = developer;
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



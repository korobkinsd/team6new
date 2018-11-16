
package com.staff.model;


import javax.persistence.*;

@Entity
@Table(name = "vacancy", schema = "team6")
public class Vacancy  {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @Column(name = "position")
    private String position;

    
    @Column(name = "idDeveloper")
    private Long idDeveloper;

    
    @Column(name = "salaryFrom")
    private double salaryFrom;
    
    @Column(name = "salaryTo")
    private double salaryTo;
    
    @Column(name = "experienceYearsRequire")
    private double experienceYearsRequire;
    
    @Column(name = "state")
    private String state;

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
        this.experienceYearsRequire = experienceYearsRequire;
    }



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

    public Long getIdDeveloper() {
        return idDeveloper;
    }

    public void setIdDeveloper(Long idDeveloper) {
        this.idDeveloper = idDeveloper;
    }

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
        return "Vacancy [id=" + id + ", position=" + position + ", idDeveloper=" + idDeveloper + ", salaryFrom=" + salaryFrom
                + ", salaryTo=" + salaryTo + ", experienceYearsRequire "+experienceYearsRequire+"]" + isNew();
    }





}



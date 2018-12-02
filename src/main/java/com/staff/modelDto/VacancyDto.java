package com.staff.modelDto;

import com.staff.model.Candidate;
import com.staff.model.Requirement;
import com.staff.model.User;
import com.staff.model.Vacancy;

import java.util.List;

public class VacancyDto {
    private Long id;
    private String position;
    private Long idDeveloper;
    private double salaryFrom;
    private double salaryTo;
    private double experienceYearsRequire;
    private String state;
    private User developer;
    private List<Requirement> requirementList;
    private List<Candidate> candidateList;


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

    public double getExperienceYearsRequire() {
        return experienceYearsRequire;
    }

    public void setExperienceYearsRequire(double experienceYearsRequire) {
        this.experienceYearsRequire = experienceYearsRequire;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public User getDeveloper() {
        return developer;
    }

    public void setDeveloper(User developer) {
        this.developer = developer;
    }
/*  public VacancyDto(Vacancy vacancy) {
        this.id = vacancy.getId();
        this.position = vacancy.getPosition();
        this.idDeveloper = vacancy.getIdDeveloper();
        this.salaryFrom = vacancy.getSalaryFrom();
        this.salaryTo = vacancy.getSalaryTo();
        this.experienceYearsRequire = vacancy.getExperienceYearsRequire();
        this.state = vacancy.getState();
        this.developer = vacancy.getDeveloper();
        this.developer.setVacancies(null);
    }*/

    public VacancyDto(Vacancy vacancy) {
        this.id = vacancy.getId();
        this.position = vacancy.getPosition();
      //  this.idDeveloper = vacancy.getIdDeveloper();
        this.salaryFrom = vacancy.getSalaryFrom();
        this.salaryTo = vacancy.getSalaryTo();
        this.experienceYearsRequire = vacancy.getExperienceYearsRequire();
        this.state = vacancy.getState();
        this.developer = vacancy.getDeveloper();
        this.developer.setVacancies(null);
        this.requirementList=vacancy.getRequirementList();
        this.candidateList=vacancy.getCandidateList();

    }

    public List<Requirement> getRequirementList() {
        return requirementList;
    }

    public void setRequirementList(List<Requirement> requirementList) {
        this.requirementList = requirementList;
    }

    public List<Candidate> getCandidateList() {
        return candidateList;
    }

    public void setCandidateList(List<Candidate> candidateList) {
        this.candidateList = candidateList;
    }
}

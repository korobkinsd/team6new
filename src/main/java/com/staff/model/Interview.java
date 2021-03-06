package com.staff.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "interview", schema = "team6")
public class Interview {
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "vacancy_id")
    private Long vacancy_id;
    @Column(name = "candidate_id")
    private Long candidate_id;
    @Column(name = "plan_date")
    private Date plan_date;

    @Column(name = "fact_date")
    private Date fact_date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name="VACANCY_ID")
    private Vacancy vacancy;


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }


    public Long getVacancyId() {
        return vacancy_id;
    }
    public void setVacancyId(Long vacancy_id) {
        this.vacancy_id = vacancy_id;
    }


    public Long getCandidateId() {
        return candidate_id;
    }
    public void setCandidateId(Long candidate_id) {
        this.candidate_id = candidate_id;
    }


    public Date getPlanDate() {
        return plan_date;
    }
    public void setPlanDate(Date plan_date) {
        this.plan_date = plan_date;
    }


    public Date getFactDate() {
        return fact_date;
    }
    public void setFactDate(Date fact_date) {
        this.fact_date = fact_date;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Interview that = (Interview) o;
        return id == that.id &&
                Objects.equals(vacancy_id, that.vacancy_id) &&
                Objects.equals(candidate_id, that.candidate_id) &&
                Objects.equals(plan_date, that.plan_date) &&
                Objects.equals(fact_date, that.fact_date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, vacancy_id, candidate_id, plan_date, fact_date);
    }

    @Override
    public String toString() {
        return "Interview [id=" + id + ", candidate_id=" + candidate_id + ", vacancy_id=" + vacancy_id + ", plan_date=" + plan_date
                + ", fact_date=" + fact_date;
    }


    public Vacancy getVacancy() {
        return vacancy;
    }

    public void setVacancy(Vacancy vacancy) {
        this.vacancy = vacancy;
    }
}

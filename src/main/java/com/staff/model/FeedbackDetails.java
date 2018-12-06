package com.staff.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "feedback_details", schema = "team6")
public class FeedbackDetails {


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message="is required")
    @Column(name = "feedback_text")
    @Size(min=10, max=4000, message="name must be between 10 and 4000 characters long")
    private String feedback_text;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FeedbackDetails that = (FeedbackDetails) o;
        return id == that.id &&
                Objects.equals(feedback_text, that.feedback_text);
                //&& Objects.equals(userState, that.userState);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, feedback_text);
    }

    /*@OneToMany(mappedBy = "userByIdInterviewer")
    public Collection<Interviewfeedback> getInterviewfeedbacksById() {
        return interviewfeedbacksById;
    }

    public void setInterviewfeedbacksById(Collection<Interviewfeedback> interviewfeedbacksById) {
        this.interviewfeedbacksById = interviewfeedbacksById;
    }

    @OneToMany(mappedBy = "userByIdUser")
    public Collection<Userroles> getUserrolesById() {
        return userrolesById;
    }

    public void setUserrolesById(Collection<Userroles> userrolesById) {
        this.userrolesById = userrolesById;
    }

    @OneToMany(mappedBy = "userByIdDeveloper")
    public Collection<Vacancy> getVacanciesById() {
        return vacanciesById;
    }

    public void setVacanciesById(Collection<Vacancy> vacanciesById) {
        this.vacanciesById = vacanciesById;
    }*/
}

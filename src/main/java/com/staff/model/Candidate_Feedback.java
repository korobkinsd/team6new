package com.staff.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "candidate_feedback", schema = "team6")
public class Candidate_Feedback {

    public enum FeedbackState {
            YES( "Да" )
        ,   NO( "Нет" );

        private final String description;

        FeedbackState(final String description) {
            this.description = description;
        }

        public static FeedbackState getByString(String value) {
            FeedbackState[] types = FeedbackState.values();
            for(FeedbackState c : types) {
                if (value.equals(c.getDescription())) {
                    return c;
                }
            }
            throw new UnsupportedOperationException( "Feedback value " + value + " is not supported!" );
        }

        public String getDescription() { return description; }
    }

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
        Candidate_Feedback that = (Candidate_Feedback) o;
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

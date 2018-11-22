package com.staff.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user", schema = "team6")
public class User {
    private Long id;
    private String email;
    private String password;
    private String name;
    private String surname;

    private String userState;
    /*private Collection<Interviewfeedback> interviewfeedbacksById;
    private Collection<Userroles> userrolesById;
    private Collection<Vacancy> vacanciesById;*/
   private List<Vacancy> vacancies;
    private List<Role> roles;


    @Transient

    private List<String> listUserStatus;

    @Id
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "surname")
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Column(name = "userState")
    public String getUserState() {
        return userState;
    }

    public void setUserState(String userState) {
        this.userState = userState;
    }

    @Transient
    public List<String> getListUserStatus() {
        return listUserStatus;
    }

    @Transient
    public void setListUserStatus(List<String> listUserStatus) {
        this.listUserStatus = listUserStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User that = (User) o;
        return id == that.id &&
                Objects.equals(email, that.email) &&
                Objects.equals(password, that.password) &&
                Objects.equals(name, that.name) &&
                Objects.equals(surname, that.surname);
                //&& Objects.equals(userState, that.userState);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, email, password, name, surname);
    }

    @ManyToMany(fetch = FetchType.EAGER/*LAZY,
            cascade=CascadeType.ALL*/)
    @JoinTable(name = "user_roles",
            joinColumns = { @JoinColumn(name = "USER_ID") },
            inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
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
*/
    @OneToMany(mappedBy = "developer")
    @JsonIgnore
    public List<Vacancy> getVacancies() {
        return this.vacancies;
    }

    public void setVacancies(List<Vacancy> vacancies) {
        this.vacancies = vacancies;
    }
}

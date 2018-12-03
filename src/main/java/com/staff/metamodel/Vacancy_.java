package com.staff.metamodel;

import com.staff.model.Vacancy;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;


@StaticMetamodel(Vacancy.class)
public class Vacancy_ {
    public static volatile SingularAttribute<Vacancy, Long> id;
    public static volatile SingularAttribute<Vacancy, String> position;
    public static volatile SingularAttribute<Vacancy, Double> experienceYearsRequire;
    public static volatile SingularAttribute<Vacancy, String> state;
    public static volatile SingularAttribute<Vacancy, Double> salaryFrom;
    public static volatile SingularAttribute<Vacancy, Double> salaryTo;
    public static volatile SingularAttribute<Vacancy, Long> idDeveloper;

    public static final String ID = "id";
    public static final String POSITION = "position";
    public static final String EXPERIENCE_YEARS_REQUIRE = "experience_Years_Require";
    public static final String STATE = "vacancy_state";
    public static final String SALARY_FROM = "salary_From";
    public static final String SALARY_TO = "salary_To";
    public static final String ID_DEVELOPER = "developer_id";
}



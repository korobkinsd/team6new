package com.staff.metamodel;

import com.staff.model.Interview;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Date;

@StaticMetamodel(Interview.class)
public class Interview_ {
    public static volatile SingularAttribute<Interview, Long> id;
    public static volatile SingularAttribute<Interview, Long> vacancyid;
    public static volatile SingularAttribute<Interview, Long> candidateid;
    public static volatile SingularAttribute<Interview, Date> plandate;
    public static volatile SingularAttribute<Interview, Date> factdate;

    public static final String ID = "id";
    public static final String VACANCY_ID = "vacancyid";
    public static final String CANDIDATE_ID = "candidateid";
    public static final String PLAN_DATE = "plandate";
    public static final String FACT_DATE = "factdate";
}

package com.staff.metamodel;

import com.staff.model.Candidate;
import com.staff.model.Candidate.CandidateState;
import java.util.Date;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;


@StaticMetamodel(Candidate.class)
public class Candidate_ {

	public static volatile SingularAttribute<Candidate, Date> birthday;
	public static volatile SingularAttribute<Candidate, String> surname;
	public static volatile SingularAttribute<Candidate, String> name;
	public static volatile SingularAttribute<Candidate, CandidateState> candidateState;
	public static volatile SingularAttribute<Candidate, Long> id;
	public static volatile SingularAttribute<Candidate, Double> salary;

	public static final String BIRTHDAY = "birthday";
	public static final String SURNAME = "surname";
	public static final String NAME = "name";
	public static final String CANDIDATE_STATE = "candidateState";
	public static final String ID = "id";
	public static final String SALARY = "salary";

}


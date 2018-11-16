package com.staff.metamodel;


import com.staff.model.Skill;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Skill.class)
public class Skill_ {

    public static volatile SingularAttribute<Skill, String> name;

    public static final String NAME = "name";

}
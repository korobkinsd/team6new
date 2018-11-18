package com.staff.metamodel;


import com.staff.model.Responsibility;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Responsibility.class)
public class Responsibility_ {

    public static volatile SingularAttribute<Responsibility, String> name;

    public static final String NAME = "name";

}
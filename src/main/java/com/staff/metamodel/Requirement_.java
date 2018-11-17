package com.staff.metamodel;


import com.staff.model.Requirement;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Requirement.class)
public class Requirement_ {

    public static volatile SingularAttribute<Requirement, String> name;

    public static final String NAME = "name";

}
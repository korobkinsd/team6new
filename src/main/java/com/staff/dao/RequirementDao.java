package com.staff.dao;

import com.staff.model.Requirement;
import com.staff.util.filtering.TrashUtil;

import java.util.List;

public interface RequirementDao {

   String save(Requirement requirement);

   Requirement get(String name);

   List<Requirement> list(TrashUtil trashUtil);

   void update(String name, Requirement requirement);

   void delete(String name);

}

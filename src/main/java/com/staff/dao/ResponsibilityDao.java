package com.staff.dao;

import com.staff.model.Responsibility;
import com.staff.util.filtering.TrashUtil;

import java.util.List;

public interface ResponsibilityDao {

   String save(Responsibility responsibility);

   Responsibility get(String name);

   List<Responsibility> list(TrashUtil trashUtil);

   void update(String name, Responsibility responsibility);

   void delete(String name);

}

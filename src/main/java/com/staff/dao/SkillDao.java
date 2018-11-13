package com.staff.dao;

import com.staff.model.Skill;
import com.staff.util.filtering.TrashUtil;

import java.util.List;

public interface SkillDao {

   String save(Skill skill);

   Skill get(String name);

   List<Skill> list(TrashUtil trashUtil);

   void update(String name, Skill skill);

   void delete(String name);

}

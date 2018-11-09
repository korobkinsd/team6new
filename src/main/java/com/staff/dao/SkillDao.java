package com.staff.dao;

import com.staff.model.Skill;

import java.util.List;

public interface SkillDao {

   long save(Skill skill);

   Skill get(long id);

   List<Skill> list();

   void update(long id, Skill skill);

   void delete(long id);

}

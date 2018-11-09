package com.staff.service;

import com.staff.model.Skill;

import java.util.List;

public interface SkillService {

   long save(Skill skill);
   Skill get(long id);
   List<Skill> list();
   void update(long id, Skill skill);
   void delete(long id);
}

package com.staff.service;

import com.staff.dao.SkillDao;
import com.staff.model.Skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class SkillServiceImp implements SkillService {

   @Autowired
   private SkillDao skillDao;

   @Transactional
   @Override
   public long save(Skill skill) {
      return skillDao.save(skill);
   }

   @Override
   public Skill get(long id) {
      return skillDao.get(id);
   }

   @Override
   public List<Skill> list() {
      return skillDao.list();
   }

   @Transactional
   @Override
   public void update(long id, Skill skill) {
      skillDao.update(id, skill);
   }

   @Transactional
   @Override
   public void delete(long id) {
      skillDao.delete(id);
   }

}

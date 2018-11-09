package com.staff.dao;

import com.staff.model.Skill;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class SkillDaoImp implements SkillDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public long save(Skill skill) {
      sessionFactory.getCurrentSession().save(skill);
      return skill.getId();
   }

   @Override
   public Skill get(long id) {
      return sessionFactory.getCurrentSession().get(Skill.class, id);
   }

   @Override
   public List<Skill> list() {
      Session session = sessionFactory.getCurrentSession();
      CriteriaBuilder cb = session.getCriteriaBuilder();
      CriteriaQuery<Skill> cq = cb.createQuery(Skill.class);
      Root<Skill> root = cq.from(Skill.class);
      cq.select(root);
      Query<Skill> query = session.createQuery(cq);
      return query.getResultList();
   }

   @Override
   public void update(long id, Skill skill) {
      Session session = sessionFactory.getCurrentSession();
      Skill skill2 = session.byId(Skill.class).load(id);
      skill2.setName(skill.getName());
      session.flush();
   }

   @Override
   public void delete(long id) {
      Session session = sessionFactory.getCurrentSession();
      Skill skill = session.byId(Skill.class).load(id);
      session.delete(skill);
   }

}

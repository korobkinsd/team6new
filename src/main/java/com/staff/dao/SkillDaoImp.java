package com.staff.dao;

import com.staff.model.Skill;
import com.staff.metamodel.Skill_;
import com.staff.util.filtering.TrashUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class SkillDaoImp implements SkillDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   @Transactional
   public String save(Skill skill) {
      sessionFactory.getCurrentSession().save(skill);
      return skill.getName();
   }

   @Override
   public Skill get(String name) {
      return sessionFactory.getCurrentSession().get(Skill.class, name);
   }

   @Override
   public List<Skill> list(TrashUtil trashUtil) {
      Session session = sessionFactory.getCurrentSession();
      CriteriaBuilder cb = session.getCriteriaBuilder();
      CriteriaQuery<Skill> cq = cb.createQuery(Skill.class);
      Root<Skill> root = cq.from(Skill.class);
      cq.select(root);

      List<Predicate> predicates = new ArrayList<>();

      String name = trashUtil.name;
      if (name!=null && !name.equals("") ) {
         predicates.add( cb.like(cb.lower(root.<String>get(Skill_.NAME)),"%"+name.toLowerCase()+"%"));
      }

      cq.where(
              cb.and(
                      predicates.toArray(new Predicate[0])

              ));

      if (trashUtil.order.toUpperCase().equals("ASC")){
         cq.orderBy(cb.asc(root.get(trashUtil.sortColumnName)));
      }else{
         cq.orderBy(cb.desc(root.get(trashUtil.sortColumnName)));
      }

      Query<Skill> query = session.createQuery(cq);
      query.setFirstResult((trashUtil.page-1)*trashUtil.pagesize+1);
      query.setMaxResults(trashUtil.pagesize);

      return query.getResultList();
   }

   @Override
   @Transactional
   public void update(String name, Skill skill) {
      Session session = sessionFactory.getCurrentSession();
      Skill updateSkill = session.byId(Skill.class).load(name);
      updateSkill.setName(skill.getName());
      session.update(updateSkill);
   }

   @Override
   @Transactional
   public void delete(String name) {
      Session session = sessionFactory.getCurrentSession();
      Skill skill = session.byId(Skill.class).load(name);
      session.delete(skill);
   }

}

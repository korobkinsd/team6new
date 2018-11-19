package com.staff.dao;

import com.staff.metamodel.Requirement_;
import com.staff.model.Requirement;
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
public class RequirementDaoImp implements RequirementDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   @Transactional
   public String save(Requirement requirement) {
      sessionFactory.getCurrentSession().save(requirement);
      return requirement.getName();
   }

   @Override
   public Requirement get(String name) {
      return sessionFactory.getCurrentSession().get(Requirement.class, name);
   }

   @Override
   public List<Requirement> list(TrashUtil trashUtil) {
      Session session = sessionFactory.getCurrentSession();
      CriteriaBuilder cb = session.getCriteriaBuilder();
      CriteriaQuery<Requirement> cq = cb.createQuery(Requirement.class);
      Root<Requirement> root = cq.from(Requirement.class);
      cq.select(root);

      List<Predicate> predicates = new ArrayList<>();

      String name = trashUtil.name;
      if (name!=null && !name.equals("") ) {
         predicates.add( cb.like(cb.lower(root.<String>get(Requirement_.NAME)),"%"+name.toLowerCase()+"%"));
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

      Query<Requirement> query = session.createQuery(cq);
      query.setFirstResult((trashUtil.page-1)*trashUtil.pagesize+1);
      query.setMaxResults(trashUtil.pagesize);

      return query.getResultList();
   }

   @Override
   @Transactional
   public void update(String name, Requirement requirement) {
      Session session = sessionFactory.getCurrentSession();
      Requirement updateRequirement = session.byId(Requirement.class).load(name);
      updateRequirement.setName(requirement.getName());
      session.update(updateRequirement);
   }

   @Override
   @Transactional
   public void delete(String name) {
      Session session = sessionFactory.getCurrentSession();
      Requirement requirement = session.byId(Requirement.class).load(name);
      session.delete(requirement);
   }

}

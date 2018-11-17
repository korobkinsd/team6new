package com.staff.dao;

import com.staff.metamodel.Responsibility_;
import com.staff.model.Responsibility;
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
public class ResponsibilityDaoImp implements ResponsibilityDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   @Transactional
   public String save(Responsibility responsibility) {
      sessionFactory.getCurrentSession().save(responsibility);
      return responsibility.getName();
   }

   @Override
   public Responsibility get(String name) {
      return sessionFactory.getCurrentSession().get(Responsibility.class, name);
   }

   @Override
   public List<Responsibility> list(TrashUtil trashUtil) {
      Session session = sessionFactory.getCurrentSession();
      CriteriaBuilder cb = session.getCriteriaBuilder();
      CriteriaQuery<Responsibility> cq = cb.createQuery(Responsibility.class);
      Root<Responsibility> root = cq.from(Responsibility.class);
      cq.select(root);

      List<Predicate> predicates = new ArrayList<>();

      String name = trashUtil.name;
      if (name!=null && !name.equals("") ) {
         predicates.add( cb.like(cb.lower(root.<String>get(Responsibility_.NAME)),"%"+name.toLowerCase()+"%"));
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

      Query<Responsibility> query = session.createQuery(cq);
      query.setFirstResult((trashUtil.page-1)*trashUtil.pagesize+1);
      query.setMaxResults(trashUtil.pagesize);

      return query.getResultList();
   }

   @Override
   @Transactional
   public void update(String name, Responsibility responsibility) {
      Session session = sessionFactory.getCurrentSession();
      Responsibility updateResponsibility = session.byId(Responsibility.class).load(name);
      updateResponsibility.setName(responsibility.getName());
      session.update(updateResponsibility);
   }

   @Override
   @Transactional
   public void delete(String name) {
      Session session = sessionFactory.getCurrentSession();
      Responsibility responsibility = session.byId(Responsibility.class).load(name);
      session.delete(responsibility);
   }

}

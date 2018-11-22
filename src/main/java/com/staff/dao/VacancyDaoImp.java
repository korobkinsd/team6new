package com.staff.dao;

import com.staff.metamodel.Vacancy_;
import com.staff.model.Vacancy;
import com.staff.util.filtering.SortPagining;
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
@Transactional
@Repository
public class VacancyDaoImp implements VacancyDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public long save(Vacancy vacancy) {
      sessionFactory.getCurrentSession().save(vacancy);
      return vacancy.getId();
   }

   @Override
   public Vacancy get(long id) {
      return sessionFactory.getCurrentSession().get(Vacancy.class, id);
   }

   @Override
   public List<Vacancy> list( SortPagining sortPagining) {

      Session session = sessionFactory.getCurrentSession();
      CriteriaBuilder cb = session.getCriteriaBuilder();
      CriteriaQuery<Vacancy> cq = cb.createQuery(Vacancy.class);
      Root<Vacancy> root = cq.from(Vacancy.class);
      cq.select(root);
if (sortPagining.vacancy!=null) {
    List<Predicate> predicates = new ArrayList<>();
    if (sortPagining.vacancy.getId() != null & sortPagining.vacancy.getId() != 0) {
            predicates.add(cb.equal(root.get(Vacancy_.ID), sortPagining.vacancy.getId()));

    }
  /*  if (sortPagining.vacancy.getIdDeveloper() != null & sortPagining.vacancy.getIdDeveloper() != 0) {
            predicates.add(cb.equal(root.get(Vacancy_.ID_DEVELOPER), sortPagining.vacancy.getIdDeveloper()));

    }*/
    if (sortPagining.vacancy.getExperienceYearsRequire() != 0.0) {
        predicates.add(cb.equal(root.get(Vacancy_.EXPERIENCE_YEARS_REQUIRE), sortPagining.vacancy.getExperienceYearsRequire()));
    }
    if (sortPagining.vacancy.getSalaryFrom() != 0.0) {
        predicates.add(cb.equal(root.get(Vacancy_.SALARY_FROM), sortPagining.vacancy.getSalaryFrom()));
    }
    if (sortPagining.vacancy.getSalaryTo() != 0.0) {
        predicates.add(cb.equal(root.get(Vacancy_.SALARY_TO), sortPagining.vacancy.getSalaryTo()));
    }
    if (!sortPagining.vacancy.getPosition().equals("") & sortPagining.vacancy.getPosition() != null) {
        predicates.add(cb.like(
                cb.lower(root.<String>get(Vacancy_.POSITION)), "%" + sortPagining.vacancy.getPosition().toLowerCase() + "%"));
    }
    if (!sortPagining.vacancy.getState().equals("") & sortPagining.vacancy.getState() != null) {
        predicates.add(cb.equal(root.get(Vacancy_.STATE), sortPagining.vacancy.getState()));
    }

    cq.where(
            cb.and(
                    predicates.toArray(new Predicate[predicates.size()])

            ));

    if (sortPagining.order.toUpperCase().equals("ASC")){
        cq.orderBy(cb.asc(root.get(sortPagining.getSortColumnName())));
    }else{
        cq.orderBy(cb.desc(root.get(sortPagining.getSortColumnName())));
    }
}
      Query<Vacancy> query = session.createQuery(cq);
       query.setFirstResult((sortPagining.getPage()-1) * sortPagining.getPagesize());
       query.setMaxResults(sortPagining.getPagesize());



      return query.getResultList();
   }

   @Override
   public void update(long id, Vacancy vacancy) {
      Session session = sessionFactory.getCurrentSession();
      session.update(vacancy);
      session.flush();
   }

   @Override
   public void delete(long id) {
      Session session = sessionFactory.getCurrentSession();
      Vacancy vacancy = session.byId(Vacancy.class).load(id);
      session.delete(vacancy);
   }

}

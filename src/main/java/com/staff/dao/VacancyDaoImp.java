package com.staff.dao;

import com.staff.metamodel.Vacancy_;
import com.staff.model.Vacancy;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

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
   public List<Vacancy> list(Vacancy vacancy, int pageNumber, int  pageSize) {

      Session session = sessionFactory.getCurrentSession();
      CriteriaBuilder cb = session.getCriteriaBuilder();
      CriteriaQuery<Vacancy> cq = cb.createQuery(Vacancy.class);
      Root<Vacancy> root = cq.from(Vacancy.class);
      cq.select(root);
if (vacancy!=null) {
    List<Predicate> predicates = new ArrayList<>();
    if (vacancy.getId() != null) {
        if (vacancy.getId() != 0) {
            predicates.add(cb.equal(root.get(Vacancy_.ID), vacancy.getId()));
        }
    }
    if (vacancy.getIdDeveloper() != null) {
        if (vacancy.getIdDeveloper() != 0) {
            predicates.add(cb.equal(root.get(Vacancy_.ID_DEVELOPER), vacancy.getIdDeveloper()));
        }
    }
    if (vacancy.getExperienceYearsRequire() != 0.0) {
        predicates.add(cb.equal(root.get(Vacancy_.EXPERIENCE_YEARS_REQUIRE), vacancy.getExperienceYearsRequire()));
    }
    if (vacancy.getSalaryFrom() != 0.0) {
        predicates.add(cb.equal(root.get(Vacancy_.SALARY_FROM), vacancy.getSalaryFrom()));
    }
    if (vacancy.getSalaryTo() != 0.0) {
        predicates.add(cb.equal(root.get(Vacancy_.SALARY_TO), vacancy.getSalaryTo()));
    }
    if (!vacancy.getPosition().equals("") & vacancy.getPosition() != null) {
        predicates.add(cb.like(
                cb.lower(root.<String>get(Vacancy_.POSITION)), "%" + vacancy.getPosition().toLowerCase() + "%"));
    }
    if (!vacancy.getState().equals("") & vacancy.getState() != null) {
        predicates.add(cb.equal(root.get(Vacancy_.STATE), vacancy.getState()));
    }

    cq.where(
            cb.and(
                    predicates.toArray(new Predicate[predicates.size()])

            ));
}
      Query<Vacancy> query = session.createQuery(cq);
       query.setFirstResult((pageNumber-1) * pageSize);
       query.setMaxResults(pageSize);



      return query.getResultList();
   }

   @Override
   public void update(long id, Vacancy vacancy) {
      Session session = sessionFactory.getCurrentSession();
      Vacancy vacancy2 = session.byId(Vacancy.class).load(id);
      vacancy2.setPosition(vacancy.getPosition());
      session.flush();
   }

   @Override
   public void delete(long id) {
      Session session = sessionFactory.getCurrentSession();
      Vacancy vacancy = session.byId(Vacancy.class).load(id);
      session.delete(vacancy);
   }

}

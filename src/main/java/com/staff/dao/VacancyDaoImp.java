package com.staff.dao;

import com.staff.metamodel.Vacancy_;
import com.staff.model.Requirement;
import com.staff.model.User;
import com.staff.model.Vacancy;
import com.staff.modelDto.VacancyChangeDto;
import com.staff.modelDto.VacancyDto;
import com.staff.util.filtering.VacancyFilter;
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
   public long save(VacancyChangeDto vacancyChangeDto) {
       Session session = sessionFactory.getCurrentSession();

       Vacancy vacancy = new Vacancy();
       vacancy.setDeveloper(session.byId(User.class).load(vacancyChangeDto.getDeveloper_id()));
       vacancy.setPosition(vacancyChangeDto.getPosition());
       vacancy.setSalaryFrom(vacancyChangeDto.getSalaryFrom());
       vacancy.setSalaryTo(vacancyChangeDto.getSalaryTo());
       vacancy.setExperienceYearsRequire(vacancyChangeDto.getExperienceYearsRequire());
       vacancy.setState(vacancyChangeDto.getState());

      session.save(vacancy);
      return vacancy.getId();
   }

   @Override
   public VacancyDto get(long id)
   {
       Session session = sessionFactory.getCurrentSession();

       Vacancy vacancy=session.get(Vacancy.class, id);
       VacancyDto vacancyDto =new VacancyDto(vacancy);
      if (vacancyDto.getCandidateList().size()>1){
          id=2;
      }
       return  vacancyDto;
   }

   @Override
   public List<VacancyDto> list( VacancyFilter vacancyFilter) {

      Session session = sessionFactory.getCurrentSession();
      CriteriaBuilder cb = session.getCriteriaBuilder();
      CriteriaQuery<Vacancy> cq = cb.createQuery(Vacancy.class);
      Root<Vacancy> root = cq.from(Vacancy.class);
      cq.select(root);

if (vacancyFilter.vacancy!=null) {
    List<Predicate> predicates = new ArrayList<>();
    if (vacancyFilter.vacancy.getId() != null ) {
            predicates.add(cb.equal(root.get(Vacancy_.ID), vacancyFilter.vacancy.getId()));

    }

    if (vacancyFilter.vacancy.getExperienceYearsRequire() != 0.0) {
        predicates.add(cb.equal(root.get(Vacancy_.EXPERIENCE_YEARS_REQUIRE), vacancyFilter.vacancy.getExperienceYearsRequire()));
    }
    if (vacancyFilter.vacancy.getSalaryFrom() != 0.0) {
        predicates.add(cb.equal(root.get(Vacancy_.SALARY_FROM), vacancyFilter.vacancy.getSalaryFrom()));
    }
    if (vacancyFilter.vacancy.getSalaryTo() != 0.0) {
        predicates.add(cb.equal(root.get(Vacancy_.SALARY_TO), vacancyFilter.vacancy.getSalaryTo()));
    }
    if (!vacancyFilter.vacancy.getPosition().equals("") & vacancyFilter.vacancy.getPosition() != null) {
        predicates.add(cb.like(
                cb.lower(root.<String>get(Vacancy_.POSITION)), "%" + vacancyFilter.vacancy.getPosition().toLowerCase() + "%"));
    }
    if (!vacancyFilter.vacancy.getState().equals("") & vacancyFilter.vacancy.getState() != null) {
        predicates.add(cb.equal(root.get(Vacancy_.STATE), vacancyFilter.vacancy.getState()));
    }

    cq.where(
            cb.and(
                    predicates.toArray(new Predicate[predicates.size()])

            ));

    if (vacancyFilter.order.toUpperCase().equals("ASC")){
        cq.orderBy(cb.asc(root.get(vacancyFilter.getSortColumnName())));
    }else{
        cq.orderBy(cb.desc(root.get(vacancyFilter.getSortColumnName())));
    }
}
      Query<Vacancy> query = session.createQuery(cq);
       query.setFirstResult((vacancyFilter.getPage()-1) * vacancyFilter.getPagesize());
       query.setMaxResults(vacancyFilter.getPagesize());
       List<Vacancy>  vacancyList= query.getResultList();
       List<VacancyDto> vacancyDtoList = new ArrayList<VacancyDto>();

       for (Vacancy vacancy: vacancyList){
           VacancyDto vacancyDto =new VacancyDto(vacancy);
           if (vacancyDto.getCandidateList().size()>1){
               vacancyFilter.page=1;
           }
           vacancyDtoList.add(vacancyDto);
       }

      return vacancyDtoList;
   }

   @Override
   public void update(long id, VacancyChangeDto vacancy) {
     Session session = sessionFactory.getCurrentSession();
     Vacancy vacancyold =  session.byId(Vacancy.class).load(vacancy.getId());
       vacancyold.setDeveloper(session.byId(User.class).load(vacancy.getDeveloper_id()));
       vacancyold.setPosition(vacancy.getPosition());
       vacancyold.setSalaryFrom(vacancy.getSalaryFrom());
       vacancyold.setSalaryTo(vacancy.getSalaryTo());
       vacancyold.setExperienceYearsRequire(vacancy.getExperienceYearsRequire());
       vacancyold.setState(vacancy.getState());

     session.update(vacancyold);
     session.flush();
   }

   @Override
   public void delete(long id) {
      Session session = sessionFactory.getCurrentSession();
      Vacancy vacancy = session.byId(Vacancy.class).load(id);
      session.delete(vacancy);
   }

   public void addRequirement (long id,List<String>  requirements)
    {

        Session session = sessionFactory.getCurrentSession();
        Vacancy vacancyOld =  session.byId(Vacancy.class).load(id);
        List<Requirement> requirementList =vacancyOld.getRequirementList();
        for (String requirementName: requirements) {
            Requirement requirement = new Requirement();
            requirement.setName(requirementName);
            session.save(requirement);
            requirementList.add(requirement);
        }
        vacancyOld.setRequirementList(requirementList);
        session.update(vacancyOld);
        session.flush();
    }

}

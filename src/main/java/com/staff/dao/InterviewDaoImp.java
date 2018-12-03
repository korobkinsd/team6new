package com.staff.dao;


import com.staff.metamodel.Interview_;
import com.staff.model.Interview;
import com.staff.util.filtering.InterviewUtil;
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
import java.util.Date;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class InterviewDaoImp implements  InterviewDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public long save(Interview interview) {
        sessionFactory.getCurrentSession().save(interview);
        return interview.getId();
    }

    @Override
    public Interview get(long id) {
        return sessionFactory.getCurrentSession().get(Interview.class, id);
    }

    @Override
    public List<Interview> list(InterviewUtil interviewUtil) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Interview> cq = cb.createQuery(Interview.class);
        Root<Interview> root = cq.from(Interview.class);
        cq.select(root);

        List<Predicate> predicates = new ArrayList<>();

        Long candidate_id = interviewUtil.candidate_id;
        if (candidate_id!=null && !candidate_id.equals("") ) {
            predicates.add( cb.like(cb.lower(root.<String>get(Interview_.CANDIDATE_ID)),"%"+candidate_id.toString()+"%"));
        }
        Long vacancy_id = interviewUtil.vacancy_id;
        if (vacancy_id!=null && !vacancy_id.equals("") ) {
            predicates.add( cb.like(cb.lower(root.<String>get(Interview_.VACANCY_ID)),"%"+vacancy_id.toString()+"%"));
        }
        Date plan_date = interviewUtil.plan_date;
        if (plan_date!=null && !plan_date.equals("") ) {
            predicates.add( cb.like(cb.lower(root.<String>get(Interview_.PLAN_DATE)),"%"+plan_date.toString()+"%"));
        }
        Date fact_date = interviewUtil.fact_date;
        if (fact_date!=null && !fact_date.equals("") ) {
            predicates.add( cb.like(cb.lower(root.<String>get(Interview_.FACT_DATE)),"%"+fact_date.toString()+"%"));
        }

        cq.where(
                cb.and(
                        predicates.toArray(new Predicate[predicates.size()])

                ));

        if (interviewUtil.order.toUpperCase().equals("ASC")){
            cq.orderBy(cb.asc(root.get(interviewUtil.sortColumnName)));
        }else{
            cq.orderBy(cb.desc(root.get(interviewUtil.sortColumnName)));
        }

        Query<Interview> query = session.createQuery(cq);
        query.setFirstResult((interviewUtil.page-1)*interviewUtil.pagesize+1);
        query.setMaxResults(interviewUtil.pagesize);

        return query.getResultList();
    }

    @Override
    @Transactional
    public void update(long id, Interview interview) {
        Session session = sessionFactory.getCurrentSession();
        Interview updateInterview = session.byId(Interview.class).load(id);
        updateInterview.setVacancyId(interview.getVacancyId());
        updateInterview.setCandidateId(interview.getCandidateId());
        updateInterview.setPlanDate(interview.getPlanDate());
        updateInterview.setFactDate(interview.getFactDate());
        session.update(updateInterview);
    }

    @Override
    @Transactional
    public void delete(long id) {
        Session session = sessionFactory.getCurrentSession();
        Interview interview = session.byId(Interview.class).load(id);
        session.delete(interview);
    }
}

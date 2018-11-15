package com.staff.dao;

import com.staff.model.Candidate;
import com.staff.util.filtering.CandidateFilter;
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
public class CandidateDaoImpl implements CandidateDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public long save(Candidate candidate) {
        sessionFactory.getCurrentSession().save(candidate);
        return candidate.getId();
    }

    @Override
    public Candidate get(long id) {
        return sessionFactory.getCurrentSession().get(Candidate.class, id);
    }

    @Override
    public List<Candidate> list(CandidateFilter filter) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Candidate> cq = cb.createQuery(Candidate.class);
        Root<Candidate> root = cq.from(Candidate.class);
        cq.select(root);
        List<Predicate> predicates = new ArrayList<>();
        String filterName = filter.getName();
        if (filterName!=null && !"".equals(filterName) ) {
            predicates.add( cb.like( cb.lower( root.<String>get( "NAME" )),"%"+filterName.toLowerCase()+"%" ) );
        }
        cq.where(
                cb.and(
                        predicates.toArray(new Predicate[0])

                ));
        if (filter.getOrder().toUpperCase().equals("ASC")) {
            cq.orderBy(cb.asc(root.get(filter.getSortColumnName())));
        } else {
            cq.orderBy(cb.desc(root.get(filter.getSortColumnName())));
        }
        Query<Candidate> query = session.createQuery(cq);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void update(long id, Candidate candidate) {
        Session session = sessionFactory.getCurrentSession();
        Candidate newObj = session.byId(Candidate.class).load(id);
        newObj.setName(candidate.getName());
        session.flush();
    }

    @Override
    @Transactional
    public void delete(long id) {
        Session session = sessionFactory.getCurrentSession();
        Candidate delObj = session.byId(Candidate.class).load(id);
        session.delete(delObj);
    }

}

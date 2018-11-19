package com.staff.dao;

import com.staff.metamodel.Candidate_;
import com.staff.model.Candidate;
import com.staff.util.filtering.CandidateFilter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(CandidateDaoImpl.class);

    @Override
    @Transactional
    public Long save(Candidate candidate) {
        sessionFactory.getCurrentSession().save(candidate);
        return candidate.getId();
    }

    @Override
    public Candidate get(Long id) {
        return sessionFactory.getCurrentSession().get(Candidate.class, id);
    }

    @Override
    public List<Candidate> list(CandidateFilter filter) {
        logger.debug("CandidateDaoImpl.list() start filter: " + filter.toString());
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Candidate> cq = cb.createQuery(Candidate.class);
        Root<Candidate> root = cq.from(Candidate.class);
        cq.select(root);
        List<Predicate> predicates = new ArrayList<>();

        String filterName = filter.getName();
        logger.debug("CandidateDaoImpl.list() filterName: " + filterName);
        if (filterName!=null && !"".equals(filterName) ) {
            predicates.add( cb.like( cb.lower( root.<String>get( Candidate_.NAME )),"%"+filterName.toLowerCase()+"%" ) );
        }
        cq.where(
                cb.and(
                        predicates.toArray(new Predicate[predicates.size()])

                ));
        if (filter.getOrder().toUpperCase().equals("ASC")) {
            cq.orderBy(cb.asc(root.get(filter.getSortColumnName())));
        } else {
            cq.orderBy(cb.desc(root.get(filter.getSortColumnName())));
        }
        Query<Candidate> query = session.createQuery(cq);
        //query.setFirstResult((filter.getPage()-1)*filter.getPagesize()+1);
        query.setFirstResult((filter.getPage()-1) * filter.getPagesize());
        query.setMaxResults(filter.getPagesize());
        logger.debug("CandidateDaoImpl.list() done");
        return query.getResultList();
    }

    @Override
    @Transactional
    public void update(Long id, Candidate candidate) {
        Session session = sessionFactory.getCurrentSession();
        Candidate newObj = session.byId(Candidate.class).load(id);
        newObj.setName(candidate.getName());
        session.flush();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Candidate delObj = session.byId(Candidate.class).load(id);
        session.delete(delObj);
    }

}

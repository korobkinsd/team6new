package com.staff.dao;

import com.staff.model.Candidate;
import com.staff.model.ContactDetails;
import com.staff.util.filtering.CandidateFilter;
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
public class ContactDetailsDaoImpl implements ContactDetailsDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(ContactDetails contactDetails) {
        sessionFactory.getCurrentSession().save(contactDetails);
    }

    @Override
    public ContactDetails get(ContactDetails contactDetails) {
        return sessionFactory.getCurrentSession().get(ContactDetails.class, contactDetails);
    }

    @Override
    public List<ContactDetails> list(long idCandidate) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<ContactDetails> cq = cb.createQuery(ContactDetails.class);
        Root<ContactDetails> root = cq.from(ContactDetails.class);
        cq.select(root);
        Query<Candidate> query = session.createQuery(cq);
        return query.getResultList();
    }

    @Override
    public void update(long id, Candidate candidate) {
        Session session = sessionFactory.getCurrentSession();
        Candidate newObj = session.byId(Candidate.class).load(id);
        newObj.setName(candidate.getName());
        session.flush();
    }

    @Override
    public void delete(long id) {
        Session session = sessionFactory.getCurrentSession();
        Candidate delObj = session.byId(Candidate.class).load(id);
        session.delete(delObj);
    }

}

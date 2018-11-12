package com.staff.dao;

import com.staff.metamodel.User_;
import com.staff.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public long save(User user) {
        sessionFactory.getCurrentSession().save(user);
        return user.getId();
    }

    @Override
    public User get(long id) {
        return sessionFactory.getCurrentSession().get(User.class, id);
    }

    @Override
    public List<User> list(User user) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        cq.select(root);

        List<Predicate> predicates = new ArrayList<>();

        String email = user.getEmail();
        if (email!=null && !email.equals("") ) {
            predicates.add( cb.like(cb.lower(root.<String>get(User_.EMAIL)),"%"+email.toLowerCase()+"%"));
        }
        String name = user.getName();
        if (name!=null && !name.equals("") ) {
            predicates.add( cb.like(cb.lower(root.<String>get(User_.NAME)),"%"+email.toLowerCase()+"%"));
        }
        String surname = user.getSurname();
        if (surname!=null && !surname.equals("") ) {
            predicates.add( cb.like(cb.lower(root.<String>get(User_.SURNAME)),"%"+email.toLowerCase()+"%"));
        }

        List<String> listUserStatus = user.getListUserStatus();
        if (listUserStatus!=null && listUserStatus.size() > 0) {
            predicates.add(root.get(User_.USER_STATE).in(listUserStatus));
        }

        cq.where(
                cb.and(
                        predicates.toArray(new Predicate[predicates.size()])

                ));

        Query<User> query = session.createQuery(cq);

        return query.getResultList();
    }

    @Override
    @Transactional
    public void update(long id, User user) {
        Session session = sessionFactory.getCurrentSession();
        User updateUser = session.byId(User.class).load(id);
        updateUser.setEmail(user.getEmail());
        updateUser.setName(user.getName());
        updateUser.setSurname(user.getSurname());
        session.update(updateUser);
    }

    @Override
    @Transactional
    public void delete(long id) {
        Session session = sessionFactory.getCurrentSession();
        User user = session.byId(User.class).load(id);
        session.delete(user);
    }
}

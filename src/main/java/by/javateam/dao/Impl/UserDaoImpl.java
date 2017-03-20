package by.javateam.dao.Impl;

import by.javateam.dao.UserDao;
import by.javateam.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The class implements methods for access to MySQL database for User.
 */
@Service
@SuppressWarnings("unchecked")
public class UserDaoImpl implements UserDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public UserDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<User> loadAllWithOffsetAndLimit(Integer offset, Integer limit) {
        Query query = sessionFactory.getCurrentSession().createQuery("from User u");
        if (offset != null && offset > 0) {
            query.setFirstResult(offset);
        }
        if (limit != null && limit > 0) {
            query.setMaxResults(limit);
        }
        return query.list();
    }

    @Override
    public void delete(int id) {
        User user = sessionFactory.getCurrentSession().load(
                User.class, id);
        if (null != user) {
            sessionFactory.getCurrentSession().delete(user);
        }
    }

    @Override
    public void save(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    public User loadAllUsersForId(int id) {
        return (User) sessionFactory.getCurrentSession().createQuery("from User u where id = :id").setParameter("id", id).uniqueResult();
    }

    @Override
    public User update(User user) {
        return (User) sessionFactory.getCurrentSession().merge(user);
    }

    @Override
    public List<User> loadNames() {
        return sessionFactory.getCurrentSession().createQuery("select firstName , lastName from User").list();
    }

    @Override
    public List<User> loadAll() {
        return sessionFactory.getCurrentSession().createQuery("from User").list();
    }


    @Override
    public LocalDateTime getCreatedDate(int id) {
        return (LocalDateTime) sessionFactory.getCurrentSession().createQuery("select u.createdTimestamp from User u where id = :id").setParameter("id", id).uniqueResult();
    }
}
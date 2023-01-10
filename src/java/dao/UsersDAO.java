package dao;

import models.UserProfile;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class UsersDAO {

    private Session session;

    public UsersDAO(Session session) {
        this.session = session;
    }

    public List<UserProfile> getAllUsers() throws HibernateException {
        Criteria criteria = session.createCriteria(UserProfile.class);
        return criteria.list();
    }

    public UserProfile getUserById(long id) throws HibernateException {
        return (UserProfile) session.get(UserProfile.class, id);
    }

    public UserProfile getUserByLogin(String login) throws HibernateException {
        Criteria criteria = session.createCriteria(UserProfile.class);
        return (UserProfile) criteria.add(Restrictions.eq("login", login)).uniqueResult();
    }

    public long getUserIdByLogin(String login) throws HibernateException {
        Criteria criteria = session.createCriteria(UserProfile.class);
        return ((UserProfile) criteria.add(Restrictions.eq("login", login)).uniqueResult()).getId();
    }

    public long insertUser(String login, String password) throws HibernateException {
        return (Long) session.save(new UserProfile(login, password));
    }
}
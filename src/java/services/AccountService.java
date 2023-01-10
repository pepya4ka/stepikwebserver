package services;

import dao.UsersDAO;
import models.UserProfile;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import services.exceptions.AccountServiceException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountService {
    private static final String HIBERNATE_SHOW_SQL = "true";
    private static final String HIBERNATE_HBM2DDL_AUTO = "create";

    private final SessionFactory sessionFactory;

    private final Map<String, UserProfile> sessionIdToProfile;

    public AccountService() {
        Configuration configuration = getH2Configuration();
        sessionFactory = createSessionFactory(configuration);

        sessionIdToProfile = new HashMap<>();
    }

    public List<UserProfile> getAllUsers() throws AccountServiceException {
        try {
            Session session = sessionFactory.openSession();
            UsersDAO dao = new UsersDAO(session);
            List<UserProfile> allUsers = dao.getAllUsers();
            session.close();
            return allUsers;
        } catch (HibernateException e) {
            throw new AccountServiceException(e);
        }
    }

    public UserProfile getUser(long id) throws AccountServiceException {
        try {
            Session session = sessionFactory.openSession();
            UsersDAO dao = new UsersDAO(session);
            UserProfile dataSet = dao.getUserById(id);
            session.close();
            return dataSet;
        } catch (HibernateException e) {
            throw new AccountServiceException(e);
        }
    }

    public UserProfile getUser(String login) throws AccountServiceException {
        try {
            Session session = sessionFactory.openSession();
            UsersDAO dao = new UsersDAO(session);
            UserProfile dataSet = dao.getUserByLogin(login);
            session.close();
            return dataSet;
        } catch (HibernateException e) {
            throw new AccountServiceException(e);
        }
    }

    public long addUser(String login, String password) throws AccountServiceException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            UsersDAO dao = new UsersDAO(session);
            long id = dao.insertUser(login, password);
            transaction.commit();
            session.close();
            return id;
        } catch (HibernateException e) {
            throw new AccountServiceException(e);
        }
    }

    public void addSession(String sessionId, UserProfile userProfile) {
        sessionIdToProfile.put(sessionId, userProfile);
    }

    public UserProfile getUserProfileBySessionId(String sessionId) {
        return sessionIdToProfile.get(sessionId);
    }

    public void deleteSession(String sessionId) {
        sessionIdToProfile.remove(sessionId);
    }

    private Configuration getH2Configuration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(UserProfile.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:./h2db");
        configuration.setProperty("hibernate.connection.username", "tully");
        configuration.setProperty("hibernate.connection.password", "tully");
        configuration.setProperty("hibernate.show_sql", HIBERNATE_SHOW_SQL);
        configuration.setProperty("hibernate.hbm2ddl.auto", HIBERNATE_HBM2DDL_AUTO);
        return configuration;
    }

    private SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }
}
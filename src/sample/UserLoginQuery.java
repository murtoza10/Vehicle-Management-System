package sample;

import edu.sust.db.User;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;

/**
 * Created by Murtoza10 on 11/3/2014.
 */
public class UserLoginQuery {
    private static SessionFactory factory;
    private static ServiceRegistry serviceRegistry;
    User user;
    public User FindUserByUsername(String username) {

        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(User.class);
            criteria.add(Restrictions.like("username", username));
//            criteria.uniqueResult();
            user = (User) criteria.uniqueResult();
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return user;

    }
}


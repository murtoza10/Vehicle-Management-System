package edu.sust.db;

import java.util.*;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;

/**
 * Created by Murtoza10 on 9/15/2014.
 */

public class UserMain {
    private static SessionFactory factory;
    private static ServiceRegistry serviceRegistry;

    public static void main(String[] args) {
        try{
            factory = new Configuration().configure().buildSessionFactory();
        }catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        UserMain ME = new UserMain();
        OwnerMain OM = new OwnerMain();

        java.sql.Date dob=new java.sql.Date(14,10,1);
        //OwnerDetails owner1 = OM.addOwner(2012, "sifat", dob, "paltan", "dhaka");
        //OwnerDetails owner2 = OM.addOwner(2014, "shanto", dob, "kakrail", "dhaka");
      /* Add few employee records in database */
        //Integer empID1 = ME.addUser("pavel","p2051",2);
        //String empID2 = ME.addUser("sifat","s2010",3,owner1);
        //String empID3 = ME.addUser("shanto","s2011",2,owner2);
        //Integer owner1 = OM.addOwner(2012, "sifat", dob, "paltan", "dhaka",empID1);

        //ME.retrievingdata();

      /* List down all the employees */
        //ME.listUser();

      /* Update employee's records */
        //ME.updateUser("sifat", 2);

      /* Delete an employee from the database */
        //ME.deleteUser("shanto");

      /* List down new list of the employees */
//        ME.listUser();
        ME.retrievingdata();
    }
    /* Method to CREATE an employee in the database */
    public User addUser(String username, String pass, int rank){
        try{
            factory = new Configuration().configure().buildSessionFactory();
        }catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        Session session = factory.openSession();
        Transaction tx = null;
        User user=null;
        Integer userID = null;
        try{
            tx = session.beginTransaction();
            user = new User(username,pass,rank);
            userID = (Integer) session.save(user);
//            Criteria criteria = session.createCriteria(User.class);
//            criteria.add(Restrictions.like("username", username));
////            criteria.uniqueResult();
//            user = (User) criteria.uniqueResult();

            tx.commit();
            System.out.println("user: "+ user);
            System.out.println("userID: "+userID);
            System.out.println("userID: "+user.getUserId());
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return user;
    }
    /* Method to  READ all the employees */
//    public void listUser( ){
//        Session session = factory.openSession();
//        Transaction tx = null;
//        try{
//            tx = session.beginTransaction();
//            List users = session.createQuery("FROM User ").list();
//            for (Iterator iterator =
//                         users.iterator(); iterator.hasNext();){
//                User user = (User) iterator.next();
//                System.out.print("username: " + user.getUsername());
//                System.out.print("Password: " + user.getPassword());
//                System.out.println("Rank: " + user.getRank());
//                OwnerDetails own = user.getOwner();
//                System.out.println("Owner Details ");
//                System.out.println("\tName: " +  own.getName());
//                System.out.println("\tNID: " + own.getNationalId());
//                System.out.println("\tDOB: " + own.getDob());
//                System.out.println("\tCurrent Address: " + own.getCurrAddrss());
//                System.out.println("\tPermanent Address: " + own.getPermAddrss());
//            }
//            tx.commit();
//        }catch (HibernateException e) {
//            if (tx!=null) tx.rollback();
//            e.printStackTrace();
//        }finally {
//            session.close();
//        }
//    }
    /* Method to UPDATE salary for an employee */
    public void updateUser(String uid, int rank ){
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
             User employee =
                    (User)session.get(User.class, uid);
            employee.setRank(rank);
            session.update(employee);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }
    /* Method to DELETE an employee from the records */
    public void deleteUser(int uid){
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            User employee =
                    (User)session.get(User.class, uid);
            session.delete(employee);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

    public void retrievingdata(){
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM User u WHERE u.username = :uname AND u.password = :pass").setParameter("uname","sifat").setParameter("pass","s2010");
            List<User> users =query.list();
            System.out.println(users.size());
            System.out.println(users.get(0).getUsername());
            for (Iterator iterator =
                         users.iterator(); iterator.hasNext();) {
                User user = (User) iterator.next();
                System.out.print("username: " + user.getUsername());
                System.out.print("Password: " + user.getUserId());
                System.out.println("Rank: " + user.getRank());
            }
//            List<Object[]> list = query.list();
//
//            for (Object object : list) {
//                Object[] li = (Object[])object;
//                for(Object liItem:li){
//
//                    if (liItem instanceof User) {
//                        System.out.println(((User)liItem).getUserId());
//                        System.out.println(((User)liItem).getRank());
//                    }
////                    else {
////                        System.out.println(((OwnerDetails)liItem).getCurrAddrss());
////                    }
//
//                }
//            }
            tx.commit();
        }
        catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

}

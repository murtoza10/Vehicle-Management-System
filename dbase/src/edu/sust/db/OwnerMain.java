package edu.sust.db;

import java.sql.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.*;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;

/**
 * Created by Murtoza10 on 9/15/2014.
 */
public class OwnerMain {
    private static SessionFactory factory;
    private static ServiceRegistry serviceRegistry;
    public static void main(String[] args) throws Exception {
        try{
            factory = new Configuration().configure().buildSessionFactory();
        }catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        OwnerMain ME = new OwnerMain();
//        UserMain UE = new UserMain();
//        //ME.retrievingdata();
//      /* Add few employee records in database */
//        String myDateStr = "10/06/2011";
//        String pattern = "MM/dd/yyyy";
//        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
//        Date dob = null;
//        dob = formatter.parse(myDateStr);
//        HashSet set1 = new HashSet();
//        set1.add(new Vehicle("201003E","20310D",dob,23,24,"red"));
//        set1.add(new Vehicle("201003F","20310E",dob,23,24,"black"));

//        int id= ME.lastid();
         //Integer empID1 = ME.addOwner(2013, "shanto", dob, "kakrail", "dhaka");
         //System.out.println(empID1);
//         String emp= UE.addEmployee("sifat","s2010",2,empID1);
//        String empID2 = ME.addEmployee("sifat","s2010",3,100);
//        String empID3 = ME.addEmployee("shanto","s2011",2,150);

      /* List down all the employees */
//        ME.listEmployees();

      /* Update employee's records */
//        ME.updateEmployee("sifat", 2);
//
//      /* Delete an employee from the database */
        //ME.deleteOwner(25);

//        ME.retrievingdata();
//      /* List down new list of the employees */
        //ME.listOwner();
        //ME.retrievingdata();
       // ME.listOwner();

    }


    /* Method to CREATE an employee in the database */
    public Integer addOwner(int nId,String name, Date dob,String currAddrss,String permAddrss,User user){
        try{
            factory = new Configuration().configure().buildSessionFactory();
        }catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        Session session = factory.openSession();
        Transaction tx = null;
        OwnerDetails owner=null;
        Integer ownerID =null;
        try{
            tx = session.beginTransaction();
            owner = new OwnerDetails(nId,name,dob,currAddrss,permAddrss,user);
            //owner.setVehicles(vehicle);
            ownerID=(Integer)session.save(owner);
            System.out.println(ownerID);
            tx.commit();
//            employeeID = employee.getOwnerId();
            System.out.println(ownerID);
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return ownerID;
    }
    /* Method to  READ all the employees */
    public void listOwner( ){
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            List employees = session.createQuery("FROM OwnerDetails ").list();
            for (Iterator iterator =
                         employees.iterator(); iterator.hasNext();){
                OwnerDetails employee = (OwnerDetails) iterator.next();
                System.out.print("uid: " + employee.getNationalId());
                System.out.print("Password: " + employee.getName());
                System.out.println("Rank: " + employee.getDob());
                System.out.println("Owner_id: " + employee.getOwnerId());
                Set vehicles = employee.getPhones();
                for (Iterator iterator2 =
                             vehicles.iterator(); iterator2.hasNext();){
                    Phone vehicle = (Phone) iterator2.next();
                    String pattern = "dd-MM-yyyy";
                    SimpleDateFormat formatter = new SimpleDateFormat(pattern);
                    //String mysqlDateString = formatter.format(now);
                    System.out.println("Phone num: " + vehicle.getPhone());

                }

            }
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }
    /* Method to UPDATE salary for an employee */
    public void updateOwner(String uid, int rank ){
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
    public void deleteOwner(int uid){
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            OwnerDetails employee =
                    (OwnerDetails)session.get(OwnerDetails.class, uid);


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
            Query query = session.createQuery("FROM OwnerDetails o INNER JOIN o.user u");


        List<Object[]> list = query.list();

        for (Object object : list) {
            Object[] li = (Object[])object;
            for(Object liItem:li){

                if (liItem instanceof User) {
                    System.out.println(((User)liItem).getUserId());
                }else if(liItem instanceof Phone){
                    System.out.println(((Phone)liItem).getPhone());
                }else{
                    System.out.println(((OwnerDetails)liItem).getCurrAddrss());
                }

            }
        }
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

package edu.sust.db;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;
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
        String myDateStr = "10/06/2011";
        String pattern = "MM/dd/yyyy";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        Date dob = null;
        dob = formatter.parse(myDateStr);
        UserMain UE = new UserMain();
        User user= UE.addUser("Sifat","S2010",1);
        System.out.println("userid: " + user);
        ME.addOwner(3234435,"Murtoza",dob,"sylhet","dhaka",user);
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
        //ME.retrievingdata3();


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
            System.out.println("owner: "+ owner);
            tx.commit();
//            employeeID = employee.getOwnerId();

        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return ownerID;
    }
    /* Method to  READ all the employees */
//    public void listOwner( ){
//        Session session = factory.openSession();
//        Transaction tx = null;
//        try{
//            tx = session.beginTransaction();
//            List employees = session.createQuery("FROM OwnerDetails ").list();
//            for (Iterator iterator =
//                         employees.iterator(); iterator.hasNext();){
//                OwnerDetails employee = (OwnerDetails) iterator.next();
//                System.out.print("uid: " + employee.getNationalId());
//                System.out.print("Password: " + employee.getName());
//                System.out.println("Rank: " + employee.getDob());
//                System.out.println("Owner_id: " + employee.getOwnerId());
//                Set vehicles = employee.getVehicles();
//                for (Iterator iterator2 =
//                             vehicles.iterator(); iterator2.hasNext();){
//                    Vehicle vehicle = (Vehicle) iterator2.next();
//                    String pattern = "dd-MM-yyyy";
//                    SimpleDateFormat formatter = new SimpleDateFormat(pattern);
//                    //String mysqlDateString = formatter.format(now);
//                    System.out.println("Chasis num: " + vehicle.getChasisNum());
//                    System.out.println("Engine num: " + vehicle.getEngineNum());
//                    System.out.println("Validity: " + formatter.format(vehicle.getValidTill()));
//                    System.out.println("Color: " + vehicle.getColor());
//                    System.out.println("build ID: " + vehicle.getBuildId());
//                    System.out.println("Reg ID: " + vehicle.getRegistrationId());
//                }
//
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

    public void retrievingdata1(){
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM Vehicle v INNER JOIN v.owner AS o WHERE o.ownerId = :ownid").setParameter("ownid",12);


            List<Object[]> list = query.list();
            int i=0;
            for (Object object : list) {
                Object[] li = (Object[])object;
                for(Object liItem:li){

                    if (liItem instanceof Vehicle) {

                            System.out.println(((Vehicle) liItem).getChasisNum());


                    }else{
                        System.out.println(((OwnerDetails)liItem).getName()+i);

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

    public void retrievingdata2(){
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM Vehicle v INNER JOIN v.reg r INNER JOIN r.owner o WHERE o.ownerId = :ownid").setParameter("ownid",12);


            List<Object[]> list = query.list();
            int i=0;
            for (Object object : list) {
                Object[] li = (Object[])object;
                for(Object liItem:li){

                    if (liItem instanceof Vehicle) {

                        System.out.println(((Vehicle) liItem).getChasisNum());
                    }else if(liItem instanceof VehicleRegInfo){
                        System.out.println(((VehicleRegInfo) liItem).getPlateNum());
                    }
                    else{
                        System.out.println(((OwnerDetails)liItem).getName());

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

    public void retrievingdata3(){
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM Vehicle v INNER JOIN v.owner o INNER JOIN v.reg r INNER JOIN v.build b WHERE o.ownerId = :ownid").setParameter("ownid",12);


            List<Object[]> list = query.list();
            int i=0;
            for (Object object : list) {
                Object[] li = (Object[])object;
                for(Object liItem:li){

                    if (liItem instanceof Vehicle) {

                        System.out.println(((Vehicle) liItem).getChasisNum());
                    }else if(liItem instanceof VehicleBuild){
                        System.out.println(((VehicleBuild) liItem).getModel());
                    }else if(liItem instanceof VehicleRegInfo){
                        System.out.println(((VehicleRegInfo) liItem).getRegistrationId());
                        System.out.println(((VehicleRegInfo) liItem).getPlateNum());
                    }
                    else{
                        System.out.println(((OwnerDetails)liItem).getName());

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

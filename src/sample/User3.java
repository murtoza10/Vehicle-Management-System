package sample;

import edu.sust.db.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.hibernate.*;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import org.hibernate.service.ServiceRegistry;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Created by Murtoza10 on 11/3/2014.
 */
public class User3 implements Initializable,
        ControlledScreen {

    ScreensController myController;
    private static SessionFactory factory;
    private static ServiceRegistry serviceRegistry;
    Stage primarystage;

    @FXML
    Label name,nid,ownerid,dob,curraddrss,permaddrss,phn,regnum,enginenum,chasisnum,platenum,color,manufacturer,model;

    @FXML
    ChoiceBox chcvehicle;

    final ObservableList<Integer> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
            chcvehicle.valueProperty().addListener(new ChangeListener<Integer>() {
                @Override public void changed(ObservableValue ov, Integer t, Integer t1) {
                    System.out.println(ov);
                    System.out.println(t);
                    System.out.println(t1);
                    SettingVehicle(t1);

                }
            });
            data.clear();
    }

    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }


    public void SettingLabelUser3(int uid){
        int own=0,i=1;
        String phon="",chasis="";
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
//            UserLogin UL=new UserLogin();

//            String hql = "from OwnerDetails where user = :userid";
//            List result = session.createQuery(hql)
//                    .setParameter("userid", UL.uid)
//                    .list();
//            System.out.println(UL.uid);
//            OwnerDetails owner =(OwnerDetails)result.get(0);
            Query query = session.createQuery("FROM OwnerDetails o "
                    + "INNER JOIN o.user AS u where u.userId = :userid").setParameter("userid", uid);
            List<Object[]> list = query.list();

            for (Object object : list) {
                Object[] li = (Object[])object;
                for(Object liItem:li){
                    if (liItem instanceof OwnerDetails) {
                        own= ((OwnerDetails)liItem).getOwnerId();
                    }
                }
            }

            OwnerDetails owner = (OwnerDetails)session.get(OwnerDetails.class, own);
            Set phones = owner.getPhones();
            for (Iterator iterator2 =
                         phones.iterator(); iterator2.hasNext();){
                Phone phone = (Phone) iterator2.next();
                System.out.println(phone.getPhone());
                if(i==0) {
                    phon=phone.getPhone();
                    i++;
                }
                else {
                    phon +=("," + phone.getPhone());
                }
            }
//            Criteria criteria = session.createCriteria(OwnerDetails.class);
//            criteria.add(Restrictions.like("user", UL.uid));
////            criteria.uniqueResult();
//            OwnerDetails owner = (OwnerDetails) criteria.uniqueResult();
            System.out.println(owner.getName());
            name.setText(owner.getName());
            nid.setText(Integer.toString(owner.getNationalId()));
            ownerid.setText(Integer.toString(owner.getOwnerId()));
            String pattern = "dd-MM-yyyy";
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            dob.setText(formatter.format(owner.getDob()));
            curraddrss.setText(owner.getCurrAddrss());
            permaddrss.setText(owner.getPermAddrss());
            phn.setText(phon);

//            VehicleQuery VQ=new VehicleQuery();
            Query query1 = session.createQuery("FROM VehicleRegInfo r INNER JOIN r.vehicle v INNER JOIN v.owner o INNER JOIN v.build b WHERE o.ownerId = :ownid").setParameter("ownid", own);

            List<Object[]> list1 = query1.list();



            for (Object object : list1) {
                Object[] li = (Object[])object;
                for(Object liItem:li){
                    if (liItem instanceof Vehicle) {
                        enginenum.setText(((Vehicle) liItem).getEngineNum());
                        chasisnum.setText(((Vehicle) liItem).getChasisNum());
                        color.setText(((Vehicle) liItem).getColor());
                    }else if(liItem instanceof VehicleBuild){
                        manufacturer.setText(((VehicleBuild) liItem).getManufacturer());
                        model.setText(((VehicleBuild) liItem).getModel());
                    }else if(liItem instanceof VehicleRegInfo){
                        data.add(((VehicleRegInfo) liItem).getRegistrationId());
                        regnum.setText(Integer.toString(((VehicleRegInfo) liItem).getRegistrationId()));
                        platenum.setText(Integer.toString(((VehicleRegInfo) liItem).getPlateNum()));
                    }

                }
            }
//            data.add(4);
            chcvehicle.setItems(data);

//           Vehicle vehicle= (Vehicle)session.get(Vehicle.class,chasis);
//            System.out.println(vehicle.getChasisNum());
////            System.out.println(vehicle.getBuild().getBuildId());
//            enginenum.setText(vehicle.getEngineNum());
//            chasisnum.setText(vehicle.getChasisNum());
//            color.setText(vehicle.getColor());

            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void SettingVehicle(int vehicleregid){
        int own=0,i=1;
        String phon=null,chasis=null;
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
//            UserLogin UL=new UserLogin();
//
////            String hql = "from OwnerDetails where user = :userid";
////            List result = session.createQuery(hql)
////                    .setParameter("userid", UL.uid)
////                    .list();
////            System.out.println(UL.uid);
////            OwnerDetails owner =(OwnerDetails)result.get(0);
//            Query query = session.createQuery("FROM OwnerDetails o "
//                    + "INNER JOIN o.user AS u where u.userId = :userid").setParameter("userid", UL.uid);
//            List<Object[]> list = query.list();
//
//            for (Object object : list) {
//                Object[] li = (Object[])object;
//                for(Object liItem:li){
//                    if (liItem instanceof OwnerDetails) {
//                        own= ((OwnerDetails)liItem).getOwnerId();
//                    }
//                }
//            }
//
//            OwnerDetails owner = (OwnerDetails)session.get(OwnerDetails.class, own);
//            Set phones = owner.getPhones();
//            for (Iterator iterator2 =
//                         phones.iterator(); iterator2.hasNext();){
//                Phone phone = (Phone) iterator2.next();
//                System.out.println(phone.getPhone());
//                if(i<2) {
//                    phon=(phone.getPhone() + ",");
//                    i++;
//                }
//                else {
//                    phon += phone.getPhone();
//                }
//            }
////            Criteria criteria = session.createCriteria(OwnerDetails.class);
////            criteria.add(Restrictions.like("user", UL.uid));
//////            criteria.uniqueResult();
////            OwnerDetails owner = (OwnerDetails) criteria.uniqueResult();
//            System.out.println(owner.getName());
//            name.setText(owner.getName());
//            nid.setText(Integer.toString(owner.getNationalId()));
//            ownerid.setText(Integer.toString(owner.getOwnerId()));
//            String pattern = "dd-MM-yyyy";
//            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
//            dob.setText(formatter.format(owner.getDob()));
//            curraddrss.setText(owner.getCurrAddrss());
//            permaddrss.setText(owner.getPermAddrss());
//            phn.setText(phon);

//            VehicleQuery VQ=new VehicleQuery();
            Query query1 = session.createQuery("FROM VehicleRegInfo r INNER JOIN r.vehicle v INNER JOIN v.owner o INNER JOIN v.build b WHERE r.registrationId = :regid").setParameter("regid", vehicleregid);

            List<Object[]> list1 = query1.list();

            for (Object object : list1) {
                Object[] li = (Object[])object;
                for(Object liItem:li){
                    if (liItem instanceof Vehicle) {
                        enginenum.setText(((Vehicle) liItem).getEngineNum());
                        chasisnum.setText(((Vehicle) liItem).getChasisNum());
                        color.setText(((Vehicle) liItem).getColor());
                    }else if(liItem instanceof VehicleBuild){
                        manufacturer.setText(((VehicleBuild) liItem).getManufacturer());
                        model.setText(((VehicleBuild) liItem).getModel());
                    }else if(liItem instanceof VehicleRegInfo){
                        regnum.setText(Integer.toString(((VehicleRegInfo) liItem).getRegistrationId()));
                        platenum.setText(Integer.toString(((VehicleRegInfo) liItem).getPlateNum()));
                    }

                }
            }
//           Vehicle vehicle= (Vehicle)session.get(Vehicle.class,chasis);
//            System.out.println(vehicle.getChasisNum());
////            System.out.println(vehicle.getBuild().getBuildId());
//            enginenum.setText(vehicle.getEngineNum());
//            chasisnum.setText(vehicle.getChasisNum());
//            color.setText(vehicle.getColor());

            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

}
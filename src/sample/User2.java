package sample;

import edu.sust.db.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.hibernate.*;

import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Murtoza10 on 11/3/2014.
 */
public class User2 implements Initializable,
        ControlledScreen {

    ScreensController myController;
    private static SessionFactory factory;
    private static ServiceRegistry serviceRegistry;
    Stage primarystage;

    @FXML
    TextField txtsearch2;

    @FXML
    ChoiceBox chcsoption2;

    @FXML
    Label ownname2,platenum2,reg2,validity2;

    @FXML
    TableView<Table2>table2;

    @FXML
    TableColumn<Table2,Integer>treg2;

    @FXML
    TableColumn<Table2,String>tmanufacturer2;

    @FXML
    TableColumn<Table2,String>tmodel2;

    @FXML
    TableColumn<Table2,String>tcolor2;

    @FXML
    TableColumn<Table2,String>tvalidity2;

    public static  int reg12;
    int i=0;

    final ObservableList<Table2> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        treg2.setCellValueFactory(new PropertyValueFactory<Table2, Integer>("regId"));
        tmanufacturer2.setCellValueFactory(new PropertyValueFactory<Table2, String>("manufacturer"));
        tmodel2.setCellValueFactory(new PropertyValueFactory<Table2, String>("model"));
        tcolor2.setCellValueFactory(new PropertyValueFactory<Table2, String>("color"));
        tvalidity2.setCellValueFactory(new PropertyValueFactory<Table2, String>("validity"));

        table2.setItems(data);

        chcsoption2.setItems(FXCollections.observableArrayList("OwnerName","Registration ID","Engine Number","Plate Number"));
        chcsoption2.setValue("OwnerName");

        table2.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Table2>() {

            @Override
            public void changed(ObservableValue<? extends Table2> observable,
                                Table2 oldValue, Table2 newValue) {
                ShowLabelUser2();
            }
        });

    }

    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

    @FXML
    private void goToUser2Newreg(ActionEvent event){
        myController.setScreen(ScreensFramework.User1_2newreg_SCREEN);
    }

    @FXML
    private void goToUser2trans(ActionEvent event){
        myController.setScreen(ScreensFramework.User12trans_SCREEN);
        User12Transaction user12trans=(User12Transaction)ScreensFramework.user12trans;
        user12trans.SettingLabelTran12();
    }


    public void SettingLabelUser2(){
        int own=0;
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

            Query query1 = session.createQuery("FROM VehicleRegInfo r INNER JOIN r.vehicle v INNER JOIN v.owner o INNER JOIN v.build b");

            List<Object[]> list1 = query1.list();

//            for (Object object : list1) {
//                Object[] li = (Object[])object;
                for(Object liItem:list1.get(0)){
                    if (liItem instanceof Vehicle) {
                        String pattern = "dd-MM-yyyy";
                        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
                        validity2.setText(formatter.format(((Vehicle) liItem).getValidTill()));
//                        enginenum.setText(((Vehicle) liItem).getEngineNum());
//                        chasisnum.setText(((Vehicle) liItem).getChasisNum());
//                        color.setText(((Vehicle) liItem).getColor());
                    }else if(liItem instanceof OwnerDetails){
                          ownname2.setText(((OwnerDetails) liItem).getName());
//                        model.setText(((VehicleBuild) liItem).getModel());

                    }else if(liItem instanceof VehicleRegInfo){
                        reg12=((VehicleRegInfo) liItem).getRegistrationId();
                        reg2.setText(Integer.toString(((VehicleRegInfo) liItem).getRegistrationId()));
                        platenum2.setText(Integer.toString(((VehicleRegInfo) liItem).getPlateNum()));
                    }

                }

//            }

            tx.commit();
            if(i==0) {
                AddTableData();
                i++;
            }

        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void AddTableData(){
        int reg=0;
        String manufacturer=new String();
        String model=new String();
        String color=new String();
        String validity=new String();

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

                Query query1 = session.createQuery("FROM VehicleRegInfo r INNER JOIN r.vehicle v INNER JOIN v.owner o INNER JOIN v.build b");

                List<Object[]> list1 = query1.list();

                for (Object object : list1) {
                    Object[] li = (Object[]) object;
                    for (Object liItem : li) {
                        if (liItem instanceof Vehicle) {
                            String pattern = "dd-MM-yyyy";
                            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
//                        validity2.setText(formatter.format(((Vehicle) liItem).getValidTill()));
                            color = ((Vehicle) liItem).getColor();
                            validity = formatter.format(((Vehicle) liItem).getValidTill());
//                        enginenum.setText(((Vehicle) liItem).getEngineNum());
//                        chasisnum.setText(((Vehicle) liItem).getChasisNum());
//                        color.setText(((Vehicle) liItem).getColor());
                        } else if (liItem instanceof VehicleBuild) {
//                        manufacturer.setText(((VehicleBuild) liItem).getManufacturer());
//                        model.setText(((VehicleBuild) liItem).getModel());
                            manufacturer = ((VehicleBuild) liItem).getManufacturer();
                            model = ((VehicleBuild) liItem).getModel();
                        } else if (liItem instanceof VehicleRegInfo) {
                            reg = ((VehicleRegInfo) liItem).getRegistrationId();
//                        reg2.setText(Integer.toString(((VehicleRegInfo) liItem).getRegistrationId()));
//                        platenum2.setText(Integer.toString(((VehicleRegInfo) liItem).getPlateNum()));
                        }

                    }
                    Table2 entry = new Table2(reg, manufacturer, model, color, validity);
                    data.add(entry);
                }

                tx.commit();

            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                e.printStackTrace();
            } finally {
                session.close();
            }



    }

//        public void SettingLabelUser2(){
//        int own=0;
//        String phon=null,chasis=null;
//        try {
//            factory = new Configuration().configure().buildSessionFactory();
//        } catch (Throwable ex) {
//            System.err.println("Failed to create sessionFactory object." + ex);
//            throw new ExceptionInInitializerError(ex);
//        }
//        Session session = factory.openSession();
//        Transaction tx = null;
//        try {
//            tx = session.beginTransaction();
//            UserLogin UL=new UserLogin();
//
//            Query query = session.createQuery("FROM OwnerDetails o "
//                    + "INNER JOIN o.user AS u where u.userId = :userid").setParameter("userid", UL.uid);
//            List<Object[]> list = query.list();
//
//            for (Object object : list) {
//                Object[] li = (Object[])object;
//                for(Object liItem:li){
//                    if (liItem instanceof OwnerDetails) {
//                        own= ((OwnerDetails)liItem).getOwnerId();
//                        ownname2.setText(((OwnerDetails)liItem).getName());
//                    }
//                }
//            }
//
//            Query query1 = session.createQuery("FROM VehicleRegInfo r INNER JOIN r.vehicle v INNER JOIN v.owner o INNER JOIN v.build b WHERE o.ownerId = :ownid").setParameter("ownid", own);
//
//            List<Object[]> list1 = query1.list();
//
//            for (Object object : list1) {
//                Object[] li = (Object[])object;
//                for(Object liItem:li){
//                    if (liItem instanceof Vehicle) {
//                        String pattern = "dd-MM-yyyy";
//                        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
//                        validity2.setText(formatter.format(((Vehicle) liItem).getValidTill()));
////                        enginenum.setText(((Vehicle) liItem).getEngineNum());
////                        chasisnum.setText(((Vehicle) liItem).getChasisNum());
////                        color.setText(((Vehicle) liItem).getColor());
//                    }else if(liItem instanceof VehicleBuild){
////                        manufacturer.setText(((VehicleBuild) liItem).getManufacturer());
////                        model.setText(((VehicleBuild) liItem).getModel());
//
//                    }else if(liItem instanceof VehicleRegInfo){
//                        reg12=((VehicleRegInfo) liItem).getRegistrationId();
//                        reg2.setText(Integer.toString(((VehicleRegInfo) liItem).getRegistrationId()));
//                        platenum2.setText(Integer.toString(((VehicleRegInfo) liItem).getPlateNum()));
//                    }
//
//                }
//
//            }
//
//            tx.commit();
//            if(i==0) {
//                AddTableData();
//                i++;
//            }
//
//        } catch (HibernateException e) {
//            if (tx != null) tx.rollback();
//            e.printStackTrace();
//        } finally {
//            session.close();
//        }
//    }

    @FXML
    private void Search2(){
        myController.setScreen(ScreensFramework.Search_SCREEN);

        int reg=0;
        String manufacturer=new String();
        String model=new String();
        String color=new String();
        String validity=new String();
        SearchOption search = (SearchOption) ScreensFramework.search;
        search.chcsoption.setValue(chcsoption2.getValue().toString());
        search.txtssearch.setText(txtsearch2.getText());
        search.stable2.getItems().removeAll();
        search.data.clear();
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        Session session = factory.openSession();
        Query query1=null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            if(chcsoption2.getValue().toString().equals("OwnerName")){
                query1 = session.createQuery("FROM VehicleRegInfo r INNER JOIN r.vehicle v INNER JOIN v.owner o INNER JOIN v.build b WHERE o.name = :own").setParameter("own",txtsearch2.getText());
            }
            if(chcsoption2.getValue().toString().equals("Registration ID")){
                query1 = session.createQuery("FROM VehicleRegInfo r INNER JOIN r.vehicle v INNER JOIN v.owner o INNER JOIN v.build b WHERE r.registrationId = :regid").setParameter("regid",Integer.parseInt(txtsearch2.getText()));
            }
            if(chcsoption2.getValue().toString().equals("Engine Number")){
                query1 = session.createQuery("FROM VehicleRegInfo r INNER JOIN r.vehicle v INNER JOIN v.owner o INNER JOIN v.build b WHERE v.engineNum = :engnum").setParameter("engnum",txtsearch2.getText());
            }
            if(chcsoption2.getValue().toString().equals("Plate Number")){
                query1 = session.createQuery("FROM VehicleRegInfo r INNER JOIN r.vehicle v INNER JOIN v.owner o INNER JOIN v.build b WHERE r.plateNum = :platenum").setParameter("platenum",Integer.parseInt(txtsearch2.getText()));
            }
            //Query query1 = session.createQuery("FROM VehicleTax t INNER JOIN t.reg r INNER JOIN r.vehicle v INNER JOIN v.owner o INNER JOIN v.build b INNER JOIN o.user u WHERE r.registrationId = :regid");

            List<Object[]> list1 = query1.list();

            for (Object object : list1) {
                Object[] li = (Object[]) object;
                for (Object liItem : li) {
                    if (liItem instanceof Vehicle) {
                        String pattern = "dd-MM-yyyy";
                        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
//                        validity2.setText(formatter.format(((Vehicle) liItem).getValidTill()));
                        color = ((Vehicle) liItem).getColor();
                        validity = formatter.format(((Vehicle) liItem).getValidTill());
//                        enginenum.setText(((Vehicle) liItem).getEngineNum());
//                        chasisnum.setText(((Vehicle) liItem).getChasisNum());
//                        color.setText(((Vehicle) liItem).getColor());
                    } else if (liItem instanceof VehicleBuild) {
//                        manufacturer.setText(((VehicleBuild) liItem).getManufacturer());
//                        model.setText(((VehicleBuild) liItem).getModel());
                        manufacturer = ((VehicleBuild) liItem).getManufacturer();
                        model = ((VehicleBuild) liItem).getModel();
                    } else if (liItem instanceof VehicleRegInfo) {
                        reg = ((VehicleRegInfo) liItem).getRegistrationId();
//                        reg2.setText(Integer.toString(((VehicleRegInfo) liItem).getRegistrationId()));
//                        platenum2.setText(Integer.toString(((VehicleRegInfo) liItem).getPlateNum()));
                    }

                }
                Table2 entry = new Table2(reg, manufacturer, model, color, validity);
                search.data.add(entry);
            }

            tx.commit();
            //search.stable2.setItems(search.data);
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

    @FXML
    private void AddVehicle(ActionEvent event){
        myController.setScreen(ScreensFramework.AddVehicle_SCREEN);
    }

    private void ShowLabelUser2(){
        int own=0;
        String phon=null,chasis=null;
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        Table2 selectedtable = table2.getSelectionModel().getSelectedItem();
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            Query query1 = session.createQuery("FROM VehicleRegInfo r INNER JOIN r.vehicle v INNER JOIN v.owner o INNER JOIN v.build b WHERE r.registrationId = :regid").setParameter("regid",selectedtable.getRegId());

            List<Object[]> list1 = query1.list();

//            for (Object object : list1) {
//                Object[] li = (Object[])object;
            for(Object liItem:list1.get(0)){
                if (liItem instanceof Vehicle) {
                    String pattern = "dd-MM-yyyy";
                    SimpleDateFormat formatter = new SimpleDateFormat(pattern);
//                        validity=formatter.format(((Vehicle) liItem).getValidTill());
                    validity2.setText(formatter.format(((Vehicle) liItem).getValidTill()));
                }else if(liItem instanceof OwnerDetails){
                    ownname2.setText(((OwnerDetails) liItem).getName());

                }else if(liItem instanceof VehicleRegInfo){

                    reg2.setText(Integer.toString(((VehicleRegInfo) liItem).getRegistrationId()));
                    platenum2.setText(Integer.toString(((VehicleRegInfo) liItem).getPlateNum()));
                }

            }

//            }

            tx.commit();
            if(i==0) {
                AddTableData();
                i++;
            }

        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @FXML
    private void MyProfile(ActionEvent event){
        myController.setScreen(ScreensFramework.FullView_SCREEN);
        UserLogin Ul=new UserLogin();
        FullView fullview=(FullView)ScreensFramework.fullview;
        fullview.MyProfile(Ul.uid);
    }
}

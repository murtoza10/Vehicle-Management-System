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
public class User1 implements Initializable,
        ControlledScreen {

    ScreensController myController;
    private static SessionFactory factory;
    private static ServiceRegistry serviceRegistry;
    Stage primarystage;

    String ownername, nid,dob, per_address, cur_address, phn=null, user, pass,rank, eng_num, chasis_num, valid_till, color,
            manufacturer, engtype, fueltype, wheeltype,model, yr_of_manufacture, plate_num, date_of_reg, reg_fee,
            reg_area, vehicletype, tax_amount, date_of_payment, road_permit,opstatus;

    public static int fullviewreg=0;

    @FXML
    TextField txtsearch1;

    @FXML
    ChoiceBox chcsoption1;

    @FXML
    Label ownname1,platenum1,reg1,validity1;

    @FXML
    TableView<Table2>table1;

    @FXML
    TableColumn<Table2,Integer>treg1;

    @FXML
    TableColumn<Table2,String>tmanufacturer1;

    @FXML
    TableColumn<Table2,String>tmodel1;

    @FXML
    TableColumn<Table2,String>tcolor1;

    @FXML
    TableColumn<Table2,String>tvalidity1;

    public static  int reg12,edit=0,ereg=0;
    int i=0;

    final ObservableList<Table2> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        treg1.setCellValueFactory(new PropertyValueFactory<Table2, Integer>("regId"));
        tmanufacturer1.setCellValueFactory(new PropertyValueFactory<Table2, String>("manufacturer"));
        tmodel1.setCellValueFactory(new PropertyValueFactory<Table2, String>("model"));
        tcolor1.setCellValueFactory(new PropertyValueFactory<Table2, String>("color"));
        tvalidity1.setCellValueFactory(new PropertyValueFactory<Table2, String>("validity"));

        table1.setItems(data);
        chcsoption1.setItems(FXCollections.observableArrayList("OwnerName","Registration ID","Engine Number","Plate Number"));
        chcsoption1.setValue("OwnerName");
        table1.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Table2>() {

            @Override
            public void changed(ObservableValue<? extends Table2> observable,
                                Table2 oldValue, Table2 newValue) {
                ShowLabelUser1();
            }
        });


    }

    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

    @FXML
    private void goToUser1Newreg(ActionEvent event){
        myController.setScreen(ScreensFramework.User1_2newreg_SCREEN);
//        ScreensController mainContainer = new ScreensController();
//        mainContainer.loadScreen(ScreensFramework.User1_2newreg_SCREEN,
//                ScreensFramework.User1newreg_SCREEN_FXML);
        User1_2NewReg nr=(User1_2NewReg)ScreensFramework.user12newreg;
        //nr.txtownername.setText("Murtoza");
    }

    @FXML
    private void goToUser1trans(ActionEvent event){
        myController.setScreen(ScreensFramework.User12trans_SCREEN);
        User12Transaction user12trans=(User12Transaction)ScreensFramework.user12trans;
        user12trans.SettingLabelTran12();
    }

    @FXML
    private void goToUser1Adduser(ActionEvent event){ myController.setScreen(ScreensFramework.User1adduser_SCREEN); }


    public void SettingLabelUser1(){
        int own=0;
        String phon=null,chasis=null,validity=null,ownname=null,regid=null,platenum=null;
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
//                        validity=formatter.format(((Vehicle) liItem).getValidTill());
                        validity1.setText(formatter.format(((Vehicle) liItem).getValidTill()));
                    }else if(liItem instanceof OwnerDetails){
                         ownname1.setText(((OwnerDetails) liItem).getName());

                    }else if(liItem instanceof VehicleRegInfo){
                        reg12=((VehicleRegInfo) liItem).getRegistrationId();
                        reg1.setText(Integer.toString(((VehicleRegInfo) liItem).getRegistrationId()));
                        platenum1.setText(Integer.toString(((VehicleRegInfo) liItem).getPlateNum()));
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

    @FXML
    private void handleVehicleDelete() {
        Vehicle vehicle=null;
        int selectedIndex = table1.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Table2 selectedtable = table1.getSelectionModel().getSelectedItem();
            System.out.println(selectedtable.getRegId());
            Session session = factory.openSession();
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Query query1 = session.createQuery("FROM VehicleRegInfo r INNER JOIN r.vehicle v INNER JOIN v.owner o INNER JOIN v.build b WHERE r.registrationId = :regid").setParameter("regid",selectedtable.getRegId() );

                List<Object[]> list1 = query1.list();

                for (Object object : list1) {
                    Object[] li = (Object[]) object;
                    for (Object liItem : li) {
                        if (liItem instanceof VehicleRegInfo) {
                             vehicle = ((VehicleRegInfo) liItem).getVehicle();
                        }
                    }
                }
                session.delete(vehicle);
                tx.commit();
            }catch (HibernateException e) {
                if (tx != null) tx.rollback();
                e.printStackTrace();
            } finally {
                session.close();
            }

            table1.getItems().remove(selectedIndex);
        } else {
            // Nothing selected
            Dialogs.showWarningDialog(primarystage,
                    "Please select a person in the table.",
                    "No Person Selected", "No Selection");
        }
    }

    @FXML
    private void handleUserDelete() {
        User user=null;
        int selectedIndex = table1.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Table2 selectedtable = table1.getSelectionModel().getSelectedItem();
            System.out.println(selectedtable.getRegId());
            Session session = factory.openSession();
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Query query1 = session.createQuery("FROM VehicleRegInfo r INNER JOIN r.vehicle v INNER JOIN v.owner o INNER JOIN v.build b INNER JOIN o.user u WHERE r.registrationId = :regid").setParameter("regid",selectedtable.getRegId() );

                List<Object[]> list1 = query1.list();

                for (Object object : list1) {
                    Object[] li = (Object[]) object;
                    for (Object liItem : li) {
                        if (liItem instanceof OwnerDetails) {
                            user = ((OwnerDetails) liItem).getUser();
                        }
                    }
                }
                session.delete(user);
                tx.commit();
            }catch (HibernateException e) {
                if (tx != null) tx.rollback();
                e.printStackTrace();
            } finally {
                session.close();
            }
            table1.getItems().remove(selectedIndex);
        } else {
            // Nothing selected
            Dialogs.showWarningDialog(primarystage,
                    "Please select a person in the table.",
                    "No Person Selected", "No Selection");
        }
    }

    @FXML
    private void handleEdit() {

        int selectedIndex = table1.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Table2 selectedtable = table1.getSelectionModel().getSelectedItem();
            System.out.println(selectedtable.getRegId());
            edit=1;
            myController.setScreen(ScreensFramework.User1_2newreg_SCREEN);
            String pattern = "dd-MM-yyyy";
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            int i=0;
            ereg=selectedtable.getRegId();
            User1_2NewReg nr=(User1_2NewReg)ScreensFramework.user12newreg;
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
                Query query1 = session.createQuery("FROM VehicleTax t INNER JOIN t.reg r INNER JOIN r.vehicle v INNER JOIN v.owner o INNER JOIN v.build b INNER JOIN o.user u WHERE r.registrationId = :regid").setParameter("regid",selectedtable.getRegId() );
                List<Object[]> list1 = query1.list();

                for (Object object : list1) {
                    Object[] li = (Object[]) object;
                    for (Object liItem : li) {
                        if (liItem instanceof Vehicle) {
                            eng_num=((Vehicle) liItem).getEngineNum();
                            chasis_num=((Vehicle) liItem).getChasisNum();
                            valid_till=formatter.format(((Vehicle) liItem).getValidTill());
                            color=((Vehicle) liItem).getColor();
                            nr.txtengine.setText(eng_num);
                            nr.txtchasis.setText(chasis_num);
                            nr.validity.setSelectedDate(((Vehicle) liItem).getValidTill());
                            nr.txtcolor.setText(color);

                        } else if (liItem instanceof VehicleBuild) {
                            manufacturer=((VehicleBuild) liItem).getManufacturer();
                            engtype=((VehicleBuild) liItem).getEngineType();
                            fueltype=((VehicleBuild) liItem).getFuelType();
                            wheeltype=((VehicleBuild) liItem).getWheelType();
                            model=((VehicleBuild) liItem).getModel();
                            yr_of_manufacture=((VehicleBuild) liItem).getYearOfManufacture();
                            nr.txtmanufacturer.setText(manufacturer);
                            nr.txtengtype.setText(engtype);
                            nr.txtfueltype.setText(fueltype);
                            nr.txtwheeltye.setText(wheeltype);
                            nr.txtmodel.setText(model);
                            nr.txtyrmanufacture.setText(yr_of_manufacture);
                        } else if (liItem instanceof VehicleRegInfo) {
                            plate_num=Integer.toString(((VehicleRegInfo) liItem).getPlateNum());
                            date_of_reg=formatter.format(((VehicleRegInfo) liItem).getDateOfReg());
                            reg_fee=Integer.toString(((VehicleRegInfo) liItem).getRegFee());
                            reg_area=((VehicleRegInfo) liItem).getRegArea();
                            vehicletype=((VehicleRegInfo) liItem).getVehicleType();
                            nr.txtplate.setText(plate_num);
                            nr.dateofreg.setSelectedDate(((VehicleRegInfo) liItem).getDateOfReg());
                            nr.txtregfee.setText(reg_fee);
                            nr.txtregarea.setText(reg_area);
                            nr.txtvehicletype.setText(vehicletype);
                        }else if (liItem instanceof VehicleTax) {
                            tax_amount=Integer.toString(((VehicleTax) liItem).getTaxAmount());
                            date_of_payment=formatter.format(((VehicleTax) liItem).getDateOfPayment());
                            nr.txttaxamnt.setText(tax_amount);
                            nr.dateofpayment.setSelectedDate(((VehicleTax) liItem).getDateOfPayment());
                        }else if (liItem instanceof OwnerDetails) {
                            ownername=((OwnerDetails) liItem).getName();
                            nid=Integer.toString(((OwnerDetails) liItem).getNationalId());
                            dob=formatter.format(((OwnerDetails) liItem).getDob());
                            per_address=((OwnerDetails) liItem).getPermAddrss();
                            cur_address=((OwnerDetails) liItem).getCurrAddrss();
                            Set phones = ((OwnerDetails) liItem).getPhones();
                            for (Iterator iterator2 =
                                         phones.iterator(); iterator2.hasNext();){
                                Phone phone = (Phone) iterator2.next();
                                System.out.println(phone.getPhone());
                                if(i==0) {
                                    phn=phone.getPhone();
                                    i++;
                                }
                                else {
                                    phn +=("," + phone.getPhone());
                                }
                            }
                            nr.txtownername.setText(ownername);
                            nr.txtnid.setText(nid);
                            nr.datepickerdob.setSelectedDate(((OwnerDetails) liItem).getDob());
                            nr.txtperaddress.setText(per_address);
                            nr.txtcuraddress.setText(cur_address);
                            nr.txtphn.setText(phn);
                        }else if (liItem instanceof User) {
                            user=((User) liItem).getUsername();
                            pass=((User) liItem).getPassword();
                            rank=Integer.toString(((User) liItem).getRank());
                            nr.txtuser.setText(user);
                            nr.txtpass.setText(pass);
                            nr.chcrank.setValue(((User) liItem).getRank());
                        }


                    }

                }
                Query query2 = session.createQuery("FROM VehicleStatus s INNER JOIN s.reg r WHERE r.registrationId = :regid").setParameter("regid",selectedtable.getRegId() );
                List<Object[]> list2 = query2.list();

                for (Object object : list2) {
                    Object[] li = (Object[]) object;
                    for (Object liItem : li) {
                        if (liItem instanceof VehicleStatus) {
                            road_permit=((VehicleStatus) liItem).getRoadPermit();
                            opstatus=((VehicleStatus) liItem).getOperationalStatus();
                            nr.txtroadpermit.setText(road_permit);
                            nr.chcoperstatus.setValue(opstatus);
                        }
                    }
                }

                tx.commit();
            }catch (HibernateException e) {
                if (tx != null) tx.rollback();
                e.printStackTrace();
            } finally {
                session.close();
            }
        } else {
            // Nothing selected
            Dialogs.showWarningDialog(primarystage,
                    "Please select a row in the table.",
                    "No Row Selected", "No Selection");
        }
    }

    @FXML
    private void FullView() {
        User user = null;
        int uid = 0;
        int selectedIndex = table1.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Table2 selectedtable = table1.getSelectionModel().getSelectedItem();
            System.out.println(selectedtable.getRegId());
            fullviewreg=selectedtable.getRegId();
            myController.setScreen(ScreensFramework.FullView_SCREEN);
            FullView fullview = (FullView) ScreensFramework.fullview;
            //fullview.data.clear();
            System.out.println(fullview.data);
            Session session = factory.openSession();
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Query query1 = session.createQuery("FROM VehicleRegInfo r INNER JOIN r.vehicle v INNER JOIN v.owner o INNER JOIN v.build b INNER JOIN o.user u WHERE r.registrationId = :regid").setParameter("regid", selectedtable.getRegId());

                List<Object[]> list1 = query1.list();

                for (Object object : list1) {
                    Object[] li = (Object[]) object;
                    for (Object liItem : li) {
                        if (liItem instanceof User) {
                            uid = ((User) liItem).getUserId();
                        }
                    }
                }

                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                e.printStackTrace();
            } finally {
                session.close();
            }
            fullview.SettingLabelFullView(uid);
        }else {
            // Nothing selected
            Dialogs.showWarningDialog(primarystage,
                    "Please select a row in the table.",
                    "No Row Selected", "No Selection");
        }
    }

    @FXML
    private void Search1(){
        myController.setScreen(ScreensFramework.Search_SCREEN);

        int reg=0;
        String manufacturer=new String();
        String model=new String();
        String color=new String();
        String validity=new String();
        SearchOption search = (SearchOption) ScreensFramework.search;
        search.chcsoption.setValue(chcsoption1.getValue().toString());
        search.txtssearch.setText(txtsearch1.getText());
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
            if(chcsoption1.getValue().toString().equals("OwnerName")){
                query1 = session.createQuery("FROM VehicleRegInfo r INNER JOIN r.vehicle v INNER JOIN v.owner o INNER JOIN v.build b WHERE o.name = :own").setParameter("own",txtsearch1.getText());
            }
            if(chcsoption1.getValue().toString().equals("Registration ID")){
                query1 = session.createQuery("FROM VehicleRegInfo r INNER JOIN r.vehicle v INNER JOIN v.owner o INNER JOIN v.build b WHERE r.registrationId = :regid").setParameter("regid",Integer.parseInt(txtsearch1.getText()));
            }
            if(chcsoption1.getValue().toString().equals("Engine Number")){
                query1 = session.createQuery("FROM VehicleRegInfo r INNER JOIN r.vehicle v INNER JOIN v.owner o INNER JOIN v.build b WHERE v.engineNum = :engnum").setParameter("engnum",txtsearch1.getText());
            }
            if(chcsoption1.getValue().toString().equals("Plate Number")){
                query1 = session.createQuery("FROM VehicleRegInfo r INNER JOIN r.vehicle v INNER JOIN v.owner o INNER JOIN v.build b WHERE r.plateNum = :platenum").setParameter("platenum",Integer.parseInt(txtsearch1.getText()));
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

    public void ShowLabelUser1(){
        int own=0;
        String phon=null,chasis=null,validity=null,ownname=null,regid=null,platenum=null;
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        Session session = factory.openSession();
        Transaction tx = null;
        Table2 selectedtable = table1.getSelectionModel().getSelectedItem();
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
                    validity1.setText(formatter.format(((Vehicle) liItem).getValidTill()));
                }else if(liItem instanceof OwnerDetails){
                    ownname1.setText(((OwnerDetails) liItem).getName());

                }else if(liItem instanceof VehicleRegInfo){

                    reg1.setText(Integer.toString(((VehicleRegInfo) liItem).getRegistrationId()));
                    platenum1.setText(Integer.toString(((VehicleRegInfo) liItem).getPlateNum()));
                }

            }


//            }

            tx.commit();

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

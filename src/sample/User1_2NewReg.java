package sample;

import edu.sust.db.*;
import eu.schudt.javafx.controls.calendar.DatePicker;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.converter.DateStringConverter;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Murtoza10 on 8/27/2014.
 */
public class User1_2NewReg implements Initializable,
        ControlledScreen {

    ScreensController myController;
    private static SessionFactory factory;
    private static ServiceRegistry serviceRegistry;
    Stage primarystage;

    @FXML
    TextField txtownername, txtnid, txtphn, txtuser, txtengine, txtchasis, txtcolor, txtmanufacturer, txtengtype, txtfueltype,
            txtwheeltye, txtmodel, txtyrmanufacture, txtplate, txtregfee, txtregarea,
            txtvehicletype, txttaxamnt, txtroadpermit;
    @FXML
    PasswordField txtpass;
    @FXML
    ChoiceBox chcrank, chcoperstatus;
    @FXML
    TextArea txtperaddress, txtcuraddress;
    @FXML
    DatePicker datepickerdob, validity, dateofpayment, dateofreg;

//    String ownername, nid, per_address, cur_address, phn, user, pass, eng_num, chasis_num, valid_till, color,
//            manufacturer, engtype, fueltype, wheeltype, yr_of_manufacture, plate_num, date_of_reg, reg_fee,
//            reg_area, vehicletype, tax_amount, date_of_payment, road_permit;

    int count = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializedate(datepickerdob);
        initializedate(validity);
        initializedate(dateofpayment);
        initializedate(dateofreg);
        chcrank.setItems(FXCollections.observableArrayList(1, 2, 3));
        chcrank.setValue(1);
        chcoperstatus.setItems(FXCollections.observableArrayList("active","inactive"));
        chcoperstatus.setValue("active");
    }

    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }


    @FXML
    private void SaveReg(ActionEvent event) {

        if(User1.edit==0) {
            try {
                factory = new Configuration().configure().buildSessionFactory();
            } catch (Throwable ex) {
                System.err.println("Failed to create sessionFactory object." + ex);
                throw new ExceptionInInitializerError(ex);
            }
            Session session = factory.openSession();
            Transaction tx = null;
            if (isInputValid()) {
                Integer userID = null;
                Integer ownerID = null;
                VehicleBuild build;
                UserLoginQuery Ulq = new UserLoginQuery();
                User user1 = Ulq.FindUserByUsername(txtuser.getText());
                if (user1 == null) {
                    try {
                        tx = session.beginTransaction();
                        User user = new User(txtuser.getText(), txtpass.getText(), Integer.parseInt(chcrank.getValue().toString()));
                        userID = (Integer) session.save(user);
                        System.out.println(user);
//                Criteria criteria = session.createCriteria(User.class);
//                criteria.add(Restrictions.like("username", txtuser.getText()));
////            criteria.uniqueResult();
//                user = (User) criteria.uniqueResult();
                        HashSet set1 = new HashSet();
                        for (String retval : txtphn.getText().split(",")) {
                            set1.add(new Phone(retval));
                        }
                        OwnerDetails owner = new OwnerDetails(Integer.parseInt(txtnid.getText()), txtownername.getText(), datepickerdob.getSelectedDate(), txtcuraddress.getText(), txtperaddress.getText(), user);
                        owner.setPhones(set1);
                        ownerID = (Integer) session.save(owner);
                        Query query = session.createQuery("FROM VehicleBuild b WHERE b.manufacturer = :manu AND b.engineType = :eng AND b.fuelType = :fuel AND b.wheelType = :wheel AND b.model = :model AND b.yearOfManufacture = :yrmanu ")
                                .setParameter("manu", txtmanufacturer.getText()).setParameter("eng", txtengtype.getText()).setParameter("fuel", txtfueltype.getText()).setParameter("wheel", txtwheeltye.getText())
                                .setParameter("model", txtmodel.getText()).setParameter("yrmanu", txtyrmanufacture.getText());
                        List<VehicleBuild> builds = query.list();
                        if (builds.size() > 0) {
                            build = builds.get(0);

                        } else {
                            build = new VehicleBuild(txtmanufacturer.getText(), txtengtype.getText(), txtfueltype.getText(), txtwheeltye.getText(), txtmodel.getText(), txtyrmanufacture.getText());
                            session.save(build);
                        }
                        Vehicle vehicle = new Vehicle(txtengine.getText(), txtchasis.getText(), validity.getSelectedDate(), txtcolor.getText(), owner, build);
                        session.save(vehicle);
                        VehicleRegInfo reg = new VehicleRegInfo(Integer.parseInt(txtplate.getText()), dateofreg.getSelectedDate(), Integer.parseInt(txtregfee.getText()), txtregarea.getText(), txtvehicletype.getText(), vehicle);
                        session.save(reg);
//                Criteria criteria1 = session.createCriteria(VehicleRegInfo.class);
//                criteria1.add(Restrictions.like("plateNum", Integer.parseInt(txtplate.getText())));
////            criteria.uniqueResult();
//                reg = (VehicleRegInfo) criteria1.uniqueResult();


                        VehicleTax tax = new VehicleTax(Integer.parseInt(txttaxamnt.getText()), dateofpayment.getSelectedDate(), reg);
                        session.save(tax);
                        VehicleStatus status = new VehicleStatus(txtroadpermit.getText(), chcoperstatus.getValue().toString(), reg);
                        session.save(status);
                        tx.commit();
                        Dialogs.showInformationDialog(primarystage, "Successfully Saved ",
                                "Information Dialog", "Message");


                    } catch (HibernateException e) {
                        if (tx != null) tx.rollback();
                        e.printStackTrace();
                    } finally {
                        session.close();
                    }
                } else {
                    Dialogs.showInformationDialog(primarystage, "This Username Already Exists ",
                            "Information Dialog", "Message");
                }

            }
        }else{
                edit();

        }

    }


    @FXML
    private void ClearData(ActionEvent event){
        //myController.setScreen(ScreensFramework.User1_2newreg_SCREEN);
        txtownername.setText("");
        txtnid.setText("");
        txtphn.setText("");
        txtuser.setText("");
        txtengine.setText("");
        txtchasis.setText("");
        txtcolor.setText("");
        txtmanufacturer.setText("");
        txtengtype.setText("");
        txtfueltype.setText("");
        txtwheeltye.setText(""); txtmodel.setText("");
        txtyrmanufacture.setText("");
        txtplate.setText("");
        txtregfee.setText("");
        txtregarea.setText("");
        txtvehicletype.setText("");
        txttaxamnt.setText("");
        txtroadpermit.setText("");
        txtpass.setText("");
        txtperaddress.setText("");
        txtcuraddress.setText("");
        datepickerdob.setSelectedDate(null);
        validity.setSelectedDate(null);
        dateofpayment.setSelectedDate(null);
        dateofreg.setSelectedDate(null);
        chcrank.setValue(1);
        chcoperstatus.setValue("active");


    }
    @FXML
    private void BackToUser(ActionEvent event){
        UserLogin user=new UserLogin();
        //System.out.println(user.rank);
        if(user.rank==1) {
            clear();
            myController.setScreen(ScreensFramework.User1_SCREEN);
            User1 user1= (User1)ScreensFramework.user1;
            int k=user1.table1.getSelectionModel().getSelectedIndex();
            System.out.println(k);
            user1.table1.getSelectionModel().clearSelection();
            user1.table1.getItems().clear();

            //user1.data.clear();
            user1.AddTableData();
            user1.table1.getSelectionModel().select(k);
        }
        else if(user.rank==2){
            clear();
            myController.setScreen(ScreensFramework.User2_SCREEN);
            User2 user2= (User2)ScreensFramework.user2;
            int k=user2.table2.getSelectionModel().getSelectedIndex();
            user2.table2.getSelectionModel().clearSelection();
            user2.table2.getItems().clear();
            //user2.data.clear();
            user2.AddTableData();
        }
        else
        {
            myController.setScreen(ScreensFramework.User3_SCREEN);
        }

    }


    public void initializedate(DatePicker DP){
        DP = new DatePicker(Locale.ENGLISH);
        DP.setDateFormat(new SimpleDateFormat("dd-MM-yyyy"));
        DP.getCalendarView().todayButtonTextProperty().set("Today");
        DP.getCalendarView().setShowWeeks(false);
        DP.getStylesheets().add("Datepicker.css");

    }


    private boolean isInputValid() {
        String errorMessage = "";

        if (txtownername.getText() == null || txtownername.getText().length() == 0) {
            errorMessage += "No valid Owner Name!\n";
            count++;
        }
        if (txtnid.getText() == null || txtnid.getText().length() == 0) {
            errorMessage += "No valid National ID!\n"; count++;
        }
        if (txtphn.getText() == null || txtphn.getText().length() == 0) {
            errorMessage += "No valid Phone Number!\n"; count++;
        }

        if (txtuser.getText() == null || txtuser.getText().length() == 0) {
            errorMessage += "No valid User Name!\n"; count++;
        }

        if (txtpass.getText() == null || txtpass.getText().length() == 0) {
            errorMessage += "No valid Password!\n"; count++;
        }
        if (txtengine.getText() == null || txtengine.getText().length() == 0) {
            errorMessage += "No valid Engine Number!\n"; count++;
        }
        if (txtchasis.getText() == null || txtchasis.getText().length() == 0) {
            errorMessage += "No valid Chasis Number!\n"; count++;
        }
        if (txtcolor.getText() == null || txtcolor.getText().length() == 0) {
            errorMessage += "No valid Color!\n"; count++;
        }
        if (txtmanufacturer.getText() == null || txtmanufacturer.getText().length() == 0) {
            errorMessage += "No valid Manufacturer!\n"; count++;
        }
        if (txtengtype.getText() == null ||  txtengtype.getText().length() == 0) {
            errorMessage += "No valid Engine Type!\n"; count++;
        }
        if (txtfueltype.getText() == null || txtfueltype.getText().length() == 0) {
            errorMessage += "No valid Fuel Type!\n"; count++;
        }
        if (txtwheeltye.getText() == null || txtwheeltye.getText().length() == 0) {
            errorMessage += "No valid Wheel Type!\n"; count++;
        }
        if (txtmodel.getText() == null || txtmodel.getText().length() == 0) {
            errorMessage += "No valid Model!\n"; count++;
        }
        if (txtyrmanufacture.getText() == null || txtyrmanufacture.getText().length() == 0) {
            errorMessage += "No valid Year Of Manufacture!\n"; count++;
        }
        if (txtplate.getText() == null || txtplate.getText().length() == 0) {
            errorMessage += "No valid Plate Number!\n"; count++;
        }
        if (txtregfee.getText() == null || txtregfee.getText().length() == 0) {
            errorMessage += "No valid Registration Fee!\n"; count++;
        }
        if (txtregarea.getText() == null || txtregarea.getText().length() == 0) {
            errorMessage += "No valid Registration Area!\n"; count++;
        }
        if (txtvehicletype.getText() == null || txtvehicletype.getText().length() == 0) {
            errorMessage += "No valid Vehicle Type!\n"; count++;
        }
        if (txttaxamnt.getText() == null || txttaxamnt.getText().length() == 0) {
            errorMessage += "No valid Tax Amount!\n";
        }
        if (txtroadpermit.getText() == null || txtroadpermit.getText().length() == 0) {
            errorMessage += "No valid Road Permit!\n"; count++;
        }
        if (txtperaddress.getText() == null || txtperaddress.getText().length() == 0) {
            errorMessage += "No valid Permanent Address!\n"; count++;
        }
        if (txtcuraddress.getText() == null || txtcuraddress.getText().length() == 0) {
            errorMessage += "No valid Current Address!\n"; count++;
        }

        if (datepickerdob.getSelectedDate() == null) {
            errorMessage += "No valid birthday!\n"; count++;
        } else {
            if (datepickerdob.invalidProperty().get()) {
                errorMessage += "No valid birthday. Use the format dd-mm-yyyy!\n"; count++;
            }
        }

        if (dateofpayment.getSelectedDate() == null) {
            errorMessage += "No valid Date Of Payment!\n"; count++;
        } else {
            if (dateofpayment.invalidProperty().get()) {
                errorMessage += "No valid Date Of Payment. Use the format dd-mm-yyyy!\n"; count++;
            }
        }
        if (validity.getSelectedDate() == null) {
            errorMessage += "No valid Date Of Validity!\n"; count++;
        } else {
            if (validity.invalidProperty().get()) {
                errorMessage += "No valid Date Of Validity. Use the format dd-mm-yyyy!\n"; count++;
            }
        }
        if (dateofreg.getSelectedDate() == null) {
            errorMessage += "No valid Date Of Registration!\n"; count++;
        } else {
            if (dateofreg.invalidProperty().get()) {
                errorMessage += "No valid Date Of Registration. Use the format dd-mm-yyyy!\n"; count++;
            }
        }


        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message
            if(count<4) {
                Dialogs.showInformationDialog(primarystage, errorMessage,
                        "Information Dialog", "Error Message");
            }
            else{
                Dialogs.showInformationDialog(primarystage, "Please Make Sure You Are Giving All The Input ",
                        "Information Dialog", "Error Message");
            }
            return false;
        }
    }

    private void edit(){
        User user=null;
        OwnerDetails owner=null;
        Vehicle vehicle=null;
        VehicleBuild build=null;
        VehicleRegInfo regInfo=null;
        VehicleTax tax=null;
        VehicleStatus status=null;
        int recordno=0,statusid=0,build1=0;
        User1 us1= new User1();
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
            Query query1 = session.createQuery("FROM VehicleTax t INNER JOIN t.reg r INNER JOIN r.vehicle v INNER JOIN v.owner o INNER JOIN v.build b INNER JOIN o.user u WHERE r.registrationId = :regid").setParameter("regid",us1.ereg );
            List<Object[]> list1 = query1.list();

            for (Object object : list1) {
                Object[] li = (Object[]) object;
                for (Object liItem : li) {
                    if (liItem instanceof Vehicle) {
                        build=((Vehicle) liItem).getBuild();
                        owner=((Vehicle) liItem).getOwner();

                    } else if (liItem instanceof VehicleRegInfo) {
                        vehicle=((VehicleRegInfo) liItem).getVehicle();
                    }else if (liItem instanceof VehicleTax) {
                        recordno=((VehicleTax) liItem).getRecordNo();
                        regInfo=((VehicleTax) liItem).getReg();
                    }else if (liItem instanceof OwnerDetails) {
                        user=((OwnerDetails) liItem).getUser();
                    }
                }

            }
            Query query2 = session.createQuery("FROM VehicleStatus s INNER JOIN s.reg r WHERE r.registrationId = :regid").setParameter("regid",us1.ereg );
            List<Object[]> list2 = query2.list();

            for (Object object : list2) {
                Object[] li = (Object[]) object;
                for (Object liItem : li) {
                    if (liItem instanceof VehicleStatus) {
                        statusid=((VehicleStatus) liItem).getStatusId();
                    }
                }
            }

            tax = (VehicleTax)session.get(VehicleTax.class,recordno);
            status=(VehicleStatus)session.get(VehicleStatus.class,statusid);
            if (!(txtownername.getText().equals(us1.ownername))) {
                owner.setName(txtownername.getText());
                session.update(owner);
            }
            if (!(txtnid.getText().equals(us1.nid))) {
                owner.setNationalId(Integer.parseInt(txtnid.getText()));
                session.update(owner);
            }
            if (!(datepickerdob.getSelectedDate().equals(us1.dob))) {
                owner.setDob(datepickerdob.getSelectedDate());
                session.update(owner);
            }
            if (!(txtperaddress.getText().equals(us1.per_address))) {
                owner.setPermAddrss(txtperaddress.getText());
                session.update(owner);
            }
            if (!(txtcuraddress.getText().equals(us1.cur_address))) {
                owner.setCurrAddrss(txtcuraddress.getText());
                session.update(owner);
            }
            if (!(txtphn.getText().equals(us1.phn))) {
                HashSet set1 = new HashSet();
                for (String retval : txtphn.getText().split(",")) {
                    set1.add(new Phone(retval));
                }
                owner.setPhones(set1);
                session.update(owner);
            }
            if (!(txtuser.getText().equals(us1.user))) {
                user.setUsername(txtuser.getText());
                session.update(user);
            }
            if (!(txtpass.getText().equals(us1.pass))) {
                user.setPassword(txtpass.getText());
                session.update(user);
            }
            if (!(chcrank.getValue().toString().equals(us1.rank))) {
                user.setPassword(chcrank.getValue().toString());
                session.update(user);
            }
            if (!(txtengine.equals(us1.eng_num))) {
                vehicle.setEngineNum(txtengine.getText());
                session.update(vehicle);
            }
            if (!(txtchasis.equals(us1.chasis_num))) {
                vehicle.setChasisNum(txtchasis.getText());
                session.update(vehicle);
            }
            if (!(txtcolor.equals(us1.color))) {
                vehicle.setEngineNum(txtcolor.getText());
                session.update(vehicle);
            }
            if (!(validity.getSelectedDate().equals(us1.valid_till))) {
                vehicle.setValidTill(validity.getSelectedDate());
                session.update(vehicle);
            }
            if (!(txtplate.getText().equals(us1.plate_num))) {
                regInfo.setPlateNum(Integer.parseInt(txtplate.getText()));
                session.update(regInfo);
            }
            if (!(txtregfee.getText().equals(us1.reg_fee))) {
                regInfo.setRegFee(Integer.parseInt(txtregfee.getText()));
                session.update(regInfo);
            }
            if (!(txtregarea.getText().equals(us1.reg_area))) {
                regInfo.setRegArea(txtregarea.getText());
                session.update(regInfo);
            }
            if (!(txtvehicletype.getText().equals(us1.vehicletype))) {
                regInfo.setVehicleType(txtvehicletype.getText());
                session.update(regInfo);
            }
            if (!(dateofreg.getSelectedDate().equals(us1.date_of_reg))) {
                regInfo.setDateOfReg(dateofreg.getSelectedDate());
                session.update(regInfo);
            }
            if (!(txttaxamnt.getText().equals(us1.tax_amount))) {
                tax.setTaxAmount(Integer.parseInt(txttaxamnt.getText()));
                session.update(tax);
            }
            if (!(dateofpayment.getSelectedDate().equals(us1.date_of_payment))) {
                tax.setDateOfPayment(dateofpayment.getSelectedDate());
                session.update(tax);
            }
            if (!(txtroadpermit.getText().equals(us1.road_permit))) {
                status.setRoadPermit(txtroadpermit.getText());
                session.update(status);
            }
            if (!(chcoperstatus.getValue().toString().equals(us1.opstatus))) {
                status.setOperationalStatus(chcoperstatus.getValue().toString());
                session.update(status);
            }
            if (!(txtmanufacturer.getText().equals(us1.manufacturer))) {
                build1=1;
            }else if(!(txtengtype.getText().equals(us1.engtype))){
                build1=1;
            }else if(!(txtfueltype.getText().equals(us1.fueltype))){
                build1=1;
            }else if(!(txtwheeltye.getText().equals(us1.wheeltype))){
                build1=1;
            }else if(!(txtmodel.getText().equals(us1.model))){
                build1=1;
            }else if(!(txtyrmanufacture.getText().equals(us1.yr_of_manufacture))){
                build1=1;
            }

            if(build1==1){
                Query query = session.createQuery("FROM VehicleBuild b WHERE b.manufacturer = :manu AND b.engineType = :eng AND b.fuelType = :fuel AND b.wheelType = :wheel AND b.model = :model AND b.yearOfManufacture = :yrmanu ")
                        .setParameter("manu", txtmanufacturer.getText()).setParameter("eng", txtengtype.getText()).setParameter("fuel", txtfueltype.getText()).setParameter("wheel", txtwheeltye.getText())
                        .setParameter("model", txtmodel.getText()).setParameter("yrmanu", txtyrmanufacture.getText());
                List<VehicleBuild> builds = query.list();
                if (builds.size() > 0) {
                    build = builds.get(0);
                    vehicle.setBuild(build);
                    session.update(vehicle);

                } else {
                    build = new VehicleBuild(txtmanufacturer.getText(), txtengtype.getText(), txtfueltype.getText(), txtwheeltye.getText(), txtmodel.getText(), txtyrmanufacture.getText());
                    session.save(build);
                    vehicle.setBuild(build);
                    session.update(vehicle);
                }
            }
            tx.commit();
            Dialogs.showInformationDialog(primarystage, "Successfully Edited ",
                    "Information Dialog", "Message");
            User1.edit=0;
            clear();

        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

    public void clear(){
        txtownername.setText("");
        txtnid.setText("");
        txtphn.setText("");
        txtuser.setText("");
        txtengine.setText("");
        txtchasis.setText("");
        txtcolor.setText("");
        txtmanufacturer.setText("");
        txtengtype.setText("");
        txtfueltype.setText("");
        txtwheeltye.setText(""); txtmodel.setText("");
        txtyrmanufacture.setText("");
        txtplate.setText("");
        txtregfee.setText("");
        txtregarea.setText("");
        txtvehicletype.setText("");
        txttaxamnt.setText("");
        txtroadpermit.setText("");
        txtpass.setText("");
        txtperaddress.setText("");
        txtcuraddress.setText("");
        datepickerdob.setSelectedDate(null);
        validity.setSelectedDate(null);
        dateofpayment.setSelectedDate(null);
        dateofreg.setSelectedDate(null);
        chcrank.setValue(1);
        chcoperstatus.setValue("active");
    }
}

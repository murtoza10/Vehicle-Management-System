package sample;

import edu.sust.db.*;
import eu.schudt.javafx.controls.calendar.DatePicker;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialogs;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Murtoza10 on 11/16/2014.
 */
public class AddVehicle implements Initializable,
        ControlledScreen {

    ScreensController myController;
    private static SessionFactory factory;
    private static ServiceRegistry serviceRegistry;
    Stage primarystage;

    @FXML
    TextField vtxtuser, vengnum, vchasis, vcolor, vmanu,vengtyp, vfueltyp,
            vwheeltyp, vmodel, vyrmanu, vplatenum,vregfee, vregarea,
            vvehicletyp, vtax, vroadperm;

    @FXML
    DatePicker vdatereg,vdatepay,vvalidity;

    @FXML
    ChoiceBox vchcoperational;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        vchcoperational.setItems(FXCollections.observableArrayList("active", "inactive"));
        vchcoperational.setValue("active");
    }

    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }

    @FXML
    private void SaveVehicle(ActionEvent event){

        int own=0;
        VehicleBuild build;
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
            Query query = session.createQuery("FROM OwnerDetails o INNER JOIN o.user u WHERE u.username = :uname").setParameter("uname", vtxtuser.getText());
            List<Object[]> list = query.list();

            for (Object object : list) {
                Object[] li = (Object[]) object;
                for (Object liItem : li) {

                    if (liItem instanceof OwnerDetails) {
                        own = ((OwnerDetails) liItem).getOwnerId();
                    }
                }
            }

            OwnerDetails owner=(OwnerDetails)session.get(OwnerDetails.class, own);
            Query query1 = session.createQuery("FROM VehicleBuild b WHERE b.manufacturer = :manu AND b.engineType = :eng AND b.fuelType = :fuel AND b.wheelType = :wheel AND b.model = :model AND b.yearOfManufacture = :yrmanu ")
                    .setParameter("manu", vmanu.getText()).setParameter("eng", vengtyp.getText()).setParameter("fuel", vfueltyp.getText()).setParameter("wheel", vwheeltyp.getText())
                    .setParameter("model", vmodel.getText()).setParameter("yrmanu", vyrmanu.getText());
            List<VehicleBuild> builds = query1.list();
            if (builds.size() > 0) {
                build = builds.get(0);

            } else {
                build = new VehicleBuild(vmanu.getText(), vengtyp.getText(), vfueltyp.getText(), vwheeltyp.getText(), vmodel.getText(), vyrmanu.getText());
                session.save(build);
            }
            Vehicle vehicle = new Vehicle(vengnum.getText(), vchasis.getText(), vvalidity.getSelectedDate(), vcolor.getText(), owner, build);
            session.save(vehicle);
            VehicleRegInfo reg = new VehicleRegInfo(Integer.parseInt(vplatenum.getText()), vdatereg.getSelectedDate(), Integer.parseInt(vregfee.getText()), vregarea.getText(), vvehicletyp.getText(), vehicle);
            session.save(reg);
            VehicleTax tax = new VehicleTax(Integer.parseInt(vtax.getText()), vdatepay.getSelectedDate(), reg);
            session.save(tax);
            VehicleStatus status = new VehicleStatus(vroadperm.getText(), vchcoperational.getValue().toString(), reg);
            session.save(status);

            tx.commit();
            Dialogs.showInformationDialog(primarystage, "Successfully Vehicle Added ",
                    "Information Dialog", "Message");
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }

    }
    @FXML
    private void ClearData(ActionEvent event){
        vtxtuser.setText("");
        vengnum.setText("");
        vchasis.setText("");
        vcolor.setText("");
        vmanu.setText("");
        vengtyp.setText("");
        vfueltyp.setText("");
        vwheeltyp.setText("");
        vmodel.setText("");
        vyrmanu.setText("");
        vplatenum.setText("");
        vregfee.setText("");
        vregarea.setText("");
        vvehicletyp.setText("");
        vtax.setText("");
        vroadperm.setText("");
        vdatepay.setSelectedDate(null);
        vdatereg.setSelectedDate(null);
        vvalidity.setSelectedDate(null);
        vchcoperational.setValue("active");
    }
    @FXML
    private void BackToUser(ActionEvent event){
        UserLogin user=new UserLogin();
        //System.out.println(user.rank);
        if(user.rank==1) {
            myController.setScreen(ScreensFramework.User1_SCREEN);
            User1 user1= (User1)ScreensFramework.user1;
            user1.table1.getSelectionModel().clearSelection();
            user1.table1.getItems().removeAll();
            user1.data.clear();
            user1.AddTableData();
        }
        else if(user.rank==2){
            myController.setScreen(ScreensFramework.User2_SCREEN);
            User2 user2= (User2)ScreensFramework.user2;
            user2.table2.getSelectionModel().clearSelection();
            user2.table2.getItems().removeAll();
            user2.data.clear();
            user2.AddTableData();
        }
        else
        {
            myController.setScreen(ScreensFramework.User3_SCREEN);
        }


    }

}

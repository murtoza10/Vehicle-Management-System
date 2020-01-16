package sample;

import edu.sust.db.User;
import edu.sust.db.Vehicle;
import edu.sust.db.VehicleRegInfo;
import edu.sust.db.VehicleTax;
import eu.schudt.javafx.controls.calendar.DatePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Murtoza10 on 11/15/2014.
 */
public class AddTransaction implements Initializable,
        ControlledScreen {
    ScreensController myController;
    private static SessionFactory factory;
    private static ServiceRegistry serviceRegistry;

    @FXML
    TextField atregid,attax;

    @FXML
    DatePicker atdatepay,atvalidity;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializedate(atdatepay);
        initializedate(atvalidity);
    }
    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

    @FXML
    public void SaveTran(ActionEvent event){
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
            VehicleRegInfo reg = (VehicleRegInfo)session.get(VehicleRegInfo.class, atregid.getText());
            VehicleTax tax = new VehicleTax(Integer.parseInt(attax.getText()), atdatepay.getSelectedDate(), reg);
            session.save(tax);
            Vehicle vehicle = reg.getVehicle();
            vehicle.setValidTill(atvalidity.getSelectedDate());
            session.update(vehicle);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @FXML
    public void ClearData(ActionEvent event){
        atregid.setText("");
        attax.setText("");
        atdatepay.setSelectedDate(null);
        atvalidity.setSelectedDate(null);
    }

    @FXML
    public void BackToTran(ActionEvent event){
        myController.setScreen(ScreensFramework.User12trans_SCREEN);
        User12Transaction user12tran= (User12Transaction)ScreensFramework.user12trans;
        user12tran.trantable12.getSelectionModel().clearSelection();
        user12tran.trantable12.getItems().removeAll();
        user12tran.data.clear();
        user12tran.AddTranTableData();
    }

    public void initializedate(DatePicker DP){
        DP = new DatePicker(Locale.ENGLISH);
        DP.setDateFormat(new SimpleDateFormat("dd-MM-yyyy"));
        DP.getCalendarView().todayButtonTextProperty().set("Today");
        DP.getCalendarView().setShowWeeks(false);
        DP.getStylesheets().add("Datepicker.css");

    }
}

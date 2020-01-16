package sample;

import edu.sust.db.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Murtoza10 on 8/27/2014.
 */
public class User12Transaction implements Initializable,
        ControlledScreen {

    ScreensController myController;
    private static SessionFactory factory;
    private static ServiceRegistry serviceRegistry;
    Stage primarystage;

    @FXML
    Label treg12,ttaxamount12,tdateofpay12;

    @FXML
    TableView<Transaction1>trantable12;

    @FXML
    TableColumn<Transaction1,Integer>tranrec12;

    @FXML
    TableColumn<Transaction1,Integer>tranreg12;

    @FXML
    TableColumn<Transaction1,Integer>trantax12;

    @FXML
    TableColumn<Transaction1,String>trandateofpay12;

    int j=0;

    final ObservableList<Transaction1> data = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tranrec12.setCellValueFactory(new PropertyValueFactory<Transaction1, Integer>("recno"));
        tranreg12.setCellValueFactory(new PropertyValueFactory<Transaction1, Integer>("regId"));
        trantax12.setCellValueFactory(new PropertyValueFactory<Transaction1, Integer>("tax"));
        trandateofpay12.setCellValueFactory(new PropertyValueFactory<Transaction1, String>("dateofpay"));

        trantable12.setItems(data);

        trantable12.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Transaction1>() {

            @Override
            public void changed(ObservableValue<? extends Transaction1> observable,
                                Transaction1 oldValue, Transaction1 newValue) {
                ShowLabelTran12();
            }
        });
    }

    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

    @FXML
    private void BackToUser(ActionEvent event){
        UserLogin user=new UserLogin();
        //System.out.println(user.rank);
        if(user.rank==1) {
            myController.setScreen(ScreensFramework.User1_SCREEN);
        }
        else if(user.rank==2){
            myController.setScreen(ScreensFramework.User2_SCREEN);
        }
        else
        {
            myController.setScreen(ScreensFramework.User3_SCREEN);
        }

    }

    public void SettingLabelTran12() {

        int own=0;
        User2 user12=new User2();

        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        Session session = factory.openSession();
        org.hibernate.Transaction tx = null;
        try {
            tx = session.beginTransaction();

            Query query1 = session.createQuery("FROM VehicleTax t INNER JOIN t.reg r INNER JOIN r.vehicle v ");

            List<Object[]> list1 = query1.list();

//            for (Object object : list1) {
//                Object[] li = (Object[])object;
                for(Object liItem:list1.get(0)){
                    if (liItem instanceof VehicleTax) {
                        ttaxamount12.setText(Integer.toString(((VehicleTax) liItem).getTaxAmount()));
                        System.out.println(Integer.toString(((VehicleTax) liItem).getTaxAmount()));
                        String pattern = "dd-MM-yyyy";
                        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
                        tdateofpay12.setText(formatter.format(((VehicleTax) liItem).getDateOfPayment()));

                    }else if(liItem instanceof VehicleRegInfo){
                        treg12.setText(Integer.toString(((VehicleRegInfo) liItem).getRegistrationId()));
                    }

                }
//            }

            tx.commit();
            if(j==0){
                AddTranTableData();
                j++;
            }

        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

    public void AddTranTableData(){
        int reg=0,tax=0,rec=0;
        String dateofpay=new String();
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        Session session = factory.openSession();
        org.hibernate.Transaction tx = null;
        try {
            tx = session.beginTransaction();

            Query query1 = session.createQuery("FROM VehicleTax t INNER JOIN t.reg r");

            List<Object[]> list1 = query1.list();

            for (Object object : list1) {
                Object[] li = (Object[])object;
                for(Object liItem:li){
                    if (liItem instanceof VehicleTax) {
                        rec=(((VehicleTax) liItem).getRecordNo());
                        tax=(((VehicleTax) liItem).getTaxAmount());
                        String pattern = "dd-MM-yyyy";
                        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
                        dateofpay=(formatter.format(((VehicleTax) liItem).getDateOfPayment()));

                    }else if(liItem instanceof VehicleRegInfo){
                        reg=((VehicleRegInfo) liItem).getRegistrationId();
                    }

                }
                Transaction1 entry =new Transaction1(rec,reg,tax,dateofpay);
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
    private  void GoToAddTransaction(ActionEvent event){
        myController.setScreen(ScreensFramework.User12addtrans_SCREEN);
    }

    public void ShowLabelTran12() {

        int own=0;
        User2 user12=new User2();
        Transaction1 selectedtable = trantable12.getSelectionModel().getSelectedItem();
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        Session session = factory.openSession();
        org.hibernate.Transaction tx = null;
        try {
            tx = session.beginTransaction();

            Query query1 = session.createQuery("FROM VehicleTax t INNER JOIN t.reg r INNER JOIN r.vehicle v WHERE r.registrationId = :regid").setParameter("regid",selectedtable.getRegId());

            List<Object[]> list1 = query1.list();

            for (Object object : list1) {
                Object[] li = (Object[])object;
            for(Object liItem:list1.get(0)){
                if (liItem instanceof VehicleTax) {
                    ttaxamount12.setText(Integer.toString(((VehicleTax) liItem).getTaxAmount()));
                    System.out.println(Integer.toString(((VehicleTax) liItem).getTaxAmount()));
                    String pattern = "dd-MM-yyyy";
                    SimpleDateFormat formatter = new SimpleDateFormat(pattern);
                    tdateofpay12.setText(formatter.format(((VehicleTax) liItem).getDateOfPayment()));

                }else if(liItem instanceof VehicleRegInfo){
                    treg12.setText(Integer.toString(((VehicleRegInfo) liItem).getRegistrationId()));
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

    }

}

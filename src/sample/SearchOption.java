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
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Murtoza10 on 11/16/2014.
 */
public class SearchOption implements Initializable,
        ControlledScreen {

    ScreensController myController;
    private static SessionFactory factory;
    private static ServiceRegistry serviceRegistry;
    Stage primarystage;

    @FXML
    TextField txtssearch;

    @FXML
    ChoiceBox chcsoption;

    @FXML
    TableView<Table2> stable2;

    @FXML
    TableColumn<Table2,Integer> sreg2;

    @FXML
    TableColumn<Table2,String>smanufacturer2;

    @FXML
    TableColumn<Table2,String>smodel2;

    @FXML
    TableColumn<Table2,String>scolor2;

    @FXML
    TableColumn<Table2,String>svalidity2;

    public static int s=0;

    final ObservableList<Table2> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sreg2.setCellValueFactory(new PropertyValueFactory<Table2, Integer>("regId"));
        smanufacturer2.setCellValueFactory(new PropertyValueFactory<Table2, String>("manufacturer"));
        smodel2.setCellValueFactory(new PropertyValueFactory<Table2, String>("model"));
        scolor2.setCellValueFactory(new PropertyValueFactory<Table2, String>("color"));
        svalidity2.setCellValueFactory(new PropertyValueFactory<Table2, String>("validity"));
        stable2.setItems(data);

        chcsoption.setItems(FXCollections.observableArrayList("OwnerName","Registration ID","Engine Number","Plate Number"));
        chcsoption.setValue("OwnerName");

        stable2.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Table2>() {

            @Override
            public void changed(ObservableValue<? extends Table2> observable,
                                Table2 oldValue, Table2 newValue) {

                FullView();
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
            User1 search = (User1) ScreensFramework.user1;
            search.chcsoption1.setValue(chcsoption.getValue().toString());
            search.txtsearch1.setText(txtssearch.getText());
        }
        else if(user.rank==2){
            myController.setScreen(ScreensFramework.User2_SCREEN);
            User2 search = (User2) ScreensFramework.user2;
            search.chcsoption2.setValue(chcsoption.getValue().toString());
            search.txtsearch2.setText(txtssearch.getText());
        }
        else
        {
            myController.setScreen(ScreensFramework.User3_SCREEN);
        }

    }

    @FXML
    private void Search(ActionEvent event){
        int reg=0;
        String manufacturer=new String();
        String model=new String();
        String color=new String();
        String validity=new String();
        stable2.getItems().removeAll();
        data.clear();
        if(data.isEmpty()){
            System.out.println("Empty");
        }
        else {
            System.out.println(data);
        }
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
            if(chcsoption.getValue().toString().equals("OwnerName")){
                query1 = session.createQuery("FROM VehicleRegInfo r INNER JOIN r.vehicle v INNER JOIN v.owner o INNER JOIN v.build b WHERE o.name = :own").setParameter("own",txtssearch.getText());
            }
            if(chcsoption.getValue().toString().equals("Registration ID")){
                query1 = session.createQuery("FROM VehicleRegInfo r INNER JOIN r.vehicle v INNER JOIN v.owner o INNER JOIN v.build b WHERE r.registrationId = :regid").setParameter("regid",Integer.parseInt(txtssearch.getText()));
            }
            if(chcsoption.getValue().toString().equals("Engine Number")){
                query1 = session.createQuery("FROM VehicleRegInfo r INNER JOIN r.vehicle v INNER JOIN v.owner o INNER JOIN v.build b WHERE v.engineNum = :engnum").setParameter("engnum",txtssearch.getText());
            }
            if(chcsoption.getValue().toString().equals("Plate Number")){
                query1 = session.createQuery("FROM VehicleRegInfo r INNER JOIN r.vehicle v INNER JOIN v.owner o INNER JOIN v.build b WHERE r.plateNum = :platenum").setParameter("platenum",Integer.parseInt(txtssearch.getText()));
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
                data.add(entry);
            }

            tx.commit();
            //stable2.setItems(data);
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    private void FullView() {
        User user = null;
        int uid = 0;
        int selectedIndex = stable2.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Table2 selectedtable = stable2.getSelectionModel().getSelectedItem();
            System.out.println(selectedtable.getRegId());
            s=1;
            myController.setScreen(ScreensFramework.FullView_SCREEN);
            FullView fullview = (FullView) ScreensFramework.fullview;
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

}

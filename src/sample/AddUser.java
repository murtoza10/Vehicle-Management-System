package sample;

import edu.sust.db.User;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialogs;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Murtoza10 on 11/15/2014.
 */
public class AddUser implements Initializable,
        ControlledScreen {
    ScreensController myController;
    private static SessionFactory factory;
    private static ServiceRegistry serviceRegistry;
    Stage primarystage;

    @FXML
    TextField uauser;

    @FXML
    PasswordField uapass;

    @FXML
    ChoiceBox uachcrank;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        uachcrank.setItems(FXCollections.observableArrayList(1, 2, 3));
        uachcrank.setValue(1);
    }
    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

    @FXML
    private void SaveUser(ActionEvent event) {
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        Session session = factory.openSession();
        Transaction tx = null;

        UserLoginQuery Ulq = new UserLoginQuery();
        User user1 = Ulq.FindUserByUsername(uauser.getText());
        if (user1 == null) {
            try {
                tx = session.beginTransaction();
                User user = new User(uauser.getText(), uapass.getText(), Integer.parseInt(uachcrank.getValue().toString()));
                session.save(user);
                tx.commit();
                Dialogs.showInformationDialog(primarystage, "Successfully Saved ",
                        "Information Dialog", "Message");


            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                e.printStackTrace();
            } finally {
                session.close();
            }

        }
        else{
            Dialogs.showInformationDialog(primarystage, "This Username Already Exists ",
                    "Information Dialog", "Message");
        }
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

}

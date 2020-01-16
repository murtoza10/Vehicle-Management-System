package sample;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.beans.property.Property;
/**
 * Created by Murtoza10 on 8/27/2014.
 */
public class ScreensFramework extends Application {


    @FXML
    Label errorlabel;

    public static ControlledScreen userlogin;
    public static ControlledScreen user1;
    public static ControlledScreen user2;
    public static ControlledScreen user3;
    public static ControlledScreen user12trans;
    public static ControlledScreen user1adduser;
    public static ControlledScreen user12newreg;
    public static ControlledScreen user12addtrans;
    public static ControlledScreen fullview;
    public static ControlledScreen search;
    public static ControlledScreen addvehicle;

    public static final String MAIN_SCREEN = "user";
    public static final String MAIN_SCREEN_FXML = "userlogin.fxml";
    public static final String User1_SCREEN = "user1";
    public static final String User1_SCREEN_FXML =
            "User6.fxml";
    public static final String User2_SCREEN = "user2";
    public static final String User2_SCREEN_FXML =
            "User2.fxml";
    public static final String User3_SCREEN = "user3";
    public static final String User3_SCREEN_FXML =
            "User3.fxml";
    public static final String User1adduser_SCREEN = "user1adduser";
    public static final String User1adduser_SCREEN_FXML =
            "User1-AddUser.fxml";

    public static final String User1_2newreg_SCREEN = "user1-2newreg";
    public static final String User1_2newreg_SCREEN_FXML =
            "User1_2-New Reg.fxml";
    public static final String User12trans_SCREEN = "user1-2trans";
    public static final String User12trans_SCREEN_FXML =
            "User1_2Transaction.fxml";
    public static final String User12addtrans_SCREEN = "user1-2addtrans";
    public static final String User12addtrans_SCREEN_FXML =
            "AddTransaction.fxml";
    public static final String FullView_SCREEN = "fullview";
    public static final String FullView_SCREEN_FXML =
            "FullView.fxml";
    public static final String Search_SCREEN = "Search";
    public static final String Search_SCREEN_FXML =
            "Search.fxml";
    public static final String AddVehicle_SCREEN = "AddVehicle";
    public static final String AddVehicle_SCREEN_FXML =
            "AddVehicle.fxml";

    @Override
    public void start(Stage primaryStage) {

        ScreensController mainContainer = new ScreensController();
        userlogin=mainContainer.loadScreen(ScreensFramework.MAIN_SCREEN,
                ScreensFramework.MAIN_SCREEN_FXML);
        user1=mainContainer.loadScreen(ScreensFramework.User1_SCREEN,
                ScreensFramework.User1_SCREEN_FXML);
        user2=mainContainer.loadScreen(ScreensFramework.User2_SCREEN,
                ScreensFramework.User2_SCREEN_FXML);
        user3=mainContainer.loadScreen(ScreensFramework.User3_SCREEN,
                ScreensFramework.User3_SCREEN_FXML);
        user1adduser=mainContainer.loadScreen(ScreensFramework.User1adduser_SCREEN,
                ScreensFramework.User1adduser_SCREEN_FXML);
        user12newreg=mainContainer.loadScreen(ScreensFramework.User1_2newreg_SCREEN,
                ScreensFramework.User1_2newreg_SCREEN_FXML);
        user12trans=mainContainer.loadScreen(ScreensFramework.User12trans_SCREEN,
                ScreensFramework.User12trans_SCREEN_FXML);
        user12addtrans=mainContainer.loadScreen(ScreensFramework.User12addtrans_SCREEN,
                ScreensFramework.User12addtrans_SCREEN_FXML);
        fullview=mainContainer.loadScreen(ScreensFramework.FullView_SCREEN,
                ScreensFramework.FullView_SCREEN_FXML);
        search=mainContainer.loadScreen(ScreensFramework.Search_SCREEN,
                ScreensFramework.Search_SCREEN_FXML);
        addvehicle=mainContainer.loadScreen(ScreensFramework.AddVehicle_SCREEN,
                ScreensFramework.AddVehicle_SCREEN_FXML);

        mainContainer.setScreen(ScreensFramework.MAIN_SCREEN);


        Group root = new Group();
        root.getChildren().addAll(mainContainer);

        root.autosize();
        Scene scene = new Scene(root,1350,690);
//        scene.getStylesheets().add("Style1.css");
        root.scaleXProperty().bind( scene.widthProperty().divide(mainContainer.origW) );
        root.scaleYProperty().bind( scene.heightProperty().divide( mainContainer.origH ) );
        //root.setAutoSizeChildren(true);
        //scene.getWindow().sizeToScene();
        primaryStage.sizeToScene();
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);

        //primaryStage.setResizable(false);
        primaryStage.show();
    }
}
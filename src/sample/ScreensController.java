package sample;

import edu.sust.db.OwnerDetails;
import edu.sust.db.Phone;
import edu.sust.db.User;
import edu.sust.db.Vehicle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.event.EventHandler;
import javafx.util.Duration;

import javafx.event.ActionEvent;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javafx.animation.KeyValue;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Created by Murtoza10 on 8/27/2014.
 */
public class ScreensController extends StackPane {
    private HashMap<String, Node> screens = new HashMap<String, Node>();
    public Region contentRootRegion;
    public double origW,origH=500;
    ControlledScreen myScreenControler;
    @FXML AnchorPane mainContent;

//    @FXML
//    Label name,nid,ownerid,dob,curraddrss,permaddrss,phn,regnum,enginenum,chasisnum,platenum,color,manufacturer,model;

//    private static SessionFactory factory;
//    private static ServiceRegistry serviceRegistry;
    public  ScreensController(){
        super();
    }
    public void addScreen(String name, Node screen) {
        screens.put(name, screen);
    }

    public Node getScreen(String name){
        return screens.get(name);
    }
    public ControlledScreen loadScreen(String name, String resource) {
        try {
            FXMLLoader myLoader = new
                    FXMLLoader(getClass().getResource(resource));
            contentRootRegion = (Region) myLoader.load();
            //Set a default "standard" or "100%" resolution
            origW = 1366;
            origH = 500;

            //If the Region containing the GUI does not already have a preferred width and height, set it.
            //But, if it does, we can use that setting as the "standard" resolution.
            if ( contentRootRegion.getPrefWidth() == Region.USE_COMPUTED_SIZE )
                contentRootRegion.setPrefWidth( origW );
            else
                origW = contentRootRegion.getPrefWidth();

            if ( contentRootRegion.getPrefHeight() == Region.USE_COMPUTED_SIZE )
                contentRootRegion.setPrefHeight( origH );
            else
                origH = contentRootRegion.getPrefHeight();

//            try {
//
//                AnchorPane.setTopAnchor(contentRootRegion, 0.0);
//                AnchorPane.setRightAnchor(contentRootRegion, 0.0);
//                AnchorPane.setLeftAnchor(contentRootRegion, 0.0);
//                AnchorPane.setBottomAnchor(contentRootRegion, 0.0);
//                mainContent.getChildren().setAll(n);
//            } catch (IOException e){
//                System.out.println(e.getMessage());
//            }
            ControlledScreen myScreenControler =
                    ((ControlledScreen) myLoader.getController());
            myScreenControler.setScreenParent(this);



            addScreen(name, contentRootRegion);
            return myScreenControler;
        }catch(Exception e) {
            System.out.println(e.getMessage());
            return myScreenControler;
        }
    }
    public boolean setScreen(final String name) {

        if(screens.get(name) != null) { //screen loaded
            final DoubleProperty opacity = opacityProperty();

            //Is there is more than one screen
            if(!getChildren().isEmpty()){
                Timeline fade = new Timeline(
                        new KeyFrame(Duration.ZERO,
                                new KeyValue(opacity,1.0)),
                        new KeyFrame(new Duration(1),

                                new EventHandler<ActionEvent>() {

                                    @Override
                                    public void handle(ActionEvent t) {
                                        //remove displayed screen
                                        getChildren().remove(0);
                                        //add new screen
                                        getChildren().add(0, screens.get(name));
//                                        UserLogin UL=new UserLogin();
//                                        if(UL.user3==3){
//                                            SettingLabelUser3();
//                                        }
                                        Timeline fadeIn = new Timeline(
                                                new KeyFrame(Duration.ZERO,
                                                        new KeyValue(opacity, 0.0)),
                                                new KeyFrame(new Duration(800),
                                                        new KeyValue(opacity, 1.0)));
                                        fadeIn.play();
                                    }
                                }, new KeyValue(opacity, 0.0)));
                fade.play();
            } else {
                //no one else been displayed, then just show
                setOpacity(0.0);
                getChildren().add(screens.get(name));

//                UserLogin UL=new UserLogin();
//                if(UL.user3==3){
//                    SettingLabelUser3();
//                }
                Timeline fadeIn = new Timeline(
                        new KeyFrame(Duration.ZERO,
                                new KeyValue(opacity, 0.0)),
                        new KeyFrame(new Duration(1),
                                new KeyValue(opacity, 1.0)));
                fadeIn.play();
            }
            return true;
        } else {
            System.out.println("screen hasn't been loaded!\n");
            return false;
        }

    }
    public boolean unloadScreen(String name) {
        if(screens.remove(name) == null) {
            System.out.println("Screen didn't exist");
            return false;
        } else {
            return true;
        }
    }

}

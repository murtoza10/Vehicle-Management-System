package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    TextField txtuser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void onLogin(ActionEvent event){
        System.out.println(txtuser.getText());
    }
}

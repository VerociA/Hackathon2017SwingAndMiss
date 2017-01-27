package com.greenfox.memorycardgame.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by ${rudolfps} on 2017.01.26..
 */
public class FXMLDocumentController {
    @FXML
    private Button btn1;
    @FXML
    private Button btn2;

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        if(event.getSource()==btn1){
            //get reference to the button's stage
            stage=(Stage) btn1.getScene().getWindow();
            //load up OTHER FXML document
            root = FXMLLoader.load(getClass().getResource("memory.fxml"));
        }
        else{
            stage=(Stage) btn2.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        }
        //create a new scene with root and set the stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}

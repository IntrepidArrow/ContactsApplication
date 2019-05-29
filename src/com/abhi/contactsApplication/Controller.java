package com.abhi.contactsApplication;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Optional;

public class Controller {

    @FXML
    private BorderPane mainWindowPane;

    //Add new contact method
    @FXML
    public void handleNewContactEvent(){
        Dialog<ButtonType> dialogPage = new Dialog<>();
        //Contacts application page is the main window from which the dialog page will be called.
        dialogPage.initOwner(mainWindowPane.getScene().getWindow());
        dialogPage.setTitle("Create New Contact");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("contactsDialog.fxml"));

        try{
            dialogPage.getDialogPane().setContent(fxmlLoader.load());
            System.out.println("I have reached here");
        } catch (IOException e){
            System.out.println("Unable to load window");
            e.printStackTrace();
            return;
        }
        //Adding Option buttons to dialog pane
        dialogPage.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialogPage.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        //show and wait command needed or else OK by default and window will not pop-up for display (during Testing)
        Optional<ButtonType> result = dialogPage.showAndWait();
    }
}

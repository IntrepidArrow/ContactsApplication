package com.abhi.contactsApplication;

import com.abhi.contactsApplication.dataModel.Contact;
import com.abhi.contactsApplication.dataModel.ContactData;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Optional;

public class Controller {

    @FXML
    private BorderPane mainWindowPane;
    @FXML
    private TableView<Contact> contactsTable;

    private ContactData contactData;

    public void initialize(){
        //Observable array list made here for data binding purpose
        contactData = new ContactData();
        contactData.loadContacts();
        contactsTable.setItems(contactData.getContacts());
    }
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
            System.out.println("Dialog box open successful.");
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
        if(result.isPresent() && result.get()==ButtonType.OK){
            ContactsDialogController controller = fxmlLoader.getController();
            Contact newContact = controller.makeNewContact();
            contactData.addContacts(newContact);
            contactData.saveContacts();
        }
    }
}

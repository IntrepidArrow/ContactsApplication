package com.abhi.contactsApplication;

import com.abhi.contactsApplication.dataModel.Contact;
import com.abhi.contactsApplication.dataModel.ContactData;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;

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


        //Debug to check elements added to ObservableArrayList in ContactsDaa class.
//        System.out.println(contactData.getContacts().toString());
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
        } else {
            System.out.println("Cancel button pressed: New Contact Dialog.");
        }
    }

    //From drop down menu option: only allow the user to edit given a contact is selected from the contact table
    //Edit window should show all details of that contact to begin with and allow to update to same contact
    @FXML
    public void handleEditContactEvent(){
        if(contactsTable.getItems().size() == 0){
            Alert emptyTableAlert = new Alert(Alert.AlertType.INFORMATION);
            emptyTableAlert.setTitle("No contacts in contact book!");
            emptyTableAlert.setContentText("Enter a contact in table first to perform operations.");
            emptyTableAlert.showAndWait();

            return;
        }

        Contact selectedContact = contactsTable.getSelectionModel().getSelectedItem();

        //Case: No contact selected to edit
        if(selectedContact == null){
            //Add Code for error message/ label/ window here
            Alert nullSelectAlert = new Alert(Alert.AlertType.INFORMATION);
            nullSelectAlert.setTitle("No contact selected.");
            nullSelectAlert.setHeaderText(null);
            nullSelectAlert.setContentText("Select a contact to edit");
            nullSelectAlert.showAndWait();

            return;
        }
        //else: when contact is selected - perform operation
        Dialog<ButtonType> editDialog = new Dialog<>();
        editDialog.initOwner(mainWindowPane.getScene().getWindow());
        editDialog.setTitle("Edit Contact");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("contactsDialog.fxml"));

        try{
            editDialog.getDialogPane().setContent(fxmlLoader.load());

        } catch (IOException e){
            System.out.println("Unable to Load Window");
            e.printStackTrace();
            return;
        }
        //Adding Option buttons to dialog pane
        editDialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        editDialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        ContactsDialogController controller = fxmlLoader.getController();
        controller.showSelectedContactDetails(selectedContact);

        Optional<ButtonType> result = editDialog.showAndWait();
        if(result.isPresent() && result.get()==ButtonType.OK){
            controller.updateContact(selectedContact);
            contactData.saveContacts();
        } else {
            System.out.println("Cancel button pressed: Edit Dialog.");
        }
    }

    @FXML
    public void handleExit(){
        Platform.exit();
    }
}

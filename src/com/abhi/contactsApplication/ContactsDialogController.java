package com.abhi.contactsApplication;

import com.abhi.contactsApplication.dataModel.Contact;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ContactsDialogController {
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField notesField;

    //process details entered on dialog pane and show on the mainWindowPane
    public Contact makeNewContact(){
        //get entered details
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String phoneNumber = phoneNumberField.getText();
        String notes = notesField.getText();

//        System.out.println(firstName+" " + lastName+" " + phoneNumber+" " + notes);

        //Make new contact with entered details
        Contact newContact = new Contact(firstName, lastName, phoneNumber, notes);
//        System.out.println(newContact.toString());
        return newContact;
    }

}

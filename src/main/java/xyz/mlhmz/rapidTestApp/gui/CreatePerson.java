package xyz.mlhmz.rapidTestApp.gui;

import xyz.mlhmz.rapidTestApp.database.dao.DAO;
import xyz.mlhmz.rapidTestApp.database.dao.Persons;
import xyz.mlhmz.rapidTestApp.database.dao.Tests;
import xyz.mlhmz.rapidTestApp.database.entities.Person;
import xyz.mlhmz.rapidTestApp.database.entities.Test;

import javax.swing.*;
import java.util.Date;

public class CreatePerson {
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField addressField;
    private JTextField phoneNumberField;
    private JButton addButton;
    private JLabel phoneNumberLabel;
    private JLabel addressLabel;
    private JLabel lastNameLabel;
    private JLabel firstNameLabel;
    private JPanel panel;

    public JFrame frame;

    public CreatePerson() {
        addButton.addActionListener((event) -> {
            DAO dao;
            Person person = new Person();

            person.setFirstName(firstNameField.getText());
            person.setLastName(lastNameField.getText());
            person.setPhoneNumber(phoneNumberField.getText());
            person.setAddress(addressField.getText());

            dao = new Persons();

            dao.create(person);

            frame.dispose();

            clearFields();


        });
    }

    public void run() {
        frame = new JFrame("Create Person");
        frame.add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    public void clearFields() {
        firstNameField.setText("");
        lastNameField.setText("");
        addressField.setText("");
        phoneNumberField.setText("");
    }
}

package xyz.mlhmz.rapidTestApp.gui;

import xyz.mlhmz.rapidTestApp.database.repositories.Repository;
import xyz.mlhmz.rapidTestApp.database.repositories.Persons;
import xyz.mlhmz.rapidTestApp.database.entities.Person;

import javax.swing.*;

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
            Repository repository;
            Person person = new Person();

            person.setFirstName(firstNameField.getText());
            person.setLastName(lastNameField.getText());
            person.setPhoneNumber(phoneNumberField.getText());
            person.setAddress(addressField.getText());

            repository = new Persons();

            repository.create(person);

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

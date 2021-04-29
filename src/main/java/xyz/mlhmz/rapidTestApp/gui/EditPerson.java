package xyz.mlhmz.rapidTestApp.gui;

import xyz.mlhmz.rapidTestApp.database.dao.DAO;
import xyz.mlhmz.rapidTestApp.database.dao.Persons;
import xyz.mlhmz.rapidTestApp.database.entities.Person;

import javax.swing.*;

public class EditPerson {
    private JTextField firstNameTextBox;
    private JTextField lastNameTextBox;
    private JTextField addressTextBox;
    private JTextField phoneNumberTextBox;
    private JCheckBox firstNameCheckBox;
    private JCheckBox lastNameCheckBox;
    private JCheckBox addressCheckBox;
    private JCheckBox phoneNumberCheckBox;
    private JButton submitBtn;
    private JPanel panel;

    private Long id;
    public JFrame frame;

    public EditPerson() {
        firstNameTextBox.setEnabled(false);
        lastNameTextBox.setEnabled(false);
        addressTextBox.setEnabled(false);
        phoneNumberTextBox.setEnabled(false);

        firstNameCheckBox.addActionListener(e -> {
            if (firstNameCheckBox.isSelected()) {
                firstNameTextBox.setEnabled(true);
            } else {
                firstNameTextBox.setEnabled(false);
            }
        });

        lastNameCheckBox.addActionListener(e -> {
            if (lastNameCheckBox.isSelected()) {
                lastNameTextBox.setEnabled(true);
            } else {
                lastNameTextBox.setEnabled(false);
            }
        });

        addressCheckBox.addActionListener(e -> {
            if (addressCheckBox.isSelected()) {
                addressTextBox.setEnabled(true);
            } else {
                addressTextBox.setEnabled(false);
            }
        });

        phoneNumberCheckBox.addActionListener(e -> {
            if (phoneNumberCheckBox.isSelected()) {
                phoneNumberTextBox.setEnabled(true);
            } else {
                phoneNumberTextBox.setEnabled(false);
            }
        });

        submitBtn.addActionListener(e ->  {
            DAO dao = new Persons();
            Person person = new Person();

            if (firstNameCheckBox.isSelected()) {
                person.setFirstName(firstNameTextBox.getText());
            }

            if (lastNameCheckBox.isSelected()) {
                person.setLastName(lastNameTextBox.getText());
            }

            if (addressCheckBox.isSelected()) {
                person.setAddress(addressTextBox.getText());
            }

            if (phoneNumberCheckBox.isSelected()) {
                person.setPhoneNumber(phoneNumberTextBox.getText());
            }

            dao.update(id, person);
            frame.dispose();
        });



    }

    public void run(Person person) {
        frame = new JFrame();
        frame.add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);

        // Set Persons Data
        firstNameTextBox.setText(person.getFirstName());
        lastNameTextBox.setText(person.getLastName());
        addressTextBox.setText(person.getAddress());
        phoneNumberTextBox.setText(person.getPhoneNumber());
        id = person.getId();
    }
}

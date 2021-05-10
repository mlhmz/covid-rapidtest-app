package xyz.mlhmz.rapidTestApp.gui;

import xyz.mlhmz.rapidTestApp.database.repositories.Repository;
import xyz.mlhmz.rapidTestApp.database.repositories.Persons;
import xyz.mlhmz.rapidTestApp.database.repositories.Tests;
import xyz.mlhmz.rapidTestApp.database.entities.Person;
import xyz.mlhmz.rapidTestApp.database.entities.Test;

import javax.swing.*;
import java.util.Date;

public class CreateTest {
    private JTextField textField1;
    private JCheckBox positiveCheckBox;
    private JButton submitButton;
    private JLabel personLabel;
    private JPanel panel;
    private JList personList;
    public JFrame frame;
    private DefaultListModel personListModel;

    public CreateTest() {
        personLabel.setText("Create a Person");
        submitButton.addActionListener((event) -> {
            if (personList.isSelectionEmpty()) {
                JOptionPane.showMessageDialog(null, "Please choose a Person you want to submit a Rapid Test with.");
                return;
            }

            Repository repository;
            Test test = new Test();
            test.setPositive(positiveCheckBox.isSelected());
            test.setTestDate(new Date());
            Person person = (Person) personList.getSelectedValue();
            test.setPersonId(person.getId());
            repository = new Tests();

            repository.create(test);

            frame.dispose();


        });

        personList.setLayoutOrientation(JList.VERTICAL);
    }

    public void run() {
        reloadList();
        frame = new JFrame("Create Test");
        frame.add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    public void reloadList() {
        Repository repository;
        repository = new Persons();

        personListModel = new DefaultListModel<Person>();
        personListModel.addAll(repository.get());

        personList.setModel(personListModel);
    }
}

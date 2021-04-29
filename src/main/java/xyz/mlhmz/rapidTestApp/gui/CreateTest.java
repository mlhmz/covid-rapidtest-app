package xyz.mlhmz.rapidTestApp.gui;

import xyz.mlhmz.rapidTestApp.database.dao.DAO;
import xyz.mlhmz.rapidTestApp.database.dao.Persons;
import xyz.mlhmz.rapidTestApp.database.dao.Tests;
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

            DAO dao;
            Test test = new Test();
            test.setPositive(positiveCheckBox.isSelected());
            test.setTestDate(new Date());
            Person person = (Person) personList.getSelectedValue();
            test.setPersonId(person.getId());
            dao = new Tests();

            dao.create(test);

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
        DAO dao;
        dao = new Persons();

        personListModel = new DefaultListModel<Person>();
        personListModel.addAll(dao.get());

        personList.setModel(personListModel);
    }
}

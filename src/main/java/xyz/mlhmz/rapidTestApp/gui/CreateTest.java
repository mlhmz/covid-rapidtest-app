package xyz.mlhmz.rapidTestApp.gui;

import xyz.mlhmz.rapidTestApp.database.repositories.Repository;
import xyz.mlhmz.rapidTestApp.database.repositories.Persons;
import xyz.mlhmz.rapidTestApp.database.repositories.Tests;
import xyz.mlhmz.rapidTestApp.database.entities.Person;
import xyz.mlhmz.rapidTestApp.database.entities.Test;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Date;

public class CreateTest {
    private JTextField textField1;
    private JCheckBox positiveCheckBox;
    private JButton submitButton;
    private JLabel personLabel;
    private JPanel panel;
    private JTable personTable;
    public JFrame frame;
    private DefaultListModel personListModel;

    public CreateTest() {
        personLabel.setText("Create a Person");
        submitButton.addActionListener((event) -> {
            if (personTable.getSelectionModel().isSelectionEmpty()) {
                JOptionPane.showMessageDialog(null, "Please choose a Person you want to submit a Rapid Test with.");
                return;
            }

            Repository repository;
            Test test = new Test();
            test.setPositive(positiveCheckBox.isSelected());
            test.setTestDate(new Date());
            repository = new Persons();
            Long personId = Long.parseLong(personTable.getValueAt(personTable.getSelectedRow(), 0).toString());
            Person person = (Person) repository.getById(personId);
            test.setPersonId(person.getId());
            repository = new Tests();

            repository.create(test);

            frame.dispose();


        });
    }

    public void run() {
        reloadTable();
        frame = new JFrame("Create Test");
        frame.add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    public void reloadTable() {
        Repository repository;

        // JTable Content
        DefaultTableModel newTableModel = new DefaultTableModel() {
            public boolean isCellEditable(int rowIndex, int mColIndex) {
                return false;
            }
        };

        String[] tableColumns = {
                "Person ID",
                "First Name",
                "Last Name",
                "Address",
                "Phone Number"
        };


        for (String column : tableColumns) {
            newTableModel.addColumn(column);
        }

        repository = new Persons();

        for (Object o : repository.get()) {
            Person person = (Person) o;
            Object[] data = {person.getId(),
                    person.getFirstName(),
                    person.getLastName(),
                    person.getAddress(),
                    person.getPhoneNumber()};
            newTableModel.addRow(data);


        }

        personTable.setModel(newTableModel);
    }
}

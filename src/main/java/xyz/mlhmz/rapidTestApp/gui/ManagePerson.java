package xyz.mlhmz.rapidTestApp.gui;

import xyz.mlhmz.rapidTestApp.database.repositories.Repository;
import xyz.mlhmz.rapidTestApp.database.repositories.Persons;
import xyz.mlhmz.rapidTestApp.database.repositories.Tests;
import xyz.mlhmz.rapidTestApp.database.entities.Person;
import xyz.mlhmz.rapidTestApp.database.entities.Test;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ManagePerson {
    public JFrame frame;

    private JTable personTable;

    private JPanel panel;
    private JLabel manageLabel;
    private JButton createBtn, editBtn, deleteBtn;


    public ManagePerson() {
        // Set Names
        createBtn.setName("Create");
        editBtn.setName("Edit");
        deleteBtn.setName("Delete");
        manageLabel.setName("Manage the Persons");

        deleteBtn.addActionListener((event) -> {

            if (personTable.getSelectionModel().isSelectionEmpty()) {
                JOptionPane.showMessageDialog(null, "Please choose the Person you want to delete.");
                return;
            }

            Repository repository = new Tests();
            Person person = (Person) personTable.getValueAt(personTable.getSelectedRow(), 0);

            for (Object o : repository.get()) {
                Test test = (Test) o;
                if (test.getPersonId().equals(person.getId())) {
                    repository.delete(test.getTestId());
                }
            }

            repository = new Persons();

            repository.delete(person.getId());
            reloadTable();
        });

        editBtn.addActionListener(e -> {
            if (personTable.getSelectionModel().isSelectionEmpty()) {
                JOptionPane.showMessageDialog(null, "Please choose the Person you want to edit.");
                return;
            }
            Repository repository = new Persons();
            Long personId = Long.parseLong(personTable.getValueAt(personTable.getSelectedRow(), 0).toString());
            Person person = (Person) repository.getById(personId);

            EditPerson editPerson = new EditPerson();
            editPerson.run(person);
            editPerson.frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    reloadTable();
                }
            });
        });

        createBtn.addActionListener(e -> {
            CreatePerson createPerson = new CreatePerson();
            createPerson.run();
            createPerson.frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    reloadTable();
                }
            });
        });


    }



    public void run() {
        reloadTable();
        frame = new JFrame("Manage Person");
        frame.add(panel);
        frame.pack();
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

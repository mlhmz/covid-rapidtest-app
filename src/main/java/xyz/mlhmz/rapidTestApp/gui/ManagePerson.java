package xyz.mlhmz.rapidTestApp.gui;

import xyz.mlhmz.rapidTestApp.database.repositories.Repository;
import xyz.mlhmz.rapidTestApp.database.repositories.Persons;
import xyz.mlhmz.rapidTestApp.database.repositories.Tests;
import xyz.mlhmz.rapidTestApp.database.entities.Person;
import xyz.mlhmz.rapidTestApp.database.entities.Test;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ManagePerson {
    public JFrame frame;
    private JButton deleteBtn;

    // TODO: Replace List with Table
    // Will be added in Version 1.1
    private JList personList;
    private DefaultListModel personListModel;


    private JPanel panel;
    private JLabel deleteLabel;
    private JButton createBtn;
    private JButton editBtn;

    public ManagePerson() {
        deleteBtn.addActionListener((event) -> {
            if (personList.isSelectionEmpty()) {
                JOptionPane.showMessageDialog(null, "Please choose the Person you want to delete.");
                return;
            }

            Repository repository = new Tests();
            Person person = (Person) personList.getSelectedValue();

            for (Object o : repository.get()) {
                Test test = (Test) o;
                if (test.getPersonId().equals(person.getId())) {
                    repository.delete(test.getTestId());
                }
            }

            repository = new Persons();

            repository.delete(person.getId());
            reloadList();
        });

        editBtn.addActionListener(e -> {
            if (personList.isSelectionEmpty()) {
                JOptionPane.showMessageDialog(null, "Please choose the Person you want to edit.");
                return;
            }

            Person person = (Person) personList.getSelectedValue();

            EditPerson editPerson = new EditPerson();
            editPerson.run(person);
            editPerson.frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    reloadList();
                }
            });
        });

        createBtn.addActionListener(e -> {
            CreatePerson createPerson = new CreatePerson();
            createPerson.run();
            createPerson.frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    reloadList();
                }
            });
        });

        personList.setLayoutOrientation(JList.VERTICAL);


    }



    public void run() {
        reloadList();
        frame = new JFrame("Manage Person");
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);

    }

    public void reloadList() {
        Repository repository = new Persons();
        personListModel = new DefaultListModel<Person>();
        personListModel.addAll(repository.get());

        personList.setModel(personListModel);
    }
}

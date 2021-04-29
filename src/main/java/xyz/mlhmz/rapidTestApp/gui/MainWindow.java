package xyz.mlhmz.rapidTestApp.gui;

import xyz.mlhmz.rapidTestApp.database.dao.DAO;
import xyz.mlhmz.rapidTestApp.database.dao.Persons;
import xyz.mlhmz.rapidTestApp.database.dao.Tests;
import xyz.mlhmz.rapidTestApp.database.entities.Person;
import xyz.mlhmz.rapidTestApp.database.entities.Test;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Objects;

public class MainWindow {
    private JPanel panel1;

    // Table
    private JTable testTable;
    private DefaultTableModel tableModel;

    // Navigation Buttons
    private JComboBox comboBox1;
    private JButton searchButton, reloadResetButton;
    private JTextField textField1;
    private JLabel searchBarLabel;

    // Frame
    private JFrame frame;



    // Menu Bar
    private JMenuBar menuBar;
    private JMenu appMenu, manageMenu;
    private JMenuItem reload, exit, createTestBtn, managePersonBtn;

    public MainWindow() {
        reloadResetButton.setText("Reload");



        // Menu Bar
        menuBar = new JMenuBar();

        // System Stuff like Exit
        appMenu = new JMenu("App");

        exit = new JMenuItem("Exit");

        reload = new JMenuItem("Reload");

        appMenu.add(reload);
        appMenu.addSeparator();
        appMenu.add(exit);
        menuBar.add(appMenu);

        exit.addActionListener((event) -> System.exit(0));

        reload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reloadTable();
                reloadResetButton.setText("Reload");
            }
        });

        // Creation Stuff: Manage e.g. Test or Person
        manageMenu = new JMenu("Manage");


        managePersonBtn = new JMenuItem("Person Management");



        createTestBtn = new JMenuItem("Test Creation");

        CreateTest createTest = new CreateTest();


        createTestBtn.addActionListener((event) -> {
            createTest.run();
            createTest.frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    reloadTable();
                }
            });
        });



        ManagePerson managePerson = new ManagePerson();

        managePersonBtn.addActionListener((event) -> {
            managePerson.run();
            managePerson.frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    reloadTable();
                    managePerson.frame.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            reloadTable();
                        }
                    });
                }
            });
        });


        manageMenu.add(managePersonBtn);
        manageMenu.addSeparator();
        manageMenu.add(createTestBtn);

        menuBar.add(manageMenu);




        reloadTable();

        testTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        testTable.setName("mainTable");

        // Search Module

        comboBox1.setModel(new DefaultComboBoxModel(SearchModes.values()));

        // Setting Search Bar Label
        switch ((SearchModes) Objects.requireNonNull(comboBox1.getSelectedItem())) {
            case BY_NAME:
                searchBarLabel.setText("First Name, Second Name");
                break;
            case BY_TEST_ID:
                searchBarLabel.setText("Test ID");
                break;
            case BY_PERSON_ID:
                searchBarLabel.setText("Person ID");
                break;
        }

        comboBox1.addActionListener(e -> {
            switch ((SearchModes) Objects.requireNonNull(comboBox1.getSelectedItem())) {
                case BY_NAME:
                    searchBarLabel.setText("First Name, Second Name");
                    break;
                case BY_TEST_ID:
                    searchBarLabel.setText("Test ID");
                    break;
                case BY_PERSON_ID:
                    searchBarLabel.setText("Person ID");
                    break;
            }
        });

        searchButton.addActionListener(e -> {
            String searchValue = textField1.getText();
            SearchModes searchMode = (SearchModes) comboBox1.getSelectedItem();
            searchTable(searchMode, searchValue);
            reloadResetButton.setText("Reset");
        });

        reloadResetButton.addActionListener(e -> {
            reloadTable();
            reloadResetButton.setText("Reload");
        });

        // Popup Menu for Table
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem deleteTableEntry = new JMenuItem("Delete");
        popupMenu.add(deleteTableEntry);

        deleteTableEntry.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Long id = Long.parseLong(testTable.getValueAt(testTable.getSelectedRow(), 0).toString());
                DAO dao = new Tests();
                dao.delete(id);
                reloadTable();
            }
        });

        testTable.setComponentPopupMenu(popupMenu);

        popupMenu.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                SwingUtilities.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        int rowAtPoint = testTable.rowAtPoint(SwingUtilities.convertPoint(popupMenu, new Point(0, 0), testTable));
                        if (rowAtPoint > -1) {
                            testTable.setRowSelectionInterval(rowAtPoint, rowAtPoint);
                        }
                    }
                });
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {

            }
        });



        testTable.setAutoCreateRowSorter(true);

    }

    public void run(String windowTitle) {
        frame = new JFrame(windowTitle);
        frame.setJMenuBar(menuBar);
        frame.add(panel1);
        frame.pack();

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setBounds((int) size.getWidth() / 2, (int) size.getHeight() / 2, (int) size.getWidth() / 3, (int) size.getHeight() / 3);

        frame.setVisible(true);
    }

    public void reloadTable() {
        DAO dao;

        // JTable Content
        DefaultTableModel newTableModel = new DefaultTableModel() {
            public boolean isCellEditable(int rowIndex, int mColIndex) {
                return false;
            }
        };

        String[] tableColumns = {
                "Test ID",
                "Date",
                "Status",
                "Tester's ID",
                "First Name",
                "Last Name",
                "Address",
                "Phone Number"
        };


        for (String column : tableColumns) {
            newTableModel.addColumn(column);
        }

        dao = new Tests();

        for (Object o : dao.get()) {
            Test test = (Test) o;
            dao = new Persons();
            Person person = (Person) dao.getById(test.getPersonId());
            String testResult = "null";
            if (test.isPositive()) {
                testResult = "positive";
            } else {
                testResult = "negative";
            }
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy hh:mm aa");
            Object[] data = {test.getTestId(),
                    formatter.format(test.getTestDate()),
                    testResult,
                    test.getPersonId(),
                    person.getFirstName(),
                    person.getLastName(),
                    person.getAddress(),
                    person.getPhoneNumber()};
            newTableModel.addRow(data);


        }

        testTable.setModel(newTableModel);
    }

    public void searchTable(SearchModes searchMode, Object searchData) {
        DAO dao;

        // JTable Content
        DefaultTableModel newTableModel = new DefaultTableModel() {
            public boolean isCellEditable(int rowIndex, int mColIndex) {
                return false;
            }
        };

        String[] tableColumns = {
                "Test ID",
                "Date",
                "Status",
                "Tester's ID",
                "First Name",
                "Last Name",
                "Address",
                "Phone Number"
        };


        for (String column : tableColumns) {
            newTableModel.addColumn(column);
        }

        dao = new Tests();

        switch(searchMode) {
            case BY_NAME:
                for (Object o : dao.get()) {
                    Test test = (Test) o;
                    dao = new Persons();
                    Person person = (Person) dao.getById(test.getPersonId());

                    String personName = person.getFirstName() + " " + person.getLastName();

                    String searchedName = (String) searchData;

                    if (personName.toLowerCase().equals(searchedName.toLowerCase())) {
                        String testResult = "null";
                        if (test.isPositive()) {
                            testResult = "positive";
                        } else {
                            testResult = "negative";
                        }
                        Object[] data = {test.getTestId(),
                                test.getTestDate(),
                                testResult,
                                test.getPersonId(),
                                person.getFirstName(),
                                person.getLastName(),
                                person.getAddress(),
                                person.getPhoneNumber()};
                        newTableModel.addRow(data);
                        testTable.setModel(newTableModel);
                    }

                }
                break;
            case BY_TEST_ID:
                for (Object o : dao.get()) {
                    Test test = (Test) o;
                    if (test.getTestId().equals(Long.parseLong((String) searchData))) {
                        dao = new Persons();
                        Person person = (Person) dao.getById(test.getPersonId());
                        String testResult = "null";
                        if (test.isPositive()) {
                            testResult = "positive";
                        } else {
                            testResult = "negative";
                        }
                        Object[] data = {test.getTestId(),
                                test.getTestDate(),
                                testResult,
                                test.getPersonId(),
                                person.getFirstName(),
                                person.getLastName(),
                                person.getAddress(),
                                person.getPhoneNumber()};
                        newTableModel.addRow(data);
                        testTable.setModel(newTableModel);
                    }
                }
                break;
            case BY_PERSON_ID:
                for (Object o : dao.get()) {
                    Test test = (Test) o;
                    if (test.getPersonId().equals(Long.parseLong((String) searchData))) {
                        dao = new Persons();
                        Person person = (Person) dao.getById(test.getPersonId());
                        String testResult = "null";
                        if (test.isPositive()) {
                            testResult = "positive";
                        } else {
                            testResult = "negative";
                        }
                        Object[] data = {test.getTestId(),
                                test.getTestDate(),
                                testResult,
                                test.getPersonId(),
                                person.getFirstName(),
                                person.getLastName(),
                                person.getAddress(),
                                person.getPhoneNumber()};
                        newTableModel.addRow(data);
                        testTable.setModel(newTableModel);
                    }
                }
                break;
        }
    }
}

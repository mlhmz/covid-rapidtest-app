package xyz.mlhmz.rapidTestApp;

import com.formdev.flatlaf.FlatLightLaf;
import xyz.mlhmz.rapidTestApp.database.dao.DAO;
import xyz.mlhmz.rapidTestApp.database.dao.Tests;
import xyz.mlhmz.rapidTestApp.database.entities.Test;
import xyz.mlhmz.rapidTestApp.gui.MainWindow;

import javax.swing.*;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        // Set Swing Theme to Material
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        MainWindow mainWindow = new MainWindow();
        mainWindow.run("COVID-19 Rapid Test Application");

    }
}

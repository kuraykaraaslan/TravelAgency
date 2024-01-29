package org.agency.core;

import javax.swing.*;

public class Helpers {

    // Method to set the Nimbus look and feel for GUI
    public static void setTheme() {
        try {
            // Loop through the installed look and feels to find Nimbus
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    // Set Nimbus as the look and feel
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Method to display a confirmation dialog box
    public static boolean confirm(String str) {
        String msg = str.equals("Yes") ? "Please confirm deletion." : str;
        // Show a dialog box with the specified message and "Are you sure?" as the title
        return JOptionPane.showConfirmDialog(null, msg, "Are you sure?", JOptionPane.YES_NO_OPTION) == 0;
    }

    // Method to display an error message dialog box with the default title
    public static void showErrorMessage(String str) {
        showErrorMessage(str, "ERROR");
    }

    // Method to display an error message dialog box with a custom title
    public static void showErrorMessage(String str, String title) {
        // Show a dialog box with the specified message and title, using the
        // INFORMATION_MESSAGE type
        JOptionPane.showMessageDialog(null, str, title, JOptionPane.INFORMATION_MESSAGE);
    }

    // Method to display a message dialog box indicating success or failure
    public static void showMessage(String success) {
        String msg = success.equals("success") ? "Operation successful." : "Operation failed.";
        // Show a dialog box with the specified message and "Operation" as the title,
        // using the INFORMATION_MESSAGE type
        JOptionPane.showMessageDialog(null, msg, "Operation", JOptionPane.INFORMATION_MESSAGE);
    }
}

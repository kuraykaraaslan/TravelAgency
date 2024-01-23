package org.agency.core;

import javax.swing.*;

public class Helpers {

    public static void setTheme() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static boolean confirm(String str) {
        String msg = str.equals("Yes") ? "Please confirm deletion." : str;
        return JOptionPane.showConfirmDialog(null, msg, "Are you sure?", JOptionPane.YES_NO_OPTION) == 0;
    }

    public static void showErrorMessage(String str) {
        showErrorMessage(str, "ERROR");
    }

    public static void showErrorMessage(String str, String title) {
        JOptionPane.showMessageDialog(null, str, title, JOptionPane.INFORMATION_MESSAGE);
    }


    public static void showMessage(String success) {
        String msg = success.equals("success") ? "Operation successful." : "Operation failed.";
        JOptionPane.showMessageDialog(null, msg, "Operation", JOptionPane.INFORMATION_MESSAGE);
    }
}
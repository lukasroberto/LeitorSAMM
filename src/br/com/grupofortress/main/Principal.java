package br.com.grupofortress.main;

import br.com.grupofortress.controller.CommonsMail;
import br.com.grupofortress.controller.GerarTarefasAgendadas;
import br.com.grupofortress.controller.Universal;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import org.apache.commons.mail.EmailException;
import propriedades.Propriedades;

public class Principal {

    private static final int intervaloTime = Integer.parseInt(Propriedades.getProp().getProperty("intervalotime"));
    private static final int qtqTimer = Integer.parseInt(Propriedades.getProp().getProperty("qtqtimer"));
    private static LeitorSamm n;
    private static int conta = 0;

    public static void main(String[] args) {

        n = new LeitorSamm();
//        CommonsMail mail;
//        try {
//            mail = new CommonsMail();
//             mail.enviaEmailFormatoHtml();
//        } catch (EmailException ex) {
//            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (MalformedURLException ex) {
//            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
//        }
       
        GerarTarefasAgendadas.getInstance().iniciar();
        java.util.Timer timer = new java.util.Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                conta = conta + 1;
                if (conta >= qtqTimer) {
                    conta = 0;
                    Universal.reiniciaAplicativo();
                } else {
                    n.net();
                }
            }
        }, 5000, intervaloTime);

        try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        //Schedule a job for the event-dispatching thread:
        //adding TrayIcon.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        //Check the SystemTray support
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }
        final PopupMenu popup = new PopupMenu();
        final TrayIcon trayIcon = new TrayIcon(createImage("images/fortress.png", "tray icon"));
        final SystemTray tray = SystemTray.getSystemTray();

        // Create a popup menu components
        MenuItem aboutItem = new MenuItem("Abrir");
        MenuItem configItem = new MenuItem("Configurar");
        MenuItem exitItem = new MenuItem("Exit");

        //Add components to popup menu
        popup.add(aboutItem);
        popup.addSeparator();
        popup.add(configItem);
        popup.addSeparator();
        popup.add(exitItem);

        trayIcon.setPopupMenu(popup);

        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon não pode ser adicionado.");
            return;
        }
        aboutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                n.setVisible(true);
            }
        });
        configItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Process pro;
                try {
                    pro = Runtime.getRuntime().exec("cmd.exe /c  C://propriedades/config.properties");
                    pro.waitFor();
                } catch (IOException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        trayIcon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                n.setVisible(true);
            }
        });

        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tray.remove(trayIcon);
                System.exit(0);
            }
        });
    }

    //Obtain the image URL
    protected static Image createImage(String path, String description) {
        URL imageURL = Principal.class.getResource(path);

        if (imageURL == null) {
            System.err.println("Resource not found: " + path);
            return null;
        } else {
            return (new ImageIcon(imageURL, description)).getImage();
        }
    }

}

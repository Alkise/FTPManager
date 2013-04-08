/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.alkise.manager.client.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Arrays;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import net.miginfocom.swing.MigLayout;
import ru.alkise.manager.client.model.ManagerModelIntf;
import ru.alkise.manager.client.model.observer.ObserverIntf;

/**
 *
 * @author alkise
 */
public class ManagerView extends JFrame implements ObserverIntf {

    private ManagerModelIntf model;
    private JTextField localDirectoryField;
    private JTextField hostnameField;
    private JTextField usernameField;
    private JTextField passwordField;
    private JTextField workingDirectoryField;
    private JButton saveSettingsButton;
    private JList fromList;
    private JList toList;
    private JCheckBox copyNewBox;
    private JCheckBox deleteOldBox;
    private JButton runButton;
    private DefaultListModel<String> fromListModel;
    private DefaultListModel<String> toListModel;
    private JLabel messageLabel;

    private void readRemoteSettings(String... parameters) {
        localDirectoryField.setText(model.getProperty("localDirectory"));
        hostnameField.setText(model.getProperty("hostname"));
        usernameField.setText(model.getProperty("username"));
        try {
            passwordField.setText(model.decrypt(model.getProperty("password")));
        } catch (Exception ex) {
            messageLabel.setText(ex.getMessage());
        }
        workingDirectoryField.setText(model.getProperty("workingDirectory"));

        if (parameters.length != 0) {
            messageLabel.setText(Arrays.toString(parameters));
        }
    }

    private void readRemoteData(String... parameters) {
        localDirectoryField.setText(model.getProperty("localDirectory"));
        hostnameField.setText(model.getProperty("hostname"));
        usernameField.setText(model.getProperty("username"));
        try {
            passwordField.setText(model.decrypt(model.getProperty("password")));
        } catch (Exception ex) {
            messageLabel.setText(ex.getMessage());
        }
        workingDirectoryField.setText(model.getProperty("workingDirectory"));

        if (!fromListModel.isEmpty()) {
            fromListModel.clear();
        }
        if (!toListModel.isEmpty()) {
            toListModel.clear();
        }

        for (String item : model.getLeftListItems()) {
            fromListModel.addElement(item);
        }

        for (String item : model.getRightListItems()) {
            toListModel.addElement(item);
        }

        if (parameters.length != 0) {
            messageLabel.setText(Arrays.toString(parameters));
        }
    }

    public ManagerView(ManagerModelIntf model) {
        super("FTP File Manager");

        this.model = model;
        model.registerObserver(this);

        setSize(new Dimension(800, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Main panel
        JPanel mainPanel = new JPanel(new MigLayout("wrap 1"));
        add(mainPanel);

        //Settings panel
        JPanel settingsPanel = new JPanel(new MigLayout("wrap 4"));
        settingsPanel.setBorder(BorderFactory.createTitledBorder("Settings"));

        //Local directory setting
        JLabel localDirectoryLabel = new JLabel("Local directory");
        settingsPanel.add(localDirectoryLabel);

        localDirectoryField = new JTextField();
        settingsPanel.add(localDirectoryField, "span 3");

        //FTP server setting
        JLabel hostnameLabel = new JLabel("FTP Server");
        settingsPanel.add(hostnameLabel);

        hostnameField = new JTextField(15);
        settingsPanel.add(hostnameField, "wrap");

        //Username setting
        JLabel usernameLabel = new JLabel("Username");
        settingsPanel.add(usernameLabel);

        usernameField = new JTextField(15);
        settingsPanel.add(usernameField);

        //Password setting
        JLabel passwordLabel = new JLabel("Password");
        settingsPanel.add(passwordLabel);

        passwordField = new JTextField(15);
        settingsPanel.add(passwordField);

        //Working directory setting
        JLabel workingDirectoryLabel = new JLabel("Working directory");
        settingsPanel.add(workingDirectoryLabel);

        workingDirectoryField = new JTextField(38);
        settingsPanel.add(workingDirectoryField, "span");

        //Save setting button
        settingsPanel.add(new JLabel(), "span 3");
        saveSettingsButton = new JButton("Save");
        settingsPanel.add(saveSettingsButton, "gapleft 100");

        //List panel
        JPanel listPanel = new JPanel(new MigLayout("wrap 3"));
        listPanel.setBorder(BorderFactory.createTitledBorder("Files"));

        //Left (From) List
        Dimension listDimension = new Dimension(216, 330);
        fromListModel = new DefaultListModel();
        fromList = new JList(fromListModel);
        fromList.setLayoutOrientation(JList.VERTICAL);
        JScrollPane fromScrollPane = new JScrollPane(fromList, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        fromScrollPane.setPreferredSize(listDimension);
        listPanel.add(fromScrollPane);

        //Boxes panel
        JPanel boxesPanel = new JPanel(new MigLayout("wrap 1"));

        copyNewBox = new JCheckBox("Copy new");
        boxesPanel.add(copyNewBox);

        deleteOldBox = new JCheckBox("Delete old");
        boxesPanel.add(deleteOldBox);

        runButton = new JButton("Do it");
        boxesPanel.add(runButton, "center");

        listPanel.add(boxesPanel, "top");

        //Right (To) List
        toListModel = new DefaultListModel<>();
        toList = new JList(toListModel);
        toList.setLayoutOrientation(JList.VERTICAL);
        JScrollPane toScrollPane = new JScrollPane(toList, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        toScrollPane.setPreferredSize(listDimension);
        listPanel.add(toScrollPane);

        //Status panel
        JPanel statusPanel = new JPanel(new MigLayout());
        statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));

        messageLabel = new JLabel();
        messageLabel.setHorizontalAlignment(SwingConstants.LEFT);
        statusPanel.add(messageLabel);

        mainPanel.add(settingsPanel);
        mainPanel.add(listPanel);
        mainPanel.add(statusPanel, BorderLayout.SOUTH);

        update(new Date(System.currentTimeMillis()).toString());

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void update(String... parameters) {
        switch (parameters[0]) {
            case "Settings:":
                readRemoteSettings(parameters);
                break;
            case "Load:":
                readRemoteSettings();
                readRemoteData(parameters);
                break;
            case "Error:":
                messageLabel.setText(parameters.toString());
                break;
            default:
                readRemoteData(parameters);
                break;
        }
    }
}

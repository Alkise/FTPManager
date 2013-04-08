/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.alkise.manager.client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import ru.alkise.manager.client.model.ManagerModel;
import ru.alkise.manager.client.model.ManagerModelIntf;
import ru.alkise.manager.client.view.ManagerView;
import ru.alkise.manager.server.model.ManagerModelRemoteIntf;
import ru.alkise.manager.server.model.settings.ManagerSettingsRemoteIntf;

/**
 *
 * @author alkise
 */
public class FTPManager {
    private static ManagerModelRemoteIntf remoteModel;
    private static ManagerSettingsRemoteIntf remoteSettings;
    private static ManagerModelIntf model;

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("192.168.0.201", 1099);
            remoteModel = (ManagerModelRemoteIntf) registry.lookup("ManagerModel");
            remoteSettings = (ManagerSettingsRemoteIntf) registry.lookup("ManagerSettings");
            model = new ManagerModel(remoteModel, remoteSettings);
            
            javax.swing.SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    ManagerView view = new ManagerView(model);
                }
            });
        } catch (RemoteException | NotBoundException ex) {
            ex.printStackTrace();
        }
    }
}

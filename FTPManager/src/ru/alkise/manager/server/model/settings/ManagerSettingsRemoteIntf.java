/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.alkise.manager.server.model.settings;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author alkise
 */
public interface ManagerSettingsRemoteIntf extends Remote {
    void setProperty(String key, String value) throws RemoteException;
    String getProperty(String key) throws RemoteException;
    void save() throws RemoteException;
    void load() throws RemoteException;
}

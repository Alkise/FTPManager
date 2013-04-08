/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.alkise.manager.server.model;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author alkise
 */
public interface ManagerModelRemoteIntf extends Remote {

    void copyDifferentItemsToRightList() throws RemoteException;

    void deleteDifferentItemsFromRightList() throws RemoteException;

    String[] getLeftListItems() throws RemoteException;

    String[] getRightListItems() throws RemoteException;
}

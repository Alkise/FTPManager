/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.alkise.manager.client.model;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import ru.alkise.manager.client.model.encryptor.AESEncryptor;
import ru.alkise.manager.client.model.observer.ObserverIntf;
import ru.alkise.manager.server.model.ManagerModelRemoteIntf;
import ru.alkise.manager.server.model.settings.ManagerSettingsRemoteIntf;

/**
 *
 * @author alkise
 */
public class ManagerModel implements ManagerModelIntf {

    private ManagerModelRemoteIntf remoteModel;
    private ManagerSettingsRemoteIntf remoteSettings;
    private Collection<ObserverIntf> observers;
    private AESEncryptor encryptor;

    public ManagerModel(ManagerModelRemoteIntf remoteModel, ManagerSettingsRemoteIntf remoteSettings) {
        this.remoteModel = remoteModel;
        this.remoteSettings = remoteSettings;
        observers = new ArrayList<>();
        encryptor = new AESEncryptor("TheSupaSecretKey");
    }

    @Override
    public void saveSettings() {
        try {
            remoteSettings.save();
            notifyObservers("Settings:", "Settings saved.");
        } catch (RemoteException rex) {
            notifyObservers("Error:", rex.getMessage());
        }
    }

    @Override
    public void loadSettings() {
        try {
            remoteSettings.load();
            notifyObservers("Settings:", "Settings loaded.");
        } catch (RemoteException rex) {
            notifyObservers("Error:", rex.getMessage());
        }
    }

    @Override
    public String getProperty(String key) {
        try {
            return remoteSettings.getProperty(key);
        } catch (RemoteException rex) {
            notifyObservers("Error:", rex.getMessage());
            return null;
        }
        
    }

    @Override
    public void setProperty(String key, String value) {
        try {
            remoteSettings.setProperty(key, value);
        } catch (RemoteException rex) {
            notifyObservers("Error:", rex.getMessage());
        }
    }

    @Override
    public void copyDifferentItemsToRightList() {
        try {
            remoteModel.copyDifferentItemsToRightList();
            notifyObservers("New item copied.");
        } catch (RemoteException rex) {
            notifyObservers("Error:", rex.getMessage());
        }
    }

    @Override
    public void deleteDifferentItemsFromRightList() {
        try {
            remoteModel.deleteDifferentItemsFromRightList();
            notifyObservers("All old item deleted.");
        } catch (RemoteException rex) {
            notifyObservers("Error:", rex.getMessage());
        }
    }

    @Override
    public String[] getLeftListItems() {
        try {
            return remoteModel.getLeftListItems();
        } catch (RemoteException rex) {
            notifyObservers("Error:", rex.getMessage());
            return null;
        }
    }

    @Override
    public String[] getRightListItems() {
        try {
            return remoteModel.getRightListItems();
        } catch (RemoteException rex) {
            notifyObservers("Error:", rex.getMessage());
            return null;
        }
    }

    @Override
    public void registerObserver(ObserverIntf observer) {
        if (observer != null) {
            observers.add(observer);
        }
    }

    @Override
    public void removeObserver(ObserverIntf observer) {
        observers.remove(observer);
    }

    @Override
    public void removeObservers() {
        observers.clear();
    }

    @Override
    public Collection<ObserverIntf> getObservers() {
        return observers;
    }

    @Override
    public void notifyObservers(String... parameters) {
        for (ObserverIntf observer : observers) {
            observer.update(parameters);
        }
    }

    @Override
    public String encrypt(String string) throws Exception{
        return encryptor.encrypt(string);
    }

    @Override
    public String decrypt(String string) throws Exception {
        return encryptor.decrypt(string);
    }
}

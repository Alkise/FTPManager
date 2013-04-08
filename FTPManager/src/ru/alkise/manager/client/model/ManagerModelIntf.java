/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.alkise.manager.client.model;

import ru.alkise.manager.client.model.observer.ObservableIntf;

/**
 *
 * @author alkise
 */
public interface ManagerModelIntf extends ObservableIntf {

    void saveSettings();

    void loadSettings();

    String getProperty(String key);

    void setProperty(String key, String value);

    String encrypt(String string) throws Exception;

    String decrypt(String string) throws Exception;

    void copyDifferentItemsToRightList();

    void deleteDifferentItemsFromRightList();

    String[] getLeftListItems();

    String[] getRightListItems();
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.alkise.manager.client.controller;

import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import ru.alkise.manager.client.model.ManagerModelIntf;

/**
 *
 * @author alkise
 */
public class ManagerController implements ManagerControllerIntf {

    private boolean copyFlag;
    private boolean deteleFlag;
    private ManagerModelIntf model;

    public ManagerController(ManagerModelIntf model) {
        this.model = model;
    }

    @Override
    public void saveSetting(Map<String, String> properties) {
        for (String key : properties.keySet()) {
            model.setProperty(key, properties.get(key));
        }
        model.saveSettings();
    }

    @Override
    public void copyNewFiles(boolean copyFlag) {
        this.copyFlag = copyFlag;
    }

    @Override
    public void deleteOldFiles(boolean deleteFlag) {
        this.deteleFlag = deleteFlag;
    }

    @Override
    public void run() {
        if (copyFlag) {
            model.copyDifferentItemsToRightList();
        }

        if (deteleFlag) {
            model.deleteDifferentItemsFromRightList();
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.alkise.manager.client.controller;

import java.util.Map;
import java.util.Properties;

/**
 *
 * @author alkise
 */
public interface ManagerControllerIntf {

    void saveSetting(Map<String, String> properties);

    void copyNewFiles(boolean copyFlag);

    void deleteOldFiles(boolean deleteFlag);

    void run();
}

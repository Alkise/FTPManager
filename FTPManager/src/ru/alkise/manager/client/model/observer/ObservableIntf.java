/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.alkise.manager.client.model.observer;

import java.util.Collection;

/**
 *
 * @author alkise
 */
public interface ObservableIntf {

    void registerObserver(ObserverIntf observer);

    void removeObserver(ObserverIntf observer);

    void removeObservers();

    Collection<ObserverIntf> getObservers();

    void notifyObservers(String... parameters);
}

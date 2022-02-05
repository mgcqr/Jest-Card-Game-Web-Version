package com.mgcqr.jest.core.stuff;



import java.util.HashSet;
import java.util.Iterator;

public class Observable{

    HashSet<Observer> Observers = new HashSet<Observer>();
    private Boolean change = false;

    public void addObserver(Observer o) {
        Observers.add(o);
        System.out.println("Add un observer.");
    }
    public void deleteObserver(Observer o) {
        Observers.remove(o);
        System.out.println("Remove un observer.");
    }

    public void deleteObservers() {
        Observers.clear();
        System.out.println("Remove all observers");
    }

    public int countObservers() {
        return Observers.size();
    }

    protected void clearChanged() {
        change = false;
    }

    protected void setChanged() {
        change = true;
    }

    public boolean hasChanged() {
        return change;
    }

    public void notifyObservers() {
        notifyObservers(null);
    }

    public void notifyObservers(Object arg) {
        if(change) {
            Iterator<Observer> itObservers = Observers.iterator();
            while (itObservers.hasNext()) {
                Observer o = itObservers.next();
                o.update(this, arg);
            }
            clearChanged();
        }
    }
}


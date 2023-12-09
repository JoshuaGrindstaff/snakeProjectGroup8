package com.example.snake;

import java.util.ArrayList;
import java.util.List;

public class GameObjectLists {
    private List<PowerUps> powerList;
    private List<Collidable> collidableObjects;
    private int toRemove;
    private boolean removalNeeded;
    public GameObjectLists()
    {
        powerList = new ArrayList<>();
        collidableObjects = new ArrayList<>();
    }
    public List<PowerUps> getPowerList() {return powerList;}
    public void addPowerList(PowerUps object) {powerList.add(object);}
    public void clearPowerList(){powerList.clear();}
    public void removePowerList(PowerUps object) {powerList.remove(object);}
    public int getPowerListSize(){return powerList.size();}
    public List<Collidable> getCollidableObjects() {return collidableObjects;}
    public void addCollidableObject(Collidable object) {collidableObjects.add(object);}
    public void clearCollidableObjects(){collidableObjects.clear();}
    public void setToRemove(Collidable object)
    {
        toRemove = collidableObjects.indexOf(object);
        removalNeeded = true;
    }
    public void removeCollidableObject()
    {
        if(removalNeeded)
        {
            collidableObjects.remove(toRemove);
            removalNeeded = false;
        }

    }
    public int getCollidableObjectsSize(){return collidableObjects.size();}
}

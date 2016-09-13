package com.bobgranata.linkservice.Models;

/**
 * Created by BobGranata on 16.07.2016.
 */
public class RolesModel {
    public final String id;
    public final String name;
    public final String accessKey; //access_key
    public final String inn;
    private String mmDateLastUpdate;

    public RolesModel(String id, String name, String accessKey, String inn) {
        this.id = id;
        this.name = name;
        this.accessKey = accessKey;
        this.inn = inn;
        this.mmDateLastUpdate = "0";
    }

    public RolesModel(String id, String name, String accessKey, String inn, String dateLastUpdate) {
        this.id = id;
        this.name = name;
        this.accessKey = accessKey;
        this.inn = inn;
        this.mmDateLastUpdate = dateLastUpdate;
    }

    public String getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getInn() {
        return inn;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public String getDateLastUpdate() {
        return mmDateLastUpdate;
    }

    public void setDateLastUpdate(String mmDateLastUpdate) {
        this.mmDateLastUpdate = mmDateLastUpdate;
    }
}

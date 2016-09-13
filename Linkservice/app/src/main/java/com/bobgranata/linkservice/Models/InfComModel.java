package com.bobgranata.linkservice.Models;

import java.util.List;

/**
 * Created by BobGranata on 25.07.2016.
 */
public class InfComModel {
    private String idInfCom;
    private String InfComName;
    private String idRole;

    private String mmDateLastUpdate;
//    private List<DocCirculModel> listDocCirculs;

    public InfComModel(){
    }

    public InfComModel(String id, String idRole, String name){
        this.idInfCom = id;
        this.idRole = idRole;
        this.InfComName = name;
        this.mmDateLastUpdate = "0";
    }

    public InfComModel(String id, String idRole, String name, String dateLastUpdate){
        this.idInfCom = id;
        this.idRole = idRole;
        this.InfComName = name;
        this.mmDateLastUpdate = dateLastUpdate;
    }


    public String getID() {
        return idInfCom;
    }

    public void setID(String id) {
        this.idInfCom = id;
    }

    public String getName() {
        return InfComName;
    }

    public void setName(String name) {
        InfComName = name;
    }

    public String getIdRole() {
        return idRole;
    }

    public void setIdRole(String idRole) {
        this.idRole = idRole;
    }

    public String getDateLastUpdate() {
        return mmDateLastUpdate;
    }

    public void setDateLastUpdate(String mmDateLastUpdate) {
        this.mmDateLastUpdate = mmDateLastUpdate;
    }

//    public List<DocCirculModel> getListDocCirculs() {
//        return listDocCirculs;
//    }
//
//    public void setListDocCirculs(List<DocCirculModel> listDocCirculs) {
//        this.listDocCirculs = listDocCirculs;
//    }
}

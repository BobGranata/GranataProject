package com.bobgranata.linkservice.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BobGranata on 25.07.2016.
 */
public class DocCirculModel {
    private String idDocCircul;
    private String idInfcom;
    private String nameDocCircul;
    private String statusDocCircul;
    private String createDate;

    private List<DocumentModel> listDocuments;

    public DocCirculModel() {
        idDocCircul = "";
        nameDocCircul = "";
        statusDocCircul = "";
        createDate = "";
        listDocuments = new ArrayList();
    }

    public String getID() {
        return idDocCircul;
    }

    public void setIdDocCircul(String idDocCircul) {
        this.idDocCircul = idDocCircul;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getName() {
        return nameDocCircul;
    }

    public void setNameDocCircul(String nameDocCircul) {
        this.nameDocCircul = nameDocCircul;
    }

    public String getStatus() {
        return statusDocCircul;
    }

    public void setStatusDocCircul(String statusDocCircul) {
        this.statusDocCircul = statusDocCircul;
    }

    public List<DocumentModel> getListDocument() {
        return listDocuments;
    }

    public void setListDocument(List<DocumentModel> listDocument) {
        this.listDocuments = listDocument;
    }

    public String getIdInfcom() {
        return idInfcom;
    }

    public void setIdInfcom(String idInfcom) {
        this.idInfcom = idInfcom;
    }

    public int countDocuments() {
        return listDocuments.size();
    }

    public DocumentModel getDocument(int index) {
        return listDocuments.get(index);
    }

    public void addDocument(DocumentModel document) {listDocuments.add(document);};

}

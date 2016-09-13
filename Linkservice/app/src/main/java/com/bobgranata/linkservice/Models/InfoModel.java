package com.bobgranata.linkservice.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BobGranata on 31.07.2016.
 */
public class InfoModel {
    private String mmDateLastUpdate;
    private List<InfComModel> mmListInfCom;
    private List<DocCirculModel> mmListDocCircul;
    private List<DocumentModel> mmListDocument;

    public InfoModel () {
        mmListInfCom = new ArrayList<InfComModel>();
        mmListDocCircul = new ArrayList<DocCirculModel>();
        mmListDocument = new ArrayList<DocumentModel>();
    }

    public List<InfComModel> getListInfCom() {
        return mmListInfCom;
    }

    public void setListInfCom(List<InfComModel> mmListInfCom) {
        this.mmListInfCom = mmListInfCom;
    }

    public List<DocCirculModel> getListDocCircul() {
        return mmListDocCircul;
    }

    public void setListDocCircul(List<DocCirculModel> mmListDocCircul) {
        this.mmListDocCircul = mmListDocCircul;
    }

    public void addDocCircul(DocCirculModel docCircul) {
        this.mmListDocCircul.add(docCircul);
    }

    public List<DocumentModel> getListDocument() {
        return mmListDocument;
    }

    public void setListDocument(List<DocumentModel> mmListDocument) {
        this.mmListDocument = mmListDocument;
    }

    public void addDocument(DocumentModel document) {
        this.mmListDocument.add(document);
    }

    public String getDateLastUpdate() {
        return mmDateLastUpdate;
    }

    public void setDateLastUpdate(String mmDateLastUpdate) {
        this.mmDateLastUpdate = mmDateLastUpdate;
    }
}

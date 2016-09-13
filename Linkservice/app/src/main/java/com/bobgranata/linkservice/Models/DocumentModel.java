package com.bobgranata.linkservice.Models;

/**
 * Created by BobGranata on 25.07.2016.
 */
public class DocumentModel {
    private String idDocument;
    private String idDocCircul;
    private String nameDocument;
    private String statusDocument;
    private String createDate;

    public String getID() {
        return idDocument;
    }

    public void setID(String idDocument) {
        this.idDocument = idDocument;
    }

    public String getIdDocCircul() {
        return idDocCircul;
    }

    public void setIdDocCircul(String idDocCircul) {
        this.idDocCircul = idDocCircul;
    }

    public String getName() {
        return nameDocument;
    }

    public void setName(String nameDocument) {
        this.nameDocument = nameDocument;
    }

    public String getStatus() {
        return statusDocument;
    }

    public void setStatus(String statusDocument) {
        this.statusDocument = statusDocument;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}

package com.bobgranata.linkservice.Database;

import com.bobgranata.linkservice.Models.DocCirculModel;
import com.bobgranata.linkservice.Models.DocumentModel;
import com.bobgranata.linkservice.Models.InfComModel;
import com.bobgranata.linkservice.Models.RolesModel;

import java.util.List;

/**
 * Created by BobGranata on 31.07.2016.
 */
public interface IDatabaseHandler {
    public void addRole(RolesModel role);
    public RolesModel getRole(String id);
    public List<RolesModel> getAllRoles();
    public int getRolesCount();
    public int updateRole(RolesModel role);
    public void insertRole(RolesModel role);
    public void deleteRole(String id);
    public void deleteAll();

    public void addInfCom(InfComModel infcom);
    public InfComModel getInfCom(String id);
    public List<InfComModel> getAllInfComs();
    public int getInfComsCount();
    public int updateInfCom(InfComModel infcom);
    public void insertInfCom(InfComModel infcom);
    public void deleteInfCom(InfComModel infcom);
    public List<InfComModel> getInfComs(String idRole);

    public void addDocCircul(DocCirculModel doccircul);
    public DocCirculModel getDocCircul(String idDoccircul);
    public List<DocCirculModel> getDocCirculs(String idInfCom);
    public List<DocCirculModel> getDocCirculsByDate(String sDate, String idInfCom);
    public int updateDocCircul(DocCirculModel doccircul);
    public void insertDocCircul(DocCirculModel doccircul);

    public void addDocument(DocumentModel Document);
    public DocumentModel getDocument(String idDocument);
    public boolean documentExists(String idDocument);
    public List<DocumentModel> getDocuments(String idDocCircul);
    public int updateDocument(DocumentModel document);
    public void insertDocument(DocumentModel document);

}

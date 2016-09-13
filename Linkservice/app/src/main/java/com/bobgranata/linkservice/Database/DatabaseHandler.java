package com.bobgranata.linkservice.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bobgranata.linkservice.Models.DocCirculModel;
import com.bobgranata.linkservice.Models.DocumentModel;
import com.bobgranata.linkservice.Models.InfComModel;
import com.bobgranata.linkservice.Models.RolesModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BobGranata on 31.07.2016.
 */

public class DatabaseHandler extends SQLiteOpenHelper implements IDatabaseHandler {

    private static final int DATABASE_VERSION = 6;
    private static final String DATABASE_NAME = "contactsManager";
    private static final String TABLE_ROLES = "roles";
    private static final String TABLE_INFCOM = "infcom";
    private static final String TABLE_DOCCIRCUL = "doccircul";
    private static final String TABLE_DOCUMENT = "document";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_ACCESS_KEY = "access_key";
    private static final String KEY_INN = "inn";
    private static final String KEY_STATUS = "status";
    private static final String KEY_MAIN_STATUS = "main_status";
    private static final String KEY_ID_INFCOM = "id_infcom";
    private static final String KEY_ID_ROLE = "id_role";
    private static final String KEY_ID_DOCCIRCUL = "id_doccircul";
    private static final String KEY_CREATE_DATE = "create_date";
    private static final String DATE_LAST_UPDATE = "date_last_update";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ROLE_TABLE = "CREATE TABLE " + TABLE_ROLES + "("
                + KEY_ID + " TEXT PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_ACCESS_KEY + " TEXT," + KEY_INN + " TEXT,"
                + DATE_LAST_UPDATE + " TEXT DEFAULT \"0\"" + ")";
        db.execSQL(CREATE_ROLE_TABLE);

        String CREATE_INFCOM_TABLE = "CREATE TABLE " + TABLE_INFCOM + "("
                + KEY_ID + " TEXT PRIMARY KEY," + KEY_ID_ROLE + " TEXT,"
                + KEY_NAME + " TEXT," + DATE_LAST_UPDATE + " TEXT DEFAULT \"0\"" + ")";
        db.execSQL(CREATE_INFCOM_TABLE);

        String CREATE_DOCCIRCUL_TABLE = "CREATE TABLE " + TABLE_DOCCIRCUL + "("
                + KEY_ID + " TEXT PRIMARY KEY," + KEY_ID_INFCOM + " TEXT,"
                + KEY_NAME + " TEXT," + KEY_STATUS + " TEXT,"
                + KEY_CREATE_DATE + " TEXT" + ")";
        db.execSQL(CREATE_DOCCIRCUL_TABLE);

        String CREATE_DOCUMENT_TABLE = "CREATE TABLE " + TABLE_DOCUMENT + "("
                + KEY_ID + " TEXT PRIMARY KEY," + KEY_ID_DOCCIRCUL + " TEXT,"
                + KEY_NAME + " TEXT," + KEY_STATUS + " TEXT,"
                + KEY_CREATE_DATE + " TEXT" + ")";
        db.execSQL(CREATE_DOCUMENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROLES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INFCOM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOCCIRCUL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOCUMENT);

        onCreate(db);
    }

    @Override
    public void addRole(RolesModel role) {
        if (getRole(role.getID()) != null) {
            updateRole(role);
        } else {
            insertRole(role);
        }
    }

    @Override
    public void insertRole(RolesModel role) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, role.getID());
        values.put(KEY_NAME, role.getName());
        values.put(KEY_ACCESS_KEY, role.getAccessKey());
        values.put(KEY_INN, role.getInn());

        db.insert(TABLE_ROLES, null, values);
        db.close();
    }

    @Override
    public RolesModel getRole(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ROLES, new String[] { KEY_ID,
                        KEY_NAME, KEY_ACCESS_KEY, KEY_INN, DATE_LAST_UPDATE }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor != null){
            cursor.moveToFirst();

            if (cursor.getCount() == 0) {
                return null;
            }

            String sIdRole = cursor.getString(0);
            String sNameRole = cursor.getString(1);
            String sAccesKey = cursor.getString(2);
            String sInn = cursor.getString(3);
            String sLastUpdate = cursor.getString(4);
            RolesModel role = new RolesModel(sIdRole, sNameRole, sAccesKey, sInn, sLastUpdate);

            return role;
        } else {
            return null;
        }
    }

    @Override
    public List<RolesModel> getAllRoles() {
        List<RolesModel> rolesList = new ArrayList<RolesModel>();
        String selectQuery = "SELECT  * FROM " + TABLE_ROLES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                String sIdRole = cursor.getString(0);
                String sNameRole = cursor.getString(1);
                String sAccesKey = cursor.getString(2);
                String sInn = cursor.getString(3);
                String sLastUpdate = cursor.getString(4);
                RolesModel role = new RolesModel(sIdRole, sNameRole, sAccesKey, sInn, sLastUpdate);
                rolesList.add(role);
            } while (cursor.moveToNext());
        }

        return rolesList;
    }

    @Override
    public int updateRole(RolesModel role) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, role.getID());
        values.put(KEY_NAME, role.getName());
        values.put(KEY_ACCESS_KEY, role.getAccessKey());
        values.put(KEY_INN, role.getInn());
        values.put(DATE_LAST_UPDATE, role.getDateLastUpdate());

        return db.update(TABLE_ROLES, values, KEY_ID + " = ?",
                new String[] { String.valueOf(role.getID()) });
    }

    @Override
    public void deleteRole(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ROLES, KEY_ID + " = ?", new String[] { String.valueOf(id) });
        db.close();
    }

    @Override
    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ROLES, null, null);
        db.close();
    }

    @Override
    public int getRolesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_ROLES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int resultCount = cursor.getCount();
        cursor.close();

        return resultCount;
    }

    @Override
    public void addInfCom(InfComModel infcom) {
        if (getInfCom(infcom.getID()) != null) {
            updateInfCom(infcom);
        } else {
            insertInfCom(infcom);
        }
    }

    @Override
    public InfComModel getInfCom(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_INFCOM, new String[] { KEY_ID, KEY_ID_ROLE,
                        KEY_NAME, DATE_LAST_UPDATE}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor != null){
            cursor.moveToFirst();
        }
        if (cursor.getCount() == 0) {
            return null;
        }

        String sIdInfCom = cursor.getString(0);
        String sIdRole = cursor.getString(1);
        String sNameInfCom = cursor.getString(2);
        String sDateLastUpdate = cursor.getString(3);

        InfComModel infcom = new InfComModel(sIdInfCom, sNameInfCom, sIdRole, sDateLastUpdate);

        return infcom;

    }

    @Override
    public List<InfComModel> getAllInfComs() {
        List<InfComModel> listInfCom = new ArrayList<InfComModel>();
        String selectQuery = "SELECT  * FROM " + TABLE_INFCOM;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                String sIdInfCom = cursor.getString(0);
                String sIdRole = cursor.getString(1);
                String sNameInfCom = cursor.getString(2);
                String sDateLastUpdate = cursor.getString(3);
                InfComModel infCom = new InfComModel(sIdInfCom, sNameInfCom, sIdRole, sDateLastUpdate);
                listInfCom.add(infCom);
            } while (cursor.moveToNext());
        }

        return listInfCom;
    }

    @Override
    public int getInfComsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_INFCOM;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int resultCount = cursor.getCount();
        cursor.close();

        return resultCount;
    }

    @Override
    public int updateInfCom(InfComModel infcom) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, infcom.getID());
        values.put(KEY_ID_ROLE, infcom.getIdRole());
        values.put(KEY_NAME, infcom.getName());

        if (infcom.getDateLastUpdate() != "0")
            values.put(DATE_LAST_UPDATE, infcom.getDateLastUpdate());

        return db.update(TABLE_INFCOM, values, KEY_ID + " = ?",
                new String[] { String.valueOf(infcom.getID()) });
    }

    @Override
    public void insertInfCom(InfComModel infcom) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, infcom.getID());
        values.put(KEY_ID_ROLE, infcom.getIdRole());
        values.put(KEY_NAME, infcom.getName());

        db.insert(TABLE_INFCOM, null, values);
        db.close();
    }

    @Override
    public void deleteInfCom(InfComModel infcom) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_INFCOM, KEY_ID + " = ?", new String[] { String.valueOf(infcom.getID()) });
        db.close();
    }

    @Override
    public List<InfComModel> getInfComs(String idRole) {
        List<InfComModel> listInfCom = new ArrayList<InfComModel>();
        String selectQuery = "SELECT  * FROM " + TABLE_INFCOM + " WHERE " + KEY_ID_ROLE + "=" + idRole;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(TABLE_INFCOM, new String[] { KEY_ID,
                        KEY_NAME }, KEY_ID_ROLE + "=?",
                new String[] { String.valueOf(idRole) }, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String sIdInfCom = cursor.getString(0);
                String sNameInfCom = cursor.getString(1);
                InfComModel infCom = new InfComModel(sIdInfCom, idRole, sNameInfCom);
                listInfCom.add(infCom);
            } while (cursor.moveToNext());
        }

        return listInfCom;
    }

    @Override
    public void addDocCircul(DocCirculModel doccircul) {
        if (getDocCircul(doccircul.getID()) != null) {
            updateDocCircul(doccircul);
        } else {
            insertDocCircul(doccircul);
        }
    }

    @Override
    public DocCirculModel getDocCircul(String idDocCircul) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_DOCCIRCUL, new String[] { KEY_ID, KEY_ID_INFCOM,
                        KEY_NAME, KEY_STATUS, KEY_CREATE_DATE }, KEY_ID + "=?",
                new String[] { String.valueOf(idDocCircul) }, null, null, null, null);

        if (cursor != null){
            cursor.moveToFirst();

            if (cursor.getCount() == 0) {
                return null;
            }

            String sIdDocCircul = cursor.getString(0);
            String idInfcom = cursor.getString(1);
            String nameDocCircul = cursor.getString(2);
            String statusDocCircul = cursor.getString(3);
            String createDate = cursor.getString(4);

            DocCirculModel DocCircul = new DocCirculModel();
            DocCircul.setIdDocCircul(sIdDocCircul);
            DocCircul.setIdInfcom(idInfcom);
            DocCircul.setNameDocCircul(nameDocCircul);
            DocCircul.setStatusDocCircul(statusDocCircul);
            DocCircul.setCreateDate(createDate);

            return DocCircul;
        } else {
            return null;
        }
    }

    @Override
    public List<DocCirculModel> getDocCirculs(String idInfCom) {
        List<DocCirculModel> listDocCircul = new ArrayList<DocCirculModel>();

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(TABLE_DOCCIRCUL, new String[] { KEY_ID,
                        KEY_ID_INFCOM, KEY_NAME, KEY_STATUS, KEY_CREATE_DATE }, KEY_ID_INFCOM + "=?",
                new String[] { String.valueOf(idInfCom) }, null, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String sIdDocCircul = cursor.getString(0);
                    String sIdInfcom = cursor.getString(1);
                    String sName = cursor.getString(2);
                    String sStatus = cursor.getString(3);
                    String sCreateDate = cursor.getString(4);

                    DocCirculModel docCircul = new DocCirculModel();
                    docCircul.setIdDocCircul(sIdDocCircul);
                    docCircul.setIdInfcom(sIdInfcom);
                    docCircul.setNameDocCircul(sName);
                    docCircul.setStatusDocCircul(sStatus);
                    docCircul.setCreateDate(sCreateDate);

                    docCircul.setListDocument(getDocuments(sIdDocCircul));

                    listDocCircul.add(docCircul);
                } while (cursor.moveToNext());
            }
            return listDocCircul;
        } else {
            return null;
        }
    }

    @Override
    public int updateDocCircul(DocCirculModel doccircul) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, doccircul.getID());
        values.put(KEY_ID_INFCOM, doccircul.getIdInfcom());
        values.put(KEY_NAME, doccircul.getName());
        values.put(KEY_STATUS, doccircul.getStatus());
        values.put(KEY_CREATE_DATE, doccircul.getCreateDate());

        return db.update(TABLE_DOCCIRCUL, values, KEY_ID + " = ?",
                new String[] { String.valueOf(doccircul.getID()) });
    }

    @Override
    public void insertDocCircul(DocCirculModel doccircul) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, doccircul.getID());
        values.put(KEY_ID_INFCOM, doccircul.getIdInfcom());
        values.put(KEY_NAME, doccircul.getName());
        values.put(KEY_STATUS, doccircul.getStatus());
        values.put(KEY_CREATE_DATE, doccircul.getCreateDate());

        db.insert(TABLE_DOCCIRCUL, null, values);
        db.close();
    }

    @Override
    public void addDocument(DocumentModel document) {
        if (documentExists(document.getID()) != false) {
            updateDocument(document);
        } else {
            insertDocument(document);
        }
    }

    @Override
    public boolean documentExists(String idDocument) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_DOCUMENT, new String[] { KEY_ID }, KEY_ID + "=?",
                new String[] { String.valueOf(idDocument) }, null, null, null, null);
        try {
            if (cursor != null){
                cursor.moveToFirst();

                if (cursor.getCount() == 0) {
                    return false;
                } else {
                    return true;
                }
            } else {
                return false;
            }
        } finally {
            // this gets called even if there is an exception somewhere above
            if(cursor != null)
                cursor.close();
        }
    }

    @Override
    public DocumentModel getDocument(String idDocument) {

        SQLiteDatabase db = this.getReadableDatabase();

//        Cursor cursor = db.query(TABLE_DOCUMENT, new String[] { KEY_ID, KEY_ID_DOCCIRCUL,
//                        KEY_NAME, KEY_STATUS, KEY_CREATE_DATE }, KEY_ID + "=?",
//                new String[] { String.valueOf(idDocument) }, null, null, null, null);
        Cursor cursor = db.query(TABLE_DOCUMENT, new String[] { KEY_ID }, KEY_ID + "=?",
                new String[] { String.valueOf(idDocument) }, null, null, null, null);
        try {
        if (cursor != null){
            cursor.moveToFirst();

            if (cursor.getCount() == 0) {
                return null;
            }

            String sIdDocument = cursor.getString(0);
//            String idDocCircul = cursor.getString(1);
//            String nameDocument = cursor.getString(2);
//            String statusDocument = cursor.getString(3);
//            String createDate = cursor.getString(4);

            DocumentModel document = new DocumentModel();
            document.setID(sIdDocument);
//            document.setIdDocCircul(idDocCircul);
//            document.setName(nameDocument);
//            document.setStatus(statusDocument);
//            document.setCreateDate(createDate);

            return document;
        } else {
            return null;
        }
        } finally {
            // this gets called even if there is an exception somewhere above
            if(cursor != null)
                cursor.close();
        }
    }

    @Override
    public List<DocumentModel> getDocuments(String idDocCircul) {
        List<DocumentModel> listDocuments = new ArrayList<DocumentModel>();

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(TABLE_DOCUMENT, new String[] { KEY_ID,
                        KEY_ID_DOCCIRCUL, KEY_NAME, KEY_STATUS, KEY_CREATE_DATE }, KEY_ID_DOCCIRCUL + "=?",
                new String[] { String.valueOf(idDocCircul) }, null, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String sIdDocument = cursor.getString(0);
                    String sIdDocCircul = cursor.getString(1);
                    String nameDocument = cursor.getString(2);
                    String statusDocument = cursor.getString(3);
                    String createDate = cursor.getString(4);

                    DocumentModel document = new DocumentModel();
                    document.setID(sIdDocument);
                    document.setIdDocCircul(sIdDocCircul);
                    document.setName(nameDocument);
                    document.setStatus(statusDocument);
                    document.setCreateDate(createDate);

                    listDocuments.add(document);
                } while (cursor.moveToNext());
            }
            return listDocuments;
        } else {
            return null;
        }
    }

    @Override
    public int updateDocument(DocumentModel document) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
        ContentValues values = new ContentValues();
        values.put(KEY_ID, document.getID());
        values.put(KEY_ID_DOCCIRCUL, document.getIdDocCircul());
        values.put(KEY_NAME, document.getName());
        values.put(KEY_STATUS, document.getStatus());
        values.put(KEY_CREATE_DATE, document.getCreateDate());

        return db.update(TABLE_DOCUMENT, values, KEY_ID + " = ?",
                new String[] { String.valueOf(document.getID()) });
        } finally {
            db.close();
        }
    }

    @Override
    public void insertDocument(DocumentModel document) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, document.getID());
        values.put(KEY_ID_DOCCIRCUL, document.getIdDocCircul());
        values.put(KEY_NAME, document.getName());
        values.put(KEY_STATUS, document.getStatus());
        values.put(KEY_CREATE_DATE, document.getCreateDate());

        db.insert(TABLE_DOCUMENT, null, values);
        db.close();
    }

}
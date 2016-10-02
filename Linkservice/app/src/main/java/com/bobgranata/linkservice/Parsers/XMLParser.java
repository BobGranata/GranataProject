package com.bobgranata.linkservice.Parsers;

import android.util.Base64;
import android.util.Xml;

import com.bobgranata.linkservice.Models.DocCirculModel;
import com.bobgranata.linkservice.Models.DocumentModel;
import com.bobgranata.linkservice.Models.InfComModel;
import com.bobgranata.linkservice.Models.InfoModel;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by BobGranata on 10.09.2016.
 */
public class XMLParser {
    static InputStream isReqHttpAnswer = null;
    // We don't use namespaces
    private static final String ns = null;
    private InfoModel mmAllInfo;
    private String mmDateLastUpdate;

    // конструктор
    public XMLParser(InputStream ReqHttpAnswer) {

        isReqHttpAnswer = ReqHttpAnswer;
    }

    public List startParsingInfCom() {

        // пробуем распарсит XML объект
//        try {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(isReqHttpAnswer, null);
            parser.nextTag();

            return readArrayOfInfcom(parser);

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                isReqHttpAnswer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public List startParsingDocCircul() {

        // пробуем распарсит XML объект
//        try {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(isReqHttpAnswer, null);
            parser.nextTag();

            return readArrayOfInfo(parser);

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                isReqHttpAnswer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private List readArrayOfInfcom(XmlPullParser parser) throws XmlPullParserException, IOException {
        List listInfCom = new ArrayList();

        parser.require(XmlPullParser.START_TAG, ns, "ArrayOfInfcom");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("Infcom")) {
                listInfCom.add(readInfCom(parser));
            } else {
                skip(parser);
            }
        }

        return listInfCom;
    }

    private List readArrayOfInfo(XmlPullParser parser) throws XmlPullParserException, IOException {
        List listDocCircul = new ArrayList();

        parser.require(XmlPullParser.START_TAG, ns, "ArrayOfInfo");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("Info")) {
                setDateLastUpdate(readInfo(parser, listDocCircul));
//                listDocCircul.add(readDocCircul(parser));
            } else {
                skip(parser);
            }
        }

        return listDocCircul;
    }

    private String readInfo(XmlPullParser parser, List listDocCircul) throws XmlPullParserException, IOException {
        mmAllInfo = new InfoModel();

//        List listDocCirculs = new ArrayList();

        parser.require(XmlPullParser.START_TAG, ns, "Info");
        String dateLastUpdate = parser.getAttributeValue(null, "date_last_update");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("Doccirculs")) {
                listDocCircul.add(readDocCircul(parser));
            } else {
                skip(parser);
            }
        }

//        mmAllInfo.setListInfCom(listInfComs);
        return dateLastUpdate;
    }

    // Parses the contents of an entry. If it encounters a title, summary, or link tag, hands them off
// to their respective "read" methods for processing. Otherwise, skips the tag.
    private InfComModel readInfCom(XmlPullParser parser) throws XmlPullParserException, IOException {

        parser.require(XmlPullParser.START_TAG, ns, "Infcom");
        String idInfCom = parser.getAttributeValue(null, "id_infcom");
        String idRole = parser.getAttributeValue(null, "id_role");
        String nameInfCom = parser.getAttributeValue(null, "name_infcom");

        byte[] data = Base64.decode(nameInfCom, Base64.DEFAULT);
        nameInfCom = new String(data, "cp1251");

        while (parser.next() != XmlPullParser.END_TAG) {
            parser.nextTag();
        }

//        InfComModel mInfCom = new InfComModel(idInfCom, idRole, nameInfCom);
        return new InfComModel(idInfCom, idRole, nameInfCom);
    }

    private DocCirculModel readDocCircul(XmlPullParser parser) throws XmlPullParserException, IOException {
        DocCirculModel mDocCircul = new DocCirculModel();
        parser.require(XmlPullParser.START_TAG, ns, "Doccirculs");
        String idDocCircul = parser.getAttributeValue(null, "id_dc_doc");
        String idInfCom = parser.getAttributeValue(null, "id_infcom");
        String nameDocCircul = parser.getAttributeValue(null, "name_dc");
        String createDate = parser.getAttributeValue(null, "create_date");

        byte[] data = Base64.decode(nameDocCircul, Base64.DEFAULT);
        nameDocCircul = new String(data, "cp1251");

        String statusDocCircul = parser.getAttributeValue(null, "status_dc");

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("DocsInfo")) {
                mDocCircul.addDocument(readDocument(parser, idDocCircul));
            } else {
                skip(parser);
            }
        }

        mDocCircul.setIdDocCircul(idDocCircul);;
        mDocCircul.setIdInfcom(idInfCom);
        mDocCircul.setNameDocCircul(nameDocCircul);
        mDocCircul.setStatusDocCircul(statusDocCircul);
        mDocCircul.setCreateDate(createDate);

        return mDocCircul;
    }

    private DocumentModel readDocument(XmlPullParser parser, String idDocCircul) throws XmlPullParserException, IOException {
        DocumentModel mDocument = new DocumentModel();
        parser.require(XmlPullParser.START_TAG, ns, "DocsInfo");
        String idDocument = parser.getAttributeValue(null, "id_doc");
//        String idDocCircul = parser.getAttributeValue(null, "id_dc_doc");
        String nameDocument = parser.getAttributeValue(null, "name_doc");

        byte[] data = Base64.decode(nameDocument, Base64.DEFAULT);
        nameDocument = new String(data, "cp1251");

        String createDate = parser.getAttributeValue(null, "send_date"); //create_date
        String statusDocument = parser.getAttributeValue(null, "status_doc");

        while (parser.next() != XmlPullParser.END_TAG) {
            parser.nextTag();
        }

        mDocument.setID(idDocument);
        mDocument.setIdDocCircul(idDocCircul);
        mDocument.setName(nameDocument);
        mDocument.setStatus(statusDocument);
        mDocument.setCreateDate(createDate);
        return mDocument;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

    public String getDateLastUpdate() {
        return mmDateLastUpdate;
    }

    public void setDateLastUpdate(String mmDateLastUpdate) {
        this.mmDateLastUpdate = mmDateLastUpdate;
    }
}

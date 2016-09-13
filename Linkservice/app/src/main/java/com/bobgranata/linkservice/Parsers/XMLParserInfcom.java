package com.bobgranata.linkservice.Parsers;

import android.os.Environment;
import android.util.Base64;
import android.util.Xml;

import com.bobgranata.linkservice.Models.DocCirculModel;
import com.bobgranata.linkservice.Models.DocumentModel;
import com.bobgranata.linkservice.Models.InfComModel;
import com.bobgranata.linkservice.Models.InfoModel;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by BobGranata on 24.07.2016.
 */
public class XMLParserInfcom {

    static InputStream isReqHttpAnswer = null;
    // We don't use namespaces
    private static final String ns = null;
    private InfoModel mmAllInfo;

    // конструктор
    public XMLParserInfcom(InputStream ReqHttpAnswer) {

        isReqHttpAnswer = ReqHttpAnswer;
    }

    public InfoModel startParsing() {

        // пробуем распарсит XML объект
//        try {
        try {
//            String xmlRoleFileName = Environment.getExternalStorageDirectory().toString() + File.separator + "infcom.xml";
//            InputStream isFromTestFile = new FileInputStream(xmlRoleFileName);
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(isReqHttpAnswer, null);
//            parser.setInput(isFromTestFile, null);
            parser.nextTag();

            return readRoleInfcom(parser);

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

    private InfoModel readRoleInfcom(XmlPullParser parser) throws XmlPullParserException, IOException {
        mmAllInfo = new InfoModel();

        parser.require(XmlPullParser.START_TAG, ns, "ArrayOfInfo");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("Info")) {
                String sLastUpdate = readInfo(parser);
                mmAllInfo.setDateLastUpdate(sLastUpdate);
            } else {
                skip(parser);
            }
        }

        return mmAllInfo;
    }

    private String readInfo(XmlPullParser parser) throws XmlPullParserException, IOException {
        mmAllInfo = new InfoModel();

        List listInfComs = new ArrayList();

        parser.require(XmlPullParser.START_TAG, ns, "Info");
        String dateLastUpdate = parser.getAttributeValue(null, "date_last_update");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("Infcoms")) {
                listInfComs.add(readInfCom(parser));
            } else {
                skip(parser);
            }
        }

        mmAllInfo.setListInfCom(listInfComs);
        return dateLastUpdate;
    }

    // Parses the contents of an entry. If it encounters a title, summary, or link tag, hands them off
// to their respective "read" methods for processing. Otherwise, skips the tag.
    private InfComModel readInfCom(XmlPullParser parser) throws XmlPullParserException, IOException {
        InfComModel mInfCom = new InfComModel();
        List<DocCirculModel> listDocCirculs = new ArrayList<>();

        parser.require(XmlPullParser.START_TAG, ns, "Infcoms");
        String idInfCom = parser.getAttributeValue(null, "id_infcom");
        String idRole = parser.getAttributeValue(null, "id_role");
        String nameInfCom = parser.getAttributeValue(null, "name_infcom");

        byte[] data = Base64.decode(nameInfCom, Base64.DEFAULT);
        nameInfCom = new String(data, "cp1251");

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("Doccircul")) {
                mmAllInfo.addDocCircul(readDocCircul(parser));
            } else {
                skip(parser);
            }
        }

        mInfCom = new InfComModel(idInfCom, idRole, nameInfCom);
        return mInfCom;
    }

    private DocCirculModel readDocCircul(XmlPullParser parser) throws XmlPullParserException, IOException {
        DocCirculModel mDocCircul = new DocCirculModel();
        parser.require(XmlPullParser.START_TAG, ns, "Doccircul");
        String idDocCircul = parser.getAttributeValue(null, "id_dc_doc");
        String idInfCom = parser.getAttributeValue(null, "id_infcom");
        String nameDocCircul = parser.getAttributeValue(null, "name_dc");

        byte[] data = Base64.decode(nameDocCircul, Base64.DEFAULT);
        nameDocCircul = new String(data, "cp1251");

        String statusDocCircul = parser.getAttributeValue(null, "status_dc");
        String createDate = parser.getAttributeValue(null, "create_date");

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("DocsInfo")) {
                mmAllInfo.addDocument(readDocument(parser));
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

    private DocumentModel readDocument(XmlPullParser parser) throws XmlPullParserException, IOException {
        DocumentModel mDocument = new DocumentModel();
        parser.require(XmlPullParser.START_TAG, ns, "DocsInfo");
        String idDocument = parser.getAttributeValue(null, "id_doc");
        String idDocCircul = parser.getAttributeValue(null, "id_dc_doc");
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
}

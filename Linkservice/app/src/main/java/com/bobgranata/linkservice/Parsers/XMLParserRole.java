package com.bobgranata.linkservice.Parsers;

import android.os.Environment;
import android.util.Base64;
import android.util.Xml;

import com.bobgranata.linkservice.Models.RolesModel;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by BobGranata on 26.06.2016.
 */

public class XMLParserRole {

    static InputStream isReqHttpAnswer = null;
    // We don't use namespaces
    private static final String ns = null;

    // конструктор
    public XMLParserRole(InputStream ReqHttpAnswer) {

        isReqHttpAnswer = ReqHttpAnswer;
    }

    public List startParsing() {
        try {
//            String xmlRoleFileName = Environment.getExternalStorageDirectory().toString() + File.separator + "role.xml";
//            InputStream isFromTestFile = new FileInputStream(xmlRoleFileName);
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(isReqHttpAnswer, null);
//            parser.setInput(isFromTestFile, null);
            parser.nextTag();

            return readRoles(parser);

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

    private List readRoles(XmlPullParser parser) throws XmlPullParserException, IOException {
        List listRoles = new ArrayList();

        parser.require(XmlPullParser.START_TAG, ns, "ArrayOfRole");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("Role")) {
                RolesModel rmRole = readRole(parser);
                if (rmRole != null) {
                    listRoles.add(rmRole);
                }
            } else {
                skip(parser);
            }
        }
        return listRoles;
    }

    // Parses the contents of an entry. If it encounters a title, summary, or link tag, hands them off
// to their respective "read" methods for processing. Otherwise, skips the tag.
    private RolesModel readRole(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "Role");
        String id = parser.getAttributeValue(null, "id_role");

        if (id == null) {
            return null;
        }

        String nameRole = parser.getAttributeValue(null, "name_role");

        if (nameRole != null) {
            byte[] data = Base64.decode(nameRole, Base64.DEFAULT);
            nameRole = new String(data, "cp1251");
        }

        String accessKey = parser.getAttributeValue(null, "access_key");
        String inn = parser.getAttributeValue(null, "inn_role");
        String kpp = parser.getAttributeValue(null, "kpp_role");

        while (parser.next() != XmlPullParser.END_TAG) {
            parser.nextTag();
        }
        return new RolesModel(id, nameRole, accessKey, inn);
    }

    // Processes title tags in the feed.
//    private String readTitle(XmlPullParser parser) throws IOException, XmlPullParserException {
//        parser.require(XmlPullParser.START_TAG, ns, "firstName");
//        String title = readText(parser);
//        parser.require(XmlPullParser.END_TAG, ns, "firstName");
//        return title;
//    }
//
//    // For the tags title and summary, extracts their text values.
//    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
//        String result = "";
//        if (parser.next() == XmlPullParser.TEXT) {
//            result = parser.getText();
//            parser.nextTag();
//        }
//        return result;
//    }

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

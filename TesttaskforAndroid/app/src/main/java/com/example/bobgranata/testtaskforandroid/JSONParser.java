package com.example.bobgranata.testtaskforandroid;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by BobGranata on 26.07.2017.
 */

public class JSONParser {
    static InputStream isReqHttpAnswer = null;
    // We don't use namespaces
    private static final String ns = null;
    static JSONObject jObj = null;
    static String json = "";

    // конструктор
    public JSONParser(InputStream ReqHttpAnswer) {

        isReqHttpAnswer = ReqHttpAnswer;
    }

    public List startParsing() {
        List listAdv = new ArrayList();
//        List<Adverts>
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(isReqHttpAnswer, "UTF-8"), 8); //"iso-8859-1"
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            isReqHttpAnswer.close();
            json = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        // пробуем распарсит JSON объект
        try {
            jObj = new JSONObject(json);

            JSONObject joRequest = jObj.getJSONObject("request");

            JSONObject joLinked = jObj.getJSONObject("linked");
            JSONObject joUploads = joLinked.getJSONObject("uploads");
//            JSONObject joUploads = joLinked.getJSONObject("uploads");

            JSONArray jsonArray = jObj.getJSONArray("adverts");

            for (int i = 0; i < jsonArray.length(); i++) {
//                if (i == 13)
//                Log.e("JSON Parser", "Error parsing data " +  String.valueOf(i));
                Adverts advert = new Adverts();

                String tags = "";
                String origin = "";

                JSONObject advertsMember = jsonArray.getJSONObject(i);

                String sTitle = "";
                if (advertsMember.has("title"))
                    sTitle = advertsMember.getString("title");

                String sCost = "";
                if (advertsMember.has("cost"))
                    sCost = advertsMember.getString("cost");

                String sUpdateDate = "";
                if (advertsMember.has("update_date"))
                    sUpdateDate = advertsMember.getString("update_date");

                JSONObject joLinksTags = advertsMember.getJSONObject("links");
                JSONArray jsonArrayTags = joLinksTags.getJSONArray("tags");


                if (jsonArrayTags.length() != 0) {
                    tags = jsonArrayTags.getString(0);
                }

                JSONObject joShortImages = advertsMember.getJSONObject("short_images");
                int countImg = joShortImages.getInt("total");

                if (countImg > 0) {
                    JSONObject joShortImagesMain = joShortImages.getJSONObject("main");
                    JSONObject joShortImagesMainLinks = joShortImagesMain.getJSONObject("links");
                    origin = joShortImagesMainLinks.getString("origin");
                }
                String sAdvTagsTitle = "";
                String sType = "";
                JSONObject joLinkedTags = joLinked.getJSONObject("tags");
                if (tags != "") {
                    JSONObject joLinkedAdvTags = joLinkedTags.getJSONObject(tags);
                    sType = joLinkedAdvTags.getString("title");
                }
                String sPhotoUrl = "";
                JSONObject joLinkedUploads = joLinked.getJSONObject("uploads");
                if (origin != "") {
                    if (joLinkedUploads.has(origin)) {
                        JSONObject joLinkedUploadsOrigin = joLinkedUploads.getJSONObject(origin);
                        String sFileName = joLinkedUploadsOrigin.getString("file_name");
                        String sFileExtension = joLinkedUploadsOrigin.getString("file_extension");
                        String sDomain = joLinkedUploadsOrigin.getString("domain");
                        sPhotoUrl = sDomain + sFileName + "." + sFileExtension;
                    }
                }

                listAdv.add(new Adverts(sTitle, sUpdateDate, sCost, sType, sPhotoUrl));
            }
        return listAdv;
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        return null;
    }
}
package com.example.bobgranata.testtaskforandroid;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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

            String tags = "";
            String origin = "";

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject advertsMember = jsonArray.getJSONObject(i);
                String sTitle = advertsMember.getString("title");
                String sCost = advertsMember.getString("cost");
                String sUpdateDate = advertsMember.getString("update_date");

                JSONObject joLinksTags = advertsMember.getJSONObject("links");
                JSONArray jsonArrayTags = joLinksTags.getJSONArray("tags");


                if (jsonArrayTags.length() != 0) {
                    tags = jsonArrayTags.getString(0);
                }

                JSONObject joShortImages = advertsMember.getJSONObject("short_images");
                int countImg = joShortImages.getInt("total");

                JSONObject joShortImagesMain = joShortImages.getJSONObject("main");
                JSONObject joShortImagesMainLinks = joShortImagesMain.getJSONObject("links");
                origin = joShortImagesMainLinks.getString("origin");
            }

            String sAdvTagsTitle = "";
            JSONObject joLinkedTags = joLinked.getJSONObject("tags");
            if (tags != "") {
                JSONObject joLinkedAdvTags = joLinkedTags.getJSONObject(tags);
                sAdvTagsTitle = joLinkedAdvTags.getString("title");
            }
            JSONObject joLinkedUploads = joLinked.getJSONObject("uploads");
            if (origin != "") {
                JSONObject joLinkedUploadsOrigin = joLinkedUploads.getJSONObject(origin);
                joLinkedUploadsOrigin.getString("file_name");
                joLinkedUploadsOrigin.getString("file_extension");
                joLinkedUploadsOrigin.getString("domain");
            }

        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        return null;
    }
}
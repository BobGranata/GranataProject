package com.bobgranata.linkservice.Tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.bobgranata.linkservice.Activity.LoginActivity;
import com.bobgranata.linkservice.Models.InfoModel;
import com.bobgranata.linkservice.Parsers.XMLParserInfcom;
import com.bobgranata.linkservice.Parsers.XMLParserRole;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by BobGranata on 26.06.2016.
 */
public class LoginTask extends AsyncTask<String, Void, List> {
    private static final int NET_READ_TIMEOUT_MILLIS = 30000;
    private static final int NET_CONNECT_TIMEOUT_MILLIS = 30000;
    Context mmContext;

    private final String URL_SERVER = "http://92.43.187.142:4000/";
    private final String METHOD = "GetRole";
    private final String ACCESS_KEY = "access_key";

    public LoginTask(Context context) {
        super();
        mmContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        ((LoginActivity) mmContext).mmEtLogin.setText("Полез на крышу");
    }

    @Override
    protected List doInBackground(String... params) {

        String url = URL_SERVER + METHOD + "?" + ACCESS_KEY + "=" + params[0];
        return loadXML(url);
    }

    public List loadXML(String url) {

        // создаём HTTP запрос
        InputStream isReqHttpAnswer = null;
//        boolean successReq = false;
//        try {
//            String method = "GET";
//
//            // здесь параметры необходимые в запрос добавляем
//            List<NameValuePair> params = new ArrayList<NameValuePair>();
//
//
//            if (method == "POST") {
//                DefaultHttpClient httpClient = new DefaultHttpClient();
//                HttpPost httpPost = new HttpPost(url);
//                httpPost.setEntity(new UrlEncodedFormEntity(params));
//
//                HttpResponse httpResponse = httpClient.execute(httpPost);
//                HttpEntity httpEntity = httpResponse.getEntity();
//                isReqHttpAnswer = httpEntity.getContent();
//
//            } else if (method == "GET") {
//
//                DefaultHttpClient httpClient = new DefaultHttpClient();
//                String paramString = URLEncodedUtils.format(params, "utf-8");
//                HttpGet httpGet = new HttpGet(url);
//
//                HttpResponse httpResponse = httpClient.execute(httpGet);
//                HttpEntity httpEntity = httpResponse.getEntity();
//                isReqHttpAnswer = httpEntity.getContent();
//                successReq = true;
//            }
//
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (ClientProtocolException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if (successReq) {
//            XMLParserRole xmlParserRole = new XMLParserRole(isReqHttpAnswer);
//            List listRoles = xmlParserRole.startParsing();
//            return listRoles;
//        } else {
//            return null;
//        }
        // создаём HTTP запрос

        String sError = "";
        HttpURLConnection connect = null;
        try {
            connect = (HttpURLConnection) new URL(url).openConnection();
            connect.setReadTimeout(NET_READ_TIMEOUT_MILLIS /* milliseconds */);
            connect.setConnectTimeout(NET_CONNECT_TIMEOUT_MILLIS /* milliseconds */);
            connect.setRequestMethod("GET");
            connect.setDoInput(true);
            // Starts the query
            connect.connect();

            int status = connect.getResponseCode();
            switch (status) {
                case 200:
                case 201: {
                    isReqHttpAnswer = connect.getInputStream();

                    XMLParserRole xmlParserRole = new XMLParserRole(isReqHttpAnswer);
                    List listRoles = xmlParserRole.startParsing();
                    return listRoles;
                }
                default: {
                    sError = "Ошибка. Сервер ответил не коректно. Код ответа: " + status;
                }
            }
        } catch (MalformedURLException e) {
            //код обработки ошибки
            e.printStackTrace();
            sError = e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            sError = e.getMessage();
        }
        return null;
    }

    @Override
    protected void onPostExecute(List listRoles) {
        if (listRoles != null) {
            super.onPostExecute(listRoles);

            ((LoginActivity) mmContext).updateRoleList(listRoles);

        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);

//        ((LoginActivity) mmContext).mmEtLogin.setText("Залез");
//        mInfoTextView.setText("Этаж: " + values[0]);
    }
}
package com.bobgranata.linkservice.Tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.bobgranata.linkservice.Activity.InfComActivity;
import com.bobgranata.linkservice.Database.DatabaseHandler;
import com.bobgranata.linkservice.Models.DocCirculModel;
import com.bobgranata.linkservice.Models.DocumentModel;
import com.bobgranata.linkservice.Models.InfComModel;
import com.bobgranata.linkservice.Models.InfoModel;
import com.bobgranata.linkservice.Parsers.XMLParserInfcom;

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
 * Created by BobGranata on 19.07.2016.
 */
public class RoleInfComTask extends AsyncTask<String, Void, InfoModel> {
    private static final int NET_READ_TIMEOUT_MILLIS = 30000;
    private static final int NET_CONNECT_TIMEOUT_MILLIS = 30000;
    Context mmContext;

//    http://92.43.187.142:4000/GetDocs?access_key=632CAC3A&date_last_update=0
    private final String URL_SERVER = "http://92.43.187.142:4000/";
    private final String METHOD = "GetDocs";
    private final String ACCESS_KEY = "access_key";
    private final String DATE_LAST_UPDATE = "date_last_update";

    public RoleInfComTask(Context context) {
        super();
        mmContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        ((LoginActivity) mmContext).mmEtLogin.setText("Полез на крышу");
    }

    @Override
    protected InfoModel doInBackground(String... params) {
        String url = URL_SERVER + METHOD + "?" + ACCESS_KEY + "=" + params[0] + "&" + DATE_LAST_UPDATE + "=" + params[1];
        return loadXML(url);
    }

    public InfoModel loadXML(String url) {

        // создаём HTTP запрос
        InputStream isReqHttpAnswer = null;
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

                    XMLParserInfcom xmlParserInfcom = new XMLParserInfcom(isReqHttpAnswer);
                    InfoModel imAllInfo = xmlParserInfcom.startParsing();

                    DatabaseHandler db = new DatabaseHandler(mmContext);
                    if (imAllInfo != null) {
                        for (InfComModel infcom : imAllInfo.getListInfCom()) {
                            db.addInfCom(infcom);
                        }

                        List<DocCirculModel> listDocCircul = imAllInfo.getListDocCircul();
                        for (DocCirculModel doccircul : listDocCircul) {
                            db.addDocCircul(doccircul);
                        }

                        for (DocumentModel document : imAllInfo.getListDocument()) {
                            db.addDocument(document);
                        }
                    }

                    return imAllInfo;
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

//        Toast toast = Toast.makeText(mmContext,
//                sError , Toast.LENGTH_SHORT);
//        toast.show();

        return null;
    }

    @Override
    protected void onPostExecute(InfoModel AllInfo) {
//        if (AllInfo != null) {
            super.onPostExecute(AllInfo);

            ((InfComActivity) mmContext).updateInfComList(AllInfo);
//        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);

    }
}
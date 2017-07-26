package com.example.bobgranata.testtaskforandroid;

import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by BobGranata on 26.07.2017.
 */

public class RequestDataTask extends AsyncTask<String, Void, String> {
    private static final int NET_READ_TIMEOUT_MILLIS = 1000;
    private static final int NET_CONNECT_TIMEOUT_MILLIS = 1000;

    //http://do.ngs.ru/api/v1/adverts/?include=uploads,tags&fields=short_images,cost,update_date&limit=1&offset=0
    private final String URL_SERVER = "http://do.ngs.ru/api/v1/adverts/?include=uploads,tags&fields=short_images,cost,update_date";
    private final String LIMIT = "limit";
    private final String OFFSET = "offset";

    Context mmContext;


    public RequestDataTask(Context context) {
        super();
        mmContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        ((LoginActivity) mmContext).mmEtLogin.setText("Полез на крышу");
    }

    @Override
    protected String doInBackground(String... params) {
        String sLimit = params[0];
        String sOffset = params[1];
        String url = "";

        url = URL_SERVER + "&" + LIMIT + "=" + sLimit + "&" + OFFSET + "=" + sOffset;

        return loadJSON(url);
    }

    public String loadJSON(String url) {

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
            try {
                connect.connect();
            } catch (Exception e) {
                String sss = e.getMessage();
                String sss2 = e.getMessage();
            }
            int status = connect.getResponseCode();
            switch (status) {
                case 200:
                case 201: {
                    isReqHttpAnswer = connect.getInputStream();
                    JSONParser jParser = new JSONParser(isReqHttpAnswer);
                    jParser.startParsing();
                } break;
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

        return sError;
    }

    @Override
    protected void onPostExecute(String sError) {
//        if (AllInfo != null) {
        super.onPostExecute(sError);

//        switch (mmReqMode) {
//            case mInfCom: {
//                ((InfComActivity) mmContext).updateInfComList(sError);
//            } break;
//            case mDocCircul: {
//                ((DocCirculActivity) mmContext).updateDocCirculList(sError);
//            } break;
//        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);

    }
}

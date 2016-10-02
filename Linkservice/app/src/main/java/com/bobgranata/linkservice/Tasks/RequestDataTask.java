package com.bobgranata.linkservice.Tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.bobgranata.linkservice.Activity.DocCirculActivity;
import com.bobgranata.linkservice.Activity.InfComActivity;
import com.bobgranata.linkservice.Database.DatabaseHandler;
import com.bobgranata.linkservice.Models.DocCirculModel;
import com.bobgranata.linkservice.Models.DocumentModel;
import com.bobgranata.linkservice.Models.InfComModel;
import com.bobgranata.linkservice.Models.InfoModel;
import com.bobgranata.linkservice.Parsers.XMLParser;
import com.bobgranata.linkservice.Parsers.XMLParserInfcom;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class RequestDataTask extends AsyncTask<String, Void, String> {
    private static final int NET_READ_TIMEOUT_MILLIS = 1000;
    private static final int NET_CONNECT_TIMEOUT_MILLIS = 1000;

    //    http://92.43.187.142:4000/GetDocs?access_key=632CAC3A&date_last_update=0
    private final String URL_SERVER = "http://92.43.187.142:4000/";
    private final String METHOD = "GetDocs";
    private final String ACCESS_KEY = "access_key";
    private final String DATE_LAST_UPDATE = "date_last_update";
    private final String METHOD_GET_DOCS_INFCOM = "getdocsinfcom";
    private final String METHOD_GET_INFCOM = "getinfcoms";

    private final String ID_INFCOM = "id_infcom";


    enum eMode {mInfCom, mDocCircul};
    eMode mmReqMode;
    Context mmContext;

    String mmIdInfCom;

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
        String modeReq = params[0];
        String url = "";
        if (modeReq == RequestMode.INF_COM) {
            mmReqMode = eMode.mInfCom;
            mmIdInfCom = params[1];
            String sDateLastUpdate = params[2];
//            92.43.187.142:4000/getinfcoms?access_key=1FBAC28E
            url = URL_SERVER + METHOD_GET_INFCOM + "?" + ACCESS_KEY + "=" + mmIdInfCom;
        } else if (modeReq == RequestMode.DOC_CIRCULS) {
            mmReqMode = eMode.mDocCircul;
            mmIdInfCom = params[1];
            String sDateLastUpdate = params[2];
//            92.43.187.142:4000/getdocsinfcom?id_infcom=b55f879a-b410-4c14-a625-2bbeb284f91d&date_last_update=0
            url = URL_SERVER + METHOD_GET_DOCS_INFCOM + "?" + ID_INFCOM + "=" + mmIdInfCom + "&" + DATE_LAST_UPDATE + "=" + sDateLastUpdate;
        }
        return loadXML(url);
    }

    public String loadXML(String url) {

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

                    switch (mmReqMode) {
                        case mInfCom: {
                            XMLParser xmlParser = new XMLParser(isReqHttpAnswer);
                            List<InfComModel> listInfCom = xmlParser.startParsingInfCom();

                            DatabaseHandler db = new DatabaseHandler(mmContext);

                            if (listInfCom != null) {
                                for (InfComModel infcom : listInfCom) {
                                    db.addInfCom(infcom);
                                }
                            }
                        } break;
                        case mDocCircul: {
                            XMLParser xmlParser = new XMLParser(isReqHttpAnswer);
                            List<DocCirculModel> listDocCircul = xmlParser.startParsingDocCircul();

                            DatabaseHandler db = new DatabaseHandler(mmContext);

                            String sDateLastUpdate = xmlParser.getDateLastUpdate();
                            InfComModel mUpdateInfCom = db.getInfCom(mmIdInfCom);
                            mUpdateInfCom.setDateLastUpdate(sDateLastUpdate);
                            db.updateInfCom(mUpdateInfCom);

                            if (listDocCircul != null) {
                                for (DocCirculModel docCircul : listDocCircul) {
                                    db.addDocCircul(docCircul);

                                    for (DocumentModel document : docCircul.getListDocument()) {
                                        db.addDocument(document);
                                    }
                                }
                            }
                        } break;
                    }

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

//        Toast toast = Toast.makeText(mmContext,
//                sError , Toast.LENGTH_SHORT);
//        toast.show();

        return sError;
    }

    @Override
    protected void onPostExecute(String sError) {
//        if (AllInfo != null) {
        super.onPostExecute(sError);

        switch (mmReqMode) {
            case mInfCom: {
                ((InfComActivity) mmContext).updateInfComList(sError);
            } break;
            case mDocCircul: {
                ((DocCirculActivity) mmContext).updateDocCirculList(sError);
            } break;
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);

    }
}

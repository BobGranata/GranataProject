package com.bobgranata.questionary;

import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BobGranata on 13.12.2015.
 */
public class UpdateTask extends AsyncTask<String, Void, JSONObject> {
    Context context;

    public UpdateTask(Context context) {
        super();
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(String... urls) {

        return loadJSON(urls[0]);
    }

    public JSONObject loadJSON(String url) {

        JSONParser jParser = new JSONParser();
        // здесь параметры необходимые в запрос добавляем
        List<NameValuePair> params = new ArrayList<NameValuePair>();

//        + "&results=1"
//        format=json
//        params.add(new BasicNameValuePair("results", "1"));
//        params.add(new BasicNameValuePair("format", "json"));
//        TODO Нужно что бы парамсы заносились из вне
        // посылаем запрос методом GET
        JSONObject json = jParser.makeHttpRequest(url, "GET", params);

        return json;
    }

    @Override
    protected void onPostExecute(JSONObject jsonData) {
        // если какой-то фейл, проверяем на null
        // фейл может быть по многим причинам: сервер сдох, нет сети на устройстве и т.д.
        if (jsonData != null) {
            super.onPostExecute(jsonData);
            String res = "";
            try {
                // прочитать параметр, который отправил сервер;
                // здесь вместо "result" подставляйте то, что вам надо
//                res = jsonData.getString("result");

                JSONObject response = jsonData.getJSONObject("response");
                JSONObject GeoObjectCollection = response.getJSONObject("GeoObjectCollection");

                JSONArray jsonArray = GeoObjectCollection.getJSONArray("featureMember");

                String street = "";
                String house = "";
                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject featureMember = jsonArray.getJSONObject(i);
                    JSONObject GeoObject = featureMember.getJSONObject("GeoObject");
                    JSONObject metaDataProperty = GeoObject.getJSONObject("metaDataProperty");
                    JSONObject GeocoderMetaData = metaDataProperty.getJSONObject("GeocoderMetaData");

                    JSONObject AddressDetails = GeocoderMetaData.getJSONObject("AddressDetails");
                    JSONObject Country = AddressDetails.getJSONObject("Country");
                    JSONObject AdministrativeArea = Country.getJSONObject("AdministrativeArea");
                    JSONObject SubAdministrativeArea = AdministrativeArea.getJSONObject("SubAdministrativeArea");
                    JSONObject Locality = SubAdministrativeArea.getJSONObject("Locality");

                    JSONObject Thoroughfare;
                    try {
                        Thoroughfare = Locality.getJSONObject("Thoroughfare");
                    } catch (JSONException e) {
                        JSONObject DependentLocality = Locality.getJSONObject("DependentLocality");
                        Thoroughfare = DependentLocality.getJSONObject("Thoroughfare");
                    }


                    street = Thoroughfare.getString("ThoroughfareName");

                    JSONObject Premise = Thoroughfare.getJSONObject("Premise");

                    house = Premise.getString("PremiseNumber");
                }

                // что-то делаем, к примеру вызываем метод главного Activity на обновление GUI
                ((FirstStepActivity) context).updateAdress(street, house);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
//            ((MainActivity) context).updateGUI(res);
        }
    }

}

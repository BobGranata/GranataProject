package com.bobgranata.questionary;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


public class SettingsActivity extends ActionBarActivity {
    private SharedPreferences mSettings;
//    public static final String APP_PREFERENCES_EMAIL = "email";
    public static final String APP_PREFERENCES_FIO = "fio";
    public static final String APP_PREFERENCES_CITY = "city";
    public static final String APP_PREFERENCES_CITYPOS = "citypos";
    public static final String APP_PREFERENCES_AO = "ao";
    public static final String APP_PREFERENCES_AOPOS = "aopos";
    public static final String APP_PREFERENCES_TERNUM = "ternum";
    // это будет именем файла настроек
    public static final String APP_PREFERENCES = "mysettings";

    // город для которого расчитано приложение
    public static final String APP_CITY_TYPT = "Екатеринбург"; //Екатеринбург Москва Казань
    public static final String EKAT_CITY = "Екатеринбург";
    public static final String MOSKOW_CITY = "Москва";
    public static final String KAZAN_CITY = "Казань";

    EditText etFio;
    Spinner spinCity;
    Spinner spinADistrict;
    EditText etTerNum;
    EditText etSettingEmail;
    TextView tvExtraCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        etFio = (EditText)findViewById(R.id.etFio);
        etTerNum = (EditText)findViewById(R.id.etTerNum);
        spinCity = (Spinner)findViewById(R.id.spinCity);
        spinADistrict = (Spinner)findViewById(R.id.spinADistrict);
        etSettingEmail = (EditText)findViewById(R.id.etSettingEmail);
        tvExtraCity = (TextView)findViewById(R.id.tvExtraCity);

        if (!APP_CITY_TYPT.equals(MOSKOW_CITY)) {
            TextView tvADistrict = (TextView) findViewById(R.id.tvADistrict);
            tvADistrict.setVisibility(View.GONE);
            spinADistrict.setVisibility(View.GONE);

//            spinADistrict.setEnabled(false);
//            spinCity.setVisibility(View.GONE);
//            tvExtraCity.setVisibility(View.VISIBLE);
//            tvExtraCity.setText(APP_CITY_TYPT);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

//        if (mSettings.contains(APP_PREFERENCES_EMAIL)) {
//            // Получаем строку из настроек
//            String sEmail = mSettings.getString(APP_PREFERENCES_EMAIL, "");
//            // Выводим на экран данные из настроек
//            etSettingEmail.setText(sEmail);
//        }

//        Екатеринбург Казань

        if (mSettings.contains(APP_PREFERENCES_FIO)) {
            // Получаем строку из настроек
            String Fio = mSettings.getString(APP_PREFERENCES_FIO, "");
            // Выводим на экран данные из настроек
            etFio.setText(Fio);
        }

        if (APP_CITY_TYPT.equals(MOSKOW_CITY)) {
            if (mSettings.contains(APP_PREFERENCES_AOPOS)) {
                int Ao = mSettings.getInt(APP_PREFERENCES_AOPOS, 0);
                spinADistrict.setSelection(Ao);
            }
        }

        if (mSettings.contains(APP_PREFERENCES_CITYPOS)) {
            int CitySpinPos = mSettings.getInt(APP_PREFERENCES_CITYPOS, 0);
            spinCity.setSelection(CitySpinPos);
        }

        if (mSettings.contains(APP_PREFERENCES_TERNUM)) {
            String TerNum = mSettings.getString(APP_PREFERENCES_TERNUM, "");
            etTerNum.setText(TerNum);
        }
    }

    public void onClickSaveSetting(View view) {
        // Запоминаем данные
        SharedPreferences.Editor editor = mSettings.edit();

//        editor.putString(APP_PREFERENCES_EMAIL, etSettingEmail.getText().toString());

        editor.putString(APP_PREFERENCES_FIO, etFio.getText().toString());

        if (!APP_CITY_TYPT.equals(MOSKOW_CITY)) {
            editor.putString(APP_PREFERENCES_AO, "null");
        } else {

            editor.putInt(APP_PREFERENCES_AOPOS, spinADistrict.getSelectedItemPosition());
            String sAo = "";
            if (spinADistrict.getSelectedItemPosition() != 0)
                sAo = spinADistrict.getSelectedItem().toString();
            editor.putString(APP_PREFERENCES_AO, sAo);
        }

        editor.putInt(APP_PREFERENCES_CITYPOS, spinCity.getSelectedItemPosition());
        String City = "";
        if (spinCity.getSelectedItemPosition() != 0)
            City = spinCity.getSelectedItem().toString();
        editor.putString(APP_PREFERENCES_CITY, City);

        editor.putString(APP_PREFERENCES_TERNUM, etTerNum.getText().toString());
        editor.apply();

        finish();
    }

    public void onClickCancelSetting(View view) {
        finish();
    }
}

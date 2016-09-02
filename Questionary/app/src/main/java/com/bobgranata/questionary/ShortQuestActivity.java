package com.bobgranata.questionary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class ShortQuestActivity extends ActionBarActivity {
    ArrayList csvQuestList;
    String uuidQuest;

    Uri mmPhotoUri;
    String mmLongitude;
    String mmLatitude;
    String mmPhotoFileName;

    String mClientAddressTypeStreet;
    String mClientAddressStreet;
    String mClientAddressHouse;
    String mClientAddressCorps;
    String mClientAddressStruct;
    String mClientAddressComment;

    private SharedPreferences mSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_short_quest);

        mmLongitude = getIntent().getExtras().getString("Longitude");
        mmLatitude = getIntent().getExtras().getString("Latitude");

        String strUri = getIntent().getExtras().getString("PhotoUri");
        if (strUri != null) {
            mmPhotoUri = Uri.parse(strUri);
        }
//        mmPhotoFileName = getIntent().getExtras().getString("PhotoFileName");
        mmPhotoFileName = "";
        mClientAddressTypeStreet = getIntent().getExtras().getString("ClientAddressTypeStreet");
        mClientAddressStreet = getIntent().getExtras().getString("ClientAddressStreet");
        mClientAddressHouse = getIntent().getExtras().getString("ClientAddressHouse");
        mClientAddressCorps = getIntent().getExtras().getString("ClientAddressCorps");
        mClientAddressStruct = getIntent().getExtras().getString("ClientAddressStruct");
        mClientAddressComment = getIntent().getExtras().getString("ClientAddressComment");

        String StoreName = getIntent().getExtras().getString("StoreName");
        if (StoreName != "") {
            String[] mArrNameStore = getResources().getStringArray(R.array.spinNameNet);
            Spinner spinNameNet = (Spinner)findViewById(R.id.spinNameNet);
            for (int i = 0; i < mArrNameStore.length; i++) {
                if (mArrNameStore[i].toLowerCase().equals(StoreName.toLowerCase())) {
                    spinNameNet.setSelection(i);
                    break;
                }
            }
        }

        /* Initializing settings */
        mSettings = getSharedPreferences(SettingsActivity.APP_PREFERENCES, Context.MODE_PRIVATE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_short_quest, menu);
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

    public void questXmlWrite(String report, String name, String value, String gps, String dateReport, String timeReport, String web) {
        if (csvQuestList != null) {
//            csvQuestList.add( report + ";" + name + ";" + value + ";" + gps + ";NULL;нет данных;" +
//                    dateReport + ";" + timeReport + ";" + mmPhotoFileName + ";");
            csvQuestList.add(name + ";" + value + ";");
        }
    }

    public void onClickReset(View view) {
        Intent answerIntent = new Intent();

        setResult(RESULT_OK, answerIntent);
        finish();
    }

    public String checkEmptyFild(String fildValue){
        if (!fildValue.equals(""))
            return fildValue;
        else
            return "null";
    }

    public void onClickSaveAndSend(View view) throws IOException {
        csvQuestList = new ArrayList();

        uuidQuest = UUID.randomUUID().toString();

        mmPhotoFileName = uuidQuest + ".jpg";

        String GPS = "\"" + mmLongitude + "," + mmLatitude + "\"";

        Time nowTime = new Time();
        nowTime.setToNow();

        String strCurrentDate = Integer.toString(nowTime.monthDay) + "." + Integer.toString(nowTime.month+1) + "." + Integer.toString(nowTime.year);
        String strCurrentTime = Integer.toString(nowTime.hour)  + ":" + Integer.toString(nowTime.minute);

        String progVersion = getResources().getString(R.string.tvProgramVersion);
        questXmlWrite(uuidQuest, "Номер версии", progVersion, GPS, strCurrentDate, strCurrentTime, "");

        questXmlWrite(uuidQuest, "Дата отправки анкеты", strCurrentDate, GPS, strCurrentDate, strCurrentTime, "");
        questXmlWrite(uuidQuest, "Время отправки анкеты", strCurrentTime, GPS, strCurrentDate, strCurrentTime, "");

        String Fio = "";
        if (mSettings.contains(SettingsActivity.APP_PREFERENCES_FIO)) {
            Fio = mSettings.getString(SettingsActivity.APP_PREFERENCES_FIO, "");
        }

        String TerNum = "";
        if (mSettings.contains(SettingsActivity.APP_PREFERENCES_TERNUM)) {
            TerNum = mSettings.getString(SettingsActivity.APP_PREFERENCES_TERNUM, "");
        }

        questXmlWrite(uuidQuest, "ФИО агента", checkEmptyFild(Fio), GPS, strCurrentDate, strCurrentTime, "");
        questXmlWrite(uuidQuest, "Номер анкеты", uuidQuest, GPS, strCurrentDate, strCurrentTime, "");
        questXmlWrite(uuidQuest, "Номер участка", checkEmptyFild(TerNum), GPS, strCurrentDate, strCurrentTime, "");

        Spinner spinNameNet = (Spinner)findViewById(R.id.spinNameNet);
        if (spinNameNet.getSelectedItemPosition() != 0) {
            String selected = spinNameNet.getSelectedItem().toString();
            questXmlWrite(uuidQuest, "Название на вывеске", selected, GPS, strCurrentDate, strCurrentTime, ""); //Название сети
        } else {
            questXmlWrite(uuidQuest, "Название на вывеске", "null", GPS, strCurrentDate, strCurrentTime, ""); //Название сети
        }

        if (mSettings.contains(SettingsActivity.APP_PREFERENCES_AO)) {
            String Ao = mSettings.getString(SettingsActivity.APP_PREFERENCES_AO, "");
            questXmlWrite(uuidQuest, "АОкруг", Ao, GPS, strCurrentDate, strCurrentTime, "");
        }

        if (mSettings.contains(SettingsActivity.APP_PREFERENCES_CITY)) {
            String CitySpinPos = mSettings.getString(SettingsActivity.APP_PREFERENCES_CITY, "");
            if (CitySpinPos != "") {
                questXmlWrite(uuidQuest, "Город", CitySpinPos, GPS, strCurrentDate, strCurrentTime, "");
            }
        }

        questXmlWrite(uuidQuest, "Тип улицы", checkEmptyFild(mClientAddressTypeStreet), GPS, strCurrentDate, strCurrentTime, "");

        questXmlWrite(uuidQuest, "Адрес клиента: улица", checkEmptyFild(mClientAddressStreet), GPS, strCurrentDate, strCurrentTime, "");

        questXmlWrite(uuidQuest, "Адрес клиента: дом", checkEmptyFild(mClientAddressHouse), GPS, strCurrentDate, strCurrentTime, "");

        questXmlWrite(uuidQuest, "Адрес клиента: корпус", checkEmptyFild(mClientAddressCorps), GPS, strCurrentDate, strCurrentTime, "");

        questXmlWrite(uuidQuest, "Адрес клиента: строение", checkEmptyFild(mClientAddressStruct), GPS, strCurrentDate, strCurrentTime, "");

        questXmlWrite(uuidQuest, "Адрес клиента: комментарий", checkEmptyFild(mClientAddressComment), GPS, strCurrentDate, strCurrentTime, "");

        EditText etNumberBanks = (EditText)findViewById(R.id.etNumberBanks);

        questXmlWrite(uuidQuest, "Количество касс для розничных точек", checkEmptyFild(etNumberBanks.getText().toString()), GPS, strCurrentDate, strCurrentTime, ""); //Количество касс

        EditText etNumbPOSCoolCoca = (EditText)findViewById(R.id.etNumbPOSCoolCoca);

        questXmlWrite(uuidQuest, "Количество кассовых кулеров компании Кока-Кола", checkEmptyFild(etNumbPOSCoolCoca.getText().toString()), GPS, strCurrentDate, strCurrentTime, "");

        EditText etNumbPOSCoolOther = (EditText)findViewById(R.id.etNumbPOSCoolOther);

        questXmlWrite(uuidQuest, "Количество кассовых кулеров других производителей", checkEmptyFild(etNumbPOSCoolOther.getText().toString()), GPS, strCurrentDate, strCurrentTime, "");

        EditText etCommonComment = (EditText)findViewById(R.id.etShortCommonComment);

        questXmlWrite(uuidQuest, "Общие комментарии", checkEmptyFild(etCommonComment.getText().toString()), GPS, strCurrentDate, strCurrentTime, "");

        CheckBox cbNotCalc = (CheckBox)findViewById(R.id.cbShortNotCalc);
        String YesOrNo = "Нет";
        if (cbNotCalc.isChecked())
            YesOrNo = "Да";
        String value = "Анкету не обрабатывать";
        questXmlWrite(uuidQuest, value, YesOrNo, GPS, strCurrentDate, strCurrentTime, "");

        questXmlWrite(uuidQuest, "Имя файла фотографии", mmPhotoFileName, GPS, strCurrentDate, strCurrentTime, "");
        questXmlWrite(uuidQuest, "Координаты", mmLongitude + "," + mmLatitude, GPS, strCurrentDate, strCurrentTime, "");
//===================================

        String fullQuestCSV = "";
        for (int i = 0; i < csvQuestList.size(); i++) {
            fullQuestCSV += csvQuestList.get(i).toString() + "\n";
        }

        String folderToSave = Environment.getExternalStorageDirectory().toString(); // папка куда сохранять, в данном случае - корень SD-карты

        Time time = new Time();
        time.setToNow();

//        folderToSave = folderToSave + File.separator + "2321312" + "Questionary.csv";
        folderToSave = folderToSave + File.separator + Integer.toString(time.year) + "_" + Integer.toString(time.month+1) + "_" +
                Integer.toString(time.monthDay)  + "_" +  Integer.toString(time.hour)  + "_" +
                Integer.toString(time.minute)  + "_" +  Integer.toString(time.second) + "_" +
                "Questions.csv"; //"Questionary.csv";

        File fileNameSave = new File(folderToSave);

        fileNameSave.getParentFile().mkdirs();
        try {
            FileWriter f = new FileWriter(fileNameSave);
            f.write(fullQuestCSV); //csvQuestList.toString()
            f.flush();
            f.close();
        } catch (Exception e) {
//            Toast.makeText();
        }
//------------------------------------------------------------------------------------------
//      // папка куда сохранять, в данном случае - корень SD-карты
        String fileName = folderToSave;
//        String fileName = Environment.getExternalStorageDirectory().toString() + File.separator + "Questionary.csv";

        byte[] image;
        if (mmPhotoUri != null) {
            ImageView photoQuest = new ImageView(this);
            photoQuest.setImageURI(mmPhotoUri);

            Bitmap bitmap = ((BitmapDrawable) photoQuest.getDrawable()).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 40, stream);
            image = stream.toByteArray();
        } else {
            image = new byte[1];
        }

        String FileNameInZip = uuidQuest; //UUID.randomUUID().toString();
        String zipFileName = Environment.getExternalStorageDirectory().toString() + File.separator + "questions.zip"; //questionary
        FileOutputStream fout = new FileOutputStream(zipFileName);
        ZipOutputStream zout = new ZipOutputStream(fout);
        //Для всех файлов:
        {
            ZipEntry ze = new ZipEntry(FileNameInZip + ".csv");//Имя файла - имя файла в архиве
            zout.putNextEntry(ze);

            FileInputStream fis = null;  // 2nd line

            try {
                fis = new FileInputStream(new File(fileName));
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader reader = new BufferedReader(isr);
                String line;
                StringBuilder builder = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    builder.append(line + "\n");
                }
                byte[] byteCsvQuest = builder.toString().getBytes();
                zout.write(byteCsvQuest);
                fis.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            zout.closeEntry();

            if (mmPhotoUri != null) {
                ZipEntry ze2 = new ZipEntry(FileNameInZip + ".jpg");//Имя файла - имя файла в архиве photo
                zout.putNextEntry(ze2);
                zout.write(image);
                zout.closeEntry();
            }
        }
        zout.close();

        String sEmail = "rep_short@rabota2008.ru"; //test1@rabota2008.ru
//        if (mSettings.contains(SettingsActivity.APP_PREFERENCES_EMAIL)) {
//            // Получаем строку из настроек
//            String sSettingEmail = mSettings.getString(SettingsActivity.APP_PREFERENCES_EMAIL, "");
//            if (sSettingEmail != "")
//                sEmail = sSettingEmail;
//        }

        String City = "";
        if (mSettings.contains(SettingsActivity.APP_PREFERENCES_CITY)) {
            City = mSettings.getString(SettingsActivity.APP_PREFERENCES_CITY, "");
        }

        String MessageTitle = "";
        if (City != "")
            MessageTitle += City;
        if (Fio != "")
            MessageTitle += "-" + Fio;
        if (TerNum != "")
            MessageTitle += "-" + TerNum;
        MessageTitle += "-" + UUID.randomUUID().toString();


        final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

        emailIntent.setType("plain/text");
        // Кому
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                new String[] { sEmail });
//                new String[] { "nedosekin@link-service.ru" });
        // Тема
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                MessageTitle);
        // Содержание
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                "Анкета");
        // С чем
        emailIntent.putExtra(
                android.content.Intent.EXTRA_STREAM,
                Uri.parse("file://"
                        + zipFileName));
//                        + Environment.getExternalStorageDirectory().toString() + File.separator + "test.zip")); //fileName2
        emailIntent.setType("text/video");

        // Поехали!
        ShortQuestActivity.this.startActivity(Intent.createChooser(emailIntent,
                "Отправка письма..."));

    }
}

package com.bobgranata.questionary;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;


public class MainQuest extends ActionBarActivity { // implements LocationListener
    ArrayList csvQuestList;
    String uuidQuest;

    Uri mmPhotoUri;
    String mmLongitude;
    String mmLatitude;
    String mmPhotoFileName;
    String mmStoreName;
    String mClientAddressTypeStreet;
    String mClientAddressStreet;
    String mClientAddressHouse;
    String mClientAddressCorps;
    String mClientAddressStruct;
    String mClientAddressComment;

    private Spinner mmSpinMarketArea;
    private Spinner mmSpinTypeChannel;
    private Spinner mmspinTypeService;
    private EditText etINN;
    private TextView tvInnChecker;
    private TextView mTvHaveDrinksCocaChecker;

    private SharedPreferences mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_quest_second);

        mmLongitude = getIntent().getExtras().getString("Longitude");
        mmLatitude = getIntent().getExtras().getString("Latitude");

        String strUri = getIntent().getExtras().getString("PhotoUri");

        if (strUri != null) {
            mmPhotoUri = Uri.parse(strUri);
        }

        mmPhotoFileName = "";

        mmStoreName = getIntent().getExtras().getString("StoreName");
        EditText etNameOnSign = (EditText)findViewById(R.id.etNameOnSign);
        if (!mmStoreName.equals("")) {
            etNameOnSign.setText(mmStoreName);
        }

        mClientAddressTypeStreet = getIntent().getExtras().getString("ClientAddressTypeStreet");
        mClientAddressStreet = getIntent().getExtras().getString("ClientAddressStreet");
        mClientAddressHouse = getIntent().getExtras().getString("ClientAddressHouse");
        mClientAddressCorps = getIntent().getExtras().getString("ClientAddressCorps");
        mClientAddressStruct = getIntent().getExtras().getString("ClientAddressStruct");
        mClientAddressComment = getIntent().getExtras().getString("ClientAddressComment");

        /* Initializing settings */
        mSettings = getSharedPreferences(SettingsActivity.APP_PREFERENCES, Context.MODE_PRIVATE);

        /* Initializing views */
        etINN = (EditText)findViewById(R.id.etINN);

        mTvHaveDrinksCocaChecker = (TextView)findViewById(R.id.tvHaveDrinksCocaChecker);
        mTvHaveDrinksCocaChecker.setVisibility(View.GONE);

        tvInnChecker = (TextView) findViewById(R.id.tvInnChecker);
        tvInnChecker.setVisibility(View.GONE);

        /* Set Text Watcher listener */
        etINN.addTextChangedListener(passwordWatcher);

        mmSpinMarketArea = (Spinner) findViewById(R.id.spinMarketArea);
        mmSpinTypeChannel = (Spinner) findViewById(R.id.spinTypeChannel);
        //TODO
        // Настраиваем адаптер 0
        final ArrayAdapter<?> adapterTypeChannel =
                ArrayAdapter.createFromResource(this, R.array.spinTypeChannel, android.R.layout.simple_spinner_item);
        adapterTypeChannel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Настраиваем адаптер 1
        final ArrayAdapter<?> adapterTypeChannel_1 =
                ArrayAdapter.createFromResource(this, R.array.spinTypeChannel_1, android.R.layout.simple_spinner_item);
        adapterTypeChannel_1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Настраиваем адаптер 2
        final ArrayAdapter<?> adapterTypeChannel_2 =
                ArrayAdapter.createFromResource(this, R.array.spinTypeChannel_2, android.R.layout.simple_spinner_item);
        adapterTypeChannel_2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Настраиваем адаптер 3
        final ArrayAdapter<?> adapterTypeChannel_3 =
                ArrayAdapter.createFromResource(this, R.array.spinTypeChannel_3, android.R.layout.simple_spinner_item);
        adapterTypeChannel_3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Настраиваем адаптер 4
        final ArrayAdapter<?> adapterTypeChannel_4 =
                ArrayAdapter.createFromResource(this, R.array.spinTypeChannel_4, android.R.layout.simple_spinner_item);
        adapterTypeChannel_4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Настраиваем адаптер 5
        final ArrayAdapter<?> adapterTypeChannel_5 =
                ArrayAdapter.createFromResource(this, R.array.spinTypeChannel_5, android.R.layout.simple_spinner_item);
        adapterTypeChannel_5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mmSpinMarketArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {
                switch (selectedItemPosition) {
                    case 0:{
                        // Вызываем адаптер
                        mmSpinTypeChannel.setAdapter(adapterTypeChannel);
                    } break;
                    case 1:{
                        // Вызываем адаптер
                        mmSpinTypeChannel.setAdapter(adapterTypeChannel_1);
                        String[] choose = getResources().getStringArray(R.array.spinMarketArea);
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Ваш выбор: " + choose[selectedItemPosition], Toast.LENGTH_SHORT);
                        toast.show();
                    } break;
                    case 2:{
                        // Вызываем адаптер
                        mmSpinTypeChannel.setAdapter(adapterTypeChannel_2);
                        String[] choose = getResources().getStringArray(R.array.spinMarketArea);
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Ваш выбор: " + choose[selectedItemPosition], Toast.LENGTH_SHORT);
                        toast.show();
                    } break;
                    case 3:{
                        // Вызываем адаптер
                        mmSpinTypeChannel.setAdapter(adapterTypeChannel_3);
                        String[] choose = getResources().getStringArray(R.array.spinMarketArea);
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Ваш выбор: " + choose[selectedItemPosition], Toast.LENGTH_SHORT);
                        toast.show();
                    } break;
                    case 4:{
                        // Вызываем адаптер
                        mmSpinTypeChannel.setAdapter(adapterTypeChannel_4);
                        String[] choose = getResources().getStringArray(R.array.spinMarketArea);
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Ваш выбор: " + choose[selectedItemPosition], Toast.LENGTH_SHORT);
                        toast.show();
                    } break;
                    case 5:{
                        // Вызываем адаптер
                        mmSpinTypeChannel.setAdapter(adapterTypeChannel_5);
                        String[] choose = getResources().getStringArray(R.array.spinMarketArea);
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Ваш выбор: " + choose[selectedItemPosition], Toast.LENGTH_SHORT);
                        toast.show();
                    } break;
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mmspinTypeService = (Spinner) findViewById(R.id.spinTypeService);
        mmspinTypeService.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {
                switch (selectedItemPosition) {
                    case 0:{
                        mmspinTypeService.setAdapter(adapterTypeChannel);
                    } break;
                    case 1:{
                        mmspinTypeService.setAdapter(adapterTypeChannel_1);
                    } break;
                    case 2:{
                        mmspinTypeService.setAdapter(adapterTypeChannel_2);
                    } break;
                    case 3:{
                        mmspinTypeService.setAdapter(adapterTypeChannel_3);
                    } break;
                    case 4:{
                        mmspinTypeService.setAdapter(adapterTypeChannel_4);
                    } break;
                    case 5:{
                        mmspinTypeService.setAdapter(adapterTypeChannel_5);
                    } break;
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private final TextWatcher passwordWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            tvInnChecker.setVisibility(View.VISIBLE);
        }

        public void afterTextChanged(Editable s) {
            if (s.length() == 0) {
                tvInnChecker.setVisibility(View.GONE);
            } else if (s.length() < 10 || s.length() == 11) {
                tvInnChecker.setText("Длинна ИНН должна быть 10 или 12 символов: " + etINN.getText());
            } else {
                tvInnChecker.setVisibility(View.GONE);
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_quest, menu);
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

        try {
            if (etINN.getText().toString().length() < 10 || etINN.getText().toString().length() == 11) {
                showAlert("Введён некорректный ИНН.\nИНН должен быть 10 или 12 цифр.");
                return;
            }

            csvQuestList = new ArrayList();

            uuidQuest = UUID.randomUUID().toString();

            mmPhotoFileName = uuidQuest + ".jpg";

            String GPS = "\"" + mmLongitude + "," + mmLatitude + "\"";

            Time nowTime = new Time();
            nowTime.setToNow();

            String strCurrentDate = Integer.toString(nowTime.monthDay) + "." + Integer.toString(nowTime.month + 1) + "." + Integer.toString(nowTime.year);
            String strCurrentTime = Integer.toString(nowTime.hour) + ":" + Integer.toString(nowTime.minute);

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

            EditText etLegalEntity = (EditText) findViewById(R.id.etLegalEntity);
            questXmlWrite(uuidQuest, "Юридическое лицо (название с указанием орг-правовой формы)", checkEmptyFild(etLegalEntity.getText().toString()), GPS, strCurrentDate, strCurrentTime, "");

            questXmlWrite(uuidQuest, "ИНН", checkEmptyFild(etINN.getText().toString()), GPS, strCurrentDate, strCurrentTime, "");

            EditText etNameOnSign = (EditText) findViewById(R.id.etNameOnSign);
            questXmlWrite(uuidQuest, "Название на вывеске", checkEmptyFild(etNameOnSign.getText().toString()), GPS, strCurrentDate, strCurrentTime, "");

            if (mSettings.contains(SettingsActivity.APP_PREFERENCES_AO)) {
                String Ao = mSettings.getString(SettingsActivity.APP_PREFERENCES_AO, "");
                questXmlWrite(uuidQuest, "АОкруг", Ao, GPS, strCurrentDate, strCurrentTime, "");
            }

            if (mSettings.contains(SettingsActivity.APP_PREFERENCES_CITY)) {
                String CitySpinPos = mSettings.getString(SettingsActivity.APP_PREFERENCES_CITY, "");
                if (!CitySpinPos.equals("")) {
                    questXmlWrite(uuidQuest, "Город", CitySpinPos, GPS, strCurrentDate, strCurrentTime, "");
                }
            }

            questXmlWrite(uuidQuest, "Тип улицы", checkEmptyFild(mClientAddressTypeStreet), GPS, strCurrentDate, strCurrentTime, "");

            questXmlWrite(uuidQuest, "Адрес клиента: улица", checkEmptyFild(mClientAddressStreet), GPS, strCurrentDate, strCurrentTime, "");

            questXmlWrite(uuidQuest, "Адрес клиента: дом", checkEmptyFild(mClientAddressHouse), GPS, strCurrentDate, strCurrentTime, "");

            questXmlWrite(uuidQuest, "Адрес клиента: корпус", checkEmptyFild(mClientAddressCorps), GPS, strCurrentDate, strCurrentTime, "");

            questXmlWrite(uuidQuest, "Адрес клиента: строение", checkEmptyFild(mClientAddressStruct), GPS, strCurrentDate, strCurrentTime, "");

            questXmlWrite(uuidQuest, "Адрес клиента: комментарий", checkEmptyFild(mClientAddressComment), GPS, strCurrentDate, strCurrentTime, "");

            mmSpinTypeChannel = (Spinner) findViewById(R.id.spinTypeChannel);
            if (mmSpinTypeChannel.getSelectedItemPosition() != 0) {
                String selected = mmSpinTypeChannel.getSelectedItem().toString();
                questXmlWrite(uuidQuest, "Тип (канал)", selected, GPS, strCurrentDate, strCurrentTime, "");
            } else {
                questXmlWrite(uuidQuest, "Тип (канал)", "null", GPS, strCurrentDate, strCurrentTime, "");
            }

            Spinner spinOccasionCons = (Spinner) findViewById(R.id.spinOccasionCons);
            if (spinOccasionCons.getSelectedItemPosition() != 0) {
                String selected = spinOccasionCons.getSelectedItem().toString();
                questXmlWrite(uuidQuest, "Повод потребления", selected, GPS, strCurrentDate, strCurrentTime, "");
            } else {
                questXmlWrite(uuidQuest, "Повод потребления", "null", GPS, strCurrentDate, strCurrentTime, "");
            }

            mmspinTypeService = (Spinner) findViewById(R.id.spinTypeService);
            if (mmspinTypeService.getSelectedItemPosition() != 0) {
                String selected = mmspinTypeService.getSelectedItem().toString();
                questXmlWrite(uuidQuest, "Тип обслуживания", selected, GPS, strCurrentDate, strCurrentTime, "");
            } else {
                questXmlWrite(uuidQuest, "Тип обслуживания", "null", GPS, strCurrentDate, strCurrentTime, "");
            }

            EditText etNumberBanksRetailOutlets = (EditText) findViewById(R.id.etNumberBanksRetailOutlets);
            questXmlWrite(uuidQuest, "Количество касс для розничных точек", checkEmptyFild(etNumberBanksRetailOutlets.getText().toString()), GPS, strCurrentDate, strCurrentTime, "");

            mmSpinMarketArea = (Spinner) findViewById(R.id.spinMarketArea);
            if (mmSpinMarketArea.getSelectedItemPosition() != 0) {
                String selected = mmSpinMarketArea.getSelectedItem().toString();
                questXmlWrite(uuidQuest, "Торговая площадь", selected, GPS, strCurrentDate, strCurrentTime, "");
            } else {
                questXmlWrite(uuidQuest, "Торговая площадь", "null", GPS, strCurrentDate, strCurrentTime, "");
            }

            Spinner spinSpecSalePoint = (Spinner) findViewById(R.id.spinSpecSalePoint);
            String selectedSpec = spinSpecSalePoint.getSelectedItem().toString();
            questXmlWrite(uuidQuest, "Специализация розничной точки", selectedSpec, GPS, strCurrentDate, strCurrentTime, "");

            EditText etNameConnectFace = (EditText) findViewById(R.id.etNameConnectFace);
            questXmlWrite(uuidQuest, "Имя контактного лица", checkEmptyFild(etNameConnectFace.getText().toString()), GPS, strCurrentDate, strCurrentTime, "");

            EditText etPhoneConnectFace = (EditText) findViewById(R.id.etPhoneConnectFace);
            questXmlWrite(uuidQuest, "Телефон контактного лица", checkEmptyFild(etPhoneConnectFace.getText().toString()), GPS, strCurrentDate, strCurrentTime, "");

            Spinner spinAverageVisitorsPerDay = (Spinner) findViewById(R.id.spinAverageVisitorsPerDay);
            if (spinAverageVisitorsPerDay.getSelectedItemPosition() != 0) {
                String selected = spinAverageVisitorsPerDay.getSelectedItem().toString();
                questXmlWrite(uuidQuest, "Среднее количество посетителей за день", selected, GPS, strCurrentDate, strCurrentTime, "");
            } else {
                questXmlWrite(uuidQuest, "Среднее количество посетителей за день", "null", GPS, strCurrentDate, strCurrentTime, "");
            }

            Spinner spinMethodForProducingInn = (Spinner) findViewById(R.id.spinMethodForProducingInn);
            if (spinMethodForProducingInn.getSelectedItemPosition() != 0) {
                String selected = spinMethodForProducingInn.getSelectedItem().toString();
                questXmlWrite(uuidQuest, "Способ получения ИНН", selected, GPS, strCurrentDate, strCurrentTime, "");
            } else {
                questXmlWrite(uuidQuest, "Способ получения ИНН", "null", GPS, strCurrentDate, strCurrentTime, "");
            }

            CheckBox cbPresenceCarbonDrinks = (CheckBox) findViewById(R.id.cbPresenceCarbonDrinks);
            String YesOrNo = "Нет";
            if (cbPresenceCarbonDrinks.isChecked())
                YesOrNo = "Да";
            questXmlWrite(uuidQuest, "Наличие газированных напитков", YesOrNo, GPS, strCurrentDate, strCurrentTime, "");

            CheckBox cbPresenceColdTea = (CheckBox) findViewById(R.id.cbPresenceColdTea);
            YesOrNo = "Нет";
            if (cbPresenceColdTea.isChecked())
                YesOrNo = "Да";
            questXmlWrite(uuidQuest, "Наличие холодного чая", YesOrNo, GPS, strCurrentDate, strCurrentTime, "");

            CheckBox cbHaveBottledWater = (CheckBox) findViewById(R.id.cbHaveBottledWater);
            YesOrNo = "Нет";
            if (cbHaveBottledWater.isChecked())
                YesOrNo = "Да";
            questXmlWrite(uuidQuest, "Наличие бутилированной воды", YesOrNo, GPS, strCurrentDate, strCurrentTime, "");

            CheckBox cbAvailabilityPower = (CheckBox) findViewById(R.id.cbAvailabilityPower);
            YesOrNo = "Нет";
            if (cbAvailabilityPower.isChecked())
                YesOrNo = "Да";
            questXmlWrite(uuidQuest, "Наличие энергетиков", YesOrNo, GPS, strCurrentDate, strCurrentTime, "");

            CheckBox cbAvailabilityKvass = (CheckBox) findViewById(R.id.cbAvailabilityKvass);
            YesOrNo = "Нет";
            if (cbAvailabilityKvass.isChecked())
                YesOrNo = "Да";
            questXmlWrite(uuidQuest, "Наличие кваса", YesOrNo, GPS, strCurrentDate, strCurrentTime, "");

            CheckBox cbAvailabilityJuices = (CheckBox) findViewById(R.id.cbAvailabilityJuices);
            YesOrNo = "Нет";
            if (cbAvailabilityJuices.isChecked())
                YesOrNo = "Да";
            questXmlWrite(uuidQuest, "Наличие соков", YesOrNo, GPS, strCurrentDate, strCurrentTime, "");

            CheckBox cbPresenceBeer = (CheckBox) findViewById(R.id.cbPresenceBeer);
            YesOrNo = "Нет";
            if (cbPresenceBeer.isChecked())
                YesOrNo = "Да";
            questXmlWrite(uuidQuest, "Наличие пива", YesOrNo, GPS, strCurrentDate, strCurrentTime, "");

            CheckBox cbAlcoholSpiritsWine = (CheckBox) findViewById(R.id.cbAlcoholSpiritsWine);
            YesOrNo = "Нет";
            if (cbAlcoholSpiritsWine.isChecked())
                YesOrNo = "Да";
            questXmlWrite(uuidQuest, "Наличие алкоголя (крепкие напитки, вино)", YesOrNo, GPS, strCurrentDate, strCurrentTime, "");

            CheckBox cbHaveDrinksCocaCompany = (CheckBox) findViewById(R.id.cbHaveDrinksCocaCompany);
            YesOrNo = "Нет";
            if (cbHaveDrinksCocaCompany.isChecked())
                YesOrNo = "Да";
            questXmlWrite(uuidQuest, "Наличие напитков компании Кока-Кола", YesOrNo, GPS, strCurrentDate, strCurrentTime, "");

            CheckBox cbAvailJuicesCocaCompany = (CheckBox) findViewById(R.id.cbAvailJuicesCocaCompany);
            YesOrNo = "Нет";
            if (cbAvailJuicesCocaCompany.isChecked())
                YesOrNo = "Да";
            questXmlWrite(uuidQuest, "Наличие соков компании Кока-Кола", YesOrNo, GPS, strCurrentDate, strCurrentTime, "");

            CheckBox cbHaveDrinksCompanyPepsi = (CheckBox) findViewById(R.id.cbHaveDrinksCompanyPepsi);
            YesOrNo = "Нет";
            if (cbHaveDrinksCompanyPepsi.isChecked())
                YesOrNo = "Да";
            questXmlWrite(uuidQuest, "Наличие напитков компании ПепсиКо", YesOrNo, GPS, strCurrentDate, strCurrentTime, "");

            CheckBox cbAvailabilityJuicesPepsiCo = (CheckBox) findViewById(R.id.cbAvailabilityJuicesPepsiCo);
            YesOrNo = "Нет";
            if (cbAvailabilityJuicesPepsiCo.isChecked())
                YesOrNo = "Да";
            questXmlWrite(uuidQuest, "Наличие соков компании ПепсиКо", YesOrNo, GPS, strCurrentDate, strCurrentTime, "");
//============================ Тут нули по умолчанию
            EditText etPresAndNumRefrigBrandTradeCoca = (EditText) findViewById(R.id.etPresAndNumRefrigBrandTradeCoca);
            if (!etPresAndNumRefrigBrandTradeCoca.getText().toString().equals(""))
                questXmlWrite(uuidQuest, "Наличие и количество холодильников брендированных марками компании Кока-Кола", etPresAndNumRefrigBrandTradeCoca.getText().toString(), GPS, strCurrentDate, strCurrentTime, "");
            else
                questXmlWrite(uuidQuest, "Наличие и количество холодильников брендированных марками компании Кока-Кола", "0", GPS, strCurrentDate, strCurrentTime, "");

            EditText etPresAndNumDoorsRefrigBrandTradeCoca = (EditText) findViewById(R.id.etPresAndNumDoorsRefrigBrandTradeCoca);
            if (!etPresAndNumDoorsRefrigBrandTradeCoca.getText().toString().equals(""))
                questXmlWrite(uuidQuest, "Наличие и количество дверей холодильников брендированных марками компании Кока-Кола", etPresAndNumDoorsRefrigBrandTradeCoca.getText().toString(), GPS, strCurrentDate, strCurrentTime, "");
            else
                questXmlWrite(uuidQuest, "Наличие и количество дверей холодильников брендированных марками компании Кока-Кола", "0", GPS, strCurrentDate, strCurrentTime, "");

            EditText etPresAndNumBrandRefrigPepsiCo = (EditText) findViewById(R.id.etPresAndNumBrandRefrigPepsiCo);
            if (!etPresAndNumBrandRefrigPepsiCo.getText().toString().equals(""))
                questXmlWrite(uuidQuest, "Наличие и количество брендированных холодильников ПепсиКо (другими марками ПепсиКо)", etPresAndNumBrandRefrigPepsiCo.getText().toString(), GPS, strCurrentDate, strCurrentTime, "");
            else
                questXmlWrite(uuidQuest, "Наличие и количество брендированных холодильников ПепсиКо (другими марками ПепсиКо)", "0", GPS, strCurrentDate, strCurrentTime, "");

            EditText etPresAndNumDoorsBrandRefrigPepsiCo = (EditText) findViewById(R.id.etPresAndNumDoorsBrandRefrigPepsiCo);
            if (!etPresAndNumDoorsBrandRefrigPepsiCo.getText().toString().equals(""))
                questXmlWrite(uuidQuest, "Наличие и количество дверей брендированного холодильника ПепсиКо (другими марками ПепсиКо)", etPresAndNumDoorsBrandRefrigPepsiCo.getText().toString(), GPS, strCurrentDate, strCurrentTime, "");
            else
                questXmlWrite(uuidQuest, "Наличие и количество дверей брендированного холодильника ПепсиКо (другими марками ПепсиКо)", "0", GPS, strCurrentDate, strCurrentTime, "");

            EditText etPresAndNumOtherRefrigAndBeverages = (EditText) findViewById(R.id.etPresAndNumOtherRefrigAndBeverages);
            String value = "Наличие и количество прочих холодильников б\\а напитков (небрендированный или брендированный другим производителем безалкогольных напитков)";
            questXmlWrite(uuidQuest, value, checkEmptyFild(etPresAndNumOtherRefrigAndBeverages.getText().toString()), GPS, strCurrentDate, strCurrentTime, "");


            EditText etPresAndQuantiBeerCoolers = (EditText) findViewById(R.id.etPresAndQuantiBeerCoolers);
            questXmlWrite(uuidQuest, "Наличие и количество пивных холодильников", checkEmptyFild(etPresAndQuantiBeerCoolers.getText().toString()), GPS, strCurrentDate, strCurrentTime, "");

//============================

            CheckBox cbDedicShelvesForDrinks = (CheckBox) findViewById(R.id.cbDedicShelvesForDrinks);
            YesOrNo = "Нет";
            if (cbDedicShelvesForDrinks.isChecked())
                YesOrNo = "Да";
            value = "Наличие в точке выделенной полки для напитков \\ соков (PBS)";
            questXmlWrite(uuidQuest, value, YesOrNo, GPS, strCurrentDate, strCurrentTime, "");

            CheckBox cbAddPointsSaleWarmEquipPepsiCo = (CheckBox) findViewById(R.id.cbAddPointsSaleWarmEquipPepsiCo);
            YesOrNo = "Нет";
            if (cbAddPointsSaleWarmEquipPepsiCo.isChecked())
                YesOrNo = "Да";
            value = "Наличие в точке дополнительных мест продаж `теплого` оборудования (палетты, полки и пр), брендированного Кока-Кола";
            questXmlWrite(uuidQuest, value, YesOrNo, GPS, strCurrentDate, strCurrentTime, "");

            CheckBox cbAddPointsSaleWarmEquipCoca = (CheckBox) findViewById(R.id.cbAddPointsSaleWarmEquipCoca);
            YesOrNo = "Нет";
            if (cbAddPointsSaleWarmEquipCoca.isChecked())
                YesOrNo = "Да";
            value = "Наличие в точке дополнительных мест продаж `теплого` оборудования (палетты, полки и пр), брендированного ПепсиКо";
            questXmlWrite(uuidQuest, value, YesOrNo, GPS, strCurrentDate, strCurrentTime, "");

            Spinner spinWhoBringsProdCoca = (Spinner) findViewById(R.id.spinWhoBringsProdCoca);
            if (spinWhoBringsProdCoca.getSelectedItemPosition() != 0) {
                String selected = spinWhoBringsProdCoca.getSelectedItem().toString();
                questXmlWrite(uuidQuest, "Кто привозит продукцию компании Кока-Кола", selected, GPS, strCurrentDate, strCurrentTime, "");
            } else {
                questXmlWrite(uuidQuest, "Кто привозит продукцию компании Кока-Кола", "null", GPS, strCurrentDate, strCurrentTime, "");
            }

//=================================== Тут нули по умолчанию
            EditText etNumbPOSCoolCoca = (EditText) findViewById(R.id.etNumbPOSCoolCoca);
            if (!etNumbPOSCoolCoca.getText().toString().equals(""))
                questXmlWrite(uuidQuest, "Количество кассовых кулеров компании Кока-Кола", etNumbPOSCoolCoca.getText().toString(), GPS, strCurrentDate, strCurrentTime, "");
            else
                questXmlWrite(uuidQuest, "Количество кассовых кулеров компании Кока-Кола", "0", GPS, strCurrentDate, strCurrentTime, "");

            EditText etNumbPOSCoolOther = (EditText) findViewById(R.id.etNumbPOSCoolOther);
            if (!etNumbPOSCoolOther.getText().toString().equals(""))
                questXmlWrite(uuidQuest, "Количество кассовых кулеров других производителей", etNumbPOSCoolOther.getText().toString(), GPS, strCurrentDate, strCurrentTime, "");
            else
                questXmlWrite(uuidQuest, "Количество кассовых кулеров других производителей", "0", GPS, strCurrentDate, strCurrentTime, "");
//===================================

            CheckBox cbVendingMachine = (CheckBox) findViewById(R.id.cbVendingMachine);
            YesOrNo = "Нет";
            if (cbVendingMachine.isChecked())
                YesOrNo = "Да";
            value = "Наличие вендингового аппарата";
            questXmlWrite(uuidQuest, value, YesOrNo, GPS, strCurrentDate, strCurrentTime, "");


            EditText etBrendVendingMachine = (EditText) findViewById(R.id.etBrendVendingMachine);
            questXmlWrite(uuidQuest, "Бренд вендингового аппарата", checkEmptyFild(etBrendVendingMachine.getText().toString()), GPS, strCurrentDate, strCurrentTime, "");

            EditText etStatementVendingMachine = (EditText) findViewById(R.id.etStatementVendingMachine);
            questXmlWrite(uuidQuest, "Оператор вендингового аппарата", checkEmptyFild(etStatementVendingMachine.getText().toString()), GPS, strCurrentDate, strCurrentTime, "");

            EditText etPhoneStateVendMachine = (EditText) findViewById(R.id.etPhoneStateVendMachine);
            questXmlWrite(uuidQuest, "Контактный телефон оператора вендингового аппарата", checkEmptyFild(etPhoneStateVendMachine.getText().toString()), GPS, strCurrentDate, strCurrentTime, "");

            CheckBox cbVendMachineCoca = (CheckBox) findViewById(R.id.cbVendMachineCoca);
            YesOrNo = "Нет";
            if (cbVendMachineCoca.isChecked())
                YesOrNo = "Да";
            value = "Наличие в вендинговом аппарате продукции компании Кока-Кола";
            questXmlWrite(uuidQuest, value, YesOrNo, GPS, strCurrentDate, strCurrentTime, "");

            CheckBox cbVendMachinePepsi = (CheckBox) findViewById(R.id.cbVendMachinePepsi);
            YesOrNo = "Нет";
            if (cbVendMachinePepsi.isChecked())
                YesOrNo = "Да";
            value = "Наличие в вендинговом аппарате продукции компании ПепсиКо";
            questXmlWrite(uuidQuest, value, YesOrNo, GPS, strCurrentDate, strCurrentTime, "");

            CheckBox cbVendMachineOther = (CheckBox) findViewById(R.id.cbVendMachineOther);
            YesOrNo = "Нет";
            if (cbVendMachineOther.isChecked())
                YesOrNo = "Да";
            value = "Наличие в вендинговом аппарате прочих безалкогольных напитков (кроме продукции Кока-Кола и ПепсиКо)";
            questXmlWrite(uuidQuest, value, YesOrNo, GPS, strCurrentDate, strCurrentTime, "");

            EditText etCommonComment = (EditText) findViewById(R.id.etCommonComment);
            questXmlWrite(uuidQuest, "Общие комментарии", checkEmptyFild(etCommonComment.getText().toString()), GPS, strCurrentDate, strCurrentTime, "");

            CheckBox cbNotCalc = (CheckBox) findViewById(R.id.cbNotCalc);
            YesOrNo = "Нет";
            if (cbNotCalc.isChecked())
                YesOrNo = "Да";
            value = "Анкету не обрабатывать";
            questXmlWrite(uuidQuest, value, YesOrNo, GPS, strCurrentDate, strCurrentTime, "");

            questXmlWrite(uuidQuest, "Имя файла фотографии", mmPhotoFileName, GPS, strCurrentDate, strCurrentTime, "");
            questXmlWrite(uuidQuest, "Координаты", mmLongitude + "," + mmLatitude, GPS, strCurrentDate, strCurrentTime, "");
/*
       x etLegalEntity
       x etINN
       x etNameOnSign
       x spinADistrict
       x spinCity
       x etClientAddressStreet
       x etClientAddressHouse
       x etClientAddressComment
       x etGpsLong
       x etGpsLati
       x spinTypeChannel
       x spinOccasionCons
       x spinTypeService
       x etNumberBanksRetailOutlets
       x spinMarketArea
       x spinSpecSalePoint
       x etNameConnectFace
       x etPhoneConnectFace
       x spinAverageVisitorsPerDay
       x cbPresenceCarbonDrinks
       x cbPresenceColdTea
       x cbHaveBottledWater
       x cbAvailabilityPower
       x cbAvailabilityKvass
       x cbAvailabilityJuices
       x cbPresenceBeer
       x cbAlcoholSpiritsWine
       x cbHaveDrinksCocaCompany
       x cbAvailJuicesCocaCompany
       x cbHaveDrinksCompanyPepsi
       x cbAvailabilityJuicesPepsiCo
       x etPresAndNumRefrigBrandTradeCoca
       x etPresAndNumDoorsRefrigBrandTradeCoca
       x etPresAndNumBrandRefrigPepsiCo
       x etPresAndNumDoorsBrandRefrigPepsiCo
       x etPresAndNumOtherRefrigAndBeverages
       x etPresAndQuantiBeerCoolers
       x cbDedicShelvesForDrinks
       x cbAddPointsSaleWarmEquipCoca
       x cbAddPointsSaleWarmEquipPepsiCo
       x spinWhoBringsProdCoca
       x etSAPCode
       x etNumbPOSCoolCoca
       x etNumbPOSCoolOther
        cbVendingMachine
        etBrendVendingMachine
        etStatementVendingMachine
        etPhoneStateVendMachine
        cbVendMachineCoca
        cbVendMachinePepsi
        cbVendMachineOther
*/


            String fullQuestCSV = "";
            for (int i = 0; i < csvQuestList.size(); i++) {
                fullQuestCSV += csvQuestList.get(i).toString() + "\n";
            }

            String folderToSave = Environment.getExternalStorageDirectory().toString(); // папка куда сохранять, в данном случае - корень SD-карты

            Time time = new Time();
            time.setToNow();

            folderToSave = folderToSave + File.separator + Integer.toString(time.year) + "_" + Integer.toString(time.month + 1) + "_" +
                    Integer.toString(time.monthDay) + "_" + Integer.toString(time.hour) + "_" +
                    Integer.toString(time.minute) + "_" + Integer.toString(time.second) + "_" +
                    "Questionary.csv";

            File fileNameSave = new File(folderToSave);

            fileNameSave.getParentFile().mkdirs();
            try {
                FileWriter f = new FileWriter(fileNameSave);
                f.write(fullQuestCSV); //csvQuestList.toString()
                f.flush();
                f.close();
            } catch (Exception e) {
                etLegalEntity.setText(e.getMessage());
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
                bitmap.compress(Bitmap.CompressFormat.JPEG, 40, stream); //PNG
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
                    // .png
                    ZipEntry ze2 = new ZipEntry(FileNameInZip + ".jpg");//Имя файла - имя файла в архиве photo
                    zout.putNextEntry(ze2);
                    zout.write(image);
                    zout.closeEntry();
                }
            }
            zout.close();

            try {
                if (!onUnzipZip(zipFileName)) {
                    showAlert("Не удалось создать пакет с данными.\n Попробуйте сделать фото вывески в более низком разрешении.");
                    return;
                }
            } catch (Exception e) {
                showAlert("Не удалось создать пакет с данными.\n Попробуйте сделать фото вывески в более низком разрешении.");
                return;
            }

            String sEmail = "rep_full@rabota2008.ru"; //test1@rabota2008.ru
            // TODO мыло поменять
//            String sEmail = "nedosekin@link-service.ru";

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
                    new String[]{sEmail});
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
            MainQuest.this.startActivity(Intent.createChooser(emailIntent,
                    "Отправка письма..."));

        } catch (Exception e) {
            showAlert("Непредвиденная ошибка, при формировании письма. Попробуйте отправить снова.");
            return;
        }
    }

    public void showAlert(String textAlert) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ошибка");
        builder.setMessage(textAlert);
        builder.setCancelable(true);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // Кнопка ОК
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); // Отпускает диалоговое окно
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public boolean onUnzipZip(String PathZip) throws IOException {
        InputStream is = new FileInputStream(new File(PathZip)); //
        ZipInputStream zis = new ZipInputStream(new BufferedInputStream(is));
        ZipEntry ze;
        while ((ze = zis.getNextEntry()) != null) {
            byte[] buffer = new byte[64];
            int count;
            while ((count = zis.read(buffer)) > -1) {
                break;
            }
            if (count == -1) {
                return false;
            }
            zis.closeEntry();
        }
        zis.close();
        is.close();
        return true;
    }

    public void onClickHaveDrinksCoca(View view) {
        CheckBox HaveDrinksCoca = (CheckBox)view;
        CheckBox cbAvailJuicesCocaCompany = (CheckBox)findViewById(R.id.cbAvailJuicesCocaCompany);
        if (HaveDrinksCoca.isChecked()) {
            mTvHaveDrinksCocaChecker.setVisibility(View.VISIBLE);
        } else {
            if (!cbAvailJuicesCocaCompany.isChecked())
                mTvHaveDrinksCocaChecker.setVisibility(View.GONE);
        }
    }

    public void onClickJuicesCocaCompany(View view) {
        CheckBox JuicesCocaCompany = (CheckBox)view;
        CheckBox cbHaveDrinksCocaCompany = (CheckBox)findViewById(R.id.cbHaveDrinksCocaCompany);

        if (JuicesCocaCompany.isChecked()) {
            mTvHaveDrinksCocaChecker.setVisibility(View.VISIBLE);
        } else {
            if (!cbHaveDrinksCocaCompany.isChecked())
                mTvHaveDrinksCocaChecker.setVisibility(View.GONE);
        }
    }
}

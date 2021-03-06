package com.bobgranata.linkservice.Activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;

import com.bobgranata.linkservice.Adapters.DocCirculListAdapter;
import com.bobgranata.linkservice.Database.DatabaseHandler;
import com.bobgranata.linkservice.Models.DocCirculModel;
import com.bobgranata.linkservice.R;
import com.bobgranata.linkservice.Tasks.RequestDataTask;
import com.bobgranata.linkservice.Tasks.RequestMode;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

public class DocCirculActivity extends AppCompatActivity {

    private DocCirculListAdapter adapterDC;
    private SharedPreferences mSharedSettingsPeriod;

    ProgressBar mmDocCirculProgressBar;
    List<DocCirculModel> mmListDocCircul;
    ExpandableListView mmElvDocCirculList;

    TextView mmTvNoOneDocCircul;
    TextView mmTvCommonCreateDate;
    TextView mmTvDocCirculTitle;

    String mmInfComId;
    int mmChosePeriodIndex;

    final String ACCAUNTING = "Accaunting";
    final String REQUIREMENTS = "Requirements"; // Требование
    final String REQUEST = "Request"; // Запрос на информационное обслуживание
    final String NDFL2 = "Ndfl2"; //2-НДФЛ
    final String UNFORM = "Unform"; // Обращение налогоплатильщика // Письма абонента
    final String REPRESENT = "Representation"; //Представление
    final String BANK = "BankGuarantee"; //Уведомление о факте выдачи гарантии
    final String PETITION = "Petition"; //Заявление
    final String ALL_TYPE = "all";

    public static final String APP_PREFERENCES = "lssettings";
    final String PERIOD = "period";
    final int BY_YEAR = 0;
    final int BY_HALF_YEAR = 1;
    final int BY_MONTH = 2;
    final int BY_WEEK = 3;
    final int BY_DAY = 4;

    final String[] mTimePeriodName ={"Год", "6 месяцев", "Месяц", "Неделя", "День"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_circul);

        mSharedSettingsPeriod = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        mmDocCirculProgressBar = (ProgressBar)findViewById(R.id.docCirculProgressBar);
        mmElvDocCirculList = (ExpandableListView)findViewById(R.id.elvDocCirculList);
        mmTvCommonCreateDate = (TextView) findViewById(R.id.tvCommonCreateDate);
        mmTvDocCirculTitle = (TextView) findViewById(R.id.tvDocCirculTitle);
        mmTvNoOneDocCircul = (TextView) findViewById(R.id.tvNoOneDocCircul);

        if (android.os.Build.VERSION.SDK_INT <
                android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            mmElvDocCirculList.setIndicatorBounds(650, 700);
        } else {
            mmElvDocCirculList.setIndicatorBoundsRelative(650, 700);
        }

        mmInfComId = getIntent().getExtras().getString(InfComActivity.INFCOM_ID);

        if (mmInfComId.equals("")) {

        }

        mmDocCirculProgressBar.setVisibility(View.VISIBLE);

        RequestDataTask roleInfComTask = new RequestDataTask(this);
        //http://92.43.187.142:4000/GetDocs?access_key=632CAC3A&date_last_update=0

        DatabaseHandler db = new DatabaseHandler(this);
        String sLastUpdate = db.getInfCom(mmInfComId).getDateLastUpdate();

//        roleInfComTask.execute(sAccesKey, sLastUpdate);
        roleInfComTask.execute(RequestMode.DOC_CIRCULS, mmInfComId, sLastUpdate);

    }

    public void updateDocCirculList(String sError) {
        mmDocCirculProgressBar.setVisibility(View.INVISIBLE);

//        DatabaseHandler db = new DatabaseHandler(this);
//        mmListDocCircul = db.getDocCirculs(mmInfComId);

        //---------------------------------------------------------------------
//        // Запоминаем данные
//        SharedPreferences.Editor editor = mSharedSettingsPeriod.edit();
//        editor.putInt(PERIOD, 0);
//        editor.apply();
//        //---------------------------------------------------------------------
        int iPeriod = 0;
        if (mSharedSettingsPeriod.contains(PERIOD)) {
            // Получаем строку из настроек
            iPeriod = mSharedSettingsPeriod.getInt (PERIOD, 0);
        }
        updateListByTime(iPeriod);

        Toast.makeText(
                getApplicationContext(),
                "Выбранный период: "
                        + mTimePeriodName[iPeriod],
                Toast.LENGTH_SHORT).show();
        //---------------------------------------------------------------------

//        if (mmListDocCircul.size() > 0) {
        if (mmElvDocCirculList.getCount() > 0) {

            // используем кастомный адаптер данных DocCirculListAdapter
//            adapterDC = new DocCirculListAdapter(this, mmListDocCircul);
//
//            mmElvDocCirculList.setAdapter(adapterDC);
            mmElvDocCirculList.setTextFilterEnabled(true);

            mmTvCommonCreateDate.setVisibility(View.VISIBLE);
            mmTvDocCirculTitle.setVisibility(View.VISIBLE);

        } else {
            mmTvNoOneDocCircul.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_doc_circul, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.searchByName));
        if (null != searchView) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setIconifiedByDefault(true);

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    adapterDC.getFilter().filter(newText);
                    return false;
                }
            });
        }
        return true;
    }

    public void updateListByTypeDoc(String sType) {

        List<DocCirculModel> ListByTypeDocCircul = new ArrayList<DocCirculModel>(mmListDocCircul);
        if (mmListDocCircul.size() > 0) {

//            final String REQUIREMENTS = "Requirements"; // Требование
//            final String REQUEST = "Request"; // Запрос на информационное обслуживание
//            final String NDFL2 = "Ndfl2"; //2-НДФЛ
//            final String UNFORM = "Unform"; // Обращение налогоплатильщика // Письма абонента
//            final String REPRESENT = "Representation"; //Представление
//            final String BANK = "BankGuarantee"; //Уведомление о факте выдачи гарантии
//            final String PETITION = "Petition"; //Заявление
            if (sType.equals(REQUIREMENTS)) {
                for (DocCirculModel docCircul: mmListDocCircul) {
                    if (!docCircul.getName().contains("Требование")) {
                        ListByTypeDocCircul.remove(docCircul);
                    }
                }
            } else if (sType.equals(PETITION)) {
                for (DocCirculModel docCircul: mmListDocCircul) {
                    if (!docCircul.getName().contains("Заявление")) {
                        ListByTypeDocCircul.remove(docCircul);
                    }
                }
            } else if (sType.equals(BANK)) {
                for (DocCirculModel docCircul: mmListDocCircul) {
                    if (!docCircul.getName().contains("гарантии")) {
                        ListByTypeDocCircul.remove(docCircul);
                    }
                }
            } else if (sType.equals(REQUEST)) {
                for (DocCirculModel docCircul: mmListDocCircul) {
                    if (!docCircul.getName().contains("Запрос")) {
                        ListByTypeDocCircul.remove(docCircul);
                    }
                }
            } else if (sType.equals(NDFL2)) {
                for (DocCirculModel docCircul: mmListDocCircul) {
                    if (!docCircul.getName().contains("2-НДФЛ")) {
                        ListByTypeDocCircul.remove(docCircul);
                    }
                }
            } else if (sType.equals(UNFORM)) {
                for (DocCirculModel docCircul: mmListDocCircul) {
                    if (!docCircul.getName().contains("Обращение") && !docCircul.getName().contains("Письма")) {
                        ListByTypeDocCircul.remove(docCircul);
                    }
                }
            } else if (sType.equals(REPRESENT)) {
                for (DocCirculModel docCircul: mmListDocCircul) {
                    if (!docCircul.getName().contains("Представление")) {
                    ListByTypeDocCircul.remove(docCircul);
                    }
                }
            } else if (sType.equals(ACCAUNTING)) {
                for (DocCirculModel docCircul: mmListDocCircul) {
                    if (docCircul.getName().contains("Требование") || docCircul.getName().contains("Заявление") ||
                            docCircul.getName().contains("гарантии") || docCircul.getName().contains("Запрос") ||
                            docCircul.getName().contains("2-НДФЛ") || docCircul.getName().contains("Обращение") ||
                            docCircul.getName().contains("Письма") || docCircul.getName().contains("Представление")) {
                        ListByTypeDocCircul.remove(docCircul);
                    }
                }
            }
        }

        adapterDC = new DocCirculListAdapter(this, ListByTypeDocCircul);
        mmElvDocCirculList.setAdapter(adapterDC);
    }

    public void updateListByTime(int iTime) {

        DatabaseHandler db = new DatabaseHandler(this);

//        mmListDocCircul = db.getDocCirculs(mmInfComId);

//        List<DocCirculModel> ListByTypeDocCircul = new ArrayList<DocCirculModel>(mmListDocCircul);
        List<DocCirculModel> ListByTypeDocCircul = new ArrayList<DocCirculModel>();

//        if (mmListDocCircul.size() > 0) {
        if (true) {

            Calendar calendar = Calendar.getInstance();
            Date nowDate = new Date();
            calendar.setTime(nowDate);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String sFilterDate = "";

            switch (iTime) {
                case BY_YEAR : {
//                    WHERE date_entering<= data
                    calendar.add(Calendar.YEAR, -1);
                    Date dFormat = calendar.getTime();
                    sFilterDate = dateFormat.format(dFormat);

                } break;
                case BY_HALF_YEAR : {
                    calendar.add(Calendar.MONTH, -6);
                    Date dFormat = calendar.getTime();
                    sFilterDate = dateFormat.format(dFormat);

                } break;
                case BY_MONTH : {
                    calendar.add(Calendar.MONTH, -1);
                    Date dFormat = calendar.getTime();
                    sFilterDate = dateFormat.format(dFormat);

                } break;
                case BY_WEEK : {
                    calendar.add(Calendar.HOUR, -24*7);
                    Date dFormat = calendar.getTime();
                    sFilterDate = dateFormat.format(dFormat);

                } break;
                case BY_DAY : {
                    calendar.add(Calendar.HOUR, -24);
                    Date dFormat = calendar.getTime();
                    sFilterDate = dateFormat.format(dFormat);

                } break;
            }

            ListByTypeDocCircul = db.getDocCirculsByDate(sFilterDate, mmInfComId);
        }
        adapterDC = new DocCirculListAdapter(this, ListByTypeDocCircul);
        mmElvDocCirculList.setAdapter(adapterDC);
    }

    public void SelectTimePeriod() {

        int iPeriod = 0;
        if (mSharedSettingsPeriod.contains(PERIOD)) {
            // Получаем строку из настроек
            iPeriod = mSharedSettingsPeriod.getInt (PERIOD, 0);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(DocCirculActivity.this);
        builder.setTitle("Выберите за какой период показывать документы:")
                .setCancelable(true)

                // добавляем переключатели
                .setSingleChoiceItems(mTimePeriodName, iPeriod,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int item) {
                                mmChosePeriodIndex = item;
                            }
                        })

                // Добавляем кнопки
                .setPositiveButton("Готово",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                updateListByTime(mmChosePeriodIndex);

                                // Запоминаем данные
                                SharedPreferences.Editor editor = mSharedSettingsPeriod.edit();
                                editor.putInt(PERIOD, mmChosePeriodIndex);
                                editor.apply();

                                Toast.makeText(
                                        getApplicationContext(),
                                        "Выбранный период: "
                                                + mTimePeriodName[mmChosePeriodIndex],
                                        Toast.LENGTH_SHORT).show();
                            }
                        })

                .setNegativeButton("Отмена",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dialog.cancel();

                            }
                        });

        builder.create().show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            //noinspection SimplifiableIfStatement
//            case (R.id.search_id) :
//                Toast.makeText(getApplicationContext(), "Search option selected", Toast.LENGTH_LONG).show();
//                return true;
            case R.id.filter_id : {
                SelectTimePeriod();
                return true;
            }

            case R.id.menuTypeFilterPetition : {
                Toast.makeText(getApplicationContext(), "Заявления", Toast.LENGTH_SHORT).show();
                updateListByTypeDoc(PETITION);
                return true;
            }

            case R.id.menuTypeFilterByBankGuarantee : {
                Toast.makeText(getApplicationContext(), "Банковские гарантии", Toast.LENGTH_SHORT).show();
                updateListByTypeDoc(BANK);
                return true;
            }

            case R.id.menuTypeFilterByAccounting : {
                Toast.makeText(getApplicationContext(), "Отчётность", Toast.LENGTH_SHORT).show();
                updateListByTypeDoc(ACCAUNTING);
                return true;
            }
            case R.id.menuTypeFilterByRequirements : {
                Toast.makeText(getApplicationContext(), "Требования", Toast.LENGTH_SHORT).show();
                updateListByTypeDoc(REQUIREMENTS);
                return true;
            }
            case R.id.menuTypeFilterByRequest : {
                Toast.makeText(getApplicationContext(), "Запрос на ИОН", Toast.LENGTH_SHORT).show();
                updateListByTypeDoc(REQUEST);
                return true;
            }
            case R.id.menuTypeFilterByNDFL2 : {
                Toast.makeText(getApplicationContext(), "2-НДФЛ", Toast.LENGTH_SHORT).show();
                updateListByTypeDoc(NDFL2);
                return true;
            }
            case R.id.menuTypeFilterByUnform : {
                Toast.makeText(getApplicationContext(), "Письма абонента", Toast.LENGTH_SHORT).show();
                updateListByTypeDoc(UNFORM);
                return true;
            }
            case R.id.menuTypeFilterByRepresentation : {
                Toast.makeText(getApplicationContext(), "Представления", Toast.LENGTH_SHORT).show();
                updateListByTypeDoc(REPRESENT);
                return true;
            }
            case R.id.menuFilterByTypeAll : {
                Toast.makeText(getApplicationContext(), "Все документообороты", Toast.LENGTH_SHORT).show();
                updateListByTypeDoc(ALL_TYPE);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

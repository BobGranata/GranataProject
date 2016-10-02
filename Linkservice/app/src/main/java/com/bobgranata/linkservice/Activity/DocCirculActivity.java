package com.bobgranata.linkservice.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.bobgranata.linkservice.Adapters.DocCirculListAdapter;
import com.bobgranata.linkservice.Adapters.ListRolesAdapter;
import com.bobgranata.linkservice.Database.DatabaseHandler;
import com.bobgranata.linkservice.Models.DocCirculModel;
import com.bobgranata.linkservice.Models.DocumentModel;
import com.bobgranata.linkservice.R;
import com.bobgranata.linkservice.Tasks.RequestDataTask;
import com.bobgranata.linkservice.Tasks.RequestMode;

import java.util.ArrayList;
import java.util.List;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

public class DocCirculActivity extends AppCompatActivity {
    ProgressBar mmDocCirculProgressBar;
    List<DocCirculModel> mmListDocCircul;
    ExpandableListView mmElvDocCirculList;

    String mmInfComId;

    final String ACCAUNTING = "acaunting";
    final String REQUIREMENTS = "requirements"; // Требование
    final String REQUEST = "request"; // Запрос на информационное обслуживание
    final String NDFL2 = "ndfl2";
    final String UNFORM = "unform"; // Обращение налогоплатильщика // Письма абонента
    final String REPRESENT = "representation";
    final String ALL_TYPE = "all";

    final int BY_YEAR = 0;
    final int BY_HALF_YEAR = 1;
    final int BY_MONTH = 2;
    final int BY_WEEK = 3;
    final int BY_DAY = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_circul);

        mmDocCirculProgressBar = (ProgressBar)findViewById(R.id.docCirculProgressBar);
        mmElvDocCirculList = (ExpandableListView)findViewById(R.id.elvDocCirculList);

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

//==========================================================================
        RequestDataTask roleInfComTask = new RequestDataTask(this);
        //http://92.43.187.142:4000/GetDocs?access_key=632CAC3A&date_last_update=0

        // TODO очевидно значение нужно откуда то брать. Из базы например.
        DatabaseHandler db = new DatabaseHandler(this);
        String sLastUpdate = db.getInfCom(mmInfComId).getDateLastUpdate();

//        roleInfComTask.execute(sAccesKey, sLastUpdate);
        roleInfComTask.execute(RequestMode.DOC_CIRCULS, mmInfComId, sLastUpdate);
//==========================================================================

//        DatabaseHandler db = new DatabaseHandler(this);
//        mmListDocCircul = db.getDocCirculs(sInfComId);
//
//        if (mmListDocCircul.size() > 0) {
//
//            // используем кастомный адаптер данных
//            DocCirculListAdapter adapter = new DocCirculListAdapter(this, mmListDocCircul);
//
//            mmElvDocCirculList.setAdapter(adapter);
//        }
//
//        mmDocCirculProgressBar.setVisibility(View.INVISIBLE);

//        mmListInfCom = db.getAllInfComs();

    }

    public void updateDocCirculList(String sError) {
        mmDocCirculProgressBar.setVisibility(View.INVISIBLE);

        DatabaseHandler db = new DatabaseHandler(this);

        mmListDocCircul = db.getDocCirculs(mmInfComId);

        if (mmListDocCircul.size() > 0) {

            // используем кастомный адаптер данных
            DocCirculListAdapter adapter = new DocCirculListAdapter(this, mmListDocCircul);

            mmElvDocCirculList.setAdapter(adapter);
        }
    }

    public void updateListByTypeDoc(String sType) {

        List<DocCirculModel> ListByTypeDocCircul = new ArrayList<DocCirculModel>(mmListDocCircul);
        if (mmListDocCircul.size() > 0) {
//            ACCAUNTING
            if (sType.equals(REQUIREMENTS)) {
                for (DocCirculModel docCircul: mmListDocCircul) {
                    if (docCircul.getName().indexOf("Требование") == -1) {
                        ListByTypeDocCircul.remove(docCircul);
                    }
                }
            } else if (sType.equals(REQUEST)) {
                for (DocCirculModel docCircul: mmListDocCircul) {
                    if (docCircul.getName().indexOf("Запрос") == -1) {
                        ListByTypeDocCircul.remove(docCircul);
                    }
                }
            } else if (sType.equals(NDFL2)) {
                for (DocCirculModel docCircul: mmListDocCircul) {
                    if (docCircul.getName().indexOf("ЗАГЛУШКА") == -1) {
                        ListByTypeDocCircul.remove(docCircul);
                    }
                }
            } else if (sType.equals(UNFORM)) {
                for (DocCirculModel docCircul: mmListDocCircul) {
                    if (docCircul.getName().indexOf("ЗАГЛУШКА") == -1) {
                        ListByTypeDocCircul.remove(docCircul);
                    }
                }
            } else if (sType.equals(REPRESENT)) {
                for (DocCirculModel docCircul: mmListDocCircul) {
                    if (docCircul.getName().indexOf("ЗАГЛУШКА") == -1) {
                    ListByTypeDocCircul.remove(docCircul);
                    }
                }
            }
        }

        // используем кастомный адаптер данных
        DocCirculListAdapter adapter = new DocCirculListAdapter(this, ListByTypeDocCircul);

        mmElvDocCirculList.setAdapter(adapter);
    }

    public void updateListByTime(int iTime) {

        DatabaseHandler db = new DatabaseHandler(this);

        mmListDocCircul = db.getDocCirculs(mmInfComId);

        List<DocCirculModel> ListByTypeDocCircul = new ArrayList<DocCirculModel>(mmListDocCircul);
        if (mmListDocCircul.size() > 0) {
            switch (iTime) {
                case BY_YEAR : {
//                    WHERE date_entering<= data
                    ListByTypeDocCircul = db.getDocCirculsByDate("01.09.2016 11:58:44");
                }
                case BY_HALF_YEAR : {

                }
                case BY_MONTH : {

                }
                case BY_WEEK : {

                }
                case BY_DAY : {

                }
            }
        }

        // используем кастомный адаптер данных
        DocCirculListAdapter adapter = new DocCirculListAdapter(this, ListByTypeDocCircul);

        mmElvDocCirculList.setAdapter(adapter);
    }

    public void SelectTimePeriod() {
        final String[] mTimePeriodName ={"Год", "6 месяцев", "Месяц", "Неделя", "День"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Выберите за какой период показывать документы:"); // заголовок для диалога

        builder.setItems(mTimePeriodName, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                updateListByTime(item);

                Toast.makeText(getApplicationContext(),
                        "Выбранный период: " + mTimePeriodName[item],
                        Toast.LENGTH_SHORT).show();
            }
        });
        builder.setCancelable(false);

        AlertDialog alert = builder.create();
        alert.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_doc_circul, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            //noinspection SimplifiableIfStatement
//            case (R.id.search_id) :
//                Toast.makeText(getApplicationContext(), "Search option selected", Toast.LENGTH_LONG).show();
//                return true;
            case R.id.filter_id : {
                Toast.makeText(getApplicationContext(), "Filter option selected", Toast.LENGTH_SHORT).show();
                SelectTimePeriod();
                return true;
            }
            case R.id.menuTypeFilterByAccounting : {
                Toast.makeText(getApplicationContext(), "Filter option selected", Toast.LENGTH_SHORT).show();
                updateListByTypeDoc(ACCAUNTING);
                return true;
            }
            case R.id.menuTypeFilterByRequirements : {
                Toast.makeText(getApplicationContext(), "Filter option selected", Toast.LENGTH_SHORT).show();
                updateListByTypeDoc(REQUIREMENTS);
                return true;
            }
            case R.id.menuTypeFilterByRequest : {
                Toast.makeText(getApplicationContext(), "Filter option selected", Toast.LENGTH_SHORT).show();
                updateListByTypeDoc(REQUEST);
                return true;
            }
            case R.id.menuTypeFilterByNDFL2 : {
                Toast.makeText(getApplicationContext(), "Filter option selected", Toast.LENGTH_SHORT).show();
                updateListByTypeDoc(NDFL2);
                return true;
            }
            case R.id.menuTypeFilterByUnform : {
                Toast.makeText(getApplicationContext(), "Filter option selected", Toast.LENGTH_SHORT).show();
                updateListByTypeDoc(UNFORM);
                return true;
            }
            case R.id.menuTypeFilterByRepresentation : {
                Toast.makeText(getApplicationContext(), "Filter option selected", Toast.LENGTH_SHORT).show();
                updateListByTypeDoc(REPRESENT);
                return true;
            }
            case R.id.menuFilterByTypeAll : {
                Toast.makeText(getApplicationContext(), "Filter option selected", Toast.LENGTH_SHORT).show();
                updateListByTypeDoc(ALL_TYPE);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

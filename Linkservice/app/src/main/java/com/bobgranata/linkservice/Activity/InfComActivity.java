package com.bobgranata.linkservice.Activity;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.bobgranata.linkservice.Database.DatabaseHandler;
import com.bobgranata.linkservice.Adapters.ListInfComAdapter;
import com.bobgranata.linkservice.Models.DocCirculModel;
import com.bobgranata.linkservice.Models.DocumentModel;
import com.bobgranata.linkservice.Models.InfComModel;
import com.bobgranata.linkservice.Models.InfoModel;
import com.bobgranata.linkservice.Models.RolesModel;
import com.bobgranata.linkservice.R;
import com.bobgranata.linkservice.Tasks.RequestDataTask;
import com.bobgranata.linkservice.Tasks.RequestMode;
import com.bobgranata.linkservice.Tasks.RoleInfComTask;

import java.util.List;

public class InfComActivity extends AppCompatActivity {
    ProgressBar mmInfComProgressBar;
    List<InfComModel> mmListInfCom;
    ListView mmLvInfComList;
    String mmIdRole;

    private ListInfComAdapter mmInfComAdapter;

    public final static String INFCOM_ID = "INFCOM_ID";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inf_com);

        mmInfComProgressBar = (ProgressBar)findViewById(R.id.infComProgressBar);

        String sAccesKey = getIntent().getExtras().getString(LoginActivity.ACESS_KEY);
        mmIdRole = getIntent().getExtras().getString(LoginActivity.ID_ROLE);

        mmInfComProgressBar.setVisibility(View.VISIBLE);

        RequestDataTask roleInfComTask = new RequestDataTask(this);
        //http://92.43.187.142:4000/GetDocs?access_key=632CAC3A&date_last_update=0

        DatabaseHandler db = new DatabaseHandler(this);
        String sLastUpdate = db.getRole(mmIdRole).getDateLastUpdate();

        roleInfComTask.execute(RequestMode.INF_COM, sAccesKey, sLastUpdate);

        // получаем экземпляр элемента ListView
        mmLvInfComList = (ListView)findViewById(R.id.lvInfComList);
        mmLvInfComList.setTextFilterEnabled(true);

        mmLvInfComList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
//                // При клике на эелементе списка запускаем активность
//                // описания деталей новостей.
                Intent intent = new Intent(InfComActivity.this,
                        DocCirculActivity.class);
                String sInfComId = mmInfComAdapter.getItem(position).getID();
                intent.putExtra(INFCOM_ID, sInfComId);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_inf_com, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.searchInfCom));
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
                    if (mmInfComAdapter != null) {
                        mmInfComAdapter.getFilter().filter(newText);
                    }
                    return false;
                }
            });
        }
        return true;
    }

    public void updateInfComList(InfoModel listInfo) {
        mmInfComProgressBar.setVisibility(View.INVISIBLE);

        DatabaseHandler db = new DatabaseHandler(this);

        if (listInfo != null) {
            String sDateLastUpdate = listInfo.getDateLastUpdate();
            RolesModel sUpdateRole = db.getRole(mmIdRole);
            sUpdateRole.setDateLastUpdate(sDateLastUpdate);
            db.updateRole(sUpdateRole);
        }

        // TODO проверять есть ли хоть одно направление в базе
        mmListInfCom = db.getInfComs(mmIdRole);
        // используем кастомный адаптер данных
        mmInfComAdapter = new ListInfComAdapter(this, mmListInfCom);

        mmLvInfComList.setAdapter(mmInfComAdapter);
        mmLvInfComList.setTextFilterEnabled(true);
    }

    public void updateInfComList(String sError) {
        mmInfComProgressBar.setVisibility(View.INVISIBLE);

        DatabaseHandler db = new DatabaseHandler(this);

        // TODO проверять есть ли хоть одно направление в базе
        mmListInfCom = db.getInfComs(mmIdRole);
        // используем кастомный адаптер данных
        mmInfComAdapter = new ListInfComAdapter(this, mmListInfCom);

        mmLvInfComList.setAdapter(mmInfComAdapter);
        mmLvInfComList.setTextFilterEnabled(true);
    }
}

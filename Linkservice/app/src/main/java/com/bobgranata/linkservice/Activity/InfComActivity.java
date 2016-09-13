package com.bobgranata.linkservice.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

public class InfComActivity extends Activity {
    ProgressBar mmInfComProgressBar;
    List<InfComModel> mmListInfCom;
    ListView mmLvInfComList;
    String mmIdRole;

    public final static String INFCOM_ID = "INFCOM_ID";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inf_com);

        mmInfComProgressBar = (ProgressBar)findViewById(R.id.infComProgressBar);

        String sAccesKey = getIntent().getExtras().getString(LoginActivity.ACESS_KEY);
        mmIdRole = getIntent().getExtras().getString(LoginActivity.ID_ROLE);

        if (sAccesKey.equals("") || mmIdRole.equals("")) {

        }

        mmInfComProgressBar.setVisibility(View.VISIBLE);
//        RoleInfComTask roleInfComTask = new RoleInfComTask(this);
        RequestDataTask roleInfComTask = new RequestDataTask(this);
        //http://92.43.187.142:4000/GetDocs?access_key=632CAC3A&date_last_update=0

        // TODO очевидно значение нужно откуда то брать. Из базы например.
        DatabaseHandler db = new DatabaseHandler(this);
        String sLastUpdate = db.getRole(mmIdRole).getDateLastUpdate();

//        roleInfComTask.execute(sAccesKey, sLastUpdate);
        roleInfComTask.execute(RequestMode.INF_COM, sAccesKey, sLastUpdate);

        //https://geocode-maps.yandex.ru/1.x/?geocode=55.157723,61.381479&results=1&format=xml

        // получаем экземпляр элемента ListView
        mmLvInfComList = (ListView)findViewById(R.id.lvInfComList);

        mmLvInfComList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
//                // При клике на эелементе списка запускаем активность
//                // описания деталей новостей.
                Intent intent = new Intent(InfComActivity.this,
                        DocCirculActivity.class);
                String sInfComId = mmListInfCom.get(position).getID();
                intent.putExtra(INFCOM_ID, sInfComId);
                startActivity(intent);
            }
        });
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

//        if (listInfo != null) {
//            for (InfComModel infcom : listInfo.getListInfCom()) {
//                db.addInfCom(infcom);
//            }
//
//            List<DocCirculModel> listDocCircul = listInfo.getListDocCircul();
//            for (DocCirculModel doccircul : listDocCircul) {
//                db.addDocCircul(doccircul);
//            }
//
//            for (DocumentModel document : listInfo.getListDocument()) {
//                db.addDocument(document);
//            }
//        }

        // TODO проверять есть ли хоть одно направление в базе
        mmListInfCom = db.getInfComs(mmIdRole);
        // используем кастомный адаптер данных
        ListInfComAdapter adapter = new ListInfComAdapter(this, mmListInfCom);

        mmLvInfComList.setAdapter(adapter);
    }

    public void updateInfComList(String sError) {
        mmInfComProgressBar.setVisibility(View.INVISIBLE);

        DatabaseHandler db = new DatabaseHandler(this);

        // TODO проверять есть ли хоть одно направление в базе
        mmListInfCom = db.getInfComs(mmIdRole);
        // используем кастомный адаптер данных
        ListInfComAdapter adapter = new ListInfComAdapter(this, mmListInfCom);

        mmLvInfComList.setAdapter(adapter);
    }
}

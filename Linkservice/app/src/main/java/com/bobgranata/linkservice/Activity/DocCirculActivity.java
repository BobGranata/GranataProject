package com.bobgranata.linkservice.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
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

import java.util.List;

public class DocCirculActivity extends Activity {
    ProgressBar mmDocCirculProgressBar;
    List<DocCirculModel> mmListDocCircul;
    ExpandableListView mmElvDocCirculList;

    String mmInfComId;

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
}

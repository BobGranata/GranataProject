package com.example.bobgranata.testtaskforandroid;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rvListOfAds = (RecyclerView)findViewById(R.id.rvListOfAds);
        rvListOfAds.setHasFixedSize(true);

        mContext = getApplicationContext();
        LinearLayoutManager llm_lay = new LinearLayoutManager(mContext);
        rvListOfAds.setLayoutManager(llm_lay);

        initializeData();
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(mListAdverts);
        rvListOfAds.setAdapter(adapter);
    }

    private List<Adverts> mListAdverts;
    private void initializeData() {
        mListAdverts = new ArrayList<>();
        mListAdverts.add(new Adverts("Скупка ноутбуков с выездом! 1", "26.07.2017", R.drawable.cat_commandments));
        mListAdverts.add(new Adverts("Скупка ноутбуков с выездом! 2", "26.07.2017", R.drawable.cat_commandments));
        mListAdverts.add(new Adverts("Скупка ноутбуков с выездом! 3", "26.07.2017", R.drawable.cat_commandments));
    }
}

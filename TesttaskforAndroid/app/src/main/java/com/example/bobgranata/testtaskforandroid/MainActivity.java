package com.example.bobgranata.testtaskforandroid;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Context mContext;

    RecyclerViewAdapter mAdapter;
    LinearLayoutManager mllm_lay;
    RecyclerView mRvListOfAds;
    ProgressBar mmUpdateListProgressBar;

    private List<Adverts> mListAdverts;

    int visibleItemCount;
    int totalItemCount;
    int firstVisibleItem;

    boolean mLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRvListOfAds = (RecyclerView)findViewById(R.id.rvListOfAds);
        mmUpdateListProgressBar = (ProgressBar)findViewById(R.id.updateListProgressBar);
//        mRvListOfAds.setHasFixedSize(true);

        mContext = getApplicationContext();
        mllm_lay = new LinearLayoutManager(mContext);
        mRvListOfAds.setLayoutManager(mllm_lay);

        mListAdverts = new ArrayList<>();
        initializeData();
//        mAdapter = new RecyclerViewAdapter(mListAdverts);
//        mRvListOfAds.setAdapter(mAdapter);

        mRvListOfAds.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = recyclerView.getChildCount();
                totalItemCount = mllm_lay.getItemCount();
                firstVisibleItem = mllm_lay.findFirstVisibleItemPosition();

                if (!mLoading && (visibleItemCount + firstVisibleItem) >= totalItemCount) {
                    if (totalItemCount == 400)
                    Log.e("Total item count", "Error converting result " + String.valueOf(totalItemCount));
                    mLoading = true;
                    new RequestDataTask().execute("100", String.valueOf(totalItemCount));
                }
            }
        });
    }

    public void ProgressWheelOn() {
        mmUpdateListProgressBar.setVisibility(View.VISIBLE);
    }

    public void ProgressWheelOff() {
        mmUpdateListProgressBar.setVisibility(View.INVISIBLE);
    }

    private void initializeData() {
        new RequestDataTask().execute("100", "0");
    }

    public void updateList(List<Adverts> updateListAdverts) {
        for(Adverts itemAdv : updateListAdverts) {
            mListAdverts.add(itemAdv);
        }

        if (mAdapter == null) {
            mAdapter = new RecyclerViewAdapter(mListAdverts);
            mRvListOfAds.setAdapter(mAdapter);
        }

        mAdapter.notifyDataSetChanged();
        mLoading = false;
    }

    public class RequestDataTask extends AsyncTask<String, Void, String> {
        private static final int NET_READ_TIMEOUT_MILLIS = 1000;
        private static final int NET_CONNECT_TIMEOUT_MILLIS = 1000;

        //http://do.ngs.ru/api/v1/adverts/?include=uploads,tags&fields=short_images,cost,update_date&limit=1&offset=0
        private final String URL_SERVER = "http://do.ngs.ru/api/v1/adverts/?include=uploads,tags&fields=short_images,cost,update_date";
        private final String LIMIT = "limit";
        private final String OFFSET = "offset";

        List<Adverts> mListAdv;

        public RequestDataTask() {
            super();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgressWheelOn();
        }

        @Override
        protected String doInBackground(String... params) {
            String sLimit = params[0];
            String sOffset = params[1];
            String url = "";

            url = URL_SERVER + "&" + LIMIT + "=" + sLimit + "&" + OFFSET + "=" + sOffset;

            return loadJSON(url);
        }

        public String loadJSON(String url) {

            // создаём HTTP запрос
            InputStream isReqHttpAnswer = null;
            String sError = "";

            HttpURLConnection connect = null;
            try {
                connect = (HttpURLConnection) new URL(url).openConnection();
                connect.setReadTimeout(NET_READ_TIMEOUT_MILLIS /* milliseconds */);
                connect.setConnectTimeout(NET_CONNECT_TIMEOUT_MILLIS /* milliseconds */);
                connect.setRequestMethod("GET");
                connect.setDoInput(true);
                // Starts the query
                connect.connect();

                int status = connect.getResponseCode();
                switch (status) {
                    case 200:
                    case 201: {
                        isReqHttpAnswer = connect.getInputStream();
                        JSONParser jParser = new JSONParser(isReqHttpAnswer);
                        mListAdv = jParser.startParsing();
                    } break;
                    default: {
                        sError = "Ошибка. Сервер ответил не коректно. Код ответа: " + status;
                    }
                }
            } catch (MalformedURLException e) {
                //код обработки ошибки
                e.printStackTrace();
                sError = e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                sError = e.getMessage();
            }

            return sError;
        }

        @Override
        protected void onPostExecute(String sError) {
            super.onPostExecute(sError);

            ProgressWheelOff();
            updateList(mListAdv);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);

        }
    }
}

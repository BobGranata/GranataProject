package com.bobgranata.questionary;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.Date;
import java.util.UUID;

public class FirstStepActivity extends ActionBarActivity implements TextWatcher {
    Uri photoUri;
    boolean pushGpsBtn;
    boolean pushPhotoBtn;
    Button btnForward;
    Button btnGetGps;
    Button mBtnPhoto;
    EditText mEtPhotoFileName;

    String mmLongitude;
    String mmLatitude;
    String photoFileName;
    String[] mArrNameStore;

    private static int TAKE_PICTURE = 1;
    private static int TAKE_CLEAN = 2;

    private LocationManager locationManager;

    AutoCompleteTextView mAutoCompleteStoreName;

    private SharedPreferences mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_step);
        pushGpsBtn = false;
        pushPhotoBtn = false;
        btnForward = (Button)findViewById(R.id.btnForward);
        btnGetGps = (Button)findViewById(R.id.btnFirstStepGetGPS);
        mBtnPhoto = (Button)findViewById(R.id.btnFirstStepGetPhoto);
        mEtPhotoFileName = (EditText)findViewById(R.id.etPhotoFileName);

        photoUri = null;
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        mArrNameStore = getResources().getStringArray(R.array.spinNameNet);

        /* Initializing settings */
        mSettings = getSharedPreferences(SettingsActivity.APP_PREFERENCES, Context.MODE_PRIVATE);


        mAutoCompleteStoreName = (AutoCompleteTextView) findViewById(R.id.autoCompleteStoreName);
        mAutoCompleteStoreName.addTextChangedListener(this);
        mAutoCompleteStoreName.setAdapter(new ArrayAdapter(this,
                android.R.layout.simple_dropdown_item_1line, mArrNameStore));
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_first_step, menu);
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

    public void onClickGetPhoto(View view) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            photoFileName = UUID.randomUUID().toString() + ".jpg";

            File file = new File(Environment.getExternalStorageDirectory(),
                    photoFileName);

            Uri photoPath = Uri.fromFile(file);
            mEtPhotoFileName.setText(photoPath.toString());
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoPath);
            startActivityForResult(intent, TAKE_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
//        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        TextView tvCheckSavePhoto = (TextView)findViewById(R.id.tvCheckSavePhoto);
        String messageSave = "";

        if (requestCode == TAKE_PICTURE) {
            // Check if the result includes a thumbnail Bitmap
            if (imageReturnedIntent == null) {
                if (resultCode == -1) {
                    tvCheckSavePhoto.setText("Фотография сохранена");
                    btnForward.setEnabled(true);
                } else {
                    tvCheckSavePhoto.setText("Фотография не сохранена, попробуйте снова сделать снимок");
                    btnForward.setEnabled(false);
                }
                //TODO autoCompleteStoreName.requestFocus();
                mAutoCompleteStoreName.requestFocus();
            }
        } else if (requestCode == TAKE_CLEAN) {
            if (resultCode == RESULT_OK) {
                btnForward.setEnabled(false);

                EditText edLongFirstStep = (EditText)findViewById(R.id.edLongFirstStep);
                EditText etLatFirstStep = (EditText)findViewById(R.id.etLatFirstStep);

                Spinner spinClientAddressTypeStreet = (Spinner)findViewById(R.id.spinClientAddressTypeStreet);
                EditText etFSClientAddressStreet = (EditText)findViewById(R.id.etFSClientAddressStreet);
                EditText etFSClientAddressHouse = (EditText)findViewById(R.id.etFSClientAddressHouse);
                EditText etFSClientAddressCorps = (EditText)findViewById(R.id.etFSClientAddressCorps);
                EditText etFSClientAddressStruct = (EditText)findViewById(R.id.etFSClientAddressStruct);
                EditText etFSClientAddressComment = (EditText)findViewById(R.id.etFSClientAddressComment);

                edLongFirstStep.setText("");
                etLatFirstStep.setText("");
                spinClientAddressTypeStreet.setSelection(0);
                etFSClientAddressStreet.setText("");
                etFSClientAddressHouse.setText("");
                etFSClientAddressCorps.setText("");
                etFSClientAddressStruct.setText("");
                etFSClientAddressComment.setText("");
                mEtPhotoFileName.setText("");
                mAutoCompleteStoreName.setText("");

            }
        }
    }

    public void onClickForward(View view) {

        String sStoreName = mAutoCompleteStoreName.getText().toString();

        if (sStoreName.equals("")) {
            showAlert("Необходимо заполнить название магазина.");
            return;
        }

        boolean LongQuest = true;
        for (int i = 1; i < mArrNameStore.length; i++) {
            if (mArrNameStore[i].toLowerCase().equals(sStoreName.toLowerCase())) {
                LongQuest = false;
                break;
            }
        }

        Intent intent;
        if (LongQuest) {
            intent = new Intent(this, MainQuest.class);
        } else {
            intent = new Intent(this, ShortQuestActivity.class);
        }

        EditText edLongFirstStep = (EditText)findViewById(R.id.edLongFirstStep);
        EditText etLatFirstStep = (EditText)findViewById(R.id.etLatFirstStep);

        Spinner spinClientAddressTypeStreet = (Spinner)findViewById(R.id.spinClientAddressTypeStreet);
        EditText etFSClientAddressStreet = (EditText)findViewById(R.id.etFSClientAddressStreet);
        EditText etFSClientAddressHouse = (EditText)findViewById(R.id.etFSClientAddressHouse);
        EditText etFSClientAddressCorps = (EditText)findViewById(R.id.etFSClientAddressCorps);
        EditText etFSClientAddressStruct = (EditText)findViewById(R.id.etFSClientAddressStruct);
        EditText etFSClientAddressComment = (EditText)findViewById(R.id.etFSClientAddressComment);

        mmLongitude = edLongFirstStep.getText().toString();
        mmLatitude = etLatFirstStep.getText().toString();

        intent.putExtra("Longitude", mmLongitude);
        intent.putExtra("Latitude", mmLatitude);

        intent.putExtra("StoreName", sStoreName);

        intent.putExtra("ClientAddressTypeStreet", spinClientAddressTypeStreet.getSelectedItem().toString());
        intent.putExtra("ClientAddressStreet", etFSClientAddressStreet.getText().toString());
        intent.putExtra("ClientAddressHouse", etFSClientAddressHouse.getText().toString());
        intent.putExtra("ClientAddressCorps", etFSClientAddressCorps.getText().toString());
        intent.putExtra("ClientAddressStruct", etFSClientAddressStruct.getText().toString());
        intent.putExtra("ClientAddressComment", etFSClientAddressComment.getText().toString());

        String photoUri = mEtPhotoFileName.getText().toString();
        intent.putExtra("PhotoUri", photoUri);

        startActivityForResult(intent, TAKE_CLEAN);
//        startActivity(intent);
    }

    public void onClickFirstStepGetGPS(View view) {
        String looseSettings = "";
        //TODO переделать все строчки на строковые константы из настроек
        if (mSettings.contains(SettingsActivity.APP_PREFERENCES_FIO)) {
            if (mSettings.getString(SettingsActivity.APP_PREFERENCES_FIO, "").equals(""))
                looseSettings += "\nФИО";
        } else
            looseSettings += "\nФИО";

        if (mSettings.contains(SettingsActivity.APP_PREFERENCES_TERNUM)) {
            if (mSettings.getString(SettingsActivity.APP_PREFERENCES_TERNUM, "").equals(""))
                looseSettings += "\nномер территории";
        } else
            looseSettings += "\nномер территории";

        if (mSettings.contains(SettingsActivity.APP_PREFERENCES_CITY)) {
            if (mSettings.getString(SettingsActivity.APP_PREFERENCES_CITY, "").equals(""))
                looseSettings += "\nгород";
        } else
            looseSettings += "\nгород";

        if (mSettings.contains(SettingsActivity.APP_PREFERENCES_AO)) {
            if (mSettings.getString(SettingsActivity.APP_PREFERENCES_AO, "").equals(""))
                looseSettings += "\nАОкруг";
        } else
            looseSettings += "\nАОкруг";

        if (!looseSettings.equals("")) {
            showAlert("Для заполнения анкеты, необходимо указать в настройках: " + looseSettings);
        } else {
            mBtnPhoto.setEnabled(true);

//TODO Тут координаты
//        new UpdateTask(this).execute("https://geocode-maps.yandex.ru/1.x/?geocode=37.613636,55.764897&results=1&format=json");

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1000 * 10, 10, locationListener);

        boolean isNetworkEnabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (isNetworkEnabled) {
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, 1000 * 10, 10,
                    locationListener);
        }

        checkEnabled();
        }
    }

//--------------------------------------------------------------------------------------------------
    @Override
    protected void onResume() {
        super.onResume();

//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
//                1000 * 10, 10, locationListener);
//        locationManager.requestLocationUpdates(
//                LocationManager.NETWORK_PROVIDER, 1000 * 10, 10,
//                locationListener);
//        checkEnabled();
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(locationListener);
    }

    private LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            showLocation(location);
        }

        @Override
        public void onProviderDisabled(String provider) {
            checkEnabled();
        }

        @Override
        public void onProviderEnabled(String provider) {
            checkEnabled();
            showLocation(locationManager.getLastKnownLocation(provider));
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
//            if (provider.equals(LocationManager.GPS_PROVIDER)) {
//                tvStatusGPS.setText("Status: " + String.valueOf(status));
//            } else if (provider.equals(LocationManager.NETWORK_PROVIDER)) {
//                tvStatusNet.setText("Status: " + String.valueOf(status));
//            }
        }
    };

    private void showLocation(Location location) {
        if (location == null)
            return;

        EditText edLongFirstStep = (EditText)findViewById(R.id.edLongFirstStep);
        EditText etLatFirstStep = (EditText)findViewById(R.id.etLatFirstStep);
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();
        edLongFirstStep.setText(Double.toString(longitude));
        etLatFirstStep.setText(Double.toString(latitude));

        String longitudeHttp = Double.toString(longitude); //"37.495727"
        String latitudeHttp = Double.toString(latitude); //"55.737348"
// TODO закоментим пока Яндекс
//        if (longitudeHttp != "" && latitudeHttp != "")
//            new UpdateTask(this).execute("https://geocode-maps.yandex.ru/1.x/?geocode=" + longitudeHttp + "," + latitudeHttp + "&results=1&format=json");

//        if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
//            edLongFirstStep.setText(formatLocation(location));
//        } else if (location.getProvider().equals(
//                LocationManager.NETWORK_PROVIDER)) {
//            etLatFirstStep.setText(formatLocation(location));
//        }
    }

    private String formatLocation(Location location) {
        if (location == null)
            return "";
        return String.format(
                "Coordinates: lat = %1$.4f, lon = %2$.4f, time = %3$tF %3$tT",
                location.getLatitude(), location.getLongitude(), new Date(
                        location.getTime()));
    }

    private void checkEnabled() {
//        tvEnabledGPS.setText("Enabled: "
//                + locationManager
//                .isProviderEnabled(LocationManager.GPS_PROVIDER));
//        tvEnabledNet.setText("Enabled: "
//                + locationManager
//                .isProviderEnabled(LocationManager.NETWORK_PROVIDER));
    }

    public void onClickLocationSettings(View view) {
        startActivity(new Intent(
                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
    };

    public void showAlert(String AlertText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ошибка");
        builder.setMessage(AlertText);
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

    public void updateAdress(String street, String house) {
        EditText etFSClientAddressStreet = (EditText)findViewById(R.id.etFSClientAddressStreet);
        EditText etFSClientAddressHouse = (EditText)findViewById(R.id.etFSClientAddressHouse);
        EditText etFSClientAddressCorps = (EditText)findViewById(R.id.etFSClientAddressCorps);
        EditText etFSClientAddressStruct = (EditText)findViewById(R.id.etFSClientAddressStruct);


        etFSClientAddressStreet.setText(street);

        int corpPos = house.toLowerCase().indexOf('к');
        int StructPos = house.toLowerCase().indexOf('с');

        if (corpPos >= 0 && corpPos != house.length()-1) {
            etFSClientAddressHouse.setText(house.substring(0, corpPos));
            etFSClientAddressCorps.setText(house.substring(corpPos+1, house.length()));
        } else if (StructPos >= 0 && StructPos != house.length()-1) {
            etFSClientAddressHouse.setText(house.substring(0, StructPos));
            etFSClientAddressStruct.setText(house.substring(StructPos+1, house.length()));
        } else {
            etFSClientAddressHouse.setText(house);
        }
    }

    public void onClickBackward(View view) {
        finish();
    }
}
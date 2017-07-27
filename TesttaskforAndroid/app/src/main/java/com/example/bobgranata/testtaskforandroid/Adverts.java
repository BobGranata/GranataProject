package com.example.bobgranata.testtaskforandroid;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by BobGranata on 25.07.2017.
 */

public class Adverts {
    String date;
    String title;
    String price;
    String type;
    String tags;
    String photoUrl;
    int photoId;

    Adverts() {
        date = "";
        title = "";
        price = "";
        type = "";
        tags = "";
        photoUrl = "";
        photoId = R.drawable.cat_commandments;
    }

    Adverts(String title, String date, int photoId) {
        this.title = title;
        this.date = date;
        this.photoId = photoId;
    }

    Adverts(String title, String date, String price, String type, String photoUrl) {

        if (title.length() >= 45) {
            title = title.substring(0, 45);
            title += "...";
        }
        this.title = title;

        if (date != "") {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            Date pdate = null;
            try {
                pdate = fmt.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            SimpleDateFormat fmtOut = new SimpleDateFormat("dd.MM.yyyy");
            date = fmtOut.format(pdate);
        }

        this.date = date;

        if (price == "" || price.equals("0")) {
            price = "цена не указана.";
        } else {
            price += " руб.";
        }
        this.price = price;

        if (type.length() > 1) {
            String firstLet = type.substring(0,1).toUpperCase();
            String lastWord = type.substring(1,type.length()).toLowerCase();
            type = firstLet + lastWord;
        }
        this.type = type;

        this.photoUrl = "https://" + photoUrl;

        this.photoId = R.drawable.cat_commandments;
    }
}

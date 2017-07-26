package com.example.bobgranata.testtaskforandroid;

import java.util.ArrayList;
import java.util.List;

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
        this.title = title;
        this.date = date;
        this.price = price;
        this.type = type;
        this.photoUrl = photoUrl;
    }
}

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
    int photoId;

    Adverts(String title, String date, int photoId) {
        this.title = title;
        this.date = date;
        this.photoId = photoId;
    }
}

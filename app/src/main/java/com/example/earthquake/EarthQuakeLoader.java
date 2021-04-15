package com.example.earthquake;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.util.List;

public class EarthQuakeLoader extends AsyncTaskLoader<List<Earthquake>> {

    private static final String LOG_TAG = EarthQuakeLoader.class.getSimpleName();
    String urlString;
    public EarthQuakeLoader(@NonNull Context context,String url) {
        super(context);
        urlString = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
        Log.d(LOG_TAG,"OnStartLoading called");

    }

    @Nullable
    @Override
    public List<Earthquake> loadInBackground() {
        Log.d(LOG_TAG,"loadInBackground called");
        if (urlString!=null){
            return QueryUtils.fetchEarthQuakeData(urlString);
        }
        return null;
     }
}

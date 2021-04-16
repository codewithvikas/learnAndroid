package com.example.earthquake;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements EarthQuakeItemListener, LoaderManager.LoaderCallbacks<List<Earthquake>> {

    RecyclerView earthquakeList;
    TextView emptyStateView;
    ProgressBar progressBar;
    EarthQuakeAdapter earthQuakeAdapter;

    public static final int EARTHQUAKE_LOADER_ID = 1;

    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query";

    private static final String LOG_TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        earthquakeList = findViewById(R.id.earthquake_list);
        emptyStateView = findViewById(R.id.empty_state_view);
        progressBar = findViewById(R.id.data_progress_status);

        earthquakeList.setLayoutManager(new LinearLayoutManager(this));

         earthQuakeAdapter = new EarthQuakeAdapter(new ArrayList<Earthquake>(),this);
        earthquakeList.setAdapter(earthQuakeAdapter);

        if (isInternetAvailable()){
            android.app.LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID,null,this);
        }
        else {
            progressBar.setVisibility(View.GONE);
            makeEmptyStateVisible();
            emptyStateView.setText(R.string.network_error);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.action_Setting){
                Intent intent = new Intent(this,SettingActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    boolean isInternetAvailable(){
        ConnectivityManager connectivityManager  = (ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo!=null && networkInfo.isConnectedOrConnecting();
    }
    @Override
    public void itemClicked(Earthquake earthquake) {
        Intent openBrowser = new Intent(Intent.ACTION_VIEW);
        Uri dataUri = Uri.parse(earthquake.getUrl());
        openBrowser.setData(dataUri);
        if (openBrowser.resolveActivity(getPackageManager())!=null){
            startActivity(openBrowser);
        }
    }

    @NonNull
    @Override
    public Loader<List<Earthquake>> onCreateLoader(int id, @Nullable Bundle args) {

        Log.d(LOG_TAG,"OnCreateLoader called");

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String minMagnitude = sharedPreferences.getString(
                getString(R.string.setting_min_magnitude_key),
                getString(R.string.setting_min_magnitude_default)
        );
        Uri baseUri = Uri.parse(USGS_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("format","geojson");
        uriBuilder.appendQueryParameter("limit","10");
        uriBuilder.appendQueryParameter("minmag",minMagnitude);
        uriBuilder.appendQueryParameter("orderby","time");
        return new EarthQuakeLoader(this,uriBuilder.toString());

    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Earthquake>> loader, List<Earthquake> data) {
        progressBar.setVisibility(View.GONE);

        if (data==null||data.isEmpty()){
            makeEmptyStateVisible();
            emptyStateView.setText(R.string.empty_state);
        }
        else {
            makeEarthQuakeListVisible();
            earthQuakeAdapter.clear();

            earthQuakeAdapter.addAll(data);
        }

        Log.d(LOG_TAG,"OnLoadFinished called");
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Earthquake>> loader) {
        earthQuakeAdapter.clear();
        Log.d(LOG_TAG,"OnLoaderReset called");
    }


    void makeEmptyStateVisible(){
        emptyStateView.setVisibility(View.VISIBLE);
        earthquakeList.setVisibility(View.INVISIBLE);
    }
    void makeEarthQuakeListVisible(){
        earthquakeList.setVisibility(View.VISIBLE);
        emptyStateView.setVisibility(View.INVISIBLE);
    }

}
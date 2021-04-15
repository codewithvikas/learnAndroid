package com.example.earthquake;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class QueryUtils {

    public static String RESPONSE_FROM_JSON = "{\"type\":\"FeatureCollection\",\"metadata\":{\"generated\":1617086428000,\"url\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2016-01-01&endtime=2016-01-31&minmag=6&limit=10\",\"title\":\"USGS Earthquakes\",\"status\":200,\"api\":\"1.10.3\",\"limit\":10,\"offset\":1,\"count\":10}," +
            "\"features\":[" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":7.2,\"place\":\"88km N of Yelizovo, Russia\",\"time\":1454124312220,\"updated\":1594162166283,\"tz\":720,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us20004vvx\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us20004vvx&format=geojson\",\"felt\":3,\"cdi\":3.4,\"mmi\":6.719,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":1,\"sig\":799,\"net\":\"us\",\"code\":\"20004vvx\",\"ids\":\",gcmt20160130032510,pt16030050,at00o1qxho,us20004vvx,gcmt20160130032512,atlas20160130032512,\",\"sources\":\",gcmt,pt,at,us,gcmt,atlas,\",\"types\":\",associate,cap,dyfi,finite-fault,general-text,geoserve,impact-link,impact-text,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,tectonic-summary,\",\"nst\":null,\"dmin\":0.958,\"rms\":1.19,\"gap\":17,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 7.2 - 88km N of Yelizovo, Russia\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[158.5463,53.9776,177]},\"id\":\"us20004vvx\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":6.1,\"place\":\"94km SSE of Taron, Papua New Guinea\",\"time\":1453777820750,\"updated\":1478815803221,\"tz\":600,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us20004uks\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us20004uks&format=geojson\",\"felt\":null,\"cdi\":null,\"mmi\":4.1,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":1,\"sig\":572,\"net\":\"us\",\"code\":\"20004uks\",\"ids\":\",gcmt20160126031023,us20004uks,gcmt20160126031020,\",\"sources\":\",gcmt,us,gcmt,\",\"types\":\",associate,cap,geoserve,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,tectonic-summary,\",\"nst\":null,\"dmin\":1.537,\"rms\":0.74,\"gap\":25,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.1 - 94km SSE of Taron, Papua New Guinea\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[153.2454,-5.2952,26]},\"id\":\"us20004uks\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":5.3,\"place\":\"50km NNE of Al Hoceima, Morocco\",\"time\":1453695722730,\"updated\":1594393180266,\"tz\":0,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us10004gy9\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us10004gy9&format=geojson\",\"felt\":117,\"cdi\":7.2,\"mmi\":5.551,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":0,\"sig\":695,\"net\":\"us\",\"code\":\"10004gy9\",\"ids\":\",gcmt20160125042203,us10004gy9,gcmt20160125042202,atlas20160125042202,\",\"sources\":\",gcmt,us,gcmt,atlas,\",\"types\":\",associate,cap,dyfi,geoserve,impact-text,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,tectonic-summary,\",\"nst\":null,\"dmin\":2.201,\"rms\":0.92,\"gap\":20,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.3 - 50km NNE of Al Hoceima, Morocco\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-3.6818,35.6493,12]},\"id\":\"us10004gy9\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":7.1,\"place\":\"86km E of Old Iliamna, Alaska\",\"time\":1453631429557,\"updated\":1594161953847,\"tz\":-540,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/ak01613v15nv\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=ak01613v15nv&format=geojson\",\"felt\":1816,\"cdi\":7.2,\"mmi\":7.461,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":1,\"sig\":1496,\"net\":\"ak\",\"code\":\"01613v15nv\",\"ids\":\",ak12496371,at00o1gd6r,us10004gqp,ak01613v15nv,atlas20160124103030,atlas20160124103029,\",\"sources\":\",ak,at,us,ak,atlas,atlas,\",\"types\":\",associate,cap,dyfi,finite-fault,general-text,geoserve,impact-link,impact-text,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,tectonic-summary,trump-shakemap,\",\"nst\":null,\"dmin\":null,\"rms\":1.05,\"gap\":null,\"magType\":\"mw\",\"type\":\"earthquake\",\"title\":\"M 7.1 - 86km E of Old Iliamna, Alaska\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-153.3392,59.6204,125.6]},\"id\":\"ak01613v15nv\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":6.6,\"place\":\"215km SW of Tomatlan, Mexico\",\"time\":1453399617650,\"updated\":1478815764127,\"tz\":-420,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us10004g4l\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us10004g4l&format=geojson\",\"felt\":11,\"cdi\":2.7,\"mmi\":3.92,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":1,\"sig\":673,\"net\":\"us\",\"code\":\"10004g4l\",\"ids\":\",gcmt20160121180659,at00o1bebo,pt16021050,us10004g4l,gcmt20160121180657,\",\"sources\":\",gcmt,at,pt,us,gcmt,\",\"types\":\",associate,cap,dyfi,geoserve,impact-link,impact-text,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,tectonic-summary,\",\"nst\":null,\"dmin\":2.413,\"rms\":0.98,\"gap\":74,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.6 - 215km SW of Tomatlan, Mexico\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-106.9337,18.8239,10]},\"id\":\"us10004g4l\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":6.7,\"place\":\"52km SE of Shizunai, Japan\",\"time\":1452741933640,\"updated\":1594161934603,\"tz\":540,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us10004ebx\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us10004ebx&format=geojson\",\"felt\":51,\"cdi\":5.8,\"mmi\":6.108,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":1,\"sig\":720,\"net\":\"us\",\"code\":\"10004ebx\",\"ids\":\",us10004ebx,gcmt20160114032534,at00o0xauk,pt16014050,gcmt20160114032533,atlas20160114032533,\",\"sources\":\",us,gcmt,at,pt,gcmt,atlas,\",\"types\":\",associate,cap,dyfi,geoserve,impact-link,impact-text,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,\",\"nst\":null,\"dmin\":0.281,\"rms\":0.98,\"gap\":22,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.7 - 52km SE of Shizunai, Japan\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[142.781,41.9723,46]},\"id\":\"us10004ebx\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":6.1,\"place\":\"12km WNW of Charagua, Bolivia\",\"time\":1452741928270,\"updated\":1478815697357,\"tz\":-240,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us10004ebw\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us10004ebw&format=geojson\",\"felt\":3,\"cdi\":2.2,\"mmi\":2.21,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":0,\"sig\":573,\"net\":\"us\",\"code\":\"10004ebw\",\"ids\":\",us10004ebw,gcmt20160114032528,\",\"sources\":\",us,gcmt,\",\"types\":\",cap,dyfi,geoserve,impact-text,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,tectonic-summary,\",\"nst\":null,\"dmin\":5.492,\"rms\":1.04,\"gap\":16,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.1 - 12km WNW of Charagua, Bolivia\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-63.3288,-19.7597,582.56]},\"id\":\"us10004ebw\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":2.2,\"place\":\"74km NW of Rumoi, Japan\",\"time\":1452532083920,\"updated\":1594393087726,\"tz\":540,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us10004djn\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us10004djn&format=geojson\",\"felt\":8,\"cdi\":3.4,\"mmi\":3.8,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":0,\"sig\":594,\"net\":\"us\",\"code\":\"10004djn\",\"ids\":\",us10004djn,gcmt20160111170803,\",\"sources\":\",us,gcmt,\",\"types\":\",cap,dyfi,geoserve,impact-text,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,tectonic-summary,\",\"nst\":null,\"dmin\":1.139,\"rms\":0.96,\"gap\":33,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.2 - 74km NW of Rumoi, Japan\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[141.0867,44.4761,238.81]},\"id\":\"us10004djn\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":6.5,\"place\":\"227km SE of Sarangani, Philippines\",\"time\":1452530285900,\"updated\":1594393074746,\"tz\":480,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us10004dj5\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us10004dj5&format=geojson\",\"felt\":1,\"cdi\":2.7,\"mmi\":6.398,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":1,\"sig\":650,\"net\":\"us\",\"code\":\"10004dj5\",\"ids\":\",gcmt20160111163807,pt16011050,at00o0srjp,us10004dj5,gcmt20160111163805,atlas20160111163805,\",\"sources\":\",gcmt,pt,at,us,gcmt,atlas,\",\"types\":\",associate,cap,dyfi,geoserve,impact-link,impact-text,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,tectonic-summary,\",\"nst\":null,\"dmin\":3.144,\"rms\":0.72,\"gap\":22,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.5 - 227km SE of Sarangani, Philippines\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[126.8621,3.8965,13]},\"id\":\"us10004dj5\"},\n" +
            "{\"type\":\"Feature\",\"properties\":{\"mag\":6,\"place\":\"Pacific-Antarctic Ridge\",\"time\":1451986454620,\"updated\":1478815631921,\"tz\":-540,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us10004bgk\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us10004bgk&format=geojson\",\"felt\":0,\"cdi\":1,\"mmi\":0,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":0,\"sig\":554,\"net\":\"us\",\"code\":\"10004bgk\",\"ids\":\",gcmt20160105093415,us10004bgk,gcmt20160105093414,\",\"sources\":\",gcmt,us,gcmt,\",\"types\":\",associate,cap,dyfi,geoserve,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,\",\"nst\":null,\"dmin\":30.75,\"rms\":0.67,\"gap\":71,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.0 - Pacific-Antarctic Ridge\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-136.2603,-54.2906,10]},\"id\":\"us10004bgk\"}],\"bbox\":[-153.3392,-54.2906,10,158.5463,59.6204,582.56]}" +
            "";


    static ArrayList<Earthquake> fetchEarthQuakeData(String urlString){

        /*try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        URL url = createUrl(urlString);
        String jsonResponse = makeHttpRequest(url);
        return QueryUtils.extractEarthQuake(jsonResponse);
    }
    static URL createUrl(String urlString){
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.d("EarthQuake Data","Problem building URL");
        }
        return url;
    }

    static String makeHttpRequest(URL url){
        String jsonResponse = "";
        if (url==null){
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if(urlConnection.getResponseCode()==200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
            else {
                Log.d("QuackJson","Error Respose: "+urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("Quack Data","Problem in retrieving EarthQuake data");
        }
        finally {
            if (urlConnection!=null){
                urlConnection.disconnect();
            }
            if (inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonResponse;
    }
    static String readFromStream(InputStream inputStream){
        StringBuilder stringBuilder = new StringBuilder();
        if (inputStream!=null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            try {
                String line = bufferedReader.readLine();
                while (line!=null){
                    stringBuilder.append(line);
                    line = bufferedReader.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return stringBuilder.toString();
    }

    public static ArrayList<Earthquake> extractEarthQuake(String jsonResponse){
        ArrayList<Earthquake> earthquakes = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray features = jsonObject.getJSONArray("features");
            for (int i = 0;i<features.length();i++){
                JSONObject feature = features.getJSONObject(i);
                double mag = feature.getJSONObject("properties").getDouble("mag");
                String place = feature.getJSONObject("properties").getString("place");
                long time = feature.getJSONObject("properties").getLong("time");
                String url = feature.getJSONObject("properties").getString("url");
                earthquakes.add(new Earthquake(mag,place,time,url));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return earthquakes;
    }

    public static String dateToDisplay(long unixTime){
        Date date = new Date(unixTime);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM DD YYYY", Locale.ENGLISH);

        String dateToDisplay = simpleDateFormat.format(date);
        return dateToDisplay;
    }

    public static String timeToDisplay(long unixTime){
        Date date =  new Date(unixTime);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm a");
        return simpleDateFormat.format(date);
    }

}

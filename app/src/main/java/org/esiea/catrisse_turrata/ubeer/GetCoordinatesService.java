package org.esiea.catrisse_turrata.ubeer;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class GetCoordinatesService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_COORDINATES = "org.esiea.catrisse_turrata.ubeer.action.FOO";
    private static final String API_KEY = "AIzaSyBmuUqou3PJXUtJRRS2zZq6Ul7feXdH3EI";
    private static final String PLACES_API_KEY ="AIzaSyDh4ghFcDx-C5i9u4xFosBV47D0x_7DcZE";
    ArrayList locations;
    boolean gps;
    // TODO: Rename parameters
    private static final String loc="locPARAM";
    private static final String g="gpsPARAM";
    //private static final String EXTRA_PARAM2 = "com.gustutur.osef.test.extra.PARAM2";

    public GetCoordinatesService() {
        super("GetCoordinatesService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionCoordinates(Context context,ArrayList<String> l,boolean gps) {


        Intent intent = new Intent(context, GetCoordinatesService.class);
        intent.setAction(ACTION_COORDINATES);

        intent.putExtra(loc,l);
        intent.putExtra(g,gps);
        //intent.putExtra(EXTRA_PARAM2, param2);

        context.startService(intent);

    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_COORDINATES.equals(action)) {
                locations=intent.getStringArrayListExtra(loc);
                gps=intent.getBooleanExtra(g,gps);
                //final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                //final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionCoordinates();
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionCoordinates() {
        Intent myIntent = new Intent(SetupActivity.COORDINATES_UPDATE);
        int size=locations.size();
        if(gps){
            size++;
        }
        String latitudes[] = new String[size];
        String longitudes[] = new String[size];

        for(int j=0;j<locations.size();j++) {


            // Log.d("AAA","Thread service name : "+Thread.currentThread().getName());
            URL url = null;

            try {
                String[] splitted = locations.get(j).toString().split(" ");

                String urlString = "";
                urlString += "https://maps.googleapis.com/maps/api/geocode/json?address=";
                for (int i = 0; i < splitted.length; i++) {
                    urlString += splitted[i];
                    if (i < splitted.length - 1) {
                        urlString += "+";
                    }
                }
                urlString += "&key=" + API_KEY;
                Log.w("AAA", urlString);


                //url = new URL("http://binouze.fabrigli.fr/bieres.json");
                //url = new URL("https://maps.googleapis.com/maps/api/geocode/json?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA&key="+API_KEY);
                url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                if (HttpURLConnection.HTTP_OK == conn.getResponseCode()) {
                    copyInputStreamToFile(conn.getInputStream(),
                            new File(getCacheDir(), "temp.json"));

                    try {
                        longitudes[j] = getCoordinatesFromFile().getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getString("lng");
                        latitudes[j] = getCoordinatesFromFile().getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getString("lat");
                    } catch (JSONException e) {
                        Log.e("AAA","Error");
                        //LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(SetupActivity.COORDINATES_ERROR));
                        //e.printStackTrace();
                    }
                    Log.w("NNN",latitudes[j]+"   "+longitudes[j]);

                    //Log.d("AAA","coordinates json download !");
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //throw new UnsupportedOperationException("Not yet implemented");
        }

        if(gps){

        }

        double totalLat=0;
        double totalLng=0;
        boolean notFound=false;
        for(int i=0;i<size;i++){
            if(latitudes[i]==null||longitudes[i]==null){
                    notFound=true;
            }else{
                totalLat+=Double.parseDouble(latitudes[i]);
                totalLng+=Double.parseDouble(longitudes[i]);
            }
        }

        if(!notFound) {
            String averageLat = String.valueOf(totalLat / size);
            String averageLng = String.valueOf(totalLng / size);

            URL url = null;


            //SECOND REQUEST

            try {
                String urlString = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + averageLat + "," + averageLng + "&radius=500&type=bar&key=" + PLACES_API_KEY;
                url = new URL(urlString);
                HttpURLConnection conn2 = (HttpURLConnection) url.openConnection();
                conn2.setRequestMethod("GET");
                conn2.connect();
                if (HttpURLConnection.HTTP_OK == conn2.getResponseCode()) {
                    copyInputStreamToFile(conn2.getInputStream(),
                            new File(getCacheDir(), "bars.json"));

                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            myIntent.putExtra("RESULT", 1);



        }
        else{
            myIntent.putExtra("RESULT", 0);
            Log.d("AAA","coucou3");
        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(myIntent);
    }

    private void copyInputStreamToFile(InputStream in, File file) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JSONObject getCoordinatesFromFile(){  //JsonArray
        try {
            InputStream is = new FileInputStream(getCacheDir() + "/" + "temp.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            return new JSONObject(new String(buffer, "UTF-8")); // construction du tableau
        } catch (IOException e) {
            e.printStackTrace();
            return new JSONObject();
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONObject();
        }
    }
}

package org.esiea.catrisse_turrata.ubeer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ResultsActivity extends AppCompatActivity {
    public static final String COORDINATES_UPDATE = "com.octip.cours.inf4042_11.BIERS_UPDATE";
    private BarsAdapter bAdapter;
    protected Context context;
    private TextView test;
    private Button testButton;
    private JSONObject JSONBar;
    private Bar[] BarArray;
    //just a push test
    //just a push test 2

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        context = getApplicationContext();

        final RecyclerView barsView = (RecyclerView) findViewById(R.id.rv_bars);
        final Context context = getApplicationContext();

        try {
            JSONArray JSONBarArray=getCoordinatesFromFile().getJSONArray("results");
            int JSONBarArrayLength=JSONBarArray.length();

            BarArray=new Bar[JSONBarArrayLength];

            for (int i=0;i<JSONBarArrayLength;i++)
            {


                JSONObject tmpJSONBar=JSONBarArray.getJSONObject(i);

                BarArray[i]=new Bar(tmpJSONBar.getString("name"),tmpJSONBar.getString("vicinity"),Boolean.parseBoolean(tmpJSONBar.getJSONObject("opening_hours").getString("open_now")),Float.parseFloat(tmpJSONBar.getString("rating")));
                //Log.d("bars", BarArray[i].getName());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        barsView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        bAdapter=new BarsAdapter(BarArray);
        barsView.setAdapter(bAdapter);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.A:
                break;
            case R.id.B:
                startActivity(new Intent(context, SetupActivity.class));
                break;

        }
        return true;
    }

    public JSONObject getCoordinatesFromFile(){  //JsonArray
        try {
            InputStream is = new FileInputStream(getCacheDir() + "/" + "bars.json");
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

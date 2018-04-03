package org.esiea.catrisse_turrata.ubeer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class SetupActivity extends AppCompatActivity {
    private PersonsAdapter pAdapter;
    public static final String COORDINATES_UPDATE = "update";
    private AlertDialog.Builder alertBuilder;
    private NotificationManagerCompat notificationManager;
    private NotificationCompat.Builder notifBuilder;
    protected Context context;
    protected RecyclerView personsView;
    protected Button addPerson;
    protected Button removePerson;
    protected Button searchButton;
    protected Button gpsButton;
    private GifImageView beerGif;
    private boolean gpsOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        context = getApplicationContext();

        personsView = (RecyclerView) findViewById(R.id.rv_persons);
        addPerson=(Button) findViewById(R.id.add_Person);
        gpsButton=(Button) findViewById(R.id.gps_me);
        removePerson=(Button) findViewById(R.id.remove_Person);
        searchButton = (Button) findViewById(R.id.search);
        beerGif = (GifImageView) findViewById(R.id.GifImageView);
        gpsOn=false;

        GifImageView gifImageView = (GifImageView) findViewById(R.id.GifImageView);
        gifImageView.setGifImageResource(R.drawable.load);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        notificationManager = NotificationManagerCompat.from(this);

        alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        })
                .setIcon(android.R.drawable.ic_dialog_alert);
        notifBuilder = new NotificationCompat.Builder(this, "A")
                .setSmallIcon(R.drawable.beer_icon)
                .setContentTitle(getString(R.string.notification_title))
                .setContentText(getString(R.string.notification_text))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);



        pAdapter = new PersonsAdapter();
        personsView.setAdapter(pAdapter);

        personsView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        IntentFilter intentFilter = new IntentFilter(COORDINATES_UPDATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(new CoordinatesUpdate(), intentFilter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchClick();
            }
        });
        gpsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gpsMe();
            }
        });
        addPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pAdapter.addPerson();
            }
        });
        removePerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pAdapter.removePerson();
            }
        });
    }

    public void gpsMe(){

        PersonsAdapter.PersonsHolder p=(PersonsAdapter.PersonsHolder)personsView.findViewHolderForLayoutPosition(0);
        p.changeGps();

    }


    public void searchClick(){
        beerGif.setVisibility(View.VISIBLE);
        Toast toast = Toast.makeText(context, "Searching", Toast.LENGTH_SHORT);
        toast.show();
        ArrayList locations = new ArrayList<String>();
        boolean gps=false;
        boolean emptyFields=false;
        for(int i=0;i<pAdapter.getItemCount();i++){
            PersonsAdapter.PersonsHolder p = (PersonsAdapter.PersonsHolder) personsView.getChildViewHolder(personsView.getChildAt(i));
            String s = p.getLocationAsString();

            if(s.length()<1){
                emptyFields=true;
            }

            if(s.equals("#MyLocation")){
                gps=true;
            }else{
                locations.add(s);
            }
        }
        if(!emptyFields){
            GetCoordinatesService.startActionCoordinates(context,locations,gps);
        }else{
            alertBuilder.setTitle(R.string.warning_title)
                    .setMessage(R.string.warning_empty_message);
            alertBuilder.show();
            beerGif.setVisibility(View.GONE);
        }

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

    public class CoordinatesUpdate extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            beerGif.setVisibility(View.GONE);
            int i = intent.getIntExtra("RESULT",0);
            Log.w("AAA",getIntent().toString());
            Log.d("iii", "i is equal to:"+i);
            if(i==1){
                Log.d("tag", "onReceive: now lauching results activity");
                Toast toast = Toast.makeText(getApplicationContext(), "received", Toast.LENGTH_SHORT);
                toast.show();
                notificationManager.notify(1, notifBuilder.build());
                startActivity(new Intent(context, ResultsActivity.class));
            }else{
                alertBuilder.setTitle(R.string.warning_title)
                        .setMessage(R.string.warning_notfound_message);
                alertBuilder.show();

            }

        }
    }

}
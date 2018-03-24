package org.esiea.catrisse_turrata.ubeer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
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
    public static final String COORDINATES_UPDATE = "updateeee";
    public static final String COORDINATES_ERROR = "error";
    private AlertDialog.Builder builder;
    protected Context context;
    protected RecyclerView personsView;
    protected Button addPerson;
    protected Button removePerson;
    protected Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        context = getApplicationContext();



        personsView = (RecyclerView) findViewById(R.id.rv_persons);
        addPerson=(Button) findViewById(R.id.add_Person);
        removePerson=(Button) findViewById(R.id.remove_Person);
        searchButton = (Button) findViewById(R.id.search);

        GifImageView gifImageView = (GifImageView) findViewById(R.id.GifImageView);
        gifImageView.setGifImageResource(R.drawable.load);

        builder = new AlertDialog.Builder(this);
        builder.setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

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


    public void searchClick(){
        Toast toast = Toast.makeText(context, "Searching", Toast.LENGTH_SHORT);
        toast.show();
        ArrayList locations = new ArrayList<String>();
        boolean emptyFields=false;
        for(int i=0;i<pAdapter.getItemCount();i++){
            PersonsAdapter.PersonsHolder p = (PersonsAdapter.PersonsHolder) personsView.getChildViewHolder(personsView.getChildAt(i));
            String s = p.getLocationAsString();

            if(s.length()<1){
                emptyFields=true;
            }
            Log.w("aaa",emptyFields+"");
            locations.add(s);
        }

        if(!emptyFields){
            GetCoordinatesService.startActionCoordinates(context,locations);
        }else{
            builder.setTitle(R.string.warning_title)
                    .setMessage(R.string.warning_empty_message);
            builder.show();
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
                //pAdapter.addPerson();
                break;
            case R.id.B:
                //pAdapter.removePerson();
                break;

        }
        return true;
    }

    public class CoordinatesUpdate extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int i = intent.getIntExtra("RESULT",0);
            Log.w("AAA",getIntent().toString());
            if(i==1){
                Toast toast = Toast.makeText(getApplicationContext(), "received", Toast.LENGTH_SHORT);
                toast.show();
                startActivity(new Intent(context, ResultsActivity.class));
            }else{
                builder.setTitle(R.string.warning_title)
                        .setMessage(R.string.warning_notfound_message);
                builder.show();

            }








        }
    }

}
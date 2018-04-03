package org.esiea.catrisse_turrata.ubeer;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


import java.util.LinkedList;


/**
 * Created by arthu on 15/03/2018.
 */

public class PersonsAdapter extends RecyclerView.Adapter<PersonsAdapter.PersonsHolder>{
    LinkedList personsList;
    int nbrPersons;



    public PersonsAdapter(){
        personsList=new LinkedList<String>();
        nbrPersons=0;
        for(int i=0;i<2;i++){
            addPerson();
        }

    }

    @Override
    public PersonsAdapter.PersonsHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_person_element, parent, false);

        return new PersonsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PersonsAdapter.PersonsHolder holder, int position) {
        if (position==1)
        {
            holder.personName.setText("aaa");
            holder.personLocation.setText("16 avenuue des champs élysées paris");
        }
        else
        {
            holder.personName.setText("bbb");
            holder.personLocation.setText("20 boulevard Hausseman");
        }
        holder.personName.setHint(personsList.get(position).toString());
        holder.personLocation.setHint("address");

        if(holder.gpsOn){
            //holder.personName.setBackgroundColor(Color.parseColor("#d3d3d3"));
            holder.personName.setFocusable(false);
            holder.personName.setText("#Me");

            //holder.personLocation.setBackgroundColor(Color.parseColor("#d3d3d3"));
            holder.personLocation.setFocusable(false);
            holder.personLocation.setText("#MyLocation");

        }else{
            //holder.personName.setBackgroundColor(Color.parseColor("#fff9e6"));  //R.color.colorPrimaryLight);
            holder.personName.setFocusableInTouchMode(true);
            holder.personName.setText("");

            //holder.personLocation.setBackgroundColor(Color.parseColor("#fff9e6"));  //R.color.colorPrimaryLight);
            holder.personLocation.setFocusableInTouchMode(true);
            holder.personLocation.setText("");
            }



    }

    @Override
    public int getItemCount() {
        return personsList.size();
    }

    public void addPerson(){
        if(nbrPersons<6) {
            nbrPersons++;
            personsList.add("person " + nbrPersons);
            notifyDataSetChanged();
            //notifyItemInserted(nbrPersons-1);
        }
    }
    public void removePerson(){
        if(nbrPersons>1){
            nbrPersons--;
            personsList.removeLast();
            notifyDataSetChanged();
            //notifyItemRemoved(nbrPersons++);
        }
    }





    class PersonsHolder extends RecyclerView.ViewHolder{
        public EditText personName;
        public EditText personLocation;
        public boolean gpsOn;
        public PersonsHolder(View itemView){
            super(itemView);
            personName=(EditText) itemView.findViewById(R.id.rv_person_element_name);
            personLocation=(EditText) itemView.findViewById(R.id.rv_person_element_location);
            gpsOn=false;
        }
        public void changeGps(){
            gpsOn=!gpsOn;

            //personLocation.getBackground().setColorFilter(Color.parseColor("#006600"), PorterDuff.Mode.SRC_IN);

            notifyDataSetChanged();
        }

        public void setLocation(String s){
            this.personLocation.setText(s);
        }
        public String getLocationAsString(){
            return String.valueOf(personLocation.getText());
        }
    }
}

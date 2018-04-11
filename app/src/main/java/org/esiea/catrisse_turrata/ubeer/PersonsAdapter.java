package org.esiea.catrisse_turrata.ubeer;

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
        holder.personName.setHint("Personne"+""+personsList.get(position).toString());
        holder.personLocation.setHint(R.string.address);
        boolean focusable;
        String name;
        String location;



        if(holder.gpsOn){
            focusable=false;
            name="#Me";
            location="#MyLocation";

        }else if(holder.exemple==1){
            focusable=true;
            name="Arthur";
            location="20 rue Emile Zola Nogent sur Marne";
            }
        else if(holder.exemple==2){
            focusable=true;
            name="Augustin";
            location="Paris";
        }
        else{
            focusable=true;
            name="";
            location="";
        }
        holder.personName.setFocusableInTouchMode(focusable);
        holder.personName.setText(name);

        //holder.personLocation.setBackgroundColor(Color.parseColor("#fff9e6"));  //R.color.colorPrimaryLight);
        holder.personLocation.setFocusableInTouchMode(focusable);
        holder.personLocation.setText(location);



    }

    @Override
    public int getItemCount() {
        return personsList.size();
    }

    public void addPerson(){
        Log.w("a",nbrPersons+" aaa");
        if(nbrPersons<6) {
            Log.w("a",nbrPersons+" bbb");
            nbrPersons++;
            personsList.add(""+ nbrPersons);
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
        public int exemple;
        public PersonsHolder(View itemView){
            super(itemView);
            personName=(EditText) itemView.findViewById(R.id.rv_person_element_name);
            personLocation=(EditText) itemView.findViewById(R.id.rv_person_element_location);
            gpsOn=false;
            exemple=0;
        }
        public void changeGps(){
            gpsOn=!gpsOn;
            //personLocation.getBackground().setColorFilter(Color.parseColor("#006600"), PorterDuff.Mode.SRC_IN);
            notifyDataSetChanged();
        }

        public void setExemple(int i){
            this.exemple=i;
            notifyDataSetChanged();
        }
        public String getLocationAsString(){
            return String.valueOf(personLocation.getText());
        }
    }
}

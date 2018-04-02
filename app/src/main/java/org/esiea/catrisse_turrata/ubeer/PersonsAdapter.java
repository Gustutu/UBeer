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
        holder.personName.setHint(personsList.get(position).toString());
        holder.personLocation.setHint("address");

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
        public PersonsHolder(View itemView){
            super(itemView);
            personName=(EditText) itemView.findViewById(R.id.rv_person_element_name);
            personLocation=(EditText) itemView.findViewById(R.id.rv_person_element_location);
        }

        public void setLocation(String s){
            this.personLocation.setText(s);
        }
        public String getLocationAsString(){
            return String.valueOf(personLocation.getText());
        }
    }
}

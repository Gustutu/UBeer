package org.esiea.catrisse_turrata.ubeer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;


public class BarsAdapter extends RecyclerView.Adapter<BarsAdapter.BarsHolder>{



    private Bar barsArray[];
    public BarsAdapter(Bar barsArray[])
    {
        this.barsArray = barsArray;
    }

    @Override
    public BarsAdapter.BarsHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_bar_element, parent, false);

        return new BarsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BarsAdapter.BarsHolder holder, int position) {

            holder.barName.setText(barsArray[position].getName());
            holder.barRate.setText(Float.toString(barsArray[position].getRank()));
            holder.barIsOpen.setText(Boolean.toString(barsArray[position].getIsOpen()));
            //ajouter chapmps adress xml....ect
        
    }

    @Override
    public int getItemCount() {
        return barsArray.length;
    }

    public void setData(Bar data[]){
        this.barsArray = data;
        notifyDataSetChanged();
    }


    class BarsHolder extends RecyclerView.ViewHolder{
        public TextView barName;
        public TextView barRate;
        public TextView barIsOpen;
        public BarsHolder(View itemView){
            super(itemView);
            barName=(TextView) itemView.findViewById(R.id.rv_bar_element_name);
            barIsOpen=(TextView) itemView.findViewById(R.id.rv_bar_element_isOpen);
            barRate=(TextView) itemView.findViewById(R.id.rv_bar_element_rate);

        }
    }
}

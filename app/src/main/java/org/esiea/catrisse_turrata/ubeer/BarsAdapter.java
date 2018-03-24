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



    private JSONObject barsArray;
    public BarsAdapter(JSONObject barsArray)
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
        /*try {
            String actualBar = barsArray.getJSONObject(position).getString("name");
            holder.barName.setText(actualBar);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

    }

    @Override
    public int getItemCount() {
        return barsArray.length();
    }

    public void setData(JSONObject data){
        this.barsArray = data;
        notifyDataSetChanged();
    }


    class BarsHolder extends RecyclerView.ViewHolder{
        public TextView barName;
        public BarsHolder(View itemView){
            super(itemView);
            barName=(TextView) itemView.findViewById(R.id.rv_bar_element_location);

        }
    }
}

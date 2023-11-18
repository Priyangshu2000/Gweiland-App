package com.example.gweiland.Adapters;



import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gweiland.Constants.Constant;
import com.example.gweiland.Models.CoinDetails;
import com.example.gweiland.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class cryptocoinsAdapter extends RecyclerView.Adapter<cryptocoinsAdapter.MyViewHolder> {

    ArrayList<CoinDetails>coins;
    Context context;

    public cryptocoinsAdapter(ArrayList<CoinDetails> coins,Context context) {

        super();

        this.coins = coins;
        this.context=context;
    }

    @NonNull
    @Override
    public cryptocoinsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.single_coin,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull cryptocoinsAdapter.MyViewHolder holder, int position) {

        CoinDetails details=coins.get(position);
        holder.coinName.setText(details.getCoinName());
        holder.coinAbbreviation.setText(details.getCoinAbbreviation());

        NumberFormat numberFormat=NumberFormat.getInstance(Locale.US);
        String price="$"+numberFormat.format(Double.valueOf(details.getCoinPrice()))+" USD";

        holder.coinPrice.setText(price);

        Picasso.get().load(details.getCoinIcon()).into(holder.coinIcon);



        if(Double.parseDouble(details.getCoinDelta())<0){
            holder.coinGraph.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.negative));
            holder.coinDelta.setTextColor(context.getColor(R.color.negative));
            String delta=details.getCoinDelta()+"%";
            holder.coinDelta.setText(delta);
        }else{
            String delta="+"+details.getCoinDelta()+"%";
            holder.coinDelta.setText(delta);
            holder.coinGraph.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.positive));
            holder.coinDelta.setTextColor(context.getColor(R.color.positive));
        }
    }

    @Override
    public int getItemCount() {

        return coins.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView coinIcon,coinGraph;
        TextView coinAbbreviation,coinName,coinPrice,coinDelta;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            coinIcon=itemView.findViewById(R.id.coin_icon);
            coinGraph=itemView.findViewById(R.id.coin_graph);
            coinAbbreviation=itemView.findViewById(R.id.coin_abb);
            coinName=itemView.findViewById(R.id.coin_name);
            coinPrice=itemView.findViewById(R.id.coin_price);
            coinDelta=itemView.findViewById(R.id.coin_delta);
        }
    }

}

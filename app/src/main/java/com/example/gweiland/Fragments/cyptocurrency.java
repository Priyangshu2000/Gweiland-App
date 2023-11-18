package com.example.gweiland.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gweiland.Adapters.cryptocoinsAdapter;
import com.example.gweiland.Constants.Constant;
import com.example.gweiland.Models.CoinDetails;
import com.example.gweiland.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import com.android.volley.Response;
import com.squareup.picasso.Picasso;

public class cyptocurrency extends Fragment {

    cryptocoinsAdapter adapter;

    RecyclerView recyclerView;

    ArrayList<CoinDetails> coins;


    TextView btcPrice,btcDelta;
    ImageView btcGraph,btcIcon;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        String message = bundle.getString("Tag");


        View view = inflater.inflate(R.layout.fragment_cyptocurrency, container, false);
        recyclerView = view.findViewById(R.id.crypto_recycler);

        btcPrice=view.findViewById(R.id.btc_price);
        btcDelta=view.findViewById(R.id.btc_delta);
        btcGraph=view.findViewById(R.id.btc_graph);
        btcIcon=view.findViewById(R.id.btc_icon);




        coins = new ArrayList<>();
        fetchCoins(message);

        return view;
    }

    private void fetchCoins(String tag) {

        if(!tag.isEmpty()){
            tag="&sort="+tag;
        }

        String url = Constant.COIN_URL + "?CMC_PRO_API_KEY=" + Constant.API_KEY + "&limit=20"+tag;

        getData(url);

    }


    private void getData(String url) {

        RequestQueue mRequestQueue;
        StringRequest mStringRequest;
        mRequestQueue = Volley.newRequestQueue(getContext());

        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    addDataToList(response.toString());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        mRequestQueue.add(mStringRequest);
    }

    private void formatCoin(CoinDetails coin, String data) throws JSONException {
        JSONObject object = (JSONObject) new JSONObject(data).get("data");
        JSONArray jsonArray = object.getJSONArray(coin.getCoinAbbreviation());
        JSONObject zerothObject = (JSONObject) jsonArray.get(0);
        String logo = zerothObject.getString("logo");

        coin.setCoinIcon(logo);
        if(coin.getCoinAbbreviation().equals("BTC")){

            NumberFormat numberFormat=NumberFormat.getInstance(Locale.US);
            String price="$"+numberFormat.format(Double.valueOf(coin.getCoinPrice()))+" USD";

            btcPrice.setText(price);


            if(Double.parseDouble(coin.getCoinDelta())<0){
                String delta=coin.getCoinDelta()+"%";
                btcDelta.setText(delta);
                btcDelta.setTextColor(getContext().getColor(R.color.negative));
                btcGraph.setColorFilter(R.color.negative);
            }else{
                String delta="+"+coin.getCoinDelta()+"%";
                btcDelta.setText(delta);
            }
            Picasso.get().load(coin.getCoinIcon()).into(btcIcon);
        }
    }


    @SuppressLint("DefaultLocale")
    private void addDataToList(String data) throws JSONException {

        JSONObject object = new JSONObject(data);

        JSONArray coinData = object.getJSONArray("data");


        for (int i = 0; i < coinData.length(); i++) {

            JSONObject jsonObject = (JSONObject) coinData.get(i);

            String name = jsonObject.getString("name");
            String symbol = jsonObject.getString("symbol");


            JSONObject quote = (JSONObject) jsonObject.get("quote");


            JSONObject priceObject = (JSONObject) quote.get("USD");


            String price = priceObject.getString("price");

            Double dPrice = Double.valueOf(price);

            if (dPrice > 100) {
                price = String.valueOf(dPrice.intValue());
            } else {
                price = String.format("%.2f", dPrice);
            }


            String priceChange = priceObject.getString("percent_change_24h");
            Double delta = Double.valueOf(priceChange);

            priceChange = String.format("%.1f", delta);

            CoinDetails coin = new CoinDetails("nothing", symbol, name, price, priceChange);
            coins.add(coin);
        }

        getIcon(0);
        loadRecycler();


    }

    private void getIcon(int position) {
        String url = Constant.ICON_URL + "?CMC_PRO_API_KEY=" + Constant.API_KEY + "&symbol=" + coins.get(position).getCoinAbbreviation();

        RequestQueue mRequestQueue;
        StringRequest mStringRequest;
        mRequestQueue = Volley.newRequestQueue(getContext());

        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    formatCoin(coins.get(position),response.toString());
                    if(position<19){
                    getIcon(position+1);}
                    else{
//                        loadRecycler();
                    }
                } catch (Exception e) {
//                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        mRequestQueue.add(mStringRequest);

    }

    private void loadRecycler() {


        adapter = new cryptocoinsAdapter(coins, getContext());
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false) {

            @Override
            public boolean canScrollVertically() {
                return true;
            }

        };
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
        adapter.notifyDataSetChanged();
    }
}
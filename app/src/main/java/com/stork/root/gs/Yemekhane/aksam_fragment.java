package com.stork.root.gs.Yemekhane;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.stork.root.gs.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 16.03.2017.
 */

public class aksam_fragment extends Fragment {

    TextView yemek1, yemek2,yemek3,yemek4,yemek5,yemek6,yemek7,yemek8,yemek9,aksam;
    String gelengun, gelengun1;
    gün gun = new gün();
    Context mContext;


    Handler h = new Handler();
    int delay = 300;
    Runnable runnable;

    @Override
    public void onResume() {
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                gelengun = gun.getYenigün();
                if (!gelengun.equals(gelengun1)){
                    loadData();
                    gelengun1 = gelengun;
                    Log.d("Myapp", gelengun);
                }
                runnable = this;
                h.postDelayed(runnable, delay);
            }
        }, delay);
        super.onResume();
    }

    @Override
    public void onPause() {
        h.removeCallbacks(runnable);
        super.onPause();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_aksam,container,false);
        mContext = container.getContext();


        aksam = (TextView) viewGroup.findViewById(R.id.tvOgunYemekhane);
        yemek1= (TextView) viewGroup.findViewById(R.id.tvYemekhane1);
        yemek2= (TextView) viewGroup.findViewById(R.id.tvYemekhane2);
        yemek3= (TextView) viewGroup.findViewById(R.id.tvYemekhane3);
        yemek4= (TextView) viewGroup.findViewById(R.id.tvYemekhane4);
        yemek5= (TextView) viewGroup.findViewById(R.id.tvYemekhane5);
        yemek6= (TextView) viewGroup.findViewById(R.id.tvYemekhane6);
        //yemek7= (TextView) viewGroup.findViewById(R.id.tvYemekhane7);
        //yemek8= (TextView) viewGroup.findViewById(R.id.tvYemekhane8);
        yemek9= (TextView) viewGroup.findViewById(R.id.tvYemekhane9);


        gelengun = gun.getYenigün();
        gelengun1 = gun.getYenigün();
        Typeface type = Typeface.createFromAsset(getActivity().getAssets(),"fonts/font2.ttf");
        aksam.setTypeface(type);
        yemek1.setTypeface(type);
        yemek2.setTypeface(type);
        yemek3.setTypeface(type);
        yemek4.setTypeface(type);
        yemek5.setTypeface(type);
        yemek6.setTypeface(type);
       // yemek7.setTypeface(type);
       // yemek8.setTypeface(type);
        yemek9.setTypeface(type);

        loadData();

        return viewGroup;
    }

    public void loadData(){

        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url ="http://www.gsuapp.com/yemekhane_aksam.php";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            Log.d("Myapp", "Response: " + response);
                            List<menu> data = new ArrayList<>();

                            try {
                                JSONArray jArray = new JSONArray(response);
                                for (int i = 0; i < jArray.length(); i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    menu menu = new menu();
                                    menu.a1 = json_data.getString("a1");
                                    menu.a2 = json_data.getString("a2");
                                    menu.a3 = json_data.getString("a3");
                                    menu.a4 = json_data.getString("a4");
                                    menu.a5 = json_data.getString("a5");
                                    menu.a6 = json_data.getString("a6");
                                    //                      menu.a7 = json_data.getString("a7");
                                    //                      menu.a8 = json_data.getString("a8");
                                    menu.asalata = json_data.getString("asalata");
                                    menu.pid = json_data.getInt("pid");
                                    data.add(menu);
                                }

                                yemek1.setText(data.get(0).a1);
                                yemek2.setText(data.get(0).a2);
                                yemek3.setText(data.get(0).a3);
                                yemek4.setText(data.get(0).a4);
                                yemek5.setText(data.get(0).a5);
                                yemek6.setText(data.get(0).a6);
                                //                 yemek7.setText(data.get(0).a7);
                                //                 yemek8.setText(data.get(0).a8);
                                yemek9.setText(data.get(0).asalata);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else{
                            Log.d("Myapp", "Response Gelmedi");
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error!=null){
                    error.printStackTrace();
                }
            }
        }){

            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("pid", gelengun);
                return params;
            }
        };



        queue.add(stringRequest);

    }

}

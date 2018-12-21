package com.stork.root.gs.Duyurular;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.stork.root.gs.MainActivity;
import com.stork.root.gs.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DuyurularActivity extends AppCompatActivity {

    final String TAG = "DuyurularActivity";
    RecyclerView rvItem;
    List<Duyurular> data=new ArrayList<>();
    DuyurularAdapter duyurularAdapter;
    LinearLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duyurular);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);




        rvItem = (RecyclerView) findViewById(R.id.rvItem);
        rvItem.setHasFixedSize(true);

        manager = new LinearLayoutManager(this);


        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://feeder.gsu.edu.tr/api/getjsonnews";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jArray = new JSONArray(response);
                            for (int i = 0; i < jArray.length(); i++) {
                                JSONObject json_data = jArray.getJSONObject(i);
                                Duyurular duyurular = new Duyurular();
                                duyurular.Baslik = json_data.getString("Baslik");
                                duyurular.Icerik = json_data.getString("Icerik");
                                duyurular.date = json_data.getString("YayinTarihi");
                                duyurular.YayinYeri = json_data.getString("YayinYeri");
                                duyurular.Icon = json_data.getString("Icon");
                                JSONArray linksJson = json_data.getJSONArray("Linkler");
                                for(int k=0;k<linksJson.length();k++){

                                    JSONObject jsonLinks=linksJson.getJSONObject(k);
                                    if(duyurular.Linkler == ""){
                                        duyurular.Linkler += jsonLinks.getString("Href");
                                        duyurular.LinkTexts += jsonLinks.getString("Text");
                                    }else{
                                        duyurular.Linkler += "<new>" + jsonLinks.getString("Href");
                                        duyurular.LinkTexts += "<new>" + jsonLinks.getString("Text");
                                    }
                                }
                                JSONArray ImagesJson = json_data.getJSONArray("Images");
                                for(int k=0;k<ImagesJson.length();k++){

                                    JSONObject jsonImages=ImagesJson.getJSONObject(k);
                                    if(duyurular.Images == ""){
                                        duyurular.Images += jsonImages.getString("ImgHref");
                                    }else{
                                        duyurular.Images += "<new>" + jsonImages.getString("ImgHref");
                                    }
                                }
                                data.add(duyurular);
                            }
                            duyurularAdapter = new DuyurularAdapter(getApplicationContext(),data);
                            rvItem.setAdapter(duyurularAdapter);
                            rvItem.setLayoutManager(manager);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error!=null){
                    error.printStackTrace();
                }
            }
        }) {

        };
        queue.add(stringRequest);



    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                Intent intent = new Intent(DuyurularActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

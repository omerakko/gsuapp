package com.stork.root.gs.Etkinlikler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.stork.root.gs.MainActivity;
import com.stork.root.gs.MySingleton;
import com.stork.root.gs.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EtkinliklerActivity extends AppCompatActivity {


    final String TAG = "EtkinliklerActivity";
    RecyclerView rvItem;
    List<Etkinlikler> data=new ArrayList<>();
    LinearLayoutManager manager;
    EtkinlikAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etkinlikler);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);




        rvItem = (RecyclerView) findViewById(R.id.rvItem);
        rvItem.setHasFixedSize(true);
        manager = new LinearLayoutManager(this);


        String url = "http://www.gsuapp.com/etkinlikler.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        /*
                        ArrayList<Etkinlikler> etkinliklerList = new JsonConverter<Etkinlikler>().toArrayList(response, Etkinlikler.class);
                        */

                        try {
                            JSONArray jArray = new JSONArray(response);
                            for (int i = 0; i < jArray.length(); i++) {
                                JSONObject json_data = jArray.getJSONObject(i);
                                Etkinlikler etkinlikler = new Etkinlikler();
                                etkinlikler.etkinlikadi = json_data.getString("etkinlikadi");
                                etkinlikler.etkinlikaciklama = json_data.getString("etkinlikaciklama");
                                etkinlikler.etkinliktarihi = json_data.getString("etkinliktarihi");
                                etkinlikler.etkinlikBitTarihi = json_data.getString("etkinlikBitTarihi");
                                etkinlikler.etkinlikyeri = json_data.getString("etkinlikyeri");
                                etkinlikler.iconurl = json_data.getString("iconurl");
                                etkinlikler.kulupadi = json_data.getString("kulupadi");
                                etkinlikler.Linkler = json_data.getString("Linkler");
                                etkinlikler.LinkTexts = json_data.getString("LinkTexts");
                                etkinlikler.pid = Integer.parseInt(json_data.getString("pid"));

                                data.add(etkinlikler);
                            }
                            adapter = new EtkinlikAdapter(getApplicationContext(), data);
                            rvItem.setAdapter(adapter);
                            rvItem.setLayoutManager(manager);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(error != null){
                            Log.d(TAG, error.toString());
                            Toast.makeText(getApplicationContext(), "Internet Baglantısı Yok", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);



    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                Intent intent = new Intent(EtkinliklerActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



    public void OnClick(View v){
        AlertDialog alertDialog = new AlertDialog.Builder(EtkinliklerActivity.this).create();
        alertDialog.setTitle("Yeni Etkinlik");
        SpannableString ss = new SpannableString("Yeni Etkinlik Eklemek İçin info@gsuapp.com Adresine Mail Atmanız Yeterli.");
        ss.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {
            }
        }, 27, 42, 0);
        alertDialog.setMessage(ss);

        alertDialog.show();
    }
}

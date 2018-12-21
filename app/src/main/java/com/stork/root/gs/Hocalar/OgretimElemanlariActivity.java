package com.stork.root.gs.Hocalar;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.stork.root.gs.MainActivity;
import com.stork.root.gs.MySingleton;
import com.stork.root.gs.R;
import com.kosalgeek.android.json.JsonConverter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class OgretimElemanlariActivity extends AppCompatActivity {

    final String TAG = "OgretimElemanlariActivity";

    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    private RecyclerView mRVhocalar;
    private HocalarAdapter mAdapter;

    SearchView searchView = null;
    TextView t1,t2;
    RecyclerView rvItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogretim_elemanlari);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



//SearchView ekranında tüm hocaların görünmesi için


        rvItem = (RecyclerView)findViewById(R.id.hocalarrecyclerview);
        rvItem.setHasFixedSize(true);

        LinearLayoutManager manager = new LinearLayoutManager(this);

        rvItem.setLayoutManager(manager);


        String url = "http://www.gsuapp.com/hocalar.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response);

                        ArrayList<Hocalar> HocalarList = new JsonConverter<Hocalar>().toArrayList(response, Hocalar.class);

                        HocalarAdapter adapter = new HocalarAdapter(getApplicationContext(), HocalarList);
                        rvItem.setAdapter(adapter);

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
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat
                .getActionView(searchItem);

        TextView searchText = (TextView) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchText.setTextColor(Color.WHITE);
        ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text)).setHintTextColor(Color.WHITE);


        SearchManager searchManager = (SearchManager) OgretimElemanlariActivity.this.getSystemService(Context.SEARCH_SERVICE);
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(OgretimElemanlariActivity.this.getComponentName()));
            searchView.setIconified(true);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

            switch (item.getItemId()) {
                case android.R.id.home:
                    // todo: goto back activity from here

                    Intent intent = new Intent(OgretimElemanlariActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                    return true;

                default:
                    return super.onOptionsItemSelected(item);
            }

    }

    @Override
    protected void onNewIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            if (searchView != null) {
                searchView.clearFocus();
            }
            new AsyncFetch(query).execute();

        }
    }

    private class AsyncFetch extends AsyncTask<String, String, String> {

        ProgressDialog pdLoading = new ProgressDialog(OgretimElemanlariActivity.this);
        HttpURLConnection conn;
        URL url = null;
        String searchQuery;

        public AsyncFetch(String searchQuery){
            this.searchQuery=searchQuery;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pdLoading.setMessage("\tLütfen Bekleyiniz...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                url = new URL("http://www.gsuapp.com/search.php");

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return e.toString();
            }
            try {

                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");

                conn.setDoInput(true);
                conn.setDoOutput(true);

                Uri.Builder builder = new Uri.Builder().appendQueryParameter("Query", searchQuery);
                String query = builder.build().getEncodedQuery();

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return e1.toString();
            }

            try {

                int response_code = conn.getResponseCode();

                if (response_code == HttpURLConnection.HTTP_OK) {

                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    return (result.toString());

                } else {
                    return("Connection error");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                conn.disconnect();
            }


        }

        @Override
        protected void onPostExecute(String result) {

            pdLoading.dismiss();
            List<Hocalar> data=new ArrayList<>();

            pdLoading.dismiss();
            if(result.equals("yok")) {
                Toast.makeText(OgretimElemanlariActivity.this, "Yok", Toast.LENGTH_LONG).show();
            }else{

                try {

                    JSONArray jArray = new JSONArray(result);
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json_data = jArray.getJSONObject(i);
                        Hocalar hocalar = new Hocalar();
                        hocalar.adsoyad = json_data.getString("adsoyad");
                        hocalar.odanu = json_data.getString("odanu");
                        hocalar.email = json_data.getString("email");
                        hocalar.telefonnu = json_data.getString("telefonnu");
                        hocalar.bolum = json_data.getString("bolum");
                        hocalar.hocaImg = json_data.getString("hocaImg");
                        hocalar.pid = json_data.getInt("pid");
                        data.add(hocalar);


                    }

                    mRVhocalar = (RecyclerView) findViewById(R.id.hocalarrecyclerview);
                    mAdapter = new HocalarAdapter(OgretimElemanlariActivity.this, data);
                    mRVhocalar.setAdapter(mAdapter);
                    mRVhocalar.setLayoutManager(new LinearLayoutManager(OgretimElemanlariActivity.this));

                } catch (JSONException e) {
                    Toast.makeText(OgretimElemanlariActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                    Toast.makeText(OgretimElemanlariActivity.this, result.toString(), Toast.LENGTH_LONG).show();
                }

            }

        }

    }


}

package com.stork.root.gs.Etkinlikler;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.stork.root.gs.MySingleton;
import com.stork.root.gs.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EtkinlikAddActivity extends AppCompatActivity implements View.OnClickListener {


    EditText kadi,etarih,eadi,eaciklama;
    String skadi,setarih,seadi,seaciklama;
    AlertDialog.Builder builder;
    String url ="https://gsuapp.000webhostapp.com/etkinlikAdd.php";
    Button ResimSec, EtkinlikEkle;
    private final int IMG_REQUEST = 1;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etkinlik_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);




        kadi = (EditText) findViewById(R.id.etkulupadi);
        etarih = (EditText) findViewById(R.id.ettarih);
        eadi = (EditText) findViewById(R.id.etetkinlikbaslıgı);
        eaciklama = (EditText) findViewById(R.id.etaciklama);
        builder = new AlertDialog.Builder(EtkinlikAddActivity.this);
        ResimSec = (Button) findViewById(R.id.afissec);
        EtkinlikEkle = (Button) findViewById(R.id.etkinlikekle);
        ResimSec.setOnClickListener(this);
        EtkinlikEkle.setOnClickListener(this);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                Intent intent = new Intent(EtkinlikAddActivity.this, EtkinliklerActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.afissec:
                    ResimSec();
                break;

            case R.id.etkinlikekle:
                upLoadImg();
                break;
        }
    }

    private void upLoadImg() {

        skadi = kadi.getText().toString();
        setarih = etarih.getText().toString();
        seadi = eadi.getText().toString();
        seaciklama = eaciklama.getText().toString();


        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());


        if (skadi.equals("") || setarih.equals("") || seadi.equals("") || seaciklama.equals("")) {
            builder.setTitle("Bir şeyler yanlış gidiyor");
            builder.setMessage("Lüffen Tüm Alanları Doldurur Musun ?");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            builder.setTitle("Etkinlik Başvurusu");
                            builder.setMessage("Etkinlik Başvurunuz Alınmıştır. Tarafımızca İncelendikten Sonra Yayınlanacaktır.");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    kadi.setText("");
                                    eadi.setText("");
                                    etarih.setText("");
                                    eaciklama.setText("");
                                }
                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        }
                    }

                    , new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("kulupadi", skadi);
                    params.put("etkinlikbasligi ", seadi);
                    params.put("etkinliktarihi", setarih);
                    params.put("etkinlikaciklama", seaciklama);
                    params.put("afisurl", seadi);
                    params.put("image", imageToString(bitmap));

                    return params;
                }
            };


            MySingleton.getInstance(EtkinlikAddActivity.this).addToRequestQueue(stringRequest);

        }
    }


    public void ResimSec(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMG_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==IMG_REQUEST && requestCode==RESULT_OK && data!=null){
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                EtkinlikEkle.setVisibility(View.VISIBLE);
                ResimSec.setVisibility(View.GONE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String imageToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes,Base64.DEFAULT);
    }


/*
    public void OnclickSec(View v){

    }

    public void OnclickAdd(View v){


        skadi = kadi.getText().toString();
        setarih = etarih.getText().toString();
        seadi = eadi.getText().toString();
        seaciklama = eaciklama.getText().toString();


        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());



        if(skadi.equals("")||setarih.equals("")||seadi.equals("")||seaciklama.equals("")){
            builder.setTitle("Bir şeyler yanlış gidiyor");
            builder.setMessage("Lüffen Tüm Alanları Doldurur Musun ?");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                    }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

        else{

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            builder.setTitle("Etkinlik Başvurusu");
                            builder.setMessage("Etkinlik Başvurunuz Alınmıştır. Tarafımızca İncelendikten Sonra Yayınlanacaktır.");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    kadi.setText("");
                                    eadi.setText("");
                                    etarih.setText("");
                                    eaciklama.setText("");
                                }
                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        }
                    }

                    , new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("kulupadi",skadi);
                    params.put("etkinlikbasligi ",seadi);
                    params.put("etkinliktarihi",setarih);
                    params.put("etkinlikaciklama",seaciklama);
                    params.put("afisurl",seadi);

                    return params;
                }
            };


            MySingleton.getInstance(EtkinlikAddActivity.this).addToRequestQueue(stringRequest);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("kulupadi",skadi);
                    params.put("etkinlikbasligi ",seadi);
                    params.put("etkinliktarihi",setarih);
                    params.put("etkinlikaciklama",seaciklama);
                    params.put("afisurl",seadi);

                    return params;
                }
            };

            queue.add(stringRequest);
            */









}


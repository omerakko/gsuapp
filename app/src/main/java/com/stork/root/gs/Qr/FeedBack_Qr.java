package com.stork.root.gs.Qr;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;

import java.util.Calendar;
import java.util.Date;

import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.stork.root.gs.R;

import android.provider.Settings.System;

public class FeedBack_Qr extends AppCompatActivity {
    ImageView imageview;
    EditText feedBackadAdSoyad, feedBackOgrenciNo;
    TelephonyManager tm;
    String IMEI;
    String register_url = "http://www.gsuapp.com/feedback.php";
    String androidID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);

        androidID=System.getString(this.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        Typeface font = Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/font.ttf");
        Typeface type = Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/font3.ttf");
        Typeface type1 = Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/font2.ttf");

        TextView adSoyadTextView = (TextView) findViewById(R.id.text_AdSoyad);
        TextView ogrencino = (TextView) findViewById(R.id.text_ogrno);

        feedBackadAdSoyad = (EditText) findViewById(R.id.feedback_AdSoyad);
        feedBackadAdSoyad.setTypeface(type1);
        feedBackOgrenciNo = (EditText) findViewById(R.id.feedback_ogrno);
        feedBackOgrenciNo.setTypeface(type1);
        adSoyadTextView.setTypeface(font);
        imageview= (ImageView) findViewById(R.id.imageview);

        ogrencino.setTypeface(font);



        Button FeedBackButton = (Button) findViewById(R.id.btn_FeedBackSend);
        FeedBackButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                String result = feedBackOgrenciNo.getText().toString();
                Integer a;
                a=result.length();
                if (feedBackadAdSoyad.getText().toString().equals("") || feedBackOgrenciNo.getText().toString().equals("")){
                    Toast.makeText(FeedBack_Qr.this,"Lütfen Bütün Alanları Doldurunuz ?",Toast.LENGTH_LONG).show();
                }else if (a != 8){
                    Toast.makeText(FeedBack_Qr.this,"Lütfen Geçerli Bir Ogrenci Numarası  Giriniz",Toast.LENGTH_LONG).show();
                }else{
                    registerFeedback();
                }
            }
        });

        FeedBackButton.setTypeface(type);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                Intent intent = new Intent(FeedBack_Qr.this, Qr_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @RequiresApi(api = Build.VERSION_CODES.M)
    private void registerFeedback(){
        String text= feedBackadAdSoyad.getText().toString().trim();
        String text2 = feedBackOgrenciNo.getText().toString().trim();
        Date currentTime = Calendar.getInstance().getTime();
        String final1= text+"-"+text2+"-"+currentTime+"-"+androidID ;
        if(final1!=null ){
            MultiFormatWriter multiFormatWriter1=new MultiFormatWriter();
            try {
                BitMatrix bitmatrix = multiFormatWriter1.encode(final1, BarcodeFormat.QR_CODE,100,100);
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.createBitmap(bitmatrix);

                imageview.setImageBitmap(bitmap);


            }

            catch (WriterException e){
                e.printStackTrace();
            }

        }

    }

}


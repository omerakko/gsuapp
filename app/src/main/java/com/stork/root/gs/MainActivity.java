package com.stork.root.gs;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.stork.root.gs.Duyurular.DuyurularActivity;
import com.stork.root.gs.Etkinlikler.EtkinliklerActivity;
import com.stork.root.gs.Qr.Qr_Activity;
import com.stork.root.gs.Hocalar.OgretimElemanlariActivity;
import com.stork.root.gs.Yemekhane.YemekhaneActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button yemekhane,duyurular,ogretimElemanları,bizKimiz,kikencere,etkinlikler;
    TextView tvToolbarTitle, tvToolbarTitle2;
    private FirebaseAnalytics firebaseAnalytics;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Typeface type = Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/font3.ttf");

        firebaseAnalytics = FirebaseAnalytics.getInstance(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        tvToolbarTitle = (TextView)findViewById(R.id.toolbar_title);
        tvToolbarTitle2 = (TextView)findViewById(R.id.toolbar_title1);
        //tvToolbarTitle.setTypeface(type);
        //tvToolbarTitle2.setTypeface(type);

        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);

        yemekhane = (Button)findViewById(R.id.yemekhaneBtn);
        duyurular = (Button)findViewById(R.id.duyurularBtn);
        ogretimElemanları = (Button)findViewById(R.id.OgretimElemanlariBtn);
        bizKimiz = (Button)findViewById(R.id.menu_hakkimizda);
        kikencere = (Button)findViewById(R.id.menu_kikencere);
        etkinlikler = (Button)findViewById(R.id.etkinliklerBtn);

        yemekhane.setOnClickListener(this);
        duyurular.setOnClickListener(this);
        ogretimElemanları.setOnClickListener(this);
        bizKimiz.setOnClickListener(this);
        kikencere.setOnClickListener(this);
        etkinlikler.setOnClickListener(this);

        yemekhane.setTypeface(type);
        duyurular.setTypeface(type);
        ogretimElemanları.setTypeface(type);
        bizKimiz.setTypeface(type);
        kikencere.setTypeface(type);
        etkinlikler.setTypeface(type);
    }
    /*

    public void OnClick(View v){
        Intent intent = new Intent(getApplicationContext(),DuyurularActivity.class);
        startActivity(intent);
    }


    public void OnClickOgretimElemanlari(View v){
        Intent intent = new Intent(getApplicationContext(),OgretimElemanlariActivity.class);
        startActivity(intent);
    }


    public void OnClickYemekhane(View v){
        Intent intent = new Intent(getApplicationContext(),YemekhaneActivity.class);
        startActivity(intent);
    }

    public void OnClickEtkinlikler(View v){
        Intent intent = new Intent(getApplicationContext(),EtkinliklerActivity.class);
        startActivity(intent);
    }

    public void HakkımızdaButton(View v){
        Intent intent = new Intent(getApplicationContext(),HakkimizdaActivity.class);
        startActivity(intent);
    }


    public void Kikencere(View v){

        Uri uriUrl = Uri.parse("http://uni.gsu.edu.tr/index.php/fr/");
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }*/


    @Override
    public void onClick(View view) {
        Bundle params = new Bundle();
        params.putInt("ButtonId", view.getId());
        String btnName = null;
        switch (view.getId()){
            case R.id.yemekhaneBtn:
                Intent intent = new Intent(getApplicationContext(),YemekhaneActivity.class);
                startActivity(intent);
                btnName = "Yemekhane";
                break;
            case R.id.duyurularBtn:
                Intent intent1 = new Intent(getApplicationContext(),DuyurularActivity.class);
                startActivity(intent1);
                btnName = "Duyurular";
                break;
            case R.id.OgretimElemanlariBtn:
                Intent intent2 = new Intent(getApplicationContext(),OgretimElemanlariActivity.class);
                startActivity(intent2);
                btnName = "Ogretim Elemanları";
                break;
            case R.id.menu_hakkimizda:
                Intent intent3 = new Intent(getApplicationContext(),Qr_Activity.class);
                startActivity(intent3);
                btnName = "Qr Kod";
                break;
            case R.id.menu_kikencere:
                Uri uriUrl = Uri.parse("http://uni.gsu.edu.tr/index.php/fr/");
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(launchBrowser);
                btnName = "Kikencere";
                break;
            case R.id.etkinliklerBtn:
                Intent intent4 = new Intent(getApplicationContext(),EtkinliklerActivity.class);
                startActivity(intent4);
                btnName = "Etkinlikler";
                break;
        }
        firebaseAnalytics.logEvent(btnName,params);
    }
}




package com.stork.root.gs.Hocalar;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.stork.root.gs.R;
import com.squareup.picasso.Picasso;

public class OgretimElemaniDetayActivity extends AppCompatActivity {

    TextView adsoyad, email, bolum, odanu, telefonnu, emaill, bolumm, odanuu, telefonnuu;
    ImageView oeImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogretim_elemani_detay);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Typeface type = Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/font2.ttf");
        Typeface type1 = Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/font3.ttf");


        adsoyad = (TextView) findViewById(R.id.tvadsoyad);
        email = (TextView) findViewById(R.id.tvemail);
        emaill = (TextView) findViewById(R.id.tvemaill);
        bolum = (TextView) findViewById(R.id.tvbolum);
        bolumm = (TextView) findViewById(R.id.tvbolumm);
        odanu = (TextView) findViewById(R.id.tvodanu);
        odanuu = (TextView) findViewById(R.id.odanuu);
        telefonnu = (TextView) findViewById(R.id.tvtelefonnu);
        telefonnuu = (TextView) findViewById(R.id.tvtelefonnuu);
        email.setPaintFlags(email.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);


        adsoyad.setTypeface(type1);
        email.setTypeface(type);
        emaill.setTypeface(type);
        bolumm.setTypeface(type);
        bolum.setTypeface(type);
        odanu.setTypeface(type);
        odanuu.setTypeface(type);
        telefonnu.setTypeface(type);
        telefonnuu.setTypeface(type);



        oeImg = (ImageView) findViewById(R.id.oeImg);

        if (getIntent().getSerializableExtra("Detayogretimelemanı") != null){
            final Hocalar hoca = (Hocalar)getIntent().getSerializableExtra("Detayogretimelemanı");

            adsoyad.setText(hoca.adsoyad);
            bolum.setText(hoca.bolum);
            odanu.setText(hoca.odanu);
            telefonnu.setText(hoca.telefonnu);
            email.setText(hoca.email);

            String fullUrl = "http://www.gsuapp.com/" + hoca.hocaImg;

            Picasso.with(this)
                    .load(fullUrl)
                    .error(android.R.drawable.stat_notify_error)
                    .into(oeImg);

            email.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                            "mailto",hoca.email, null));
                    startActivity(Intent.createChooser(emailIntent, "Send email..."));
                }
            });
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                Intent intent = new Intent(OgretimElemaniDetayActivity.this, OgretimElemanlariActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

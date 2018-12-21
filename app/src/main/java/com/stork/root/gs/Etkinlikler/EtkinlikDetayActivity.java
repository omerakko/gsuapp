package com.stork.root.gs.Etkinlikler;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.stork.root.gs.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EtkinlikDetayActivity extends AppCompatActivity {

    TextView kulupadi, etkinlikadi, etkinliktarihi, etkinlikdetay;
    ImageView image;
    Button btnCalaneder;
    Etkinlikler etkinlikler;
    int etkinlikGunu, ayniGun, fark;
    String sYil, sGun, sAy, bitisYil, bitisAy, bitisGun;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etkinlik_detay);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        kulupadi = (TextView) findViewById(R.id.detaykulupadi);
        etkinlikadi = (TextView) findViewById(R.id.detayetkinlikbasligi);
        etkinliktarihi = (TextView) findViewById(R.id.detayetkinliktarihiyeri);
        etkinlikdetay = (TextView) findViewById(R.id.detayetkinlikdetayi);
        Typeface type1 = Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/font2.ttf");
        Typeface type = Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/font3.ttf");

        etkinlikdetay.setTypeface(type1);
        kulupadi.setTypeface(type);
        etkinlikadi.setTypeface(type);
        etkinliktarihi.setTypeface(type);

        btnCalaneder = (Button) findViewById(R.id.addCalendar);
        btnCalaneder.setTypeface(type);
        image = (ImageView) findViewById(R.id.IMGkuluplogo);

        if (getIntent().getSerializableExtra("Detayetkinlikler") != null){
            etkinlikler = (Etkinlikler) getIntent().getSerializableExtra("Detayetkinlikler");
            if (etkinlikler.etkinliktarihi.equals("")){
                btnCalaneder.setVisibility(View.INVISIBLE);
            }else{
                farkHesapla(etkinlikler.etkinliktarihi, etkinlikler.etkinlikBitTarihi);
            }

            String thereLinkTexts = etkinlikler.LinkTexts;
            if (thereLinkTexts != null) {

                SpannableString ss = new SpannableString((etkinlikler.etkinlikaciklama));
                String[] linkTextItem = etkinlikler.LinkTexts.split("<new>");
                final String[] linkHrefItem = etkinlikler.Linkler.split("<new>");
                int[] start = new int[linkTextItem.length];
                int[] end = new int[linkTextItem.length];

                for (int k = 0; k < linkTextItem.length; k++) {
                    start[k] = (etkinlikler.etkinlikaciklama).indexOf(linkTextItem[k]);
                    end[k] = start[k] + linkTextItem[k].length();
                }

                for (int k = 0; k < linkTextItem.length; k++) {
                    // end = start + item.length();
                    final int finalK = k;
                    final int finalK1 = k;
                    ss.setSpan(new ClickableSpan() {
                        @Override
                        public void onClick(View view) {
                            Uri uriUrl = Uri.parse(linkHrefItem[finalK1]);
                            Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                            startActivity(launchBrowser);
                        }
                    }, start[k], end[k], 0);

                }
                etkinlikdetay.setText(ss);
                etkinlikdetay.setMovementMethod(LinkMovementMethod.getInstance());
                etkinlikdetay.setHighlightColor(Color.TRANSPARENT);
            }else{
                etkinlikdetay.setText(etkinlikler.etkinlikaciklama);
            }

            kulupadi.setText(etkinlikler.kulupadi);
            etkinlikadi.setText(etkinlikler.etkinlikadi);
            String yer = etkinlikler.etkinlikyeri;
            String basTarihi = etkinlikler.etkinliktarihi;
            String bitTarihi = etkinlikler.etkinlikBitTarihi;
            if (yer.isEmpty() && basTarihi.equals(bitTarihi)){
                etkinliktarihi.setText(basTarihi);
            }else if (yer.isEmpty() && basTarihi != bitTarihi){
                etkinliktarihi.setText(basTarihi + "-" + bitTarihi);
            }else if (!yer.isEmpty() && basTarihi.equals(bitTarihi)){
                etkinliktarihi.setText(basTarihi + " || " + yer);
            }else if (!yer.isEmpty() && basTarihi != bitTarihi){
                etkinliktarihi.setText(basTarihi + " - " + bitTarihi + " || " + yer);
            }

            String fullUrl = "http://www.gsuapp.com/" + etkinlikler.iconurl;

            Picasso.with(this)
                    .load(fullUrl)
                    .error(android.R.drawable.stat_notify_error)
                    .into(image);

            btnCalaneder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Build.VERSION.SDK_INT >= 14) {
                        Calendar beginCal = Calendar.getInstance();
                        Calendar endCal = Calendar.getInstance();
                        beginCal.set(Integer.parseInt(sYil), Integer.parseInt(sAy) - 1, Integer.parseInt(sGun), 9, 0);
                        endCal.set(Integer.parseInt(bitisYil), Integer.parseInt(bitisAy) - 1, Integer.parseInt(bitisGun), 10, 0);

                        Intent intent = new Intent(Intent.ACTION_INSERT)
                                .setData(CalendarContract.Events.CONTENT_URI)
                                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginCal.getTimeInMillis())
                                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endCal.getTimeInMillis())
                                .putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true)
                                .putExtra(CalendarContract.Events.TITLE, etkinlikler.etkinlikadi)
                                .putExtra(CalendarContract.Events.EVENT_LOCATION, etkinlikler.etkinlikyeri)
                                .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
                        startActivity(intent);
                    }else {
                        Calendar beginCal = Calendar.getInstance();
                        Calendar endCal = Calendar.getInstance();
                        beginCal.set(Integer.parseInt(sYil), Integer.parseInt(sAy) - 1, Integer.parseInt(sGun), 9, 0);
                        endCal.set(Integer.parseInt(bitisYil), Integer.parseInt(bitisAy) - 1, Integer.parseInt(bitisGun), 10, 0);

                        Intent intent = new Intent(Intent.ACTION_EDIT);
                        intent.setType("vnd.android.cursor.item/event");
                        intent.putExtra("beginTime", beginCal.getTimeInMillis());
                        intent.putExtra("endTime", endCal.getTimeInMillis());
                        intent.putExtra("allDay", true);
                        intent.putExtra("rrule", "FREQ=YEARLY");
                        intent.putExtra("title", etkinlikler.etkinlikadi);
                        startActivity(intent);
                    }
                }
            });

        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                Intent intent = new Intent(EtkinlikDetayActivity.this, EtkinliklerActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void farkHesapla(String tarihBaslangic, String tarihBitis){
        String[] tarihBaslangicObject = tarihBaslangic.split("/");
        String[] tarihBitisObject = tarihBitis.split("/");
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy");
        DateFormat dateFormat1 = new SimpleDateFormat("dd");
        DateFormat dateFormat2 = new SimpleDateFormat("MM");

        sYil = dateFormat.format(today);
        sGun = dateFormat1.format(today);
        sAy =  dateFormat2.format(today);
        bitisAy = dateFormat2.format(today);
        bitisGun = dateFormat1.format(today);
        bitisYil = dateFormat.format(today);

        sYil = tarihBaslangicObject[2];
        sAy = tarihBaslangicObject[1];
        sGun = tarihBaslangicObject[0];

        bitisGun = tarihBitisObject[0];
        bitisAy = tarihBitisObject[1];
        bitisYil = tarihBitisObject[2];

    }

}

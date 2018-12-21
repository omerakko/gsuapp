package com.stork.root.gs.Yemekhane;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.eftimoff.viewpagertransformers.DrawFromBackTransformer;
import com.eftimoff.viewpagertransformers.ZoomInTransformer;
import com.stork.root.gs.MainActivity;
import com.stork.root.gs.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class YemekhaneActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    TextView tarihyemekler;

    static String sGun, sYil, sAy;

    private RecyclerView rvItem;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<String> mDataset;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yemekhane);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        int a = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
        mDataset = new ArrayList<>();
        for (int i = 1; i<=a; i++) {
            mDataset.add(String.valueOf(i));
        }



        gün gun = new gün();
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy");
        DateFormat dateFormat1 = new SimpleDateFormat("d");
        DateFormat dateFormat2 = new SimpleDateFormat("MM");

        sYil = dateFormat.format(today);
        sGun = dateFormat1.format(today);
        sAy =  dateFormat2.format(today);
        String zeroDay = "";
        if (Integer.parseInt(sGun) < 10){
            zeroDay = "0" + sGun;
        }else{
            zeroDay = sGun;
        }

        tarihyemekler = (TextView) findViewById(R.id.tarihyemekler);

        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/font3.ttf");
        tarihyemekler.setTypeface(type);

        tarihyemekler.setText(zeroDay + "/" + sAy + "/" + sYil);

        Log.d("MYAPP", "Deneme" + sGun);

        gun.gündegistir(sGun);

        rvItem = (RecyclerView) findViewById(R.id.rvItem);
        rvItem.setHasFixedSize(true);



        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvItem.setLayoutManager(layoutManager);
        mAdapter = new CalendarAdapter(this,mDataset);
        rvItem.setAdapter(mAdapter);


        viewPager = (ViewPager) findViewById(R.id.svItemYemekhane);
        pagerAdapter = new YemekhaneAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                Intent intent = new Intent(YemekhaneActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void OnClick(View v){
        if (rvItem.getVisibility() == View.GONE){
            rvItem.setVisibility(View.VISIBLE);
        }else{
            rvItem.setVisibility(View.GONE);
        }



    }


    public String getsYil() {
        return sYil;
    }

    public String getsAy() {
        return sAy;
    }

    public static String getsGun() {
        return sGun;
    }
}
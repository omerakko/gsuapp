package com.stork.root.gs.Duyurular;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.eftimoff.viewpagertransformers.DrawFromBackTransformer;
import com.eftimoff.viewpagertransformers.ParallaxPageTransformer;
import com.eftimoff.viewpagertransformers.RotateUpTransformer;
import com.eftimoff.viewpagertransformers.ZoomOutSlideTransformer;
import com.eftimoff.viewpagertransformers.ZoomOutTranformer;
import com.rd.PageIndicatorView;
import com.stork.root.gs.R;




public class DuyuruDetayActivity extends AppCompatActivity{

    TextView tvTitle, tvDuyuru, tvTarih;
    ImageView updownBtnImg;
    LinearLayout allImageLayout, llMarginli, imageShowSlider;
    ViewPager viewPager;
    ImageSliderViewPagerAdapter adapter;
    String [] urls;
    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    LinearLayout.LayoutParams lpp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duyuru_detay);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tvTitle = (TextView)findViewById(R.id.tvTitleDetay);
        tvDuyuru = (TextView)findViewById(R.id.tvDuyuruDetay);
        tvTarih = (TextView)findViewById(R.id.tvDateDetay);
        viewPager = (ViewPager) findViewById(R.id.imagesliderViewPager);



        int colorRed, colorYellow;
        colorRed = Color.parseColor("#b61117");
        colorYellow = Color.parseColor("#f49814");
        PageIndicatorView pageIndicatorView = (PageIndicatorView) findViewById(R.id.pageIndicatorView);
        pageIndicatorView.setViewPager(viewPager);
        pageIndicatorView.setDynamicCount(false);
        pageIndicatorView.setPadding(10);
        pageIndicatorView.setUnselectedColor(colorRed);
        pageIndicatorView.setSelectedColor(colorYellow);
        pageIndicatorView.setAnimationDuration(3000);





        if (getIntent().getSerializableExtra("Detayduyuru") != null){
            Duyurular duyuru = (Duyurular)getIntent().getSerializableExtra("Detayduyuru");

            SpannableString ss = new SpannableString(stripHtml(duyuru.Icerik));
            Log.d("DENEME", "DENEME123" + stripHtml(duyuru.Icerik));
            String[] linkTextItem = duyuru.LinkTexts.split("<new>");

            final String[] linkHrefItem = duyuru.Linkler.split("<new>");
            int[] start = new int[linkTextItem.length];
            int[] end = new int[linkTextItem.length];

            for(int k = 0;k<linkTextItem.length;k++){
                start[k] = stripHtml(duyuru.Icerik).indexOf(linkTextItem[k]);
                end[k] = start[k] + linkTextItem[k].length();
            }

            for (int k = 0; k<linkTextItem.length; k++) {
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


            urls = duyuru.Images.split("<new>");
            adapter = new ImageSliderViewPagerAdapter(this,urls);
            viewPager.setAdapter(adapter);
            //viewPager.setPageTransformer(true, new DrawFromBackTransformer());
            viewPager.setPageTransformer(true, new ZoomOutTranformer());
            allImageLayout = (LinearLayout)findViewById(R.id.allimageLayout);
            updownBtnImg = (ImageView)findViewById(R.id.updownBtnImg);
            llMarginli = (LinearLayout)findViewById(R.id.llMarginli);
            imageShowSlider = (LinearLayout) findViewById(R.id.imageShowSlider);
            lp.setMargins(0, 0, 0, 0);
            lp.setMargins(0, 0, 0, 30);
            int a = urls.length;
            Log.d("uzunluk", "uzunluk" + a);

            if(urls[0].equals("")){
                llMarginli.setVisibility(View.INVISIBLE);
                imageShowSlider.setVisibility(View.INVISIBLE);
            }




            tvTitle.setText(stripHtml(duyuru.Baslik));
            if (ss.length() < 1){
                tvDuyuru.setVisibility(View.GONE);
                allImageLayout.setVisibility(View.VISIBLE);
                llMarginli.setVisibility(View.GONE);
            }else {
                tvDuyuru.setText(ss);
            }
            tvDuyuru.setMovementMethod(LinkMovementMethod.getInstance());
            tvDuyuru.setHighlightColor(Color.TRANSPARENT);
            tvTarih.setText(duyuru.date);
        }
    }

    public String stripHtml(String html) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            return String.valueOf(Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY));
        } else {
            return String.valueOf(Html.fromHtml(html));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                Intent intent = new Intent(DuyuruDetayActivity.this, DuyurularActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void imageShowSlider(View v){
        if (allImageLayout.getVisibility() == View.GONE){
            allImageLayout.setVisibility(View.VISIBLE);
            updownBtnImg.setImageResource(R.drawable.updownbutton);
            llMarginli.setLayoutParams(lp);
        }else{
            allImageLayout.setVisibility(View.GONE);
            updownBtnImg.setImageResource(R.drawable.dropdownbutton);
            llMarginli.setLayoutParams(lpp);


        }
    }


}


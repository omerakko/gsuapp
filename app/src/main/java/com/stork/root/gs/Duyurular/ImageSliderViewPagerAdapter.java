package com.stork.root.gs.Duyurular;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.stork.root.gs.R;

/**
 * Created by sonka on 20.11.2017.
 */

public class ImageSliderViewPagerAdapter extends PagerAdapter {
    private Context ctx;
    private  String[] urls;
    private LayoutInflater inflater;


    public ImageSliderViewPagerAdapter(Context ctx,String []urls){
        this.ctx = ctx;
        this.urls=urls;
    }
    @Override
    public int getCount() {
        return urls.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view ==(LinearLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflater =  (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.swap,container,false);
        ImageView img =(ImageView)v.findViewById(R.id.imagee);
        Picasso.with(ctx)
                .load(urls[position])
                .error(android.R.drawable.stat_notify_error)
                .into(img);
        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        container.refreshDrawableState();
    }

}

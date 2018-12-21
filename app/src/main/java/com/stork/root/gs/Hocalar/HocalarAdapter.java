package com.stork.root.gs.Hocalar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.stork.root.gs.R;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

/**
 * Created by root on 13.03.2017.
 */

public class HocalarAdapter extends RecyclerView.Adapter<HocalarAdapter.MyHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<Hocalar> data= Collections.emptyList();

    public HocalarAdapter(Context context, List<Hocalar> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.from(parent.getContext())
                .inflate(R.layout.container_hocalar, parent, false);
        Typeface type = Typeface.createFromAsset(context.getAssets(),"fonts/font2.ttf");


        MyHolder myHolder = new MyHolder(view);
        myHolder.adsoyad.setTypeface(type);
        myHolder.bolum.setTypeface(type);
        return myHolder;
    }


    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        final Hocalar hocalar = data.get(position);

        holder.adsoyad.setText(hocalar.adsoyad);
        holder.bolum.setText("Bölüm: " + hocalar.bolum);

        String fullUrl = "http://www.gsuapp.com/" + hocalar.hocaImg;

        Picasso.with(context)
                .load(fullUrl)
                .into(holder.hocalarImg);

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, OgretimElemaniDetayActivity.class);
                intent.putExtra("Detayogretimelemanı", hocalar);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);



            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public static class MyHolder extends RecyclerView.ViewHolder{

        CardView cv;
        TextView adsoyad;
        TextView bolum;
        ImageView hocalarImg;

        public MyHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cvHocalar);
            adsoyad = (TextView) itemView.findViewById(R.id.tvadsoyad);
            bolum = (TextView) itemView.findViewById(R.id.tvbolum);
            hocalarImg = (ImageView) itemView.findViewById(R.id.hocalarImg);
        }


    }

}

package com.stork.root.gs.Etkinlikler;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.stork.root.gs.R;

import java.util.List;

/**
 * Created by root on 16.04.2017.
 */

public class EtkinlikAdapter extends RecyclerView.Adapter<EtkinlikAdapter.EtkinlikViewHolder>  {


    private List<Etkinlikler> etkinlikler;
    private Context context;

    public EtkinlikAdapter(Context context, List<Etkinlikler> etkinlikler){
        this.context = context;
        this.etkinlikler = etkinlikler;
    }

    @Override
    public EtkinlikAdapter.EtkinlikViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.from(parent.getContext())
                .inflate(R.layout.etkinlik_card_view, parent, false);
        Typeface type = Typeface.createFromAsset(context.getAssets(),"fonts/font2.ttf");


        EtkinlikAdapter.EtkinlikViewHolder etkinlikViewHolder = new EtkinlikAdapter.EtkinlikViewHolder(view);
        etkinlikViewHolder.tvEtkinlikAdi.setTypeface(type);
        etkinlikViewHolder.tvEtkinlikTarihYer.setTypeface(type);
        etkinlikViewHolder.tvKulupAdi.setTypeface(type);

        return etkinlikViewHolder;
    }

    @Override
    public void onBindViewHolder(EtkinlikAdapter.EtkinlikViewHolder holder, int position) {


        final Etkinlikler etkinlikler = this.etkinlikler.get(position);
        holder.tvKulupAdi.setText(etkinlikler.kulupadi);
        holder.tvEtkinlikAdi.setText(etkinlikler.etkinlikadi);
        String yer = etkinlikler.etkinlikyeri;
        String basTarihi = etkinlikler.etkinliktarihi;
        String bitTarihi = etkinlikler.etkinlikBitTarihi;
        if (yer.isEmpty() && basTarihi.equals(bitTarihi)){
            holder.tvEtkinlikTarihYer.setText(basTarihi);
        }else if (yer.isEmpty() && basTarihi != bitTarihi){
            holder.tvEtkinlikTarihYer.setText(basTarihi + "-" + bitTarihi);
        }else if (!yer.isEmpty() && basTarihi.equals(bitTarihi)){
            holder.tvEtkinlikTarihYer.setText(basTarihi + " || " + yer);
        }else if (!yer.isEmpty() && basTarihi != bitTarihi){
            holder.tvEtkinlikTarihYer.setText(basTarihi + " - " + bitTarihi + " || " + yer);
        }



        String fullUrl = "http://www.gsuapp.com/" + etkinlikler.iconurl;

        Picasso.with(context)
                .load(fullUrl)
                .error(android.R.drawable.stat_notify_error)
                .into(holder.ivImg);



        holder.linearLayoutetkinlikler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EtkinlikDetayActivity.class);
                intent.putExtra("Detayetkinlikler", etkinlikler);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(etkinlikler != null) {
            return etkinlikler.size();
        }
        return 0;
    }

    public static class EtkinlikViewHolder extends RecyclerView.ViewHolder{

        public LinearLayout linearLayoutetkinlikler;
        public ImageView ivImg;
        public TextView tvKulupAdi;
        public TextView tvEtkinlikAdi;
        public TextView tvEtkinlikTarihYer;

        public EtkinlikViewHolder(View itemView) {
            super(itemView);
            linearLayoutetkinlikler = (LinearLayout) itemView.findViewById(R.id.etkinliklayout);
            ivImg = (ImageView) itemView.findViewById(R.id.IMGkuluplogo);
            tvKulupAdi = (TextView) itemView.findViewById(R.id.detaykulupadi);
            tvEtkinlikAdi = (TextView) itemView.findViewById(R.id.detayetkinlikbasligi);
            tvEtkinlikTarihYer = (TextView) itemView.findViewById(R.id.detayetkinliktarihiyeri);
        }
    }
}


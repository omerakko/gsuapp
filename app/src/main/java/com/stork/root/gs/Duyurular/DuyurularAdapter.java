package com.stork.root.gs.Duyurular;


import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.stork.root.gs.R;

import java.util.List;

public class DuyurularAdapter extends RecyclerView.Adapter<DuyurularAdapter.DuyurularViewHolder> {

    private List<Duyurular> duyurulars;
    private Context context;

    public DuyurularAdapter(Context context, List<Duyurular> duyurulars){
        this.context = context;
        this.duyurulars = duyurulars;
    }

    @Override
    public DuyurularViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.from(parent.getContext())
                .inflate(R.layout.item_cardview_layout, parent, false);
        Typeface type = Typeface.createFromAsset(context.getAssets(),"fonts/font2.ttf");
        Typeface type1 = Typeface.createFromAsset(context.getAssets(),"fonts/font.ttf");


        DuyurularViewHolder duyurularViewHolder = new DuyurularViewHolder(view);
        duyurularViewHolder.tvTarih.setTypeface(type1);
        duyurularViewHolder.tvDuyuru.setTypeface(type);
        duyurularViewHolder.tvTitle.setTypeface(type);
        duyurularViewHolder.tvYayinYeri.setTypeface(type1);

        return duyurularViewHolder;
    }

    @Override
    public void onBindViewHolder(DuyurularViewHolder holder, int position) {
        final Duyurular duyurular = duyurulars.get(position);

        holder.tvTitle.setText(stripHtml(duyurular.Baslik));
        holder.tvDuyuru.setText(stripHtml(duyurular.Icerik));
        holder.tvTarih.setText(duyurular.date);
        holder.tvYayinYeri.setText(duyurular.YayinYeri);

        holder.cvDuyurular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(context, DuyuruDetayActivity.class);
                    intent.putExtra("Detayduyuru", duyurular);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        if(duyurulars != null) {
            return duyurulars.size();
        }
        return 0;
    }
    public String stripHtml(String html) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            return String.valueOf(Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY));
        } else {
            return String.valueOf(Html.fromHtml(html));
        }
    }


    public static class DuyurularViewHolder extends RecyclerView.ViewHolder{

        public CardView cvDuyurular;
        public ImageView ivImg;
        public TextView tvTitle;
        public TextView tvDuyuru;
        public TextView tvTarih;
        public TextView tvYayinYeri;

        public DuyurularViewHolder(View itemView) {
            super(itemView);

            cvDuyurular = (CardView) itemView.findViewById(R.id.cvDuyurular);
            ivImg = (ImageView) itemView.findViewById(R.id.ivImg);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvDuyuru = (TextView) itemView.findViewById(R.id.tvDuyuru);
            tvTarih = (TextView) itemView.findViewById(R.id.tvTarih);
            tvYayinYeri = (TextView) itemView.findViewById(R.id.tvYayinYeri);
        }
    }
}
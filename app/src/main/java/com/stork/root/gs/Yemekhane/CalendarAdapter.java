package com.stork.root.gs.Yemekhane;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stork.root.gs.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by root on 12.04.2017.
 */
public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.ViewHolder> {

    private ArrayList<String> mDataSet;
    private Context context;
    String Ay;
    Calendar calendar = Calendar.getInstance();
    Date today = calendar.getTime();
    YemekhaneActivity yemekhaneActivity = new YemekhaneActivity();
    gün gun = new gün();

    public CalendarAdapter(Context c, ArrayList<String> mDataSet) {
        this.mDataSet = mDataSet;
        this.context = c;
    }

    @Override
    public CalendarAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.calendar_days,parent,false);
        ViewHolder vh = new ViewHolder(v);
        context = parent.getContext();
        Typeface type = Typeface.createFromAsset(context.getAssets(),"fonts/font2.ttf");
        Typeface type2 = Typeface.createFromAsset(context.getAssets(),"fonts/font3.ttf");
        vh.calendarDay.setTypeface(type);
        vh.calendarMonth.setTypeface(type2);
        return vh;
    }

    @Override
    public void onBindViewHolder(CalendarAdapter.ViewHolder holder, int position) {
        AyHesapla();
        final String ControledDay;
        if (Integer.parseInt(mDataSet.get(position))<10){
            ControledDay = "0" + mDataSet.get(position);
           // holder.calendarDay.setText("0" + mDataSet.get(position));
        }else{
            ControledDay = mDataSet.get(position);
        }
        holder.calendarDay.setText(ControledDay);
        holder.calendarMonth.setText(Ay);


        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                String month = yemekhaneActivity.getsAy();
                String year = yemekhaneActivity.getsYil();
                String gun1 = gun.getYenigün();


                if (!gun1.equals(mDataSet.get(pos))){
                    TextView tv = (TextView)((Activity) context).findViewById(R.id.tarihyemekler);
/*                Thread t = new Thread();
                try {
                    t.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
  */
                    tv.setText(ControledDay + "/" + month + "/" + year);
                    gun.gündegistir(mDataSet.get(pos));
                    Log.d("MyApp","YENİ NESNE OLUŞTUTULDU");

                }else{

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ItemClickListener itemClickListener;
        public TextView calendarDay, calendarMonth;
        LinearLayout linearLayout;
        public ViewHolder(View itemView) {
            super(itemView);

            calendarDay = (TextView)itemView.findViewById(R.id.gün);
            calendarMonth = (TextView)itemView.findViewById(R.id.günyazi);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.llayout);

            itemView.setOnClickListener(this);

        }

        public void setItemClickListener(ItemClickListener itemClickListener){
            this.itemClickListener = itemClickListener;
        }


        @Override
        public void onClick(View v) {
            this.itemClickListener.onItemClick(this.getLayoutPosition());
        }
    }

    public void AyHesapla(){
        DateFormat dateFormat = new SimpleDateFormat("MM");
        String sayiAy = dateFormat.format(today);
        switch (sayiAy) {
            case "01" :
                Ay = "Ocak";
                break;
            case "02" :
                Ay = "Şubat";
                break;
            case "03" :
                Ay = "Mart";
                break;
            case "04" :
                Ay = "Nisan";
                break;
            case "05" :
                Ay = "Mayıs";
                break;
            case "06" :
                Ay = "Haziran";
                break;
            case "07" :
                Ay = "Temmuz";
                break;
            case "08" :
                Ay = "Ağustos";
                break;
            case "09" :
                Ay = "Eylül";
                break;
            case "10" :
                Ay = "Ekim";
                break;
            case "11" :
                Ay = "Kasım";
                break;
            case "12" :
                Ay = "Aralık";
                break;

        }
    }
}

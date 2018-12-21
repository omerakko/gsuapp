package com.stork.root.gs.Qr;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.stork.root.gs.MainActivity;
import com.stork.root.gs.R;

public class
Qr_Activity extends AppCompatActivity {

    TextView aboutUs;
    Button btn_Qr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainqr);
        Typeface type = Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/font3.ttf");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        aboutUs = (TextView) findViewById(R.id.text_AboutUs);
        aboutUs.setText("Gsu app Qr kod yoklama sistemi, Qr kod oluşturmak için tıklayınız"
            );
        aboutUs.setTypeface(type);

        btn_Qr = (Button) findViewById(R.id.btn_Qr);
        btn_Qr.setTypeface(type);

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                Intent intent = new Intent(Qr_Activity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void FeedBackActivityButton(View v){
        Intent intent = new Intent(getApplicationContext(),FeedBack_Qr.class);
        startActivity(intent);
    }
}

package com.stork.root.gs.Hocalar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.stork.root.gs.Hocalar.Bolumler.Bilgisayar;
import com.stork.root.gs.Hocalar.Bolumler.Diller;
import com.stork.root.gs.Hocalar.Bolumler.Endustri;
    import com.stork.root.gs.Hocalar.Bolumler.Felsefe;
import com.stork.root.gs.Hocalar.Bolumler.Hukuk;
import com.stork.root.gs.Hocalar.Bolumler.Iktisat;
import com.stork.root.gs.Hocalar.Bolumler.Iletisim;
import com.stork.root.gs.Hocalar.Bolumler.Isletme;
import com.stork.root.gs.Hocalar.Bolumler.Matematik;
import com.stork.root.gs.Hocalar.Bolumler.Siyaset;
import com.stork.root.gs.Hocalar.Bolumler.Sosyoloji;
import com.stork.root.gs.Hocalar.Bolumler.Uluslar;
import com.stork.root.gs.MainActivity;
import com.stork.root.gs.R;

public class BolumHocalarActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn10,btn11,btn12;
    final String TAG = "BolumHocalarActivity";

    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    SearchView searchView = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bolum_hocalar);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        btn1 = (Button) findViewById(R.id.bilgisayar);
        btn2 = (Button) findViewById(R.id.endüstri);
        btn3 = (Button) findViewById(R.id.hukuk);
        btn4 = (Button) findViewById(R.id.diller);

        btn5 = (Button) findViewById(R.id.felsefe);
        btn6 = (Button) findViewById(R.id.iktisat);
        btn7 = (Button) findViewById(R.id.iletisim);
        btn8 = (Button) findViewById(R.id.isletme);

        btn9 = (Button) findViewById(R.id.matematik);
        btn10 = (Button) findViewById(R.id.siyaset);
        btn11 = (Button) findViewById(R.id.sosyoloji);
        btn12 = (Button) findViewById(R.id.uluslar);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btn10.setOnClickListener(this);
        btn11.setOnClickListener(this);
        btn12.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.bilgisayar:
                Intent intent = new Intent(getApplicationContext(),Bilgisayar.class);
                startActivity(intent);
                break;

            case R.id.endüstri:
                Intent intent1 = new Intent(getApplicationContext(),Endustri.class);
                startActivity(intent1);
                break;

            case R.id.hukuk:
                Intent intent2 = new Intent(getApplicationContext(),Hukuk.class);
                startActivity(intent2);
                break;


            case R.id.diller:
                Intent intent3 = new Intent(getApplicationContext(),Diller.class);
                startActivity(intent3);
                break;

            case R.id.felsefe:
                Intent intent4 = new Intent(getApplicationContext(),Felsefe.class);
                startActivity(intent4);
                break;

            case R.id.iktisat:
                Intent intent5 = new Intent(getApplicationContext(),Iktisat.class);
                startActivity(intent5);
                break;


            case R.id.iletisim:
                Intent intent6 = new Intent(getApplicationContext(),Iletisim.class);
                startActivity(intent6);
                break;


            case R.id.isletme:
                Intent intent7 = new Intent(getApplicationContext(),Isletme.class);
                startActivity(intent7);
                break;


            case R.id.matematik:
                Intent intent8 = new Intent(getApplicationContext(),Matematik.class);
                startActivity(intent8);
                break;

            case R.id.siyaset:
                Intent intent9 = new Intent(getApplicationContext(),Siyaset.class);
                startActivity(intent9);
                break;


            case R.id.sosyoloji:
                Intent intent10 = new Intent(getApplicationContext(),Sosyoloji.class);
                startActivity(intent10);
                break;


            case R.id.uluslar:
                Intent intent11 = new Intent(getApplicationContext(),Uluslar.class);
                startActivity(intent11);
                break;
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_main1, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                Intent intent = new Intent(BolumHocalarActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;

            case R.id.action_search:

                Intent intent1 = new Intent(BolumHocalarActivity.this, OgretimElemanlariActivity.class);
                startActivity(intent1);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

}

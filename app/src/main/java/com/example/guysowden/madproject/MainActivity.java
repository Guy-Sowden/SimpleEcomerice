package com.example.guysowden.madproject;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    int numItem;
    int numTable;
    TextView total;
    FrameLayout fl;
    MenuItem menu1 = new MenuItem();
    private Spinner tables;
    Button Checkout;
    ArrayList<Fragment> mil = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.tables = findViewById(R.id.spinner2);
        addfromfile();
        total = findViewById(R.id.total);
        Checkout = findViewById(R.id.Checkout);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.tables, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tables.setAdapter(adapter);

        Checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Payment.class);
                String res = "<div style='padding:0;'>";
                for (int d = 0; d < numItem; d++) {
                    MenuItem e = (MenuItem) mil.get(d);
                    res += e.toBillItem();
                }
                MenuItem e = (MenuItem) mil.get(1);
                Log.d("mainScreen", "bill" + res);
                i.putExtra("bill", res);
                i.putExtra("TableNumber", tables.getSelectedItem().toString());
                startActivity(i);
            }
        });
    }

    public void addMenuItem(Fragment menu1, String name, int price, String url) {
        Bundle args = new Bundle();
        args.putString("Name", name);
        args.putInt("Price", price);
        args.putString("ImageURL", url);
        Log.d("URLimag",url);
        menu1.setArguments(args);

        android.support.v4.app.FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.add(R.id.container, menu1, "fragment " + numItem);
        trans.commit();
        mil.add(menu1);
        numItem++;
    }
    public void updateprice(String totallprice){
        total.setText("Price:"+totallprice);
    }
    public void addfromfile(){
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //File products = new File("pro.csv");
            InputStream open  = new URL("https://bluehills.club/BOTS/pro.txt").openStream();

            BufferedReader bf = new BufferedReader(new InputStreamReader(open));
            String line;
            while ((line = bf.readLine()) != null) {
                Log.d("lineread",line);

                String[] args = line.split(",");
                Log.d("Erors", "entered2");
                MenuItem temp = new MenuItem();
                //Log.d("integer value",args[1]);
                int val = Integer.parseInt(args[1]);
                addMenuItem(temp, args[0], val, args[2]);
                //MI.add(temp);
            }
        }
        catch (IOException e){

        }
    }
}
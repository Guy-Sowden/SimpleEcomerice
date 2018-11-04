package com.example.guysowden.madproject;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class bill extends AppCompatActivity {
    TextView textblock;
    Button Print;
    String bill;
    String Table;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        Bundle extras = getIntent().getExtras();
        bill = extras.getString("Menu");
         Table = extras.getString("Table");
        Log.d("billingscreen", bill);
        textblock = findViewById(R.id.textblock);
        bill = "<center><h1>Table " + Table + "</h1>" + bill;
        bill += "</div>\n <h1  style='padding:0; margin:0px;'> Total " + MenuItem.formatPrice(String.valueOf(MenuItem.getTotal())) + "</h1></center>";

        textblock.setText(Html.fromHtml(bill));
        setTitle("Bill");
        Button email = findViewById(R.id.email);
        email.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("*/*");
                intent.putExtra(Intent.EXTRA_EMAIL, "guyden1997@gmail.com");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Order");
                intent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(bill));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }});
    }
}
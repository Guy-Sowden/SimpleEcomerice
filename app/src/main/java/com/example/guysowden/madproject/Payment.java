package com.example.guysowden.madproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static android.support.v4.text.HtmlCompat.fromHtml;
import static android.text.Html.FROM_HTML_MODE_COMPACT;


public class Payment extends AppCompatActivity {
    WebView wv;
    String website;
    String bill;
    String Table;
    ArrayList<Fragment> mil = new ArrayList<>();
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        website = "https://bluehills.club/BOTS/Stripe?amount=" + MenuItem.getTotal();
        wv = findViewById(R.id.payments);

        wv.getSettings().setUserAgentString("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36");

        wv.setWebViewClient(new myWebClient());
        wv.getSettings().setJavaScriptEnabled(true);
        wv.loadUrl(website);
        Bundle extras = getIntent().getExtras();
        bill = extras.getString("bill");
        Table = extras.getString("TableNumber");
        Log.d("paymentScreen",bill);
    }
    public class myWebClient extends WebViewClient
    {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
            Log.d("paymentScreen",url);
            if(url.equals("https://bluehills.club/BOTS/Stripe/charge.php")){
                Log.d("paymentScreen","Payment accepted");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent i = new Intent(Payment.this,bill.class);
                i.putExtra("Menu", bill);
                i.putExtra("Table", Table);
                startActivity(i);
            }
        }
    }
    public void onDistroy(){
        if(wv != null){
            wv.destroy();
        }
    }

}

package com.example.guysowden.madproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import static com.example.guysowden.madproject.R.layout.activity_main;
import static com.example.guysowden.madproject.R.layout.fragment_menu_item;

public class MenuItem extends Fragment {
    private String mParam1;
    private String mParam2;
    TextView priceView = null;
    private ImageView iv;
    TextView nameView = null;
    private int price;
    private String name;
    private Button buy;
    private Button remove;
    boolean gf = false;
    String Imageurl;
    TextView Buynumber;
    int number;
    boolean vegan = false;
    static int totalPrice;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(fragment_menu_item, container, false);

        buy = (Button) view.findViewById(R.id.buybutton);
        remove = (Button) view.findViewById(R.id.Remove);
        this.priceView = view.findViewById(R.id.price);
        priceView.setText("Example");
        this.nameView = view.findViewById(R.id.Name);
        this.iv = view.findViewById(R.id.imageView);
        Buynumber = view.findViewById(R.id.BuyNumber);
        remove.setEnabled(false);
        //get args
        if(getArguments() != null){
            name = getArguments().getString("Name");
            price = getArguments().getInt("Price");
            Imageurl = getArguments().getString("ImageURL");
            nameView.setText(Html.fromHtml("<h1>"+name+"</h1>"));
            priceView.setText(formatPrice(String.valueOf(price)));
            try {
                URL url = new URL(Imageurl);
                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                iv.setImageBitmap(bmp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //Events
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(number<7) {
                    remove.setEnabled(true);
                    buy();
                    Buynumber.setText("x"+String.valueOf(number));
                }
                if(number == 7){
                    buy.setEnabled(false);
                }
                ((MainActivity)getActivity()).updateprice(formatPrice(Integer.toString(totalPrice)));
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(number>0) {
                    buy.setEnabled(true);
                    remove();
                    Buynumber.setText("x"+String.valueOf(number));
                }
                if(number == 0){
                    remove.setEnabled(false);
                }
                ((MainActivity)getActivity()).updateprice(formatPrice(Integer.toString(totalPrice)));
            }
        });

        return view;
    }
    private void buy () {
        totalPrice += price;
        number++;
    }
    private void remove () {
        totalPrice -= price;
        number--;
    }
    public static int getTotal(){
        return totalPrice;
    }
    public static String formatPrice(String price){
        if(Integer.valueOf(price) != 0) {
            return "$" + price.substring(0, price.length() - 2) + "." + price.substring(price.length() - 2, price.length());
        }
        return "$0.0";
    }
    public String toBillItem(){
        String res="";
        Log.d("bill", String.valueOf(number));
        if(number>0) {
            res = "<h3 style='padding:0px; margin:0px;'><u>"+name + "</u></h3>";
            if(number >=2){
                res += "unit price = " +  formatPrice(String.valueOf(price))+"<br> amount x" + number +"";
            }
            res += "\n<h2 style='padding:0; margin:0px;'>Total " + formatPrice(String.valueOf(number * price))+"</h2>\n___________________________________\n<br>";
        }
        Log.d("bill", "resp :"+res);
        return res;
    }

}


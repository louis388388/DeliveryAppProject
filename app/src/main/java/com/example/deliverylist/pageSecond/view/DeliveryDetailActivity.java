package com.example.deliverylist.pageSecond.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.example.deliverylist.R;
import com.example.deliverylist.databinding.ActivityDeliveryDetailBinding;
import com.example.deliverylist.index.Index_Struct;
import com.example.deliverylist.pageFirst.view.MainActivity;
import com.example.deliverylist.pageSecond.viewmodel.SecondViewModel;

import java.util.Objects;

import static com.example.deliverylist.pageFirst.view.MainActivity.requestViewModel;

public class DeliveryDetailActivity extends AppCompatActivity {

    ActivityDeliveryDetailBinding activityDeliveryDetailBinding;
    SecondViewModel secondViewModel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_delivery_detail);

        activityDeliveryDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_delivery_detail);
        secondViewModel = ViewModelProviders.of(this).get(SecondViewModel.class);

        readingIncomingData(getIntent());
        setContentView();
        setUpFavoriteButton();
    }

    private void setContentView(){

        //Set address textView
        activityDeliveryDetailBinding.tvFromSecond.setText(secondViewModel.getAddress_start());
        activityDeliveryDetailBinding.tvToSecond.setText(secondViewModel.getAddress_end());

        //Set Picture drawable
        GlideUrl url = new GlideUrl(secondViewModel.getGoods_picture(), new LazyHeaders.Builder()
                .addHeader("User-Agent", "your-user-agent")
                .build());

        Glide.with(activityDeliveryDetailBinding.getRoot().getContext()).load(url).into(activityDeliveryDetailBinding.imgGoods);

        //Set Price Column
        activityDeliveryDetailBinding.tvPriceSecond.setText(secondViewModel.getPrice());

        //Set Back button
        activityDeliveryDetailBinding.btnBack.setOnClickListener(v -> finish());

    }

    private void setUpFavoriteButton(){

        //set favorite button, text will change according to Whether it is favorite.
        activityDeliveryDetailBinding.buttonAddFavorite.setText(!requestViewModel.getArrayListItem(secondViewModel.getPosition()) ?"Add to Favorite":"Remove Favorite");

        activityDeliveryDetailBinding.buttonAddFavorite.setOnClickListener(v -> {
            requestViewModel.setArrayListItem(secondViewModel.getPosition(),!requestViewModel.getArrayListItem(secondViewModel.getPosition()));
            MainActivity.recyclerAdapter.notifyDataSetChanged();
            finish();
        });

    }

    private void readingIncomingData(Intent intent){

        if (intent == null || intent.getExtras() == null)
            return;

        if (intent.getExtras().containsKey(Index_Struct.POSITION)) {
            secondViewModel.setPosition(intent.getExtras().getInt(Index_Struct.POSITION, -1));
        }
        if (intent.getExtras().containsKey(Index_Struct.ADDRESS_START)) {
            secondViewModel.setAddress_start(intent.getExtras().getString(Index_Struct.ADDRESS_START, ""));
        }
        if (intent.getExtras().containsKey(Index_Struct.ADDRESS_END)) {
            secondViewModel.setAddress_end(intent.getExtras().getString(Index_Struct.ADDRESS_END, ""));
        }
        if (intent.getExtras().containsKey(Index_Struct.POSITION)) {
            secondViewModel.setPrice(intent.getExtras().getString(Index_Struct.PRICE, ""));
        }
        if (intent.getExtras().containsKey(Index_Struct.GOODS_PICTURE)) {
            secondViewModel.setGoods_picture(intent.getExtras().getString(Index_Struct.GOODS_PICTURE, ""));
        }

    }

}

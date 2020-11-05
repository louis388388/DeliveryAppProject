package com.example.deliverylist.pageFirst.view.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.example.deliverylist.R;
import com.example.deliverylist.data.Entity.Entity;
import com.example.deliverylist.databinding.RecyclerContentBinding;
import com.example.deliverylist.index.Index_Struct;
import com.example.deliverylist.pageFirst.viewmodel.RequestViewModel;
import com.example.deliverylist.pageSecond.view.DeliveryDetailActivity;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

    private final List<Entity> entityList;
    private final RequestViewModel requestViewModelRecycler;

    public RecyclerAdapter (List<Entity> entity, RequestViewModel requestViewModel){
        entityList = entity;
        requestViewModelRecycler = requestViewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerContentBinding recyclerContentBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.recycler_content, parent, false);
        return new ViewHolder(recyclerContentBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        calculation(position);

        holder.updateViewData(
                entityList.get(position).getGoodsPicture(),
                entityList.get(position).getRoute().getStart(),
                entityList.get(position).getRoute().getEnd(),
                calculation(position),
                position

        );
    }

    private String calculation(int position){

        double total = StringToFloat(entityList.get(position).getSurcharge())
                + StringToFloat(entityList.get(position).getDeliveryFee());

        return "$"+ new BigDecimal(total).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    private double StringToFloat(String string){

        return Double.parseDouble(string.replaceAll("[$]",""));
    }

    @Override
    public int getItemCount() {
        return entityList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RecyclerContentBinding recyclerContentBinding;

        ViewHolder(@NonNull  RecyclerContentBinding recyclerContentBinding) {
            super(recyclerContentBinding.getRoot());
            this.recyclerContentBinding = recyclerContentBinding;
        }

        private void updateViewData(String goods_picture, String address_start, String address_end, String price,int position) {

            //Is it favorite?
            if (requestViewModelRecycler.getArrayListItem(position)) {
                recyclerContentBinding.imageFavorite.setVisibility(View.VISIBLE);
            } else
                recyclerContentBinding.imageFavorite.setVisibility(View.GONE);

             //Setting Up ImageView
            GlideUrl url = new GlideUrl(goods_picture, new LazyHeaders.Builder()
                    .addHeader("User-Agent", "your-user-agent")
                    .build());
            Glide.with(recyclerContentBinding.getRoot().getContext()).load(url).centerCrop().into(recyclerContentBinding.imageGoods);

            //Setting up textView content
            recyclerContentBinding.tvFrom.setText(address_start);
            recyclerContentBinding.tvTo.setText(address_end);
            recyclerContentBinding.tvPrice.setText(price);

            //onClick to next page
            recyclerContentBinding.constraintLayout.setOnClickListener(v -> {

                Intent intent = new Intent(v.getContext(), DeliveryDetailActivity.class);
                intent.putExtra(Index_Struct.POSITION,position);
                intent.putExtra(Index_Struct.GOODS_PICTURE,goods_picture);
                intent.putExtra(Index_Struct.ADDRESS_START,address_start);
                intent.putExtra(Index_Struct.ADDRESS_END,address_end);
                intent.putExtra(Index_Struct.PRICE,price);

                v.getContext().startActivity(intent);
            });
        }

    }
}

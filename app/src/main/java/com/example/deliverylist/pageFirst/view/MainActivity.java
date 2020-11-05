package com.example.deliverylist.pageFirst.view;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.deliverylist.R;
import com.example.deliverylist.databinding.ActivityMainBinding;
import com.example.deliverylist.pageFirst.view.Adapter.RecyclerAdapter;
import com.example.deliverylist.pageFirst.viewmodel.RequestViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static RequestViewModel requestViewModel;
    ActivityMainBinding binding;
    public static RecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        requestViewModel = ViewModelProviders.of(this).get(RequestViewModel.class);

//        setUpObserver();
        setUpLiveDataObserver();
        setUpRequestAPI();
        setUpScrollViewListener();
        getStoreFavoriteInfo();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setStoreFavoriteInfo();
    }

    private void setUpRequestAPI(){
        requestViewModel.getPosts(requestViewModel.getOffset(),20);
    }

    private void setUpObserver(){
        requestViewModel.entityMutableLiveData.observe(this, entities -> {

//            RecyclerAdapter recyclerAdapter = new RecyclerAdapter(entities);
//            binding.itemDetailRecycler.setLayoutManager(new LinearLayoutManager(this));
//            binding.itemDetailRecycler.setAdapter(recyclerAdapter);
        });
    }

    private void setUpLiveDataObserver(){
        requestViewModel.addEntityMutableLiveData.observe(this, entities -> {

            recyclerAdapter = new RecyclerAdapter(entities,requestViewModel);
            binding.itemDetailRecycler.setLayoutManager(new LinearLayoutManager(this));
            binding.itemDetailRecycler.setAdapter(recyclerAdapter);
        });
    }

    private void setUpScrollViewListener(){

        binding.scrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {

            if (binding.scrollView.getChildAt(0).getMeasuredHeight() <= binding.scrollView.getHeight()+binding.scrollView.getScrollY()){
                requestViewModel.setOffset(requestViewModel.getOffset()+20);
                requestViewModel.getPosts(requestViewModel.getOffset(),20);
            }
        });
    }

    public void setStoreFavoriteInfo(){

        SharedPreferences.Editor editor = getSharedPreferences("FavoriteList", MODE_PRIVATE).edit();
        editor.putInt("FavoriteBool", requestViewModel.getFavoriteMark().size());
        for (int i = 0; i < requestViewModel.getFavoriteMark().size(); i++)
        {
            editor.putBoolean("item_"+i, requestViewModel.getFavoriteMark().get(i));
        }
        editor.commit();
    }

    public void getStoreFavoriteInfo(){
        requestViewModel.resetArrayListItem();
        SharedPreferences preferDataList = getSharedPreferences("FavoriteList", MODE_PRIVATE);
        int environNums = preferDataList.getInt("FavoriteBool", 0);
        for (int i = 0; i < environNums; i++)
        {
            requestViewModel.addArrayListItemFromLocal(preferDataList.getBoolean("item_"+i, false));
        }
    }
}
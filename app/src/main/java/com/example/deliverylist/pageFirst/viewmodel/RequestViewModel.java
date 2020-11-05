package com.example.deliverylist.pageFirst.viewmodel;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.deliverylist.data.Entity.Entity;
import com.example.deliverylist.data.networkClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestViewModel extends ViewModel {

    private int offset = 0;
    private ArrayList<Boolean> favoriteMark = new ArrayList<>();
    public List<Entity> entityList = new ArrayList<>();

    public MutableLiveData<List<Entity>> entityMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<List<Entity>> addEntityMutableLiveData = new MutableLiveData<>();

    public void getPosts(int offset, int limit){
        networkClient.getINSTANCE().getPosts(offset,limit).enqueue(new Callback<List<Entity>>() {

            @Override
            public void onResponse(Call<List<Entity>> call, Response<List<Entity>> response) {
                if (response.body()!= null){
                    entityList.addAll(response.body());
                    addEntityMutableLiveData.setValue(entityList);

                    for (int i=0; i <entityList.size(); i++){
                        favoriteMark.add(false);
                    }
                }
//                entityMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Entity>> call, Throwable t) {

                Log.d("onFailure", "Failure Code: "+ t);
            }
        });
    }

    public ArrayList<Boolean> getFavoriteMark(){
        return favoriteMark;
    }

    public void resetArrayListItem(){
        favoriteMark.clear();
    }

    public void addArrayListItemFromLocal(Boolean isFavorite){
        favoriteMark.add(isFavorite);
    }

    public void setArrayListItem(int position,boolean isFavorite){
        favoriteMark.set(position, isFavorite);
    }

    public boolean getArrayListItem(int position){
        if (favoriteMark == null)
            return false;
        else
            return favoriteMark.get(position);
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

}

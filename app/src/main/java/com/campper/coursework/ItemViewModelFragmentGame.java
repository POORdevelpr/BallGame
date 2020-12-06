package com.campper.coursework;

import android.content.ClipData;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ItemViewModelFragmentGame extends ViewModel {
    private final MutableLiveData<ClipData.Item> selectedItem = new MutableLiveData<>();

    public void setSelectedItem(ClipData.Item item){
        selectedItem.setValue(item);
    }

    public LiveData<ClipData.Item> getSelectedItem(){
        return selectedItem;
    }
}

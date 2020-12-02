package com.campper.coursework;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;

import com.campper.coursework.model.Card;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class GridViewAdapter implements ListAdapter {
    private AtomicInteger atomicIncrement = new AtomicInteger(0);
    AtomicIntegerArray atomicIntEqualCardsArray = new AtomicIntegerArray(2);
    private Context context;
    private ArrayList<Card> cardsArrayList;

    public GridViewAdapter(Context context, ArrayList<Card> cardsArrayList) {
        this.context = context;
        this.cardsArrayList = cardsArrayList;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
    }

    @Override
    public int getCount() {
        return cardsArrayList.size() - 1;
    }

    @Override
    public Object getItem(int position) {
        return cardsArrayList.get(position).getCardFrontImageId();
    }

    @Override
    public long getItemId(int position) {
        return cardsArrayList.get(position).getCardFrontImageId();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

     ArrayList<ImageView> imageList = new ArrayList<>();

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_view_item,
                    parent, false);
        }

        final ImageView imageView = convertView.findViewById(R.id.grid_view_item_img);


        convertView.setOnClickListener((View v) -> {
            imageView.setImageResource(cardsArrayList.get(position).getCardFrontImageId());
            imageList.add(imageView);

            atomicIncrement.getAndIncrement();
            Log.d("atomicInt", ""+atomicIncrement.get());
            if(atomicIncrement.get() > 1){
                imageList.get(0).setVisibility(View.INVISIBLE);
            }
        });

    return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return cardsArrayList.size() - 1;
    }

    @Override
    public boolean isEmpty() {
        return cardsArrayList.isEmpty();
    }
}

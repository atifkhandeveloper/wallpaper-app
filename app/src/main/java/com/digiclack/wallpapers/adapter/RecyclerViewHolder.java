package com.digiclack.wallpapers.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.digiclack.wallpapers.R;

public class RecyclerViewHolder extends ViewHolder {
    ImageView imageView;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        this.imageView = (ImageView) itemView.findViewById(R.id.image);
    }
}

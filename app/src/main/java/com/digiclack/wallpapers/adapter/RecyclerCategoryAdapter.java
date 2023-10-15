package com.digiclack.wallpapers.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.bumptech.glide.Glide;
import com.digiclack.wallpapers.R;
import com.digiclack.wallpapers.activity.QuotesListActivity;
import com.digiclack.wallpapers.utility.CategoryInfo;

import java.util.ArrayList;

import yuku.ambilwarna.BuildConfig;

public class RecyclerCategoryAdapter extends Adapter<RecyclerCategoryAdapter.ViewHolder> {
    ArrayList<CategoryInfo> categoryInfoList;
    Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout cardView;
        ImageView imageView;
        TextView tvTitle;

        public ViewHolder(View view) {
            super(view);
            this.cardView = (RelativeLayout) view.findViewById(R.id.cardview);
            this.imageView = (ImageView) view.findViewById(R.id.imageView);
            this.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        }
    }

    public RecyclerCategoryAdapter(Context context, ArrayList<CategoryInfo> categoryInfoList) {
        this.context = context;
        this.categoryInfoList = categoryInfoList;
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public int getItemCount() {
        return this.categoryInfoList.size();
    }

    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Glide.with(this.context).load(Integer.valueOf(this.context.getResources().getIdentifier(((CategoryInfo) this.categoryInfoList.get(position)).getCATEGORY_DRAWABLE(), "drawable", this.context.getPackageName()))).placeholder(R.drawable.no_image).error(R.drawable.no_image).into(viewHolder.imageView);
        viewHolder.tvTitle.setText(((CategoryInfo) this.categoryInfoList.get(position)).getCATEGORY_NAME());
        viewHolder.cardView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent quotesListActivity = new Intent(RecyclerCategoryAdapter.this.context, QuotesListActivity.class);
                quotesListActivity.putExtra("categoryId", ((CategoryInfo) RecyclerCategoryAdapter.this.categoryInfoList.get(position)).getCATEGORY_ID());
                quotesListActivity.putExtra("categoryName", ((CategoryInfo) RecyclerCategoryAdapter.this.categoryInfoList.get(position)).getCATEGORY_NAME());
                quotesListActivity.putExtra("searchString", BuildConfig.FLAVOR);
                RecyclerCategoryAdapter.this.context.startActivity(quotesListActivity);
            }
        });
    }

    public ViewHolder onCreateViewHolder(ViewGroup arg0, int position) {
        return new ViewHolder(LayoutInflater.from(arg0.getContext()).inflate(R.layout.row_layout_category, arg0, false));
    }
}

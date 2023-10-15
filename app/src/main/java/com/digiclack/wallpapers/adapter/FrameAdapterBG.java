package com.digiclack.wallpapers.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.digiclack.wallpapers.R;
import com.digiclack.wallpapers.utility.CustomSquareFrameLayout;
import com.digiclack.wallpapers.utility.CustomSquareImageView;

public class FrameAdapterBG extends BaseAdapter {
    public ViewHolder holder = null;
    LayoutInflater inflater;
    private Context mContext;
    SharedPreferences preferences;
    String[] valArr;

    public class ViewHolder {
        public ImageView img_lock;
        public CustomSquareImageView mThumbnail;
        public CustomSquareFrameLayout root;
    }

    public FrameAdapterBG(Context context, String[] val) {
        this.mContext = context;
        this.valArr = val;
        this.inflater = LayoutInflater.from(context);
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public int getCount() {
        return this.valArr.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.sticker_grid_item, null);
            this.holder = new ViewHolder();
            this.holder.root = (CustomSquareFrameLayout) convertView.findViewById(R.id.root);
            this.holder.mThumbnail = (CustomSquareImageView) convertView.findViewById(R.id.thumbnail_image);
            this.holder.img_lock = (ImageView) convertView.findViewById(R.id.img_lock);
            convertView.setTag(this.holder);
        } else {
            this.holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(this.mContext).load(Integer.valueOf(this.mContext.getResources().getIdentifier(this.valArr[position], "drawable", this.mContext.getPackageName()))).thumbnail(0.1f).dontAnimate().centerCrop().placeholder(R.drawable.no_image).error(R.drawable.no_image).into(this.holder.mThumbnail);

            this.holder.img_lock.setVisibility(View.GONE);



        return convertView;
    }
}

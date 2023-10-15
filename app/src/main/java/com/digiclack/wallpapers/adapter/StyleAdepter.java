package com.digiclack.wallpapers.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.digiclack.wallpapers.R;
import com.digiclack.wallpapers.interfacelistner.GetTemplateListener;

public class StyleAdepter extends Adapter<RecyclerViewHolder> {
    OnClickListener clickListener = new OnClickListener() {
        public void onClick(View v) {
            StyleAdepter.this.onGetTemplate = (GetTemplateListener) StyleAdepter.this.context;
            int position = ((RecyclerViewHolder) v.getTag()).getPosition();
            if (StyleAdepter.this.val.equals("template")) {





                    StyleAdepter.this.onGetTemplate.ontemplate(position + 1);



            }
        }
    };
    Activity context;
    LayoutInflater inflater;
    GetTemplateListener onGetTemplate;
    String[] templateImageid = new String[]{"at1", "at2", "at3", "at4", "at5", "at6", "at7", "at8", "at9", "at10", "at11", "at12", "at13", "at14", "at15", "at16", "at17", "at18", "at19", "at20", "at21", "at22", "at23", "at24", "at25", "at26"};
    String val;

    public StyleAdepter(Activity context, String val) {
        this.context = context;
        this.val = val;
        this.inflater = LayoutInflater.from(context);
    }

    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(this.inflater.inflate(R.layout.item_template, parent, false));
    }

    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        if (this.val.equals("template")) {
            holder.imageView.setImageResource(this.context.getResources().getIdentifier(this.templateImageid[position], "drawable", this.context.getPackageName()));
        }
        holder.imageView.setOnClickListener(this.clickListener);
        holder.imageView.setTag(holder);
    }

    public int getItemCount() {
        if (this.val.equals("template")) {
            return this.templateImageid.length;
        }
        return 0;
    }
}

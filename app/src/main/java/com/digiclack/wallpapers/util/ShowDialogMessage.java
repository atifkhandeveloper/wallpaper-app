package com.digiclack.wallpapers.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Build.VERSION;

import androidx.appcompat.app.AlertDialog.Builder;

import com.digiclack.wallpapers.R;

public class ShowDialogMessage {
    @SuppressLint("ResourceType")
    public void showMessage(Context context, String title, String msg) {
        Builder builder;
        if (VERSION.SDK_INT >= 21) {
            builder = new Builder(context);
        } else {
            builder = new Builder(context);
        }
        builder.setTitle(title).setMessage(msg).setPositiveButton(17039379, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        }).setIcon(R.mipmap.ic_launcher).show();
    }
}

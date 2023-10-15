package com.digiclack.wallpapers.adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.PopupMenu.OnMenuItemClickListener;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.bumptech.glide.Glide;
import com.digiclack.wallpapers.R;
import com.digiclack.wallpapers.activity.AddTextQuotesActivity;
import com.digiclack.wallpapers.activity.QuotesListActivity;
import com.digiclack.wallpapers.activity.SaveActivity;
import com.digiclack.wallpapers.db.DatabaseHandler;
import com.digiclack.wallpapers.utility.CustomTextView;
import com.digiclack.wallpapers.utility.FontTextView;
import com.digiclack.wallpapers.utility.QuotesInfo;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Random;

import yuku.ambilwarna.BuildConfig;

public class RecyclerQuotesListAdapter extends Adapter<RecyclerQuotesListAdapter.ViewHolder> {
    Context context;
    String filename = BuildConfig.FLAVOR;
    String[] imagesColorArr = new String[]{"e8", "e13", "f7", "f13", "fa8", "#ffffff", "fa11", "fn15", "j15", "#1ab4f5", "#fff500", "#fce6bb", "#ff5858", "#00ff4e", "#bd4ef7", "l3", "m11", "m18", "#ff8c10", "#03fdf2", "#fe44a0", "#a2ec89", "m19", "sm9", "t2", "wd17"};
    String[] likeArray;
    int[] noOfIndex;
    SharedPreferences preferences;
    ArrayList<QuotesInfo> quotesInfoList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView imageView;
        ToggleButton imgLike;
        RelativeLayout lay_Copy;
        RelativeLayout lay_Edit;
        FrameLayout lay_Imgframe;
        RelativeLayout lay_Like;
        RelativeLayout lay_Save;
        RelativeLayout lay_Share;
        CustomTextView tvLike;
        FontTextView tvQuotes;
        View view1;

        public ViewHolder(View view) {
            super(view);
            this.cardView = (CardView) view.findViewById(R.id.cardview);
            this.imageView = (ImageView) view.findViewById(R.id.imageView);
            this.view1 = view.findViewById(R.id.view);
            this.tvQuotes = (FontTextView) view.findViewById(R.id.tvQuotes);
            this.lay_Imgframe = (FrameLayout) view.findViewById(R.id.lay_Imgframe);
            this.lay_Like = (RelativeLayout) view.findViewById(R.id.lay_Like);
            this.lay_Edit = (RelativeLayout) view.findViewById(R.id.lay_Edit);
            this.lay_Save = (RelativeLayout) view.findViewById(R.id.lay_Save);
            this.lay_Copy = (RelativeLayout) view.findViewById(R.id.lay_Copy);
            this.lay_Share = (RelativeLayout) view.findViewById(R.id.lay_Share);
            this.imgLike = (ToggleButton) view.findViewById(R.id.imgLike);
            this.tvLike = (CustomTextView) view.findViewById(R.id.tvLike);
        }
    }

    public RecyclerQuotesListAdapter(Context context, ArrayList<QuotesInfo> quotesInfoList) {
        this.context = context;
        this.quotesInfoList = quotesInfoList;
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.likeArray = new String[quotesInfoList.size()];
        this.noOfIndex = new int[quotesInfoList.size()];
        for (int i = 0; i < quotesInfoList.size(); i++) {
            this.likeArray[i] = ((QuotesInfo) quotesInfoList.get(i)).getQUOTES_LIKED();
            this.noOfIndex[i] = -1;
        }
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public int getItemCount() {
        return this.quotesInfoList.size();
    }

    public void onBindViewHolder(final ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {
        viewHolder.tvQuotes.setText(((QuotesInfo) this.quotesInfoList.get(position)).getQUOTES_TEXT());
        if (this.likeArray[position].equals("Like")) {
            viewHolder.imgLike.setBackgroundResource(R.drawable.like_no);
            viewHolder.imgLike.setChecked(false);
            viewHolder.tvLike.setText(((QuotesInfo) this.quotesInfoList.get(position)).getQUOTES_LIKED());
        } else {
            viewHolder.imgLike.setBackgroundResource(R.drawable.like_yes);
            viewHolder.imgLike.setChecked(true);
            viewHolder.tvLike.setText(((QuotesInfo) this.quotesInfoList.get(position)).getQUOTES_LIKED());
        }
        changeBackground(viewHolder.imageView, viewHolder.view1, viewHolder.tvQuotes, this.noOfIndex[position]);
        viewHolder.lay_Imgframe.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                RecyclerQuotesListAdapter.this.noOfIndex[position] = new Random().nextInt(RecyclerQuotesListAdapter.this.imagesColorArr.length);
                RecyclerQuotesListAdapter.this.changeBackground(viewHolder.imageView, viewHolder.view1, viewHolder.tvQuotes, RecyclerQuotesListAdapter.this.noOfIndex[position]);
            }
        });



       RecyclerQuotesListAdapter.this.noOfIndex[position] = new Random().nextInt(RecyclerQuotesListAdapter.this.imagesColorArr.length);
        RecyclerQuotesListAdapter.this.changeBackground(viewHolder.imageView, viewHolder.view1, viewHolder.tvQuotes, RecyclerQuotesListAdapter.this.noOfIndex[position]);


        viewHolder.lay_Edit.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent quotesListActivity = new Intent(RecyclerQuotesListAdapter.this.context, AddTextQuotesActivity.class);
                quotesListActivity.putExtra("modeOfChosingPic", "chooserActivity");
                quotesListActivity.putExtra("quote_edit", ((QuotesInfo) RecyclerQuotesListAdapter.this.quotesInfoList.get(position)).getQUOTES_TEXT());
                quotesListActivity.putExtra("positn", "main");
                int index = RecyclerQuotesListAdapter.this.noOfIndex[position];
                if (index != -1) {
                    if (index == 9 || index == 10 || index == 11 || index == 12 || index == 13 || index == 14 || index == 18 || index == 19 || index == 20 || index == 21) {
                        quotesListActivity.putExtra("profile", "color");
                    } else if (index == 5) {
                        quotesListActivity.putExtra("profile", "color");
                    } else {
                        quotesListActivity.putExtra("profile", "bg");
                    }
                    quotesListActivity.putExtra("background", RecyclerQuotesListAdapter.this.imagesColorArr[index]);
                } else {
                    quotesListActivity.putExtra("profile", "color");
                    quotesListActivity.putExtra("background", "#000000");
                }
                RecyclerQuotesListAdapter.this.context.startActivity(quotesListActivity);
            }
        });
        viewHolder.lay_Like.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (viewHolder.imgLike.isChecked()) {
                    viewHolder.imgLike.setBackgroundResource(R.drawable.like_no);
                    RecyclerQuotesListAdapter.this.setQuotesLiked(((QuotesInfo) RecyclerQuotesListAdapter.this.quotesInfoList.get(position)).getQUOTES_ID(), "Like");
                    viewHolder.imgLike.setChecked(false);
                    viewHolder.tvLike.setText("Like");
                    RecyclerQuotesListAdapter.this.likeArray[position] = "Like";
                    return;
                }
                viewHolder.imgLike.setBackgroundResource(R.drawable.like_yes);
                RecyclerQuotesListAdapter.this.setQuotesLiked(((QuotesInfo) RecyclerQuotesListAdapter.this.quotesInfoList.get(position)).getQUOTES_ID(), "Liked");
                viewHolder.imgLike.setChecked(true);
                viewHolder.tvLike.setText("Liked");
                RecyclerQuotesListAdapter.this.likeArray[position] = "Liked";
            }
        });
        viewHolder.lay_Save.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                RecyclerQuotesListAdapter.this.saveImageInGallery( viewHolder.lay_Imgframe, false, position);
            }
        });
        viewHolder.lay_Copy.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ((ClipboardManager) RecyclerQuotesListAdapter.this.context.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("label", ((QuotesInfo) RecyclerQuotesListAdapter.this.quotesInfoList.get(position)).getQUOTES_TEXT()));
                Toast.makeText(RecyclerQuotesListAdapter.this.context, RecyclerQuotesListAdapter.this.context.getResources().getString(R.string.txtCopy), Toast.LENGTH_LONG).show();
            }
        });
        viewHolder.lay_Share.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(RecyclerQuotesListAdapter.this.context, viewHolder.lay_Share);
                popup.getMenuInflater().inflate(R.menu.poupup_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.textShare :
                                RecyclerQuotesListAdapter.this.shareImage(BuildConfig.FLAVOR, ((QuotesInfo) RecyclerQuotesListAdapter.this.quotesInfoList.get(position)).getQUOTES_TEXT(), false);
                                return true;
                            case R.id.imageShare :
                                RecyclerQuotesListAdapter.this.saveImageInGallery( viewHolder.lay_Imgframe, true, position);
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popup.show();
            }
        });
    }

    public ViewHolder onCreateViewHolder(ViewGroup arg0, int position) {
        return new ViewHolder(LayoutInflater.from(arg0.getContext()).inflate(R.layout.row_layout_quotes, arg0, false));
    }

    private void changeBackground(ImageView imageView, View view1, TextView tvQuotes, int index) {
        if (index == -1) {
            Glide.with(this.context).load(Integer.valueOf(R.drawable.white)).into(imageView);
            imageView.setColorFilter(Color.parseColor("#000000"));
            view1.setBackgroundColor(Color.parseColor("#00000000"));
            tvQuotes.setTextColor(-1);
        } else if (index == 9 || index == 10 || index == 11 || index == 12 || index == 13 || index == 14 || index == 18 || index == 19 || index == 20 || index == 21) {
            Glide.with(this.context).load(Integer.valueOf(R.drawable.white)).into(imageView);
            imageView.setColorFilter(Color.parseColor(this.imagesColorArr[index]));
            view1.setBackgroundColor(Color.parseColor("#00000000"));
            tvQuotes.setTextColor(-1);
        } else if (index == 5) {
            Glide.with(this.context).load(Integer.valueOf(R.drawable.white)).into(imageView);
            imageView.setColorFilter(Color.parseColor(this.imagesColorArr[index]));
            view1.setBackgroundColor(Color.parseColor("#00000000"));
            tvQuotes.setTextColor(-16777216);
        } else {
            imageView.setColorFilter(null);
            Glide.with(this.context).load(Integer.valueOf(this.context.getResources().getIdentifier(this.imagesColorArr[index], "drawable", this.context.getPackageName()))).into(imageView);
            view1.setBackgroundColor(Color.parseColor("#65000000"));
            tvQuotes.setTextColor(-1);
        }
    }

    private void setQuotesLiked(int quotesId, String liked) {
        DatabaseHandler dh = DatabaseHandler.getDbHandler(this.context);
        boolean updateQuotedLiked = dh.updateQuotesLikedStatus(quotesId, liked);
        dh.close();
    }

    private void saveImageInGallery( FrameLayout lay_Imgframe, boolean checkSaveShare, int position) {
        Bitmap bitmapNot = viewToBitmap(lay_Imgframe);

        saveBitmap(bitmapNot, true, checkSaveShare, position);
    }

    private Bitmap viewToBitmap(View frameLayout) {
        Bitmap b = null;
        try {
            b = Bitmap.createBitmap(frameLayout.getWidth(), frameLayout.getHeight(), Config.ARGB_8888);
            frameLayout.draw(new Canvas(b));
            return b;
        } finally {
            frameLayout.destroyDrawingCache();
        }
    }

    private void saveBitmap(final Bitmap bitmap, final boolean inPNG, final boolean checkSaveShare, final int position) {
        final ProgressDialog pd = new ProgressDialog(this.context);
        pd.setMessage(this.context.getResources().getString(R.string.plzwait));
        pd.setCancelable(false);
        pd.show();
        new Thread(new Runnable() {
            public void run() {
                try {
                    File pictureFileDir = new File(Environment.getExternalStorageDirectory(), "/Text On Photo");
                    if (pictureFileDir.exists() || pictureFileDir.mkdirs()) {
                        String photoFile = "text_on_photo_" + System.currentTimeMillis();
                        if (inPNG) {
                            photoFile = photoFile + ".png";
                        } else {
                            photoFile = photoFile + ".jpg";
                        }
                        if (QuotesListActivity.pathsOfFileUri[0].equals(BuildConfig.FLAVOR)) {
                            RecyclerQuotesListAdapter.this.filename = pictureFileDir.getPath() + File.separator + photoFile;
                        } else {
                            RecyclerQuotesListAdapter.this.filename = QuotesListActivity.pathsOfFileUri[0];
                        }
                        File pictureFile = new File(RecyclerQuotesListAdapter.this.filename);
                        try {
                            if (!pictureFile.exists()) {
                                pictureFile.createNewFile();
                            }
                            FileOutputStream ostream = new FileOutputStream(pictureFile);
                            if (inPNG) {
                                bitmap.compress(CompressFormat.PNG, 100, ostream);
                            } else {
                                Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
                                Canvas canvas = new Canvas(newBitmap);
                                canvas.drawColor(-1);
                                canvas.drawBitmap(bitmap, 0.0f, 0.0f, null);
                                newBitmap.compress(CompressFormat.JPEG, 100, ostream);
                                newBitmap.recycle();
                            }
                            ostream.flush();
                            ostream.close();
                            bitmap.recycle();
                            RecyclerQuotesListAdapter.this.context.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(pictureFile)));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Thread.sleep(1000);
                        pd.dismiss();
                        return;
                    }
                    Log.d(BuildConfig.FLAVOR, "Can't create directory to save image.");
                    Toast.makeText(RecyclerQuotesListAdapter.this.context, RecyclerQuotesListAdapter.this.context.getResources().getString(R.string.create_dir_err), Toast.LENGTH_LONG).show();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }).start();
        pd.setOnDismissListener(new OnDismissListener() {
            public void onDismiss(DialogInterface dialog) {
                if (checkSaveShare && !RecyclerQuotesListAdapter.this.filename.equals(BuildConfig.FLAVOR)) {
                    RecyclerQuotesListAdapter.this.shareImage(RecyclerQuotesListAdapter.this.filename, BuildConfig.FLAVOR, checkSaveShare);
                } else if (RecyclerQuotesListAdapter.this.filename.equals(BuildConfig.FLAVOR)) {
                    Toast.makeText(RecyclerQuotesListAdapter.this.context, RecyclerQuotesListAdapter.this.context.getResources().getString(R.string.error).toString(), Toast.LENGTH_SHORT).show();
                } else {
                    Intent quotesListActivity = new Intent(RecyclerQuotesListAdapter.this.context, SaveActivity.class);
                    quotesListActivity.putExtra("uri", RecyclerQuotesListAdapter.this.filename);
                    quotesListActivity.putExtra("way", "AdapterQuote");
                    quotesListActivity.putExtra("quote_edit", ((QuotesInfo) RecyclerQuotesListAdapter.this.quotesInfoList.get(position)).getQUOTES_TEXT());
                    int index = RecyclerQuotesListAdapter.this.noOfIndex[position];
                    if (index != -1) {
                        if (index == 9 || index == 10 || index == 11 || index == 12 || index == 13 || index == 14 || index == 18 || index == 19 || index == 20 || index == 21) {
                            quotesListActivity.putExtra("profile", "color");
                            quotesListActivity.putExtra("txtColor", "white");
                        } else if (index == 5) {
                            quotesListActivity.putExtra("profile", "color");
                            quotesListActivity.putExtra("txtColor", "black");
                        } else {
                            quotesListActivity.putExtra("profile", "bg");
                            quotesListActivity.putExtra("txtColor", "white");
                        }
                        quotesListActivity.putExtra("background", RecyclerQuotesListAdapter.this.imagesColorArr[index]);
                    } else {
                        quotesListActivity.putExtra("profile", "color");
                        quotesListActivity.putExtra("background", "#000000");
                        quotesListActivity.putExtra("txtColor", "white");
                    }
                    RecyclerQuotesListAdapter.this.context.startActivity(quotesListActivity);
                }
            }
        });
    }

    private void shareImage(String filePath, String strQuotes, boolean deleteIs) {
        File file = null;
        String sAux = BuildConfig.FLAVOR;
        if (!filePath.equals(BuildConfig.FLAVOR)) {
            file = new File(Uri.parse(filePath).getPath());
        }
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("image/*");
        intent.putExtra("android.intent.extra.SUBJECT", this.context.getResources().getString(R.string.app_name));
        if (filePath.equals(BuildConfig.FLAVOR)) {
            sAux = strQuotes;
        } else {
            sAux = (this.context.getResources().getString(R.string.sharetext) + " " + this.context.getResources().getString(R.string.app_name) + ". " + this.context.getResources().getString(R.string.sharetext1)) + "https://play.google.com/store/apps/details?id=" + this.context.getPackageName();
        }
        intent.putExtra("android.intent.extra.TEXT", sAux);
        if (!filePath.equals(BuildConfig.FLAVOR)) {
            intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(file));
        }
        this.context.startActivity(Intent.createChooser(intent, this.context.getString(R.string.share_via).toString()));
        if (!filePath.equals(BuildConfig.FLAVOR) && deleteIs) {
            QuotesListActivity.pathsOfFileUri[0] = filePath;
        }
    }
}

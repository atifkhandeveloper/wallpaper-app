package com.digiclack.wallpapers.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Layout.Alignment;
import android.text.Selection;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.UnderlineSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.digiclack.wallpapers.APPUtility;
import com.digiclack.wallpapers.AdAdmob;
import com.digiclack.wallpapers.OnColorChangedListener;
import com.digiclack.wallpapers.R;
import com.digiclack.wallpapers.activity.GPUImageFilterTools.FilterAdjuster;
import com.digiclack.wallpapers.activity.GPUImageFilterTools.FilterType;
import com.digiclack.wallpapers.adapter.StyleAdepter;
import com.digiclack.wallpapers.db.DatabaseHandler;
import com.digiclack.wallpapers.db.Quotes;
import com.digiclack.wallpapers.db.QuotesSelect;
import com.digiclack.wallpapers.interfacelistner.GetColorListener;
import com.digiclack.wallpapers.interfacelistner.GetTemplateListener;
import com.digiclack.wallpapers.interfacelistner.OnGetImgBackgndSelect;
import com.digiclack.wallpapers.quotesedit.ComponentInfo;
import com.digiclack.wallpapers.quotesedit.CustomShaderSpan;
import com.digiclack.wallpapers.quotesedit.CustomShadowSpan;
import com.digiclack.wallpapers.quotesedit.CustomTypefaceSpan;
import com.digiclack.wallpapers.quotesedit.MultiTouchListener;
import com.digiclack.wallpapers.quotesedit.MultiTouchListener.TouchCallbackListener;
import com.digiclack.wallpapers.quotesedit.PictureConstant;
import com.digiclack.wallpapers.quotesedit.RepeatListener;
import com.digiclack.wallpapers.quotesedit.ResizableImageview;
import com.digiclack.wallpapers.quotesedit.ResizableImageview.TouchEventListener;
import com.digiclack.wallpapers.quotesedit.SelectedTextData;
import com.digiclack.wallpapers.utility.Constants;
import com.digiclack.wallpapers.utility.CustomTextView;
import com.digiclack.wallpapers.utility.ImageUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFilterGroup;
import jp.co.cyberagent.android.gpuimage.GPUImageView;
import com.digiclack.wallpapers.LineColorPicker;
import com.digiclack.wallpapers.OnColorChangedListener;
import yuku.ambilwarna.AmbilWarnaDialog;
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener;
import yuku.ambilwarna.BuildConfig;

public class AddTextQuotesActivity extends Activity implements OnClickListener, OnSeekBarChangeListener, GetColorListener, GetTemplateListener, OnGetImgBackgndSelect, TouchCallbackListener, TouchEventListener {
    private static final int EDIT_QUOTE_RESULT = 901;
    private static final int GET_BG_RESULT = 904;
    private static final int GET_STICKER = 906;
    private static final int SELECT_BACKGROUND_FROM_CAMERA = 905;
    private static final int SEL_BACKGROUND_FROM_GALLERY = 907;
    private static final int WRITE_QUOTE_RESULT = 902;
    public static Bitmap bitmapOriginal;
    public static AddTextQuotesActivity c;
    public static boolean isTextTouchListener = true;
    int a;
    RelativeLayout alledit_ll;
    double angle = 0.0d;
    ArrayList<SelectedTextData> arrayfortv = new ArrayList();
    int b;
    ImageView back_arrow_add_quotes;
    int backgroundColor = -1;
    Bitmap bb;
    Bitmap bitRel;
    Bitmap bitmap;
    Button blur_lay_cntrl;
    LinearLayout blur_seekbar_lay;
    Button bold;
    ImageView btn_down;
    ImageView btn_left;
    ImageView btn_right;
    ImageView btn_top;
    SpannableStringBuilder builder;
    float cX = 0.0f;
    float cY = 0.0f;
    private String categoryBG = BuildConfig.FLAVOR;
    private String categoryQuote = BuildConfig.FLAVOR;
    private String categorySticker = BuildConfig.FLAVOR;
    boolean ch = false;
    int chang = 3;
    CustomTextView clr_opacity_txt;
    RelativeLayout cntrls_stkr_lay;
    RelativeLayout cntrls_stkrclr_lay;
    private int color;
    LineColorPicker colorPicker;
    ImageView color_;
    CustomTextView color_punch;
    RelativeLayout color_rel;
    CustomTextView color_txt;
    RelativeLayout complete_img;
    CustomTextView contrl_txt;
    Button controll_btn_stckr;
    RelativeLayout controller_sticker;
    int count;
    double dAngle = 0.0d;
    DatabaseHandler db;
    DatabaseHandler dbHelper;
    String defaultText = "Tap on word to highlight";
    int defaultcolor_all = -1;

    RelativeLayout rr_addtext;

    Bitmap addtext_to_sticker;


    private OnClickListener deleteClickListener = new OnClickListener() {
        public void onClick(View v) {
            AddTextQuotesActivity.this.selectFocus = true;
            AddTextQuotesActivity.this.ft1 = BuildConfig.FLAVOR;
            AddTextQuotesActivity.this.shr1 = "null";
            View view = AddTextQuotesActivity.this.getCurrentFocus();
            if (view != null) {
                ((InputMethodManager) AddTextQuotesActivity.this.getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
            ((View) v.getParent()).setVisibility(View.GONE);
            ((TextView) ((ViewGroup) v.getParent()).getChildAt(0)).setText("Ultimate Textgram text for demo");
            if (AddTextQuotesActivity.this.textView.getPaintFlags() == 1289 || AddTextQuotesActivity.this.textView.getPaintFlags() == 1305 || AddTextQuotesActivity.this.textView.getPaintFlags() == 1297) {
                AddTextQuotesActivity.this.textView.setPaintFlags(AddTextQuotesActivity.this.textView.getPaintFlags() & -9);
                AddTextQuotesActivity.this.textView.setPaintFlags(AddTextQuotesActivity.this.textView.getPaintFlags() & -17);
            }
            if (AddTextQuotesActivity.this.alledit_ll.getVisibility() == View.VISIBLE) {
                AddTextQuotesActivity.this.menu_ll.setVisibility(View.VISIBLE);
                AddTextQuotesActivity.this.alledit_ll.setVisibility(View.GONE);
                AddTextQuotesActivity.this.sb_effectsfilter.setVisibility(View.VISIBLE);
                AddTextQuotesActivity.this.sb_opctyfilter.setVisibility(View.VISIBLE);
                AddTextQuotesActivity.this.blur_lay_cntrl.setVisibility(View.VISIBLE);
                AddTextQuotesActivity.this.blur_seekbar_lay.setVisibility(View.VISIBLE);
            }
            if (AddTextQuotesActivity.this.textView.getTag().equals("text_tv")) {
                AddTextQuotesActivity.this.arrayfortv.clear();
            }
            if (AddTextQuotesActivity.this.rl.getVisibility() == View.VISIBLE) {
                AddTextQuotesActivity.this.text_tv.clearFocus();
                Selection.removeSelection(AddTextQuotesActivity.this.text_tv.getText());
                AddTextQuotesActivity.this.text_tv.setCursorVisible(false);
                AddTextQuotesActivity.this.fortext.removeView(AddTextQuotesActivity.this.rl);
            }
        }
    };
    ImageView delete_iv;
    Button done_add_quotes;
    String drawableName;
    Button duplicate;
    boolean ed = false;
    private OnClickListener editTextClickListener = new OnClickListener() {
        public void onClick(View v) {
            String quote = BuildConfig.FLAVOR;
            if (AddTextQuotesActivity.this.textView.getTag().equals("text_tv")) {
                quote = AddTextQuotesActivity.this.setSelectionAll1(AddTextQuotesActivity.this.text_tv);
            }
            AddTextQuotesActivity.this.selectFocus = false;
            AddTextQuotesActivity.this.storedArray.clear();
            Intent intent = new Intent(AddTextQuotesActivity.this, EditQuoteActivity.class);
            intent.putExtra("quote", quote);
            AddTextQuotesActivity.this.startActivityForResult(intent, AddTextQuotesActivity.EDIT_QUOTE_RESULT);
        }
    };
    CustomTextView edit_ivTxt;
    CustomTextView effect_txt;
    int end;
    int existingcolor = -1;
    private File f = null;
    String filename;
    FilterType filterType = FilterType.GAUSSIAN_BLUR;
    boolean flag = false;
    boolean flag2 = false;
    boolean flgPunch;
    CustomTextView font_punch;
    RelativeLayout font_rel;
    CustomTextView font_txt;
    ImageView fonts;
    ImageView footer_effects_image;
    CustomTextView format_punch;
    CustomTextView format_txt;
    LinearLayout formatall_type;
    RelativeLayout fortext;
    String ft1 = BuildConfig.FLAVOR;
    boolean ftrue = true;
    Button g1;
    Button g2;
    Button g3;
    GestureDetector gd;
    GPUImageView gpuImageview;
    SeekBar hue_seekbar;
    ImageView img_color_punch;
    ImageView img_color_txt;
    ImageView img_font_punch;
    ImageView img_font_txt;
    ImageView img_format_punch;
    ImageView img_format_txt;
    ImageView img_opacity;
    ImageView img_shader_punch;
    ImageView img_shader_txt;
    ImageView img_shadow_punch;
    ImageView img_shadow_txt;
    String intent_quote;
    boolean isBlurLayVisible = true;
    boolean isStickerLayVisible = true;
    boolean isUpadted = false;
    boolean isfrst = true;
    Button italic;
    RelativeLayout la_color;
    RelativeLayout la_fonts;
    RelativeLayout la_shader;
    RelativeLayout la_shadow;
    RelativeLayout la_size;
    RelativeLayout lay_colorfilter;
    RelativeLayout lay_hue;
    RelativeLayout lay_viewStyle;
    ImageView list_of_brnd;
    ImageView list_of_sticker;
    ImageView list_of_style;
    GPUImageFilter mFilter;
    FilterAdjuster mFilterAdjuster;
    private OnTouchListener mTouchListener = new OnTouchListener() {
        public boolean onTouch(View view, MotionEvent event) {
            AddTextQuotesActivity.this.selectFocus = true;
            if (!view.getTag().equals("move_iv")) {
                if (!view.getTag().equals("scale_iv")) {
                    if (view.getTag().equals("rotate_iv")) {

                        Log.e("11111", "sticker view action move");
                        switch (event.getAction()) {
                            case 0:
                                AddTextQuotesActivity.this.hideEditRel();
                                Rect rect = new Rect();
                                ((View) view.getParent()).getGlobalVisibleRect(rect);
                                AddTextQuotesActivity.this.cX = rect.exactCenterX();
                                AddTextQuotesActivity.this.cY = rect.exactCenterY();
                                AddTextQuotesActivity.this.vAngle = (double) ((View) view.getParent()).getRotation();
                                AddTextQuotesActivity.this.tAngle = (Math.atan2((double) (AddTextQuotesActivity.this.cY - event.getRawY()), (double) (AddTextQuotesActivity.this.cX - event.getRawX())) * 180.0d) / 3.141592653589793d;
                                AddTextQuotesActivity.this.dAngle = AddTextQuotesActivity.this.vAngle - AddTextQuotesActivity.this.tAngle;
                                break;
                            case 1:
                                AddTextQuotesActivity.this.checkandShowEditRel();
                                break;
                            case 2:
                                AddTextQuotesActivity.this.angle = (Math.atan2((double) (AddTextQuotesActivity.this.cY - event.getRawY()), (double) (AddTextQuotesActivity.this.cX - event.getRawX())) * 180.0d) / 3.141592653589793d;
                                float rotation = (float) (AddTextQuotesActivity.this.angle + AddTextQuotesActivity.this.dAngle);
                                if (rotation > -5.0f && rotation < 5.0f) {
                                    rotation = 0.0f;
                                }
                                Log.i("rotation", BuildConfig.FLAVOR + (AddTextQuotesActivity.this.angle + AddTextQuotesActivity.this.dAngle));
                                ((View) view.getParent()).setRotation(rotation);
                                break;
                            default:
                                break;
                        }
                    }

                }

                Log.e("22222", "sticker view action move");

                switch (event.getAction()) {

                    case 0:
                        view.bringToFront();
                        AddTextQuotesActivity.this.hideEditRel();
                        AddTextQuotesActivity.this.move_orgX = event.getRawX();
                        AddTextQuotesActivity.this.move_orgY = event.getRawY();
                        AddTextQuotesActivity.this.rlX = ((View) view.getParent()).getX();
                        AddTextQuotesActivity.this.rlY = ((View) view.getParent()).getY();
                        break;
                    case 1:
                        AddTextQuotesActivity.this.checkandShowEditRel();
                        break;
                    case 2:
                        Log.e("scale_iv", "sticker view action move");
                        float offsetX = event.getRawX() - AddTextQuotesActivity.this.move_orgX;
                        float offsetY = event.getRawY() - AddTextQuotesActivity.this.move_orgY;
                        ((View) view.getParent()).setX(AddTextQuotesActivity.this.rlX);
                        ((View) view.getParent()).setY(AddTextQuotesActivity.this.rlY);
                        int nw_width = (int) (((float) ((View) view.getParent()).getLayoutParams().width) + offsetX);
                        int nw_Height = (int) (((float) ((View) view.getParent()).getLayoutParams().height) + offsetY);
                        if (nw_width > AddTextQuotesActivity.this.fortext.getWidth()) {
                            nw_width = AddTextQuotesActivity.this.fortext.getWidth();
                        }
                        if (nw_Height > AddTextQuotesActivity.this.fortext.getHeight()) {
                            nw_Height = AddTextQuotesActivity.this.fortext.getHeight();
                        }
                        AddTextQuotesActivity.this.move_orgX = event.getRawX();
                        AddTextQuotesActivity.this.move_orgY = event.getRawY();
                        if (((float) nw_width) >= AddTextQuotesActivity.this.view_width) {
                            if (((float) nw_Height) >= AddTextQuotesActivity.this.view_height) {
                                ((View) view.getParent()).getLayoutParams().width = nw_width;
                                ((View) view.getParent()).getLayoutParams().height = nw_Height;
                                ((View) view.getParent()).postInvalidate();
                                ((View) view.getParent()).requestLayout();
                                AddTextQuotesActivity.this.updateTextSizeonScale(Math.abs(nw_width), Math.abs(nw_Height));
                                break;
                            }
                            return true;
                        }
                        return true;
                    default:
                        break;
                }
            }

            Log.e("333333", "sticker view action move");

            switch (event.getAction()) {
                case 0:
                    AddTextQuotesActivity.this.move_orgX = event.getRawX();
                    AddTextQuotesActivity.this.move_orgY = event.getRawY();
                    break;
                case 1:
                    AddTextQuotesActivity.this.checkandShowEditRel();
                    break;
                case 2:
                    Float offsetY = event.getRawY() - AddTextQuotesActivity.this.move_orgY;
                    ((View) view.getParent()).setX(((View) view.getParent()).getX() + (event.getRawX() - AddTextQuotesActivity.this.move_orgX));
                    ((View) view.getParent()).setY(((View) view.getParent()).getY() + offsetY);
                    AddTextQuotesActivity.this.move_orgX = event.getRawX();
                    AddTextQuotesActivity.this.move_orgY = event.getRawY();
                    break;
            }
            return true;
        }
    };
    int max = 0;
    LinearLayout menu_ll;
    int min = 0;
    String modeClrSelection = "textclr";
    String modeOfChosingPic;
    String modefont = BuildConfig.FLAVOR;
    String modefontselection = "textfont";
    String modeformatselection = "textformat";
    String modeshaderselection = "textshader";
    String modeshadowselection = "textshadow";
    ImageView move_iv;
    float move_orgX = -1.0f;
    float move_orgY = -1.0f;
    int oldlength = 0;
    String[] pallete = new String[]{"#ffffff", "#cccccc", "#999999", "#666666", "#000000", "#ffd700", "#daa520", "#b8860b", "#b8860b", "#ccff66", "#adff2f", "#00ff00", "#32cd32", "#3cb371", "#99cccc", "#66cccc", "#339999", "#669999", "#006666", "#ffcccc", "#ff9999", "#ff6666", "#ff3333", "#cc0033"};
    LayoutParams params_rl;
    CustomTextView picture_txt;
    String pos;
    SharedPreferences preferences;
    float r;
    float rat1;
    RelativeLayout re_background;
    RelativeLayout re_sticker;
    RelativeLayout re_template;
    RecyclerView recyclerView;
    RelativeLayout rel_effects;
    RelativeLayout rl;
    float rlX;
    float rlY;
    ImageView rotate_iv;
    SeekBar sb_effects;
    SeekBar sb_effectsfilter;
    SeekBar sb_opctyfilter;
    String sc_hght1 = "200";
    String sc_wdth1 = "200";
    ImageView scale_iv;
    float screenHeight;
    float screenWidth;
    RelativeLayout scroll_all;
    LinearLayout scroll_of_all_backgrounds;
    LinearLayout scroll_of_all_effects;
    Button sd_color;
    boolean seekflag = true;
    boolean selectFocus = true;
    boolean selecttemplate = true;
    Button sh;
    Button sh1;
    Button sh10;
    Button sh2;
    Button sh3;
    Button sh4;
    Button sh5;
    Button sh6;
    Button sh7;
    Button sh8;
    Button sh9;
    ImageView shader;
    CustomTextView shader_punch;
    RelativeLayout shader_rel;
    CustomTextView shader_txt;
    ImageView shadow;
    CustomTextView shadow_punch;
    RelativeLayout shadow_rel;
    SeekBar shadow_seekbar;
    CustomTextView shadow_txt;
    int shadowcolor = -16777216;
    String shr1 = "null";
    ImageView size;
    String sizeSt1 = "20";
    SpannableString spannableString;
    int start;
    CustomTextView sticker_txt;
    ArrayList<SelectedTextData> storedArray = new ArrayList();
    Button strike;
    RecyclerView styl_recycler;
    double tAngle = 0.0d;
    RelativeLayout tab_clrs_stkr;
    RelativeLayout tab_cntrl_stkr;
    RelativeLayout tab_font_punch;
    RelativeLayout tab_font_txt;
    RelativeLayout tab_format_punch;
    RelativeLayout tab_format_txt;
    RelativeLayout tab_punch;
    RelativeLayout tab_shader_punch;
    RelativeLayout tab_shader_txt;
    RelativeLayout tab_shadow_punch;
    RelativeLayout tab_shadow_txt;
    RelativeLayout tab_text;
    int temp_postn = 1;
    String template = "t1";
    int textSizeOffset = 5;
    private OnTouchListener textTouchListener = new OnTouchListener() {
        public boolean onTouch(View view, MotionEvent event) {
            AddTextQuotesActivity.this.removeImageViewControll();
            AddTextQuotesActivity.this.textView.setLongClickable(false);

            if (android.os.Build.VERSION.SDK_INT >= 21) {
                AddTextQuotesActivity.this.textView.setShowSoftInputOnFocus(false);
            } else {
            }
            AddTextQuotesActivity.this.setDefault();
            if (AddTextQuotesActivity.this.rl.getChildAt(1).getVisibility() == View.VISIBLE) {
                if (AddTextQuotesActivity.this.flag) {
                    AddTextQuotesActivity.this.textView.setCursorVisible(false);

                } else {
                    AddTextQuotesActivity.this.textView.setCursorVisible(false);

                    AddTextQuotesActivity.this.flag = true;
                }
            }
            switch (event.getAction()) {
                case 0:
                    int i;
                    ((View) view.getParent()).bringToFront();
                    AddTextQuotesActivity.this.ed = false;
                    if (AddTextQuotesActivity.this.textView != null) {
                        int noch = ((ViewGroup) AddTextQuotesActivity.this.textView.getParent()).getChildCount();
                        Log.e("22222", "xxxx" + noch);
                        for (i = 1; i < noch; i++) {

                            ((ViewGroup) AddTextQuotesActivity.this.textView.getParent()).getChildAt(i).setVisibility(View.INVISIBLE);
                        }
                    }
                    int noch1 = ((ViewGroup) view.getParent()).getChildCount();
                    for (i = 1; i < noch1; i++) {
                        Log.e("33333", "xxxx" + noch1);
                        View child = ((ViewGroup) view.getParent()).getChildAt(i);
                        if (i != 3) {
                            child.setVisibility(View.VISIBLE);
                        }
                    }
                    AddTextQuotesActivity.this.checkandShowEditRel();
                    AddTextQuotesActivity.this.textView = (EditText) view;
                    AddTextQuotesActivity.this.flgPunch = true;
                    AddTextQuotesActivity.this.move_orgX = event.getRawX();
                    AddTextQuotesActivity.this.move_orgY = event.getRawY();
                    break;
                case 1:
                    AddTextQuotesActivity.this.checkandShowEditRel();
                    if (!AddTextQuotesActivity.this.flgPunch) {
                        AddTextQuotesActivity.this.textView.setCursorVisible(false);
                        break;
                    }
                    AddTextQuotesActivity.this.textView.setCursorVisible(false);
                    break;
                case 2:
                    AddTextQuotesActivity.this.flgPunch = false;
                    if (AddTextQuotesActivity.this.alledit_ll.getVisibility() == View.VISIBLE) {
                        AddTextQuotesActivity.this.menu_ll.setVisibility(View.VISIBLE);
                        AddTextQuotesActivity.this.alledit_ll.setVisibility(View.GONE);
                    }
                    AddTextQuotesActivity.this.textView.setCursorVisible(false);
                    float offsetY = event.getRawY() - AddTextQuotesActivity.this.move_orgY;
                    ((View) view.getParent()).setX(((View) view.getParent()).getX() + (event.getRawX() - AddTextQuotesActivity.this.move_orgX));
                    ((View) view.getParent()).setY(((View) view.getParent()).getY() + offsetY);
                    AddTextQuotesActivity.this.move_orgX = event.getRawX();
                    AddTextQuotesActivity.this.move_orgY = event.getRawY();
                    break;
            }
            return false;
        }
    };


    EditText textView = null;
    String text_angle_1 = "0";
    EditText text_tv;
    RelativeLayout top_option;
    SeekBar transparency_seekbar;
    Typeface ttD;
    Typeface ttf1;
    Typeface ttf10;
    Typeface ttf11;
    Typeface ttf12;
    Typeface ttf13;
    Typeface ttf14;
    Typeface ttf15;
    Typeface ttf16;
    Typeface ttf17;
    Typeface ttf18;
    Typeface ttf19;
    Typeface ttf2;
    Typeface ttf20;
    Typeface ttf21;
    Typeface ttf22;
    Typeface ttf23;
    Typeface ttf24;
    Typeface ttf25;
    Typeface ttf26;
    Typeface ttf27;
    Typeface ttf28;
    Typeface ttf29;
    Typeface ttf3;
    Typeface ttf4;
    Typeface ttf5;
    Typeface ttf6;
    Typeface ttf7;
    Typeface ttf8;
    Typeface ttf9;
    Button underline;
    double vAngle = 0.0d;
    float view_height;
    float view_width;
    String x1 = "0.0f";
    String y1 = "0.0f";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_main1);
        initialize();
        c = this;
        this.db = new DatabaseHandler(this);
        this.preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (!this.preferences.getBoolean("isAdsDisabled", false)) {

        }
        DisplayMetrics dimension = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dimension);
        this.screenWidth = (float) dimension.widthPixels;
        this.screenHeight = (float) (dimension.heightPixels - Constants.dpToPx(this, R.styleable.AppCompatTheme_spinnerStyle));
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            this.pos = extra.getString("positn");
            if (this.pos.equals("main")) {
                this.re_background.setVisibility(View.VISIBLE);
                temp_Referesh();
            }
        }
        this.intent_quote = getIntent().getStringExtra("quote_edit");
        this.modeOfChosingPic = getIntent().getStringExtra("modeOfChosingPic");
        String profile = getIntent().getStringExtra("profile");
        String mDrawableName = getIntent().getStringExtra("background");
        if (this.modeOfChosingPic.equals("chooserActivity")) {
            this.drawableName = mDrawableName;
            if (profile.equals("color")) {
                this.backgroundColor = Color.parseColor(mDrawableName);
                this.bitmap = Bitmap.createBitmap(300, 300, Config.ARGB_8888);
                this.bitmap.eraseColor(this.backgroundColor);
            } else {
                Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(mDrawableName, "drawable", getPackageName()));
                int width = bitmap1.getWidth();
                int height = bitmap1.getHeight();
                if (width < height) {
                    height = width;
                } else {
                    width = height;
                }
                this.bitmap = Constants.cropCenterBitmap(bitmap1, width, height);
            }
            this.bitmap = ImageUtils.resizeBitmap(this.bitmap, (int) this.screenWidth, (int) this.screenWidth);
            setImageInBackgrounds(this.bitmap);
        } else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.image_error), Toast.LENGTH_SHORT).show();
            finish();
        }
        applyGusianBlur();
        addNormalTextTemplateMethod(this.intent_quote);
        this.shadow_seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            int shadow_color;
            float shadow_dx;
            float shadow_dy;
            float shadow_radius;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float shadow = (float) (progress / 2);
                if (fromUser) {
                    AddTextQuotesActivity.this.selectFocus = true;
                    AddTextQuotesActivity.this.fstCallMethod();
                    if (this.shadow_color == 0) {
                        this.shadow_color = -16777216;
                    }
                    if (AddTextQuotesActivity.this.modeshadowselection.equals("textshadow")) {
                        AddTextQuotesActivity.this.textView.setShadowLayer(shadow, shadow, shadow, this.shadow_color);
                    } else {
                        AddTextQuotesActivity.this.setShadowonSelected(shadow, shadow, shadow, this.shadow_color);
                    }
                }
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                if (AddTextQuotesActivity.this.textView.getTag().equals("text_tv")) {
                    if (AddTextQuotesActivity.this.text_tv.getSelectionStart() < 0) {
                    }
                    if (AddTextQuotesActivity.this.text_tv.getSelectionEnd() < 0) {
                    }
                    if (!AddTextQuotesActivity.this.modeshadowselection.equals("punchshadow")) {
                        this.shadow_color = AddTextQuotesActivity.this.text_tv.getShadowColor();
                        this.shadow_dx = AddTextQuotesActivity.this.text_tv.getShadowDx();
                        this.shadow_dy = AddTextQuotesActivity.this.text_tv.getShadowDy();
                        this.shadow_radius = AddTextQuotesActivity.this.text_tv.getShadowRadius();
                    } else if (AddTextQuotesActivity.this.arrayfortv.size() > 0) {
                        SelectedTextData d = (SelectedTextData) AddTextQuotesActivity.this.arrayfortv.get(0);
                        this.shadow_color = d.getText_shadowcolor();
                        this.shadow_dx = d.getText_shadowdx();
                        this.shadow_dy = d.getText_shadowdy();
                        this.shadow_radius = d.getText_shadowradius();
                    }
                }
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        this.g1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                AddTextQuotesActivity.this.selectFocus = true;
                AddTextQuotesActivity.this.textView.setGravity(3);
                AddTextQuotesActivity.this.bold.setBackgroundResource(R.drawable.bold);
                AddTextQuotesActivity.this.italic.setBackgroundResource(R.drawable.italic);
                AddTextQuotesActivity.this.underline.setBackgroundResource(R.drawable.underline);
                AddTextQuotesActivity.this.strike.setBackgroundResource(R.drawable.text);
                AddTextQuotesActivity.this.g1.setBackgroundResource(R.drawable.left_align_text1);
                AddTextQuotesActivity.this.g2.setBackgroundResource(R.drawable.center_align_text);
                AddTextQuotesActivity.this.g3.setBackgroundResource(R.drawable.right_align_text);
            }
        });
        this.g2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                AddTextQuotesActivity.this.selectFocus = true;
                AddTextQuotesActivity.this.textView.setGravity(1);
                AddTextQuotesActivity.this.bold.setBackgroundResource(R.drawable.bold);
                AddTextQuotesActivity.this.italic.setBackgroundResource(R.drawable.italic);
                AddTextQuotesActivity.this.underline.setBackgroundResource(R.drawable.underline);
                AddTextQuotesActivity.this.strike.setBackgroundResource(R.drawable.text);
                AddTextQuotesActivity.this.g1.setBackgroundResource(R.drawable.left_align_text);
                AddTextQuotesActivity.this.g2.setBackgroundResource(R.drawable.center_align_text1);
                AddTextQuotesActivity.this.g3.setBackgroundResource(R.drawable.right_align_text);
            }
        });
        this.g3.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                AddTextQuotesActivity.this.selectFocus = true;
                AddTextQuotesActivity.this.textView.setGravity(5);
                AddTextQuotesActivity.this.bold.setBackgroundResource(R.drawable.bold);
                AddTextQuotesActivity.this.italic.setBackgroundResource(R.drawable.italic);
                AddTextQuotesActivity.this.underline.setBackgroundResource(R.drawable.underline);
                AddTextQuotesActivity.this.strike.setBackgroundResource(R.drawable.text);
                AddTextQuotesActivity.this.g1.setBackgroundResource(R.drawable.left_align_text);
                AddTextQuotesActivity.this.g2.setBackgroundResource(R.drawable.center_align_text);
                AddTextQuotesActivity.this.g3.setBackgroundResource(R.drawable.right_align_text1);
            }
        });
        this.sd_color.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                AddTextQuotesActivity.this.selectFocus = true;
                if (AddTextQuotesActivity.this.modeshadowselection.equals("textshadow")) {
                    AddTextQuotesActivity.this.shadowcolor = AddTextQuotesActivity.this.textView.getShadowColor();
                } else {
                    ArrayList<SelectedTextData> alstd = null;
                    if (AddTextQuotesActivity.this.textView.getTag().equals("text_tv")) {
                        alstd = AddTextQuotesActivity.this.arrayfortv;
                    }
                    if (alstd.size() > 0) {
                        SelectedTextData d = (SelectedTextData) alstd.get(0);
                        AddTextQuotesActivity.this.shadowcolor = d.getText_shadowcolor();
                    }
                }
                new AmbilWarnaDialog(AddTextQuotesActivity.this, AddTextQuotesActivity.this.shadowcolor, new OnAmbilWarnaListener() {
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        AddTextQuotesActivity.this.fstCallMethod();
                        if (AddTextQuotesActivity.this.modeshadowselection.equals("textshadow")) {
                            AddTextQuotesActivity.this.textView.setShadowLayer(AddTextQuotesActivity.this.textView.getShadowRadius(), AddTextQuotesActivity.this.textView.getShadowDx(), AddTextQuotesActivity.this.textView.getShadowDy(), color);
                            AddTextQuotesActivity.this.shadowcolor = color;
                            return;
                        }
                        ArrayList<SelectedTextData> alstd = null;
                        if (AddTextQuotesActivity.this.textView.getTag().equals("text_tv")) {
                            alstd = AddTextQuotesActivity.this.arrayfortv;
                        }
                        if (alstd.size() > 0) {
                            AddTextQuotesActivity.this.setShadowonSelected(((SelectedTextData) alstd.get(alstd.size() - 1)).getText_shadowradius(), ((SelectedTextData) alstd.get(alstd.size() - 1)).getText_shadowdx(), ((SelectedTextData) alstd.get(alstd.size() - 1)).getText_shadowdy(), color);
                            AddTextQuotesActivity.this.shadowcolor = color;
                        }
                    }

                    public void onCancel(AmbilWarnaDialog dialog) {
                    }
                }).show();
            }
        });
        this.sh.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                AddTextQuotesActivity.this.selectFocus = true;
                AddTextQuotesActivity.this.fstCallMethod();
                if (AddTextQuotesActivity.this.modeshaderselection.equals("textshader")) {
                    AddTextQuotesActivity.this.shr1 = "null";
                    AddTextQuotesActivity.this.textView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                    AddTextQuotesActivity.this.textView.getPaint().setShader(null);
                    AddTextQuotesActivity.this.textView.invalidate();
                    AddTextQuotesActivity.this.textView.requestLayout();
                } else {
                    AddTextQuotesActivity.this.setShaderonSelected("null");
                }
                AddTextQuotesActivity.this.updateColors();
            }
        });
        this.sh1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                AddTextQuotesActivity.this.selectFocus = true;
                Shader shader = new BitmapShader(BitmapFactory.decodeResource(AddTextQuotesActivity.this.getResources(), R.drawable.sh1), TileMode.MIRROR, TileMode.MIRROR);
                AddTextQuotesActivity.this.fstCallMethod();
                if (AddTextQuotesActivity.this.modeshaderselection.equals("textshader")) {
                    AddTextQuotesActivity.this.textView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                    AddTextQuotesActivity.this.textView.getPaint().setShader(shader);
                    AddTextQuotesActivity.this.textView.invalidate();
                    AddTextQuotesActivity.this.textView.requestLayout();
                    AddTextQuotesActivity.this.shaderTag("shs1");
                } else {
                    AddTextQuotesActivity.this.setShaderonSelected("shs1");
                }
                AddTextQuotesActivity.this.updateColors();
            }
        });
        this.sh2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                AddTextQuotesActivity.this.selectFocus = true;
                Shader shader = new BitmapShader(BitmapFactory.decodeResource(AddTextQuotesActivity.this.getResources(), R.drawable.sh2), TileMode.MIRROR, TileMode.MIRROR);
                AddTextQuotesActivity.this.fstCallMethod();
                if (AddTextQuotesActivity.this.modeshaderselection.equals("textshader")) {
                    AddTextQuotesActivity.this.textView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                    AddTextQuotesActivity.this.textView.getPaint().setShader(shader);
                    AddTextQuotesActivity.this.textView.invalidate();
                    AddTextQuotesActivity.this.textView.requestLayout();
                    AddTextQuotesActivity.this.shaderTag("shs2");
                } else {
                    AddTextQuotesActivity.this.setShaderonSelected("shs2");
                }
                AddTextQuotesActivity.this.updateColors();
            }
        });
        this.sh3.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                AddTextQuotesActivity.this.selectFocus = true;
                Shader shader = new BitmapShader(BitmapFactory.decodeResource(AddTextQuotesActivity.this.getResources(), R.drawable.sh3), TileMode.MIRROR, TileMode.MIRROR);
                AddTextQuotesActivity.this.fstCallMethod();
                if (AddTextQuotesActivity.this.modeshaderselection.equals("textshader")) {
                    AddTextQuotesActivity.this.textView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                    AddTextQuotesActivity.this.textView.getPaint().setShader(shader);
                    AddTextQuotesActivity.this.textView.invalidate();
                    AddTextQuotesActivity.this.textView.requestLayout();
                    AddTextQuotesActivity.this.shaderTag("shs3");
                } else {
                    AddTextQuotesActivity.this.setShaderonSelected("shs3");
                }
                AddTextQuotesActivity.this.updateColors();
            }
        });
        this.sh4.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                AddTextQuotesActivity.this.selectFocus = true;
                Shader shader = new BitmapShader(BitmapFactory.decodeResource(AddTextQuotesActivity.this.getResources(), R.drawable.sh4), TileMode.MIRROR, TileMode.MIRROR);
                AddTextQuotesActivity.this.fstCallMethod();
                if (AddTextQuotesActivity.this.modeshaderselection.equals("textshader")) {
                    AddTextQuotesActivity.this.textView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                    AddTextQuotesActivity.this.textView.getPaint().setShader(shader);
                    AddTextQuotesActivity.this.textView.invalidate();
                    AddTextQuotesActivity.this.textView.requestLayout();
                    AddTextQuotesActivity.this.shaderTag("shs4");
                } else {
                    AddTextQuotesActivity.this.setShaderonSelected("shs4");
                }
                AddTextQuotesActivity.this.updateColors();
            }
        });
        this.sh5.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                AddTextQuotesActivity.this.selectFocus = true;
                Shader shader = new BitmapShader(BitmapFactory.decodeResource(AddTextQuotesActivity.this.getResources(), R.drawable.sh5), TileMode.MIRROR, TileMode.MIRROR);
                AddTextQuotesActivity.this.fstCallMethod();
                if (AddTextQuotesActivity.this.modeshaderselection.equals("textshader")) {
                    AddTextQuotesActivity.this.textView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                    AddTextQuotesActivity.this.textView.getPaint().setShader(shader);
                    AddTextQuotesActivity.this.textView.invalidate();
                    AddTextQuotesActivity.this.textView.requestLayout();
                    AddTextQuotesActivity.this.shaderTag("shs5");
                } else {
                    AddTextQuotesActivity.this.setShaderonSelected("shs5");
                }
                AddTextQuotesActivity.this.updateColors();
            }
        });
        this.sh6.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                AddTextQuotesActivity.this.selectFocus = true;
                Shader shader = new BitmapShader(BitmapFactory.decodeResource(AddTextQuotesActivity.this.getResources(), R.drawable.sh6), TileMode.MIRROR, TileMode.MIRROR);
                AddTextQuotesActivity.this.fstCallMethod();
                if (AddTextQuotesActivity.this.modeshaderselection.equals("textshader")) {
                    AddTextQuotesActivity.this.textView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                    AddTextQuotesActivity.this.textView.getPaint().setShader(shader);
                    AddTextQuotesActivity.this.textView.invalidate();
                    AddTextQuotesActivity.this.textView.requestLayout();
                    AddTextQuotesActivity.this.shaderTag("shs6");
                } else {
                    AddTextQuotesActivity.this.setShaderonSelected("shs6");
                }
                AddTextQuotesActivity.this.updateColors();
            }
        });
        this.sh7.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                AddTextQuotesActivity.this.selectFocus = true;
                Shader shader = new BitmapShader(BitmapFactory.decodeResource(AddTextQuotesActivity.this.getResources(), R.drawable.sh7), TileMode.MIRROR, TileMode.MIRROR);
                AddTextQuotesActivity.this.fstCallMethod();
                if (AddTextQuotesActivity.this.modeshaderselection.equals("textshader")) {
                    AddTextQuotesActivity.this.textView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                    AddTextQuotesActivity.this.textView.getPaint().setShader(shader);
                    AddTextQuotesActivity.this.textView.invalidate();
                    AddTextQuotesActivity.this.textView.requestLayout();
                    AddTextQuotesActivity.this.shaderTag("shs7");
                } else {
                    AddTextQuotesActivity.this.setShaderonSelected("shs7");
                }
                AddTextQuotesActivity.this.updateColors();
            }
        });
        this.sh8.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                AddTextQuotesActivity.this.selectFocus = true;
                Shader shader = new BitmapShader(BitmapFactory.decodeResource(AddTextQuotesActivity.this.getResources(), R.drawable.sh8), TileMode.MIRROR, TileMode.MIRROR);
                AddTextQuotesActivity.this.fstCallMethod();
                if (AddTextQuotesActivity.this.modeshaderselection.equals("textshader")) {
                    AddTextQuotesActivity.this.textView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                    AddTextQuotesActivity.this.textView.getPaint().setShader(shader);
                    AddTextQuotesActivity.this.textView.invalidate();
                    AddTextQuotesActivity.this.textView.requestLayout();
                    AddTextQuotesActivity.this.shaderTag("shs8");
                } else {
                    AddTextQuotesActivity.this.setShaderonSelected("shs8");
                }
                AddTextQuotesActivity.this.updateColors();
            }
        });
        this.sh9.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                AddTextQuotesActivity.this.selectFocus = true;
                Shader shader = new BitmapShader(BitmapFactory.decodeResource(AddTextQuotesActivity.this.getResources(), R.drawable.sh9), TileMode.MIRROR, TileMode.MIRROR);
                AddTextQuotesActivity.this.fstCallMethod();
                if (AddTextQuotesActivity.this.modeshaderselection.equals("textshader")) {
                    AddTextQuotesActivity.this.textView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                    AddTextQuotesActivity.this.textView.getPaint().setShader(shader);
                    AddTextQuotesActivity.this.textView.invalidate();
                    AddTextQuotesActivity.this.textView.requestLayout();
                    AddTextQuotesActivity.this.shaderTag("shs9");
                } else {
                    AddTextQuotesActivity.this.setShaderonSelected("shs9");
                }
                AddTextQuotesActivity.this.updateColors();
            }
        });
        this.sh10.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                AddTextQuotesActivity.this.selectFocus = true;
                Shader shader = new BitmapShader(BitmapFactory.decodeResource(AddTextQuotesActivity.this.getResources(), R.drawable.sh10), TileMode.MIRROR, TileMode.MIRROR);
                AddTextQuotesActivity.this.fstCallMethod();
                if (AddTextQuotesActivity.this.modeshaderselection.equals("textshader")) {
                    AddTextQuotesActivity.this.textView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                    AddTextQuotesActivity.this.textView.getPaint().setShader(shader);
                    AddTextQuotesActivity.this.textView.invalidate();
                    AddTextQuotesActivity.this.textView.requestLayout();
                    AddTextQuotesActivity.this.shaderTag("shs10");
                } else {
                    AddTextQuotesActivity.this.setShaderonSelected("shs10");
                }
                AddTextQuotesActivity.this.updateColors();
            }
        });
        this.bold.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                AddTextQuotesActivity.this.selectFocus = true;
                AddTextQuotesActivity.this.fstCallMethod();
                if (AddTextQuotesActivity.this.modeformatselection.equals("textformat")) {
                    if (AddTextQuotesActivity.this.textView.getTypeface().getStyle() == Typeface.BOLD) {
                        AddTextQuotesActivity.this.textView.setTypeface(Typeface.create(AddTextQuotesActivity.this.textView.getTypeface(), Typeface.BOLD), Typeface.NORMAL);
                    } else if (AddTextQuotesActivity.this.textView.getTypeface().getStyle() == Typeface.BOLD_ITALIC) {
                        AddTextQuotesActivity.this.textView.setTypeface(Typeface.create(AddTextQuotesActivity.this.textView.getTypeface(), Typeface.NORMAL), Typeface.NORMAL);
                        AddTextQuotesActivity.this.textView.setTypeface(AddTextQuotesActivity.this.textView.getTypeface(), AddTextQuotesActivity.this.textView.getTypeface().getStyle() | 2);
                    } else {
                        AddTextQuotesActivity.this.textView.setTypeface(AddTextQuotesActivity.this.textView.getTypeface(), AddTextQuotesActivity.this.textView.getTypeface().getStyle() | 1);
                    }
                    AddTextQuotesActivity.this.textView.invalidate();
                    AddTextQuotesActivity.this.textView.requestLayout();
                    AddTextQuotesActivity.this.defaultsetup();
                    AddTextQuotesActivity.this.checkboldItalic();
                    AddTextQuotesActivity.this.textView.setSelection(AddTextQuotesActivity.this.min, AddTextQuotesActivity.this.max);
                    return;
                }
                if (AddTextQuotesActivity.this.textView.getTag().equals("text_tv")) {
                    for (int i = 0; i < AddTextQuotesActivity.this.arrayfortv.size(); i++) {
                        SelectedTextData d = (SelectedTextData) AddTextQuotesActivity.this.arrayfortv.get(i);
                        if (d.isText_bold()) {
                            d.setText_bold(false);
                        } else {
                            d.setText_bold(true);
                        }
                        AddTextQuotesActivity.this.arrayfortv.set(i, d);
                    }
                }
                AddTextQuotesActivity.this.defaultsetup();
                AddTextQuotesActivity.this.checkboldItalic();
                AddTextQuotesActivity.this.textView.setSelection(AddTextQuotesActivity.this.min, AddTextQuotesActivity.this.max);
            }
        });
        this.italic.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                AddTextQuotesActivity.this.selectFocus = true;
                AddTextQuotesActivity.this.fstCallMethod();
                if (AddTextQuotesActivity.this.modeformatselection.equals("textformat")) {
                    if (AddTextQuotesActivity.this.textView.getTypeface().getStyle() == Typeface.ITALIC) {
                        AddTextQuotesActivity.this.textView.setTypeface(Typeface.create(AddTextQuotesActivity.this.textView.getTypeface(), Typeface.NORMAL), Typeface.NORMAL);
                    } else if (AddTextQuotesActivity.this.textView.getTypeface().getStyle() == Typeface.BOLD_ITALIC) {
                        AddTextQuotesActivity.this.textView.setTypeface(Typeface.create(AddTextQuotesActivity.this.textView.getTypeface(), Typeface.BOLD), Typeface.NORMAL);
                        AddTextQuotesActivity.this.textView.setTypeface(AddTextQuotesActivity.this.textView.getTypeface(), AddTextQuotesActivity.this.textView.getTypeface().getStyle() | Typeface.BOLD);
                    } else {
                        AddTextQuotesActivity.this.textView.setTypeface(AddTextQuotesActivity.this.textView.getTypeface(), AddTextQuotesActivity.this.textView.getTypeface().getStyle() | Typeface.ITALIC);
                    }
                    AddTextQuotesActivity.this.defaultsetup();
                    AddTextQuotesActivity.this.checkboldItalic();
                    AddTextQuotesActivity.this.textView.setSelection(AddTextQuotesActivity.this.min, AddTextQuotesActivity.this.max);
                    return;
                }
                if (AddTextQuotesActivity.this.textView.getTag().equals("text_tv")) {
                    for (int i = 0; i < AddTextQuotesActivity.this.arrayfortv.size(); i++) {
                        SelectedTextData d = (SelectedTextData) AddTextQuotesActivity.this.arrayfortv.get(i);
                        if (d.isText_italic()) {
                            d.setText_italic(false);
                        } else {
                            d.setText_italic(true);
                        }
                        AddTextQuotesActivity.this.arrayfortv.set(i, d);
                    }
                }
                AddTextQuotesActivity.this.defaultsetup();
                AddTextQuotesActivity.this.checkboldItalic();
                AddTextQuotesActivity.this.textView.setSelection(AddTextQuotesActivity.this.min, AddTextQuotesActivity.this.max);
            }
        });
        this.underline.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                AddTextQuotesActivity.this.selectFocus = true;
                AddTextQuotesActivity.this.fstCallMethod();
                if (AddTextQuotesActivity.this.modeformatselection.equals("textformat")) {
                    if ((AddTextQuotesActivity.this.textView.getPaintFlags() & 8) == 8) {
                        AddTextQuotesActivity.this.textView.setPaintFlags(AddTextQuotesActivity.this.textView.getPaintFlags() & -9);
                    } else {
                        AddTextQuotesActivity.this.textView.setPaintFlags(AddTextQuotesActivity.this.textView.getPaintFlags() | 8);
                    }
                    AddTextQuotesActivity.this.defaultsetup();
                    AddTextQuotesActivity.this.checkboldItalic();
                    AddTextQuotesActivity.this.textView.setSelection(AddTextQuotesActivity.this.min, AddTextQuotesActivity.this.max);
                    return;
                }
                if (AddTextQuotesActivity.this.textView.getTag().equals("text_tv")) {
                    for (int i = 0; i < AddTextQuotesActivity.this.arrayfortv.size(); i++) {
                        SelectedTextData d = (SelectedTextData) AddTextQuotesActivity.this.arrayfortv.get(i);
                        if (d.isText_underline()) {
                            d.setText_underline(false);
                        } else {
                            d.setText_underline(true);
                        }
                        AddTextQuotesActivity.this.arrayfortv.set(i, d);
                    }
                }
                AddTextQuotesActivity.this.defaultsetup();
                AddTextQuotesActivity.this.checkboldItalic();
                AddTextQuotesActivity.this.textView.setSelection(AddTextQuotesActivity.this.min, AddTextQuotesActivity.this.max);
            }
        });
        this.strike.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                AddTextQuotesActivity.this.selectFocus = true;
                AddTextQuotesActivity.this.fstCallMethod();
                if (AddTextQuotesActivity.this.modeformatselection.equals("textformat")) {
                    if ((AddTextQuotesActivity.this.textView.getPaintFlags() & 16) == 16) {
                        AddTextQuotesActivity.this.textView.setPaintFlags(AddTextQuotesActivity.this.textView.getPaintFlags() & -17);
                    } else {
                        AddTextQuotesActivity.this.textView.setPaintFlags(AddTextQuotesActivity.this.textView.getPaintFlags() | 16);
                    }
                    AddTextQuotesActivity.this.defaultsetup();
                    AddTextQuotesActivity.this.checkboldItalic();
                    AddTextQuotesActivity.this.textView.setSelection(AddTextQuotesActivity.this.min, AddTextQuotesActivity.this.max);
                    return;
                }
                if (AddTextQuotesActivity.this.textView.getTag().equals("text_tv")) {
                    for (int i = 0; i < AddTextQuotesActivity.this.arrayfortv.size(); i++) {
                        SelectedTextData d = (SelectedTextData) AddTextQuotesActivity.this.arrayfortv.get(i);
                        if (d.isText_strike()) {
                            d.setText_strike(false);
                        } else {
                            d.setText_strike(true);
                        }
                        AddTextQuotesActivity.this.arrayfortv.set(i, d);
                    }
                }
                AddTextQuotesActivity.this.defaultsetup();
                AddTextQuotesActivity.this.checkboldItalic();
                AddTextQuotesActivity.this.textView.setSelection(AddTextQuotesActivity.this.min, AddTextQuotesActivity.this.max);
            }
        });
        this.gd = new GestureDetector(this, new OnGestureListener() {
            public boolean onSingleTapUp(MotionEvent e) {
                Log.e("GestureDetector", "onSingleTapUp");
                if (!AddTextQuotesActivity.this.flag) {
                    AddTextQuotesActivity.this.textView.setCursorVisible(false);
                }
                if (!(AddTextQuotesActivity.this.flag && AddTextQuotesActivity.this.flag2)) {
                    if (AddTextQuotesActivity.this.selectFocus) {
                        Selection.removeSelection(AddTextQuotesActivity.this.textView.getText());
                        AddTextQuotesActivity.this.textView.setTextIsSelectable(true);
                        AddTextQuotesActivity.this.textView.setSelectAllOnFocus(false);
                        AddTextQuotesActivity.this.textView.setCursorVisible(false);
                        if (AddTextQuotesActivity.this.ftrue) {
                            AddTextQuotesActivity.this.ftrue = false;
                        }
                        AddTextQuotesActivity.this.checkandShowEditRel();
                    } else {
                        AddTextQuotesActivity.this.textView.setSelectAllOnFocus(false);
                        AddTextQuotesActivity.this.textView.setCursorVisible(false);
                    }
                }
                return false;
            }

            public void onShowPress(MotionEvent e) {
                Log.e("GestureDetector", "onShowPress");
            }

            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                Log.e("GestureDetector", "onScroll");
                return false;
            }

            public void onLongPress(MotionEvent e) {
            }

            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                Log.e("GestureDetector", "onFling");
                return false;
            }

            public boolean onDown(MotionEvent e) {
                Log.e("GestureDetector", "onDown");
                return false;
            }
        });
        this.gd.setOnDoubleTapListener(new OnDoubleTapListener() {
            public boolean onDoubleTap(MotionEvent e) {
                Log.e("GestureDetector", "setOnDoubleTapListener");
                AddTextQuotesActivity.this.textView.clearFocus();
                return false;
            }

            public boolean onDoubleTapEvent(MotionEvent e) {
                Log.e("GestureDetector", "onDoubleTapEvent");
                AddTextQuotesActivity.this.textView.clearFocus();
                return false;
            }

            public boolean onSingleTapConfirmed(MotionEvent e) {
                Log.e("GestureDetector", "onSingleTapConfirmed");
                if (!AddTextQuotesActivity.this.flag) {
                    AddTextQuotesActivity.this.textView.setCursorVisible(false);
                }
                if (!(AddTextQuotesActivity.this.flag && AddTextQuotesActivity.this.flag2) && AddTextQuotesActivity.this.selectFocus) {
                    AddTextQuotesActivity.this.seekflag = false;
                    AddTextQuotesActivity.this.start = 0;
                    AddTextQuotesActivity.this.end = 0;
                    int cursor = AddTextQuotesActivity.this.textView.getSelectionStart();
                    int start_txt = Constants.getStart(AddTextQuotesActivity.this.textView.getText().toString(), AddTextQuotesActivity.this.textView.getSelectionStart());
                    int end_txt = Constants.getEnd(AddTextQuotesActivity.this.textView.getText().toString(), AddTextQuotesActivity.this.textView.getSelectionEnd());
                    Log.e("end_txt", BuildConfig.FLAVOR + start_txt + ", " + end_txt);
                    if (start_txt != end_txt) {
                        AddTextQuotesActivity.this.textView.setSelection(start_txt, end_txt);
                        if (AddTextQuotesActivity.this.textView.getTag().equals("text_tv")) {
                            float hh = Float.valueOf(AddTextQuotesActivity.this.text_tv.getTextSize()).floatValue();
                            AddTextQuotesActivity.this.start = AddTextQuotesActivity.this.text_tv.getSelectionStart();
                            if (AddTextQuotesActivity.this.start < 0) {
                                AddTextQuotesActivity.this.start = 0;
                            }
                            AddTextQuotesActivity.this.end = AddTextQuotesActivity.this.text_tv.getSelectionEnd();
                            if (AddTextQuotesActivity.this.end < 0) {
                                AddTextQuotesActivity.this.end = 0;
                            }
                            if (!(AddTextQuotesActivity.this.start == 0 && AddTextQuotesActivity.this.end == AddTextQuotesActivity.this.text_tv.length())) {
                                AddTextQuotesActivity.this.setPunch(AddTextQuotesActivity.this.arrayfortv, AddTextQuotesActivity.this.start, AddTextQuotesActivity.this.end);
                            }
                            int i = 0;
                            while (i < AddTextQuotesActivity.this.arrayfortv.size()) {

                                Log.e("while", "yess" + arrayfortv.size());
                                if (((SelectedTextData) AddTextQuotesActivity.this.arrayfortv.get(i)).getStart() == AddTextQuotesActivity.this.start && ((SelectedTextData) AddTextQuotesActivity.this.arrayfortv.get(i)).getEnd() == AddTextQuotesActivity.this.end) {
                                    int aa = ((SelectedTextData) AddTextQuotesActivity.this.arrayfortv.get(i)).getText_size();
                                }
                                i++;
                            }
                        }
                        AddTextQuotesActivity.this.textView.setSelection(AddTextQuotesActivity.this.start, AddTextQuotesActivity.this.end);
                    } else {
                        AddTextQuotesActivity.this.textView.setCursorVisible(false);
                        if (AddTextQuotesActivity.this.textView.getTag().equals("text_tv")) {
                            AddTextQuotesActivity.this.setSelectionAll(AddTextQuotesActivity.this.text_tv);
                        }
                    }
                }
                AddTextQuotesActivity.this.textView.clearFocus();
                Selection.removeSelection(AddTextQuotesActivity.this.textView.getText());
                AddTextQuotesActivity.this.a = 0;
                AddTextQuotesActivity.this.b = 0;
                AddTextQuotesActivity.this.start = 0;
                AddTextQuotesActivity.this.end = 0;
                AddTextQuotesActivity.this.min = 0;
                AddTextQuotesActivity.this.max = 0;
                AddTextQuotesActivity.this.updateTextSizeonScale(AddTextQuotesActivity.this.rl.getWidth(), AddTextQuotesActivity.this.rl.getHeight());

                Log.e("xxxxxx", "yess");
                return false;
            }
        });
        Log.e("vvvvvv", "yess");
        this.underline.setPaintFlags(this.underline.getPaintFlags() | 8);
        this.strike.setPaintFlags(this.strike.getPaintFlags() | 16);
        this.styl_recycler.setAdapter(new StyleAdepter(this, "template"));
        this.styl_recycler.setHasFixedSize(true);
        this.styl_recycler.setLayoutManager(new LinearLayoutManager(this));
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(RecyclerView.HORIZONTAL);
        this.styl_recycler.setLayoutManager(llm);
        this.text_tv.post(new Runnable() {
            public void run() {
                int noch1 = ((ViewGroup) AddTextQuotesActivity.this.text_tv.getParent()).getChildCount();
                for (int i = 1; i < noch1; i++) {

                    Log.e("for", "yess");
                    View child = ((ViewGroup) AddTextQuotesActivity.this.text_tv.getParent()).getChildAt(i);
                    if (i != 3) {
                        child.setVisibility(View.VISIBLE);
                    }
                }
                AddTextQuotesActivity.this.checkandShowEditRel();
            }
        });

        AdAdmob adAdmob = new AdAdmob(this);
        adAdmob.FullscreenAd(this);
    }

    public void onClickButtons(View view) {
        switch (view.getId()) {
            case R.id.btn_Picker:
                new AmbilWarnaDialog(this, this.backgroundColor, new OnAmbilWarnaListener() {
                    public void onOk(AmbilWarnaDialog dialog, int colorSelect) {
                        AddTextQuotesActivity.this.drawableName = "color";
                        AddTextQuotesActivity.this.backgroundColor = colorSelect;
                        AddTextQuotesActivity.this.bitmap = Bitmap.createBitmap(300, 300, Config.ARGB_8888);
                        AddTextQuotesActivity.this.bitmap.eraseColor(colorSelect);
                        AddTextQuotesActivity.this.bitmap = ImageUtils.resizeBitmap(AddTextQuotesActivity.this.bitmap, (int) AddTextQuotesActivity.this.screenWidth, (int) AddTextQuotesActivity.this.screenWidth);
                        AddTextQuotesActivity.this.setImageInBackgrounds(AddTextQuotesActivity.this.bitmap);
                    }

                    public void onCancel(AmbilWarnaDialog dialog) {
                    }
                }).show();
                return;
            case R.id.btn_Cam:
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intent, SELECT_BACKGROUND_FROM_CAMERA);
                return;
            case R.id.btn_Gallery:


                Intent intent1 = new Intent();
                intent1.setType("image/*");
                intent1.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent1, SEL_BACKGROUND_FROM_GALLERY);

                return;
            case R.id.btn_Background:
                this.sb_effectsfilter.setVisibility(View.GONE);
                this.sb_opctyfilter.setVisibility(View.GONE);
                this.blur_lay_cntrl.setVisibility(View.GONE);
                this.blur_seekbar_lay.setVisibility(View.GONE);
                this.scroll_of_all_effects.setVisibility(View.GONE);
                setDefault();
                removeImageViewControll();
                showBackground();
                return;
            default:
                return;
        }
    }

    private void showBackground() {
        int position = getPositionFromCategory(this.categoryBG);
        Intent intent = new Intent(this, BackGroundList.class);
        intent.putExtra("categoryPos", position);
        intent.putExtra("quote_edit", BuildConfig.FLAVOR);
        intent.putExtra("categoryQuote", this.categoryQuote);
        intent.putExtra("hasAuthor", BuildConfig.FLAVOR);
        startActivityForResult(intent, GET_BG_RESULT);
    }

    private void updatePositionSticker(String incr) {
        int childCount = this.fortext.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = this.fortext.getChildAt(i);
            if ((view instanceof ResizableImageview) && ((ResizableImageview) view).getBorderVisbilty()) {
                if (incr.equals("incrX")) {
                    ((ResizableImageview) view).incrX();
                }
                if (incr.equals("decX")) {
                    ((ResizableImageview) view).decX();
                }
                if (incr.equals("incrY")) {
                    ((ResizableImageview) view).incrY();
                }
                if (incr.equals("decY")) {
                    ((ResizableImageview) view).decY();
                }
            }
        }
    }

    private void applyGusianBlur() {
        this.mFilter = GPUImageFilterTools.createFilterForType(getApplicationContext(), FilterType.GAUSSIAN_BLUR);
        this.gpuImageview.setFilter(this.mFilter);
        this.gpuImageview.setVisibility(View.VISIBLE);
        this.mFilterAdjuster = new FilterAdjuster(this.mFilter);
        this.sb_effectsfilter.setProgress(25);
        if (this.mFilterAdjuster != null) {
            this.mFilterAdjuster.adjust(25);
        }
        this.gpuImageview.requestRender();
    }

    private void setPunch(ArrayList<SelectedTextData> arr, int start, int end) {
        boolean bl = true;
        if (arr.size() > 0) {
            int i = 0;
            while (i < arr.size()) {
                if (((SelectedTextData) arr.get(i)).getStart() == start || ((SelectedTextData) arr.get(i)).getEnd() == end) {
                    arr.remove(i);
                    defaultsetup();
                    bl = false;
                }
                i++;
            }
            if (bl && start != end) {
                setSelect_ChangeformArray(arr, start, end);
            }
        } else if (this.pos.equals("main")) {
            int _postn = Integer.parseInt(this.template.replace("t", BuildConfig.FLAVOR));
            if (start != end) {
                setSelect_Chang(arr, start, end, _postn);
            }
        } else if (start != end) {
            mainAddMethod1();
        }
    }

    private void temp_Referesh() {
        Editor editor = getSharedPreferences("MY_PREFS_NAME", 0).edit();
        SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME", 0);
        if (prefs.getInt("temp", 0) > 26) {
            this.temp_postn = 1;
            editor.putInt("temp", this.temp_postn + 1);
            editor.commit();
        } else if (prefs.getInt("temp", 0) == 0) {
            this.temp_postn = 1;
            editor.putInt("temp", this.temp_postn + 1);
            editor.commit();
        } else {
            this.temp_postn = prefs.getInt("temp", 0);
            editor.putInt("temp", this.temp_postn + 1);
            editor.commit();
        }
    }

    protected void fstCallMethod() {
        this.min = 0;
        this.max = this.textView.getText().length();
        this.textView.setCursorVisible(false);
        if (this.textView.isFocused()) {
            int selStart = this.textView.getSelectionStart();
            int selEnd = this.textView.getSelectionEnd();
            this.min = Math.max(0, Math.min(selStart, selEnd));
            this.max = Math.max(0, Math.max(selStart, selEnd));
        }
        if (this.min != 0 || this.max != this.textView.length()) {
            mainAddMethod();
        }
    }

    protected void callFous() {
        ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(this.textView.getWindowToken(), 0);
    }

    private void visibleBorder(RelativeLayout rl) {
        int noch = rl.getChildCount();
        for (int i = 1; i < noch; i++) {
            if (i != 3) {
                rl.getChildAt(i).setVisibility(View.VISIBLE);
            }
        }
    }

    protected void addNormalTextTemplateMethod(String quotTxt) {
        Shader sadr = null;
        String text = BuildConfig.FLAVOR;
        int text_color = this.defaultcolor_all;
        this.scroll_of_all_effects.setVisibility(View.GONE);
        this.scroll_of_all_backgrounds.setVisibility(View.GONE);
        this.ed = false;
        this.flag = false;
        this.flag2 = false;
        if (this.textView != null) {
            this.textView.setCursorVisible(false);
            this.textView.setSelectAllOnFocus(false);
        }
        Log.e("temp_postn is", BuildConfig.FLAVOR + this.temp_postn);
        List<Quotes> quotesw = this.db.getQuotesAllRowValue(this.temp_postn);
        if (quotesw.size() != 0) {
            for (int i = 0; i < quotesw.size(); i++) {
                Log.e("sizeoflist", BuildConfig.FLAVOR + i);
                Log.e("sizeoflist", BuildConfig.FLAVOR + quotesw.size());
                this.template = ((Quotes) quotesw.get(i)).get_template();
                if (quotTxt.equals(BuildConfig.FLAVOR)) {
                    text = BuildConfig.FLAVOR;
                } else {
                    text = quotTxt;
                }
                int grvty = ((Quotes) quotesw.get(i)).get_gravity();
                int size = (int) Float.parseFloat(((Quotes) quotesw.get(i)).get_size());
                text_color = Integer.parseInt(((Quotes) quotesw.get(i)).get_color());
                String fontt = ((Quotes) quotesw.get(i)).get_font();
                Typeface font_type = Typeface.createFromAsset(getAssets(), fontt);
                List<QuotesSelect> dbsetPunch = this.db.getSelectAllRowValue(this.temp_postn);
                Typeface font_typePunch = Typeface.createFromAsset(getAssets(), ((QuotesSelect) dbsetPunch.get(i)).get_font());
                String shadow_dx = ((Quotes) quotesw.get(i)).get_shadow_dx();
                String shadow_dy = ((Quotes) quotesw.get(i)).get_shadow_dy();
                String shadow_radius = ((Quotes) quotesw.get(i)).get_shadow_radius();
                String shadow_color = ((Quotes) quotesw.get(i)).get_shadow_color();
                String shaderr = ((Quotes) quotesw.get(i)).get_shader();
                float sh_x = Float.parseFloat(shadow_dx);
                float sh_y = Float.parseFloat(shadow_dy);
                float sh_rdus = Float.parseFloat(shadow_radius);
                int sh_color = Integer.parseInt(shadow_color);
                String[] parts = ((Quotes) quotesw.get(i)).get_scale().split("-");
                int width = (int) Float.parseFloat(parts[0]);
                int height = (int) Float.parseFloat(parts[1]);
                int position_x = ((Quotes) quotesw.get(i)).getPos_x();
                int position_y = ((Quotes) quotesw.get(i)).getPos_y();
                int rotation = ((Quotes) quotesw.get(i)).getRotation();
                if (!shaderr.equals("null")) {
                    Shader bitmapShader = new BitmapShader(BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(shaderr, "drawable", getPackageName())), TileMode.MIRROR, TileMode.MIRROR);
                }
                float mainLayWidth = (float) (bitmapOriginal.getWidth() / 2);
                float mainLayHeight = (float) (bitmapOriginal.getHeight() / 2);
                if (!(this.rl.getVisibility() == View.VISIBLE || text.equals(BuildConfig.FLAVOR))) {
                    this.ft1 = fontt;
                    this.rl.getLayoutParams().width = width;
                    this.rl.getLayoutParams().height = height;
                    float positionY = mainLayHeight - ((float) (height / 2));
                    this.rl.setX(mainLayWidth - ((float) (width / 2)));
                    this.rl.setY(positionY);
                    this.rl.setRotation((float) rotation);
                    this.rl.postInvalidate();
                    this.rl.requestLayout();
                    this.text_tv.setText(text);
                    this.text_tv.setTypeface(font_type);
                    this.text_tv.setTextColor(text_color);
                    this.text_tv.setShadowLayer(sh_x, sh_y, sh_rdus, sh_color);
                    this.text_tv.getPaint().setShader(sadr);
                    if (grvty == 49) {
                        this.text_tv.setGravity(1);
                    } else if (grvty == 51) {
                        this.text_tv.setGravity(3);
                    } else if (grvty == 53) {
                        this.text_tv.setGravity(5);
                    }
                    hideChilds(this.rl);
                    this.textView = this.text_tv;
                    setObserver(this.textView);
                    this.rl.setVisibility(View.VISIBLE);
                    this.rl.bringToFront();
                    this.storedArray.clear();
                    this.arrayfortv.clear();
                    if (this.chang == 0) {
                        this.chang = 4;
                    }
                    setChangPunch(this.arrayfortv, text, 4, 1);
                    updateTextSizeonScale(width, height);
                    this.rl.postInvalidate();
                    this.rl.requestLayout();
                }
                this.selectFocus = true;
                this.textView.clearFocus();
                this.textView.setSelectAllOnFocus(false);
                this.textView.setCursorVisible(false);
                this.oldlength = this.textView.length();
            }
        }
    }

    private void setChangPunch(ArrayList<SelectedTextData> arr, String quotTxt, int chang2, int selectedId) {
        List<QuotesSelect> quotesw = this.db.getSelectAllRowValue(this.temp_postn);
        for (int j = 0; j < quotesw.size(); j++) {
            if (((QuotesSelect) quotesw.get(j)).get_text_id() == selectedId) {
                String[] split = quotTxt.split(" ");
                ArrayList<Integer> positions = new ArrayList();
                int space_pos = quotTxt.indexOf(" ");
                positions.add(Integer.valueOf(space_pos));
                while (space_pos < quotTxt.length()) {
                    space_pos = quotTxt.indexOf(" ", space_pos + 1);
                    if (space_pos == -1) {
                        break;
                    }
                    positions.add(Integer.valueOf(space_pos));
                }
                int i;
                if (chang2 == 1) {
                    for (i = 0; i < split.length; i++) {
                        if (i == 0) {
                            punchStartCh(arr, quotTxt, split[i], selectedId);
                        } else if (i % 2 == 0) {
                            punchMiddle(arr, quotTxt, split[i], ((Integer) positions.get(i - 1)).intValue(), selectedId);
                        }
                    }
                } else if (chang2 == 2) {
                    for (i = 0; i < split.length; i++) {
                        if (i % 2 == 1) {
                            punchMiddle(arr, quotTxt, split[i], ((Integer) positions.get(i - 1)).intValue(), selectedId);
                        }
                    }
                } else if (chang2 == 3) {
                    for (i = 0; i < split.length; i++) {
                        if (i == 0) {
                            punchStartCh(arr, quotTxt, split[i], selectedId);
                        } else if (i % 3 == 0) {
                            punchMiddle(arr, quotTxt, split[i], ((Integer) positions.get(i - 1)).intValue(), selectedId);
                        }
                    }
                } else if (chang2 == 4) {
                    for (i = 0; i < split.length; i++) {
                        if (i == 0) {
                            punchStartCh(arr, quotTxt, split[i], selectedId);
                        } else if (i % 5 == 0) {
                            punchMiddle(arr, quotTxt, split[i], ((Integer) positions.get(i - 1)).intValue(), selectedId);
                        }
                    }
                }
            }
        }
    }

    private void punchMiddle(ArrayList<SelectedTextData> arr, String quotTxt, String string, int spacepos, int textId) {
        Matcher matcher = Pattern.compile("\\s\\S*" + string.replaceAll("\\)", BuildConfig.FLAVOR).replaceAll("\\(", BuildConfig.FLAVOR)).matcher(quotTxt);
        if (matcher.find(spacepos)) {
            setAddQuoteSelect(arr, matcher.start() + 1, matcher.end(), textId);
        }
    }

    private void punchStartCh(ArrayList<SelectedTextData> arr, String quotTxt, String string, int textId) {
        Matcher matcher = Pattern.compile(string.replaceAll("\\)", BuildConfig.FLAVOR).replaceAll("\\(", BuildConfig.FLAVOR)).matcher(quotTxt);
        if (matcher.find()) {
            setAddQuoteSelect(arr, matcher.start(), matcher.end(), textId);
        }
    }

    private void hideChilds(RelativeLayout rl) {
        int noch = rl.getChildCount();
        for (int i = 1; i < noch; i++) {
            rl.getChildAt(i).setVisibility(View.INVISIBLE);
        }
    }

    protected void UpdateAddTextMethod() {
        Shader sadr = null;
        String text2 = BuildConfig.FLAVOR;
        int text_color = this.defaultcolor_all;
        this.shr1 = "null";
        this.scroll_of_all_effects.setVisibility(View.GONE);
        this.scroll_of_all_backgrounds.setVisibility(View.GONE);
        List<Quotes> quotesw = this.db.getQuotesAllRowValue(this.temp_postn);
        this.template = ((Quotes) quotesw.get(0)).get_template();
        int size = (int) Float.parseFloat(((Quotes) quotesw.get(0)).get_size());
        text_color = Integer.parseInt(((Quotes) quotesw.get(0)).get_color());
        String fontt = ((Quotes) quotesw.get(0)).get_font();
        int grvty = ((Quotes) quotesw.get(0)).get_gravity();
        Typeface font_type = Typeface.createFromAsset(getAssets(), fontt);
        List<QuotesSelect> dbsetPunch = this.db.getSelectAllRowValue(this.temp_postn);
        Typeface font_typePunch = Typeface.createFromAsset(getAssets(), ((QuotesSelect) dbsetPunch.get(0)).get_font());
        String shadow_dx = ((Quotes) quotesw.get(0)).get_shadow_dx();
        String shadow_dy = ((Quotes) quotesw.get(0)).get_shadow_dy();
        String shadow_radius = ((Quotes) quotesw.get(0)).get_shadow_radius();
        String shadow_color = ((Quotes) quotesw.get(0)).get_shadow_color();
        String shaderr = ((Quotes) quotesw.get(0)).get_shader();
        float sh_x = Float.parseFloat(shadow_dx);
        float sh_y = Float.parseFloat(shadow_dy);
        float sh_rdus = Float.parseFloat(shadow_radius);
        int sh_color = Integer.parseInt(shadow_color);
        String[] parts = ((Quotes) quotesw.get(0)).get_scale().split("-");
        int width = (int) Float.parseFloat(parts[0]);
        int height = (int) Float.parseFloat(parts[1]);
        if (!shaderr.equals("null")) {
            Shader bitmapShader = new BitmapShader(BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(shaderr, "drawable", getPackageName())), TileMode.MIRROR, TileMode.MIRROR);
        }
        if (this.textView.getTag().equals("text_tv")) {
            this.ft1 = fontt;
            text2 = String.valueOf(this.text_tv.getText());
            this.rl.setRotation(this.rl.getRotation());
            this.rl.postInvalidate();
            this.rl.requestLayout();
            this.text_tv.setText(text2);
            this.text_tv.setTypeface(font_type);
            this.text_tv.setTextColor(text_color);
            this.text_tv.setShadowLayer(sh_x, sh_y, sh_rdus, sh_color);
            this.text_tv.getPaint().setShader(sadr);
            if (grvty == 49) {
                this.text_tv.setGravity(1);
            } else if (grvty == 51) {
                this.text_tv.setGravity(3);
            } else if (grvty == 53) {
                this.text_tv.setGravity(5);
            }
            this.textView = this.text_tv;
            setObserver(this.textView);
            setSelect1(this.arrayfortv, String.valueOf(this.text_tv.getText()));
            return;
        }
        Toast.makeText(this, "no text", Toast.LENGTH_SHORT).show();
    }

    private void setSelect(ArrayList<SelectedTextData> arr) {
        List<QuotesSelect> quotesw = this.db.getSelectAllRowValue(this.temp_postn);
        for (int i = 0; i < quotesw.size(); i++) {
            SelectedTextData std = new SelectedTextData();
            std.setStart(Integer.parseInt(((QuotesSelect) quotesw.get(i)).get_start()));
            std.setEnd(Integer.parseInt(((QuotesSelect) quotesw.get(i)).get_end()));
            if (this.textView.getTag().equals("text_tv")) {
                std.setText_size((int) PictureConstant.convertPixelsToDp((float) (5 + ((int) Float.valueOf(this.text_tv.getTextSize()).floatValue())), this));
            }
            std.setText_color(Integer.parseInt(((QuotesSelect) quotesw.get(i)).get_color()));
            std.setText_ttf(((QuotesSelect) quotesw.get(i)).get_font());
            std.setText_shadowdx(Float.parseFloat(((QuotesSelect) quotesw.get(i)).get_shadow_dx()));
            std.setText_shadowdy(Float.parseFloat(((QuotesSelect) quotesw.get(i)).get_shadow_dy()));
            std.setText_shadowradius(Float.parseFloat(((QuotesSelect) quotesw.get(i)).get_shadow_radius()));
            std.setText_shadowcolor(Integer.parseInt(((QuotesSelect) quotesw.get(i)).get_shadow_color()));
            std.setText_shader(((QuotesSelect) quotesw.get(i)).get_shader());
            std.setText_bold(Boolean.valueOf(((QuotesSelect) quotesw.get(i)).get_textbold()).booleanValue());
            std.setText_italic(Boolean.valueOf(((QuotesSelect) quotesw.get(i)).get_text_italic()).booleanValue());
            std.setText_underline(Boolean.valueOf(((QuotesSelect) quotesw.get(i)).get_text_underline()).booleanValue());
            std.setText_strike(Boolean.valueOf(((QuotesSelect) quotesw.get(i)).get_text_strik()).booleanValue());
            arr.add(std);
        }
        defaultsetup();
    }

    private void setAddQuoteSelect(ArrayList<SelectedTextData> arr, int start, int end, int selectedTextId) {
        int pos = selectedTextId - 1;
        List<QuotesSelect> quotesw = this.db.getSelectAllRowValue(this.temp_postn);
        if (quotesw.size() > 0) {
            SelectedTextData std = new SelectedTextData();
            std.setStart(start);
            std.setEnd(end);
            if (this.textView.getTag().equals("text_tv")) {
                std.setText_size((int) PictureConstant.convertPixelsToDp((float) (5 + ((int) Float.valueOf(this.text_tv.getTextSize()).floatValue())), this));
            }
            if (this.storedArray.size() > 0) {
                std.setText_color(((SelectedTextData) this.storedArray.get(0)).getText_color());
                std.setText_ttf(((SelectedTextData) this.storedArray.get(0)).getText_ttf());
                std.setText_shadowdx(((SelectedTextData) this.storedArray.get(0)).getText_shadowdx());
                std.setText_shadowdy(((SelectedTextData) this.storedArray.get(0)).getText_shadowdy());
                std.setText_shadowradius(((SelectedTextData) this.storedArray.get(0)).getText_shadowradius());
                std.setText_shadowcolor(((SelectedTextData) this.storedArray.get(0)).getText_shadowcolor());
                std.setText_shader(((SelectedTextData) this.storedArray.get(0)).getText_shader());
                std.setText_bold(((SelectedTextData) this.storedArray.get(0)).isText_bold());
                std.setText_italic(((SelectedTextData) this.storedArray.get(0)).isText_italic());
                std.setText_underline(((SelectedTextData) this.storedArray.get(0)).isText_underline());
                std.setText_strike(((SelectedTextData) this.storedArray.get(0)).isText_strike());
                arr.add(std);
            } else {
                std.setText_color(Integer.parseInt(((QuotesSelect) quotesw.get(pos)).get_color()));
                std.setText_ttf(((QuotesSelect) quotesw.get(pos)).get_font());
                std.setText_shadowdx(Float.parseFloat(((QuotesSelect) quotesw.get(pos)).get_shadow_dx()));
                std.setText_shadowdy(Float.parseFloat(((QuotesSelect) quotesw.get(pos)).get_shadow_dy()));
                std.setText_shadowradius(Float.parseFloat(((QuotesSelect) quotesw.get(pos)).get_shadow_radius()));
                std.setText_shadowcolor(Integer.parseInt(((QuotesSelect) quotesw.get(pos)).get_shadow_color()));
                std.setText_shader(((QuotesSelect) quotesw.get(pos)).get_shader());
                std.setText_bold(Boolean.valueOf(((QuotesSelect) quotesw.get(pos)).get_textbold()).booleanValue());
                std.setText_italic(Boolean.valueOf(((QuotesSelect) quotesw.get(pos)).get_text_italic()).booleanValue());
                std.setText_underline(Boolean.valueOf(((QuotesSelect) quotesw.get(pos)).get_text_underline()).booleanValue());
                std.setText_strike(Boolean.valueOf(((QuotesSelect) quotesw.get(pos)).get_text_strik()).booleanValue());
                arr.add(std);
            }
        }
        Log.e("timeend1", BuildConfig.FLAVOR + System.currentTimeMillis());
    }

    private void setSelect1(ArrayList<SelectedTextData> arr, String text) {
        if (text.equals(this.defaultText)) {
            arr.clear();
            setSelect(arr);
            return;
        }
        List<QuotesSelect> quotesw = this.db.getSelectAllRowValue(this.temp_postn);
        if (quotesw.size() > 0 && arr.size() > 0) {
            for (int j = 0; j < arr.size(); j++) {
                SelectedTextData std = new SelectedTextData();
                std.setStart(((SelectedTextData) arr.get(j)).getStart());
                std.setEnd(((SelectedTextData) arr.get(j)).getEnd());
                Resources r = getResources();
                if (this.textView.getTag().equals("text_tv")) {
                    std.setText_size((int) PictureConstant.convertPixelsToDp((float) (5 + ((int) Float.valueOf(this.text_tv.getTextSize()).floatValue())), this));
                }
                std.setText_color(Integer.parseInt(((QuotesSelect) quotesw.get(0)).get_color()));
                std.setText_ttf(((QuotesSelect) quotesw.get(0)).get_font());


                Log.e("aaaaacc", "" + quotesw.get(0));


                try {
                    Float aa = Float.parseFloat(((QuotesSelect) quotesw.get(0)).get_shadow_dx());
                    std.setText_shadowdx(aa);
                    std.setText_shadowdy(Float.parseFloat(((QuotesSelect) quotesw.get(0)).get_shadow_dy()));
                    std.setText_shadowradius(Float.parseFloat(((QuotesSelect) quotesw.get(0)).get_shadow_radius()));
                    std.setText_shadowcolor(Integer.parseInt(((QuotesSelect) quotesw.get(0)).get_shadow_color()));
                    std.setText_shader(((QuotesSelect) quotesw.get(0)).get_shader());
                    std.setText_bold(Boolean.valueOf(((QuotesSelect) quotesw.get(0)).get_textbold()).booleanValue());
                    std.setText_italic(Boolean.valueOf(((QuotesSelect) quotesw.get(0)).get_text_italic()).booleanValue());
                    std.setText_underline(Boolean.valueOf(((QuotesSelect) quotesw.get(0)).get_text_underline()).booleanValue());
                    std.setText_strike(Boolean.valueOf(((QuotesSelect) quotesw.get(0)).get_text_strik()).booleanValue());
                    arr.set(j, std);
                } catch (Exception e) {

                }


            }
        }
        updateTextSizeonScale(this.rl.getWidth(), this.rl.getHeight());
    }

    private void setSelect_Chang(ArrayList<SelectedTextData> arr, int start, int end, int postn) {
        List<QuotesSelect> quotesw = this.db.getSelectAllRowValue(postn);
        SelectedTextData std = new SelectedTextData();
        std.setStart(start);
        std.setEnd(end);
        if (this.textView.getTag().equals("text_tv")) {
            std.setText_size((int) PictureConstant.convertPixelsToDp((float) (5 + ((int) Float.valueOf(this.text_tv.getTextSize()).floatValue())), this));
        }
        std.setText_color(Integer.parseInt(((QuotesSelect) quotesw.get(0)).get_color()));
        std.setText_ttf(((QuotesSelect) quotesw.get(0)).get_font());
        std.setText_shadowdx(Float.parseFloat(((QuotesSelect) quotesw.get(0)).get_shadow_dx()));
        std.setText_shadowdy(Float.parseFloat(((QuotesSelect) quotesw.get(0)).get_shadow_dy()));
        std.setText_shadowradius(Float.parseFloat(((QuotesSelect) quotesw.get(0)).get_shadow_radius()));
        std.setText_shadowcolor(Integer.parseInt(((QuotesSelect) quotesw.get(0)).get_shadow_color()));
        std.setText_shader(((QuotesSelect) quotesw.get(0)).get_shader());
        std.setText_bold(Boolean.valueOf(((QuotesSelect) quotesw.get(0)).get_textbold()).booleanValue());
        std.setText_italic(Boolean.valueOf(((QuotesSelect) quotesw.get(0)).get_text_italic()).booleanValue());
        std.setText_underline(Boolean.valueOf(((QuotesSelect) quotesw.get(0)).get_text_underline()).booleanValue());
        std.setText_strike(Boolean.valueOf(((QuotesSelect) quotesw.get(0)).get_text_strik()).booleanValue());
        arr.add(std);
        defaultsetup();
    }

    private void setSelect_ChangeformArray(ArrayList<SelectedTextData> arr, int start, int end) {
        SelectedTextData std = new SelectedTextData();
        std.setStart(start);
        std.setEnd(end);
        std.setText_size(((SelectedTextData) arr.get(arr.size() - 1)).getText_size());
        std.setText_color(((SelectedTextData) arr.get(arr.size() - 1)).getText_color());
        std.setText_ttf(((SelectedTextData) arr.get(arr.size() - 1)).getText_ttf());
        std.setText_shadowdx(((SelectedTextData) arr.get(arr.size() - 1)).getText_shadowdx());
        std.setText_shadowdy(((SelectedTextData) arr.get(arr.size() - 1)).getText_shadowdy());
        std.setText_shadowradius(((SelectedTextData) arr.get(arr.size() - 1)).getText_shadowradius());
        std.setText_shadowcolor(((SelectedTextData) arr.get(arr.size() - 1)).getText_shadowcolor());
        std.setText_shader(((SelectedTextData) arr.get(arr.size() - 1)).getText_shader());
        std.setText_bold(((SelectedTextData) arr.get(arr.size() - 1)).isText_bold());
        std.setText_italic(((SelectedTextData) arr.get(arr.size() - 1)).isText_italic());
        std.setText_underline(((SelectedTextData) arr.get(arr.size() - 1)).isText_underline());
        std.setText_strike(((SelectedTextData) arr.get(arr.size() - 1)).isText_strike());
        arr.add(std);
        defaultsetup();
    }

    private void setSelectionAll(EditText text_tv) {
        Selection.removeSelection(text_tv.getText());
        callFous();
        text_tv.setSelectAllOnFocus(true);
        text_tv.setTextIsSelectable(true);
        text_tv.setCursorVisible(true);
        text_tv.setSelection(0, text_tv.getText().length());
    }

    private void checkandShowEditRel() {
        if (this.isfrst) {
            if (this.alledit_ll.getVisibility() != View.VISIBLE) {
                this.menu_ll.setVisibility(View.GONE);
                this.alledit_ll.setVisibility(View.VISIBLE);
                this.sb_effectsfilter.setVisibility(View.GONE);
                this.sb_opctyfilter.setVisibility(View.GONE);
                this.blur_lay_cntrl.setVisibility(View.GONE);
                this.blur_seekbar_lay.setVisibility(View.GONE);
                this.scroll_of_all_effects.setVisibility(View.GONE);
                this.scroll_of_all_backgrounds.setVisibility(View.GONE);
                this.controll_btn_stckr.setVisibility(View.GONE);
                this.controller_sticker.setVisibility(View.GONE);
                this.scroll_all.setVisibility(View.GONE);
                this.top_option.setVisibility(View.GONE);
                this.color_rel.setVisibility(View.INVISIBLE);
                this.lay_viewStyle.setVisibility(View.VISIBLE);
                this.shadow_rel.setVisibility(View.INVISIBLE);
                this.shader_rel.setVisibility(View.INVISIBLE);
                this.font_rel.setVisibility(View.GONE);
                ((TextView) findViewById(R.id.tep_txt)).setTextColor(-16777216);
                ((TextView) findViewById(R.id.for_txt)).setTextColor(-1);
                ((TextView) findViewById(R.id.font_txt)).setTextColor(-1);
                ((TextView) findViewById(R.id.co_txt)).setTextColor(-1);
                ((TextView) findViewById(R.id.shw_txt)).setTextColor(-1);
                ((TextView) findViewById(R.id.shdr_txt)).setTextColor(-1);
                this.list_of_style.setBackgroundResource(R.drawable.stye_icon);
                this.fonts.setBackgroundResource(R.drawable.fonts1);
                this.size.setBackgroundResource(R.drawable.size1);
                this.color_.setBackgroundResource(R.drawable.color_1);
                this.shadow.setBackgroundResource(R.drawable.shadow1);
                this.shader.setBackgroundResource(R.drawable.shader_1);
                this.la_size.setBackgroundResource(R.drawable.gradient);
                this.la_fonts.setBackgroundResource(R.drawable.gradient);
                this.la_color.setBackgroundResource(R.drawable.gradient);
                this.la_shadow.setBackgroundResource(R.drawable.gradient);
                this.la_shader.setBackgroundResource(R.drawable.gradient);
                this.re_template.setBackgroundColor(-1);
                this.isfrst = false;
            }
        } else if (this.alledit_ll.getVisibility() != View.VISIBLE) {
            this.menu_ll.setVisibility(View.GONE);
            this.alledit_ll.setVisibility(View.VISIBLE);
            this.sb_effectsfilter.setVisibility(View.GONE);
            this.sb_opctyfilter.setVisibility(View.GONE);
            this.blur_lay_cntrl.setVisibility(View.GONE);
            this.blur_seekbar_lay.setVisibility(View.GONE);
            this.scroll_of_all_effects.setVisibility(View.GONE);
            this.scroll_of_all_backgrounds.setVisibility(View.GONE);
            this.controll_btn_stckr.setVisibility(View.GONE);
            this.controller_sticker.setVisibility(View.GONE);
            this.scroll_all.setVisibility(View.GONE);
        }
    }

    private int comapreSizes1(int textSize, int punchSize, ArrayList<SelectedTextData> alstd) {
        String compare = "Ay";
        TextPaint textpaintNormal = new TextPaint();
        TextPaint textPaintPunch = new TextPaint();
        Typeface punch = Typeface.createFromAsset(getAssets(), ((SelectedTextData) alstd.get(0)).getText_ttf());
        textpaintNormal.setTextSize((float) textSize);
        textpaintNormal.setTypeface(this.textView.getTypeface());
        textPaintPunch.setTextSize(1.0f);
        textPaintPunch.setTypeface(punch);
        int normalHeight = new StaticLayout(compare, textpaintNormal, 100, Alignment.ALIGN_CENTER, 1.0f, 0.0f, true).getHeight();
        return getOptimumTextSize1(2, 400, compare, new RectF(0.0f, 0.0f, (float) 100, (float) ((int) (((double) normalHeight) + (((double) normalHeight) * 0.1d)))), textPaintPunch, this.text_tv.getGravity());
    }

    private void updatePunchSize(SpannableString spannableString, ArrayList<SelectedTextData> arrayfortv) {
        for (int i = 0; i < arrayfortv.size(); i++) {
            if (((SelectedTextData) arrayfortv.get(i)).getEnd() <= this.textView.getText().length()) {
                try {
                    spannableString.setSpan(new TextAppearanceSpan(null, 0, (int) (((float) ((SelectedTextData) arrayfortv.get(i)).getText_size()) * getResources().getDisplayMetrics().density), null, null), ((SelectedTextData) arrayfortv.get(i)).getStart(), ((SelectedTextData) arrayfortv.get(i)).getEnd(), 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        SpannableStringBuilder builder = new SpannableStringBuilder().append(spannableString);
        this.textView.setText(builder.subSequence(0, builder.length()));
        updateColors();
    }

    private void updateColorBarText(int currentTextColor) {
        if (this.shr1.equals("null")) {
            Drawable background = getResources().getDrawable(R.drawable.square_bk);
            int primaryColor = currentTextColor;
            this.existingcolor = primaryColor;
            background.setColorFilter(primaryColor, Mode.DARKEN);
            this.img_format_txt.setBackgroundDrawable(background);
            this.img_font_txt.setBackgroundDrawable(background);
            this.img_shadow_txt.setBackgroundDrawable(background);
            this.img_shader_txt.setBackgroundDrawable(background);
            this.img_color_txt.setBackgroundDrawable(background);
            return;
        }
        Drawable background = getResources().getDrawable(getResources().getIdentifier(this.shr1, "drawable", getPackageName()));
        this.img_format_txt.setBackgroundDrawable(background);
        this.img_font_txt.setBackgroundDrawable(background);
        this.img_shadow_txt.setBackgroundDrawable(background);
        this.img_shader_txt.setBackgroundDrawable(background);
        this.img_color_txt.setBackgroundDrawable(background);
    }

    private void updateCircleBarsPunch(int currentPunchColor) {
        if (((SelectedTextData) this.arrayfortv.get(0)).getText_shader().equals("null")) {
            Drawable background = getResources().getDrawable(R.drawable.square_bk1);
            int primaryColor = currentPunchColor;
            this.existingcolor = primaryColor;
            background.setColorFilter(primaryColor, Mode.DARKEN);
            this.img_format_punch.setBackgroundDrawable(background);
            this.img_font_punch.setBackgroundDrawable(background);
            this.img_shadow_punch.setBackgroundDrawable(background);
            this.img_shader_punch.setBackgroundDrawable(background);
            this.img_color_punch.setBackgroundDrawable(background);
            return;
        }
        Drawable background = getResources().getDrawable(getResources().getIdentifier(((SelectedTextData) this.arrayfortv.get(0)).getText_shader(), "drawable", getPackageName()));
        this.img_format_punch.setBackgroundDrawable(background);
        this.img_font_punch.setBackgroundDrawable(background);
        this.img_shadow_punch.setBackgroundDrawable(background);
        this.img_shader_punch.setBackgroundDrawable(background);
        this.img_color_punch.setBackgroundDrawable(background);
    }

    private void hideEditRel() {
        if (this.alledit_ll.getVisibility() == View.VISIBLE) {
            this.menu_ll.setVisibility(View.VISIBLE);
            this.alledit_ll.setVisibility(View.GONE);
            this.sb_effectsfilter.setVisibility(View.VISIBLE);
            this.sb_opctyfilter.setVisibility(View.VISIBLE);
            this.blur_lay_cntrl.setVisibility(View.VISIBLE);
            this.blur_seekbar_lay.setVisibility(View.VISIBLE);
        }
    }

    public void defaultsetup() {
        updateColors();
        SpannableString spannableString = new SpannableString(this.textView.getText().toString());
        ArrayList<SelectedTextData> alstd = null;
        if (this.textView.getTag().equals("text_tv")) {
            alstd = this.arrayfortv;
        }
        for (int i = 0; i < alstd.size(); i++) {
            if (((SelectedTextData) alstd.get(i)).getEnd() <= this.textView.getText().length()) {
                try {
                    Typeface ttq;
                    spannableString.setSpan(new TextAppearanceSpan(null, 0, (int) (((float) ((SelectedTextData) alstd.get(i)).getText_size()) * getResources().getDisplayMetrics().density), null, null), ((SelectedTextData) alstd.get(i)).getStart(), ((SelectedTextData) alstd.get(i)).getEnd(), 0);
                    spannableString.setSpan(new ForegroundColorSpan(((SelectedTextData) alstd.get(i)).getText_color()), ((SelectedTextData) alstd.get(i)).getStart(), ((SelectedTextData) alstd.get(i)).getEnd(), 33);
                    if (((SelectedTextData) alstd.get(i)).getText_ttf().equals(BuildConfig.FLAVOR)) {
                        ttq = this.textView.getTypeface();
                    } else {
                        ttq = Typeface.createFromAsset(getAssets(), ((SelectedTextData) alstd.get(i)).getText_ttf());
                    }
                    spannableString.setSpan(new CustomTypefaceSpan(ttq), ((SelectedTextData) alstd.get(i)).getStart(), ((SelectedTextData) alstd.get(i)).getEnd(), 0);
                    spannableString.setSpan(new CustomShadowSpan(((SelectedTextData) alstd.get(i)).getText_shadowradius(), ((SelectedTextData) alstd.get(i)).getText_shadowdx(), ((SelectedTextData) alstd.get(i)).getText_shadowdy(), ((SelectedTextData) alstd.get(i)).getText_shadowcolor()), ((SelectedTextData) alstd.get(i)).getStart(), ((SelectedTextData) alstd.get(i)).getEnd(), 0);
                    Shader sadr = null;
                    if (!((SelectedTextData) alstd.get(i)).getText_shader().equals("null")) {
                        sadr = new BitmapShader(BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(((SelectedTextData) alstd.get(i)).getText_shader(), "drawable", getPackageName())), TileMode.MIRROR, TileMode.MIRROR);
                    }
                    spannableString.setSpan(new CustomShaderSpan(sadr), ((SelectedTextData) alstd.get(i)).getStart(), ((SelectedTextData) alstd.get(i)).getEnd(), 0);
                    StyleSpan styleSpan = new StyleSpan(1);
                    styleSpan = new StyleSpan(2);
                    if (((SelectedTextData) alstd.get(i)).isText_bold()) {
                        spannableString.setSpan(new StyleSpan(1), ((SelectedTextData) alstd.get(i)).getStart(), ((SelectedTextData) alstd.get(i)).getEnd(), 0);
                    } else {
                        spannableString.removeSpan(styleSpan);
                    }
                    if (((SelectedTextData) alstd.get(i)).isText_italic()) {
                        spannableString.setSpan(new StyleSpan(2), ((SelectedTextData) alstd.get(i)).getStart(), ((SelectedTextData) alstd.get(i)).getEnd(), 0);
                    } else {
                        spannableString.removeSpan(styleSpan);
                    }
                    if (((SelectedTextData) alstd.get(i)).isText_underline()) {
                        spannableString.setSpan(new UnderlineSpan(), ((SelectedTextData) alstd.get(i)).getStart(), ((SelectedTextData) alstd.get(i)).getEnd(), 0);
                    } else {
                        spannableString.removeSpan(new UnderlineSpan());
                    }
                    if (((SelectedTextData) alstd.get(i)).isText_strike()) {
                        spannableString.setSpan(new StrikethroughSpan(), ((SelectedTextData) alstd.get(i)).getStart(), ((SelectedTextData) alstd.get(i)).getEnd(), 0);
                    } else {
                        spannableString.removeSpan(new StrikethroughSpan());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        SpannableStringBuilder builder = new SpannableStringBuilder().append(spannableString);
        this.textView.setText(builder.subSequence(0, builder.length()));
    }

    private void updateColors() {
        updateColorBarText(this.textView.getCurrentTextColor());
        if (this.arrayfortv.size() > 0) {
            updateCircleBarsPunch(((SelectedTextData) this.arrayfortv.get(0)).getText_color());
        }
    }

    public void setFontonSelected(String ttf) {
        int i;
        SpannableString spannableString = new SpannableString(this.textView.getText().toString());
        ArrayList<SelectedTextData> alstd = null;
        if (this.textView.getTag().equals("text_tv")) {
            for (i = 0; i < this.arrayfortv.size(); i++) {
                SelectedTextData d = (SelectedTextData) this.arrayfortv.get(i);
                d.setText_ttf(ttf);
                this.arrayfortv.set(i, d);
                alstd = this.arrayfortv;
            }
        }
        if (alstd != null) {
            for (i = 0; i < alstd.size(); i++) {
                if (((SelectedTextData) alstd.get(i)).getEnd() <= this.textView.getText().length()) {
                    try {
                        Typeface ttq;
                        spannableString.setSpan(new TextAppearanceSpan(null, 0, (int) (((float) ((SelectedTextData) alstd.get(i)).getText_size()) * getResources().getDisplayMetrics().density), null, null), ((SelectedTextData) alstd.get(i)).getStart(), ((SelectedTextData) alstd.get(i)).getEnd(), 0);
                        spannableString.setSpan(new ForegroundColorSpan(((SelectedTextData) alstd.get(i)).getText_color()), ((SelectedTextData) alstd.get(i)).getStart(), ((SelectedTextData) alstd.get(i)).getEnd(), 33);
                        if (((SelectedTextData) alstd.get(i)).getText_ttf().equals(BuildConfig.FLAVOR)) {
                            ttq = this.textView.getTypeface();
                        } else {
                            ttq = Typeface.createFromAsset(getAssets(), ((SelectedTextData) alstd.get(i)).getText_ttf());
                        }
                        spannableString.setSpan(new CustomTypefaceSpan(ttq), ((SelectedTextData) alstd.get(i)).getStart(), ((SelectedTextData) alstd.get(i)).getEnd(), 0);
                        spannableString.setSpan(new CustomShadowSpan(((SelectedTextData) alstd.get(i)).getText_shadowradius(), ((SelectedTextData) alstd.get(i)).getText_shadowdx(), ((SelectedTextData) alstd.get(i)).getText_shadowdy(), ((SelectedTextData) alstd.get(i)).getText_shadowcolor()), ((SelectedTextData) alstd.get(i)).getStart(), ((SelectedTextData) alstd.get(i)).getEnd(), 0);
                        Shader sadr = null;
                        if (!((SelectedTextData) alstd.get(i)).getText_shader().equals("null")) {
                            Shader bitmapShader = new BitmapShader(BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(((SelectedTextData) alstd.get(i)).getText_shader(), "drawable", getPackageName())), TileMode.MIRROR, TileMode.MIRROR);
                        }
                        spannableString.setSpan(new CustomShaderSpan(sadr), ((SelectedTextData) alstd.get(i)).getStart(), ((SelectedTextData) alstd.get(i)).getEnd(), 0);
                        if (((SelectedTextData) alstd.get(i)).isText_bold()) {
                            spannableString.setSpan(new StyleSpan(1), ((SelectedTextData) alstd.get(i)).getStart(), ((SelectedTextData) alstd.get(i)).getEnd(), 0);
                        }
                        if (((SelectedTextData) alstd.get(i)).isText_italic()) {
                            spannableString.setSpan(new StyleSpan(2), ((SelectedTextData) alstd.get(i)).getStart(), ((SelectedTextData) alstd.get(i)).getEnd(), 0);
                        }
                        if (((SelectedTextData) alstd.get(i)).isText_underline()) {
                            spannableString.setSpan(new UnderlineSpan(), ((SelectedTextData) alstd.get(i)).getStart(), ((SelectedTextData) alstd.get(i)).getEnd(), 0);
                        }
                        if (((SelectedTextData) alstd.get(i)).isText_strike()) {
                            spannableString.setSpan(new StrikethroughSpan(), ((SelectedTextData) alstd.get(i)).getStart(), ((SelectedTextData) alstd.get(i)).getEnd(), 0);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            SpannableStringBuilder builder = new SpannableStringBuilder().append(spannableString);
            this.textView.setText(builder.subSequence(0, builder.length()));
            int normaltextSize = (int) this.textView.getTextSize();
            this.textSizeOffset = comapreSizes1(normaltextSize, (int) (((float) ((SelectedTextData) alstd.get(0)).getText_size()) * getResources().getDisplayMetrics().density), alstd) - normaltextSize;
            if (this.textSizeOffset < 0) {
                this.textSizeOffset = 5;
            }
            Log.e("textSizeOffset", BuildConfig.FLAVOR + this.textSizeOffset);
            updateTextSizeonScale(this.rl.getWidth(), this.rl.getHeight());
        }
    }

    public void setColoronSelected(int color) {
        int i;
        Log.e("coloredText", BuildConfig.FLAVOR + this.textView.getText().toString());
        SpannableString spannableString = new SpannableString(this.textView.getText().toString());
        ArrayList<SelectedTextData> alstd = null;
        if (this.textView.getTag().equals("text_tv") && this.arrayfortv.size() > 0) {
            for (i = 0; i < this.arrayfortv.size(); i++) {
                SelectedTextData d = (SelectedTextData) this.arrayfortv.get(i);
                d.setText_color(color);
                this.arrayfortv.set(i, d);
                alstd = this.arrayfortv;
                Log.e("coloredText", BuildConfig.FLAVOR + ((SelectedTextData) alstd.get(i)).toString());
            }
        }
        if (alstd != null) {
            for (i = 0; i < alstd.size(); i++) {
                if (((SelectedTextData) alstd.get(i)).getEnd() <= this.textView.getText().length()) {
                    try {
                        Typeface ttq;
                        spannableString.setSpan(new TextAppearanceSpan(null, 0, (int) (((float) ((SelectedTextData) alstd.get(i)).getText_size()) * getResources().getDisplayMetrics().density), null, null), ((SelectedTextData) alstd.get(i)).getStart(), ((SelectedTextData) alstd.get(i)).getEnd(), 0);
                        spannableString.setSpan(new ForegroundColorSpan(((SelectedTextData) alstd.get(i)).getText_color()), ((SelectedTextData) alstd.get(i)).getStart(), ((SelectedTextData) alstd.get(i)).getEnd(), 33);
                        if (((SelectedTextData) alstd.get(i)).getText_ttf().equals(BuildConfig.FLAVOR)) {
                            ttq = this.textView.getTypeface();
                        } else {
                            ttq = Typeface.createFromAsset(getAssets(), ((SelectedTextData) alstd.get(i)).getText_ttf());
                        }
                        spannableString.setSpan(new CustomTypefaceSpan(ttq), ((SelectedTextData) alstd.get(i)).getStart(), ((SelectedTextData) alstd.get(i)).getEnd(), 0);
                        spannableString.setSpan(new CustomShadowSpan(((SelectedTextData) alstd.get(i)).getText_shadowradius(), ((SelectedTextData) alstd.get(i)).getText_shadowdx(), ((SelectedTextData) alstd.get(i)).getText_shadowdy(), ((SelectedTextData) alstd.get(i)).getText_shadowcolor()), ((SelectedTextData) alstd.get(i)).getStart(), ((SelectedTextData) alstd.get(i)).getEnd(), 0);
                        Shader sadr = null;
                        if (!((SelectedTextData) alstd.get(i)).getText_shader().equals("null")) {
                            sadr = new BitmapShader(BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(((SelectedTextData) alstd.get(i)).getText_shader(), "drawable", getPackageName())), TileMode.MIRROR, TileMode.MIRROR);
                        }
                        spannableString.setSpan(new CustomShaderSpan(sadr), ((SelectedTextData) alstd.get(i)).getStart(), ((SelectedTextData) alstd.get(i)).getEnd(), 0);
                        if (((SelectedTextData) alstd.get(i)).isText_bold()) {
                            spannableString.setSpan(new StyleSpan(1), ((SelectedTextData) alstd.get(i)).getStart(), ((SelectedTextData) alstd.get(i)).getEnd(), 0);
                        }
                        if (((SelectedTextData) alstd.get(i)).isText_italic()) {
                            spannableString.setSpan(new StyleSpan(2), ((SelectedTextData) alstd.get(i)).getStart(), ((SelectedTextData) alstd.get(i)).getEnd(), 0);
                        }
                        if (((SelectedTextData) alstd.get(i)).isText_underline()) {
                            spannableString.setSpan(new UnderlineSpan(), ((SelectedTextData) alstd.get(i)).getStart(), ((SelectedTextData) alstd.get(i)).getEnd(), 0);
                        }
                        if (((SelectedTextData) alstd.get(i)).isText_strike()) {
                            spannableString.setSpan(new StrikethroughSpan(), ((SelectedTextData) alstd.get(i)).getStart(), ((SelectedTextData) alstd.get(i)).getEnd(), 0);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        SpannableStringBuilder builder = new SpannableStringBuilder().append(spannableString);
        this.textView.setText(builder.subSequence(0, builder.length()));
    }

    public void setShadowonSelected(float radius, float dx, float dy, int color) {
        int i;
        SpannableString spannableString = new SpannableString(this.textView.getText().toString());
        ArrayList<SelectedTextData> alstd = null;
        if (this.textView.getTag().equals("text_tv")) {
            for (i = 0; i < this.arrayfortv.size(); i++) {
                SelectedTextData d = (SelectedTextData) this.arrayfortv.get(i);
                d.setText_shadowradius(radius);
                d.setText_shadowdx(dx);
                d.setText_shadowdy(dy);
                d.setText_shadowcolor(color);
                this.arrayfortv.set(i, d);
                alstd = this.arrayfortv;
            }
        }
        if (alstd != null) {
            for (i = 0; i < alstd.size(); i++) {
                if (((SelectedTextData) alstd.get(i)).getEnd() <= this.textView.getText().length()) {
                    try {
                        Typeface ttq;
                        spannableString.setSpan(new TextAppearanceSpan(null, 0, (int) (((float) ((SelectedTextData) alstd.get(i)).getText_size()) * getResources().getDisplayMetrics().density), null, null), ((SelectedTextData) alstd.get(i)).getStart(), ((SelectedTextData) alstd.get(i)).getEnd(), 0);
                        spannableString.setSpan(new ForegroundColorSpan(((SelectedTextData) alstd.get(i)).getText_color()), ((SelectedTextData) alstd.get(i)).getStart(), ((SelectedTextData) alstd.get(i)).getEnd(), 33);
                        if (((SelectedTextData) alstd.get(i)).getText_ttf().equals(BuildConfig.FLAVOR)) {
                            ttq = this.textView.getTypeface();
                        } else {
                            ttq = Typeface.createFromAsset(getAssets(), ((SelectedTextData) alstd.get(i)).getText_ttf());
                        }
                        spannableString.setSpan(new CustomTypefaceSpan(ttq), ((SelectedTextData) alstd.get(i)).getStart(), ((SelectedTextData) alstd.get(i)).getEnd(), 0);
                        spannableString.setSpan(new CustomShadowSpan(((SelectedTextData) alstd.get(i)).getText_shadowradius(), ((SelectedTextData) alstd.get(i)).getText_shadowdx(), ((SelectedTextData) alstd.get(i)).getText_shadowdy(), ((SelectedTextData) alstd.get(i)).getText_shadowcolor()), ((SelectedTextData) alstd.get(i)).getStart(), ((SelectedTextData) alstd.get(i)).getEnd(), 0);
                        Shader sadr = null;
                        if (!((SelectedTextData) alstd.get(i)).getText_shader().equals("null")) {
                            sadr = new BitmapShader(BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(((SelectedTextData) alstd.get(i)).getText_shader(), "drawable", getPackageName())), TileMode.MIRROR, TileMode.MIRROR);
                        }
                        spannableString.setSpan(new CustomShaderSpan(sadr), ((SelectedTextData) alstd.get(i)).getStart(), ((SelectedTextData) alstd.get(i)).getEnd(), 0);
                        if (((SelectedTextData) alstd.get(i)).isText_bold()) {
                            spannableString.setSpan(new StyleSpan(1), ((SelectedTextData) alstd.get(i)).getStart(), ((SelectedTextData) alstd.get(i)).getEnd(), 0);
                        }
                        if (((SelectedTextData) alstd.get(i)).isText_italic()) {
                            spannableString.setSpan(new StyleSpan(2), ((SelectedTextData) alstd.get(i)).getStart(), ((SelectedTextData) alstd.get(i)).getEnd(), 0);
                        }
                        if (((SelectedTextData) alstd.get(i)).isText_underline()) {
                            spannableString.setSpan(new UnderlineSpan(), ((SelectedTextData) alstd.get(i)).getStart(), ((SelectedTextData) alstd.get(i)).getEnd(), 0);
                        }
                        if (((SelectedTextData) alstd.get(i)).isText_strike()) {
                            spannableString.setSpan(new StrikethroughSpan(), ((SelectedTextData) alstd.get(i)).getStart(), ((SelectedTextData) alstd.get(i)).getEnd(), 0);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        SpannableStringBuilder builder = new SpannableStringBuilder().append(spannableString);
        this.textView.setText(builder.subSequence(0, builder.length()));
    }

    public void setShaderonSelected(String shader) {
        int i;
        SpannableString spannableString = new SpannableString(this.textView.getText().toString());
        ArrayList<SelectedTextData> alstd = null;
        if (this.textView.getTag().equals("text_tv")) {
            for (i = 0; i < this.arrayfortv.size(); i++) {
                SelectedTextData d = (SelectedTextData) this.arrayfortv.get(i);
                d.setText_shader(shader);
                this.arrayfortv.set(i, d);
                alstd = this.arrayfortv;
            }
        }
        if (alstd != null) {
            for (i = 0; i < alstd.size(); i++) {
                if (((SelectedTextData) alstd.get(i)).getEnd() <= this.textView.getText().length()) {
                    try {
                        Typeface ttq;
                        spannableString.setSpan(new TextAppearanceSpan(null, 0, (int) (((float) ((SelectedTextData) alstd.get(i)).getText_size()) * getResources().getDisplayMetrics().density), null, null), ((SelectedTextData) alstd.get(i)).getStart(), ((SelectedTextData) alstd.get(i)).getEnd(), 0);
                        spannableString.setSpan(new ForegroundColorSpan(((SelectedTextData) alstd.get(i)).getText_color()), ((SelectedTextData) alstd.get(i)).getStart(), ((SelectedTextData) alstd.get(i)).getEnd(), 33);
                        if (((SelectedTextData) alstd.get(i)).getText_ttf().equals(BuildConfig.FLAVOR)) {
                            ttq = this.textView.getTypeface();
                        } else {
                            ttq = Typeface.createFromAsset(getAssets(), ((SelectedTextData) alstd.get(i)).getText_ttf());
                        }
                        spannableString.setSpan(new CustomTypefaceSpan(ttq), ((SelectedTextData) alstd.get(i)).getStart(), ((SelectedTextData) alstd.get(i)).getEnd(), 0);
                        spannableString.setSpan(new CustomShadowSpan(((SelectedTextData) alstd.get(i)).getText_shadowradius(), ((SelectedTextData) alstd.get(i)).getText_shadowdx(), ((SelectedTextData) alstd.get(i)).getText_shadowdy(), ((SelectedTextData) alstd.get(i)).getText_shadowcolor()), ((SelectedTextData) alstd.get(i)).getStart(), ((SelectedTextData) alstd.get(i)).getEnd(), 0);
                        Shader sadr = null;
                        if (!((SelectedTextData) alstd.get(i)).getText_shader().equals("null")) {
                            sadr = new BitmapShader(BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(((SelectedTextData) alstd.get(i)).getText_shader(), "drawable", getPackageName())), TileMode.MIRROR, TileMode.MIRROR);
                        }
                        spannableString.setSpan(new CustomShaderSpan(sadr), ((SelectedTextData) alstd.get(i)).getStart(), ((SelectedTextData) alstd.get(i)).getEnd(), 0);
                        if (((SelectedTextData) alstd.get(i)).isText_bold()) {
                            spannableString.setSpan(new StyleSpan(1), ((SelectedTextData) alstd.get(i)).getStart(), ((SelectedTextData) alstd.get(i)).getEnd(), 0);
                        }
                        if (((SelectedTextData) alstd.get(i)).isText_italic()) {
                            spannableString.setSpan(new StyleSpan(2), ((SelectedTextData) alstd.get(i)).getStart(), ((SelectedTextData) alstd.get(i)).getEnd(), 0);
                        }
                        if (((SelectedTextData) alstd.get(i)).isText_underline()) {
                            spannableString.setSpan(new UnderlineSpan(), ((SelectedTextData) alstd.get(i)).getStart(), ((SelectedTextData) alstd.get(i)).getEnd(), 0);
                        }
                        if (((SelectedTextData) alstd.get(i)).isText_strike()) {
                            spannableString.setSpan(new StrikethroughSpan(), ((SelectedTextData) alstd.get(i)).getStart(), ((SelectedTextData) alstd.get(i)).getEnd(), 0);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        if (!shader.equals("null")) {
            BitmapShader bitmapShader = new BitmapShader(BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(shader, "drawable", getPackageName())), TileMode.MIRROR, TileMode.MIRROR);
        }
        SpannableStringBuilder builder = new SpannableStringBuilder().append(spannableString);
        this.textView.setText(builder.subSequence(0, builder.length()));
    }

    private void initialize() {
        this.dbHelper = new DatabaseHandler(this);
        this.view_width = (float) Constants.dpToPx(getApplicationContext(), 40);
        this.view_height = (float) Constants.dpToPx(getApplication(), 100);
        rr_addtext = findViewById(R.id.rr_addtext);

        this.blur_seekbar_lay = (LinearLayout) findViewById(R.id.blur_seekbar_lay);
        this.img_opacity = (ImageView) findViewById(R.id.img_opacity);
        this.back_arrow_add_quotes = (ImageView) findViewById(R.id.back_arrow_add_quotes);
        this.scroll_all = (RelativeLayout) findViewById(R.id.scroll_all);
        this.complete_img = (RelativeLayout) findViewById(R.id.complete_img);
        this.top_option = (RelativeLayout) findViewById(R.id.top_option);
        this.done_add_quotes = (Button) findViewById(R.id.done_add_quotes);
        this.controller_sticker = (RelativeLayout) findViewById(R.id.controller_sticker);
        this.controll_btn_stckr = (Button) findViewById(R.id.control_btn_stkr);
        this.blur_lay_cntrl = (Button) findViewById(R.id.blur_lay_contrl);
        this.btn_left = (ImageView) findViewById(R.id.left);
        this.btn_top = (ImageView) findViewById(R.id.top);
        this.btn_right = (ImageView) findViewById(R.id.right);
        this.btn_down = (ImageView) findViewById(R.id.down);
        this.duplicate = (Button) findViewById(R.id.duplicate);
        this.duplicate.setTypeface(Constants.getTextTypeface(this));
        this.tab_cntrl_stkr = (RelativeLayout) findViewById(R.id.controlls_stkr);
        this.tab_clrs_stkr = (RelativeLayout) findViewById(R.id.clr_opacity);
        this.tab_cntrl_stkr.setOnClickListener(this);
        this.tab_clrs_stkr.setOnClickListener(this);
        this.cntrls_stkr_lay = (RelativeLayout) findViewById(R.id.controlls);
        this.cntrls_stkrclr_lay = (RelativeLayout) findViewById(R.id.clr_stkr);
        this.picture_txt = (CustomTextView) findViewById(R.id.footer_text3);
        this.sticker_txt = (CustomTextView) findViewById(R.id.footer_text4);
        this.effect_txt = (CustomTextView) findViewById(R.id.footer_text5);
        this.picture_txt.setTextColor(-1);
        this.sticker_txt.setTextColor(-1);
        this.effect_txt.setTextColor(-1);
        this.tab_text = (RelativeLayout) findViewById(R.id.tabtxtrel);
        this.tab_punch = (RelativeLayout) findViewById(R.id.tabpunchrel);
        this.tab_shadow_punch = (RelativeLayout) findViewById(R.id.tabshadowpunch);
        this.tab_shadow_txt = (RelativeLayout) findViewById(R.id.tabShadowtxt);
        this.tab_shader_punch = (RelativeLayout) findViewById(R.id.tabshaderpunch);
        this.tab_shader_txt = (RelativeLayout) findViewById(R.id.tabshadertxt);
        this.tab_font_punch = (RelativeLayout) findViewById(R.id.tabfontpunch);
        this.tab_font_txt = (RelativeLayout) findViewById(R.id.tabfonttxt);
        this.tab_format_txt = (RelativeLayout) findViewById(R.id.tabformattxt);
        this.tab_format_punch = (RelativeLayout) findViewById(R.id.tabformatpunch);
        this.gpuImageview = (GPUImageView) findViewById(R.id.gpuimage);
        this.done_add_quotes.setOnClickListener(this);
        this.back_arrow_add_quotes.setOnClickListener(this);
        this.tab_text.setOnClickListener(this);
        this.tab_punch.setOnClickListener(this);
        this.tab_shadow_punch.setOnClickListener(this);
        this.tab_shadow_txt.setOnClickListener(this);
        this.tab_shader_punch.setOnClickListener(this);
        this.tab_shader_txt.setOnClickListener(this);
        this.tab_font_punch.setOnClickListener(this);
        this.tab_font_txt.setOnClickListener(this);
        this.tab_format_txt.setOnClickListener(this);
        this.tab_format_punch.setOnClickListener(this);
        this.img_format_txt = (ImageView) findViewById(R.id.img_format_txt);
        this.img_format_punch = (ImageView) findViewById(R.id.img_format_punch);
        this.img_font_txt = (ImageView) findViewById(R.id.img_font_txt);
        this.img_font_punch = (ImageView) findViewById(R.id.img_font_punch);
        this.img_shadow_txt = (ImageView) findViewById(R.id.img_shadow_txt);
        this.img_shadow_punch = (ImageView) findViewById(R.id.img_shadow_punch);
        this.img_shader_txt = (ImageView) findViewById(R.id.img_shader_txt);
        this.img_shader_punch = (ImageView) findViewById(R.id.img_shader_punch);
        this.img_color_txt = (ImageView) findViewById(R.id.img_color_txt);
        this.img_color_punch = (ImageView) findViewById(R.id.img_color_punch);
        this.format_txt = (CustomTextView) findViewById(R.id.txt_format);
        this.format_punch = (CustomTextView) findViewById(R.id.punch_format);
        this.font_txt = (CustomTextView) findViewById(R.id.txt_font);
        this.font_punch = (CustomTextView) findViewById(R.id.punch_font);
        this.shadow_txt = (CustomTextView) findViewById(R.id.txt_shadow);
        this.shadow_punch = (CustomTextView) findViewById(R.id.punch_shadow);
        this.shader_txt = (CustomTextView) findViewById(R.id.txt_shader);
        this.shader_punch = (CustomTextView) findViewById(R.id.punch_shader);
        this.color_txt = (CustomTextView) findViewById(R.id.txt_clr);
        this.color_punch = (CustomTextView) findViewById(R.id.txt_punch);
        this.contrl_txt = (CustomTextView) findViewById(R.id.controls_txt);
        this.clr_opacity_txt = (CustomTextView) findViewById(R.id.clr_opacity_txt);
        this.rl = (RelativeLayout) findViewById(R.id.rl);
        this.format_punch.setTextColor(-1);
        this.font_punch.setTextColor(-1);
        this.color_punch.setTextColor(-1);
        this.shadow_punch.setTextColor(-1);
        this.shader_punch.setTextColor(-1);
        this.lay_hue = (RelativeLayout) findViewById(R.id.lay_hue);
        this.lay_colorfilter = (RelativeLayout) findViewById(R.id.lay_colorfilter);
        this.text_tv = (EditText) findViewById(R.id.text);
        this.text_tv.setTag("text_tv");
        isTextTouchListener = setDefaultTouchListener(true);
        this.rotate_iv = (ImageView) findViewById(R.id.rotate);
        this.rotate_iv.setTag("rotate_iv");
        this.rotate_iv.setOnTouchListener(this.mTouchListener);
        this.delete_iv = (ImageView) findViewById(R.id.delete);
        this.delete_iv.setOnClickListener(this.deleteClickListener);
        this.edit_ivTxt = (CustomTextView) findViewById(R.id.edit);
        this.edit_ivTxt.setOnClickListener(this.editTextClickListener);
        this.move_iv = (ImageView) findViewById(R.id.move);
        this.move_iv.setTag("move_iv");
        this.move_iv.setOnTouchListener(this.mTouchListener);
        this.scale_iv = (ImageView) findViewById(R.id.scale);
        this.scale_iv.setTag("scale_iv");
        this.scale_iv.setOnTouchListener(this.mTouchListener);
        Button fnt1 = (Button) findViewById(R.id.fnt1);
        Button fnt2 = (Button) findViewById(R.id.fnt2);
        Button fnt3 = (Button) findViewById(R.id.fnt3);
        Button fnt4 = (Button) findViewById(R.id.fnt4);
        Button fnt5 = (Button) findViewById(R.id.fnt5);
        Button fnt6 = (Button) findViewById(R.id.fnt6);
        Button fnt7 = (Button) findViewById(R.id.fnt7);
        Button fnt8 = (Button) findViewById(R.id.fnt8);
        Button fnt9 = (Button) findViewById(R.id.fnt9);
        Button fnt10 = (Button) findViewById(R.id.fnt10);
        Button fnt11 = (Button) findViewById(R.id.fnt11);
        Button fnt12 = (Button) findViewById(R.id.fnt12);
        Button fnt13 = (Button) findViewById(R.id.fnt13);
        Button fnt14 = (Button) findViewById(R.id.fnt14);
        Button fnt15 = (Button) findViewById(R.id.fnt15);
        Button fnt16 = (Button) findViewById(R.id.fnt16);
        Button fnt17 = (Button) findViewById(R.id.fnt17);
        Button fnt18 = (Button) findViewById(R.id.fnt18);
        Button fnt19 = (Button) findViewById(R.id.fnt19);
        Button fnt20 = (Button) findViewById(R.id.fnt20);
        Button fnt21 = (Button) findViewById(R.id.fnt21);
        Button fnt22 = (Button) findViewById(R.id.fnt22);
        Button fnt23 = (Button) findViewById(R.id.fnt23);
        Button fnt24 = (Button) findViewById(R.id.fnt24);
        Button fnt25 = (Button) findViewById(R.id.fnt25);
        Button fnt26 = (Button) findViewById(R.id.fnt26);
        Button fnt27 = (Button) findViewById(R.id.fnt27);
        Button fnt28 = (Button) findViewById(R.id.fnt28);
        Button fnt29 = (Button) findViewById(R.id.fnt29);
        this.g1 = (Button) findViewById(R.id.g1);
        this.g2 = (Button) findViewById(R.id.g2);
        this.g3 = (Button) findViewById(R.id.g3);
        this.sd_color = (Button) findViewById(R.id.sd_color);
        this.sh = (Button) findViewById(R.id.sh);
        this.sh1 = (Button) findViewById(R.id.sh1);
        this.sh2 = (Button) findViewById(R.id.sh2);
        this.sh3 = (Button) findViewById(R.id.sh3);
        this.sh4 = (Button) findViewById(R.id.sh4);
        this.sh5 = (Button) findViewById(R.id.sh5);
        this.sh6 = (Button) findViewById(R.id.sh6);
        this.sh7 = (Button) findViewById(R.id.sh7);
        this.sh8 = (Button) findViewById(R.id.sh8);
        this.sh9 = (Button) findViewById(R.id.sh9);
        this.sh10 = (Button) findViewById(R.id.sh10);
        this.bold = (Button) findViewById(R.id.bold);
        this.italic = (Button) findViewById(R.id.italic);
        this.underline = (Button) findViewById(R.id.underline);
        this.strike = (Button) findViewById(R.id.strike);
        this.ttD = Typeface.createFromAsset(getAssets(), "DroidSans.ttf");
        this.ttf1 = Typeface.createFromAsset(getAssets(), "majalla.ttf");
        this.ttf2 = Typeface.createFromAsset(getAssets(), "MVBOLI.TTF");
        this.ttf3 = Typeface.createFromAsset(getAssets(), "PortLligatSans-Regular.ttf");
        this.ttf4 = Typeface.createFromAsset(getAssets(), "ROD.TTF");
        this.ttf5 = Typeface.createFromAsset(getAssets(), "Aspergit.otf");
        this.ttf6 = Typeface.createFromAsset(getAssets(), "windsong.ttf");
        this.ttf7 = Typeface.createFromAsset(getAssets(), "Walkway_Bold.ttf");
        this.ttf8 = Typeface.createFromAsset(getAssets(), "Sofia-Regular.otf");
        this.ttf9 = Typeface.createFromAsset(getAssets(), "segoe.ttf");
        this.ttf10 = Typeface.createFromAsset(getAssets(), "Capture_it.ttf");
        this.ttf11 = Typeface.createFromAsset(getAssets(), "Advertising Script Bold Trial.ttf");
        this.ttf12 = Typeface.createFromAsset(getAssets(), "Advertising Script Monoline Trial.ttf");
        this.ttf13 = Typeface.createFromAsset(getAssets(), "Beyond Wonderland.ttf");
        this.ttf14 = Typeface.createFromAsset(getAssets(), "CalliGravity.ttf");
        this.ttf15 = Typeface.createFromAsset(getAssets(), "Cosmic Love.ttf");
        this.ttf16 = Typeface.createFromAsset(getAssets(), "lesser concern shadow.ttf");
        this.ttf17 = Typeface.createFromAsset(getAssets(), "lesser concern.ttf");
        this.ttf18 = Typeface.createFromAsset(getAssets(), "Queen of Heaven.ttf");
        this.ttf19 = Typeface.createFromAsset(getAssets(), "QUIGLEYW.TTF");
        this.ttf20 = Typeface.createFromAsset(getAssets(), "squealer embossed.ttf");
        this.ttf21 = Typeface.createFromAsset(getAssets(), "squealer.ttf");
        this.ttf22 = Typeface.createFromAsset(getAssets(), "still time.ttf");
        this.ttf23 = Typeface.createFromAsset(getAssets(), "Constantia Italic.ttf");
        this.ttf24 = Typeface.createFromAsset(getAssets(), "DejaVuSans_Bold.ttf");
        this.ttf25 = Typeface.createFromAsset(getAssets(), "Aladin_Regular.ttf");
        this.ttf26 = Typeface.createFromAsset(getAssets(), "Adobe Caslon Pro Italic.ttf");
        this.ttf27 = Typeface.createFromAsset(getAssets(), "aparaji.ttf");
        this.ttf28 = Typeface.createFromAsset(getAssets(), "ARDECODE.ttf");
        this.ttf29 = Typeface.createFromAsset(getAssets(), "ufonts_com_ck_scratchy_box.ttf");
        fnt1.setTypeface(this.ttf1);
        fnt2.setTypeface(this.ttf7);
        fnt3.setTypeface(this.ttf3);
        fnt4.setTypeface(this.ttf4);
        fnt5.setTypeface(this.ttf5);
        fnt6.setTypeface(this.ttf6);
        fnt7.setTypeface(this.ttf2);
        fnt8.setTypeface(this.ttf8);
        fnt9.setTypeface(this.ttf9);
        fnt10.setTypeface(this.ttf10);
        fnt11.setTypeface(this.ttf11);
        fnt12.setTypeface(this.ttf12);
        fnt13.setTypeface(this.ttf13);
        fnt14.setTypeface(this.ttf14);
        fnt15.setTypeface(this.ttf15);
        fnt16.setTypeface(this.ttf16);
        fnt17.setTypeface(this.ttf17);
        fnt18.setTypeface(this.ttf18);
        fnt19.setTypeface(this.ttf19);
        fnt20.setTypeface(this.ttf20);
        fnt21.setTypeface(this.ttf21);
        fnt22.setTypeface(this.ttf22);
        fnt23.setTypeface(this.ttf23);
        fnt24.setTypeface(this.ttf24);
        fnt25.setTypeface(this.ttf25);
        fnt26.setTypeface(this.ttf26);
        fnt27.setTypeface(this.ttf27);
        fnt28.setTypeface(this.ttf28);
        fnt29.setTypeface(this.ttf29);
        this.recyclerView = (RecyclerView) findViewById(R.id.res_recyclerview_);
        this.styl_recycler = (RecyclerView) findViewById(R.id.styl_recycler);
        this.fortext = (RelativeLayout) findViewById(R.id.fortext);
        this.rel_effects = (RelativeLayout) findViewById(R.id.rel_effects);
        this.alledit_ll = (RelativeLayout) findViewById(R.id.alledit_ll);
        this.menu_ll = (LinearLayout) findViewById(R.id.menu_ll);
        this.formatall_type = (LinearLayout) findViewById(R.id.format_alltype);
        this.scroll_of_all_effects = (LinearLayout) findViewById(R.id.scroll_of_all_efct);
        this.scroll_of_all_backgrounds = (LinearLayout) findViewById(R.id.scroll_of_all_backgrounds);
        this.sb_effects = (SeekBar) findViewById(R.id.sb_effects);
        this.sb_effectsfilter = (SeekBar) findViewById(R.id.sb_effectsfilter);
        this.sb_opctyfilter = (SeekBar) findViewById(R.id.sb_opctyfilter);
        this.hue_seekbar = (SeekBar) findViewById(R.id.hue_seekBar);
        this.colorPicker = (LineColorPicker) findViewById(R.id.picker);
        this.la_size = (RelativeLayout) findViewById(R.id.la_size);
        this.la_fonts = (RelativeLayout) findViewById(R.id.la_fonts);
        this.la_color = (RelativeLayout) findViewById(R.id.la_color);
        this.la_shadow = (RelativeLayout) findViewById(R.id.la_shadow);
        this.la_shader = (RelativeLayout) findViewById(R.id.la_shader);
        this.re_template = (RelativeLayout) findViewById(R.id.re_template);
        this.font_rel = (RelativeLayout) findViewById(R.id.font_rel);
        this.color_rel = (RelativeLayout) findViewById(R.id.color_rel);
        this.shadow_rel = (RelativeLayout) findViewById(R.id.shadow_rel);
        this.shader_rel = (RelativeLayout) findViewById(R.id.shader_rel);
        this.lay_viewStyle = (RelativeLayout) findViewById(R.id.lay_viewStyle);
        this.list_of_brnd = (ImageView) findViewById(R.id.list_of_brnd);
        this.list_of_sticker = (ImageView) findViewById(R.id.list_of_sticker);
        this.footer_effects_image = (ImageView) findViewById(R.id.footer_effects_image);
        this.size = (ImageView) findViewById(R.id.size);
        this.fonts = (ImageView) findViewById(R.id.fonts);
        this.color_ = (ImageView) findViewById(R.id.color);
        this.shadow = (ImageView) findViewById(R.id.shadow);
        this.shader = (ImageView) findViewById(R.id.shader);
        this.list_of_style = (ImageView) findViewById(R.id.list_of_style);
        this.re_background = (RelativeLayout) findViewById(R.id.re_background);
        this.re_sticker = (RelativeLayout) findViewById(R.id.re_sticker);
        this.hue_seekbar.setProgress(1);
        this.transparency_seekbar = (SeekBar) findViewById(R.id.trans_seekBar);
        this.transparency_seekbar.setProgress(255);
        this.transparency_seekbar.setOnSeekBarChangeListener(this);
        this.shadow_seekbar = (SeekBar) findViewById(R.id.shadow_seekBar);
        this.shadow_seekbar.setProgress(1);
        this.shadow_seekbar.setOnSeekBarChangeListener(this);
        this.sb_effectsfilter.setProgress(25);
        this.re_background.setOnClickListener(this);
        this.rel_effects.setOnClickListener(this);
        this.re_sticker.setOnClickListener(this);
        this.sb_effects.setOnSeekBarChangeListener(this);
        this.sb_effectsfilter.setOnSeekBarChangeListener(this);
        this.sb_opctyfilter.setOnSeekBarChangeListener(this);
        this.hue_seekbar.setOnSeekBarChangeListener(this);
        this.img_opacity.setImageAlpha(25);
        this.sb_opctyfilter.setProgress(50);
        this.img_opacity.setVisibility(View.VISIBLE);
        int[] colors = new int[this.pallete.length];
        for (int i = 0; i < colors.length; i++) {
            colors[i] = Color.parseColor(this.pallete[i]);
        }
        this.colorPicker.setColors(colors);
        this.colorPicker.setSelectedColor(colors[8]);
        this.color = this.colorPicker.getColor();
        this.colorPicker.setOnColorChangedListener(new OnColorChangedListener() {
            public void onColorChanged(int c) {
                AddTextQuotesActivity.this.color = c;
                int childCount1 = AddTextQuotesActivity.this.fortext.getChildCount();

                Log.e("childCount1", "" + childCount1);
                for (int i = 0; i < childCount1; i++) {
                    View view1 = AddTextQuotesActivity.this.fortext.getChildAt(i);
                    if ((view1 instanceof ResizableImageview) && ((ResizableImageview) view1).getBorderVisbilty()) {
                        Log.e("color", "" + color);
                        Log.e("setColorFilter", "" + i);
                        ((ResizableImageview) view1).setColorFilter(AddTextQuotesActivity.this.color);
                    }
                }
            }
        });
        this.btn_left.setOnTouchListener(new RepeatListener(200, 100, new OnClickListener() {
            public void onClick(View view) {
                AddTextQuotesActivity.this.updatePositionSticker("decX");
            }
        }));
        this.btn_right.setOnTouchListener(new RepeatListener(200, 100, new OnClickListener() {
            public void onClick(View view) {
                AddTextQuotesActivity.this.updatePositionSticker("incrX");
            }
        }));
        this.btn_top.setOnTouchListener(new RepeatListener(200, 100, new OnClickListener() {
            public void onClick(View view) {
                AddTextQuotesActivity.this.updatePositionSticker("decY");
            }
        }));
        this.btn_down.setOnTouchListener(new RepeatListener(200, 100, new OnClickListener() {
            public void onClick(View view) {
                AddTextQuotesActivity.this.updatePositionSticker("incrY");
            }
        }));
        this.controller_sticker.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            }
        });
        this.formatall_type.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            }
        });
        this.controll_btn_stckr.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (AddTextQuotesActivity.this.isStickerLayVisible) {
                    AddTextQuotesActivity.this.controll_btn_stckr.setBackgroundResource(R.drawable.slide_up);
                    AddTextQuotesActivity.this.controller_sticker.setVisibility(View.GONE);
                    AddTextQuotesActivity.this.isStickerLayVisible = false;
                    return;
                }
                AddTextQuotesActivity.this.controll_btn_stckr.setBackgroundResource(R.drawable.slide_down);
                AddTextQuotesActivity.this.controller_sticker.setVisibility(View.VISIBLE);
                AddTextQuotesActivity.this.isStickerLayVisible = true;
            }
        });
        this.duplicate.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                int childCount = AddTextQuotesActivity.this.fortext.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View view = AddTextQuotesActivity.this.fortext.getChildAt(i);
                    if ((view instanceof ResizableImageview) && ((ResizableImageview) view).getBorderVisbilty()) {
                        ResizableImageview riv = new ResizableImageview(AddTextQuotesActivity.this);
                        riv.setComponentInfo(((ResizableImageview) view).getComponentInfo());
                        AddTextQuotesActivity.this.fortext.addView(riv);
                        ((ResizableImageview) view).setBorderVisibility(false);
                        riv.setMainLayoutWH((float) AddTextQuotesActivity.this.fortext.getWidth(), (float) AddTextQuotesActivity.this.fortext.getHeight());
                        riv.setOnTouchListener(new MultiTouchListener().enableRotation(true).setOnTouchCallbackListener(AddTextQuotesActivity.this));
                        riv.setOnTouchCallbackListener(AddTextQuotesActivity.this);
                        riv.setBorderVisibility(true);
                    }
                }
            }
        });
        this.blur_lay_cntrl.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (AddTextQuotesActivity.this.isBlurLayVisible) {
                    AddTextQuotesActivity.this.blur_lay_cntrl.setBackgroundResource(R.drawable.slide_up);
                    AddTextQuotesActivity.this.blur_seekbar_lay.setVisibility(View.GONE);
                    AddTextQuotesActivity.this.isBlurLayVisible = false;
                    return;
                }
                AddTextQuotesActivity.this.blur_lay_cntrl.setBackgroundResource(R.drawable.slide_down);
                AddTextQuotesActivity.this.blur_seekbar_lay.setVisibility(View.VISIBLE);
                AddTextQuotesActivity.this.sb_effectsfilter.setVisibility(View.VISIBLE);
                AddTextQuotesActivity.this.sb_opctyfilter.setVisibility(View.VISIBLE);
                AddTextQuotesActivity.this.isBlurLayVisible = true;
            }
        });
        this.gpuImageview.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                AddTextQuotesActivity.this.isStickerLayVisible = true;
                if (AddTextQuotesActivity.this.isBlurLayVisible) {
                    AddTextQuotesActivity.this.blur_lay_cntrl.setVisibility(View.VISIBLE);
                    AddTextQuotesActivity.this.blur_lay_cntrl.setBackgroundResource(R.drawable.slide_down);
                    AddTextQuotesActivity.this.blur_seekbar_lay.setVisibility(View.VISIBLE);
                    AddTextQuotesActivity.this.sb_effectsfilter.setVisibility(View.VISIBLE);
                    AddTextQuotesActivity.this.sb_opctyfilter.setVisibility(View.VISIBLE);
                } else {
                    AddTextQuotesActivity.this.blur_lay_cntrl.setVisibility(View.VISIBLE);
                    AddTextQuotesActivity.this.blur_lay_cntrl.setBackgroundResource(R.drawable.slide_up);
                    AddTextQuotesActivity.this.blur_seekbar_lay.setVisibility(View.GONE);
                    AddTextQuotesActivity.this.sb_effectsfilter.setVisibility(View.GONE);
                    AddTextQuotesActivity.this.sb_opctyfilter.setVisibility(View.GONE);
                }
                AddTextQuotesActivity.this.controll_btn_stckr.setVisibility(View.GONE);
                AddTextQuotesActivity.this.controller_sticker.setVisibility(View.GONE);
                AddTextQuotesActivity.this.removeImageViewControll();
                AddTextQuotesActivity.this.fortext.post(new Runnable() {
                    public void run() {
                        PictureConstant.removeTextViewControll(AddTextQuotesActivity.this.fortext);
                    }
                });
                AddTextQuotesActivity.this.selectFocus = true;
                AddTextQuotesActivity.this.setDefault();
                AddTextQuotesActivity.this.flag2 = false;
                if (AddTextQuotesActivity.this.textView != null) {
                    if (AddTextQuotesActivity.this.textView.getTag().equals("text_tv") && AddTextQuotesActivity.this.textView.getText().toString().equals(BuildConfig.FLAVOR)) {
                        AddTextQuotesActivity.this.text_tv.setText(AddTextQuotesActivity.this.getResources().getString(R.string.tab_write));
                    }
                    AddTextQuotesActivity.this.textView.clearFocus();
                    Selection.removeSelection(AddTextQuotesActivity.this.textView.getText());
                    if (AddTextQuotesActivity.this.rl.getVisibility() == View.VISIBLE) {
                        Selection.removeSelection(AddTextQuotesActivity.this.text_tv.getText());
                        AddTextQuotesActivity.this.hideChilds(AddTextQuotesActivity.this.rl);
                    }
                }
                if (AddTextQuotesActivity.this.alledit_ll.getVisibility() == View.VISIBLE) {
                    AddTextQuotesActivity.this.menu_ll.setVisibility(View.VISIBLE);
                    AddTextQuotesActivity.this.alledit_ll.setVisibility(View.GONE);
                    AddTextQuotesActivity.this.sb_effectsfilter.setVisibility(View.VISIBLE);
                    AddTextQuotesActivity.this.sb_opctyfilter.setVisibility(View.VISIBLE);
                    AddTextQuotesActivity.this.blur_lay_cntrl.setVisibility(View.VISIBLE);
                    AddTextQuotesActivity.this.blur_seekbar_lay.setVisibility(View.VISIBLE);
                }
                View view = AddTextQuotesActivity.this.getCurrentFocus();
                if (view != null) {
                    ((InputMethodManager) AddTextQuotesActivity.this.getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });


        rr_addtext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewText();

            }
        });


    }


    void addNewText() {

        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_chagetext_dilaoge);

        Button yes = (Button) dialog.findViewById(R.id.btn_yes);
        Button no = (Button) dialog.findViewById(R.id.btn_no);
        yes.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();


                text_tv.buildDrawingCache();
                try {
                    text_tv.setDrawingCacheEnabled(true);
                    addtext_to_sticker = Bitmap.createBitmap(text_tv.getDrawingCache());
                    text_tv.setDrawingCacheEnabled(false);

                    addSticker("aaa", "COLOR", addtext_to_sticker);

                    return;
                } catch (Exception e3) {
                    e3.printStackTrace();

                    Log.e("sssss", "" + e3.getMessage());
                    return;
                }
            }
        });
        no.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();


    }


    private String setSelectionAll1(EditText text_tv) {
        Selection.removeSelection(text_tv.getText());
        text_tv.setCursorVisible(false);
        int max = text_tv.getText().length();
        text_tv.setSelection(max, max);
        return text_tv.getText().toString();
    }

    private void updateTextSizeonScale(int width, int height) {
        int rectRight = width;
        int rectBottom = height - Constants.dpToPx(this, 75);
        if (rectRight > 0 && rectBottom > 0) {
            int suggestedSize;
            RectF boundsFloat = new RectF(0.0f, 0.0f, (float) rectRight, (float) rectBottom);
            int gravity = this.textView.getGravity();
            TextPaint textPaint = this.textView.getPaint();
            this.spannableString = new SpannableString(this.textView.getText().toString());
            if (this.arrayfortv.size() > 0) {
                suggestedSize = ((SelectedTextData) this.arrayfortv.get(0)).getText_size();
                Log.e("perfectpunchSize2", BuildConfig.FLAVOR + (((float) suggestedSize) * getResources().getDisplayMetrics().density));
            } else {
                suggestedSize = (int) this.textView.getTextSize();
            }
            this.builder = createSpannableString(this.arrayfortv, suggestedSize, this.spannableString, true);
            this.textView.setTextSize(0, (float) (getOptimumTextSize(5, 400, this.builder, boundsFloat, textPaint, gravity) - this.textSizeOffset));
            int k = (int) PictureConstant.convertPixelsToDp((float) (this.textSizeOffset + ((int) Float.valueOf(this.textView.getTextSize()).floatValue())), this);
            Log.e("perfectpunchSize3", BuildConfig.FLAVOR + (((float) k) * getResources().getDisplayMetrics().density));
            if (this.textView.getTag().equals("text_tv")) {
                for (int i = 0; i < this.arrayfortv.size(); i++) {
                    ((SelectedTextData) this.arrayfortv.get(i)).setText_size(k);
                }
            }
            updatePunchSize(this.spannableString, this.arrayfortv);
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tabformattxt:
                this.tab_format_punch.setBackgroundResource(R.drawable.gradient);
                this.tab_format_txt.setBackgroundColor(-1);
                this.modeformatselection = "textformat";
                this.format_txt.setTextColor(-16777216);
                this.format_punch.setTextColor(-1);
                checkboldItalic();
                return;
            case R.id.tabformatpunch:
                this.tab_format_punch.setBackgroundColor(-1);
                this.tab_format_txt.setBackgroundResource(R.drawable.gradient);
                this.modeformatselection = "punchformat";
                this.format_txt.setTextColor(-1);
                this.format_punch.setTextColor(-16777216);
                checkboldItalic();
                return;
            case R.id.tabfonttxt:
                this.tab_font_punch.setBackgroundResource(R.drawable.gradient);
                this.tab_font_txt.setBackgroundColor(-1);
                this.modefontselection = "textfont";
                this.font_txt.setTextColor(-16777216);
                this.font_punch.setTextColor(-1);
                return;
            case R.id.tabfontpunch:
                this.tab_font_punch.setBackgroundColor(-1);
                this.tab_font_txt.setBackgroundResource(R.drawable.gradient);
                this.modefontselection = "punchfont";
                this.font_txt.setTextColor(-1);
                this.font_punch.setTextColor(-16777216);
                return;
            case R.id.fnt:
                this.selectFocus = true;
                fstCallMethod();
                if (this.modefontselection.equals("textfont")) {
                    this.textView.setTypeface(this.ttD, Typeface.NORMAL);
                    fontTag("DroidSans.ttf");
                    return;
                }
                setFontonSelected("DroidSans.ttf");
                return;
            case R.id.fnt1:
                this.selectFocus = true;
                fstCallMethod();
                if (this.modefontselection.equals("textfont")) {
                    this.textView.setTypeface(this.ttf1, Typeface.NORMAL);
                    fontTag("majalla.ttf");
                    return;
                }
                setFontonSelected("majalla.ttf");
                return;
            case R.id.fnt2:
                this.selectFocus = true;
                fstCallMethod();
                if (this.modefontselection.equals("textfont")) {
                    this.textView.setTypeface(this.ttf7, Typeface.NORMAL);
                    fontTag("Walkway_Bold.ttf");
                    return;
                }
                setFontonSelected("Walkway_Bold.ttf");
                return;
            case R.id.fnt3:
                this.selectFocus = true;
                fstCallMethod();
                if (this.modefontselection.equals("textfont")) {
                    this.textView.setTypeface(this.ttf3, Typeface.NORMAL);
                    fontTag("PortLligatSans-Regular.ttf");
                    return;
                }
                setFontonSelected("PortLligatSans-Regular.ttf");
                return;
            case R.id.fnt4:
                this.selectFocus = true;
                fstCallMethod();
                if (this.modefontselection.equals("textfont")) {
                    this.textView.setTypeface(this.ttf4, Typeface.NORMAL);
                    fontTag("ROD.TTF");
                    return;
                }
                setFontonSelected("ROD.TTF");
                return;
            case R.id.fnt5:
                this.selectFocus = true;
                fstCallMethod();
                if (this.modefontselection.equals("textfont")) {
                    this.textView.setTypeface(this.ttf5, Typeface.NORMAL);
                    fontTag("Aspergit.otf");
                    return;
                }
                setFontonSelected("Aspergit.otf");
                return;
            case R.id.fnt7:
                this.selectFocus = true;
                fstCallMethod();
                if (this.modefontselection.equals("textfont")) {
                    this.textView.setTypeface(this.ttf2, Typeface.NORMAL);
                    fontTag("MVBOLI.TTF");
                    return;
                }
                setFontonSelected("MVBOLI.TTF");
                return;
            case R.id.fnt8:
                this.selectFocus = true;
                fstCallMethod();
                if (this.modefontselection.equals("textfont")) {
                    this.textView.setTypeface(this.ttf8, Typeface.NORMAL);
                    fontTag("Sofia-Regular.otf");
                    return;
                }
                setFontonSelected("Sofia-Regular.otf");
                return;
            case R.id.fnt9:
                this.selectFocus = true;
                fstCallMethod();
                if (this.modefontselection.equals("textfont")) {
                    this.textView.setTypeface(this.ttf9, Typeface.NORMAL);
                    fontTag("segoe.ttf");
                    return;
                }
                setFontonSelected("segoe.ttf");
                return;
            case R.id.fnt10:
                this.selectFocus = true;
                fstCallMethod();
                if (this.modefontselection.equals("textfont")) {
                    this.textView.setTypeface(this.ttf10, Typeface.NORMAL);
                    fontTag("Capture_it.ttf");
                    return;
                }
                setFontonSelected("Capture_it.ttf");
                return;
            case R.id.fnt6:
                this.selectFocus = true;
                fstCallMethod();
                if (this.modefontselection.equals("textfont")) {
                    this.textView.setTypeface(this.ttf6, Typeface.NORMAL);
                    fontTag("windsong.ttf");
                    return;
                }
                setFontonSelected("windsong.ttf");
                return;
            case R.id.fnt11:
                this.selectFocus = true;
                fstCallMethod();
                if (this.modefontselection.equals("textfont")) {
                    this.textView.setTypeface(this.ttf11, Typeface.NORMAL);
                    fontTag("Advertising Script Bold Trial.ttf");
                    return;
                }
                setFontonSelected("Advertising Script Bold Trial.ttf");
                return;
            case R.id.fnt12:
                this.selectFocus = true;
                fstCallMethod();
                if (this.modefontselection.equals("textfont")) {
                    this.textView.setTypeface(this.ttf12, Typeface.NORMAL);
                    fontTag("Advertising Script Monoline Trial.ttf");
                    return;
                }
                setFontonSelected("Advertising Script Monoline Trial.ttf");
                return;
            case R.id.fnt13:
                this.selectFocus = true;
                fstCallMethod();
                if (this.modefontselection.equals("textfont")) {
                    this.textView.setTypeface(this.ttf13, Typeface.NORMAL);
                    fontTag("Beyond Wonderland.ttf");
                    return;
                }
                setFontonSelected("Beyond Wonderland.ttf");
                return;
            case R.id.fnt14:
                this.selectFocus = true;
                fstCallMethod();
                if (this.modefontselection.equals("textfont")) {
                    this.textView.setTypeface(this.ttf14, Typeface.NORMAL);
                    fontTag("CalliGravity.ttf");
                    return;
                }
                setFontonSelected("CalliGravity.ttf");
                return;
            case R.id.fnt15:
                this.selectFocus = true;
                fstCallMethod();
                if (this.modefontselection.equals("textfont")) {
                    this.textView.setTypeface(this.ttf15, Typeface.NORMAL);
                    fontTag("Cosmic Love.ttf");
                    return;
                }
                setFontonSelected("Cosmic Love.ttf");
                return;
            case R.id.fnt16:
                this.selectFocus = true;
                fstCallMethod();
                if (this.modefontselection.equals("textfont")) {
                    this.textView.setTypeface(this.ttf16, Typeface.NORMAL);
                    fontTag("lesser concern shadow.ttf");
                    return;
                }
                setFontonSelected("lesser concern shadow.ttf");
                return;
            case R.id.fnt17:
                this.selectFocus = true;
                fstCallMethod();
                if (this.modefontselection.equals("textfont")) {
                    this.textView.setTypeface(this.ttf17, Typeface.NORMAL);
                    fontTag("lesser concern.ttf");
                    return;
                }
                setFontonSelected("lesser concern.ttf");
                return;
            case R.id.fnt18:
                this.selectFocus = true;
                fstCallMethod();
                if (this.modefontselection.equals("textfont")) {
                    this.textView.setTypeface(this.ttf18, Typeface.NORMAL);
                    fontTag("Queen of Heaven.ttf");
                    return;
                }
                setFontonSelected("Queen of Heaven.ttf");
                return;
            case R.id.fnt19:
                this.selectFocus = true;
                fstCallMethod();
                if (this.modefontselection.equals("textfont")) {
                    this.textView.setTypeface(this.ttf19, Typeface.NORMAL);
                    fontTag("QUIGLEYW.TTF");
                    return;
                }
                setFontonSelected("QUIGLEYW.TTF");
                return;
            case R.id.fnt20:
                this.selectFocus = true;
                fstCallMethod();
                if (this.modefontselection.equals("textfont")) {
                    this.textView.setTypeface(this.ttf20, Typeface.NORMAL);
                    fontTag("squealer embossed.ttf");
                    return;
                }
                setFontonSelected("squealer embossed.ttf");
                return;
            case R.id.fnt21:
                this.selectFocus = true;
                fstCallMethod();
                if (this.modefontselection.equals("textfont")) {
                    this.textView.setTypeface(this.ttf21, Typeface.NORMAL);
                    fontTag("squealer.ttf");
                    return;
                }
                setFontonSelected("squealer.ttf");
                return;
            case R.id.fnt22:
                this.selectFocus = true;
                fstCallMethod();
                if (this.modefontselection.equals("textfont")) {
                    this.textView.setTypeface(this.ttf22, Typeface.NORMAL);
                    fontTag("still time.ttf");
                    return;
                }
                setFontonSelected("still time.ttf");
                return;
            case R.id.fnt23:
                this.selectFocus = true;
                fstCallMethod();
                if (this.modefontselection.equals("textfont")) {
                    this.textView.setTypeface(this.ttf23, Typeface.NORMAL);
                    fontTag("Constantia Italic.ttf");
                    return;
                }
                setFontonSelected("Constantia Italic.ttf");
                return;
            case R.id.fnt24:
                this.selectFocus = true;
                fstCallMethod();
                if (this.modefontselection.equals("textfont")) {
                    this.textView.setTypeface(this.ttf24, Typeface.NORMAL);
                    fontTag("DejaVuSans_Bold.ttf");
                    return;
                }
                setFontonSelected("DejaVuSans_Bold.ttf");
                return;
            case R.id.fnt25:
                this.selectFocus = true;
                fstCallMethod();
                if (this.modefontselection.equals("textfont")) {
                    this.textView.setTypeface(this.ttf25, Typeface.NORMAL);
                    fontTag("Aladin_Regular.ttf");
                    return;
                }
                setFontonSelected("Aladin_Regular.ttf");
                return;
            case R.id.fnt26:
                this.selectFocus = true;
                fstCallMethod();
                if (this.modefontselection.equals("textfont")) {
                    this.textView.setTypeface(this.ttf26, Typeface.NORMAL);
                    fontTag("Adobe Caslon Pro Italic.ttf");
                    return;
                }
                setFontonSelected("Adobe Caslon Pro Italic.ttf");
                return;
            case R.id.fnt27:
                this.selectFocus = true;
                fstCallMethod();
                if (this.modefontselection.equals("textfont")) {
                    this.textView.setTypeface(this.ttf27, Typeface.NORMAL);
                    fontTag("aparaji.ttf");
                    return;
                }
                setFontonSelected("aparaji.ttf");
                return;
            case R.id.fnt28:
                this.selectFocus = true;
                fstCallMethod();
                if (this.modefontselection.equals("textfont")) {
                    this.textView.setTypeface(this.ttf28, Typeface.NORMAL);
                    fontTag("ARDECODE.ttf");
                    return;
                }
                setFontonSelected("ARDECODE.ttf");
                return;
            case R.id.fnt29:
                this.selectFocus = true;
                fstCallMethod();
                if (this.modefontselection.equals("textfont")) {
                    this.textView.setTypeface(this.ttf29, Typeface.NORMAL);
                    fontTag("ufonts_com_ck_scratchy_box.ttf");
                    return;
                }
                setFontonSelected("ufonts_com_ck_scratchy_box.ttf");
                return;
            case R.id.tabtxtrel:
                this.tab_punch.setBackgroundResource(R.drawable.gradient);
                this.tab_text.setBackgroundColor(-1);
                this.modeClrSelection = "textclr";
                this.color_txt.setTextColor(-16777216);
                this.color_punch.setTextColor(-1);
                return;
            case R.id.tabpunchrel:
                this.tab_punch.setBackgroundColor(-1);
                this.tab_text.setBackgroundResource(R.drawable.gradient);
                this.modeClrSelection = "punchclr";
                this.color_punch.setTextColor(-16777216);
                this.color_txt.setTextColor(-1);
                return;
            case R.id.dropcolor_picker:
                this.selectFocus = true;
                fstCallMethod();
                hideChilds(this.rl);
                createDropColorImg("text");
                return;
            case R.id.colorpicker:
                this.selectFocus = true;
                fstCallMethod();
                if (this.modeClrSelection.equals("textclr")) {
                    this.existingcolor = this.textView.getCurrentTextColor();
                } else if (this.arrayfortv.size() > 0) {
                    this.existingcolor = ((SelectedTextData) this.arrayfortv.get(0)).getText_color();
                }
                AmbilWarnaDialog aaa = new AmbilWarnaDialog(this, this.existingcolor, new OnAmbilWarnaListener() {
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        if (AddTextQuotesActivity.this.modeClrSelection.equals("textclr")) {
                            AddTextQuotesActivity.this.shr1 = "null";
                            AddTextQuotesActivity.this.textView.getPaint().setShader(null);
                            AddTextQuotesActivity.this.textView.postInvalidate();
                            AddTextQuotesActivity.this.textView.requestLayout();
                            AddTextQuotesActivity.this.textView.setTextColor(color);
                            AddTextQuotesActivity.this.existingcolor = color;
                        } else {
                            AddTextQuotesActivity.this.setShaderonSelected("null");
                            AddTextQuotesActivity.this.setColoronSelected(color);
                            AddTextQuotesActivity.this.existingcolor = color;
                        }
                        AddTextQuotesActivity.this.updateColors();
                    }

                    public void onCancel(AmbilWarnaDialog dialog) {
                        if (AddTextQuotesActivity.this.b == 0 || AddTextQuotesActivity.this.b == -1) {
                            AddTextQuotesActivity.this.textView.setSelection(AddTextQuotesActivity.this.start, AddTextQuotesActivity.this.end);
                        } else {
                            AddTextQuotesActivity.this.textView.setSelection(AddTextQuotesActivity.this.a, AddTextQuotesActivity.this.b);
                        }
                    }
                });
                aaa.show();

                return;
            case R.id.c5:
                this.selectFocus = true;
                fstCallMethod();
                if (this.modeClrSelection.equals("textclr")) {
                    this.shr1 = "null";
                    this.textView.getPaint().setShader(null);
                    this.textView.postInvalidate();
                    this.textView.requestLayout();
                    this.textView.setTextColor(-2697256);
                    this.existingcolor = -2697256;
                } else {
                    setShaderonSelected("null");
                    setColoronSelected(-2697256);
                    this.existingcolor = -2697256;
                }
                updateColors();
                return;
            case R.id.c6:
                this.selectFocus = true;
                fstCallMethod();
                if (this.modeClrSelection.equals("textclr")) {
                    this.shr1 = "null";
                    this.textView.getPaint().setShader(null);
                    this.textView.postInvalidate();
                    this.textView.requestLayout();
                    this.textView.setTextColor(-16777216);
                    this.existingcolor = -16777216;
                } else {
                    setShaderonSelected("null");
                    setColoronSelected(-16777216);
                    this.existingcolor = -16777216;
                }
                updateColors();
                return;
            case R.id.c1:
                this.selectFocus = true;
                fstCallMethod();
                if (this.modeClrSelection.equals("textclr")) {
                    this.shr1 = "null";
                    this.textView.getPaint().setShader(null);
                    this.textView.postInvalidate();
                    this.textView.requestLayout();
                    this.textView.setTextColor(-65536);
                    this.existingcolor = -65536;
                    Log.e("coloredText1", BuildConfig.FLAVOR + this.textView.getText().toString());
                } else {
                    setShaderonSelected("null");
                    setColoronSelected(-65536);
                    this.existingcolor = -65536;
                }
                updateColors();
                return;
            case R.id.c2:
                this.selectFocus = true;
                fstCallMethod();
                if (this.modeClrSelection.equals("textclr")) {
                    this.shr1 = "null";
                    this.textView.getPaint().setShader(null);
                    this.textView.postInvalidate();
                    this.textView.requestLayout();
                    this.textView.setTextColor(-16711936);
                    this.existingcolor = -16711936;
                } else {
                    setShaderonSelected("null");
                    setColoronSelected(-16711936);
                    this.existingcolor = -16711936;
                }
                updateColors();
                return;
            case R.id.c3:
                this.selectFocus = true;
                fstCallMethod();
                if (this.modeClrSelection.equals("textclr")) {
                    this.shr1 = "null";
                    this.textView.getPaint().setShader(null);
                    this.textView.postInvalidate();
                    this.textView.requestLayout();
                    this.textView.setTextColor(-16776961);
                    this.existingcolor = -16776961;
                } else {
                    setShaderonSelected("null");
                    setColoronSelected(-16776961);
                    this.existingcolor = -16776961;
                }
                updateColors();
                return;
            case R.id.c4:
                this.selectFocus = true;
                fstCallMethod();
                if (this.modeClrSelection.equals("textclr")) {
                    this.shr1 = "null";
                    this.textView.getPaint().setShader(null);
                    this.textView.postInvalidate();
                    this.textView.requestLayout();
                    this.textView.setTextColor(-256);
                    this.existingcolor = -256;
                } else {
                    setShaderonSelected("null");
                    setColoronSelected(-256);
                    this.existingcolor = -256;
                }
                updateColors();
                return;
            case R.id.c11:
                this.selectFocus = true;
                fstCallMethod();
                if (this.modeClrSelection.equals("textclr")) {
                    this.shr1 = "null";
                    this.textView.getPaint().setShader(null);
                    this.textView.postInvalidate();
                    this.textView.requestLayout();
                    this.textView.setTextColor(-1702802);
                    this.existingcolor = -1702802;
                } else {
                    setShaderonSelected("null");
                    setColoronSelected(-1702802);
                    this.existingcolor = -1702802;
                }
                updateColors();
                return;
            case R.id.c12:
                this.selectFocus = true;
                fstCallMethod();
                if (this.modeClrSelection.equals("textclr")) {
                    this.shr1 = "null";
                    this.textView.getPaint().setShader(null);
                    this.textView.postInvalidate();
                    this.textView.requestLayout();
                    this.textView.setTextColor(-223198);
                    this.existingcolor = -223198;
                } else {
                    setShaderonSelected("null");
                    setColoronSelected(-223198);
                    this.existingcolor = -223198;
                }
                updateColors();
                return;
            case R.id.c13:
                this.selectFocus = true;
                fstCallMethod();
                if (this.modeClrSelection.equals("textclr")) {
                    this.shr1 = "null";
                    this.textView.getPaint().setShader(null);
                    this.textView.postInvalidate();
                    this.textView.requestLayout();
                    this.textView.setTextColor(-5366888);
                    this.existingcolor = -5366888;
                } else {
                    setShaderonSelected("null");
                    setColoronSelected(-5366888);
                    this.existingcolor = -5366888;
                }
                updateColors();
                return;
            case R.id.c14:
                this.selectFocus = true;
                fstCallMethod();
                if (this.modeClrSelection.equals("textclr")) {
                    this.shr1 = "null";
                    this.textView.getPaint().setShader(null);
                    this.textView.postInvalidate();
                    this.textView.requestLayout();
                    this.textView.setTextColor(-16238219);
                    this.existingcolor = -16238219;
                } else {
                    setShaderonSelected("null");
                    setColoronSelected(-16238219);
                    this.existingcolor = -16238219;
                }
                updateColors();
                return;
            case R.id.tabShadowtxt:
                this.tab_shadow_punch.setBackgroundResource(R.drawable.gradient);
                this.tab_shadow_txt.setBackgroundColor(-1);
                this.modeshadowselection = "textshadow";
                this.shadow_txt.setTextColor(-16777216);
                this.shadow_punch.setTextColor(-1);
                this.shadow_seekbar.setProgress((int) (2.0f * this.textView.getShadowRadius()));
                return;
            case R.id.tabshadowpunch:
                this.tab_shadow_punch.setBackgroundColor(-1);
                this.tab_shadow_txt.setBackgroundResource(R.drawable.gradient);
                this.modeshadowselection = "punchshadow";
                this.shadow_punch.setTextColor(-16777216);
                this.shadow_txt.setTextColor(-1);
                if (this.arrayfortv.size() > 0) {
                    this.shadow_seekbar.setProgress((int) (2.0f * ((SelectedTextData) this.arrayfortv.get(0)).getText_shadowradius()));
                    return;
                }
                return;
            case R.id.tabshadertxt:
                this.tab_shader_punch.setBackgroundResource(R.drawable.gradient);
                this.tab_shader_txt.setBackgroundColor(-1);
                this.modeshaderselection = "textshader";
                this.shader_txt.setTextColor(-16777216);
                this.shader_punch.setTextColor(-1);
                return;
            case R.id.tabshaderpunch:
                this.tab_shader_punch.setBackgroundColor(-1);
                this.tab_shader_txt.setBackgroundResource(R.drawable.gradient);
                this.modeshaderselection = "punchshader";
                this.shader_txt.setTextColor(-1);
                this.shader_punch.setTextColor(-16777216);
                return;
            case R.id.re_template:
                this.top_option.setVisibility(View.GONE);
                this.font_rel.setVisibility(View.INVISIBLE);
                this.color_rel.setVisibility(View.INVISIBLE);
                this.shadow_rel.setVisibility(View.INVISIBLE);
                this.shader_rel.setVisibility(View.INVISIBLE);
                this.lay_viewStyle.setVisibility(View.VISIBLE);
                this.font_rel.setVisibility(View.GONE);
                this.list_of_style.setBackgroundResource(R.drawable.stye_icon);
                this.fonts.setBackgroundResource(R.drawable.fonts1);
                this.size.setBackgroundResource(R.drawable.size1);
                this.color_.setBackgroundResource(R.drawable.color_1);
                this.shadow.setBackgroundResource(R.drawable.shadow1);
                this.shader.setBackgroundResource(R.drawable.shader_1);
                ((TextView) findViewById(R.id.tep_txt)).setTextColor(-16777216);
                ((TextView) findViewById(R.id.for_txt)).setTextColor(-1);
                ((TextView) findViewById(R.id.font_txt)).setTextColor(-1);
                ((TextView) findViewById(R.id.co_txt)).setTextColor(-1);
                ((TextView) findViewById(R.id.shw_txt)).setTextColor(-1);
                ((TextView) findViewById(R.id.shdr_txt)).setTextColor(-1);
                this.la_shader.setBackgroundResource(R.drawable.gradient);
                this.la_fonts.setBackgroundResource(R.drawable.gradient);
                this.la_color.setBackgroundResource(R.drawable.gradient);
                this.la_shadow.setBackgroundResource(R.drawable.gradient);
                this.la_size.setBackgroundResource(R.drawable.gradient);
                this.re_template.setBackgroundColor(-1);
                return;
            case R.id.la_size:
                this.modefont = "formatting";
                this.selectFocus = true;
                this.top_option.setVisibility(View.VISIBLE);
                this.la_size.setBackgroundColor(-1);
                this.la_fonts.setBackgroundResource(R.drawable.gradient);
                this.la_color.setBackgroundResource(R.drawable.gradient);
                this.la_shadow.setBackgroundResource(R.drawable.gradient);
                this.la_shader.setBackgroundResource(R.drawable.gradient);
                this.re_template.setBackgroundResource(R.drawable.gradient);
                this.list_of_style.setBackgroundResource(R.drawable.stye_icon1);
                this.fonts.setBackgroundResource(R.drawable.fonts1);
                this.size.setBackgroundResource(R.drawable.size);
                this.color_.setBackgroundResource(R.drawable.color_1);
                this.shadow.setBackgroundResource(R.drawable.shadow1);
                this.shader.setBackgroundResource(R.drawable.shader_1);
                ((TextView) findViewById(R.id.tep_txt)).setTextColor(-1);
                ((TextView) findViewById(R.id.for_txt)).setTextColor(-16777216);
                ((TextView) findViewById(R.id.font_txt)).setTextColor(-1);
                ((TextView) findViewById(R.id.co_txt)).setTextColor(-1);
                ((TextView) findViewById(R.id.shw_txt)).setTextColor(-1);
                ((TextView) findViewById(R.id.shdr_txt)).setTextColor(-1);
                this.font_rel.setVisibility(View.INVISIBLE);
                this.color_rel.setVisibility(View.INVISIBLE);
                this.shadow_rel.setVisibility(View.INVISIBLE);
                this.shader_rel.setVisibility(View.INVISIBLE);
                this.lay_viewStyle.setVisibility(View.INVISIBLE);
                checkboldItalic();
                return;
            case R.id.la_fonts:
                this.modefont = "fonts";
                this.selectFocus = true;
                this.top_option.setVisibility(View.GONE);
                this.la_fonts.setBackgroundColor(-1);
                this.la_size.setBackgroundResource(R.drawable.gradient);
                this.la_color.setBackgroundResource(R.drawable.gradient);
                this.la_shadow.setBackgroundResource(R.drawable.gradient);
                this.la_shader.setBackgroundResource(R.drawable.gradient);
                this.re_template.setBackgroundResource(R.drawable.gradient);
                this.list_of_style.setBackgroundResource(R.drawable.stye_icon1);
                this.fonts.setBackgroundResource(R.drawable.fonts);
                this.size.setBackgroundResource(R.drawable.size1);
                this.color_.setBackgroundResource(R.drawable.color_1);
                this.shadow.setBackgroundResource(R.drawable.shadow1);
                this.shader.setBackgroundResource(R.drawable.shader_1);
                ((TextView) findViewById(R.id.tep_txt)).setTextColor(-1);
                ((TextView) findViewById(R.id.for_txt)).setTextColor(-1);
                ((TextView) findViewById(R.id.font_txt)).setTextColor(-16777216);
                ((TextView) findViewById(R.id.co_txt)).setTextColor(-1);
                ((TextView) findViewById(R.id.shw_txt)).setTextColor(-1);
                ((TextView) findViewById(R.id.shdr_txt)).setTextColor(-1);
                this.font_rel.setVisibility(View.VISIBLE);
                this.color_rel.setVisibility(View.INVISIBLE);
                this.shadow_rel.setVisibility(View.INVISIBLE);
                this.shader_rel.setVisibility(View.INVISIBLE);
                this.lay_viewStyle.setVisibility(View.INVISIBLE);
                return;
            case R.id.la_color:
                this.selectFocus = true;
                this.top_option.setVisibility(View.GONE);
                this.la_color.setBackgroundColor(-1);
                this.la_fonts.setBackgroundResource(R.drawable.gradient);
                this.la_size.setBackgroundResource(R.drawable.gradient);
                this.la_shadow.setBackgroundResource(R.drawable.gradient);
                this.la_shader.setBackgroundResource(R.drawable.gradient);
                this.re_template.setBackgroundResource(R.drawable.gradient);
                this.list_of_style.setBackgroundResource(R.drawable.stye_icon1);
                this.fonts.setBackgroundResource(R.drawable.fonts1);
                this.size.setBackgroundResource(R.drawable.size1);
                this.color_.setBackgroundResource(R.drawable.color);
                this.shadow.setBackgroundResource(R.drawable.shadow1);
                this.shader.setBackgroundResource(R.drawable.shader_1);
                ((TextView) findViewById(R.id.tep_txt)).setTextColor(-1);
                ((TextView) findViewById(R.id.for_txt)).setTextColor(-1);
                ((TextView) findViewById(R.id.font_txt)).setTextColor(-1);
                ((TextView) findViewById(R.id.co_txt)).setTextColor(-16777216);
                ((TextView) findViewById(R.id.shw_txt)).setTextColor(-1);
                ((TextView) findViewById(R.id.shdr_txt)).setTextColor(-1);
                this.font_rel.setVisibility(View.INVISIBLE);
                this.color_rel.setVisibility(View.VISIBLE);
                this.shadow_rel.setVisibility(View.INVISIBLE);
                this.shader_rel.setVisibility(View.INVISIBLE);
                this.lay_viewStyle.setVisibility(View.INVISIBLE);
                return;
            case R.id.la_shadow:
                this.selectFocus = true;
                this.top_option.setVisibility(View.GONE);
                this.la_shadow.setBackgroundColor(-1);
                this.la_fonts.setBackgroundResource(R.drawable.gradient);
                this.la_color.setBackgroundResource(R.drawable.gradient);
                this.la_size.setBackgroundResource(R.drawable.gradient);
                this.la_shader.setBackgroundResource(R.drawable.gradient);
                this.re_template.setBackgroundResource(R.drawable.gradient);
                this.list_of_style.setBackgroundResource(R.drawable.stye_icon1);
                this.fonts.setBackgroundResource(R.drawable.fonts1);
                this.size.setBackgroundResource(R.drawable.size1);
                this.color_.setBackgroundResource(R.drawable.color_1);
                this.shadow.setBackgroundResource(R.drawable.shadow);
                this.shader.setBackgroundResource(R.drawable.shader_1);
                ((TextView) findViewById(R.id.tep_txt)).setTextColor(-1);
                ((TextView) findViewById(R.id.for_txt)).setTextColor(-1);
                ((TextView) findViewById(R.id.font_txt)).setTextColor(-1);
                ((TextView) findViewById(R.id.co_txt)).setTextColor(-1);
                ((TextView) findViewById(R.id.shw_txt)).setTextColor(-16777216);
                ((TextView) findViewById(R.id.shdr_txt)).setTextColor(-1);
                this.font_rel.setVisibility(View.INVISIBLE);
                this.color_rel.setVisibility(View.INVISIBLE);
                this.shadow_rel.setVisibility(View.VISIBLE);
                this.shader_rel.setVisibility(View.INVISIBLE);
                this.lay_viewStyle.setVisibility(View.INVISIBLE);
                if (this.modeshadowselection.equals("textshadow")) {
                    this.shadow_seekbar.setProgress((int) (2.0f * this.textView.getShadowRadius()));
                    return;
                } else if (this.arrayfortv.size() > 0) {
                    this.shadow_seekbar.setProgress((int) (2.0f * ((SelectedTextData) this.arrayfortv.get(0)).getText_shadowradius()));
                    return;
                } else {
                    return;
                }
            case R.id.la_shader:
                this.selectFocus = true;
                this.top_option.setVisibility(View.INVISIBLE);
                this.la_shader.setBackgroundColor(-1);
                this.la_fonts.setBackgroundResource(R.drawable.gradient);
                this.la_color.setBackgroundResource(R.drawable.gradient);
                this.la_shadow.setBackgroundResource(R.drawable.gradient);
                this.la_size.setBackgroundResource(R.drawable.gradient);
                this.re_template.setBackgroundResource(R.drawable.gradient);
                this.list_of_style.setBackgroundResource(R.drawable.stye_icon1);
                this.fonts.setBackgroundResource(R.drawable.fonts1);
                this.size.setBackgroundResource(R.drawable.size1);
                this.color_.setBackgroundResource(R.drawable.color_1);
                this.shadow.setBackgroundResource(R.drawable.shadow1);
                this.shader.setBackgroundResource(R.drawable.shader);
                ((TextView) findViewById(R.id.tep_txt)).setTextColor(-1);
                ((TextView) findViewById(R.id.for_txt)).setTextColor(-1);
                ((TextView) findViewById(R.id.font_txt)).setTextColor(-1);
                ((TextView) findViewById(R.id.co_txt)).setTextColor(-1);
                ((TextView) findViewById(R.id.shw_txt)).setTextColor(-1);
                ((TextView) findViewById(R.id.shdr_txt)).setTextColor(-16777216);
                this.font_rel.setVisibility(View.INVISIBLE);
                this.color_rel.setVisibility(View.INVISIBLE);
                this.shadow_rel.setVisibility(View.INVISIBLE);
                this.shader_rel.setVisibility(View.VISIBLE);
                this.lay_viewStyle.setVisibility(View.INVISIBLE);
                return;
            case R.id.controlls_stkr:
                this.tab_cntrl_stkr.setBackgroundColor(-1);
                this.tab_clrs_stkr.setBackgroundResource(R.drawable.gradient);
                this.contrl_txt.setTextColor(-16777216);
                this.clr_opacity_txt.setTextColor(-1);
                this.cntrls_stkr_lay.setVisibility(View.VISIBLE);
                this.cntrls_stkrclr_lay.setVisibility(View.GONE);
                return;
            case R.id.clr_opacity:
                this.contrl_txt.setTextColor(-1);
                this.clr_opacity_txt.setTextColor(-16777216);
                this.tab_cntrl_stkr.setBackgroundResource(R.drawable.gradient);
                this.tab_clrs_stkr.setBackgroundColor(-1);
                this.cntrls_stkr_lay.setVisibility(View.GONE);
                this.cntrls_stkrclr_lay.setVisibility(View.VISIBLE);
                return;
            case R.id.dropcolor_picker1:
                createDropColorImg("sticker");
                return;
            case R.id.colorpicker1:
                new AmbilWarnaDialog(this, this.color, new OnAmbilWarnaListener() {
                    public void onOk(AmbilWarnaDialog dialog, int colorSelect) {
                        AddTextQuotesActivity.this.color = colorSelect;
                        int childCount1 = AddTextQuotesActivity.this.fortext.getChildCount();
                        for (int i = 0; i < childCount1; i++) {
                            View view1 = AddTextQuotesActivity.this.fortext.getChildAt(i);
                            if ((view1 instanceof ResizableImageview) && ((ResizableImageview) view1).getBorderVisbilty()) {
                                ((ResizableImageview) view1).setColorFilter(AddTextQuotesActivity.this.color);
                            }
                        }
                    }

                    public void onCancel(AmbilWarnaDialog dialog) {
                    }
                }).show();
                return;
            case R.id.re_background:
                removeImageViewControll();
                this.sb_effectsfilter.setVisibility(View.GONE);
                this.sb_opctyfilter.setVisibility(View.GONE);
                this.blur_lay_cntrl.setVisibility(View.GONE);
                this.blur_seekbar_lay.setVisibility(View.GONE);
                this.scroll_of_all_effects.setVisibility(View.GONE);
                this.re_sticker.setBackgroundColor(-16777216);
                this.rel_effects.setBackgroundColor(-16777216);
                if (this.scroll_of_all_backgrounds.getVisibility() != View.VISIBLE) {
                    this.picture_txt.setTextColor(-16777216);
                    this.sticker_txt.setTextColor(-1);
                    this.effect_txt.setTextColor(-1);
                    this.re_background.setBackgroundColor(-1);
                    this.footer_effects_image.setBackgroundResource(R.drawable.effects_image1);
                    this.list_of_brnd.setBackgroundResource(R.drawable.backrunds);
                    this.list_of_sticker.setBackgroundResource(R.drawable.stickerr1);
                    this.scroll_all.setVisibility(View.GONE);
                    this.scroll_of_all_backgrounds.setVisibility(View.VISIBLE);
                    return;
                }
                setDefault();
                return;
            case R.id.re_sticker:
                this.sb_effectsfilter.setVisibility(View.GONE);
                this.sb_opctyfilter.setVisibility(View.GONE);
                this.blur_lay_cntrl.setVisibility(View.INVISIBLE);
                this.blur_seekbar_lay.setVisibility(View.GONE);
                this.controll_btn_stckr.setVisibility(View.GONE);
                this.controller_sticker.setVisibility(View.GONE);
                this.scroll_all.setVisibility(View.GONE);
                this.scroll_of_all_effects.setVisibility(View.GONE);
                this.scroll_of_all_backgrounds.setVisibility(View.GONE);
                setDefault();
                removeImageViewControll();
                int position = getStickerPositionFromCategory(this.categorySticker);
                Intent intentSticker = new Intent(this, StickerList.class);
                intentSticker.putExtra("position", position);
                startActivityForResult(intentSticker, GET_STICKER);
                return;
            case R.id.rel_effects:
                removeImageViewControll();
                this.sb_effectsfilter.setVisibility(View.GONE);
                this.sb_opctyfilter.setVisibility(View.GONE);
                this.sb_effects.setVisibility(View.GONE);
                this.scroll_of_all_backgrounds.setVisibility(View.GONE);
                this.blur_lay_cntrl.setVisibility(View.GONE);
                this.blur_seekbar_lay.setVisibility(View.GONE);
                this.controll_btn_stckr.setVisibility(View.GONE);
                this.controller_sticker.setVisibility(View.GONE);
                this.re_background.setBackgroundColor(-16777216);
                this.re_sticker.setBackgroundColor(-16777216);
                if (this.scroll_of_all_effects.getVisibility() != View.VISIBLE) {
                    this.picture_txt.setTextColor(-1);
                    this.sticker_txt.setTextColor(-1);
                    this.effect_txt.setTextColor(-16777216);
                    this.rel_effects.setBackgroundColor(-1);
                    this.footer_effects_image.setBackgroundResource(R.drawable.effects_image);
                    this.list_of_brnd.setBackgroundResource(R.drawable.backrund1);
                    this.list_of_sticker.setBackgroundResource(R.drawable.stickerr1);
                    this.scroll_all.setVisibility(View.GONE);
                    this.scroll_of_all_effects.setVisibility(View.VISIBLE);
                    return;
                }
                setDefault();
                return;
            case R.id.back_arrow_add_quotes:
                this.selectFocus = true;
                onBackPressed();
                return;
            case R.id.done_add_quotes:
                this.selectFocus = true;
                if (this.textView != null) {
                    Selection.removeSelection(this.textView.getText());
                }
                removeImageViewControll();
                this.controll_btn_stckr.setVisibility(View.GONE);
                this.controller_sticker.setVisibility(View.GONE);
                if (this.textView != null) {
                    this.textView.setCursorVisible(false);
                    int noch = ((ViewGroup) this.textView.getParent()).getChildCount();
                    for (int i = 1; i < noch; i++) {
                        ((ViewGroup) this.textView.getParent()).getChildAt(i).setVisibility(View.INVISIBLE);
                    }
                }
                try {
                    this.complete_img.setDrawingCacheEnabled(true);
                    this.bitRel = Bitmap.createBitmap(this.complete_img.getDrawingCache());
                    this.complete_img.setDrawingCacheEnabled(false);
                    try {
                        this.bb = Bitmap.createBitmap(this.gpuImageview.capture());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Bitmap bitRel_logo = mergeBitmap(this.bb, this.bitRel);
                    bitmapOriginal = bitRel_logo;

                    this.bitRel = bitRel_logo;
                    saveBitmap(true);
                    return;
                } catch (OutOfMemoryError e2) {
                    e2.printStackTrace();
                    return;
                } catch (Exception e3) {
                    e3.printStackTrace();
                    return;
                }
            case R.id.effect1:
                this.selectFocus = true;
                this.mFilter = null;
                this.filterType = FilterType.GAUSSIAN_BLUR;
                this.gpuImageview.setFilter(new GPUImageFilter(GPUImageFilter.NO_FILTER_VERTEX_SHADER, GPUImageFilter.NO_FILTER_FRAGMENT_SHADER));
                this.sb_effects.setVisibility(View.GONE);
                this.sb_effects.setProgress(0);
                GPUImageFilter filter1 = GPUImageFilterTools.createFilterForType(this, this.filterType);
                GPUImageFilter filter2 = GPUImageFilterTools.createFilterForType(this, FilterType.GAUSSIAN_BLUR);
                new FilterAdjuster(filter1).adjust(this.sb_effects.getProgress());
                new FilterAdjuster(filter2).adjust(this.sb_effectsfilter.getProgress());
                GPUImageFilterGroup group = new GPUImageFilterGroup();
                group.addFilter(filter1);
                group.addFilter(filter2);
                this.gpuImageview.setFilter(group);
                this.gpuImageview.requestRender();
                this.gpuImageview.setVisibility(View.VISIBLE);
                return;
            case R.id.effect3:
                this.selectFocus = true;
                this.filterType = FilterType.SEPIA;
                switchFilterTo(GPUImageFilterTools.createFilterForType(getApplicationContext(), FilterType.SEPIA));
                this.gpuImageview.requestRender();
                return;
            case R.id.effect4:
                this.selectFocus = true;
                this.filterType = FilterType.RGB;
                switchFilterTo(GPUImageFilterTools.createFilterForType(getApplicationContext(), FilterType.RGB));
                this.gpuImageview.requestRender();
                return;
            case R.id.effect5:
                this.selectFocus = true;
                this.filterType = FilterType.GRAYSCALE;
                switchFilterTo(GPUImageFilterTools.createFilterForType(getApplicationContext(), FilterType.GRAYSCALE));
                this.gpuImageview.requestRender();
                return;
            case R.id.effect6:
                this.selectFocus = true;
                this.filterType = FilterType.FALSE_COLOR;
                switchFilterTo(GPUImageFilterTools.createFilterForType(this, FilterType.FALSE_COLOR));
                this.gpuImageview.requestRender();
                return;
            case R.id.effect7:
                this.selectFocus = true;
                this.filterType = FilterType.BLEND_SOFT_LIGHT;
                switchFilterTo(GPUImageFilterTools.createFilterForType(getApplicationContext(), FilterType.BLEND_SOFT_LIGHT));
                this.gpuImageview.requestRender();
                return;
            case R.id.effect8:
                this.selectFocus = true;
                this.filterType = FilterType.PIXELATION;
                switchFilterTo(GPUImageFilterTools.createFilterForType(getApplicationContext(), FilterType.PIXELATION));
                this.gpuImageview.requestRender();
                return;
            case R.id.effect9:
                this.selectFocus = true;
                this.filterType = FilterType.HUE;
                switchFilterTo(GPUImageFilterTools.createFilterForType(getApplicationContext(), FilterType.HUE));
                this.gpuImageview.requestRender();
                return;
            case R.id.effect10:
                this.selectFocus = true;
                this.filterType = FilterType.BLEND_DARKEN;
                switchFilterTo(GPUImageFilterTools.createFilterForType(getApplicationContext(), FilterType.BLEND_DARKEN));
                this.gpuImageview.requestRender();
                return;
            case R.id.effect11:
                this.selectFocus = true;
                this.filterType = FilterType.BLEND_ALPHA;
                switchFilterTo(GPUImageFilterTools.createFilterForType(getApplicationContext(), FilterType.BLEND_ALPHA));
                this.gpuImageview.requestRender();
                return;
            default:
                return;
        }
    }

    private int getStickerPositionFromCategory(String category) {
        String[] TITLES = getResources().getStringArray(R.array.sticker_category);
        for (int i = 0; i < TITLES.length; i++) {
            if (category.equals(TITLES[i])) {
                return i;
            }
        }
        return 0;
    }

    private void checkboldItalic() {
        this.bold.setBackgroundResource(R.drawable.bold);
        this.italic.setBackgroundResource(R.drawable.italic);
        this.underline.setBackgroundResource(R.drawable.underline);
        this.strike.setBackgroundResource(R.drawable.text);
        if (this.textView.getGravity() == 17) {
            this.g1.setBackgroundResource(R.drawable.left_align_text);
            this.g2.setBackgroundResource(R.drawable.center_align_text1);
            this.g3.setBackgroundResource(R.drawable.right_align_text);
        } else if (this.textView.getGravity() == 49) {
            this.g1.setBackgroundResource(R.drawable.left_align_text);
            this.g2.setBackgroundResource(R.drawable.center_align_text1);
            this.g3.setBackgroundResource(R.drawable.right_align_text);
        } else if (this.textView.getGravity() == 51) {
            this.g1.setBackgroundResource(R.drawable.left_align_text1);
            this.g2.setBackgroundResource(R.drawable.center_align_text);
            this.g3.setBackgroundResource(R.drawable.right_align_text);
        } else if (this.textView.getGravity() == 53) {
            this.g1.setBackgroundResource(R.drawable.left_align_text);
            this.g2.setBackgroundResource(R.drawable.center_align_text);
            this.g3.setBackgroundResource(R.drawable.right_align_text1);
        }
        if (this.modeformatselection.equals("textformat")) {
            if (this.textView.getTypeface().getStyle() == Typeface.BOLD) {
                this.bold.setBackgroundResource(R.drawable.bold1);
            }
            if (this.textView.getTypeface().getStyle() == Typeface.BOLD_ITALIC) {
                this.bold.setBackgroundResource(R.drawable.bold1);
                this.italic.setBackgroundResource(R.drawable.italic1);
            }
            if (this.textView.getPaintFlags() == 1289 || this.textView.getPaintFlags() == 1305) {
                this.underline.setBackgroundResource(R.drawable.underline1);
            }
            if (this.textView.getPaintFlags() == 1297 || this.textView.getPaintFlags() == 1305) {
                this.strike.setBackgroundResource(R.drawable.text1);
            }
        } else if (this.arrayfortv.size() > 0) {
            if (((SelectedTextData) this.arrayfortv.get(0)).isText_bold()) {
                this.bold.setBackgroundResource(R.drawable.bold1);
            }
            if (((SelectedTextData) this.arrayfortv.get(0)).isText_italic()) {
                this.italic.setBackgroundResource(R.drawable.italic1);
            }
            if (((SelectedTextData) this.arrayfortv.get(0)).isText_underline()) {
                this.underline.setBackgroundResource(R.drawable.underline1);
            }
            if (((SelectedTextData) this.arrayfortv.get(0)).isText_strike()) {
                this.strike.setBackgroundResource(R.drawable.text1);
            }
        }
    }

    private void setDefault() {
        this.picture_txt.setTextColor(-1);
        this.sticker_txt.setTextColor(-1);
        this.effect_txt.setTextColor(-1);
        this.list_of_brnd.setBackgroundResource(R.drawable.backrund1);
        this.list_of_sticker.setBackgroundResource(R.drawable.stickerr1);
        this.footer_effects_image.setBackgroundResource(R.drawable.effects_image1);
        this.scroll_of_all_effects.setVisibility(View.GONE);
        this.scroll_of_all_backgrounds.setVisibility(View.GONE);
        this.re_background.setBackgroundColor(-16777216);
        this.re_sticker.setBackgroundColor(-16777216);
        this.rel_effects.setBackgroundColor(-16777216);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.selectFocus = true;
        if (resultCode != -1) {
            return;
        }
        if (data != null || requestCode == SELECT_BACKGROUND_FROM_CAMERA || requestCode == SEL_BACKGROUND_FROM_GALLERY || requestCode == GET_STICKER || requestCode == GET_BG_RESULT) {
            Uri selectedImageUri;
            Intent _main;
            String quoted_txt;
            String mDrawableName;
            if (requestCode == SEL_BACKGROUND_FROM_GALLERY) {
                selectedImageUri = data.getData();
                if (selectedImageUri != null) {
                    _main = new Intent(this, CropActivityTwo.class);
                    _main.putExtra("value", "image");
                    _main.setData(selectedImageUri);
                    startActivity(_main);
                }
            }
            if (requestCode == SELECT_BACKGROUND_FROM_CAMERA) {
                try {
                    selectedImageUri = Uri.fromFile(this.f);
                    if (selectedImageUri != null) {
                        _main = new Intent(this, CropActivityTwo.class);
                        _main.putExtra("value", "image");
                        _main.setData(selectedImageUri);
                        startActivity(_main);
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
            if (requestCode == EDIT_QUOTE_RESULT) {
                checkandShowEditRel();
                this.selectFocus = true;
                this.storedArray = new ArrayList();
                quoted_txt = data.getStringExtra("quote_edit");
                hideChilds(this.rl);
                if (this.textView.getTag().equals("text_tv")) {
                    this.text_tv.setText(BuildConfig.FLAVOR + quoted_txt);
                    this.storedArray = new ArrayList(this.arrayfortv);
                    this.arrayfortv.clear();
                    setChangPunch(this.arrayfortv, quoted_txt, 4, 1);
                    updateTextSizeonScale(this.rl.getWidth(), this.rl.getHeight());
                    visibleBorder(this.rl);
                }
            }
            if (requestCode == GET_BG_RESULT) {
                if (this.isBlurLayVisible) {
                    this.blur_lay_cntrl.setVisibility(View.VISIBLE);
                    this.blur_lay_cntrl.setBackgroundResource(R.drawable.slide_down);
                    this.blur_seekbar_lay.setVisibility(View.VISIBLE);
                    this.sb_effectsfilter.setVisibility(View.VISIBLE);
                    this.sb_opctyfilter.setVisibility(View.VISIBLE);
                } else {
                    this.blur_lay_cntrl.setVisibility(View.VISIBLE);
                    this.blur_lay_cntrl.setBackgroundResource(R.drawable.slide_up);
                    this.blur_seekbar_lay.setVisibility(View.GONE);
                    this.sb_effectsfilter.setVisibility(View.GONE);
                    this.sb_opctyfilter.setVisibility(View.GONE);
                }
                this.categoryBG = data.getStringExtra("categoryBG");
                mDrawableName = data.getStringExtra("background");
                this.drawableName = mDrawableName;
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(mDrawableName, "drawable", getPackageName()));
                if (bitmap == null) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.image_error), Toast.LENGTH_SHORT).show();
                } else {
                    int width = bitmap.getWidth();
                    int height = bitmap.getHeight();
                    if (width < height) {
                        height = width;
                    } else {
                        width = height;
                    }
                    setImageInBackgrounds(ImageUtils.resizeBitmap(Constants.cropCenterBitmap(bitmap, width, height), (int) this.screenWidth, (int) this.screenWidth));
                }
            }
            if (requestCode == WRITE_QUOTE_RESULT) {
                quoted_txt = data.getStringExtra("quote_edit");
                String has_author = data.getStringExtra("hasAuthor");
                this.categoryQuote = data.getStringExtra("categoryQuote");
                this.scroll_of_all_backgrounds.setVisibility(View.GONE);
                this.scroll_of_all_effects.setVisibility(View.GONE);
                this.sb_effectsfilter.setVisibility(View.VISIBLE);
                this.sb_opctyfilter.setVisibility(View.VISIBLE);
                this.blur_lay_cntrl.setVisibility(View.VISIBLE);
                this.blur_seekbar_lay.setVisibility(View.VISIBLE);
                this.scroll_all.setVisibility(View.GONE);
            }
            if (requestCode == GET_STICKER) {
                mDrawableName = data.getStringExtra("sticker");
                String stickerType = data.getStringExtra("stickerType");
                this.categorySticker = data.getStringExtra("stickerCategory");
                addSticker(mDrawableName, stickerType);
                return;
            }
            return;
        }
        AlertDialog alertDialog = new Builder(this, 16974126).setMessage(ImageUtils.getSpannableString(this, Typeface.DEFAULT, R.string.picUpImg)).setPositiveButton(ImageUtils.getSpannableString(this, Typeface.DEFAULT, R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).create();

        alertDialog.show();
    }

    private void addSticker(String resId, String stickerType) {
        if (stickerType.equals("COLOR")) {
            this.lay_colorfilter.setVisibility(View.VISIBLE);
            this.lay_hue.setVisibility(View.GONE);
            this.colorPicker.setSelectedColor(Color.parseColor("#ffffff"));
        } else {
            this.lay_colorfilter.setVisibility(View.GONE);
            this.lay_hue.setVisibility(View.VISIBLE);
            this.hue_seekbar.setProgress(1);
        }
        this.transparency_seekbar.setProgress(255);
        int stckrwidth = Constants.dpToPx(this, 140);
        int mainLayHeight = bitmapOriginal.getHeight() / 2;
        float positionX = (float) ((bitmapOriginal.getWidth() / 2) - (stckrwidth / 2));
        float positionY = (float) (mainLayHeight - (Constants.dpToPx(this, 140) / 2));
        ComponentInfo ci = new ComponentInfo();
        ci.setPOS_X(positionX);
        ci.setPOS_Y(positionY);
        ci.setWIDTH(stckrwidth);
        ci.setHEIGHT(stckrwidth);
        ci.setROTATION(0.0f);
        Log.e("resId", "" + resId);
        Log.e("stickerType", "" + stickerType);
        ci.setRES_ID(resId);
        ci.setTYPE(stickerType);
        ci.setHUE(1);
        ci.setOPACITY(255);
        ResizableImageview riv = new ResizableImageview(this);
        riv.setComponentInfo(ci);
        this.fortext.addView(riv);
        riv.setMainLayoutWH((float) this.fortext.getWidth(), (float) this.fortext.getHeight());
        riv.setOnTouchListener(new MultiTouchListener().enableRotation(true).setOnTouchCallbackListener(this));
        riv.setOnTouchCallbackListener(this);
        riv.setBorderVisibility(true);
        this.sb_effectsfilter.setVisibility(View.INVISIBLE);
        this.sb_opctyfilter.setVisibility(View.INVISIBLE);
        this.blur_lay_cntrl.setVisibility(View.INVISIBLE);
        this.blur_seekbar_lay.setVisibility(View.INVISIBLE);
        if (this.isStickerLayVisible) {
            this.controll_btn_stckr.setVisibility(View.VISIBLE);
            this.controller_sticker.setVisibility(View.VISIBLE);
            this.controll_btn_stckr.setBackgroundResource(R.drawable.slide_down);
        } else {
            this.controll_btn_stckr.setVisibility(View.VISIBLE);
            this.controller_sticker.setVisibility(View.GONE);
            this.controll_btn_stckr.setBackgroundResource(R.drawable.slide_up);
        }
        if (stickerType.equals("COLOR")) {
            this.lay_colorfilter.setVisibility(View.VISIBLE);
            this.lay_hue.setVisibility(View.GONE);
            this.colorPicker.setSelectedColor(Color.parseColor("#ffffff"));
        } else {
            this.lay_colorfilter.setVisibility(View.GONE);
            this.lay_hue.setVisibility(View.VISIBLE);
            this.hue_seekbar.setProgress(ci.getHUE());
        }
        this.transparency_seekbar.setProgress(ci.getOPACITY());
    }


    private void addSticker(String resId, String stickerType, Bitmap bb) {


        if (stickerType.equals("COLOR")) {
            this.lay_colorfilter.setVisibility(View.VISIBLE);
            this.lay_hue.setVisibility(View.GONE);
            this.colorPicker.setSelectedColor(Color.parseColor("#ffffff"));
        } else {
            this.lay_colorfilter.setVisibility(View.GONE);
            this.lay_hue.setVisibility(View.VISIBLE);
            this.hue_seekbar.setProgress(1);
        }
        this.transparency_seekbar.setProgress(255);
        int stckrwidth = Constants.dpToPx(this, 140);
        int mainLayHeight = bitmapOriginal.getHeight() / 2;
        float positionX = (float) ((bitmapOriginal.getWidth() / 2) - (stckrwidth / 2));
        float positionY = (float) (mainLayHeight - (Constants.dpToPx(this, 140) / 2));
        ComponentInfo ci = new ComponentInfo();
        ci.setPOS_X(positionX);
        ci.setPOS_Y(positionY);
        ci.setWIDTH(stckrwidth);
        ci.setHEIGHT(stckrwidth);
        ci.setROTATION(0.0f);
        Log.e("resId", "" + resId);
        Log.e("stickerType", "" + stickerType);
        ci.setRES_ID(resId);
        ci.setTYPE(stickerType);
        ci.setHUE(1);
        ci.setOPACITY(255);
        ResizableImageview riv = new ResizableImageview(this);
        riv.setComponentInfoBitmap(ci, bb);
        this.fortext.addView(riv);
        riv.setMainLayoutWH((float) this.fortext.getWidth(), (float) this.fortext.getHeight());
        riv.setOnTouchListener(new MultiTouchListener().enableRotation(true).setOnTouchCallbackListener(this));
        riv.setOnTouchCallbackListener(this);
        riv.setBorderVisibility(true);
        this.sb_effectsfilter.setVisibility(View.INVISIBLE);
        this.sb_opctyfilter.setVisibility(View.INVISIBLE);
        this.blur_lay_cntrl.setVisibility(View.INVISIBLE);
        this.blur_seekbar_lay.setVisibility(View.INVISIBLE);
        if (this.isStickerLayVisible) {
            this.controll_btn_stckr.setVisibility(View.VISIBLE);
            this.controller_sticker.setVisibility(View.VISIBLE);
            this.controll_btn_stckr.setBackgroundResource(R.drawable.slide_down);
        } else {
            this.controll_btn_stckr.setVisibility(View.VISIBLE);
            this.controller_sticker.setVisibility(View.GONE);
            this.controll_btn_stckr.setBackgroundResource(R.drawable.slide_up);
        }
        if (stickerType.equals("COLOR")) {
            this.lay_colorfilter.setVisibility(View.VISIBLE);
            this.lay_hue.setVisibility(View.GONE);
            this.colorPicker.setSelectedColor(Color.parseColor("#ffffff"));
        } else {
            this.lay_colorfilter.setVisibility(View.GONE);
            this.lay_hue.setVisibility(View.VISIBLE);
            this.hue_seekbar.setProgress(ci.getHUE());
        }
        this.transparency_seekbar.setProgress(ci.getOPACITY());
    }

    public void removeImageViewControll() {
        int childCount = this.fortext.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = this.fortext.getChildAt(i);
            if (view instanceof ResizableImageview) {
                ((ResizableImageview) view).setBorderVisibility(false);
            }
        }
        if (this.controller_sticker.getVisibility() == View.VISIBLE) {
            this.controll_btn_stckr.setVisibility(View.GONE);
            this.controller_sticker.setVisibility(View.GONE);
        }
    }

    private void fontTag(String fontt) {
        if (this.textView.getTag().equals("text_tv")) {
            this.ft1 = fontt;
        }
        int normaltextSize = (int) this.textView.getTextSize();
        if (this.arrayfortv.size() > 0) {
            this.textSizeOffset = comapreSizes1(normaltextSize, (int) (((float) ((SelectedTextData) this.arrayfortv.get(0)).getText_size()) * getResources().getDisplayMetrics().density), this.arrayfortv) - normaltextSize;
            if (this.textSizeOffset < 0) {
                this.textSizeOffset = 5;
            }
        }
        updateTextSizeonScale(this.rl.getWidth(), this.rl.getHeight());
    }

    private void shaderTag(String shaderr) {
        if (this.textView.getTag().equals("text_tv")) {
            this.shr1 = shaderr;
        }
    }

    private GPUImageFilter switchFilterTo(GPUImageFilter filter) {
        Log.i("filter", BuildConfig.FLAVOR + this.mFilter);
        Log.i("filter", BuildConfig.FLAVOR + filter.toString());
        if (this.mFilter == null || !(filter == null || this.mFilter.getClass().equals(filter.getClass()))) {
            this.mFilter = filter;
            GPUImageFilter filter1 = this.mFilter;
            GPUImageFilter filter2 = GPUImageFilterTools.createFilterForType(this, FilterType.GAUSSIAN_BLUR);
            this.mFilterAdjuster = new FilterAdjuster(filter2);
            this.mFilterAdjuster.adjust(this.sb_effectsfilter.getProgress());
            GPUImageFilterGroup group = new GPUImageFilterGroup();
            group.addFilter(filter1);
            group.addFilter(filter2);
            this.gpuImageview.setFilter(group);
            this.gpuImageview.setVisibility(View.VISIBLE);
            this.mFilterAdjuster = new FilterAdjuster(this.mFilter);
            this.sb_effects.setVisibility(this.mFilterAdjuster.canAdjust() ? View.VISIBLE : View.GONE);
            this.sb_effects.setOnSeekBarChangeListener(this);
            this.sb_effectsfilter.setOnSeekBarChangeListener(this);
            if (this.mFilterAdjuster.canAdjust()) {
                new FilterAdjuster(filter1).adjust(50);
                new FilterAdjuster(filter2).adjust(this.sb_effectsfilter.getProgress());
                GPUImageFilterGroup group1 = new GPUImageFilterGroup();
                group1.addFilter(filter1);
                group1.addFilter(filter2);
                this.sb_effects.setProgress(50);
                this.gpuImageview.setFilter(group1);
                this.gpuImageview.setVisibility(View.VISIBLE);
            }
        }
        Log.i("filter", BuildConfig.FLAVOR + this.gpuImageview.getFilter());
        return this.mFilter;
    }

    public Bitmap mergeBitmap(Bitmap orig, Bitmap ontop) {
        float x;
        Bitmap bit = Bitmap.createBitmap(ontop.getWidth(), ontop.getHeight(), ontop.getConfig());
        if (orig.getWidth() < ontop.getWidth()) {
            x = (float) ((ontop.getWidth() - orig.getWidth()) / 2);
        } else {
            x = 0.0f;
        }
        Canvas canvas = new Canvas(bit);
        canvas.drawBitmap(orig, x, 0.0f, null);
        canvas.drawBitmap(ontop, 0.0f, 0.0f, null);
        return bit;
    }

    protected void onResume() {
        super.onResume();
        this.sb_effects.setVisibility(View.INVISIBLE);
    }

    public void onBackPressed() {
        if (this.alledit_ll.getVisibility() == View.VISIBLE) {
            this.alledit_ll.setVisibility(View.GONE);
            this.sb_effectsfilter.setVisibility(View.VISIBLE);
            this.sb_opctyfilter.setVisibility(View.VISIBLE);
            this.blur_lay_cntrl.setVisibility(View.VISIBLE);
            this.blur_seekbar_lay.setVisibility(View.VISIBLE);
            this.menu_ll.setVisibility(View.VISIBLE);
            if (this.textView != null) {
                Selection.removeSelection(this.textView.getText());
                hideChilds(this.rl);
                return;
            }
            return;
        }
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.pageexit_dialog);
        ((Button) dialog.findViewById(R.id.no)).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ((Button) dialog.findViewById(R.id.yes)).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                AddTextQuotesActivity.this.selectFocus = true;
                dialog.dismiss();
                AddTextQuotesActivity.this.finish();
            }
        });
        dialog.show();
    }

    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        int i;
        View view1;
        switch (seekBar.getId()) {
            case R.id.sb_effects:
                if (this.mFilterAdjuster != null) {
                    this.mFilterAdjuster.adjust(progress);
                }
                this.gpuImageview.requestRender();
                return;
            case R.id.trans_seekBar:
                int childCount2 = this.fortext.getChildCount();
                for (i = 0; i < childCount2; i++) {
                    view1 = this.fortext.getChildAt(i);
                    if ((view1 instanceof ResizableImageview) && ((ResizableImageview) view1).getBorderVisbilty()) {
                        ((ResizableImageview) view1).settransparency(progress);
                    }
                }
                return;
            case R.id.hue_seekBar:
                int childCount1 = this.fortext.getChildCount();
                for (i = 0; i < childCount1; i++) {
                    view1 = this.fortext.getChildAt(i);
                    if ((view1 instanceof ResizableImageview) && ((ResizableImageview) view1).getBorderVisbilty()) {
                        ((ResizableImageview) view1).setHueProg(progress);
                    }
                }
                return;
            case R.id.sb_opctyfilter:
                try {
                    this.img_opacity.setImageAlpha(progress);
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            case R.id.sb_effectsfilter:
                this.sb_effects.setVisibility(View.INVISIBLE);
                GPUImageFilter filter1 = GPUImageFilterTools.createFilterForType(this, this.filterType);
                GPUImageFilter filter2 = GPUImageFilterTools.createFilterForType(this, FilterType.GAUSSIAN_BLUR);
                new FilterAdjuster(filter1).adjust(this.sb_effects.getProgress());
                new FilterAdjuster(filter2).adjust(progress);
                GPUImageFilterGroup group = new GPUImageFilterGroup();
                group.addFilter(filter1);
                group.addFilter(filter2);
                this.gpuImageview.setFilter(group);
                this.gpuImageview.requestRender();
                return;
            default:
                return;
        }
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
        switch (seekBar.getId()) {
            case R.id.sb_effectsfilter:
                this.sb_effects.setVisibility(View.INVISIBLE);
                this.selectFocus = true;
                return;
            default:
                return;
        }
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    private void mainAddMethod() {
        this.min = 0;
        this.max = this.textView.getText().length();
        if (this.textView.isFocused()) {
            this.a = this.textView.getSelectionStart();
            this.b = this.textView.getSelectionEnd();
            int selStart = this.textView.getSelectionStart();
            int selEnd = this.textView.getSelectionEnd();
            this.min = Math.max(0, Math.min(selStart, selEnd));
            this.max = Math.max(0, Math.max(selStart, selEnd));
            if (this.min != this.max) {
                SelectedTextData std = new SelectedTextData();
                std.setStart(this.min);
                std.setEnd(this.max);
                std.setText_size((int) (this.textView.getTextSize() / getResources().getDisplayMetrics().density));
                std.setText_color(this.textView.getCurrentTextColor());
                String fft = BuildConfig.FLAVOR;
                String shdrt = BuildConfig.FLAVOR;
                if (this.textView.getTag().equals("text_tv")) {
                    fft = this.ft1;
                }
                if (this.textView.getTag().equals("text_tv")) {
                    shdrt = this.shr1;
                }
                std.setText_ttf(fft);
                std.setText_shadowdx(this.textView.getShadowDx());
                std.setText_shadowdy(this.textView.getShadowDy());
                std.setText_shadowradius(this.textView.getShadowRadius());
                std.setText_shadowcolor(this.textView.getShadowColor());
                std.setText_shader(shdrt);
                std.setText_bold(false);
                std.setText_italic(false);
                std.setText_underline(false);
                std.setText_strike(false);
                if (this.textView.getTag().equals("text_tv")) {
                    boolean datapresent = false;
                    if (this.arrayfortv.size() > 0) {
                        int i = 0;
                        while (i < this.arrayfortv.size()) {
                            if (((SelectedTextData) this.arrayfortv.get(i)).getStart() == this.min && ((SelectedTextData) this.arrayfortv.get(i)).getEnd() == this.max) {
                                datapresent = true;
                            }
                            i++;
                        }
                    }
                    if (!datapresent) {
                        this.arrayfortv.add(std);
                    }
                }
            }
            if (this.alledit_ll.getVisibility() != View.VISIBLE) {
                this.menu_ll.setVisibility(View.GONE);
                this.alledit_ll.setVisibility(View.VISIBLE);
                this.sb_effectsfilter.setVisibility(View.GONE);
                this.blur_lay_cntrl.setVisibility(View.GONE);
                this.blur_seekbar_lay.setVisibility(View.GONE);
                this.scroll_of_all_effects.setVisibility(View.GONE);
                this.scroll_of_all_backgrounds.setVisibility(View.GONE);
                this.scroll_all.setVisibility(View.GONE);
            }
            defaultsetup();
        }
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void mainAddMethod1() {
        this.min = 0;
        this.max = this.textView.getText().length();
        if (this.textView.isFocused()) {
            this.a = this.textView.getSelectionStart();
            this.b = this.textView.getSelectionEnd();
            int selStart = this.textView.getSelectionStart();
            int selEnd = this.textView.getSelectionEnd();
            this.min = Math.max(0, Math.min(selStart, selEnd));
            this.max = Math.max(0, Math.max(selStart, selEnd));
            SelectedTextData std = new SelectedTextData();
            std.setStart(this.min);
            std.setEnd(this.max);
            std.setText_size((int) ((this.textView.getTextSize() + 5.0f) / getResources().getDisplayMetrics().density));
            std.setText_color(-16777216);
            String fft = BuildConfig.FLAVOR;
            String shdrt = BuildConfig.FLAVOR;
            if (this.textView.getTag().equals("text_tv")) {
                fft = this.ft1;
            }
            if (this.textView.getTag().equals("text_tv")) {
                shdrt = this.shr1;
            }
            std.setText_ttf(fft);
            std.setText_shadowdx(this.textView.getShadowDx());
            std.setText_shadowdy(this.textView.getShadowDy());
            std.setText_shadowradius(this.textView.getShadowRadius());
            std.setText_shadowcolor(this.textView.getShadowColor());
            std.setText_shader(shdrt);
            std.setText_bold(false);
            std.setText_italic(false);
            std.setText_underline(false);
            std.setText_strike(false);
            if (this.textView.getTag().equals("text_tv")) {
                boolean datapresent = false;
                if (this.arrayfortv.size() > 0) {
                    int i = 0;
                    while (i < this.arrayfortv.size()) {
                        if (((SelectedTextData) this.arrayfortv.get(i)).getStart() == this.min && ((SelectedTextData) this.arrayfortv.get(i)).getEnd() == this.max) {
                            datapresent = true;
                        }
                        i++;
                    }
                }
                if (!datapresent) {
                    this.arrayfortv.add(std);
                }
            }
            if (this.alledit_ll.getVisibility() != View.VISIBLE) {
                this.menu_ll.setVisibility(View.GONE);
                this.alledit_ll.setVisibility(View.VISIBLE);
                this.sb_effectsfilter.setVisibility(View.GONE);
                this.blur_lay_cntrl.setVisibility(View.GONE);
                this.blur_seekbar_lay.setVisibility(View.GONE);
                this.scroll_all.setVisibility(View.GONE);
                this.scroll_of_all_effects.setVisibility(View.GONE);
                this.scroll_of_all_backgrounds.setVisibility(View.GONE);
            }
            defaultsetup();
        }
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void ontemplate(int position) {
        Log.e("templte_pos", BuildConfig.FLAVOR + position);
        this.temp_postn = position;
        this.selecttemplate = false;
        if (this.rl.getVisibility() == View.VISIBLE) {
            UpdateAddTextMethod();
        } else {
            Toast.makeText(this, getResources().getString(R.string.error_edit), Toast.LENGTH_SHORT).show();
        }
    }

    protected void onPause() {
        super.onPause();
        freeMemory();
    }

    public void onDestroy() {
        super.onDestroy();
        try {
            this.back_arrow_add_quotes = null;
            this.f = null;
            this.params_rl = null;
            this.done_add_quotes = null;
            this.db = null;
            if (this.bitmap != null) {
                this.bitmap.recycle();
                this.bitmap = null;
            }
            if (this.bitRel != null) {
                this.bitRel.recycle();
                this.bitRel = null;
            }
            if (this.bb != null) {
                this.bb.recycle();
                this.bb = null;
            }
            if (bitmapOriginal != null) {
                bitmapOriginal.recycle();
                bitmapOriginal = null;
            }
            this.filename = null;
            this.complete_img = null;
            c = null;
            this.gpuImageview = null;
            this.blur_seekbar_lay = null;
            this.top_option = null;
            this.re_background = null;
            this.re_sticker = null;
            this.rel_effects = null;
            this.scroll_all = null;
            this.tab_text = null;
            this.tab_punch = null;
            this.tab_shadow_txt = null;
            this.tab_shadow_punch = null;
            this.tab_shader_txt = null;
            this.tab_shader_punch = null;
            this.tab_font_txt = null;
            this.tab_font_punch = null;
            this.tab_format_txt = null;
            this.tab_format_punch = null;
            this.lay_hue = null;
            this.lay_colorfilter = null;
            this.cntrls_stkr_lay = null;
            this.cntrls_stkrclr_lay = null;
            this.tab_cntrl_stkr = null;
            this.tab_clrs_stkr = null;
            this.fortext = null;
            this.rl = null;
            this.alledit_ll = null;
            this.text_tv = null;
            this.scale_iv = null;
            this.move_iv = null;
            this.rotate_iv = null;
            this.delete_iv = null;
            this.controll_btn_stckr = null;
            this.edit_ivTxt = null;
            this.controller_sticker = null;
            this.btn_left = null;
            this.btn_right = null;
            this.btn_top = null;
            this.btn_down = null;
            this.duplicate = null;
            this.blur_lay_cntrl = null;
            this.list_of_brnd = null;
            this.size = null;
            this.list_of_sticker = null;
            this.footer_effects_image = null;
            this.fonts = null;
            this.color_ = null;
            this.shadow = null;
            this.img_opacity = null;
            this.shader = null;
            this.list_of_style = null;
            this.menu_ll = null;
            this.formatall_type = null;
            this.scroll_of_all_backgrounds = null;
            this.scroll_of_all_effects = null;
            this.mFilterAdjuster = null;
            this.la_size = null;
            this.la_fonts = null;
            this.la_color = null;
            this.la_shadow = null;
            this.la_shader = null;
            this.re_template = null;
            this.font_rel = null;
            this.color_rel = null;
            this.shadow_rel = null;
            this.shader_rel = null;
            this.lay_viewStyle = null;
            this.temp_postn = 1;
            this.g1 = null;
            this.g2 = null;
            this.g3 = null;
            this.sd_color = null;
            this.sh = null;
            this.sh1 = null;
            this.sh2 = null;
            this.sh3 = null;
            this.sh4 = null;
            this.sh5 = null;
            this.sh6 = null;
            this.sh7 = null;
            this.sh8 = null;
            this.sh9 = null;
            this.sh10 = null;
            this.bold = null;
            this.italic = null;
            this.underline = null;
            this.strike = null;
            this.textView = null;
            this.sb_effects = null;
            this.sb_effectsfilter = null;
            this.sb_opctyfilter = null;
            this.hue_seekbar = null;
            this.transparency_seekbar = null;
            this.shadow_seekbar = null;
            this.colorPicker = null;
            this.arrayfortv = null;
            this.storedArray = null;
            this.mFilter = null;
            this.ttD = null;
            this.ttf1 = null;
            this.ttf2 = null;
            this.ttf3 = null;
            this.ttf4 = null;
            this.ttf5 = null;
            this.ttf6 = null;
            this.ttf7 = null;
            this.ttf8 = null;
            this.ttf9 = null;
            this.ttf10 = null;
            this.ttf11 = null;
            this.ttf12 = null;
            this.ttf13 = null;
            this.ttf14 = null;
            this.ttf15 = null;
            this.ttf16 = null;
            this.ttf17 = null;
            this.ttf18 = null;
            this.ttf19 = null;
            this.ttf20 = null;
            this.ttf21 = null;
            this.ttf22 = null;
            this.ttf23 = null;
            this.ttf24 = null;
            this.ttf25 = null;
            this.ttf26 = null;
            this.ttf27 = null;
            this.ttf28 = null;
            this.ttf29 = null;
            this.gd = null;
            this.recyclerView = null;
            this.styl_recycler = null;
            this.dbHelper = null;
            this.spannableString = null;
            this.builder = null;
            this.preferences = null;
            this.filterType = null;
            this.img_format_txt = null;
            this.img_format_punch = null;
            this.img_font_txt = null;
            this.img_font_punch = null;
            this.img_shadow_txt = null;
            this.img_shadow_punch = null;
            this.img_shader_txt = null;
            this.img_shader_punch = null;
            this.img_color_txt = null;
            this.img_color_punch = null;
            this.format_txt = null;
            this.format_punch = null;
            this.font_txt = null;
            this.font_punch = null;
            this.shadow_txt = null;
            this.shadow_punch = null;
            this.shader_txt = null;
            this.shader_punch = null;
            this.color_txt = null;
            this.color_punch = null;
            this.contrl_txt = null;
            this.clr_opacity_txt = null;
            this.picture_txt = null;
            this.sticker_txt = null;
            this.effect_txt = null;
            this.pallete = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        freeMemory();
    }

    private void freeMemory() {
        try {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        Glide.get(AddTextQuotesActivity.this).clearDiskCache();
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            Glide.get(this).clearMemory();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        Constants.freeMemory();
    }

    private void setImageInBackgrounds(Bitmap selectBitmap) {
        this.complete_img.getLayoutParams().height = selectBitmap.getHeight();
        this.complete_img.getLayoutParams().width = selectBitmap.getWidth();
        this.complete_img.postInvalidate();
        this.complete_img.requestLayout();
        bitmapOriginal = selectBitmap;
        this.gpuImageview.setImage(selectBitmap);
        this.gpuImageview.setVisibility(View.VISIBLE);
    }

    private void saveBitmap(final boolean inPNG) {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage(getResources().getString(R.string.plzwait));
        pd.setCancelable(false);
        pd.show();
        new Thread(new Runnable() {
            public void run() {
                try {
                    File pictureFileDir = new File(APPUtility.getAppDir(), getResources().getString(R.string.app_name));
                    if (pictureFileDir.exists() || pictureFileDir.mkdirs()) {
                        String photoFile = "text_On_photo" + System.currentTimeMillis();
                        if (inPNG) {
                            photoFile = photoFile + ".png";
                        } else {
                            photoFile = photoFile + ".jpg";
                        }
                        AddTextQuotesActivity.this.filename = pictureFileDir.getPath() + File.separator + photoFile;
                        File pictureFile = new File(AddTextQuotesActivity.this.filename);
                        try {
                            if (!pictureFile.exists()) {
                                pictureFile.createNewFile();
                            }
                            FileOutputStream ostream = new FileOutputStream(pictureFile);
                            if (inPNG) {
                                AddTextQuotesActivity.this.bitRel.compress(CompressFormat.PNG, 100, ostream);
                            } else {
                                Bitmap newBitmap = Bitmap.createBitmap(AddTextQuotesActivity.this.bitRel.getWidth(), AddTextQuotesActivity.this.bitRel.getHeight(), AddTextQuotesActivity.this.bitRel.getConfig());
                                Canvas canvas = new Canvas(newBitmap);
                                canvas.drawColor(-1);
                                canvas.drawBitmap(AddTextQuotesActivity.this.bitRel, 0.0f, 0.0f, null);
                                newBitmap.compress(CompressFormat.JPEG, 100, ostream);
                                newBitmap.recycle();
                            }
                            ostream.flush();
                            ostream.close();
                            AddTextQuotesActivity.this.isUpadted = true;
                            AddTextQuotesActivity.this.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(pictureFile)));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Thread.sleep(1000);
                        pd.dismiss();
                        return;
                    }
                    Log.d(BuildConfig.FLAVOR, "Can't create directory to save image.");
                    Toast.makeText(AddTextQuotesActivity.this.getApplicationContext(), AddTextQuotesActivity.this.getResources().getString(R.string.create_dir_err), Toast.LENGTH_LONG).show();
                } catch (Exception e2) {
                }
            }
        }).start();
        pd.setOnDismissListener(new OnDismissListener() {
            public void onDismiss(DialogInterface dialog) {
                if (AddTextQuotesActivity.this.isUpadted) {
                    Intent intent = new Intent(AddTextQuotesActivity.this, SaveActivity.class);
                    intent.putExtra("uri", AddTextQuotesActivity.this.filename);
                    intent.putExtra("way", "AddQuote");
                    AddTextQuotesActivity.this.startActivity(intent);
                    if (!AddTextQuotesActivity.this.preferences.getBoolean("isAdsDisabled", false)) {
                    }
                }
            }
        });
    }

    public void onColor(int position, String type) {
        if (position != 0) {
            if (type.equals("text")) {
                visibleBorder(this.rl);
                if (this.modeClrSelection.equals("textclr")) {
                    this.shr1 = "null";
                    this.textView.getPaint().setShader(null);
                    this.textView.postInvalidate();
                    this.textView.requestLayout();
                    this.textView.setTextColor(position);
                    this.existingcolor = position;
                } else {
                    setShaderonSelected("null");
                    setColoronSelected(position);
                    this.existingcolor = position;
                }
                updateColors();
                return;
            }
            this.color = position;
            int childCount1 = this.fortext.getChildCount();
            for (int i = 0; i < childCount1; i++) {
                View view1 = this.fortext.getChildAt(i);
                if ((view1 instanceof ResizableImageview) && ((ResizableImageview) view1).getBorderVisbilty()) {
                    ((ResizableImageview) view1).setColorFilter(this.color);
                }
            }
        } else if (type.equals("text")) {
            visibleBorder(this.rl);
        }
    }

    public void onTouchCallback(View v) {
        removeImageViewControll();
        this.effect_txt.setTextColor(-1);
        this.sticker_txt.setTextColor(-16777216);
        this.rel_effects.setBackgroundResource(R.drawable.gradient);
        this.re_sticker.setBackgroundColor(-1);
        this.footer_effects_image.setBackgroundResource(R.drawable.effects_image1);
        this.list_of_brnd.setBackgroundResource(R.drawable.backrund1);
        this.list_of_sticker.setBackgroundResource(R.drawable.stickers);
        if (this.textView != null) {
            int noch = ((ViewGroup) this.textView.getParent()).getChildCount();
            for (int i = 1; i < noch; i++) {
                ((ViewGroup) this.textView.getParent()).getChildAt(i).setVisibility(View.INVISIBLE);
            }
        }
        this.controll_btn_stckr.setVisibility(View.GONE);
        this.controller_sticker.setVisibility(View.GONE);
        if (this.alledit_ll.getVisibility() == View.VISIBLE) {
            this.menu_ll.setVisibility(View.VISIBLE);
            this.alledit_ll.setVisibility(View.GONE);
            this.sb_effectsfilter.setVisibility(View.VISIBLE);
            this.blur_lay_cntrl.setVisibility(View.VISIBLE);
            this.blur_seekbar_lay.setVisibility(View.VISIBLE);
        }
        this.scroll_of_all_effects.setVisibility(View.INVISIBLE);
        this.scroll_of_all_backgrounds.setVisibility(View.GONE);
    }

    public void onTouchUpCallback(View v) {
        this.sb_effectsfilter.setVisibility(View.INVISIBLE);
        this.blur_lay_cntrl.setVisibility(View.INVISIBLE);
        this.blur_seekbar_lay.setVisibility(View.INVISIBLE);
        if (this.isStickerLayVisible) {
            this.controll_btn_stckr.setVisibility(View.VISIBLE);
            this.controller_sticker.setVisibility(View.VISIBLE);
            this.controll_btn_stckr.setBackgroundResource(R.drawable.slide_down);
        } else {
            this.controll_btn_stckr.setVisibility(View.VISIBLE);
            this.controller_sticker.setVisibility(View.GONE);
            this.controll_btn_stckr.setBackgroundResource(R.drawable.slide_up);
        }
        ComponentInfo ci = ((ResizableImageview) v).getComponentInfo();
        String type = ci.getTYPE();
        int alphaProg = ci.getOPACITY();
        if (type.equals("COLOR")) {
            this.lay_colorfilter.setVisibility(View.VISIBLE);
            this.lay_hue.setVisibility(View.GONE);
            this.colorPicker.setSelectedColor(Color.parseColor("#ffffff"));
        } else {
            this.lay_colorfilter.setVisibility(View.GONE);
            this.lay_hue.setVisibility(View.VISIBLE);
            this.hue_seekbar.setProgress(ci.getHUE());
        }
        this.transparency_seekbar.setProgress(alphaProg);
    }

    public void onTouchMoveCallback(View v) {
    }

    public void setObserver(final EditText edTxt) {
        edTxt.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                if (AddTextQuotesActivity.this.isKeyboardShown(edTxt.getRootView())) {
                    AddTextQuotesActivity.this.textView.setCursorVisible(false);
                    AddTextQuotesActivity.this.ch = true;
                } else if (AddTextQuotesActivity.this.ch) {
                    edTxt.setShowSoftInputOnFocus(false);
                    AddTextQuotesActivity.this.gpuImageview.performClick();
                    AddTextQuotesActivity.this.ch = false;
                }
            }
        });
    }

    private boolean isKeyboardShown(View rootView) {
        Rect r = new Rect();
        rootView.getWindowVisibleDisplayFrame(r);
        return ((float) (rootView.getBottom() - r.bottom)) > 128.0f * rootView.getResources().getDisplayMetrics().density;
    }

    private void setSelectTextDataOneAtaTime(ArrayList<SelectedTextData> arra, String dlft, EditText editView, int txtID) {
        List<QuotesSelect> quotesw = this.db.getSelectAllRowValue(this.temp_postn);
        for (int i = 0; i < quotesw.size(); i++) {
            if (((QuotesSelect) quotesw.get(i)).get_text_id() == txtID) {
                SelectedTextData std = new SelectedTextData();
                std.setStart(Integer.parseInt(((QuotesSelect) quotesw.get(i)).get_start()));
                std.setEnd(Integer.parseInt(((QuotesSelect) quotesw.get(i)).get_end()));
                Resources r = getResources();
                if (dlft.equals("default")) {
                    std.setText_size((int) PictureConstant.convertPixelsToDp((float) (((int) Float.parseFloat(((QuotesSelect) quotesw.get(i)).get_size())) + ((int) Float.valueOf(editView.getTextSize()).floatValue())), this));
                } else {
                    std.setText_size((int) Float.parseFloat(((QuotesSelect) quotesw.get(i)).get_size()));
                }
                std.setText_color(Integer.parseInt(((QuotesSelect) quotesw.get(i)).get_color()));
                std.setText_ttf(((QuotesSelect) quotesw.get(i)).get_font());
                std.setText_shadowdx(Float.parseFloat(((QuotesSelect) quotesw.get(i)).get_shadow_dx()));
                std.setText_shadowdy(Float.parseFloat(((QuotesSelect) quotesw.get(i)).get_shadow_dy()));
                std.setText_shadowradius(Float.parseFloat(((QuotesSelect) quotesw.get(i)).get_shadow_radius()));
                std.setText_shadowcolor(Integer.parseInt(((QuotesSelect) quotesw.get(i)).get_shadow_color()));
                std.setText_shader(((QuotesSelect) quotesw.get(i)).get_shader());
                std.setText_bold(Boolean.valueOf(((QuotesSelect) quotesw.get(i)).get_textbold()).booleanValue());
                std.setText_italic(Boolean.valueOf(((QuotesSelect) quotesw.get(i)).get_text_italic()).booleanValue());
                std.setText_underline(Boolean.valueOf(((QuotesSelect) quotesw.get(i)).get_text_underline()).booleanValue());
                std.setText_strike(Boolean.valueOf(((QuotesSelect) quotesw.get(i)).get_text_strik()).booleanValue());
                arra.add(std);
            }
        }
        defaultsetup();
    }

    private int getOptimumTextSize(int start, int end, SpannableStringBuilder text, RectF availableSpace, TextPaint textPaint, int gravity) {
        return binarySearch(start, end, text, availableSpace, textPaint, gravity);
    }

    private int binarySearch(int start, int end, SpannableStringBuilder text, RectF availableSpace, TextPaint textPaint, int gravity) {
        int lastBest = start;
        int lo = start;
        int hi = end - 1;
        while (lo <= hi) {
            int i = (lo + hi) >>> 1;
            int midValCmp = onTestSize(i, text, availableSpace, textPaint, gravity);
            if (midValCmp < 0) {
                lastBest = lo;
                lo = i + 1;
            } else if (midValCmp <= 0) {
                return i;
            } else {
                hi = i - 1;
                lastBest = hi;
            }
        }
        return lastBest;
    }

    public int onTestSize(int suggestedSize, SpannableStringBuilder text, RectF availableSpace, TextPaint textPaint, int gravity) {
        textPaint.setTextSize((float) suggestedSize);
        RectF textRect = new RectF();
        float _widthLimit = availableSpace.width();
        StaticLayout layout = null;
        SpannableStringBuilder builder = createSpannableString(this.arrayfortv, suggestedSize, this.spannableString, false);
        if (gravity == 17) {
            layout = new StaticLayout(builder, textPaint, (int) _widthLimit, Alignment.ALIGN_CENTER, (float) 1, 0.0f, true);
        } else if (gravity == 49) {
            layout = new StaticLayout(builder, textPaint, (int) _widthLimit, Alignment.ALIGN_CENTER, (float) 1, 0.0f, true);
        } else if (gravity == 51) {
            layout = new StaticLayout(builder, textPaint, (int) _widthLimit, Alignment.ALIGN_NORMAL, (float) 1, 0.0f, true);
        } else if (gravity == 53) {
            layout = new StaticLayout(builder, textPaint, (int) _widthLimit, Alignment.ALIGN_OPPOSITE, (float) 1, 0.0f, true);
        }
        textRect.bottom = (float) layout.getHeight();
        textRect.right = _widthLimit;
        textRect.offsetTo(0.0f, 0.0f);
        if (availableSpace.contains(textRect)) {
            return -1;
        }
        return 1;
    }

    private int getOptimumTextSize1(int start, int end, String text, RectF availableSpace, TextPaint textPaint, int gravity) {
        return binarySearch1(start, end, text, availableSpace, textPaint, gravity);
    }

    private int binarySearch1(int start, int end, String text, RectF availableSpace, TextPaint textPaint, int gravity) {
        int lastBest = start;
        int lo = start;
        int hi = end - 1;
        while (lo <= hi) {
            int i = (lo + hi) >>> 1;
            int midValCmp = onTestSize1(i, text, availableSpace, textPaint, gravity);
            if (midValCmp < 0) {
                lastBest = lo;
                lo = i + 1;
            } else if (midValCmp <= 0) {
                return i;
            } else {
                hi = i - 1;
                lastBest = hi;
            }
        }
        return lastBest;
    }

    public int onTestSize1(int suggestedSize, String text, RectF availableSpace, TextPaint textPaint, int gravity) {
        textPaint.setTextSize((float) suggestedSize);
        RectF textRect = new RectF();
        float _widthLimit = availableSpace.width();
        StaticLayout layout = null;
        if (gravity == 17) {
            layout = new StaticLayout(text, textPaint, (int) _widthLimit, Alignment.ALIGN_CENTER, (float) 1, 0.0f, true);
        } else if (gravity == 49) {
            layout = new StaticLayout(text, textPaint, (int) _widthLimit, Alignment.ALIGN_CENTER, (float) 1, 0.0f, true);
        } else if (gravity == 51) {
            layout = new StaticLayout(text, textPaint, (int) _widthLimit, Alignment.ALIGN_NORMAL, (float) 1, 0.0f, true);
        } else if (gravity == 53) {
            layout = new StaticLayout(text, textPaint, (int) _widthLimit, Alignment.ALIGN_OPPOSITE, (float) 1, 0.0f, true);
        }
        textRect.bottom = (float) layout.getHeight();
        textRect.right = _widthLimit;
        textRect.offsetTo(0.0f, 0.0f);
        if (availableSpace.contains(textRect)) {
            return -1;
        }
        return 1;
    }

    public SpannableStringBuilder createSpannableString(ArrayList<SelectedTextData> arrayfortv, int textSize, SpannableString spannableString, boolean isFirst) {
        if (arrayfortv.size() > 0) {
            for (int i = 0; i < arrayfortv.size(); i++) {
                if (((SelectedTextData) arrayfortv.get(i)).getEnd() <= this.textView.getText().length()) {
                    try {
                        spannableString.setSpan(new TextAppearanceSpan(null, 0, textSize, null, null), ((SelectedTextData) arrayfortv.get(i)).getStart(), ((SelectedTextData) arrayfortv.get(i)).getEnd(), 0);
                        if (isFirst) {
                            Typeface ttq;
                            if (((SelectedTextData) arrayfortv.get(i)).getText_ttf().equals(BuildConfig.FLAVOR)) {
                                ttq = this.textView.getTypeface();
                            } else {
                                ttq = Typeface.createFromAsset(getAssets(), ((SelectedTextData) arrayfortv.get(i)).getText_ttf());
                            }
                            spannableString.setSpan(new CustomTypefaceSpan(ttq), ((SelectedTextData) arrayfortv.get(i)).getStart(), ((SelectedTextData) arrayfortv.get(i)).getEnd(), 0);
                            spannableString.setSpan(new ForegroundColorSpan(((SelectedTextData) arrayfortv.get(i)).getText_color()), ((SelectedTextData) arrayfortv.get(i)).getStart(), ((SelectedTextData) arrayfortv.get(i)).getEnd(), 33);
                            spannableString.setSpan(new CustomShadowSpan(((SelectedTextData) arrayfortv.get(i)).getText_shadowradius(), ((SelectedTextData) arrayfortv.get(i)).getText_shadowdx(), ((SelectedTextData) arrayfortv.get(i)).getText_shadowdy(), ((SelectedTextData) arrayfortv.get(i)).getText_shadowcolor()), ((SelectedTextData) arrayfortv.get(i)).getStart(), ((SelectedTextData) arrayfortv.get(i)).getEnd(), 0);
                            Shader sadr = null;
                            if (!((SelectedTextData) arrayfortv.get(i)).getText_shader().equals("null")) {
                                sadr = new BitmapShader(BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(((SelectedTextData) arrayfortv.get(i)).getText_shader(), "drawable", getPackageName())), TileMode.MIRROR, TileMode.MIRROR);
                            }
                            spannableString.setSpan(new CustomShaderSpan(sadr), ((SelectedTextData) arrayfortv.get(i)).getStart(), ((SelectedTextData) arrayfortv.get(i)).getEnd(), 0);
                            if (((SelectedTextData) arrayfortv.get(i)).isText_bold()) {
                                spannableString.setSpan(new StyleSpan(1), ((SelectedTextData) arrayfortv.get(i)).getStart(), ((SelectedTextData) arrayfortv.get(i)).getEnd(), 0);
                            } else {
                                spannableString.removeSpan(new StyleSpan(1));
                            }
                            if (((SelectedTextData) arrayfortv.get(i)).isText_italic()) {
                                spannableString.setSpan(new StyleSpan(2), ((SelectedTextData) arrayfortv.get(i)).getStart(), ((SelectedTextData) arrayfortv.get(i)).getEnd(), 0);
                            } else {
                                spannableString.removeSpan(new StyleSpan(2));
                            }
                            if (((SelectedTextData) arrayfortv.get(i)).isText_underline()) {
                                spannableString.setSpan(new UnderlineSpan(), ((SelectedTextData) arrayfortv.get(i)).getStart(), ((SelectedTextData) arrayfortv.get(i)).getEnd(), 0);
                            } else {
                                spannableString.removeSpan(new UnderlineSpan());
                            }
                            if (((SelectedTextData) arrayfortv.get(i)).isText_strike()) {
                                spannableString.setSpan(new StrikethroughSpan(), ((SelectedTextData) arrayfortv.get(i)).getStart(), ((SelectedTextData) arrayfortv.get(i)).getEnd(), 0);
                            } else {
                                spannableString.removeSpan(new StrikethroughSpan());
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return new SpannableStringBuilder().append(spannableString);
    }

    public void onDelete(View v) {
        this.controll_btn_stckr.setVisibility(View.GONE);
        this.controller_sticker.setVisibility(View.GONE);
    }

    public boolean setDefaultTouchListener(boolean enable) {
        if (enable) {
            this.text_tv.setClickable(true);
            this.text_tv.setFocusable(true);
            this.text_tv.requestFocus();
            this.text_tv.setEnabled(true);
            this.text_tv.setTextIsSelectable(true);
            this.text_tv.setCursorVisible(false);
            this.text_tv.setOnTouchListener(this.textTouchListener);
            this.selectFocus = true;
            this.ed = false;
            this.flag = false;
            this.flag2 = false;
            this.textView = this.text_tv;
            setObserver(this.textView);
            this.textView.setSelectAllOnFocus(false);
            this.textView.setTextIsSelectable(true);
            this.textView.setCursorVisible(false);
            return true;
        }
        this.text_tv.setClickable(false);
        this.text_tv.setEnabled(false);
        this.text_tv.setOnTouchListener(null);
        this.selectFocus = false;
        return false;
    }

    public void createDropColorImg(final String type) {
        final ProgressDialog ringProgressDialog1 = ProgressDialog.show(this, BuildConfig.FLAVOR, getString(R.string.plzwait), true);
        ringProgressDialog1.setCancelable(false);
        View view = null;
        if (type.equals("sticker")) {
            int childCount1 = this.fortext.getChildCount();
            for (int i = 0; i < childCount1; i++) {
                View view1 = this.fortext.getChildAt(i);
                if ((view1 instanceof ResizableImageview) && ((ResizableImageview) view1).getBorderVisbilty()) {
                    view = view1;
                    ((ResizableImageview) view1).setBorderVisibility(false);
                }
            }
        }
        new Thread(new Runnable() {
            public void run() {
                try {
                    AddTextQuotesActivity.this.complete_img.setDrawingCacheEnabled(true);
                    AddTextQuotesActivity.this.bitRel = Bitmap.createBitmap(AddTextQuotesActivity.this.complete_img.getDrawingCache());
                    AddTextQuotesActivity.this.complete_img.setDrawingCacheEnabled(false);
                    try {
                        AddTextQuotesActivity.this.bb = Bitmap.createBitmap(AddTextQuotesActivity.this.gpuImageview.capture());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    AddTextQuotesActivity.bitmapOriginal = AddTextQuotesActivity.this.mergeBitmap(AddTextQuotesActivity.this.bb, AddTextQuotesActivity.this.bitRel);
                    Thread.sleep(1000);
                } catch (Exception e2) {
                }
                ringProgressDialog1.dismiss();
            }
        }).start();
        final View finalView = view;
        ringProgressDialog1.setOnDismissListener(new OnDismissListener() {
            public void onDismiss(DialogInterface dialog) {
                if (type.equals("sticker")) {
                    ((ResizableImageview) finalView).setBorderVisibility(true);
                }
                Intent ii = new Intent(AddTextQuotesActivity.this, PickColorImageActivity.class);
                ii.putExtra("type", type);
                AddTextQuotesActivity.this.startActivity(ii);
            }
        });
    }

    public void ongetImageBitmap(String imgType) {
        if (imgType.equals("image")) {
            this.drawableName = BuildConfig.FLAVOR;
            this.bitmap = ImageUtils.resizeBitmap(CropActivityTwo.bitmapImage, (int) this.screenWidth, (int) this.screenWidth);
            setImageInBackgrounds(this.bitmap);
            CropActivityTwo.bitmapImage.recycle();
        }
    }

    private int getPositionFromCategory(String category) {
        String[] TITLES = getResources().getStringArray(R.array.listOfManageQuotesItem);
        for (int i = 0; i < TITLES.length; i++) {
            if (category.equals(TITLES[i])) {
                return i;
            }
        }
        return 0;
    }


}

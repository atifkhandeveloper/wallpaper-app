package com.digiclack.wallpapers.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.digiclack.wallpapers.R;

import yuku.ambilwarna.BuildConfig;

public class EditQuoteActivity extends Activity {
    OnClickListener cancelClickListener = new OnClickListener() {
        public void onClick(View v) {
            EditQuoteActivity.this.finish();
        }
    };
    ImageView cancel_edit;
    OnClickListener doneCLickListener = new OnClickListener() {
        public void onClick(View v) {
            EditQuoteActivity.this.hidesoftinput();
            String edited_quote = EditQuoteActivity.this.quote_edittext.getText().toString();
            if (edited_quote.isEmpty()) {
                EditQuoteActivity.this.quote_edittext.setError(EditQuoteActivity.this.getResources().getString(R.string.error_edit));
            }
            if (edited_quote.length() > 500) {
                EditQuoteActivity.this.quote_edittext.setError(EditQuoteActivity.this.getResources().getString(R.string.max_limit_quote));
            } else if (!EditQuoteActivity.this.isEdited) {
                EditQuoteActivity.this.finish();
            } else if (edited_quote.isEmpty()) {
                EditQuoteActivity.this.quote_edittext.setError(EditQuoteActivity.this.getResources().getString(R.string.error_edit));
            } else {
                Intent intent = new Intent();
                intent.putExtra("quote_edit", edited_quote);
                EditQuoteActivity.this.setResult(-1, intent);
                EditQuoteActivity.this.finish();
            }
        }
    };
    ImageView done_edit;
    private boolean isEdited = false;

    SharedPreferences preferences;
    EditText quote_edittext;
    String quotedText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_quotes);
        init();
        this.preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        this.quote_edittext.requestFocus();
        getWindow().setSoftInputMode(5);
    }

    private void init() {
        this.cancel_edit = (ImageView) findViewById(R.id.cancel_edit);
        this.done_edit = (ImageView) findViewById(R.id.done_edit);
        this.quote_edittext = (EditText) findViewById(R.id.quote_edit_text);
        this.done_edit.setOnClickListener(this.doneCLickListener);
        this.cancel_edit.setOnClickListener(this.cancelClickListener);
        this.quotedText = getIntent().getStringExtra("quote");
        if (!this.quotedText.equals(BuildConfig.FLAVOR)) {
            this.quote_edittext.setText(BuildConfig.FLAVOR + this.quotedText);
        }
        this.quote_edittext.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.equals(BuildConfig.FLAVOR)) {
                    EditQuoteActivity.this.isEdited = true;
                    if (s.length() >= 500) {
                        EditQuoteActivity.this.quote_edittext.setError(EditQuoteActivity.this.getResources().getString(R.string.max_limit_quote));
                    } else {
                        EditQuoteActivity.this.quote_edittext.setError(null);
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });
        ((RelativeLayout) findViewById(R.id.lay_touch)).setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                EditQuoteActivity.this.hidesoftinput();
                return false;
            }
        });
    }

    private void hidesoftinput() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    protected void onResume() {
        super.onResume();
        if (this.preferences.getBoolean("isAdsDisabled", false)) {

        }
    }

    protected void onPause() {
        super.onPause();
        if (this.preferences.getBoolean("isAdsDisabled", false)) {

        }
    }

    private boolean isNetworkAvailable() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}

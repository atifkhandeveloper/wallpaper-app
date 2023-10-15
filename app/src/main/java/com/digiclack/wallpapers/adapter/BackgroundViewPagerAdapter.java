package com.digiclack.wallpapers.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.legacy.app.FragmentPagerAdapter;

import com.digiclack.wallpapers.R;
import com.digiclack.wallpapers.fragment.FragmentBGList;
import com.digiclack.wallpapers.utility.Constants;

import java.util.ArrayList;

public class BackgroundViewPagerAdapter extends FragmentPagerAdapter {
    private String[] TITLES;
    private Context _context;
    int categoryPosition;
    String categoryQuote;
    ArrayList<Fragment> fragments = new ArrayList();
    String hasAuthor;
    String quote;

    public BackgroundViewPagerAdapter(Context context, FragmentManager fm, int categoryPosition, String quote, String hasAuthor, String categoryQuote) {
        super(fm);
        this._context = context;
        this.categoryPosition = categoryPosition;
        this.quote = quote;
        this.hasAuthor = hasAuthor;
        this.categoryQuote = categoryQuote;
        this.TITLES = context.getResources().getStringArray(R.array.listOfChoosePicItem);
        Fragment f = new Fragment();

            this.fragments.add(f);

    }

    public Fragment getItem(int position) {
        Fragment f = new FragmentBGList();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position + 1);
        bundle.putString("quote_edit", this.quote);
        bundle.putString("hasAuthor", this.hasAuthor);
        bundle.putString("categoryQuote", this.categoryQuote);
        bundle.putString("categoryBG", this.TITLES[position].toString());
        f.setArguments(bundle);
        this.fragments.set(position, f);
        return f;
    }

    public CharSequence getPageTitle(int position) {
        return this.TITLES[position];
    }

    @RequiresApi(api = 16)
    public View getTabView(int position) {
        View v = LayoutInflater.from(this._context).inflate(R.layout.custom_tab_lay, null);
        ImageView iv = (ImageView) v.findViewById(R.id.img_background);
        ((TextView) v.findViewById(R.id.tab_text)).setText(getPageTitle(position));
        if (position == this.categoryPosition) {
            v.setBackgroundColor(this._context.getResources().getColor(R.color.white));
        }
        iv.setBackground(this._context.getResources().getDrawable(this._context.getResources().getIdentifier(Constants.backgroundCataDrawable[position], "drawable", this._context.getPackageName())));
        return v;
    }

    public int getCount() {
        return this.TITLES.length;
    }

    public Fragment currentFragment(int position) {
        return (Fragment) this.fragments.get(position);
    }
}

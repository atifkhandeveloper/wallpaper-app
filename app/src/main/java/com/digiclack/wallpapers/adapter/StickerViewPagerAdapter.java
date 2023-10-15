package com.digiclack.wallpapers.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;

import androidx.legacy.app.FragmentPagerAdapter;

import com.digiclack.wallpapers.R;
import com.digiclack.wallpapers.fragment.FragmentSticker;

import java.util.ArrayList;

public class StickerViewPagerAdapter extends FragmentPagerAdapter {
    private String[] TITLES;
    private Context _context;
    int categoryPosition;
    ArrayList<Fragment> fragments = new ArrayList();

    public StickerViewPagerAdapter(Context context, FragmentManager fm, int categoryPosition) {
        super(fm);
        this._context = context;
        this.categoryPosition = categoryPosition;
        this.TITLES = context.getResources().getStringArray(R.array.sticker_category);
        Fragment f = new Fragment();
        for (int i = 0; i < this.TITLES.length; i++) {
            this.fragments.add(f);
        }
    }

    public Fragment getItem(int position) {
        Fragment f = new FragmentSticker();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position + 1);
        f.setArguments(bundle);
        this.fragments.set(position, f);
        return f;
    }

    public CharSequence getPageTitle(int position) {
        return this.TITLES[position];
    }

    public int getCount() {
        return this.TITLES.length;
    }

    public Fragment currentFragment(int position) {
        return (Fragment) this.fragments.get(position);
    }
}

package com.almoufasseralsaghir.pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.almoufasseralsaghir.R;

public class HelpFragmentAdapter extends FragmentPagerAdapter {
    protected static final int[] CONTENT = new int[] { 
    	R.drawable.help_page9, 
    	R.drawable.help_page8, 
    	R.drawable.help_page7, 
    	R.drawable.help_page6, 
    	R.drawable.help_page5,
    	R.drawable.help_page4,
    	R.drawable.help_page3,
    	R.drawable.help_page2,
    	R.drawable.help_page1};
   

    private int mCount = CONTENT.length;

    public HelpFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return HelpFragment.newInstance(CONTENT[position % CONTENT.length]);
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
    	return null;
//      return TestFragmentAdapter.CONTENT[position % CONTENT.length];
    }

    public void setCount(int count) {
        if (count > 0 && count <= 10) {
            mCount = count;
            notifyDataSetChanged();
        }
    }
}
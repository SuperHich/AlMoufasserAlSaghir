package com.example.almoufasseralsaghir;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.ImageView;

import com.almoufasseralsaghir.utils.ConfirmationDialog;
import com.almoufasseralsaghir.utils.IClickCustomListener;
import com.almoufasseralsaghir.utils.SanabilActivity;
import com.example.almoufasseralsaghir.pager.HelpFragmentAdapter;

public class HelpActivity extends SanabilActivity implements IClickCustomListener {

	private ConfirmationDialog exitDialog ;

	private HelpFragmentAdapter mAdapter;
	private ViewPager mPager;
	private ImageView icn1, icn2, icn3, icn4, icn5, icn6, icn7, icn8, icn9;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help);
		
		icn1 = (ImageView) findViewById(R.id.icn1);
		icn2 = (ImageView) findViewById(R.id.icn2);
		icn3 = (ImageView) findViewById(R.id.icn3);
		icn4 = (ImageView) findViewById(R.id.icn4);
		icn5 = (ImageView) findViewById(R.id.icn5);
		icn6 = (ImageView) findViewById(R.id.icn6);
		icn7 = (ImageView) findViewById(R.id.icn7);
		icn8 = (ImageView) findViewById(R.id.icn8);
		icn9 = (ImageView) findViewById(R.id.icn9);
		
		mAdapter = new HelpFragmentAdapter(getSupportFragmentManager());

        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);
        
        mPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				
				switch (arg0) {
				case 0:
					icn1.setImageResource(R.drawable.dot1);
					icn2.setImageResource(R.drawable.dot2);
					break;
					
				case 1:
					icn1.setImageResource(R.drawable.dot2);
					icn2.setImageResource(R.drawable.dot1);
					icn3.setImageResource(R.drawable.dot2);
					break;
					
				case 2:
					icn2.setImageResource(R.drawable.dot2);
					icn3.setImageResource(R.drawable.dot1);
					icn4.setImageResource(R.drawable.dot2);
					break;
					
				case 3:
					icn3.setImageResource(R.drawable.dot2);
					icn4.setImageResource(R.drawable.dot1);
					icn5.setImageResource(R.drawable.dot2);
					break;
					
				case 4:
					icn4.setImageResource(R.drawable.dot2);
					icn5.setImageResource(R.drawable.dot1);
					icn6.setImageResource(R.drawable.dot2);
					break;
					
				case 5:
					icn5.setImageResource(R.drawable.dot2);
					icn6.setImageResource(R.drawable.dot1);
					icn7.setImageResource(R.drawable.dot2);
					break;
					
				case 6:
					icn6.setImageResource(R.drawable.dot2);
					icn7.setImageResource(R.drawable.dot1);
					icn8.setImageResource(R.drawable.dot2);
					break;
					
				case 7:
					icn7.setImageResource(R.drawable.dot2);
					icn8.setImageResource(R.drawable.dot1);
					icn9.setImageResource(R.drawable.dot2);
					break;
					
				case 8:
					icn8.setImageResource(R.drawable.dot2);
					icn9.setImageResource(R.drawable.dot1);
					break;

				}
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {}
		});

//        mIndicator = (IconPageIndicator)findViewById(R.id.indicator);
//        mIndicator.setViewPager(mPager);
		
		
	}
	
	public void onBackPressed() {
		 exitDialog();
	}
	public  void exitDialog() {
			exitDialog = new ConfirmationDialog(this,
					R.style.CustomDialogTheme, 
					 this);
			exitDialog.setCancelable(false);
			exitDialog.show();
		}
	@Override
	public void onClickYes() {
		exitDialog.dismiss();
		finish();
	}
	@Override
	public void onClickNo() {
		exitDialog.dismiss();		
	}
	
}

package com.almoufasseralsaghir.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.almoufasseralsaghir.HomeLoggedIn;
import com.almoufasseralsaghir.MainActivity;
import com.almoufasseralsaghir.QuestionsActivity;
import com.almoufasseralsaghir.R;
import com.almoufasseralsaghir.RegistrationActivity;
import com.almoufasseralsaghir.SouraActivity;
import com.almoufasseralsaghir.database.AlMoufasserDB;
import com.almoufasseralsaghir.external.TafseerManager;

/**
 * AlMoufasserAlSaghir
 * @author HICHEM LAROUSSI - RAMI TRABELSI
 * Copyright (c) 2014 Zad Group. All rights reserved.
 *
 * This is Super Scaler
 *
 */

@SuppressLint("NewApi")
public class MySuperScaler extends FragmentActivity {
	
	private static final String TAG = MySuperScaler.class.getSimpleName();
	public static float scale ;
	public static boolean scaled = false;
	protected TafseerManager mTafseerManager ;
	protected AlMoufasserDB myDB;
	
	private Activity thisAct ;
	
	public static int screen_width;
	public static int screen_height;
	public static int my_font_size ;
	
	protected boolean isFirstStart = true;
	
	public static boolean isTablet ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		thisAct = this ;
		mTafseerManager = TafseerManager.getInstance(this);
		
		memoryAnalyser();
		
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2){   //API LEVEL 13
			display.getSize(size);
		}else{    
		    size.x = display.getWidth();
		    size.y = display.getHeight();
		}
		
		screen_width = size.x;
		screen_height = size.y;
		
		isTablet = isTablet(thisAct);
		
		Log.e("SCREEN WIDTH *****", String.valueOf(screen_width));
		Log.e("SCREEN Height *****", String.valueOf(screen_height));
		Log.e("SCALE *****", String.valueOf(scale));
		
		
		
		
		if (tabletInchSize()> 8.5 ) my_font_size = 14 ;
		
		else if (tabletInchSize()>= 6 && tabletInchSize()<= 7.5) my_font_size =  10;
		
		else my_font_size = 9 ;
		
		
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		myDB = new AlMoufasserDB(this);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		memoryAnalyser();
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		
		super.onWindowFocusChanged(hasFocus);
		View container=getWindow().getDecorView().findViewById(android.R.id.content);

		if(container.getTag()==null){
			View rootView=((ViewGroup)container).getChildAt(0);
			float xScale = (float) container.getWidth() / rootView.getWidth();
			float yScale = (float) container.getHeight() / rootView.getHeight();
			 scale = Math.min(xScale, yScale);
			scaleViewAndChildren(rootView, scale);
			
			
			
			
			if(! (thisAct instanceof QuestionsActivity))
			{
			RelativeLayout souraLayout = (RelativeLayout) findViewById(R.id.principal_layout);
	//		souraLayout.getLayoutParams().width = screen_width ;
			souraLayout.getLayoutParams().height = screen_height ;
			}
			container.setTag("IsScaled");
			scaled = true ;
			
			Log.e("SCALEEEEED", Boolean.toString(scaled));
		}

	}

	public static void scaleViewAndChildren(View root, float scale) {
		ViewGroup.LayoutParams layoutParams = root.getLayoutParams();

		if (layoutParams.width != ViewGroup.LayoutParams.MATCH_PARENT
				&& layoutParams.width != ViewGroup.LayoutParams.WRAP_CONTENT) {
			layoutParams.width *= scale;
		}
		if (layoutParams.height != ViewGroup.LayoutParams.MATCH_PARENT
				&& layoutParams.height != ViewGroup.LayoutParams.WRAP_CONTENT) {
			layoutParams.height *= scale;
		}

		if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
			ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) layoutParams;
			marginParams.leftMargin *= scale;
			marginParams.rightMargin *= scale;
			marginParams.topMargin *= scale;
			marginParams.bottomMargin *= scale;
		}

		root.setLayoutParams(layoutParams);

		root.setPadding((int) (root.getPaddingLeft() * scale),
				(int) (root.getPaddingTop() * scale),
				(int) (root.getPaddingRight() * scale),
				(int) (root.getPaddingBottom() * scale));

		if (root instanceof TextView) {
			TextView textView = (TextView) root;
			textView.setTextSize(textView.getTextSize() * scale);
	//		 Object myfontsize = (Titanium.Platform.displayCaps.platformHeight * 3) / 100;
		}
		

		if (root instanceof ViewGroup) {
			ViewGroup groupView = (ViewGroup) root;
			for (int cnt = 0; cnt < groupView.getChildCount(); ++cnt)
				scaleViewAndChildren(groupView.getChildAt(cnt), scale);
		}
	}

//	protected Drawable createLargeDrawable(int resId) throws IOException {
//		
//		 int MAX_SIZE = (int) ((screen_width + screen_height)/scale);
//		
////		int MAX_SIZE = 5000 ;
//		Log.i("MAX SIZE VALUE", String.valueOf(MAX_SIZE));
//		 
//		 
//	    InputStream is = getResources().openRawResource(resId);
//	    BitmapRegionDecoder brd = BitmapRegionDecoder.newInstance(is, true);
//
//	    try {
//	        if (brd.getWidth() <= MAX_SIZE && brd.getHeight() <= MAX_SIZE) {
//	            return new BitmapDrawable(getResources(), is);
//	        }
//
//	        int rowCount = (int) Math.ceil((float) brd.getHeight() / (float) MAX_SIZE);
//	        int colCount = (int) Math.ceil((float) brd.getWidth() / (float) MAX_SIZE);
//
//	        BitmapDrawable[] drawables = new BitmapDrawable[rowCount * colCount];
//
//	        for (int i = 0; i < rowCount; i++) {
//
//	            int top = MAX_SIZE * i;
//	            int bottom = i == rowCount - 1 ? brd.getHeight() : top + MAX_SIZE;
//
//	            for (int j = 0; j < colCount; j++) {
//
//	                int left = MAX_SIZE * j;
//	                int right = j == colCount - 1 ? brd.getWidth() : left + MAX_SIZE;
//
//	                Bitmap b = brd.decodeRegion(new Rect(left, top, right, bottom), null);
//	                BitmapDrawable bd = new BitmapDrawable(getResources(), b);
//	                bd.setGravity(Gravity.TOP | Gravity.LEFT);
//	                drawables[i * colCount + j] = bd;
//	            }
//	        }
//
//	        LayerDrawable ld = new LayerDrawable(drawables);
//	        for (int i = 0; i < rowCount; i++) {
//	            for (int j = 0; j < colCount; j++) {
//	                ld.setLayerInset(i * colCount + j, MAX_SIZE * j, MAX_SIZE * i, 0, 0);
//	            }
//	        }
//
//	        return ld;
//	    }
//	    finally {
//	        brd.recycle();
//	    }
//	}
//	
//	protected Drawable createLargeDrawable2(String pathName, int width, int height) {
//
//		int MAX_SIZE = (int) ((screen_width + screen_height)/scale);
//
//		//		int MAX_SIZE = 5000 ;
//		Log.i("MAX SIZE VALUE", String.valueOf(MAX_SIZE));
//
//		try {
//			//	    InputStream is = getResources().openRawResource(resId);
//			BitmapRegionDecoder brd = BitmapRegionDecoder.newInstance(pathName, true);
//
//			try {
//				if (brd.getWidth() <= MAX_SIZE && brd.getHeight() <= MAX_SIZE) {
//					BitmapFactory.Options opts =new BitmapFactory.Options();
//					opts.outWidth = width;
//					opts.outHeight = height;
//					Bitmap bm = BitmapFactory.decodeFile(pathName, opts);
//					return new BitmapDrawable(getResources(), bm);
//				}
//
//				int rowCount = (int) Math.ceil((float) brd.getHeight() / (float) MAX_SIZE);
//				int colCount = (int) Math.ceil((float) brd.getWidth() / (float) MAX_SIZE);
//
//				BitmapDrawable[] drawables = new BitmapDrawable[rowCount * colCount];
//
//				for (int i = 0; i < rowCount; i++) {
//
//					int top = MAX_SIZE * i;
//					int bottom = i == rowCount - 1 ? brd.getHeight() : top + MAX_SIZE;
//
//					for (int j = 0; j < colCount; j++) {
//
//						int left = MAX_SIZE * j;
//						int right = j == colCount - 1 ? brd.getWidth() : left + MAX_SIZE;
//
//						Bitmap b = brd.decodeRegion(new Rect(left, top, right, bottom), null);
//						BitmapDrawable bd = new BitmapDrawable(getResources(), b);
//						bd.setGravity(Gravity.TOP | Gravity.LEFT);
//						drawables[i * colCount + j] = bd;
//					}
//				}
//
//				LayerDrawable ld = new LayerDrawable(drawables);
//				for (int i = 0; i < rowCount; i++) {
//					for (int j = 0; j < colCount; j++) {
//						ld.setLayerInset(i * colCount + j, MAX_SIZE * j, MAX_SIZE * i, 0, 0);
//					}
//				}
//
//				return ld;
//
//
//			}
//			finally {
//				brd.recycle();
//			}
//
//		}catch(IOException ex){
//			ex.printStackTrace();
//		};
//
//		return null;
//
//	}

	public void onBackPressed() {
//		super.onBackPressed();
		
		if(thisAct instanceof SouraActivity)
		{
			startActivity(new Intent(thisAct, HomeLoggedIn.class));
//			Utils.animateFad(thisAct);
			finish();	
		}
		else if(thisAct instanceof HomeLoggedIn || thisAct instanceof RegistrationActivity)
		{
			MainActivity.first_entry = false;
			startActivity(new Intent(thisAct, MainActivity.class));
//			Utils.animateFad(thisAct);
			finish();
		}
		else finish();
		
		
	}
	
	public static boolean isTablet(Context context) {
	    return (context.getResources().getConfiguration().screenLayout
	            & Configuration.SCREENLAYOUT_SIZE_MASK)
	            >= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}
	
	
	@Override
	protected void onStop() {
		super.onStop();
		
		myDB.close();
	}
	
    protected double tabletInchSize(){
		
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		float widthInInches = metrics.widthPixels / metrics.xdpi;
		float heightInInches = metrics.heightPixels / metrics.ydpi;
		
		double sizeInInches = Math.sqrt(Math.pow(widthInInches, 2) + Math.pow(heightInInches, 2));
		//0.5" buffer for 7" devices
	//	boolean is7inchTablet = sizeInInches >= 6.5 && sizeInInches <= 7.5; 
		
		Log.e("//////////////// SIZE IN INCH ////////////////", String.valueOf(sizeInInches));
		
		return sizeInInches ;
	}
	
    protected void hideKeyboard() {
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
	}
    
	public void memoryAnalyser(){
		
		Log.i(TAG,"... Memory Analyser check test ");
		Runtime r = Runtime.getRuntime();
		long mem0 = r.totalMemory();
		Log.v(TAG,"Total memory is: " + (int)(mem0 / (1024*1024)) + " MB"); 
		long mem1 = r.freeMemory();
		Log.v(TAG,"Initial free memory: " + (int)(mem1 / (1024*1024)) + " MB");
		long mem2 = r.maxMemory();
		Log.v(TAG,"Max memory: " + (int)(mem2 / (1024*1024)) + " MB");

		Log.v(TAG,"Memory usage : " + (int)((mem0*100)/mem2) + " %");
		
		Log.v(TAG,"Memory allocated : " + (int)((mem0 - mem1) / (1024*1024)) + " MB");
		
		long mem_alloc = Debug.getNativeHeapAllocatedSize();
		Log.v(TAG,"Native Heap memory Allocated : " + (int)(mem_alloc / (1024*1024)) + " MB");
	}
}

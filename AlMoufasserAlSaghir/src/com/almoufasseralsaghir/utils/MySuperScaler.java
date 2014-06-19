package com.almoufasseralsaghir.utils;

import java.io.IOException;
import java.io.InputStream;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.almoufasseralsaghir.HomeLoggedIn;
import com.almoufasseralsaghir.MainActivity;
import com.almoufasseralsaghir.QuestionsActivity;
import com.almoufasseralsaghir.R;
import com.almoufasseralsaghir.SouraActivity;
import com.almoufasseralsaghir.database.AlMoufasserDB;
import com.almoufasseralsaghir.external.TafseerManager;

/**
 * This is Super Scaler
 *
 */
@SuppressLint("NewApi")
public class MySuperScaler extends FragmentActivity {
	
	public static float scale ;
	public static boolean scaled = false;
	protected TafseerManager mTafseerManager ;
	protected AlMoufasserDB myDB;
	
	private Activity thisAct ;
	
	public static int screen_width;
	public static int screen_height;
	
	protected boolean isFirstStart = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		thisAct = this ;
		mTafseerManager = TafseerManager.getInstance(this);
		
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		myDB = new AlMoufasserDB(this);
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
			
			
			Display display = getWindowManager().getDefaultDisplay();
			Point size = new Point();
			display.getSize(size);
			screen_width = size.x;
			screen_height = size.y;
			
			
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

	protected Drawable createLargeDrawable(int resId) throws IOException {
		
		 int MAX_SIZE = (int) ((screen_width + screen_height)/scale);
		
	//	int MAX_SIZE = 3000 ;
		Log.i("MAX SIZE VALUE", String.valueOf(MAX_SIZE));
		 
		 
	    InputStream is = getResources().openRawResource(resId);
	    BitmapRegionDecoder brd = BitmapRegionDecoder.newInstance(is, true);

	    try {
	        if (brd.getWidth() <= MAX_SIZE && brd.getHeight() <= MAX_SIZE) {
	            return new BitmapDrawable(getResources(), is);
	        }

	        int rowCount = (int) Math.ceil((float) brd.getHeight() / (float) MAX_SIZE);
	        int colCount = (int) Math.ceil((float) brd.getWidth() / (float) MAX_SIZE);

	        BitmapDrawable[] drawables = new BitmapDrawable[rowCount * colCount];

	        for (int i = 0; i < rowCount; i++) {

	            int top = MAX_SIZE * i;
	            int bottom = i == rowCount - 1 ? brd.getHeight() : top + MAX_SIZE;

	            for (int j = 0; j < colCount; j++) {

	                int left = MAX_SIZE * j;
	                int right = j == colCount - 1 ? brd.getWidth() : left + MAX_SIZE;

	                Bitmap b = brd.decodeRegion(new Rect(left, top, right, bottom), null);
	                BitmapDrawable bd = new BitmapDrawable(getResources(), b);
	                bd.setGravity(Gravity.TOP | Gravity.LEFT);
	                drawables[i * colCount + j] = bd;
	            }
	        }

	        LayerDrawable ld = new LayerDrawable(drawables);
	        for (int i = 0; i < rowCount; i++) {
	            for (int j = 0; j < colCount; j++) {
	                ld.setLayerInset(i * colCount + j, MAX_SIZE * j, MAX_SIZE * i, 0, 0);
	            }
	        }

	        return ld;
	    }
	    finally {
	        brd.recycle();
	    }
	}
	
	public void onBackPressed() {
//		super.onBackPressed();
		
		if(thisAct instanceof SouraActivity)
		{
			startActivity(new Intent(thisAct, HomeLoggedIn.class));
//			Utils.animateFad(thisAct);
			finish();	
		}
		else if(thisAct instanceof HomeLoggedIn)
		{
			MainActivity.first_entry = false;
			startActivity(new Intent(thisAct, MainActivity.class));
//			Utils.animateFad(thisAct);
			finish();
		}
		else finish();
		
		
	}
	
	
	
	
	@Override
	protected void onStop() {
		super.onStop();
		
		myDB.close();
	}
}

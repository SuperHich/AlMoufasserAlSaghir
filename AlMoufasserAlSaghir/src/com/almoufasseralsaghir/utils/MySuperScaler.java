package com.almoufasseralsaghir.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.almoufasseralsaghir.external.TafseerManager;
import com.example.almoufasseralsaghir.HomeLoggedIn;
import com.example.almoufasseralsaghir.MainActivity;
import com.example.almoufasseralsaghir.SouraActivity;
import com.example.almoufasseralsaghir.database.AlMoufasserDB;

/**
 * This is Super Scaler
 *
 */
public class MySuperScaler extends FragmentActivity {
	
	public static float scale ;
	public static boolean scaled = false;
	protected TafseerManager mTafseerManager ;
	protected AlMoufasserDB myDB;
	
	private Activity thisAct ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		thisAct = this ;
		mTafseerManager = TafseerManager.getInstance(this);
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
	
	

	public void onBackPressed() {
		super.onBackPressed();
		
		if(thisAct instanceof SouraActivity)
		{
			startActivity(new Intent(thisAct, HomeLoggedIn.class));
			Utils.animateFad(thisAct);
			finish();	
		}
		else if(thisAct instanceof HomeLoggedIn)
		{
			MainActivity.first_entry = false;
			startActivity(new Intent(thisAct, MainActivity.class));
			Utils.animateFad(thisAct);
			finish();
		}
		else finish();
		
		
	}
}

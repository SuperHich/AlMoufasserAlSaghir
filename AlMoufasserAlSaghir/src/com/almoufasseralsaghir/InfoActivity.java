package com.almoufasseralsaghir;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.almoufasseralsaghir.utils.MySuperScaler;
import com.almoufasseralsaghir.utils.Utils;
import com.example.almoufasseralsaghir.R;

public class InfoActivity extends MySuperScaler  {

	private RelativeLayout principal_layout;
	private Button previous, more_apps, help ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info_activity);
		
		principal_layout = (RelativeLayout) findViewById(R.id.principal_layout);
		
		previous = (Button) findViewById(R.id.previous);
		more_apps = (Button) findViewById(R.id.more_apps);
		help = (Button) findViewById(R.id.help);
		
		previous.setOnTouchListener(new OnTouchListener() {
			
			@Override
		    public boolean onTouch(View v, MotionEvent event) {
		      switch (event.getAction()) {
		      case MotionEvent.ACTION_DOWN: {
		          Button view = (Button) v;
		          view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
		          v.invalidate();
		          break;
		      }
		      case MotionEvent.ACTION_UP: {
		    	// Your action here on button click
					finish();
		      }
		      case MotionEvent.ACTION_CANCEL: {
		          Button view = (Button) v;
		          view.getBackground().clearColorFilter();
		          view.invalidate();
		          break;
		      }
		      }
		      return true;
		    }
		});
		
		more_apps.setOnTouchListener(new OnTouchListener() {
			
			@Override
		    public boolean onTouch(View v, MotionEvent event) {
		      switch (event.getAction()) {
		      case MotionEvent.ACTION_DOWN: {
		          Button view = (Button) v;
		          view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
		          v.invalidate();
		          break;
		      }
		      case MotionEvent.ACTION_UP: {
		    	  startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://alnour.ws/zadapp/")));  
		      
		      }
		      case MotionEvent.ACTION_CANCEL: {
		          Button view = (Button) v;
		          view.getBackground().clearColorFilter();
		          view.invalidate();
		          break;
		      }
		      }
		      return true;
		    }
		});
		
		
		help.setOnTouchListener(new OnTouchListener() {
			
			@Override
		    public boolean onTouch(View v, MotionEvent event) {
		      switch (event.getAction()) {
		      case MotionEvent.ACTION_DOWN: {
		          Button view = (Button) v;
		          view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
		          v.invalidate();
		          break;
		      }
		      case MotionEvent.ACTION_UP: {
		    	  
		    	  startActivity(new Intent(InfoActivity.this, HelpActivity.class));
		      }
		      case MotionEvent.ACTION_CANCEL: {
		          Button view = (Button) v;
		          view.getBackground().clearColorFilter();
		          view.invalidate();
		          break;
		      }
		      }
		      return true;
		    }
		});
		
		
		
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		Utils.cleanViews(principal_layout);
	}
	
}

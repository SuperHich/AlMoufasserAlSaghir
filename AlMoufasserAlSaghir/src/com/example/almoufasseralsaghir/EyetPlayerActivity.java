package com.example.almoufasseralsaghir;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.almoufasseralsaghir.utils.FontFitTextView;
import com.almoufasseralsaghir.utils.MySuperScaler;
import com.almoufasseralsaghir.utils.Utils;
import com.evgenii.jsevaluator.JsEvaluator;
import com.evgenii.jsevaluator.interfaces.JsCallback;

@SuppressLint("SetJavaScriptEnabled")
public class EyetPlayerActivity extends MySuperScaler  {

	private Button info, favourites, previous, home ;
	private Button repeat_eya, set_favourite, play_eya, next_eya, previous_eya ;
	private WebView eyet_webview ;
	FontFitTextView eya_repetitions ;
	int repetitions_sura_nbr = 1 ;
	
	private int suraId, partNb;
	
	private String partText;
	private int playingCounter = 1;
	private WebViewClient client;
	
	public static int repetitions_aya_nbr = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.eyet_player_screen);
		
		if(getIntent().getExtras() != null)
		{
			suraId = getIntent().getExtras().getInt("suraId");
			partNb = getIntent().getExtras().getInt("partNb");
			
			myDB.populatePartText(suraId, partNb);
			partText = mTafseerManager.getPartText();
			
		}
		
		eyet_webview = (WebView) findViewById(R.id.eyet_webview);
		eyet_webview.getSettings().setJavaScriptEnabled(true);
		
		StringBuilder myFonts = new StringBuilder();
		for(String page : mTafseerManager.getCurrentlyUsedFonts()){
			myFonts.append("@font-face{font-family: P"+page+";src: url('FONTS/QCF_P"+page+".TTF')}");
		}
		
		String style = "<head><script type='text/javascript' src='JS/jquery-1.10.2.min.js'></script><style type=\"text/css\">"+myFonts+"@font-face{font-family: myFirstFontB; src: url('FONTS/QCF_BSML.TTF')}.sora, .bsmla{font-family:myFirstFontB;} .sora{ width: 100% ; margin-top: 8px; background-size: 100% 51px; background-repeat: no-repeat; }.bsmla{ margin-top: -5px; display:block; text-align: center; } body{width : 100% !important; font-size: 56px;line-height:85px; margin: 0px; direction: rtl; background-color: blue|||; text-align: right;  } body a{ color: black; text-decoration: none; border:0 solid; border-radius:35px; padding: -15px 0; }</style></head>";
		String htmlPart = "<html>"+style+"<body><div style='padding-right: 20px; margin:0 0px 0 0px !important; background-color: red|||; width: 90%'>"+partText+"</div></body></html>";
		
		Log.i("EyetPlayerActivity", htmlPart);
		eyet_webview.loadDataWithBaseURL("file:///android_asset/", htmlPart, "text/html", "UTF-8", null);
		
		client = new WebViewClient(){ 
			
	        @Override 
	        public boolean shouldOverrideUrlLoading(WebView view, String url) { 
	        	
	        	AyaDialog dialog = new AyaDialog(EyetPlayerActivity.this);
	        	dialog.show();
	        	Log.e("webView Clicked ", url);
	        	HighLightPlayingAya(playingCounter);
	        	return true;
	        } 
	    }; 
	    
	    eyet_webview.setWebViewClient(client);
		
		info = (Button) findViewById(R.id.e6_info);
		favourites = (Button) findViewById(R.id.e6_favourites);
		previous = (Button) findViewById(R.id.e6_previous);
		home = (Button) findViewById(R.id.e6_home);
		
		repeat_eya = (Button) findViewById(R.id.repeat_eya);
		set_favourite = (Button) findViewById(R.id.select_favourite);
		play_eya = (Button) findViewById(R.id.play_eya);
		next_eya = (Button) findViewById(R.id.next_eya);
		previous_eya = (Button) findViewById(R.id.previous_eya);
		
		eya_repetitions = (FontFitTextView) findViewById(R.id.eya_repetitions);
		
		info.bringToFront();
		favourites.bringToFront();
		previous.bringToFront();
		home.bringToFront();
		
		repeat_eya.bringToFront();
		set_favourite.bringToFront();
		play_eya.bringToFront();
		next_eya.bringToFront();
		previous_eya.bringToFront();

		eya_repetitions.setText(String.valueOf(repetitions_sura_nbr));
		eya_repetitions.bringToFront();
		
		boolean isFav = myDB.isPartFavorite(suraId, partNb);
		toggleFavourite(!isFav);

		play_eya.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				
			}
		});
		
		
		
		
		repeat_eya.setOnTouchListener(new OnTouchListener() {
			
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
		    	  if (repetitions_sura_nbr < 3) repetitions_sura_nbr++ ;
	  		      else repetitions_sura_nbr = 1 ;
		    	  
		    	  eya_repetitions.setText(String.valueOf(repetitions_sura_nbr));

		    	  
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
		set_favourite.getBackground().clearColorFilter();
		set_favourite.setOnTouchListener(new OnTouchListener() {
			
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
		    	  boolean isFav = myDB.isPartFavorite(suraId, partNb);
		    	  if(isFav){
		    		  myDB.removeFromPartFavorite(suraId, partNb);
		    	  }
		    	  else{
		    		  myDB.addToPartFavorite(suraId, partNb);
		    	  }
		    	  
		    	  toggleFavourite(isFav);
		    	  

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
		
		
//		play_eya.setOnTouchListener(new OnTouchListener() {
//			
//			@Override
//		    public boolean onTouch(View v, MotionEvent event) {
//		      switch (event.getAction()) {
//		      case MotionEvent.ACTION_DOWN: {
//		          Button view = (Button) v;
//		          view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
//		          v.invalidate();
//		          break;
//		      }
//		      case MotionEvent.ACTION_UP: {
//		    	// Your action here on button click
//		      }
//		      case MotionEvent.ACTION_CANCEL: {
//		          Button view = (Button) v;
//		          view.getBackground().clearColorFilter();
//		          view.invalidate();
//		          break;
//		      }
//		      }
//		      return true;
//		    }
//		});
		
		
		next_eya.setOnTouchListener(new OnTouchListener() {
			
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
		    	  if(playingCounter++ <= mTafseerManager.getNumberOfTracks())
		    	  {
		    		  	playingCounter += 1;
		    	  		HighLightPlayingAya(playingCounter);
		    	  }
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
		
		
		previous_eya.setOnTouchListener(new OnTouchListener() {
			
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
		    	  
		    	  if(playingCounter-- >= 0){
		    		  playingCounter -= 1;  
		    		  HighLightPlayingAya(playingCounter);
		    	  }
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

		info.setOnTouchListener(new OnTouchListener() {
			
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
		    	  startActivity(new Intent(EyetPlayerActivity.this, InfoActivity.class));
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
		
		favourites.setOnTouchListener(new OnTouchListener() {
			
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
		    	  final Dialog dialog = new FavouriteDialog(EyetPlayerActivity.this);
		    	  dialog.show();
				
		     
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
		
		home.setOnTouchListener(new OnTouchListener() {
			
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
		    	   if (myDB.whoIsLoggedIn().isLoggedIn()) MainActivity.first_entry = false ;
		    	   SouraActivity.soura_act.finish();
					startActivity(new Intent(EyetPlayerActivity.this, MainActivity.class));
					Utils.animateFad(EyetPlayerActivity.this);
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
		
	}
	
	private void toggleFavourite(boolean isFav){
	   	  if(isFav){
	   		  set_favourite.setBackgroundResource(R.drawable.eya_dialog_favourite_inactive);
	   	  }
	   	  else{
	   		  set_favourite.setBackgroundResource(R.drawable.eya_dialog_favourite_active);
	   	  }
	}
	
	private void HighLightPlayingAya(int id){
		
		JsEvaluator jsEvaluator = new JsEvaluator(this);
		jsEvaluator.evaluate("$('a').css('background-color','');", new JsCallback() {
			
			@Override
			public void onResult(String arg0) {
				Log.e("LogName", arg0);
			}
		});
		
		jsEvaluator.evaluate("$('."+id+"').css('background','rgba(153, 198, 215, .3)');", new JsCallback() {
			
			@Override
			public void onResult(String arg0) {
				Log.e("LogName", arg0);
			}
		});
		
		
//		eyet_webview.addJavascriptInterface(new JavaScriptInterface(), "myObj");
//		jsEvaluator.callFunction(new JsCallback() {
//			  @Override
//			  public void onResult(final String result) {
//			    // get result here
//			  }
//			}, "functionName", "parameter 1", "parameter 2", 912, 101.3);
		
//	    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//	        // In KitKat+ you should use the evaluateJavascript method
//
//	    	eyet_webview.evaluateJavascript(jsString, new ValueCallback<String>() {
//	    		@Override
//	    		public void onReceiveValue(String s) {
//	    			Log.d("LogName", s); // Prints: "this"
//	    		}
//	    	});
//	    	jsString = "$('."+id+"').css('background','rgba(153, 198, 215, .3)');";
//	    	eyet_webview.evaluateJavascript(jsString, new ValueCallback<String>() {
//	    		@Override
//	    		public void onReceiveValue(String s) {
//	    			Log.d("LogName", s); // Prints: "this"
//	    		}
//	    	});
//	    }
	}
	
	public class JavaScriptInterface {
	    public void returnResult(String result) {
	        // result from JavaScript
	    }
	}
	

}

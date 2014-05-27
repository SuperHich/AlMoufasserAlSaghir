package com.example.almoufasseralsaghir;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.almoufasseralsaghir.utils.FontFitTextView;
import com.almoufasseralsaghir.utils.MySuperScaler;

public class EyetPlayerActivity extends MySuperScaler{

	private Button info, favourites, previous, home ;
	private Button repeat_eya, set_favourite, play_eya, next_eya, previous_eya ;
	private WebView eyet_webview ;
	FontFitTextView eya_repetitions ;
	int repetitions_nbr = 1 ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.eyet_player_screen);
		
		eyet_webview = (WebView) findViewById(R.id.eyet_webview);
		
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

		eya_repetitions.setText(String.valueOf(repetitions_nbr));
		eya_repetitions.bringToFront();
		
		play_eya.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				final Dialog dialog = new Dialog(EyetPlayerActivity.this);
		    	  dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); 
		    	  dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		    	  dialog.setContentView(R.layout.eya_dialog);
		    	  
		    	  RelativeLayout popup_view = (RelativeLayout) dialog.findViewById(R.id.eya_layout);
		    	  popup_view.getLayoutParams().width = 1422;
		    	  popup_view.getLayoutParams().height = 800;
		    	  MySuperScaler.scaleViewAndChildren(popup_view, MySuperScaler.scale);
				
		    	  Button eya_dialog_previous = (Button) dialog.findViewById(R.id.eya_dialog_previous);
		    	  
		    	  final Button eya_dialog_network = (Button) dialog.findViewById(R.id.eya_network);
		    	  final Button eya_dialog_copy = (Button) dialog.findViewById(R.id.eya_copy);
		    	  final Button eya_dialog_fav = (Button) dialog.findViewById(R.id.eya_favourite);
		    	  final Button eya_dialog_listen = (Button) dialog.findViewById(R.id.eya_listen);
		    	  final Button eya_dialog_maana = (Button) dialog.findViewById(R.id.eya_maana);
		    	  final Button eya_dialog_mofradat = (Button) dialog.findViewById(R.id.eya_mofradat);

		    	  final FontFitTextView eya_dialog_repetitions = (FontFitTextView) dialog.findViewById(R.id.eya_dialog_repetitions);
		    	  
		    	  eya_dialog_repetitions.setText(String.valueOf(repetitions_nbr));
		    	  
		    	  eya_dialog_network.setOnTouchListener(new OnTouchListener() {
		  			
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
		    	  
		    	  
		    	  eya_dialog_copy.setOnTouchListener(new OnTouchListener() {
		  			
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
		    	  
		    	  eya_dialog_fav.getBackground().clearColorFilter();
		    	  eya_dialog_fav.setOnTouchListener(new OnTouchListener() {
		  			
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
		  		    	  eya_dialog_fav.setBackgroundResource(R.drawable.eya_dialog_favourite_active);
		  		    	
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
		    	  
		    	  
		    	  eya_dialog_listen.setOnTouchListener(new OnTouchListener() {
		  			
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
		  		    	if (repetitions_nbr < 3) repetitions_nbr++ ;
		  		    	else repetitions_nbr = 1 ;
				    	  
		  		    	eya_dialog_repetitions.setText(String.valueOf(repetitions_nbr));

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
		    	  
		    	  
		    	  eya_dialog_maana.setOnTouchListener(new OnTouchListener() {
		  			
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
		    	  
		    	  
		    	  eya_dialog_mofradat.setOnTouchListener(new OnTouchListener() {
		  			
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
		    	  
		    	  eya_dialog_previous.setOnTouchListener(new OnTouchListener() {
		  			
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

		  		    	  dialog.dismiss();
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
				dialog.show();
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
		    	  if (repetitions_nbr < 3) repetitions_nbr++ ;
	  		      else repetitions_nbr = 1 ;
		    	  
		    	  eya_repetitions.setText(String.valueOf(repetitions_nbr));

		    	  
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
		    	  set_favourite.setBackgroundResource(R.drawable.eya_dialog_favourite_active);

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
}

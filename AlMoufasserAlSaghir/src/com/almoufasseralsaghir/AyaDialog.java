package com.almoufasseralsaghir;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.almoufasseralsaghir.database.AlMoufasserDB;
import com.almoufasseralsaghir.external.TafseerManager;
import com.almoufasseralsaghir.utils.FontFitTextView;
import com.almoufasseralsaghir.utils.MySuperScaler;

public class AyaDialog extends Dialog{

	private AlMoufasserDB myDB;
	
	ArrayList<String> new_data = null  ;
	
	private boolean isEntry = true;
	private boolean isMaana = false;
	private boolean isMofradat = false;
	
	private Context mcontext ;
	
	private Button eya_dialog_network, eya_dialog_previous , eya_dialog_mofradat , 
					eya_dialog_copy, eya_dialog_fav, eya_dialog_listen, eya_dialog_maana;
	public static Button eya_dialog_play;
	private RelativeLayout popup_view,all_buttons;
	public static FontFitTextView eya_dialog_repetitions;
	private WebView mofradat_webview, maana_webview ;
	private TafseerManager mTafseerManager;
	
	public AyaDialog(Context context) {
		 super(context);
		 this.mcontext = context ;
		  
		 myDB = new AlMoufasserDB(context);
		 mTafseerManager = TafseerManager.getInstance(mcontext);
		 
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		setContentView(R.layout.eya_dialog);

		popup_view = (RelativeLayout) findViewById(R.id.eya_layout);
		popup_view.getLayoutParams().width = 1422;
		popup_view.getLayoutParams().height = 800;
		MySuperScaler.scaleViewAndChildren(popup_view, MySuperScaler.scale);

		eya_dialog_previous = (Button) findViewById(R.id.eya_dialog_previous);

		all_buttons = (RelativeLayout) findViewById(R.id.eya_dialog_all_buttons);
		
		eya_dialog_network = (Button) findViewById(R.id.eya_network);
		eya_dialog_copy = (Button) findViewById(R.id.eya_copy);
		eya_dialog_fav = (Button) findViewById(R.id.eya_favourite);
		eya_dialog_listen = (Button) findViewById(R.id.eya_listen);
		eya_dialog_maana = (Button) findViewById(R.id.eya_maana);
		eya_dialog_mofradat = (Button) findViewById(R.id.eya_mofradat);
		eya_dialog_repetitions = (FontFitTextView) findViewById(R.id.eya_dialog_repetitions);

		eya_dialog_play = (Button) findViewById(R.id.dialog_play_eya);
		
		mofradat_webview = (WebView) findViewById(R.id.aya_mofradat_webview);
		maana_webview = (WebView) findViewById(R.id.aya_maana_webview);
		
		
		popup_view.setBackgroundResource(R.drawable.eya_dialog_bg) ;
		
		boolean isFav = myDB.isPartFavorite(EyetPlayerActivity.suraId, EyetPlayerActivity.partNb);
		toggleFavourite(!isFav);
		
		
		
		all_buttons.setVisibility(View.VISIBLE);
		mofradat_webview.setVisibility(View.GONE);
		maana_webview.setVisibility(View.GONE);
		
		mofradat_webview.setBackgroundColor(0x00000000);
		maana_webview.setBackgroundColor(0x00000000);
		
		eya_dialog_repetitions.setText(String.valueOf(EyetPlayerActivity.repetitions_aya_nbr));

		eya_dialog_network.setOnTouchListener(new OnTouchListener() {

			@SuppressWarnings("deprecation")
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
					
					String clipText = myDB.getAyaText(EyetPlayerActivity.suraId, EyetPlayerActivity.ayaID);
					
					int sdk = android.os.Build.VERSION.SDK_INT;
					if(sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
					    android.text.ClipboardManager clipboard = (android.text.ClipboardManager) mcontext.getSystemService(Context.CLIPBOARD_SERVICE);
					    clipboard.setText(clipText);
					} else {
					    android.content.ClipboardManager clipboard = (android.content.ClipboardManager) mcontext.getSystemService(Context.CLIPBOARD_SERVICE); 
					    android.content.ClipData clip = android.content.ClipData.newPlainText("text label",clipText);
					    clipboard.setPrimaryClip(clip);
					}
					
					
//					Intent sharingIntent = new Intent(Intent.ACTION_SEND);    
//			         sharingIntent.setType("text/html"); 
//			         sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml("<p>"+clipText+"</p>")); 
//			        mcontext.startActivity(Intent.createChooser(sharingIntent,"Share using"));
					
			        
			        String shareBody = clipText;
			        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
			        sharingIntent.setType("text/plain");
			        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
			        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
			        mcontext.startActivity(Intent.createChooser(sharingIntent,mcontext.getResources().getString(R.string.share)));
			        
			        
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

			@SuppressWarnings("deprecation")
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
					
					String clipText = myDB.getAyaText(EyetPlayerActivity.suraId, EyetPlayerActivity.ayaID);
					
					int sdk = android.os.Build.VERSION.SDK_INT;
					if(sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
					    android.text.ClipboardManager clipboard = (android.text.ClipboardManager) mcontext.getSystemService(Context.CLIPBOARD_SERVICE);
					    clipboard.setText(clipText);
					} else {
					    android.content.ClipboardManager clipboard = (android.content.ClipboardManager) mcontext.getSystemService(Context.CLIPBOARD_SERVICE); 
					    android.content.ClipData clip = android.content.ClipData.newPlainText("text label",clipText);
					    clipboard.setPrimaryClip(clip);
					}
					
					Toast.makeText(mcontext, mcontext.getResources().getText(R.string.copy_aya), Toast.LENGTH_SHORT).show();
					
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
					 boolean isFav = myDB.isPartFavorite(EyetPlayerActivity.suraId, EyetPlayerActivity.partNb);
			    	  if(isFav){
			    		  myDB.removeFromPartFavorite(EyetPlayerActivity.suraId, EyetPlayerActivity.partNb);
			    	  }
			    	  else{
			    		  myDB.addToPartFavorite(EyetPlayerActivity.suraId, EyetPlayerActivity.partNb);
			    	  }
			    	  
			    	  
			    	  toggleFavourite(isFav);
			    	 (( EyetPlayerActivity) mcontext).toggleFavourite(isFav);

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
					
					if (EyetPlayerActivity.repetitions_aya_nbr < 3) 
						EyetPlayerActivity.repetitions_aya_nbr++ ;
					else EyetPlayerActivity.repetitions_aya_nbr = 0 ;

					eya_dialog_repetitions.setText(String.valueOf(EyetPlayerActivity.repetitions_aya_nbr));

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
		
		eya_dialog_play.setOnTouchListener(new OnTouchListener() {

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
					
					((EyetPlayerActivity) mcontext).repetition_mode = true ;
					
				
					
					if (!((EyetPlayerActivity) mcontext).mPlayer.isPlaying()){
						eya_dialog_play.setBackgroundResource(R.drawable.pause_eya);	
						((EyetPlayerActivity) mcontext).prepareDialogPlayer(EyetPlayerActivity.trackID);

					} else {
						((EyetPlayerActivity) mcontext).mPlayer.stop();
						eya_dialog_play.setBackgroundResource(R.drawable.play_eya);
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
					
					popup_view.setBackgroundResource(R.drawable.e5_maana_bg) ;
					
					all_buttons.setVisibility(View.GONE);
					mofradat_webview.setVisibility(View.GONE);
					maana_webview.setVisibility(View.VISIBLE);
					
					isMofradat = false ;
					isEntry = false ;
					isMaana = true ;
					
					
					new AsyncTask<Void, String, String>() {

						@Override
						protected String doInBackground(Void... params) {

							String data = myDB.getAyaTafseer(EyetPlayerActivity.suraId, EyetPlayerActivity.ayaID);

							
							
							return data;
						}

						@Override
						protected void onPostExecute(String data) {
							String style = "<style type=\"text/css\">body {font-size: 27px;margin: 10px; direction: rtl;  text-align: justify; }</style>";
							
							Log.e("-----------My DATA ------", data);
									
							if(data != null)
							{	
								maana_webview.loadData("<html><head>"+style+"</head><body>"+data+"</body></html>", "text/html; charset=UTF-8", null);

							}else{ 
								
								mTafseerManager.showPopUp(mcontext, R.string.error_try_again);
							}
						}

					}.execute();
					
					
					
					
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
					
					popup_view.setBackgroundResource(R.drawable.mofradat_bg) ;
					
					all_buttons.setVisibility(View.GONE);
					mofradat_webview.setVisibility(View.VISIBLE);
					maana_webview.setVisibility(View.GONE);
					isMofradat = true ;
					isEntry = false ;
					isMaana = false ;
					
					new AsyncTask<Void, String, String>() {

						@Override
						protected String doInBackground(Void... params) {

							String mofdata = myDB.getAyaWordMeaning(EyetPlayerActivity.suraId, EyetPlayerActivity.ayaID);
							
							String[] old_data = mofdata.split("\\r?\\n");
							
							
							for (int i = 0; i < old_data.length; i++){
								old_data[i] =  " <br> "+ old_data[i] + " <br> ";
							}
							
							
							StringBuilder result = new StringBuilder();
							for (int i = 0; i < old_data.length; i++) {
								result.append(old_data[i]);
							}
							String my_data = result.toString();
						
							Log.e("-----------My DATA ------", my_data);
							
							
							return my_data;
						}

						@Override
						protected void onPostExecute(String my_data) {
							String style = "<style type=\"text/css\">body {font-size: 27px;margin: 10px; direction: rtl;  text-align: justify; }</style>";
		
									
							if(my_data != null)
							{	
								mofradat_webview.loadData("<html><head>"+style+"</head><body><div>"+my_data+"</div></body></html>", "text/html; charset=UTF-8", null);

							}else{ 
								
								mTafseerManager.showPopUp(mcontext, R.string.error_try_again);
							}
						}

					}.execute();
					
					
					
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
					if (isEntry) {
						dismiss();
					}
					else 
					
					if (isMofradat || isMaana){
						all_buttons.setVisibility(View.VISIBLE);
						mofradat_webview.setVisibility(View.GONE);
						maana_webview.setVisibility(View.GONE);
						isMofradat = false ;
						isEntry = true ;
						isMaana = false ;
						popup_view.setBackgroundResource(R.drawable.eya_dialog_bg) ;
						
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

	}
	
	@Override
	public void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		((EyetPlayerActivity) mcontext).mPlayer.stop();
		((EyetPlayerActivity) mcontext).repetition_mode = false ;
		
		myDB.close();
	}

	private void toggleFavourite(boolean isFav){
	   	  if(isFav){
	   		eya_dialog_fav.setBackgroundResource(R.drawable.eya_dialog_favourite_inactive);
	   	  }
	   	  else{
	   		eya_dialog_fav.setBackgroundResource(R.drawable.eya_dialog_favourite_active);
	   	  }
	}
}

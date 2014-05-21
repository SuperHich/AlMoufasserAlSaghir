package com.example.almoufasseralsaghir;

import org.json.JSONObject;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.almoufasseralsaghir.external.TafseerManager;
import com.almoufasseralsaghir.external.User;
import com.almoufasseralsaghir.utils.ConfirmationDialog;
import com.almoufasseralsaghir.utils.FontFitTextView;
import com.almoufasseralsaghir.utils.IClickCustomListener;
import com.almoufasseralsaghir.utils.ImageAdapter;
import com.almoufasseralsaghir.utils.SanabilActivity;
import com.almoufasseralsaghir.utils.Utils;
import com.example.almoufasseralsaghir.database.AlMoufasserDB;


public class MainActivity extends SanabilActivity  implements IClickCustomListener{

	private Button  register_enter, login_btn, new_user,
					deconnect, account_settings, settings;
	private RelativeLayout register_interface, logged_in_interface ;
	private EditText email_login ;
	private FontFitTextView name_logged_in ;
	private ListView listViewArticles;
	ImageView herbes, email_hint, welcomer ;
	private ConfirmationDialog exitDialog ;
	private TafseerManager tafseerManager;
	private MediaPlayer welcome_mp, welcome_mp2 ;
	
	int i = 0 ;
	public static boolean first_entry = true;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
	
	tafseerManager = TafseerManager.getInstance(this);
	
	herbes = (ImageView) findViewById(R.id.herbes);
	welcomer = (ImageView) findViewById(R.id.welcomer);
	
	register_enter = (Button) findViewById(R.id.register);
	
	register_interface = (RelativeLayout) findViewById(R.id.register_interface);
	login_btn = (Button) findViewById(R.id.login_btn);
	new_user = (Button) findViewById(R.id.new_user_btn);
	
	email_login = (EditText) findViewById(R.id.mail_login);
	email_hint = (ImageView) findViewById(R.id.hint_login);
	
	logged_in_interface = (RelativeLayout) findViewById(R.id.logged_in);
	deconnect = (Button) findViewById(R.id.deconnect);
	account_settings = (Button) findViewById(R.id.account_settings);
	settings = (Button) findViewById(R.id.settings);
	
	name_logged_in = (FontFitTextView) findViewById(R.id.welcome_logged_name);
	

//	name_logged_in.setText("Sedki Trimech");
//	name_logged_in.resizeText(600,200);
	
	name_logged_in.setText(TafseerManager.getSavedUser().getName());
	
	herbes.bringToFront();
	
	
	
///////////////  SOUND ANIMATION : GIF ALIKE /////////////////////////////////////////////////////////
	
	if (first_entry) {
		
		register_enter.setVisibility(View.VISIBLE);
		register_interface.setVisibility(View.INVISIBLE);
		logged_in_interface.setVisibility(View.INVISIBLE);
		
//	welcome_mp = new MediaPlayer();
//	welcome_mp = MediaPlayer.create(this, R.raw.welcome_msg_1);
//	welcome_mp.start();
	
	new CountDownTimer(4000, 60) {

	     public void onTick(long millisUntilFinished) {
	    	
	    	 if (i == 6) i = 0;
	    	 
	    	 switch (i)  {
	    	 case 0 : welcomer.setBackgroundResource(R.drawable.welcomer_1_modified);
	    		 break ;
	    	 case 1 : welcomer.setBackgroundResource(R.drawable.welcomer_2_modified);
	    		 break ;
	    	 case 2 : welcomer.setBackgroundResource(R.drawable.welcomer_4_modified);
	    		 break ;
	    	 case 3 : welcomer.setBackgroundResource(R.drawable.welcomer_4_modified);
	    		 break ;
	    	 case 4 : welcomer.setBackgroundResource(R.drawable.welcomer_3_modified);
   		 	 break ;
	    	 case 5 : welcomer.setBackgroundResource(R.drawable.welcomer_2_modified);
   		   	 break ;
	    	 }
	    	 i++ ;
	     }
	     public void onFinish() {
	    	 welcomer.setBackgroundResource(R.drawable.welcomer_2_modified);
	     }
	  }.start();
	
	
//	welcome_mp.setOnCompletionListener(new OnCompletionListener() {
//		@Override
//		public void onCompletion(MediaPlayer mp) {
//			mp.release();
//			welcome_mp = MediaPlayer.create(MainActivity.this, R.raw.welcome_msg_2);
//			welcome_mp.start();
//			i = 0 ;
//			new CountDownTimer(4000, 60) {
//
//			     public void onTick(long millisUntilFinished) {
//			    	
//			    	 if (i == 6) i = 0;
//			    	 
//			    	 switch (i)  {
//			    	 case 0 : welcomer.setBackgroundResource(R.drawable.welcomer_1_modified);
//			    		 break ;
//			    	 case 1 : welcomer.setBackgroundResource(R.drawable.welcomer_2_modified);
//			    		 break ;
//			    	 case 2 : welcomer.setBackgroundResource(R.drawable.welcomer_4_modified);
//			    		 break ;
//			    	 case 3 : welcomer.setBackgroundResource(R.drawable.welcomer_4_modified);
//			    		 break ;
//			    	 case 4 : welcomer.setBackgroundResource(R.drawable.welcomer_3_modified);
//		    		 	 break ;
//			    	 case 5 : welcomer.setBackgroundResource(R.drawable.welcomer_2_modified);
//		    		   	 break ;
//			    	 }
//			    	 i++ ;
//			     }
//
//			     public void onFinish() {
//			     welcomer.setBackgroundResource(R.drawable.welcomer);
//			     }
//			  }.start();
//			
//		}
//	});
	
	
	
	} else {
		welcomer.setBackgroundResource(R.drawable.welcomer);
		register_enter.setVisibility(View.INVISIBLE);
		register_interface.setVisibility(View.INVISIBLE);
		logged_in_interface.setVisibility(View.VISIBLE);
	}
	
///////////////FIRST HEADER VIEW : ENTER/////////////////////////////////////////////////////////
	
	register_enter.setOnTouchListener(new OnTouchListener() {
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
	    	  register_enter.setVisibility(View.GONE);
				register_interface.setVisibility(View.VISIBLE);
				register_interface.bringToFront();
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
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////
	
///////////////   SECOND HEADER VIEW : LOGIN    /////////////////////////////////////////////////////////
	
	
	email_login.setOnFocusChangeListener(new OnFocusChangeListener() {
		
		@Override
		public void onFocusChange(View v, boolean hasFocus) {

			if (hasFocus) email_hint.setVisibility(View.INVISIBLE);
			else 
				if(email_login.getText().toString().equals(""))
				email_hint.setVisibility(View.VISIBLE);
		}
	});
	
	
	login_btn.setOnTouchListener(new OnTouchListener() {
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
	    	  final ProgressDialog pd = new ProgressDialog(MainActivity.this);
	    	  
	    	  if (Utils.isOnline(getApplicationContext())){
				new AsyncTask<Void, JSONObject, JSONObject>() {

					@Override
					protected void onPreExecute() {
						register_interface.setVisibility(View.GONE);
			            pd.setCancelable(false);
			            pd.setIndeterminate(true);
			            pd.show();
					}
					
					@Override
					protected JSONObject doInBackground(Void... params) {
						
//						AlMoufasserDB db = new AlMoufasserDB(MainActivity.this);
//						Cursor suras = db.getSuras();
//						while (suras.moveToNext()) {
//					
//							Log.i(""," " + suras.getString(3));
//							
//						}
//						db.close();
						
						return tafseerManager.loginUser(email_login.getText().toString());
					}
					
					@Override
					protected void onPostExecute(JSONObject result) {
						pd.dismiss();
						if(result != null)
						{	
							User userLoggedIn = tafseerManager.parseUser(result);
							if(userLoggedIn != null){
								tafseerManager.saveUser(userLoggedIn);
								name_logged_in.setText(userLoggedIn.getName());
								logged_in_interface.setVisibility(View.VISIBLE);
								logged_in_interface.bringToFront();
							}
							
						}else{ 
							register_interface.setVisibility(View.VISIBLE);
							tafseerManager.showPopUp(MainActivity.this, R.string.error_try_again);
						}
					}
					
				}.execute();
	    	  } else {
	    		  tafseerManager.showPopUp(MainActivity.this, R.string.error_internet_connexion);
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
	
	
	
	new_user.setOnTouchListener(new OnTouchListener() {
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
				startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
	    		//		Utils.animateFad(RegistrationActivity.this);
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
/////////////////////////////////////////////////////////////////////////////////////////////////////////
	
///////////////   THIRD HEADER VIEW : LOGGED IN    /////////////////////////////////////////////////////////
	
		deconnect.setOnTouchListener(new OnTouchListener() {
		
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
	//    	  name_logged_in.setText("");
	//	    	  new AsyncTask<Void, Integer, Integer>() {
//		    		  
//					@Override
//					protected void onPreExecute() {
//						
//					}
//					
//					@Override
//					protected Integer doInBackground(Void... params) {
//						
//						User savedUser = tafseerManager.getSavedUser();
//						int uid 				= savedUser.getUid(); 
//						String udid 			= savedUser.getUdid();
//						String email  			= savedUser.getEmail();
//						
//						return tafseerManager.deleteUser(uid, udid, email);
//					}
//					
//					@Override
//					protected void onPostExecute(Integer result) {
//						if(result > 0)
//						{
//							name_logged_in.setText("");
//							logged_in_interface.setVisibility(View.GONE);
//							register_interface.setVisibility(View.GONE);
//							register_enter.setVisibility(View.VISIBLE);
//							register_enter.bringToFront();					
//						}else
//							tafseerManager.showPopUp(MainActivity.this, R.string.error_try_again);
//					}
//					
//				}.execute();
	    	  
	    	  logged_in_interface.setVisibility(View.GONE);
	    	  register_interface.setVisibility(View.GONE);
	    	  register_enter.setVisibility(View.VISIBLE);
	    	  register_enter.bringToFront();
	    	  
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
	
	
	account_settings.setOnTouchListener(new OnTouchListener() {
		
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
	    	  Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
	    	  intent.putExtra("update", true);
	    	  startActivity(intent);
	
				//		Utils.animateFad(RegistrationActivity.this);
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
	
		settings.setOnTouchListener(new OnTouchListener() {
		
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
	    	  final Dialog dialog = new Dialog(MainActivity.this);
	    	  dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); 
	    	  dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
	    	  dialog.setContentView(R.layout.main_dialog_settings);
	    	  dialog.setCancelable(true);
	    	  
	    	  RelativeLayout popup_view = (RelativeLayout) dialog.findViewById(R.id.popup_main);
	    	  popup_view.getLayoutParams().height = 521;
	    	  popup_view.getLayoutParams().width = 847;
	    	  SanabilActivity.scaleViewAndChildren(popup_view, SanabilActivity.scale);
	    	  
	    	  Button popup_confirm = (Button) dialog.findViewById(R.id.popup_confirm);
	    	  final Button popup_reader1 = (Button) dialog.findViewById(R.id.popup_active_btn);
	    	  final Button popup_reader2 = (Button) dialog.findViewById(R.id.popup_inactive_btn);
	    	  
	    	  popup_reader1.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

					popup_reader1.setBackgroundResource(R.drawable.popup_active_btn);
					popup_reader2.setBackgroundResource(R.drawable.popup_inactive_btn);
   
////////////////////// SET READER 2 IN MY APPLICATION //////////////////////////////////////////////////////////////////			
	
				}
	    	  });
	    	  
	    	  popup_reader2.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						
						popup_reader1.setBackgroundResource(R.drawable.popup_inactive_btn);
						popup_reader2.setBackgroundResource(R.drawable.popup_active_btn);
	
////////////////////// SET READER 2 IN MY APPLICATION //////////////////////////////////////////////////////////////////			
						
					}
		    	  });
	    	  
	    	  popup_confirm.setOnTouchListener(new OnTouchListener() {
	    			
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
	    
////////////////////// SET READER IN MYAPPLICATION //////////////////////////////////////////////////////////////////
	    		    	  
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
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////

	
///////////////   LIST VIEW : HANDLING    /////////////////////////////////////////////////////////
	
	
	Integer[] lListIcone={R.drawable.list_4 , R.drawable.list_3 ,R.drawable.list_2 ,R.drawable.list_1 };
	ArrayAdapter<Integer> adapter = new ImageAdapter(this, R.layout.rowlv_module, lListIcone);

	listViewArticles = (ListView) findViewById(R.id.listView1);
	listViewArticles.setAdapter(adapter);
	listViewArticles.setDivider(null);

	listViewArticles.setOnItemClickListener(new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
			switch (position) {
			case 0:
				Intent i1 = new Intent(MainActivity.this, HomeLoggedIn.class);
				i1.putExtra("part", "1");
				startActivity(i1);
				Utils.animateSlide(MainActivity.this);
				
				break;
			case 1:
				Intent i2 = new Intent(MainActivity.this, HomeLoggedIn.class);
				i2.putExtra("part", "2");
				startActivity(i2);
				Utils.animateSlide(MainActivity.this);
				break;
			case 2:
				Intent i3 = new Intent(MainActivity.this, HomeLoggedIn.class);
				i3.putExtra("part", "3");
				startActivity(i3);
				Utils.animateSlide(MainActivity.this);
				break;
			case 3:
				Intent i4 = new Intent(MainActivity.this, HomeLoggedIn.class);
				i4.putExtra("part", "4");
				startActivity(i4);
				Utils.animateSlide(MainActivity.this);
				break;
			default:
				break;
			}
		}
		
	});
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
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

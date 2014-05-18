package com.example.almoufasseralsaghir;

import org.json.JSONObject;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.almoufasseralsaghir.external.TafseerManager;
import com.almoufasseralsaghir.external.User;
import com.almoufasseralsaghir.utils.ConfirmationDialog;
import com.almoufasseralsaghir.utils.IClickCustomListener;
import com.almoufasseralsaghir.utils.ImageAdapter;
import com.almoufasseralsaghir.utils.SanabilActivity;


public class MainActivity extends SanabilActivity  implements IClickCustomListener{

	private Button  register_enter, login_btn, new_user,
					deconnect, account_settings, settings;
	private RelativeLayout register_interface, logged_in_interface ;
	private EditText email_login ;
	private TextView name_logged_in ;
	private ListView listViewArticles;
	ImageView herbes, email_hint ;
	private ConfirmationDialog exitDialog ;
	private TafseerManager tafseerManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
	
	tafseerManager = TafseerManager.getInstance(this);
	
	herbes = (ImageView) findViewById(R.id.herbes);
	herbes.bringToFront();
	
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
	name_logged_in = (TextView) findViewById(R.id.welcome_logged_name);
	
//	name_logged_in.setText("Mohamed Rami Trabelsi");

	register_enter.setVisibility(View.VISIBLE);
	register_interface.setVisibility(View.INVISIBLE);
	logged_in_interface.setVisibility(View.INVISIBLE);
	
	///////////////FIRST VIEW : ENTER/////////////////////////////////////////////////////////
	
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
	
///////////////   SECOND VIEW : LOGIN    /////////////////////////////////////////////////////////
	
	
	email_login.setOnFocusChangeListener(new OnFocusChangeListener() {
		
		@Override
		public void onFocusChange(View v, boolean hasFocus) {

			if (hasFocus) 
				email_hint.setVisibility(View.INVISIBLE);
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
				
				new AsyncTask<Void, JSONObject, JSONObject>() {

					@Override
					protected void onPreExecute() {
						register_interface.setVisibility(View.GONE);
					}
					
					@Override
					protected JSONObject doInBackground(Void... params) {

						return tafseerManager.loginUser(email_login.getText().toString());
					}
					
					@Override
					protected void onPostExecute(JSONObject result) {
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
	
///////////////   THIRD VIEW : LOGGED IN    /////////////////////////////////////////////////////////
	
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
	
// SHOULD BE IMPLEMENTED WITH INTENT PUT EXTRA    		
				
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

	
///////////////   LIST VIEW : HANDLING    /////////////////////////////////////////////////////////
	
	
	Integer[] lListIcone={R.drawable.list_1 , R.drawable.list_2 ,R.drawable.list_3 ,R.drawable.list_4 };
	ArrayAdapter<Integer> adapter = new ImageAdapter(this, R.layout.rowlv_module, lListIcone);

	listViewArticles = (ListView) findViewById(R.id.listView1);
	listViewArticles.setAdapter(adapter);
	listViewArticles.setDivider(null);

	listViewArticles.setOnItemClickListener(new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
			switch (position) {
			case 0:
//				startActivity(new Intent(ActivityModules.this, M8_Home.class));
//				Utils.animateFad(ActivityModules.this);
//				finish();
				break;
			case 1:
//				startActivity(new Intent(ActivityModules.this, M9_Home.class));
//				Utils.animateFad(ActivityModules.this);
//				finish();
				break;
			case 2:
//				startActivity(new Intent(ActivityModules.this, M10_Home.class));
//				Utils.animateFad(ActivityModules.this);
//				finish();
				break;
			case 3:
//				startActivity(new Intent(ActivityModules.this, M11_Home.class));
//				Utils.animateFad(ActivityModules.this);
//				finish();
				break;
			default:
				break;
			}
		}
		
	});
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	AsyncTask<Void, JSONObject, JSONObject> LoginAsync = new AsyncTask<Void, JSONObject, JSONObject>() {
//
//		@Override
//		protected JSONObject doInBackground(Void... params) {
//			
//			return tafseerManager.loginUser(email_login.getText().toString());
//		}
//		
//		@Override
//		protected void onPostExecute(JSONObject result) {
//			if(result != null)
//			{
//				logged_in_interface.setVisibility(View.VISIBLE);
//				logged_in_interface.bringToFront();
//			}
//		}
//		
//	};
	
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

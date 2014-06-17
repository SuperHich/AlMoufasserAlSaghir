package com.almoufasseralsaghir;

import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.almoufasseralsaghir.entity.User;
import com.almoufasseralsaghir.utils.MySuperScaler;
import com.almoufasseralsaghir.utils.Utils;
import com.example.almoufasseralsaghir.R;

public class RegistrationActivity extends MySuperScaler{

	private EditText register_name, register_mail, register_mail_confirm, register_fb, register_twitter,
					 register_bas_droit_1, register_bas_droit_2, register_bas_droit_3,
					 register_bas_gauche_1, register_bas_gauche_2 , register_bas_gauche_3;
	
	private ImageView register_bas_gauche_1_hint, register_bas_gauche_2_hint , register_bas_gauche_3_hint ;
	
	private RelativeLayout principal_layout;
	private Button register_cancel, register_confirm ;
	private Button social_1, social_2, social_3 ;
//	private String social_state = "mail";
	private int state_social_1 = 0;
	private int state_social_2 = 1;
	private int state_social_3 = 2;
	
	private final int state_mail = 0;
	 private final int state_fb = 1;
	 private final int state_tw = 2;
		
	private boolean isUpdate = false;
	private User loggedInUser;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration);
		
		principal_layout = (RelativeLayout) findViewById(R.id.principal_layout);
				
		register_cancel = (Button) findViewById(R.id.register_cancel);
		register_confirm = (Button) findViewById(R.id.register_confirm);
		
		register_name = (EditText) findViewById(R.id.register_name);
		register_mail = (EditText) findViewById(R.id.register_mail);
		register_mail_confirm = (EditText) findViewById(R.id.register_mail_confirm);
		register_fb = (EditText) findViewById(R.id.register_fb);
		register_twitter = (EditText) findViewById(R.id.register_twitter);
		
		register_bas_droit_1 = (EditText) findViewById(R.id.register_bas_droit_1);
		register_bas_droit_2 = (EditText) findViewById(R.id.register_bas_droit_2);
		register_bas_droit_3 = (EditText) findViewById(R.id.register_bas_droit_3);
		
		register_bas_gauche_1 = (EditText) findViewById(R.id.register_bas_gauche_1);
		register_bas_gauche_2 = (EditText) findViewById(R.id.register_bas_gauche_2);
		register_bas_gauche_3 = (EditText) findViewById(R.id.register_bas_gauche_3);
		
		register_bas_gauche_1_hint = (ImageView) findViewById(R.id.register_bas_gauche_1_hint);
		register_bas_gauche_2_hint = (ImageView) findViewById(R.id.register_bas_gauche_2_hint);
		register_bas_gauche_3_hint = (ImageView) findViewById(R.id.register_bas_gauche_3_hint);
	
		social_1 = (Button) findViewById(R.id.social_btn_bas_droit_1);
		social_2 = (Button) findViewById(R.id.social_btn_bas_droit_2);
		social_3 = (Button) findViewById(R.id.social_btn_bas_droit_3);
		
		if(getIntent().getExtras() != null)
		{
			isUpdate = getIntent().getExtras().getBoolean("update");
			loggedInUser = mTafseerManager.getLoggedInUser();
			showUser(loggedInUser);
		}
//		register_cancel.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				v.getBackground().setColorFilter(0x77000000,PorterDuff.Mode.SRC_ATOP);
//				v.invalidate();
//				startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
//		//		Utils.animateFad(RegistrationActivity.this);
//				finish();
//			}
//		});
		
		register_cancel.setOnTouchListener(new OnTouchListener() {
			@Override
		    public boolean onTouch(View v, MotionEvent event) {
		      switch (event.getAction()) {
		      case MotionEvent.ACTION_DOWN: {
		          Button view = (Button) v;
		          view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
		          v.invalidate();
		          break;
		      }
		      case MotionEvent.ACTION_UP:
		          // Your action here on button click
//		  				Utils.animateFad(RegistrationActivity.this);
		  				finish();
		    	  
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
		
		register_confirm.setOnTouchListener(new OnTouchListener() {
			@Override
		    public boolean onTouch(View v, MotionEvent event) {
		      switch (event.getAction()) {
		      case MotionEvent.ACTION_DOWN: {
		          Button view = (Button) v;
		          view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
		          v.invalidate();
		          break;
		      }
		      case MotionEvent.ACTION_UP:
		          // Your action here on button click
//		    	  startActivity(new Intent(RegistrationActivity.this, HomeLoggedIn.class));
		  		//		Utils.animateFad(RegistrationActivity.this);
		    	 
		    	  if (Utils.isOnline(getApplicationContext())){
		    	  new AsyncTask<Void, String, String>() {
		    		  		    		  
						@Override
						protected void onPreExecute() {
							loggedInUser = new User();
						}
						
						@Override
						protected String doInBackground(Void... params) {
							
							String name 			= register_name.getText().toString();
							String email  			= register_mail.getText().toString();
							String email_confirm  	= register_mail_confirm.getText().toString();
							String twitter  		= register_twitter.getText().toString();
							String facebook 		= register_fb.getText().toString();
							String follower1 		= register_bas_droit_1.getText().toString();
							String type1 			= String.valueOf(state_social_1);
							String follower2  		= register_bas_droit_2.getText().toString();
							String type2 			= String.valueOf(state_social_2);
							String follower3 		= register_bas_droit_3.getText().toString();
							String type3 			= String.valueOf(state_social_3);
							
							if(email_confirm.length() == 0)
								return "10";
							
							if(follower1.length() == 0)
								return "20";
							
							if(follower2.length() == 0)
								return "30";
							
							if(follower3.length() == 0)
								return "40";
							
							else if(email.equals(email_confirm)){
								loggedInUser.setUdid(mTafseerManager.getDeviceID());
								loggedInUser.setName(name);
								loggedInUser.setEmail(email);
								loggedInUser.setTwitter(twitter);
								loggedInUser.setFacebook(facebook);
								loggedInUser.setFollower1(follower1);
								loggedInUser.setType1(type1);
								loggedInUser.setFollower2(follower2);
								loggedInUser.setType2(type2);
								loggedInUser.setFollower3(follower3);							
								loggedInUser.setType3(type3);
								
								if(!isUpdate)
									return mTafseerManager.registerUser(loggedInUser);
								else
									return mTafseerManager.updateUser(loggedInUser);
							}else
								return "50";
							
						}
						
						@Override
						protected void onPostExecute(String result) {
							if(result != null)
							{
								if(result.equals("2")){
									mTafseerManager.showPopUp(RegistrationActivity.this, R.string.user_already_registered);
								} 
								else if(result.equals("10")){
									mTafseerManager.showPopUp(RegistrationActivity.this, R.string.register_confirm_email);
								}
								else if(result.equals("20")){
									mTafseerManager.showPopUp(RegistrationActivity.this, R.string.register_follower1);
								}
								else if(result.equals("30")){
									mTafseerManager.showPopUp(RegistrationActivity.this, R.string.register_follower2);
								}
								else if(result.equals("40")){
									mTafseerManager.showPopUp(RegistrationActivity.this, R.string.register_follower3);
								}
								else if(result.equals("50")){
									mTafseerManager.showPopUp(RegistrationActivity.this, R.string.register_request_confirm_email);
								}
								else{
									loggedInUser.setUid(result);
									
									if(!isUpdate)
										myDB.insertUser(loggedInUser);
									else{
										if(myDB.updateUser(loggedInUser))
											mTafseerManager.setLoggedInUser(loggedInUser);
									}
									
									Toast.makeText(RegistrationActivity.this, R.string.register_success, Toast.LENGTH_LONG).show();
									finish();
								}
							}else
								mTafseerManager.showPopUp(RegistrationActivity.this, R.string.error_try_again);
						}
						
					}.execute();
		    	  }else {
		    		  mTafseerManager.showPopUp(RegistrationActivity.this, R.string.error_internet_connexion);
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
		
		
		
		
		
		
		social_1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				state_social_1 = toggleSocialButton(social_1, state_social_1);
			}
		});
		
		social_2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				state_social_2 = toggleSocialButton(social_2, state_social_2);
			}
		});
		
		social_3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				state_social_3 = toggleSocialButton(social_3, state_social_3);
			}
		});
		
		register_bas_gauche_1.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {

				if (hasFocus) register_bas_gauche_1_hint.setVisibility(View.INVISIBLE);
				else 
					if(register_bas_gauche_1.getText().toString().equals(""))
					register_bas_gauche_1_hint.setVisibility(View.VISIBLE);
				
			}
		});
		register_bas_gauche_2.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {

				if (hasFocus) register_bas_gauche_2_hint.setVisibility(View.INVISIBLE);
				else 
					if(register_bas_gauche_2.getText().toString().equals(""))
					register_bas_gauche_2_hint.setVisibility(View.VISIBLE);
				
			}
		});
		register_bas_gauche_3.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
		
				if (hasFocus) register_bas_gauche_3_hint.setVisibility(View.INVISIBLE);
				else 
					if(register_bas_gauche_3.getText().toString().equals(""))
					register_bas_gauche_3_hint.setVisibility(View.VISIBLE);
				
			}
		});
		
	}
	
	private int toggleSocialButton(Button button, int currentBkg){
		switch(currentBkg) {
		case state_mail:
			button.setBackgroundResource(R.drawable.register_social_fb);
			return state_fb;
		case state_fb:
			button.setBackgroundResource(R.drawable.register_social_twitter);
			return state_tw;
		case state_tw:
			button.setBackgroundResource(R.drawable.register_social_mail);
			return state_mail;
		default:
			return state_fb;
		}
	}
	
	private void showUser(User user){
		register_name.setText(user.getName());
		register_mail.setText(user.getEmail());
//		register_mail_confirm.setText(user.getEmail());
		register_twitter.setText(user.getTwitter());
		register_fb.setText(user.getFacebook());
		register_bas_droit_1.setText(user.getFollower1());
		toggleSocialButton(social_1, Integer.parseInt(user.getType1()));
		register_bas_droit_2.setText(user.getFollower2());
		toggleSocialButton(social_2, Integer.parseInt(user.getType2()));
		register_bas_droit_3.setText(user.getFollower3());
		toggleSocialButton(social_3, Integer.parseInt(user.getType3()));
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		Utils.cleanViews(principal_layout);
	}

}

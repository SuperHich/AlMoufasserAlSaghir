package com.example.almoufasseralsaghir;

import java.util.ArrayList;

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

import com.almoufasseralsaghir.external.TafseerManager;
import com.almoufasseralsaghir.utils.ConfirmationDialog;
import com.almoufasseralsaghir.utils.IClickCustomListener;
import com.almoufasseralsaghir.utils.SanabilActivity;
import com.almoufasseralsaghir.utils.Utils;
import com.example.almoufasseralsaghir.entity.User;

public class RegistrationActivity extends SanabilActivity  implements IClickCustomListener{

	private EditText register_name, register_mail, register_mail_confirm, register_fb, register_twitter,
					 register_bas_droit_1, register_bas_droit_2, register_bas_droit_3,
					 register_bas_gauche_1, register_bas_gauche_2 , register_bas_gauche_3;
	
	private ImageView register_bas_gauche_1_hint, register_bas_gauche_2_hint , register_bas_gauche_3_hint ;
	
	private Button register_cancel, register_confirm ;
	private Button social_1, social_2, social_3 ;
//	private String social_state = "mail";
	private int state_social_1 = 0;
	private int state_social_2 = 1;
	private int state_social_3 = 2;
	
	private final int state_mail = 0;
	 private final int state_fb = 1;
	 private final int state_tw = 2;
	
	
	private ConfirmationDialog exitDialog ;
	
	private TafseerManager tafseerManager;
	private boolean isUpdate = false;
	private User savedUser;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration);
		
		tafseerManager = TafseerManager.getInstance(this);
		
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
			savedUser = tafseerManager.getSavedUser();
			showUser(savedUser);
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
		    	  new AsyncTask<Void, Integer, Integer>() {
		    		  		    		  
						@Override
						protected void onPreExecute() {
							savedUser = new User();
						}
						
						@Override
						protected Integer doInBackground(Void... params) {
							
							String name 			= register_name.getText().toString();
							String email  			= register_mail.getText().toString();
							String email_confirm  	= register_mail_confirm.getText().toString();
							String twitter  		= register_twitter.getText().toString();
							String facebook 		= register_fb.getText().toString();
							String follower1 		= register_bas_droit_1.getText().toString();
							String follower2  		= register_bas_droit_2.getText().toString();
							String follower3 		= register_bas_droit_3.getText().toString();
							
							if(email.equals(email_confirm)){
								savedUser.setUdid(tafseerManager.getDeviceID());
								savedUser.setName(name);
								savedUser.setEmail(email);
								savedUser.setTwitter(twitter);
								savedUser.setFacebook(facebook);
								savedUser.setFollower1(follower1);
								savedUser.setFollower2(follower2);
								savedUser.setFollower3(follower3);							
								
								if(!isUpdate)
									return tafseerManager.registerUser(savedUser);
								else
									return tafseerManager.updateUser(savedUser);
							}
							return -1;
						}
						
						@Override
						protected void onPostExecute(Integer result) {
							if(result > 0)
							{
								if(result == 2)
									tafseerManager.showPopUp(RegistrationActivity.this, R.string.user_already_registered);
								else{
									savedUser.setUid(result);
									tafseerManager.saveUser(savedUser);
									finish();
								}
							}else
								tafseerManager.showPopUp(RegistrationActivity.this, R.string.error_try_again);
						}
						
					}.execute();
		    	  }else {
		    		  tafseerManager.showPopUp(RegistrationActivity.this, R.string.error_internet_connexion);
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

				switch(state_social_1) {
				case state_mail:
					social_1.setBackgroundResource(R.drawable.register_social_fb);
					state_social_1 = state_fb;
					break;
				case state_fb:
					social_1.setBackgroundResource(R.drawable.register_social_twitter);
					state_social_1 = state_tw;
					break;
				case state_tw:
					social_1.setBackgroundResource(R.drawable.register_social_mail);
					state_social_1 = state_mail;
					break;
				
				}
			}
		});
		
		social_2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				switch(state_social_2) {
				case state_mail:
					social_2.setBackgroundResource(R.drawable.register_social_fb);
					state_social_2 = state_fb;
					break;
				case state_fb:
					social_2.setBackgroundResource(R.drawable.register_social_twitter);
					state_social_2 = state_tw;
					break;
				case state_tw:
					social_2.setBackgroundResource(R.drawable.register_social_mail);
					state_social_2 = state_mail;
					break;
				
				}
			}
		});
		
		social_3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				switch(state_social_3) {
				case state_mail:
					social_3.setBackgroundResource(R.drawable.register_social_fb);
					state_social_3 = state_fb;
					break;
				case state_fb:
					social_3.setBackgroundResource(R.drawable.register_social_twitter);
					state_social_3 = state_tw;
					break;
				case state_tw:
					social_3.setBackgroundResource(R.drawable.register_social_mail);
					state_social_3 = state_mail;
					break;
				
				}
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
	
	
	
	private void showUser(User user){
		register_name.setText(user.getName());
		register_mail.setText(user.getEmail());
//		register_mail_confirm.setText(user.getEmail());
		register_twitter.setText(user.getTwitter());
		register_fb.setText(user.getFacebook());
		register_bas_droit_1.setText(user.getFollower1());
		register_bas_droit_2.setText(user.getFollower2());
		register_bas_droit_3.setText(user.getFollower3());
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

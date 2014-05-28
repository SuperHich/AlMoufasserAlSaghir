package com.example.almoufasseralsaghir;

import com.almoufasseralsaghir.utils.MySuperScaler;
import com.almoufasseralsaghir.utils.Utils;
import com.example.almoufasseralsaghir.database.AlMoufasserDB;
import com.example.almoufasseralsaghir.entity.User;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;


import android.view.KeyEvent;



@SuppressLint("HandlerLeak")
public class SplashHome extends MySuperScaler {
	
	private static final int STOPSPLASH = 0;
	private static final long SPLASHTIME = 2000;
	private Handler splashHandler = new Handler() {
	private	Intent intent  ;
		
	public void handleMessage(Message msg) {

		User user = myDB.whoIsLoggedIn();
		mTafseerManager.setLoggedInUser(user);

		if (user.isLoggedIn())
		{
			intent = new Intent(SplashHome.this, SouraActivity.class);
		} else 	
		{
			intent = new Intent(SplashHome.this, MainActivity.class);
		}
		SplashHome.this.startActivity(intent);
		Utils.animateSlide(SplashHome.this);
		SplashHome.this.finish();

		super.handleMessage(msg);
	}
	};

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splashhome);

		Message msg = new Message();
		msg.what = STOPSPLASH;

		splashHandler.sendMessageDelayed(msg, SPLASHTIME);

	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			splashHandler.removeMessages(STOPSPLASH);
		}
		return super.onKeyDown(keyCode, event);
	}


}
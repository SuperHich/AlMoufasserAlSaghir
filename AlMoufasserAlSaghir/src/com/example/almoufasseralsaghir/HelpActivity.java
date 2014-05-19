package com.example.almoufasseralsaghir;

import android.os.Bundle;

import com.almoufasseralsaghir.utils.ConfirmationDialog;
import com.almoufasseralsaghir.utils.IClickCustomListener;
import com.almoufasseralsaghir.utils.SanabilActivity;

public class HelpActivity extends SanabilActivity implements IClickCustomListener {

	private ConfirmationDialog exitDialog ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help);
		
		
		
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

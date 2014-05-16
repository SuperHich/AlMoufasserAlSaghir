package com.almoufasseralsaghir.utils;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.almoufasseralsaghir.R;


public class ConfirmationDialog extends Dialog implements OnClickListener {

	private Button yes, no;
	private Context context;
	private IClickCustomListener clickCustomListener;

	public ConfirmationDialog(Activity context, int customdialogtheme,
			
			IClickCustomListener clickCustonLestener) {
		super(context, customdialogtheme);
		this.context = context;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_yes_no);
		getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);	// Removes notification bar
		


		initControls();

		this.clickCustomListener = clickCustonLestener;
	}

	private void initControls() {
		yes = (Button) findViewById(R.id.yes);
		no = (Button) findViewById(R.id.no);

		yes.setOnClickListener(this);
		no.setOnClickListener(this);

	}

	@Override
	public void onClick(View arg0) {
		int id = arg0.getId();
		if (id == R.id.yes) {
			clickCustomListener.onClickYes();
		} else if (id == R.id.no) {
			clickCustomListener.onClickNo();
		}
	}

}
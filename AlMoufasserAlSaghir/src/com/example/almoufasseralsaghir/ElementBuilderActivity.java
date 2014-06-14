package com.example.almoufasseralsaghir;

import java.io.File;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.almoufasseralsaghir.external.TafseerManager;
import com.almoufasseralsaghir.utils.MySuperScaler;
import com.almoufasseralsaghir.utils.RightHorizontalScrollView;
import com.example.almoufasseralsaghir.entity.QuizElementToAdd;

public class ElementBuilderActivity extends MySuperScaler{
	
	private RelativeLayout all_buildings_layout, all_builds_imgs;
	private ImageView puller;
	private SeekBar seek_buildings;
	private RelativeLayout principal_layout; 
	private RightHorizontalScrollView horizontal_scroll;
	private Button back, all_buildings;
	private ImageView img_draggable;
	
	private ObjectAnimator objectAnimator;
	
	private boolean isScrollEnabled = false;
	private boolean isAllBuildingsShown = false;
	private int suraId;
	private int partNb;
	
	private QuizElementToAdd currentQuizElement;
	private ImageView imgToDrag;
	private RelativeLayout viewToDragIn, viewToDragFrom;
	
	private boolean isDragOk = false;
	private float newX;
	
	private Bitmap bmToDrag;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.buildings_layout);
		
		if(getIntent().getExtras() != null){
			suraId = getIntent().getExtras().getInt("suraId");
			partNb = getIntent().getExtras().getInt("partNb");
		}
		
		TafseerManager.QuizPNGPath = getExternalFilesDir(null) + File.separator + "_QuizPNGS" + File.separator;
		TafseerManager.QuizPNGGrayPath = getExternalFilesDir(null) + File.separator + "_QuizPNGsGray" + File.separator;
		
		
		principal_layout = (RelativeLayout) findViewById(R.id.principal_layout);
		horizontal_scroll = (RightHorizontalScrollView) findViewById(R.id.horizontal_scroll);
		back = (Button) findViewById(R.id.back);
		all_buildings = (Button) findViewById(R.id.all_buildings);
		img_draggable = (ImageView) findViewById(R.id.img_draggable);
		
		///// Bottom Layout
		all_buildings_layout = (RelativeLayout) findViewById(R.id.all_buildings_layout);
		all_builds_imgs = (RelativeLayout) findViewById(R.id.all_builds_imgs);
		puller = (ImageView) findViewById(R.id.puller);
		seek_buildings = (SeekBar) findViewById(R.id.seek_buildings);
//		seek_buildings.setMax(all_buildings_layout.getLayoutParams().width);
		seek_buildings.setMax(1365);
		puller.bringToFront();
		
		seek_buildings.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				
				int real_progress = getRealProgress(seekBar.getProgress());
				horizontal_scroll.smoothScrollTo(real_progress, 0);
				Log.v(""," "+ seekBar.getProgress() + " ... " + real_progress + " ... " + horizontal_scroll.getMaxScrollAmount() + " ... " + horizontal_scroll.getMeasuredWidth());
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				
			}
		});
		
		back.bringToFront();
		all_buildings.bringToFront();
		
		back.setOnTouchListener(new OnTouchListener() {
			
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
		    	  if(isScrollEnabled){
		    		  isScrollEnabled = false;
		    		  all_buildings_layout.setVisibility(View.VISIBLE);
		    		  all_buildings.setVisibility(View.VISIBLE);
		    	  }else
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
		
		all_buildings.setOnTouchListener(new OnTouchListener() {
			
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
					isScrollEnabled = true;
					all_buildings_layout.setVisibility(View.GONE);
					all_buildings.setVisibility(View.GONE);
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
		
		horizontal_scroll.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				return !isScrollEnabled;
			}
		});
		
		  
		puller.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				 switch (event.getAction()) {
			      case MotionEvent.ACTION_DOWN: {
			          ImageView view = (ImageView) v;
			          view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
			          v.invalidate();
			          break;
			      }
			      case MotionEvent.ACTION_UP: {
			    	  
			    	  if(!isAllBuildingsShown){
			    		  isAllBuildingsShown = true;
			    		  objectAnimator = ObjectAnimator.ofFloat(all_buildings_layout, "translationY", 0, -all_builds_imgs.getLayoutParams().height);
			    		  objectAnimator.setDuration(700);
			    		  objectAnimator.start();

			    		  objectAnimator.addListener(new AnimatorListener() {

			    			  @Override
			    			  public void onAnimationStart(Animator arg0) {
			    				  // TODO Auto-generated method stub

			    			  }

			    			  @Override
			    			  public void onAnimationRepeat(Animator arg0) {
			    				  // TODO Auto-generated method stub

			    			  }

			    			  @Override
			    			  public void onAnimationEnd(Animator arg0) {
			    				  // TODO Auto-generated method stub

			    			  }

			    			  @Override
			    			  public void onAnimationCancel(Animator arg0) {
			    				  // TODO Auto-generated method stub

			    			  }
			    		  });
			    	  }else{
			    		  isAllBuildingsShown = false;
			    		  objectAnimator.reverse();
			    	  }
					
			      }
			      case MotionEvent.ACTION_CANCEL: {
			    	  ImageView view = (ImageView) v;
			          view.getBackground().clearColorFilter();
			          view.invalidate();
			          break;
			      }
			      }
			      return true;
			}
		});
		
//		Bitmap bm = BitmapFactory.decodeFile(TafseerManager.QuizPNGPath + "buildings_black_white.png");
//		Drawable d = Drawable.createFromPath(TafseerManager.QuizPNGPath + "complete_colored.png");
//		img_draggable.setImageBitmap(bm);
//		img_draggable.setBackgroundDrawable(d);
		
		img_draggable.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					Log.v("", "x="+event.getX() + " y="+event.getY());
				}
				return false;
			}
		});
		
		
	}
	

	@Override
	protected void onStart() {
		super.onStart();
		
		myDB.populateQuizElements();
		
		currentQuizElement = myDB.getQuizElementInfos(suraId, partNb);
		
		prepareElementToAdd();
	}

	private void prepareElementToAdd() {
		//==========================================================================================
	    //  scroll the view to a suitable position according to the new element position
	    //==========================================================================================
		
		newX = currentQuizElement.getQuizElementX() - horizontal_scroll.getLayoutParams().width/2 + currentQuizElement.getQuizWidth()/2;
		
		int diff = img_draggable.getLayoutParams().width - horizontal_scroll.getLayoutParams().width;
		if (newX > diff) 
			newX = (float) diff;

		if (newX<0f) newX=0f;
		
//		newX = currentQuizElement.getQuizWidth();

		horizontal_scroll.postDelayed(new Runnable() {
			@Override
			public void run() {
				horizontal_scroll.smoothScrollTo((int)newX, 0);
				seek_buildings.setProgress(getSeekProgress((int)newX));
			}
		},300);

		 Log.i("prepareElementToAdd", "newX 3  " + newX);
		 
//		 horizontal_scroll.setLeft((int)newX/6);
		 
		 imgToDrag = new ImageView(this);
		 imgToDrag.setTag(currentQuizElement.getQuizFileName());
		 bmToDrag = BitmapFactory.decodeFile(TafseerManager.QuizPNGPath + currentQuizElement.getQuizFileName());
		 imgToDrag.setImageBitmap(bmToDrag);
		 imgToDrag.setOnTouchListener(new OnTouchListener() {
		 
			public boolean onTouch(View view, MotionEvent motionEvent) {

				if (motionEvent.getAction() == MotionEvent.ACTION_DOWN && !isDragOk) {
					
					// create it from the object's tag
					
					ClipData.Item item = new ClipData.Item((CharSequence)view.getTag());
					
					String[] mimeTypes = { ClipDescription.MIMETYPE_TEXT_PLAIN };

					ClipData data = new ClipData(view.getTag().toString(), mimeTypes, item);

					DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);


					view.startDrag( data, //data to be dragged
							shadowBuilder, //drag shadow
							view, //local data about the drag and drop operation
							0   //no needed flags
							);

					view.setVisibility(View.INVISIBLE);

					return true;
				}

				else {
					imgToDrag.setVisibility(View.VISIBLE);
					return false;
				}
			}
		});
		 
		
		 float scale = currentQuizElement.getQuizHeight()/100;
		 float smallWidth = currentQuizElement.getQuizWidth()/scale;
		 float smallHeight = currentQuizElement.getQuizHeight()/scale;

		 viewToDragFrom = new RelativeLayout(this);
		 RelativeLayout.LayoutParams smallParams = new RelativeLayout.LayoutParams((int)smallWidth, (int)smallHeight);
		 viewToDragFrom.setLayoutParams(smallParams);
		 
		 viewToDragFrom.addView(imgToDrag);
		 
		 viewToDragIn = new RelativeLayout(this);
		 RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int)currentQuizElement.getQuizWidth(), (int)currentQuizElement.getQuizHeight());
		 params.setMargins(getFixedX((int)currentQuizElement.getQuizElementX()), getScaledY(currentQuizElement.getQuizElementY()), 0, 0);
		 viewToDragIn.setLayoutParams(params);
		 viewToDragIn.setOnDragListener(new OnDragListener() {
			
			@Override
			public boolean onDrag(View v, DragEvent event) {
				switch (event.getAction()) {
				case DragEvent.ACTION_DRAG_STARTED:
					break;
				case DragEvent.ACTION_DRAG_ENTERED:
					break;
				case DragEvent.ACTION_DRAG_EXITED:
					break;
				case DragEvent.ACTION_DROP:
					// if the view is the viewToDragIn, we accept the drag item
					if(v == viewToDragIn) {

						View view4 = (View) event.getLocalState();
						ViewGroup owner = (ViewGroup) view4.getParent();
						owner.removeView(view4);

						RelativeLayout container = (RelativeLayout) v;
						container.addView(view4);
						view4.setVisibility(View.VISIBLE);
						
						isDragOk = true;
						
//						 ImageView img = (ImageView) view4.findViewWithTag(currentQuizElement.getQuizFileName());
//					      Bitmap bitmap = ((BitmapDrawable)img.getDrawable()).getBitmap();
//					      
//					/// HERE................
//					      					      
//					      Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.buildings_coloried_to_crop);
//					      Bitmap resizedbitmap1 = Bitmap.createBitmap(bmp,(int)currentQuizElement.getQuizElementX(),(int) currentQuizElement.getQuizElementY(),(int)currentQuizElement.getQuizWidth(), (int)currentQuizElement.getQuizHeight());
//					      
//					      drawBitmapOnPosition(cropBitmap1(), getFixedX((int)currentQuizElement.getQuizElementX()), getScaledY(currentQuizElement.getQuizElementY()));
					      
					      
					     
						
//						ImageView img = (ImageView) view4.findViewWithTag(currentQuizElement.getQuizFileName());
//						Bitmap bitmap = ((BitmapDrawable)img.getDrawable()).getBitmap();
//						
//						drawBitmapOnPosition(bitmap, currentQuizElement.getQuizElementX(), currentQuizElement.getQuizElementY());
						
						
					}
					break;
				case DragEvent.ACTION_DRAG_ENDED:
				default:
					break;
				}
				return true;
			}
		});
		 
		 principal_layout.addView(viewToDragFrom);
		 principal_layout.addView(viewToDragIn);
	}
	
	private int getScaledX(double x){
		
		double dx = Double.valueOf(x);
		
		double dPercent = (double) (dx * 100.0 / 6144.0);
		double f = (double) (dPercent * img_draggable.getLayoutParams().width / 100.0);
		
		return (int)f;
	}

	private int getScaledY(double y){
		
		double dy = Double.valueOf(y);
		
		double dPercent = (double) (dy * 100.0 / 768.0);
		double f = (double) (dPercent * img_draggable.getLayoutParams().height / 100.0);
		
		return (int)f;
	}
	
	private int getRealProgress(double progress){
		
		double dPercent = (double) (progress * 100.0 / 1365.0);
		double f = (double) (dPercent * img_draggable.getMeasuredWidth() / 100.0);
		
		return (int)f;
	}
	
	private int getSeekProgress(double scroll){
		
		double dPercent = (double) (scroll * 100.0 / img_draggable.getMeasuredWidth() );
		double f = (double) (dPercent * 1365.0 / 100.0);
		
		return (int)f;
	}
	
	
	
	private Bitmap cropBitmap1()
	 {
		  
		     Bitmap bmp2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.buildings_coloried_to_crop); 
		     Bitmap bmOverlay = Bitmap.createBitmap((int)currentQuizElement.getQuizWidth(), (int)currentQuizElement.getQuizHeight(), Bitmap.Config.ARGB_8888);

		     Paint p = new Paint();
		     p.setXfermode(new PorterDuffXfermode(Mode.CLEAR));              
		     Canvas c = new Canvas(bmOverlay); 
		     c.drawBitmap(bmp2,  (int)currentQuizElement.getQuizElementX(), (int)currentQuizElement.getQuizElementY(), null); 
		     c.drawRect((int)currentQuizElement.getQuizElementX(), (int)currentQuizElement.getQuizElementY(), (int)(currentQuizElement.getQuizElementX()+currentQuizElement.getQuizWidth()), (int)(currentQuizElement.getQuizElementY()+currentQuizElement.getQuizHeight()), p);

		     return bmOverlay;
		     
//	     Bitmap bmp2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.buildings_coloried_to_crop); 
//	     Bitmap bmOverlay = Bitmap.createBitmap((int)currentQuizElement.getQuizWidth(), (int)currentQuizElement.getQuizHeight(), Bitmap.Config.ARGB_8888);
//
//	     Paint p = new Paint();
//	     p.setXfermode(new PorterDuffXfermode(Mode.CLEAR));              
//	     Canvas c = new Canvas(bmOverlay); 
//	     c.drawBitmap(bmp2, 0, 0, null); 
//	     c.drawRect((int)currentQuizElement.getQuizElementX(), (int)currentQuizElement.getQuizElementY(), (int)(currentQuizElement.getQuizElementX()+currentQuizElement.getQuizWidth()), (int)(currentQuizElement.getQuizElementY()+currentQuizElement.getQuizHeight()), p);

//		try{
//			 
//            Paint paint = new Paint();
//            paint.setFilterBitmap(true);
//            Bitmap bitmapOrg = BitmapFactory.decodeResource(getResources(),R.drawable.buildings_coloried_to_crop);
// 
//            int targetX  = (int)currentQuizElement.getQuizElementX();
//            int targetY = (int)currentQuizElement.getQuizElementY();
//            int targetWidth  = (int)currentQuizElement.getQuizWidth();
//            int targetHeight = (int)currentQuizElement.getQuizHeight();
// 
// 
//            Bitmap targetBitmap = Bitmap.createBitmap(targetWidth, targetHeight,Bitmap.Config.ARGB_8888);
// 
//            RectF rectf = new RectF(0, 0, 100, 100);
// 
//            Canvas canvas = new Canvas(targetBitmap);
//            Path path = new Path();
// 
//            path.addRect(rectf, Path.Direction.CW);
//            canvas.clipPath(path);
// 
//            canvas.drawBitmap( bitmapOrg, new Rect(targetX, targetY, bitmapOrg.getWidth(), bitmapOrg.getHeight()),
//                            new Rect(targetX, targetY, targetWidth, targetHeight), paint);
// 
// 
// 
//            Matrix matrix = new Matrix();
//            matrix.postScale(1f, 1f);
//            Bitmap resizedBitmap = Bitmap.createBitmap(targetBitmap, 0, 0, 100, 100, matrix, true);
// 
//            return resizedBitmap;
// 
//        }
//        catch(Exception e){
//            System.out.println("Error1 : " + e.getMessage() + e.toString());
//      }
//		
//	     return null;
	 }
	
	public void drawBitmapOnPosition(Bitmap bm, float left, float top){
		
		Bitmap bitmap = ((BitmapDrawable)img_draggable.getDrawable()).getBitmap();
		Bitmap mutableBitmap = bitmap.copy(Config.ARGB_8888, true);
		
//		Bitmap bm = Bitmap.createScaledBitmap(bitmapToDraw, (int)currentQuizElement.getQuizElementX(), (int)currentQuizElement.getQuizElementY(), false);

		Canvas canvas = new Canvas();
		canvas.setBitmap(mutableBitmap);
//		canvas.drawBitmap(bitmapToDraw, new Matrix(), null);
		
//		Bitmap bitmapToDraw = decodeSampledBitmapFromDescriptor(bmToDrag, (int)currentQuizElement.getQuizWidth(), (int)currentQuizElement.getQuizHeight());

		canvas.drawBitmap(bm, left, top, null);
	
		img_draggable.setImageBitmap(mutableBitmap);
		
	}
	
	private int getFixedX(int x){
		int diff = 6144 - x;
		return 1365 - diff;		
	}
	
	private int getFixedY(int y){
		int diff = 768 - y;
		return 800 - diff;		
	}
	
	public Bitmap originalResolution (String filePath, int width, int height)
	{   
		int reqHeight=width;
		int reqWidth=height;
		BitmapFactory.Options options = new BitmapFactory.Options();   

		// First decode with inJustDecodeBounds=true to check dimensions
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;        

		return BitmapFactory.decodeFile(filePath, options); 
	}

	private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {

		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			// Calculate ratios of height and width to requested height and width
			final int heightRatio = Math.round((float) height / (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			// Choose the smallest ratio as inSampleSize value, this will guarantee
			// a final image with both dimensions larger than or equal to the
			// requested height and width.
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}
}

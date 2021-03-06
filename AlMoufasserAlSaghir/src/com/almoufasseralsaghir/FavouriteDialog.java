package com.almoufasseralsaghir;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.almoufasseralsaghir.database.AlMoufasserDB;
import com.almoufasseralsaghir.entity.PartFavourite;
import com.almoufasseralsaghir.external.TafseerManager;
import com.almoufasseralsaghir.utils.FontFitTextView;
import com.almoufasseralsaghir.utils.MySuperScaler;
import com.almoufasseralsaghir.utils.SwipeListView;
import com.almoufasseralsaghir.utils.SwipeListView.SwipeListViewCallback;

/**
 * AlMoufasserAlSaghir
 * @author HICHEM LAROUSSI - RAMI TRABELSI
 * Copyright (c) 2014 Zad Group. All rights reserved.
 */

public class FavouriteDialog extends Dialog {

	private ListView fav_list;
	private MyAdapter m_Adapter;
	private Context context ;
	private AlMoufasserDB myDB;
	private ArrayList<PartFavourite> data;
	
	public FavouriteDialog(Context context) {
	  
	  super(context);
	  this.context = context ;
	  requestWindowFeature(Window.FEATURE_NO_TITLE); 
  	  getWindow().setBackgroundDrawableResource(android.R.color.transparent);
  	  setContentView(R.layout.favourites);
  	  
  	  myDB = new AlMoufasserDB(context);
  	  data = myDB.getPartFavorite();
  	  
  	  setCancelable(false);
  	  RelativeLayout popup_view = (RelativeLayout) findViewById(R.id.favourites_layout);
  	  popup_view.getLayoutParams().width = 1155;
  	  popup_view.getLayoutParams().height = 800;
  	  MySuperScaler.scaleViewAndChildren(popup_view, MySuperScaler.scale);
  	  
  	  Button fav_previous = (Button) findViewById(R.id.fav_previous);
  	  fav_previous.setOnTouchListener(new OnTouchListener() {
			
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
					dismiss();
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
  	  
  	  
  	  fav_list = (ListView) findViewById(R.id.favourites_listView);
  	  fav_list.setDivider(null);
  	  
  	  SwipeListViewCallback mySwipeListViewCallback = new SwipeListViewCallback() {
  		  
  		  @Override
  		  public void onSwipeItem(boolean isRight, int position) {
  			 m_Adapter.onSwipeItem(isRight, position);
  		  }
  		  
  		  @Override
  		  public void onItemClickListener(ListAdapter adapter, int position) {
  			  
  			  PartFavourite item = data.get(position);
  			SouraActivity.soura_act.finish();
  			  Intent intent = new Intent(getContext(), SouraActivity.class);
  			  intent.putExtra(TafseerManager.SURA_ID, item.getSuraId());
  			  intent.putExtra(TafseerManager.PART_NB, item.getPartNb());
  			  getContext().startActivity(intent);

  			  dismiss();
  		  }
  		  
  		  @Override
  		  public ListView getListView() {
  			  return fav_list;
  		  }
  	  };
  		 
  	  SwipeListView l = new SwipeListView(context, mySwipeListViewCallback);
  	  l.exec();
  	  m_Adapter = new MyAdapter(data);
  	  fav_list.setAdapter(m_Adapter);
		
//		set_fav.setOnTouchListener(new OnTouchListener() {
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
//
//		    	  m_Adapter.addItem("Item Item Item Item Item ");
//		      
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
		
	}
	public class MyAdapter extends BaseAdapter {

		protected List<PartFavourite> m_List;
		private final int INVALID = -1;
		protected int DELETE_POS = -1;

		public MyAdapter(ArrayList<PartFavourite> list) {
			m_List = list;
		}

		public void addItem(PartFavourite item) {
			m_List.add(item);
			notifyDataSetChanged();
		}

		public void addItemAll(List<PartFavourite> item) {
			//
			m_List.addAll(item);
			notifyDataSetChanged();
		}

		public void onSwipeItem(boolean isRight, int position) {
			if (isRight == false) {
				DELETE_POS = position;
			} else if (DELETE_POS == position) {
				DELETE_POS = INVALID;
			}
			//
			notifyDataSetChanged();
		}

		public void deleteItem(int pos) {
			//
			m_List.remove(pos);
			DELETE_POS = INVALID;
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return m_List.size();
		}

		@Override
		public PartFavourite getItem(int position) {
			return m_List.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.favourite_list_row, null);
			}
			FontFitTextView text = ViewHolderPattern.get(convertView, R.id.reminder_time);
			Button delete = ViewHolderPattern.get(convertView, R.id.swipe_delete);
			if (DELETE_POS == position) {
				delete.setVisibility(View.VISIBLE);
			} else
				delete.setVisibility(View.GONE);
			delete.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					PartFavourite item = getItem(position);
					myDB.removeFromPartFavorite(item.getSuraId(), item.getPartNb());
					
					deleteItem(position);
				}
			});

			final PartFavourite item = getItem(position);
			
			text.setText(item.getSuraName() + " - " + item.getPartName());

			return convertView;
		}
	}

	public static class ViewHolderPattern {
		@SuppressWarnings("unchecked")
		public static <T extends View> T get(View view, int id) {
			SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
			if (viewHolder == null) {
				viewHolder = new SparseArray<View>();
				view.setTag(viewHolder);
			}
			View childView = viewHolder.get(id);
			if (childView == null) {
				childView = view.findViewById(id);
				viewHolder.put(id, childView);
			}
			return (T) childView;
		}
	}

	@Override
	public void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		
		myDB.close();
	}
}

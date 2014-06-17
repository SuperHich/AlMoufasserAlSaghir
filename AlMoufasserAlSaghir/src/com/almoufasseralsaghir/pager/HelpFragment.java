package com.almoufasseralsaghir.pager;

import com.almoufasseralsaghir.utils.MySuperScaler;
import com.example.almoufasseralsaghir.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public final class HelpFragment extends Fragment {
    private static final String KEY_CONTENT = "TestFragment:Content";
    private int mImageId;
    
    public static HelpFragment newInstance(int imageId) {
        HelpFragment fragment = new HelpFragment();
        fragment.mImageId = imageId;
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
            mImageId = savedInstanceState.getInt(KEY_CONTENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {        
    	
    	
    	View view = inflater.inflate(R.layout.help_viewpager, container, false);
		if(!(MySuperScaler.scaled))
			MySuperScaler.scaleViewAndChildren(view, MySuperScaler.scale);
    	
    	ImageView image = (ImageView) view.findViewById(R.id.image_viewer);
    	image.setImageResource(mImageId);

        
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_CONTENT, mImageId);
    }
}

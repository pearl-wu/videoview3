package com.bais.cordova.video;

import java.util.ArrayList;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

public class videocontroller extends Activity{
	
	 private Bundle extras;
	 private int number;
	 private int totle = 0;
	 private ArrayList<String> mediaurls;
	 private VideoView videoww;
	 private MediaController mMediaController;

	 
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        extras = getIntent().getExtras();
        number = extras.getInt("medianumber");
        mediaurls = extras.getStringArrayList("mediaUrl");        

        //Toast.makeText(getBaseContext(), mediaurls.size()+">>>", Toast.LENGTH_SHORT).show();

		FrameLayout frelLayout = new FrameLayout(this);
		    
		videoww = new VideoView(this);
		RelativeLayout.LayoutParams videowwLayoutParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		videowwLayoutParam.addRule(RelativeLayout.CENTER_IN_PARENT);
		videoww.setLayoutParams(videowwLayoutParam);		
		frelLayout.addView(videoww);

		   mMediaController = new MediaController(this);
	       videoww.setMediaController(mMediaController);
	       videoww.setVideoURI(Uri.parse(mediaurls.get(totle)));
	       		if(number == 2) totle++;
	       mMediaController.setMediaPlayer(videoww);
	       videoww.requestFocus();
	       videoww.setDrawingCacheEnabled(true); 
	       videoww.start();       
	       
	       RelativeLayout.LayoutParams frelLayoutLayoutParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
	       this.addContentView(frelLayout, frelLayoutLayoutParam);	
       
       
	    videoww.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
       {
	       	@Override
	            public void onCompletion(MediaPlayer mp){
	            
	           // Toast.makeText(getBaseContext(), totle+">>>"+mediaurls.get(totle), Toast.LENGTH_SHORT).show();
						if(number==2)
						{
			       		videoww.setVideoURI(Uri.parse(mediaurls.get(totle)));
			       			if(totle+1 == mediaurls.size()){
			       				totle = 0;
			       			}else{
			       				totle++;
			       			}
			       			return;
						}
						finish();	            
	            }
	   }); 	
       
       videoww.setOnPreparedListener(new OnPreparedListener() {
           @Override
           public void onPrepared(MediaPlayer mp) {
        	   videoww.start();           
           }
       });
       
      /* videoww.setOnKeyListener(new OnKeyListener(){
    	   @Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
		        return true;
			}
       });*/
      
	}
	public boolean dispatchKeyEvent (KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT
		|| event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP 
		|| event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT 
		|| event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN)
		{ 
			//Toast.makeText(getBaseContext(), "dispatchKeyEvent", Toast.LENGTH_SHORT).show();
			return true; 
		} 
		return super.dispatchKeyEvent(event);
	}
}

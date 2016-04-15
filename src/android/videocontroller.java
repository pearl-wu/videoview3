package com.bais.cordova.video;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
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
		RelativeLayout.LayoutParams videowwLayoutParam = new RelativeLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
		videowwLayoutParam.addRule(RelativeLayout.CENTER_VERTICAL,RelativeLayout.TRUE);
		videowwLayoutParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,RelativeLayout.TRUE);
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
	       
	       RelativeLayout.LayoutParams frelLayoutLayoutParam = new RelativeLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
	       frelLayoutLayoutParam.addRule(RelativeLayout.CENTER_VERTICAL,RelativeLayout.TRUE);
	       frelLayoutLayoutParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,RelativeLayout.TRUE);
	       this.addContentView(frelLayout, frelLayoutLayoutParam);	
       
       
	    videoww.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
       {
	       	@Override
	            public void onCompletion(MediaPlayer mp){
	            
	            Toast.makeText(getBaseContext(), totle+">>>"+mediaurls.get(totle), Toast.LENGTH_SHORT).show();
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
       

	}

}

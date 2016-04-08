package com.bais.cordova.video;

import java.io.File;
import java.util.Iterator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONException;
import org.json.JSONObject;
import tw.com.bais.video.R;

public class VideoPlayer extends CordovaPlugin {

    private static final String TAG = "BACKGROUND_VIDEO";
    private static final String ACTION_PLAY_RECORDING = "play";
    private static final String ACTION_START_RECORDING = "start";
    private static final String ACTION_STOP_RECORDING = "stop";
    private static final String ACTION_PREVIEW_RECORDING = "preview";    
    private static final String FILE_EXTENSION = ".mp4";
    private String FILE_PATH = "";
    private String FILE_NAME = "";
    private VideoView videoww;
    private static final int ACTIVITY_CODE_PLAY_MEDIA = 7;  
    private ImageView image_play;
    private ImageView image_over;

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        FILE_PATH = cordova.getActivity().getFilesDir().toString() + "/";
    }
    
 
    @SuppressLint("RtlHardcoded") 
    public boolean execute(String action, CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
    	
    	try {
        	
            Log.d(TAG, "ACTION: " + action);
            
            if(ACTION_START_RECORDING.equals(action)){
            	
                FILE_NAME = args.getString(0);
                JSONObject options = args.getJSONObject(1);
                
                final int widthV =  options.getInt("widthV");
                final int heightV =  options.getInt("heightV");
                final int TOP =  options.getInt("topV");
                final int LEFT =  options.getInt("leftV");
               
                    //Get screen dimensions
                    cordova.getActivity().runOnUiThread(new Runnable() {
                    	
                        @SuppressLint("RtlHardcoded")
                        @Override
                        public void run() {     
                        
                            
                            
                            try {
                            	
                            	 videoww = new VideoView(cordova.getActivity());
                                 videoww.setVideoURI(Uri.parse(getNextFileName()));
                                 videoww.requestFocus();
                                   //Toast.makeText(cordova.getActivity(), videoww.isShown()+">>>", Toast.LENGTH_LONG).show();
                                 
                                 RelativeLayout main = new RelativeLayout(cordova.getActivity());
                                 main.setBackgroundColor(Color.argb(0, 255, 0, 0));
                                 RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                                 main.setLayoutParams(p);
                                 main.addView(videoww);                                 
                                 
                                 image_over = new ImageView(cordova.getActivity());
                                 image_over.setImageDrawable(cordova.getActivity().getResources().getDrawable(R.drawable.image_over));
	                             RelativeLayout.LayoutParams p_over = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	                             p_over.addRule(RelativeLayout.CENTER_VERTICAL);
	                             p_over.addRule(RelativeLayout.CENTER_HORIZONTAL);
	                             image_over.setLayoutParams(p_over);
	                             image_over.setDrawingCacheEnabled(true);
	                             main.addView(image_over);                                
                                 
                                 
                                 image_play = new ImageView(cordova.getActivity());
	                             image_play.setImageDrawable(cordova.getActivity().getResources().getDrawable(R.drawable.image_play));
	                             RelativeLayout.LayoutParams p_play = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	                             p_play.addRule(RelativeLayout.CENTER_VERTICAL);
	                             p_play.addRule(RelativeLayout.CENTER_HORIZONTAL);
	                             image_play.setLayoutParams(p_play);
	                             image_play.setDrawingCacheEnabled(true);
	                             main.addView(image_play);
	                             
	                             ImageView image_box = new ImageView(cordova.getActivity());
	                             image_box.setImageDrawable(cordova.getActivity().getResources().getDrawable(R.drawable.image_box));
	                             
	                             
	                             FrameLayout.LayoutParams p_main = new FrameLayout.LayoutParams(widthV-15,heightV-15);  
	                             p_main.setMargins(LEFT+10, TOP+5, 0, 0);
	                             cordova.getActivity().addContentView(main, p_main);
	                             
	                             FrameLayout.LayoutParams p_box = new FrameLayout.LayoutParams(widthV,heightV);  
	                             p_box.setMargins(LEFT, TOP, 0, 0);
	                             cordova.getActivity().addContentView(image_box, p_box);
                            } catch(Exception e) {
                                Log.e(TAG, "Error during preview create", e);
                                callbackContext.error(TAG + ": " + e.getMessage());
                            }
                        }
                    });
                return true;
            }
            
            if(ACTION_PLAY_RECORDING.equals(action)){
            	
            	cordova.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
            	
		            	Animation am = new AlphaAnimation(1.0f, 0.0f);
		            	am.setDuration(1000);
		            	am.setRepeatCount( 0 );
		            	image_over.setAnimation(am);
		            	am.startNow();
		            	videoww.seekTo(0);
		            	videoww.start();
		            	 	//Toast.makeText(cordova.getActivity(), image_play.isShown()+">>>", Toast.LENGTH_LONG).show();
		          		if(image_play.isShown() == true){
		          			image_play.setVisibility(View.INVISIBLE);
		          			image_over.setVisibility(View.INVISIBLE);
		          		}
                    }
                });
          		
    			 videoww.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
                 {
                 	@Override
                      public void onCompletion(MediaPlayer mp)
                      {                 		
                     	 videoww.start();
                      }
                 });           	
            	
            	return true;
            }
            

            if(ACTION_STOP_RECORDING.equals(action)) {
            	          	
                    cordova.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        	
                        	Animation am = new AlphaAnimation(0.0f, 1.0f);
                        	am.setDuration(1000);
    		            	image_over.setAnimation(am);
                        	am.startNow();
                        	
                        	if (videoww.isPlaying()){
                        		videoww.pause();
                        	}
                        	if(image_play.isShown() == false){
                     			image_play.setVisibility(View.VISIBLE);
                     			image_over.setVisibility(View.VISIBLE);
                     		}
                        }
                    });
                return true;
            }
            
            if(ACTION_PREVIEW_RECORDING.equals(action)){
            	
            	//Toast.makeText(cordova.getActivity(), image_play.isShown()+">>>", Toast.LENGTH_LONG).show();
        		final JSONObject options = null;

            	cordova.getActivity().runOnUiThread(new Runnable() {
            		@Override
            		public void run(){
            			
                    	
                    	if (videoww.isPlaying()){
                    		videoww.pause();
                    	}
                    	if(image_play.isShown() == false){
                 			image_play.setVisibility(View.VISIBLE);
                 			image_over.setVisibility(View.VISIBLE);
                 			image_over.setAlpha(1.0f);
                 		}
          			
            		}
            	});
              return true;
            }

            callbackContext.error(TAG + ": INVALID ACTION");
            return false;
        } catch(Exception e) {
            Log.e(TAG, "ERROR: " + e.getMessage(), e);
            callbackContext.error(TAG + ": " + e.getMessage());
            return false;
        }
    }

    public static String stripFileProtocol(String uriString) {
        if (uriString.startsWith("file://")) {
            return Uri.parse(uriString).getPath();
        }
        return uriString;
    }
    
    private String getNextFileName(){
        int i=1;
        String tmpFileName = FILE_NAME;
        while(new File(FILE_PATH + tmpFileName + FILE_EXTENSION).exists()) {
            tmpFileName = FILE_NAME + '_' + i;
            i++;
        }
        return tmpFileName;
    }
    
	  //Plugin Method Overrides
	    @Override
	    public void onPause(boolean multitasking) {
	    	cordova.getActivity().finish();
	        super.onPause(multitasking);
	    }
	
	    @Override
	    public void onResume(boolean multitasking) {
	        super.onResume(multitasking);
	    }
	
	    @Override
	    public void onDestroy() {
	        super.onDestroy();
	        android.os.Process.killProcess(android.os.Process.myPid());
	    }
}

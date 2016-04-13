package com.bais.cordova.video;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.json.JSONException;
import org.json.JSONObject;

import com.hutchind.cordova.plugins.vitamio.VitamioMedia;

import tw.com.bais.video.R;

public class VideoPlayer extends CordovaPlugin{

    private static final String TAG = "BACKGROUND_VIDEO";
    private static final String ACTION_PLAY_RECORDING = "play";
    private static final String ACTION_START_RECORDING = "start";
    private static final String ACTION_STOP_RECORDING = "stop";
    private static final String ACTION_PREVIEW_RECORDING = "preview";    
    private static final String FILE_EXTENSION = ".mp4";
    private String FILE_PATH = "";
    private String FILE_NAME = "";
    private VideoView videoww; 
    private RelativeLayout main;
    private ImageView image_play;
    private ImageView image_over;
    private SurfaceView surfaceview;
    private SurfaceHolder mHandler;
    private MediaPlayer Mplayer;

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
            	
            	final JSONObject options = args.getJSONObject(0);
                    //Get screen dimensions
                    cordova.getActivity().runOnUiThread(new Runnable() {
                        @SuppressLint("RtlHardcoded")
                        @Override
                        public void run() {  
                            try {
                            	startplay(options);
                            } catch(Exception e) {
                                Log.e(TAG, "Error during preview create", e);
                                callbackContext.error(TAG + ": " + e.getMessage());
                            }
                        }
                    });
                return true;
            }
            
            if(ACTION_PLAY_RECORDING.equals(action)){
            	
            	FILE_NAME = args.getString(0);
            	            	
            	cordova.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

		          		try {
		          			if(image_play.isShown()){
		          				viewstart();
		          			}
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SecurityException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalStateException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                    	
		            	Animation am = new AlphaAnimation(1.0f, 0.0f);
		            	am.setDuration(1000);
		            	am.setRepeatCount( 0 );
		            	image_over.setAnimation(am);
		            	am.startNow();
		          		image_play.setVisibility(View.INVISIBLE);
		          		image_over.setVisibility(View.INVISIBLE);
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

                        	if(image_play.isShown() == false){
                     			image_play.setVisibility(View.VISIBLE);
                     			image_over.setVisibility(View.VISIBLE);
                     		}
                        	
                    		main.removeView(surfaceview);
                    		Mplayer.release();

                        }
                    });
                return true;
            }
            
            if(ACTION_PREVIEW_RECORDING.equals(action)){
            	//Toast.makeText(cordova.getActivity(), image_play.isShown()+">>>", Toast.LENGTH_LONG).show();
            	cordova.getActivity().runOnUiThread(new Runnable() {
            		@Override
            		public void run(){
                    	if (Mplayer.isPlaying()){
                    		Mplayer.release();
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

	public void startplay(JSONObject option) throws JSONException{
    	
        int widthV =  option.getInt("widthV");
        int heightV =  option.getInt("heightV");
        int TOP =  option.getInt("topV");
        int LEFT =  option.getInt("leftV");

        main = new RelativeLayout(cordova.getActivity());
        main.setBackgroundColor(Color.argb(0, 255, 0, 0));
        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(widthV, widthV);
        main.setLayoutParams(p);

         image_over = new ImageView(cordova.getActivity());
         image_over.setImageDrawable(cordova.getActivity().getResources().getDrawable(R.drawable.image_over));
         RelativeLayout.LayoutParams p_over = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
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

         FrameLayout.LayoutParams p_main = new FrameLayout.LayoutParams(widthV-30,heightV-10); 
         p_main.setMargins(LEFT+15, TOP+5, 0, 0);
         cordova.getActivity().addContentView(main, p_main);

         FrameLayout.LayoutParams p_box = new FrameLayout.LayoutParams(widthV,heightV);  
         p_box.setMargins(LEFT, TOP, 0, 0);
         cordova.getActivity().addContentView(image_box, p_box);

    }
    
    private void viewstart() throws IllegalArgumentException, SecurityException, IllegalStateException, IOException {
    	
    	//videoww = new VideoView(cordova.getActivity());
    	//videoww.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        //videoww.setVideoURI(Uri.parse(getNextFileName()));
        //videoww.requestFocus();
        //Toast.makeText(cordova.getActivity(), videoww.isShown()+">>>", Toast.LENGTH_LONG).show();
    	
        surfaceview = new SurfaceView(cordova.getActivity());
        mHandler = surfaceview.getHolder();
        RelativeLayout.LayoutParams Hpl = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        surfaceview.setLayoutParams(Hpl);
        mHandler.setFixedSize(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mHandler.setKeepScreenOn(true);
        surfaceview.setDrawingCacheEnabled(true);
        main.addView(surfaceview);
    	
    	
        /* surfaceview = new SurfaceView(cordova.getActivity());
        mHandler = surfaceview.getHolder();
        mHandler.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mHandler.setFixedSize(widthV, heightV);
        mHandler.setKeepScreenOn(true);
        main.addView(surfaceview); */ 
        Toast.makeText(cordova.getActivity(), "viewstart", Toast.LENGTH_LONG).show();
        Mplayer = new MediaPlayer();      
        Mplayer.setDataSource(getNextFileName());
		Mplayer.prepare();

        // mHandler = videoww.getHolder();
    	 mHandler.addCallback(new SurfaceHolder.Callback() {
             @Override
             public void surfaceCreated(SurfaceHolder holder) {   
            	 Mplayer.setDisplay(holder);  
            	 Mplayer.start();
             }
             @Override
             public void surfaceDestroyed(SurfaceHolder holder) {}
             @Override
             public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}
         });
    	
    	 Mplayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
         {
         	@Override
              public void onCompletion(MediaPlayer mp)
              {                 		
             	//videoww.start();
         		Mplayer.start();
              }
         }); 
    	
    }
    

    
    private boolean play(final String url, final JSONObject options) throws IllegalArgumentException, SecurityException, IllegalStateException, IOException{
		final CordovaInterface cordovaObj = cordova;
		final CordovaPlugin plugin = this;
		
	    final MediaPlayer mMediaPlayer;
	    MediaController mMediaController = null;
	    SurfaceView mMediaView;
	    SurfaceHolder holder;

	    RelativeLayout relLayout = new RelativeLayout(cordova.getActivity());
		RelativeLayout.LayoutParams relLayoutParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        relLayoutParam.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        mMediaView = new SurfaceView(cordova.getActivity());
        mMediaView.setLayoutParams(relLayoutParam);
        relLayout.addView(mMediaView);
		
		
		mMediaPlayer = new MediaPlayer();
        mMediaController = new MediaController(cordova.getActivity(), true);
       /* mMediaController.setMediaPlayer(cordova.getActivity());
        mMediaPlayer.setOnBufferingUpdateListener(cordova.getActivity());
        mMediaPlayer.setOnCompletionListener(cordova.getActivity());
        mMediaPlayer.setOnErrorListener(cordova.getActivity());
        mMediaPlayer.setOnPreparedListener(cordova.getActivity());
        mMediaPlayer.setOnVideoSizeChangedListener(cordova.getActivity());*/

        mMediaController.setAnchorView(relLayout);
        mMediaController.setEnabled(true);
        
        mMediaPlayer.setDataSource(getNextFileName());
        mMediaPlayer.prepare();
        
        holder = mMediaView.getHolder();
        
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {   
           	 //Toast.makeText(cordova.getActivity(), "surface", Toast.LENGTH_LONG).show();
            	mMediaPlayer.setDisplay(holder);  
            	mMediaPlayer.start();
            }
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
           	 Mplayer.release();
            }
            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}
        });

		return true;
	}


	  //Plugin Method Overrides
	    @Override
	    public void onPause(boolean multitasking) {
	    	Mplayer.release();
	    	cordova.getActivity().finish();
	        super.onPause(multitasking);
	    }
	
	    @Override
	    public void onResume(boolean multitasking) {
	        super.onResume(multitasking);
	    }
	
	    @Override
	    public void onDestroy() {
	        Mplayer.release();
	        android.os.Process.killProcess(android.os.Process.myPid());
	        super.onDestroy();
	    }
}

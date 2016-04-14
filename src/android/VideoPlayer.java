package com.bais.cordova.video;

import java.io.IOException;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONException;
import org.json.JSONObject;

import tw.com.bais.video.R;

public class VideoPlayer extends CordovaPlugin{

    private static final String TAG = "BACKGROUND_VIDEO";
    private static final String ACTION_START_RECORDING = "beginning";
    private static final String ACTION_MANY_RECORDING = "many_play"; 
    private static final String ACTION_PLAY_RECORDING = "play";
    private static final String ACTION_STOP_RECORDING = "stop";
    private static final int ACTIVITY_CODE_PLAY_MEDIA = 7;
    private String FILE_NAME = "";
    private RelativeLayout main;
    private ImageView image_play;
    private ImageView image_over;
    private SurfaceView surfaceview;
    private SurfaceHolder mHandler;
    private MediaPlayer Mplayer;
    

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
		          			
			            	Animation am = new AlphaAnimation(1.0f, 0.0f);
			            	am.setDuration(1000);
			            	am.setRepeatCount( 0 );
			            	image_over.setAnimation(am);
			            	am.startNow();
			          		image_play.setVisibility(View.INVISIBLE);
			          		image_over.setVisibility(View.INVISIBLE);
		          			
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
            
            if(ACTION_MANY_RECORDING.equals(action)){
            	
            	final JSONObject video_names = args.getJSONObject(0);
            	FILE_NAME = args.getString(1);
            	            	
            	cordova.getActivity().runOnUiThread(new Runnable() {
            		@Override
            		public void run(){
            			
            			if(image_play.isShown() == false){
            				main.removeView(surfaceview);
                    		Mplayer.release();
                			image_play.setVisibility(View.VISIBLE);
                 			image_over.setVisibility(View.VISIBLE);
                 			image_over.setAlpha(1.0f);
            			}	
            			
            			play(videocontroller.class, FILE_NAME, null);
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

    	/*videoww = new VideoView(cordova.getActivity());
    	videoww.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        videoww.setVideoURI(Uri.parse(getNextFileName()));
        videoww.requestFocus();
        main.addView(videoww);
        Toast.makeText(cordova.getActivity(), videoww.isShown()+">>>", Toast.LENGTH_LONG).show();
        videoww.start();*/
    	
    	
        surfaceview = new SurfaceView(cordova.getActivity());
        mHandler = surfaceview.getHolder();
        RelativeLayout.LayoutParams Hpl = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        surfaceview.setLayoutParams(Hpl);
        mHandler.setFixedSize(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mHandler.setKeepScreenOn(true);
        surfaceview.setDrawingCacheEnabled(true);   	
        main.addView(surfaceview);  
        Mplayer = new MediaPlayer();      
        Mplayer.setDataSource(FILE_NAME);
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
         		 Mplayer.start();
              }
         }); 
    	
    }
    
    private void play(final Class activityClass, final String url, final JSONObject options){
    	final CordovaInterface cordovaObj = cordova;
    	final CordovaPlugin plugin = this;

    	cordova.getActivity().runOnUiThread(new Runnable() {
			public void run() {
				
				Toast.makeText(cordova.getActivity(), "play play", Toast.LENGTH_SHORT).show();
				final Intent streamIntent = new Intent(cordovaObj.getActivity().getApplicationContext(), activityClass);
				Bundle extras = new Bundle();
				extras.putString("mediaUrl", url);
				streamIntent.putExtras(extras);
				
				cordovaObj.startActivityForResult(plugin, streamIntent, ACTIVITY_CODE_PLAY_MEDIA);
			}
		});				
	}


	  //Plugin Method Overrides

	    @Override
	    public void onPause(boolean multitasking) {
	    	 //super.onPause(true);
	    		//Mplayer.release();
		    	//cordova.getActivity().finish();	
	    }
	    
	    public void onRestart(){}
	
	    @Override
	    public void onResume(boolean multitasking) {
	        //super.onResume(true);
	    }
	
	    @Override
	    public void onDestroy() {
	    	//super.onDestroy();
	    	///Mplayer.release();
	 	   // android.os.Process.killProcess(android.os.Process.myPid());
	    }
}

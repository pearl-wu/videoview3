package com.bais.cordova.video;

import java.io.IOException;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.MediaController;
import android.widget.MediaController.MediaPlayerControl;
import android.widget.RelativeLayout;
import android.widget.VideoView;

public class videocontroller extends Activity implements  OnBufferingUpdateListener, OnCompletionListener, OnPreparedListener,
OnVideoSizeChangedListener, SurfaceHolder.Callback, MediaPlayerControl{
	
	
	 private Bundle extras;
	 private String file_name;
	 private MediaPlayer Mplayer;
	 
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
        extras = getIntent().getExtras();
        file_name = extras.getString("mediaUrl");
        
		FrameLayout frelLayout = new FrameLayout(this);
		
		

		Mplayer = new MediaPlayer();
	   /* try {
			Mplayer.setDataSource(file_name);
			Mplayer.prepare();
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
		}*/
	    
	    
	    SurfaceView surfaceview = new SurfaceView(this);
	    SurfaceHolder mHandler;
	    mHandler = surfaceview.getHolder();
	    mHandler.addCallback(this); 
	    
		/*final VideoView videoww = new VideoView(this);
		RelativeLayout.LayoutParams videowwLayoutParam = new RelativeLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
		videowwLayoutParam.addRule(RelativeLayout.CENTER_VERTICAL,RelativeLayout.TRUE);
		videowwLayoutParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,RelativeLayout.TRUE);
		videoww.setLayoutParams(videowwLayoutParam);
		videoww.setDrawingCacheEnabled(true);*/ 
		frelLayout.addView(surfaceview);

			try {
	
		       MediaController mMediaController = new MediaController(this);
		       mMediaController.setEnabled(true);
		       Mplayer = MediaPlayer.create(this, Uri.parse(file_name));
		       Mplayer.setOnBufferingUpdateListener(this);
		       Mplayer.setOnCompletionListener(this);
		       Mplayer.setOnPreparedListener(this);
		       Mplayer.setScreenOnWhilePlaying(true);
		       Mplayer.setOnVideoSizeChangedListener(this);
		       Mplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		       
			} catch (Exception e) {
		       e.printStackTrace();
		    }       
       
       /*videoww.setMediaController(mMediaController);
       videoww.setVideoURI(Uri.parse(file_name));
       mMediaController.setMediaPlayer(videoww);
       videoww.requestFocus();
       videoww.start();*/
       
	   WindowManager.LayoutParams lp = new WindowManager.LayoutParams();   
	   lp.width = WindowManager.LayoutParams.MATCH_PARENT;
       lp.height = WindowManager.LayoutParams.MATCH_PARENT;
       lp.x = 0;
       lp.y = 0;
       this.addContentView(frelLayout, lp);	
       
       
      /* videoww.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
       {
       	@Override
            public void onCompletion(MediaPlayer mp)
            {                 		
       			videoww.start();
            }
       }); 	*/
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		Mplayer.start();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		Mplayer.pause();
	}

	@Override
	public int getDuration() {
		// TODO Auto-generated method stub
		return Mplayer.getDuration();
	}

	@Override
	public int getCurrentPosition() {
		// TODO Auto-generated method stub
		return Mplayer.getCurrentPosition();
	}

	@Override
	public void seekTo(int pos) {
		// TODO Auto-generated method stub
		Mplayer.seekTo(pos);
	}

	@Override
	public boolean isPlaying() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getBufferPercentage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean canPause() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canSeekBackward() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canSeekForward() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getAudioSessionId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		// TODO Auto-generated method stub
		
		
		
		
		
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent) {
		// TODO Auto-generated method stub
		
	}

}

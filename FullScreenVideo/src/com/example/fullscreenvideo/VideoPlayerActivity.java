package com.example.fullscreenvideo;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.VideoView;

public class VideoPlayerActivity extends Activity implements OnCompletionListener {

    private VideoView mVV;

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        String videoPath=null;
        Bundle e = getIntent().getExtras();
        if (e != null) {
            videoPath = e.getString("videoPath");
        }
        playFileRes(videoPath);
    }
    
    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_video_player);
        String videoPath=null;    
        Bundle e = getIntent().getExtras();
        if (e!=null) {
            videoPath = e.getString("videoPath");
        }
        mVV = (VideoView)findViewById(R.id.myvideoview);
        mVV.setOnCompletionListener(this);
        
        mVV.setOnPreparedListener( new MediaPlayer.OnPreparedListener() {
	            @Override
	            public void onPrepared(MediaPlayer mp) {
	            mp.setLooping(true);
	            }
        });
        
        mVV.setOnTouchListener( new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
            if( ((VideoView)v).isPlaying() )
                   ((VideoView)v).pause();
            else
                   ((VideoView)v).start();
            return true;
            }
        });
        
        if (!playFileRes(videoPath)) return;
        mVV.start();
    }

    private boolean playFileRes(String videoPath) {
        if (videoPath==null || "".equals(videoPath)) {
            stopPlaying();
            return false;
        } else {
            mVV.setVideoURI( Uri.parse(videoPath) );
            return true;
        }
    }
    public void stopPlaying() {
        mVV.stopPlayback();
        this.finish();             
    }
    @Override
    public void onCompletion(MediaPlayer mp) {
        finish();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}

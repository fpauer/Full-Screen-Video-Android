package com.example.fullscreenvideo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button btnPlayVideo = (Button) findViewById(R.id.btnPlayVideo);
		btnPlayVideo.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.video_test;
				playVideo( videoPath );
			}
		});
	}

	private void playVideo(String videoPath) {
	    Intent videoPlaybackActivity = new Intent(this, VideoPlayerActivity.class);
	    videoPlaybackActivity.putExtra("videoPath", videoPath);
	    startActivity(videoPlaybackActivity);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}

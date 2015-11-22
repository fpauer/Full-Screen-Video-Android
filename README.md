# Android - Full screen video in any screen orientation

<img width="728" src="https://fernandopauer.files.wordpress.com/2015/07/google-dev.png?w=411" >

Android SDK has a Media Player to play videos however you probably have a necessity to create a simple custom player and fast to use.

I had this necessity and I made my researches to create something helpful and with clean code.

This tutorial will show you how to play a video in a full screen mode using VideoView and changing the activity orientation.

## Android Manifest

For any activity that you create , you have to define it inside the android manifest, that is our definition for the activity:

```xml

<activity
    android:name=".VideoPlayerActivity"
    android:label="@string/app_name"
    android:launchMode="singleTask"
    android:theme="@android:style/Theme.NoTitleBar.Fullscreen"
    android:configChanges="orientation|screenSize" ></activity>

```

I called my class activity as “VideoPlayerActivity” , and defined the property configChanges as “orientation|screenSize” because I want the behavior of change the orientation when rotate the device.

If your target API level 13 or higher, you must include the screenSize value in addition to the orientation value as described here.  

## XML

First, create a XML for your activity , I called it as activity_video_player.xml


```xml

<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="horizontal"
    android:layout_width="fill_parent"
    android:layout_height="fill_parent"
    >
    <VideoView
        android:id="@+id/myvideoview"
        android:layout_width="fill_parent"
        android:layout_height="fill_parent"
        android:layout_gravity="center"
    />
</LinearLayout>

```

I set the LinerLayout to horizontal orientation, because it adapts better landscape videos. In VideoView we have to set “fill_parent” for height and width , in that way our video will expand and fill the entire screen.


## VideoPlayerActivity code

Now we can create our class, it is simple and easy to understand, that is our class code:


```java
public class VideoPlayerActivity extends Activity implements OnCompletionListener {
           
    private VideoView mVV;
         
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
        mVV.setOnPreparedListener(this);
        mVV.setOnTouchListener(this);
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

```

It’s pretty simple, in method onCreated , we received the video path from previous activity and prepared the VideoView to convert the video path to video uri. And we set three event listeners to each action. Finally, we implement an override of the method onConfigurationChanged and it will changing the activity orientation when the user rotate the device


## Video Loop

Now we will define the loop behavior for our video . When the video was prepared we set the looping as true.

I have to replace this line below:

```java
mVV.setOnPreparedListener(this);
```

For this one :

```java
mVV.setOnPreparedListener( new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
            mp.setLooping(true);
            }
});
```

## Stopping at touch

We can improve the user experience if we implement a simple control when the user touch the screen during the video exhibition, we can just stop or play the video when the user touch the screen.
For that you have to replace the line below:


```java
mVV.setOnTouchListener(this);
```

For this one:

```java
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
```

## Playing a different video

Since we set “singleLaunch” to property taskMode in the file AndroidManifest.xml, that means if we attempt to launch the VideoPlayer activity while its still running, onCreate() won’t be called, so it won’t play a different video. In this case, the onNewIntent() function must be called with the new intent. We simply parse the extras bundle to get the video path and it will play the new file. Please insert this code in the file VideoPlayerActivity.java :

```java
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
```

Calling VidePlayer Activity

Now, I have just to call our activity from another one passing the video path.

It can be done in that way!

```java
private void playVideo(String videoPath) {
    Intent videoPlaybackActivity = new Intent(this, VideoPlayerActivity.class);
    videoPlaybackActivity.putExtra("videoPath", videoPath);
    startActivity(videoPlaybackActivity);
}
```

I hope, everything is clear and If you have any doubts or found any bug please comment below!

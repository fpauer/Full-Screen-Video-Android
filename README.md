# Full screen video in an android app for any screen orientation

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
package etn.app.danghoc.sevicenhac;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class MediaPlayerService extends Service {
    MediaPlayer myMediaPlayer;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        myMediaPlayer = MediaPlayer.create(this,R.raw.kac);
        myMediaPlayer.setLooping(true);
    }
    @Override
    public void onDestroy() {
         myMediaPlayer.stop(); }

    @Override
    public void onStart(Intent intent, int startId) {
         myMediaPlayer.start(); }
}

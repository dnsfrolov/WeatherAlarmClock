package com.softmiracle.materialweatherclock.ui.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.softmiracle.materialweatherclock.R;
import com.softmiracle.materialweatherclock.alarm.AlarmManagerHelper;
import com.softmiracle.materialweatherclock.models.alarm.AlarmModel;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Denys on 17.01.2017.
 */
public class BootAlarmActivity extends AppCompatActivity {

    @Bind(R.id.boot_alarm_off)
    RelativeLayout rvAlarmOff;
    @Bind(R.id.boot_put_off)
    RelativeLayout rvPutOff;

    private AlarmModel alarm;
    private MediaPlayer mediaPlayer;
    private Vibrator vibration;

    public static final String ALARM_CLOCK = "alarm_clock";
    public static final String ONLY_ONCE = "Only once";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boot_alarm);
        ButterKnife.bind(this);

        alarm = (AlarmModel) getIntent().getSerializableExtra(ALARM_CLOCK);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        final int remind = alarm.remind;
//        if (remind == 0) {
//
//        }

        startPlayingRing();

        if (alarm.vibrate) {
            startVibrate();
        }

        rvPutOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stopPlayRing();
                if (alarm.vibrate) {
                    stopVibrate();
                }

                long nextTime = System.currentTimeMillis() + 1000 * 60 * remind;
                Intent intent = new Intent(BootAlarmActivity.this, BootAlarmActivity.class);
                intent.putExtra(ALARM_CLOCK, alarm);
                PendingIntent pi = PendingIntent.getActivity(BootAlarmActivity.this,
                        alarm.id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) BootAlarmActivity.this
                        .getSystemService(ALARM_SERVICE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, nextTime, pi);
                } else {
                    alarmManager.set(AlarmManager.RTC_WAKEUP, nextTime, pi);
                }
                Log.d("TAG", nextTime + "");
                finish();
            }
        });

        rvAlarmOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!alarm.repeat.equals(ONLY_ONCE)) {
                    AlarmManagerHelper.startAlarmClock(BootAlarmActivity.this, alarm);
                }
                finish();
            }
        });
    }

    private void stopVibrate() {
        vibration.cancel();
    }

    private void startVibrate() {
        vibration = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        long[] pattern = new long[]{1000, 1000, 1000, 1000};
        vibration.vibrate(pattern, 2);
    }

    private void stopPlayRing() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    private void startPlayingRing() {
        RingtoneManager ringtoneManager = new RingtoneManager(this);
        ringtoneManager.setType(RingtoneManager.TYPE_ALARM);
        ringtoneManager.getCursor();

        Uri ringtoneUri = ringtoneManager.getRingtoneUri(alarm.ringPosition);
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_ALARM, 7, AudioManager.ADJUST_SAME);
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(this, ringtoneUri);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
            mediaPlayer.prepare();
            mediaPlayer.start();
            mediaPlayer.setLooping(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        stopPlayRing();
        if (alarm.vibrate) {
            stopVibrate();
        }
    }
}

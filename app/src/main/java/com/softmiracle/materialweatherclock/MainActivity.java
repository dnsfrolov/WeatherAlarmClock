package com.softmiracle.materialweatherclock;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.softmiracle.materialweatherclock.alarm.AlarmClockBuilder;
import com.softmiracle.materialweatherclock.alarm.AlarmManagerHelper;
import com.softmiracle.materialweatherclock.alarm.db.AlarmDBUtils;
import com.softmiracle.materialweatherclock.models.alarm.AlarmModel;
import com.softmiracle.materialweatherclock.ui.AddAlarmActivity;
import com.softmiracle.materialweatherclock.ui.EditAlarmActivity;

import java.util.List;

import butterknife.Bind;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.add_alarmlist)
    RecyclerView recyclerView;
    @Bind(R.id.floating_action_btn)
    FloatingActionButton floatingActionButton;
    @Bind(R.id.alarmlist_time)
    TextView tvTime;
    @Bind(R.id.alarmlist_sun)
    TextView tvSun;
    @Bind(R.id.alarmlist_mon)
    TextView tvMon;
    @Bind(R.id.alarmlist_tue)
    TextView tvTue;
    @Bind(R.id.alarmlist_wed)
    TextView tvWed;
    @Bind(R.id.alarmlist_thu)
    TextView tvThu;
    @Bind(R.id.alarmlist_fri)
    TextView tvFri;
    @Bind(R.id.alarmlist_sat)
    TextView tvSat;
    @Bind(R.id.alarmlist_switch)
    SwitchCompat switchCompat;

    private List<AlarmModel> alarmList;
    private AlarmAdapter alarmAdapter;

    public static final String WEEK_DAY = "Week day";
    private static final String BOOT = "boot";
    private static final String FLAG = "flag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0) {
                    recyclerView.setAdapter(alarmAdapter);
                }
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences(BOOT, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                boolean flag = sharedPreferences.getBoolean(FLAG, false);

                if (!flag) {
                    initDB();
                    editor.putBoolean(FLAG, true);
                    editor.apply();
                }

                alarmList = AlarmDBUtils.queryAlarmClock(MainActivity.this);
                alarmAdapter.notifyDataSetChanged();
                Message message = handler.obtainMessage();
                message.what = 0;
                handler.sendMessage(message);
            }
        }).start();

        /*floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(AddAlarmActivity.newIntent(MainActivity.this));
            }
        });*/

        alarmAdapter = new AlarmAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        startService(new Intent(this, AlarmClockService.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        alarmList = AlarmDBUtils.queryAlarmClock(this);
        alarmAdapter.notifyDataSetChanged();
    }


    private class AlarmAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View mView = LayoutInflater.from(MainActivity.this).inflate(R.layout.alarm_list, parent, false);
            return new AlarmViewHolder(mView);
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
            final AlarmViewHolder alarmViewHolder = (AlarmViewHolder) holder;
            alarmViewHolder.initData(holder.getAdapterPosition());
            alarmViewHolder.alarmCardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    startActivity(new Intent(EditAlarmActivity.newIntent(MainActivity.this, alarmList.get(holder.getAdapterPosition()).id)));
                    return false;
                }
            });
        }

        @Override
        public int getItemCount() {
            return alarmList.size();
        }
    }


    private class AlarmViewHolder extends RecyclerView.ViewHolder {

        CardView alarmCardView;

        AlarmViewHolder(View realContentView) {
            super(realContentView);
            alarmCardView = (CardView) findViewById(R.id.alarmlist_cView);
        }

        void initData(final int adapterPosition) {
            final AlarmModel alarm = alarmList.get(adapterPosition);

            setViewVisible(tvSun, alarm.sunday);
            setViewVisible(tvMon, alarm.monday);
            setViewVisible(tvTue, alarm.tuesday);
            setViewVisible(tvWed, alarm.wednesday);
            setViewVisible(tvThu, alarm.thursday);
            setViewVisible(tvFri, alarm.friday);
            setViewVisible(tvSat, alarm.saturday);

            String hour = (alarm.hour >= 10 ? alarm.hour + "" : "0" + alarm.hour);
            String minute = alarm.minute >= 10 ? alarm.minute + "" : "0" + alarm.minute;
            tvTime.setText(hour + ":" + minute);

            if (alarm.enable) {
                enableTextColor();
            } else {
                disableTextColor();
            }

            switchCompat.setOnCheckedChangeListener(null);
            switchCompat.setChecked(alarm.enable);
            switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    alarm.setEnable(isChecked);
                    alarmAdapter.notifyItemChanged(adapterPosition);

                    if (isChecked) {
                        enableTextColor();
                        AlarmManagerHelper.startAlarmClock(MainActivity.this, alarm);
                    } else {
                        disableTextColor();
                        AlarmManagerHelper.cancelAlarmClock(MainActivity.this, alarm.id);
                    }
                    AlarmDBUtils.updateAlarmClock(MainActivity.this, alarm);
                }
            });
        }

        private void disableTextColor() {
            tvTime.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorTeal_800));
            tvSun.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorTeal_800));
            tvMon.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorTeal_800));
            tvTue.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorTeal_800));
            tvWed.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorTeal_800));
            tvThu.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorTeal_800));
            tvFri.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorTeal_800));
            tvSat.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorTeal_800));
        }

        private void enableTextColor() {
            tvTime.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorTeal_100));
            tvSun.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorTeal_100));
            tvMon.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorTeal_100));
            tvTue.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorTeal_100));
            tvWed.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorTeal_100));
            tvThu.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorTeal_100));
            tvFri.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorTeal_100));
            tvSat.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorTeal_100));
        }

        private void setViewVisible(View view, boolean isVisible) {
            view.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        }
    }

    private void initDB() {
        AlarmClockBuilder clockBuilder = new AlarmClockBuilder();
        AlarmModel alarm = clockBuilder.enable(true)
                .hour(7)
                .minute(0)
                .repeat(WEEK_DAY)
                .sunday(false)
                .monday(true)
                .tuesday(true)
                .wednesday(true)
                .thursday(true)
                .friday(true)
                .saturday(false)
                .ringPosition(0)
                .ring(firstRing(this))
                .volume(10)
                .vibrate(true)
                .remind(3)
                .builder(0);

        AlarmDBUtils.insertAlarmClock(this, alarm);
    }

    private String firstRing(Context context) {
        RingtoneManager ringtoneManager = new RingtoneManager(context);
        ringtoneManager.setType(RingtoneManager.TYPE_ALARM);
        Cursor cursor = ringtoneManager.getCursor();
        String ringName = null;

        while (cursor.moveToNext()) {
            ringName = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX);
            if (ringName != null) {
                break;
            }
        }
        return ringName;
    }
}

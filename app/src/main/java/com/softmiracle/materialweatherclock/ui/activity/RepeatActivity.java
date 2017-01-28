package com.softmiracle.materialweatherclock.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.softmiracle.materialweatherclock.R;
import com.softmiracle.materialweatherclock.alarm.AlarmClockBuilder;
import com.softmiracle.materialweatherclock.alarm.AlarmClockLab;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Denys on 27.01.2017.
 */
public class RepeatActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.repeat_once)
    TextView tvOnce;
    @Bind(R.id.repeat_weekday)
    TextView tvWeekDay;
    @Bind(R.id.repeat_everyday)
    TextView tvEveryDay;
    @Bind(R.id.repeat_weekend)
    TextView tvWeekend;
    @Bind(R.id.repeat_choice)
    TextView tvChoice;

    private AlarmClockLab alarmClockLab;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, RepeatActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repeat);
        ButterKnife.bind(this);

        alarmClockLab = new AlarmClockBuilder().builderLab(0);

        tvOnce.setOnClickListener(this);
        tvWeekDay.setOnClickListener(this);
        tvEveryDay.setOnClickListener(this);
        tvWeekend.setOnClickListener(this);
        tvChoice.setOnClickListener(this);

        String repeat = alarmClockLab.repeat;
        if (repeat.equals(tvOnce.getText().toString())) {
            tvOnce.setTextColor(ContextCompat.getColor(this, R.color.colorTeal_800));
        } else if (repeat.equals(tvWeekDay.getText().toString())) {
            tvWeekDay.setTextColor(ContextCompat.getColor(this, R.color.colorTeal_800));
        } else if (repeat.equals(tvEveryDay.getText().toString())) {
            tvEveryDay.setTextColor(ContextCompat.getColor(this, R.color.colorTeal_800));
        } else if (repeat.equals(tvWeekend.getText().toString())) {
            tvWeekend.setTextColor(ContextCompat.getColor(this, R.color.colorTeal_800));
        } else if (repeat.equals(tvChoice.getText().toString())) {
            tvChoice.setTextColor(ContextCompat.getColor(this, R.color.colorTeal_800));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.repeat_once:
                //tvOnce.setTextColor(ContextCompat.getColor(this, R.color.colorTeal_800));
                alarmClockLab.setRepeat(tvOnce.getText().toString());
                alarmClockLab.setSunday(false);
                alarmClockLab.setMonday(false);
                alarmClockLab.setTuesday(false);
                alarmClockLab.setWednesday(false);
                alarmClockLab.setThursday(false);
                alarmClockLab.setFriday(false);
                alarmClockLab.setSaturday(false);
                break;

            case R.id.repeat_weekday:
                alarmClockLab.setRepeat(tvWeekDay.getText().toString());
                alarmClockLab.setSunday(false);
                alarmClockLab.setMonday(true);
                alarmClockLab.setTuesday(true);
                alarmClockLab.setWednesday(true);
                alarmClockLab.setThursday(true);
                alarmClockLab.setFriday(true);
                alarmClockLab.setSaturday(false);
                break;

            case R.id.repeat_everyday:
                alarmClockLab.setRepeat(tvEveryDay.getText().toString());
                alarmClockLab.setSunday(true);
                alarmClockLab.setMonday(true);
                alarmClockLab.setTuesday(true);
                alarmClockLab.setWednesday(true);
                alarmClockLab.setThursday(true);
                alarmClockLab.setFriday(true);
                alarmClockLab.setSaturday(true);
                break;

            case R.id.repeat_weekend:
                alarmClockLab.setRepeat(tvWeekend.getText().toString());
                alarmClockLab.setSunday(true);
                alarmClockLab.setMonday(false);
                alarmClockLab.setTuesday(false);
                alarmClockLab.setWednesday(false);
                alarmClockLab.setThursday(false);
                alarmClockLab.setFriday(false);
                alarmClockLab.setSaturday(true);
                break;

            case R.id.repeat_choice:
                alarmClockLab.setRepeat(tvChoice.getText().toString());
                startActivity(RepeatChoiceActivity.newIntent(this));
                break;

            default:
                break;
        }
        finish();
    }
}

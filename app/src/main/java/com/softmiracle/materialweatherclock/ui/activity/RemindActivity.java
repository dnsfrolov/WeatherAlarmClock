package com.softmiracle.materialweatherclock.ui.activity;

import android.os.Bundle;
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
public class RemindActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.remind_three)
    TextView tv_remind_three;
    @Bind(R.id.remind_five)
    TextView tv_remind_five;
    @Bind(R.id.remind_ten)
    TextView tv_remind_ten;
    @Bind(R.id.remind_twenty)
    TextView tv_remind_twenty;
    @Bind(R.id.remind_half_hour)
    TextView tv_remind_half_hour;

    private AlarmClockLab alarmClockLab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind);
        ButterKnife.bind(this);

        tv_remind_three.setOnClickListener(this);
        tv_remind_five.setOnClickListener(this);
        tv_remind_ten.setOnClickListener(this);
        tv_remind_twenty.setOnClickListener(this);
        tv_remind_half_hour.setOnClickListener(this);

        alarmClockLab = new AlarmClockBuilder().builderLab(0);
        int remind = alarmClockLab.remind;
        switch (remind) {
            case 3:
                tv_remind_three.setTextColor(ContextCompat.getColor(this, R.color.colorRed_500));
                break;
            case 5:
                tv_remind_five.setTextColor(ContextCompat.getColor(this, R.color.colorRed_500));
                break;
            case 10:
                tv_remind_ten.setTextColor(ContextCompat.getColor(this, R.color.colorRed_500));
                break;
            case 20:
                tv_remind_twenty.setTextColor(ContextCompat.getColor(this, R.color.colorRed_500));
                break;
            case 30:
                tv_remind_half_hour.setTextColor(ContextCompat.getColor(this, R.color.colorRed_500));
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.remind_three:
                alarmClockLab.setRemind(3);
                break;
            case R.id.remind_five:
                alarmClockLab.setRemind(5);
                break;
            case R.id.remind_ten:
                alarmClockLab.setRemind(10);
                break;
            case R.id.remind_twenty:
                alarmClockLab.setRemind(20);
                break;
            case R.id.remind_half_hour:
                alarmClockLab.setRemind(30);
                break;
            default:
                break;
        }
        finish();
    }
}

package com.softmiracle.materialweatherclock.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.softmiracle.materialweatherclock.R;
import com.softmiracle.materialweatherclock.alarm.AlarmManagerHelper;
import com.softmiracle.materialweatherclock.alarm.db.AlarmDBUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Denys on 28.01.2017.
 */
public class DeleteActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String ALARM_ID = "id";
    @Bind(R.id.delete_cancel)
    TextView tvCancel;
    @Bind(R.id.delete_ok)
    TextView tvOk;

    private int id;

    public static Intent newIntent(Context context, int id) {
        Intent intent = new Intent(context, DeleteActivity.class);
        intent.putExtra(ALARM_ID, id);
        return intent;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        ButterKnife.bind(this);

        id = getIntent().getIntExtra(ALARM_ID, 0);
        tvCancel.setOnClickListener(this);
        tvOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.delete_ok) {
            AlarmManagerHelper.cancelAlarmClock(this, id);
            AlarmDBUtils.deleteAlarmClock(this, id);
        }
        finish();
    }
}

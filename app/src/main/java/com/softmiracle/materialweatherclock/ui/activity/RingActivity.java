package com.softmiracle.materialweatherclock.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softmiracle.materialweatherclock.R;
import com.softmiracle.materialweatherclock.alarm.AlarmClockBuilder;
import com.softmiracle.materialweatherclock.alarm.AlarmClockLab;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Denys on 27.01.2017.
 */
public class RingActivity extends AppCompatActivity {

    @Bind(R.id.ring_list)
    RecyclerView rv_ringList;

    private List<String> ringList;
    private AlarmClockLab alarmClockLab;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, RingActivity.class);
        return intent;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring);
        ButterKnife.bind(this);

        alarmClockLab = new AlarmClockBuilder().builderLab(0);

        ringList = showRingList(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.scrollToPositionWithOffset(alarmClockLab.ringPosition, 100);
        rv_ringList.setLayoutManager(linearLayoutManager);
        rv_ringList.setAdapter(new RingAdapter());
    }


    private List<String> showRingList(Context context) {
        List<String> ringNameList = new ArrayList<>();
        RingtoneManager ringtoneManager = new RingtoneManager(context);
        ringtoneManager.setType(RingtoneManager.TYPE_ALARM);
        Cursor cursor = ringtoneManager.getCursor();
        while (cursor.moveToNext()) {
            String ringName = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX);
            ringNameList.add(ringName);
        }
        return ringNameList;
    }

    private class RingAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(RingActivity.this).inflate(R.layout.ring_list_item, parent, false);
            return new RingViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((RingViewHolder) holder).initView(position);
        }

        @Override
        public int getItemCount() {
            return ringList.size();
        }
    }


    private class RingViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout ringView;
        private TextView ringText;

        public RingViewHolder(View itemView) {
            super(itemView);
            ringView = (RelativeLayout) itemView.findViewById(R.id.id_ring_item);
            ringText = (TextView) itemView.findViewById(R.id.ring_list_text);
        }

        public void initView(final int position) {
            String ring = ringList.get(position);
            ringText.setText(ring);
            ringText.setTextColor(ring.equals(alarmClockLab.ring) ? ContextCompat.getColor
                    (RingActivity.this, R.color.colorRed_500) : ContextCompat.getColor(RingActivity.this, R.color.colorWhite));

            ringView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alarmClockLab.setRingPosition(position);
                    alarmClockLab.setRing(ringList.get(position));
                    finish();
                }
            });
        }
    }
}

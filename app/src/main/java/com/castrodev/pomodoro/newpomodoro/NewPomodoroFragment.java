package com.castrodev.pomodoro.newpomodoro;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.castrodev.pomodoro.MainActivity;
import com.castrodev.pomodoro.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rodrigocastro on 31/07/16.
 */
public class NewPomodoroFragment extends Fragment implements NewPomodoroContract.View {

    @BindView(R.id.text_count_down_time)
    TextView textCountDownTime;
    @BindView(R.id.fab_start_stop_pomodoro)
    FloatingActionButton fabStartStopPomodoro;

    private boolean running;

    private NewPomodoroContract.UserActionsListener mActionListener;

    public static NewPomodoroFragment newInstance() {
        return new NewPomodoroFragment();
    }

    public NewPomodoroFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActionListener = new NewPomodoroPresenter(this, getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_newpomodoro, container, false);
        ButterKnife.bind(this, root);
        setupView();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mActionListener.updateTimerLength();
    }

    private void setupView() {
        setCountDownTimeRunning(false);
    }


    @OnClick(R.id.fab_start_stop_pomodoro)
    public void startStopPomodoro() {
        if (isCountDownTimeRunning()) {
            Log.i("NEW_POMODORO", "Stopping the counter");
            mActionListener.stopCounter();
        } else {
            Log.i("NEW_POMODORO", "Starting the counter");
            mActionListener.startCounter();
        }

    }

    public boolean isCountDownTimeRunning() {
        return running;
    }

    @Override
    public void showCountDownTime(String time) {
        textCountDownTime.setText(time);
    }

    @Override
    public void setCountDownTimeRunning(boolean running) {
        this.running = running;
        ((MainActivity) getActivity()).setPomodoroRunning(running);
        changeFabIcon(running ? R.drawable.ic_stop : R.drawable.ic_start);
        textCountDownTime.setEnabled(running);
        if(mActionListener!=null)
            mActionListener.updateTimerLength();

    }

    private void changeFabIcon(int iconRes) {
        fabStartStopPomodoro.setImageResource(iconRes);
    }

}

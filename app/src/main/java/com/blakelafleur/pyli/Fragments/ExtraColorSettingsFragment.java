package com.blakelafleur.pyli.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.blakelafleur.pyli.Activies.MainActivity;
import com.blakelafleur.pyli.DataSenders.BasicHttpSender;
import com.blakelafleur.pyli.Operations.BlinkAllOperation;
import com.blakelafleur.pyli.Operations.FadeAllOperation;
import com.blakelafleur.pyli.Operations.OperationInterface;
import com.blakelafleur.pyli.R;

/**
 * Created by blake on 2/21/16.
 */
public class ExtraColorSettingsFragment extends Fragment {
    private static final String API_FADE_ALL = "/fade/_all";
    private static final String API_BLINK_ALL = "/basic/_all";

    private MainActivityFragmentInteractionListener mListener;
    private Button mBlinkRainbow;
    private Button mFadeRainbow;
    private String[] ledStripLabels = {"1", "2", "3", "4", "5"};
    private ArrayAdapter<String> ledStripAdapter;
    private GridView ledStripGrid;
    private SeekBar speedBar;
    private TextView speedLabel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_extra_color_setting, container, false);
        mBlinkRainbow = (Button) view.findViewById(R.id.blink_rainbow_btn);
        mFadeRainbow = (Button) view.findViewById(R.id.fade_rainbow_btn);
        renderCheckboxes(view);
        initBlinkRainbowListener();
        initFadeRainbowListener();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivityFragmentInteractionListener) {
            mListener = (MainActivityFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement MainActivityFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void renderCheckboxes(final View view) {
        ledStripGrid = (GridView) view.findViewById(R.id.led_strips_view);
        ledStripAdapter = new ArrayAdapter<String>(
                view.getContext(),
                android.R.layout.simple_list_item_multiple_choice,
                ledStripLabels
        );
        ledStripGrid.setNumColumns(ledStripAdapter.getCount());
        ledStripGrid.setAdapter(ledStripAdapter);
    }

    private void initBlinkRainbowListener() {
        this.mBlinkRainbow.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initFadeRainbowListener() {
        this.mFadeRainbow.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void sendAction(View view, String url) {
        SparseBooleanArray checkedBoxes = ledStripGrid.getCheckedItemPositions();
        for (int i = 0; i < ledStripAdapter.getCount(); i++) {
            if (!checkedBoxes.get(i)) {
                continue;
            }
            int pos = ledStripAdapter.getPosition(ledStripLabels[i]);

            float speed = getSpeed(speedBar.getProgress());
            OperationInterface op = null;

            switch (url) {
                case API_BLINK_ALL:
                    op = new BlinkAllOperation(pos, speed);
                    break;
                case API_FADE_ALL:
                    op = new FadeAllOperation(pos, speed);
            }

            new BasicHttpSender(view).send(String.format("http://%s%s", MainActivity.getActiveConnection().getHost(), url), op.toJSON());
        }
    }

    private float getSpeed(int value) {
        return ((float) value / (float) 500);
    }
}

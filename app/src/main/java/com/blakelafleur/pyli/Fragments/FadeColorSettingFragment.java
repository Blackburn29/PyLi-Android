package com.blakelafleur.pyli.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.blakelafleur.pyli.Activies.MainActivity;
import com.blakelafleur.pyli.DataSenders.BasicHttpSender;
import com.blakelafleur.pyli.Operations.FadeOperation;
import com.blakelafleur.pyli.R;
import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.OpacityBar;
import com.larswerkman.holocolorpicker.SVBar;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BasicColorSettingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BasicColorSettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FadeColorSettingFragment extends Fragment implements SeekBar.OnSeekBarChangeListener {

    private static final String API_URL = "/fade/_basic";
    private MainActivityFragmentInteractionListener mListener;
    private ColorPicker mPicker;
    private BasicHttpSender mHttpSender;
    private String[] ledStripLabels = {"1", "2", "3", "4", "5"};
    private ArrayAdapter<String> ledStripAdapter;
    private GridView ledStripGrid;
    private SeekBar speedBar;
    private TextView speedLabel;

    public FadeColorSettingFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FadeColorSettingFragment.
     */
    public static FadeColorSettingFragment newInstance() {
        FadeColorSettingFragment fragment = new FadeColorSettingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
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

    private void initColorPicker(final View view) {
        this.mHttpSender = new BasicHttpSender(view);
        mPicker = (ColorPicker) view.findViewById(R.id.picker);
        SVBar svBar = (SVBar) view.findViewById(R.id.svbar);
        OpacityBar opacityBar = (OpacityBar) view.findViewById(R.id.opacitybar);

        mPicker.setShowOldCenterColor(false);
        mPicker.addSVBar(svBar);
        mPicker.addOpacityBar(opacityBar);
        mPicker.setOnColorChangedListener(new DebouncedOnColorChangedListener(view, 100) {
            @Override
            public void onDebouncedClick(int color) {
                sendColor(view, color);
            }
        });
    }

    private void sendColor(View view, int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        SparseBooleanArray checkedBoxes = ledStripGrid.getCheckedItemPositions();
        for (int i = 0; i < ledStripAdapter.getCount(); i++) {
            if (!checkedBoxes.get(i)) {
                continue;
            }
            int pos = ledStripAdapter.getPosition(ledStripLabels[i]);

            float speed = getSpeed(speedBar.getProgress());

            FadeOperation op = new FadeOperation(pos, speed, hsv[0], hsv[1], hsv[2]);
            Log.d(MainActivity.TAG, op.toJSON().toString());
            new BasicHttpSender(view).send(String.format("http://%s%s", MainActivity.getActiveConnection().getHost(), API_URL), op.toJSON());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fade_color_setting, container, false);
        renderCheckboxes(view);
        initColorPicker(view);

        speedLabel = (TextView) view.findViewById(R.id.speed_label);
        speedBar = (SeekBar) view.findViewById(R.id.speed_bar);
        speedBar.setOnSeekBarChangeListener(this);
        speedBar.setProgress(speedBar.getMax() / 2);
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

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        speedLabel.setText(String.format("%ss", getSpeed(progress)));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    private float getSpeed(int value) {
        return ((float) value / (float) 500);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
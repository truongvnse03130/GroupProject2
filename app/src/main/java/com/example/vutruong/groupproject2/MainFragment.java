package com.example.vutruong.groupproject2;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.logging.Handler;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements View.OnClickListener, HttpGetValue.SendingDataToFragment {

    private Button btnCall, btnAbout;
    private TextView textDisplay;
    private String textContent = "";

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            textContent = savedInstanceState.getString("textViewDisplay");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setView(view);
    }

    public void setView(View view) {
        textDisplay = (TextView) view.findViewById(R.id.text);
        btnAbout = (Button) view.findViewById(R.id.btnAbout);
        btnCall = (Button) view.findViewById(R.id.btnCall);

        textDisplay.setText(textContent);

        btnCall.setOnClickListener(this);
        btnAbout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final HttpGetValue asyncTask = new HttpGetValue();
        asyncTask.setDataInstance(this);
        switch (v.getId()) {
            case R.id.btnAbout:
                getFragmentManager().beginTransaction().replace(R.id.frame_container, new AboutFragment()).addToBackStack(null).commit();
                break;
            case R.id.btnCall:
                android.os.Handler handler = new android.os.Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        asyncTask.execute("https://api.myjson.com/bins/44fql");
                    }
                }, 5000);

                break;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("textViewDisplay", textContent);
    }

    @Override
    public void sendData(String str) {
        textContent = str;
        textDisplay.setText(textContent);
    }
}

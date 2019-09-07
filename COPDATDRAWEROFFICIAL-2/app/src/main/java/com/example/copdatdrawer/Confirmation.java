package com.example.copdatdrawer;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by roseceigler on 3/26/18.
 */

public class Confirmation extends Fragment {
    //removed the BUY button
    //Proceed to Confirmation Precedes PIN
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_purchases, container, false);

        v.findViewById(R.id.btn_buyPinEnter);
        return v;
    }
}
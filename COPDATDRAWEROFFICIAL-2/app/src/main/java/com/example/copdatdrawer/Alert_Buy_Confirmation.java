package com.example.copdatdrawer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by LemelCovington on 3/26/18.
 */

public class Alert_Buy_Confirmation extends Fragment {

    public Alert_Buy_Confirmation() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_buypin, container, false);

        v.findViewById(R.id.btn_buyPinEnter).setOnClickListener(mListener);



        return v;
    }

    private final View.OnClickListener mListener = new View.OnClickListener() {


        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_buyPinEnter:
                    // do stuff

                    Fragment fragment = new shop();
                    android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(null).commit();
                    break;

            }
        }


    };
}
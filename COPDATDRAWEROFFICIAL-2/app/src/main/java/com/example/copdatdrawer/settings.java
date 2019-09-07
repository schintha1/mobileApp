package com.example.copdatdrawer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class settings extends Fragment {
    public int ID;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        //   return inflater.inflate(R.layout.fragment_settings, container, false);
        // }





        View v = inflater.inflate(R.layout.fragment_settings_personal, container, false);



        v.findViewById(R.id.btn_Personal).setOnClickListener(mListener);

        v.findViewById(R.id.btn_Card).setOnClickListener(mListener);

        v.findViewById(R.id.btn_Preferences).setOnClickListener(mListener);


        return v;
    }



    private final View.OnClickListener mListener = new View.OnClickListener() {


        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_Personal:
                    // do stuff

                    Fragment fragment = new settings_Personal();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(null).commit();
                    break;
                case R.id.btn_Card:
                    // do stuff
                    Fragment fragment2 = new settings_Card();
                    FragmentManager fragmentManager2 = getActivity().getSupportFragmentManager();
                    fragmentManager2.beginTransaction().replace(R.id.content_frame, fragment2).addToBackStack(null).commit();
                    break;
                case R.id.btn_Preferences:
                    // do stuff
                    Fragment fragment3 = new settings_Preferences();
                    FragmentManager fragmentManager3 = getActivity().getSupportFragmentManager();
                    fragmentManager3.beginTransaction().replace(R.id.content_frame, fragment3).addToBackStack(null).commit();
                    break;
            }
        }
    };


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("User Settings");
    }
}
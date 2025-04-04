package com.example.platewise;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link HomeFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class HomeFragment extends Fragment {

//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment HomeFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static HomeFragment newInstance(String param1, String param2) {
//        HomeFragment fragment = new HomeFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view=inflater.inflate(R.layout.fragment_home, container, false);
        ImageButton img1=view.findViewById(R.id.imageButton);
        ImageButton img2=view.findViewById(R.id.imageButton2);
        ImageButton mapbtn=view.findViewById(R.id.mapbtn);

        img1.setOnClickListener(v -> {
            // Handle first button click
            Intent intent = new Intent(getActivity(), EventInfo.class); // Replace AnotherActivity with the target activity class
            startActivity(intent);
            Toast.makeText(getActivity(), "Button 1 clicked", Toast.LENGTH_SHORT).show();
        });

        img2.setOnClickListener(v -> {
            // Handle second button click
            Intent intent = new Intent(getActivity(), leftfood.class); // Replace AnotherActivity with the target activity class
            startActivity(intent);
            Toast.makeText(getActivity(), "Button 2 clicked", Toast.LENGTH_SHORT).show();
        });
        mapbtn.setOnClickListener(v -> {
        Intent intent=new Intent(getActivity(),MapsActivity.class);
        startActivity(intent);
        Toast.makeText(getActivity(), "Button map clicked", Toast.LENGTH_SHORT).show();
        });
        return view;
    }
}
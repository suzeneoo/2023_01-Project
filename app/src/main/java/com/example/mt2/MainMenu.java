package com.example.mt2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class MainMenu extends Fragment {

    private View view;
    private Button button3;
    private Button button6;
    private Button button7;
    private Button button4;
    private Button button8;
    private Button button2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_main_menu, container, false);

        button2 = view.findViewById(R.id.button2);
        button3 = view.findViewById(R.id.button3);
        button4 = view.findViewById(R.id.button4);
        button6 = view.findViewById(R.id.button6);
        button7 = view.findViewById(R.id.button7);
        button8 = view.findViewById(R.id.button8);

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //프래그먼트 이동
                Bundle bundle = new Bundle(); //무언가 담을 준비를 할 수 있는 보따리
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                Fragment_menu_button3 fragbutton3 = new Fragment_menu_button3();
                fragbutton3.setArguments(bundle);
                transaction.replace(R.id.menu_frame_layout,fragbutton3);
                transaction.commit(); //저장
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //프래그먼트 이동
                Bundle bundle = new Bundle(); //무언가 담을 준비를 할 수 있는 보따리
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                Fragment_menu_button4 fragbutton4 = new Fragment_menu_button4();
                fragbutton4.setArguments(bundle);
                transaction.replace(R.id.menu_frame_layout,fragbutton4);
                transaction.commit(); //저장
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //프래그먼트 이동
                Bundle bundle = new Bundle(); //무언가 담을 준비를 할 수 있는 보따리
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                Fragment_menu_button6 fragbutton6 = new Fragment_menu_button6();
                fragbutton6.setArguments(bundle);
                transaction.replace(R.id.menu_frame_layout,fragbutton6);
                transaction.commit(); //저장
            }
        });
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //프래그먼트 이동
                Bundle bundle = new Bundle(); //무언가 담을 준비를 할 수 있는 보따리
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                Fragment_menu_button7 fragbutton7 = new Fragment_menu_button7();
                fragbutton7.setArguments(bundle);
                transaction.replace(R.id.menu_frame_layout,fragbutton7);
                transaction.commit(); //저장
            }
        });
        button2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle(); //무언가 담을 준비를 할 수 있는 보따리
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                Fragment_menu_button2 fragbutton2 = new Fragment_menu_button2();
                fragbutton2.setArguments(bundle);
                transaction.replace(R.id.menu_frame_layout,fragbutton2);
                transaction.commit(); //저장
            }
        });

        return view;


    }


}


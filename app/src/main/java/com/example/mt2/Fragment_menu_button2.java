package com.example.mt2;

import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

public class Fragment_menu_button2 extends Fragment {

    private View view;

    private ImageButton imageButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_menu_button2, container, false);

        imageButton = view.findViewById(R.id.imageButton);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle(); //무언가 담을 준비를 할 수 있는 보따리
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                MainMenu mainmenu = new MainMenu();
                mainmenu.setArguments(bundle);
                transaction.replace(R.id.menu_frame_layout,mainmenu);
                transaction.commit(); //저장
            }
        });

        return view;
    }
}
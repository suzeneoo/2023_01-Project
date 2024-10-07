package com.example.mt2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class MainMenuSearch extends Fragment {

    private EditText editText;
    private Button searchbtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_search, container, false);

        editText = view.findViewById(R.id.editText);
        searchbtn = view.findViewById(R.id.searchbtn);

        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Bundle bundle = new Bundle(); //무언가 담을 준비를 할 수 있는 보따리
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                    fragment_shape_result fragment_shape_result = new fragment_shape_result();
                    fragment_shape_result.setArguments(bundle);
                    transaction.replace(R.id.menu_frame_layout,fragment_shape_result);
                    transaction.commit(); //저장
            }
        });

        return view;
    }
}
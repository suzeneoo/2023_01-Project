package com.example.mt2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class fragment_shape_result extends Fragment {
    TextView textview;
    ListView listView;
    View view;
    FillInfoArrayList fillIfoArrayList = new FillInfoArrayList();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_shape_result, container, false);

        ArrayList<FillInfo> arrayList = fillIfoArrayList.getArrayList();
        listView = (ListView) view.findViewById(R.id.listView);
        final FillInfoAdaptor.FillInfoAdapter adapter = new FillInfoAdaptor.FillInfoAdapter(getActivity().getApplicationContext(), arrayList);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Toast.makeText(getActivity().getApplicationContext(), adapter.getItem(position).getItemName()+"", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }
}
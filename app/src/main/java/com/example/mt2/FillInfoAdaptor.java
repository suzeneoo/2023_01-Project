package com.example.mt2;

import android.view.LayoutInflater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class FillInfoAdaptor {


    public static class FillInfoAdapter extends BaseAdapter {
        Context mContext = null;
        LayoutInflater mLayoutInflater = null;
        ArrayList<FillInfo> sample;


        public FillInfoAdapter(Context context, ArrayList<FillInfo> data) {
            mContext = context;
            sample = data;
            mLayoutInflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            return sample.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public FillInfo getItem(int position) {
            return sample.get(position);
        }

        @Override
        public View getView(int position, View converView, ViewGroup parent) {
            View view = mLayoutInflater.inflate(R.layout.listview_design, null);

            TextView tv_fillName = (TextView) view.findViewById(R.id.tv_fillName);
            TextView tv_efcyQesitm = (TextView) view.findViewById(R.id.tv_efcyQesitm);

            tv_fillName.setText(sample.get(position).getItemName());
            tv_efcyQesitm.setText(sample.get(position).getEfcyQesitm());

//        imageView.setImageResource(sample.get(position).getPoster());

            return view;
        }

    }
}

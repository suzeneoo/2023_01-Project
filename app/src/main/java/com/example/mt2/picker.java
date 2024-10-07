package com.example.mt2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;

// 인생사 새옹지마, 포기하마라 나이스 타이밍이 올거다
public class picker extends AppCompatActivity {

    DatePicker datepicker;
    Button pickerBtn;
    public static String yy,mm,dd;
    public static boolean check=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picker_activity);
        pickerBtn = findViewById(R.id.pickerBtn);
        datepicker = findViewById(R.id.datepicker);

        datepicker.init(datepicker.getYear(), datepicker.getMonth(), datepicker.getDayOfMonth(),

                new DatePicker.OnDateChangedListener() {

                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        yy=Integer.toString(year);
                        mm=Integer.toString(monthOfYear+1);
                        dd=Integer.toString(dayOfMonth);
                        check=true;
                    }
                });

        pickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),signup.class);
                intent.putExtra("yy",yy);
                intent.putExtra("mm",mm);
                intent.putExtra("dd",dd);
                startActivityForResult(intent,1000);
            }
        });
    }
}
package com.example.projetfinal;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private int m_argent;
    private int m_multiplier;
    private int m_clickValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        m_clickValue = 1;
        m_multiplier = 1;
        m_argent = 0;
        setContentView(R.layout.activity_main);
        ImageView clicker = findViewById(R.id.Clicker);
        TextView argent = findViewById(R.id.numberMoney);
        ImageView buttonMain = findViewById(R.id.Clicker_main);
        clicker.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                m_argent += (m_multiplier * m_clickValue);
                argent.setText("" + m_argent);
            }
        });

        buttonMain.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonSwitch(R.layout.activity_main);
            }
        });


    }


    public void buttonSwitch(int v) {
        setContentView(v);
    }
}
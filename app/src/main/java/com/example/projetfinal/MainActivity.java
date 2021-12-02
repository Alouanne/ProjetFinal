package com.example.projetfinal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private int m_argent;
    private int m_multiplier;
    private int m_clickValue;
    private Context contexte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        m_clickValue = 1;
        m_multiplier = 1;
        m_argent = 0;
        contexte = getApplicationContext();
        buttonSwitch(R.layout.activity_main);
        ImageView buttonMain = findViewById(R.id.Clicker_main);

        buttonMain.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonSwitch(R.layout.activity_main);
            }

        });
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //m_argent +=
            }
        }, 0, 1000);
        ImageView buttonshop = findViewById(R.id.Shop_main);
        buttonshop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Toast.makeText(getApplicationContext(),"hello",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), MagasinActivity.class);
                startActivity(intent);
            }

        });

    }


    public void buttonSwitch(int v) {
        setContentView(v);
        if(v == R.layout.activity_main){
            ImageView clicker = findViewById(R.id.Clicker);
            TextView argent = findViewById(R.id.numberMoney);
            argent.setText("" + m_argent);
            clicker.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    m_argent += (m_multiplier * m_clickValue);
                    argent.setText("" + m_argent);
                }
            });
        }
    }

}
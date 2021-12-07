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
    private int[] etatUpgrades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        m_clickValue = 1;
        m_multiplier = 1;
        m_argent = 0;
        etatUpgrades = new int[] {0,0,0,0,0,0,0};
        buttonSwitch(R.layout.activity_main);
        ImageView buttonMain = findViewById(R.id.Clicker_main);

        buttonMain.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonSwitch(R.layout.activity_main);
            }

        });
        TextView argent = findViewById(R.id.numberMoney);

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                m_argent += m_multiplier;
                argent.setText("" + m_argent);
            }
        }, 0, 1000);
        ImageView buttonshop = findViewById(R.id.Shop_main);
        buttonshop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), MagasinActivity.class);
                intent.putExtra("argent", m_argent);
                intent.putExtra("listeUpgrades", etatUpgrades);
                startActivityForResult(intent, 1);
            }

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);

        if (data != null) {
            switch (resultCode) {
                case RESULT_OK:
                    int points = data.getIntExtra("pointage", 100);
                    m_argent = points;
                    ((TextView) findViewById(R.id.numberMoney)).setText(String.valueOf(points));
                    etatUpgrades = data.getIntArrayExtra("listeUpgrades");
                    break;
                default:
                    break;
            }
        }
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
package com.example.projetfinal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private Integer m_argent;
    private int m_multiplier;
    private int m_clickValue;
    private int[] etatUpgrades;
    private String FNAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FNAME = "sauvegardeClicker.txt";

        FileInputStream fis;
        try {
            fis = openFileInput(FNAME);
            byte[] buffer = new byte[1024];
            StringBuilder content = new StringBuilder();
            while ((fis.read(buffer)) !=
                    -1) {
                content.append(new String(buffer));
            }

            String contenu = content.toString();
            Pattern p = Pattern.compile(" ");
            String quantite[] = contenu.split(p.pattern());
            m_argent = Integer.parseInt(quantite[0]);
            etatUpgrades = new int[7];
            for (int i = 1; i < 8; i++)
            {
                etatUpgrades[i-1] = Integer.parseInt(quantite[i]);
            }
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(m_argent == null)
            m_argent = 0;
        if(etatUpgrades == null)
            etatUpgrades = new int[] {0,0,0,0,0,0,0};
        m_clickValue = 1;
        m_multiplier = 1;
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

    @Override
    public void onStop()
    {
        FileOutputStream fos;
        try {
            fos = openFileOutput(FNAME, Context.MODE_PRIVATE);
            fos.write(String.valueOf(m_argent).getBytes());

            for (int i = 0; i < etatUpgrades.length; i++)
            {
                fos.write(" ".getBytes());
                System.out.println(etatUpgrades[i]);
                fos.write(String.valueOf(etatUpgrades[i]).getBytes());
            }
            fos.write(" buffer".getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        super.onStop();
    }
}
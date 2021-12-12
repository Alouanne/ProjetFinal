package com.example.projetfinal;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayout;

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
    private int multPermenant;
    private int pointPerm;
    private int nbClickSecondes;
    public ImageView catClicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FNAME = "sauvegardeClicker.txt";
        nbClickSecondes = 0;
        setContentView(R.layout.activity_main);

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

            m_multiplier= etatUpgrades[0]+etatUpgrades[1]*20+etatUpgrades[2]*90+etatUpgrades[3]*360;
            m_multiplier+= etatUpgrades[4]*2160+etatUpgrades[5]*18100+etatUpgrades[6]*162885;
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

        ImageView clicker = findViewById(R.id.Clicker);
        TextView argent = findViewById(R.id.numberMoney);
        argent.setText("" + m_argent);
        clicker.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                m_argent +=  m_clickValue;
                argent.setText("" + m_argent);
                nbClickSecondes = nbClickSecondes + 1;
            }
        });

        ImageView buttonshopPerm = findViewById(R.id.specialShop_main);
        catClicker = findViewById(R.id.Clicker);
        catClicker.setBackgroundResource(R.drawable.clicker_ripple);
        buttonshopPerm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), MagasinPermenant.class);
                intent.putExtra("argent", m_argent);
                intent.putExtra("listeUpgrades", etatUpgrades);
                intent.putExtra("MultiplicateurPermenant", multPermenant);
                intent.putExtra("PointPermenant", 0);
                startActivityForResult(intent, 2);
            }

        });

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                int m_multiplier= etatUpgrades[0]+etatUpgrades[1]*20+etatUpgrades[2]*90+etatUpgrades[3]*360;
                m_multiplier+= etatUpgrades[4]*2160+etatUpgrades[5]*18100+etatUpgrades[6]*162885;
                if(m_multiplier!=0 && multPermenant!=0){
                    m_multiplier = m_multiplier*multPermenant;
                }else if(multPermenant!= 0){
                    m_multiplier = multPermenant;
                }
                m_argent += m_multiplier;
                argent.setText("" + m_argent);
            }
        }, 0, 1000);

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateBackground();
            }
        }, 0000, 1000);

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

    public void updateBackground()
    {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                if (nbClickSecondes < 3)
                {
                    catClicker.setBackgroundResource(R.drawable.clicker_ripple);
                }
                else
                {
                    catClicker.setBackgroundResource(R.drawable.clicker_ripple_fire);
                }
                nbClickSecondes = 0;
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
                    int multiplic = data.getIntExtra("MultiplicateurPermenant", 0);
                    multPermenant = multiplic;
                    pointPerm = data.getIntExtra("PointPermenant",0);


                    break;
                default:
                    break;
            }
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
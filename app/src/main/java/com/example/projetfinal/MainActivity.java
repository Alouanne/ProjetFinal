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
    private int[] multPermenant;
    private int pointPerm;
    private int nbClickSecondes;
    public ImageView catClicker;
    public static final String FNAME = "sauvegardeClicker.txt";
    public static final int RESULT_SWITCH_TO_MAGASIN = -2;
    public static final int RESULT_SWITCH_TO_MAGASIN_PERMANENT = -3;

    //Extras
    public static final String LISTE_UPGRADES = "listeUpgrades";
    public static final String LISTE_UPGRADES_PERMANENT = "listeUpgradesPermanent";
    public static final String POINTAGE = "pointage";
    public static final String MULTIPLIER = "multiplier";
    public static final String POINTS_PERMANENTS = "ptPerm";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nbClickSecondes = 0;
        setContentView(R.layout.activity_main);

        etatUpgrades = new int[] {0,0,0,0,0,0,0};
        multPermenant = new int[]{1,20,90,360,2160,18100,162885, 1};
        m_multiplier = 0;
        m_clickValue = 1;
        m_argent = 0;
        pointPerm = 0;
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
            for (int i = 1; i < 8; i++)
            {
                etatUpgrades[i-1] = Integer.parseInt(quantite[i]);
            }
            int index = 8;
            for (int i = 0; i < 7; i++)
            {
                if ((index + i) < (quantite.length - 2))
                {
                    multPermenant[i] = Integer.parseInt(quantite[i+index]);
                }
            }
            for (int i = 0; i < etatUpgrades.length; i++) {
                m_multiplier += etatUpgrades[i]*multPermenant[i];
            }
            m_clickValue = multPermenant[7];
            pointPerm = Integer.parseInt(quantite[quantite.length-2]);

            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
                intent.putExtra(MainActivity.POINTAGE, m_argent);
                intent.putExtra(MainActivity.LISTE_UPGRADES, etatUpgrades);
                intent.putExtra(MainActivity.LISTE_UPGRADES_PERMANENT, multPermenant);
                intent.putExtra(MainActivity.POINTS_PERMANENTS, pointPerm);
                intent.putExtra(MULTIPLIER, m_multiplier);
                startActivityForResult(intent, 2);
            }

        });

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                m_argent += m_multiplier;
                argent.setText("" + m_argent);
            }
        }, 1000, 1000);

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
                intent.putExtra(POINTAGE, m_argent);
                intent.putExtra(LISTE_UPGRADES, etatUpgrades);
                intent.putExtra(LISTE_UPGRADES_PERMANENT, multPermenant);
                intent.putExtra(MULTIPLIER, m_multiplier);
                intent.putExtra(POINTS_PERMANENTS, pointPerm);
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

        if (data != null)
        {
                switch (resultCode) {
                    case RESULT_OK:
                        m_argent = data.getIntExtra(POINTAGE, 0);
                        ((TextView) findViewById(R.id.numberMoney)).setText(String.valueOf(m_argent));
                        etatUpgrades = data.getIntArrayExtra(LISTE_UPGRADES);
                        multPermenant = data.getIntArrayExtra(LISTE_UPGRADES_PERMANENT);
                        m_multiplier = data.getIntExtra(MainActivity.MULTIPLIER, 0);
                        pointPerm = data.getIntExtra(MainActivity.POINTS_PERMANENTS, 0);
                        System.out.println("multiplier = " + m_multiplier);
                        for (int i = 0; i < etatUpgrades.length; i++) {
                            m_multiplier += etatUpgrades[i]*multPermenant[i];
                        }
                        break;

                    case RESULT_SWITCH_TO_MAGASIN:
                        data.setClass(getApplicationContext(), MagasinActivity.class);
                        startActivityForResult(data, 1);
                        break;

                    case  RESULT_SWITCH_TO_MAGASIN_PERMANENT:
                        data.setClass(getApplicationContext(), MagasinPermenant.class);
                        startActivityForResult(data, 2);
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
                fos.write(String.valueOf(etatUpgrades[i]).getBytes());
            }
            for (int i = 0; i < multPermenant.length; i++)
            {
                fos.write(" ".getBytes());
                fos.write(String.valueOf(multPermenant[i]).getBytes());
            }
            fos.write(" ".getBytes());
            fos.write(String.valueOf(pointPerm).getBytes());
            fos.write(" buffer".getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("closing mainActivity");
        super.onStop();
    }
}
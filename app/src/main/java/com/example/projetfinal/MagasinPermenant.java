package com.example.projetfinal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MagasinPermenant extends AppCompatActivity {

    private ArrayList<ItemObjPerm> listeUpgrades;
    private Intent intent;
    private RecyclerView mainListView;
    private MagasinAddapter adapter;
    private int m_argent;
    private int[] etatUpgrades;
    private int[] etatPermenant;
    private int pointPerm;
    private int multiplier;
    private int prestige;
    private double statClicksSecondes;
    private int statPointTotals;
    private int statClickTotal;
    private int statReset;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset);
        intent = getIntent();

        etatUpgrades = intent.getIntArrayExtra(MainActivity.LISTE_UPGRADES);
        intent.putExtra(MainActivity.LISTE_UPGRADES, etatUpgrades);
        m_argent = intent.getIntExtra(MainActivity.POINTAGE,0);
        intent.putExtra(MainActivity.POINTAGE, m_argent);
        etatPermenant = intent.getIntArrayExtra(MainActivity.LISTE_UPGRADES_PERMANENT);
        intent.putExtra(MainActivity.LISTE_UPGRADES_PERMANENT, etatPermenant);
        pointPerm = intent.getIntExtra(MainActivity.POINTS_PERMANENTS, 0);
        intent.putExtra(MainActivity.POINTS_PERMANENTS, pointPerm);
        multiplier = intent.getIntExtra(MainActivity.MULTIPLIER, 0);
        intent.putExtra(MainActivity.MULTIPLIER, multiplier);
        statClicksSecondes = intent.getDoubleExtra(MainActivity.STAT_CLICKS_SECONDES,0);
        statClickTotal = intent.getIntExtra(MainActivity.STAT_CLICKS_TOTAL, 0);
        statPointTotals = intent.getIntExtra(MainActivity.STAT_POINTS,0);
        statReset = intent.getIntExtra(MainActivity.STAT_RESET,0);

        prestige = 0;
        int rer;
        for (int i = 0; i < etatUpgrades.length; i++) {
            rer =etatUpgrades[i];
            prestige += 100 * Math.pow(rer,2);
        }

        TextView point =findViewById(R.id.PointReset);
        point.setText(pointPerm + " prestige");

        Button butonReset  = findViewById(R.id.ResetB);
        butonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pointPerm += prestige;
                m_argent = 0;
                for (int i = 0; i < etatUpgrades.length; i++)
                {
                    etatUpgrades[i] = 0;
                }
                multiplier = 0;
                statReset +=1;
                intent.putExtra(MainActivity.POINTAGE, m_argent);
                intent.putExtra(MainActivity.LISTE_UPGRADES_PERMANENT, etatPermenant);
                intent.putExtra(MainActivity.POINTS_PERMANENTS, pointPerm);
                intent.putExtra(MainActivity.LISTE_UPGRADES, etatUpgrades);
                intent.putExtra(MainActivity.MULTIPLIER, multiplier);
                intent.putExtra(MainActivity.STAT_CLICKS_SECONDES, statClicksSecondes);
                intent.putExtra(MainActivity.STAT_CLICKS_TOTAL, statClickTotal);
                intent.putExtra(MainActivity.STAT_POINTS, statPointTotals);
                intent.putExtra(MainActivity.STAT_RESET, statReset);
                setResult(RESULT_OK, intent);
                System.out.println("multiplier = " + multiplier);
                finish();
            }
        });

        butonReset.setText(prestige+"asd");
        listeUpgrades = new ArrayList<>();
        listeUpgrades.add(new ItemObjPerm(1000, 1.5, "Multiplier le revenue des balle de laine par 3", 0,3, "Les balls de laine, c'est parfait", 0));
        listeUpgrades.add(new ItemObjPerm(10000, 1.5, "Multiplier le revenue des par 3", 0,3, "",1));
        listeUpgrades.add(new ItemObjPerm(10000, 1.5, "Multiplier le revenue des par 3", 0,3, "",2));


        mainListView = (RecyclerView) findViewById(R.id.RecucleView);
        adapter = new MagasinAddapter(MagasinPermenant.this, listeUpgrades);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mainListView.setLayoutManager(layoutManager);
        mainListView.setAdapter(adapter);

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                double  prestige = 0;
                int rer;
                for (int i = 0; i < etatUpgrades.length; i++) {
                    rer =etatUpgrades[i];
                        prestige += 100 * Math.pow(rer,2);
                }
                Button butonReset  = findViewById(R.id.ResetB);
                butonReset.setText(""+prestige);
            }
        }, 0, 1000);

        ImageView buttonmain = findViewById(R.id.Clicker_main);
        buttonmain.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                intent.putExtra(MainActivity.POINTAGE, m_argent);
                intent.putExtra(MainActivity.LISTE_UPGRADES, etatUpgrades);
                intent.putExtra(MainActivity.LISTE_UPGRADES_PERMANENT, etatPermenant);
                intent.putExtra(MainActivity.MULTIPLIER, multiplier);
                intent.putExtra(MainActivity.POINTS_PERMANENTS, pointPerm);
                intent.putExtra(MainActivity.STAT_CLICKS_SECONDES, statClicksSecondes);
                intent.putExtra(MainActivity.STAT_CLICKS_TOTAL, statClickTotal);
                intent.putExtra(MainActivity.STAT_POINTS, statPointTotals);
                intent.putExtra(MainActivity.STAT_RESET, statReset);
                setResult(RESULT_OK, intent);
                finish();
            }

        });
        ImageView buttonshop = findViewById(R.id.Shop_main);
        buttonshop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                intent.putExtra(MainActivity.POINTAGE, m_argent);
                intent.putExtra(MainActivity.LISTE_UPGRADES, etatUpgrades);
                intent.putExtra(MainActivity.LISTE_UPGRADES_PERMANENT, etatPermenant);
                intent.putExtra(MainActivity.MULTIPLIER, multiplier);
                intent.putExtra(MainActivity.POINTS_PERMANENTS, pointPerm);
                intent.putExtra(MainActivity.STAT_CLICKS_SECONDES, statClicksSecondes);
                intent.putExtra(MainActivity.STAT_CLICKS_TOTAL, statClickTotal);
                intent.putExtra(MainActivity.STAT_POINTS, statPointTotals);
                intent.putExtra(MainActivity.STAT_RESET, statReset);
                setResult(MainActivity.RESULT_SWITCH_TO_MAGASIN, intent);
                finish();
            }

        });
    }
        public class MagasinAddapter extends RecyclerView.Adapter<MagasinAddapter.MagasinViewHolder>
        {
            private final Context contexte;
            private final ArrayList<ItemObjPerm> listeUpgrades;

            public MagasinAddapter(Context contexte, ArrayList<ItemObjPerm> listeUpgrades)
            {
                this.contexte = contexte;
                this.listeUpgrades = listeUpgrades;

            }

            @Override
            public MagasinAddapter.MagasinViewHolder onCreateViewHolder(final ViewGroup parent, int viewType)
            {
                View view = LayoutInflater.from(contexte).inflate(R.layout.row_reset, parent, false);
                final MagasinAddapter.MagasinViewHolder magasinViewHolder = new MagasinAddapter.MagasinViewHolder(view);


                view.findViewById(R.id.buttonReset).setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        int position = magasinViewHolder.getAdapterPosition();
                        if (pointPerm >= listeUpgrades.get(position).getM_cout()) {

                            pointPerm -= listeUpgrades.get(position).getM_cout();
                            if (etatPermenant[listeUpgrades.get(position).getValeur_changer()] != 0) {
                                etatPermenant[listeUpgrades.get(position).getValeur_changer()] *= listeUpgrades.get(position).getAjout();
                            } else {
                                etatPermenant[listeUpgrades.get(position).getValeur_changer()] = listeUpgrades.get(position).getAjout();
                            }
                            listeUpgrades.get(position).addNumber();
                            Button buttonAchat = (Button) view.findViewById(R.id.buttonReset);
                            buttonAchat.setText(listeUpgrades.get(position).getM_cout() + "");
                            TextView point =findViewById(R.id.PointReset);
                            point.setText(pointPerm + " prestige");

                        }else{
                            Toast.makeText(getApplicationContext(),"Pas assez de prestige", Toast.LENGTH_SHORT).show();
                    }

                }
                });
                return magasinViewHolder;
            }

            @Override
            public void onBindViewHolder(final MagasinAddapter.MagasinViewHolder holder, int position)
            {
                ItemObjPerm itemMagasin = listeUpgrades.get(position);
                holder.textViewUpgrade.setText(itemMagasin.getM_explication());
                holder.buttonAchat.setText(String.valueOf(itemMagasin.getM_cout()));
                holder.textViewTitre.setText(itemMagasin.getM_titre());
            }

            @Override
            public int getItemCount()
            {
                return listeUpgrades.size();
            }            class MagasinViewHolder extends RecyclerView.ViewHolder
            {
                public TextView textViewUpgrade;
                public Button buttonAchat;
                public TextView textViewTitre;
                public MagasinViewHolder(View view)
                {
                    super(view);
                    textViewUpgrade =(TextView)view.findViewById(R.id.infoReset);
                    textViewTitre =(TextView)view.findViewById(R.id.NomSpecial);
                    buttonAchat = (Button) view.findViewById(R.id.buttonReset);
                }
            }
        }
    @Override
    public void onBackPressed()
    {
        intent.putExtra(MainActivity.POINTAGE, m_argent);
        intent.putExtra(MainActivity.LISTE_UPGRADES_PERMANENT, etatPermenant);
        intent.putExtra(MainActivity.POINTS_PERMANENTS, pointPerm);
        intent.putExtra(MainActivity.LISTE_UPGRADES, etatUpgrades);
        intent.putExtra(MainActivity.MULTIPLIER, multiplier);
        intent.putExtra(MainActivity.STAT_CLICKS_SECONDES, statClicksSecondes);
        intent.putExtra(MainActivity.STAT_CLICKS_TOTAL, statClickTotal);
        intent.putExtra(MainActivity.STAT_POINTS, statPointTotals);
        intent.putExtra(MainActivity.STAT_RESET, statReset);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onStop()
    {
        FileOutputStream fos;
        try {
            fos = openFileOutput(MainActivity.FNAME, Context.MODE_PRIVATE);
            fos.write(String.valueOf(m_argent).getBytes());

            for (int i = 0; i < etatUpgrades.length; i++)
            {
                fos.write(" ".getBytes());
                fos.write(String.valueOf(etatUpgrades[i]).getBytes());
            }
            for (int i = 0; i < etatPermenant.length; i++)
            {
                fos.write(" ".getBytes());
                fos.write(String.valueOf(etatPermenant[i]).getBytes());
            }
            fos.write(" ".getBytes());
            fos.write(String.valueOf(pointPerm).getBytes());
            fos.write(" ".getBytes());
            fos.write(String.valueOf(statClickTotal).getBytes());
            fos.write(" ".getBytes());
            fos.write(String.valueOf(statClicksSecondes).getBytes());
            fos.write(" ".getBytes());
            fos.write(String.valueOf(statPointTotals).getBytes());
            fos.write(" ".getBytes());
            fos.write(String.valueOf(statReset).getBytes());
            fos.write(" buffer".getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("closing Magasin Permanent");

        super.onStop();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);

        if (data != null) {
            switch (resultCode) {
                case RESULT_OK:
                    m_argent = data.getIntExtra(MainActivity.POINTAGE, 100);
                    etatUpgrades = data.getIntArrayExtra(MainActivity.LISTE_UPGRADES);
                    etatPermenant = data.getIntArrayExtra(MainActivity.LISTE_UPGRADES_PERMANENT);
                    pointPerm = data.getIntExtra(MainActivity.POINTS_PERMANENTS,0);
                    multiplier = data.getIntExtra(MainActivity.MULTIPLIER,0);

                    break;
                default:
                    break;
            }
        }
        onBackPressed();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_clicker, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.statisitiques:
                builder = new AlertDialog.Builder(MagasinPermenant.this);
                String s1 = getString(R.string.nbClicks);
                String s2 = " " + String.valueOf(statClickTotal) +"\n";
                //s2 = String.format(s2, "%10.3F");
                String s3 = getString(R.string.nbClicksSecondes);
                String s4 = " " +String.valueOf(statClicksSecondes) + "\n";
                String s5 = getString(R.string.nbPoints);
                String s6 = " " +String.valueOf(statPointTotals) + "\n";
                String s7 = getString(R.string.nbResets);
                String s8 = " " +String.valueOf(statReset);
                String message = s1 + s2 +s3+s4+s5+s6+s7+s8;

                builder.setMessage(message).setTitle("Statistiques");
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
            case R.id.aPropos:
                builder = new AlertDialog.Builder(MagasinPermenant.this);
                builder.setMessage("Créateurs:\nJulien Forget\nAléanne Camiré").setTitle("À propos");
                AlertDialog alertDialog2 = builder.create();
                alertDialog2.show();
                break;
            default:
                Toast.makeText(getApplicationContext(), "Help", Toast.LENGTH_LONG).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}



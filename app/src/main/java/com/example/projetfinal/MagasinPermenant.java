package com.example.projetfinal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

        System.out.println("multiplier : " + multiplier);
        prestige = 0;
        int rer;
        for (int i = 0; i < etatUpgrades.length; i++) {
            rer =etatUpgrades[i];
            prestige += 100 * Math.pow(rer,2);
        }

        TextView point =findViewById(R.id.PointReset);



        listeUpgrades = new ArrayList<>();

        listeUpgrades.add(new ItemObjPerm(10000, 1.5, "Multiplier le clickage par 3", etatPermenant[7], 3, "Les main de Dieu",7));
        listeUpgrades.add(new ItemObjPerm(10000, 1.5, "Multiplier le revenue des balles de laine par 3", etatPermenant[0], 3, "Les balls de laine, c'est parfait", 0));
        listeUpgrades.add(new ItemObjPerm(25000, 1.5, "Multiplier le revenue des poissons par 3", etatPermenant[1]/20, 3, "Miam! du poisson",1));
        listeUpgrades.add(new ItemObjPerm(50000, 1.5, "Multiplier le revenue des cloches par 3", etatPermenant[2]/90, 3, "Ding Ding, c'est un chat",2));
        listeUpgrades.add(new ItemObjPerm(75000, 1.5, "Multiplier le revenue des batons par 3", etatPermenant[3]/360, 3, "T'es sur que c'est pas un chien",3));
        listeUpgrades.add(new ItemObjPerm(100000, 1.5, "Multiplier le revenue des souris par 3", etatPermenant[4]/2160, 3, "Un chat sans ça souris, c'est quoi",4));
        listeUpgrades.add(new ItemObjPerm(125000, 1.5, "Multiplier le revenue des roomba par 3", etatPermenant[5]/18100, 3, "Le divertisement infinie du robot",5));
        listeUpgrades.add(new ItemObjPerm(150000, 1.5, "Multiplier le revenue des lasers par 3", etatPermenant[6]/162885, 3, "Vader-cat!Vader-cat",6));



        mainListView = (RecyclerView) findViewById(R.id.RecucleView);
        adapter = new MagasinAddapter(MagasinPermenant.this, listeUpgrades);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mainListView.setLayoutManager(layoutManager);
        mainListView.setAdapter(adapter);


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
                intent.putExtra(MainActivity.LISTE_UPGRADES_PERMANENT, etatPermenant);
                if(m_argent == 0 && etatUpgrades[0]==0) {
                    onBackPressed();
                }else{
                    Toast.makeText(getApplicationContext(),"Erreur", Toast.LENGTH_SHORT).show();

                }
            }
        });
        point.setText(pointPerm + " prestige");

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
                multiplier = 0;
                for (int i = 0; i < etatUpgrades.length; i++) {
                    multiplier += etatUpgrades[i]*etatPermenant[i];
                }
                m_argent += multiplier;
            }
        }, 0, 1000);

        ImageView buttonmain = findViewById(R.id.Clicker_main);
        buttonmain.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                setResult(RESULT_OK, intent);
                finish();
            }

        });
        ImageView buttonshop = findViewById(R.id.Shop_main);
        buttonshop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent intente = new Intent(getApplicationContext(), MagasinActivity.class);
                intente.putExtra(MainActivity.POINTAGE, m_argent);
                intente.putExtra(MainActivity.LISTE_UPGRADES_PERMANENT, etatPermenant);
                intente.putExtra(MainActivity.POINTS_PERMANENTS, pointPerm);
                intente.putExtra(MainActivity.LISTE_UPGRADES, etatUpgrades);
                intente.putExtra(MainActivity.MULTIPLIER,multiplier);
                startActivityForResult(intente, 2);

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
                System.out.println(listeUpgrades.get(0).getM_explication());


                view.findViewById(R.id.buttonReset).setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        int position = magasinViewHolder.getAdapterPosition();
                        if (pointPerm >= listeUpgrades.get(position).getM_cout()) {

                            pointPerm -= listeUpgrades.get(position).getM_cout();
                            etatPermenant[listeUpgrades.get(position).getValeur_changer()] *= listeUpgrades.get(position).getAjout();
                            listeUpgrades.get(position).addNumber();
                            Button buttonAchat = (Button) view.findViewById(R.id.buttonReset);
                            buttonAchat.setText(listeUpgrades.get(position).getM_cout() + "");
                            TextView point =findViewById(R.id.PointReset);
                            point.setText(pointPerm + " prestige");
                            intent.putExtra(MainActivity.LISTE_UPGRADES_PERMANENT, etatPermenant);

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
                System.out.println(etatUpgrades[i]);
                fos.write(String.valueOf(etatUpgrades[i]).getBytes());
            }
            for (int i = 0; i < etatPermenant.length; i++)
            {
                fos.write(" ".getBytes());
                System.out.println(etatPermenant[i]);
                fos.write(String.valueOf(etatPermenant[i]).getBytes());
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
}



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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset);
        intent = getIntent();

        etatUpgrades = intent.getIntArrayExtra("listeUpgrades");
        m_argent = intent.getIntExtra("argent",0);
        etatPermenant = intent.getIntArrayExtra("MultiplicateurPermenant");
        pointPerm = intent.getIntExtra("PointPermenant", 0);
        etatUpgrades = intent.getIntArrayExtra("listeUpgrades");

        double  prestige = 0;
        int rer;
        for (int i = 0; i < etatUpgrades.length; i++) {
            rer =etatUpgrades[i];
            do {
                prestige += 100 * Math.pow(rer,2);
            }while(rer >50);
        }
        TextView point =findViewById(R.id.PointReset);
        point.setText(pointPerm + " prestige");

        Button butonReset  = findViewById(R.id.ResetB);
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
                int m_multiplier= etatUpgrades[0]*etatPermenant[0]+etatUpgrades[1]*etatPermenant[1]+etatUpgrades[2]*etatPermenant[2]+etatUpgrades[3]*etatPermenant[3];
                m_multiplier+= etatUpgrades[4]*etatPermenant[7]+etatUpgrades[5]*etatPermenant[5]+etatUpgrades[6]*etatPermenant[6];
                m_argent += m_multiplier;
                double  prestige = 0;
                int rer;
                for (int i = 0; i < etatUpgrades.length; i++) {
                    rer =etatUpgrades[i];
                    do {
                        prestige += 100 * Math.pow(rer,2);
                    }while(rer >50);
                }
                Button butonReset  = findViewById(R.id.ResetB);
                butonReset.setText(""+prestige);
            }
        }, 0, 1000);
        ImageView buttonmain = findViewById(R.id.Clicker_main);
        buttonmain.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                intent.putExtra("pointage", m_argent);
                intent.putExtra("MultiplicateurPermenant", etatPermenant);
                intent.putExtra("pointPerm", pointPerm);
                setResult(RESULT_OK, intent);
                finish();
            }

        });
        ImageView buttonshop = findViewById(R.id.Shop_main);
        buttonshop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent intente = new Intent(getApplicationContext(), MagasinActivity.class);
                intente.putExtra("pointage", m_argent);
                intente.putExtra("MultiplicateurPermenant", etatPermenant);
                intente.putExtra("pointPerm", pointPerm);
                intente.putExtra("listeUpgrades", etatUpgrades);
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
        intent.putExtra("pointage", m_argent);
        intent.putExtra("MultiplicateurPermenant", etatPermenant);
        intent.putExtra("pointPerm", pointPerm);
        intent.putExtra("listeUpgrades", etatUpgrades);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onStop()
    {
        FileOutputStream fos;
        try {
            fos = openFileOutput("sauvegardeClicker.txt", Context.MODE_PRIVATE);
            fos.write(String.valueOf(m_argent).getBytes());

            for (int i = 0; i < etatUpgrades.length; i++)
            {
                fos.write(" ".getBytes());
                System.out.println(etatUpgrades[i]);
                fos.write(String.valueOf(etatUpgrades[i]).getBytes());
            }
            fos.write(" buffer".getBytes());
            fos.close();
            System.out.println("rerussite");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("fin");

        super.onStop();
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
                    etatUpgrades = data.getIntArrayExtra("listeUpgrades");
                    etatPermenant = data.getIntArrayExtra("MultiplicateurPermenant");
                    pointPerm = data.getIntExtra("pointPerm",0);


                    break;
                default:
                    break;
            }
        }
        onBackPressed();
    }
}



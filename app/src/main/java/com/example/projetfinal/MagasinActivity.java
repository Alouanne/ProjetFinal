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

public class MagasinActivity extends AppCompatActivity {

    private RecyclerView mainListView;
    private ArrayList<ItemMagasin> listeUpgrades;
    private MagasinAdapter adapter;
    private int m_argent;
    private int[] etatUpgrades;
    private Intent intent;
    private int[] etatPermenant;
    private int pointPerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.magasin_layout);
        intent = getIntent();

        etatUpgrades = intent.getIntArrayExtra("listeUpgrades");
        m_argent = intent.getIntExtra("argent",0);
        intent.putExtra("listeUpgrades", etatUpgrades);
        etatPermenant = intent.getIntArrayExtra("MultiplicateurPermenant");
        ((TextView) findViewById(R.id.textViewArgent)).setText(String.valueOf(m_argent));



        listeUpgrades = new ArrayList<>();
        listeUpgrades.add(new ItemMagasin(getString(R.string.ballFil), 2,2,1.07, R.drawable.yarn, etatUpgrades[0]));
        listeUpgrades.add(new ItemMagasin(getString(R.string.poisson), 72,72,1.15,R.drawable.fish, etatUpgrades[1]));
        listeUpgrades.add(new ItemMagasin(getString(R.string.cloche), 749,749,1.14,R.drawable.bell, etatUpgrades[2]));
        listeUpgrades.add(new ItemMagasin(getString(R.string.baton), 9752,9752,1.13,R.drawable.stick, etatUpgrades[3]));
        listeUpgrades.add(new ItemMagasin(getString(R.string.souris), 123456,123456,1.13,R.drawable.souris, etatUpgrades[4]));
        listeUpgrades.add(new ItemMagasin(getString(R.string.roomba), 1358016,1358016,1.12,R.drawable.roomba, etatUpgrades[5]));
        listeUpgrades.add(new ItemMagasin(getString(R.string.laser), 14659738,14659738,1.12,R.drawable.laser, etatUpgrades[6]));

        mainListView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new MagasinAdapter(MagasinActivity.this, listeUpgrades);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mainListView.setLayoutManager(layoutManager);
        mainListView.setAdapter(adapter);

        TextView argent = (TextView) (findViewById(R.id.textViewArgent));
        argent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("pointage", m_argent);
                setResult(RESULT_OK, intent);
                //onActivityResult(1,1, intent);
                finish();
            }
        });
        ImageView buttonshop = findViewById(R.id.specialShop_main);
        buttonshop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent intente = new Intent(getApplicationContext(), MagasinPermenant.class);
                intente.putExtra("pointage", m_argent);
                intente.putExtra("MultiplicateurPermenant", etatPermenant);
                intente.putExtra("pointPerm", pointPerm);
                intente.putExtra("listeUpgrades", etatUpgrades);
                startActivityForResult(intente, 2);

            }

        });
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                int m_multiplier= etatUpgrades[0]*etatPermenant[0]+etatUpgrades[1]*etatPermenant[1]+etatUpgrades[2]*etatPermenant[2]+etatUpgrades[3]*etatPermenant[3];
                m_multiplier+= etatUpgrades[4]*etatPermenant[7]+etatUpgrades[5]*etatPermenant[5]+etatUpgrades[6]*etatPermenant[6];
                m_argent += m_multiplier;
                argent.setText("" + m_argent);



            }
        }, 0, 1000);



        ImageView buttonmain = findViewById(R.id.Clicker_main);
        buttonmain.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                intent.putExtra("pointage", m_argent);
                setResult(RESULT_OK, intent);
                finish();
            }

        });

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

    public class MagasinAdapter extends RecyclerView.Adapter<MagasinAdapter.MagasinViewHolder>
    {
        private final Context contexte;
        private final ArrayList<ItemMagasin> listeUpgrades;

        public MagasinAdapter(Context contexte, ArrayList<ItemMagasin> listeUpgrades)
        {
            this.contexte = contexte;
            this.listeUpgrades = listeUpgrades;
        }

        @Override
        public MagasinViewHolder onCreateViewHolder(final ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(contexte).inflate(R.layout.row_magasin, parent, false);
            final MagasinViewHolder magasinViewHolder = new MagasinViewHolder(view);


            view.findViewById(R.id.buttonAchat).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    int position = magasinViewHolder.getAdapterPosition();

                    if (m_argent >= listeUpgrades.get(position).getPrixUpgrade())
                    {
                        m_argent -= listeUpgrades.get(position).getPrixUpgrade();
                        listeUpgrades.get(position).updatePrix();
                        etatUpgrades[position] += 1;
                        magasinViewHolder.buttonAchat.setText(String.valueOf(listeUpgrades.get(position).getPrixUpgrade()));
                        ((TextView) findViewById(R.id.textViewArgent)).setText(String.valueOf(m_argent));
                        intent.putExtra("listeUpgrades", etatUpgrades);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Pas assez d'argent", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            return magasinViewHolder;
        }

        @Override
        public void onBindViewHolder(final MagasinViewHolder holder, int position)
        {
            final ItemMagasin itemMagasin = listeUpgrades.get(position);
            holder.textViewUpgrade.setText(itemMagasin.getInfoUpgrade());
            holder.buttonAchat.setText(String.valueOf(itemMagasin.getPrixUpgrade()));
            holder.imageViewUpgrade.setImageDrawable(getDrawable(itemMagasin.getIdImage()));
        }

        @Override
        public int getItemCount()
        {
            return listeUpgrades.size();
        }


        @Override
        public long getItemId(int position) {
            return position;
        }

        class MagasinViewHolder extends RecyclerView.ViewHolder
        {
            public TextView textViewUpgrade;
            public ImageView imageViewUpgrade;
            public Button buttonAchat;

            public MagasinViewHolder(View view)
            {
                super(view);
                textViewUpgrade =(TextView)view.findViewById(R.id.infoUpgrade);
                imageViewUpgrade = (ImageView) view.findViewById(R.id.imageUpgrade);
                buttonAchat = (Button) view.findViewById(R.id.buttonAchat);
            }
        }
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
package com.example.projetfinal;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.magasin_layout);
        ImageView image = findViewById(R.id.Shop_main);
        intent = getIntent();

        etatUpgrades = intent.getIntArrayExtra("listeUpgrades");
        m_argent = intent.getIntExtra("argent",0);
        intent.putExtra("listeUpgrades", etatUpgrades);
        ((TextView) findViewById(R.id.textViewArgent)).setText(String.valueOf(m_argent));

        listeUpgrades = new ArrayList<>();
        listeUpgrades.add(new ItemMagasin("Ball de fil", 2,2,1.07, R.drawable.yarn, etatUpgrades[0]));
        listeUpgrades.add(new ItemMagasin("Poisson", 72,72,1.15,R.drawable.fish, etatUpgrades[1]));
        listeUpgrades.add(new ItemMagasin("Cloche", 749,749,1.14,R.drawable.bell, etatUpgrades[2]));
        listeUpgrades.add(new ItemMagasin("Baton", 9752,9752,1.13,R.drawable.stick, etatUpgrades[3]));
        listeUpgrades.add(new ItemMagasin("Souris", 123456,123456,1.13,R.drawable.souris, etatUpgrades[4]));
        listeUpgrades.add(new ItemMagasin("Roomba", 1358016,1358016,1.12,R.drawable.roomba, etatUpgrades[5]));
        listeUpgrades.add(new ItemMagasin("Laser", 14659738,14659738,1.12,R.drawable.laser, etatUpgrades[6]));

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

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                int m_multiplier= etatUpgrades[0]+etatUpgrades[1]*20+etatUpgrades[2]*90+etatUpgrades[3]*360;
                m_multiplier+= etatUpgrades[4]*2160+etatUpgrades[5]*18100+etatUpgrades[6]*162885;
                m_argent += m_multiplier;
                argent.setText("" + m_argent);
            }
        }, 0, 1000);
    }

    @Override
    public void onBackPressed()
    {
        intent.putExtra("pointage", m_argent);
        setResult(RESULT_OK, intent);
        finish();
    }

    public class MagasinAdapter extends RecyclerView.Adapter<MagasinAdapter.MagasinViewHolder>
    {
        private Context contexte;
        private ArrayList<ItemMagasin> listeUpgrades;

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
                        Toast.makeText(getApplicationContext(),"Pas assez d'argent", Toast.LENGTH_SHORT).show();;
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

}
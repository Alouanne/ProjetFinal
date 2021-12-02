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

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MagasinActivity extends AppCompatActivity {

    private RecyclerView mainListView;
    private ArrayList<ItemMagasin> listeConversions;
    private MagasinAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.magasin_layout);
        ImageView image = findViewById(R.id.Shop_main);

        listeConversions = new ArrayList<>();
        listeConversions.add(new ItemMagasin("Ball de fil", 2,2,1.07));
        listeConversions.add(new ItemMagasin("poisson", 72,72,1.15));
        listeConversions.add(new ItemMagasin("bell", 749,749,1.14));
        listeConversions.add(new ItemMagasin("stick", 9752,9752,1.13));
        listeConversions.add(new ItemMagasin("souris", 123456,123456,1.13));
        listeConversions.add(new ItemMagasin("roomba", 1358016,1358016,1.12 ));
        listeConversions.add(new ItemMagasin("laser", 14659738,14659738,1.12));


        mainListView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new MagasinAdapter(MagasinActivity.this, listeConversions);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mainListView.setLayoutManager(layoutManager);
        mainListView.setAdapter(adapter);
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


            return magasinViewHolder;
        }


        @Override
        public void onBindViewHolder(final MagasinViewHolder holder, int position)
        {
            final ItemMagasin itemMagasin = listeUpgrades.get(position);
            holder.textViewUpgrade.setText(itemMagasin.getInfoUpgrade());

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
}
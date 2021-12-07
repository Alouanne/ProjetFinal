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


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.magasin_layout);
            intent = getIntent();

            etatUpgrades = intent.getIntArrayExtra("listeUpgrades");
            m_argent = intent.getIntExtra("argent",0);
            intent.putExtra("listeUpgrades", etatUpgrades);
            ((TextView) findViewById(R.id.textViewArgent)).setText(String.valueOf(m_argent));

            listeUpgrades = new ArrayList<>();
            listeUpgrades.add(new ItemObjPerm(1000, 1.5, "Multiplier le revenue des par 3", 0));



}


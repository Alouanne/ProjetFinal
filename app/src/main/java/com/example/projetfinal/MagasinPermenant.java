package com.example.projetfinal;

import static com.example.projetfinal.MainActivity.FNAME;

import android.content.Context;
import android.content.DialogInterface;
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

/**
 * @author Aléanne Camiré et Julien Forget
 * Ceci est la class associer a l'activité reset
 * Le but de cette classes est de donner acces au joueurs a des upgrades permenante ainsi que l'habileté de recommencer
 */
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
    private long prestige;
    private double statClicksSecondes;
    private int statPointTotals;
    private int statClickTotal;
    private int statReset;
    private AlertDialog.Builder builder;

    /**
     * Quand reset.xlm est crée, on fait agir cette fonction
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset);
        //Les valeurs constante ce font initialiser
        intent = getIntent();
        etatUpgrades = intent.getIntArrayExtra(MainActivity.LISTE_UPGRADES);
        m_argent = intent.getIntExtra(MainActivity.POINTAGE,0);
        etatPermenant = intent.getIntArrayExtra(MainActivity.LISTE_UPGRADES_PERMANENT);
        pointPerm = intent.getIntExtra(MainActivity.POINTS_PERMANENTS, 0);
        multiplier = intent.getIntExtra(MainActivity.MULTIPLIER, 0);
        statClicksSecondes = intent.getDoubleExtra(MainActivity.STAT_CLICKS_SECONDES,0);
        statClickTotal = intent.getIntExtra(MainActivity.STAT_CLICKS_TOTAL, 0);
        statPointTotals = intent.getIntExtra(MainActivity.STAT_POINTS,0);
        statReset = intent.getIntExtra(MainActivity.STAT_RESET,0);
        //On set le nombre de points a reservoir si tu reset
        prestige = 0;
        int rer;
        for (int i = 0; i < etatUpgrades.length; i++) {
            rer =etatUpgrades[i];
            prestige += 100 * Math.pow(rer,2);
        }

        TextView point = findViewById(R.id.PointReset);


        //Création du recycleView
        listeUpgrades = new ArrayList<>();

        listeUpgrades.add(new ItemObjPerm(10000, 1.39, "Multiplier le clickage par 3", etatPermenant[7], 3, "Les main de Dieu",7));
        listeUpgrades.add(new ItemObjPerm(10000, 1.05, "Multiplier le revenue des balles de laine par 3", etatPermenant[0], 3, "Les balls de laine, c'est parfait", 0));
        listeUpgrades.add(new ItemObjPerm(25000, 1.02, "Multiplier le revenue des poissons par 3", etatPermenant[1]/20, 3, "Miam! du poisson",1));
        listeUpgrades.add(new ItemObjPerm(50000, 1.13, "Multiplier le revenue des cloches par 3", etatPermenant[2]/90, 3, "Ding Ding, c'est un chat",2));
        listeUpgrades.add(new ItemObjPerm(75000, 1.43, "Multiplier le revenue des batons par 3", etatPermenant[3]/360, 3, "T'es sur que c'est pas un chien",3));
        listeUpgrades.add(new ItemObjPerm(100000, 1.12, "Multiplier le revenue des souris par 3", etatPermenant[4]/2160, 3, "Un chat sans ça souris, c'est quoi",4));
        listeUpgrades.add(new ItemObjPerm(125000, 1.48, "Multiplier le revenue des roomba par 3", etatPermenant[5]/18100, 3, "Le divertisement infinie du robot",5));
        listeUpgrades.add(new ItemObjPerm(150000, 1.27, "Multiplier le revenue des lasers par 3", etatPermenant[6]/162885, 3, "Vader-cat! Vader-cat",6));
        mainListView = (RecyclerView) findViewById(R.id.RecucleView);
        adapter = new MagasinAddapter(MagasinPermenant.this, listeUpgrades);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mainListView.setLayoutManager(layoutManager);
        mainListView.setAdapter(adapter);

        //si on click sur le button reset, il faut crée le intent opour le réenvoyer au point original, et tout réinitia lasier a 0
        Button butonReset  = findViewById(R.id.ResetB);
        butonReset.setText(String.valueOf(prestige));
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
                finish();
            }
        });
        point.setText(pointPerm + " prestige");
        //Crée le timer qui roule en arrière plan qui calcul les points
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                multiplier = 0;
                for (int i = 0; i < etatUpgrades.length; i++) {
                    multiplier += etatUpgrades[i]*etatPermenant[i];
                }
                m_argent += multiplier;
            }
        }, 0, 1000);
        //Crée le button pour retournée à l'activité principale
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
        //Crée le button pour aller à l''activité magasin
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

    /**
     * Crée pour utilisé le recyclerView, ainsi que toute ces fonction nécésaire
     */
        public class MagasinAddapter extends RecyclerView.Adapter<MagasinAddapter.MagasinViewHolder>
        {
            private final Context contexte;
            private final ArrayList<ItemObjPerm> listeUpgrades;

            /**
             * Création du MagasinAddapter
             * @param contexte le context de la fonction
             * @param listeUpgrades la liste de Item dans le magasin
             */
            public MagasinAddapter(Context contexte, ArrayList<ItemObjPerm> listeUpgrades)
            {
                this.contexte = contexte;
                this.listeUpgrades = listeUpgrades;

            }

            /**
             * Création du viewHolder
             * @param parent le viewGroup
             * @param viewType le viewType du viewGroup
             * @return le viewHolder
             */
            @Override
            public MagasinAddapter.MagasinViewHolder onCreateViewHolder(final ViewGroup parent, int viewType)
            {
                View view = LayoutInflater.from(contexte).inflate(R.layout.row_reset, parent, false);
                final MagasinAddapter.MagasinViewHolder magasinViewHolder = new MagasinAddapter.MagasinViewHolder(view);

                //si le button dans le recyclerView est peser, on fait ceci
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

            /**
             * Quand on connect le viewHolderm on crée des holder
             * @param holder tient les information sur les textView et les button
             * @param position quel position on est dans le listview
             */
            @Override
            public void onBindViewHolder(final MagasinAddapter.MagasinViewHolder holder, int position)
            {
                ItemObjPerm itemMagasin = listeUpgrades.get(position);
                holder.textViewUpgrade.setText(itemMagasin.getM_explication());
                holder.buttonAchat.setText(String.valueOf(itemMagasin.getM_cout()));
                holder.textViewTitre.setText(itemMagasin.getM_titre());
            }

            /**
             *  Trouvez la grandeur de la list
             * @return la grandeur de la list
             */
            @Override
            public int getItemCount()
            {
                return listeUpgrades.size();
            }

            /**
             * Création du viewholder pour le magasin
             */
            class MagasinViewHolder extends RecyclerView.ViewHolder
            {
                public TextView textViewUpgrade;
                public Button buttonAchat;
                public TextView textViewTitre;

                /**
                 * Crée le viewHolder
                 * @param view
                 */
                public MagasinViewHolder(View view)
                {
                    super(view);
                    textViewUpgrade =(TextView)view.findViewById(R.id.infoReset);
                    textViewTitre =(TextView)view.findViewById(R.id.NomSpecial);
                    buttonAchat = (Button) view.findViewById(R.id.buttonReset);
                }
            }
        }

    /**
     * Si le joueurs click sur le backpress, il sauvegarde son progres avant de retourné a l'activité main
     */
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

    /**
     * Si le joueurs sort de l'application, sauvegarde le progres
     */
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

        super.onStop();
    }

    /**
     * Quand on retourne de l'activité magasin, on fait ce progres
     * @param requestCode le code qu'on à envoyer
     * @param resultCode le code que nous avons reçu
     * @param data l'intent qui à été envoyer avec le rest
     */
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

    /**
     * set up pour les trois point
     * @param menu le menu trois point du haut de la page
     * @return vrai
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_clicker, menu);

        return true;
    }

    /**
     * Quand l'option du menu delete save data est appeler, on mets toute à 0
     */
    public void delete()
    {
        m_argent = 0;
        for (int i = 0; i < etatUpgrades.length; i++)
        {
            etatUpgrades[i] = 0;
        }
        etatPermenant = new int[]{1,20,90,360,2160,18100,162885, 1};
        pointPerm = 0;
        statClickTotal = 0;
        statReset = 0;
        statPointTotals = 0;
        statClicksSecondes = 0;
        multiplier = 0;

        onBackPressed();
    }
/**
 * Quand chaque aspect du menu est clické, il fait different switch case
 */
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
            case R.id.delete:
                builder = new AlertDialog.Builder(this);
                builder.setMessage("Voulez-vous supprimer toutes les données associés à cette application?").setTitle("Supprimer la sauvegarde");
                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteFile(FNAME);
                        delete();
                    }
                });
                builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog alertDialog3 = builder.create();
                alertDialog3.show();
                break;
            default:
                Toast.makeText(getApplicationContext(), "Help", Toast.LENGTH_LONG).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}



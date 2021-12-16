package com.example.projetfinal;

import static com.example.projetfinal.MainActivity.FNAME;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.graphics.drawable.Drawable;
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
 * @author Julien Forget et Aléanne Camiré
 * Activité contenant un menu, un RecyclerView contenant tout les upgrades réguliers, un TextView affichant le nombre de points,
 * un interface composé de 3 ImageView qui servent à naviguer entre les activités
 */
public class MagasinActivity extends AppCompatActivity {

    private RecyclerView mainListView;
    private ArrayList<ItemMagasin> listeUpgrades;
    private MagasinAdapter adapter;
    private int m_argent;
    private int[] etatUpgrades;
    private Intent intent;
    private int[] etatPermenant;
    private int pointPerm;
    private int multiplier;
    private double statClicksSecondes;
    private int statPointTotals;
    private int statClickTotal;
    private int statReset;
    private AlertDialog.Builder builder;

    /**
     * Lorsque l'activité est créée :
     * - démarre un Timer qui augmente à chaque seconde le nombre de points et affiche le nouveau pointage
     * - récupère toutes les information envoyées par l'activité principale
     * - Ajoute les onClickListener aux 3 ImageView de l'interface en bas de l'écran
     * - initialise le RecyclerView selon les informations fournies par l'activité principale
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.magasin_layout);
        intent = getIntent();

        //Récupère les informations envoyées par l'activité principale
        multiplier = intent.getIntExtra(MainActivity.MULTIPLIER, 0);
        etatUpgrades = intent.getIntArrayExtra(MainActivity.LISTE_UPGRADES);
        etatPermenant = intent.getIntArrayExtra(MainActivity.LISTE_UPGRADES_PERMANENT);
        m_argent = intent.getIntExtra(MainActivity.POINTAGE,0);
        pointPerm = intent.getIntExtra(MainActivity.POINTS_PERMANENTS,0);
        for (int i = 0; i < etatUpgrades.length; i++) {
            multiplier += etatUpgrades[i]*etatPermenant[i];
        }
        statClicksSecondes = intent.getDoubleExtra(MainActivity.STAT_CLICKS_SECONDES,0);
        statClickTotal = intent.getIntExtra(MainActivity.STAT_CLICKS_TOTAL, 0);
        statPointTotals = intent.getIntExtra(MainActivity.STAT_POINTS,0);
        statReset = intent.getIntExtra(MainActivity.STAT_RESET,0);

        //Initialise la liste des upgrades pouvant être achetées
        listeUpgrades = new ArrayList<>();
        listeUpgrades.add(new ItemMagasin(getString(R.string.ballFil), 2,2,1.07, R.drawable.yarn, etatUpgrades[0], R.drawable.cat_yarn3));
        listeUpgrades.add(new ItemMagasin(getString(R.string.poisson), 72,72,1.15,R.drawable.fish, etatUpgrades[1], R.drawable.cat_fish2));
        listeUpgrades.add(new ItemMagasin(getString(R.string.cloche), 749,749,1.14,R.drawable.bell, etatUpgrades[2], R.drawable.cat_bell));
        listeUpgrades.add(new ItemMagasin(getString(R.string.baton), 9752,9752,1.13,R.drawable.stick, etatUpgrades[3], R.drawable.rod));
        listeUpgrades.add(new ItemMagasin(getString(R.string.souris), 123456,123456,1.13,R.drawable.souris, etatUpgrades[4], R.drawable.rat));
        listeUpgrades.add(new ItemMagasin(getString(R.string.roomba), 1358016,1358016,1.12,R.drawable.roomba, etatUpgrades[5], R.drawable.aspirateur2));
        listeUpgrades.add(new ItemMagasin(getString(R.string.laser), 14659738,14659738,1.12,R.drawable.laser, etatUpgrades[6], R.drawable.nyan_cat2));

        //Initialise le RecyclerView avec la liste des upgrades pouvant être achetées
        mainListView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new MagasinAdapter(MagasinActivity.this, listeUpgrades);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mainListView.setLayoutManager(layoutManager);
        mainListView.setAdapter(adapter);

        TextView argent = (TextView) (findViewById(R.id.textViewArgent));
        argent.setText(String.valueOf(m_argent));

        //  finit l'activité et demande à l'activité principale de démarrer l'activité MagasinPermanent avec les informations suivantes:
        //  - les statisitiques
        //  - le multiplier
        //  - les deux types de points accumulés
        //  - les améliorations achetées
        ImageView buttonshop = findViewById(R.id.specialShop_main);
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
                setResult(MainActivity.RESULT_SWITCH_TO_MAGASIN_PERMANENT, intent);
                finish();
            }

        });

        //Timer qui augmente à chaque seconde le nombre de points par la valeur du mutiplier
        //Affiche le nouveau nombre de points
        //Met à jour la statistique du nombre de points accumulés
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                m_argent += multiplier;
                statPointTotals += multiplier;
                argent.setText("" + m_argent);
            }
        }, 0000, 1000);


        //  finit l'activité en renvoyant à l'activité principale les informations suivantes:
        //  - les statisitiques
        //  - le multiplier
        //  - les deux types de points accumulés
        //  - les améliorations achetées
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
    }

    /**
     * finit l'activité en renvoyant à l'activité principale les informations suivantes:
     * - les statisitiques
     * - le multiplier
     * - les deux types de points accumulés
     * - les améliorations achetées
     */
    @Override
    public void onBackPressed()
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

    /**
     *  Adapter qui convertit les informations contenus dans un ItemMagasin en une view personnalisée qui peut être mise dans un RecyclerView
     */
    public class MagasinAdapter extends RecyclerView.Adapter<MagasinAdapter.MagasinViewHolder>
    {
        private final Context contexte;
        private final ArrayList<ItemMagasin> listeUpgrades;

        /**
         * Initialise le contexte et la liste des upgrades utilisés par l'adapter
         * @param contexte contexte de l'activité
         * @param listeUpgrades liste de les ItemMagasin à mettre dans le RecyclerView
         */
        public MagasinAdapter(Context contexte, ArrayList<ItemMagasin> listeUpgrades)
        {
            this.contexte = contexte;
            this.listeUpgrades = listeUpgrades;
        }

        /**
         * Inflate les rows du RecyclerView et ajoute les  onCLickListener pour le bouton de chaque row
         * @param parent le ViewGroup qui regroupe les rows
         * @param viewType Le type de view
         * @return magasinViewHolder
         */
        @Override
        public MagasinViewHolder onCreateViewHolder(final ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(contexte).inflate(R.layout.row_magasin, parent, false);
            final MagasinViewHolder magasinViewHolder = new MagasinViewHolder(view);


            // Si le nombre de points est suffisant,
            // - Augmente de 1 le etatUpgrade (liste qui traque le nb d'achats de chaque upgrade) à la même position que le bouton clické de la row (view)
            // - Diminue le nombre de points par le prix de l'upgrade de l'ItemMagasin associé à la row (view)
            // - Change le TextView pour qu'il affiche le nouveau nombre de points après l'achat
            // - Update le prix de l'upgrade de l'ItemMagasin associé à la row (view)
            // - Augmente le multiplier selon l'ItemMagasin acheté
            // Sinon affiche un toast
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
                        multiplier += etatPermenant[position];
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Pas assez d'argent", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            return magasinViewHolder;
        }

        /**
         * Construit une row du recycler view avec l'ItemMagasin à la même position dans l'ArrayList que la position de la row dans le RecyclerView
         * Le TextView va correspondre au nom de l'ItemMagasin
         * L'ImageView va correspondre à l'identifiant de la ressource Drawable de l'ItemMagasin
         * Le texte du Button va correspondre aux prix de l'upgrade de l'ItemMagsin
         * L'image du Button va correspondre à l'identifiant de la ressource Drawable du bouton de l'ItemMagasin
         * @param holder une row (view) du RecyclerView
         * @param position la position de la row (view) dans le RecyclerView
         */
        @Override
        public void onBindViewHolder(final MagasinViewHolder holder, int position)
        {
            final ItemMagasin itemMagasin = listeUpgrades.get(position);
            holder.textViewUpgrade.setText(itemMagasin.getInfoUpgrade());
            holder.buttonAchat.setText(String.valueOf(itemMagasin.getPrixUpgrade()));
            Drawable img = getDrawable(itemMagasin.getIdImageButton());
            holder.buttonAchat.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null);
            holder.imageViewUpgrade.setImageDrawable(getDrawable(itemMagasin.getIdImage()));

        }

        /**
         * Retourne la taille du RecyclerView
         * @return le nombre de row du RecyclerView (sa taille)
         */
        @Override
        public int getItemCount()
        {
            return listeUpgrades.size();
        }

        /**
         * Objet qui garde en mémoire l'ImageView, le TextView et le Button de chaque row du RecyclerView
         */
        class MagasinViewHolder extends RecyclerView.ViewHolder
        {
            public TextView textViewUpgrade;
            public ImageView imageViewUpgrade;
            public Button buttonAchat;

            /**
             * Trouve le TextView, l'ImageView et le Button qui compose la view, et les stocks en mémoire dans leurs objets correspondants
             * @param view : une row du RecyclerView
             */
            public MagasinViewHolder(View view)
            {
                super(view);
                textViewUpgrade =(TextView)view.findViewById(R.id.infoUpgrade);
                imageViewUpgrade = (ImageView) view.findViewById(R.id.imageUpgrade);
                buttonAchat = (Button) view.findViewById(R.id.buttonAchat);
            }
        }
    }

    /**
     * Lorsque l'activité se ferme:
     * enregistre dans un fichier texte interne les informations suivantes:
     * - Les statistiques
     * - Les améliorations achetées
     * - Les différents points accumulés
     */
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

        super.onStop();
    }

    /**
     * - Remet à 0 les statistiques, les différents points accumulés et le multiplier
     * - Enlève tout les upgrades achetés
     * - Retourne à l'activité principale avec les variables réinitialiser
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
     * Crée le menu situé en haut de l'activité principale qui contient les 3 éléments suivants:
     *  - À propos
     *  - Statistiques
     *  - Supprimer la sauvegarde
     *
     * @param menu
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_clicker, menu);

        return true;
    }

    /**
     * Ajoute les fonctionnalités suivantes aux éléments du menu lorsqu'ils sont sélectionnés (onClickListener):
     * - Pour l'élément "À Propos" : Affiche un AlertDialog contenant les informations générales de l'application
     * - Pour l'élément "Statistiques" : Affiche un AlertDialog contenant le nombre total de clicks, le nombre maximum de clicks par seconde,
     *                                  le nombre total de points accumulés et le nombre de reset effectués.
     * - Pour l'élément "Supprimer la sauvegarde" : Affiche un AlertDialog avec deux boutons, oui et non.
     *                                              Le bouton oui supprime la sauvegarde de l'application
     *                                              Le bouton non ferme l'AlertDialog
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.statisitiques:
                builder = new AlertDialog.Builder(MagasinActivity.this);
                String s1 = getString(R.string.nbClicks);
                String s2 = " " + String.valueOf(statClickTotal) +"\n";
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
                builder = new AlertDialog.Builder(MagasinActivity.this);
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
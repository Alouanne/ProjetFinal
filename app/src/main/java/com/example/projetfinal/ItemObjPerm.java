package com.example.projetfinal;

/**
 * @author Aléanne Camiré et Julien Forget
 * Ceci est la class d'objet utiliser par la class magasin permenant pour guarder l'information de chaque objet
 */
public class ItemObjPerm {
    int m_cout;
    double m_multiplieur;
    String m_explication;
    String m_titre;
    int nombre;
    int ajout;
    int cout_original;
    int valeur_changer;

    /**
     * Le créateur de ItemObjPerm
     * @param m_cout le coup original de l'item
     * @param m_multiplieur le nombre utiliser pour la formule pour le prix des items
     * @param m_explication L'explication de l'item
     * @param nombre Le nombre d'item que le joueurs a
     * @param ajout L'ajout est le numero que doit être inscrit dans etatPermenant(etatPermenant[ajout]
     * @param titre Le titre qui sera donner à l'item
     * @param valeur Valeur est la valeur que etatPermenant changera (etatPermenant[ajout] *= valeur)
     */
    public ItemObjPerm(int m_cout, double m_multiplieur, String m_explication, int nombre, int ajout, String titre, int valeur ) {
        this.m_cout = m_cout;
        this.m_multiplieur = m_multiplieur;
        this.m_explication = m_explication;
        this.nombre = nombre;
        cout_original = m_cout;
        this.ajout = ajout;
        m_titre = titre;
        valeur_changer = valeur;
        nombre--;
        if(nombre!=0) {
            this.m_cout = (int) ((nombre + 2) * (cout_original * Math.pow(m_multiplieur, nombre)));
        }
    }

    /**
     * Allez chercher la valeur qu'il faut multiplier pour avoir notre nouveau etatPermenant
     * @return la valeur que etatPermenant changera
     */
    public int getValeur_changer() {
        return valeur_changer;
    }

    /**
     * Allez chercher le titre de l'item
     * @return le titre de l'item
     */
    public String getM_titre() {
        return m_titre;
    }

    /**
     * Allez chercher quel item doit être modifier
     * @return le numero d'item qu'il faut être modifier
     */
    public int getAjout() {
        return ajout;
    }

    /**
     * Allez chercher le cout de l'upgrade
     * @return le coute de l'upgrade
     */
    public int getM_cout() {
        return m_cout;
    }

    /**
     * Allez chercher l'explication de l'item
     * @return l'explication de l'item
     */
    public String getM_explication() {
        return m_explication;
    }

    /**
     * Ajouter un nombre au nombre d'item que le joueurs à, et trouvé le nouveau cout pour l'upgrade, basé sur le nouveau nombre
     */
    public void addNumber(){
        nombre++;
        m_cout = (int) ((nombre+2)*(cout_original*Math.pow(m_multiplieur, nombre)));

    }
}

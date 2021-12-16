package com.example.projetfinal;

public class ItemObjPerm {
    int m_cout;
    double m_multiplieur;
    String m_explication;
    String m_titre;
    int nombre;
    int ajout;
    int cout_original;
    int valeur_changer;
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

    public int getValeur_changer() {
        return valeur_changer;
    }

    public String getM_titre() {
        return m_titre;
    }

    public int getAjout() {
        return ajout;
    }

    public void setAjout(int ajout) {
        this.ajout = ajout;
    }

    public int getM_cout() {
        return m_cout;
    }

    public void setM_cout(int m_cout) {
        this.m_cout = m_cout;
    }

    public double getM_multiplieur() {
        return m_multiplieur;
    }

    public void setM_multiplieur(double m_multiplieur) {
        this.m_multiplieur = m_multiplieur;
    }

    public String getM_explication() {
        return m_explication;
    }

    public void setM_explication(String m_explication) {
        this.m_explication = m_explication;
    }
    public void addNumber(){
        nombre++;
        m_cout = (int) ((nombre+1000000)*(cout_original*Math.pow(m_multiplieur, nombre)));

    }
}

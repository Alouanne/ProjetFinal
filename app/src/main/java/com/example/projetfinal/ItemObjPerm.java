package com.example.projetfinal;

public class ItemObjPerm {
    int m_cout;
    double m_multiplieur;
    String m_explication;
    int nombre;

    public ItemObjPerm(int m_cout, double m_multiplieur, String m_explication, int nombre) {
        this.m_cout = m_cout;
        this.m_multiplieur = m_multiplieur;
        this.m_explication = m_explication;
        this.nombre = nombre;
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
}

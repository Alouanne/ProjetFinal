package com.example.projetfinal;

import android.graphics.drawable.Drawable;

public class ItemMagasin
{
    private String infoUpgrade;
    private int prixUpgrade;
    private double nb;
    private double coefficientA;
    private double coefficientB;
    //private Drawable image;

    public ItemMagasin(String infoUpgrade, int prixUpgrade, double coefficientA, double coefficientB)
    {
        this.infoUpgrade = infoUpgrade;
        this.prixUpgrade = prixUpgrade;
        this.coefficientA = coefficientA;
        this.coefficientB = coefficientB;
        nb = 0;
    }

    public void  setInfoUpgrade(String infoUpgrade)
    {
        this.infoUpgrade = infoUpgrade;
    }

    public  void setPrixUpgrade(int prixUpgrade)
    {
        this.prixUpgrade = prixUpgrade;
    }

   /* public void setIdImage(Drawable idImage)
    {
        this.image = idImage;
    }
*/
    public int getPrixUpgrade()
    {
        return prixUpgrade;
    }
/*
    public Drawable getIdImage()
    {
        return image;
    }
*/
    public String getInfoUpgrade()
    {
        return infoUpgrade;
    }

    public void updatePrix()
    {
        prixUpgrade = (int) (coefficientA*Math.pow(coefficientB, nb));
    }
}

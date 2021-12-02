package com.example.projetfinal;

import android.graphics.drawable.Drawable;

public class ItemMagasin
{
    private String infoUpgrade;
    private int prixUpgrade;
    private double nb;
    private double coefficientA;
    private double coefficientB;
    private int idImage;

    public ItemMagasin(String infoUpgrade, int prixUpgrade, double coefficientA, double coefficientB, int idImage)
    {
        this.infoUpgrade = infoUpgrade;
        this.prixUpgrade = prixUpgrade;
        this.coefficientA = coefficientA;
        this.coefficientB = coefficientB;
        this.idImage = idImage;

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

   public void setIdImage(int idImage)
    {
        this.idImage = idImage;
    }

    public int getPrixUpgrade()
    {
        return prixUpgrade;
    }

    public int getIdImage()
    {
        return idImage;
    }

    public String getInfoUpgrade()
    {
        return infoUpgrade;
    }

    public void updatePrix()
    {
        prixUpgrade = (int) (coefficientA*Math.pow(coefficientB, nb));
        nb++;
    }
}

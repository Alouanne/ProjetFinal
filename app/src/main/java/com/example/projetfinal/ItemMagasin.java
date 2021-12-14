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
    private int idImageButton;

    public ItemMagasin(String infoUpgrade, int prixUpgrade, double coefficientA, double coefficientB, int idImage, int nbAchats, int idImageButton)
    {
        this.infoUpgrade = infoUpgrade;
        this.prixUpgrade = prixUpgrade;
        this.coefficientA = coefficientA;
        this.coefficientB = coefficientB;
        this.idImage = idImage;
        nb = nbAchats;
        this.idImageButton = idImageButton;

        for (int i = 0; i < nbAchats; i++)
            updatePrix();
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

    public int getIdImageButton()
    {
        return idImageButton;
    }

    public void updatePrix()
    {
        prixUpgrade = (int) ((nb+2)*(coefficientA*Math.pow(coefficientB, nb)));
        nb++;
    }
}

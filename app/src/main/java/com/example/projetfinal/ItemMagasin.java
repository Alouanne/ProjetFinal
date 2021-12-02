package com.example.projetfinal;

import android.graphics.drawable.Drawable;

public class ItemMagasin
{
    private String infoUpgrade;
    private int prixUpgrade;
    //private Drawable image;

    public ItemMagasin(String infoUpgrade, int prixUpgrade)
    {
        this.infoUpgrade = infoUpgrade;
        this.prixUpgrade = prixUpgrade;
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
}

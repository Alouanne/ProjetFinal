package com.example.projetfinal;

import android.graphics.drawable.Drawable;

/**
 * @author Julien Forget et Aléanne Camiré
 * Objet qui contient:
 * - le nom de l'upgrade
 * - le prix de l'upgrade
 * - le nombre de fois que l'upgrade a été acheté
 * - les identifiants des ressources correspondants aux images des upgrades
 * - les coefficients utilisés pour calculer l'inflation du prix de l'upgrade
 */
public class ItemMagasin
{
    private String infoUpgrade;
    private int prixUpgrade;
    private double nb;
    private double coefficientA;
    private double coefficientB;
    private int idImage;
    private int idImageButton;

    /**
     *  inialise les variables à leur valeur correpondante à l'exeption du prixUpgrade
     *  Le prixUpgrade correspond au coût initial de l'upgrade si le nbAchats est égal à 0
     *  sinon, la méthode updatePrix va être appelée autant de fois que la valeur nbAchcts pour calculer le prixUpgrade
     *  si le coefficientA est égal à 0, le prixUpgrade restera 0 après chaque achat
     *  si le coefficientB est égal à 0, le prixUpgrade restera 0 après chaque achat
     *  si le coefficientB est égal à 1, le prixUpgrade augmentera linéairement avec le nbAchats après chaque achat
     *
     * @param infoUpgrade nom de l'upgrade
     * @param prixUpgrade prix de l'upgrade
     * @param coefficientA prix constant de l'upgrade  utilisé pour calculer la prochain prix de l'upgrade
     * @param coefficientB double qui détermine le taux exponentiel de croissance du prix de l'upgrade
     * @param idImage identifiant de la ressource Drawable à affichée à gauche du nom de l'upgrade
     * @param nbAchats nombre de fois que l'upgrade à déjà été acheté
     * @param idImageButton identifiant de la ressource Drawable à affichée à l'intérieur du bouton pour faire l'achat
     */
    public ItemMagasin(String infoUpgrade, int prixUpgrade, double coefficientA, double coefficientB, int idImage, int nbAchats, int idImageButton)
    {
        this.infoUpgrade = infoUpgrade;
        this.prixUpgrade = prixUpgrade;
        this.coefficientA = coefficientA;
        this.coefficientB = coefficientB;
        this.idImage = idImage;
        this.idImageButton = idImageButton;
        nb = 0;

        for (int i = 0; i < nbAchats; i++)
            updatePrix();
    }

    /**
     * @return le prix nécessaire pour acheter le prochain upgrade
     */
    public int getPrixUpgrade()
    {
        return prixUpgrade;
    }

    /**
     *
     * @return l'identifiant de la ressource Drawable de l'image de l'upgrade
     */
    public int getIdImage()
    {
        return idImage;
    }

    /**
     *
     * @return le nom de l'upgrade
     */
    public String getInfoUpgrade()
    {
        return infoUpgrade;
    }

    /**
     *
     * @return l'identifiant de la ressource Drawable de l'image à l'intérieur du bouton pour acheter l'upgrade
     */
    public int getIdImageButton()
    {
        return idImageButton;
    }

    /**
     * Calcul le nouveau prix de l'upgrade avec la valeur du coefficientA, du coefficientB et selon le nombre de fois qu'il a été acheté jusqu'à présent
     * Puis, Augmente le nb de fois qu'il a été acheté de 1
     */
    public void updatePrix()
    {
        prixUpgrade = (int) ((nb+2)*(coefficientA*Math.pow(coefficientB, nb)));
        nb++;
    }
}

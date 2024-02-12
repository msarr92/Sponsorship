package com.groupeisi.sponsorship.entities;

import java.util.Date;

public class Parrainer {
    private int id;
    private Date dateParrainage;

    private User electeur;
    private User candidat;
    public Parrainer() {
    }

    public User getElecteur() {
        return electeur;
    }

    public void setElecteur(User electeur) {
        this.electeur = electeur;
    }

    public User getCandidat() {
        return candidat;
    }

    public void setCandidat(User candidat) {
        this.candidat = candidat;
    }

    public Parrainer(int id, Date dateParrainage, User electeur, User candidat) {
        this.id = id;
        this.dateParrainage = dateParrainage;
        this.electeur = electeur;
        this.candidat = candidat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateParrainage() {
        return dateParrainage;
    }

    public void setDateParrainage(Date dateParrainage) {
        this.dateParrainage = dateParrainage;
    }
}

package com.groupeisi.sponsorship.entities;

public class Role {
    private int idRole;
    private String name;
    private int etat;

    public Role() {
    }

    public Role(int idRole, String name, int etat) {
        this.idRole = idRole;
        this.name = name;
        this.etat = etat;
    }

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }
}

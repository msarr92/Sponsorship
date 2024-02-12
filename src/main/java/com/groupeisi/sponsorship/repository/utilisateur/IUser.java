package com.groupeisi.sponsorship.repository.utilisateur;


import com.groupeisi.sponsorship.entities.User;

public interface IUser {
    public User seConnecter(String login, String password);

}

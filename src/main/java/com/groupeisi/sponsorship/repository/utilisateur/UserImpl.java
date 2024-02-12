package com.groupeisi.sponsorship.repository.utilisateur;


import com.groupeisi.sponsorship.DBConnexion;
import com.groupeisi.sponsorship.entities.Role;
import com.groupeisi.sponsorship.entities.User;
import com.groupeisi.sponsorship.repository.role.IRole;
import com.groupeisi.sponsorship.repository.role.RoleImpl;
import com.groupeisi.sponsorship.repository.utilisateur.IUser;

import java.sql.ResultSet;

public class UserImpl implements IUser {
    private  DBConnexion db= new DBConnexion();
    @Override
    public User seConnecter(String login, String password) {

        String sql="SELECT * from user where login= ? AND password=?";
        User user=null;
            try {
                //Préparation ou initialisation de la requete
                db.initPrepar(sql);
                //Passage de valeurs
                db.getPstm().setString(1, login);
                db.getPstm().setString(2, password);
                //Exécution de la requete
               ResultSet rs = db.executeSelect();
                if(rs.next()){
                    user = new User();
                    user.setId(rs.getInt("idUser"));
                    user.setNom(rs.getString("nom"));
                    user.setPrenom(rs.getString("prenom"));
                    user.setLogin(rs.getString("login"));
                    user.setPassword(rs.getString(5));
                    user.setActived(rs.getInt("actived"));
                    IRole iRole = new RoleImpl();
                    int idRole = rs.getInt("profil");
                    Role role = iRole.getRoleById(idRole);
                    user.setProfil(role);
                }
                db.closeConnection();
            }catch (Exception e){
                e.printStackTrace();
            }
            return user;
    }
}

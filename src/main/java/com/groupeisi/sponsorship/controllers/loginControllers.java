package com.groupeisi.sponsorship.controllers;

import com.groupeisi.sponsorship.SessionUser;
import com.groupeisi.sponsorship.entities.User;
import com.groupeisi.sponsorship.repository.utilisateur.IUser;
import com.groupeisi.sponsorship.repository.utilisateur.UserImpl;
import com.groupeisi.sponsorship.tools.Notification;
import com.groupeisi.sponsorship.tools.Outils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class loginControllers {


    @FXML
    private TextField loginTfd;

    @FXML
    private PasswordField passwordTfd;

    private IUser userDao=new UserImpl();
    private final IUser user=new UserImpl();
    public static User A=null;

    @FXML
    void connecter(ActionEvent event) {
        String login=loginTfd.getText();
        String password=passwordTfd.getText();
        if (login.isEmpty() || password.isEmpty()){
            Notification.NotifError("Erreur","Tout les champs sont obligatoire");
        } else {
            User user=userDao.seConnecter(login,password);
            if (user!=null){
                int userId =user.getId();
                SessionUser.setUserId(userId);
                try{
                    Notification.NotifSuccess("Succés !", "Connexion réussie !");
                    if (user.getProfil().getName().equalsIgnoreCase("admin")){
                        Outils.load(event,"Administrateur","/Fxml/admin.fxml");
                       // Outils.load(event,"Administrateur","/Fxml/infParrain.fxml");
                    } else if (user.getProfil().getName().equalsIgnoreCase("candidat")) {
                        Outils.load(event,"Candidat","/Fxml/infParrain.fxml");
                    }else
                        Outils.load(event,"Electeur","/Fxml/parrain.fxml");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else {
                Notification.NotifError("Erreur !","login ou mot de passe incorrecte");
            }
        }
    }



}

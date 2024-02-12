package com.groupeisi.sponsorship.controllers;

import com.groupeisi.sponsorship.DBConnexion;

import com.groupeisi.sponsorship.SessionUser;
import com.groupeisi.sponsorship.entities.Parrainer;
import com.groupeisi.sponsorship.entities.User;
import com.groupeisi.sponsorship.repository.parrainnage.ParrainImpl;

import com.groupeisi.sponsorship.tools.Notification;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class parrainController implements Initializable {

    @FXML
    private TableColumn<User, String> nom;

    @FXML
    private TableColumn<User, Integer> id;

    @FXML
    private Button parrainerBtn;

    @FXML
    private TableColumn<User, String> prenom;
    @FXML
    private TableView<User> tableCan;



    @FXML
    private TableView<User> tabPar;


    private DBConnexion db=new DBConnexion();

   private ParrainImpl parrainDao=new ParrainImpl();

    @FXML
   private TextField nbreParrain;



    private int idUserConnecter;

   private int idCandidat;

   // private IUser userDao=new UserImpl();

    public ObservableList<User> getUser(){
        ObservableList<User> users= FXCollections.observableArrayList();
        String sql="SELECT * FROM user JOIN role on user.profil=role.idRole WHERE profil=2";
        try{
            db.initPrepar(sql);
            ResultSet rs=db.executeSelect();
            while(rs.next()){
                User user= new User();
                user.setId(rs.getInt("idUser"));
                user.setNom(rs.getString("nom"));
                user.setPrenom(rs.getString("prenom"));
                users.add(user);
            }
            db.closeConnection();
        }catch (SQLException e){
            throw new RuntimeException();
        }
        return users;
    }


    public void loadTable(){
        ObservableList<User> liste=getUser();
        tableCan.setItems(liste);
        id.setCellValueFactory(new PropertyValueFactory<User,Integer>("id"));
        nom.setCellValueFactory(new PropertyValueFactory<User,String>("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<User,String>("prenom"));
    }

/*
    void desactiver(ActionEvent event) {
        String sql="UPDATE user SET actived=? WHERE idUser= ? ";
        try{
            db.initPrepar(sql);
            db.getPstm().setInt(1,0);
            db.getPstm().setInt(2,idD);
            db.executeMaj();
            db.closeConnection();
            loadTable();
            Notification.NotifSuccess("Succes","normal");
        }catch (SQLException e){
            throw new RuntimeException();
        }
    }
 */



    @FXML
    void parrainer(ActionEvent event) {
        int nbrPar = parrainDao.nbrParrainCand(idCandidat);
           if (parrainDao.dejaParrain(idUserConnecter)) {
               if (nbrPar <= 7) {
                   parrainDao.parrainer(idCandidat, idUserConnecter);
                   Notification.NotifSuccess("SUCESS", "Parrainage Reussi");
               }
               else {
                   Notification.NotifError("Erreur", "Ce Candidat a deja atteint le nombre maximal de parrainage");
               }
           }else {
             Notification.NotifError("Erreur", "Vous avez deja Parrainer ce Candidat");
           }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadTable();
        idUserConnecter= SessionUser.getUserId();
        tableCan.getSelectionModel().selectedItemProperty().addListener ((observableValue, user, t1) -> {
            parrainerBtn.setDisable(t1==null ||!SessionUser.estConnecte());
        });
    }

    @FXML
    void selectCand(MouseEvent event) {
        User user = tableCan.getSelectionModel().getSelectedItem();
        idCandidat = user.getId();
    }
    /*
    public boolean dejaParrain(int idElecteur, int idCandidat) {
        String sql="SELECT *FROM parrainer WHERE electeur=? AND candidat=? ";
        try{
            db.initPrepar(sql);
            db.getPstm().setInt(1,idElecteur);
            db.getPstm().setInt(2,idCandidat);
            try {
                ResultSet rs = db.executeSelect();
                return rs.next();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public void parrainer(User electeur, User candidat) {
        String sql = "INSERT INTO parrainer (DatePar, electeur, candidat) VALUES (CURRENT_TIMESTAMP, ?, ?)";

        try {
            db.initPrepar(sql);
            db.getPstm().setInt(1,electeur.getId());
            db.getPstm().setInt(2,candidat.getId());
            db.executeMaj();

        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception, par exemple afficher un message à l'utilisateur
        }
    }
     */


    public ObservableList<User> getParrain(){
        ObservableList<User> users= FXCollections.observableArrayList();
        String sql="SELECT * FROM user JOIN role on user.profil=role.idRole WHERE profil=2";
        try{
            db.initPrepar(sql);
            ResultSet rs=db.executeSelect();
            while(rs.next()){
                User user= new User();
                user.setId(rs.getInt("idUser"));
                user.setNom(rs.getString("nom"));
                user.setPrenom(rs.getString("prenom"));
                users.add(user);
            }
            db.closeConnection();
        }catch (SQLException e){
            throw new RuntimeException();
        }
        return users;
    }

}

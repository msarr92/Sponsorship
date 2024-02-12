package com.groupeisi.sponsorship.controllers;

import com.groupeisi.sponsorship.DBConnexion;
import com.groupeisi.sponsorship.SessionUser;
import com.groupeisi.sponsorship.entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class candidatController implements Initializable {

    @FXML
    private TableColumn<User, Integer> id;

    @FXML
    private TextField nbreParrain;

    @FXML
    private TableColumn<User, String> nom;

    @FXML
    private TableColumn<User, String> prenom;

    @FXML
    private TableView<User> tabPar;

    private DBConnexion db=new DBConnexion();


    private int idUserConnecter;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadTable();
        idUserConnecter= SessionUser.getUserId();

    }

    public int nbrParrainCand(int candidat){
        String sql="SELECT COUNT(*) FROM parrainer WHERE candidat = ?";
        ResultSet rs;
        int cpt=0;
        try {
            db.initPrepar(sql);
            db.getPstm().setInt(1,candidat);
           rs= db.executeSelect();
           while (rs.next()){
               cpt++;
           }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cpt;
    }

    int compter=nbrParrainCand(idUserConnecter);



    public void loadTable(){
        nbreParrain.setText(String.valueOf(compter));
        ObservableList<User> liste=getParrain();
        tabPar.setItems(liste);
        id.setCellValueFactory(new PropertyValueFactory<User,Integer>("id"));
        nom.setCellValueFactory(new PropertyValueFactory<User,String>("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<User,String>("prenom"));
    }
    public ObservableList<User> getParrain(){
        ObservableList<User> users= FXCollections.observableArrayList();
        String sql="SELECT * FROM user JOIN role on user.profil=role.idRole WHERE profil=3";
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

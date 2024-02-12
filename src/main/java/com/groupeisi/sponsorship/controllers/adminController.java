package com.groupeisi.sponsorship.controllers;

import com.groupeisi.sponsorship.DBConnexion;
import com.groupeisi.sponsorship.entities.Role;
import com.groupeisi.sponsorship.entities.User;
import com.groupeisi.sponsorship.tools.Notification;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;


import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class adminController implements Initializable {

    @FXML
    private Button btnActiver;

    @FXML
    private Button btnAjouter;

    @FXML
    private Button btnDesactiver;

    @FXML
    private ComboBox<Role> choiceBox;

    @FXML
    private TextField loginTfx;

    @FXML
    private TableColumn<User, String> login;

    @FXML
    private TableColumn<User, Integer> id;

    @FXML
    private TableColumn<User, String> nom;

    @FXML
    private TextField nomTfx;

    @FXML
    private TableColumn<User, String> prenom;

    @FXML
    private TextField prenomTfx;

    @FXML
    private TableColumn<User, Role> profil;

    @FXML
    private TextField profilTfx;

    @FXML
    private TableView<User> table;

    private int idD;

    private DBConnexion db=new DBConnexion();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadTable();
        loadComboBox();

    }


    public ObservableList<User> getUser(){
        ObservableList<User> users= FXCollections.observableArrayList();
        String sql="SELECT * FROM user JOIN role on user.profil=role.idRole WHERE profil!=1";
        try{
            db.initPrepar(sql);
            ResultSet rs=db.executeSelect();
            while(rs.next()){
                User user= new User();
                user.setId(rs.getInt("idUser"));
                user.setNom(rs.getString("nom"));
                user.setPrenom(rs.getString("prenom"));
                user.setLogin(rs.getString("login"));
                Role role= new Role();
                role.setName(rs.getString("name"));
                user.setProfil(role);
                users.add(user);
            }
            //db.closeConnection();
        }catch (SQLException e){
            throw new RuntimeException();
        }
        return users;
    }



    public ObservableList<Role> getRole(){
        ObservableList<Role> roles= FXCollections.observableArrayList();
        String sql="SELECT * FROM role ORDER BY role.name ASC";
        try{
            db.initPrepar(sql);
            ResultSet rs=db.executeSelect();
            while(rs.next()){
                Role role= new Role();
               // role.setIdRole(rs.getInt("idRole"));
                role.setName(rs.getString("name"));
                roles.add(role);
            }
            db.closeConnection();
        }catch (SQLException e){
            throw new RuntimeException();
        }
        return roles;
    }

    public void loadComboBox(){
        ObservableList<Role> roles=getRole();
        choiceBox.setItems(roles);
    }

    public void loadTable(){
        ObservableList<User> liste=getUser();
        table.setItems(liste);
        id.setCellValueFactory(new PropertyValueFactory<User,Integer>("id"));
        nom.setCellValueFactory(new PropertyValueFactory<User,String>("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<User,String>("prenom"));
        login.setCellValueFactory(new PropertyValueFactory<User,String>("login"));

    }

    private int getroleId(String roleName){
        String sql="SELECT idRole FROM role WHERE name=?";
        try{
            db.initPrepar(sql);
            db.getPstm().setString(1,roleName);
            ResultSet rs= db.executeSelect();
            if (rs!=null && rs.next()) {
                return rs.getInt("idRole");
            }
        }catch (SQLException e){
            throw new RuntimeException();
        }
        return -1;
    }

    @FXML
    void ajoutUser(ActionEvent event) {
        String sql="INSERT INTO user (nom,prenom,login,password,actived,profil) VALUES(?,?,?,?,?,?)";
        try{
            String nom=nomTfx.getText(),prenom=prenomTfx.getText();
            String login=loginTfx.getText();
            String selectRole=choiceBox.getValue().getName();
            int roleId=getroleId(selectRole);
            db.initPrepar(sql);
            db.getPstm().setString(1,nom);
            db.getPstm().setString(2,prenom);
            db.getPstm().setString(3,login.toLowerCase()+nom+"1234");
            db.getPstm().setString(4,"passer");
            db.getPstm().setInt(5,1);
            db.getPstm().setInt(6,roleId);
            db.executeMaj();
            db.closeConnection();
            loadTable();
        }catch (SQLException e){
            throw new RuntimeException();
        }
    }

    @FXML
    void activer(ActionEvent event) {

    }

    @FXML
    void desactiver(ActionEvent event) {
        String sql="UPDATE user SET actived=? WHERE idUser= ? ";
        try{
            db.initPrepar(sql);
            db.getPstm().setInt(1,0);
            db.getPstm().setInt(2,idD);
            db.executeMaj();
            db.closeConnection();
            loadTable();
            Notification.NotifSuccess("Succes","Vous avez desactiver ");
        }catch (SQLException e){
            throw new RuntimeException();
        }
    }



    @FXML
    void getData(MouseEvent event) {
        User user=table.getSelectionModel().getSelectedItem();
        idD=user.getId();
        prenomTfx.setText(user.getPrenom());
        nomTfx.setText(user.getNom());
        btnAjouter.setDisable(true);
    }

}

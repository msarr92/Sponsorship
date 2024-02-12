package com.groupeisi.sponsorship.repository.parrainnage;

import com.groupeisi.sponsorship.DBConnexion;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ParrainImpl implements IParrain {

    private DBConnexion db=new DBConnexion();

    // Méthode pour vérifier si l'électeur a déjà parrainé ce candidat
    @SneakyThrows
    @Override
    public boolean dejaParrain(int idElecteur) {
        String sql="SELECT *FROM parrainer WHERE electeur=?  ";
        ResultSet rs = null;
        try{
            db.initPrepar(sql);
            db.getPstm().setInt(1,idElecteur);
            rs= db.executeSelect();
        }catch (Exception e){
            e.printStackTrace();
        }
        if (rs.next()){
            return true;
        }else {
            return false;
        }
    }

    public void parrainer(int electeur, int candidat) {
        String sql = "INSERT INTO parrainer (DatePar, electeur, candidat) VALUES (CURRENT_TIMESTAMP, ?, ?)";
        try {
            db.initPrepar(sql);
            db.getPstm().setInt(1,electeur);
            db.getPstm().setInt(2,candidat);
            db.executeMaj();
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception, par exemple afficher un message à l'utilisateur
        }
    }

    public int nbrParrainCand(int candidat){
        String sql="SELECT COUNT(*) FROM parrainer WHERE candidat = ?";
        int cpt=0;
        ResultSet rs;
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

    /*
    // Méthode pour récupérer la liste des électeurs qui ont parrainé un candidat donné
    public List<Utilisateur> getElecteursParrainantCandidat(int idCandidat) {
        List<Utilisateur> electeursParrainants = new ArrayList<>();
        String sql = "SELECT electeur FROM parrainer WHERE candidat = ?";

        try (Connection connection = // obtenir la connexion à la base de données
                     PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, idCandidat);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int idElecteur = resultSet.getInt("electeur");
                    // Vous devez implémenter une méthode pour récupérer un Utilisateur par son ID
                    Utilisateur electeur = utilisateurDAO.getUtilisateurParId(idElecteur);
                    electeursParrainants.add(electeur);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return electeursParrainants;
    }
     */


}

package com.groupeisi.sponsorship.repository.role;

import com.groupeisi.sponsorship.DBConnexion;
import com.groupeisi.sponsorship.entities.Role;

import com.groupeisi.sponsorship.DBConnexion;

import java.sql.ResultSet;

public class RoleImpl implements IRole {
    private DBConnexion db = new DBConnexion();
    private ResultSet rs;
    @Override
    public Role getRoleById(int id) {
        String sql = "SELECT * FROM role WHERE idRole = ?";
        Role role = null;
        try {
            db.initPrepar(sql);
            db.getPstm().setInt(1, id);
            rs = db.executeSelect();
            if(rs.next()){
                role = new Role(
                        rs.getInt("idRole"),
                        rs.getString("name"),
                        rs.getInt("etat")
                );
            }
            db.closeConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
        return role;
    }
}

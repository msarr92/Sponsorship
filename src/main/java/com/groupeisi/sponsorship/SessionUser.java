package com.groupeisi.sponsorship;

import com.groupeisi.sponsorship.entities.User;
import lombok.Getter;

public class SessionUser {

    @Getter
    private static int userId;

    public static void setUserId(int newUserId) {
        userId = newUserId;
    }


    public static boolean estConnecte() {
        return userId != 0;
    }
}


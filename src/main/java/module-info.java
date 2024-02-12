module com.groupeisi.sponsorship {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires TrayNotification;
    requires lombok;


    opens com.groupeisi.sponsorship to javafx.fxml;
    exports com.groupeisi.sponsorship;
    exports com.groupeisi.sponsorship.controllers;
    opens com.groupeisi.sponsorship.controllers to javafx.fxml;
    opens com.groupeisi.sponsorship.entities to javafx.base;
}
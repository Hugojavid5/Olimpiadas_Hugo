module org.hugo.olimpiadas_hugo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    exports Language;
    exports controlador;
    opens controlador to javafx.fxml;
    opens Language to javafx.fxml;
    exports model;
    exports Dao;
    opens org.hugo.olimpiadas_hugo to javafx.fxml;
    exports org.hugo.olimpiadas_hugo;
}
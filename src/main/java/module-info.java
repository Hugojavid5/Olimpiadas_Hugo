module org.hugo.olimpiadas_hugo {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.hugo.olimpiadas_hugo to javafx.fxml;
    exports org.hugo.olimpiadas_hugo;
}
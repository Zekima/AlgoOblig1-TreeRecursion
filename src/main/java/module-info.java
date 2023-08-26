module com.example.treapplikasjon {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.example.treapplikasjon to javafx.fxml;
    exports com.example.treapplikasjon;
}
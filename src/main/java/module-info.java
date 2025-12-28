module com.example.resumefx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires org.apache.pdfbox;

    opens com.example.resumefx to javafx.fxml;
    exports com.example.resumefx;
}
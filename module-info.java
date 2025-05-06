module de.bbq.tagebuch {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;

    // Nur das JAXB-API-Modul braucht es wirklich:
    requires jakarta.xml.bind;

    // Öffne Dein Package für FXML + JAXB-Reflection
    opens de.bbq.tagebuch to javafx.fxml, jakarta.xml.bind;

    exports de.bbq.tagebuch;
}

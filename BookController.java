package de.bbq.tagebuch;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class BookController {

    @FXML private Label userLabel;
    @FXML private Label pageLabel;

    // Read‐only view nodes
    @FXML private VBox  readPane;
    @FXML private Label readDate;
    @FXML private Label readTitle;
    @FXML private Label readText;

    // Edit view nodes
    @FXML private VBox        editPane;
    @FXML private DatePicker  leftDate;
    @FXML private TextField   leftTitle;
    @FXML private TextArea    leftArea;
    @FXML private ImageView   leftImage;
    @FXML private Button      loadImageButton;
    @FXML private ColorPicker colorPicker;
    @FXML private Button      saveButton;

    // Navigation & mode toggle
    @FXML private Button       prevButton;
    @FXML private Button       nextButton;
    @FXML private Button       newEntryButton;
    @FXML private ToggleButton editToggle;

    private List<TagebuchEintrag> entries;
    private int                   currentIndex = 0;
    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @FXML
    public void initialize() {
        TagebuchEintraege wrap = TagebuchRepository.load();
        entries = wrap.getEntries();

        colorPicker.setValue(javafx.scene.paint.Color.web("#FFFDE7"));
        setupModeListener();
        bindReadFields();
        showPage(0);
    }

    public void setUserName(String name) {
        userLabel.setText("Dein Tagebuch, " + name);
    }

    private void showPage(int idx) {
        if (entries.isEmpty()) {
            prevButton.setDisable(true);
            nextButton.setDisable(true);
            pageLabel.setText("Seite 0 / 0");
            applyBackground(readPane, "#FFFFFF");
            applyBackground(editPane, "#FFFDE7");
            editToggle.setSelected(false);
            leftDate.setValue(null);
            leftTitle.clear();
            leftArea.clear();
            loadImage(leftImage, null);
            return;
        }

        int safeIdx = Math.max(0, Math.min(idx, entries.size() - 1));
        currentIndex = safeIdx;

        TagebuchEintrag e = entries.get(safeIdx);
        leftDate.setValue(e.getDatum());
        leftTitle.setText(e.getUeberschrift());
        leftArea.setText(e.getText());
        loadImage(leftImage, e.getImagePath());

        applyBackground(editPane, e.getSeitenFarbe());
        applyBackground(readPane, e.getSeitenFarbe());

        prevButton.setDisable(safeIdx == 0);
        nextButton.setDisable(safeIdx >= entries.size() - 1);
        pageLabel.setText("Seite " + (safeIdx + 1) + " / " + entries.size());
    }

    @FXML private void handlePrev() { showPage(currentIndex - 1); }
    @FXML private void handleNext() { showPage(currentIndex + 1); }

    @FXML private void handleNewEntry() {
        editToggle.setSelected(true);
        TagebuchEintrag neu = new TagebuchEintrag(
            LocalDate.now(), "", "", toHex(colorPicker.getValue()), null
        );
        entries.add(neu);
        showPage(entries.size() - 1);
        TagebuchRepository.save(new TagebuchEintraege(entries));
    }

    @FXML
    private void handleSave() {
        if (!editToggle.isSelected()) return;

        // Wenn noch keine Einträge existieren, neuen anlegen
        if (entries.isEmpty()) {
            TagebuchEintrag neu = new TagebuchEintrag(
                leftDate.getValue(),
                leftTitle.getText(),
                leftArea.getText(),
                toHex(colorPicker.getValue()),
                null
            );
            entries.add(neu);
            currentIndex = entries.size() - 1;
        } else {
            // Existierenden aktualisieren
            TagebuchEintrag neu = new TagebuchEintrag(
                leftDate.getValue(),
                leftTitle.getText(),
                leftArea.getText(),
                toHex(colorPicker.getValue()),
                entries.get(currentIndex).getImagePath()
            );
            entries.set(currentIndex, neu);
        }

        showPage(currentIndex);
        TagebuchRepository.save(new TagebuchEintraege(entries));
    }

    @FXML private void handleLoadImage() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Bild auswählen");
        Stage stage = (Stage) loadImageButton.getScene().getWindow();
        File file = fc.showOpenDialog(stage);
        if (file != null) {
            String path = file.toURI().toString();
            entries.get(currentIndex).setImagePath(path);
            loadImage(leftImage, path);
            TagebuchRepository.save(new TagebuchEintraege(entries));
        }
    }

    private void setupModeListener() {
        editToggle.selectedProperty().addListener((obs, oldVal, newVal) -> {
            readPane.setVisible(!newVal);
            readPane.setManaged(!newVal);
            editPane.setVisible(newVal);
            editPane.setManaged(newVal);
            newEntryButton.setDisable(!newVal);
        });
        readPane.setVisible(true);
        readPane.setManaged(true);
        editPane.setVisible(false);
        editPane.setManaged(false);
        newEntryButton.setDisable(true);
    }

    private void bindReadFields() {
        readDate.textProperty().bind(
            Bindings.createStringBinding(
                () -> {
                    var d = leftDate.getValue();
                    return d != null ? d.format(fmt) : "–";
                },
                leftDate.valueProperty()
            )
        );
        readTitle.textProperty().bind(
            Bindings.when(leftTitle.textProperty().isEmpty())
                    .then("–")
                    .otherwise(leftTitle.textProperty())
        );
        readText.textProperty().bind(leftArea.textProperty());
    }

    private void applyBackground(Region pane, String hex) {
        try {
            var c = javafx.scene.paint.Color.web(hex);
            pane.setBackground(new Background(
                new BackgroundFill(c, CornerRadii.EMPTY, Insets.EMPTY)
            ));
        } catch (Exception ignored) {}
    }

    private void loadImage(ImageView iv, String path) {
        if (path == null || path.isBlank()) {
            iv.setImage(null);
            iv.setVisible(false);
        } else {
            iv.setImage(new Image(path, true));
            iv.setVisible(true);
        }
    }

    private String toHex(javafx.scene.paint.Color c) {
        int r = (int)Math.round(c.getRed()   * 255);
        int g = (int)Math.round(c.getGreen() * 255);
        int b = (int)Math.round(c.getBlue()  * 255);
        return String.format("#%02X%02X%02X", r, g, b);
    }
}

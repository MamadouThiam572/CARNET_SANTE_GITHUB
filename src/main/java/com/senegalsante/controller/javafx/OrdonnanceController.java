package com.senegalsante.controller.javafx;

import com.senegalsante.model.Ordonnance;
import com.senegalsante.model.Ordonnance.LigneOrdonnance;
import com.senegalsante.model.User;
import com.senegalsante.service.OrdonnanceService;
import com.senegalsante.util.SpringContext;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Controller
public class OrdonnanceController {

    private OrdonnanceService ordonnanceService;

    // --- LIST VIEW ---
    @FXML
    private TableView<Ordonnance> ordonnanceTable;
    @FXML
    private TableColumn<Ordonnance, String> dateColumn;
    @FXML
    private TableColumn<Ordonnance, String> medecinColumn;
    @FXML
    private TableColumn<Ordonnance, String> specialiteColumn;
    @FXML
    private TableColumn<Ordonnance, String> traitementColumn;
    @FXML
    private TableColumn<Ordonnance, Void> actionsColumn;

    // --- FORM VIEW ---
    @FXML
    private TextField medecinField;
    @FXML
    private TextField specialiteField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField dureeField;
    @FXML
    private TextArea commentairesArea;
    @FXML
    private Label photoLabel;
    private String photoPath;

    // Form - Médicaments
    @FXML
    private TextField medNomField;
    @FXML
    private TextField medDosageField;
    @FXML
    private TextField medFreqField;
    @FXML
    private TableView<LigneOrdonnance> medicamentsTable;
    @FXML
    private TableColumn<LigneOrdonnance, String> medNomByIdColumn;
    @FXML
    private TableColumn<LigneOrdonnance, String> medDosageByIdColumn;
    @FXML
    private TableColumn<LigneOrdonnance, String> medFreqByIdColumn;
    @FXML
    private TableColumn<LigneOrdonnance, Void> medActionByIdColumn;

    private User currentUser;
    private Ordonnance currentOrdonnance; // For edition
    private Stage dialogStage;

    // Internal list for the form
    private ObservableList<LigneOrdonnance> formMedicaments = FXCollections.observableArrayList();

    public OrdonnanceController() {
        // Dependency Injection handled manually via SpringContext if not autowired by
        // Spring directly
    }

    // Used to set service manually or via context
    public void setOrdonnanceService(OrdonnanceService service) {
        this.ordonnanceService = service;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
        if (ordonnanceTable != null) {
            refreshList();
        }
    }

    public void setDialogStage(Stage stage) {
        this.dialogStage = stage;
    }

    @FXML
    public void initialize() {
        // Init service if needed (fallback)
        if (ordonnanceService == null) {
            try {
                ordonnanceService = SpringContext.getContext().getBean(OrdonnanceService.class);
            } catch (Exception e) {
                // Ignore if context not ready yet (will be set manually)
            }
        }

        // Setup List View
        if (ordonnanceTable != null) {
            setupTable();
        }

        // Setup Form View
        if (medicamentsTable != null) {
            setupFormTable();
        }
    }

    private void setupTable() {
        dateColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getDatePrescription().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        medecinColumn.setCellValueFactory(new PropertyValueFactory<>("nomMedecin"));
        specialiteColumn.setCellValueFactory(new PropertyValueFactory<>("specialite"));
        traitementColumn.setCellValueFactory(cellData -> {
            int count = cellData.getValue().getMedicaments().size();
            String summary = count > 0 ? cellData.getValue().getMedicaments().get(0).getNomMedicament() : "Aucun";
            if (count > 1)
                summary += " (+" + (count - 1) + " autres)";
            return new javafx.beans.property.SimpleStringProperty(summary);
        });

        // Actions
        actionsColumn.setCellFactory(param -> new TableCell<>() {
            private final Button viewBtn = new Button("Voir");
            private final Button editBtn = new Button("Modifier");
            private final Button deleteBtn = new Button("Supprimer");
            private final HBox pane = new HBox(5, viewBtn, editBtn, deleteBtn);

            {
                viewBtn.setStyle(
                        "-fx-background-color: #3b82f6; -fx-text-fill: white; -fx-padding: 3 8; -fx-background-radius: 4; -fx-cursor: hand; -fx-font-size: 11px;");
                editBtn.setStyle(
                        "-fx-background-color: #10b981; -fx-text-fill: white; -fx-padding: 3 8; -fx-background-radius: 4; -fx-cursor: hand; -fx-font-size: 11px;");
                deleteBtn.setStyle(
                        "-fx-background-color: #ef4444; -fx-text-fill: white; -fx-padding: 3 8; -fx-background-radius: 4; -fx-cursor: hand; -fx-font-size: 11px;");

                viewBtn.setOnAction(event -> {
                    Ordonnance ord = getTableView().getItems().get(getIndex());
                    if (ord.getCheminPhoto() != null && !ord.getCheminPhoto().isEmpty()) {
                        afficherPhoto(ord.getCheminPhoto());
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText("Aucune photo");
                        alert.setContentText("Cette ordonnance n'a pas de photo associée.");
                        alert.showAndWait();
                    }
                });
                editBtn.setOnAction(event -> handleModifier(getTableView().getItems().get(getIndex())));
                deleteBtn.setOnAction(event -> handleSupprimer(getTableView().getItems().get(getIndex())));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : pane);
            }
        });
    }

    private void afficherPhoto(String cheminPhoto) {
        try {
            Stage photoStage = new Stage();
            photoStage.setTitle("Photo de l'ordonnance");

            javafx.scene.image.Image image = new javafx.scene.image.Image("file:" + cheminPhoto);
            javafx.scene.image.ImageView imageView = new javafx.scene.image.ImageView(image);
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(800);

            javafx.scene.layout.StackPane root = new javafx.scene.layout.StackPane(imageView);
            root.setStyle("-fx-background-color: #f3f4f6; -fx-padding: 20;");

            Scene scene = new Scene(root);
            photoStage.setScene(scene);
            photoStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erreur");
            alert.setContentText("Impossible d'afficher la photo.");
            alert.showAndWait();
        }
    }

    private void refreshList() {
        if (currentUser != null && ordonnanceService != null && ordonnanceTable != null) {
            ordonnanceTable.setItems(FXCollections.observableArrayList(
                    ordonnanceService.getOrdonnancesByUser(currentUser)));
        }
    }

    // --- ACTIONS PRINCIPALES ---

    @FXML
    public void handleAjouterOrdonnance() {
        showDialog(null);
    }

    private void handleModifier(Ordonnance ordonnance) {
        showDialog(ordonnance);
    }

    private void handleSupprimer(Ordonnance ordonnance) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmer la suppression");
        alert.setHeaderText("Supprimer cette ordonnance ?");
        alert.setContentText("Cette action est irréversible.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                ordonnanceService.deleteOrdonnance(ordonnance);
                refreshList();
            }
        });
    }

    // --- GESTION DU DIALOGUE (FORMULAIRE) ---

    private void showDialog(Ordonnance ordonnance) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/add-ordonnance.fxml"));
            // Manually set controller factory or just let it load since we are same class?
            // Better: use SpringContext to get a NEW instance of THIS controller for the
            // dialog
            loader.setControllerFactory(SpringContext.getContext()::getBean);

            Parent page = loader.load();
            OrdonnanceController dialogController = loader.getController();

            Stage stage = new Stage();
            stage.setTitle(ordonnance == null ? "Nouvelle Ordonnance" : "Modifier Ordonnance");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(page));

            dialogController.setDialogStage(stage);
            dialogController.setOrdonnanceService(ordonnanceService);
            dialogController.setCurrentUser(currentUser);
            dialogController.initForm(ordonnance);

            stage.showAndWait();

            // Refresh main list after dialog closes
            refreshList();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Initialize Form Data
    public void initForm(Ordonnance ordonnance) {
        this.currentOrdonnance = (ordonnance != null) ? ordonnance : new Ordonnance();
        photoPath = null;

        if (ordonnance != null) {
            medecinField.setText(ordonnance.getNomMedecin());
            specialiteField.setText(ordonnance.getSpecialite());
            datePicker.setValue(ordonnance.getDatePrescription());
            dureeField.setText(ordonnance.getDureeTraitement());
            commentairesArea.setText(ordonnance.getCommentaires());
            formMedicaments.setAll(ordonnance.getMedicaments());

            // Photo
            if (ordonnance.getCheminPhoto() != null && !ordonnance.getCheminPhoto().isEmpty()) {
                photoPath = ordonnance.getCheminPhoto();
                photoLabel.setText("✓ Photo existante");
                photoLabel.setStyle("-fx-text-fill: #10b981;");
            } else {
                photoLabel.setText("Aucune photo");
                photoLabel.setStyle("-fx-text-fill: #999;");
            }
        } else {
            datePicker.setValue(LocalDate.now());
            if (photoLabel != null) {
                photoLabel.setText("Aucune photo");
                photoLabel.setStyle("-fx-text-fill: #999;");
            }
        }

        medicamentsTable.setItems(formMedicaments);
    }

    private void setupFormTable() {
        medNomByIdColumn.setCellValueFactory(new PropertyValueFactory<>("nomMedicament"));
        medDosageByIdColumn.setCellValueFactory(new PropertyValueFactory<>("dosage"));
        medFreqByIdColumn.setCellValueFactory(new PropertyValueFactory<>("frequence"));

        medActionByIdColumn.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button("X");
            {
                btn.setStyle(
                        "-fx-text-fill: red; -fx-background-color: transparent; -fx-cursor: hand; -fx-font-weight: bold;");
                btn.setOnAction(e -> {
                    LigneOrdonnance item = getTableView().getItems().get(getIndex());
                    formMedicaments.remove(item);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
    }

    @FXML
    public void handleAjouterMedicament() {
        if (medNomField.getText().isEmpty())
            return;

        LigneOrdonnance ligne = new LigneOrdonnance(
                medNomField.getText(),
                medDosageField.getText(),
                medFreqField.getText());
        formMedicaments.add(ligne);

        medNomField.clear();
        medDosageField.clear();
        medFreqField.clear();
        medNomField.requestFocus();
    }

    @FXML
    public void handleChoisirPhoto() {
        javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();
        fileChooser.setTitle("Choisir une photo de l'ordonnance");
        fileChooser.getExtensionFilters().addAll(
                new javafx.stage.FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.gif"));

        java.io.File selectedFile = fileChooser.showOpenDialog(dialogStage);
        if (selectedFile != null) {
            try {
                // Créer le dossier ordonnances s'il n'existe pas
                java.io.File ordonnancesDir = new java.io.File("ordonnances_photos");
                if (!ordonnancesDir.exists()) {
                    ordonnancesDir.mkdirs();
                }

                // Copier le fichier avec un nom unique
                String fileName = System.currentTimeMillis() + "_" + selectedFile.getName();
                java.io.File destFile = new java.io.File(ordonnancesDir, fileName);
                java.nio.file.Files.copy(selectedFile.toPath(), destFile.toPath(),
                        java.nio.file.StandardCopyOption.REPLACE_EXISTING);

                photoPath = destFile.getAbsolutePath();
                photoLabel.setText("✓ " + selectedFile.getName());
                photoLabel.setStyle("-fx-text-fill: #10b981;");
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Erreur");
                alert.setContentText("Impossible de charger la photo.");
                alert.showAndWait();
            }
        }
    }

    @FXML
    public void handleEnregistrer() {
        if (medecinField.getText().isEmpty() || datePicker.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Champs manquants");
            alert.setContentText("Le nom du médecin et la date sont obligatoires.");
            alert.showAndWait();
            return;
        }

        currentOrdonnance.setNomMedecin(medecinField.getText());
        currentOrdonnance.setSpecialite(specialiteField.getText());
        currentOrdonnance.setDatePrescription(datePicker.getValue());
        currentOrdonnance.setDureeTraitement(dureeField.getText());
        currentOrdonnance.setCommentaires(commentairesArea.getText());
        currentOrdonnance.setUser(currentUser);
        currentOrdonnance.setCheminPhoto(photoPath);

        // Update medications list (clear and addAll to ensure persistence updates)
        currentOrdonnance.getMedicaments().clear();
        currentOrdonnance.getMedicaments().addAll(new ArrayList<>(formMedicaments));

        ordonnanceService.saveOrdonnance(currentOrdonnance);

        dialogStage.close();
    }

    @FXML
    public void handleAnnuler() {
        dialogStage.close();
    }

    // --- NAVIGATION & SIDEBAR ---
    @FXML
    private BorderPane mainContainer;

    @FXML
    public void handleDashboard() {
        navigateToScreen("/fxml/dashboard.fxml");
    }

    @FXML
    public void handleMedications() {
        navigateToScreen("/fxml/medications.fxml");
    }

    @FXML
    public void handleAppointments() {
        navigateToScreen("/fxml/appointments.fxml");
    }

    @FXML
    public void handleHealthTracking() {
        navigateToScreen("/fxml/health-tracking.fxml");
    }

    @FXML
    public void handleFamily() {
        navigateToScreen("/fxml/family.fxml");
    }

    @FXML
    public void handlePrescriptions() {
    } // Deja ici

    @FXML
    public void handleProfile() {
        navigateToScreen("/fxml/profile.fxml");
    }

    @FXML
    public void handleLogout() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Déconnexion");
        alert.setHeaderText("Voulez-vous vraiment vous déconnecter ?");
        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            navigateToScreen("/fxml/login.fxml");
        }
    }

    private void navigateToScreen(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            loader.setControllerFactory(SpringContext.getContext()::getBean);
            Parent root = loader.load();

            // Pass currentUser
            Object controller = loader.getController();
            if (controller instanceof DashboardController) {
                ((DashboardController) controller).setCurrentUser(currentUser);
            } else if (controller instanceof OrdonnanceController) {
                ((OrdonnanceController) controller).setCurrentUser(currentUser);
            }

            if (mainContainer != null && mainContainer.getScene() != null) {
                mainContainer.getScene().setRoot(root);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

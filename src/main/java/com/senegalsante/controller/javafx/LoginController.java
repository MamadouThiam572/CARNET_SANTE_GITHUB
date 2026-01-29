package com.senegalsante.controller.javafx;

import com.senegalsante.model.User;
import com.senegalsante.repository.UserRepository;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.senegalsante.util.SpringContext;

import java.util.Optional;

/**
 * Contrôleur pour l'écran de connexion
 * 
 * Responsabilités :
 * - Gérer l'authentification des utilisateurs
 * - Valider les champs de saisie
 * - Naviguer vers le dashboard après connexion réussie
 * - Afficher les messages d'erreur
 * 
 * Annotations JavaFX :
 * 
 * @FXML : Lie les éléments du fichier FXML à ce contrôleur
 */
@Component
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    // Éléments de l'interface (liés au fichier login.fxml)
    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button loginButton;

    @FXML
    private Hyperlink registerLink;

    /**
     * Méthode initialize() : Appelée automatiquement après le chargement du FXML
     * Utilisée pour initialiser les composants
     */
    @FXML
    public void initialize() {
        // Masquer le message d'erreur au démarrage
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);

        // Ajouter des écouteurs pour effacer l'erreur lors de la saisie
        emailField.textProperty().addListener((obs, oldVal, newVal) -> hideError());
        passwordField.textProperty().addListener((obs, oldVal, newVal) -> hideError());
    }

    /**
     * Gère le clic sur le bouton "Se connecter"
     */
    @FXML
    private void handleLogin() {
        String email = emailField.getText().trim();
        String password = passwordField.getText();

        // Validation des champs
        if (email.isEmpty() || password.isEmpty()) {
            showError("Veuillez remplir tous les champs");
            return;
        }

        // Validation du format email
        if (!isValidEmail(email)) {
            showError("Format d'email invalide");
            return;
        }

        // Recherche de l'utilisateur dans la base de données
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isEmpty()) {
            showError("Email ou mot de passe incorrect");
            return;
        }

        User user = userOpt.get();

        // Vérification du mot de passe
        if (!user.getPassword().equals(password)) {
            showError("Email ou mot de passe incorrect");
            return;
        }

        // Connexion réussie - Navigation vers le dashboard
        navigateToDashboard(user);
    }

    /**
     * Gère le clic sur le lien "Créer un compte"
     */
    @FXML
    private void handleRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/register.fxml"));
            loader.setControllerFactory(SpringContext.getContext()::getBean);
            Parent root = loader.load();

            Scene scene = emailField.getScene();

            // Animation de transition
            FadeTransition fadeOut = new FadeTransition(Duration.millis(200), scene.getRoot());
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.setOnFinished(e -> {
                scene.setRoot(root);
                FadeTransition fadeIn = new FadeTransition(Duration.millis(200), root);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                fadeIn.play();
            });
            fadeOut.play();

        } catch (Exception e) {
            e.printStackTrace();
            showError("Erreur lors du chargement de l'écran d'inscription");
        }
    }

    /**
     * Navigation vers le dashboard après connexion réussie
     */
    private void navigateToDashboard(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dashboard.fxml"));
            loader.setControllerFactory(SpringContext.getContext()::getBean);
            Parent root = loader.load();

            // Passer l'utilisateur au contrôleur du dashboard
            DashboardController dashboardController = loader.getController();
            dashboardController.setCurrentUser(user);

            Scene scene = emailField.getScene();

            // Animation de transition
            FadeTransition fadeOut = new FadeTransition(Duration.millis(200), scene.getRoot());
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.setOnFinished(e -> {
                scene.setRoot(root);
                FadeTransition fadeIn = new FadeTransition(Duration.millis(200), root);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                fadeIn.play();
            });
            fadeOut.play();

        } catch (Exception e) {
            e.printStackTrace();
            showError("Erreur lors du chargement du dashboard");
        }
    }

    /**
     * Affiche un message d'erreur
     */
    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
        errorLabel.setManaged(true);

        // Animation de shake (secousse)
        errorLabel.setTranslateX(0);
        javafx.animation.Timeline timeline = new javafx.animation.Timeline(
                new javafx.animation.KeyFrame(Duration.millis(0),
                        new javafx.animation.KeyValue(errorLabel.translateXProperty(), 0)),
                new javafx.animation.KeyFrame(Duration.millis(50),
                        new javafx.animation.KeyValue(errorLabel.translateXProperty(), -10)),
                new javafx.animation.KeyFrame(Duration.millis(100),
                        new javafx.animation.KeyValue(errorLabel.translateXProperty(), 10)),
                new javafx.animation.KeyFrame(Duration.millis(150),
                        new javafx.animation.KeyValue(errorLabel.translateXProperty(), -10)),
                new javafx.animation.KeyFrame(Duration.millis(200),
                        new javafx.animation.KeyValue(errorLabel.translateXProperty(), 0)));
        timeline.play();
    }

    /**
     * Masque le message d'erreur
     */
    private void hideError() {
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);
    }

    /**
     * Valide le format d'un email
     */
    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }
}

package com.senegalsante.controller.javafx;

import com.senegalsante.model.User;
import com.senegalsante.service.UserService;
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

@Component
public class RegisterController {

    @Autowired
    private UserService userService;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Label errorLabel;

    @FXML
    public void initialize() {
        if (errorLabel != null) {
            errorLabel.setVisible(false);
            errorLabel.setManaged(false);
        }
    }

    @FXML
    private void handleRegister() {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
            showError("Veuillez remplir tous les champs");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showError("Les mots de passe ne correspondent pas");
            return;
        }

        try {
            User user = userService.registerUser(firstName, lastName, email, phone, password);

            // Redirection directe vers le Dashboard
            navigateToDashboard(user);

        } catch (RuntimeException e) {
            showError(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            showError("Une erreur est survenue lors de l'inscription");
        }
    }

    private void navigateToDashboard(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dashboard.fxml"));
            loader.setControllerFactory(SpringContext.getContext()::getBean);
            Parent root = loader.load();

            // Passer l'utilisateur au contrôleur du dashboard
            DashboardController dashboardController = loader.getController();
            dashboardController.setCurrentUser(user);

            Scene scene = emailField.getScene();
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
            showError("Erreur lors du chargement de l'accueil");
        }
    }

    @FXML
    private void handleBackToLogin() {
        navigateToScreen("/fxml/login.fxml");
    }

    private void navigateToScreen(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            loader.setControllerFactory(SpringContext.getContext()::getBean);
            Parent root = loader.load();

            Scene scene = emailField.getScene();
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
            showError("Erreur lors de la navigation");
        }
    }

    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
        errorLabel.setManaged(true);
    }
}

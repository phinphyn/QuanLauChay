package com.example.demo;

import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.EventObject;
import java.util.ResourceBundle;


public class LoginController implements Initializable {
    @FXML
    private Button cancelButton;
    @FXML
    private Label loginMessageLabel;

    @FXML
    private ImageView lockImageView;
    @FXML
    private TextField EnterusernameTextField;
    @FXML
    private PasswordField EnterPasswordTextField;
    @FXML
    private Button loginbutton;
    @FXML
    private ImageView brandingImageView;
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;



    public void loginButtonOnAction(ActionEvent event) throws IOException, SQLException {

        if (!EnterusernameTextField.getText().isBlank() && !EnterPasswordTextField.getText().isBlank()){
            loginMessageLabel.setText("You try to Login");
            validateLogin();

        } else {
            loginMessageLabel.setText("Please Enter Username and password");
        }
    }


    public void cancelButtonAction(ActionEvent event){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }



    private void validateLogin() throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectionDB = connectNow.getConnection();

        String verifyLogin = "SELECT COUNT(1) FROM user_account WHERE Username = ? AND Password = ?";
        try {
            PreparedStatement preparedStatement = connectionDB.prepareStatement(verifyLogin);
            preparedStatement.setString(1, EnterusernameTextField.getText());
            preparedStatement.setString(2, EnterPasswordTextField.getText());

            ResultSet queryResult = preparedStatement.executeQuery();

            if (queryResult.next()) {
                int count = queryResult.getInt(1);
                if (count == 1) {
                    loginMessageLabel.setText("Congratulations!");

                    // Open home.fxml
                    Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
                    Stage stage = (Stage) loginMessageLabel.getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } else {
                    loginMessageLabel.setText("Invalid login. Try again!");
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            if (connectionDB != null) {
                connectionDB.close();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File branding = new File("@../../../../../../icons/Meal_1.png");
        Image brandingImage = new Image(branding.toURI().toString());
        brandingImageView.setImage(brandingImage);
    }





}

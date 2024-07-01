package edu.iCET.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import edu.iCET.bo.BoFactory;
import edu.iCET.bo.custom.UserBo;
import edu.iCET.dto.User;
import edu.iCET.util.BoType;
import jakarta.activation.CommandMap;
import jakarta.activation.MailcapCommandMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;

public class LoginFormController implements Initializable {
    public JFXTextField txtEmail;
    static int randomNumber;
    public Label lblNotValiedPassword;
    public JFXPasswordField txtPassword;
    public JFXButton btnRegister;
    public JFXButton btnLogin;
    private String encryptedpassword=null;
    private UserBo userBo= BoFactory.getInstance().getBo(BoType.User);

    private boolean isValidEmail(String email) {
        return email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    }

    public void sendOtpEmail() {

        String email=txtEmail.getText();
        String USERNAME = "clothyfystore@gmail.com"; // your new email
        String PASSWORD = "lczv zhpz hnrs dukm"; // your new password

        // Set up the SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        try {
            // Generate a random OTP
            Random random = new Random();
            randomNumber = random.nextInt(9000) + 1000;

            // Create an authenticator
            Authenticator auth = new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(USERNAME, PASSWORD);
                }
            };

            // Create a session with the properties and the authenticator
            Session session = Session.getInstance(properties, auth);

            // Create a new email message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(email)); // sender Email
            message.setSentDate(new Date());
            message.setSubject("Clothify Store");
            message.setText("Your OTP is: " + randomNumber);

            // Set MailcapCommandMap to avoid conflicts
            MailcapCommandMap mc = new MailcapCommandMap();
            mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
            mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
            mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
            mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
            CommandMap.setDefaultCommandMap(mc);

            // Send the message
            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblNotValiedPassword.setVisible(false);
    }


    public void txtPasswordOnAction(ActionEvent actionEvent) {

        String password = txtPassword.getText();
        String encryptedpassword = null;

        try{
            MessageDigest m=MessageDigest.getInstance("MD5");
            m.update(password.getBytes());

            byte[] bytes=m.digest();

            StringBuilder s=new StringBuilder();
            for(int i=0 ; i<bytes.length ; i++){
                s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));

            }
            encryptedpassword=s.toString();
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }

        System.out.println("My Password >>"+password);
        System.out.println("My Password generate >>"+encryptedpassword);

        if (password.equals(password)){
            System.out.println("Correct Password");
            try {
                Parent fxmlLorder= new FXMLLoader(getClass().getResource("/view/DashBord-page.fxml")).load();
                Stage stage=new Stage();
                stage.setScene(new Scene(fxmlLorder));

                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else {
            System.out.println("Not Correct");
        }
    }

    public void btnRegisterOnAction(ActionEvent actionEvent) {

        try {
            Parent fxmlLorder= new FXMLLoader(getClass().getResource("/view/User-Register-Form.fxml")).load();
            Stage stage=new Stage();
            stage.setScene(new Scene(fxmlLorder));
            ((Stage)btnRegister.getScene().getWindow()).close();
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnLoginOnAction(ActionEvent actionEvent) {

        String email=txtEmail.getText();
        if(email.isEmpty() || !isValidEmail(email)){
            showAlert("Please enter a valid email address.");
            return;
        }

        User search=null;

        try {
             search= userBo.search(email);
        }catch (Exception e){
            showAlert("User not found for email");
            return;
        }
        passwordGenaret();
        System.out.println(search.getPassword());
        System.out.println(encryptedpassword);
        if(encryptedpassword.equals(search.getPassword())){
            try {
                Parent fxmlLorder= new FXMLLoader(getClass().getResource("/view/DashBord-page.fxml")).load();
                Stage stage=new Stage();
                stage.setScene(new Scene(fxmlLorder));
                ((Stage)btnLogin.getScene().getWindow()).close();
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else {
            lblNotValiedPassword.setVisible(true);
        }
    }
    private void passwordGenaret() {

        String password=txtPassword.getText();
        encryptedpassword = null;

        try{
            MessageDigest m=MessageDigest.getInstance("MD5");
            m.update(password.getBytes());

            byte[] bytes=m.digest();

            StringBuilder s=new StringBuilder();
            for(int i=0 ; i<bytes.length ; i++){
                s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));

            }
            encryptedpassword=s.toString();
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }

        System.out.println("My Password >>"+password);
        System.out.println("My Password generate >>"+encryptedpassword);
    }
}
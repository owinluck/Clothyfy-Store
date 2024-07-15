package edu.iCET.controller.user;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import edu.iCET.bo.BoFactory;
import edu.iCET.bo.custom.UserBo;
import edu.iCET.db.DbConnection;
import edu.iCET.dto.User;
import edu.iCET.util.BoType;
import jakarta.activation.CommandMap;
import jakarta.activation.MailcapCommandMap;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserRegisterForm implements Initializable {
    public JFXTextField txtAdminUserName;
    public JFXPasswordField txtAdminPassword;
    public JFXTextField txtOtp;
    public JFXButton btnSend;
    static int randomNumber;
    public Label lblValiedUserOrPasswordMg;
    public ImageView imgValued;
    public JFXTextField txtNewUserName;
    public JFXPasswordField txtUserPassword;
    public JFXTextField txtUserEmail;
    public JFXButton btnBack;
    public JFXTextField txtPhoneNumber;
    public Label lblEmpId;

    private String encryptedpassword=null;
    String adminPassword="admin";

    private UserBo userBo= BoFactory.getInstance().getBo(BoType.User);
    public void btnSendOnAction(ActionEvent actionEvent) {
        String email=txtAdminUserName.getText();
        String password=txtAdminPassword.getText();
        if(email.isEmpty() || !isValidEmail(email)){
            showAlert("Please enter a valid email address.");
            return;
        }
        if(password.isEmpty() || !adminPassword.equalsIgnoreCase(password)){
            showAlert("Please enter a valid password");
            return;
        }
        sendOtpEmail();
    }

    private boolean isValidEmail(String email) {
        return email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void sendOtpEmail() {


        String USERNAME = "email"; // your new email
        String PASSWORD = "////"; // your new password

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
            message.setRecipient(Message.RecipientType.TO, new InternetAddress("YOUR EMAIL")); // sender Email
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imgValued.setVisible(false);
        txtUserPassword.setEditable(false);
        txtNewUserName.setEditable(false);
        txtUserEmail.setEditable(false);
        generateOrderId();
    }

    private void passwordGenaret() {

        String password=txtUserPassword.getText();
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

    public void txtOtpOnAction(ActionEvent actionEvent) {

        int otp= Integer.parseInt(txtOtp.getText());
        if(randomNumber== otp){
            imgValued.setVisible(true);
            txtUserPassword.setEditable(true);
            txtNewUserName.setEditable(true);
            txtUserEmail.setEditable(true);
            txtPhoneNumber.setEditable(true);
        }
    }

    public void btnCreateOnAction(ActionEvent actionEvent) {

        String email=txtUserEmail.getText();
        if(email.isEmpty() || !isValidEmail(email)){
            showAlert("Please enter a valid email address.");
            return;
        }
        String newUserName = txtNewUserName.getText();
        if(newUserName.isEmpty()){
            showAlert("Empty User Name....Try Again...");
            return;
        }
        String password=txtUserPassword.getText();
        String empId=lblEmpId.getText();
        String phoneNumber=txtPhoneNumber.getText();
        if(password.isEmpty() || empId.isEmpty() || phoneNumber.isEmpty()){
            showAlert("User Data is Not Completed....Try Again...");
            return;
        }
        passwordGenaret();

        User user = new User(newUserName,email,encryptedpassword,empId,phoneNumber);
        boolean b = userBo.saveUser(user);
        System.out.println(b);
        if(b){
            showAlert("Employee Added....");
            generateOrderId();
            clearTextField();
        }
    }

    private void clearTextField() {
        txtNewUserName.setText(null);
        txtPhoneNumber.setText(null);
        txtUserEmail.setText(null);
        txtUserPassword.setText(null);
    }

    public void btnBackOnAction(ActionEvent actionEvent) {

        try {
            Parent fxmlLorder= new FXMLLoader(getClass().getResource("/view/Login-Form.fxml")).load();
            Stage stage=new Stage();
            stage.setScene(new Scene(fxmlLorder));
            ((Stage)btnBack.getScene().getWindow()).close();
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public  void generateOrderId() {
        try {
            Connection connection = DbConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM userentity");
            Integer count = 0;
            while (resultSet.next()){
                count = resultSet.getInt(1);
                System.out.println(count+"  count ");
            }
            if (count == 0) {
                lblEmpId.setText("E001");
            }
            String lastOrderId="";
            ResultSet resultSet1 = connection.createStatement().executeQuery("SELECT empId\n" +
                    "FROM userentity\n" +
                    "ORDER BY empId DESC\n" +
                    "LIMIT 1;");
            if (resultSet1.next()){
                lastOrderId = resultSet1.getString(1);
                Pattern pattern = Pattern.compile("[A-Za-z](\\d+)");
                Matcher matcher = pattern.matcher(lastOrderId);
                if (matcher.find()) {
                    int number = Integer.parseInt(matcher.group(1));
                    number++;
                    lblEmpId.setText(String.format("E%03d", number));
                } else {
                    new Alert(Alert.AlertType.WARNING,"hello").show();
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

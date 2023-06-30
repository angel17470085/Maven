package PojoEcommerce;

import com.fasterxml.jackson.databind.util.ClassUtil;

public class Login {

    public Login(String userEmail, String userPassword) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }
    public Login() {
        this.userEmail = "";
        this.userPassword = "";
    }
    private String userEmail;
    private String userPassword;
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }



}

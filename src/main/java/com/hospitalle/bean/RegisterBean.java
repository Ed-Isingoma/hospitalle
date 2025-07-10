package com.hospitalle.bean;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.Serial;
import java.io.Serializable;

import com.hospitalle.services.AuthService;

@Named("registerBean")
@RequestScoped
public class RegisterBean implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String username;
    private String email;
    private String password;
    private String role;

    private final AuthService authService = new AuthService();

    public String register() {
        authService.register(username, email, password, role);
        return "login.xhtml?faces-redirect=true";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

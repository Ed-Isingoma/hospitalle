package com.hospitalle.models;

import javax.persistence.*;

@Entity
@Table(name="Authentications")
public class Auth {
    @Id @GeneratedValue
    private Long id;
    private String username, email, password, role;

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return username;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

}

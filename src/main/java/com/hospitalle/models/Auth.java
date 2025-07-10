package com.hospitalle.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="Authentications")
public class Auth {
    @Id @GeneratedValue
    private Long id;
    private String username, email, password, role;
    private boolean deleted;

    @OneToMany(mappedBy = "doctor")
    private Set<Speciality> specialities;

//    @OneToMany(mappedBy = "doctor")
//    private List<Availability> availabilities;

    public List<Speciality> getSpecialities() {
        return new ArrayList<>(specialities);
    }

//    public List<Availability> getAvailabilities() {
//        return availabilities;
//    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
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

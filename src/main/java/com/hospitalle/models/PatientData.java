package com.hospitalle.models;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="PatientData")
public class PatientData {
    @Id @GeneratedValue
    private Long id;
    private String allergies, other_diseases;
    private LocalDate date_of_birth;
    private byte[] photo;

    @OneToOne @JoinColumn(name="auth")
    private Auth auth;

    public Long getId() {
        return id;
    }

    public String getAllergies() {
        return allergies;
    }

    public String getOtherDiseases() {
        return other_diseases;
    }

    public LocalDate getDateOfBirth() {
        return date_of_birth;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public Auth getAuth() {
        return auth;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public void setOtherDiseases(String other_diseases) {
        this.other_diseases = other_diseases;
    }

    public void setDateOfBirth(LocalDate date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public void setAuth(Auth auth) {
        this.auth = auth;
    }
}

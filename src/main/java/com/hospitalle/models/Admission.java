package com.hospitalle.models;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="Admissions")
public class Admission {
    @Id @GeneratedValue
    private Long id;
    private LocalDate start_date, discharge_date;
    private String ward;

    @ManyToOne
    @JoinColumn(name="patient")
    private Auth patient;

    public Long getId() {
        return id;
    }

    public LocalDate getStartTime() {
        return start_date;
    }

    public LocalDate getDischargeDate() {
        return discharge_date;
    }

    public String getWard() {
        return ward;
    }

    public Auth getPatient() {
        return patient;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStartTime(LocalDate start_date) {
        this.start_date = start_date;
    }

    public void setDischargeDate(LocalDate discharge_date) {
        this.discharge_date = discharge_date;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public void setPatient(Auth patient) {
        this.patient = patient;
    }
}
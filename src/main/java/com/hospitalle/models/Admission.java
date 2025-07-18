package com.hospitalle.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="Admissions")
public class Admission {
    @Id @GeneratedValue
    private Long id;
    private LocalDateTime start_time, discharge_date;
    private String ward;

    @ManyToOne
    @JoinColumn(name="patient")
    private Auth patient;

    @OneToOne(mappedBy="admission")
    private Payment payment;

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getStart_time() {
        return start_time;
    }

    public LocalDateTime getDischarge_date() {
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

    public void setStart_time(LocalDateTime start_time) {
        this.start_time = start_time;
    }

    public void setDischarge_date(LocalDateTime discharge_date) {
        this.discharge_date = discharge_date;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public void setPatient(Auth patient) {
        this.patient = patient;
    }
}
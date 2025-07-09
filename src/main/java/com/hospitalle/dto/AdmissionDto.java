package com.hospitalle.dto;

import com.hospitalle.models.Auth;

import java.time.LocalDateTime;

public class AdmissionDto {
    private Long id;
    private LocalDateTime start_time, discharge_date;
    private String ward;
    private Auth patient;
    private Integer bill;
    private boolean paid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStart_time() {
        return start_time;
    }

    public void setStart_time(LocalDateTime start_date) {
        this.start_time = start_date;
    }

    public LocalDateTime getDischarge_date() {
        return discharge_date;
    }

    public void setDischarge_date(LocalDateTime discharge_date) {
        this.discharge_date = discharge_date;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public Auth getPatient() {
        return patient;
    }

    public void setPatient(Auth patient) {
        this.patient = patient;
    }

    public Integer getBill() {
        return bill;
    }

    public void setBill(Integer bill) {
        this.bill = bill;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
}

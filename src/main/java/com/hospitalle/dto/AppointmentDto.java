package com.hospitalle.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import com.hospitalle.models.Speciality;
import com.hospitalle.models.Auth; // assuming Auth represents patient

public class AppointmentDto implements Serializable {
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String diagnosis, prescription, patientFeedback, patient, doctor;
    private String speciality;
    private boolean accepted, paid;
    private int bill;

    public int getBill() {
        return bill;
    }

    public void setBill(int bill) {
        this.bill = bill;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public boolean getAccepted() { return accepted; }
    public void setAccepted(boolean accepted) { this.accepted = accepted; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }

    public String getPrescription() { return prescription; }
    public void setPrescription(String prescription) { this.prescription = prescription; }

    public String getPatientFeedback() { return patientFeedback; }
    public void setPatientFeedback(String patientFeedback) { this.patientFeedback = patientFeedback; }

    public String getPatient() { return patient; }
    public void setPatient(String patient) { this.patient = patient; }

    public String getDoctor() { return doctor; }
    public void setDoctor(String doctor) { this.doctor = doctor; }

    public String getSpeciality() { return speciality; }
    public void setSpeciality(String speciality) { this.speciality = speciality; }
}


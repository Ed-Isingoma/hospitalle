package com.hospitalle.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import com.hospitalle.models.Speciality;
import com.hospitalle.models.Auth; // assuming Auth represents patient

public class AppointmentDto implements Serializable {
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String symptoms;
    private String diagnosis;
    private String prescription;
    private String patientFeedback;
    private Auth patient, doctor;
    private Speciality speciality;
    private boolean accepted;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public boolean getAccepted() { return accepted; }
    public void setAccepted(boolean accepted) { this.accepted = accepted; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    public String getSymptoms() { return symptoms; }
    public void setSymptoms(String symptoms) { this.symptoms = symptoms; }

    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }

    public String getPrescription() { return prescription; }
    public void setPrescription(String prescription) { this.prescription = prescription; }

    public String getPatientFeedback() { return patientFeedback; }
    public void setPatientFeedback(String patientFeedback) { this.patientFeedback = patientFeedback; }

    public Auth getPatient() { return patient; }
    public void setPatient(Auth patient) { this.patient = patient; }

    public Auth getDoctor() { return doctor; }
    public void setDoctor(Auth doctor) { this.doctor = doctor; }

    public Speciality getSpeciality() { return speciality; }
    public void setSpeciality(Speciality speciality) { this.speciality = speciality; }
}


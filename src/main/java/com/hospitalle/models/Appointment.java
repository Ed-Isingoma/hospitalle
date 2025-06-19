package com.hospitalle.models;

import javax.persistence.*;

@Entity
@Table(name="Appointment")
public class Appointment {
    @Id @GeneratedValue
    private Long id;
    private String symptoms, patient_feedback, diagnosis, prescription;

    @ManyToOne @JoinColumn(name="patient")
    private Auth patient;

    @ManyToOne @JoinColumn(name="speciality")
    private Speciality speciality;

    public Long getId() {
        return id;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public String getPatientFeedback() {
        return patient_feedback;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getPrescription() {
        return prescription;
    }

    public Auth getPatient() {
        return patient;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public void setPatientFeedback(String patient_feedback) {
        this.patient_feedback = patient_feedback;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public void setPatient(Auth patient) {
        this.patient = patient;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

}

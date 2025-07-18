package com.hospitalle.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="appointment")
public class Appointment {
    @Id @GeneratedValue
    private Long id;
    private String patient_feedback, diagnosis, prescription;
    private boolean accepted;

    @ManyToOne @JoinColumn(name="patient")
    private Auth patient;

    @ManyToOne @JoinColumn(name="speciality")
    private Speciality speciality;

    @OneToOne(mappedBy = "appointment")
    private Availability availability;

    @OneToOne(mappedBy="appointment")
    private Payment payment;

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Availability getAvailability() {
        return availability;
    }

    public void setPatient_feedback(String patient_feedback) {
        this.patient_feedback = patient_feedback;
    }

    public Long getId() {
        return id;
    }

    public boolean getAccepted() { return accepted; }

    public void setAccepted(boolean accepted) { this.accepted = accepted;}

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

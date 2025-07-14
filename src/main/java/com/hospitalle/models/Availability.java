package com.hospitalle.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="availabilities")
public class Availability {
    @Id @GeneratedValue
    private Long id;
    private LocalDateTime start_time, end_time;

    @OneToOne @JoinColumn(name="appointment")
    private Appointment appointment;

    @ManyToOne @JoinColumn(name="doctor")
    private Auth doctor;

    public Long getId() {
        return id;
    }

    public LocalDateTime getStartTime() {
        return start_time;
    }

    public LocalDateTime getEndTime() {
        return end_time;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public Auth getDoctor() {
        return doctor;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStartTime(LocalDateTime start_time) {
        this.start_time = start_time;
    }

    public void setEndTime(LocalDateTime end_time) {
        this.end_time = end_time;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public void setDoctor(Auth doctor) {
        this.doctor = doctor;
    }
}

package com.hospitalle.models;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="payments")
public class Payment {
    @Id @GeneratedValue
    private Long id;
    private LocalDate payment_date;

    @OneToOne @JoinColumn(name="appointment")
    private Appointment appointment;

    @OneToOne @JoinColumn(name="admission")
    private Admission admission;

    public Long getId() {
        return id;
    }

    public LocalDate getPaymentDate() {
        return payment_date;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public Admission getAdmission() {
        return admission;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPaymentDate(LocalDate payment_date) {
        this.payment_date = payment_date;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public void setAdmission(Admission admission) {
        this.admission = admission;
    }
}

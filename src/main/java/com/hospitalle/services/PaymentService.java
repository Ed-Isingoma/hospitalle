package com.hospitalle.services;

import com.hospitalle.models.Admission;
import com.hospitalle.dto.AppointmentDto;
import com.hospitalle.models.Payment;

import java.io.Serializable;
import java.util.List;

public interface PaymentService extends Serializable {
    List<Payment> getPayments();

    void editPayment(Payment edit);

    public Long newPayment(Admission newAdm);
    public Long newPayment(AppointmentDto newAppt);

}

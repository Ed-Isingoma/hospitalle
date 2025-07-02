package com.hospitalle.services;

import com.hospitalle.dao.PaymentDao;
import com.hospitalle.models.Admission;
import com.hospitalle.models.Appointment;
import com.hospitalle.models.Payment;

import java.time.LocalDate;
import java.util.List;

public class PaymentService {
    private final PaymentDao paymentDao = new PaymentDao();

    public List<Payment> getPayments() {
        return paymentDao.findAll();
    }

    public Long newPayment(Admission newAdm, Appointment newAppt, int newAmt, LocalDate newDate) {
        Payment pay = new Payment();
        pay.setAdmission(newAdm);
        pay.setAppointment(newAppt);
        pay.setAmount(newAmt);
        pay.setPaymentDate(newDate);
        return paymentDao.save(pay);
    }

    public void editPayment(Payment edit) {
        paymentDao.update(edit);
    }
}

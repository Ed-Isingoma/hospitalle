package com.hospitalle.services.impl;

import com.hospitalle.dao.AdmissionDao;
import com.hospitalle.dao.AppointmentDao;
import com.hospitalle.dao.PaymentDao;
import com.hospitalle.dto.AppointmentDto;
import com.hospitalle.models.Admission;
import com.hospitalle.models.Appointment;
import com.hospitalle.models.Payment;
import com.hospitalle.services.PaymentService;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;

public class PaymentServiceImpl implements PaymentService {
    @Inject
    private PaymentDao paymentDao;
    @Inject
    private AdmissionDao admissionDao;
    @Inject
    private AppointmentDao apptDao;

    public List<Payment> getPayments() {
        return paymentDao.findAll();
    }

    public Long newPayment(Admission newAdm) {
        Payment pay = new Payment();
        Admission adm = admissionDao.findById(newAdm.getId());

        pay.setAdmission(adm);
        pay.setPaymentDate(LocalDate.now());
        return paymentDao.save(pay);
    }

    public Long newPayment(AppointmentDto newAppt) {
        Payment pay = new Payment();
        Appointment appt = apptDao.findById(newAppt.getId());

        pay.setAppointment(appt);
        pay.setPaymentDate(LocalDate.now());
        return paymentDao.save(pay);
    }

    public void editPayment(Payment edit) {
        paymentDao.update(edit);
    }
}

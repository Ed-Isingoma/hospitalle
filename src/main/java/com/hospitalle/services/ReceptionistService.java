package com.hospitalle.services;

import com.hospitalle.dao.AppointmentDao;
import com.hospitalle.dao.AdmissionDao;
import com.hospitalle.dao.AuthDao;
import com.hospitalle.dao.AvailabilityDao;
import com.hospitalle.models.Appointment;
import com.hospitalle.models.Admission;
import com.hospitalle.models.Auth;
import com.hospitalle.models.Availability;

import java.util.List;

public class ReceptionistService {
    private final AppointmentDao appointmentDao = new AppointmentDao();
    private final AdmissionDao admissionDao = new AdmissionDao();
    private final AuthDao authDao = new AuthDao();
    private final AvailabilityDao availabilityDao = new AvailabilityDao();


    public List<Appointment> getPastAppointments() {
        return appointmentDao.findPastAppointments();
    }

    public List<Appointment> getFutureAppointments() {
        return appointmentDao.findFutureAppointments();
    }
    public List<Admission> getAllAdmissions() {
        return admissionDao.findAll();
    }

    public List<Auth> getAllDoctors() {
        return authDao.findByRole("doctor");
    }

    public List<Availability> getAllAvailabilities() {
        return availabilityDao.findAll();
    }
}

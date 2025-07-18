package com.hospitalle.services.impl;

import com.hospitalle.dao.AppointmentDao;
import com.hospitalle.dao.AdmissionDao;
import com.hospitalle.dao.AuthDao;
import com.hospitalle.dao.AvailabilityDao;
import com.hospitalle.dto.AppointmentDto;
import com.hospitalle.models.Appointment;
import com.hospitalle.models.Admission;
import com.hospitalle.models.Auth;
import com.hospitalle.models.Availability;
import com.hospitalle.services.ReceptionistService;

import javax.inject.Inject;
import java.util.List;

public class ReceptionistServiceImpl implements ReceptionistService {
    @Inject
    private AppointmentDao appointmentDao;
    @Inject
    private AdmissionDao admissionDao;
    @Inject
    private AuthDao authDao;
    @Inject
    private AvailabilityDao availabilityDao;

    public List<AppointmentDto> getPastAppointments() {
        return appointmentDao.findPastAppointments();
    }

    public List<AppointmentDto> getFutureAppointments() {
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

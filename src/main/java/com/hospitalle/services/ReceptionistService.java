package com.hospitalle.services;

import com.hospitalle.dto.AppointmentDto;
import com.hospitalle.models.Admission;
import com.hospitalle.models.Auth;
import com.hospitalle.models.Availability;

import java.io.Serializable;
import java.util.List;

public interface ReceptionistService extends Serializable {
    List<AppointmentDto> getPastAppointments();

    List<AppointmentDto> getFutureAppointments();

    List<Admission> getAllAdmissions();

    List<Auth> getAllDoctors();

    List<Availability> getAllAvailabilities();
}

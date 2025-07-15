package com.hospitalle.services;

import com.hospitalle.dto.AppointmentDto;
import com.hospitalle.models.Auth;
import com.hospitalle.services.impl.AppointmentServiceImpl;

import java.io.Serializable;
import java.util.List;

public interface AppointmentService extends Serializable {
    void newAppt(Long av, Long sp, Auth patient);

    record AppointmentLists(
            List<AppointmentDto> pendingFuture,
            List<AppointmentDto> acceptedFuture,
            List<AppointmentDto> acceptedPast
    ) {}

    AppointmentLists findAppointments(Auth user, boolean isDoctor);

    void addPatientFeedback(AppointmentDto apptDto);

    void addDoctorChanges(Long id, String diagnosis, String prescription);

    void acceptAppointment(Long id);
}

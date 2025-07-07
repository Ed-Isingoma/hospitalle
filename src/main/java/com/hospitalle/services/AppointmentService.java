package com.hospitalle.services;

import com.hospitalle.dao.AppointmentDao;
import com.hospitalle.dao.AvailabilityDao;
import com.hospitalle.dao.SpecialityDao;
import com.hospitalle.dto.AppointmentDto;
import com.hospitalle.models.Availability;
import com.hospitalle.models.Appointment;
import com.hospitalle.models.Auth;
import com.hospitalle.models.Speciality;

import javax.transaction.Transactional;
import java.util.*;
import java.time.LocalDateTime;

public class AppointmentService {
    private final AvailabilityDao availDao = new AvailabilityDao();
    private final AppointmentDao apptDao = new AppointmentDao();
    private final SpecialityDao specialityDao   = new SpecialityDao();

    public static record AppointmentLists(
            List<AppointmentDto> pendingFuture,
            List<AppointmentDto> acceptedFuture,
            List<AppointmentDto> acceptedPast
    ) {}

    @Transactional
    public void newAppt(Availability avail, Speciality spec, Auth patient) {
        Appointment appt = new Appointment();
        appt.setPatient(patient);
        appt.setSpeciality(spec);
        appt.setAccepted(false);
        apptDao.save(appt);

        avail.setAppointment(appt);
        availDao.update(avail);
    }

    public AppointmentLists findAppointments(Auth doctor) {
        List<Availability> slots = availDao.findByDoctor(doctor);
        Map<Long, AppointmentDto> map = new LinkedHashMap<>();
        for (Availability av : slots) {
            Appointment app = av.getAppointment();
            if (app == null) continue;
            Long id = app.getId();

            AppointmentDto dto = map.computeIfAbsent(id, k -> {
                AppointmentDto d = new AppointmentDto();
                d.setId(id);
                d.setSymptoms(app.getSymptoms());
                d.setDiagnosis(app.getDiagnosis());
                d.setPrescription(app.getPrescription());
                d.setPatientFeedback(app.getPatientFeedback());
                d.setPatient(app.getPatient());
                d.setAccepted(app.getAccepted());
                d.setSpeciality(app.getSpeciality());
                d.setStartTime(av.getStartTime());
                d.setEndTime(av.getEndTime());
                return d;
            });

            if (av.getStartTime().isBefore(dto.getStartTime())) {
                dto.setStartTime(av.getStartTime());
            }
            if (av.getEndTime().isAfter(dto.getEndTime())) {
                dto.setEndTime(av.getEndTime());
            }
        }

        LocalDateTime now = LocalDateTime.now();
        List<AppointmentDto> pendingFuture  = new ArrayList<>();
        List<AppointmentDto> acceptedFuture = new ArrayList<>();
        List<AppointmentDto> acceptedPast   = new ArrayList<>();

        for (AppointmentDto dto : map.values()) {
            boolean accepted = dto.getAccepted();
            LocalDateTime start = dto.getStartTime();

            if (!accepted && ( !start.isBefore(now) )) {
                pendingFuture.add(dto);

            } else if (accepted && ( !start.isBefore(now) )) {
                acceptedFuture.add(dto);

            } else if (accepted) {
                acceptedPast.add(dto);
            }
        }

        return new AppointmentLists(pendingFuture, acceptedFuture, acceptedPast);
    }

    public void doUpdateAppointment (AppointmentDto apptDto) {
        Appointment appt = new Appointment();
        appt.setId(apptDto.getId());
        appt.setSymptoms(apptDto.getSymptoms());
        appt.setDiagnosis(apptDto.getDiagnosis());
        appt.setPrescription(apptDto.getPrescription());
        appt.setPatientFeedback(apptDto.getPatientFeedback());
        appt.setPatient(apptDto.getPatient());
        appt.setAccepted(apptDto.getAccepted());
        appt.setSpeciality(apptDto.getSpeciality());

        apptDao.update(appt);
    }
}

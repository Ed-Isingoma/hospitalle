package com.hospitalle.services.impl;

import com.hospitalle.dao.AppointmentDao;
import com.hospitalle.dao.AvailabilityDao;
import com.hospitalle.dao.SpecialityDao;
import com.hospitalle.dto.AppointmentDto;
import com.hospitalle.models.*;
import com.hospitalle.services.AppointmentService;

import javax.inject.Inject;
import java.util.*;
import java.time.LocalDateTime;

public class AppointmentServiceImpl implements AppointmentService {
    @Inject
    private AvailabilityDao availDao;
    @Inject
    private AppointmentDao apptDao;
    @Inject
    private SpecialityDao specDao;

    public void newAppt(Long av, Long sp, Auth patient) {
        Availability avail = availDao.findById(av);
        Speciality spec = specDao.findById(sp);

        Appointment appt = new Appointment();
        appt.setPatient(patient);
        appt.setSpeciality(spec);
        appt.setAccepted(false);
        apptDao.save(appt);

        avail.setAppointment(appt);
        availDao.update(avail);
    }

    public AppointmentLists findAppointments(Auth user, boolean isDoctor) {
        try {
            List<Availability> slots;
            List<AppointmentDto> apptDtos = new ArrayList<>();

            if (isDoctor) {
                slots = availDao.findByDoctor(user);
                for (Availability av : slots) {
                    Appointment app = av.getAppointment();
                    if (app == null) continue;
                    AppointmentDto dto = assignDto(app, av);
                    apptDtos.add(dto);
                }
            } else {
                List<Appointment> appts = apptDao.findByParameters("Appointment", Map.of("patient", user));
                appts.forEach(appt -> {
                    apptDtos.add(assignDto(appt, appt.getAvailability()));
                });
            }

            LocalDateTime now = LocalDateTime.now();
            List<AppointmentDto> pendingFuture  = new ArrayList<>();
            List<AppointmentDto> acceptedFuture = new ArrayList<>();
            List<AppointmentDto> acceptedPast   = new ArrayList<>();

            for (AppointmentDto dto : apptDtos) {
                boolean accepted = dto.getAccepted();
                LocalDateTime start = dto.getStartTime();
                if (!accepted && (!start.isBefore(now))) {
                    pendingFuture.add(dto);
                } else if (accepted && (!start.isBefore(now))) {
                    acceptedFuture.add(dto);
                } else if (accepted) {
                    acceptedPast.add(dto);
                }
            }

            return new AppointmentLists(pendingFuture, acceptedFuture, acceptedPast);
        } catch (Exception e) {
            System.out.println("Some error here: " + e.getMessage());
//            e.printStackTrace();
            return new AppointmentLists(
                    new ArrayList<>(),
                    new ArrayList<>(),
                    new ArrayList<>()
            );
        }
    }

    public void addPatientFeedback(AppointmentDto apptDto) {
        apptDao.addFeedback(apptDto.getId(), apptDto.getPatientFeedback());
    }

    public void addDoctorChanges(Long id, String diagnosis, String prescription) {
        Appointment appt = apptDao.findById(id);
        appt.setDiagnosis(diagnosis);
        appt.setPrescription(prescription);

        apptDao.update(appt);
    }

    public void acceptAppointment(Long id) {
        Appointment appt = apptDao.findById(id);
        appt.setAccepted(true);
        apptDao.update(appt);
    }

    public static AppointmentDto assignDto(Appointment appt, Availability av) {
        AppointmentDto d = new AppointmentDto();
        d.setId(appt.getId());
        d.setDiagnosis(appt.getDiagnosis());
        d.setPrescription(appt.getPrescription());
        d.setPatientFeedback(appt.getPatientFeedback());
        d.setPatient(appt.getPatient().getUsername());
        d.setBill(appt.getSpeciality().getHourlyPrice());
        d.setAccepted(appt.getAccepted());
        d.setDoctor(appt.getSpeciality().getDoctor().getUsername());
        d.setSpeciality(appt.getSpeciality().getName());
        d.setStartTime(av.getStartTime());
        d.setPaid(appt.getPayment() != null);
        d.setEndTime(av.getEndTime());
        return d;
    }
}

package com.hospitalle.services;

import com.hospitalle.dao.AppointmentDao;
import com.hospitalle.dao.AvailabilityDao;
import com.hospitalle.dao.SpecialityDao;
import com.hospitalle.dto.AppointmentDto;
import com.hospitalle.models.*;

import java.util.*;
import java.time.LocalDateTime;

public class AppointmentService {
    private final AvailabilityDao availDao = new AvailabilityDao();
    private final AppointmentDao apptDao = new AppointmentDao();
    private final SpecialityDao specDao = new SpecialityDao();

    public static record AppointmentLists(
            List<AppointmentDto> pendingFuture,
            List<AppointmentDto> acceptedFuture,
            List<AppointmentDto> acceptedPast
    ) {}

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

//            List<Long> dtoFutureIds = new ArrayList<>();

            for (AppointmentDto dto : apptDtos) {
                boolean accepted = dto.getAccepted();
                LocalDateTime start = dto.getStartTime();
                if (!accepted && (!start.isBefore(now))) {
                    pendingFuture.add(dto);
                } else if (accepted && (!start.isBefore(now))) {
                    acceptedFuture.add(dto);
//                    dtoFutureIds.add(dto.getId());
                } else if (accepted) {
                    acceptedPast.add(dto);
                }
            }

//            List<Payment> payments = apptDao.findPaymentsForAppts(dtoFutureIds);

//            for (AppointmentDto dtoFuture : acceptedFuture) {
//                boolean hasPayment = payments.stream()
//                        .anyMatch(p -> p.getAppointment().getId().equals(dtoFuture.getId()));
//
//                dtoFuture.setPaid(hasPayment);
//            }

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

    public void addDoctorChanges(AppointmentDto apptDto) {
        Appointment appt = new Appointment();
        appt.setId(apptDto.getId());
        appt.setDiagnosis(apptDto.getDiagnosis());
        appt.setPrescription(apptDto.getPrescription());
        appt.setAccepted(apptDto.getAccepted());

        apptDao.update(appt);
    }

    protected AppointmentDto assignDto(Appointment appt, Availability av) {
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

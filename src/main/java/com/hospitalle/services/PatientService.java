package com.hospitalle.services;

import com.hospitalle.dao.AppointmentDao;
import com.hospitalle.dao.AuthDao;
import com.hospitalle.dao.SpecialityDao;
import com.hospitalle.dto.AppointmentDto;
import com.hospitalle.models.Auth;
import com.hospitalle.models.Appointment;

import java.util.ArrayList;
import java.util.List;

public class PatientService {
    private final AppointmentDao appointmentDao = new AppointmentDao();
    private final AuthDao authDao = new AuthDao();
    private SpecialityDao specialityDao = new SpecialityDao();

    public List<AppointmentDto> getAppointmentsFor(Auth patient) {
        List<Appointment> raw = appointmentDao.findByPatient(patient);
//        raw.add(null);
        List<AppointmentDto> appts = new ArrayList<>();

        for (Appointment ap : raw) {
            AppointmentDto dto = new AppointmentDto();
            dto.setId(ap.getId());
            dto.setDoctor(ap.getSpeciality().getDoctor());
            dto.setPatient(patient);
            dto.setSymptoms(ap.getSymptoms());
            dto.setDiagnosis(ap.getDiagnosis());
            dto.setPrescription(ap.getPrescription());
            dto.setPatientFeedback(ap.getPatientFeedback());
            dto.setSpeciality(ap.getSpeciality());

            // dto.setStartTime(ap.getSlot().getStartTime());
            // dto.setEndTime(ap.getSlot().getEndTime());

            appts.add(dto);
        }
        return appts;
    }

    public List<Auth> getDoctors() {
        return authDao.findByRoleWithSpecialities("doctor");
    }

    // public List<List<Speciality>> getSpecialitiesFor(List<Auth> doctors) {
    //     List<List<Speciality>> specialities = new ArrayList<>();
    //     for (Auth doc : doctors) {
    //         specialities.add(specialityDao.findByDoctor(doc));
    //     }
    //     return specialities;
    // }
}

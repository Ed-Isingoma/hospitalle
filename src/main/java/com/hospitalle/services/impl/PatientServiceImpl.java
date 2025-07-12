package com.hospitalle.services.impl;

import com.hospitalle.dao.AppointmentDao;
import com.hospitalle.dao.AuthDao;
import com.hospitalle.dao.SpecialityDao;
import com.hospitalle.models.Auth;
import com.hospitalle.services.PatientService;

import javax.inject.Inject;
import java.util.List;

public class PatientServiceImpl implements PatientService {
    @Inject
    private AuthDao authDao;
    @Inject
    private SpecialityDao specialityDao;

//    public List<AppointmentDto> getAppointmentsFor(Auth patient) {
//        List<Appointment> raw = appointmentDao.findByPatient(patient);
//        raw.add(null);
//        List<AppointmentDto> appts = new ArrayList<>();
//
//        for (Appointment ap : raw) {
//            AppointmentDto dto = new AppointmentDto();
//            dto.setId(ap.getId());
//            dto.setDoctor(ap.getSpeciality().getDoctor());
//            dto.setPatient(patient);
//            dto.setSymptoms(ap.getSymptoms());
//            dto.setDiagnosis(ap.getDiagnosis());
//            dto.setPrescription(ap.getPrescription());
//            dto.setPatientFeedback(ap.getPatientFeedback());
////            dto.setSpeciality(ap.getSpeciality());
//            dto.setStartTime(ap.getAvailability().getStartTime());
//            dto.setEndTime(ap.getAvailability().getEndTime());
//
//            appts.add(dto);
//        }
//        return appts;
//    }

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

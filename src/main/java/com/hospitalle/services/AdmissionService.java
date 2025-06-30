package com.hospitalle.services;

import com.hospitalle.dao.AdmissionDao;
import com.hospitalle.dao.AuthDao;
import com.hospitalle.models.Admission;
import com.hospitalle.models.Auth;

import java.time.LocalDate;
import java.util.List;

public class AdmissionService {
    private final AdmissionDao dao = new AdmissionDao();
    private final AuthDao authDao = new AuthDao();

    public List<Admission> findByPatient(Auth patient) {
        return dao.findByPatient(patient);
    }

    public Long newAdmission(LocalDate start, String ward, Long patientId) {
        Auth patient = authDao.findById(patientId);
        Admission adm = new Admission();
        adm.setPatient(patient);
        adm.setWard(ward);
        adm.setStartTime(start);
        return dao.save(adm);
    }

    public void editAdmission(Admission adm) {
        dao.update(adm);
    }
}

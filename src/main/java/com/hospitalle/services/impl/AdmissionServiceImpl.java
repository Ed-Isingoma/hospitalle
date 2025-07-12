package com.hospitalle.services.impl;

import com.hospitalle.dao.AdmissionDao;
import com.hospitalle.dao.AuthDao;
import com.hospitalle.models.Admission;
import com.hospitalle.models.Auth;
import com.hospitalle.services.AdmissionService;

import javax.inject.Inject;
import java.util.List;

public class AdmissionServiceImpl implements AdmissionService {
    @Inject
    private AdmissionDao dao;
    @Inject
    private AuthDao authDao;

    public List<Admission> findByPatient(Auth patient) {
        return dao.findByPatient(patient);
    }

    public void newAdmission(Admission adm) {
        dao.save(adm);
    }

    public void editAdmission(Admission adm) {
        dao.update(adm);
    }
}

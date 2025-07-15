package com.hospitalle.services;

import com.hospitalle.models.Admission;
import com.hospitalle.models.Auth;

import java.io.Serializable;
import java.util.List;

public interface AdmissionService extends Serializable {

    List<Admission> findByPatient(Auth patient);

    void newAdmission(Admission ad);

    void editAdmission(Admission adm);

}

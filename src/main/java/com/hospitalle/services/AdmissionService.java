package com.hospitalle.services;

import com.hospitalle.dao.AdmissionDao;
import com.hospitalle.dao.AuthDao;
import com.hospitalle.dto.AdmissionDto;
import com.hospitalle.models.Admission;
import com.hospitalle.models.Auth;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AdmissionService {
    private final AdmissionDao dao = new AdmissionDao();
    private final AuthDao authDao = new AuthDao();

    public List<AdmissionDto> findByPatient(Auth patient) {
        List<Admission> adms = dao.findByPatient(patient);
        return assignDto(adms);
    }

    public Long newAdmission(LocalDateTime start, String ward, Long patientId) {
        Auth patient = authDao.findById(patientId);
        Admission adm = new Admission();
        adm.setPatient(patient);
        adm.setWard(ward);
        adm.setStart_time(start);
        return dao.save(adm);
    }

    protected List<AdmissionDto> assignDto(List<Admission> adms) {
        List<AdmissionDto> dtos = new ArrayList<>();


        for (Admission adm : adms) {
            Integer bill;
            if (adm.getPayment() != null ) {
                bill = adm.getPayment().getAmount();
            } else {
                bill = null;
            }

            AdmissionDto dto = new AdmissionDto();
            dto.setId(adm.getId());
            dto.setBill(bill);
            dto.setPaid(adm.getPayment() != null);
            dto.setDischarge_date(adm.getDischarge_date());
            dto.setWard(adm.getWard());
            dto.setStart_time(adm.getStart_time());

            dtos.add(dto);
        }
        return dtos;
    }
    public void editAdmission(Admission adm) {
        dao.update(adm);
    }
}

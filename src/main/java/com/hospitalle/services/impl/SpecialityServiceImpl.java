package com.hospitalle.services.impl;

import com.hospitalle.dao.SpecialityDao;
import com.hospitalle.models.Auth;
import com.hospitalle.models.Speciality;
import com.hospitalle.services.SpecialityService;

import javax.inject.Inject;
import java.util.List;

public class SpecialityServiceImpl implements SpecialityService {
    @Inject
    private SpecialityDao dao;

    public List<Speciality> findByDoctor(Auth doctor) {
        return dao.findByDoctor(doctor);
    }

    public void addSpec(String name, int price, Auth doc) {
        Speciality speciality = new Speciality();
        speciality.setName(name);
        speciality.setHourlyPrice(price);
        speciality.setDoctor(doc);
        dao.save(speciality);
    }

    public void removeSpec(Speciality spec) {
        dao.delete(spec);
    }

//    public void saveAll(Auth doctor, List<Speciality> list) {
//        dao.saveAll(doctor, list);
//    }
}

package com.hospitalle.services;

import com.hospitalle.dao.SpecialityDao;
import com.hospitalle.models.Auth;
import com.hospitalle.models.Speciality;

import java.util.List;

public class SpecialityService {
    private final SpecialityDao dao = new SpecialityDao();

    public List<Speciality> findByDoctor(Auth doctor) {
        return dao.findByDoctor(doctor);
    }

    public void addSpec(String name, int price) {
        Speciality speciality = new Speciality();
        speciality.setName(name);
        speciality.setHourlyPrice(price);
        dao.save(speciality);
    }

    public void removeSpec(Long id) {
        Speciality spec = dao.findById(id);
        dao.delete(spec);
    }

    public void saveAll(Auth doctor, List<Speciality> list) {
        dao.saveAll(doctor, list);
    }
}

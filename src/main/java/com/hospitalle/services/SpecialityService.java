package com.hospitalle.services;

import com.hospitalle.models.Auth;
import com.hospitalle.models.Speciality;

import java.io.Serializable;
import java.util.List;

public interface SpecialityService extends Serializable {
    void addSpec(String name, int price, Auth doc);

    void removeSpec(Speciality spec);

    List<Speciality> findByDoctor(Auth doctor);
}

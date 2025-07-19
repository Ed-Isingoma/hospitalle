package com.hospitalle.services;

import com.hospitalle.models.Auth;
import com.hospitalle.models.Availability;

import java.io.Serializable;
import java.util.List;

public interface AuthService extends Serializable {
    Auth authenticate(String username, String password);

    void editOrDelete(Auth user);

    void register(String username, String email, String password, String role);

    String dashboardFor(Auth user);

    List<Availability> getBookableSlots(Auth doctor);

    List<Auth> completePatients(String query);
}

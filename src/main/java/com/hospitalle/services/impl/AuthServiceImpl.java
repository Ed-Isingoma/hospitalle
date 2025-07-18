package com.hospitalle.services.impl;

import com.hospitalle.dao.AuthDao;
import com.hospitalle.models.Auth;
import com.hospitalle.models.Availability;
import com.hospitalle.services.AuthService;

import java.util.List;

public class AuthServiceImpl implements AuthService {
    private final AuthDao dao = new AuthDao();

    public Auth findById(Long id) {
        return dao.findById(id);
    }

    public List<Auth> completePatients(String query) {
        return dao.findByUsernameContainingIgnoreCase(query);
    }

    public Auth authenticate(String username, String password) {
        return dao.findByUsernameAndPassword(username, password);
    }

    public void editOrDelete(Auth user) {
        dao.update(user);
    }

    public void register(String username, String email, String password, String role) {
        Auth a = new Auth();
        a.setUsername(username);
        a.setEmail(email);
        a.setPassword(password);
        a.setRole(role);
        dao.save(a);
    }

    public List<Availability> getBookableSlots(Auth doctor) {
        return dao.findBookableSlots(doctor);
    }

    public String dashboardFor(Auth user) {
        return switch (user.getRole()) {
            case "doctor" -> "doctor_dashboard.xhtml?faces-redirect=true";
            case "patient" -> "patient_dashboard.xhtml?faces-redirect=true";
            case "receptionist" -> "receptionist_dashboard.xhtml?faces-redirect=true";
            default -> null;
        };
    }
}

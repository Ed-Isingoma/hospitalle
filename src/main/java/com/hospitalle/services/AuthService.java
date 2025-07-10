package com.hospitalle.services;

import com.hospitalle.dao.AuthDao;
import com.hospitalle.models.Auth;
import com.hospitalle.models.Availability;

import javax.transaction.Transactional;
import java.util.List;

public class AuthService {
    private final AuthDao dao = new AuthDao();

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
        System.out.println("this is the auth object: " + a);
        Long saved = dao.save(a);
        System.out.println("this is the id saved: " + saved);
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

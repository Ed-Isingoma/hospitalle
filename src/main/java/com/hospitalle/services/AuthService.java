package com.hospitalle.services;

import com.hospitalle.dao.AuthDao;
import com.hospitalle.models.Auth;

import javax.transaction.Transactional;

public class AuthService {
    private final AuthDao dao = new AuthDao();

    public Auth authenticate(String username, String password) {
        Auth u = dao.findByUsernameAndPassword(username, password);
        if (u == null) {
            return null;
        }
        return u;
    }

    @Transactional
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

    public String dashboardFor(Auth user) {
        return switch (user.getRole()) {
            case "doctor" -> "doctor_dashboard.xhtml?faces-redirect=true";
            case "patient" -> "patient_dashboard.xhtml?faces-redirect=true";
            case "receptionist" -> "receptionist_dashboard.xhtml?faces-redirect=true";
            default -> null;
        };
    }
}

package com.hospitalle.bean;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serial;
import java.io.Serializable;
import com.hospitalle.models.Auth;
import com.hospitalle.services.impl.AuthServiceImpl;

@Named("loginBean")
@SessionScoped
public class LoginBean implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String username;
    private String password;

    @Inject
    private AuthServiceImpl authServiceImpl;

    public String login() {
        Auth user = authServiceImpl.authenticate(username, password);
        if (user == null) {
            FacesContext.getCurrentInstance()
                    .addMessage(null,
                            new FacesMessage(
                                    FacesMessage.SEVERITY_ERROR,
                                    "Login failed",
                                    "Invalid credentials"
                            )
                    );
            return null; // stay on login page
        }

        FacesContext.getCurrentInstance()
                .getExternalContext()
                .getSessionMap()
                .put("user", user);

        return authServiceImpl.dashboardFor(user);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

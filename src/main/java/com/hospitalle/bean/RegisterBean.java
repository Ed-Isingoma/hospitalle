package com.hospitalle.bean;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serial;
import java.io.Serializable;

import com.hospitalle.services.impl.AuthServiceImpl;

@Named("registerBean")
@RequestScoped
public class RegisterBean implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    protected String regUsername, regEmail, regPassword, regRole;
    protected String regConfirmPassword;

    @Inject
    private AuthServiceImpl authServiceImpl;

    public String register() {
        authServiceImpl.register(regUsername, regEmail, regPassword, regRole);
        return "login.xhtml?faces-redirect=true";
    }

    public void validateConfirmPassword(FacesContext ctx, UIComponent comp, Object value) {
        String confirm = (value == null? "" : value.toString());
        UIInput passInput = (UIInput) comp.findComponent("pass");
        String original = (passInput.getValue()==null? "" : passInput.getValue().toString());
        if (!original.equals(confirm)) {
            throw new ValidatorException(
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Passwords do not match",
                            "Please make sure both passwords are identical"));
        }
    }

    public String getRegUsername() {
        return regUsername;
    }

    public void setRegUsername(String regUsername) {
        this.regUsername = regUsername;
    }

    public String getRegConfirmPassword() {
        return regConfirmPassword;
    }

    public void setRegConfirmPassword(String regConfirmPassword) {
        this.regConfirmPassword = regConfirmPassword;
    }

    public String getRegEmail() {
        return regEmail;
    }

    public void setRegEmail(String regEmail) {
        this.regEmail = regEmail;
    }

    public String getRegPassword() {
        return regPassword;
    }

    public void setRegPassword(String regPassword) {
        this.regPassword = regPassword;
    }

    public String getRegRole() {
        return regRole;
    }

    public void setRegRole(String regRole) {
        this.regRole = regRole;
    }
}

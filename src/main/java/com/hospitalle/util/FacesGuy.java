package com.hospitalle.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class FacesGuy {
    public static void info(String message) {
        FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", message)
        );
    }

    public static void error(String message) {
        FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", message)
        );
    }
}

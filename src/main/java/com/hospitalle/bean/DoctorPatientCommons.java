package com.hospitalle.bean;

import com.hospitalle.dto.AppointmentDto;
import com.hospitalle.models.Auth;
import com.hospitalle.services.AppointmentService;
import com.hospitalle.services.AuthService;
import com.hospitalle.util.FacesGuy;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.util.List;

public class DoctorPatientCommons {

    protected Auth me;
    protected boolean deleteAccount;
    protected String  newEmail, newPassword, confirmNewPassword, currentPassword;

    protected final AppointmentService appointmentService   = new AppointmentService();
    protected AppointmentDto updatedAppt;
    protected final AuthService authService = new AuthService();

    protected List<AppointmentDto> pendingAppts;
    protected List<AppointmentDto> upcomingAcceptedAppts;
    protected List<AppointmentDto> pastAcceptedAppts;

    public void loadApptData() {
        AppointmentService.AppointmentLists apptLists = appointmentService.findAppointments(me);
        pendingAppts = apptLists.pendingFuture();
        upcomingAcceptedAppts = apptLists.acceptedFuture();
        pastAcceptedAppts = apptLists.acceptedPast();
    }

    public AppointmentDto getUpdatedAppt() {
        return updatedAppt;
    }

    public void setUpdatedAppt(AppointmentDto updatedAppt) {
        this.updatedAppt = updatedAppt;
    }

    public void deleteAccount() {
        me.setDeleted(deleteAccount);
        authService.editOrDelete(me);
        FacesGuy.info("Deleting Account");
        logout();
    }

    public String logout(){
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        context.getSessionMap().remove("user");
        context.invalidateSession();
//        try {
//            context.redirect(
//                    FacesContext.getCurrentInstance()
//                            .getExternalContext()
//                            .getRequestContextPath()
//                            + "/login.xhtml"
//            );
//        } catch (Exception e) {
//            FacesGuy.error(e.getMessage());
//            System.out.println(e.getMessage());
//        }
        return "login.xhtml?faces-redirect=true";
    }

    public void editProfile() {
        me.setEmail(newEmail);
        me.setPassword(newPassword);
        authService.editOrDelete(me);
    }

    public boolean isDeleteAccount() {
        return deleteAccount;
    }

    public void setDeleteAccount(boolean deleteAccount) {
        this.deleteAccount = deleteAccount;
    }


    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public List<AppointmentDto> getPendingAppts() {
        return pendingAppts;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }

    public void validatePasswordConfirmation() {
        if (me.getPassword().equals(currentPassword) && newPassword != null && newPassword.equals(confirmNewPassword)) {
            newEmail = me.getEmail();
            editProfile();
        } else {
            FacesGuy.error("Current password does not match");
        }
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public void setPastAcceptedAppts(List<AppointmentDto> pastAcceptedAppts) {
        this.pastAcceptedAppts = pastAcceptedAppts;
    }

    public void setUpcomingAcceptedAppts(List<AppointmentDto> upcomingAcceptedAppts) {
        this.upcomingAcceptedAppts = upcomingAcceptedAppts;
    }

    public void setPendingAppts(List<AppointmentDto> pendingAppts) {
        this.pendingAppts = pendingAppts;
    }

    public List<AppointmentDto> getUpcomingAcceptedAppts() {
        return upcomingAcceptedAppts;
    }

    public List<AppointmentDto> getPastAcceptedAppts() {
        return pastAcceptedAppts;
    }

    public Auth getMe() { return me; }

}

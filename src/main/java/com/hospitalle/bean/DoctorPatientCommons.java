package com.hospitalle.bean;

import com.hospitalle.dto.AppointmentDto;
import com.hospitalle.models.Auth;
import com.hospitalle.models.Availability;
import com.hospitalle.services.impl.AppointmentServiceImpl;
import com.hospitalle.services.impl.AuthServiceImpl;
import com.hospitalle.util.FacesGuy;
import com.hospitalle.util.StringFormatter;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class DoctorPatientCommons {

    protected Auth usr;
    protected boolean deleteAccount, editEmail, editPassword;
    protected String  newEmail, newPassword, confirmNewPassword, currentPassword;

    @Inject
    protected AppointmentServiceImpl appointmentServiceImpl;
    @Inject
    protected AuthServiceImpl authServiceImpl;

    protected List<AppointmentDto> pendingAppts;
    protected List<AppointmentDto> upcomingAcceptedAppts;
    protected List<AppointmentDto> pastAcceptedAppts;


    public void loadApptData() {
        boolean isDoctor = usr.getRole().equals("doctor");
        AppointmentServiceImpl.AppointmentLists apptLists = appointmentServiceImpl.findAppointments(usr, isDoctor);
        pendingAppts = apptLists.pendingFuture();
//        System.out.println("This is the appt data: " + pendingAppts + upcomingAcceptedAppts + pastAcceptedAppts);
        upcomingAcceptedAppts = apptLists.acceptedFuture();
        pastAcceptedAppts = apptLists.acceptedPast();
    }

    public boolean isEditEmail() {
        return editEmail;
    }

    public void setEditEmail(boolean editEmail) {
        this.editEmail = editEmail;
    }

    public boolean isEditPassword() {
        return editPassword;
    }

    public void setEditPassword(boolean editPassword) {
        this.editPassword = editPassword;
    }

    public void deleteAcc() {
        usr.setDeleted(deleteAccount);
        authServiceImpl.editOrDelete(usr);
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
        usr.setEmail(newEmail);
        usr.setPassword(newPassword);
        authServiceImpl.editOrDelete(usr);
    }

    public String formatTime(LocalDateTime timeObj) {
        return StringFormatter.formatTime(timeObj);
    }

    public String formatMoney(Integer money) {
        return StringFormatter.formatMoney(money);
    }

    public boolean isDeleteAccount() {
        return deleteAccount;
    }

    public void setDeleteAccount(boolean deleteAccount) {
        this.deleteAccount = deleteAccount;
    }

    public String formatAvailabilities(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            return "Wrong-format date";
        }

        DateTimeFormatter dayFmt  = DateTimeFormatter.ofPattern("EEEE, MMM d");
        DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("hh:mm a");

        String day      = start.format(dayFmt);
        String fromTime = start.format(timeFmt);
        String toTime   = end.format(timeFmt);

        return String.format("%s (%s – %s)", day, fromTime, toTime);
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
        if (usr.getPassword().equals(currentPassword) && newPassword != null && newPassword.equals(confirmNewPassword)) {
            newEmail = usr.getEmail();
            editProfile();
        } else {
            FacesGuy.error("passwordFail", "Current password does not match");
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

    public Auth getUsr() { return usr; }

}

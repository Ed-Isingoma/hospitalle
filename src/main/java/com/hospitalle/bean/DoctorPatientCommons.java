package com.hospitalle.bean;

import com.hospitalle.dto.AppointmentDto;
import com.hospitalle.models.Auth;
import com.hospitalle.models.Availability;
import com.hospitalle.services.AppointmentService;
import com.hospitalle.services.AuthService;
import com.hospitalle.util.FacesGuy;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class DoctorPatientCommons {

    protected Auth me;
    protected boolean deleteAccount;
    protected String  newEmail, newPassword, confirmNewPassword, currentPassword;

    protected final AppointmentService appointmentService   = new AppointmentService();
    protected final AuthService authService = new AuthService();

    protected List<AppointmentDto> pendingAppts;
    protected List<AppointmentDto> upcomingAcceptedAppts;
    protected List<AppointmentDto> pastAcceptedAppts;

    public List<Availability> getBookableSlots (Auth doctor) {
        return authService.getBookableSlots(doctor);
    }

    public void loadApptData() {
        boolean isDoctor = me.getRole().equals("doctor");
        AppointmentService.AppointmentLists apptLists = appointmentService.findAppointments(me, isDoctor);
        pendingAppts = apptLists.pendingFuture();
//        System.out.println("This is the appt data: " + pendingAppts + upcomingAcceptedAppts + pastAcceptedAppts);
        upcomingAcceptedAppts = apptLists.acceptedFuture();
        pastAcceptedAppts = apptLists.acceptedPast();
    }

    public void deleteAccount() {
        me.setDeleted(deleteAccount);
        authService.editOrDelete(me);
        FacesGuy.info("Deleting Account");
        logout();
    }

    public String formatTime(LocalDateTime timeObj) {
        if (timeObj == null) return "";
        return timeObj.format(DateTimeFormatter.ofPattern("MMM dd, yyyy 'at' h:mm a"));
    }

    public String formatMoney(Integer money) {
        if (money == null) return "Unset";

        NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US);
        return formatter.format(money) + " /=";
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

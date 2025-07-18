package com.hospitalle.bean;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.hospitalle.dto.AppointmentDto;
import com.hospitalle.models.*;
import com.hospitalle.services.impl.AdmissionServiceImpl;
import com.hospitalle.services.impl.AuthServiceImpl;
import com.hospitalle.services.impl.PaymentServiceImpl;
import com.hospitalle.services.impl.ReceptionistServiceImpl;
import com.hospitalle.util.FacesGuy;
import com.hospitalle.util.StringFormatter;
import org.primefaces.event.RowEditEvent;

@Named("receptionistBean")
@SessionScoped
public class ReceptionistBean extends RegisterBean implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private List<AppointmentDto> pastAppts, futureAppts;
    private List<Admission> admissions;
    private Auth usr;
    private boolean deleteAccount;
    private String  newEmail, newPassword;
    private int admittedPatients, doctorsAvailable,
            totalAppointmentsDone, totalUnpaidMoney, totalPaidMoney;
    private Admission newAdmission;

    @Inject
    private ReceptionistServiceImpl receptionistServiceImpl;
    @Inject
    private AuthServiceImpl authServiceImpl;
    @Inject
    private PaymentServiceImpl paymentServiceImpl;
    @Inject
    private AdmissionServiceImpl admissionServiceImpl;

    @PostConstruct
    public void init() {
        usr = (Auth) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("user");
        List<Auth> doctors = receptionistServiceImpl.getAllDoctors();
        doctorsAvailable = doctors.size();
        loadTotals();
        newAdmission = new Admission();
    }

    public void loadTotals() {
        pastAppts = receptionistServiceImpl.getPastAppointments();
        futureAppts = receptionistServiceImpl.getFutureAppointments();
        admissions = receptionistServiceImpl.getAllAdmissions();
        admittedPatients = admissions.size();
        totalAppointmentsDone = pastAppts.size();
        totalUnpaidMoney = pastAppts.stream().filter(ap -> !ap.isPaid())
                .mapToInt(AppointmentDto::getBill).sum() +
                futureAppts.stream().filter(ap -> !ap.isPaid())
                        .mapToInt(AppointmentDto::getBill).sum() +
                admissions.stream().filter(ad -> ad.getPayment() == null)
                        .mapToInt(Admission::getBill).sum();

        totalPaidMoney = pastAppts.stream().filter(AppointmentDto::isPaid)
                .mapToInt(AppointmentDto::getBill).sum() +
                futureAppts.stream().filter(AppointmentDto::isPaid)
                        .mapToInt(AppointmentDto::getBill).sum() +
                admissions.stream().filter(ad -> ad.getPayment() != null)
                        .mapToInt(Admission::getBill).sum();
    }

    public String deleteAccount() {
        usr.setDeleted(deleteAccount);
        authServiceImpl.editOrDelete(usr);
        FacesGuy.info("Account Deleted!");
        return "login.xhtml?faces-redirect=true";
    }

    public void editProfile() {
        usr.setEmail(newEmail);
        usr.setPassword(newPassword);
        authServiceImpl.editOrDelete(usr);
        FacesGuy.info("Editing profile successful");
    }

    public void createPatientAction() {
        authServiceImpl.register(regUsername, regEmail, regPassword, "patient");
        FacesGuy.info("mainForm:admissionEditor", "New patient “" + regUsername + "” has been registered");
        regUsername = regEmail = regPassword = regConfirmPassword = regRole = null;
    }

    public void addAdmissionPayment(Admission adm){
        Long id = paymentServiceImpl.newPayment(adm);
        loadTotals();
        FacesGuy.info("Admission record modified");
    }

    public void addApptPayment(AppointmentDto appt) {
        Long id = paymentServiceImpl.newPayment(appt);
        loadTotals();
    }

    public String formatTime(LocalDateTime time) {
        return StringFormatter.formatTime(time);
    }

    public String formatMoney(Integer money) {
        return StringFormatter.formatMoney(money);
    }

    public void newAdmissionAction() {
        admissionServiceImpl.newAdmission(newAdmission);
        loadTotals();
        FacesGuy.info("mainForm:admissionEditor", "Admission has been added for “" +
                newAdmission.getPatient().getUsername() + "”");

        newAdmission = new Admission();
    }

    public List<Auth> completePatients(String query) {
        return authServiceImpl.completePatients(query);
    }

    public void editAdmission(RowEditEvent<Admission> event) {
        Admission adm = event.getObject();
        admissionServiceImpl.editAdmission(adm);
        loadTotals();
        FacesGuy.info("mainForm:admissionEditor", "Admission updated");
    }

    public void onRowCancel() {
        FacesGuy.error("mainForm:admissionEditor", "Edit cancelled");
    }

    public int getAdmittedPatients() {
        return admittedPatients;
    }

    public void setAdmittedPatients(int admittedPatients) {
        this.admittedPatients = admittedPatients;
    }

    public int getDoctorsAvailable() {
        return doctorsAvailable;
    }

    public void setDoctorsAvailable(int doctorsAvailable) {
        this.doctorsAvailable = doctorsAvailable;
    }

    public int getTotalAppointmentsDone() {
        return totalAppointmentsDone;
    }

    public void setTotalAppointmentsDone(int totalAppointmentsDone) {
        this.totalAppointmentsDone = totalAppointmentsDone;
    }

    public int getTotalUnpaidMoney() {
        return totalUnpaidMoney;
    }

    public void setTotalUnpaidMoney(int totalUnpaidMoney) {
        this.totalUnpaidMoney = totalUnpaidMoney;
    }

    public int getTotalPaidMoney() {
        return totalPaidMoney;
    }

    public void setTotalPaidMoney(int totalPaidMoney) {
        this.totalPaidMoney = totalPaidMoney;
    }

    public Admission getNewAdmission() {
        return newAdmission;
    }

    public void setNewAdmission(Admission newAdmission) {
        this.newAdmission = newAdmission;
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

    public List<AppointmentDto> getPastAppts() {
        return pastAppts;
    }

    public List<AppointmentDto> getFutureAppts() {
        return futureAppts;
    }

    public List<Admission> getAdmissions() {
        return admissions;
    }

    public void setAdmissions(List<Admission> admissions) {
        this.admissions = admissions;
    }

}

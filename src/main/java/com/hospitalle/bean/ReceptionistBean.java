package com.hospitalle.bean;

import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.hospitalle.models.*;
import com.hospitalle.services.AdmissionService;
import com.hospitalle.services.AuthService;
import com.hospitalle.services.PaymentService;
import com.hospitalle.services.ReceptionistService;
import com.hospitalle.util.FacesGuy;

@Named("receptionistBean")
@SessionScoped
public class ReceptionistBean implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private List<Appointment> pastAppts, futureAppts;
    private List<Admission> admissions;
    private List<Auth> doctors;
    private List<Payment> payments;
    private Auth me;
    private List<Availability> availabilities;
    private boolean deleteAccount;
    private String  newEmail, newPassword, admissionWard;
    private int newPaymentAmount;
    private LocalDate newPaymentDate, admissionStartTime;
    private Admission newPaymentAdmission, editingAdmission;
    private Appointment newPaymentAppointment;
    private Payment paymentInEdit;
    private Long admissionPatientId;

    private final ReceptionistService receptionistService = new ReceptionistService();
    private final AuthService authService = new AuthService();
    private final PaymentService paymentService = new PaymentService();
    private final AdmissionService admissionService = new AdmissionService();

    @PostConstruct
    public void init() {
        me = (Auth) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("user");

        pastAppts = receptionistService.getPastAppointments();
        futureAppts = receptionistService.getFutureAppointments();
        admissions = receptionistService.getAllAdmissions();
        doctors = receptionistService.getAllDoctors();
        availabilities = receptionistService.getAllAvailabilities();
        payments = paymentService.getPayments();
    }

    public String deleteAccount() {
        me.setDeleted(deleteAccount);
        authService.editOrDelete(me);
        FacesGuy.info("Account Deleted!");
        return "login.xhtml?faces-redirect=true";
    }

    public void editProfile() {
        me.setEmail(newEmail);
        me.setPassword(newPassword);
        authService.editOrDelete(me);
        FacesGuy.info("Editing profile successful");
    }

    public void createPayment() {
        Long id = paymentService.newPayment(newPaymentAdmission, newPaymentAppointment, newPaymentAmount, newPaymentDate);
        if (id != null) {
            FacesGuy.info("Payment Added");
        } else {
            FacesGuy.error("Payment failed to be saved");
        }
    }

    public void newAdmission() {
        Long id = admissionService.newAdmission(admissionStartTime, admissionWard, admissionPatientId);
        if (id != null) {
            FacesGuy.info("Admission Added");
        } else {
            FacesGuy.error("Admission failed to be created");
        }
    }

    public void editAdmission() {
        admissionService.editAdmission(editingAdmission);
        FacesGuy.info("Admission edited");
    }
    public LocalDate getAdmissionStartTime() {
        return admissionStartTime;
    }

    public Admission getEditingAdmission() {
        return editingAdmission;
    }

    public void setEditingAdmission(Admission editingAdmission) {
        this.editingAdmission = editingAdmission;
    }

    public void setAdmissionStartTime(LocalDate admissionStartTime) {
        this.admissionStartTime = admissionStartTime;
    }

    public String getAdmissionWard() {
        return admissionWard;
    }

    public void setAdmissionWard(String admissionWard) {
        this.admissionWard = admissionWard;
    }

    public Long getAdmissionPatientId() {
        return admissionPatientId;
    }

    public void setAdmissionPatientId(Long admissionPatientId) {
        this.admissionPatientId = admissionPatientId;
    }

    public void editPayment() {
        paymentService.editPayment(paymentInEdit);
        FacesGuy.info("Payment Edited");
    }

    public int getNewPaymentAmount() {
        return newPaymentAmount;
    }

    public void setNewPaymentAmount(int newPaymentAmount) {
        this.newPaymentAmount = newPaymentAmount;
    }

    public LocalDate getNewPaymentDate() {
        return newPaymentDate;
    }

    public void setNewPaymentDate(LocalDate newPaymentDate) {
        this.newPaymentDate = newPaymentDate;
    }

    public Admission getNewPaymentAdmission() {
        return newPaymentAdmission;
    }

    public void setNewPaymentAdmission(Admission newPaymentAdmission) {
        this.newPaymentAdmission = newPaymentAdmission;
    }

    public Appointment getNewPaymentAppointment() {
        return newPaymentAppointment;
    }

    public void setNewPaymentAppointment(Appointment newPaymentAppointment) {
        this.newPaymentAppointment = newPaymentAppointment;
    }

    public Payment getPaymentInEdit() {
        return paymentInEdit;
    }

    public void setPaymentInEdit(Payment paymentInEdit) {
        this.paymentInEdit = paymentInEdit;
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

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public Auth getMe() { return me; }

    public List<Appointment> getPastAppts() {
        return pastAppts;
    }

    public void setPastAppts(List<Appointment> pastAppts) {
        this.pastAppts = pastAppts;
    }

    public List<Appointment> getFutureAppts() {
        return futureAppts;
    }

    public void setFutureAppts(List<Appointment> futureAppts) {
        this.futureAppts = futureAppts;
    }

    public List<Admission> getAdmissions() {
        return admissions;
    }

    public void setAdmissions(List<Admission> admissions) {
        this.admissions = admissions;
    }

    public List<Auth> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<Auth> doctors) {
        this.doctors = doctors;
    }

    public List<Availability> getAvailabilities() {
        return availabilities;
    }

    public void setAvailabilities(List<Availability> availabilities) {
        this.availabilities = availabilities;
    }
}

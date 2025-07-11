package com.hospitalle.bean;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import com.hospitalle.dto.AppointmentDto;
import com.hospitalle.models.Admission;
import com.hospitalle.models.Auth;
import com.hospitalle.models.Availability;
import com.hospitalle.services.impl.AdmissionServiceImpl;
import com.hospitalle.services.impl.AppointmentServiceImpl;
import com.hospitalle.services.impl.PatientServiceImpl;
import com.hospitalle.util.FacesGuy;
import org.primefaces.event.RowEditEvent;

@Named("patientBean")
@SessionScoped
public class PatientBean extends DoctorPatientCommons implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private List<Auth> doctors;
    private List<Admission> admissions;
    private Long newApptSpecialityId, newApptAvailabilityId;
    private Auth selectedDoctor;

    @Inject
    private PatientServiceImpl patientServiceImpl;
    @Inject
    private AppointmentServiceImpl appointmentServiceImpl;
    @Inject
    private AdmissionServiceImpl admissionServiceImpl;

    @PostConstruct
    public void init() {
            usr = (Auth) FacesContext.getCurrentInstance()
                    .getExternalContext().getSessionMap().get("user");

//            if (me == null) {
//                logout();
//                return;
//            }

            doctors = patientServiceImpl.getDoctors();
            loadApptData();
            admissions = admissionServiceImpl.findByPatient(usr);

    }

    public List<Availability> getBookableSlots() {
        return authServiceImpl.getBookableSlots(selectedDoctor);
    }

    public List<Admission> getAdmissions() {
        return admissions;
    }

    public void newAppointment() {
        appointmentServiceImpl.newAppt(newApptAvailabilityId, newApptSpecialityId, usr);
        loadApptData();
        FacesGuy.info("Your appointment with Dr. " + selectedDoctor.getUsername() + " has been booked.");

        newApptSpecialityId    = null;
        newApptAvailabilityId  = null;
    }

    public Long getNewApptAvailabilityId() {
        return newApptAvailabilityId;
    }

    public void setNewApptAvailabilityId(Long newApptAvailabilityId) {
        this.newApptAvailabilityId = newApptAvailabilityId;
    }

    public void setNewApptSpecialityId(Long newApptSpecialityId) {
        this.newApptSpecialityId = newApptSpecialityId;
    }

    public void savePatientFeedback(RowEditEvent<AppointmentDto> event) {
        AppointmentDto edited = event.getObject();
        appointmentServiceImpl.addPatientFeedback(edited);
        FacesGuy.info("Feedback saved");
    }

    public Auth getSelectedDoctor() {
        return selectedDoctor;
    }

    public void setSelectedDoctor(Auth selectedDoctor) {
        this.selectedDoctor = selectedDoctor;
    }

    public Long getNewApptSpecialityId() {
        return newApptSpecialityId;
    }

    public List<Auth> getDoctors() {
        return doctors;
    }

    public void prepareForBooking(Auth doctor) {
        this.selectedDoctor = doctor;
        this.newApptSpecialityId = null;
        this.newApptAvailabilityId = null;
    }

    public void setDoctors(List<Auth> doctors) {
        this.doctors = doctors;
    }
}

package com.hospitalle.bean;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

//import java.util.logging.Logger;
//import java.util.logging.Level;
//import javax.faces.context.FacesContext;

import com.hospitalle.dto.AdmissionDto;
import com.hospitalle.dto.AppointmentDto;
import com.hospitalle.models.Auth;
import com.hospitalle.services.AdmissionService;
import com.hospitalle.services.AppointmentService;
import com.hospitalle.services.PatientService;
import com.hospitalle.util.FacesGuy;
import org.primefaces.event.RowEditEvent;

@Named("patientBean")
@SessionScoped
public class PatientBean extends DoctorPatientCommons implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

//    private static final Logger logger = Logger.getLogger(PatientBean.class.getName());

    private List<Auth> doctors;
    private List<AdmissionDto> admissions;
    private Long newApptSpecialityId, newApptAvailabilityId;
    private Auth selectedDoctor;

    private final PatientService patientService = new PatientService();
    private final AppointmentService appointmentService = new AppointmentService();
    private final AdmissionService admissionService = new AdmissionService();

    @PostConstruct
    public void init() {
//        try {
            me = (Auth) FacesContext.getCurrentInstance()
                    .getExternalContext().getSessionMap().get("user");

//            if (me == null) {
//                logout();
//                return;
//            }

            doctors = patientService.getDoctors();
            loadApptData();
            admissions = admissionService.findByPatient(me);

//        } catch (Exception e) {
//            logger.severe("Exception type: " + e.getClass().getName());
//            logger.severe("Exception message: " + e.getMessage());
//        }
    }

    public List<AdmissionDto> getAdmissions() {
        return admissions;
    }

    public void newAppointment() {
        appointmentService.newAppt(newApptAvailabilityId, newApptSpecialityId, me);
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
        appointmentService.addPatientFeedback(edited);
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

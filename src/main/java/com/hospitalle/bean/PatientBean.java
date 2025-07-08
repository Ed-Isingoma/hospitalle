package com.hospitalle.bean;

import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import com.hospitalle.models.Admission;
import com.hospitalle.models.Auth;
import com.hospitalle.models.Availability;
import com.hospitalle.models.Speciality;
import com.hospitalle.services.AdmissionService;
import com.hospitalle.services.AppointmentService;
import com.hospitalle.services.PatientService;

@Named("patientBean")
@SessionScoped
public class PatientBean extends DoctorPatientCommons implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private List<Auth> doctors;
    private List<Admission> admissions;
    private Availability newApptAvailability;
    private Speciality newApptSpeciality;
    private Auth selectedDoctor;

    private final PatientService patientService = new PatientService();
    private final AppointmentService appointmentService = new AppointmentService();
    private final AdmissionService admissionService = new AdmissionService();

    @PostConstruct
    public void init() {
        me = (Auth) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("user");
//        if (me == null) {
//            logout();

//        } else {
            doctors = patientService.getDoctors();
            loadApptData();
            admissions = admissionService.findByPatient(me);
//        }
    }

    public List<Admission> getAdmissions() {
        return admissions;
    }

    public void setAdmissions(List<Admission> admissions) {
        this.admissions = admissions;
    }

    public void newAppointment() {
        appointmentService.newAppt(newApptAvailability, newApptSpeciality, me);
    }

    public Availability getNewApptAvailability() {
        return newApptAvailability;
    }

    public void setNewApptAvailability(Availability newApptAvailability) {
        this.newApptAvailability = newApptAvailability;
    }

    public void setNewApptSpeciality(Speciality newApptSpeciality) {
        this.newApptSpeciality = newApptSpeciality;
    }

    public Auth getSelectedDoctor() {
        return selectedDoctor;
    }


    public void saveUpdatedAppt() {
        appointmentService.doUpdateAppointment(updatedAppt);
        loadApptData();
    }

    public void setSelectedDoctor(Auth selectedDoctor) {
        this.selectedDoctor = selectedDoctor;
    }

    public Speciality getNewApptSpeciality() {
        return newApptSpeciality;
    }

    public void setNewApptSpecialityId(Speciality newApptSpeciality) {
        this.newApptSpeciality = newApptSpeciality;
    }

    public List<Auth> getDoctors() {
        return doctors;
    }
    public void setDoctors(List<Auth> doctors) {
        this.doctors = doctors;
    }
}

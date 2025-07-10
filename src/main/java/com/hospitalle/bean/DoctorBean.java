package com.hospitalle.bean;

import javax.annotation.PostConstruct;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.hospitalle.models.Auth;
import com.hospitalle.models.Availability;
import com.hospitalle.models.Speciality;
import com.hospitalle.services.AvailabilityService;
import com.hospitalle.services.SpecialityService;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named("doctorBean")
@SessionScoped
public class DoctorBean extends DoctorPatientCommons implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private List<Availability> futureSlots;
    private List<Speciality> specialities;

    private LocalDateTime newStart, newEnd;
    private String newSpecName;
    private int newHourlyPrice;

    private final AvailabilityService availabilityService = new AvailabilityService();
    private final SpecialityService specialityService = new SpecialityService();

    @PostConstruct
    public void init() {
        me = (Auth) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("user");
        loadApptData();

        futureSlots  = availabilityService.findFutureSlots(me);
        specialities = specialityService.findByDoctor(me);
    }

    public void saveUpdatedAppt() {
//        appointmentService.addDoctorChanges();
//        loadApptData();

//        futureSlots  = availabilityService.findFutureSlots(me);
//        specialities = specialityService.findByDoctor(me);
    }

    public void addSlot() {
        availabilityService.addSlot(me, newStart, newEnd);
        futureSlots = availabilityService.findFutureSlots(me);
    }

    public void addSpeciality() {
        specialityService.addSpec(newSpecName, newHourlyPrice);
        specialities = specialityService.findByDoctor(me);
    }

    public void removeSpeciality(Long id) {
        specialityService.removeSpec(id);
        specialities = specialityService.findByDoctor(me);
    }

    public void removeSlot(Long id) {
        availabilityService.removeSlot(id);
        futureSlots = availabilityService.findFutureSlots(me);
    }

    public List<Availability> getFutureSlots() { return futureSlots; }
    public List<Speciality> getSpecialities() { return specialities; }

    public LocalDateTime getNewStart() { return newStart; }
    public void setNewStart(LocalDateTime newStart) { this.newStart = newStart; }

    public LocalDateTime getNewEnd()   { return newEnd;   }
    public void setNewEnd(LocalDateTime newEnd)   { this.newEnd = newEnd;   }

    public String getNewSpecName() { return newSpecName; }
    public void setNewSpecName(String newSpec) { this.newSpecName = newSpec; }

    public int getNewHourlyPrice() { return newHourlyPrice; }
    public void setNewHourlyPrice(int price) { this.newHourlyPrice = price; }
}

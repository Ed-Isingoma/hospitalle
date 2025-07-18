package com.hospitalle.bean;

import javax.annotation.PostConstruct;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

import com.hospitalle.dto.AppointmentDto;
import com.hospitalle.models.Auth;
import com.hospitalle.models.Availability;
import com.hospitalle.models.Speciality;
import com.hospitalle.services.impl.AvailabilityServiceImpl;
import com.hospitalle.services.impl.SpecialityServiceImpl;
import com.hospitalle.util.FacesGuy;
import org.primefaces.event.RowEditEvent;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named("doctorBean")
@SessionScoped
public class DoctorBean extends DoctorPatientCommons implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private List<Availability> futureSlots;
    private Availability scheduleToDelete;
    private List<Speciality> specialities;
    private Speciality specToDelete;
    private LocalDate selectedDate;
    private int selectedHour, newHourlyPrice;
    private String newSpecName;

    @Inject
    private AvailabilityServiceImpl availabilityServiceImpl;
    @Inject
    private SpecialityServiceImpl specialityServiceImpl;

    @PostConstruct
    public void init() {
        usr = (Auth) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("user");
        loadApptData();

        futureSlots  = availabilityServiceImpl.findFutureSlots(usr);
        specialities = specialityServiceImpl.findByDoctor(usr);

        if (futureSlots != null && !futureSlots.isEmpty()) {
            LocalDate maxDate = futureSlots.stream()
                    .map(av -> av.getStartTime().toLocalDate())
                    .max(Comparator.naturalOrder())
                    .orElse(LocalDate.now());
            selectedDate = maxDate.plusDays(1);
        } else {
            selectedDate = LocalDate.now().plusDays(1);
        }
        selectedHour = 8;
    }

    public void acceptAppointment(AppointmentDto appt) {
        appointmentServiceImpl.acceptAppointment(appt.getId());
        loadApptData();
        futureSlots = availabilityServiceImpl.findFutureSlots(usr);
        FacesGuy.info("Appointment accepted");
    }

    public void saveUpdatedAppt(RowEditEvent<AppointmentDto> event) {
        AppointmentDto edited = event.getObject();

        String newDiagnosis = edited.getDiagnosis();
        String newPrescription = edited.getPrescription();
        Long apptId = edited.getId();

        appointmentServiceImpl.addDoctorChanges(apptId, newDiagnosis, newPrescription);

        loadApptData();
        futureSlots  = availabilityServiceImpl.findFutureSlots(usr);
        specialities = specialityServiceImpl.findByDoctor(usr);
        FacesGuy.info("Appointment updated");
    }

    public void addSpeciality() {
        specialityServiceImpl.addSpec(newSpecName, newHourlyPrice, usr);
        specialities = specialityServiceImpl.findByDoctor(usr);
    }

    public void removeSpeciality() {
        specialityServiceImpl.removeSpec(specToDelete);
        specialities = specialityServiceImpl.findByDoctor(usr);
    }

    public void prevDate() {
        selectedDate = selectedDate.minusDays(1);
    }

    public void nextDate() {
        selectedDate = selectedDate.plusDays(1);
    }

    public String showSelectedDate(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("EEE, MMM d"));
    }

    public void prevTime() {
        if (selectedHour > 8) {
            selectedHour--;
        }
    }

    public void nextTime() {
        if (selectedHour < 22) {
            selectedHour++;
        }
    }

    public void setSpecToDelete(Speciality specToDelete) {
        this.specToDelete = specToDelete;
    }

    public void addSchedule() {
        LocalDateTime start = selectedDate.atTime(selectedHour, 0);
        LocalDateTime end   = start.plusHours(1);

        availabilityServiceImpl.addSlot(usr, start, end);
        futureSlots  = availabilityServiceImpl.findFutureSlots(usr);
    }

    public LocalDate getSelectedDate() {
        return selectedDate;
    }
    public void setSelectedDate(LocalDate selectedDate) {
        this.selectedDate = selectedDate;
    }

    public int getSelectedHour() {
        return selectedHour;
    }
    public void setSelectedHour(int selectedHour) {
        this.selectedHour = selectedHour;
    }

    public void setScheduleToDelete(Availability sc) {
        this.scheduleToDelete = sc;
    }

    public void deleteAvailability() {
        availabilityServiceImpl.removeSlot(this.scheduleToDelete);
        futureSlots = availabilityServiceImpl.findFutureSlots(usr);
    }

    public List<Availability> getFutureSlots() { return futureSlots; }
    public List<Speciality> getSpecialities() { return specialities; }

    public String getNewSpecName() { return newSpecName; }
    public void setNewSpecName(String newSpec) { this.newSpecName = newSpec; }

    public int getNewHourlyPrice() { return newHourlyPrice; }
    public void setNewHourlyPrice(int price) { this.newHourlyPrice = price; }
}

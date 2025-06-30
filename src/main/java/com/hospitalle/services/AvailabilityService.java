package com.hospitalle.services;

import com.hospitalle.dao.AvailabilityDao;
import com.hospitalle.models.Availability;
import com.hospitalle.models.Auth;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class AvailabilityService {
    private AvailabilityDao dao = new AvailabilityDao();

    public List<Availability> findByDoctor(Auth doctor) {
        return dao.findByDoctor(doctor);
    }

    public List<Availability> findFutureSlots(Auth doctor) {
        return dao.findByDoctor(doctor).stream()
                .filter(a -> a.getAppointment() == null && a.getStartTime().isAfter(LocalDateTime.now()))
                .collect(Collectors.toList());
    }

    public void removeSlot(Long id) {
        Availability av = dao.findById(id);
        dao.delete(av);
    }

    public void addSlot(Auth doctor, LocalDateTime start, LocalDateTime end) {
        Availability av = new Availability();
        av.setDoctor(doctor);
        av.setStartTime(start);
        av.setEndTime(end);
        dao.save(av);
    }
}

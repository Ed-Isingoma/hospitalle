package com.hospitalle.services.impl;

import com.hospitalle.dao.AvailabilityDao;
import com.hospitalle.models.Availability;
import com.hospitalle.models.Auth;
import com.hospitalle.services.AvailabilityService;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class AvailabilityServiceImpl implements AvailabilityService {
    @Inject
    private AvailabilityDao dao;

    public List<Availability> findByDoctor(Auth doctor) {
        return dao.findByDoctor(doctor);
    }

    public List<Availability> findFutureSlots(Auth doctor) {
        return dao.findByDoctor(doctor).stream()
                .filter(a -> (a.getAppointment() == null ||
                        (a.getAppointment() != null && !a.getAppointment().getAccepted())
                        ) && a.getStartTime().isAfter(LocalDateTime.now()))
                .collect(Collectors.toList());
    }

    public void removeSlot(Availability slot) {
        dao.delete(slot);
    }

    public void addSlot(Auth doctor, LocalDateTime start, LocalDateTime end) {
        Availability av = new Availability();
        av.setDoctor(doctor);
        av.setStartTime(start);
        av.setEndTime(end);
        dao.save(av);
    }
}

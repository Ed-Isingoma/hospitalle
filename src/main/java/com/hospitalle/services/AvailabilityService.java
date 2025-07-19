package com.hospitalle.services;

import com.hospitalle.models.Auth;
import com.hospitalle.models.Availability;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public interface AvailabilityService extends Serializable {
    List<Availability> findFutureSlots(Auth doctor);

    void removeSlot(Availability slot);

    void addSlot(Auth doctor, LocalDateTime start, LocalDateTime end);
}

package com.hospitalle.dao;

import com.hospitalle.models.Appointment;

public class AppointmentDao extends GenericDao<Appointment> {
    public AppointmentDao(){ super(Appointment.class); }
}
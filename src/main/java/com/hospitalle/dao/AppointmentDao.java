package com.hospitalle.dao;

import com.hospitalle.models.Appointment;
import com.hospitalle.util.HibernateUtil;
import org.hibernate.Session;

import java.time.LocalDateTime;
import java.util.List;

public class AppointmentDao extends GenericDao<Appointment> {
    public AppointmentDao(){ super(Appointment.class); }

    public List<Appointment> findPastAppointments() {
        try (Session session = HibernateUtil.getSession()) {
            String hql = """
            SELECT DISTINCT a
            FROM Appointment a
            JOIN a.availabilities av
            WHERE av.start_time < :now
        """;
            return session.createQuery(hql, Appointment.class)
                    .setParameter("now", LocalDateTime.now())
                    .getResultList();
        }
    }

    public List<Appointment> findFutureAppointments() {
        try (Session session = HibernateUtil.getSession()) {
            String hql = """
            SELECT DISTINCT a
            FROM Appointment a
            JOIN a.availabilities av
            WHERE av.start_time >= :now
        """;
            return session.createQuery(hql, Appointment.class)
                    .setParameter("now", LocalDateTime.now())
                    .getResultList();
        }
    }


}
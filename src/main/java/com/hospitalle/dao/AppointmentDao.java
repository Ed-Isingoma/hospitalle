package com.hospitalle.dao;

import com.hospitalle.dto.AppointmentDto;
import com.hospitalle.models.Appointment;
import com.hospitalle.models.Payment;
import com.hospitalle.services.impl.AppointmentServiceImpl;
import com.hospitalle.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class AppointmentDao extends GenericDao<Appointment> {
    public AppointmentDao(){ super(Appointment.class); }

    public List<AppointmentDto> findPastAppointments() {
        try (Session session = HibernateUtil.getSession()) {
            String hql = """
            SELECT DISTINCT a
            FROM Appointment a
            JOIN a.availability av
            WHERE av.start_time < :now
        """;
            List<Appointment> appts = session.createQuery(hql, Appointment.class)
                    .setParameter("now", LocalDateTime.now())
                    .getResultList();

            return appts.stream().map(ap-> AppointmentServiceImpl.assignDto(ap, ap.getAvailability()))
                    .collect(Collectors.toList());
        }
    }

    public void addFeedback(Long id, String feedback) {
        Transaction tx = null;
        try(Session session = HibernateUtil.getSession()) {
            tx = session.beginTransaction();
            Appointment appt = session.get(Appointment.class, id);
            appt.setPatient_feedback(feedback);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("Error during addFeedback: " + e.getMessage());
        }
    }

    public List<AppointmentDto> findFutureAppointments() {
        try (Session session = HibernateUtil.getSession()) {
            String hql = """
            SELECT DISTINCT a
            FROM Appointment a
            JOIN a.availability av
            WHERE av.start_time >= :now
        """;
            List<Appointment> appts = session.createQuery(hql, Appointment.class)
                    .setParameter("now", LocalDateTime.now())
                    .getResultList();

            return appts.stream().map(ap-> AppointmentServiceImpl.assignDto(ap, ap.getAvailability()))
                    .collect(Collectors.toList());
        }
    }

}

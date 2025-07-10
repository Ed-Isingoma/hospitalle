package com.hospitalle.dao;

import com.hospitalle.models.Appointment;
import com.hospitalle.models.Payment;
import com.hospitalle.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public class AppointmentDao extends GenericDao<Appointment> {
    public AppointmentDao(){ super(Appointment.class); }

    public List<Appointment> findPastAppointments() {
        try (Session session = HibernateUtil.getSession()) {
            String hql = """
            SELECT DISTINCT a
            FROM Appointment a
            JOIN a.availability av
            WHERE av.start_time < :now
        """;
            return session.createQuery(hql, Appointment.class)
                    .setParameter("now", LocalDateTime.now())
                    .getResultList();
        }
    }

    public List<Payment> findPaymentsForAppts(List<Long> ids) {
        try (Session session = HibernateUtil.getSession()) {
            String hql = """
            SELECT p
            FROM Payment p
            WHERE p.appointment.id IN (:ids)
            """;
            return session.createQuery(hql, Payment.class)
                    .setParameter("ids", ids)
                    .getResultList();
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

    public List<Appointment> findFutureAppointments() {
        try (Session session = HibernateUtil.getSession()) {
            String hql = """
            SELECT DISTINCT a
            FROM Appointment a
            JOIN a.availability av
            WHERE av.start_time >= :now
        """;
            return session.createQuery(hql, Appointment.class)
                    .setParameter("now", LocalDateTime.now())
                    .getResultList();
        }
    }
}
package com.hospitalle.dao;

import com.hospitalle.models.Auth;
import com.hospitalle.models.Availability;
import com.hospitalle.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.hibernate.query.Query;

public class AuthDao extends GenericDao<Auth> {
    public AuthDao(){ super(Auth.class); }

    public Auth findByUsernameAndPassword(String usr, String pwd) {
        try (Session s = HibernateUtil.getSession()) {
            Query<Auth> q = s.createQuery(
                    "FROM Auth a WHERE a.username = :usr AND a.password = :pwd",
                    Auth.class
            );
            q.setParameter("usr", usr);
            q.setParameter("pwd", pwd);
            return q.uniqueResult();
        } catch (HibernateException he) {
            System.err.println("Error in findByUsernameAndPassword: " + he.getMessage());
            return null;
        }
    }

    public List<Availability> findBookableSlots(Auth doctor) {
        try (Session session = HibernateUtil.getSession()) {
            String hql = """
            FROM Availability a
            WHERE a.doctor = :doctor
              AND a.start_time >= :now
              AND a.appointment IS NULL
            ORDER BY a.start_time ASC
        """;

            return session.createQuery(hql, Availability.class)
                    .setParameter("doctor", doctor)
                    .setParameter("now", LocalDateTime.now())
                    .getResultList();
        }
    }
    public List<Auth> findByRole(String role) {
        try (Session s = HibernateUtil.getSession()) {
            String hql = "FROM Auth a WHERE a.role = :role";
            Query<Auth> q = s.createQuery(hql, Auth.class);
            q.setParameter("role", role);
            return q.list();
        } catch (HibernateException he) {
            System.err.println("Error in findByRole: " + he.getMessage());
            return Collections.emptyList();
        }
    }

    public List<Auth> findByRoleWithSpecialities(String role) {
        try (Session session = HibernateUtil.getSession()) {
            String hql = """
            SELECT DISTINCT a
            FROM Auth a
            LEFT JOIN FETCH a.specialities
            WHERE a.role = :role
        """;

            Query<Auth> query = session.createQuery(hql, Auth.class);
            query.setParameter("role", role);
            return query.list();
        } catch (HibernateException he) {
            System.err.println("Error in findByRoleWithSpecialities: " + he.getMessage());
            return Collections.emptyList();
        }
    }

}

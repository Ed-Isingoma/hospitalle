package com.hospitalle.dao;

import com.hospitalle.models.Auth;
import com.hospitalle.models.Availability;
import com.hospitalle.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Collections;
import java.util.List;

public class AvailabilityDao extends GenericDao<Availability> {
    public AvailabilityDao(){ super(Availability.class); }

    public List<Availability> findByDoctor(Auth user) {
        try (Session s = HibernateUtil.getSession()) {
            String hql = "FROM Availability a WHERE a.doctor = :user";
            Query<Availability> q = s.createQuery(hql, Availability.class);
            q.setParameter("user", user);
            return q.list();
        } catch (HibernateException he) {
            System.err.println("Error in findByDoctor: " + he.getMessage());
            return Collections.emptyList();
        }
    }
}

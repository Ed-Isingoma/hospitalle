package com.hospitalle.dao;

import com.hospitalle.models.Auth;
import com.hospitalle.models.Speciality;
import com.hospitalle.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Collections;
import java.util.List;

public class SpecialityDao extends GenericDao<Speciality> {
    public SpecialityDao(){ super(Speciality.class); }

    public List<Speciality> findByDoctor(Auth user) {
        try (Session s = HibernateUtil.getSession()) {
            String hql = "FROM Speciality s WHERE s.doctor = :user";
            Query<Speciality> q = s.createQuery(hql, Speciality.class);
            q.setParameter("user", user);
            return q.list();
        } catch (HibernateException he) {
            System.err.println("Error in findByDoctor: " + he.getMessage());
            return Collections.emptyList();
        }
    }

    public void saveAll(Auth user, List<Speciality> specs) {
        try (Session s = HibernateUtil.getSession()) {
            String hql = "FROM Speciality s WHERE s.doctor = :user";
            Query<Speciality> q = s.createQuery(hql, Speciality.class);
            q.setParameter("user", user);
        } catch (HibernateException he) {
            System.err.println("Error in saveAll: " + he.getMessage());
        }
    }
}

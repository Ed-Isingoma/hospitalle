package com.hospitalle.dao;

import com.hospitalle.models.Admission;
import com.hospitalle.models.Auth;
import com.hospitalle.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Collections;
import java.util.List;

public class AdmissionDao extends GenericDao<Admission> {
    public AdmissionDao(){ super(Admission.class); }

    @Override
    public List<Admission> findByPatient(Auth user) {
        try (Session session = HibernateUtil.getSession()) {
            String hql = "FROM Admission a WHERE a.patient = :user";
            Query<Admission> q = session.createQuery(hql, Admission.class);
            q.setParameter("user", user);
            return q.list();
        } catch (HibernateException he) {
            System.err.println("Error in findByPatient: " + he.getMessage());
            return Collections.emptyList();
        }
    }
}

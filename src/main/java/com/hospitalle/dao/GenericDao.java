package com.hospitalle.dao;

import com.hospitalle.models.Auth;
import org.hibernate.*;
import com.hospitalle.util.HibernateUtil;
import org.hibernate.query.Query;

import java.util.*;

public abstract class GenericDao<T> {
    private final Class<T> theTable;


    protected GenericDao(Class<T> theTable) {
        this.theTable = theTable;
    }

    public Long save(T inst) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSession()) {
            tx = session.beginTransaction();
            Long id = (Long) session.save(inst);
            tx.commit();
            return id;
        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            System.out.println("Error in save: " + he.getMessage());
            return null;
        }
    }

    public void update(T inst) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSession()) {
            tx = session.beginTransaction();
            session.merge(inst);
            tx.commit();
        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            System.out.println("Error in update: " + he.getMessage());
        }
    }

    public T findById(Long id) {
        try (Session s = HibernateUtil.getSession()) {
            return s.get(theTable, id);
        } catch (HibernateException he) {
            System.out.println("Error in findById: " + he.getMessage());
            return null;
        }
    }

    public List<T> findAll() {
        try (Session s = HibernateUtil.getSession()) {
            return s.createQuery("from " + theTable.getSimpleName(), theTable).list();
        } catch (HibernateException he) {
            System.out.println("Error in findAll: " + he.getMessage());
            return Collections.emptyList();
        }
    }

    public List<T> findByParameters(String tableName, Map<String, Object> criteria) {
        if (criteria == null || criteria.isEmpty()) {
            return findAll();
        }

        StringBuilder hql = new StringBuilder("from ")
                .append(tableName)
                .append(" where ");

        List<String> predicates = new ArrayList<>();
        for (String column : criteria.keySet()) {
            predicates.add(column + " = :" + column);
        }
        hql.append(String.join(" and ", predicates));

        try (Session session = HibernateUtil.getSession()) {
            Query<T> query = session.createQuery(hql.toString(), theTable);
            for (Map.Entry<String, Object> entry : criteria.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
            return query.list();
        } catch (HibernateException he) {
            System.out.println("Error in findByParameters: " + he.getMessage());
            return Collections.emptyList();
        }
    }

    public void delete(T e) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSession()) {
            tx = s.beginTransaction();
            s.delete(e);
            tx.commit();
        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            System.out.println("Error in delete: " + he.getMessage());
        }
    }

    public List<T> findByPatient(Auth user) {
        try (Session s = HibernateUtil.getSession()) {
            String hql = "FROM Appointment a WHERE a.patient = :user";
            Query<T> q = s.createQuery(hql, theTable);
            q.setParameter("user", user);
            return q.list();
        } catch (HibernateException he) {
            System.err.println("Error in findByPatient: " + he.getMessage());
            return Collections.emptyList();
        }
    }
}

package com.hospitalle.dao;

import org.hibernate.*;
import com.hospitalle.util.HibernateUtil;
import java.util.*;

public abstract class GenericDao<T> {
    private final Class<T> theTable;

    protected GenericDao(Class<T> theTable) {
        this.theTable = theTable;
    }
    
    public void save(T inst) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSession()) {
            tx = s.beginTransaction();
            s.saveOrUpdate(inst);
            tx.commit();
        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            System.out.println("Error in save: " + he.getMessage());
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
}

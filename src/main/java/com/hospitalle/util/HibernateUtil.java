package com.hospitalle.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory sf;
    static {
        try {
            sf = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            ex.printStackTrace();
            System.out.println("Failed in the initials");
        }
    }

    public static Session getSession() {
        return sf.openSession();
    }
}


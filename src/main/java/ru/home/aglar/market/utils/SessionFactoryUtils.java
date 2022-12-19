package ru.home.aglar.market.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;

@Component
public class SessionFactoryUtils {
    private SessionFactory sessionFactory;

    public void init() {
        sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();
    }

    public Session getSession() {
        return (sessionFactory != null) ? sessionFactory.getCurrentSession() : null;
    }

    public void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
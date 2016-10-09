package com.mkyong.controlMethods;

import com.mkyong.main.Main;
import com.mkyong.transport.APPUSER;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.Iterator;
import java.util.List;

public class LoginMethods {

    private static SessionFactory factory;





    public static boolean validate( String username, String pass ){

        boolean returnval = false;

        try{
            factory = new Configuration().configure().buildSessionFactory();
        }catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }


        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            List users = session.createQuery("FROM APPUSER").list();
            for (Iterator iterator = users.iterator(); iterator.hasNext();)
            {
                APPUSER user = (APPUSER) iterator.next();
                if(user.getnazwaUzytkownika().equals(username) && user.getHaslo().equals(pass))
                {
                    returnval = true;
                    Main.activeUser = user.getappUserId();
                    Main.activeUserEntity = user;
                }
            }
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }

        return returnval;
    }




}

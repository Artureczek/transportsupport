package com.mkyong.controlMethods;

import com.mkyong.main.Main;
import com.mkyong.transport.APPUSER;
import com.mkyong.transport.POJAZD;
import com.mkyong.transport.PRACOWNIK;
import com.mkyong.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by artur on 09.10.2016.
 */
public class ViewCarsMethods {

    public static List<POJAZD> getCars() {

        Session session = HibernateUtil.getSessionFactory().openSession();
        List<POJAZD> returnPojazd = new ArrayList<>();

        try {
            session.beginTransaction();
            String hql = "FROM POJAZD P WHERE P.user =:user";
            Query query = session.createQuery(hql).setParameter("user", Main.activeUserEntity);
            List results = query.list();

            for (Iterator iterator = results.iterator(); iterator.hasNext(); ) {
                POJAZD car = (POJAZD) iterator.next();
                returnPojazd.add(car);
            }

            //session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
            return returnPojazd;
        }

    }

    public static POJAZD getOneCar(String numerRejestracyjny) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        POJAZD returnPojazd = new POJAZD();
        try {
            session.beginTransaction();
            String hql = "FROM POJAZD P WHERE P.nrRejestracji =:numerRejestracyjny";
            Query query = session.createQuery(hql).setParameter("numerRejestracyjny",numerRejestracyjny).setMaxResults(1);
            List results = query.list();

            for (Iterator iterator = results.iterator(); iterator.hasNext(); ) {
                returnPojazd = (POJAZD) iterator.next();

            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
            return returnPojazd;
        }

    }

    public static List<POJAZD> getCarsByPlate(List<String> plateList) {

        List<POJAZD> returnList = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();
            for(String plate : plateList) {
                String hql = "FROM POJAZD P WHERE P.nrRejestracji =:numerRejestracyjny";
                Query query = session.createQuery(hql).setParameter("numerRejestracyjny", plate.split(" ")[1]).setMaxResults(1);
                List results = query.list();

                for (Iterator iterator = results.iterator(); iterator.hasNext(); ) {
                    POJAZD returnPojazd = (POJAZD) iterator.next();
                    returnList.add(returnPojazd);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
        return returnList;

    }

    public static void saveCar(POJAZD pracownik){

        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Object merged = session.merge(pracownik);
            session.beginTransaction();
            session.saveOrUpdate(merged);
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

    public static void deleteCar(POJAZD pojazd){

        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Object merged = session.merge(pojazd);
            session.beginTransaction();
            session.delete(merged);
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

}
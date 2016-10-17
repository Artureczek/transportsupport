package com.mkyong.controlMethods;

import com.mkyong.controllers.ViewCarsController;
import com.mkyong.controllers.ViewWorkersController;
import com.mkyong.main.Main;
import com.mkyong.transport.APPUSER;
import com.mkyong.transport.DOKUMENTPRACOWNIKA;
import com.mkyong.transport.PRACOWNIK;
import com.mkyong.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by artur on 09.10.2016.
 */
public class ViewWorkersMethods {

    public static List<PRACOWNIK> getWorkers(Boolean driversOnly) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        List<PRACOWNIK> returnPracownik = new ArrayList<>();

        try {
            session.beginTransaction();
            String hql;
            if(driversOnly)
                hql = "FROM PRACOWNIK P WHERE P.user =:user AND P.czyKierowca=true";
            else
                hql = "FROM PRACOWNIK P WHERE P.user =:user";
            Query query = session.createQuery(hql).setParameter("user", Main.activeUserEntity);
            List results = query.list();

            for (Iterator iterator = results.iterator(); iterator.hasNext(); ) {
                PRACOWNIK user = (PRACOWNIK) iterator.next();
                returnPracownik.add(user);
            }

            //session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
            return returnPracownik;
        }

    }

    public static PRACOWNIK getOneWorker(String imie, String nazwisko) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        PRACOWNIK returnPracownik;
        try {
            session.beginTransaction();
            String hql = "FROM PRACOWNIK P WHERE P.imie =:imie AND P.nazwisko =:nazwisko";
            Query query = session.createQuery(hql).setParameter("imie",imie).setParameter("nazwisko",nazwisko).setMaxResults(1);
            List results = query.list();

            for (Iterator iterator = results.iterator(); iterator.hasNext(); ) {
                returnPracownik = (PRACOWNIK) iterator.next();
                session.close();
                return returnPracownik;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<PRACOWNIK> getWorkersByNameAndSurname(List<String> names) {

        List<PRACOWNIK> returnList = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        PRACOWNIK returnPracownik;
        try {
            session.beginTransaction();

            for (String name : names) {
                String hql = "FROM PRACOWNIK P WHERE P.imie =:imie AND P.nazwisko =:nazwisko";
                Query query = session.createQuery(hql).setParameter("imie", name.split(" ")[0]).setParameter("nazwisko", name.split(" ")[1]).setMaxResults(1);
                List results = query.list();

                for (Iterator iterator = results.iterator(); iterator.hasNext(); ) {
                    returnPracownik = (PRACOWNIK) iterator.next();
                    returnList.add(returnPracownik);
                }
            }

            } catch(Exception e){
                e.printStackTrace();
                return null;
            }finally {
                session.close();
            }
        return returnList;
    }

    public static void saveWorker(PRACOWNIK pracownik){

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

    public static void deleteWorker(PRACOWNIK pracownik){

        deleteWorkersDocuments(pracownik);
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Object merged = session.merge(pracownik);
            session.beginTransaction();
            session.delete(merged);
            session.getTransaction().commit();


        }catch (ConstraintViolationException cve){
            ViewWorkersController.usedWorkerAlert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

    private static void deleteWorkersDocuments(PRACOWNIK pracownik){

        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            String hql = "FROM DOKUMENTPRACOWNIKA DP WHERE DP.pracownik=:pracownik";
            Query query = session.createQuery(hql).setParameter("pracownik",pracownik);
            List results = query.list();
            if(results.size()>0) {

                for (Iterator iterator = results.iterator(); iterator.hasNext(); ) {
                    DOKUMENTPRACOWNIKA dokumentpracownika = (DOKUMENTPRACOWNIKA) iterator.next();
                    session.delete(dokumentpracownika);
                }
                session.getTransaction().commit();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

    }
}
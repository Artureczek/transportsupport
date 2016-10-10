package com.mkyong.controlMethods;

import com.mkyong.controllers.WorkersDocumentsController;
import com.mkyong.main.Main;
import com.mkyong.transport.DOKUMENTPRACOWNIKA;
import com.mkyong.transport.NAZWADOKUMENTU;
import com.mkyong.transport.PRACOWNIK;
import com.mkyong.transport.PUSTYDOKUMENT;
import com.mkyong.util.HibernateUtil;
import org.apache.commons.io.FileUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by akielbiewski on 10.10.2016.
 */
public class WorkersDocumentsMethods {

    public static List<DOKUMENTPRACOWNIKA> getWorkersDocuments(PRACOWNIK pracownik){

        List<DOKUMENTPRACOWNIKA> dokumentyPracownika = new ArrayList<>();

        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();
            String hql = "FROM DOKUMENTPRACOWNIKA DP WHERE DP.pracownik =:pracownik";
            Query query = session.createQuery(hql).setParameter("pracownik", pracownik);
            List results = query.list();

            for (Iterator iterator = results.iterator(); iterator.hasNext(); ) {
                DOKUMENTPRACOWNIKA dokument = (DOKUMENTPRACOWNIKA) iterator.next();
                dokumentyPracownika.add(dokument);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
            return dokumentyPracownika;
        }
    }
    public static File getEmpty() throws IOException {

        PUSTYDOKUMENT pustydokument = new PUSTYDOKUMENT();
        Session session = HibernateUtil.getSessionFactory().openSession();
        File file = new File("lol");

        try {
            session.beginTransaction();
            String hql = "FROM PUSTYDOKUMENT";
            Query query = session.createQuery(hql).setMaxResults(1);
            List results = query.list();

            if(!results.iterator().hasNext())
                return null;
            else
                pustydokument = (PUSTYDOKUMENT) results.iterator().next();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }

        FileUtils.writeByteArrayToFile(file, pustydokument.getDokument());
        return file;
    }

    public static File getWorkersFile(String fileName, PRACOWNIK pracownik) throws IOException {

        File file = new File("lol");
        NAZWADOKUMENTU nazwadokumentu = getNazwa(fileName);
        DOKUMENTPRACOWNIKA dokumentpracownika = new DOKUMENTPRACOWNIKA();
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();
            String hql = "FROM DOKUMENTPRACOWNIKA DP where DP.pracownik=:pracownik and DP.nazwadokumentu=:nazwadokumentu";
            Query query = session.createQuery(hql).setParameter("nazwadokumentu", nazwadokumentu).setParameter("pracownik", pracownik).setMaxResults(1);
            List results = query.list();

            if(!results.iterator().hasNext())
                return null;
            else {
                dokumentpracownika = (DOKUMENTPRACOWNIKA) results.iterator().next();
                WorkersDocumentsController.selectedDokumentPracownika = dokumentpracownika;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }

        FileUtils.writeByteArrayToFile(file, dokumentpracownika.getDokument());
        return file;
    }

    public static void deleteDocument(DOKUMENTPRACOWNIKA dokumentpracownika) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Object merged = session.merge(dokumentpracownika);
            session.beginTransaction();
            session.delete(merged);
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }


    private static NAZWADOKUMENTU getNazwa(String nazwa){

        NAZWADOKUMENTU returnNazwa = new NAZWADOKUMENTU();
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();
            String hql = "FROM NAZWADOKUMENTU ND WHERE ND.nazwaDokumentu=:nazwa";
            Query query = session.createQuery(hql).setParameter("nazwa", nazwa).setMaxResults(1);
            List results = query.list();

            for (Iterator iterator = results.iterator(); iterator.hasNext(); ) {
                returnNazwa = (NAZWADOKUMENTU) iterator.next();

            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
            return returnNazwa;
        }


    }




}

package com.mkyong.controlMethods;

import com.mkyong.controllers.EmployeePartAController;
import com.mkyong.transport.DOKUMENTPRACOWNIKA;
import com.mkyong.transport.NAZWADOKUMENTU;
import com.mkyong.transport.PRACOWNIK;
import com.mkyong.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.List;

/**
 * Created by akielbiewski on 10.10.2016.
 */
public class AddDocumentMethods {

    public static NAZWADOKUMENTU findDocumentByNazwa(String nazwa){

        Session session = HibernateUtil.getSessionFactory().openSession();
        NAZWADOKUMENTU returnNazwaDokumentu = new NAZWADOKUMENTU();
        try {
            session.beginTransaction();
            String hql = "FROM NAZWADOKUMENTU ND WHERE ND.nazwaDokumentu =:nazwa";
            Query query = session.createQuery(hql).setParameter("nazwa",nazwa).setMaxResults(1);
            List results = query.list();

            for (Iterator iterator = results.iterator(); iterator.hasNext(); ) {
                returnNazwaDokumentu = (NAZWADOKUMENTU) iterator.next();

            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
            return returnNazwaDokumentu;
        }

    }

    public static void saveDocument(DOKUMENTPRACOWNIKA dokumentPracownika){

        Session session = HibernateUtil.getSessionFactory().openSession();
        dokumentPracownika = checkIfDocumentExcists(dokumentPracownika);

            try
            {
                session.beginTransaction();
                session.saveOrUpdate(dokumentPracownika);
                session.getTransaction().commit();

            } catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {

                session.close();
            }
        }

    private static DOKUMENTPRACOWNIKA checkIfDocumentExcists(DOKUMENTPRACOWNIKA dokumentpracownika){


        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();
            String hql = "FROM DOKUMENTPRACOWNIKA DP WHERE DP.nazwadokumentu =:dokument AND DP.pracownik=:pracownik";
            Query query = session.createQuery(hql)
                    .setParameter("dokument",dokumentpracownika.getNazwadokumentu())
                    .setParameter("pracownik",dokumentpracownika.getPracownik())
                    .setMaxResults(1);

            List results = query.list();
            if(results.size()>0){
                DOKUMENTPRACOWNIKA newDokument = (DOKUMENTPRACOWNIKA) results.iterator().next();
                newDokument.setDokument(dokumentpracownika.getDokument());
                return newDokument;
            }else{
                return dokumentpracownika;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }


}





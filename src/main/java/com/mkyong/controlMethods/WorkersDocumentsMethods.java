package com.mkyong.controlMethods;

import com.mkyong.main.Main;
import com.mkyong.transport.DOKUMENTPRACOWNIKA;
import com.mkyong.transport.PRACOWNIK;
import com.mkyong.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

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




}

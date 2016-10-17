package com.mkyong.controlMethods;

import com.mkyong.controllers.ViewWorkersController;
import com.mkyong.main.Main;
import com.mkyong.transport.TRASA;
import com.mkyong.transport.TRASA;
import com.mkyong.transport.TRASAPOJAZD;
import com.mkyong.transport.TRASAPRACOWNIK;
import com.mkyong.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by akielbiewski on 14.10.2016.
 */
public class ViewRoutesMethods {
    
    public static List<TRASA> getAllRoutes(){
        
        List<TRASA> returnList = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();
            String hql = "FROM TRASA T WHERE T.user =:user";
            Query query = session.createQuery(hql).setParameter("user", Main.activeUserEntity);
            List results = query.list();

            for (Iterator iterator = results.iterator(); iterator.hasNext(); ) {
                TRASA trasa = (TRASA) iterator.next();
                returnList.add(trasa);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
            return returnList;
        }
    }

    public static void deleteRoute(TRASA trasa){

        Set<TRASAPOJAZD> trasapojazds = trasa.getPojazdy();
        Set<TRASAPRACOWNIK> trasapracowniks = trasa.getKierowcy();

        Session session = HibernateUtil.getSessionFactory().openSession();
        try {

            for(TRASAPRACOWNIK trasapracownik : trasapracowniks) {
                Object merged = session.merge(trasapracownik);
                session.beginTransaction();
                session.delete(merged);
            }

            for(TRASAPOJAZD trasapracownik : trasapojazds) {
                Object merged = session.merge(trasapracownik);
                session.beginTransaction();
                session.delete(merged);
            }

            Object merged = session.merge(trasa);
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

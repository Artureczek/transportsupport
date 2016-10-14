package com.mkyong.controlMethods;

import com.mkyong.main.Main;
import com.mkyong.transport.TRASA;
import com.mkyong.transport.TRASA;
import com.mkyong.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
}

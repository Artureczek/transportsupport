package com.mkyong.controlMethods;

import com.mkyong.transport.POJAZD;
import com.mkyong.transport.PRACOWNIK;
import com.mkyong.util.HibernateUtil;
import org.hibernate.Session;

public class AddCarMethods {

	public static boolean Validate(POJAZD pojazd){

		boolean result = true;
		Session session = HibernateUtil.getSessionFactory().openSession();

		if(result) {

			try
			{
				session.beginTransaction();
				session.save(pojazd);
				session.getTransaction().commit();

			} catch (Exception e)
			{
				result = false;
				e.printStackTrace();
			}
			finally
			{

				session.close();
			}
		}


		return result;
	}
	
	
}

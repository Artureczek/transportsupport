package com.mkyong.controlMethods;

import com.mkyong.transport.PRACOWNIK;
import com.mkyong.util.HibernateUtil;
import org.hibernate.Session;

public class AddEmployeeMethods {

	
	public static boolean Validate(PRACOWNIK pracownik){

		boolean result = true;
		Session session = HibernateUtil.getSessionFactory().openSession();

		if(result) {

			try
			{
				session.beginTransaction();
				session.save(pracownik);
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

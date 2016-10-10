package com.mkyong.controlMethods;

import com.mkyong.controllers.EmployeePartAController;
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
				EmployeePartAController.editedPracownik = pracownik;
				System.out.println( EmployeePartAController.editedPracownik.getPracownikId() + " " + EmployeePartAController.editedPracownik.getImie());

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

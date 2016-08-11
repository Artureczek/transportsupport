import com.mkyong.transport.APPUSER;
import com.mkyong.util.HibernateUtil;
import org.hibernate.Session;

public class CreateUserMethods {


	private static boolean validate(APPUSER user, String repeat)
	{
		if(user.getHaslo().equals(repeat))
			return true;
		else
			return false;
	}


	public static boolean createUser(APPUSER user, String repeat)
	{

		boolean result = validate(user, repeat);
		Session session = HibernateUtil.getSessionFactory().openSession();

		if(result) {

			try
			{
				session.beginTransaction();
				session.save(user);
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

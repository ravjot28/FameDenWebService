import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.fameden.bean.Employee;

public class TestHibernate {

	public void processSave(Employee emp) {
		try {
			SessionFactory session = new Configuration().configure(
					"hibernate.cfg.xml").buildSessionFactory();
			Session s = session.openSession();
			s.beginTransaction();
			s.save(emp);
			s.getTransaction().commit();
			s.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String as[]){
		TestHibernate th = new TestHibernate();
		Employee emp = new Employee();
		emp.setEmpDOB("02-OCT-1988");
		emp.setEmpName("Ghuman");
		th.processSave(emp);
	}

}

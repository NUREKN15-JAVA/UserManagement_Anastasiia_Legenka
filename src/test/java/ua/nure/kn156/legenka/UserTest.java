package ua.nure.kn156.legenka;


import java.util.Calendar;
import java.util.Date;


import junit.framework.TestCase;
import ua.nure.kn156.legenka.User;

public class UserTest extends TestCase{
	private User user;
	private Date dateOfBirth;
	private static final int AGE = 19;

	public void setUp() throws Exception {
		super.setUp();
		user = new User();
		Calendar calendar = Calendar.getInstance();
		calendar.set(1998, Calendar.JULY, 12);
		dateOfBirth = calendar.getTime();
	}

	public void testGetFullName() {
		user.setFirstName("Nastya");
		user.setLastName("Legenka");
		assertEquals("Legenka, Nastya", user.getFullName());
	}
	
	public void testGetAge(){
		user.setDateOfBirth(dateOfBirth);
		assertEquals(AGE, user.getAge());
	}
	
	public void testGetFullNameForException() {
		user.setFirstName("Nastya");
	    try{
	    	user.getFullName();
	        fail("expected IllegalArgumentException");

	    } catch(IllegalArgumentException e){
	        //ignore, this exception is expected.
	    }
	}
}

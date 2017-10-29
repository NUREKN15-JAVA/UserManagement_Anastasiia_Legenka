package ua.nure.kn156.legenka.db;

import java.util.Collection;
import java.util.Date;

import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;

import ua.nure.kn156.legenka.User;

public class HsqldbUserDaoTest extends DatabaseTestCase {
	HsqldbUserDAO dao;
	private static final Long TEST_ID = 1001L;
	private ConnectionFactory connectionFactory;
	
	protected void setUp() throws Exception{
		super.setUp();
		dao=new HsqldbUserDAO(connectionFactory);
	}
	
	public void testCreate(){
		User user = new User();
		user.setFirstName("John");
		user.setLastName("Doe");
		user.setDateOfBirth(new Date());
		assertNull(user.getId());
		User createdUser;
		try {
			createdUser = dao.create(user);
			assertNotNull(createdUser);
			assertNotNull(createdUser.getId());
			assertEquals(user.getFullName(), createdUser.getFullName());
			assertEquals(user.getAge(),  createdUser.getAge());
		} catch (DatabaseException e) {
			fail(e.toString());
		}
	}
	
	public void testFindAll(){
		try {
			Collection collection = dao.findAll();
			assertNotNull("Collection is null", collection);
			assertEquals("Collection size.", 2, collection.size());
		} catch (DatabaseException e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}
	
 public void testFind(){
	 try {
		User user = dao.find(TEST_ID);
		assertNotNull("User is null", user);
		assertEquals("Wrong id", TEST_ID, user.getId());
		} catch (DatabaseException e) {
			e.printStackTrace();
			fail(e.toString());
		}
 }

 public void testUpdate(){
	 try {
		User user = dao.find(TEST_ID);
		assertNotNull("User is null", user);
		user.setLastName("Smith");
		dao.update(user);
		User updatedUser = dao.find(TEST_ID);
		assertNotNull("User is null",  updatedUser);
        assertEquals(user.getLastName(), updatedUser.getLastName());//возможно нужно проверить все поля
		} catch (DatabaseException e) {
			e.printStackTrace();
			fail(e.toString());
		}
 }
 
 public void testDelete(){
	 try {
		    User user = dao.find(TEST_ID);
			assertNotNull("User is null", user);
			dao.delete(user);
			User deletedUser = dao.find(TEST_ID);
		    assertNull("User is not deleted",  deletedUser);
		} catch (DatabaseException e) {
			e.printStackTrace();//надо ли это?
			fail(e.toString());
		}
 }
 
	@Override
	protected IDatabaseConnection getConnection() throws Exception {
		connectionFactory= new ConnectionFactoryImpl("org.hsqldb.jdbcDriver", "jdbc:hsqldb:file:db/usermanagement", "sa", "");
		return new DatabaseConnection(connectionFactory.createConnection());
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		IDataSet dataSet = new XmlDataSet(getClass().getClassLoader().getResourceAsStream("usersDataSet.xml"));
		return dataSet;
	}

}

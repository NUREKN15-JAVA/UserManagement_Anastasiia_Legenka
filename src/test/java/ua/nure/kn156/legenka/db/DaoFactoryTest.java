package ua.nure.kn156.legenka.db;

import static org.junit.Assert.*;

import org.junit.Test;

public class DaoFactoryTest {
// метод set up
	@Test
	public void testGetUserDao() {
		try {
			DaoFactory daoFactory = DaoFactory.getInstance();
			assertNotNull ("DaoFactory instance is null", daoFactory);
			UserDAO userDAO=daoFactory.getUserDao();
			assertNotNull ("UserDao instance is null", userDAO);
		} catch (RuntimeException e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}

}

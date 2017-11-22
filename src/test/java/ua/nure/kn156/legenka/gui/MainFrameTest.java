package ua.nure.kn156.legenka.gui;

import java.awt.Component;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.mockobjects.dynamic.Mock;

import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.eventdata.JTableMouseEventData;
import junit.extensions.jfcunit.eventdata.MouseEventData;
import junit.extensions.jfcunit.eventdata.StringEventData;
import junit.extensions.jfcunit.finder.AbstractButtonFinder;
import junit.extensions.jfcunit.finder.DialogFinder;
import junit.extensions.jfcunit.finder.NamedComponentFinder;
import ua.nure.kn156.legenka.User;
import ua.nure.kn156.legenka.db.DaoFactory;
import ua.nure.kn156.legenka.db.MockDaoFactory;
import ua.nure.kn156.legenka.util.Messages;

public class MainFrameTest extends JFCTestCase {
	List<User> users;
	private Mock mockUserDao;
	private MainFrame mainFrame;

	protected void setUp() throws Exception {
		super.setUp();
		try {
			Properties properties = new Properties();
			properties.setProperty("dao.factory", MockDaoFactory.class.getName());
			DaoFactory.init(properties);
			mockUserDao = ((MockDaoFactory) DaoFactory.getInstance()).getMockUserDao();
			User expectedUser = new User(new Long(1000), "George", "Bush", new Date());
			users = Collections.singletonList(expectedUser);
			mockUserDao.expectAndReturn("findAll", users);
			setHelper(new JFCTestHelper());
			mainFrame = new MainFrame();
		} catch (Exception e) {
			e.printStackTrace();
		}
		mainFrame.setVisible(true);
	}

	protected void tearDown() throws Exception {
		try {
			mockUserDao.verify();
			mainFrame.setVisible(false);
			getHelper().cleanUp(this);
			super.tearDown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Component find(Class componentClass, String name) {
		Component component;
		NamedComponentFinder finder = new NamedComponentFinder(componentClass, name);
		finder.setWait(1000);
		component = finder.find(mainFrame, 0);
		assertNotNull("Could not find component'" + name + "'", component);
//		 try {
//		 Thread.sleep(1000);
//		 } catch (InterruptedException e) {
//		 // TODO Auto-generated catch block
//		 e.printStackTrace();
//		 }
		return component;
	}

	public void testBrowsePanel() {
		find(JPanel.class, "browsePanel");
		find(JButton.class, "addButton");
		find(JButton.class, "editButton");
		find(JButton.class, "deleteButton");
		find(JButton.class, "detailsButton");
		JTable table = (JTable) find(JTable.class, "userTable");
		assertEquals(3, table.getColumnCount());
		assertEquals(Messages.getString("UserTableModel.id"), table.getColumnName(0));
		assertEquals(Messages.getString("UserTableModel.first_name"), table.getColumnName(1));
		assertEquals(Messages.getString("UserTableModel.last_name"), table.getColumnName(2));
		assertEquals(1, table.getRowCount());
	}

	public void testAddUser() {
		try {
			String firstName = "John";
			String lastName = "Doe";
			Date date = new Date();

			User user = new User(firstName, lastName, date);
			User expectedUser = new User(new Long(1), firstName, lastName, date);
			mockUserDao.expectAndReturn("create", user, expectedUser);
			ArrayList<User> users = new ArrayList<>(this.users);
			users.add(expectedUser);
			mockUserDao.expectAndReturn("findAll", users);

			JTable table = (JTable) find(JTable.class, "userTable");
			assertEquals(1, table.getRowCount());

			JButton addButton = (JButton) find(JButton.class, "addButton");
			getHelper().enterClickAndLeave(new MouseEventData(this, addButton));

			find(JPanel.class, "addPanel");

			fillFields(firstName, lastName, date);
			JButton okButton = (JButton) find(JButton.class, "okButton");
			getHelper().enterClickAndLeave(new MouseEventData(this, okButton));

			find(JPanel.class, "browsePanel");
			table = (JTable) find(JTable.class, "userTable");
			assertEquals(2, table.getRowCount());// 1

			mockUserDao.verify();
		} catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testDeleteUser() {
		try {
			String firstName = "George";
			String lastName = "Bush";
			Date date = new Date();
			User expectedUser = new User(new Long(1000), firstName, lastName, date);
			mockUserDao.expect("delete", expectedUser);

			ArrayList<User> users = new ArrayList<>();
			mockUserDao.expectAndReturn("findAll", users);

			JTable table = (JTable) find(JTable.class, "userTable");
			assertEquals(1, table.getRowCount());
			JButton deleteButton = (JButton) find(JButton.class, "deleteButton");
			getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0, 0, 1));
			getHelper().enterClickAndLeave(new MouseEventData(this, deleteButton));
			(new DialogFinder(Messages.getString("BrowsePanel.message3"))).findAll();
			JButton yesButton = (JButton) (new AbstractButtonFinder("Yes")).find();
			getHelper().enterClickAndLeave(new MouseEventData(this, yesButton));
			find(JPanel.class, "browsePanel");
			table = (JTable) find(JTable.class, "userTable");
			assertEquals(0, table.getRowCount());
			mockUserDao.verify();

		} catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testAddUserCancel() {
		try {
			String firstName = "John";
			String lastName = "Doe";
			Date now = new Date();

			mockUserDao.expectAndReturn("findAll", this.users);

			JTable userTable = (JTable) find(JTable.class, "userTable");
			assertEquals(1, userTable.getRowCount());

			JButton addButton = (JButton) find(JButton.class, "addButton");
			getHelper().enterClickAndLeave(new MouseEventData(this, addButton));

			find(JPanel.class, "addPanel");

			fillFields(firstName, lastName, now);

			JButton cancelButton = (JButton) find(JButton.class, "cancelButton");
			getHelper().enterClickAndLeave(new MouseEventData(this, cancelButton));

			find(JPanel.class, "browsePanel");
			userTable = (JTable) find(JTable.class, "userTable");
			assertEquals(1, userTable.getRowCount());

			mockUserDao.verify();
		} catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testDeleteUserCancel() {

		mockUserDao.expectAndReturn("findAll", this.users);

		JTable userTable = (JTable) find(JTable.class, "userTable");
		assertEquals(1, userTable.getRowCount());

		JButton deleteButton = (JButton) find(JButton.class, "deleteButton");
		getHelper().enterClickAndLeave(new JTableMouseEventData(this, userTable, 0, 0, 1));
		getHelper().enterClickAndLeave(new MouseEventData(this, deleteButton));
		(new DialogFinder(Messages.getString("BrowsePanel.message3"))).findAll();
		JButton noButton = (JButton) (new AbstractButtonFinder("No")).find();
		getHelper().enterClickAndLeave(new MouseEventData(this, noButton));

		find(JPanel.class, "browsePanel");
		userTable = (JTable) find(JTable.class, "userTable");
		assertEquals(1, userTable.getRowCount());
		mockUserDao.verify();
	}

	public void testEditUser() {
		try {

		String firstName = "George";
		String lastName = "Bush";
		Date date = new Date();
		User expectedUser = new User(new Long(1000), firstName, lastName, date);
     	mockUserDao.expect("update", expectedUser);

			mockUserDao.expectAndReturn("findAll", this.users);

			JTable table = (JTable) find(JTable.class, "userTable");
			assertEquals(1, table.getRowCount());
			JButton editButton = (JButton) find(JButton.class, "editButton");
			getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0, 0, 1));
			getHelper().enterClickAndLeave(new MouseEventData(this, editButton));

			find(JPanel.class, "editPanel");

			JTextField firstNameField = (JTextField) find(JTextField.class, "firstNameField");
			JTextField lastNameField = (JTextField) find(JTextField.class, "lastNameField");

			getHelper().sendString(new StringEventData(this, firstNameField, "1"));
			getHelper().sendString(new StringEventData(this, lastNameField, "1"));

			JButton okButton = (JButton) find(JButton.class, "okButton");
			getHelper().enterClickAndLeave(new MouseEventData(this, okButton));

			find(JPanel.class, "browsePanel");
			table = (JTable) find(JTable.class, "userTable");
			assertEquals(1, table.getRowCount());
			mockUserDao.verify();

		} catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testEditUserCancel() {

		try {
			mockUserDao.expectAndReturn("findAll", users);

			JTable table = (JTable) find(JTable.class, "userTable");
			assertEquals(1, table.getRowCount());

			JButton editButton = (JButton) find(JButton.class, "editButton");
			getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0, 0, 1));
			getHelper().enterClickAndLeave(new MouseEventData(this, editButton));

			find(JPanel.class, "editPanel");
			JTextField firstNameField = (JTextField) find(JTextField.class, "firstNameField");
			JTextField lastNameField = (JTextField) find(JTextField.class, "lastNameField");

			getHelper().sendString(new StringEventData(this, firstNameField, "1"));
			getHelper().sendString(new StringEventData(this, lastNameField, "1"));

			JButton cancelButton = (JButton) find(JButton.class, "cancelButton");
			getHelper().enterClickAndLeave(new MouseEventData(this, cancelButton));

			find(JPanel.class, "browsePanel");
			table = (JTable) find(JTable.class, "userTable");
			assertEquals(1, table.getRowCount());
			mockUserDao.verify();

		} catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testDetailsUser() {
		try {
			mockUserDao.expectAndReturn("findAll", users);

			JTable table = (JTable) find(JTable.class, "userTable");
			assertEquals(1, table.getRowCount());

			JButton detailsButton = (JButton) find(JButton.class, "detailsButton");
			getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0, 0, 1));
			getHelper().enterClickAndLeave(new MouseEventData(this, detailsButton));

			find(JPanel.class, "detailsPanel");

			JTextField fullNameField = (JTextField) find(JTextField.class, "fullNameField");
			JTextField ageField = (JTextField) find(JTextField.class, "ageField");
			
			assertEquals("George Bush", fullNameField.getText());
			assertEquals("0", ageField.getText());

			JButton okButton = (JButton) find(JButton.class, "okButton");
			getHelper().enterClickAndLeave(new MouseEventData(this, okButton));

			find(JPanel.class, "browsePanel");
			table = (JTable) find(JTable.class, "userTable");
			assertEquals(1, table.getRowCount());
			mockUserDao.verify();

		} catch (Exception e) {
			fail(e.toString());
		}
	}

	private void fillFields(String firstName, String lastName, Date now) {
		JTextField firstNameField = (JTextField) find(JTextField.class, "firstNameField");
		JTextField lastNameField = (JTextField) find(JTextField.class, "lastNameField");
		JTextField dateOfBirthField = (JTextField) find(JTextField.class, "dateOfBirthField");
		getHelper().sendString(new StringEventData(this, firstNameField, firstName));
		getHelper().sendString(new StringEventData(this, lastNameField, lastName));
		DateFormat formatter = DateFormat.getDateInstance();
		String date = formatter.format(now);
		getHelper().sendString(new StringEventData(this, dateOfBirthField, date));
	}

}

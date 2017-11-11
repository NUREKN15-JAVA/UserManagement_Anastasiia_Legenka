package ua.nure.kn156.legenka.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import javax.swing.JOptionPane;

import ua.nure.kn156.legenka.User;
import ua.nure.kn156.legenka.db.DatabaseException;
import ua.nure.kn156.legenka.util.Messages;

public class EditPanel extends AddPanel {
	private User user;

	public EditPanel(MainFrame parent) {
		super(parent);
		setName(Messages.getString("EditPanel.0")); //$NON-NLS-1$
	}

	public void setUserData(User user) {
		this.user = user;
		getFirstNameField().setText(user.getFirstName());
		getLastNameField().setText(user.getLastName());
		DateFormat format = DateFormat.getDateInstance();
		getDateOfBirthField().setText(format.format(user.getDateOfBirth()));
	}

	public void actionPerformed(ActionEvent e) {
		if (Messages.getString("EditPanel.1").equalsIgnoreCase(e.getActionCommand())) { //$NON-NLS-1$
			user.setFirstName(getFirstNameField().getText());
			user.setLastName(getLastNameField().getText());
			try {
				DateFormat format = DateFormat.getDateInstance();
				Date date = format.parse(getDateOfBirthField().getText());
				user.setDateOfBirth(date);
				parent.getDao().update(user);
			} catch (ParseException e1) {
				getDateOfBirthField().setBackground(Color.RED);
			} catch (DatabaseException e1) {
				JOptionPane.showMessageDialog(this, e1.getMessage(), Messages.getString("EditPanel.error"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
			}
		}
		clearFields();
		this.setVisible(false);
		parent.showBrowsePanel();
	}

}

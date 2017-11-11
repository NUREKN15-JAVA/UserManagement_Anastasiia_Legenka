package ua.nure.kn156.legenka.gui;

import java.awt.event.ActionEvent;
import java.text.DateFormat;

import ua.nure.kn156.legenka.User;
import ua.nure.kn156.legenka.util.Messages;

	public class DetailsPanel extends AddPanel {

		private User user;
		

		public DetailsPanel(MainFrame mainFrame) {
			super(mainFrame);
			setName("detailsPanel"); //$NON-NLS-1$
		}

		public void actionPerformed (ActionEvent e) {
	        if ("ok".equalsIgnoreCase(e.getActionCommand())) { //$NON-NLS-1$
	            user.setFirstName(getFirstNameField().getText());
	            user.setLastName(getLastNameField().getText());       
	        }
	        clearFields();
			this.setVisible(false);
			parent.showBrowsePanel();
	    }

	    public void setUserData(User user) {
	        DateFormat format = DateFormat.getDateInstance();
	        this.user = user;
	        getFirstNameField().setText(user.getFirstName());
	        getFirstNameField().setEditable(false);
	        getLastNameField().setText(user.getLastName());
	        getLastNameField().setEditable(false);
	        getDateOfBirthField().setText(format.format(user.getDateOfBirth()));
	        getDateOfBirthField().setEditable(false);
	    }

		
	}

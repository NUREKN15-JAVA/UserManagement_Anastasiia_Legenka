package ua.nure.kn156.legenka.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ua.nure.kn156.legenka.User;
import ua.nure.kn156.legenka.util.Messages;

	public class DetailsPanel extends JPanel implements ActionListener {

		protected MainFrame parent;
		private JPanel fieldPanel;
		private JPanel buttonPanel;
		private JTextField getFullNameField;
		private JTextField ageField;
		private JButton okButton;
		

		public DetailsPanel(MainFrame parent) {
			this.parent = parent;
			initialize();
		}

		private void initialize() {
			this.setName("detailsPanel"); //$NON-NLS-1$
			this.setLayout(new BorderLayout());
			this.add(getFieldPanel(), BorderLayout.NORTH);
			this.add(getButtonPanel(), BorderLayout.SOUTH);
		}
	    
		private JTextField getAgeField() {

			if (ageField == null) {
				ageField = new JTextField();
				ageField.setName("ageField"); //$NON-NLS-1$
			}
			return ageField;
		}

		
		private JPanel getFieldPanel(){
			if(fieldPanel == null){
				fieldPanel = new JPanel();
				fieldPanel.setLayout(new GridLayout(3,2));
				addLabeledField(fieldPanel, Messages.getString("DetailsPanel.full_name"), getFullNameField());   //$NON-NLS-1$
				addLabeledField(fieldPanel,Messages.getString("DetailsPanel.age"), getAgeField());   //$NON-NLS-1$
			}
			return fieldPanel;
		}
		
		protected JTextField getFullNameField() {
			if(getFullNameField == null){
				getFullNameField = new JTextField();
				getFullNameField.setName("fullNameField");  //$NON-NLS-1$
			}
			return getFullNameField;
		}
		
		private void addLabeledField(JPanel panel, String labelText, JTextField textField) {
			JLabel label = new JLabel(labelText);
			label.setLabelFor(textField);
			panel.add(label);
			panel.add(textField);
		}
		private JPanel getButtonPanel(){
			if(buttonPanel == null){
				buttonPanel = new JPanel();
				buttonPanel.add(getOkButton(), null);
			}
			return buttonPanel;
		}

		private JButton getOkButton() {
			if (okButton == null){
				okButton = new JButton();
				okButton.setText("Ok");   //$NON-NLS-1$
				okButton.setName("okButton"); //$NON-NLS-1$
				okButton.setActionCommand("ok");  //$NON-NLS-1$
				okButton.addActionListener(this);
			}
			return okButton;
		}

	    public void setUserData(User user) {
	        getFullNameField().setText(user.getFirstName()+" "+user.getLastName()); //$NON-NLS-1$
	        getFullNameField().setEditable(false);
	        getAgeField().setText(String.valueOf(user.getAge()));
	        getAgeField().setEditable(false);
	    }

		@Override
		public void actionPerformed(ActionEvent e) {
			if("ok".equalsIgnoreCase(e.getActionCommand())){  //$NON-NLS-1$
				this.setVisible(false);
				parent.showBrowsePanel();
				}
		}

		
	}

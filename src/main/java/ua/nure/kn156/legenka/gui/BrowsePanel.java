package ua.nure.kn156.legenka.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import ua.nure.kn156.legenka.User;
import ua.nure.kn156.legenka.db.DatabaseException;
import ua.nure.kn156.legenka.util.Messages;

public class BrowsePanel extends JPanel implements ActionListener {

	private MainFrame parent;
	private JPanel buttonPanel;
	private JButton addButton;
	private JButton detailsButton;
	private JButton deleteButton;
	private JButton editButton;
	private JScrollPane tablePanel;
	private JTable userTable;

	public BrowsePanel(MainFrame mainFrame) {
		parent = mainFrame;
		initialize();
	}

	private void initialize() {
		this.setName("browsePanel"); //$NON-NLS-1$
		this.setLayout(new BorderLayout());
		this.add(getTablePanel(), BorderLayout.CENTER);
		this.add(getButtonsPanel(), BorderLayout.SOUTH);
	}

	private JPanel getButtonsPanel() {
		if (buttonPanel == null) {
			buttonPanel = new JPanel();
			buttonPanel.add(getAddButton(), null);
			buttonPanel.add(getEditButton(), null);
			buttonPanel.add(getDeleteButton(), null);
			buttonPanel.add(getDetailsButton(), null);
		}
		return buttonPanel;
	}

	private JButton getDetailsButton() {
		if (detailsButton == null) {
			detailsButton = new JButton();
			detailsButton.setText(Messages.getString("BrowsePanel.details")); //$NON-NLS-1$
			detailsButton.setName("detailsButton"); //$NON-NLS-1$
			detailsButton.setActionCommand("details"); //$NON-NLS-1$
			detailsButton.addActionListener(this);
		}
		return detailsButton;
	}

	private JButton getDeleteButton() {
		if (deleteButton == null) {
			deleteButton = new JButton();
			deleteButton.setText(Messages.getString("BrowsePanel.delete")); //$NON-NLS-1$
			deleteButton.setName("deleteButton"); //$NON-NLS-1$
			deleteButton.setActionCommand("delete"); //$NON-NLS-1$
			deleteButton.addActionListener(this);
		}
		return deleteButton;
	}

	private JButton getEditButton() {
		if (editButton == null) {
			editButton = new JButton();
			editButton.setText(Messages.getString("BrowsePanel.edit")); //$NON-NLS-1$
			editButton.setName("editButton"); //$NON-NLS-1$
			editButton.setActionCommand("edit"); //$NON-NLS-1$
			editButton.addActionListener(this);
		}
		return editButton;
	}

	private JButton getAddButton() {
		if (addButton == null) {
			addButton = new JButton();
			addButton.setText(Messages.getString("BrowsePanel.add")); //$NON-NLS-1$
			addButton.setName("addButton"); //$NON-NLS-1$
			addButton.setActionCommand("add"); //$NON-NLS-1$
			addButton.addActionListener(this);
		}
		return addButton;
	}

	private JScrollPane getTablePanel() {
		if (tablePanel == null) {
			tablePanel = new JScrollPane(getUserTable());
		}
		return tablePanel;
	}

	private JTable getUserTable() {
		if (userTable == null) {
			userTable = new JTable();
			userTable.setName("userTable"); //$NON-NLS-1$
		}
		return userTable;
	}

	public void initTable() {
		UserTableModel model;
		try {
			model = new UserTableModel(parent.getDao().findAll());
		} catch (DatabaseException e) {
			model = new UserTableModel(new ArrayList());
			JOptionPane.showMessageDialog(this, e.getMessage(), Messages.getString("BrowsePanel.message"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
		}
		getUserTable().setModel(model);
	}

	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		if ("add".equalsIgnoreCase(actionCommand)) { //$NON-NLS-1$
			this.setVisible(false);
			parent.showAddPanel();
		} else if ("edit".equalsIgnoreCase(e.getActionCommand())) { //$NON-NLS-1$
			int selectedRow = userTable.getSelectedRow();
			if (selectedRow == -1) {
				JOptionPane.showMessageDialog(this, Messages.getString("BrowsePanel.select_user"), Messages.getString("BrowsePanel.message1"), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
				return;
			}
			User user = ((UserTableModel) userTable.getModel()).getUser(selectedRow);
			this.setVisible(false);
			parent.showEditPanel(user);
		} else if ("delete".equalsIgnoreCase(actionCommand)) { //$NON-NLS-1$
			int selectedRow = userTable.getSelectedRow();
			if (selectedRow == -1) {
				JOptionPane.showMessageDialog(this, Messages.getString("BrowsePanel.select_user"), Messages.getString("BrowsePanel.message2"), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
				return;
			}
			if (JOptionPane.showConfirmDialog(parent, Messages.getString("BrowsePanel.confirmation"), //$NON-NLS-1$
					Messages.getString("BrowsePanel.message3"), JOptionPane.YES_NO_OPTION, //$NON-NLS-1$
					JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
				try {
					parent.getDao().delete(((UserTableModel) userTable.getModel()).getUser(selectedRow));
				} catch (DatabaseException e1) {
					JOptionPane.showMessageDialog(this, e1.getMessage(), Messages.getString("BrowsePanel.message4"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
				}
			}
			initTable();
			return;
		}
		else if("details".equalsIgnoreCase(actionCommand)){ //$NON-NLS-1$
			int selectedRow = userTable.getSelectedRow();
            if (selectedRow == -1) {
            	JOptionPane.showMessageDialog(this, Messages.getString("BrowsePanel.choose_user"), Messages.getString("BrowsePanel.message5"), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
            	return;//$NON-NLS-1$ //$NON-NLS-2$
            }
            User user = ((UserTableModel) userTable.getModel()).getUser(selectedRow);
         	this.setVisible(false);
			parent.showDetailsPanel(user);
            }
}
		}
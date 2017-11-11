package ua.nure.kn156.legenka.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ua.nure.kn156.legenka.User;
import ua.nure.kn156.legenka.db.DaoFactory;
import ua.nure.kn156.legenka.db.UserDAO;
import ua.nure.kn156.legenka.util.Messages;

public class MainFrame extends JFrame {

	private static final int FRAME_WIDTH = 800;
	private static final int FRAME_HEIGHT = 600;
	private JPanel contentPanel;
	private JPanel browsePanel;
	private AddPanel addPanel;
	private UserDAO dao;
	private EditPanel editPanel;
	private DetailsPanel detailsPanel;

	public MainFrame() {
		super();
		dao = DaoFactory.getInstance().getUserDao();
		initialize();
	}

	public UserDAO getDao() {
		return dao;
	}

	private void initialize() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setTitle(Messages.getString("MainFrame.user_management")); //$NON-NLS-1$
		this.setContentPane(getContentPanel());
		
	}

	private JPanel getContentPanel() {
		if (contentPanel == null) {
			contentPanel = new JPanel();
			contentPanel.setLayout(new BorderLayout());
			contentPanel.add(getBrowsePanel(), BorderLayout.CENTER);
		}
		return contentPanel;
	}

	private JPanel getBrowsePanel() {
		if (browsePanel == null) {
			browsePanel = new BrowsePanel(this);
		}
		((BrowsePanel) browsePanel).initTable();
		return browsePanel;
	}

	public static void main(String[] args) {
		MainFrame frame = new MainFrame();
		frame.setVisible(true);
	}

	public void showAddPanel() {
		showPanel(getAddPanel());

	}

	public void showBrowsePanel() {
		showPanel(getBrowsePanel());

	}

	private void showPanel(JPanel panel) {
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setVisible(true);
		panel.repaint();
	}

	private AddPanel getAddPanel() {
		if (addPanel == null) {
			addPanel = new AddPanel(this);
		}
		return addPanel;
	}

	public void showEditPanel(User user) {
		getEditPanel().setUserData(user);
		showPanel(getEditPanel());
	}

	private EditPanel getEditPanel() {
		if (editPanel == null) {
			editPanel = new EditPanel(this);
		}
		return editPanel;
	}

	public void showDetailsPanel(User user) {
		getDetailsPanel().setUserData(user);
		showPanel(getDetailsPanel());
		
	}

	private DetailsPanel getDetailsPanel() {
		if(detailsPanel == null){
			detailsPanel = new DetailsPanel(this);
		}
		return detailsPanel;
	}

}

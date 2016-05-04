package start;

import javax.swing.*;
import java.awt.*;


public class LoginForm extends JDialog {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JTextField jLoginField = null;

	private JPasswordField jPasswordField = null;

	private JButton jButtonOK = null;

	private JLabel jLabelLogin = null;

	private JLabel jLabelPassword = null;
	
	

	/**
	 * @param owner
	 */
	public LoginForm() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(375, 95);
		this.setTitle("Доступ к САБ \"Грант\"");
		this.setContentPane(getJContentPane());
		this.setLocation((Global.getSettings().getScreenSize().width-this.getSize().width)/2, 
						 (Global.getSettings().getScreenSize().height-this.getSize().height)/2);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabelPassword = new JLabel();
			jLabelPassword.setBounds(new Rectangle(158, 15, 57, 16));
			jLabelPassword.setText("Пароль:");
			jLabelLogin = new JLabel();
			jLabelLogin.setBounds(new Rectangle(21, 15, 49, 16));
			jLabelLogin.setText("Логин:");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJLoginField(), null);
			jContentPane.add(getJPasswordField(), null);
			jContentPane.add(getJButtonOK(), null);
			jContentPane.add(jLabelLogin, null);
			jContentPane.add(jLabelPassword, null);
			this.getRootPane().setDefaultButton(jButtonOK);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jLoginField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJLoginField() {
		if (jLoginField == null) {
			jLoginField = new JTextField();
			jLoginField.setBounds(new Rectangle(19, 35, 120, 22));
		}
		return jLoginField;
	}

	/**
	 * This method initializes jPasswordField	
	 * 	
	 * @return javax.swing.JPasswordField	
	 */
	private JPasswordField getJPasswordField() {
		if (jPasswordField == null) {
			jPasswordField = new JPasswordField();
			jPasswordField.setBounds(new Rectangle(157, 35, 118, 22));
		}
		return jPasswordField;
	}

	/**
	 * This method initializes jButtonOK	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonOK() {
		if (jButtonOK == null) {
			jButtonOK = new JButton();
			jButtonOK.setBounds(new Rectangle(295, 35, 56, 22));
			jButtonOK.setText("Ok");			
			jButtonOK.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					closeLoginForm();
					Global.getSettings().setDBlogin(getLogin());
					Global.getSettings().setDBpassword(getPassword());
					try {
						Global.setConnection(Global.getSettings().getDBconnectionURI(), 
								 Global.getSettings().getDBlogin(), 
								 Global.getSettings().getDBpassword());
						if (Global.getConnection()!=null) {
							Global.getUserList().createList();
							Global.getSettings().setUserDepartment(Global.getUserList().getDepcode(getLogin()));
							StartForm startform = new StartForm();
							startform.setVisible(true);
						}	
						else {
							Global.errorBox("Неправильный логин или пароль!");							
							System.exit(0);
						}	
					} catch (Exception e1) {
						
					}
					
				}
			});
		}
		return jButtonOK;
	}
	
	private void closeLoginForm(){
		this.setVisible(false);
	}
	
	public String getLogin(){
		return jLoginField.getText();
	}
	
	public String getPassword(){
		return jPasswordField.getText();
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"

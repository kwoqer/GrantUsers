package start;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import start.Global;
import work.*;


public class StartForm extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JComboBox jCBUsers = null;
	private JLabel jLblUsers = null;
	private JLabel jLblAction = null;
	private JComboBox jCBActions = null;
	private JButton jBtnRun = null;
	private JButton jBtnExit = null;

	/**
	 * This method initializes 
	 * 
	 */
	public StartForm() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(388, 202));
        this.setContentPane(getJContentPane());
        this.setTitle("Пользователи САБ \"Грант\"");
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
			jLblAction = new JLabel();
			jLblAction.setBounds(new Rectangle(16, 81, 150, 15));
			jLblAction.setText("Действие");
			jLblUsers = new JLabel();
			jLblUsers.setBounds(new Rectangle(16, 21, 150, 15));
			jLblUsers.setText("Пользователь");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJCBUsers(), null);
			jContentPane.add(jLblUsers, null);
			jContentPane.add(jLblAction, null);
			jContentPane.add(getJCBActions(), null);
			jContentPane.add(getJBtnRun(), null);
			jContentPane.add(getJBtnExit(), null);
			this.getRootPane().setDefaultButton(jBtnRun);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jCBUsers	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJCBUsers() {
		if (jCBUsers == null) {
			jCBUsers = new JComboBox();
			jCBUsers.setBounds(new Rectangle(16, 44, 351, 22));
		}
		//UserList ul = new UserList();
		//ul.createList();
		for (Iterator iter = Global.getUserList().iterator(); iter.hasNext();) {
			User u = (User)iter.next();
			String d = Global.getSettings().getUserDepartment(); 
			if ((d.equalsIgnoreCase("AAU")) ||
				(d.equalsIgnoreCase("AAO")) ||				
				(d.equalsIgnoreCase("AAM") && (u.getDepcode().equalsIgnoreCase("AAN"))) ||
				((d.equalsIgnoreCase("AAN") && (u.getDepcode().equalsIgnoreCase("AAM")))) ||
				((d.equalsIgnoreCase("AAB")) && 
						((u.getDepcode().equalsIgnoreCase("AAR")) || 
						 (u.getDepcode().equalsIgnoreCase("AAP")) || 
						 (u.getDepcode().equalsIgnoreCase("AAT")))
						 ) ||
				(Global.getSettings().getDBlogin().equalsIgnoreCase("AUDITOR")) ||
				(Global.getSettings().getDBlogin().equalsIgnoreCase("GRAND2")) ||
				(d.equalsIgnoreCase(u.getDepcode()))
				) {
			jCBUsers.addItem(u.getFormatFullName());
			}
		}
		return jCBUsers;
	}

	/**
	 * This method initializes jCBActions	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJCBActions() {
		if (jCBActions == null) {
			jCBActions = new JComboBox();
			jCBActions.setBounds(new Rectangle(16, 105, 350, 22));
			jCBActions.addItem("Заявка на предоставление доступа к счетам");
			jCBActions.addItem("Заявка на закрытие доступа к счетам");
			jCBActions.addItem("Заявка на предоставление доступа к меню");
			jCBActions.addItem("Заявка на предоставление права подписи на узлах");
			
		}
		return jCBActions;
	}

	/**
	 * This method initializes jBtnRun	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJBtnRun() {
		if (jBtnRun == null) {
			jBtnRun = new JButton();
			jBtnRun.setBounds(new Rectangle(29, 135, 133, 27));
			jBtnRun.setText("Выполнить");
			jBtnRun.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					process(jCBUsers.getSelectedItem().toString(), jCBActions.getSelectedIndex());					
				}
			});
		}
		return jBtnRun;
	}

	/**
	 * This method initializes jBtnExit	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJBtnExit() {
		if (jBtnExit == null) {
			jBtnExit = new JButton();
			jBtnExit.setBounds(new Rectangle(270, 135, 83, 27));
			jBtnExit.setText("Выход");
			jBtnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.exit(0);
				}
			});
		}
		return jBtnExit;
	}
	
	private void process(String fnuser, int iaction){
		WordInstance wo = Global.getWordObject();
		User user = Global.getUserList().getUserByFullName(fnuser);
		
		switch (iaction) {
		case 0: {// Заявка на получение доступа к счетам	
			Application2 app2 = new Application2(user);
			wo.open(Global.getSettings().getTemplatesPath()+System.getProperty("file.separator")+"app2.rtf");									
			app2.arrangeMasks();
			wo.printToBookmark("AddAccess", "+");
			wo.printToBookmark("Department",app2.getDepartmentField());
			wo.printToBookmark("Job",app2.getJobField());
			wo.printToBookmark("Name", app2.getNameField());
			wo.printToBookmark("Login", app2.getLoginField());
			wo.printToBookmark("TabNum", app2.getTabnumField());
			wo.printToBookmark("Room", app2.getRoomField());
			wo.printToBookmark("Phone", app2.getPhoneField());
			wo.printToBookmark("ChiefSignature",app2.getFullChiefSignature());
			wo.goToBookmark("Table");
			int nr = app2.differenceRows();
			if (nr > 0){		
				if (nr > 20)
					wo.goToBookmark("EndOfTable");
				wo.AddRows(nr);
				wo.goToBookmark("Table");
			}
			
			for (Iterator iter = app2.iterator(); iter.hasNext();) {
				Row row = (Row) iter.next();				
					wo.printText(row.getA());
					wo.moveRight();
					wo.printText(row.getS());
					wo.moveRight();
					wo.printText(row.getH());
					wo.moveRight();
					wo.printText(row.getD());
					wo.moveRight();
					wo.printText(row.getK());
					wo.moveRight();
					wo.printText(row.getP());
					wo.moveRight();
					wo.printText(row.getO());
					wo.moveRight();		
					wo.printText(row.getR());
					wo.moveRight();
				
			}
			wo.saveAs(Global.getSettings().getRTFPath()
					+System.getProperty("file.separator")
					+"app2_"+Global.translit(app2.getUserLastName())+".rtf");
			break;
		}
		case 1:{ // Заявка на отключение доступа к счетам
			Application2 app2 = new Application2(user);	
			wo.open(Global.getSettings().getTemplatesPath()+System.getProperty("file.separator")+"app2.rtf");									
			wo.printToBookmark("TerminateAccess", "+");
			wo.printToBookmark("Department",app2.getDepartmentField());
			wo.printToBookmark("Job",app2.getJobField());
			wo.printToBookmark("Name", app2.getNameField());
			wo.printToBookmark("Login", app2.getLoginField());
			wo.printToBookmark("TabNum", app2.getTabnumField());
			wo.printToBookmark("Room", app2.getRoomField());
			wo.printToBookmark("Phone", app2.getPhoneField());
			wo.printToBookmark("ChiefSignature",app2.getFullChiefSignature());
			wo.saveAs(Global.getSettings().getRTFPath()
					+System.getProperty("file.separator")
					+"app2_"+Global.translit(app2.getUserLastName())+".rtf");
			
			break;
		}
		case 2:{ // Заявка на получение доступа к меню
			Application3m app3m = new Application3m(user);
			wo.open(Global.getSettings().getTemplatesPath()+System.getProperty("file.separator")+"app3m.rtf");
			app3m.createRows();
			wo.printToBookmark("AddAccess", "+");
			wo.printToBookmark("Department",app3m.getDepartmentField());
			wo.printToBookmark("Job",app3m.getJobField());
			wo.printToBookmark("Name", app3m.getNameField());
			wo.printToBookmark("Login", app3m.getLoginField());
			wo.printToBookmark("TabNum", app3m.getTabnumField());
			wo.printToBookmark("Room", app3m.getRoomField());
			wo.printToBookmark("Phone", app3m.getPhoneField());
			wo.printToBookmark("ChiefSignature",app3m.getFullChiefSignature());
			wo.goToBookmark("Table");
			for (Iterator iter = app3m.iterator(); iter.hasNext();) {
				String row = (String)iter.next();				
				if (row.substring(row.length()-1).equals(":")){
					wo.boldFont();
					wo.printText(row);
					wo.boldFont();
					wo.pressEnter();
				}else{
					wo.printText(row);
					wo.pressEnter();
				}
			}
			wo.saveAs(Global.getSettings().getRTFPath()
					+System.getProperty("file.separator")
					+"app3m_"+Global.translit(app3m.getUserLastName())+".rtf");
			break;
		}
		case 3:{ // Заявка на получение доступа к узлам
			Application3n app3n = new Application3n(user);
			wo.open(Global.getSettings().getTemplatesPath()+System.getProperty("file.separator")+"app3n.rtf");
			app3n.createRows();
			wo.printToBookmark("AddAccess", "+");
			wo.printToBookmark("Department",app3n.getDepartmentField());
			wo.printToBookmark("Job",app3n.getJobField());
			wo.printToBookmark("Name", app3n.getNameField());
			wo.printToBookmark("Login", app3n.getLoginField());
			wo.printToBookmark("TabNum", app3n.getTabnumField());
			wo.printToBookmark("Room", app3n.getRoomField());
			wo.printToBookmark("Phone", app3n.getPhoneField());
			wo.printToBookmark("ChiefSignature",app3n.getFullChiefSignature());
			wo.goToBookmark("Table");
			for (Iterator iter = app3n.iterator(); iter.hasNext();) {
				String row = (String)iter.next();				
				wo.printText(row);
				wo.pressEnter();
			}
			wo.saveAs(Global.getSettings().getRTFPath()
					+System.getProperty("file.separator")
					+"app3n_"+Global.translit(app3n.getUserLastName())+".rtf");
			break;
		}
		}
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"

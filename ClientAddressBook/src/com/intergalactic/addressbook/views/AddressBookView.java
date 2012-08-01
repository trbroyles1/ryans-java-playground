package com.intergalactic.addressbook.views;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import com.intergalactic.addressbook.beans.Person;
import com.intergalactic.addressbook.models.PersonsModel;
import com.jgoodies.binding.adapter.Bindings;
import com.jgoodies.binding.beans.BeanAdapter;
import com.jgoodies.binding.beans.PropertyConnector;
import com.jgoodies.binding.value.ValueModel;

public class AddressBookView {

	private JFrame frmAddressBook;
	private JTable table;
	private JTextField titleText;
	private JTextField firstNameText;
	private JTextField lastNameText;
	JButton btnAddPerson;
	JButton btnSave;
	JButton btnDelete;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddressBookView window = new AddressBookView();
					window.frmAddressBook.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AddressBookView() {
		initialize();
		setUpBindings();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frmAddressBook = new JFrame();
		frmAddressBook.setTitle("Address Book");
		frmAddressBook.setBounds(100, 100, 708, 455);
		frmAddressBook.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JScrollPane scrollPane = new JScrollPane();
		
		table = new JTable();
				
		scrollPane.setViewportView(table);
		
		JToolBar toolBar = new JToolBar();
		
		titleText = new JTextField();
		titleText.setColumns(10);
		
		firstNameText = new JTextField();
		firstNameText.setColumns(10);
		
		JLabel lblTitle = new JLabel("Title");
		
		JLabel lblFirstName = new JLabel("First Name");
		
		JLabel lblLastName = new JLabel("Last Name");
		
		lastNameText = new JTextField();
		lastNameText.setColumns(10);
		GroupLayout groupLayout = new GroupLayout(frmAddressBook.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 692, Short.MAX_VALUE)
				.addComponent(toolBar, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 692, Short.MAX_VALUE)
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblTitle)
					.addGap(18)
					.addComponent(titleText, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
					.addGap(35)
					.addComponent(lblFirstName)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(firstNameText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblLastName)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lastNameText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(258, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 255, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(toolBar, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTitle)
						.addComponent(titleText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblFirstName)
						.addComponent(firstNameText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblLastName)
						.addComponent(lastNameText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(81, Short.MAX_VALUE))
		);
		
		JButton btnNewButton = new JButton("Details");
		toolBar.add(btnNewButton);
		
		btnAddPerson = new JButton("Add Person");
		toolBar.add(btnAddPerson);
		
		btnSave = new JButton("Save");
		toolBar.add(btnSave);
		
		btnDelete = new JButton("Delete");
		toolBar.add(btnDelete);
		frmAddressBook.getContentPane().setLayout(groupLayout);
		frmAddressBook.getContentPane().setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{table, scrollPane, toolBar, btnNewButton}));
		
		JMenuBar menuBar = new JMenuBar();
		frmAddressBook.setJMenuBar(menuBar);
		
		JMenu mnApplication = new JMenu("Application");
		menuBar.add(mnApplication);
		
		JMenuItem mntmFile = new JMenuItem("Exit");
		mnApplication.add(mntmFile);
	}
	
/**
 * Perform initial bindings	
 */
	private void setUpBindings(){
		final PersonsModel pm = new PersonsModel(); //create an initial PersonsModel view model
		BeanAdapter<PersonsModel> ba = new BeanAdapter<PersonsModel>(pm,true); //create a BeanAdapter for PersonsModel to connect properties
		BeanAdapter<Person> pa = new BeanAdapter<Person>(new Person(),true); //Create a BeanAdapter for Persons to connect properties of persons
		
		table.addPropertyChangeListener(new listen());
		//wire up table from the adapter 
		ValueModel tmModel = ba.getValueModel("tableModel");
		PropertyConnector.connectAndUpdate(tmModel, table, "model");;

		//Wire up the person bean adapter from the persons model
		PropertyConnector.connect(pa, "bean", pm, "currentPerson");
		
		ValueModel titleModel = pa.getValueModel("title");
		ValueModel firstNameModel = pa.getValueModel("firstName");
		ValueModel lastNameModel = pa.getValueModel("lastName");
		Bindings.bind(titleText,titleModel);
		Bindings.bind(firstNameText, firstNameModel);
		Bindings.bind(lastNameText, lastNameModel);

		//wire up mouse clicks
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				pm.setSelectionIndex(table.getSelectedRow());
			}
		});

		btnAddPerson.setAction(pm.getExecuteAddPerson());
		btnSave.setAction(pm.getExecuteSavePerson());
		btnDelete.setAction(pm.getExecuteDeletePerson());
	}
	private class listen implements PropertyChangeListener{

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			System.out.println(String.format("%s changed", evt.getPropertyName()));
			
		}
		
	}
}

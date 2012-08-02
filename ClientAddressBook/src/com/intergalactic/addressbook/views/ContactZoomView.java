package com.intergalactic.addressbook.views;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JList;
import javax.swing.JFormattedTextField;
import javax.swing.JEditorPane;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JScrollPane;

import com.intergalactic.addressbook.Contact;
import com.intergalactic.addressbook.beans.Address;
import com.intergalactic.addressbook.beans.Person;
import com.intergalactic.addressbook.models.ContactZoomPresentationModel;
import com.intergalactic.addressbook.services.ContactZoomService;
import com.jgoodies.binding.adapter.Bindings;
import com.jgoodies.binding.beans.BeanAdapter;
import com.jgoodies.binding.beans.PropertyAdapter;
import com.jgoodies.binding.beans.PropertyConnector;
import com.jgoodies.binding.value.ValueModel;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.SwingConstants;

public class ContactZoomView extends JDialog implements ContactZoomService {
	private JTextField txtNoteText;
	private JTable tableAddresses;
	JLabel lblContactPersonName;
	private JTextField txtLine1;
	private JTextField txtLine2;
	private JTextField txtCity;
	private JTextField txtStateProv;
	private JTextField txtZip;
	private JTextField txtCountry;
	
	JButton btnAddaddress;
	JButton btnSaveaddress;
	JButton btnDeleteaddress;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ContactZoomView dialog = new ContactZoomView();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public ContactZoomView() {

		setTitle("Contact Zoom");
		setBounds(100, 100, 788, 473);
		getContentPane().setLayout(null);
		
		lblContactPersonName = new JLabel("Contact Person Name");
		lblContactPersonName.setBounds(10, 0, 256, 39);
		lblContactPersonName.setFont(new Font("Tahoma", Font.PLAIN, 22));
		getContentPane().add(lblContactPersonName);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Addresses", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(0, 34, 772, 216);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Selected Address", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(391, 11, 371, 196);
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		JToolBar toolBar_1 = new JToolBar();
		toolBar_1.setBounds(6, 16, 359, 24);
		panel_2.add(toolBar_1);
		
		btnAddaddress = new JButton("AddAddress");
		toolBar_1.add(btnAddaddress);
		
		btnSaveaddress = new JButton("SaveAddress");
		toolBar_1.add(btnSaveaddress);
		
		btnDeleteaddress = new JButton("DeleteAddress");
		toolBar_1.add(btnDeleteaddress);
		
		JLabel lblLine = new JLabel("Line 1");
		lblLine.setHorizontalAlignment(SwingConstants.TRAILING);
		lblLine.setBounds(16, 51, 35, 14);
		panel_2.add(lblLine);
		
		txtLine1 = new JTextField();
		txtLine1.setBounds(56, 48, 305, 20);
		panel_2.add(txtLine1);
		txtLine1.setColumns(10);
		
		JLabel lblLine_1 = new JLabel("Line 2");
		lblLine_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblLine_1.setBounds(16, 77, 35, 14);
		panel_2.add(lblLine_1);
		
		txtLine2 = new JTextField();
		txtLine2.setBounds(56, 74, 305, 20);
		panel_2.add(txtLine2);
		txtLine2.setColumns(10);
		
		JLabel lblCity = new JLabel("City");
		lblCity.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCity.setBounds(16, 102, 35, 14);
		panel_2.add(lblCity);
		
		txtCity = new JTextField();
		txtCity.setBounds(56, 99, 149, 20);
		panel_2.add(txtCity);
		txtCity.setColumns(10);
		
		JLabel lblStateProv = new JLabel("State / Prov");
		lblStateProv.setHorizontalAlignment(SwingConstants.TRAILING);
		lblStateProv.setBounds(215, 102, 66, 14);
		panel_2.add(lblStateProv);
		
		txtStateProv = new JTextField();
		txtStateProv.setBounds(284, 99, 77, 20);
		panel_2.add(txtStateProv);
		txtStateProv.setColumns(10);
		
		JLabel lblPostalCode = new JLabel("Zip");
		lblPostalCode.setHorizontalAlignment(SwingConstants.TRAILING);
		lblPostalCode.setBounds(16, 127, 35, 14);
		panel_2.add(lblPostalCode);
		
		txtZip = new JTextField();
		txtZip.setBounds(56, 124, 75, 20);
		panel_2.add(txtZip);
		txtZip.setColumns(10);
		
		JLabel lblCountry = new JLabel("Country");
		lblCountry.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCountry.setBounds(141, 127, 46, 14);
		panel_2.add(lblCountry);
		
		txtCountry = new JTextField();
		txtCountry.setBounds(197, 124, 164, 20);
		panel_2.add(txtCountry);
		txtCountry.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 24, 377, 180);
		panel.add(scrollPane);
		
		tableAddresses = new JTable();
		tableAddresses.setModel(new DefaultTableModel(
			new Object[][] {
				{"Ryan", "Greenville"},
				{"Jaclyn", "ca"},
			},
			new String[] {
				"First Name", "Last Address"
			}
		));
		scrollPane.setViewportView(tableAddresses);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Notes", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(0, 247, 772, 188);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JList listNotes = new JList();
		listNotes.setBounds(199, 180, -192, -169);
		panel_1.add(listNotes);
		
		JFormattedTextField noteDate = new JFormattedTextField();
		noteDate.setBounds(267, 35, 125, 20);
		panel_1.add(noteDate);
		
		JLabel lblNoteDate = new JLabel("Note Date");
		lblNoteDate.setBounds(205, 38, 57, 14);
		panel_1.add(lblNoteDate);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setBounds(195, 11, 567, 24);
		panel_1.add(toolBar);
		
		JButton btnAddnote = new JButton("AddNote");
		toolBar.add(btnAddnote);
		
		JButton btnSavenote = new JButton("SaveNote");
		toolBar.add(btnSavenote);
		
		JButton btnDeletenote = new JButton("DeleteNote");
		toolBar.add(btnDeletenote);
		
		txtNoteText = new JTextField();
		txtNoteText.setBounds(199, 58, 563, 119);
		panel_1.add(txtNoteText);
		txtNoteText.setColumns(10);

	}

	public void setBindings (final ContactZoomPresentationModel model){
		BeanAdapter<ContactZoomPresentationModel> contactAdapter = new BeanAdapter<ContactZoomPresentationModel>(model,true); //create an adapter for observing the model
		BeanAdapter<Address> addressAdapter = new BeanAdapter<Address>(new Address(),true); //create an adapter for observing addresses
		
		ValueModel contactAddressModel = contactAdapter.getValueModel("currentAddress"); //create a value model to observe the current address of the model
		PropertyConnector.connectAndUpdate(contactAddressModel, addressAdapter, "bean"); //bind the current address of the model to the "bean" property of our adapter
		
		ValueModel tmModel = contactAdapter.getValueModel("tableModelAddress"); //create a value model to observe the presentation model's address table model
		PropertyConnector.connectAndUpdate(tmModel, tableAddresses, "model");
		
		//Chain BeanAdapters and value models from the presentation model... to the contact... to the contact's person. Silly, I know...but this is how it's done.
		BeanAdapter<Contact> ca = new BeanAdapter<Contact>(new Contact());
		BeanAdapter<Person> pa = new BeanAdapter<Person>(new Person());
		
		ValueModel cam = contactAdapter.getValueModel("contact");
		PropertyConnector.connectAndUpdate(cam, ca, "bean");
		
		ValueModel pam = ca.getValueModel("person");
		PropertyConnector.connectAndUpdate(pam, pa, "bean");
		
		//NOW we can bind the label!
		ValueModel personNameModel = pa.getValueModel("fullName");
		Bindings.bind(lblContactPersonName, personNameModel);
		
		//Create value models to observe the address adapter
		ValueModel line1Model = addressAdapter.getValueModel("line1");
		ValueModel line2Model = addressAdapter.getValueModel("line2");
		ValueModel line3Model = addressAdapter.getValueModel("line3");
		ValueModel cityModel = addressAdapter.getValueModel("city");
		ValueModel stateProvModel = addressAdapter.getValueModel("stateProv");
		ValueModel postalCodeModel = addressAdapter.getValueModel("postalCode");
		ValueModel countryModel = addressAdapter.getValueModel("country");
		
		Bindings.bind(txtLine1, line1Model);
		Bindings.bind(txtLine2, line2Model);
		//Bindings.bind(txtLine3, line3Model);
		Bindings.bind(txtCity, cityModel);
		Bindings.bind(txtStateProv, stateProvModel);
		Bindings.bind(txtZip, postalCodeModel);
		Bindings.bind(txtCountry, countryModel);
		
		//Value models for connecting buttons to actions
		ValueModel addAddressModel = contactAdapter.getValueModel("executeAddAddress");
		ValueModel saveAddressModel = contactAdapter.getValueModel("executeSaveAddress");
		ValueModel deleteAddressModel = contactAdapter.getValueModel("executeDeleteAddress");
		
		//connect the buttons
		PropertyConnector.connectAndUpdate(addAddressModel, btnAddaddress, "action");
		PropertyConnector.connectAndUpdate(saveAddressModel, btnSaveaddress, "action");
		PropertyConnector.connectAndUpdate(deleteAddressModel, btnDeleteaddress, "action");
		
		//special handling to make the current address update when the table row is changed
		tableAddresses.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				model.setAddressSelectionIndex(tableAddresses.getSelectedRow());
			}
		});
		
		
	}
	@Override
	public void showContact(ContactZoomPresentationModel pm) {
		// TODO Auto-generated method stub
		this.setBindings(pm);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
}

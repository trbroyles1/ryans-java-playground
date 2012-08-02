package com.intergalactic.addressbook.models;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;

import com.google.inject.Inject;
import com.intergalactic.addressbook.Contact;
import com.intergalactic.addressbook.beans.Address;
import com.intergalactic.addressbook.services.ContactZoomService;
import com.jgoodies.binding.beans.ExtendedPropertyChangeSupport;
import com.jgoodies.binding.list.SelectionInList;

public class ContactZoomPresentationModel {
	
	//privates
	private ExtendedPropertyChangeSupport changeSupport = new ExtendedPropertyChangeSupport(this);
	private addressSelectionChangeListener scl = new addressSelectionChangeListener();
	private AddressTableModel tableModel;
	private Contact contact;
	private Address currentAddress;
	private int addressSelectionIndex = -1;
	private AddAddressAction executeAddAddress = new AddAddressAction();
	private SaveAddressAction executeSaveAddress = new SaveAddressAction();
	private DeleteAddressAction executeDeleteAddress = new DeleteAddressAction();
	private ContactZoomService service;
	
	//constructors
	@Inject
	public ContactZoomPresentationModel(Contact c, ContactZoomService service){
		this.service = service;
		addPropertyChangeListener(scl);
	}
	
	//properties
	public AddressTableModel getTableModelAddress(){
		if (tableModel == null){
			tableModel = new AddressTableModel(new SelectionInList<Address>(contact.getAddresses()));
		}
		
		return tableModel;
	}
	
	public void setContact(Contact newContact){
		Contact oldContact = this.contact;
		this.contact = newContact;
		changeSupport.firePropertyChange("contact", oldContact, newContact);
	}
	public Contact getContact(){
		return this.contact;
	}
	
	public Address getCurrentAddress(){
		return this.currentAddress;
	}
	
	public void setCurrentAddress(Address currentAddress){
		Address a = this.currentAddress;
		this.currentAddress = currentAddress;
		changeSupport.firePropertyChange("currentAddress", a, currentAddress);
	}
	
	public int getAddressSelectionIndex() {
		return addressSelectionIndex;
	}

	public void setAddressSelectionIndex(int selectionIndex) {
		int oldSelectionIndex = this.addressSelectionIndex;
		this.addressSelectionIndex = selectionIndex;
		changeSupport.firePropertyChange("addressSelectionIndex", oldSelectionIndex, selectionIndex);
	}
	//actions
	public AddAddressAction getExecuteAddAddress(){
		return executeAddAddress;
	}
	class AddAddressAction extends AbstractAction{

		public AddAddressAction(){
			putValue(NAME, "Add Address");
			putValue(SHORT_DESCRIPTION, "Adds a new address");
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			getTableModelAddress().addRow(new Address());
			
		}
		
	}
	
	public SaveAddressAction getExecuteSaveAddress(){
		return executeSaveAddress;
	}
	class SaveAddressAction extends AbstractAction{

		public SaveAddressAction(){
			putValue(NAME, "Save Address");
			putValue(SHORT_DESCRIPTION, "Saves the current address");
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			getCurrentAddress().Save();
			getContact().AssociateAddress(getCurrentAddress());
			
		}
		
	}
	
	public DeleteAddressAction getExecuteDeleteAddress(){
		return executeDeleteAddress;
	}
	class DeleteAddressAction extends AbstractAction{

		public DeleteAddressAction(){
			putValue(NAME, "Delete Address");
			putValue(SHORT_DESCRIPTION, "Deletes the current address");
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			getCurrentAddress().Delete();
			getTableModelAddress().removeRow(getCurrentAddress());
			
		}
		
	}
	
	
	//listeners
	class addressSelectionChangeListener implements PropertyChangeListener{

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			// TODO Auto-generated method stub
			if (evt.getPropertyName() == "addressSelectionIndex" && (Integer)evt.getNewValue() > -1){
				setCurrentAddress((Address)getTableModelAddress().getRow((Integer) evt.getNewValue()));
			}
			
		}
		
	}

	//public methods
	public void addPropertyChangeListener(PropertyChangeListener x){
		changeSupport.addPropertyChangeListener(x);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener x){
		changeSupport.removePropertyChangeListener(x);
	}
	
	public void showContact(){
		service.showContact(this);
	}

}

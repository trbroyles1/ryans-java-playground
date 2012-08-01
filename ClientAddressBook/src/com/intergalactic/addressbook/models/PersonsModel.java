package com.intergalactic.addressbook.models;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;

import com.intergalactic.addressbook.beans.Person;
import com.jgoodies.binding.beans.ExtendedPropertyChangeSupport;
import com.jgoodies.binding.list.SelectionInList;

public class PersonsModel {
	private ExtendedPropertyChangeSupport changeSupport = new ExtendedPropertyChangeSupport(this);
	private selectionChangeListener scl = new selectionChangeListener();
	private PersonsTableModel tableModel;
	private Person currentPerson;
	private int selectionIndex;
	private AddPersonAction executeAddPerson;
	private SavePersonAction executeSavePerson;
	private DeletePersonAction executeDeletePerson;
	
	public PersonsModel(){
		addPropertyChangeListener(scl);
		executeAddPerson = new AddPersonAction();
		executeSavePerson = new SavePersonAction();
		executeDeletePerson = new DeletePersonAction();
	}
	
	public PersonsTableModel getTableModel(){
		if (tableModel == null){
			tableModel = new PersonsTableModel(new SelectionInList<Person>(Person.Load()));
		}
		
		return tableModel;
	}
	
	public Person getCurrentPerson(){
		return ((PersonsTableModel)getTableModel()).getRow(selectionIndex);
	}
	
	public void setCurrentPerson(Person currentPerson){
		Person oldPerson = this.currentPerson;
		this.currentPerson = currentPerson;
		changeSupport.firePropertyChange("currentPerson", oldPerson, currentPerson);
	}
	
	public int getSelectionIndex() {
		return selectionIndex;
	}

	public void setSelectionIndex(int selectionIndex) {
		int oldSelectionIndex = this.selectionIndex;
		this.selectionIndex = selectionIndex;
		changeSupport.firePropertyChange("selectionIndex", oldSelectionIndex, selectionIndex);
	}
	
	//Actions
	public AddPersonAction getExecuteAddPerson(){
		return executeAddPerson;
	}
	
	public SavePersonAction getExecuteSavePerson(){
		return executeSavePerson;
	}
	
	public DeletePersonAction getExecuteDeletePerson(){
		return executeDeletePerson;
	}

	public void addPropertyChangeListener(PropertyChangeListener x){
		changeSupport.addPropertyChangeListener(x);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener x){
		changeSupport.removePropertyChangeListener(x);
	}
	
	class selectionChangeListener implements PropertyChangeListener{

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			// TODO Auto-generated method stub
			if (evt.getPropertyName() == "selectionIndex" && (Integer)evt.getNewValue() > -1){
				setCurrentPerson((Person)getTableModel().getRow((Integer) evt.getNewValue()));
			}
			
		}
		
	}
	
	class AddPersonAction extends AbstractAction{

		public AddPersonAction(){
			putValue(NAME, "Add Person");
			putValue(SHORT_DESCRIPTION, "Adds a new person");
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			getTableModel().addRow(new Person());
			
		}
		
	}
	
	class SavePersonAction extends AbstractAction{

		public SavePersonAction(){
			putValue(NAME, "Save Person");
			putValue(SHORT_DESCRIPTION, "Saves the current person");
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			getCurrentPerson().Save();
			
		}
		
	}
	
	class DeletePersonAction extends AbstractAction{

		public DeletePersonAction(){
			putValue(NAME, "Delete Person");
			putValue(SHORT_DESCRIPTION, "Deletes the current person");
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			getCurrentPerson().Delete();
			getTableModel().removeRow(getCurrentPerson());
			
		}
		
	}
	
	
	
}

package com.intergalactic.addressbook.models;

import java.util.List;

import com.intergalactic.addressbook.beans.Person;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.list.SelectionInList;

public class PersonsTableModel extends AbstractTableAdapter<Person> {

	SelectionInList<Person> listModel = new SelectionInList<Person>();
	private Person selection;
	
	public PersonsTableModel(SelectionInList<Person> listModel){
		super(listModel,new String[] {"Title","First Name","Last Name"});
		this.listModel = listModel;
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Person p = (Person)getRow(rowIndex);
		if (columnIndex == 0){
			return p.getTitle();
		}
		else if (columnIndex == 1){
			return p.getFirstName();
		}
		else if (columnIndex == 2){
			return p.getLastName();
		}
		else{
			throw new java.lang.IndexOutOfBoundsException("Valid column indexes for PersonsTableModel are 0, 1 and 2");
		}
		// TODO Auto-generated method stub
	}
	
	public void addRow(Person p){
		List<Person> l = listModel.getList();
		l.add(p);
		listModel.setList(l);
		listModel.setSelection(p);
		fireTableDataChanged();
	}
	
	public void removeRow(Person p){
		List<Person> l = listModel.getList();
		l.remove(p);
		listModel.setList(l);
		fireTableDataChanged();
	}

}

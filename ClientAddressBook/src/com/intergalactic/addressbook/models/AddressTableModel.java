package com.intergalactic.addressbook.models;

import java.util.List;

import com.intergalactic.addressbook.beans.Address;
import com.intergalactic.addressbook.beans.Person;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.list.SelectionInList;

public class AddressTableModel extends AbstractTableAdapter<Address> {
	SelectionInList<Address> listModel;
	
	public AddressTableModel(SelectionInList<Address> listModel){
		super(listModel,"Line 1","Line 2","Line 3","City","State / Prov","Postal Code","Country");
		this.listModel = listModel;
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		Address a = getRow(rowIndex);
		if (columnIndex == 0){
			return a.getLine1();
		}
		else if (columnIndex == 1){
			return a.getLine2();
		}
		else if (columnIndex == 2){
			return a.getLine3();
		}
		else if (columnIndex == 3){
			return a.getCity();
		}
		else if (columnIndex == 4){
			return a.getStateProv();
		}
		else if (columnIndex == 5){
			return a.getPostalCode();
		}
		else if (columnIndex == 6){
			return a.getCountry();
		}
		else{
			return null;
		}
		
	}
	
	public void addRow(Address a){
		List<Address> l = listModel.getList();
		l.add(a);
		listModel.setList(l);
		listModel.setSelection(a);
		fireTableDataChanged();
	}
	
	public void removeRow(Address a){
		List<Address> l = listModel.getList();
		l.remove(a);
		listModel.setList(l);
		fireTableDataChanged();
	}

}

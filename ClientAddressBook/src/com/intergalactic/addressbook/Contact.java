package com.intergalactic.addressbook;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Date;
import java.util.List;


import com.intergalactic.addressbook.beans.Address;
import com.intergalactic.addressbook.beans.ContactNote;
import com.intergalactic.addressbook.beans.Person;


public class Contact implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3819411700729003530L;
	
	private Person person;
	private Date dateLastContacted;
	private List<Address> addresses;
	private List<ContactNote> notes;
	
	public Contact(){
		
	}
	
	public Contact(Person p){
		setPerson(p);
		setAddresses(Address.loadForPerson(p.getId()));
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public Date getDateLastContacted() {
		return dateLastContacted;
	}
	public void setDateLastContacted(Date dateLastContacted) {
		this.dateLastContacted = dateLastContacted;
	}
	public List<Address> getAddresses() {
		return addresses;
	}
	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}
	public List<ContactNote> getNotes() {
		return notes;
	}
	public void setNotes(List<ContactNote> notes) {
		this.notes = notes;
	}
	
	public void AssociateAddress(Address a){
		if(!getAddresses().contains(a)){
			getAddresses().add(a);
		}
		Connection conn;
		try{
			Class.forName(Configuration.getDbdriver());
			Statement stmt;
			conn = DriverManager.getConnection(Configuration.getDburl(), Configuration.getDbusername(), Configuration.getDbpassword());
			if (getPerson().getId() > 0 && a.getId() > 0){
				stmt = conn.prepareStatement("INSERT IGNORE INTO ContactAddresses (ContactID,AddressID)" +
						"VALUES(?,?)");
				((PreparedStatement)stmt).setInt(1, getPerson().getId());
				((PreparedStatement)stmt).setInt(2, a.getId());
				((PreparedStatement)stmt).executeUpdate();
			}
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
	}
	
}

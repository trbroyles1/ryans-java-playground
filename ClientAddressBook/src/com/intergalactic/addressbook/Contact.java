package com.intergalactic.addressbook;

import java.io.Serializable;
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
	
	private int id;
	private Person person;
	private Date dateLastContacted;
	private List<Address> addresses;
	private List<ContactNote> notes;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	
}

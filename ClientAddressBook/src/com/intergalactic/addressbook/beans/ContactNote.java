package com.intergalactic.addressbook.beans;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import com.intergalactic.addressbook.Configuration;

public class ContactNote implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4069477032730314010L;
	
	private int id;
	private int contactId;
	private Date dateAdded;
	private String noteText;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getContactId() {
		return contactId;
	}
	public void setContactId(int contactId) {
		this.contactId = contactId;
	}
	public Date getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}
	public String getNoteText() {
		return noteText;
	}
	public void setNoteText(String noteText) {
		this.noteText = noteText;
	}
	
	public static ContactNote Load(int id){
		Connection conn;
		ContactNote c = new ContactNote();
		try{
			Class.forName(Configuration.getDbdriver());
			conn = DriverManager.getConnection(Configuration.getDburl(), Configuration.getDbusername(), Configuration.getDbpassword());
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(String.format("SELECT TOP 1 * FROM ContactNote WHERE ID = %1$", id));
			while (rs.next()){
				c.setId(rs.getInt("ID"));
				c.setContactId(rs.getInt("ContactID"));
				c.setDateAdded(new Date(rs.getDate("DateAdded").toString()));
				c.setNoteText(rs.getString("Note"));
			}
			rs.close();
			conn.close();
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
		return c;
	}
	
	public void Save(){
		Connection conn;
		try{
			Class.forName(Configuration.getDbdriver());
			Statement stmt;
			conn = DriverManager.getConnection(Configuration.getDburl(), Configuration.getDbusername(), Configuration.getDbpassword());
			if (this.id < 1) {
				stmt = conn.prepareCall("SaveNewContactNote");
				((CallableStatement)stmt).setInt("in_contactid", contactId);
				((CallableStatement)stmt).setDate("in_dateadded", new java.sql.Date(dateAdded.getTime()));
				((CallableStatement)stmt).setString("in_note", noteText);
				ResultSet rs = ((CallableStatement)stmt).executeQuery();
				while (rs.next()){
					id = rs.getInt(1);
				}
				rs.close();
			}
			conn.close();
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void Delete(){
		try{
			if (this.id > 0){
				Class.forName(Configuration.getDbdriver());
				Statement stmt;
				Connection conn = DriverManager.getConnection(Configuration.getDburl(), Configuration.getDbusername(), Configuration.getDbpassword());
				stmt = conn.prepareStatement("DELETE FROM ContactNote WHERE ID = ?");
				((PreparedStatement)stmt).setInt(1, id);
				((PreparedStatement)stmt).executeUpdate();
				conn.close();
			}
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
	}

}

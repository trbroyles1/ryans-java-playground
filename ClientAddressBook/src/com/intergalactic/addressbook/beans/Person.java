package com.intergalactic.addressbook.beans;

import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;

import com.intergalactic.addressbook.Configuration;
import com.jgoodies.binding.beans.ExtendedPropertyChangeSupport;

public class Person implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7230637825940654947L;
	private ExtendedPropertyChangeSupport changeSupport = new ExtendedPropertyChangeSupport(this);
	private int id;
	private String title;
	private String firstName;
	private String lastName;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		int oldId = this.id;
		this.id = id;
		changeSupport.firePropertyChange("id", oldId, id);
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		String oldTitle = this.title;
		this.title = title;
		changeSupport.firePropertyChange("title", oldTitle, title);
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		String oldFirstName = this.firstName;
		this.firstName = firstName;
		changeSupport.firePropertyChange("firstName", oldFirstName, firstName);
		changeSupport.firePropertyChange("fullName", oldFirstName, firstName);
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		String oldLastName = this.lastName;
		this.lastName = lastName;
		changeSupport.firePropertyChange("lastName", oldLastName, lastName);
		changeSupport.firePropertyChange("fullName", oldLastName, lastName);
	}
	
	public String getFullName(){
		return String.format("%s %s", firstName,lastName);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener x) {
		changeSupport.addPropertyChangeListener(x);
 	}
		 
	public void removePropertyChangeListener(PropertyChangeListener x) {
		changeSupport.removePropertyChangeListener(x);
	}
	
 	public static Person Load(int id){
		Connection conn;
		Person p = new Person();
		try{
			Class.forName(Configuration.getDbdriver());
			conn = DriverManager.getConnection(Configuration.getDburl(), Configuration.getDbusername(), Configuration.getDbpassword());
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(String.format("SELECT TOP 1 * FROM ListOfNames WHERE ID = %1$", id));
			while (rs.next()){
				p.setId(rs.getInt("ID"));
				p.setTitle(rs.getString("Title"));
				p.setFirstName(rs.getString("FirstName"));
				p.setLastName(rs.getString("LastName"));
			}
			rs.close();
			conn.close();
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
		return p;
	}
	
	public static List<Person> Load(){
		Connection conn;
		ArrayList<Person> lp = new ArrayList<Person>();
		
		try{
			Class.forName(Configuration.getDbdriver());
			conn = DriverManager.getConnection(Configuration.getDburl(), Configuration.getDbusername(), Configuration.getDbpassword());
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(String.format("SELECT * FROM ListOfNames"));
			while (rs.next()){
				Person p = new Person();
				p.setId(rs.getInt("ID"));
				p.setTitle(rs.getString("Title"));
				p.setFirstName(rs.getString("FirstName"));
				p.setLastName(rs.getString("LastName"));
				lp.add(p);
			}
			rs.close();
			conn.close();
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
		return lp;
	}
	
	public void Save(){
		Connection conn;
		try{
			Class.forName(Configuration.getDbdriver());
			Statement stmt;
			conn = DriverManager.getConnection(Configuration.getDburl(), Configuration.getDbusername(), Configuration.getDbpassword());
			if (this.id > 0){
				stmt = conn.prepareStatement("UPDATE ListOfNames SET Title = ?, FirstName = ?, LastName = ? WHERE ID = ?");
				((PreparedStatement)stmt).setString(1, title);
				((PreparedStatement)stmt).setString(2, firstName);
				((PreparedStatement)stmt).setString(3, lastName);
				((PreparedStatement)stmt).setInt(4, id);
				((PreparedStatement)stmt).executeUpdate();
			}
			else {
				stmt = conn.prepareCall("{call SaveNewPerson (?, ?, ?)}");
				((CallableStatement)stmt).setString("in_title", title);
				((CallableStatement)stmt).setString("in_firstname", firstName);
				((CallableStatement)stmt).setString("in_lastname", lastName);
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
				stmt = conn.prepareStatement("DELETE FROM ListOfNames WHERE ID = ?");
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

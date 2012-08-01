package com.intergalactic.addressbook.beans;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.intergalactic.addressbook.Configuration;

public class Address implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4479293234529362511L;
	
	private int id;
	private String line1;
	private String line2;
	private String line3;
	private String city;
	private String stateProv;
	private String postalCode;
	private String country;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLine1() {
		return line1;
	}
	public void setLine1(String line1) {
		this.line1 = line1;
	}
	public String getLine2() {
		return line2;
	}
	public void setLine2(String line2) {
		this.line2 = line2;
	}
	public String getLine3() {
		return line3;
	}
	public void setLine3(String line3) {
		this.line3 = line3;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStateProv() {
		return stateProv;
	}
	public void setStateProv(String stateProv) {
		this.stateProv = stateProv;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	public static Address Load(int id){
		Connection conn;
		Address a = new Address();
		try{
			Class.forName(Configuration.getDbdriver());
			conn = DriverManager.getConnection(Configuration.getDburl(), Configuration.getDbusername(), Configuration.getDbpassword());
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(String.format("SELECT TOP 1 * FROM Address WHERE ID = %1$", id));
			while (rs.next()){
				a.setId(rs.getInt("ID"));
				a.setLine1(rs.getString("Line1"));
				a.setLine2(rs.getString("Line2"));
				a.setLine3(rs.getString("Line3"));
				a.setCity(rs.getString("City"));
				a.setStateProv(rs.getString("StateProv"));
				a.setPostalCode(rs.getString("PostalCode"));
				a.setCountry(rs.getString("Country"));
			}
			rs.close();
			conn.close();
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
		return a;
	}
	
	public void Save(){
		Connection conn;
		try{
			Class.forName(Configuration.getDbdriver());
			Statement stmt;
			conn = DriverManager.getConnection(Configuration.getDburl(), Configuration.getDbusername(), Configuration.getDbpassword());
			if (this.id > 0){
				stmt = conn.prepareStatement("UPDATE Address" +
						"SET Line1 = ?, Line2 = ?, Line3 = ? " +
						"City = ?, StateProv = ?, PostalCode = ?, Country = ?" +
						"WHERE ID = ?");
				((PreparedStatement)stmt).setString(1, line1);
				((PreparedStatement)stmt).setString(2, line2);
				((PreparedStatement)stmt).setString(3, line3);
				((PreparedStatement)stmt).setString(4, city);
				((PreparedStatement)stmt).setString(5, stateProv);
				((PreparedStatement)stmt).setString(6, postalCode);
				((PreparedStatement)stmt).setString(7, country);
				((PreparedStatement)stmt).setInt(8, id);
				((PreparedStatement)stmt).executeUpdate();
			}
			else {
				stmt = conn.prepareCall("SaveNewAddress");
				((CallableStatement)stmt).setString("in_line1", line1);
				((CallableStatement)stmt).setString("in_line2", line2);
				((CallableStatement)stmt).setString("in_line3", line3);
				((CallableStatement)stmt).setString("in_city", city);
				((CallableStatement)stmt).setString("in_stateprov", stateProv);
				((CallableStatement)stmt).setString("in_postalcode", postalCode);
				((CallableStatement)stmt).setString("in_country", country);
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
				stmt = conn.prepareStatement("DELETE FROM Address WHERE ID = ?");
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

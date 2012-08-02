package com.intergalactic.addressbook;

import java.io.Serializable;

public final class Configuration implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2359000615656686996L;
	
	private static final String dbUserName = "test";
	private static final String dbPassWord = "test-test";
	private static final String dbDriver = "com.mysql.jdbc.Driver";
	private static final String dbURL = "jdbc:mysql://fake.us-east-1.rds.amazonaws.com:3306/JavaAddressBook";
	
	public static String getDbusername() {
		return dbUserName;
	}
	public static String getDbpassword() {
		return dbPassWord;
	}
	public static String getDbdriver() {
		return dbDriver;
	}
	public static String getDburl() {
		return dbURL;
	}

}

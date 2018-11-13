package com.bca.bankunit.service;

import java.sql.*;

public class ConnectDB {
	Connection con;
	Statement st;
	ResultSet rs;
	
	public void openDB() throws Exception{
		Class.forName("com.mysql.jdbc.Driver");
		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bcabank","root","");
		st=con.createStatement();
	}
	
	public ResultSet executeQuery(String query) throws Exception{
		rs = st.executeQuery(query);
		return rs;
	}
	public void executeUpdate(String query) throws Exception{
		st.executeUpdate(query);
	}
	public void closeDB() throws Exception{
		st.close();
		con.close();
	}
}

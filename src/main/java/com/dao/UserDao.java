package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.entity.User;


public class UserDao {
	
private Connection conn;

public UserDao(Connection conn) {
	super();
	this.conn = conn;
}


public boolean Register(User u) {
	
	boolean f=false;
	try {
		String sqlString="insert into user_dtls(full_name,email,password) values (?,?,?)";
		PreparedStatement pStatement=conn.prepareStatement(sqlString);
		pStatement.setString(1,u.getFullName());
		pStatement.setString(2,u.getEmail());
		pStatement.setString(3, u.getPassword());
		
	int i=pStatement.executeUpdate();
	if(i==1) {
		f=true;
	}
	
		
	} catch (Exception e2) {
	  e2.printStackTrace();
	}
	return f;
	
}

public User login(String em,String ps) {
	User u=null;
	try {
		String sqlString="select *from user_dtls where email=? and password=?";
		PreparedStatement pStatement=conn.prepareStatement(sqlString);
		pStatement.setString(1, em);
		pStatement.setString(2, ps);
		
		ResultSet rSet=pStatement.executeQuery();
		while (rSet.next()) {
			u=new User();
			u.setId(rSet.getInt(1));
			u.setFullName(rSet.getString(2));
			u.setEmail(rSet.getString(3));
			u.setPassword(rSet.getString(4));
			
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	return u;
	
}
public boolean checkOldPassword(int userid, String oldPassword) {
	boolean f = false;

	try {
		String sql = "select * from user_dtls where id=? and password=?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, userid);
		ps.setString(2, oldPassword);

		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			f = true;
		}

	} catch (Exception e) {
		e.printStackTrace();
	}

	return f;
}
public boolean changePassword(int userid, String newPassword) {
	boolean f = false;

	try {
		String sql = "update user_dtls set password=? where id=?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, newPassword);
		ps.setInt(2, userid);

		int i = ps.executeUpdate();
		if (i == 1) {
			f = true;
		}

	} catch (Exception e) {
		e.printStackTrace();
	}

	return f;
}

}

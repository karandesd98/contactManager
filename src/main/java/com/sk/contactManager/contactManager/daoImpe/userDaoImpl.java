package com.sk.contactManager.contactManager.daoImpe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sk.contactManager.contactManager.security.DataBaseConnectionUtility;
import com.sk.contactManager.contactManager.security.MultipleResultQuery;
import com.sk.contactManager.contactManager.Dao.userDao;
import com.sk.contactManager.contactManager.Models.User;
@Service
public class userDaoImpl implements userDao {

	@Autowired
	private BCryptPasswordEncoder passWordEncoder;
	
	@Override
	public void saveUser(Object... userInfo) {
		
		String name=userInfo[0]!=null?userInfo[0].toString():"";
		String password=userInfo[1]!=null?userInfo[1].toString():"";
		String email=userInfo[2]!=null?userInfo[2].toString():"";
		String about=userInfo[3]!=null?userInfo[3].toString():"";
		password=passWordEncoder.encode(password);
		
		try {
		Connection con=	DataBaseConnectionUtility.getDataSource().getConnection();
		if(con!=null)
		{
			
			String query = " insert into my_user (USER_NAME,EMAIL,PASSWORD,ROLE,ABOUT)"
			        + " values (?, ?, ?, ?, ?)";
			
			// create the mysql insert preparedstatement
		      PreparedStatement preparedStmt = con.prepareStatement(query);
		      preparedStmt.setString (1, name);
		      preparedStmt.setString (2, email);
		      preparedStmt.setString   (3, password);
		      preparedStmt.setString(4, "USER");
		      preparedStmt.setString(5, about);
		 
		   // execute the preparedstatement
		      preparedStmt.execute();
		      
		      preparedStmt.close();
		      con.close();
			
		}
		else
		{
			System.out.println("can not connect to database");
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
			
	}

	

	@Override
	public User getUserByUserName(String userName) {

        User user=null;

		try {
			Connection con=	DataBaseConnectionUtility.getDataSource().getConnection();
			if(con!=null)
			{
				String query = "select USER_ID, USER_NAME, EMAIL, PASSWORD, ROLE, IS_ENABLED, IMAGE, ABOUT from my_user where EMAIL=?";

				PreparedStatement preparedStmt = con.prepareStatement(query);
				preparedStmt.setString(1, userName);

				ResultSet myRs = preparedStmt.executeQuery();

				while (myRs.next()) {
					Integer USER_ID = myRs.getInt(1);
					String USER_NAME = myRs.getString(2);
					String EMAIL = myRs.getString(3);
					String PASSWORD = myRs.getString(4);
					String ROLE = myRs.getString(5);
					Boolean enabled = myRs.getBoolean(6);
					String IMAGE = myRs.getString(7);
					String ABOUT = myRs.getString(8);
					
					System.out.println(EMAIL + "     " + EMAIL);
					
					user=new User(USER_ID,USER_NAME,EMAIL,PASSWORD,ROLE,enabled,IMAGE,ABOUT);
				}

				preparedStmt.close();
				con.close();

			}
			else
			{
				System.out.println("can not connect to database");
			}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		
		
		return user;
	
		
		
	}



	@Override
	public void saveContact(Object... userInfo) {

		String userName=userInfo[0]!=null?userInfo[0].toString():"";
		String nickName=userInfo[1]!=null?userInfo[1].toString():"";
		String email=userInfo[2]!=null?userInfo[2].toString():"";
		String phoneNumber=userInfo[3]!=null?userInfo[3].toString():"";
		String work=userInfo[4]!=null?userInfo[4].toString():"";
		String desc=userInfo[5]!=null?userInfo[5].toString():"";
		Integer userId=userInfo[6]!=null?Integer.parseInt(userInfo[6].toString()) :0;
		String fileName =userInfo[7]!=null?userInfo[7].toString():"";
		
		try {
		Connection con=	DataBaseConnectionUtility.getDataSource().getConnection();
		if(con!=null)
		{
			
			String query = " insert into USER_CONTACT (CONTACT_PERSON_NAME, EMAIL,PHONE_NUMBER,USER_ID,WORK,CONTACT_PERSON_NICKNAME,profilePath)"
			        + " values (?, ?, ?, ?, ?, ?, ?)";
			
		      PreparedStatement preparedStmt = con.prepareStatement(query);
		      preparedStmt.setString (1, userName);
		      preparedStmt.setString(2, email);
		      preparedStmt.setString(3, phoneNumber);
		      preparedStmt.setInt(4, userId);
		      preparedStmt.setString(5, work);
		      preparedStmt.setString(6, nickName);
		      preparedStmt.setString(7, fileName);

		 
		   // execute the preparedstatement
		      preparedStmt.execute();
		      
		      preparedStmt.close();
		      con.close();
			
		}
		else
		{
			System.out.println("can not connect to database");
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
			
	
		
	}

	@Override
	public Map<Integer, List<Object[]>> getMyAllContact(Object[] objects) {
		
		Integer userId=objects[0]!=null? Integer.parseInt(objects[0].toString()) :0;
		
       Map<Integer, List<Object[]>> mapObj=null;
		try {
			Connection con=	DataBaseConnectionUtility.getDataSource().getConnection();
			if(con!=null)
			{
				MultipleResultQuery	mrq =new	MultipleResultQuery(con,"CALL sp_getAllContact(:userId)");
				mrq.setInt("userId", userId);
				mapObj= mrq.execute();

			}
			else
			{
				System.out.println("can not connect to database");
			}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		
		return mapObj;
	}



	@Override
	public Integer deleteContact(Object... objects) {
		
		Integer userContactId=objects[0]!=null? Integer.parseInt(objects[0].toString()) :0;
		Integer res=0;
		try {
			Connection con=	DataBaseConnectionUtility.getDataSource().getConnection();
			if(con!=null)
			{
				
				String query = "update user_contact uc \r\n"
						+ "set is_deleted=1 \r\n"
						+ "where user_contact_id=? ;";
			
		      PreparedStatement preparedStmt = con.prepareStatement(query);
		      preparedStmt.setInt(1, userContactId);
		
		   // execute the preparedstatement
		      res = preparedStmt.executeUpdate();
		      
		      preparedStmt.close();
		      con.close();}
			else
			{
				System.out.println("can not connect to database");
			}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		
		
		return res;
	}



	@Override
	public void updateContact(Object... userInfo) {
		
		String userName=userInfo[0]!=null?userInfo[0].toString():"";
		String nickName=userInfo[1]!=null?userInfo[1].toString():"";
		String email=userInfo[2]!=null?userInfo[2].toString():"";
		String phoneNumber=userInfo[3]!=null?userInfo[3].toString():"";
		String work=userInfo[4]!=null?userInfo[4].toString():"";
		Integer userContactId=userInfo[5]!=null?Integer.parseInt(userInfo[5].toString()) :0;
		
		try {
		Connection con=	DataBaseConnectionUtility.getDataSource().getConnection();
		if(con!=null)
		{
			
			String query = "update user_contact uc \r\n"
					+ "set uc.CONTACT_PERSON_NAME=?,\r\n"
					+ "CONTACT_PERSON_NICKNAME=?,\r\n"
					+ "EMAIL=?,\r\n"
					+ "PHONE_NUMBER=?,\r\n"
					+ "WORK=?\r\n"
					+ "where user_contact_id=? ;";
			
		      PreparedStatement preparedStmt = con.prepareStatement(query);
		      preparedStmt.setString (1, userName);
		      preparedStmt.setString (2, nickName);
		      preparedStmt.setString(3, email);
		      preparedStmt.setString(4, phoneNumber);
		      preparedStmt.setString(5, work);
		      preparedStmt.setInt(6, userContactId);

		   // execute the preparedstatement
		      preparedStmt.execute();
		      
		      preparedStmt.close();
		      con.close();
			
		}
		else
		{
			System.out.println("can not connect to database");
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
			
	}

}

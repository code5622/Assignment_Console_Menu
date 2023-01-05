package com.ineuron;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class JDBC 
{
	// Establish the Connection
	private String url = "jdbc:mysql://localhost:3306/assignmentdb";
	private String user = "root";
	private String pwd = "";
	
	public void read(String sqlQuery, String searchItem)
	{		
		System.out.println(searchItem);
		try
		(
			Connection connection = DriverManager.getConnection(url, user, pwd);
			PreparedStatement psmt = connection.prepareStatement(sqlQuery);
		) 		
		{		
			if(connection != null && psmt != null)
			{
				if(searchItem != null)
				{
					System.out.println(sqlQuery);
					psmt.setString(1, "%" + searchItem + "%");
				}
				
				try(ResultSet resultSet = psmt.executeQuery();)
				{
					int numberofRecords = 0;
					
					while(resultSet.next())
					{
						if(numberofRecords == 0)
							System.out.println("id\tname\taddress\t\t\tgender\tdob");	
						
						int sid = resultSet.getInt(1);
						String sname = resultSet.getString("sname");
						String saddress = resultSet.getString("saddr");
						String sgender = resultSet.getString("sgender");
						java.util.Date date = resultSet.getDate("sdob");
						SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
						String sdob = sdf.format(date);
						String result = String.format("%d\t%s\t%s\t\t%s\t%s", sid, sname, saddress, sgender, sdob);
						System.out.println(result);
						numberofRecords += 1;
					}
					
					if(numberofRecords == 0)
						System.out.println("No student found");					
				}	
				catch(SQLException e)
				{
					e.printStackTrace();
				}
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
	}
	
	public void readDate(String sqlQuery, java.sql.Date searchDate)
	{		

		try
		(
			Connection connection = DriverManager.getConnection(url, user, pwd);
			PreparedStatement psmt = connection.prepareStatement(sqlQuery);
		) 		
		{		
			if(connection != null && psmt != null)
			{
				if(searchDate != null)
				{
					System.out.println(sqlQuery);
					psmt.setString(1, "%" + searchDate + "%");
				}
				
				try(ResultSet resultSet = psmt.executeQuery();)
				{
					int numberofRecords = 0;
					
					while(resultSet.next())
					{
						if(numberofRecords == 0)
							System.out.println("id\tname\taddress\t\t\tgender\tdob");	
						
						int sid = resultSet.getInt(1);
						String sname = resultSet.getString("sname");
						String saddress = resultSet.getString("saddr");
						String sgender = resultSet.getString("sgender");
						java.util.Date date = resultSet.getDate("sdob");
						SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
						String sdob = sdf.format(date);
						String result = String.format("%d\t%s\t%s\t\t%s\t%s", sid, sname, saddress, sgender, sdob);
						System.out.println(result);
						numberofRecords += 1;
					}
					
					if(numberofRecords == 0)
						System.out.println("No student found");					
				}	
				catch(SQLException e)
				{
					e.printStackTrace();
				}
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
	}
	
	public void create(String query, String name, String address, String gender, java.sql.Date date)
	{
		try
		(
			// 1. Resources used in jDBC Application	
			Connection connection = DriverManager.getConnection(url, user, pwd);
				
			// 3. Creation of Statement Object(to move to the location using connection)
			PreparedStatement psmt = connection.prepareStatement(query);
		) 		
		{		
			if(psmt != null)
			{
				psmt.setString(1, name);
				psmt.setString(2, address);
				psmt.setString(3, gender);
				psmt.setDate(4, date);
				
				// 4. Using statement Object execute the query\
				try
				{
					Integer numOfRows = psmt.executeUpdate();
					System.out.println("Number of rows affected is: " + numOfRows + "\n");					
				}
				catch(Exception e)
				{
					System.out.println("Student not found");
				}
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}			
	}
	
	public void update(String query, String sname, String saddress, String sgender, java.sql.Date sdob, String searchName)
	{
		try
		(
			// 1. Resources used in jDBC Application	
			Connection connection = DriverManager.getConnection(url, user, pwd);
			
			// 3. Creation of Statement Object(to move to the location using connection)
			PreparedStatement psmt = connection.prepareStatement(query);
		) 		
		{		
			if(psmt != null)
			{
				psmt.setString(1, sname);
				psmt.setString(2, saddress);
				psmt.setString(3, sgender);
				psmt.setDate(4, sdob);
				psmt.setString(5, searchName);
				
				// 4. Using statement Object execute the query\
				try
				{
					Integer numOfRows = psmt.executeUpdate();
					if(numOfRows > 0)
						System.out.println("Student is updated\n");					
				}
				catch(Exception e)
				{
					System.out.println("Student not found");
				}
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}			
	}
	
	public void delete(String query, String studentName)
	{
		try
		(
			// 1. Resources used in jDBC Application	
			Connection connection = DriverManager.getConnection(url, user, pwd);
				
			// 3. Creation of Statement Object(to move to the location using connection)
			PreparedStatement psmt = connection.prepareStatement(query);
		) 		
		{		
			if(psmt != null)
			{
				psmt.setString(1, studentName);
				
				// 4. Using statement Object execute the query\
				try
				{
					Integer numOfRows = psmt.executeUpdate();
					if(numOfRows > 0)
						System.out.println("Student is removed\n");					
				}
				catch(Exception e)
				{
					System.out.println("Student not found");
				}
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}			
	}
}

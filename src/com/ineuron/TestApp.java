package com.ineuron;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class TestApp
{	
	public static JDBC jdbc;
	public static Scanner sc;
	public static boolean exitMenu;
	
	static
	{
		jdbc = new JDBC();
		sc = new Scanner(System.in);
		exitMenu = false;
	}
	
	public static void main(String[] args)
	{		
		while(!exitMenu)
		{			
			System.out.println("Menu");
			System.out.println("1. Read all students");
			System.out.println("2. Read student by column");
			System.out.println("3. Create a student");
			System.out.println("4. Update a student");
			System.out.println("5. Delete a student");
			System.out.println("6. Exit menu");
			
			int menuItem = sc.nextInt();
			
			switch(menuItem)
			{
				case 1:
				{
					menuReadAll();
					break;
				}
				case 2:
				{
					menuRead();
					break;
				}
				case 3:
				{
					menuCreate();
					break;
				}
				case 4:
				{
					menuUpdate();
					break;
				}
				case 5:
				{
					menuDelete();
					break;
				}
				case 6:
				{
					exitMenu();
				}
			}	
		}
		
		sc.close();
	}
	
	public static void menuReadAll()
	{
		jdbc.read("SELECT * FROM student", null);	
	}
	
	public static void menuRead()
	{
		System.out.println("On which column do you want to search:");
		System.out.println("1. name");
		System.out.println("2. address");
		System.out.println("3. gender");
		System.out.println("4. dob (dd-MM-yyyy)");
		System.out.println("5. doj (MM-dd-yyyy)");
		System.out.println("6. dom (yyyy-MM-dd)");
		
		int menuItem = sc.nextInt();
	
		System.out.println("Enter an item to search for:");
		String searchItem = sc.next();
		
		SimpleDateFormat sdf = null;
		String searchColumn = "sdob";
		java.sql.Date sqlDate = null;
		long time = 0;
		
		switch(menuItem)
		{
			case 1: 
				searchColumn = "sname";
				break;
			case 2:
				searchColumn = "saddr";
				break;
			case 3:
				searchColumn = "sgender";
				break;
			case 4:
				sdf = new SimpleDateFormat("dd-MM-yyyy");
				break;
			case 5:
				sdf = new SimpleDateFormat("MM-dd-yyyy");
				break;		
			case 6:
				sdf = new SimpleDateFormat("yyyy-MM-dd");
				break;					
		}
		
		String sqlQuery = "SELECT * FROM student WHERE " + searchColumn + " LIKE ?";

		if(menuItem < 4)
		{
			jdbc.read(sqlQuery, searchItem);			
		}
		else
		{
			sqlQuery = "SELECT * FROM student WHERE " + searchColumn + " LIKE ?";
			
			try 
			{
				java.util.Date uDate = sdf.parse(searchItem);
				time = uDate.getTime();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			sqlDate = new java.sql.Date(time);
			
			jdbc.readDate(sqlQuery, sqlDate);	
		}
	}
	
	public static void menuCreate() 
	{
		System.out.println("Enter a name:");
		String name = sc.next();
		System.out.println("Enter an address");
		String address = sc.next();
		System.out.println("Enter a gender");
		String gender = sc.next();
		System.out.println("Choose a dateformat");
		System.out.println("1. dob (dd-MM-YYYY)");
		System.out.println("2. doj (MM-dd-yyyy)");
		System.out.println("3. dom (yyyy-mm-dd)");
		System.out.println("4. Exit menu");	
		
		int menuItem = sc.nextInt();
		
		SimpleDateFormat sdf = null;
		java.sql.Date sqlDate = null;
		long time = 0;
		
		switch(menuItem)
		{
			case 1:
			{
				System.out.println("Enter a dob (dd-MM-yyyy)");	
				sdf = new SimpleDateFormat("dd-MM-yyyy");
				break;
			}
			case 2:
			{
				System.out.println("Enter a doj (MM-dd-yyyy)");
				sdf = new SimpleDateFormat("MM-dd-yyyy");
				break;
			}
			case 3:
			{
				System.out.println("Enter a dom (yyyy-MM-dd)");
				sdf = new SimpleDateFormat("yyyy-MM-dd");
				break;
			}
		}
		
		String date = sc.next();
		
		try 
		{
			java.util.Date uDate = sdf.parse(date);
			time = uDate.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		sqlDate = new java.sql.Date(time);
		
		String insertQuery = "INSERT INTO student (`sname`, `saddr`, `sgender`, `sdob`) VALUES(?, ?, ?, ?)"; 
		
		jdbc.create(insertQuery, name, address, gender, sqlDate);
	}
	
	public static void menuUpdate()
	{
		System.out.println("Enter a studentname to update:");
		String searchName = sc.next();
		System.out.println("Enter a name:");
		String name = sc.next();
		System.out.println("Enter an address");
		String address = sc.next();
		System.out.println("Enter a gender");
		String gender = sc.next();
		System.out.println("Enter a dob (dd-MM-yyyy)");	
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		
		String date = sc.next();
		long time = 0;
		
		try 
		{
			java.util.Date uDate = sdf.parse(date);
			time = uDate.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		java.sql.Date sqlDate = new java.sql.Date(time);
		
		jdbc.update(
			"UPDATE student SET sname=?, saddr=?, sgender=?, sdob=? WHERE sname=?", 
			name, address, gender, sqlDate, searchName
		);
	}
	
	public static void menuDelete()
	{
		System.out.println("Enter a name to delete:");
		String studentName = sc.next();
		jdbc.delete("DELETE FROM student WHERE sname=?", studentName);
	}
	
	public static void exitMenu()
	{
		exitMenu = true;
		System.out.println("Menu is closed");
	}
}

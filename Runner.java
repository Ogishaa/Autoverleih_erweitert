package Autoverleih_erweitert;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


public class Runner {				
	static int newTable = 0;
	static String tableName = "";
	
	public static Connection getConnection(String url, String user, String password)  
	{
		try 
		{
			return DriverManager.getConnection(url, user, password);
		} catch (SQLException e) 
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public static void createTable(Connection c, String[] inhalt) 
	{
		try 
		{
			for (int i = 0; i < inhalt.length; i++) 
			{
				if(inhalt[i].contains("id")) 
				{
					inhalt[i] += " INT";
				}
				else if(inhalt[i].contains("preis")) 
				{
					inhalt[i] += " DOUBLE";
				}
				else if(inhalt[i].contains("datum")) 
				{
					inhalt[i] += " DATE";
				}
				else 
				{
					inhalt[i] += String.format(" VARCHAR(%s)", (inhalt[i].length() + 15));
				}
			}
        	Statement stmt = c.createStatement();
            String sql = String.format("CREATE TABLE IF NOT EXISTS %s(" +
                    "%s PRIMARY KEY, %s, %s, %s);", tableName, inhalt[0], inhalt[1], inhalt[2], inhalt[3]);
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) 
		{
            e.printStackTrace();
        }
    }

	public static void main(String[] args) 
	{
		String url = "jdbc:mysql://localhost:3306/AutoKundenVerleih_erweitert";
		String user = "ogisha";
		String password = "ogisha123";
		
		try {
			Connection connection = getConnection(url, user, password);
			System.out.println("Connection erfolgreich\n");
			connection.setAutoCommit(true);
			
			System.out.println("\nDROP TABLES");
			AutoKundenVerleih.dropTableKundeLeihtAuto(connection);
			Auto.dropTableAuto(connection);
			Kunde.dropTableKunde(connection);
			
			System.out.println("\nCREATE TABLES");
			Auto.createTableAuto(connection);
			Kunde.createTableKunde(connection);
			AutoKundenVerleih.createTableKundeLeihtAuto(connection);
			System.out.println();
			
			Scanner scanner = new Scanner (new File("C:\\Users\\ogisha\\Desktop\\Schule\\3AHWII\\INFI\\Test.csv"));
			while(scanner.hasNextLine()) 
			{
				String row = scanner.nextLine();
				
				int anzInhalte = 0;
				boolean notEmpty = false;
				for (int i = 0; i < row.length(); i++) 
				{
					if(row.charAt(i) != ';') notEmpty = true;
					else if (row.charAt(i) == ';' && notEmpty == true) 
					{
						anzInhalte++;
						notEmpty = false;
					}
 				} 
				System.out.println(anzInhalte);
				if(anzInhalte == 0) 
				{
					newTable = 0;
				}
				else 
				{
					String[] inhalt = new String[anzInhalte];
					
					int index = 0;
					for (int i = 0; i < row.length(); i++) 
					{
						if(inhalt[index] == null) 
						{
							inhalt[index] = "";
						}
						char c = row.charAt(i);
						if(c != ';') 
						{
							inhalt[index] += c;
						}
						else index++;
					}

					if (newTable == 0) 
					{
						tableName = inhalt[0];
						newTable++;
					}
					else if(newTable == 1) 
					{				
						newTable++;
					}
					else 
					{
						if(tableName.equals("Auto")) 
						{
							Auto.insertIntoAuto(connection, inhalt);
						}
						else if(tableName.equals("Kunde")) 
						{
							Kunde.insertIntoKunde(connection, inhalt);
						}
						else if(tableName.equals("AutoKundenVerleih")) 
						{
							AutoKundenVerleih.insertIntoKundeLeihtAuto(connection, inhalt);
						}
					}
				}
			}
			scanner.close();
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	} 
}
package Autoverleih_erweitert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Kunde {
	
	public static void dropTableKunde(Connection c ) {
        try {
            Statement stmt = c.createStatement();
            String sql = "DROP TABLE IF EXISTS Kunde;";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	public static void createTableKunde(Connection c) {
        try {
            Statement stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS Kunde(" +
                    "id INT PRIMARY KEY," +							//AUTO_INCREMENT
                    "name VARCHAR(30)," +
                    "email VARCHAR(25)," +
                    "Geburtsdatum DATE);";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	

    public static void insertIntoKunde(Connection c, String[] inhalt) {
    	try {
    		String sql = "INSERT INTO Kunde VALUES (?, ?, ?, ?)";
			PreparedStatement preStmt = c.prepareStatement(sql);
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
			LocalDate datum = LocalDate.parse(inhalt[3], formatter);
			java.sql.Date sqlGeburtsdatum = java.sql.Date.valueOf(datum);
			
			preStmt.setInt(1, Integer.parseInt(inhalt[0]));
			preStmt.setString(2, inhalt[1]);
	        preStmt.setString(3, inhalt[2]);
	        preStmt.setDate(4, sqlGeburtsdatum);
	        preStmt.executeUpdate();
	        
	        preStmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
    }
}

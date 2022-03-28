package Autoverleih_erweitert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Auto {

	public static void dropTableAuto(Connection c ) {
		try {
        	Statement stmt = c.createStatement();
            String sql = "DROP TABLE IF EXISTS Auto;";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
//AUFGABE a)
	public static void createTableAuto(Connection c) {
		try {
        	Statement stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS Auto(" +
                    "kennzeichen VARCHAR(8) PRIMARY KEY," +
                    "modell VARCHAR(30)," +
                    "PreisProTag double," +
                    "Zulassungsdatum DATE);";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	public static void insertIntoAuto(Connection c, String[] inhalt) {
		try {
			String sql = "INSERT INTO Auto VALUES (?, ?, ?, ?)";
			PreparedStatement preStmt = c.prepareStatement(sql);
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
			LocalDate datum = LocalDate.parse(inhalt[3], formatter);
			java.sql.Date sqlZulassungsdatum = java.sql.Date.valueOf(datum);
			
			preStmt.setString(1, inhalt[0]);
			preStmt.setString(2, inhalt[1]);
	        preStmt.setDouble(3, Double.parseDouble(inhalt[2]));
	        preStmt.setDate(4, sqlZulassungsdatum);
	        preStmt.executeUpdate();
	        
	        preStmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}

    }
}

package Autoverleih_erweitert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AutoKundenVerleih {
	public static void dropTableKundeLeihtAuto(Connection c ) {
        try {
            Statement stmt = c.createStatement();
            String sql = "DROP TABLE IF EXISTS KundeLeihtAuto;";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	public static void createTableKundeLeihtAuto(Connection c) {
        try {
            Statement stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS KundeleihtAuto(" +
                    "kundenid INT," +
                    "kennzeichen VARCHAR(8)," +
                    "beginn DATE," +
                    "ende DATE," +
                    "PRIMARY KEY(kundenid, kennzeichen, beginn)," +
                    "FOREIGN KEY(kundenid) REFERENCES Kunde(id) ON DELETE Restrict," +
                    "FOREIGN KEY(kennzeichen) REFERENCES Auto(kennzeichen) ON DELETE Restrict);";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	public static void insertIntoKundeLeihtAuto (Connection c, String[] inhalt) {
		try {
			String sql = "INSERT INTO KundeLeihtAuto VALUES (?, ?, ?, ?);";
			PreparedStatement preStmt = c.prepareStatement(sql);
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
			LocalDate datum = LocalDate.parse(inhalt[3], formatter);
			java.sql.Date sqlEnde = java.sql.Date.valueOf(datum);
			
			java.sql.Date sqlBeginn = java.sql.Date.valueOf(LocalDate.now());
			
			preStmt.setInt(1, Integer.parseInt(inhalt[0]));
			preStmt.setString(2, inhalt[1]);
	        preStmt.setDate(3, sqlBeginn);
	        preStmt.setDate(4, sqlEnde);
	        preStmt.executeUpdate();
			
            preStmt.close();
		} catch (SQLException e) {
            e.printStackTrace();
        }
	}
}
